package com.example.framgia.alarmclock.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.listener.OnSelectMusicListener;
import com.example.framgia.alarmclock.ui.adapter.SoundMusicFragmentPagerAdapter;

import java.io.IOException;

/**
 * Created by framgia on 20/07/2016.
 */
public class SoundMusicActivity extends AppCompatActivity implements OnSelectMusicListener {
    private String mSound;
    private String mPath;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_music);
        loadData();
        initViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.sound_and_music));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_sound);
        SoundMusicFragmentPagerAdapter pagerAdapter =
            new SoundMusicFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_sound);
        tabLayout.setupWithViewPager(viewPager);
        mMediaPlayer = new MediaPlayer();
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
        mMediaPlayer.stop();
        finish();
    }

    public String getSound() {
        return mSound;
    }

    private void playSong() {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mPath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(this, R.string.error_play_song, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                getSoundMusic();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        getSoundMusic();
    }

    @Override
    public void onSelected(String musicName, String musicPath) {
        mSound = musicName;
        mPath = musicPath;
        playSong();
    }

    @Override
    public String getMusicName(){
        return mSound;
    }
}
