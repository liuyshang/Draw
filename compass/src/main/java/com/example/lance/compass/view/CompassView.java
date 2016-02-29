package com.example.lance.compass.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.example.lance.compass.R;

/**
 * author: Lance
 * time: 2016/1/19 18:09
 * e-mail: lance.cao@anarry.com
 */
public class CompassView extends View {

    /**
     * 显示的方向
     */
    private float bearing;
    /**
     * 指针
     */
    private Paint markerPaint;
    /**
     * 文字
     */
    private Paint textPaint;
    /**
     * 罗盘
     */
    private Paint circlePaint;
    /**
     * 北
     */
    private String northString;
    /**
     * 南
     */
    private String southString;
    /**
     * 东
     */
    private String eastString;
    /**
     * 西
     */
    private String westString;
    /**
     * 字体高度
     */
    private int textHeight;

    public CompassView(Context context) {
        this(context, null);
    }

    public CompassView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CompassView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intCompassView();
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
    }

    @SuppressLint("NewApi")
    private void intCompassView() {
        setFocusable(true);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(getResources().getColor(R.color.background));
        circlePaint.setStrokeWidth(1);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStrokeWidth(5);
        textPaint.setTextSize(40);
        textPaint.setColor(getResources().getColor(R.color.text));

        markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        markerPaint.setStrokeWidth(5);
        markerPaint.setColor(getResources().getColor(R.color.marker));

        northString = getResources().getString(R.string.cardinal_north);
        eastString = getResources().getString(R.string.cardinal_east);
        westString = getResources().getString(R.string.cardinal_west);
        southString = getResources().getString(R.string.cardinal_south);

        textHeight = (int) textPaint.measureText("yY");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mMeasureWidth = getMeasuredWidth();
        int mMeasureHeight = getMeasuredHeight();

        int px = mMeasureWidth / 2;
        int py = mMeasureHeight / 2;

        int radius = Math.min(px, py);

        //绘制背景
        canvas.drawCircle(px, py, radius, circlePaint);
        //旋转视图
        canvas.save();
        canvas.rotate(-bearing, px, py);

        int textWidth = (int) textPaint.measureText("W");
        int cardinalX = px - textWidth / 2;
        int cardinalY = py - radius + textHeight;

        //每15绘制一个标记，每45绘制一个文本
        for (int i = 0; i < 24; i++) {
            //绘制一个标记
            canvas.drawLine(px, py - radius, px, py - radius + 20, markerPaint);
            canvas.save();
            canvas.translate(0, textHeight);

            //绘制基本方位
            if (i % 6 == 0) {
                String dirString = "";
                switch (i) {
                    case 0:
                        dirString = northString;
                        //绘制指针
                        int arrowY = 2 * textHeight;
                        canvas.drawLine(px, arrowY, px - 10, 3 * textHeight, markerPaint);
                        canvas.drawLine(px, arrowY, px + 10, 3 * textHeight, markerPaint);
                        break;
                    case 6:
                        dirString = eastString;
                        break;
                    case 12:
                        dirString = southString;
                        break;
                    case 18:
                        dirString = westString;
                        break;
                }
                canvas.drawText(dirString, cardinalX, cardinalY, textPaint);
            } else if (i % 3 == 0) {
                //每45绘制文本
                String angle = String.valueOf(i * 15);
                float angleTextWidth = textPaint.measureText(angle);

                int angleTextX = (int) (px - angleTextWidth / 2);
                int angleTextY = py - radius + textHeight;
                canvas.drawText(angle, angleTextX, angleTextY, textPaint);
            }
            canvas.restore();
            canvas.rotate(15, px, py);
        }
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //罗盘是一个填充尽可能多的空间的圆，通过设置最短的边界、高度或者宽度来设置测量的尺寸
        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);

        int d = Math.min(measuredHeight, measuredWidth);

        setMeasuredDimension(d, d);
    }

    private int measure(int measureSpec) {
        int result = 0;

        //对测量说明进行解码
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            //如果没有指定界限，则返回默认大小200
            result = 200;
        } else {
            //由于希望填充可用的空间
            //所以总是返回整个可用的边界
            result = specSize;
        }
        return result;
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.dispatchPopulateAccessibilityEvent(event);
        if (isShown()) {
            String bearingStr = String.valueOf(bearing);
            if (bearingStr.length() > AccessibilityEvent.MAX_TEXT_LENGTH) {
                bearingStr = bearingStr.substring(0, AccessibilityEvent.MAX_TEXT_LENGTH);
            }
            event.getText().add(bearingStr);
            return true;
        } else
            return false;
    }
}
