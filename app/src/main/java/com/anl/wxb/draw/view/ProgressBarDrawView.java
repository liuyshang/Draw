package com.anl.wxb.draw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.anl.wxb.draw.R;

/**
 * author: admin
 * time: 2016/3/18 15:51
 * e-mail: lance.cao@anarry.com
 * 进度条显示，用另一个不完整的圆覆盖背景圆圈
 */
public class ProgressBarDrawView extends View {

    /**
     * 圆 的画笔
     */
    private Paint mPaint,pPaint;
    /**
     * 文字的画笔
     */
    private Paint tPaint;
    /**
     * 矩形
     */
    private RectF mRectf;
    /**
     * 画笔宽度
     */
    private float ptWidth;
    /**
     * 画笔颜色
     */
    private int[] ptColor;
    /**
     * 文字颜色
     */
    private int txColor;
    /**
     * 圆心位置
     */
    private float originX, originY;
    /**
     * 半径
     */
    private float radius;
    /**
     * 百分比
     */
    private float precent;
    /**
     * 线性渐变
     * */
    private LinearGradient linearGradient;
    /**
     * 镜像渐变
     * */
    private RadialGradient radialGradient;
    /**
     * 角度渐变
     * */
    private SweepGradient sweepGradient;

    public ProgressBarDrawView(Context context) {
        this(context, null);
    }

    public ProgressBarDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Log.i(">>>","init");
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.circle);
        ptWidth = array.getFloat(R.styleable.circle_ptwidth, 20);
        radius = array.getFloat(R.styleable.circle_radius, 200);
        txColor = array.getInt(R.styleable.circle_txcolor, Color.RED);
        array.recycle();

        ptColor = new int[]{Color.MAGENTA,Color.BLACK,Color.CYAN,Color.BLUE,Color.GREEN,Color.RED};
        linearGradient = new LinearGradient(0,0,radius,radius,ptColor[((int) (Math.random() * 6))],
                ptColor[((int) (Math.random() * 6))], Shader.TileMode.REPEAT);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(ptWidth);
        mPaint.setColor(Color.GRAY);

        pPaint = new Paint();
        pPaint.setStyle(Paint.Style.STROKE);
        pPaint.setAntiAlias(true);
        pPaint.setStrokeWidth(ptWidth);
        pPaint.setShader(linearGradient);

        tPaint = new Paint();
        tPaint.setStyle(Paint.Style.FILL);
        tPaint.setAntiAlias(true);
        tPaint.setColor(txColor);

        precent = 0;

        mRectf = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(">>>", "onDraw");
        super.onDraw(canvas);
        drawPath(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(">>>", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //圆心位置
        originX = getWidth() / 2;
        originY = getHeight() / 2;

        mRectf.top = originY - radius;
        mRectf.left = originX - radius;
        mRectf.right = originX + radius;
        mRectf.bottom = originY + radius;
    }

    private void drawPath(Canvas canvas) {
        //画背景圆
        canvas.drawArc(mRectf, 0, 360, false, mPaint);
        //画进度条， precent / 100 = x / 360

        canvas.drawArc(mRectf, -90, (float) (precent * 3.6), false, pPaint);
        //显示百分比
        if (precent != 0) {
            String text = ((int) precent) + "%";
            tPaint.setTextSize(radius / 4);
            float textWidth = tPaint.measureText(text, 0, text.length());
            canvas.drawText(text, originX - textWidth / 2, originY + radius / 8, tPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setPrecent(int precent) {
        this.precent = precent;
        invalidate();
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public void setPaintWidth(float ptWidth) {
        this.ptWidth = ptWidth;
        invalidate();
    }
}
