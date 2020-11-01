package com.jacstuff.graphiclayersprototype;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Animator {

    private SurfaceView view;

    private Context context;
    private AnimationThread animationThread;
    protected SurfaceHolder surfaceHolder;
    private ScheduledExecutorService scheduledExecutorService;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Drawable title, background;

    public Animator(Context context, SurfaceView view){
        this.context = context;
        this.view = view;
        this.surfaceHolder = view.getHolder();
        //surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        initDrawables();
        surfaceCreated();
    }


    private void initDrawables(){
       title = context.getResources().getDrawable(R.drawable.title);
        background = context.getResources().getDrawable(R.drawable.title_bg);
    }


    public void surfaceCreated() {
        animationThread = new AnimationThread(context, paint, surfaceHolder);
        scheduledExecutorService = Executors.newScheduledThreadPool(4);
        scheduledExecutorService.scheduleAtFixedRate(animationThread, 0, 25, TimeUnit.MILLISECONDS);
    }

    public void stop(){
        scheduledExecutorService.shutdown();
    }


}
