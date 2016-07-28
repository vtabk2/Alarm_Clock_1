package com.example.framgia.alarmclock.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.framgia.alarmclock.ui.fragment.ListSongsFragment;
import com.example.framgia.alarmclock.ui.fragment.SelectedSongsFragment;

/**
 * Created by framgia on 25/07/2016.
 */
public class ListSongsFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 2;
    private final int TAB_SELECTED_SONGS = 0;
    private final int TAB_LIST_SONGS = 1;
    private String[] TAB_TITLES = {"SELECTED SONGS", "LIST SONGS"};

    public ListSongsFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_SELECTED_SONGS:
                return new SelectedSongsFragment();
            case TAB_LIST_SONGS:
                return new ListSongsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
