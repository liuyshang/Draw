package com.anl.wxb.draw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.anl.wxb.draw.R;

/**
 * Created by admin on 2015/8/17.
 */
/*
* Android 画图最基本的三个对象(color,paint,canvas)
* 三个类都存在 android.graphics包下
* 1.Color：颜色对象，相当于现实生活中的调料
* 2.Paint：画笔对象，相当于现实生活中画图用的笔--主要还是对画笔进行设置
* 3.Canvas：画布对象，相当于现实生活中画图用的纸或布
*
* 常用方法
* 1、Color：颜色对象
* 1.通过Color.颜色名，获取颜色，因为是静态的，返回一个整数值
*   int BLACK,BLUE,CYAN,DKGRAY,GRAY,GREEN,LTGRAY,MAGENTA,RED,TRANSPARENT,WHITE,YELLOW
* 2.调用静态的argb方法，可以调出个性的颜色
*   public static int argb (int alpha, int red, int green ,int blue){}
*
*   alpha是设置透明度的，red设置红色，green设置绿色，blue设置蓝色，范围0-255
*
* 2、Paint：画笔对象
* setAntiAlias:设置画笔的锯齿效果
* setColor：设置画笔颜色
* setARGB：设置画笔的a,r,g,b值
* setAlpha:设置透明度
* setTextSize：设置字体尺寸
* setStyle：设置画笔风格，空心或实心
* setStrokeWidth：设置空心的边框
* getColor：得到画笔的颜色
* getAlpha:得到画笔的Alpha值
*
* 3、Canvas：画布对象，绘制常见图形的方法
* 1.直线：drawLine(float startX, float startY, float stopX, float stopY, Paint paint)
* 2.矩形：drawRect(float left, float top, float right, float bottom,Paint paint)
* 3.圆形：drawCircle(float cx, float cy, float radius, Paint paint)
* */

public class CircleDrawView extends View {

    /**
     * 单位大小
     */
    private static final int UNIT = 100;
    /**
     * 半径大小，UNIT的倍数
     */
    private float radius = 3;
    /**
     * 画笔的颜色
     */
    private int paintColor = Color.GREEN;
    /**
     * 画笔的大小
     */
    private int paintWidth = 5;
    /**
     * 画布的大小
     */
    private float screenX, screenY;
    /**
     * 圆心的位置
     */
    private float originX, originY;

    private Paint mPaint;
    private Path mPath;

    public CircleDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleDrawView(Context context) {
        this(context, null);
    }

    public CircleDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.circle);
        radius = array.getFloat(R.styleable.circle_radius, 3);
        paintColor = array.getInt(R.styleable.circle_paintcolor, Color.GREEN);
        paintWidth = array.getInt(R.styleable.circle_paintwidth, 5);
        array.recycle();

        mPaint = new Paint();
        mPaint.setColor(paintColor);
        mPaint.setStrokeWidth(paintWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(">>>", "CircleDrawView onDraw");
        canvas.drawCircle(originX, originY, radius * UNIT, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(">>>", "ACTION_DOWN");
                originX = event.getX();
                originY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                originX = event.getX();
                originY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(">>>", "ACTION_CANCEL");
                originX = screenX / 2;
                originY = screenY / 2;
                invalidate();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(">>>", "CircleDrawView onMeasure");
        screenX = getMeasuredWidth();
        screenY = getMeasuredHeight();
        originX = screenX / 2;
        originY = screenY / 2;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    public void setPaintWidth(int paintWidth) {
        this.paintWidth = paintWidth;
    }
}
