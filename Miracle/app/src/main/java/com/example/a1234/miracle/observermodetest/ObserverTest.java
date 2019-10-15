package com.example.a1234.miracle.observermodetest;

/**
 * @author wsbai
 * @date 2019-06-15
 * 观察者模式定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。观察者模式完美的将观察者和被观察的对象分离开，一个对象的状态发生变化时，所有依赖于它的对象都得到通知并自动刷新。
 * 回调函数其实也算是一种观察者模式的实现方式，回调函数实现的观察者和被观察者往往是一对一的依赖关系。
 * 所以最明显的区别是观察者模式是一种设计思路，而回调函数式一种具体的实现方式；另一明显区别是一对多还是多对多的依赖关系方面。
 */
public class ObserverTest {
    private ClientObserver clientObserver;
    private ClientObserver2 clientObserver2;
    private ClientObserver3 clientObserver3;
    private ServerObservable serverObservable;

    public void start() {
        clientObserver = new ClientObserver();
        clientObserver2 = new ClientObserver2();
        clientObserver3 = new ClientObserver3();
        serverObservable = new ServerObservable();
        serverObservable.regist(clientObserver);
        serverObservable.regist(clientObserver2);
        serverObservable.regist(clientObserver3);
        serverObservable._notify();
        //取消注册
        serverObservable.unregist(clientObserver2);
        serverObservable._notify();
    }
}
