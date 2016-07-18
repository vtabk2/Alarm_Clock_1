package com.example.framgia.alarmclock.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.framgia.alarmclock.R;

/**
 * Created by framgia on 15/07/2016.
 */
public class RepeatActivity extends AppCompatActivity implements
    CompoundButton.OnCheckedChangeListener {
    private CheckBox mCheckBoxMonday, mCheckBoxTuesday, mCheckBoxWednesday, mCheckBoxThursday,
        mCheckBoxFriday, mCheckBoxSaturday, mCheckBoxSunday, mCheckBoxEveryday;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);
        initViews();
    }

    private void initViews() {
        getSupportActionBar().setTitle(getText(R.string.title_repeat));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mCheckBoxMonday = (CheckBox) findViewById(R.id.repeat_monday);
        mCheckBoxTuesday = (CheckBox) findViewById(R.id.repeat_tuesday);
        mCheckBoxWednesday = (CheckBox) findViewById(R.id.repeat_wednesday);
        mCheckBoxThursday = (CheckBox) findViewById(R.id.repeat_thursday);
        mCheckBoxFriday = (CheckBox) findViewById(R.id.repeat_friday);
        mCheckBoxSaturday = (CheckBox) findViewById(R.id.repeat_saturday);
        mCheckBoxSunday = (CheckBox) findViewById(R.id.repeat_sunday);
        mCheckBoxEveryday = (CheckBox) findViewById(R.id.repeat_everyday);
        mCheckBoxMonday.setOnCheckedChangeListener(this);
        mCheckBoxTuesday.setOnCheckedChangeListener(this);
        mCheckBoxWednesday.setOnCheckedChangeListener(this);
        mCheckBoxThursday.setOnCheckedChangeListener(this);
        mCheckBoxFriday.setOnCheckedChangeListener(this);
        mCheckBoxSaturday.setOnCheckedChangeListener(this);
        mCheckBoxSunday.setOnCheckedChangeListener(this);
        mCheckBoxEveryday.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setChecked(boolean isChecked) {
        mCheckBoxMonday.setChecked(isChecked);
        mCheckBoxTuesday.setChecked(isChecked);
        mCheckBoxWednesday.setChecked(isChecked);
        mCheckBoxThursday.setChecked(isChecked);
        mCheckBoxFriday.setChecked(isChecked);
        mCheckBoxSaturday.setChecked(isChecked);
        mCheckBoxSunday.setChecked(isChecked);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.repeat_everyday) {
            setChecked(mCheckBoxEveryday.isChecked());
        }
    }
}
