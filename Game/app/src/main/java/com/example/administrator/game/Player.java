package com.example.administrator.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class Player extends GameObject{
    private Bitmap spriteSheet;
    private int score;
    private boolean playing;
    private boolean up;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap res, int w, int h, int numFrames) {
        //starting horizontal spot
        x = 100;
        //starting vertical spot
        y = GamePanel.HEIGHT/2;
        //change in y
        dy = 0;
        score = 0;
        height = h;
        width = w;

        //new array of the different frames of helicopter
        Bitmap[] image = new Bitmap[numFrames];
        //helicopter picture
        spriteSheet = res;

        for(int i =0; i < image.length; i++) {
            //create array of different frames
            image[i] = Bitmap.createBitmap(spriteSheet, i*width, 0, width, height);
        }

        //send frames to animation object
        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

    }

    public void setUp(boolean b) {
        up = b;
    }

    //update the player
    public void update() {
        long elapsed = (System.nanoTime() - startTime)/1000000;
        //update the score for each second passed
        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }

        animation.update();

        //update the acceleration of the helicopter
        if (up) {
            dy -= 1;
        } else {
            dy += 1;
        }

        //cap the acceleration
        if (dy > 14) {
            dy =14;
        }

        if (dy < -14) {
            dy = -14;
        }

        //update the position of the helicopter
        y += dy*2;
    }

    //draw the helicopter
    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public int getScore() {
        return score * 3;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean b) {
        playing = b;
    }

    public void resetDY() {
        dy = 0;
    }

    public void resetScore() {
        score = 0;
    }

}
