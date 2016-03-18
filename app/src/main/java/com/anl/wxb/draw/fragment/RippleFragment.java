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
 * time: 2016/3/18 11:40
 * e-mail: lance.cao@anarry.com
 */
public class RippleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(">>>", "RippleFragment onCreateView");
        return inflater.inflate(R.layout.fragment_ripple, null);
    }

    @Override
    public void onDestroyView() {
        Log.i(">>>", "RippleFragment onDestroyView");
        super.onDestroyView();
    }
}
