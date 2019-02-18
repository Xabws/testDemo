package com.example.a1234.miracle.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1234.miracle.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: wsBai
 * date: 2019/2/18
 */
public class AlbumAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList arrayList;
    private OnItemClickListener onItemClickListener;

    public AlbumAdapter(Context context, ArrayList arrayList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(context).inflate(R.layout.album_layout
                , parent, false);
        viewHolder = new CommendAdapter.DrawerHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
