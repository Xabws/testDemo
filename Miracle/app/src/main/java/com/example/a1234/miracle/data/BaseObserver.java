package com.example.a1234.miracle.data;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer {
    public abstract void onSuccess(Object o);

    public abstract void onFailed(Throwable e);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        onSuccess(o);
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    @Override
    public void onComplete() {

    }
}
