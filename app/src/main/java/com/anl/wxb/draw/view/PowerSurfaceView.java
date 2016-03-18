package com.anl.wxb.draw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * author: admin
 * time: 2016/3/17 12:09
 * e-mail: lance.cao@anarry.com
 */
public class PowerSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    /**
     * X轴每次增加的单位值
     */
    private static final double ADD_COUNT = 0.0005;
    /**
     * X,Y轴放大的倍数
     */
    private static final double MULTIPLE_XY = 300;
    /**
     * 画布的宽高
     */
    private float screenX, screenY;
    /**
     * 线程标志
     */
    private boolean falg = false;
    /**
     * 原点位置
     */
    private float originX, originY;
    /**
     * X轴的坐标
     */
    private float x;
    /**
     * X增加次数
     */
    private int time = 0;
    /**
     * 平方次数
     */
    private int exponent = 1;

    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;

    public PowerSurfaceView(Context context) {
        this(context, null);
    }

    public PowerSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.i(">>>", "init");
        mHolder = this.getHolder();
        mHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.GREEN);

        mPath = new Path();

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(">>>", "surfaceCreated");
        screenX = this.getWidth();
        screenY = this.getHeight();

        //原点在画布的中点
        originX = screenX / 2;
        originY = screenY / 2;
        falg = true;
        mPath.reset();
        time = 0;
        x = -1;

        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        while (falg) {
            //返回报错处理
            try {
                mCanvas = mHolder.lockCanvas();
                mCanvas.drawColor(Color.GRAY);
                drawPath(mCanvas);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null)
                    mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawPath(Canvas canvas) {
        //停止画图
        if ((1 - x) < ADD_COUNT) {
            falg = false;
        }
        mPath.moveTo((float) (originX + x * MULTIPLE_XY),
                (float) (originY - Math.pow(x, exponent) * MULTIPLE_XY));
        time++;
        x += time * ADD_COUNT;
        mPath.lineTo((float) (originX + x * MULTIPLE_XY),
                (float) (originY - Math.pow(x, exponent) * MULTIPLE_XY));
        canvas.drawPath(mPath, mPaint);
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    public void setFalg(boolean falg) {
        this.falg = falg;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            falg = false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
