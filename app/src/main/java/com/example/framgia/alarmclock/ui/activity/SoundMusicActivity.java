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
import com.example.framgia.alarmclock.utility.MusicPlayerUtils;

/**
 * Created by framgia on 20/07/2016.
 */
public class SoundMusicActivity extends BaseActivity implements OnSelectMusicListener {
    private String mSound;
    private String mPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_music);
        loadData();
        initViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.sound_and_music));
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_sound);
        SoundMusicFragmentPagerAdapter pagerAdapter =
            new SoundMusicFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_sound);
        tabLayout.setupWithViewPager(viewPager);
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
}
