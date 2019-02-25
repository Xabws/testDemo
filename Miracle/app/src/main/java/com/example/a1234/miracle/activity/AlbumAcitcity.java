package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.adapter.AlbumAdapter;
import com.example.a1234.miracle.adapter.AlbumPagerAdapter;
import com.example.a1234.miracle.customview.SpacesItemDecoration;
import com.example.a1234.miracle.data.MediaBean;
import com.example.a1234.miracle.data.MediaFolderBean;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.utils.AlbumController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.rv_pager)
    ViewPager2 rvPager;
    private AlbumAdapter adapter;
    private AlbumPagerAdapter albumPagerAdapter;
    //图片封面的list
    private ArrayList<MediaFolderBean> ablumlist;
    private HashMap<String, List<MediaBean>> mhashmap = new HashMap<>();
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AlbumAcitcity.this, 3);
        rvAlbum.setLayoutManager(gridLayoutManager);
        rvAlbum.addItemDecoration(new SpacesItemDecoration(AlbumAcitcity.this));
        adapter = new AlbumAdapter(AlbumAcitcity.this, ablumlist, new AlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String url) {
                showBigPics(position);
               /* scaleView.setVisibility(View.VISIBLE);
                Glide.with(AlbumAcitcity.this).load(url).into(scaleView);*/
            }
        });
        rvAlbum.setAdapter(adapter);
        albumController = new AlbumController(new AlbumController.MediaCallback() {
            @Override
            public void onAllMedias(HashMap<String, List<MediaBean>> allPhotos) {
                mhashmap.putAll(allPhotos);
                //key:文件夹，value：文件夹内容
                ablumlist = new ArrayList();
                for (Map.Entry<String, List<MediaBean>> entry : mhashmap.entrySet()) {
                    String FolerName = entry.getKey().substring(entry.getKey().lastIndexOf("/") + 1);
                    MediaFolderBean mediaFolderBean = new MediaFolderBean(FolerName, entry.getKey(), entry.getValue());
                    ablumlist.add(mediaFolderBean);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setList(ablumlist);
                        adapter.setType(AlbumAdapter.TYPE_FOLDER);
                    }
                });


            }
        });
        albumController.getAllMediaInfos();
    }

    private void showBigPics(int position) {
        rvPager.setVisibility(View.VISIBLE);
        albumPagerAdapter = new AlbumPagerAdapter(AlbumAcitcity.this, adapter.getCurrentList(), new AlbumPagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String url) {

            }
        });
        rvPager.setAdapter(albumPagerAdapter);
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
        if (adapter.getCurrentType() == AlbumAdapter.TYPE_FOLDER) {
            finish();
        } else {
            adapter.setType(AlbumAdapter.TYPE_FOLDER);
        }
    }


}
