package com.example.framgia.alarmclock.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.ui.fragment.DataFragmentPagerAdapter;

/**
 * Created by framgia on 14/07/2016.
 */
public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.title_settings));
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_setting);
        viewPager.setAdapter(new DataFragmentPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_setting);
        tabLayout.setupWithViewPager(viewPager);
    }
}
