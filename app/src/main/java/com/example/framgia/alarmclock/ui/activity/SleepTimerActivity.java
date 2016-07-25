package com.example.framgia.alarmclock.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.ui.fragment.TimePickerFragment;

/**
 * Created by framgia on 15/07/2016.
 */
public class SleepTimerActivity extends AppCompatActivity
    implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,
    SeekBar.OnSeekBarChangeListener {
    private TextView mTextViewTimePicker;
    private Button mButtonStart, mButtonStop;
    private SeekBar mSeekBarVolume;
    private SharedPreferences mSharedPreferences;
    private CheckBox mCheckBoxNoise, mCheckBoxShuffle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_timer);
        initViews();
        initSharedPreferences();
    }

    private void initSharedPreferences() {
        mSharedPreferences = getSharedPreferences(Constants.SHARE_PREFERENCES, MODE_PRIVATE);
        int progress = mSharedPreferences.getInt(Constants.VOLUME_SLEEP_TIMER,
            Constants.VOLUME_SLEEP_DEFAULT);
        mSeekBarVolume.setProgress(progress);
        String time = mSharedPreferences.getString(Constants.TIME_PICKER_SLEEP_TIMER,
            getText(R.string.timer_sleep_default).toString());
        mTextViewTimePicker.setText(time);
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.title_sleep_timer));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTextViewTimePicker = (TextView) findViewById(R.id.text_timer_sleep);
        mButtonStart = (Button) findViewById(R.id.start_timer_sleep);
        mButtonStop = (Button) findViewById(R.id.stop_timer_sleep);
        mSeekBarVolume = (SeekBar) findViewById(R.id.seekBar_volume);
        mCheckBoxNoise = (CheckBox) findViewById(R.id.checkbox_noise);
        mCheckBoxShuffle = (CheckBox) findViewById(R.id.checkbox_shuffle);
        mTextViewTimePicker.setOnClickListener(this);
        mButtonStart.setOnClickListener(this);
        mButtonStop.setOnClickListener(this);
        mSeekBarVolume.setOnSeekBarChangeListener(this);
        findViewById(R.id.layout_sound_music).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        // TODO: 15/07/2016
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_timer_sleep:
                onVisibility(true);
                onStartSleepTimer();
                break;
            case R.id.stop_timer_sleep:
                onVisibility(false);
                break;
            case R.id.text_timer_sleep:
                onChangeTimeSleep();
                break;
            case R.id.layout_sound_music:
                startActivity(new Intent(this, ListSongsActivity.class));
                break;
        }
    }

    private void onStartSleepTimer() {
        // TODO: 20/07/2016
    }

    private void onChangeTimeSleep() {
        TimePickerFragment timePickerFragment = new TimePickerFragment()
            .setTextViewTimePicker(mTextViewTimePicker);
        timePickerFragment.show(getSupportFragmentManager(), Constants.TIME_PICKER);
    }

    private void onVisibility(boolean isStart) {
        mButtonStart.setVisibility(isStart ? View.GONE : View.VISIBLE);
        mButtonStop.setVisibility(isStart ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        // nothing
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // nothing
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(Constants.VOLUME_SLEEP_TIMER, seekBar.getProgress());
        editor.apply();
    }
}
