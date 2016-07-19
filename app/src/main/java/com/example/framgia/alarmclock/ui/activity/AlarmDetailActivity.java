package com.example.framgia.alarmclock.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.controller.AlarmRepository;
import com.example.framgia.alarmclock.data.model.Alarm;
import com.example.framgia.alarmclock.data.model.Repeat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by framgia on 15/07/2016.
 */
public class AlarmDetailActivity extends AppCompatActivity implements View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {
    private TextView mTextViewAlarmTime, mTextViewRepeatValue, mTextViewSoundValue,
        mTextViewSnoozeValue;
    private SeekBar mSeekBarVolume;
    private CheckBox mCheckBoxVibration, mCheckBoxFadeIn;
    private EditText mEditTextNoteValue;
    private Button mButtonSaveNewAlarm, mButtonSaveAlarm, mButtonDeleteAlarm;
    private Realm mRealm;
    private AlarmRepository mAlarmRepository;
    private Alarm mAlarm;
    private SimpleDateFormat mSimpleDateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);
        initViews();
        handleViewsOnClick();
        loadData();
        setDataToViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(R.string.set_alarm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTextViewAlarmTime = (TextView) findViewById(R.id.text_view_alarm_time);
        mTextViewRepeatValue = (TextView) findViewById(R.id.text_view_repeat_value);
        mTextViewSoundValue = (TextView) findViewById(R.id.text_view_sound_value);
        mTextViewSnoozeValue = (TextView) findViewById(R.id.text_view_snooze_value);
        mSeekBarVolume = (SeekBar) findViewById(R.id.seek_bar_volume);
        mCheckBoxVibration = (CheckBox) findViewById(R.id.checkbox_vibration);
        mCheckBoxFadeIn = (CheckBox) findViewById(R.id.checkbox_fade_in);
        mEditTextNoteValue = (EditText) findViewById(R.id.edit_text_note_value);
        mButtonSaveNewAlarm = (Button) findViewById(R.id.button_save_new_alarm);
        mButtonSaveAlarm = (Button) findViewById(R.id.button_save_alarm);
        mButtonDeleteAlarm = (Button) findViewById(R.id.button_delete_alarm);
    }

    private void handleViewsOnClick() {
        findViewById(R.id.linear_layout_repeat).setOnClickListener(this);
        findViewById(R.id.linear_layout_sound).setOnClickListener(this);
        findViewById(R.id.linear_layout_snooze).setOnClickListener(this);
        findViewById(R.id.relative_layout_vibration).setOnClickListener(this);
        findViewById(R.id.relative_layout_fade_in).setOnClickListener(this);
        mCheckBoxVibration.setOnCheckedChangeListener(this);
        mCheckBoxFadeIn.setOnCheckedChangeListener(this);
        mTextViewAlarmTime.setOnClickListener(this);
        mButtonSaveNewAlarm.setOnClickListener(this);
        mButtonSaveAlarm.setOnClickListener(this);
        mButtonDeleteAlarm.setOnClickListener(this);
        mEditTextNoteValue.clearFocus();
    }

    private void loadData() {
        mRealm = Realm.getDefaultInstance();
        mAlarmRepository = new AlarmRepository(mRealm);
        Intent intent = getIntent();
        int id = intent.getIntExtra(Constants.OBJECT_ID, Constants.DEFAULT_INTENT_VALUE);
        if (id == Constants.DEFAULT_INTENT_VALUE) {
            mButtonSaveNewAlarm.setVisibility(View.VISIBLE);
            mAlarm = new Alarm(mAlarmRepository.getNextId(),
                Calendar.getInstance().getTimeInMillis(), Constants.DEFAULT_ALARM_SOUND,
                Constants.DEFAULT_ALARM_VOLUME, true, false, Constants.DEFAULT_ALARM_SNOOZE_TIME,
                "", true, new Repeat(false, false, false, false, false, false, false, false));
        } else {
            mButtonSaveAlarm.setVisibility(View.VISIBLE);
            mButtonDeleteAlarm.setVisibility(View.VISIBLE);
            mAlarm = mAlarmRepository.getAlarmById(id);
        }
    }

    private void setDataToViews() {
        mSimpleDateFormat = new SimpleDateFormat(Constants.ALARM_TIME_FORMAT);
        mTextViewAlarmTime.setText(mSimpleDateFormat.format(new Date(mAlarm.getTime())));
        mTextViewRepeatValue.setText(mAlarm.getRepeat().getRepeatDay());
        mTextViewSoundValue.setText(mAlarm.getSound());
        mSeekBarVolume.setProgress(mAlarm.getVolume());
        mCheckBoxVibration.setChecked(mAlarm.isVibrated());
        mCheckBoxFadeIn.setChecked(mAlarm.isFadeIn());
        mTextViewSnoozeValue
            .setText(String.format("%d %s", mAlarm.getSnoozeTime(), Constants.MINUTES));
        mEditTextNoteValue.setText(mAlarm.getNote());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_alarm_time:
                // TODO: 18/07/2016 wait for TimePickerFragment in SleepTimer
                break;
            case R.id.linear_layout_repeat:
                openActivity(RepeatActivity.class);
                break;
            case R.id.linear_layout_sound:
                // TODO: 18/07/2016 open activity select sound
                break;
            case R.id.relative_layout_vibration:
                mCheckBoxVibration.setChecked(!mCheckBoxVibration.isChecked());
                break;
            case R.id.relative_layout_fade_in:
                mCheckBoxFadeIn.setChecked(!mCheckBoxFadeIn.isChecked());
                break;
            case R.id.linear_layout_snooze:
                // TODO: 18/07/2016 open activity select snooze time
                break;
            case R.id.button_save_new_alarm:
                mAlarmRepository.addAlarm(mAlarm);
                finish();
                break;
            case R.id.button_save_alarm:
                mRealm.beginTransaction();
                mAlarm.setVolume(mSeekBarVolume.getProgress());
                mAlarm.setVibrated(mCheckBoxVibration.isChecked());
                mAlarm.setFadeIn(mCheckBoxFadeIn.isChecked());
                mAlarm.setNote(mEditTextNoteValue.getText().toString());
                mRealm.commitTransaction();
                mAlarmRepository.updateAlarm(mAlarm);
                finish();
                break;
            case R.id.button_delete_alarm:
                mAlarmRepository.deleteAlarm(mAlarm);
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.checkbox_vibration:
                mCheckBoxVibration.setChecked(isChecked);
                break;
            case R.id.checkbox_fade_in:
                mCheckBoxFadeIn.setChecked(isChecked);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void openActivity(Class myClass) {
        Intent intent = new Intent(this, myClass);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
