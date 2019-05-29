package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.AcitivityNettyBinding;
import com.example.a1234.miracle.netty.IMSClientBootstrap;
import com.example.baselib.netty.im.IMSConfig;

/**
 * @author wsbai
 * @date 2019-05-28
 */
public class NettyActivity extends BaseActivity {
    private AcitivityNettyBinding acitivityNettyBinding;
    private IMSClientBootstrap imsClientBootstrap;

    @Override
    public int getContentViewId() {
        return R.layout.acitivity_netty;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        acitivityNettyBinding = DataBindingUtil.setContentView(this, getContentViewId());
        acitivityNettyBinding.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imsClientBootstrap = IMSClientBootstrap.getInstance();
                imsClientBootstrap.init("192.168.2.171", IMSConfig.APP_STATUS_FOREGROUND);
            }
        });
    }

}
