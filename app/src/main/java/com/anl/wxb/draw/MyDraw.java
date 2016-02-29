package com.anl.wxb.draw;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

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

// 自定义 view,需要实现 一个显式的构造函数，重写 onDraw 方法，一切的操作都在该方法上进行
public class MyDraw extends View {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyDraw(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDraw(Context context) {
        super(context);
    }

    public MyDraw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    /**
     * 要画图形，最起码要有三个对象：
     * 1.颜色对象 Color
     * 2.画笔对象 Paint
     * 3.画布对象 Canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
//        设置字体颜色
        paint.setColor(Color.YELLOW);
//        设置字体大小
        paint.setTextSize(30);
//        让画出的图形是空心的
        paint.setStyle(Paint.Style.STROKE);
//        设置画出的线的粗细程度
        paint.setStrokeWidth(5);

//        画出一根线
        canvas.drawLine(0, 0, 200, 200, paint);
//        画矩形
        canvas.drawRect(200,300,300,400,paint);
//        画圆
        canvas.drawCircle(200,200,100,paint);
//        画出字符串 drawText(String text, float x, float y, Paint paint)
//        y 是基准线，不是 字符串的 底部
        canvas.drawText("LiuYSha",300,300,paint);
//        canvas.drawLine(0,60,500,60,paint);
//        绘制图片
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),150,150,paint);

        super.onDraw(canvas);
    }
}
