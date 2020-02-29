package com.example.a1234.miracle.activity;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.AcitivityLoadingBinding;


/**
 * author: wsBai
 * date: 2018/12/4
 */
public class LoadingActivitiy extends BaseActivity {
    private AcitivityLoadingBinding acitivityLoadingBinding;
    @Override
    public int getContentViewId() {
        return R.layout.acitivity_loading;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        acitivityLoadingBinding = DataBindingUtil.setContentView(this,getContentViewId());
        Glide.with(this)
                .load(R.drawable.loading_cat)
                .into(acitivityLoadingBinding.ivLoading);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
