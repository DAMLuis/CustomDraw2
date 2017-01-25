package com.example.luis.customview2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class Yin_yang extends View {

    private int pColor1;
    private int pColor2;
    private int color3;
    private int color4;
    private Paint paint;
    private float [] data= {50,50};
    private RectF rectF;

    public Yin_yang(Context context) {
        super(context);
    }


    public Yin_yang(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public Yin_yang(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Yin_yang,
                0, 0
        );

        try {
            pColor1 = a.getColor(R.styleable.Yin_yang_color1, 0xff000000);
            pColor2 = a.getColor(R.styleable.Yin_yang_color2, 0xff000000);
            color3= a.getColor(R.styleable.Yin_yang_color3, 0xff000000);
            color4 =a.getColor(R.styleable.Yin_yang_color4, 0xff000000);
        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }



    }

    private float[] yinSegment(){

        float[] segValues = new float[this.data.length];
        float Total = getTotal();

        for (int i = 0; i < this.data.length; i++){

            segValues[i] = (this.data[i]/Total) * 360;
        }

        return segValues;
    }

    private float getTotal(){

        float total = 0;

        for (float val : this.data){
            total +=val;
        }

        return total;
    }

    public void setData(float[] data){

        this.data = data;
        invalidate();
    }


    @Override
        protected void onDraw(Canvas canvas) {
        if (data != null){

            int top = 0;
            int left = 0;
            int endBottom = getHeight();
            int endRight = endBottom;
            int x = getWidth();
            int y = getHeight();
            float radius;

            rectF = new RectF(left, top, endRight, endBottom);



            float[] segment = yinSegment();

            float segStartPoint = 270;

            for (int i = 0; i < segment.length; i++){

                int color = pColor1;
                if (i>0){
                    color = pColor2;
                }
                paint.setColor(color);

                canvas.drawArc(rectF, segStartPoint, segment[i], true, paint);

                radius= x/4f;
                paint.setColor(pColor2);
                canvas.drawCircle(x/1.9f, y/4f , radius, paint);


                radius= x/4f;
                paint.setColor(pColor1);
                canvas.drawCircle(x/2.1f, y/1.33f , radius, paint);

                segStartPoint += segment[i];
            }

            radius= x/15;
            paint.setColor(color3);
            canvas.drawCircle(x/1.9f, y/5f , radius, paint);

            paint.setColor(color4);
            canvas.drawCircle(x/2.1f, y/1.2f, radius, paint);






        }


        }



}








