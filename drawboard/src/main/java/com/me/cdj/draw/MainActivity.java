package com.me.cdj.draw;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private EmbossMaskFilter embossMaskFilter;
    private BlurMaskFilter blurMaskFilter;
    private DrawBoardView drawBoard;


    /**
     * 保存更改画笔颜色、宽度的值
     * */
    private int colorId;
    private float sizeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 获取手机屏幕的尺寸
         *
         * 必须在onCreate之后
         * */
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = displayMetrics.widthPixels;
//        int height = displayMetrics.heightPixels;

        /**
         * System services not available to Activities before onCreate()
         * */
//        WindowManager windowManager = (WindowManager) mainActivity.getSystemService(Context.WINDOW_SERVICE);
//        width = windowManager.getDefaultDisplay().getWidth();
//        height= windowManager.getDefaultDisplay().getHeight();


        drawBoard = (DrawBoardView) findViewById(R.id.draw_board);

        embossMaskFilter = new EmbossMaskFilter(new float[] {1.5f, 1.5f, 1.5f}, 0.6f, 6, 4.2f);
        blurMaskFilter =new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.red:
                drawBoard.paint.setColor(Color.RED);
                colorId = Color.RED;
                item.setCheckable(true);
                break;
            case R.id.orange:
                drawBoard.paint.setColor(getResources().getColor(R.color.orange));
                colorId = getResources().getColor(R.color.orange);
                item.setCheckable(true);
                break;
            case R.id.yellow:
                drawBoard.paint.setColor(getResources().getColor(R.color.yellow));
                colorId = getResources().getColor(R.color.yellow);
                item.setCheckable(true);
                break;
            case R.id.green:
                drawBoard.paint.setColor(Color.GREEN);
                colorId = Color.GREEN;
                item.setCheckable(true);
                break;
            case R.id.blue:
                drawBoard.paint.setColor(Color.BLUE);
                colorId = Color.BLUE;
                item.setCheckable(true);
                break;
            case R.id.indigo:
                drawBoard.paint.setColor(getResources().getColor(R.color.indigo));
                colorId = getResources().getColor(R.color.indigo);
                item.setCheckable(true);
                break;
            case R.id.purple:
                drawBoard.paint.setColor(getResources().getColor(R.color.purple));
                colorId = getResources().getColor(R.color.purple);
                item.setCheckable(true);
                break;
            case R.id.width_6:
                drawBoard.paint.setStrokeWidth(6);
                sizeId = 6;
                item.setCheckable(true);
                break;
            case R.id.width_8:
                drawBoard.paint.setStrokeWidth(8);
                sizeId = 8;
                item.setCheckable(true);
                break;
            case R.id.width_10:
                drawBoard.paint.setStrokeWidth(10);
                sizeId = 10;
                item.setCheckable(true);
                break;
            case R.id.width_12:
                drawBoard.paint.setStrokeWidth(12);
                sizeId = 12;
                item.setCheckable(true);
                break;
            case R.id.width_14:
                drawBoard.paint.setStrokeWidth(14);
                sizeId = 14;
                item.setCheckable(true);
                break;
            case R.id.clear:
                drawBoard.clear();
                drawBoard.paint.setColor(colorId);
                drawBoard.paint.setStrokeWidth(sizeId);
                break;
            case R.id.save:
                drawBoard.save();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
