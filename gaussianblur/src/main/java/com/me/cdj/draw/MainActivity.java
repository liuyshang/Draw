package com.me.cdj.draw;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private Context mContext;

    private Button btn;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.bt_title);
        iv = (ImageView) findViewById(R.id.iv_a);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TestDialog(mContext,R.style.Transparent).show();
            }
        });
    }

}
