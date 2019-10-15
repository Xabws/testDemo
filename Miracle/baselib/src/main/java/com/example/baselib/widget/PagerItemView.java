package com.example.baselib.widget;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.R;


/**
 * Created by a1234 on 2018/9/12.
 */

public class PagerItemView extends RecyclerView.ViewHolder {
    public ImageView ivPage;
    public TextView tvPager;
    public ConstraintLayout clPager;

    public PagerItemView(View itemView) {
        super(itemView);
        ivPage = itemView.findViewById(R.id.iv_page);
        tvPager = itemView.findViewById(R.id.tv_pager);
        clPager = itemView.findViewById(R.id.cl_pager);
    }
}
