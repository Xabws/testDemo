package com.example.a1234.miracle.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1234.miracle.API;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.customview.ResizableImageView;
import com.example.a1234.miracle.data.ZHContent;
import com.example.a1234.miracle.data.ZHNewsExtra;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.retrofit.inter.RetrofitAPI_Interface;
import com.example.a1234.miracle.utils.HtmlUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by a1234 on 2018/9/10.
 */
public class NewsDetailActivity extends BaseActivity {
    @BindView(R.id.wv_content)
    WebView wvContent;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.cl_like)
    ConstraintLayout clLike;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.cl_comment)
    ConstraintLayout clComment;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.cl_title)
    ConstraintLayout clTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.story_title)
    ConstraintLayout storyTitle;
    @BindView(R.id.iv_cover)
    ResizableImageView ivCover;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    private String NewsId;
    private ZHContent zhContent;
    private ZHNewsExtra zhNewsExtra;
    private int beforeScrollY = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void Event(MessageEvent messageEvent) {
        NewsId = messageEvent.getMessage();
        initData();
    }

    private void initData() {
        RetrofitAPI_Interface retrofitAPI_interface = retrofit.create(RetrofitAPI_Interface.class);
        io.reactivex.Observable<ZHContent> observable = retrofitAPI_interface.getNewsDetail(NewsId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZHContent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZHContent zhContent) {
                        NewsDetailActivity.this.zhContent = zhContent;
                        tvTitle.setText(zhContent.getTitle());
                        tvSource.setText(zhContent.getImage_source());
                        Picasso.get().load(zhContent.getImage()).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                if (ivCover.getWidth() != 0 && ivCover.getHeight() != 0) {
                                    float ratio = (float) ivCover.getWidth() / (float) ivCover.getHeight();
                                    ivCover.setBackground(new BitmapDrawable(cropBitmap(bitmap, ratio)));

                                }
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
                        String htmlData = HtmlUtil.createHtmlData(zhContent.getBody(), zhContent.getCss(), zhContent.getJs());
                        addWebView(htmlData);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(NewsDetailActivity.this, "error", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        io.reactivex.Observable<ZHNewsExtra> extra_observable = retrofitAPI_interface.getNewsExtra(NewsId);
        extra_observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZHNewsExtra>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZHNewsExtra zhNewsExtra) {
                        zhNewsExtra = zhNewsExtra;
                        if (zhNewsExtra != null) {
                            tvComment.setText(zhNewsExtra.getComments() + "");
                            tvLike.setText(zhNewsExtra.getPopularity() + "");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        WebSettings webSettings = wvContent.getSettings();//获取webview设置属性
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js
        wvContent.setWebViewClient(new MyWebViewClient());
        wvContent.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner");//这个是给图片设置点击监听的，如果你项目需要webview中图片，点击查看大图功能，可以这么添加
        // webSettings.setBuiltInZoomControls(true); // 显示放大缩小
        // webSettings.setSupportZoom(true); // 可以缩放
        clTitle.bringToFront();
        scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                beforeScrollY = oldScrollY;
                if (oldScrollY > 0) {
                    if (scrollY < (storyTitle.getHeight())) {
                        float degree = 1 - (float) scrollY / (float) storyTitle.getHeight();
                        clTitle.setAlpha(degree);
                        clTitle.bringToFront();
                        if (clTitle.getVisibility() == View.INVISIBLE)
                            clTitle.setVisibility(View.VISIBLE);
                    } else {
                        if (clTitle.getVisibility() == View.VISIBLE)
                            clTitle.setVisibility(View.INVISIBLE);
                    }
                }
                Log.d("sssss", scrollY + " " + oldScrollY + " " + storyTitle.getHeight());
            }
        });
    }

    private void addWebView(String html) {
        /**
         *
         data : 指定需要加载的HTML 代码.
         mimeType :指定HTML 代码的 MIME类型.对于HTML代码可指定为text/html .
         encoding :指定HTML 代码的代码编码所用的字符集.比如指定 UTF-8.
         */
        wvContent.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.iv_back, R.id.cl_like, R.id.cl_comment, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.cl_like:
                break;
            case R.id.cl_comment:
                startActivity(new Intent(this, NewsCommentActivity.class));
                break;
            case R.id.iv_share:
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();//重置webview中img标签的图片大小
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        /**
         * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
         **/
        private void imgReset() {
            if (wvContent != null) {
                wvContent.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName('img'); " +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "var img = objs[i];   " +
                        "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                        "}" +
                        "})()");
            }

        }

        private void addImageClickListner() {
            // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
            if (wvContent != null) {
                wvContent.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName(\"img\"); " +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "    objs[i].onclick=function()  " +
                        "    {  "
                        + "        window.imagelistner.openImage(this.src);  " +
                        "    }  " +
                        "}" +
                        "})()");
            }
        }
    }

    public static class JavaScriptInterface {

        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
            Log.i("TAG", "响应点击事件!");
           /* Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, BigImageActivity.class);//BigImageActivity查看大图的类，自己定义就好
            context.startActivity(intent);*/
        }
    }

    /**
     * @param bitmap
     * @param ratio:width/height
     * @return
     */
    private Bitmap cropBitmap(Bitmap bitmap, float ratio) {
        int height;
        height = (int) (bitmap.getWidth() / ratio);
        return Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() - height, bitmap.getWidth(), height);
    }

}
