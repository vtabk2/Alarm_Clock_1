package com.example.framgia.alarmclock.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;

/**
 * Created by framgia on 19/07/2016.
 */
public class SnoozeActivity extends BaseActivity {
    public static final int SNOOZE_FIVE_MINUTES = 5;
    public static final int SNOOZE_TEN_MINUTES = 10;
    public static final int SNOOZE_FIFTEEN_MINUTES = 15;
    public static final int SNOOZE_TWENTY_MINUTES = 20;
    public static final int SNOOZE_TWENTY_FIVE_MINUTES = 25;
    public static final int SNOOZE_THIRTY_MINUTES = 30;
    public static final int SNOOZE_NEVER = 0;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButtonFiveMinutes, mRadioButtonTenMinutes,
        mRadioButtonFifteenMinutes, mRadioButtonTwentyMinutes, mRadioButtonTwentyFiveMinutes,
        mRadioButtonThirtyMinutes, mRadioButtonNever;
    private int mSnoozeTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        getSupportActionBar().setTitle(R.string.snooze_duration);
        initViews();
        loadData();
    }

    private void initViews() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group_snooze);
        mRadioButtonFiveMinutes = (RadioButton) findViewById(R.id.radio_button_five_minutes);
        mRadioButtonTenMinutes = (RadioButton) findViewById(R.id.radio_button_ten_minutes);
        mRadioButtonFifteenMinutes = (RadioButton) findViewById(R.id.radio_button_fifteen_minutes);
        mRadioButtonTwentyMinutes = (RadioButton) findViewById(R.id.radio_button_twenty_minutes);
        mRadioButtonTwentyFiveMinutes =
            (RadioButton) findViewById(R.id.radio_button_twenty_five_minutes);
        mRadioButtonThirtyMinutes = (RadioButton) findViewById(R.id.radio_button_thirty_minutes);
        mRadioButtonNever = (RadioButton) findViewById(R.id.radio_button_never);
    }

    private void loadData() {
        Intent intent = getIntent();
        mSnoozeTime = intent.getIntExtra(Constants.INTENT_SNOOZE_TIME, Constants
            .DEFAULT_INTENT_VALUE);
        switch (mSnoozeTime) {
            case SNOOZE_FIVE_MINUTES:
                mRadioButtonFiveMinutes.setChecked(true);
                break;
            case SNOOZE_TEN_MINUTES:
                mRadioButtonTenMinutes.setChecked(true);
                break;
            case SNOOZE_FIFTEEN_MINUTES:
                mRadioButtonFifteenMinutes.setChecked(true);
                break;
            case SNOOZE_TWENTY_MINUTES:
                mRadioButtonTwentyMinutes.setChecked(true);
                break;
            case SNOOZE_TWENTY_FIVE_MINUTES:
                mRadioButtonTwentyFiveMinutes.setChecked(true);
                break;
            case SNOOZE_THIRTY_MINUTES:
                mRadioButtonThirtyMinutes.setChecked(true);
                break;
            case SNOOZE_NEVER:
                mRadioButtonNever.setChecked(true);
                break;
        }
    }

    private void getSnoozeTime() {
        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_button_five_minutes:
                mSnoozeTime = SNOOZE_FIVE_MINUTES;
                break;
            case R.id.radio_button_ten_minutes:
                mSnoozeTime = SNOOZE_TEN_MINUTES;
                break;
            case R.id.radio_button_fifteen_minutes:
                mSnoozeTime = SNOOZE_FIFTEEN_MINUTES;
                break;
            case R.id.radio_button_twenty_minutes:
                mSnoozeTime = SNOOZE_TWENTY_MINUTES;
                break;
            case R.id.radio_button_twenty_five_minutes:
                mSnoozeTime = SNOOZE_TWENTY_FIVE_MINUTES;
                break;
            case R.id.radio_button_thirty_minutes:
                mSnoozeTime = SNOOZE_THIRTY_MINUTES;
                break;
            case R.id.radio_button_never:
                mSnoozeTime = SNOOZE_NEVER;
                break;
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.INTENT_SNOOZE_TIME, mSnoozeTime);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        getSnoozeTime();
    }
}
