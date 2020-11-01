package com.jacstuff.graphiclayersprototype;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.SurfaceHolder;

class AnimationThread extends Thread{

    private final SurfaceHolder sh;
    Context ctx;
    int x = 200;
    int y = 200;
    int yLimit = 700;
    int initialY = 200;
    int width = 800;
    int height = 800;
    private Paint paint;

    AnimationThread(Context context, Paint paint, SurfaceHolder surfaceHolder){
        this.sh = surfaceHolder;
        this.ctx = context;
        this.paint = paint;
    }

    public void run() {
       // Bitmap bmp2 = bmp1.copy(bmp1.getConfig(), true);
        updateY();
        updateX();
        Canvas canvas = null;
        try {
            canvas = sh.lockCanvas(null);
            synchronized (sh) {
                if (canvas == null){
                    return;
                }
                //paint.setColor(Color.BLACK);
               // canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                //paint.setColor(Color.TRANSPARENT);
               // canvas.drawRect(0,0,width, height, paint);
                //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
              //  paint.setColor(Color.RED);
               // canvas.drawRect(x,y, x+80, y+80, paint);
                canvas.drawBitmap(getCanvasBitmap(), 0,0 , null);

            }
        } finally {
            if (canvas != null) {
                sh.unlockCanvasAndPost(canvas);
            }
        }
    }



    private Bitmap getCanvasBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawCircle(400, 400, 200, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

        paint.setColor(Color.RED);
        canvas.drawRect(x,y, x+80, y+80, paint);

        return bitmap;
    }

    private void updateY(){
        y++;
        if(y == yLimit){
            y = initialY;
        }
    }

    private void updateX(){
        x++;
        if(x == yLimit){
            x = initialY;
        }
    }
    void setSurfaceSize(int width, int height) {
        synchronized (sh) {
            this.width = width;
            this.height = height;
        }
    }

}