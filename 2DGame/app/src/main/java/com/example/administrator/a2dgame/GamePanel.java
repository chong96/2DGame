package com.example.administrator.a2dgame;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Administrator on 6/28/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public GamePanel(Context context) {
        super(context);

        //add callback to surface holder
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
