package com.example.a1234.miracle.utils;

import android.app.Application;
import android.widget.Toast;

import com.example.a1234.miracle.MyApplication;


/**
 * Created by wenbin on 16/5/5.
 */
public class ToastUtil {
    private static Toast toast;



    /**
     * 吐司
     *
     * @param text
     */
    public static void toast(String text) {
        if (toast==null){
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        }else {
            cencelToast();
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 吐司
     *
     * @param text
     */
    public static void toast2(String text) {
        if (toast==null){
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_LONG);
        }else {
            cencelToast();
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_LONG);
        }
        toast.show();
    }

    public static void cencelToast(){
        if(toast!=null){
            toast.cancel();
            toast=null;
        }
    }
}
