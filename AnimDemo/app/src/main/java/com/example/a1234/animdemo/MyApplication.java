package com.example.a1234.animdemo;

import android.app.Application;

import com.example.a1234.animdemo.component.AppComponent;
import com.example.a1234.animdemo.component.DaggerAppComponent;
import com.example.a1234.animdemo.module.AppModule;
import com.example.a1234.animdemo.module.NetModule;

public class MyApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://github.com/"))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}