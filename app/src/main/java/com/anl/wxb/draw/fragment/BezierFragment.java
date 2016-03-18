package com.anl.wxb.draw.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anl.wxb.draw.R;
import com.anl.wxb.draw.util.Injector;
import com.anl.wxb.draw.util.KeyboardUtil;
import com.anl.wxb.draw.util.ViewInject;
import com.anl.wxb.draw.view.BezierSurfaceView;

/**
 * author: admin
 * time: 2016/3/14 17:04
 * e-mail: lance.cao@anarry.com
 */
public class BezierFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.rl_view)
    private RelativeLayout rlView;
    @ViewInject(R.id.et)
    private EditText et;
    @ViewInject(R.id.bt)
    private Button bt;

    private Context mContext;
    private View view;
    private KeyboardUtil keyboard;
    private BezierSurfaceView surfaceView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(">>>", "BezierFragment onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_bezier, null);
        }
        mContext = getActivity();
        Injector.initInjectedView(this, view);
        surfaceView = new BezierSurfaceView(mContext);
        setListener();
        keyboard = new KeyboardUtil(mContext, et);
        addGraph();
        return view;
    }

    @Override
    public void onResume() {
        Log.i(">>>", "BezierFragment onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(">>>", "BezierFragment onPause");
        super.onPause();
    }

    private void setListener() {
        et.setOnClickListener(this);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                addGraph();
                break;
            default:
                break;
        }
    }

    /**
     * 添加贝塞斯曲线
     */
    public void addGraph() {
        //清空布局
        surfaceView.setFlag(false);
        rlView.removeAllViews();

        keyboard.hideKeyBoard();

        String etPoint = et.getText().toString();
        String[] point = etPoint.split(",");
        if (!TextUtils.isEmpty(etPoint) && point.length != 2) {
            Toast.makeText(mContext, "X Y用“,”隔开", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(etPoint) && point.length == 2) {
            surfaceView.setqStartX(Integer.parseInt(point[0]));
            surfaceView.setqStartY(Integer.parseInt(point[1]));
        }
        try {
            surfaceView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            rlView.addView(surfaceView);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(">>>","BezierFragment onDestroyView");
    }
}
