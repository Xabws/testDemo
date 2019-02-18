package com.example.a1234.miracle.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
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
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@RuntimePermissions
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
        Observable<ZHNewsListData> observable = retrofitAPI_interface.getLatest();//获取观察者对象
        /**
         * 在RxJava 中，Scheduler ——调度器，相当于线程控制器，RxJava 通过它来指定每一段代码应该运行在什么样的线程。RxJava 已经内置了几个 Scheduler ，它们已经适合大多数的使用场景：
         Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
         Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
         Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
         Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
         另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
         */
        observable.subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ZHNewsListData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZHNewsListData zhNewsListData) {
                        currentDate = Long.parseLong(zhNewsListData.getDate());
                        zhStoryArrayList = zhNewsListData.getStories();
                        latestAdapter.setData(zhStoryArrayList);
                        latestAdapter.notifyDataSetChanged();
                        if (layoutReadingContent.isRefreshing())
                            layoutReadingContent.setRefreshing(false);
                        mRefreshState = STATE_REFRESH_FINISH;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*call.enqueue(new Callback<ZHNewsListData>() {
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

        });*/
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
        Observable<ZHNewsListData> observable = retrofitAPI_interface.getOldNews(date);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZHNewsListData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZHNewsListData zhNewsListData) {
                        if (zhNewsListData != null) {
                            if (zhStoryArrayList == null) {
                                zhStoryArrayList = zhNewsListData.getStories();
                            } else {
                                zhStoryArrayList.addAll(zhNewsListData.getStories());
                            }
                            latestAdapter.setData(zhStoryArrayList);
                            latestAdapter.notifyDataSetChanged();
                        }
                        if (layoutReadingContent.isRefreshing())
                            layoutReadingContent.setRefreshing(false);
                        mRefreshState = STATE_REFRESH_FINISH;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
       /* call.enqueue(new Callback<ZHNewsListData>() {
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

        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        onNavigationHeadClick();
        MainActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(this);
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

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestPermission() {
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
                startActivity(new Intent(MainActivity.this, Fake3DActivity.class));

                break;
            case R.id.nav_manage:
                startActivity(new Intent(MainActivity.this, AlbumAcitcity.class));

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

    //也就是说你获取了相应的权限之后就会执行这个方法
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onStoragePermission() {

    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
        //给用户解释要请求什么权限，为什么需要此权限
    void showRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("使用此功能需要WRITE_EXTERNAL_STORAGE和RECORD_AUDIO权限，下一步将继续请求权限")
                .setPositiveButton("下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续执行请求
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();//取消执行请求
            }
        })
                .show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionDenied() {
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionNeverAskAgain() {
    }
}
