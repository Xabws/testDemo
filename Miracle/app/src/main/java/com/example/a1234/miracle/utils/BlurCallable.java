package com.example.a1234.miracle.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.example.a1234.miracle.utils.blurkit.BlurKit;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by a1234 on 2018/8/3.
 */

public class BlurCallable implements Callable<Drawable> {
    private Activity activity;
    private int radius = 15;//模糊程度;
    @Override
    public Drawable call() throws Exception {
        return applyBlur();
    }

    /***
     * 高斯模糊背景
     */
    private Drawable applyBlur() {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        view.destroyDrawingCache();

        /**
         * 获取当前窗口快照，相当于截屏
         */
        Bitmap bmp1 = view.getDrawingCache();
        int height = getOtherHeight();
        int virtualbar = getVirtualBarHeigh();
        /**
         * 除去状态栏和标题栏
         */
        Bitmap bmp2 = Bitmap.createBitmap(bmp1, 0, height, bmp1.getWidth(), bmp1.getHeight() - height - virtualbar);
        return blur(bmp2);
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Drawable blur(Bitmap bkg) {
     /*   float scaleFactor = 4;//图片缩放比例；
              Bitmap overlay = ImageUtil.resizeImage(bkg,
                (int) (ContentUtil.getScreenWidth(activity) / scaleFactor),
                (int) ((ContentUtil.getScreenHeight(activity) - getOtherHeight()) / scaleFactor));
*/
       // overlay = ImageUtil.doBlur(overlay, (int) radius, false);
        bkg = BlurKit.getInstance().blur(bkg, (int) radius);
        Drawable drawable = new BitmapDrawable(activity.getResources(), bkg);
        return drawable;
       /* view.setBackground(drawable);
        setVisibility(VISIBLE);*/
    }

    /**
     * 获取系统状态栏和软件标题栏，部分软件没有标题栏，看自己软件的配置；
     *
     * @return
     */
    private int getOtherHeight() {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentTop - statusBarHeight;
        return statusBarHeight + titleBarHeight;
    }

    /**
     * 获取虚拟功能键高度
     */
    public int getVirtualBarHeigh() {
        int vh = 0;
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

}
