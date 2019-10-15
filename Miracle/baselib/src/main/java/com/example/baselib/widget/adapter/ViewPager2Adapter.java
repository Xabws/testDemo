package com.example.baselib.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.baselib.R;
import com.example.baselib.retrofit.data.ZHTopStory;
import com.example.baselib.widget.PagerItemView;

import java.util.List;

public class ViewPager2Adapter extends RecyclerView.Adapter<PagerItemView> {
    private List<ZHTopStory> dataList;
    private Context mContext;
    private PagerItemClickListener PagerItemClickListener;

    public ViewPager2Adapter(Context mContext, PagerItemClickListener pagerItemClickListener) {
        this.mContext = mContext;
        this.PagerItemClickListener = pagerItemClickListener;
    }

    public void setDataList(List<ZHTopStory> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public PagerItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        PagerItemView viewHolder = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item_layout, parent, false);
        viewHolder = new PagerItemView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PagerItemView holder,final int position) {
        Glide.with(mContext).load(dataList.get(position).getImage()).into(holder.ivPage);
        holder.tvPager.setText(dataList.get(position).getTitle());
        holder.clPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PagerItemClickListener.onItemClick(dataList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.dataList == null ? 0 : this.dataList.size();
    }

    public interface PagerItemClickListener {
        void onItemClick(int newsId);
    }

    public void setPagerItemClickListener(PagerItemClickListener PagerItemClickListener) {
        this.PagerItemClickListener = PagerItemClickListener;
    }

}