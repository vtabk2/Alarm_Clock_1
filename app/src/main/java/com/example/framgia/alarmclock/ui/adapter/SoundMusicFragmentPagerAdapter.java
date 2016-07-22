package com.example.framgia.alarmclock.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.framgia.alarmclock.data.listener.OnSelectMusicListener;
import com.example.framgia.alarmclock.ui.fragment.MusicFragment;
import com.example.framgia.alarmclock.ui.fragment.SoundFragment;

/**
 * Created by framgia on 20/07/2016.
 */
public class SoundMusicFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 2;
    private final int TAB_SOUND = 0;
    private final int TAB_MUSIC = 1;
    private String TAB_TITLES[] = new String[]{"SOUNDS", "MUSIC"};

    public SoundMusicFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_SOUND:
                return new SoundFragment();
            case TAB_MUSIC:
                return new MusicFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
