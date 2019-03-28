package com.example.a1234.miracle.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a1234.miracle.IMyAidlInterface;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.adapter.LatestAdapter;
import com.example.a1234.miracle.customview.loadmorerecycleview.LoadMoreRecyclerView;
import com.example.a1234.miracle.customview.loadmorerecycleview.OnPullUpRefreshListener;
import com.example.a1234.miracle.databinding.ActivityMainBinding;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.service.MiracleProcessService;
import com.example.a1234.miracle.utils.blurkit.BlurKit;
import com.example.a1234.miracle.viewmodel.ZHNewsViewModel;
import com.example.baselib.arch.viewmodel.BaseViewModel;
import com.example.baselib.retrofit.API;
import com.example.baselib.retrofit.data.WeatherBean;
import com.example.baselib.retrofit.data.ZHNewsListData;
import com.example.baselib.retrofit.data.ZHStory;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    //刷新的状态
    private static final int STATE_REFRESHING = 1;
    private static final int STATE_REFRESH_FINISH = 2;
    public static final int RESQUST_CODE = 5;
    ActivityMainBinding activityMainBinding;
    private int mRefreshState = STATE_REFRESH_FINISH;
    // 抽屉菜单对象
    private DrawerToggle mDrawerToggle; //侧滑菜单状态监听器
    private ArrayList<ZHStory> zhStoryArrayList;
    private IMyAidlInterface mStub;
    private LatestAdapter latestAdapter;
    private ZHNewsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BlurKit.init(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityMainBinding = DataBindingUtil.setContentView(this, getContentViewId());
        latestAdapter = new LatestAdapter(this, new LatestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EventBus.getDefault().postSticky(new MessageEvent(viewModel.getNewsList().get(position).getId()));
                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                startActivity(intent);
            }
        });
        activityMainBinding.setRecyclerAdapter(latestAdapter);
        viewModel = ViewModelProviders.of(MainActivity.this).get(ZHNewsViewModel.class);
        // zhNewsViewModel.loadData(API.LATEST);
        subscribeToModel(viewModel);
        activityMainBinding.layoutReadingContent.setOnRefreshListener(this);
        activityMainBinding.rvNews.setOnPullUpRefreshListener(onPullUpRefresh());
        //aidl调用
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //调用asInterface()方法获得IMyAidlInterface实例
                mStub = IMyAidlInterface.Stub.asInterface(service);
                if (mStub != null) {
                    try {
                        int x = mStub.add(1, 9);
                        Log.d("ddd", x + "");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("ddd", "empty!");
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(new Intent(this, MiracleProcessService.class), serviceConnection, BIND_AUTO_CREATE);
        init();
    }

    /**
     * 获取当天的新闻
     */
    private void getNews() {
        viewModel.updateList(ZHNewsViewModel.DATECURRENT);
    }

    /**
     * 获取之前的新闻
     * date:获取此日期之前：20180912：2018年9月11日的新闻
     */
    private void getOldNews(long date) {
        viewModel.updateList(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        onNavigationHeadClick();
        MainActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(this);
        //设置toolbar标题文本
        activityMainBinding.include.toolbar.setTitle("首页");
        //设置toolbar
        setSupportActionBar(activityMainBinding.include.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置左上角图标是否可点击
            actionBar.setHomeButtonEnabled(true);
            //左上角加上一个返回图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerToggle = new DrawerToggle(this, activityMainBinding.drawer, activityMainBinding.include.toolbar, R.string.open, R.string.close) {
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
        activityMainBinding.drawer.addDrawerListener(mDrawerToggle);
        activityMainBinding.navigationView.setNavigationItemSelectedListener(this);
        getNews();
    }

    private void onNavigationHeadClick() {
        //   View drawerView = navigationView.inflateHeaderView(R.layout.navigation_head);
        View drawerView = activityMainBinding.navigationView.getHeaderView(0);
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
                startActivity(new Intent(MainActivity.this, ThreadTestActivity.class));
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_suggestion:
                break;
            case R.id.nav_about:
                break;
        }
        activityMainBinding.include.toolbar.setTitle(item.getTitle());
        activityMainBinding.drawer.closeDrawer(GravityCompat.START);
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
                    if (viewModel.getCurrentDate() != 0) {
                        viewModel.setCurrentDate(viewModel.getCurrentDate() - 1);
                    }
                    getOldNews(viewModel.getCurrentDate());
                }
                mRefreshState = STATE_REFRESHING;
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

    /**
     * 订阅数据变化来刷新UI
     *
     * @param model
     */
    private void subscribeToModel(final BaseViewModel model) {
        /**
         * 创建一个观察者用来更新UI操作
         */
        model.getLiveObservableData().observe(this, new androidx.lifecycle.Observer<ZHNewsListData>() {
            @Override
            public void onChanged(ZHNewsListData zhNewsListData) {
                if (zhNewsListData != null) {
                    model.setUiObservableData(zhNewsListData);
                    latestAdapter.setData(zhNewsListData.getStories());
                    if (activityMainBinding.layoutReadingContent.isRefreshing())
                        activityMainBinding.layoutReadingContent.setRefreshing(false);
                    mRefreshState = STATE_REFRESH_FINISH;
                }
            }
        });
    }
}
