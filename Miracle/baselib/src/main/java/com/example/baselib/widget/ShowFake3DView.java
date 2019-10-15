package com.example.baselib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.baselib.R;

import java.util.ArrayList;

/**
 * author: wsBai
 * date: 2018/12/4
 */
public class ShowFake3DView extends RelativeLayout {
    private Context context;
    private ArrayList<String> list;
    private int moveOffset;
    private int startX, currentX;
    private ImageView imageView;
    // 当前图片的编号,初始为1
    private Bitmap nextBitmap;
    private int scrNum = 1;

    public ShowFake3DView(Context context) {
        super(context);
        initView(context);
    }

    public ShowFake3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ShowFake3DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_3d, this);
        imageView = this.findViewById(R.id.iv);
    }

    public void setPicList(ArrayList<String> list) {
        this.list = list;
        try {
            nextBitmap = BitmapFactory.decodeFile(list.get(0).substring(7));
        } catch (Exception e) {
            e.printStackTrace();
            nextBitmap = null;
        }
        if (nextBitmap != null) {
            imageView.setImageBitmap(nextBitmap);
        } else {
            Glide.with(context).load(list.get(0)).placeholder(imageView.getDrawable()).into(imageView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                // 判断手势滑动方向，并切换图片
                if (currentX - startX > 6) {
                    //modifySrcL();
                    for (int i = 0; i < (currentX - startX) / 6; i++) {
                        modifySrcL();
                    }
                } else if (currentX - startX < -6) {
                    // modifySrcR();
                    for (int i = 0; i < (currentX - startX) / -6; i++) {
                        modifySrcR();
                    }
                }
                // 重置起始位置
//                startX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    // 向右滑动修改资源
    private void modifySrcR() {
        if (list == null) {
            return;
        }
        if (scrNum > list.size()) {
            scrNum = 1;
        }
        if (scrNum > 0) {
            Log.d("sssss",list.get(scrNum-1));
            try {
                nextBitmap = BitmapFactory.decodeFile(list.get(scrNum-1).substring(7));
            } catch (Exception e) {
                e.printStackTrace();
                nextBitmap = null;
            }
            if (nextBitmap != null) {
                imageView.setImageBitmap(nextBitmap);
            } else {
                Glide.with(context).load(list.get(scrNum - 1)).placeholder(imageView.getDrawable()).into(imageView);
            }
            scrNum++;
        }
        startX = currentX;

    }

    // 向左滑动修改资源
    private void modifySrcL() {
        if (list == null) {
            return;
        }
        if (scrNum <= 0) {
            scrNum = list.size();
        }
        if (scrNum <= list.size()) {
            try {
                nextBitmap = BitmapFactory.decodeFile(list.get(scrNum-1).substring(7));
            } catch (Exception e) {
                nextBitmap = null;
                e.printStackTrace();
            }
            if (nextBitmap != null) {
                imageView.setImageBitmap(nextBitmap);
            } else {
                Glide.with(context).load(list.get(scrNum - 1)).placeholder(imageView.getDrawable()).into(imageView);
            }
            scrNum--;
        }
        startX = currentX;
    }
}
