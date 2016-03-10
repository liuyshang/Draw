package com.example.lance.draw.ui;

import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lance.draw.R;
import com.example.lance.draw.util.ViewInject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * bitmap代表一张位图
 * <p/>
 * createBitmap(Bitmap source,int x,int y,int width,int height);
 * createScaleBitmap(Bitmap src,int dstWidth,int dstHeight,boolean filter)
 * createBitmap(int width,int height,Bitmap.Config config)
 * CreateBitmap(Bitmap source,int x,int y,int width,int height,Matrix m,boolean filter)
 * <p/>
 * BitmapFactory 工具类
 * decodeByteArray(byte[] data,int offset,int length)
 * decodeFile(String pathName)
 * decodeResource(Resource res,int id)
 * decodeStream(InputStream is)
 */

/**
 * 查看目录下的图片
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    /**
     * Assets按钮
     */
    @ViewInject(R.id.bt_assets)
    private Button btAssets;
    /**
     * DCIM按钮
     */
    @ViewInject(R.id.bt_tumblr)
    private Button btTumblr;
    /**
     * 图片
     */
    @ViewInject(R.id.iv)
    private ImageView iv;

    private String[] images = null;
    private AssetManager manager = null;
    private File file;
    private File[] fileIV;
    private int currentImg = 0;
    private int currentIv = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        checkFilePermission();

        //获取assets目录下所有文件
        try {
            manager = getAssets();
            images = manager.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取/DCIM/Camera目录下所有的文件
        file = new File(Environment.getExternalStorageDirectory().getPath() +
                File.separator + "DCIM" + File.separator + "Camera");
        if (file.isDirectory()) {
            fileIV = file.listFiles();
        }
    }

    private void checkFilePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void setListener() {
        btAssets.setOnClickListener(this);
        btTumblr.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_assets:
                setAssets();
                break;
            case R.id.bt_tumblr:
                setDCIM();
                break;
            default:
                break;
        }
    }

    private void setDCIM() {
        if (fileIV == null || fileIV.length == 0) {
            Toast.makeText(MainActivity.this, "该文件没有权限或者该文件夹为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //如果数组越界
        if (currentIv >= fileIV.length) {
            currentIv = 0;
        }
        //找到下一个图片
        while (!fileIV[currentIv].getName().endsWith(".jpg")) {
            currentIv++;
            //如果数组越界
            if (currentIv >= fileIV.length) {
                currentIv = 0;
            }
        }
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        //如果图片还未回收，先强制回收该图片
        if (drawable != null && !drawable.getBitmap().isRecycled()) {
            drawable.getBitmap().recycle();
        }
        iv.setImageBitmap(BitmapFactory.decodeFile(fileIV[currentIv].getAbsolutePath()));
        currentIv++;
    }

    private void setAssets() {
        //如果数组越界
        if (currentImg >= images.length) {
            currentImg = 0;
        }
        //找到下一个图片文件
        while (!images[currentImg].endsWith(".jpg")) {
            //如果发生数据越界
            currentImg++;
            if (currentImg >= images.length) {
                currentImg = 0;
            }
        }
        InputStream stream = null;
        try {
            //打开指定资源对应的输入流
            stream = manager.open(images[currentImg]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        //如果图片还未回收，先强制回收该图片
        if (drawable != null && !drawable.getBitmap().isRecycled()) {
            drawable.getBitmap().recycle();
        }
        //改变iv显示的图片
        iv.setImageBitmap(BitmapFactory.decodeStream(stream));
        currentImg++;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//同意权限
                    Toast.makeText(this, "同意", Toast.LENGTH_SHORT).show();
                } else {//拒绝权限
                    Toast.makeText(this, "拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
