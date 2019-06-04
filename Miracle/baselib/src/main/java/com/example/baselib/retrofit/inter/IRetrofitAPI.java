package com.example.baselib.retrofit.inter;

import com.example.baselib.retrofit.API;
import com.example.baselib.retrofit.data.ZHCommend;
import com.example.baselib.retrofit.data.ZHContent;
import com.example.baselib.retrofit.data.ZHNewsExtra;
import com.example.baselib.retrofit.data.ZHNewsListData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by a1234 on 2018/9/12.
 */

public interface IRetrofitAPI {
    @GET(API.LATEST)
        //GET注解的字符串 "news/latest"会追加到BaseUrl
        // Call<ZHNewsListData> getLatest();
    Observable<ZHNewsListData> getLatest();
    // @GET注解的作用:采用Get方法发送网络请求

    // getCall() = 接收网络请求数据的方法
    // 其中返回类型为Call<*>，*是接收数据的类（Reception）
    // 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>
    @GET(API.DETAILS)
    Observable<ZHContent> getNewsDetail(@Path("newsId") int NewsId);

    @GET(API.NEWS_EXTRA)
    Observable<ZHNewsExtra> getNewsExtra(@Path("newsId") int NewsId);

    @GET(API.NEWS_BEFORE)
    Observable<ZHNewsListData> getOldNews(@Path("date") long date);

    @GET(API.NEWS_COMMENT_LONG)
    Observable<ZHCommend> getLongNewsComment(@Path("newsId") int newsId);

    @GET(API.NEWS_COMMENT_SHORT)
    Observable<ZHCommend> getShortNewsComment(@Path("newsId") int newsId);
}
