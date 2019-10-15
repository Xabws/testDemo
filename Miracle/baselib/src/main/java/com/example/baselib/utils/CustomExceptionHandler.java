package com.example.baselib.utils;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 全局捕捉错误handler
 */
public class CustomExceptionHandler implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler defaultUEH = null;

    public CustomExceptionHandler() {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable throwable) {
        if (defaultUEH != null) {
            defaultUEH.uncaughtException(t, throwable);
        }
    }
}
