package com.example.a1234.miracle.customview;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by a1234 on 2018/9/12.
 */

public class PagerItemView extends ConstraintLayout {
    @BindView(R.id.iv_page)
    ResizableImageView ivPage;
    @BindView(R.id.tv_pager)
    TextView tvPager;
    @BindView(R.id.cl_pager)
    ConstraintLayout clPager;
    private Context context;
    private String img,text;

    public PagerItemView(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public PagerItemView(Context context,String path,String title) {
        super(context);
        this.context = context;
        this.img = path;
        this.text = title;
        init();
    }
    public PagerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PagerItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
       View v = inflate(getContext(), R.layout.pager_item_layout, this);
        ButterKnife.bind(this,v);
        Glide.with(context).load(img).into(ivPage);
        tvPager.setText(text);
    }

    public void setIvPage(String path){
        Glide.with(context).load(path).into(ivPage);
    }
    public void setTvPager(String title){
        tvPager.setText(title);
    }

    @OnClick(R.id.cl_pager)
    public void onViewClicked() {
    }
}
