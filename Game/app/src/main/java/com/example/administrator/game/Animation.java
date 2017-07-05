package com.example.administrator.game;


import android.graphics.Bitmap;

//create heli on screen
public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    //to set delay between frame changes
    public void setDelay(long d) {
        delay = d;
    }

    //to set manual frame
    public void setFrame(int i) {
        currentFrame = i;
    }

    public void update() {
        //time the amount of time elapsed
        long elapsed = (System.nanoTime() - startTime)/1000000;

        //if time elapsed is longer than the delay between frame changes, change frame and reset time elapsed
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }

        //if the loop has ran through entire array of different frames, reset current frame to first in array
        if (currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    //return the image of the current frame
    public Bitmap getImage() {
        return frames[currentFrame];
    }

    public int getFrame() {
        return currentFrame;
    }

    public boolean playedOnce() {
        return playedOnce;
    }


}

