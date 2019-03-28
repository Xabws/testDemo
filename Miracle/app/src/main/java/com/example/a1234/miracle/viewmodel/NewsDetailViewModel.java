package com.example.a1234.miracle.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.activity.NewsCommentActivity;
import com.example.a1234.miracle.databinding.ActivityNewsDetailBinding;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.baselib.arch.viewmodel.BaseViewModel;
import com.example.baselib.retrofit.data.ZHContent;
import com.example.baselib.retrofit.data.ZHNewsDetail;
import com.example.baselib.retrofit.data.ZHNewsExtra;
import com.example.baselib.retrofit.repository.APiRepository;
import com.example.baselib.retrofit.util.NetUtils;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wsbai
 * @date 2019/3/27
 */
public class NewsDetailViewModel extends BaseViewModel<ZHNewsDetail> {
    private Application application;
    //回调给Acitvity的livedata；
    private LiveData<ZHNewsDetail> mLiveObservableData;
    private String NewsId;

    public NewsDetailViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void init(String newsId) {
        NewsId = newsId;
        mLiveObservableData = Transformations.switchMap(NetUtils.netConnected(application), new Function<Boolean, LiveData<ZHNewsDetail>>() {
            @Override
            public LiveData<ZHNewsDetail> apply(Boolean isNetConnected) {
                if (!isNetConnected)
                    return ABSENT;
                return concatRequest(newsId);
            }
        });
    }

    private MutableLiveData<ZHNewsDetail> concatRequest(String newsId) {
        MutableLiveData<ZHNewsDetail> mutableLiveData = new MutableLiveData<>();
        ZHNewsDetail zhNewsDetail = new ZHNewsDetail();
        //获取文章正文
        Observable<ZHContent> bodyObservable = APiRepository.getNewsDetailRepository(newsId);
        //获取文章评论点赞数目
        Observable<ZHNewsExtra> extraObservable = APiRepository.getNewsExtraRepository(newsId);
        /**
         * 合并多个请求，merge为非顺序请求，concat为顺序请求
         * 如果每个请求之间存在先后依赖，则可以使用map或flatMap操作符
         */
        Observable.concat(bodyObservable, extraObservable)
                .subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object object) {
                        if (object instanceof ZHContent) {
                            zhNewsDetail.setZhContent((ZHContent) object);
                        } else if (object instanceof ZHNewsExtra) {
                            zhNewsDetail.setZhNewsExtra((ZHNewsExtra) object);
                        }
                        mutableLiveData.setValue(zhNewsDetail);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return mutableLiveData;
    }

    public String getNewsId() {
        return NewsId;
    }

    @Override
    public LiveData<ZHNewsDetail> getLiveObservableData() {
        return mLiveObservableData;
    }
}
