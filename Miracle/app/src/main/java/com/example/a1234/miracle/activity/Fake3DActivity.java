package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.os.Environment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.customview.ShowFake3DView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: wsBai
 * date: 2018/12/4
 */
public class Fake3DActivity extends BaseActivity {
    @BindView(R.id.iv_image)
    ShowFake3DView ivImage;
    @BindView(R.id.animation_view)
    LottieAnimationView animationView;
    private ArrayList<String> panoramasList;

    @Override
    public int getContentViewId() {
        return R.layout.acitivity_fake3d;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        animationView.setAnimation("LottieLogo1.json");
        animationView.loop(true);
        animationView.playAnimation();
        // getFileName();
        // ivImage.setPicList(panoramasList);

    }

    /**
     * 遍历文件夹下所有文件
     *
     * @return
     */
    public void getFileName() {
        panoramasList = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory() + "/Fake3d/");
        if (!file.exists())
            return;
        final File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) { // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                panoramasList.add("file://" + Environment.getExternalStorageDirectory() + "/Fake3d/" + subFile[iFileLength].getName());
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_image)
    public void onViewClicked() {
    }
}
