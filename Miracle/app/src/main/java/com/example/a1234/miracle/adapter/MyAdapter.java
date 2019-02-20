package com.example.a1234.miracle.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.customview.viewpager.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * author: wsBai
 * date: 2019/2/20
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> mList = new ArrayList<>();
    private Activity activity;
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public MyAdapter(List<String> mList,Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new MyAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        Glide.with(activity).load(mList.get(position)).into(holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }

}
