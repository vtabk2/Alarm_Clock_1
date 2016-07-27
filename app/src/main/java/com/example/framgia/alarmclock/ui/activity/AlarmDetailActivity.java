package com.example.framgia.alarmclock.ui.activity;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.controller.AlarmRepository;
import com.example.framgia.alarmclock.data.model.Alarm;
import com.example.framgia.alarmclock.data.model.Repeat;
import com.example.framgia.alarmclock.data.model.Song;
import com.example.framgia.alarmclock.ui.fragment.TimePickerFragment;
import com.example.framgia.alarmclock.utility.AlarmUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by framgia on 15/07/2016.
 */
public class AlarmDetailActivity extends AppCompatActivity implements View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {
    public static final int SNOOZE_TIME_CODE = 0;
    public static final int REPEAT_CODE = 1;
    public static final int SOUND_MUSIC_CODE = 2;
    private TextView mTextViewAlarmTime, mTextViewRepeatValue, mTextViewSoundValue,
        mTextViewSnoozeValue;
    private SeekBar mSeekBarVolume;
    private CheckBox mCheckBoxVibration, mCheckBoxFadeIn;
    private EditText mEditTextNoteValue;
    private Button mButtonSaveNewAlarm, mButtonSaveAlarm, mButtonDeleteAlarm;
    private Realm mRealm;
    private Alarm mAlarm;
    private int mSnoozeTime;
    private Repeat mRepeat;
    private Song mSong;

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
    }

    private void loadData() {
        mRealm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        int id = intent.getIntExtra(Constants.OBJECT_ID, Constants.DEFAULT_INTENT_VALUE);
        if (id == Constants.DEFAULT_INTENT_VALUE) {
            mButtonSaveNewAlarm.setVisibility(View.VISIBLE);
            createNewAlarm();
        } else {
            mButtonSaveAlarm.setVisibility(View.VISIBLE);
            mButtonDeleteAlarm.setVisibility(View.VISIBLE);
            mAlarm = AlarmRepository.getAlarmById(id);
        }
        mSnoozeTime = mAlarm.getSnoozeTime();
        mRealm.beginTransaction();
        mRepeat = mRealm.createObject(Repeat.class);
        mRepeat.copyFrom(mAlarm.getRepeat());
        mSong = mRealm.createObject(Song.class);
        mSong.copyFrom(mAlarm.getSong());
        mRealm.commitTransaction();
    }

    private void setDataToViews() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.ALARM_TIME_FORMAT);
        mTextViewAlarmTime.setText(simpleDateFormat.format(new Date(mAlarm.getTime())));
        mTextViewRepeatValue.setText(mAlarm.getRepeat().getRepeatDay());
        mTextViewSoundValue.setText(mSong.getName());
        mSeekBarVolume.setProgress(mAlarm.getVolume());
        mCheckBoxVibration.setChecked(mAlarm.isVibrated());
        mCheckBoxFadeIn.setChecked(mAlarm.isFadeIn());
        mTextViewSnoozeValue
            .setText(String.format("%d %s", mSnoozeTime, Constants.MINUTES));
        mEditTextNoteValue.setText(mAlarm.getNote());
    }

    private void createNewAlarm() {
        mRealm.beginTransaction();
        mAlarm = mRealm.createObject(Alarm.class);
        mAlarm.setId(AlarmRepository.getNextId());
        mAlarm.setTime(Calendar.getInstance().getTimeInMillis());
        mAlarm.setVolume(Constants.DEFAULT_ALARM_VOLUME);
        mAlarm.setVibrated(true);
        mAlarm.setSnoozeTime(Constants.DEFAULT_ALARM_SNOOZE_TIME);
        mAlarm.setEnabled(true);
        Song song = mRealm.createObject(Song.class);
        song.setName(Constants.DEFAULT_ALARM_SOUND);
        song.setAlarmMusic(true);
        mAlarm.setSong(song);
        Repeat repeat = mRealm.createObject(Repeat.class);
        repeat.initRepeatDay();
        mAlarm.setRepeat(repeat);
        mRealm.commitTransaction();
    }

    private long convertStringToTimeLong(String timeString) {
        DateFormat formatter = new SimpleDateFormat(Constants.ALARM_TIME_FORMAT);
        Date date;
        try {
            date = formatter.parse(timeString);
        } catch (ParseException e) {
            date = new Date();
            Toast.makeText(this, R.string.error_parse_time_string, Toast.LENGTH_SHORT).show();
        }
        return date.getTime();
    }

    private void openRepeatActivity() {
        Intent intentRepeat = new Intent(this, RepeatActivity.class);
        // con not pass realm object to other activity
        intentRepeat.putExtra(Constants.INTENT_REPEAT_MONDAY, mRepeat.isRepeatMonday());
        intentRepeat.putExtra(Constants.INTENT_REPEAT_TUESDAY, mRepeat.isRepeatTuesday());
        intentRepeat
            .putExtra(Constants.INTENT_REPEAT_WEDNESDAY, mRepeat.isRepeatWednesday());
        intentRepeat.putExtra(Constants.INTENT_REPEAT_THURSDAY, mRepeat.isRepeatThursday());
        intentRepeat.putExtra(Constants.INTENT_REPEAT_FRIDAY, mRepeat.isRepeatFriday());
        intentRepeat.putExtra(Constants.INTENT_REPEAT_SATURDAY, mRepeat.isRepeatSaturday());
        intentRepeat.putExtra(Constants.INTENT_REPEAT_SUNDAY, mRepeat.isRepeatSunday());
        intentRepeat.putExtra(Constants.INTENT_REPEAT_EVERYDAY, mRepeat.isRepeatEveryday());
        startActivityForResult(intentRepeat, REPEAT_CODE);
    }

    private void saveAlarm() {
        mRealm.beginTransaction();
        mAlarm.setTime(convertStringToTimeLong(mTextViewAlarmTime.getText().toString()));
        mAlarm.setSong(mSong);
        mAlarm.setRepeat(mRepeat);
        mAlarm.setVolume(mSeekBarVolume.getProgress());
        mAlarm.setVibrated(mCheckBoxVibration.isChecked());
        mAlarm.setFadeIn(mCheckBoxFadeIn.isChecked());
        mAlarm.setSnoozeTime(mSnoozeTime);
        mAlarm.setNote(mEditTextNoteValue.getText().toString());
        mRealm.commitTransaction();
        AlarmRepository.updateAlarm(mAlarm);
        AlarmUtils.setupAlarm(this, mAlarm);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_alarm_time:
                TimePickerFragment timePickerFragment = new TimePickerFragment()
                    .setTextViewTimePicker(mTextViewAlarmTime);
                timePickerFragment.show(getSupportFragmentManager(), Constants.TIME_PICKER);
                break;
            case R.id.linear_layout_repeat:
                openRepeatActivity();
                break;
            case R.id.linear_layout_sound:
                Intent intentSoundMusic = new Intent(this, SoundMusicActivity.class);
                intentSoundMusic.putExtra(Constants.INTENT_SOUND_MUSIC, mSong.getName());
                intentSoundMusic.putExtra(Constants.INTENT_SOUND_MUSIC_PATH, mSong.getPath());
                startActivityForResult(intentSoundMusic, SOUND_MUSIC_CODE);
                break;
            case R.id.relative_layout_vibration:
                mCheckBoxVibration.setChecked(!mCheckBoxVibration.isChecked());
                break;
            case R.id.relative_layout_fade_in:
                mCheckBoxFadeIn.setChecked(!mCheckBoxFadeIn.isChecked());
                break;
            case R.id.linear_layout_snooze:
                Intent intentSnooze = new Intent(this, SnoozeActivity.class);
                intentSnooze.putExtra(Constants.INTENT_SNOOZE_TIME, mSnoozeTime);
                startActivityForResult(intentSnooze, SNOOZE_TIME_CODE);
                break;
            case R.id.button_save_new_alarm:
            case R.id.button_save_alarm:
                saveAlarm();
                break;
            case R.id.button_delete_alarm:
                AlarmRepository.deleteAlarm(mAlarm);
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
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SNOOZE_TIME_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    mSnoozeTime = data.getIntExtra(Constants.INTENT_SNOOZE_TIME, Constants
                        .DEFAULT_INTENT_VALUE);
                    mTextViewSnoozeValue
                        .setText(String.format("%d %s", mSnoozeTime, Constants.MINUTES));
                }
                break;
            case REPEAT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    mRealm.beginTransaction();
                    mRepeat.setRepeatMonday(data.getBooleanExtra(Constants.INTENT_REPEAT_MONDAY,
                        Constants.DEFAULT_INTENT_BOOLEAN));
                    mRepeat.setRepeatTuesday(data.getBooleanExtra(Constants.INTENT_REPEAT_TUESDAY,
                        Constants.DEFAULT_INTENT_BOOLEAN));
                    mRepeat
                        .setRepeatWednesday(data.getBooleanExtra(Constants.INTENT_REPEAT_WEDNESDAY,
                            Constants.DEFAULT_INTENT_BOOLEAN));
                    mRepeat.setRepeatThursday(data.getBooleanExtra(Constants.INTENT_REPEAT_THURSDAY,
                        Constants.DEFAULT_INTENT_BOOLEAN));
                    mRepeat.setRepeatFriday(data.getBooleanExtra(Constants.INTENT_REPEAT_FRIDAY,
                        Constants.DEFAULT_INTENT_BOOLEAN));
                    mRepeat.setRepeatSaturday(data.getBooleanExtra(Constants.INTENT_REPEAT_SATURDAY,
                        Constants.DEFAULT_INTENT_BOOLEAN));
                    mRepeat.setRepeatSunday(data.getBooleanExtra(Constants.INTENT_REPEAT_SUNDAY,
                        Constants.DEFAULT_INTENT_BOOLEAN));
                    mRepeat.setRepeatEveryday(data.getBooleanExtra(Constants.INTENT_REPEAT_EVERYDAY,
                        Constants.DEFAULT_INTENT_BOOLEAN));
                    mRealm.commitTransaction();
                    mTextViewRepeatValue.setText(mRepeat.getRepeatDay());
                }
                break;
            case SOUND_MUSIC_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    mRealm.beginTransaction();
                    String songName = data.getStringExtra(Constants.INTENT_SOUND_MUSIC);
                    mSong.setName(songName);
                    mSong.setPath(data.getStringExtra(Constants.INTENT_SOUND_MUSIC_PATH));
                    mSong.setAlarmMusic(true);
                    mSong.setChecked(true);
                    mRealm.commitTransaction();
                    mTextViewSoundValue.setText(songName);
                }
                break;
        }
    }
}
