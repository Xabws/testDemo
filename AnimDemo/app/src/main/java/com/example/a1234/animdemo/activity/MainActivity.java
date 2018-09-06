package com.example.a1234.animdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1234.animdemo.R;
import com.example.a1234.animdemo.adapter.DrawerAdapter;
import com.example.a1234.animdemo.customview.AnimInterpolationView;
import com.example.a1234.animdemo.customview.AnimView;
import com.example.a1234.animdemo.utils.blurkit.BlurKit;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.animview)
    AnimView animview;
    @BindView(R.id.animinter)
    AnimInterpolationView animinter;
    @BindView(R.id.tv_3)
    TextView tv3;
    DrawerAdapter drawerAdapter;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_music)
    ImageView ivMusic;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    // 抽屉菜单对象
    private DrawerToggle mDrawerToggle; //侧滑菜单状态监听器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BlurKit.init(this);
        init();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {

       /* onViewClicked(animview);
        onViewClicked(animinter);
        onViewClicked(tv3);*/
        //设置toolbar标题文本
        toolbar.setTitle("首页");
        //设置toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置左上角图标是否可点击
            actionBar.setHomeButtonEnabled(true);
            //左上角加上一个返回图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerToggle = new DrawerToggle(this, drawer, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawer.addDrawerListener(mDrawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @OnClick({R.id.animview, R.id.animinter, R.id.tv_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.animview:
                startActivity(new Intent(MainActivity.this, Demo1Activity.class));
                break;
            case R.id.animinter:
                startActivity(new Intent(MainActivity.this, Demo2Activity.class));
                break;
            case R.id.tv_3:
                startActivity(new Intent(MainActivity.this, Demo3Activity.class));
                break;
        }
    }

    private class DrawerToggle extends ActionBarDrawerToggle {
        public DrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String mString = null;
        switch (id) {
            case R.id.nav_notification:
                mString = "通知";
                break;
            case R.id.nav_message:
                mString = "私信";
                break;
            case R.id.nav_manage:
                mString = "应用管理";
                break;
            case R.id.nav_theme:
                mString = "主题风格";
                break;
            case R.id.nav_setting:
                mString = "设置";
                break;
            case R.id.nav_suggestion:
                mString = "意见反馈";
                break;
            case R.id.nav_about:
                mString = "关于";
                break;
        }
        toolbar.setTitle(mString);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
