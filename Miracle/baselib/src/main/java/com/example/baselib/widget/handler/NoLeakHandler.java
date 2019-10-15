package com.example.baselib.widget.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import com.example.baselib.utils.HandlerCheck;

import java.lang.ref.WeakReference;

/**
 * 处理内存泄漏的handler
 * 如果该类需要使用Activity相关变量，可以采用弱引用的方式将Activity的变量传过去。
 * 在获取Activity的时候还可以加上判断当前Activity是不是isFinishing的代码，
 * 避免因为当前Activity已经进入了finish状态，还去引用这个Activity
 *
 * @param <T>
 */
public class NoLeakHandler<T> extends Handler {

    private final WeakReference<T> mTargetRef;

    public NoLeakHandler(T target) {
        mTargetRef = new WeakReference(target);
    }

    public NoLeakHandler(Looper looper, T target) {
        super(looper);
        mTargetRef = new WeakReference(target);
    }

    public NoLeakHandler(Callback callback, T target) {
        super(callback);
        mTargetRef = new WeakReference(target);
    }

    public NoLeakHandler(Looper looper, Callback callback, T target) {
        super(looper, callback);
        mTargetRef = new WeakReference(target);
    }

    @Override
    public final void dispatchMessage(Message msg) {
        //这里可以判断下当前的Activity是不是处于finish状态，如果处于activity.isFinishing()状态则target也为空
        if (HandlerCheck.checkNotLeak(mTargetRef) != null) {
            super.dispatchMessage(msg);
        }
    }

    @Override
    public final void handleMessage(Message msg) {
        T target;
        if ((target = HandlerCheck.checkNotLeak(mTargetRef)) != null) {
            processMessage(target, msg);
        }
    }

    protected void processMessage(T context, Message msg) {
    }
}