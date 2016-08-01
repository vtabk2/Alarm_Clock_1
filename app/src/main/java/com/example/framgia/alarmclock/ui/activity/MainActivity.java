package com.example.framgia.alarmclock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AnalogClock;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.controller.AlarmRepository;
import com.example.framgia.alarmclock.data.model.Alarm;
import com.example.framgia.alarmclock.utility.AlarmUtils;
import com.example.framgia.alarmclock.utility.MusicPlayerUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int DEFAULT_NULL = -1;
    private TextClock mTextClockHour, mTextClockSecond, mTextClockAmPm;
    private AnalogClock mAnalogClockThinLine;
    private TextView mTextViewBattery, mTextViewHideHour, mTextViewHideSecond, mTextViewDay;
    private ImageView mImageViewWeather, mImageViewSettings, mImageViewHelp, mImageViewAlarm;
    private ImageView mImageViewTimer, mImageViewStopAlarm, mImageViewSnooze;
    private GestureDetector mGestureDetector;
    private float mAlpha;
    private boolean mIscreated;
    private boolean mSlideFingers;
    private SharedPreferences mSharedPreferences;
    private Typeface mTypeFaceDay, mTypeFaceTime;
    private Vibrator mVibrator;
    private Alarm mAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideStatusBar();
        initViews();
        updateData();
        showAdvanced();
        onSimpleOnGestureListener();
        initAlarmData();
        setupAction();
    }

    private void initAlarmData() {
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Intent intentAlarmData = getIntent();
        if (intentAlarmData != null) {
            mAlarm = AlarmRepository.getAlarmById(
                intentAlarmData.getIntExtra(Constants.OBJECT_ID, Constants.DEFAULT_INTENT_VALUE));
        }
    }

    private void updateData() {
        mSharedPreferences = getSharedPreferences(Constants.SHARE_PREFERENCES,
            Context.MODE_PRIVATE);
        mSlideFingers = mSharedPreferences.getBoolean(Constants.SLIDE_FINGERS, true);
        mAlpha = Constants.ALPHA_MAX;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIscreated = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIscreated) {
            updateData();
            showAdvanced();
            hideStatusBar();
        }
    }

    private void initViews() {
        initTextClocks();
        initImageView();
        initTextView();
        initOnListener();
    }

    private void initTextView() {
        // text day
        mTextViewDay = (TextView) findViewById(R.id.text_day);
        mTextViewDay.setTypeface(mTypeFaceDay);
        // text battery
        mTextViewBattery = (TextView) findViewById(R.id.text_battery);
        // hide text
        mTextViewHideHour = (TextView) findViewById(R.id.text_hide_hour);
        mTextViewHideSecond = (TextView) findViewById(R.id.text_hide_second);
        mTextViewHideHour.setTypeface(mTypeFaceTime);
        mTextViewHideSecond.setTypeface(mTypeFaceTime);
    }

    private void initImageView() {
        // image icon
        mImageViewWeather = (ImageView) findViewById(R.id.image_weather);
        mImageViewSettings = (ImageView) findViewById(R.id.image_settings);
        mImageViewHelp = (ImageView) findViewById(R.id.image_help);
        mImageViewAlarm = (ImageView) findViewById(R.id.image_alarm);
        mImageViewTimer = (ImageView) findViewById(R.id.image_timer);
        mImageViewStopAlarm = (ImageView) findViewById(R.id.image_stop_alarm);
        mImageViewSnooze = (ImageView) findViewById(R.id.image_snooze);
    }

    private void initOnListener() {
        mImageViewHelp.setOnClickListener(this);
        mImageViewAlarm.setOnClickListener(this);
        mImageViewTimer.setOnClickListener(this);
        mImageViewSnooze.setOnClickListener(this);
        mImageViewWeather.setOnClickListener(this);
        mImageViewSettings.setOnClickListener(this);
        mImageViewStopAlarm.setOnClickListener(this);
    }

    private void initTextClocks() {
        mAnalogClockThinLine = (AnalogClock) findViewById(R.id.analog_clock_main);
        mTypeFaceTime = Typeface.createFromAsset(getAssets(), Constants.FONT_TIME);
        mTypeFaceDay = Typeface.createFromAsset(getAssets(), Constants.FONT_DAY);
        // text clock
        mTextClockHour = (TextClock) findViewById(R.id.text_clock_hour);
        mTextClockSecond = (TextClock) findViewById(R.id.text_clock_second);
        mTextClockAmPm = (TextClock) findViewById(R.id.text_clock_am_pm);
        mTextClockHour.setTypeface(mTypeFaceTime);
        mTextClockSecond.setTypeface(mTypeFaceTime);
        mTextClockAmPm.setTypeface(mTypeFaceDay);
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void showAdvanced() {
        showAdvancedTextClock();
        showAdvancedBattery();
        showAdvancedTypeClock();
        showAdvancedTextViewDay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAlarm();
    }

    private void stopAlarm() {
        if (mVibrator.hasVibrator()) {
            mVibrator.cancel();
        }
        MusicPlayerUtils.stopMusic();
    }

    private void onSimpleOnGestureListener() {
        GestureDetector.SimpleOnGestureListener simpleOnGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    if (mSlideFingers) {
                        float y1 = e1.getY();
                        float y2 = e2.getY();
                        if (y1 > y2) {
                            onFadeInChangeBrightness(Constants.UP);
                        } else if (y1 < y2) {
                            onFadeInChangeBrightness(Constants.DOWN);
                        }
                    }
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
            };
        mGestureDetector = new GestureDetector(this, simpleOnGestureListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_weather:
                // TODO: 12/07/2016
                break;
            case R.id.image_settings:
                openActivity(SettingActivity.class);
                break;
            case R.id.image_help:
                // TODO: 12/07/2016
                break;
            case R.id.image_alarm:
                openActivity(ListAlarmsActivity.class);
                break;
            case R.id.image_timer:
                openActivity(SleepTimerActivity.class);
                break;
            case R.id.image_stop_alarm:
                stopAlarm();
                mImageViewSnooze.setVisibility(View.INVISIBLE);
                mImageViewStopAlarm.setVisibility(View.INVISIBLE);
                break;
            case R.id.image_snooze:
                updateSnooze();
                break;
        }
    }

    private void openActivity(Class myClass) {
        Intent intent = new Intent(this, myClass);
        startActivity(intent);
        stopAlarm();
    }

    private void showAdvancedTextViewDay() {
        String date = new SimpleDateFormat(Constants.FORMAT_DAY_SHORT)
            .format(Calendar.getInstance().getTime()).toUpperCase();
        mTextViewDay.setText(date);
        boolean showDay = mSharedPreferences.getBoolean(Constants.SHOW_DAY, true);
        mTextViewDay.setVisibility(showDay ? View.VISIBLE : View.INVISIBLE);
    }

    private void showAdvancedTextClock() {
        boolean use24HourFormat = mSharedPreferences.getBoolean(Constants.USE_24_HOUR_FORMAT, true);
        if (use24HourFormat) {
            mTextClockHour.setFormat12Hour(null);
            mTextClockHour.setFormat24Hour(Constants.FORMAT_TIME_24_HOUR);
        } else {
            mTextClockHour.setFormat12Hour(Constants.FORMAT_TIME_12_HOUR);
            mTextClockHour.setFormat24Hour(null);
        }
        mTextClockAmPm.setFormat12Hour(Constants.FORMAT_AM_PM);
        mTextClockAmPm.setFormat24Hour(null);
        mTextClockSecond.setFormat12Hour(null);
        mTextClockSecond.setFormat24Hour(Constants.FORMAT_TIME_SECOND);
        mTextClockAmPm.setVisibility(use24HourFormat ? View.INVISIBLE : View.VISIBLE);
    }

    private void showAdvancedBattery() {
        // battery
        boolean showBattery = mSharedPreferences.getBoolean(Constants.SHOW_BATTERY, true);
        mTextViewBattery.setVisibility(showBattery ? View.VISIBLE : View.GONE);
        if (showBattery) {
            IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = registerReceiver(null, intentFilter);
            assert batteryStatus != null;
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, DEFAULT_NULL);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, DEFAULT_NULL);
            int batteryPct = level * Constants.PERCENTAGES / scale;
            mTextViewBattery.setText(String.format(getString(R.string.battery), batteryPct));
        }
    }

    private void showAdvancedTypeClock() {
        int typeClock =
            mSharedPreferences.getInt(Constants.TYPE_CLOCKS, Constants.TYPE_CLOCKS_WHITE);
        switch (typeClock) {
            case Constants.TYPE_CLOCKS_WHITE:
                showColorViews(Color.WHITE, R.color.colorWhite);
                showAnalog(false);
                break;
            case Constants.TYPE_CLOCKS_BLUE:
                showColorViews(Color.BLUE, R.color.colorBlue);
                showAnalog(false);
                break;
            case Constants.TYPE_CLOCKS_GREEN:
                showColorViews(Color.GREEN, R.color.colorGreen);
                showAnalog(false);
                break;
            case Constants.TYPE_CLOCKS_RED:
                showColorViews(Color.RED, R.color.colorRed);
                showAnalog(false);
                break;
            case Constants.TYPE_CLOCKS_YELLOW:
                showColorViews(Color.YELLOW, R.color.colorYellow);
                showAnalog(false);
                break;
            case Constants.TYPE_CLOCKS_ANALOG:
                showColorViews(Color.WHITE, R.color.colorWhite);
                showAnalog(true);
                mTextClockAmPm.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void showAnalog(boolean showAnalog) {
        boolean showSeconds = mSharedPreferences.getBoolean(Constants.SHOW_SECONDS, true);
        mTextClockHour.setVisibility(showAnalog ? View.INVISIBLE : View.VISIBLE);
        mTextClockSecond.setVisibility(showSeconds ? View.VISIBLE : View.INVISIBLE);
        mAnalogClockThinLine.setVisibility(showAnalog ? View.VISIBLE : View.INVISIBLE);
        mTextViewHideHour.setVisibility(showAnalog ? View.INVISIBLE : View.VISIBLE);
        mTextViewHideSecond
            .setVisibility(showSeconds && !showAnalog ? View.VISIBLE : View.INVISIBLE);
    }

    private void showColorViews(int color, int colorHide) {
        // text clock
        mTextClockHour.setTextColor(color);
        mTextClockAmPm.setTextColor(color);
        mTextClockSecond.setTextColor(color);
        // text view
        mTextViewDay.setTextColor(color);
        mTextViewBattery.setTextColor(color);
        mTextViewHideHour.setTextColor(ContextCompat.getColor(this, colorHide));
        mTextViewHideSecond.setTextColor(ContextCompat.getColor(this, colorHide));
        // image view
        mImageViewHelp.setColorFilter(color);
        mImageViewAlarm.setColorFilter(color);
        mImageViewTimer.setColorFilter(color);
        mImageViewWeather.setColorFilter(color);
        mImageViewSettings.setColorFilter(color);
    }

    private void onFadeInChangeBrightness(int type) {
        switch (type) {
            case Constants.DOWN:
                onDownBrightness();
                break;
            case Constants.UP:
                onUpBrightness();
                break;
        }
    }

    private void onDownBrightness() {
        if (mAlpha > Constants.ALPHA_MIN) {
            mAlpha -= Constants.ALPHA_DELTA;
            setAlpha();
        }
    }

    private void setAlpha() {
        // text clock
        mTextClockHour.setAlpha(mAlpha);
        mTextClockAmPm.setAlpha(mAlpha);
        mTextClockSecond.setAlpha(mAlpha);
        // image view
        mImageViewHelp.setAlpha(mAlpha);
        mImageViewAlarm.setAlpha(mAlpha);
        mImageViewTimer.setAlpha(mAlpha);
        mImageViewWeather.setAlpha(mAlpha);
        mImageViewSettings.setAlpha(mAlpha);
        // text view
        mTextViewDay.setAlpha(mAlpha);
        mTextViewBattery.setAlpha(mAlpha);
        // analog clock
        mAnalogClockThinLine.setAlpha(mAlpha);
    }

    private void onUpBrightness() {
        if (mAlpha < Constants.ALPHA_MAX) {
            mAlpha += Constants.ALPHA_DELTA;
            setAlpha();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void playRingAndVibrate() {
        if (mAlarm.isValid() && mVibrator.hasVibrator()) {
            mVibrator.vibrate(new long[]{Constants.TIME_VIBRATOR, Constants.TIME_VIBRATOR},
                Constants.VIBRATOR_REPEAT);
        }
        MusicPlayerUtils.playMusic(this, mAlarm.getSong().getPath());
    }

    public void setupAction() {
        String action = getIntent().getAction();
        if (action == null) return;
        switch (action) {
            case Constants.ACTION_FULLSCREEN_ACTIVITY:
                showAlarmSnooze();
                playRingAndVibrate();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void showAlarmSnooze() {
        mImageViewSnooze.setVisibility(View.VISIBLE);
        mImageViewStopAlarm.setVisibility(View.VISIBLE);
    }

    private void updateSnooze() {
        mImageViewSnooze.setVisibility(View.INVISIBLE);
        AlarmUtils.setAlarmSnooze(this, mAlarm);
        stopAlarm();
        finish();
    }
}
