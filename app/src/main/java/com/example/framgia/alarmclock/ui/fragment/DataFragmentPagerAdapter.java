package com.example.framgia.alarmclock.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by framgia on 14/07/2016.
 */
public class DataFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 2;
    private String[] TABTITLES = {"FUNCTIONS", "CLOCKS"};

    public DataFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TABTITLES[position];
    }
}
