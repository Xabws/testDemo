package com.example.baselib.retrofit.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by a1234 on 2018/9/6.
 */

public class User {
    private Context context;
    private String name;
    private String age;

    public User(Context context) {
        this.context = context;
        Log.d("User", "a person created"+context);
    }

    public User(String name) {
        this.name = name;
        Log.d("User", "a person name:" + name);
    }

    public User(String name, String age, Context context) {
        this.age = age;
        this.name = name;
        this.context = context;
        Log.d("User", "a person name:" + name + " age: " + age + " context: " + context);
    }

    public void Toast() {
        Toast.makeText(context, "llll", Toast.LENGTH_LONG).show();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
