package com.example.a1234.animdemo.retrofit.inter;

import com.example.a1234.animdemo.data.ZHContent;
import com.example.a1234.animdemo.data.ZHNewsExtra;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by a1234 on 2018/9/11.
 */

public interface GetNewsExtra_Inter {
    @GET
    Call<ZHNewsExtra> getLatest(@Url String url);
}
