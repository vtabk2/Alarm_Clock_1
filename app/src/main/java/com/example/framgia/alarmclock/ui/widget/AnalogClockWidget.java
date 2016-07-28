package com.example.framgia.alarmclock.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.framgia.alarmclock.R;

/**
 * Created by framgia on 18/07/2016.
 */
public class AnalogClockWidget extends AppWidgetProvider {
    private static RemoteViews buildUpdate(Context context) {
        return new RemoteViews(context.getPackageName(), R.layout.widget_clock_analog);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        appWidgetManager.updateAppWidget(appWidgetIds, buildUpdate(context));
    }
}