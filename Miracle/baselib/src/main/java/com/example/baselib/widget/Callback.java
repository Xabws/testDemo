package com.example.baselib.widget;

public interface Callback<T> {
    void onEvent(int code, String msg, T t);
}
