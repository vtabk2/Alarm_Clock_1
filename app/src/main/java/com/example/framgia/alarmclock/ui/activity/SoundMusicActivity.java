package com.example.framgia.alarmclock.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.listener.OnSelectMusicListener;
import com.example.framgia.alarmclock.ui.adapter.SoundMusicFragmentPagerAdapter;
import com.example.framgia.alarmclock.ui.fragment.MusicFragment;
import com.example.framgia.alarmclock.ui.fragment.SoundFragment;
import com.example.framgia.alarmclock.utility.MusicPlayerUtils;

/**
 * Created by framgia on 20/07/2016.
 */
public class SoundMusicActivity extends BaseActivity implements OnSelectMusicListener,
    ViewPager.OnPageChangeListener {
    private static final int FRAGMENT_SOUND = 0;
    private static final int FRAGMENT_MUSIC = 1;
    private String mSound;
    private String mPath;
    private ViewPager mViewPager;
    private SoundMusicFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_music);
        loadData();
        initViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.sound_and_music));
        mViewPager = (ViewPager) findViewById(R.id.view_pager_sound);
        mPagerAdapter = new SoundMusicFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_sound);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(this);
    }

    private void loadData() {
        Intent intent = getIntent();
        mSound = intent.getStringExtra(Constants.INTENT_SOUND_MUSIC);
        mPath = intent.getStringExtra(Constants.INTENT_SOUND_MUSIC_PATH);
    }

    private void getSoundMusic() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.INTENT_SOUND_MUSIC, mSound);
        returnIntent.putExtra(Constants.INTENT_SOUND_MUSIC_PATH, mPath);
        setResult(Activity.RESULT_OK, returnIntent);
        MusicPlayerUtils.stopMusic();
        finish();
    }

    @Override
    public void onBackPressed() {
        getSoundMusic();
    }

    @Override
    public String getMusicName() {
        return mSound;
    }

    @Override
    public void onSelected(String musicName, String musicPath) {
        mSound = musicName;
        mPath = musicPath;
        MusicPlayerUtils.stopMusic();
        MusicPlayerUtils.playMusic(this, mPath);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // nothing
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case FRAGMENT_SOUND:
                SoundFragment soundFragment = (SoundFragment) mPagerAdapter
                    .instantiateItem(mViewPager, position);
                if (soundFragment != null)
                    soundFragment.fragmentIsVisible();
                break;
            case FRAGMENT_MUSIC:
                MusicFragment musicFragment = (MusicFragment) mPagerAdapter
                    .instantiateItem(mViewPager, position);
                if (musicFragment != null)
                    musicFragment.fragmentIsVisible();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // nothing
    }
}
