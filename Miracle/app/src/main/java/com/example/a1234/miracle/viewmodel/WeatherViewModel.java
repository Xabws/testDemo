package com.example.a1234.miracle.viewmodel;

import android.app.Application;

import com.example.baselib.arch.viewmodel.BaseViewModel;
import com.example.baselib.retrofit.data.WeatherBean;

/**
 * @author wsbai
 * @date 2019/3/25
 */
public class WeatherViewModel extends BaseViewModel<WeatherBean> {

    public WeatherViewModel(Application application) {
        super(application);
    }
}
