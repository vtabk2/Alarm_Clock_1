package com.example.framgia.alarmclock.data;

import java.text.DateFormatSymbols;

/**
 * Created by framgia on 12/07/2016.
 */
public class Constants {
    public static final String[] NAMES_OF_DAYS = DateFormatSymbols.getInstance().getShortWeekdays();
    public static final String NAMES_OF_DAYS_SEPERATOR = ", ";
    public static final String EVERYDAY = "Everyday";
    public static final String ID_FIELD = "mId";
    public final static String FONT_TIME = "fonts/digital_clock.ttf";
    public final static String FORMAT_TIME_SECOND = "ss";
    public final static int DOWN = 0;
    public final static int UP = 1;
    public final static float ALPHA_DELTA = 0.1f;
    public final static float ALPHA_MAX = 1.0f;
    public final static float ALPHA_MIN = 0.2f;
    public final static String SHARE_PREFERENCES = "alarmClock";
    public final static String TYPE_CLOCKS = "typeClocks";
    public final static int TYPE_CLOCKS_WHITE = 0;
    public final static int TYPE_CLOCKS_BLUE = 1;
    public final static int TYPE_CLOCKS_GREEN = 2;
    public final static int TYPE_CLOCKS_RED = 3;
    public final static int TYPE_CLOCKS_YELLOW = 4;
    public final static int TYPE_CLOCKS_ANALOG = 5;
}
