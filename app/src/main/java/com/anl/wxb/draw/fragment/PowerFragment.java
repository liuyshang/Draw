package com.anl.wxb.draw.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.anl.wxb.draw.R;
import com.anl.wxb.draw.util.Injector;
import com.anl.wxb.draw.util.KeyboardUtil;
import com.anl.wxb.draw.util.ViewInject;
import com.anl.wxb.draw.view.PowerSurfaceView;

/**
 * author: admin
 * time: 2016/3/17 12:25
 * e-mail: lance.cao@anarry.com
 */
public class PowerFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.rl_view)
    private RelativeLayout rlView;
    @ViewInject(R.id.et)
    private EditText et;
    @ViewInject(R.id.bt)
    private Button bt;

    private View mView;
    private Context mContext;
    private PowerSurfaceView powerSurfaceView;
    private KeyboardUtil keyboard;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                keyboard.hideKeyBoard();
                addSquare();
                break;
            case R.id.et:
                et.setText("");
                break;
            default:
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(">>>","PowerFragment onCreateView");
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_power, null);
        }
        mContext = getActivity();
        Injector.initInjectedView(this, mView);
        powerSurfaceView = new PowerSurfaceView(mContext);
        keyboard = new KeyboardUtil(mContext,et);
        setListener();
        addSquare();
        return mView;
    }

    /**
     * 添加N次幂曲线
     */
    private void addSquare() {
        Log.i(">>>","addSquare");
        powerSurfaceView.setFalg(false);
        rlView.removeAllViews();

        if (!TextUtils.isEmpty(et.getText().toString())) {
            powerSurfaceView.setExponent(Integer.parseInt(et.getText().toString()));
        }
        try {
            powerSurfaceView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            rlView.addView(powerSurfaceView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        et.setOnClickListener(this);
        bt.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(">>>", "PowerFragment onDestroyView");
    }
}
