package com.example.a1234.miracle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.adapter.LatestAdapter;
import com.example.a1234.miracle.customview.loadmorerecycleview.LoadMoreRecyclerView;
import com.example.a1234.miracle.customview.loadmorerecycleview.OnPullUpRefreshListener;
import com.example.a1234.miracle.data.ZHNewsListData;
import com.example.a1234.miracle.data.ZHStory;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.retrofit.inter.RetrofitAPI_Interface;
import com.example.a1234.miracle.utils.blurkit.BlurKit;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    //刷新的状态
    private static final int STATE_REFRESHING = 1;
    private static final int STATE_REFRESH_FINISH = 2;
    public static final int RESQUST_CODE = 5;
    private int mRefreshState = STATE_REFRESH_FINISH;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.layout_reading_content)
    com.example.a1234.miracle.customview.SwipeRefreshPagerLayout layoutReadingContent;
    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    // 抽屉菜单对象
    private DrawerToggle mDrawerToggle; //侧滑菜单状态监听器
    @BindView(R.id.rv_news)
    LoadMoreRecyclerView rvNews;
    private ArrayList<ZHStory> zhStoryArrayList;
    private LatestAdapter latestAdapter;
    private long currentDate;

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
        layoutReadingContent.setOnRefreshListener(this);
        rvNews.setOnPullUpRefreshListener(onPullUpRefresh());
    }

    /**
     * 获取当天的新闻
     */
    private void getNews() {
        if (latestAdapter == null) {
            latestAdapter = new LatestAdapter(this, new LatestAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    EventBus.getDefault().postSticky(new MessageEvent(zhStoryArrayList.get(position).getId()));
                    Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                    //intent.putExtra("newsId", zhStoryArrayList.get(position).getId());
                    startActivity(intent);
                }
            });
            rvNews.setLayoutManager(new LinearLayoutManager(this));
            rvNews.setAdapter(latestAdapter);
        }
        RetrofitAPI_Interface retrofitAPI_interface = retrofit.create(RetrofitAPI_Interface.class);
        Call<ZHNewsListData> call = retrofitAPI_interface.getLatest();
        call.enqueue(new Callback<ZHNewsListData>() {
            @Override
            public void onResponse(Call<ZHNewsListData> call, Response<ZHNewsListData> response) {
                currentDate = Long.parseLong(response.body().getDate());
                zhStoryArrayList = response.body().getStories();
                latestAdapter.setData(zhStoryArrayList);
                latestAdapter.notifyDataSetChanged();
                if (layoutReadingContent.isRefreshing())
                    layoutReadingContent.setRefreshing(false);
                mRefreshState = STATE_REFRESH_FINISH;
            }

            @Override
            public void onFailure(Call<ZHNewsListData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT);
            }

        });
    }

    /**
     * 获取之前的新闻
     * date:获取此日期之前：20180912：2018年9月11日的新闻
     */
    private void getOldNews(long date) {
        if (latestAdapter == null) {
            latestAdapter = new LatestAdapter(this, new LatestAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    EventBus.getDefault().postSticky(new MessageEvent(zhStoryArrayList.get(position).getId()));
                    Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                    //intent.putExtra("newsId", zhStoryArrayList.get(position).getId());
                    startActivity(intent);
                }
            });
            rvNews.setLayoutManager(new LinearLayoutManager(this));
            rvNews.setAdapter(latestAdapter);
        }
        RetrofitAPI_Interface retrofitAPI_interface = retrofit.create(RetrofitAPI_Interface.class);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("news/");
        stringBuffer.append("before/");
        stringBuffer.append(date);
        Call<ZHNewsListData> call = retrofitAPI_interface.getOldNews(stringBuffer.toString());
        call.enqueue(new Callback<ZHNewsListData>() {
            @Override
            public void onResponse(Call<ZHNewsListData> call, Response<ZHNewsListData> response) {
                if (response != null) {
                    if (zhStoryArrayList == null) {
                        zhStoryArrayList = response.body().getStories();
                    } else {
                        zhStoryArrayList.addAll(response.body().getStories());
                    }
                    latestAdapter.setData(zhStoryArrayList);
                    latestAdapter.notifyDataSetChanged();
                }
                if (layoutReadingContent.isRefreshing())
                    layoutReadingContent.setRefreshing(false);
                mRefreshState = STATE_REFRESH_FINISH;
            }

            @Override
            public void onFailure(Call<ZHNewsListData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        onNavigationHeadClick();
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
        getNews();
    }

    private void onNavigationHeadClick() {
        //   View drawerView = navigationView.inflateHeaderView(R.layout.navigation_head);
        View drawerView = navigationView.getHeaderView(0);
        final ImageView image_head = drawerView.findViewById(R.id.image_head);
        image_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(image_head, "head", Snackbar.LENGTH_LONG)
                        .setAction("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });
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
        switch (id) {
            case R.id.nav_notification:
                startActivity(new Intent(MainActivity.this, Demo1Activity.class));
                break;
            case R.id.nav_message:
                startActivity(new Intent(MainActivity.this, Demo2Activity.class));
                break;
            case R.id.nav_manage:
                startActivity(new Intent(MainActivity.this, NewsActivity.class));
                break;
            case R.id.nav_theme:
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_suggestion:
                break;
            case R.id.nav_about:
                break;
        }
        toolbar.setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 上拉加载
     *
     * @return
     */
    private OnPullUpRefreshListener onPullUpRefresh() {
        return new OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                //正在刷新的话，就不加载下拉刷新了
                if (mRefreshState == STATE_REFRESHING) {
                    return;
                } else {
                    if (currentDate != 0) {
                        currentDate--;
                    }
                    getOldNews(currentDate);
                }
                mRefreshState = STATE_REFRESHING;
                //mRefreshListener.onRefreshing();
                /*if (!marketController.Islistend()) {
                    marketController.startPullUpRefresh();
                } else {
                    mRefreshListener.onRefreshFinish();
                    ToastUtil.toast(getResources().getString(R.string.none_more_data));
                }
*/
            }
        };
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        getNews();
    }
}
