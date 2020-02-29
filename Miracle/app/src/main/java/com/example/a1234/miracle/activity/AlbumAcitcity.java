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

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author: wsBai
 * date: 2019/2/18
 */
public class AlbumAcitcity extends BaseActivity {
    private ActivityAlbumBinding activityAlbumBinding;
    private AlbumAdapter adapter;
    private AlbumPagerAdapter albumPagerAdapter;
    private AlbumViewModel viewModel;
    private List<ImageBean> folderNameList;
    public static final String PICTURE_ALL_IMGS = "0";
//    public static final String PICTURE_ALL_FOLDERS = "1";

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
        });

        activityAlbumBinding.setAdapterFolder(adapter);
        viewModel = ViewModelProviders.of(AlbumAcitcity.this).get(AlbumViewModel.class);
        subscribeToModel(viewModel);
        albumPagerAdapter = new AlbumPagerAdapter(AlbumAcitcity.this, new AlbumPagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String url) {

            }
        });
        activityAlbumBinding.setViewpager2Adapter(albumPagerAdapter);
        activityAlbumBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //显示文字标题需要重写ImageBean中的toString方法
        activityAlbumBinding.niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                ImageBean imageBean = folderNameList.get(position);
                //切换至所有图片
               /* if (imageBean.getName().equals(PICTURE_ALL_FOLDERS)) {
                   // adapter.setCurrentType(AlbumAdapter.TYPE_FOLDER);
                    viewModel.getFolderList();
                } else*/
                if (imageBean.getUrl().equals(PICTURE_ALL_IMGS)) {
                    //adapter.setCurrentType(AlbumAdapter.TYPE_PHOTO);
                    viewModel.getAllPics();
                } else {
                    //  adapter.setCurrentType(AlbumAdapter.TYPE_PHOTO);
                    viewModel.getPicList(imageBean.getName());
                }

            }
        });
    }

    private void showBigPics(int position) {
      /*  if (adapter.getCurrentType() == AlbumAdapter.TYPE_FOLDER) {
            adapter.setCurrentType(AlbumAdapter.TYPE_PHOTO);
            viewModel.getPicList(position);
        } else {
            activityAlbumBinding.rvPager.setVisibility(View.VISIBLE);
            albumPagerAdapter.setPiclist(adapter.getDataList());
        }*/

    }


    @Override
    public void Event(MessageEvent messageEvent) {
        super.Event(messageEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        model.getLiveObservableData().observe(this, ImageBeanList -> {
            Log.d("folders", ImageBeanList.toString());
            adapter.setDataList(ImageBeanList);
            if (folderNameList == null) {
                folderNameList = new ArrayList<>();
                folderNameList.add(new ImageBean("全部图片", PICTURE_ALL_IMGS, false));
//        folderNameList.add(new ImageBean("全部相册", PICTURE_ALL_FOLDERS, true));
                folderNameList.addAll(ImageBeanList);
                activityAlbumBinding.niceSpinner.attachDataSource(folderNameList);
            }
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
