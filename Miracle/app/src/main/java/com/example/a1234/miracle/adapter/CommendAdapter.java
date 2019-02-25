package com.example.a1234.miracle.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.customview.glideshape.GlideCircleTransform;
import com.example.a1234.miracle.data.ZHCommendData;
import com.example.a1234.miracle.utils.DateFormatUtil;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
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
        Glide.with(context).load(list.get(position ).getAvatar()).bitmapTransform(new GlideCircleTransform(context)).into(bannerViewHolder.ivAvatar);
        bannerViewHolder.tvComment.setText(list.get(position).getContent());
        bannerViewHolder.tvCommentTime.setText(DateFormatUtil.transForDate1(Integer.parseInt(list.get(position).getTime())));
        bannerViewHolder.tvLike.setText(list.get(position).getLikes()+"");
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
        view = LayoutInflater.from(context).inflate(R.layout.comment_list_layout
                , parent, false);
        viewHolder = new DrawerHolder(view);
        return viewHolder;
    }

    public void setData(ArrayList<ZHCommendData> list) {
        this.list = list;
    }


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