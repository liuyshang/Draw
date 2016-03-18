package com.anl.wxb.draw.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anl.wxb.draw.R;

/**
 * author: admin
 * time: 2016/3/17 18:08
 * e-mail: lance.cao@anarry.com
 */
public class CircleFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(">>>","CircleFragment onCreateView");
        return inflater.inflate(R.layout.fragment_circle,null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(">>>","CircleFragment onDestroyView");
    }
}
