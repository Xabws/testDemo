package com.example.a1234.animdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.example.a1234.animdemo.R;
import com.example.a1234.animdemo.adapter.LatestAdapter;
import com.example.a1234.animdemo.data.ZHStory;
import com.example.a1234.animdemo.eventbus.MessageEvent;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by a1234 on 2018/8/16.
 */

public class NewsActivity extends BaseActivity {
    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    private String TAG = "NewsActivity";
    /*@Named("name")
    @Inject
    User user;
    @Named("context")
    @Inject
    User user2;*/
    private ArrayList<ZHStory> zhStoryArrayList;
    private LatestAdapter latestAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DaggerUserComponent.builder().userModule(new UserModule(this, "timmy", "8")).build().inject(this);
       // getNews();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_demo3;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void Event(MessageEvent messageEvent) {

    }

}
