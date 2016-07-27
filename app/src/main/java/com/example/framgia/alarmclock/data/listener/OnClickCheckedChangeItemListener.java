package com.example.framgia.alarmclock.data.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by framgia on 27/07/2016.
 */
public interface OnClickCheckedChangeItemListener {
    void onClickCheckedChangeItem(View view, RecyclerView.ViewHolder holder, int position);
}
