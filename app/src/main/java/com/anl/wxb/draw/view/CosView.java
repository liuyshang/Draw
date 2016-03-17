package com.anl.wxb.draw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * author: admin
 * time: 2016/3/15 17:17
 * e-mail: lance.cao@anarry.com
 * 画 y = Cosx 函数
 */
public class CosView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    /**
     * X每次增加的数值
     */
    private static final double ADD_COUNT = 0.02;
    /**
     * X初始值增加的角度
     */
    private static final double ADD_ANGLE = Math.PI / 2;
    /**
     * 2*PI
     */
    private static final double TWO_PI = 2 * Math.PI;
    /**
     * Y放大倍数
     */
    private static final double MULTIPLEY = 150;
    /**
     * X放大倍数
     */
    private static final double MULTIPLEX = 40;
    /**
     * 次数
     */
    private int time = 0;

    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;

    /**
     * 布局的宽度
     */
    private int screenX;
    /**
     * 布局的高度
     */
    private int screenY;
    /**
     * X Y
     */
    private float x = 0;
    /**
     * 初始X值
     */
    private float startX = 0;
    /**
     * 原点的位置
     */
    private float originX, originY;
    /**
     * 线程标志
     */
    private boolean falg = false;

    public CosView(Context context) {
        this(context, null);
    }

    public CosView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
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

        originX = 0;
        originY = screenY / 2;
        startX = 0;
        falg = true;

        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(">>>", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(">>>", "surfaceDestroyed");
    }

    @Override
    public void run() {
        while (falg) {
            mCanvas = mHolder.lockCanvas();
            mCanvas.drawColor(Color.GRAY);
            canvasPath(mCanvas);
            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * 画 Cos 曲线
     *
     * @param canvas
     */
    private void canvasPath(Canvas canvas) {
        cosAgain();
        x = (float) (startX + time * ADD_COUNT);
        mPath.moveTo((float) (originX + (x * MULTIPLEX)), (originY - (float) (Math.cos(x) * MULTIPLEY)));
        //更新 x 值
        time++;
        x = (float) (startX + time * ADD_COUNT);
        mPath.lineTo((float) (originX + (x * MULTIPLEX)), (originY - (float) (Math.cos(x) * MULTIPLEY)));
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 重新画一次Cos曲线
     */
    private void cosAgain() {
        if (Math.abs(4 * TWO_PI - time * ADD_COUNT) < ADD_COUNT) {
            mPath.reset();
            startX += ADD_ANGLE;
            originX -= startX * MULTIPLEX;
            time = 0;
        }
        if (startX == TWO_PI){
            startX = 0;
            originX = 0;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //点击暂停
        falg = !falg;
        if (falg) {
            //重新启动一个线程
            new Thread(this).start();
        }
        return super.onTouchEvent(event);
    }

    public void setFalg(boolean falg) {
        this.falg = falg;
    }
}
