package com.me.cdj.draw;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.SumPathEffect;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PathText(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 使用PathEffect绘制不同效果的路径
     * */
    class PathView extends View{

        /**
         *
         * */
        float phase = 0;

        /**
         * 初始化PathEffect
         * */
        PathEffect[] effects = new PathEffect[7];

        /**
         * 定义颜色
         * */
        int[] colors;

        /**
         * 定义画笔
         * */
        Paint paint;

        /**
         * 定义路径
         * */
        Path path;

        public PathView(Context context) {
            super(context);
            init();
        }

        public PathView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public PathView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        public void init(){
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);

            path = new Path();
            path.moveTo(0,0);
            /**
             * 生成15个点，随机生成他们的Y坐标，并将他们练成一条Path
             * */
            for (int i=1; i<=15; i++){
                float count = (float) (Math.random() * 60);
                path.lineTo(i * 20, count);
                Log.i(TAG,"Y: " + count);
            }
            /**
             * 初始化7个颜色
             * */
            colors = new int[]{Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.RED};
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(Color.WHITE);
            /**
             * 初始化7种路径效果
             * */
            effects[0] = null;

            /**
             * CornerPathEffect: 将Path的各个连接线段之间的夹角用一种更平滑的方式连接，类似于圆弧与切线的效果
             * */
            effects[1] = new CornerPathEffect(15);

            /**
             * DiscretePathEffect: 打散Path的线段，使得在原来路径的基础上发生打散效果，第一个参数segmentLength指定最大的段长，
             * 第二个参数指定偏移量
             * */
            effects[2] = new DiscretePathEffect(3.0f,5.0f);

            /**
             *DashPathEffect: 将path的线段虚拟化 第一个参数intervals为虚线的ON和OFF数组，该数组的length必须大于等于2，phase位绘制的偏移量
             * */
            effects[3] = new DashPathEffect(new float[]{20,10,5,10},phase);

            Path p = new Path();
            p.addRect(0,0,8,8, Path.Direction.CCW);
            /**
             *PathDashPathEffect是使用Path图形来填充当前的路径 第一个参数shape值填充图形，
             * 第二个参数advance指每个图形间的间距，第三个参数phase为绘制的偏移量
             * 第四个参数style为该类自由的枚举值，有Style.ROTATE、Style.MORPH和Style.TRANSLATE
             * */
            effects[4] = new PathDashPathEffect(p, 12, phase, PathDashPathEffect.Style.ROTATE);

            /**
             *ComposePathEffect 组合效果  表现时，会首先将第二个参数表现出来，在第二个参数的基础上增加第一个参数的效果
             * */
            effects[5] = new ComposePathEffect(effects[2], effects[4]);

            /**
             *SumPathEffect 叠加效果 ，两个参数的效果各自独立进行表现，将两个效果简单的重叠在一起显示出来
             * */
            effects[6] = new SumPathEffect(effects[4], effects[2]);

            /**
             * 将画布移动到（8,8）出开始绘制
             * */
            canvas.translate(8,8);
            for (int i=0; i<7; i++){
                paint.setPathEffect(effects[i]);
                paint.setColor(colors[i]);
                canvas.drawPath(path, paint);
                canvas.translate(0,60);
            }

            /**
             * 改变phase值，形成动画效果
             * */
            phase += 1;
            invalidate();
        }
    }

    /**
     *沿着Path绘制文本
     * */
    class PathText extends View{

        private String Str = "疯狂Android讲义";
        private Path[] paths = new Path[3];
        private Paint paint;

        public PathText(Context context) {
            super(context);
            init();
        }

        public PathText(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public PathText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public PathText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        public void init(){
            /**
             * 路径1
             * */
            paths[0] = new Path();
            paths[0].moveTo(0,0);
            for (int i=1; i<16; i++){
                paths[0].lineTo(i * 30, (float) (Math.random() * 60));
            }
            paths[0].close();


            RectF rectF = new RectF(0,0,200,200);
            /**
             * 路径2
             * */
            paths[1] = new Path();
            paths[1].addRect(rectF, Path.Direction.CCW);

            /**
             * 路径3
             * */
            paths[2] = new Path();
            paths[2].addArc(rectF,90,270);

            /**
             * 初始化画笔
             * */
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.CYAN);
            paint.setStrokeWidth(3);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawColor(Color.WHITE);
            canvas.translate(40, 40);
            //设置从右边开始绘制
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(20);
            //绘制路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[0], paint);
            //沿着路径绘制一段文字
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(Str, paths[0], -8, 20, paint);

            canvas.translate(0, 100);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[1], paint);
            paint.setColor(Color.YELLOW);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(Str, paths[1], -20, 20, paint);

            canvas.translate(0, 240);
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[2], paint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawTextOnPath(Str,paths[2],10,-20,paint);
        }
    }
}
