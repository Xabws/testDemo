package com.example.a1234.miracle;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

    }

    //创建一个静态的方法，以便获取context对象
    public static Context getContext() {
        return mContext;
    }

    private void init() {
    }

}