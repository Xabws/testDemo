package com.example.a1234.animdemo.retrofit.inter;

import com.example.a1234.animdemo.data.ZHContent;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by a1234 on 2018/9/10.
 */

public interface GetNewsDetail_Inter {
    @GET
    Call<ZHContent> getLatest(@Url String url);
}
