package com.example.framgia.alarmclock.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.controller.SongRepository;
import com.example.framgia.alarmclock.data.listener.OnTimeSetPickerListener;
import com.example.framgia.alarmclock.ui.fragment.TimePickerFragment;
import com.example.framgia.alarmclock.utility.MusicPlayerUtils;
import com.example.framgia.alarmclock.utility.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by framgia on 15/07/2016.
 */
public class SleepTimerActivity extends BaseActivity implements View.OnClickListener,
    SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener,
    OnTimeSetPickerListener {
    private static final int HOUR = 0;
    private static final int MINUTE = 1;
    private static final int SECONDS_AN_HOUR = 3600;
    private static final int SECONDS_A_MINUTE = 60;
    private static final int MILLISECONDS_A_SECOND = 1000;
    private static String GMT_FORMAT = "GMT+00:00";
    private TextView mTextViewTimePicker;
    private Button mButtonStart, mButtonStop;
    private SeekBar mSeekBarVolume;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private CheckBox mCheckBoxNoise, mCheckBoxShuffle;
    private LinearLayout mLinearLayoutMusic;
    private CountDownTimer mCountDownTimer;
    private TextView mTextViewNumberSelectedSongs;
    private TimePickerFragment mTimePickerFragment;
    private boolean mIsPlaying;
    private long mTimeCountDown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_timer);
        initViews();
        initOnListener();
        initSharedPreferences();
        onChangeRotate(savedInstanceState);
    }

    private void onChangeRotate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mTextViewTimePicker.setText(savedInstanceState.getString(
                Constants.TIME_SLEEP_ON_ROTATE_CHANGE, getString(R.string.timer_sleep_default)));
            mIsPlaying = savedInstanceState.getBoolean(Constants.SLEEP_TIMER_IS_PLAYING);
            if (mIsPlaying)
                countDownTime(savedInstanceState.getLong(Constants.SLEEP_TIMER_TIME_COUNT_DOWN));
        }
        if (mTimePickerFragment == null)
            mTimePickerFragment =
                TimePickerFragment.newInstance(this, mTextViewTimePicker.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.TIME_SLEEP_ON_ROTATE_CHANGE,
            mTextViewTimePicker.getText().toString());
        outState.putBoolean(Constants.SLEEP_TIMER_IS_PLAYING, mIsPlaying);
        if (mIsPlaying)
            outState.putLong(Constants.SLEEP_TIMER_TIME_COUNT_DOWN, mTimeCountDown);
    }

    private void initOnListener() {
        mTextViewTimePicker.setOnClickListener(this);
        mButtonStart.setOnClickListener(this);
        mButtonStop.setOnClickListener(this);
        mSeekBarVolume.setOnSeekBarChangeListener(this);
        findViewById(R.id.layout_sound_music).setOnClickListener(this);
        mCheckBoxNoise.setOnCheckedChangeListener(this);
    }

    private void initSharedPreferences() {
        mSharedPreferences = getSharedPreferences(Constants.SHARE_PREFERENCES, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        int progress = mSharedPreferences.getInt(Constants.VOLUME_SLEEP_TIMER,
            Constants.VOLUME_SLEEP_DEFAULT);
        mSeekBarVolume.setProgress(progress);
        String time = mSharedPreferences.getString(Constants.TIME_PICKER_SLEEP_TIMER,
            getText(R.string.timer_sleep_default).toString());
        mTextViewTimePicker.setText(time);
        mCheckBoxNoise.setChecked(
            mSharedPreferences.getBoolean(Constants.SLEEP_TIMER_NOISE_IS_CHECKED, true));
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.title_sleep_timer));
        mTextViewTimePicker = (TextView) findViewById(R.id.text_timer_sleep);
        mButtonStart = (Button) findViewById(R.id.start_timer_sleep);
        mButtonStop = (Button) findViewById(R.id.stop_timer_sleep);
        mSeekBarVolume = (SeekBar) findViewById(R.id.seekBar_volume);
        mCheckBoxNoise = (CheckBox) findViewById(R.id.checkbox_noise);
        mCheckBoxShuffle = (CheckBox) findViewById(R.id.checkbox_shuffle);
        mLinearLayoutMusic = (LinearLayout) findViewById(R.id.layout_sound_music);
        mTextViewNumberSelectedSongs =
            (TextView) findViewById(R.id.text_view_number_selected_songs);
        mTextViewNumberSelectedSongs.setText(String.format
            (Locale.getDefault(), Constants.NUMBER_SELECTED_SONGS, SongRepository.getSize()));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.checkbox_noise:
                mEditor.putBoolean(Constants.SLEEP_TIMER_NOISE_IS_CHECKED, isChecked);
                mEditor.apply();
                setSleepTimerMusic(isChecked);
                break;
        }
    }

    private void setSleepTimerMusic(boolean isCheckedNoise) {
        mLinearLayoutMusic.setClickable(!isCheckedNoise);
        ((TextView) findViewById(R.id.text_view_music_title)).setTextColor(isCheckedNoise ? Color
            .GRAY : Color.WHITE);
        mCheckBoxShuffle.setClickable(!isCheckedNoise);
        mCheckBoxShuffle.setTextColor(isCheckedNoise ? Color.GRAY : Color.WHITE);
    }

    private long getTotalMilliseconds() {
        String[] arr = mTextViewTimePicker.getText().toString().split(":");
        return (Integer.parseInt(arr[HOUR]) * SECONDS_AN_HOUR + Integer.parseInt(arr[MINUTE]) *
            SECONDS_A_MINUTE) * MILLISECONDS_A_SECOND;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_timer_sleep:
                onStartSleepTimer();
                break;
            case R.id.stop_timer_sleep:
                mIsPlaying = false;
                onVisibility(false);
                onStopSleepTimer();
                break;
            case R.id.text_timer_sleep:
                mTimePickerFragment =
                    TimePickerFragment.newInstance(this, mTextViewTimePicker.getText().toString());
                mTimePickerFragment.show(getSupportFragmentManager(), Constants.TIME_PICKER);
                break;
            case R.id.layout_sound_music:
                startActivity(new Intent(this, ListSongsActivity.class));
                break;
        }
    }

    private void onStartSleepTimer() {
        if (mCheckBoxNoise.isChecked()) {
            MusicPlayerUtils.playMusic(this, String.valueOf(R.raw.noise));
            countDownTime(getTotalMilliseconds());
        } else if (SongRepository.getSize() != 0) {
            MusicPlayerUtils.playMusic(this, SongRepository.getAllSongs().get(0).getPath());
            countDownTime(getTotalMilliseconds());
        } else {
            ToastUtils.showToast(getApplicationContext(), R.string.prompt_there_is_no_song);
        }
    }

    private void countDownTime(final long milliseconds) {
        mIsPlaying = true;
        onVisibility(true);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants
            .TIME_FORMAT, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(GMT_FORMAT));
        mCountDownTimer = new CountDownTimer(milliseconds, MILLISECONDS_A_SECOND) {
            public void onTick(long millisUntilFinished) {
                mButtonStop.setText(String.format(Locale.getDefault(), getString(R.string
                        .sleep_timer_count_down), Constants.STOP,
                    simpleDateFormat.format(new Date(millisUntilFinished))));
                mTimeCountDown = millisUntilFinished;
            }

            public void onFinish() {
                mIsPlaying = false;
                onVisibility(false);
                onStopSleepTimer();
            }
        }.start();
    }

    private void onStopSleepTimer() {
        mButtonStop.setText(Constants.STOP);
        if (mCountDownTimer != null) mCountDownTimer.cancel();
        MusicPlayerUtils.stopMusic();
    }

    private void onVisibility(boolean isStart) {
        mButtonStart.setVisibility(isStart ? View.GONE : View.VISIBLE);
        mButtonStop.setVisibility(isStart ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        MusicPlayerUtils.setVolume(this, progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // nothing
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mEditor.putInt(Constants.VOLUME_SLEEP_TIMER, seekBar.getProgress());
        mEditor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTextViewNumberSelectedSongs.setText(String.format
            (Locale.getDefault(), Constants.NUMBER_SELECTED_SONGS, SongRepository.getSize()));
    }

    @Override
    public void onTimeSetPicker(int hourOfDay, int minute) {
        mTextViewTimePicker.setText(TimePickerFragment.getFormatTime(this, hourOfDay, minute));
    }
}
