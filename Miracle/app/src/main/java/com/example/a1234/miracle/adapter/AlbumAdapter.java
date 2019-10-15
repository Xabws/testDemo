package com.example.a1234.miracle.adapter;

import android.content.Context;
import android.util.Size;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.MyApplication;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.AlbumLayoutBinding;
import com.example.baselib.retrofit.data.ImageBean;
import com.example.baselib.utils.DeviceUtil;

import java.util.ArrayList;

import androidx.databinding.BindingAdapter;

/**
 * author: wsBai
 * date: 2019/2/18
 */
public class AlbumAdapter extends CommonAdapter<AlbumLayoutBinding, ImageBean> {
    public static final int TYPE_FOLDER = 0;//文件夹目录
    public static final int TYPE_PHOTO = 1;//图片目录
    private int currentType = TYPE_FOLDER;

    public AlbumAdapter(Context context, IAdapterClickInterface<AlbumLayoutBinding, ImageBean> adapterClickInterface, int currentType) {
        super(context, adapterClickInterface);
        this.currentType = currentType;
    }


    public AlbumAdapter(Context context, ArrayList<ImageBean> dataList, IAdapterClickInterface<AlbumLayoutBinding, ImageBean> adapterClickInterface, int currentType) {
        super(context, dataList, adapterClickInterface);
        this.currentType = currentType;
    }

    @Override
    public int getLayoutId() {
        return R.layout.album_layout;
    }

    @Override
    public void bindView(CommonViewHolder viewHolder, ImageBean imageBean, int position) {
        viewHolder.bindView.setImageBean(imageBean);
        viewHolder.bindView.rlMain.setOnClickListener(v -> adapterClickInterface.onItemClick(viewHolder.bindView, imageBean, position));
    }

    /**
     * 绑定xml中的imageview(因为需要用到Glide单独绑定)
     * 该方法必须为静态
     *
     * @param imageView
     */
    @BindingAdapter({"picture"})
    public static void loadimage(ImageView imageView, String url) {
        Size s = DeviceUtil.getScreeWH(MyApplication.getContext());
        Glide.with(imageView.getContext()).load(url).override(s.getWidth() / 3, s.getWidth() / 3).placeholder(R.mipmap.ic_launcher).into(imageView);
    }

    public int getCurrentType() {
        return currentType;
    }

    public void setCurrentType(int currentType) {
        this.currentType = currentType;
    }
}
