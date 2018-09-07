package com.example.a1234.animdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a1234.animdemo.R;
import com.example.a1234.animdemo.component.DaggerUserComponent;
import com.example.a1234.animdemo.data.User;
import com.example.a1234.animdemo.module.UserModule;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by a1234 on 2018/8/16.
 */

public class Demo3Activity extends Activity {
    @BindView(R.id.get)
    TextView get;
    @BindView(R.id.post)
    TextView post;
    @BindView(R.id.postfile)
    TextView postfile;
    @BindView(R.id.postform)
    TextView postform;
    private String TAG = "Demo3Activity";
    @Named("name")
    @Inject
    User user;
    @Named("context")
    @Inject
    User user2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        ButterKnife.bind(this);
        DaggerUserComponent.builder().userModule(new UserModule(this, "timmy", "8")).build().inject(this);
        user.Toast();
        onViewClicked(get);
        onViewClicked(post);
        onViewClicked(postfile);
        onViewClicked(postform);
    }

    /**
     * 1.1. 异步GET请求
     * <p>
     * -new OkHttpClient;
     * -构造Request对象；
     * -通过前两步中的对象构建Call对象；
     * -通过Call#enqueue(Callback)方法来提交异步请求；
     */
    private void getParamAsync() {
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    /**
     * 这种方式与前面的区别就是在构造Request对象时，需要多构造一个RequestBody对象，用它来携带我们要提交的数据。
     * 在构造 RequestBody 需要指定MediaType，用于描述请求/响应 body 的内容类型
     */
    private void postParamAsync() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";
        Request request = new Request.Builder().url("https://api.github.com/markdown/raw").post(RequestBody.create(mediaType, requestBody)).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    private void postForm() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("search", "Jurassic Park").build();
        Request request = new Request.Builder().url("https://en.wikipedia.org/w/index.php").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    private void postFile() {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File("test.md");
        Request request = new Request.Builder().url("https://api.github.com/markdown/raw").post(RequestBody.create(mediaType, file)).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    @OnClick({R.id.get, R.id.post, R.id.postfile, R.id.postform})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get:
                getParamAsync();
                break;
            case R.id.post:
                postParamAsync();
                break;
            case R.id.postfile:
                postFile();
                break;
            case R.id.postform:
                postForm();
                break;
        }
    }
}
