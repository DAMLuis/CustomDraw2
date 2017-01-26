package com.example.luis.customview2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class Yin_yang extends View {


    private int pColor1, pColor2;
    private int color3, color4;
    private int indice = 0;
    private float x = 0, y = 0;
    private Paint paint;
    private float[] data = {50, 50};
    private RectF rectF;
    private String accion = "accion";
    private Point puntoCentro;

    public Yin_yang(Context context) {
        super(context);
    }


    public Yin_yang(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public Yin_yang(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        puntoCentro = new Point();

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Yin_yang,
                0, 0
        );

        try {
            pColor1 = a.getColor(R.styleable.Yin_yang_color1, 0xff000000);
            pColor2 = a.getColor(R.styleable.Yin_yang_color2, 0xff000000);
            color3 = a.getColor(R.styleable.Yin_yang_color3, 0xff000000);
            color4 = a.getColor(R.styleable.Yin_yang_color4, 0xff000000);
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }


    }

    private float[] yinSegment() {

        float[] segValues = new float[this.data.length];
        float Total = getTotal();

        for (int i = 0; i < this.data.length; i++) {

            segValues[i] = (this.data[i] / Total) * 360;
        }

        return segValues;
    }

    private float getTotal() {

        float total = 0;

        for (float val : this.data) {
            total += val;
        }

        return total;
    }

    public void setData(float[] data) {

        this.data = data;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (data != null) {

            int top = 0;
            int left = 0;
            int endBottom = getHeight();
            int endRight = endBottom;
            x = getWidth();
            y = getHeight();
            float radius;

            rectF = new RectF(left, top, endRight, endBottom);


            float[] segment = yinSegment();

            float segStartPoint = 270;

            for (int i = 0; i < segment.length; i++) {

                int color = pColor1;
                if (i > 0) {
                    color = pColor2;
                }
                paint.setColor(color);


                canvas.drawArc(rectF, segStartPoint, segment[i], true, paint);


                //punto arriba mediano
                radius = x / 4f;
                paint.setColor(pColor2);
                canvas.drawCircle(x / 1.9f, y / 4f, radius, paint);

                //punto abajo mediano
                radius = x / 4f;
                paint.setColor(pColor1);
                canvas.drawCircle(x / 2.1f, y / 1.33f, radius, paint);


                segStartPoint += segment[i];


            }

            radius = x / 15;
            paint.setColor(color3);
            canvas.drawCircle(x / 1.9f, y / 5f, radius, paint);

            paint.setColor(color4);
            canvas.drawCircle(x / 2.1f, y / 1.2f, radius, paint);




        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int xx=(int)event.getX();
        int yy=(int)event.getY();
        Bitmap drawable = getBitmapFromView(this);
        int pixel=drawable.getPixel(xx,yy);

        int red =Color.red(pixel);
        int green = Color.green(pixel);
        int blue=Color.blue(pixel);

        Toast.makeText(getContext(), "Rojo"+ red +" verde" + green  + " azul " + blue, Toast.LENGTH_SHORT).show();

        if(red==255 && green==255 && blue==255 ){
            accion="blanco";
        }
        return super.onTouchEvent(event);
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight() , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }



}








