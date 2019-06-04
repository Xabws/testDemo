package com.example.baselib.retrofit.repository;

import com.example.baselib.arch.datamodel.ApiClient;
import com.example.baselib.retrofit.data.ZHCommend;
import com.example.baselib.retrofit.data.ZHContent;
import com.example.baselib.retrofit.data.ZHNewsExtra;
import com.example.baselib.retrofit.data.ZHNewsListData;

import io.reactivex.Observable;

/**
 * @author wsbai
 * @date 2019/3/26
 */
public class APiRepository {
    public static Observable<ZHNewsListData> getLatestRepository() {
        Observable<ZHNewsListData> observableForGetFuliDataFromNetWork = ApiClient.getApiService().getLatest();
        //可以操作Observable来筛选网络或者是本地数据
        return observableForGetFuliDataFromNetWork;
    }

    public static Observable<ZHContent> getNewsDetailRepository(int NewsId) {
        Observable<ZHContent> observableForGetAndroidDataFromNetWork = ApiClient.getApiService().getNewsDetail(NewsId);
        //可以操作Observable来筛选网络或者是本地数据
        return observableForGetAndroidDataFromNetWork;
    }

    public static Observable<ZHNewsExtra> getNewsExtraRepository(int NewsId) {
        Observable<ZHNewsExtra> observableForGetAndroidDataFromNetWork = ApiClient.getApiService().getNewsExtra(NewsId);
        //可以操作Observable来筛选网络或者是本地数据
        return observableForGetAndroidDataFromNetWork;
    }

    public static Observable<ZHNewsListData> getOldNewsRepository(long date) {
        Observable<ZHNewsListData> observableForGetAndroidDataFromNetWork = ApiClient.getApiService().getOldNews(date);
        //可以操作Observable来筛选网络或者是本地数据
        return observableForGetAndroidDataFromNetWork;
    }


    public static Observable<ZHCommend> getLongNewsCommentRepository(int NewsId) {
        Observable<ZHCommend> observableForGetAndroidDataFromNetWork = ApiClient.getApiService().getLongNewsComment(NewsId);
        //可以操作Observable来筛选网络或者是本地数据
        return observableForGetAndroidDataFromNetWork;
    }

    public static Observable<ZHCommend> getShortNewsCommentRepository(int NewsId) {
        Observable<ZHCommend> observableForGetAndroidDataFromNetWork = ApiClient.getApiService().getShortNewsComment(NewsId);
        //可以操作Observable来筛选网络或者是本地数据
        return observableForGetAndroidDataFromNetWork;
    }

}
