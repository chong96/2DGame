package com.example.administrator.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;


class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    private long smokeStartTimer;
    private long missileStartTimer;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<Smokepuff> smoke;
    private ArrayList<Missile> missiles;
    private ArrayList<TopBorder> topBorder;
    private ArrayList<BottomBorder> bottomBorder;
    private Random rand = new Random();
    private int maxBorderHeight;
    private int minBorderHeight;
    private boolean topDown = true;
    private boolean bottomDown = true;
    //increase to slow down difficulty progression, decrease to speed up difficulty progression
    private int progressDenom = 20;
    private boolean newGameCreated;

    private Explosion explosion;
    private long startReset;
    private boolean reset;
    private boolean disappear;
    private boolean started;
    private int best;


    public GamePanel(Context context) {
        super(context);

        //add callback to surface holder to intercept events
        getHolder().addCallback(this);


        //makes it able to process actions
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        //make background variable
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        //make helicopter variable
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25, 3);
        //make array list to hold smoke balls
        smoke = new ArrayList<Smokepuff>();
        //make array list to hold missiles
        missiles = new ArrayList<Missile>();
        //make array list to hold top border
        topBorder = new ArrayList<TopBorder>();
        //make array list to hold bottom border
        bottomBorder = new ArrayList<BottomBorder>();

        smokeStartTimer = System.nanoTime();
        missileStartTimer = System.nanoTime();

        //make new thread where game will be run on
        thread = new MainThread(getHolder(), this);
        //we can safely start game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        int counter = 0;
        //try to stop game
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //if player has finger down, make helicopter go up
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying() && newGameCreated && reset) {
                player.setPlaying(true);
                player.setUp(true);
            }
            if (player.getPlaying()) {
                if (!started) {
                    started = true;
                }
                reset = false;
                player.setUp(true);
            }
            return true;
        }
        //vice versa ^^
        if (event.getAction() == MotionEvent.ACTION_UP) {
            player.setUp(false);
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update() {
        //update the view of both background and player
        if (player.getPlaying()) {

            if (bottomBorder.isEmpty()) {
                player.setPlaying(false);
                return;
            }

            if (topBorder.isEmpty()) {
                player.setPlaying(false);
                return;
            }

            bg.update();
            player.update();

            //calculate threshold of height the border can have based on the score
            //min and max border height are updated, and the border switched directions when either max or min
            //is met

            maxBorderHeight = 30 + player.getScore() / progressDenom;
            //cap max border so that it can at most take up 1/2 the screen
            if (maxBorderHeight > HEIGHT/4) {
                maxBorderHeight = HEIGHT/4;
            }
            minBorderHeight = 5 + player.getScore() / progressDenom;

            //check top border collision
            for (int i = 0; i < topBorder.size(); i++) {
                if (collision(topBorder.get(i),player)) {
                    player.setPlaying(false);
                }
            }

            //check bottom border collision
            for (int i = 0; i < bottomBorder.size(); i++) {
                if (collision(bottomBorder.get(i),player)) {
                    player.setPlaying(false);
                }
            }

            //update top border
            this.updateTopBorder();

            //update bottom border
            this.updateBottomBorder();

            long missilesElapsed = (System.nanoTime() - missileStartTimer)/1000000;
            //decide on how often missiles come
            if (missilesElapsed > (2000 - player.getScore()/4)) {
                //first missile always down the middle
                if (missiles.size() == 0) {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile),
                            WIDTH + 10, HEIGHT / 2, 45, 15, player.getScore(), 13));
                } else {
                    missiles.add(new Missile(BitmapFactory.decodeResource(getResources(), R.drawable.missile),
                            WIDTH + 10, (int) (rand.nextDouble() * (HEIGHT - (maxBorderHeight * 2)) + maxBorderHeight), 45, 15, player.getScore(), 13 ));
                }
                missileStartTimer = System.nanoTime();
            }

            for (int i = 0; i < missiles.size(); i++) {
                missiles.get(i).update();
                //remove missile and end game
                if (collision(missiles.get(i),player)) {
                    missiles.remove(i);
                    player.setPlaying(false);
                    break;
                }
                //remove missile if miss
                if (missiles.get(i).getX() < -100) {
                    missiles.remove(i);
                }
            }

            //calculate time in between smoke puffs
            long elapsed = (System.nanoTime() - smokeStartTimer)/1000000;
            //add a new smoke every time period
            if (elapsed > 120) {
                smoke.add(new Smokepuff(player.getX(),player.getY()+10));
                //reset smoke timer
                smokeStartTimer = System.nanoTime();
            }

            //check to see if smoke puffs need to be deleted
            for (int i = 0; i < smoke.size(); i++) {
                smoke.get(i).update();
                //if off the screen, delete it
                if (smoke.get(i).getX() < -10) {
                    smoke.remove(i);
                }
            }
        } else {
            player.resetDY();
            if (!reset) {
                newGameCreated = false;
                startReset = System.nanoTime();
                reset = true;
                disappear = true;
                explosion = new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion), player.getX(),
                        player.getY() - 30, 100, 100 ,25);
            }

            explosion.update();
            long resetElapsed = (System.nanoTime() - startReset)/ 1000000;

            if (resetElapsed > 2500 && !newGameCreated) {
                newGame();
            }

        }
    }

    public boolean collision(GameObject a, GameObject b) {
        //check to see if the missile hit
        if (Rect.intersects(a.getRectangle(),b.getRectangle())) {
            return true;
        }
        return false;
    }
    @Override
    public void draw(Canvas canvas) {

        final float scaleFactorX = getWidth()/(WIDTH * 1.f);
        final float scaleFactorY = getHeight()/(HEIGHT * 1.f);
        if (canvas !=null) {
            final int savedState = canvas.save();


            canvas.scale(scaleFactorX,scaleFactorY);
            bg.draw(canvas);
            if (!disappear) {
                player.draw(canvas);
            }
            //draw smokepuffs
            for (Smokepuff sp : smoke) {
                sp.draw(canvas);
            }
            //draw missiles
            for (Missile m : missiles) {
                m.draw(canvas);
            }
            //draw top border
            for (TopBorder tb: topBorder) {
                tb.draw(canvas);
            }
            //draw bottom border
            for (BottomBorder bb: bottomBorder) {
                bb.draw(canvas);
            }
            //draw explosion
            if (started) {
                explosion.draw(canvas);
            }

            drawText(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    private void drawText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        canvas.drawText("DISTANCE: " + player.getScore(), 10, HEIGHT - 10, paint);
        canvas.drawText("BEST: " + best, WIDTH -215, HEIGHT -10, paint);

        if (!player.getPlaying() && newGameCreated && reset) {
            Paint paint1 = new Paint();
            paint1.setTextSize(40);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("PRESS TO START", WIDTH/2 - 50, HEIGHT/2,paint1);

            paint1.setTextSize(20);
            canvas.drawText("PRESS AND HOLD TO GO UP", WIDTH/2 - 50, HEIGHT/2 + 20, paint1);
            canvas.drawText("RELEASE TO GO DOWN", WIDTH/2 - 50, HEIGHT/2 + 40, paint1);
        }
    }

    public void updateTopBorder() {
        //every 50 points, insert randomly placed bottom block to break patter
        if (player.getScore() % 50 == 0) {
            topBorder.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                    topBorder.get(topBorder.size() - 1).getX()+20, 0, (int)((rand.nextDouble() * maxBorderHeight) + 1)));
        }
        for (int i = 0; i < topBorder.size(); i++) {
            topBorder.get(i).update();
            if (topBorder.get(i).getX() < -20) {
                topBorder.remove(i);
                //remove element in array list, replace with new one

                if (topBorder.get(topBorder.size() - 1).getHeight() >= maxBorderHeight) {
                    topDown = false;
                }

                if (topBorder.get(topBorder.size() - 1).getHeight() <= minBorderHeight) {
                    topDown = true;
                }

                //new border will have taller height
                if (topDown) {
                    topBorder.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                            topBorder.get(topBorder.size() - 1).getX() + 20, 0, topBorder.get(topBorder.size() - 1).getHeight() + 1));
                } else {
                    //new border will have shorter height
                    topBorder.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                            topBorder.get(topBorder.size() - 1).getX() + 20, 0, topBorder.get(topBorder.size() - 1).getHeight() - 1));
                }
            }
        }
    }


    public void updateBottomBorder() {
        //every 40 points, insert randomly placed top block to break pattern
        if (player.getScore() % 40 == 0) {
            bottomBorder.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                    bottomBorder.get(bottomBorder.size()-1).getX()+20,(int)((rand.nextDouble() * maxBorderHeight) + (HEIGHT - maxBorderHeight))));
        }

        //update bottom border
        for (int i = 0; i < bottomBorder.size(); i++) {
            bottomBorder.get(i).update();

            //if border is moving off screen, remove it and replace with new one
            if (bottomBorder.get(i).getX() < -20) {
                bottomBorder.remove(i);


                if (bottomBorder.get(bottomBorder.size() - 1).getY() >= HEIGHT - maxBorderHeight) {
                    bottomDown = true;
                }

                if (bottomBorder.get(bottomBorder.size() - 1).getY() <= HEIGHT - minBorderHeight) {
                    bottomDown = false;
                }

                if (bottomDown) {
                    bottomBorder.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick)
                            , bottomBorder.get(bottomBorder.size() - 1).getX() + 20, bottomBorder.get(bottomBorder.size() - 1).getY() + 1));
                } else {
                    bottomBorder.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick)
                            , bottomBorder.get(bottomBorder.size() - 1).getX() + 20, bottomBorder.get(bottomBorder.size() - 1).getY() - 1));
                }
            }
        }
    }

    public void newGame() {

        disappear = false;

        bottomBorder.clear();
        topBorder.clear();

        smoke.clear();
        missiles.clear();

        minBorderHeight = 5;
        maxBorderHeight = 30;

        player.resetDY();
        player.setY(HEIGHT / 2);

        if (player.getScore() > best) {
            best = player.getScore();
        }

        player.resetScore();

        //create initial borders

        //initial top borders
        for (int i = 0; i*20 < WIDTH +40; i++) {

            //first top border created
            if (i == 0) {
                topBorder.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                        i * 20, 0, 10));
            }
            else {
                topBorder.add(new TopBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                        i * 20, 0, topBorder.get(i-1).getHeight() + 1));
            }
        }

        //initial bottom borders
        for (int i = 0; i*20 < WIDTH +40; i++) {
            if (i == 0) {
                bottomBorder.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                        i * 20, HEIGHT - minBorderHeight));
            }
            else {
                bottomBorder.add(new BottomBorder(BitmapFactory.decodeResource(getResources(), R.drawable.brick),
                        i * 20, bottomBorder.get(i-1).getY() - 1));
            }
        }

        newGameCreated = true;
    }
}
