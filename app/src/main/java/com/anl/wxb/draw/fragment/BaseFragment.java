package com.anl.wxb.draw.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.anl.wxb.draw.R;
import com.anl.wxb.draw.util.KeyboardUtil;

/**
 * author: admin
 * time: 2016/3/14 12:05
 * e-mail: lance.cao@anarry.com
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout rlView;
    private EditText et;
    private Button bt;

    private Context mContext;
    private View view;
    private KeyboardUtil keyboard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(">>>","onCreateView");
        view = inflater.inflate(getLayout(), null);
        mContext = getActivity();
        keyboard = new KeyboardUtil(mContext);
        initView(view);
        initGraph();
        setListener();
        return view;
    }

    private void setListener() {
        et.setOnClickListener(this);
        bt.setOnClickListener(this);
    }

    private void initView(View view) {
        rlView = (RelativeLayout) view.findViewById(R.id.rl_view);
        et = (EditText) view.findViewById(R.id.et);
        bt = (Button) view.findViewById(R.id.bt);
    }

    @Override
    public void onResume() {
        Log.i(">>>","onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(">>>","onPause");
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        Log.i(">>>","onDestroyView");
        super.onDestroyView();
    }

    public abstract void initGraph();

    public abstract int getLayout();
}
