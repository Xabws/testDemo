package com.example.a1234.miracle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.adapter.CommendAdapter;
import com.example.a1234.miracle.adapter.LatestAdapter;
import com.example.a1234.miracle.data.ZHCommend;
import com.example.a1234.miracle.data.ZHCommendData;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.retrofit.inter.RetrofitAPI_Interface;
import com.example.a1234.miracle.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsCommentActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.edit)
    ImageView edit;
    @BindView(R.id.cl_title)
    ConstraintLayout clTitle;
    @BindView(R.id.tv_long)
    TextView tvLong;
    @BindView(R.id.cl_long)
    ConstraintLayout clLong;
    @BindView(R.id.rv_long)
    RecyclerView rvLong;
    @BindView(R.id.bg)
    ConstraintLayout bg;
    private String newsId;
    private ArrayList<ZHCommendData> longcommend;
    private ArrayList<ZHCommendData> shortcommend;
    private CommendAdapter commendAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void Event(MessageEvent messageEvent) {
        newsId = messageEvent.getMessage();
        initData();
    }

    private void initData() {
        if (commendAdapter == null) {
            commendAdapter = new CommendAdapter(this, new CommendAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                }
            });
            rvLong.setLayoutManager(new LinearLayoutManager(this));
            rvLong.setAdapter(commendAdapter);
        }
        RetrofitAPI_Interface retrofitAPI_interface = retrofit.create(RetrofitAPI_Interface.class);
        Observable<ZHCommend> observable = retrofitAPI_interface.getLongNewsComment(newsId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZHCommend>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZHCommend zhCommend) {
                        longcommend = zhCommend.getComments();
                        commendAdapter.setData(longcommend);
                        commendAdapter.notifyDataSetChanged();
                        LogUtils.d(zhCommend.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(NewsCommentActivity.this, "error", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Observable<ZHCommend> extra_observable = retrofitAPI_interface.getShortNewsComment(newsId);
        extra_observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZHCommend>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZHCommend zhCommend) {
                        shortcommend = zhCommend.getComments();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
