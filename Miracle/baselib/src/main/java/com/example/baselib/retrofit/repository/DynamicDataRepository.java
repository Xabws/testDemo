package com.example.baselib.retrofit.repository;

import com.example.baselib.arch.datamodel.ApiClient;
import com.example.baselib.retrofit.util.JsonUtil;
import com.example.baselib.retrofit.util.SwitchSchedulers;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * 根据url获取动态对象（根据json转换对象）
 * @author wsbai
 * @date 2019/3/26
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
