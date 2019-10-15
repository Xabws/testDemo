package com.example.a1234.miracle.observermodetest;

import android.util.Log;

/**
 * @author wsbai
 * @date 2019-06-15
 */
public class ClientObserver2 implements MyObserver {
    @Override
    public void update() {
        Log.d("观察者2：", "getNewMessage!!!!!");
    }
}
