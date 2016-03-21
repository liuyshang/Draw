package com.anl.wxb.draw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * author: admin
 * time: 2016/3/21 10:34
 * e-mail: lance.cao@anarry.com
 */
public class LoadingSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Canvas mCanvas;
    private RectF mRectf;
    /**
     * 画笔的宽度
     */
    private float ptWidth = 20;
    /**
     * 画笔的颜色
     */
    private int[] ptColor;
    /**
     * 圆心的位置
     */
    private float originX, originY;
    /**
     * 圆的半径
     */
    private float radius = 200;
    /**
     * 线程运行标志
     */
    private boolean flag;
    /**
     * 起始角度
     */
    private float startAngle;
    /**
     * 弧度
     */
    private float sweepAngle;
    /**
     * 弧度加减的标志
     */
    private boolean sflag;
    /**
     * 线性渐变
     */
    private LinearGradient linearGradient;

    public LoadingSurfaceView(Context context) {
        this(context, null);
    }

    public LoadingSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.i(">>>", "init");
        mHolder = this.getHolder();
        mHolder.addCallback(this);

        ptColor = new int[]{Color.MAGENTA, Color.BLACK, Color.CYAN, Color.BLUE, Color.GREEN, Color.RED};
        linearGradient = new LinearGradient(0, 0, radius / 4, radius / 4, ptColor[((int) (Math.random() * 6))],
                ptColor[((int) (Math.random() * 6))], Shader.TileMode.MIRROR);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ptWidth);
        mPaint.setShader(linearGradient);

        startAngle = 0;
        sweepAngle = 0;
        mRectf = new RectF();
        flag = true;
        sflag = true;
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(">>>", "surfaceCreated");
        originX = this.getWidth() / 2;
        originY = this.getHeight() / 2;

        mRectf.top = originY - radius;
        mRectf.bottom = originY + radius;
        mRectf.left = originX - radius;
        mRectf.right = originX + radius;

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
        Log.i(">>>", "run");
        while (flag) {
            //返回键报错处理
            if (mHolder != null)
                try {
                    mCanvas = mHolder.lockCanvas();
                    mCanvas.drawColor(Color.GRAY);
                    drawPath();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (mCanvas != null) {
                        mHolder.unlockCanvasAndPost(mCanvas);
                    }
                }
        }
    }

    private void drawPath() {
        mCanvas.drawArc(mRectf, startAngle, sweepAngle, false, mPaint);
        if (sflag) {
            startAngle -= 1;
            sweepAngle += 2;
        } else {
            startAngle += 1;
            sweepAngle -= 2;
        }
        if (startAngle == -179 && sweepAngle > 0) {
            startAngle = 0;
            sweepAngle = -360;
            sflag = true;
        }
        if (startAngle == -179 && sweepAngle < 0) {
            startAngle = 180;
            sweepAngle = 0;
            sflag = false;
        }
        if (startAngle == 359 && sweepAngle < 0) {
            startAngle = 180;
            sweepAngle = 360;
            sflag = false;
        }
        if (startAngle == 359 && sweepAngle > 0) {
            startAngle = 0;
            sweepAngle = 0;
            sflag = true;
        }
    }

    public void setPtWidth(float ptWidth) {
        this.ptWidth = ptWidth;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
