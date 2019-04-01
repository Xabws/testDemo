package com.example.a1234.miracle.adapter;

import android.content.Context;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.utils.DeviceUtil;
import com.example.baselib.retrofit.data.ImageBean;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: wsBai
 * date: 2019/2/18
 */
public class AlbumPagerAdapter extends RecyclerView.Adapter<AlbumPagerAdapter.DrawerHolder> {
    private Context context;
    private ArrayList<ImageBean> piclist;
    private OnItemClickListener onItemClickListener;
    private int currentPosition = 0;

    public AlbumPagerAdapter(Context context, ArrayList<ImageBean> piclist, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.piclist = piclist;
        this.onItemClickListener = onItemClickListener;
    }

    public AlbumPagerAdapter(Context contextt, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AlbumPagerAdapter.DrawerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        AlbumPagerAdapter.DrawerHolder viewHolder = null;
        view = LayoutInflater.from(context).inflate(R.layout.album_pager_layout
                , parent, false);
        viewHolder = new AlbumPagerAdapter.DrawerHolder(view);
        return viewHolder;
    }


    public int getCurrentPosition() {
        return currentPosition;
    }

    public ArrayList<ImageBean> getCurrentList() {
        return piclist;
    }

    @Override
    public void onBindViewHolder(AlbumPagerAdapter.DrawerHolder holder, final int position) {
        final DrawerHolder bannerViewHolder = (DrawerHolder) holder;
        Size s = DeviceUtil.getScreeWH(context);

        bannerViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position, piclist.get(position).getUrl());
            }
        });
        Glide.with(context).load(piclist.get(position)).override(s.getWidth() / 3, s.getWidth() / 3).into(bannerViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return piclist != null ? piclist.size() : 0;
    }

    static class DrawerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        public DrawerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @OnClick({R.id.image})
    public void onViewClicked() {

    }


    public interface OnItemClickListener {
        void onItemClick(int position, String url);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPiclist(ArrayList<ImageBean> piclist) {
        this.piclist = piclist;
    }
}
