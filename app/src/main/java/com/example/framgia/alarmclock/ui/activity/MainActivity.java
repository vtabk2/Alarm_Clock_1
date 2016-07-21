package com.example.framgia.alarmclock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int DEFAULT_NULL = -1;
    private TextClock mTextClockHour, mTextClockSecond, mTextClockAmPm;
    private TextView mTextViewBattery, mTextViewHideHour, mTextViewHideSecond, mTextViewDay;
    private ImageView mImageViewWeather, mImageViewSettings, mImageViewHelp, mImageViewAlarm;
    private ImageView mImageViewTimer;
    private GestureDetector mGestureDetector;
    private float mAlpha;
    private boolean mIscreated;
    private boolean mSlideFingers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideStatusBar();
        initViews();
        showAdvanced();
        onSimpleOnGestureListener();
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
            showAdvanced();
            hideStatusBar();
        }
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

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void initViews() {
        Typeface typeFaceTime = Typeface.createFromAsset(getAssets(), Constants.FONT_TIME);
        Typeface typeFaceDay = Typeface.createFromAsset(getAssets(), Constants.FONT_DAY);
        mTextViewDay = (TextView) findViewById(R.id.text_day);
        // text clock
        mTextClockHour = (TextClock) findViewById(R.id.text_clock_hour);
        mTextClockSecond = (TextClock) findViewById(R.id.text_clock_second);
        mTextClockAmPm = (TextClock) findViewById(R.id.text_clock_am_pm);
        mTextClockHour.setTypeface(typeFaceTime);
        mTextClockSecond.setTypeface(typeFaceTime);
        mTextClockAmPm.setTypeface(typeFaceDay);
        // hide text
        mTextViewHideHour = (TextView) findViewById(R.id.text_hide_hour);
        mTextViewHideSecond = (TextView) findViewById(R.id.text_hide_second);
        mTextViewHideHour.setTypeface(typeFaceTime);
        mTextViewHideSecond.setTypeface(typeFaceTime);
        mTextViewDay.setTypeface(typeFaceDay);
        // text battery
        mTextViewBattery = (TextView) findViewById(R.id.text_battery);
        // image icon
        mImageViewWeather = (ImageView) findViewById(R.id.image_weather);
        mImageViewSettings = (ImageView) findViewById(R.id.image_settings);
        mImageViewHelp = (ImageView) findViewById(R.id.image_help);
        mImageViewAlarm = (ImageView) findViewById(R.id.image_alarm);
        mImageViewTimer = (ImageView) findViewById(R.id.image_timer);
        mImageViewWeather.setOnClickListener(this);
        mImageViewSettings.setOnClickListener(this);
        mImageViewHelp.setOnClickListener(this);
        mImageViewAlarm.setOnClickListener(this);
        mImageViewTimer.setOnClickListener(this);
        mAlpha = Constants.ALPHA_MAX;
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
        }
    }

    private void openActivity(Class myClass) {
        Intent intent = new Intent(this, myClass);
        startActivity(intent);
    }

    private void showAdvanced() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARE_PREFERENCES,
            Context.MODE_PRIVATE);
        int typeClock =
            sharedPreferences.getInt(Constants.TYPE_CLOCKS, Constants.TYPE_CLOCKS_WHITE);
        switch (typeClock) {
            case Constants.TYPE_CLOCKS_WHITE:
                showColorViews(Color.WHITE, R.color.colorWhite);
                break;
            case Constants.TYPE_CLOCKS_BLUE:
                showColorViews(Color.BLUE, R.color.colorBlue);
                break;
            case Constants.TYPE_CLOCKS_GREEN:
                showColorViews(Color.GREEN, R.color.colorGreen);
                break;
            case Constants.TYPE_CLOCKS_RED:
                showColorViews(Color.RED, R.color.colorRed);
                break;
            case Constants.TYPE_CLOCKS_YELLOW:
                showColorViews(Color.YELLOW, R.color.colorYellow);
                break;
            case Constants.TYPE_CLOCKS_ANALOG:
                // TODO: 14/07/2016
                break;
        }
        boolean showSeconds = sharedPreferences.getBoolean(Constants.SHOW_SECONDS, true);
        boolean showDay = sharedPreferences.getBoolean(Constants.SHOW_DAY, true);
        boolean use24HourFormat = sharedPreferences.getBoolean(Constants.USE_24_HOUR_FORMAT, true);
        boolean showBattery = sharedPreferences.getBoolean(Constants.SHOW_BATTERY, true);
        mSlideFingers = sharedPreferences.getBoolean(Constants.SLIDE_FINGERS, true);
        if (use24HourFormat) {
            mTextClockHour.setFormat12Hour(null);
            mTextClockHour.setFormat24Hour(Constants.FORMAT_TIME_24_HOUR);
        } else {
            mTextClockHour.setFormat12Hour(Constants.FORMAT_TIME_12_HOUR);
            mTextClockHour.setFormat24Hour(null);
        }
        mTextClockAmPm.setFormat12Hour(Constants.FORMAT_AM_PM);
        DateFormat df = new SimpleDateFormat(Constants.FORMAT_DAY_SHORT);
        String date = df.format(Calendar.getInstance().getTime()).toUpperCase();
        mTextViewDay.setText(date);
        mTextClockSecond.setFormat12Hour(null);
        mTextClockSecond.setFormat24Hour(Constants.FORMAT_TIME_SECOND);
        mTextViewDay.setVisibility(showDay ? View.VISIBLE : View.INVISIBLE);
        mTextClockSecond.setVisibility(showSeconds ? View.VISIBLE : View.INVISIBLE);
        mTextViewHideSecond.setVisibility(showSeconds ? View.VISIBLE : View.INVISIBLE);
        mTextClockAmPm.setVisibility(use24HourFormat ? View.INVISIBLE : View.VISIBLE);
        // battery
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

    private void showColorViews(int color, int colorHide) {
        mTextClockHour.setTextColor(color);
        mTextClockSecond.setTextColor(color);
        mTextClockAmPm.setTextColor(color);
        mTextViewDay.setTextColor(color);
        mTextViewHideHour.setTextColor(ContextCompat.getColor(this, colorHide));
        mTextViewHideSecond.setTextColor(ContextCompat.getColor(this, colorHide));
        mImageViewAlarm.setColorFilter(color);
        mImageViewTimer.setColorFilter(color);
        mImageViewWeather.setColorFilter(color);
        mImageViewSettings.setColorFilter(color);
        mImageViewHelp.setColorFilter(color);
        mTextViewBattery.setTextColor(color);
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
        mTextClockHour.setAlpha(mAlpha);
        mTextClockSecond.setAlpha(mAlpha);
        mImageViewWeather.setAlpha(mAlpha);
        mImageViewSettings.setAlpha(mAlpha);
        mImageViewHelp.setAlpha(mAlpha);
        mImageViewAlarm.setAlpha(mAlpha);
        mImageViewTimer.setAlpha(mAlpha);
        mTextViewBattery.setAlpha(mAlpha);
        mTextViewDay.setAlpha(mAlpha);
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
}
