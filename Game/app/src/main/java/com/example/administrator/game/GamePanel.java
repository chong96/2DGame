package com.example.administrator.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;


class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    private long smokeStartTimer;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<Smokepuff> smoke;

    public GamePanel(Context context) {
        super(context);

        //add callback to surface holder to intercept events
        getHolder().addCallback(this);

        //make new thread where game will be run on
        thread = new MainThread(getHolder(), this);
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

        smokeStartTimer = System.nanoTime();


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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //if player has finger down, make helicopter go up
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying()) {
                player.setPlaying(true);
                player.setUp(true);
            } else {
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
            bg.update();
            player.update();

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
        }
    }
    @Override
    public void draw(Canvas canvas) {

        final float scaleFactorX = getWidth()/(WIDTH * 1.f);
        final float scaleFactorY = getHeight()/(HEIGHT * 1.f);
        if (canvas !=null) {
            final int savedState = canvas.save();


            canvas.scale(scaleFactorX,scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            for (Smokepuff sp : smoke) {
                sp.draw(canvas);
            }

            canvas.restoreToCount(savedState);
        }
    }
}
