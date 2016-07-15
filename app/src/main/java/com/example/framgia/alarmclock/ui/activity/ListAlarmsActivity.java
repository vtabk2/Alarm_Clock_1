package com.example.framgia.alarmclock.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.controller.AlarmRepository;
import com.example.framgia.alarmclock.data.listener.OnCheckedChangeItemListener;
import com.example.framgia.alarmclock.data.listener.OnClickItemListener;
import com.example.framgia.alarmclock.data.listener.OnLongClickItemListener;
import com.example.framgia.alarmclock.data.model.Alarm;
import com.example.framgia.alarmclock.ui.adapter.AlarmRecyclerViewAdapter;

import java.util.List;

import io.realm.Realm;

/**
 * Created by framgia on 13/07/2016.
 */
public class ListAlarmsActivity extends AppCompatActivity implements View.OnClickListener,
    OnClickItemListener, OnCheckedChangeItemListener, OnLongClickItemListener {
    private RecyclerView mRecyclerViewListAlarms;
    private Button mButtonAddAlarm, mButtonDeleteAlarm, mButtonCancel;
    private LinearLayoutManager mLinearLayoutManager;
    private AlarmRecyclerViewAdapter mAlarmRecyclerViewAdapter;
    private AlarmRepository mAlarmRepository;
    private Realm mRealm;
    private List<Alarm> mAlarmList;
    private boolean mIsCreated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alarms);
        loadData();
        initViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(R.string.alarms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mRecyclerViewListAlarms = (RecyclerView) findViewById(R.id.recycler_view_list_alarms);
        mButtonAddAlarm = (Button) findViewById(R.id.button_add_alarm);
        mButtonDeleteAlarm = (Button) findViewById(R.id.button_delete);
        mButtonCancel = (Button) findViewById(R.id.button_cancel);
        mButtonAddAlarm.setOnClickListener(this);
        mButtonDeleteAlarm.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        mAlarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(this, mAlarmList, this, this,
            this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewListAlarms.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewListAlarms.setAdapter(mAlarmRecyclerViewAdapter);
    }

    private void loadData() {
        mRealm = Realm.getDefaultInstance();
        mAlarmRepository = new AlarmRepository(mRealm);
        mAlarmList = mAlarmRepository.getAllAlarms();
    }

    private void clearCheckBox() {
        if (AlarmRecyclerViewAdapter.IS_SHOWED_CHECKBOX) {
            AlarmRecyclerViewAdapter.IS_SHOWED_CHECKBOX = false;
            mRealm.beginTransaction();
            for (Alarm alarm : mAlarmList)
                alarm.setChecked(false);
            mRealm.commitTransaction();
            mAlarmRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void deleteCheckedItem() {
        for (Alarm alarm : mAlarmList) {
            if (alarm.isChecked()) {
                mAlarmRepository.deleteAlarm(alarm);
            }
        }
        mAlarmRecyclerViewAdapter.notifyDataSetChanged();
    }

    private boolean dataIsEmpty() {
        if (mAlarmList.isEmpty()) {
            Toast.makeText(ListAlarmsActivity.this, R.string.data_empty, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private int getColorById(int id) {
        return ContextCompat.getColor(this, id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_alarm:
                Intent intent = new Intent(this, AlarmDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.button_delete:
                deleteCheckedItem();
                break;
            case R.id.button_cancel:
                clearCheckBox();
                mButtonAddAlarm.setVisibility(View.VISIBLE);
                mButtonDeleteAlarm.setVisibility(View.GONE);
                mButtonCancel.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClickItem(View view, int position) {
        switch (view.getId()) {
            case R.id.relative_layout_item_alarm:
                if (AlarmRecyclerViewAdapter.IS_SHOWED_CHECKBOX) {
                    mRealm.beginTransaction();
                    mAlarmList.get(position).setChecked(!mAlarmList.get(position).isChecked());
                    mRealm.commitTransaction();
                    mAlarmRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(this, AlarmDetailActivity.class);
                    intent.putExtra(Constants.OBJECT_ID, mAlarmList.get(position).getId());
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onLongClickItem(View view, AlarmRecyclerViewAdapter.AlarmViewHolder holder, int
        position) {
        switch (view.getId()) {
            case R.id.relative_layout_item_alarm:
                AlarmRecyclerViewAdapter.IS_SHOWED_CHECKBOX = true;
                mButtonAddAlarm.setVisibility(View.GONE);
                mButtonDeleteAlarm.setVisibility(View.VISIBLE);
                mButtonCancel.setVisibility(View.VISIBLE);
                mRealm.beginTransaction();
                mAlarmList.get(position).setChecked(true);
                mRealm.commitTransaction();
                mAlarmRecyclerViewAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onCheckedChangeItem(AlarmRecyclerViewAdapter.AlarmViewHolder holder, int position,
                                    CompoundButton button, boolean isChecked) {
        switch (button.getId()) {
            case R.id.checkbox_select_alarm:
                holder.mRelativeLayoutItemAlarm.setBackgroundColor(getColorById(isChecked ? R
                    .color.indigo : R.color.black));
                mRealm.beginTransaction();
                mAlarmList.get(position).setChecked(isChecked);
                mRealm.commitTransaction();
                break;
            case R.id.switch_enable_alarm:
                holder.mTextViewAlarmTime.setTextColor(isChecked ? Color.WHITE : Color.GRAY);
                holder.mTextViewAlarmDay.setTextColor(isChecked ? Color.WHITE : Color.GRAY);
                holder.mTextViewAlarmNote.setTextColor(isChecked ? Color.WHITE : Color.GRAY);
                mRealm.beginTransaction();
                mAlarmList.get(position).setEnabled(isChecked);
                mRealm.commitTransaction();
                mAlarmRecyclerViewAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_alarms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                clearCheckBox();
                finish();
                break;
            case R.id.delete_multiple_alarm:
                if (!dataIsEmpty()) {
                    mButtonAddAlarm.setVisibility(View.GONE);
                    mButtonDeleteAlarm.setVisibility(View.VISIBLE);
                    mButtonCancel.setVisibility(View.VISIBLE);
                    AlarmRecyclerViewAdapter.IS_SHOWED_CHECKBOX = true;
                    mAlarmRecyclerViewAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.delete_all_alarms:
                if (!dataIsEmpty()) {
                    AlarmRecyclerViewAdapter.IS_SHOWED_CHECKBOX = true;
                    mRealm.beginTransaction();
                    for (Alarm alarm : mAlarmList)
                        alarm.setChecked(true);
                    mRealm.commitTransaction();
                    mAlarmRecyclerViewAdapter.notifyDataSetChanged();
                    new AlertDialog.Builder(this)
                        .setTitle(R.string.title_delete_all_alarms)
                        .setMessage(R.string.message_delete_all_alarms)
                        .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mAlarmRepository.deleteAll();
                                    AlarmRecyclerViewAdapter.IS_SHOWED_CHECKBOX = false;
                                    mAlarmRecyclerViewAdapter.notifyDataSetChanged();
                                }
                            })
                        .setNegativeButton(android.R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    clearCheckBox();
                                }
                            })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
                }
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearCheckBox();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsCreated = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsCreated)
            mAlarmRecyclerViewAdapter.notifyDataSetChanged();
    }
}
