package com.example.a1234.miracle.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.customview.MyViewPager;
import com.example.a1234.miracle.customview.PagerItemView;
import com.example.a1234.miracle.customview.loadmorerecycleview.LoadMoreRecycleAdapter;
import com.example.a1234.miracle.utils.LogUtils;
import com.example.baselib.retrofit.data.ZHNewsListData;
import com.example.baselib.retrofit.data.ZHStory;
import com.example.baselib.retrofit.data.ZHTopStory;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LatestAdapter extends LoadMoreRecycleAdapter {
    private Context context;
    private ZHNewsListData data;
    private OnItemClickListener onItemClickListener;
    private ViewPager2Adapter newSPagerAdapter;
    ValueAnimator valueAnimator;

    public LatestAdapter(ZHNewsListData data, Context context) {
        this.data = data;
        this.context = context;
    }

    public LatestAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case 0:
                if (newSPagerAdapter == null) {
                    newSPagerAdapter = new ViewPager2Adapter(this.context, new ViewPager2Adapter.PagerItemClickListener() {
                        @Override
                        public void onItemClick(int newsId) {
                            onItemClickListener.onItemClick(newsId);
                        }
                    });
                    newSPagerAdapter.setDataList(data.getTop_stories());
                    PagerHolder pagerHolder = (PagerHolder) holder;
                    pagerHolder.view_pager.setAdapter(newSPagerAdapter);
                }
                break;
            case 1:
                DrawerHolder bannerViewHolder = (DrawerHolder) holder;
                bannerViewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(data.getStories().get(position - 1).getId());
                    }
                });
                bannerViewHolder.tvTitle.setText(data.getStories().get(position - 1).getTitle());
                Glide.with(context).load(data.getStories().get(position - 1).getImages().get(0)).into(bannerViewHolder.ivCover);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //0位置显示viewpager
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getDataSize() {
        return data != null ? data.getStories().size() + 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder LoadMoreonCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.pager_layout
                        , parent, false);
                viewHolder = new PagerHolder(view);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.latest_list_layout
                        , parent, false);
                viewHolder = new DrawerHolder(view);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public void setData(ZHNewsListData data) {
        this.data = data;
        notifyDataSetChanged();
    }


    static class PagerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_pager)
        ViewPager2 view_pager;

        public PagerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class DrawerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_main)
        CardView rl_main;
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
        void onItemClick(int newsId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void setBanner(ViewPager2 viewPager2) {
        int currentItem = viewPager2.getCurrentItem();
        int size = data.getTop_stories().size();
        valueAnimator = ValueAnimator.ofInt(0,size-1);
        valueAnimator.setDuration(4000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value =(int)animation.getAnimatedValue();
                Log.d("xxxxxx",value+"");
                viewPager2.setCurrentItem(value);
            }
        });

    }
}