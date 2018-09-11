package com.example.a1234.animdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a1234.animdemo.R;
import com.example.a1234.animdemo.data.ZHStory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LatestAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<ZHStory> list;
    private OnItemClickListener onItemClickListener;

    public LatestAdapter(ArrayList<ZHStory> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public LatestAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_list_layout, parent, false);
        return new DrawerHolder(view);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DrawerHolder bannerViewHolder = (DrawerHolder) holder;
        bannerViewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
        bannerViewHolder.tvTitle.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getImages().get(0)).into(bannerViewHolder.ivCover);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public void setData(ArrayList<ZHStory> list) {
        this.list = list;
    }

    static class DrawerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_main)
        RelativeLayout rl_main;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public DrawerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @OnClick({R.id.ll})
    public void onViewClicked() {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}