package com.anl.wxb.draw.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anl.wxb.draw.R;

/**
 * author: admin
 * time: 2016/3/15 18:13
 * e-mail: lance.cao@anarry.com
 */
public class CosFragment extends Fragment {

    private View mView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(">>>","CosFragment onCreateView");
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_cos, null);
        }
        mContext = getActivity();
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(">>>", "CosFragment onDestroyView");
    }
}
