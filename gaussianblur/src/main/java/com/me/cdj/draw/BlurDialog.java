package com.me.cdj.draw;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * author: Lance
 * time: 2015/12/31 14:45
 * e-mail: lance.cao@anarry.com
 */
public abstract class BlurDialog extends Dialog{

    private Context mContext;
    /**
     * 显示模糊的图片
     * */
    private ImageView ivBlur;
    /**
     * 显示透明度
     * */
    private ImageView ivAlpha;
    /**
     * 布局
     * */
    private RelativeLayout rlContent;
    /**
     * 透明变化
     * */
    private AlphaAnimation animation;
    /**
     * Dialog进入动画
     * */
    private Animation inAnim;
    private Bitmap bitmap;

    public BlurDialog(Context context) {
        super(context);
        mContext = context;
    }

    public BlurDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_blur_layout);

        ivBlur = (ImageView) findViewById(R.id.iv_blur_show);
        ivAlpha = (ImageView) findViewById(R.id.iv_show_alpha);
        rlContent = (RelativeLayout) findViewById(R.id.rl_contetn);

        //背景透明动画
        animation = new AlphaAnimation(0,1.0f);
        animation.setDuration(500);
        animation.setFillAfter(true);

        //创建Dialog
        onCreateDialog();
    }

    protected abstract void onCreateDialog();

    public void setInAnim(Animation inAnim) {
        this.inAnim = inAnim;
    }

    public void setIvAlpha(int color) {
        ivAlpha.setBackgroundColor(color);
    }

    public Context getmContext() {
        return mContext;
    }

    public void setRlContent(int layourResId) {
        rlContent.addView(View.inflate(getmContext(),layourResId,null));
    }

    /**
     * 显示Dialog类，同时进行动画播放
     * */
    @Override
    public void show() {
        super.show();
        //开始截屏并进行高斯模糊
        new BlurAsynTask().execute();
        //背景开始渐变
        if (animation != null){
            ivAlpha.startAnimation(animation);
        }

        //弹出框的动画
        if (inAnim != null){
            rlContent.startAnimation(inAnim);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //要设置下面这个，不然下次截图会返回上次的画面，不能实时更新
        ((Activity)mContext).getWindow().getDecorView().setDrawingCacheEnabled(false);
        //对bitmap进行回收
        if (bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
    }

    /**
     * 实现高斯模糊
     * */
    private class BlurAsynTask extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //截图
            ((Activity)mContext).getWindow().getDecorView().setDrawingCacheEnabled(true);
            bitmap =  ((Activity)mContext).getWindow().getDecorView().getDrawingCache();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            //进行高斯模糊
            if (bitmap != null){
                bitmap = RenderScriptBlur.doBlur(bitmap,20,false,mContext);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (bitmap != null){
                ivBlur.setImageBitmap(bitmap);
                Toast.makeText(mContext,"高斯模糊完成",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
