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
 * time: 2016/3/21 10:35
 * e-mail: lance.cao@anarry.com
 */
public class LoadingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(">>>", "LoadingFragment onCreateView");
        return inflater.inflate(R.layout.fragment_loading, null);
    }

    @Override
    public void onDestroyView() {
        Log.i(">>>", "LoadingFragment onDestroyView");
        super.onDestroyView();
    }
}
