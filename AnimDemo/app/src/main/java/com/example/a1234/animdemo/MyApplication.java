package com.example.a1234.animdemo;

import android.app.Application;

import com.example.a1234.animdemo.component.AppComponent;
import com.example.a1234.animdemo.component.DaggerAppComponent;
import com.example.a1234.animdemo.module.AppModule;
import com.example.a1234.animdemo.module.NetModule;

import java.io.InputStream;

public class MyApplication extends Application {
    //MyApplication 要提供出Component，并且保证构建一次
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(API.BASEURL))
                .build();
    }

    private void init(){
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}