package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.databinding.DataBindingUtil;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.AcitivityWebviewBinding;
import com.example.a1234.miracle.utils.CrawlNews;
import com.example.a1234.miracle.utils.CreateHtmlJsoup;
import com.example.a1234.miracle.utils.LogUtils;

/**
 * @author wsbai
 * @date 2019-05-22
 */
public class WebViewActivity extends BaseActivity {
    private AcitivityWebviewBinding acitivityWebviewBinding;

    @Override
    public int getContentViewId() {
        return R.layout.acitivity_webview;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        new Thread(new Runnable() {
            @Override
            public void run() {
               String folder =  CreateHtmlJsoup.makeHeatMapHtml(WebViewActivity.this,"https://ibaotu.com/sucai/18059170.html");
                LogUtils.d(folder);
            }
        }).start();
        acitivityWebviewBinding = DataBindingUtil.setContentView(this, getContentViewId());
        //声明WebSettings子类
        WebSettings webSettings = acitivityWebviewBinding.webView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

//支持插件
        //webSettings.setPluginsEnabled(true);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        acitivityWebviewBinding.webView.loadUrl("https://www.baidu.com");
      /*  String saves = Environment.getExternalStorageDirectory()+"/sssss";
        acitivityWebviewBinding.webView.saveWebArchive(saves);*/
    }
}
