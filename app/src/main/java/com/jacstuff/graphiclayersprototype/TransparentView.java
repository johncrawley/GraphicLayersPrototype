package com.jacstuff.graphiclayersprototype;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class TransparentView extends View {

    private final static String TAG = "TransparentCardView";

    private int cardTopMargin = 0;
    private int cardWidth = 200;
    private int cardHeight = 200;
    private int cardRadiusInner = 40;
    private int cardRadiusOuter = 60;
    private int stroke = 10;
    private int transparentHeight = 0;
    private float centerX = 0;
    private float centerY = 0;
    private int mainWidth = 0;
    private int mainHeight = 0;

    private int cardColor;
    private Context context;
    private Bitmap jewel;
    private Bitmap jewelGreen, jewelBlue;

    //Flag for checking whether view is drawn or not.
    private boolean isDrawn = false;

    private OnLayoutListener layoutListener;

    public TransparentView(Context context) {
        super(context);
        this.context = context;
    }

    public TransparentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public TransparentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    public TransparentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Log.i(TAG,"init");
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TransparentCardView);
        cardTopMargin = a.getInt(R.styleable.TransparentCardView_cardTopMargin, cardTopMargin);
        cardWidth = a.getInt(R.styleable.TransparentCardView_cardWidth, cardWidth);
        cardHeight = a.getInt(R.styleable.TransparentCardView_cardHeight, cardHeight);
        cardRadiusInner = a.getInt(R.styleable.TransparentCardView_cardRadiusInner, cardRadiusInner);
        cardRadiusOuter = a.getInt(R.styleable.TransparentCardView_cardRadiusOuter, cardRadiusOuter);
        cardColor =  a.getInt(R.styleable.TransparentCardView_cardColor, cardColor);
        jewel = getBitmap(R.drawable.jewel_yellow);
        jewelGreen = getBitmap(R.drawable.jewel_green);
        jewelBlue = getBitmap(R.drawable.jewel_blue);
        a.recycle();
    }
    private int angle = 0;

    void updateAndDraw(){

        angle = (angle + 8) % 360;
    }

    /**
     * Calculates required parameters for TransparentCardView creation
     */
    private void defaultAttributes() {
        Log.i(TAG,"defaultAttributes");
        mainWidth = getWidth();
        mainHeight = getHeight();
        cardTopMargin = mainHeight / 10;
        cardWidth = mainWidth - (mainWidth / 5);
        cardHeight = mainHeight / 2;
        cardRadiusInner = cardWidth / 6;
        cardRadiusOuter = cardRadiusInner + (cardRadiusInner / 10);
        stroke = (cardRadiusInner / 3);
        transparentHeight = cardRadiusOuter;
        centerX = cardWidth / 2;
        centerY = transparentHeight + (cardRadiusOuter / 6);
        cardColor = R.color.colorPrimary;
    }

    //@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v(TAG, "onDraw : getWidth: " + getWidth() + ", getHeight: " + getHeight());
        if (!isDrawn)
            defaultAttributes();
        isDrawn = true;
        Bitmap bitmap = bitmapDraw();
        canvas.drawBitmap(bitmap, getWidth() / 2f - cardWidth / 2f, cardTopMargin, null);

    }



    private Bitmap getBitmap(int resId) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 1;
        return BitmapFactory.decodeResource(context.getResources(), resId, opts);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout");
        defaultAttributes();

        if (this.layoutListener != null && !isDrawn)
            this.layoutListener.onLayout();

        isDrawn = true;
    }

    private String numberText = "";
    private int number = 0;
    /**
     * Creates a bitmap with transparent circle & a card with dynamic height.
     *
     * @return
     */
    private Bitmap bitmapDraw() {
        number++;
        numberText = String.valueOf(number);

        Bitmap bitmap = Bitmap.createBitmap(cardWidth, cardHeight, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);

        Canvas canvasBitmap = new Canvas(bitmap);
        //canvasBitmap.drawColor(getResources().getColor(cardColor));

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        //canvasBitmap.drawCircle(centerX, centerY, cardRadiusInner, paint);

        RectF outerRectangle = new RectF(0, 0, cardWidth, transparentHeight);
        //canvasBitmap.drawRect(outerRectangle, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvasBitmap.drawText(numberText, centerX - 120 , centerY + 300, paint);
        paint.setColor(getResources().getColor(cardColor));

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stroke);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvasBitmap.drawCircle(centerX, centerY, cardRadiusOuter, paint);

        canvasBitmap.save();
        int jewelX = cardWidth / 2;
        int jewelY = (int)(cardWidth / 2f);
        canvasBitmap.translate(jewelX, jewelY);
        canvasBitmap.rotate(angle);
        int middleOffsetX = jewel.getWidth() / 2;
        int middleOffsetY = jewel.getHeight() / 2;

        int firstOffsetX = jewel.getWidth() + middleOffsetX;

        int firstOffsetY = middleOffsetY;
        canvasBitmap.drawBitmap(jewel,  - middleOffsetX, - middleOffsetY , paint);
        canvasBitmap.drawBitmap(jewelGreen,  - firstOffsetX, - firstOffsetY , paint);
        canvasBitmap.drawBitmap(jewelBlue,  middleOffsetX, - firstOffsetY , paint);
        canvasBitmap.restore();
        return bitmap;
    }


    /**
     * Listener for notifying view layout is done.
     */
    public interface OnLayoutListener {
        void onLayout();
    }
}