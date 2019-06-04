package com.example.a1234.miracle.viewmodel;

import android.app.Application;
import android.util.Log;

import com.example.a1234.miracle.utils.DateFormatUtil;
import com.example.baselib.arch.viewmodel.BaseViewModel;
import com.example.baselib.retrofit.data.ZHCommend;
import com.example.baselib.retrofit.repository.APiRepository;
import com.example.baselib.retrofit.util.NetUtils;

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
public class NewsCommentViewModel extends BaseViewModel<ZHCommend> {
    private int newsId;
    private Application application;
    /**
     * 主界面list的livedata的观察者对象
     * 短评论的观察者对象
     */
    private LiveData<ZHCommend> mLiveObservableData_short;
    /**
     * 主界面list的livedata的观察者对象
     * 长评论的观察者对象
     */
    private LiveData<ZHCommend> mLiveObservableData_long;

    public NewsCommentViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    private void init() {
        /**
         * Transformations.switchMap:
         * 在LiveData将变化的数据通知给观察者前，改变数据的类型；或者是返回一个不一样的LiveData。
         */
        mLiveObservableData_short = Transformations.switchMap(NetUtils.netConnected(application), (Function<Boolean, LiveData<ZHCommend>>) isNetConnected -> {
            if (!isNetConnected)
                return ABSENT;
            return getComment(newsId, false);
        });
        mLiveObservableData_long = Transformations.switchMap(NetUtils.netConnected(application), (Function<Boolean, LiveData<ZHCommend>>) isNetConnected -> {
            if (!isNetConnected)
                return ABSENT;
            return getComment(newsId, true);
        });
    }

    private MutableLiveData<ZHCommend> getComment(int newsId, boolean isLongComment) {
        /**
         * 在 Activity 或是 Fragment 中改变 LiveData的数据值使用MutableLiveData
         * 在ViewModel中使用MutableLiveData，然后ViewModel仅向Observer公开不可变的LiveData对象
         * 若有多个数据源，需要有多个MutableLiveData回调给view层
         */
        MutableLiveData<ZHCommend> applyData = new MutableLiveData<>();
        Observable<ZHCommend> newsObservable;
        newsObservable = isLongComment ?  APiRepository.getLongNewsCommentRepository(newsId):  APiRepository.getShortNewsCommentRepository(newsId);
        newsObservable.subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ZHCommend>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZHCommend zhNewsListData) {
                        applyData.postValue(zhNewsListData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return applyData;
    }


    public LiveData<ZHCommend> getmLiveObservableData_short() {
        return mLiveObservableData_short;
    }

    public LiveData<ZHCommend> getmLiveObservableData_long() {
        return mLiveObservableData_long;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
        init();
    }
}
