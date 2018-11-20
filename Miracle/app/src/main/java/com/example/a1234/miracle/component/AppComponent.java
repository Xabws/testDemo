package com.example.a1234.miracle.component;

import com.example.a1234.miracle.MyApplication;
import com.example.a1234.miracle.activity.BaseActivity;
import com.example.a1234.miracle.module.AppModule;
import com.example.a1234.miracle.module.NetModule;
import com.example.a1234.miracle.module.UserModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class, UserModule.class})
public interface AppComponent {
    void inject(MyApplication application);
    void inject(BaseActivity baseActivity);
}