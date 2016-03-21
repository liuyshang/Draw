package com.anl.wxb.draw.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.anl.wxb.draw.Interface.OnClickBackListener;
import com.anl.wxb.draw.R;
import com.anl.wxb.draw.fragment.BezierFragment;
import com.anl.wxb.draw.fragment.CircleFragment;
import com.anl.wxb.draw.fragment.LoadingFragment;
import com.anl.wxb.draw.fragment.ProgressBarFragment;
import com.anl.wxb.draw.fragment.CosFragment;
import com.anl.wxb.draw.fragment.PowerFragment;
import com.anl.wxb.draw.fragment.RippleFragment;
import com.anl.wxb.draw.util.Injector;
import com.anl.wxb.draw.util.ViewInject;
import com.anl.wxb.draw.view.ActionbarView;

public class GraphActivity extends AppCompatActivity implements OnClickBackListener {

    @ViewInject(R.id.actionbar_view)
    private ActionbarView actionbar;

    private Context mContext;
    private String type;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private BezierFragment bezierFragment;
    private CosFragment cosFragment;
    private PowerFragment powerFragment;
    private CircleFragment circleFragment;
    private RippleFragment rippleFragment;
    private ProgressBarFragment progressBarFragment;
    private LoadingFragment loadingFragment;

    @Override
    public void onClickBack() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(">>>", "onCreate");
        setContentView(R.layout.activity_graph);
        mContext = GraphActivity.this;
        Injector.initInjectedView(this);
        getIntentData();
        init();
        setListener();
    }

    private void setListener() {
        actionbar.setOnClickBackListener(this);
    }

    private void getIntentData() {
        try {
            Intent intent = getIntent();
            type = intent.getStringExtra("type");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        switch (type) {
            case "bezier":
                if (bezierFragment == null) {
                    bezierFragment = new BezierFragment();
                }
                transaction.add(R.id.fragment, bezierFragment, "bezier");
                actionbar.setTvTitle("贝塞尔曲线");
                break;
            case "cos":
                if (cosFragment == null) {
                    cosFragment = new CosFragment();
                }
                transaction.add(R.id.fragment, cosFragment, "cos");
                actionbar.setTvTitle("COS曲线");
                break;
            case "square":
                if (powerFragment == null) {
                    powerFragment = new PowerFragment();
                }
                transaction.add(R.id.fragment, powerFragment, "square");
                actionbar.setTvTitle("N次幂曲线");
                break;
            case "circle":
                if (circleFragment == null) {
                    circleFragment = new CircleFragment();
                }
                transaction.add(R.id.fragment, circleFragment, "square");
                actionbar.setTvTitle("圆形");
                break;
            case "ripple":
                if (rippleFragment == null) {
                    rippleFragment = new RippleFragment();
                }
                transaction.add(R.id.fragment, rippleFragment, "ripple");
                actionbar.setTvTitle("波纹");
                break;
            case "progress":
                if (progressBarFragment == null) {
                    progressBarFragment = new ProgressBarFragment();
                }
                transaction.add(R.id.fragment, progressBarFragment, "progress");
                actionbar.setTvTitle("进度条");
                break;
            case "loading":
                if (loadingFragment == null) {
                    loadingFragment = new LoadingFragment();
                }
                transaction.add(R.id.fragment, loadingFragment, "loading");
                actionbar.setTvTitle("加载效果图");
                break;
            default:
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(">>>", "onDestroy");
    }
}
