package com.example.framgia.alarmclock.utility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by framgia on 02/08/2016.
 */
public class ToastUtils {
    public static void showToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
