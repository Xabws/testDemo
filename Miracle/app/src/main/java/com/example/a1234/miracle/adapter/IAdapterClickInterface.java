package com.example.a1234.miracle.adapter;

import androidx.databinding.ViewDataBinding;

/**
 * @author wsbai
 * @date 2019/3/28
 * Adapter中的Item点击回调
 * Java8::@FunctionalInterface注释的约束：
 * 1、接口有且只能有个一个抽象方法，只有方法定义，没有方法体
 * 2、在接口中覆写Object类中的public方法，不算是函数式接口的方法。
 * B:绑定的ViewBinding对象
 * T:Adapter所使用的bean
 */
@FunctionalInterface
public interface IAdapterClickInterface<B extends ViewDataBinding, T> {
    void onItemClick(B binding, T t, int i);
}
