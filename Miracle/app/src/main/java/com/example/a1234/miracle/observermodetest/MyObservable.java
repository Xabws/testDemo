package com.example.a1234.miracle.observermodetest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 被观察者
 * @author wsbai
 * @date 2019-06-15
 */
public abstract class MyObservable {

    List<MyObserver> observerList = new ArrayList<>();

    //注册
    public void regist(MyObserver observer) {
        observerList.add(observer);
    }

    //注销
    public void unregist(MyObserver observer) {
        observerList.remove(observer);
    }

    //通知
    public void _notify() {
        for (MyObserver ob : observerList) {
            ob.update();
        }

    }
}
