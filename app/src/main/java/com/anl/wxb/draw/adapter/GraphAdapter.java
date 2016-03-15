package com.anl.wxb.draw.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author: admin
 * time: 2016/3/14 16:00
 * e-mail: lance.cao@anarry.com
 */
public class GraphAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList = new ArrayList<>();

    /*public GraphAdapter(FragmentManager fm) {
        super(fm);
    }
*/

    public GraphAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
