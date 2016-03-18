package com.anl.wxb.draw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * author: admin
 * time: 2016/3/18 11:13
 * e-mail: lance.cao@anarry.com
 */
public class RippleSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private static final int UNIT = 10;
    private static final double ADD_COUNT = 0.1;
    private static final int[] PAINT_COLOR = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.BLACK, Color.MAGENTA};

    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Canvas mCanvas;

    /**
     * 画笔的透明度
     */
    private int paintAlpha;
    /**
     * 画笔的宽度
     */
    private float paintWidth;
    /**
     * 圆心的位置
     */
    private float originX, originY;
    /**
     * 半径
     */
    private float radius;
    /**
     * 线程运行的标志
     */
    private boolean flag = false;
    /**
     * 画布的宽高
     */
    private float screenX, screenY;

    public RippleSurfaceView(Context context) {
        this(context, null);
    }

    public RippleSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.i(">>>", "init");
        mHolder = this.getHolder();
        mHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setAlpha(paintAlpha);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintWidth);
        mPaint.setColor(Color.GREEN);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(">>>", "surfaceCreated");
        screenX = this.getWidth();
        screenY = this.getHeight();

        originX = screenX / 2;
        originY = screenY / 2;
        initData();
        new Thread(this).start();
    }

    private void initData() {
        flag = true;
        paintAlpha = 255;
        paintWidth = 0;
        radius = 0;
        mPaint.setColor(PAINT_COLOR[((int) (Math.random() * 7))]);
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
        while (flag) {
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
        if (Math.abs(paintAlpha) <= 5) {
            flag = false;
        }
        paintAlpha -= ADD_COUNT * 20;
        paintWidth += ADD_COUNT * 5;
        mPaint.setAlpha(paintAlpha);
        mPaint.setStrokeWidth(paintWidth);
        Log.i(">>>", "alpha: " + paintAlpha);
        radius += ADD_COUNT * 2;
        mCanvas.drawCircle(originX, originY, radius * UNIT, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(">>>", "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                originX = event.getX();
                originY = event.getY();
                initData();
                new Thread(this).start();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            flag = false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
