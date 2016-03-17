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

    @ViewInject(R.id.bt_bezier)
    private Button btBezier;
    @ViewInject(R.id.bt_cos)
    private Button btCos;

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
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
