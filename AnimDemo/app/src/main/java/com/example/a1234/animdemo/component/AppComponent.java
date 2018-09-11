package com.example.a1234.animdemo.component;

import com.example.a1234.animdemo.MyApplication;
import com.example.a1234.animdemo.activity.BaseActivity;
import com.example.a1234.animdemo.activity.Demo1Activity;
import com.example.a1234.animdemo.activity.Demo2Activity;
import com.example.a1234.animdemo.activity.NewsActivity;
import com.example.a1234.animdemo.activity.NewsDetailActivity;
import com.example.a1234.animdemo.module.AppModule;
import com.example.a1234.animdemo.module.NetModule;
import com.example.a1234.animdemo.module.UserModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, UserModule.class})
public interface AppComponent {
    void inject(MyApplication application);
    void inject(BaseActivity baseActivity);
}