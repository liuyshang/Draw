package com.anl.wxb.draw.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anl.wxb.draw.R;
import com.anl.wxb.draw.util.Injector;
import com.anl.wxb.draw.util.ViewInject;
import com.anl.wxb.draw.view.CircleProgressBarView;

/**
 * author: admin
 * time: 2016/3/18 16:07
 * e-mail: lance.cao@anarry.com
 */
public class CircleProgressBarFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.progress_bar)
    private CircleProgressBarView progressBar;
    @ViewInject(R.id.bt)
    private Button bt;

    private View mView;
    private Context mContext;
    private int precent = 0;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt) {
            precent += 5;
            progressBar.setPrecent(precent);
            if (precent == 100) {
                precent = 0;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(">>>", "CircleProgressBarFragment onCreateView");
        mView = inflater.inflate(R.layout.fragment_progress, null);
        Injector.initInjectedView(this, mView);
        mContext = getActivity();
        setListener();
        return mView;
    }

    private void setListener() {
        bt.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        Log.i(">>>", "CircleProgressBarFragment onDestroyView");
        super.onDestroyView();
    }
}
