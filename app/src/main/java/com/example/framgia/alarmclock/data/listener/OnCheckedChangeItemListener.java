package com.example.framgia.alarmclock.data.listener;

import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;

/**
 * Created by framgia on 15/07/2016.
 */
public interface OnCheckedChangeItemListener {
    void onCheckedChangeItem(RecyclerView.ViewHolder holder, int position,
                             CompoundButton button, boolean isChecked);

}
