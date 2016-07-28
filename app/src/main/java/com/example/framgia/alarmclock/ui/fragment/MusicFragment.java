package com.example.framgia.alarmclock.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.example.framgia.alarmclock.data.listener.OnClickCheckedChangeItemListener;
import com.example.framgia.alarmclock.data.listener.OnSelectMusicListener;
import com.example.framgia.alarmclock.ui.adapter.MusicRecyclerViewAdapter;
import com.example.framgia.alarmclock.utility.MusicUtils;

/**
 * Created by framgia on 20/07/2016.
 */
public class MusicFragment extends Fragment implements OnClickCheckedChangeItemListener {
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private MusicRecyclerViewAdapter mMusicRecyclerViewAdapter;
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
        RecyclerView recyclerViewListMusics =
            (RecyclerView) view.findViewById(R.id.recycler_view_list_musics);
        mMusicRecyclerViewAdapter =
            new MusicRecyclerViewAdapter(getActivity(), MusicUtils.sMusicslist,
                mOnSelectMusicListener, this);
        recyclerViewListMusics.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListMusics.setAdapter(mMusicRecyclerViewAdapter);
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        else getData();
    }

    private void getData() {
        MusicUtils.loadMusicsList(getActivity());
        mMusicRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getData();
                else Toast.makeText(getActivity(), R.string.prompt_need_to_grant_permission, Toast
                    .LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClickCheckedChangeItem(View view, RecyclerView.ViewHolder holder, int position) {
        switch (view.getId()) {
            case R.id.radio_button_music:
                mOnSelectMusicListener.onSelected(MusicUtils.sMusicslist.get(position),
                    MusicUtils.sPathsList.get(position));
                mMusicRecyclerViewAdapter.notifyDataSetChanged();
                break;
        }
    }
}
