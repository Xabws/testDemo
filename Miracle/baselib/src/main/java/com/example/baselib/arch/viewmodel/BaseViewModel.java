package com.example.baselib.arch.viewmodel;

import android.app.Application;
import android.util.Log;

import com.example.baselib.retrofit.service.DynamicDataRepository;
import com.example.baselib.retrofit.util.SwitchSchedulers;

import java.lang.reflect.ParameterizedType;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author wsbai
 * @date 2019/3/25
 * 只做业务逻辑操作，不持有任何UI控件的引用。基础ViewModel，可自动取消Module的调用,可获取Application实例的引用
 */
public class BaseViewModel<T> extends AndroidViewModel {
    //生命周期观察的数据
    // 1.MutableLiveData的父类是LiveData
    //2.LiveData在实体类里可以通知指定某个字段的数据更新.
    //3.MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段）
    protected MutableLiveData<T> liveObservableData = new MutableLiveData<>();
    //UI使用可观察的数据 ObservableField是一个包装类
    public ObservableField<T> uiObservableData = new ObservableField<>();
    //解除订阅
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    //LiveData（没有数据时返回absent）
    protected static final MutableLiveData ABSENT = new MutableLiveData();

    static {
        //noinspection unchecked
        ABSENT.setValue(null);
    }


    public BaseViewModel(@NonNull Application application) {
        super(application);

    }

    /**
     * 动态加载数据
     *
     * @param fullUrl
     */
    public void loadData(String fullUrl) {
        DynamicDataRepository.getDynamicData(fullUrl, getTClass())
                .compose(SwitchSchedulers.applySchedulers())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(T value) {
                        Log.d("loadData", value.toString());
                        if (null != value) {
                            liveObservableData.setValue(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * LiveData支持了lifecycle生命周期检测
     *
     * @return
     */
    public LiveData<T> getLiveObservableData() {
        return liveObservableData;
    }

    /**
     * 当主动改变数据时重新设置被观察的数据
     *
     * @param product
     */
    public void setUiObservableData(T product) {
        this.uiObservableData.set(product);
    }

    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}
