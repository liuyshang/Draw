package com.anl.wxb.draw.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.anl.wxb.draw.R;
import com.anl.wxb.draw.util.Injector;
import com.anl.wxb.draw.util.ViewInject;

/**
 * author: admin
 * time: 2016/3/14 11:54
 * e-mail: lance.cao@anarry.com
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * 贝塞尔曲线
     * */
    @ViewInject(R.id.bt_bezier)
    private Button btBezier;
    /**
     * COS曲线
     * */
    @ViewInject(R.id.bt_cos)
    private Button btCos;
    /**
     * 幂次函数曲线
     * */
    @ViewInject(R.id.bt_square)
    private Button btSquare;
    /**
     * 圆
     * */
    @ViewInject(R.id.bt_circle)
    private Button btCircle;
    /**
     * 波纹
     * */
    @ViewInject(R.id.bt_ripple)
    private Button btRipple;
    /**
     * 进度条
     * */
    @ViewInject(R.id.bt_progress)
    private Button btProgress;
    /**
     * 加载效果图
     * */
    @ViewInject(R.id.bt_loading)
    private Button btLoading;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Injector.initInjectedView(this);
        mContext = MainActivity.this;
        setListener();
    }

    private void setListener() {
        btBezier.setOnClickListener(this);
        btCos.setOnClickListener(this);
        btSquare.setOnClickListener(this);
        btCircle.setOnClickListener(this);
        btRipple.setOnClickListener(this);
        btProgress.setOnClickListener(this);
        btLoading.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_bezier:
                startActivity(new Intent(mContext,GraphActivity.class).putExtra("type","bezier"));
                break;
            case R.id.bt_cos:
                startActivity(new Intent(mContext,GraphActivity.class).putExtra("type","cos"));
                break;
            case R.id.bt_square:
                startActivity(new Intent(mContext,GraphActivity.class).putExtra("type","square"));
                break;
            case R.id.bt_circle:
                startActivity(new Intent(mContext,GraphActivity.class).putExtra("type","circle"));
                break;
            case R.id.bt_ripple:
                startActivity(new Intent(mContext,GraphActivity.class).putExtra("type","ripple"));
                break;
            case R.id.bt_progress:
                startActivity(new Intent(mContext,GraphActivity.class).putExtra("type","progress"));
                break;
            case R.id.bt_loading:
                startActivity(new Intent(mContext,GraphActivity.class).putExtra("type","loading"));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
