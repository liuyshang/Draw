package com.anl.wxb.draw.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.anl.wxb.draw.Interface.OnClickBackListener;
import com.anl.wxb.draw.R;
import com.anl.wxb.draw.adapter.GraphAdapter;
import com.anl.wxb.draw.fragment.BezierFragment;
import com.anl.wxb.draw.util.Injector;
import com.anl.wxb.draw.util.ViewInject;
import com.anl.wxb.draw.view.ActionbarView;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity implements OnClickBackListener {

    @ViewInject(R.id.actionbar_view)
    private ActionbarView actionbar;
    @ViewInject(R.id.view_pager)
    private ViewPager viewPager;

    private Context mContext;
    private List<Fragment> mList = new ArrayList<>();
    private GraphAdapter mAdapter;
    private String type;

    @Override
    public void onClickBack() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(">>>","onCreate");
        setContentView(R.layout.activity_graph);
        mContext = GraphActivity.this;
        Injector.initInjectedView(this);
        getIntentData();
        initViewPager();
    }

    private void getIntentData() {
        try {
            Intent intent = getIntent();
            type = intent.getStringExtra("type");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewPager() {
        mList.add(new BezierFragment());
        mAdapter = new GraphAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(mAdapter);

        switch (type) {
            case "bezier":
                viewPager.setCurrentItem(0);
                actionbar.setTvTitle("贝塞尔曲线");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(">>>", "onDestroy");
    }
}
