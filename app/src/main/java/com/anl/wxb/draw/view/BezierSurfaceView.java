package com.anl.wxb.draw.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by admin on 2015/8/17.
 * 贝塞尔曲线
 */
public class BezierSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private SurfaceHolder mHolder;  //用于控制SurfaceView
    private Canvas mCanvas;         //声明一张画布
    private Paint qPaint;     //声明一只画笔
    private Path qPath;     //声明一条路径
    private int qStartX, qStartY, qEndX, qEndY, qControlX, qControlY;    //    分别代表贝塞尔曲线的开始坐标，结束坐标，控制点坐标
    private int screenW, screenH; //用于屏幕的宽高
    private boolean flag;       //flag用于线程的标识
    private boolean cReturn;    //Return用于标识贝塞尔曲线的控制点坐标是否返回

    public BezierSurfaceView(Context context) {
        this(context, null);
        Log.i(">>>", "GraphSurfaceView");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BezierSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BezierSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BezierSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {
        mHolder = this.getHolder();  //获得SurfaceHolder对象
        mHolder.addCallback(this);   //添加状态监听

        qPaint = new Paint();          //创建一个画笔对象
        qPaint.setAntiAlias(true);    //消除锯齿
        qPaint.setStyle(Paint.Style.STROKE); //设置画笔风格为描边
        qPaint.setStrokeWidth(7);       //设置描边的宽度为3
        qPaint.setColor(Color.GREEN);   //设置画笔的颜色为绿色
        qPath = new Path();     //        创建路径对象

        qStartY = 0;
        qStartY = 0;
        setFocusable(true);     //设置焦点
    }

    //    当SurfaceView创建时调用
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(">>>", "surfaceCreated");
//        获取屏幕的宽和高
        screenW = this.getWidth();
        screenH = this.getHeight();

        qEndX = screenW - 100;    //设置贝塞尔曲线的终点横坐标为屏幕宽度减100；
        qEndY = ((int) ((Math.random() + 0.1) * screenH));         //设置贝塞尔曲线的终点

//        设置贝塞尔曲线的控制点坐标为终点坐标与起点坐标对应的差值的一半
        qControlX = (qEndX - qStartX) / 2;
        qControlY = (qEndY - qStartY) / 2;

        flag = true;                 //设置线程标识为true
        cReturn = false;            //设置贝塞尔曲线控制点坐标不返回
        new Thread(this).start();            //启动线程
    }

    //    当SurfaceView视图发生改变的时候调用
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(">>>", "surfaceChanged");
    }

    //    当SurfaceView销毁时调用
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(">>>", "surfaceDestroyed");
    }

    @Override
    public void run() {
        while (flag) {
            mDraw();
            mGameLogic();
        }
    }

    /**
     * 调用自定义绘图方法
     */
    private void mDraw() {
        //返回报错处理
        try {
            mCanvas = mHolder.lockCanvas();           //获得画布对象，开始对画布画画
            mCanvas.drawColor(Color.BLACK);             //设置画布颜色为黑色
            canvasMethod(mCanvas);                  //调用自定义的方法，主要是在传进去的画布对象上画画
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);     //把画布显示在屏幕上
            }
        }
    }

    /**
     * 自定义方法，简单绘制一些基本图形
     *
     * @param mCanvas
     */
    private void canvasMethod(Canvas mCanvas) {
//        重置Path里的所有路径
        qPath.reset();
//        设置Path的起点
        qPath.moveTo(qStartX, qStartY);
//        设置贝塞尔曲线的控制点坐标和终点坐标
        qPath.quadTo(qControlX, qControlY, qEndX, qEndY);
//        画出贝塞尔曲线
        mCanvas.drawPath(qPath, qPaint);
    }

    /**
     * 调用自定义的逻辑方法
     */
    private void mGameLogic() {
        //判断贝塞尔曲线的控制点横坐标是否返回
        if (!cReturn) {    //控制点横坐标不返回
            //判断控制点横坐标是否小于终点横坐标减3
            if (qControlX < (qEndX - 3)) {    //小于
                qControlX += 3;    //控制点横坐标往右3
            } else {    //不小于
                cReturn = true;    //设置控制点横坐标返回
            }
        } else {    //控制点横坐标返回
            //判断控制点横坐标是否大于起点横坐标加3
            if (qControlX > (qStartX + 3)) {    //大于
                qControlX -= 3;    //控制点横坐标减3
            } else {    //不大于
                cReturn = false;    //设置控制点横坐标不返回
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(">>>", "onTouchEvent");
        flag = !flag;
        if (flag) {
            //重新启动一个线程
            new Thread(this).start();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            flag = false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getqStartX() {
        return qStartX;
    }

    public void setqStartX(int qStartX) {
        this.qStartX = qStartX;
    }

    public int getqStartY() {
        return qStartY;
    }

    public void setqStartY(int qStartY) {
        this.qStartY = qStartY;
    }

    public int getqEndX() {
        return qEndX;
    }

    public void setqEndX(int qEndX) {
        this.qEndX = qEndX;
    }

    public int getqEndY() {
        return qEndY;
    }

    public void setqEndY(int qEndY) {
        this.qEndY = qEndY;
    }
}
