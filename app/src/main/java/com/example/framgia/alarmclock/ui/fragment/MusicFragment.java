package com.example.framgia.alarmclock.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.framgia.alarmclock.R;
import com.example.framgia.alarmclock.data.listener.OnCheckedChangeItemListener;
import com.example.framgia.alarmclock.data.listener.OnSelectMusicListener;
import com.example.framgia.alarmclock.ui.adapter.MusicRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by framgia on 20/07/2016.
 */
public class MusicFragment extends Fragment implements OnCheckedChangeItemListener {
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private MusicRecyclerViewAdapter mMusicRecyclerViewAdapter;
    private List<String> mMusicslist;
    private List<String> mPathsList;
    private OnSelectMusicListener mOnSelectMusicListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, viewGroup, false);
        initView(view);
        requestPermission();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnSelectMusicListener = (OnSelectMusicListener) context;
    }

    private void initView(View view) {
        mMusicslist = new ArrayList<>();
        mPathsList = new ArrayList<>();
        RecyclerView recyclerViewListMusics =
            (RecyclerView) view.findViewById(R.id.recycler_view_list_musics);
        mMusicRecyclerViewAdapter =
            new MusicRecyclerViewAdapter(getActivity(), mMusicslist, this, mOnSelectMusicListener);
        recyclerViewListMusics.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListMusics.setAdapter(mMusicRecyclerViewAdapter);
    }

    private void loadMusicsList() {
        Cursor cursor =
            getActivity().getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA},
                    null, null, "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                mMusicslist.add(cursor
                    .getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                mPathsList.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio
                    .Media.DATA)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        mMusicRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void requestPermission() {
        if (ContextCompat
            .checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            loadMusicsList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadMusicsList();
                } else {
                    Toast.makeText(getActivity(), R.string.prompt_need_to_grant_permission, Toast
                        .LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onCheckedChangeItem(RecyclerView.ViewHolder holder, int position,
                                    CompoundButton button, boolean isChecked) {
        switch (button.getId()) {
            case R.id.radio_button_music:
                mOnSelectMusicListener.onSelected(mMusicslist.get(position), mPathsList.get
                    (position));
                break;
        }
    }
}
