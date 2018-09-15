package com.example.a1234.miracle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.customview.MyViewPager;
import com.example.a1234.miracle.customview.PagerItemView;
import com.example.a1234.miracle.customview.loadmorerecycleview.LoadMoreRecycleAdapter;
import com.example.a1234.miracle.data.ZHStory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LatestAdapter extends LoadMoreRecycleAdapter {
    private Context context;
    private ArrayList<ZHStory> list;
    private OnItemClickListener onItemClickListener;
    private NewSPagerAdapter newSPagerAdapter;

    public LatestAdapter(ArrayList<ZHStory> list, Context context) {
        this.list = list;
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
                    newSPagerAdapter = new NewSPagerAdapter(this.context);
                    int max = list.size() > 5 ? 5 : list.size();
                    newSPagerAdapter.setList(list.subList(0, max));
                    PagerHolder pagerHolder = (PagerHolder) holder;
                    pagerHolder.view_pager.setAdapter(newSPagerAdapter);
                }
                break;
            case 1:
                DrawerHolder bannerViewHolder = (DrawerHolder) holder;
                bannerViewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position - 1);
                    }
                });
                bannerViewHolder.tvTitle.setText(list.get(position - 1).getTitle());
                Glide.with(context).load(list.get(position - 1).getImages().get(0)).into(bannerViewHolder.ivCover);
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
        return list == null ? 0 : list.size();
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

    public void setData(ArrayList<ZHStory> list) {
        this.list = list;
    }

    /*
        static class PagerHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.cl_pager)
            ConstraintLayout cl_pager;
            @BindView(R.id.iv_page)
            ImageView iv_page;
            @BindView(R.id.tv_pager)
            TextView tv_pager;
            public PagerHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }*/
    static class PagerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_pager)
        MyViewPager view_pager;

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

    //viewpager的aderpter

    private class NewSPagerAdapter extends PagerAdapter {
        private List<PagerItemView> pagerList;
        private List<ZHStory> dataList;
        private Context mContext;

        public NewSPagerAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setList(List<ZHStory> list) {
            this.dataList = list;
            pagerList = new ArrayList<>();
           for( int i=0;i<dataList.size();i++ ){
               pagerList.add(new PagerItemView(mContext,dataList.get(i).getImages().get(0),dataList.get(i).getTitle()));
           }
        }

        @Override
        public int getCount() {
            return this.pagerList == null ? 0 : this.pagerList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagerList.get(position));
            return pagerList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
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