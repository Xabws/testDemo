package com.example.a1234.animdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.a1234.animdemo.R;
import com.example.a1234.animdemo.adapter.LatestAdapter;
import com.example.a1234.animdemo.data.ZHNewsLatestData;
import com.example.a1234.animdemo.data.ZHStory;
import com.example.a1234.animdemo.eventbus.MessageEvent;
import com.example.a1234.animdemo.retrofit.inter.GetLateset_Inter;

import org.greenrobot.eventbus.EventBus;

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
        getNews();
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

    private void getNews() {
        if (latestAdapter == null) {
            latestAdapter = new LatestAdapter(this, new LatestAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    EventBus.getDefault().postSticky(new MessageEvent(zhStoryArrayList.get(position).getId()));
                    Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                    //intent.putExtra("newsId", zhStoryArrayList.get(position).getId());
                    startActivity(intent);
                }
            });
            rvNews.setLayoutManager(new LinearLayoutManager(this));
            rvNews.setAdapter(latestAdapter);
        }
        GetLateset_Inter getLateset_inter = retrofit.create(GetLateset_Inter.class);
        retrofit2.Call<ZHNewsLatestData> call = getLateset_inter.getLatest();
        call.enqueue(new retrofit2.Callback<ZHNewsLatestData>() {
            @Override
            public void onResponse(retrofit2.Call<ZHNewsLatestData> call, retrofit2.Response<ZHNewsLatestData> response) {
                zhStoryArrayList = response.body().getStories();
                latestAdapter.setData(zhStoryArrayList);
                latestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(retrofit2.Call<ZHNewsLatestData> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "加载失败", Toast.LENGTH_SHORT);
            }

        });
        Log.d(TAG, call.toString());
    }
}
