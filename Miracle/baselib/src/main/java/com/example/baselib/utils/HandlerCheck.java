package com.example.baselib.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * @author wsbai
 * @date 2019-07-16
 * 用于检测当前的Activity是不是处于finish状态
 */
public class HandlerCheck {
    private static Class<?> sFragmentClassSupport;
    private static Method sGetActivitySupport;
    private static Class<?> sFragmentClass;
    private static Method sGetActivity;

    static {
        try {
            sFragmentClassSupport = Class.forName(" androidx.fragment.app.Fragment", false,
                    Thread.currentThread().getContextClassLoader());
            sGetActivitySupport = sFragmentClassSupport.getDeclaredMethod("getActivity");
        } catch (Exception e) {
            sFragmentClassSupport = null;
            sGetActivitySupport = null;
        }

        try {
            sFragmentClass = Class.forName("android.app.Fragment", false,
                    Thread.currentThread().getContextClassLoader());
            sGetActivity = sFragmentClass.getDeclaredMethod("getActivity");
        } catch (Exception e) {
            sFragmentClass = null;
            sGetActivity = null;
        }
    }
    //该方法主要判断当前的Activity是不是isFinishing，如果是则返回null。
    public static <T> T checkNotLeak(WeakReference<T> ref) {
        T target = (ref != null ? ref.get() : null);
        if (target != null) {
            Activity activity = null;
            //is the target instance which shows in the foreground (usually {@link Activity} or {@link Fragment})
            boolean isShowForeground = false;
            if ((sFragmentClassSupport != null) && (sFragmentClassSupport.isInstance(target))) {
                isShowForeground = true;
                try {
                    activity = (Activity) sGetActivitySupport.invoke(target, (Object[]) null);
                } catch (Exception ignored) {
                }
            } else if ((target instanceof Activity)) {
                isShowForeground = true;
                activity = (Activity) target;
            } else if ((sFragmentClass != null) && (sFragmentClass.isInstance(target))) {
                isShowForeground = true;
                try {
                    activity = (Activity) sGetActivity.invoke(target, (Object[]) null);
                } catch (Exception ignored) {
                }
            }

            if (isShowForeground) {
                if ((activity == null) || activity.isFinishing()) {
                    return null;
                }
            }
        }
        return target;
    }
}
