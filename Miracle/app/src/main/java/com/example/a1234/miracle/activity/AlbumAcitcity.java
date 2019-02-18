package com.example.a1234.miracle.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.adapter.AlbumAdapter;
import com.example.a1234.miracle.data.MediaBean;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.utils.AlbumController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * author: wsBai
 * date: 2019/2/18
 */
public class AlbumAcitcity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.cl_top)
    ConstraintLayout clTop;
    @BindView(R.id.rv_album)
    RecyclerView rvAlbum;
    private AlbumAdapter adapter;
    private ArrayList arrayList;
    private AlbumController albumController;

    @Override
    public int getContentViewId() {
        return R.layout.activity_album;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        albumController = new AlbumController(new AlbumController.MediaCallback() {
            @Override
            public void onAllMedias(HashMap<String, List<MediaBean>> allPhotos) {
                Log.d("sadadasadsa", allPhotos.toString());
            }
        });
        albumController.getAllPhotoInfo();
        albumController.getAllVideoInfos();
        adapter = new AlbumAdapter(this, arrayList, new AlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    @Override
    public void Event(MessageEvent messageEvent) {
        super.Event(messageEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
