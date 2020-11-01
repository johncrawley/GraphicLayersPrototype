package com.jacstuff.graphiclayersprototype;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by John on 29/08/2017.
 * The draw surface object intersects the canvas.
 */

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback {

    private Context context;
    private AnimationThread animationThread;
    protected SurfaceHolder surfaceHolder;
    private ScheduledExecutorService scheduledExecutorService;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private StateManager stateManager;
    private int width, height;

    private Drawable title, background;
    public DrawSurface(Context context){
        super(context);
    }

    public DrawSurface(Context context, StateManager stateManager, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        paint.setStyle(Paint.Style.FILL);
        this.stateManager = stateManager;
        initDrawables();
        // seems to be duplicated under surfaceCreated() method below
        //scheduledExecutorService = Executors.newScheduledThreadPool(4);

    }

    private void initDrawables(){
        title = context.getResources().getDrawable(R.drawable.title);
        background = context.getResources().getDrawable(R.drawable.title_bg);
    }


    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }




    private boolean isUpEvent(MotionEvent event){
        return event.getActionMasked() == MotionEvent.ACTION_POINTER_UP || event.getAction() == MotionEvent.ACTION_UP ;
    }


    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i("prototype", " DrawSurface, entered surfaceCreated()");
        animationThread = new AnimationThread(context, paint, surfaceHolder);
        scheduledExecutorService = Executors.newScheduledThreadPool(4);
        scheduledExecutorService.scheduleAtFixedRate(animationThread, 0, 16, TimeUnit.MILLISECONDS);
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 	  {
        animationThread.setSurfaceSize(width, height);
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
        scheduledExecutorService.shutdown();
    }






}