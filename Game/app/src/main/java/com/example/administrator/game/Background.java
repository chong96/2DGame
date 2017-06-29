package com.example.administrator.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res) {
        image = res;
        //speed at which background moves
        dx = GamePanel.MOVESPEED;
    }

    public void update() {
        //update the background moving
        x += dx;
        //if the image is at the end of its width, reset back to the beginning
        if (x < -GamePanel.WIDTH) {
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        //draw the background with starting x and y
        canvas.drawBitmap(image, x, y, null);
        //if the background is off the screen, reset it
        if (x < 0) {
            canvas.drawBitmap(image, x + GamePanel.WIDTH, y, null);
        }
    }

}
