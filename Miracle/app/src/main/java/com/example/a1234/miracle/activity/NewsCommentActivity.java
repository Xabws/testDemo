package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.adapter.CommendAdapter;
import com.example.a1234.miracle.customview.FullyLinearLayoutManager2;
import com.example.a1234.miracle.data.ZHCommend;
import com.example.a1234.miracle.data.ZHCommendData;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.retrofit.inter.RetrofitAPI_Interface;
import com.example.a1234.miracle.utils.LogUtils;

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
    @BindView(R.id.tv_short)
    TextView tvShort;
    @BindView(R.id.cl_long)
    ConstraintLayout clLong;
    @BindView(R.id.rv_long)
    RecyclerView rvLong;
    @BindView(R.id.rv_short)
    RecyclerView rv_short;
    @BindView(R.id.bg)
    ConstraintLayout bg;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.tv_comment_all)
    TextView tvCommentAll;
    private String newsId;
    private ArrayList<ZHCommendData> longcommend;
    private ArrayList<ZHCommendData> shortcommend;
    private CommendAdapter commendLongAdapter;
    private CommendAdapter commendShortAdapter;
    private int totalComment = 0;

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
        rv_short.setVisibility(View.GONE);
        if (commendLongAdapter == null) {
            commendLongAdapter = new CommendAdapter(this, new CommendAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                }
            });
            rvLong.setLayoutManager(new FullyLinearLayoutManager2(this));
            rvLong.setAdapter(commendLongAdapter);
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
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(longcommend.size());
                        stringBuffer.append("条长评");
                        tvLong.setText(stringBuffer.toString());
                        commendLongAdapter.setData(longcommend);
                        commendLongAdapter.notifyDataSetChanged();
                        totalComment+=longcommend.size();
                        tvCommentAll.setText(totalComment+"条点评");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(NewsCommentActivity.this, "error", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        if (commendShortAdapter == null) {
            commendShortAdapter = new CommendAdapter(this, new CommendAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                }
            });
            rv_short.setLayoutManager(new FullyLinearLayoutManager2(this));
            rv_short.setAdapter(commendShortAdapter);
        }
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
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(shortcommend.size());
                        stringBuffer.append("条短评");
                        tvShort.setText(stringBuffer.toString());
                        LogUtils.d(shortcommend.size() + "");
                        commendShortAdapter.setData(shortcommend);
                        commendShortAdapter.notifyDataSetChanged();
                        totalComment+=shortcommend.size();
                        tvCommentAll.setText(totalComment+"条点评");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_short.setVisibility(rv_short.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
