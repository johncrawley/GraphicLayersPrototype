package com.jacstuff.graphiclayersprototype;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;


import java.util.List;

public class TitleState implements  State {

    private int border = 50;
    private int topBorder = 150;
    private StateManager stateManager;
    private Context context;
    private Drawable title, background;
    private int noClickLimit = 20; // number of updates that must happen before we allow touch events to be registered, this stops us starting a new game too soon after a "game over".
    private int noClickCounter = 0;

    private String  tapToPlayText;
    private int sampleTextSize = 1;
    private int textSizeLimit = 152;
    private int textSizeIncrement = 4;
    private Paint paint;




    public TitleState(Context context, int canvasWidth, int canvasHeight, StateManager stateManager){
        this.context = context;
        this.stateManager = stateManager;
        initTitleGraphics(canvasWidth, canvasHeight);
    }



    private void initTitleGraphics(int canvasWidth, int canvasHeight){
        DefaultDrawableLoader imageLoader = new DefaultDrawableLoader(context);
        Rect bounds = new Rect(0,0,canvasWidth, canvasHeight);
        title = imageLoader.getDrawable(R.drawable.title);
        title.setBounds(border, topBorder, bounds.right - border, 600);
        background = imageLoader.getDrawable(R.drawable.title_bg);
        background.setBounds(0, 0, canvasWidth, canvasHeight);
        tapToPlayText = context.getString(R.string.tap_to_start);
        initPaint();
    }
    private void initPaint() {
        paint = new Paint();
        paint.setColor(DARK_RED);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(35);

    }

    private final int DARK_RED = Color.rgb(130,22,22);





    private void drawMessage(Canvas canvas){

        int textX = canvas.getWidth() / 2;
        int textY = (int) (canvas.getHeight() / 2);


        canvas.drawText(tapToPlayText, textX, textY, paint);
        canvas.drawText(tapToPlayText, textX -6, textY-4, paint);

        paint.setColor(Color.LTGRAY);
        canvas.drawText(tapToPlayText, textX -3, textY -2, paint);

    }


    public void onPause(){
    }
    public void onResume(){
    }


    @Override
    public void update() {
        calculateTextSizeIncrement();
        sampleTextSize += textSizeIncrement;
        noClickCounter++;
        printUpdate();
    }

    private int updateCount = 0;
    private int updateLimit = 1000;
    private void printUpdate(){
        updateCount++;
        if(updateCount > updateLimit){
            System.out.println("-------------updating!");
            updateCount = 0;
        }
    }
    private void calculateTextSizeIncrement(){

        if(sampleTextSize > textSizeLimit) {
            textSizeIncrement = -4;
        }
        else if(sampleTextSize < 6){
            textSizeIncrement = 4;
        }
    }


    private int drawCount = 0;
    private int drawLimit = 1000;

    @Override
    public void draw(Canvas canvas, Paint paint) {
      //  background.draw(canvas);
        title.draw(canvas);
        drawMessage(canvas);
        printDraw();
    }

    private void printDraw(){
        drawCount++;
        if(drawCount > drawLimit){
            System.out.println("-------------drawing!");
            drawCount = 0;
        }
    }

    @Override
    public void handleTouchPoints(List<TouchPoint> touchPoints) {
        if(isTooSoonToRegisterClickEvents()){
            return;
        }
    }



    private boolean isTooSoonToRegisterClickEvents(){
        return noClickCounter < noClickLimit;
    }


    @Override
    public void destroy(){

    }
}