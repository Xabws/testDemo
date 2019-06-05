package com.example.a1234.miracle.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.ActivityNewsDetailBinding;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.utils.HtmlUtil;
import com.example.a1234.miracle.viewmodel.NewsDetailViewModel;
import com.example.baselib.retrofit.data.ZHNewsDetail;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

/**
 * 该页面的头布局由Android原生控件生成
 * Created by a1234 on 2018/9/10.
 */
public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {
    private ActivityNewsDetailBinding activityNewsDetailBinding;
    private int beforeScrollY = 0;
    private NewsDetailViewModel viewModel;

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
        initData();
        viewModel.init(messageEvent.getMessage());
        subscribeToModel(viewModel);
    }

    private void initData() {
        viewModel = ViewModelProviders.of(NewsDetailActivity.this).get(NewsDetailViewModel.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityNewsDetailBinding = DataBindingUtil.setContentView(this, getContentViewId());
       /* WebSettings webSettings = activityNewsDetailBinding.wvContent.getSettings();//获取webview设置属性
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js
        activityNewsDetailBinding.wvContent.setWebViewClient(new MyWebViewClient());
        activityNewsDetailBinding.wvContent.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner");//这个是给图片设置点击监听的，如果你项目需要webview中图片，点击查看大图功能，可以这么添加
        // webSettings.setBuiltInZoomControls(true); // 显示放大缩小
        // webSettings.setSupportZoom(true); // 可以缩放
        activityNewsDetailBinding.wvContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        activityNewsDetailBinding.wvContent.setVerticalScrollBarEnabled(false);
        activityNewsDetailBinding.wvContent.setHorizontalScrollBarEnabled(false);
        activityNewsDetailBinding.wvContent.setHorizontalScrollbarOverlay(false);*/
        WebSettings webSettings = activityNewsDetailBinding.wvContent.getSettings();
        webSettings.setBlockNetworkImage(false);//不阻塞网络图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //允许混合（http，https） //websettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            webSettings.setJavaScriptEnabled(true);
        }
        activityNewsDetailBinding.wvContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//接受所有网站的证书
            }
        });
        activityNewsDetailBinding.clTitle.bringToFront();
        activityNewsDetailBinding.scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                beforeScrollY = oldScrollY;
                if (oldScrollY > 0) {
                    if (scrollY < (activityNewsDetailBinding.storyTitle.getHeight())) {
                        float degree = 1 - (float) scrollY / (float) activityNewsDetailBinding.storyTitle.getHeight();
                        activityNewsDetailBinding.clTitle.setAlpha(degree);
                        activityNewsDetailBinding.clTitle.bringToFront();
                        if (activityNewsDetailBinding.clTitle.getVisibility() == View.INVISIBLE)
                            activityNewsDetailBinding.clTitle.setVisibility(View.VISIBLE);
                    } else {
                        if (activityNewsDetailBinding.clTitle.getVisibility() == View.VISIBLE)
                            activityNewsDetailBinding.clTitle.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        activityNewsDetailBinding.ivBack.setOnClickListener(this);
        activityNewsDetailBinding.clLike.setOnClickListener(this);
        activityNewsDetailBinding.clComment.setOnClickListener(this);
        activityNewsDetailBinding.ivShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.cl_like:
                break;
            case R.id.cl_comment:
                EventBus.getDefault().postSticky(new MessageEvent(viewModel.getNewsId()));
                startActivity(new Intent(this, NewsCommentActivity.class));
                break;
            case R.id.iv_share:
                break;
        }
    }

    private void addWebView(String html) {
        /**
         *
         data : 指定需要加载的HTML 代码.
         mimeType :指定HTML 代码的 MIME类型.对于HTML代码可指定为text/html .
         encoding :指定HTML 代码的代码编码所用的字符集.比如指定 UTF-8.
         */
        activityNewsDetailBinding.wvContent.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            imgReset();//重置webview中img标签的图片大小
            // html加载完成之后，添加监听图片的点击js函数
//            addImageClickListner();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //  view.loadUrl(url);
            return true;
        }


        /**
         * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
         **/
        private void imgReset() {
            if (activityNewsDetailBinding.wvContent != null) {
                activityNewsDetailBinding.wvContent.loadUrl("javascript:(function(){" +
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
            if (activityNewsDetailBinding.wvContent != null) {
                activityNewsDetailBinding.wvContent.loadUrl("javascript:(function(){" +
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
            Log.i("TAG", "响应点击事件!" + img);
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, BigImageAcitivity.class);//BigImageActivity查看大图的类，自己定义就好
            context.startActivity(intent);
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

    /**
     * 订阅数据变化来刷新UI
     *
     * @param model
     */
    private void subscribeToModel(final NewsDetailViewModel model) {
        model.getLiveObservableData().observe(this, new androidx.lifecycle.Observer<ZHNewsDetail>() {
            @Override
            public void onChanged(ZHNewsDetail zhCommend) {
                if (zhCommend == null || zhCommend.getZhNewsExtra() == null || zhCommend.getZhContent() == null)
                    return;
                activityNewsDetailBinding.tvTitle.setText(zhCommend.getZhContent().getTitle());
                activityNewsDetailBinding.tvSource.setText(zhCommend.getZhContent().getImage_source());
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("https");
                stringBuffer.append(zhCommend.getZhContent().getImage().substring(5));
                String newurl = stringBuffer.toString();
                Picasso.get().load(newurl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        if (activityNewsDetailBinding.ivCover.getWidth() != 0 && activityNewsDetailBinding.ivCover.getHeight() != 0) {
                            float ratio = (float) activityNewsDetailBinding.ivCover.getWidth() / (float) activityNewsDetailBinding.ivCover.getHeight();
                            activityNewsDetailBinding.ivCover.setBackground(new BitmapDrawable(cropBitmap(bitmap, ratio)));
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
                String html = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"ZhiHuCSS.css\"/></head><body>" + zhCommend.getZhContent().getBody() + "</body></html>";
                activityNewsDetailBinding.wvContent.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null);
//                String htmlData  = HtmlUtil.structHtml(zhCommend.getZhContent());
//                addWebView(htmlData);
                if (zhCommend.getZhNewsExtra() != null) {
                    activityNewsDetailBinding.tvComment.setText(zhCommend.getZhNewsExtra().getComments() + "");
                    activityNewsDetailBinding.tvLike.setText(zhCommend.getZhNewsExtra().getPopularity() + "");
                }

            }
        });
    }

}
