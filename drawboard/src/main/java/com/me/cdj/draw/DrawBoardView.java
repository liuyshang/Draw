package com.me.cdj.draw;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.effect.Effect;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2015/11/6.
 */
public class DrawBoardView extends View {

    private Context context;

    private static final String TAG = "DrawBoardView";
    /**
     * 屏幕宽度（像素）
     * */
    private int width = 0;

    /**
     * 屏幕高度（像素）
     * */
    private int height = 0;

    /**
     * 屏幕密码（0.75 / 1.0 / 1.5）
     * */
    private float density = 0;

    /**
     * 屏幕密度DPI（120 / 160 / 240）
     * */
    private int densityDpi =0;

    /**
     * 起始点坐标
     * */
    private float preX;
    private float preY;

    /**
     * 定义路径，画笔
     * */
    public Path path;
    public Paint paint;

    /**
     * 定义一个内存中的图片，该图片将作为缓冲区
     * */
    private Bitmap cacheBitmap = null;

    /**
     * 定义 cacheBitmap上的Canvas对象
     * */
    private Canvas cacheCanvas = null;

    /**
     * 保存图片
     * */
    private File folder;
    private String folderPath;
    private File file;
    private String filePath;

    /**
     * 增加路径效果
     * */
    private PathEffect effect;

    public DrawBoardView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DrawBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DrawBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    public void init(){

        /**
         * 获取屏幕尺寸
         * */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

//        width = context.getWallpaperDesiredMinimumHeight();
//        height = context.getWallpaperDesiredMinimumHeight();

        /**
         * 创建一个与该View相同大小的缓存区
         * */
        cacheBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        path = new Path();
        effect = new CornerPathEffect(20);

        /**
         * 设置cacheCanvas将会绘制到内存中的cacheBitmap上
         * */
        cacheCanvas.setBitmap(cacheBitmap);

        /**
         * 设置画笔属性
         * */
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setPathEffect(effect);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint bmpPaint = new Paint();
        /**
         * 将cacheBitmap绘制到该View组建上
         * */
        canvas.drawBitmap(cacheBitmap, 0, 0, bmpPaint);
        /**
         * 沿着path绘制
         * */
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                /**
                 * 当手指按下时，记下X，Y的坐标
                 * */
                path.moveTo(event.getX(),event.getY());
                preX = event.getX();
                preY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * 更新X，Y的坐标
                 * */
                path.quadTo(preX,preY,event.getX(),event.getY());
                preX = event.getX();
                preY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(path, paint);
                invalidate();
                break;
            default:
                break;
        }

        return true;
    }


    /**
     * 清空
     * */
    public void clear(){
        init();
        invalidate();
    }

    /**
     * 保存
     * */
    public void save(){
        folderPath = Environment.getExternalStorageDirectory() + "/Draw";
        Log.i(TAG,"folderPath: " + folderPath);
        folder = new File(folderPath);
        if (!folder.exists()){
            folder.mkdirs();
        }

        String name = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date(System.currentTimeMillis()));

        if (folder.exists()){
            filePath = folderPath + "/" + name + ".png";
            Log.i(TAG,"filePath: " + filePath);
            file = new File(filePath);
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            cacheBitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
