package com.example.administrator.game;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Smokepuff extends GameObject {
    public int r;
    public Smokepuff(int x, int y) {

        //set radius of smoke clouds and position of clouds
        r = 5;
        super.x = x;
        super.y = y;
    }

    public void update() {
        //move the clouds as they plane moves
        x += -10;
    }

    public void draw(Canvas canvas) {
        //set up paint
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);

        //draw 3 circles onto the canvas
        canvas.drawCircle(x-r,y-r,r,paint);
        canvas.drawCircle(x-r+2,y-r+2,r,paint);
        canvas.drawCircle(x-r+4,y-r+4,r,paint);

    }
}
