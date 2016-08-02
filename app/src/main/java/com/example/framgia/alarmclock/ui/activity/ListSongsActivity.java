package com.example.framgia.alarmclock.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.ui.adapter.ListSongsFragmentPagerAdapter;
import com.example.framgia.alarmclock.ui.fragment.ListSongsFragment;
import com.example.framgia.alarmclock.ui.fragment.SelectedSongsFragment;

/**
 * Created by framgia on 25/07/2016.
 */
public class ListSongsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final int FRAGMENT_SELECTED_SONG = 0;
    private static final int FRAGMENT_LIST_SONGS = 1;
    private ViewPager mViewPager;
    private ListSongsFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_music);
        initViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.list_songs));
        mViewPager = (ViewPager) findViewById(R.id.view_pager_sound);
        mPagerAdapter = new ListSongsFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_sound);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // nothing
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case FRAGMENT_SELECTED_SONG:
                SelectedSongsFragment selectedSongsFragment = (SelectedSongsFragment) mPagerAdapter
                    .instantiateItem(mViewPager, position);
                if (selectedSongsFragment != null)
                    selectedSongsFragment.fragmentIsVisible();
                break;
            case FRAGMENT_LIST_SONGS:
                ListSongsFragment listSongsFragment =
                    (ListSongsFragment) mPagerAdapter.instantiateItem
                        (mViewPager, position);
                if (listSongsFragment != null)
                    listSongsFragment.fragmentIsVisible();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // nothing
    }
}
