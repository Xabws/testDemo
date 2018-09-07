package com.example.a1234.animdemo.component;

import com.example.a1234.animdemo.activity.Demo3Activity;
import com.example.a1234.animdemo.module.UserModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by a1234 on 2018/9/6.
 * 想成一个容器， module中产出的东西都放在里面
 */
@Singleton
@Component(modules = {UserModule.class})
public interface UserComponent {
    void inject(Demo3Activity demo3Activity);
}
