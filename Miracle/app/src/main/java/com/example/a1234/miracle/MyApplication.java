package com.example.a1234.miracle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.hjq.toast.ToastUtils;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 继承Activity生命周期接口，当前回调显示可见的Activity的生命周期
 */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static Context mContext;
    /*当前显示的Activity*/
    private Activity activity;
    private View ShowView;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //初始化全局toast
        ToastUtils.init(this);
        ToastUtils.setGravity(Gravity.BOTTOM, 20, 35);
        this.registerActivityLifecycleCallbacks(this);
        handleSSLHandshake();
    }

    //创建一个静态的方法，以便获取context对象
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        this.activity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    /**
     * 显示View
     * 在任意Activity上显示View
     * @param view 需要显示到Activity的视图
     */
    public void showView(View view) {
        /*Activity不为空并且没有被释放掉*/
        if (this.activity != null && !this.activity.isFinishing() && view != null) {
            /*获取Activity顶层视图,并添加自定义View*/
            ((ViewGroup) this.activity.getWindow().getDecorView()).addView(view);
            this.ShowView = view;
        }
    }

    public View getShowView() {
        return ShowView;
    }

    public void setShowView(View showView) {
        ShowView = showView;
    }

    /**
     * 隐藏View
     *
     * @param view 需要从Activity中移除的视图
     */
    public void hideView(View view) {
        /*Activity不为空并且没有被释放掉*/
        if (this.activity != null && !this.activity.isFinishing() && view != null) {
            try {
                /*获取Activity顶层视图*/
                ViewGroup root = ((ViewGroup) this.activity.getWindow().getDecorView());
                /*如果Activity中存在View对象则删除*/
                if (root.indexOfChild(view) != -1) {
                    /*从顶层视图中删除*/
                    root.removeView(view);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 忽略https的证书校验
     * <p>
     * 避免Glide加载https图片报错：
     * <p>
     * javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
     */

    public static void handleSSLHandshake() {

        try {

            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

                public X509Certificate[] getAcceptedIssuers() {

                    return new X509Certificate[0];

                }

                @Override

                public void checkClientTrusted(X509Certificate[] certs, String authType) {

                }

                @Override

                public void checkServerTrusted(X509Certificate[] certs, String authType) {

                }

            }};

            SSLContext sc = SSLContext.getInstance("TLS");

            // trustAllCerts信任所有的证书

            sc.init(null, trustAllCerts, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override

                public boolean verify(String hostname, SSLSession session) {

                    return true;

                }

            });

        } catch (Exception ignored) {

        }

    }
}