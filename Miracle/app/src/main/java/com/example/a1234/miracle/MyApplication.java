package com.example.a1234.miracle;

import android.app.Application;
import android.content.Context;

import com.example.a1234.miracle.component.AppComponent;
import com.example.a1234.miracle.component.DaggerAppComponent;
import com.example.a1234.miracle.module.AppModule;
import com.example.a1234.miracle.module.NetModule;

import java.io.InputStream;

public class MyApplication extends Application {
    //MyApplication 要提供出Component，并且保证构建一次
    private AppComponent appComponent;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(API.BASEURL))
                .build();
    }

    //创建一个静态的方法，以便获取context对象
    public static Context getContext(){
        return mContext;
    }

    private void init(){
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}