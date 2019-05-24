package com.example.a1234.miracle.customview;

public interface Callback<T> {
    void onEvent(int code, String msg, T t);
}
