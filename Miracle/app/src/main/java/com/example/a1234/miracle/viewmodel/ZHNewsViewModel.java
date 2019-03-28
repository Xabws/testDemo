package com.example.a1234.miracle.viewmodel;

import android.app.Application;
import android.util.Log;

import com.example.baselib.arch.viewmodel.BaseViewModel;
import com.example.baselib.retrofit.data.ZHNewsListData;
import com.example.baselib.retrofit.data.ZHStory;
import com.example.baselib.retrofit.repository.APiRepository;
import com.example.baselib.retrofit.util.NetUtils;

import java.util.ArrayList;

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
 * @date 2019/3/25
 */
public class ZHNewsViewModel extends BaseViewModel<ZHNewsListData> {
    public static final int DATECURRENT = -1;
    private long currentDate;
    private ArrayList<ZHStory>newsList;
    /**
     * 主界面list的livedata的观察者对象
     */
    private LiveData<ZHNewsListData> mLiveObservableData;
    /**
     * 在 Activity 或是 Fragment 中改变 LiveData的数据值使用MutableLiveData
     * 在ViewModel中使用MutableLiveData，然后ViewModel仅向Observer公开不可变的LiveData对象
     */
    MutableLiveData<ZHNewsListData> applyData = new MutableLiveData<>();

    public ZHNewsViewModel(@NonNull Application application) {
        super(application);
        /**
         * Transformations.switchMap:
         * 在LiveData将变化的数据通知给观察者前，改变数据的类型；或者是返回一个不一样的LiveData。
         */
        mLiveObservableData = Transformations.switchMap(NetUtils.netConnected(application), new Function<Boolean, LiveData<ZHNewsListData>>() {
            @Override
            public LiveData<ZHNewsListData> apply(Boolean isNetConnected) {
                if (!isNetConnected)
                    return ABSENT;
                return updateList(DATECURRENT);
            }
        });
    }


    public MutableLiveData<ZHNewsListData> updateList(long date) {
        Observable<ZHNewsListData> newsObservable;
        /**
         * 在RxJava 中，Scheduler ——调度器，相当于线程控制器，RxJava 通过它来指定每一段代码应该运行在什么样的线程。RxJava 已经内置了几个 Scheduler ，它们已经适合大多数的使用场景：
         Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
         Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
         Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
         Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
         另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
         */
        if (date != DATECURRENT) {
            newsObservable = APiRepository.getOldNewsRepository(date);
        } else {
            newsObservable = APiRepository.getLatestRepository();//获取观察者对象
        }
        newsObservable.subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<ZHNewsListData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZHNewsListData zhNewsListData) {
                        if (date != DATECURRENT) {
                            if (newsList!=null){
                                newsList.addAll(zhNewsListData.getStories());
                            }
                            zhNewsListData.setStories(newsList);
                            applyData.setValue(zhNewsListData);
                        } else {
                            /**
                             * 获取最新的信息
                             */
                            currentDate = Long.parseLong(zhNewsListData.getDate());
                            newsList = new ArrayList<>();
                            newsList.addAll(zhNewsListData.getStories());
                            zhNewsListData.setStories(newsList);
                            applyData.postValue(zhNewsListData);
                        }
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

    /**
     * LiveData支持了lifecycle生命周期检测
     *
     * @return
     */
    @Override
    public LiveData<ZHNewsListData> getLiveObservableData() {
        return mLiveObservableData;
    }

    public ArrayList<ZHStory> getNewsList() {
        return newsList;
    }

    public long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(long currentDate) {
        this.currentDate = currentDate;
    }
}
