package com.example.baselib.retrofit.service;

import com.example.baselib.arch.datamodel.ApiClient;
import com.example.baselib.retrofit.util.JsonUtil;
import com.example.baselib.retrofit.util.SwitchSchedulers;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;


/**
 * 动态url数据获取
 */
public class DynamicDataRepository {

    public static <T> Observable getDynamicData(String pullUrl, final Class<T> clazz) {

        return ApiClient.getDynamicDataService()
                .getDynamicData(pullUrl)
                .compose(SwitchSchedulers.applySchedulers())
                .map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(ResponseBody responseBody) throws Exception {
                        return JsonUtil.Str2JsonBean(responseBody.string(), clazz);
                    }
                });
    }

}