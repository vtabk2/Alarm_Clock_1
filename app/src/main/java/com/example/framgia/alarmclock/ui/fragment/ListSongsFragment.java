package com.example.framgia.alarmclock.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.Constants;
import com.example.framgia.alarmclock.data.controller.SongRepository;
import com.example.framgia.alarmclock.data.listener.OnClickCheckedChangeItemListener;
import com.example.framgia.alarmclock.data.listener.OnFragmentIsVisible;
import com.example.framgia.alarmclock.data.model.Music;
import com.example.framgia.alarmclock.data.model.Song;
import com.example.framgia.alarmclock.ui.adapter.SongRecyclerViewAdapter;
import com.example.framgia.alarmclock.utility.MusicUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by framgia on 25/07/2016.
 */
public class ListSongsFragment extends Fragment implements OnClickCheckedChangeItemListener,
    OnFragmentIsVisible {
    private SongRecyclerViewAdapter mSongRecyclerViewAdapter;
    private Realm mRealm;
    private List<Music> mMusicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, viewGroup, false);
        initView(view);
        requestPermission();
        setCheckedItems();
        return view;
    }

    private void initView(View view) {
        mRealm = Realm.getDefaultInstance();
        mMusicList = new ArrayList<>();
        RecyclerView recyclerViewListMusics =
            (RecyclerView) view.findViewById(R.id.recycler_view_list_musics);
        mSongRecyclerViewAdapter =
            new SongRecyclerViewAdapter(getActivity(), mMusicList, this);
        recyclerViewListMusics.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListMusics.setAdapter(mSongRecyclerViewAdapter);
    }

    private void getData() {
        MusicUtils.loadMusicsList(getContext(), mMusicList);
        mSongRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                Constants.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        else getData();
    }

    private void setCheckedItems() {
        mRealm.beginTransaction();
        for (Music music : mMusicList)
            music.setChecked(SongRepository.getSongByPath(music.getPath()) != null);
        mRealm.commitTransaction();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) getData();
                else Toast.makeText(getActivity(), R.string.prompt_need_to_grant_permission,
                    Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClickCheckedChangeItem(View view, RecyclerView.ViewHolder holder, int position) {
        switch (view.getId()) {
            case R.id.checkbox_select_song:
                Music music = mMusicList.get(position);
                boolean newCheckboxState = !music.isChecked();
                music.setChecked(newCheckboxState);
                mRealm.beginTransaction();
                if (newCheckboxState) {
                    Song song = mRealm.createObject(Song.class);
                    song.setName(music.getName());
                    song.setPath(music.getPath());
                    song.setChecked(true);
                } else SongRepository.getSongByPath(music.getPath()).deleteFromRealm();
                mRealm.commitTransaction();
                mSongRecyclerViewAdapter.notifyItemChanged(position);
                break;
        }
    }

    @Override
    public void fragmentIsVisible() {
        setCheckedItems();
        mSongRecyclerViewAdapter.notifyDataSetChanged();
    }
}
