package com.example.a1234.miracle.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.customview.MyViewPager;
import com.example.a1234.miracle.customview.PagerItemView;
import com.example.a1234.miracle.data.ZHCommendData;
import com.example.a1234.miracle.data.ZHStory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ZHCommendData> list;
    private OnItemClickListener onItemClickListener;

    public CommendAdapter(ArrayList<ZHCommendData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public CommendAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        DrawerHolder bannerViewHolder = (DrawerHolder) holder;
        bannerViewHolder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position );
            }
        });
        bannerViewHolder.tvAuthor.setText(list.get(position).getAuthor());
        Glide.with(context).load(list.get(position ).getAvatar()).into(bannerViewHolder.ivAvatar);
        bannerViewHolder.tvComment.setText(list.get(position).getContent());
        bannerViewHolder.tvCommentTime.setText(list.get(position).getTime()+"");
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
       /* switch (viewType) {
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

        }*/
        view = LayoutInflater.from(context).inflate(R.layout.comment_list_layout
                , parent, false);
        viewHolder = new DrawerHolder(view);
        return viewHolder;
    }

    public void setData(ArrayList<ZHCommendData> list) {
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

    static class DrawerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.iv_like)
        ImageView ivLike;
        @BindView(R.id.tv_like)
        TextView tvLike;
        @BindView(R.id.cl_title)
        ConstraintLayout clTitle;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_comment_time)
        TextView tvCommentTime;
        @BindView(R.id.rl_main)
        ConstraintLayout rlMain;

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