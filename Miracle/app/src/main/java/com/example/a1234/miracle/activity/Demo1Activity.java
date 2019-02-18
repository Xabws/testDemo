package com.example.a1234.miracle.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.a1234.miracle.customview.DrawerView;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.utils.ContentUtil;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by a1234 on 2018/8/16.
 */

public class Demo1Activity extends Activity {
    /*  private DrawerView bottomDrawerView;
      private TextView tv_content;
      RelativeLayout bg;
      TextView draw_handle;*/
    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);

        iv = findViewById(R.id.iv);
        AnimationDrawable anim = new AnimationDrawable();

        for (int i = 0; i <=200; i++) {
            int id = getResources().getIdentifier("circle_" + i, "drawable", "com.example.a1234.miracle");
            Drawable drawable = null;
             drawable = getResources().getDrawable(id);
            anim.addFrame(drawable, 1);
        }
        anim.setOneShot(false);
        iv.setImageDrawable(anim);
        anim.start();
        //anim.start();Glide.with(this).load(R.mipmap.).into(iv);
      /*  draw_handle = findViewById(R.id.draw_handle);
        tv_content = findViewById(R.id.tv_content);
        bg = findViewById(R.id.bg);
        Glide.with(this).load(R.drawable.bg).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                bg.setBackground(new BitmapDrawable(resource));
            }
        });
        bottomDrawerView.setHandlerHeight( ContentUtil.dip2px(Demo1Activity.this, 160));
        draw_handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDrawerView.openDrawer();
                Log.d("sssss","draw_handle");
            }
        });
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sssss","content");
            }
        });*/
    }

}
