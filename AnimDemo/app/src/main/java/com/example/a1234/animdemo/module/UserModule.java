package com.example.a1234.animdemo.module;

import android.content.Context;

import com.example.a1234.animdemo.data.User;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by a1234 on 2018/9/6.
 * 把它想象成一个工厂，可以向外提供一些类的对象
 */
@Module
public class UserModule {
    private Context context;
    private String name;
    private String age;

    public UserModule(Context context, String name, String age) {
        this.context = context;
        this.name = name;
        this.age = age;
    }

    /**
     * 构造方法里有什么参数，就需要在module里提供什么参数
     */
    @Provides
    public Context provideContext() {
        return this.context;
    }

    @Named("context")
    @Singleton//设置为单例（Module和Component里需要注解）
    @Provides
    public User provideUserContext(Context context) {
        return new User(context);
    }
    @Named("Providesname")
    @Provides
    public String provideName() {
        return this.name;
    }
    @Named("Providesage")
    @Provides
    public String provideAge() {
        return this.age;
    }

    @Named("user")
    @Singleton
    @Provides
    public User provideUser( @Named("Providesname")String name, @Named("Providesage")String age, Context context) {
        return new User(name, age, context);
    }
}
