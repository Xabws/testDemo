package com.example.a1234.miracle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.ActivityNewsDetail2Binding;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.viewmodel.NewsDetailViewModel;
import com.example.baselib.retrofit.data.ZHNewsDetail;
import com.example.baselib.utils.HtmlUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 该页面的头布局由html代码生成
 * Created by a1234 on 2018/9/10.
 */
public class NewsDetailActivity2 extends BaseActivity implements View.OnClickListener {
    private ActivityNewsDetail2Binding activityNewsDetailBinding;
    private int beforeScrollY = 0;
    private NewsDetailViewModel viewModel;

    @Override
    public int getContentViewId() {
        return R.layout.activity_news_detail2;
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
        viewModel = ViewModelProviders.of(NewsDetailActivity2.this).get(NewsDetailViewModel.class);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityNewsDetailBinding = DataBindingUtil.setContentView(this, getContentViewId());
        activityNewsDetailBinding.wvContent.getSettings().setJavaScriptEnabled(true);
        activityNewsDetailBinding.clTitle.bringToFront();
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
                String htmlData  = HtmlUtil.structHtml(zhCommend.getZhContent());
                addWebView(htmlData);
                if (zhCommend.getZhNewsExtra() != null) {
                    activityNewsDetailBinding.tvComment.setText(zhCommend.getZhNewsExtra().getComments() + "");
                    activityNewsDetailBinding.tvLike.setText(zhCommend.getZhNewsExtra().getPopularity() + "");
                }

            }
        });
    }

}
