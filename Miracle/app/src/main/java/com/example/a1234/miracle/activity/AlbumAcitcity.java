package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.adapter.AlbumAdapter;
import com.example.a1234.miracle.adapter.AlbumPagerAdapter;
import com.example.a1234.miracle.adapter.IAdapterClickInterface;
import com.example.a1234.miracle.databinding.ActivityAlbumBinding;
import com.example.a1234.miracle.databinding.AlbumLayoutBinding;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.viewmodel.AlbumViewModel;
import com.example.baselib.retrofit.data.ImageBean;
import com.example.baselib.widget.SpacesItemDecoration;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.OnClick;

/**
 * author: wsBai
 * date: 2019/2/18
 */
public class AlbumAcitcity extends BaseActivity {
    private ActivityAlbumBinding activityAlbumBinding;
    private AlbumAdapter adapter;
    private AlbumPagerAdapter albumPagerAdapter;
    private AlbumViewModel viewModel;

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
        activityAlbumBinding = DataBindingUtil.setContentView(this, getContentViewId());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AlbumAcitcity.this, 3);
        activityAlbumBinding.rvAlbum.setLayoutManager(gridLayoutManager);
        activityAlbumBinding.rvAlbum.addItemDecoration(new SpacesItemDecoration(AlbumAcitcity.this));
        adapter = new AlbumAdapter(AlbumAcitcity.this, new IAdapterClickInterface<AlbumLayoutBinding, ImageBean>() {
            @Override
            public void onItemClick(AlbumLayoutBinding binding, ImageBean imageBean, int position) {
                showBigPics(position);
            }
        }, AlbumAdapter.TYPE_FOLDER);

        activityAlbumBinding.setAdapterFolder(adapter);
        viewModel = ViewModelProviders.of(AlbumAcitcity.this).get(AlbumViewModel.class);
        subscribeToModel(viewModel);
        albumPagerAdapter = new AlbumPagerAdapter(AlbumAcitcity.this, new AlbumPagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String url) {

            }
        });
        activityAlbumBinding.setViewpager2Adapter(albumPagerAdapter);
    }

    private void showBigPics(int position) {
        if (adapter.getCurrentType() == AlbumAdapter.TYPE_FOLDER) {
            adapter.setCurrentType(AlbumAdapter.TYPE_PHOTO);
            viewModel.getPicList(position);
        }else{
            activityAlbumBinding.rvPager.setVisibility(View.VISIBLE);
            albumPagerAdapter.setPiclist(adapter.getDataList());
        }

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
            // adapter.setType(AlbumAdapter.TYPE_FOLDER);
        }
    }

    /**
     * 订阅数据变化来刷新UI
     *
     * @param model
     */
    private void subscribeToModel(final AlbumViewModel model) {
        /**
         * 相册封面和图片列表
         */
        model.getLiveObservableData().observe(this, ImageBean -> {
            Log.d("all_folders", ImageBean.toString());
            adapter.setDataList(ImageBean);
            // adapter.setType(AlbumAdapter.TYPE_FOLDER);
        });

        /**
         * 点击查看大图
         */
        model.getPhoto_livedata().observe(this, new Observer<ImageBean>() {
            @Override
            public void onChanged(ImageBean imageBean) {
                activityAlbumBinding.rvPager.setVisibility(View.VISIBLE);

            }
        });
    }
}
