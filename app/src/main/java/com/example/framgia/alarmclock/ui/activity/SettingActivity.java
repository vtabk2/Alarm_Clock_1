package com.example.framgia.alarmclock.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.ui.fragment.DataFragmentPagerAdapter;

/**
 * Created by framgia on 14/07/2016.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.title_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_setting);
        viewPager.setAdapter(new DataFragmentPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_setting);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_alarm:
                // TODO: 14/07/2016
                break;
            case R.id.layout_timer:
                // TODO: 14/07/2016
                break;
            case R.id.layout_weather:
                // TODO: 14/07/2016
                break;
            case R.id.layout_display:
                // TODO: 14/07/2016
                break;
            case R.id.layout_advanced:
                // TODO: 14/07/2016
                break;
        }
    }
}
