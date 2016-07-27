package com.example.framgia.alarmclock.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;

/**
 * Created by framgia on 21/07/2016.
 */
public class AdvancedActivity extends BaseActivity
    implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private final static CharSequence[] TYPE_CLOCKS = {"Analog", "Digital"};
    private SharedPreferences.Editor mEditor;
    private CheckBox mCheckBoxAutoLaunch, mCheckBoxShowBattery, mCheckBoxAutoSnooze;
    private TextView mTextViewTypeClock;
    private int mTypePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        initViews();
        initOnListener();
        showSharedPreferences();
    }

    private void initOnListener() {
        mCheckBoxAutoLaunch.setOnCheckedChangeListener(this);
        mCheckBoxShowBattery.setOnCheckedChangeListener(this);
        mCheckBoxAutoSnooze.setOnCheckedChangeListener(this);
        findViewById(R.id.layout_time_picker).setOnClickListener(this);
    }

    private void showSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARE_PREFERENCES,
            MODE_PRIVATE);
        mEditor = sharedPreferences.edit();
        mCheckBoxAutoLaunch.setChecked(sharedPreferences.getBoolean(Constants.AUTO_LAUNCH, true));
        mCheckBoxShowBattery.setChecked(sharedPreferences.getBoolean(Constants.SHOW_BATTERY, true));
        mCheckBoxAutoSnooze.setChecked(sharedPreferences.getBoolean(Constants.AUTO_SNOOZE, true));
        mTypePicker = sharedPreferences.getInt(Constants.TYPE_PICKER, Constants.TYPE_ANALOG);
        mTextViewTypeClock.setText(TYPE_CLOCKS[mTypePicker]);
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.title_advanced));
        mCheckBoxAutoLaunch = (CheckBox) findViewById(R.id.checkbox_auto_launch);
        mCheckBoxShowBattery = (CheckBox) findViewById(R.id.checkbox_show_battery_indicator);
        mCheckBoxAutoSnooze = (CheckBox) findViewById(R.id.checkbox_auto_snooze);
        mTextViewTypeClock = (TextView) findViewById(R.id.text_time_picker);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.checkbox_auto_launch:
                saveSharedPreferences(Constants.AUTO_LAUNCH, isChecked);
                break;
            case R.id.checkbox_show_battery_indicator:
                saveSharedPreferences(Constants.SHOW_BATTERY, isChecked);
                break;
            case R.id.checkbox_auto_snooze:
                saveSharedPreferences(Constants.AUTO_SNOOZE, isChecked);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_time_picker:
                Dialog dialog = createAlertDialog();
                dialog.show();
                break;
        }
    }

    private void saveSharedPreferences(String display, boolean checked) {
        mEditor.putBoolean(display, checked);
        mEditor.apply();
    }

    private Dialog createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(TYPE_CLOCKS, mTypePicker,
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    mTypePicker = item;
                }
            })
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    mTextViewTypeClock.setText(TYPE_CLOCKS[mTypePicker]);
                    mEditor.putInt(Constants.TYPE_PICKER, mTypePicker);
                    mEditor.apply();
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // nothing
                }
            });
        return builder.create();
    }
}
