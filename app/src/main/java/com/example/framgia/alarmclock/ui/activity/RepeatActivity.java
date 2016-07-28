package com.example.framgia.alarmclock.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;

/**
 * Created by framgia on 15/07/2016.
 */
public class RepeatActivity extends BaseActivity implements
    CompoundButton.OnCheckedChangeListener {
    private CheckBox mCheckBoxMonday, mCheckBoxTuesday, mCheckBoxWednesday, mCheckBoxThursday,
        mCheckBoxFriday, mCheckBoxSaturday, mCheckBoxSunday, mCheckBoxEveryday;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        initViews();
        handleViewsOnClick();
        loadData();
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.title_repeat));
        mCheckBoxMonday = (CheckBox) findViewById(R.id.checkbox_every_monday);
        mCheckBoxTuesday = (CheckBox) findViewById(R.id.checkbox_every_tuesday);
        mCheckBoxWednesday = (CheckBox) findViewById(R.id.checkbox_every_wednesday);
        mCheckBoxThursday = (CheckBox) findViewById(R.id.checkbox_every_thursday);
        mCheckBoxFriday = (CheckBox) findViewById(R.id.checkbox_every_friday);
        mCheckBoxSaturday = (CheckBox) findViewById(R.id.checkbox_every_saturday);
        mCheckBoxSunday = (CheckBox) findViewById(R.id.checkbox_every_sunday);
        mCheckBoxEveryday = (CheckBox) findViewById(R.id.checkbox_everyday);
    }

    private void handleViewsOnClick() {
        mCheckBoxMonday.setOnCheckedChangeListener(this);
        mCheckBoxTuesday.setOnCheckedChangeListener(this);
        mCheckBoxWednesday.setOnCheckedChangeListener(this);
        mCheckBoxThursday.setOnCheckedChangeListener(this);
        mCheckBoxFriday.setOnCheckedChangeListener(this);
        mCheckBoxSaturday.setOnCheckedChangeListener(this);
        mCheckBoxSunday.setOnCheckedChangeListener(this);
        mCheckBoxEveryday.setOnCheckedChangeListener(this);
    }

    private void loadData() {
        Intent intent = getIntent();
        mCheckBoxMonday.setChecked(intent
            .getBooleanExtra(Constants.INTENT_REPEAT_MONDAY, Constants.DEFAULT_INTENT_BOOLEAN));
        mCheckBoxTuesday
            .setChecked(intent.getBooleanExtra(Constants.INTENT_REPEAT_TUESDAY, Constants
                .DEFAULT_INTENT_BOOLEAN));
        mCheckBoxWednesday
            .setChecked(intent.getBooleanExtra(Constants.INTENT_REPEAT_WEDNESDAY, Constants
                .DEFAULT_INTENT_BOOLEAN));
        mCheckBoxThursday
            .setChecked(intent.getBooleanExtra(Constants.INTENT_REPEAT_THURSDAY, Constants
                .DEFAULT_INTENT_BOOLEAN));
        mCheckBoxFriday.setChecked(intent.getBooleanExtra(Constants.INTENT_REPEAT_FRIDAY, Constants
            .DEFAULT_INTENT_BOOLEAN));
        mCheckBoxSaturday
            .setChecked(intent.getBooleanExtra(Constants.INTENT_REPEAT_SATURDAY, Constants
                .DEFAULT_INTENT_BOOLEAN));
        mCheckBoxSunday.setChecked(intent.getBooleanExtra(Constants.INTENT_REPEAT_SUNDAY, Constants
            .DEFAULT_INTENT_BOOLEAN));
        mCheckBoxEveryday
            .setChecked(intent.getBooleanExtra(Constants.INTENT_REPEAT_EVERYDAY, Constants
                .DEFAULT_INTENT_BOOLEAN));
    }

    private void setCheckboxChecked(boolean isChecked) {
        mCheckBoxMonday.setChecked(isChecked);
        mCheckBoxTuesday.setChecked(isChecked);
        mCheckBoxWednesday.setChecked(isChecked);
        mCheckBoxThursday.setChecked(isChecked);
        mCheckBoxFriday.setChecked(isChecked);
        mCheckBoxSaturday.setChecked(isChecked);
        mCheckBoxSunday.setChecked(isChecked);
    }

    private void allCheckboxAreChecked() {
        mCheckBoxEveryday.setChecked(
            mCheckBoxMonday.isChecked() && mCheckBoxTuesday.isChecked() && mCheckBoxWednesday
                .isChecked() && mCheckBoxThursday.isChecked() && mCheckBoxFriday.isChecked() &&
                mCheckBoxSaturday.isChecked() && mCheckBoxSunday.isChecked());
    }

    private void getRepeat() {
        Intent returnIntent = new Intent();
        if (mCheckBoxMonday.isChecked())
            returnIntent.putExtra(Constants.INTENT_REPEAT_MONDAY, mCheckBoxMonday.isChecked());
        if (mCheckBoxTuesday.isChecked())
            returnIntent.putExtra(Constants.INTENT_REPEAT_TUESDAY, mCheckBoxTuesday.isChecked());
        if (mCheckBoxWednesday.isChecked())
            returnIntent
                .putExtra(Constants.INTENT_REPEAT_WEDNESDAY, mCheckBoxWednesday.isChecked());
        if (mCheckBoxThursday.isChecked())
            returnIntent.putExtra(Constants.INTENT_REPEAT_THURSDAY, mCheckBoxThursday.isChecked());
        if (mCheckBoxFriday.isChecked())
            returnIntent.putExtra(Constants.INTENT_REPEAT_FRIDAY, mCheckBoxFriday.isChecked());
        if (mCheckBoxSaturday.isChecked())
            returnIntent.putExtra(Constants.INTENT_REPEAT_SATURDAY, mCheckBoxSaturday.isChecked());
        if (mCheckBoxSunday.isChecked())
            returnIntent.putExtra(Constants.INTENT_REPEAT_SUNDAY, mCheckBoxSunday.isChecked());
        if (mCheckBoxEveryday.isChecked())
            returnIntent.putExtra(Constants.INTENT_REPEAT_EVERYDAY, mCheckBoxEveryday.isChecked());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.checkbox_every_monday:
            case R.id.checkbox_every_tuesday:
            case R.id.checkbox_every_wednesday:
            case R.id.checkbox_every_thursday:
            case R.id.checkbox_every_friday:
            case R.id.checkbox_every_saturday:
            case R.id.checkbox_every_sunday:
                allCheckboxAreChecked();
                break;
            case R.id.checkbox_everyday:
                if (isChecked)
                    setCheckboxChecked(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        getRepeat();
    }
}
