package com.example.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.baselib.R;
import com.example.baselib.databinding.LayoutTitleBinding;
import com.example.baselib.widget.base.BaseRelativeLayout;


/**
 * @author wsbai
 * @date 2019-07-08
 */
public class TitleView extends BaseRelativeLayout {
    private LayoutTitleBinding layoutTitleBinding;
    private TitleInter titleInter;
    /**
     * 属性定义
     */
    protected int right_src;
    protected String title_name;
    protected boolean isShowLeft;

    public TitleView(Context context) {
        super(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        layoutTitleBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_title, this, true);
    }

    @Override
    protected void initData() {
        if (right_src != 0) {
            layoutTitleBinding.clTitleRight.setVisibility(VISIBLE);
            layoutTitleBinding.ivTitleRight.setImageResource(right_src);
        }
        if (!TextUtils.isEmpty(title_name))
            layoutTitleBinding.tvTitle.setText(title_name);
        layoutTitleBinding.clTitleLeft.setVisibility(isShowLeft ? VISIBLE : GONE);
        layoutTitleBinding.clTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleInter.onBackPress();
            }
        });

        layoutTitleBinding.clTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleInter.onRightPress();
            }
        });
        layoutTitleBinding.tvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleInter.onRightPress();
            }
        });
    }

    @Override
    protected void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        right_src = typedArray.getResourceId(R.styleable.TitleView_title_img, 0);
        title_name = typedArray.getString(R.styleable.TitleView_title_name);
        isShowLeft = typedArray.getBoolean(R.styleable.TitleView_show_title_left, true);
        typedArray.recycle();

    }

    public void setTitleName(String name) {
        this.title_name = name;
        layoutTitleBinding.tvTitle.setText(name);
    }

    public void setRightImage(int resource) {
        layoutTitleBinding.clTitleRight.setVisibility(VISIBLE);
        layoutTitleBinding.ivTitleRight.setVisibility(VISIBLE);
        layoutTitleBinding.tvTitleRight.setVisibility(GONE);
        layoutTitleBinding.ivTitleRight.setImageResource(resource);
    }

    public void setRightText(String text) {
        layoutTitleBinding.clTitleRight.setVisibility(VISIBLE);
        layoutTitleBinding.tvTitleRight.setVisibility(VISIBLE);
        layoutTitleBinding.ivTitleRight.setVisibility(GONE);
        layoutTitleBinding.tvTitleRight.setText(text);
    }

    public void isShowLeft(boolean isShowLeft){
        this.isShowLeft = isShowLeft;
        layoutTitleBinding.clTitleLeft.setVisibility(isShowLeft ? VISIBLE : GONE);
    }

    public interface TitleInter {
        void onBackPress();

        void onRightPress();
    }

    public void setTitleInter(TitleInter titleInter) {
        this.titleInter = titleInter;
    }

}
