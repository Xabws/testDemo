package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.baselib.arch.viewmodel.BaseViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseActivity extends AppCompatActivity {
//    private Unbinder unbinder;
    public abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
//        ButterKnife.bind(this);
//        unbinder = ButterKnife.bind(this);
        //((MyApplication) getApplication()).getAppComponent().inject(this);
        EventBus.getDefault().register(this);
        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);

    //EventBus此方法不能设置为抽象
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0, sticky = true)
    public void Event(MessageEvent messageEvent) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
//        unbinder.unbind();
    }
}