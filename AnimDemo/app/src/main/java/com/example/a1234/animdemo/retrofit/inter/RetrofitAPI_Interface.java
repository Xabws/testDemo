package com.example.a1234.animdemo.retrofit.inter;

import com.example.a1234.animdemo.data.ZHContent;
import com.example.a1234.animdemo.data.ZHNewsExtra;
import com.example.a1234.animdemo.data.ZHNewsListData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by a1234 on 2018/9/12.
 */

public interface RetrofitAPI_Interface {
    @GET("news/latest")//GET注解的字符串 "news/latest"会追加到BaseUrl
    Call<ZHNewsListData> getLatest();
    // @GET注解的作用:采用Get方法发送网络请求

    // getCall() = 接收网络请求数据的方法
    // 其中返回类型为Call<*>，*是接收数据的类（Reception）
    // 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>
    @GET
    Call<ZHContent> getNewsDetail(@Url String url);
    @GET
    Call<ZHNewsExtra> getNewsExtra(@Url String url);
    @GET
    Call<ZHNewsListData> getOldNews(@Url String url);
}
