package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.AcitivityWeatherBinding;
import com.example.a1234.miracle.viewmodel.WeatherViewModel;
import com.example.baselib.arch.viewmodel.BaseViewModel;
import com.example.baselib.retrofit.API;
import com.example.baselib.retrofit.data.WeatherBean;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author wsbai
 * @date 2019/3/25
 */
public class WeatherActivity extends BaseActivity {
    private AcitivityWeatherBinding acitivityWeatherBinding;

    @Override
    public int getContentViewId() {
        return R.layout.acitivity_weather;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        acitivityWeatherBinding = DataBindingUtil.setContentView(this, getContentViewId());
        WeatherViewModel weatherViewModel = ViewModelProviders.of(WeatherActivity.this).get(WeatherViewModel.class);
        weatherViewModel.loadData(API.DETAILS);
        subscribeToModel(weatherViewModel);
    }

    /**
     * 订阅数据变化来刷新UI
     *
     * @param model
     */
    private void subscribeToModel(final BaseViewModel model) {
        //观察数据变化来刷新UI
        model.getLiveObservableData().observe(this, new Observer<WeatherBean>() {
            @Override
            public void onChanged(WeatherBean weatherBean) {
                Log.i("danxx", "subscribeToModel onChanged onChanged");
                model.setUiObservableData(weatherBean);
                // girlsAdapter.setGirlsList(girlsData.getResults());
            }
        });
    }
}
