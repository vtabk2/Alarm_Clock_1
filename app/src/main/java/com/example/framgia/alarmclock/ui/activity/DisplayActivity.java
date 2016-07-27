package com.example.framgia.alarmclock.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;

/**
 * Created by framgia on 18/07/2016.
 */
public class DisplayActivity extends BaseActivity implements
    CompoundButton.OnCheckedChangeListener {
    private CheckBox mCheckBoxShowSeconds, mCheckBoxShowDay, mCheckBoxUse24HourFormat,
        mCheckBoxSlideFingers;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        initViews();
        initOnListener();
        showSharedPreferences();
    }

    private void initOnListener() {
        mCheckBoxShowSeconds.setOnCheckedChangeListener(this);
        mCheckBoxShowDay.setOnCheckedChangeListener(this);
        mCheckBoxUse24HourFormat.setOnCheckedChangeListener(this);
        mCheckBoxSlideFingers.setOnCheckedChangeListener(this);
    }

    private void showSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARE_PREFERENCES,
            MODE_PRIVATE);
        mEditor = sharedPreferences.edit();
        mCheckBoxShowSeconds.setChecked(
            sharedPreferences.getBoolean(Constants.SHOW_SECONDS, true));
        mCheckBoxShowDay.setChecked(sharedPreferences.getBoolean(Constants.SHOW_DAY, true));
        mCheckBoxUse24HourFormat.setChecked(
            sharedPreferences.getBoolean(Constants.USE_24_HOUR_FORMAT, true));
        mCheckBoxSlideFingers.setChecked(
            sharedPreferences.getBoolean(Constants.SLIDE_FINGERS, true));
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.title_display));
        mCheckBoxShowSeconds = (CheckBox) findViewById(R.id.cb_show_seconds);
        mCheckBoxShowDay = (CheckBox) findViewById(R.id.cb_show_day);
        mCheckBoxUse24HourFormat = (CheckBox) findViewById(R.id.cb_use_24_hour_format);
        mCheckBoxSlideFingers = (CheckBox) findViewById(R.id.cb_text_slide_fingers);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.cb_show_seconds:
                saveSharedPreferences(Constants.SHOW_SECONDS, compoundButton.isChecked());
                break;
            case R.id.cb_show_day:
                saveSharedPreferences(Constants.SHOW_DAY, compoundButton.isChecked());
                break;
            case R.id.cb_use_24_hour_format:
                saveSharedPreferences(Constants.USE_24_HOUR_FORMAT, compoundButton.isChecked());
                break;
            case R.id.cb_text_slide_fingers:
                saveSharedPreferences(Constants.SLIDE_FINGERS, compoundButton.isChecked());
                break;
        }
    }

    private void saveSharedPreferences(String display, boolean checked) {
        mEditor.putBoolean(display, checked);
        mEditor.apply();
    }
}
