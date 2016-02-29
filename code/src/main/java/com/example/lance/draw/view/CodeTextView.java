package com.example.lance.draw.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.lance.draw.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * author: Lance
 * time: 2016/1/21 17:41
 * e-mail: lance.cao@anarry.com
 */
public class CodeTextView extends View {

    private Context context;
    private Paint mPaint;
    private Rect mRect;
    /**
     * 文本
     */
    private String mTitle;
    /**
     * 颜色
     */
    private int mColor;
    /**
     * 大小
     */
    private int mSize;

    public CodeTextView(Context context) {
        this(context, null);
    }

    public CodeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public CodeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CodeTextView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CodeTextView_content:
                    mTitle = array.getString(attr);
                    break;
                case R.styleable.CodeTextView_colors:
                    mColor = array.getColor(attr, Color.CYAN);
                    break;
                case R.styleable.CodeTextView_size:
                    mSize = array.getDimensionPixelSize(attr, (int) TypedValue
                            .applyDimension(TypedValue.COMPLEX_UNIT_SP, 16
                                    , getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }
        array.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mRect);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(">>>", "onMeasure");
        int measureX = measure(widthMeasureSpec);
        int measureY = measure(heightMeasureSpec);

        setMeasuredDimension(measureX, measureY);
    }

    private int measure(int spec) {
        int result = 0;

        int specMode = MeasureSpec.getMode(spec);
        int specSize = MeasureSpec.getSize(spec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.UNSPECIFIED) {
            result = 200;
        }
        Toast.makeText(context,String.valueOf(result),Toast.LENGTH_SHORT).show();
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(">>>", "onDraw");
        int top = 50;
        int left = 30;
        int measureX = getMeasuredWidth();
        int measureY = getMeasuredHeight();

        mPaint.setColor(Color.GRAY);
        canvas.drawRect(left, top, left + measureX, top + measureY, mPaint);

        mPaint.setTextSize(mSize);
        mPaint.setColor(mColor);
        canvas.drawText(mTitle, getWidth() / 2 - mRect.width() / 2
                , getHeight() / 2 - mRect.height() / 2, mPaint);
//        canvas.save();
//        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(">>>", "ACTION_DOWN");
                mTitle = randomText();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.i(">>>", "ACTION_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < 4){
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer buffer = new StringBuffer();
        for (Integer i:set){
            buffer.append("" + i);
        }
        return buffer.toString();
    }
}
