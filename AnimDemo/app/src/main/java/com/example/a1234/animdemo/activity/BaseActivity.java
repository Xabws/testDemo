package com.example.a1234.animdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.a1234.animdemo.MyApplication;
import com.example.a1234.animdemo.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Retrofit;

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;
    @Inject
    public Retrofit retrofit;

    public abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        ((MyApplication) getApplication()).getAppComponent().inject(this);
        initView(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    protected abstract void initView(Bundle savedInstanceState);

    //EventBus此方法不能设置为抽象
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0, sticky = true)
    public void Event(MessageEvent messageEvent){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }
}