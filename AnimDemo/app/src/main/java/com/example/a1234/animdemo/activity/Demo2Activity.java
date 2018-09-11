package com.example.a1234.animdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a1234.animdemo.MyApplication;
import com.example.a1234.animdemo.customview.CardDialog;
import com.example.a1234.animdemo.R;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by a1234 on 2018/8/16.
 */

public class Demo2Activity extends Activity {
    private CardView cardView;
    private ImageView iv;
    private CardDialog cardDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        cardView = findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDialog= CardDialog.createInstance(Demo2Activity.this, new CardDialog.CardDialogCallback() {
                    @Override
                    public void onOKClick() {

                    }

                    @Override
                    public void onCancelClick() {

                    }

                    @Override
                    public void onDismiss() {

                    }
                });
                cardDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       // cardDialog.dismiss();
                    }
                },1000);
            }
        });
        iv = findViewById(R.id.iv);
        Glide.with(this).load(R.drawable.test).into(iv);
    }

}
