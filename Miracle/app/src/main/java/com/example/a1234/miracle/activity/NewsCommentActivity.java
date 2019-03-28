package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.view.View;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.adapter.NewsCommentAdapter;
import com.example.a1234.miracle.customview.FullyLinearLayoutManager2;
import com.example.a1234.miracle.databinding.ActivityCommentBinding;
import com.example.a1234.miracle.eventbus.MessageEvent;
import com.example.a1234.miracle.utils.ToastUtil;
import com.example.a1234.miracle.viewmodel.NewsCommentViewModel;
import com.example.baselib.retrofit.data.ZHCommendData;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class NewsCommentActivity extends BaseActivity {
    private ActivityCommentBinding activityCommentBinding;
    private ArrayList<ZHCommendData> longcommend;
    private ArrayList<ZHCommendData> shortcommend;
    private NewsCommentAdapter commendLongAdapter;
    private NewsCommentAdapter commendShortAdapter;
    private int totalComment = 0;
    private NewsCommentViewModel viewModel;

    @Override
    public int getContentViewId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityCommentBinding = DataBindingUtil.setContentView(this, getContentViewId());
    }

    @Override
    public void Event(MessageEvent messageEvent) {
        initData();
        viewModel.setNewsId(messageEvent.getMessage());
        subscribeToModel(viewModel);
    }

    private void initData() {
        viewModel = ViewModelProviders.of(NewsCommentActivity.this).get(NewsCommentViewModel.class);
        activityCommentBinding.rvShort.setVisibility(View.GONE);
        if (commendLongAdapter == null) {
            commendLongAdapter = new NewsCommentAdapter(this, (binding, zhCommendData, i) -> {
                ToastUtil.toast("位置："+i+" 内容"+zhCommendData);
            });
            activityCommentBinding.setRecyclerAdapterLong(commendLongAdapter);
            activityCommentBinding.rvLong.setLayoutManager(new FullyLinearLayoutManager2(this));
        }
        if (commendShortAdapter == null) {
            commendShortAdapter = new NewsCommentAdapter(this, (binding, zhCommendData, i) -> {
                ToastUtil.toast("位置："+i+" 内容"+zhCommendData);
            });
            activityCommentBinding.rvShort.setLayoutManager(new FullyLinearLayoutManager2(this));
            activityCommentBinding.setRecyclerAdapterShort(commendShortAdapter);
        }
        activityCommentBinding.ivDown.setOnClickListener(view -> activityCommentBinding.rvShort.setVisibility(activityCommentBinding.rvShort.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        activityCommentBinding.ivBack.setOnClickListener(view -> finish());
    }

    /**
     * 订阅数据变化来刷新UI
     *
     * @param model
     */
    private void subscribeToModel(final NewsCommentViewModel model) {
        model.getmLiveObservableData_long().observe(this, zhCommend -> {
            longcommend = zhCommend.getComments();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(longcommend.size());
            stringBuffer.append("条长评");
            activityCommentBinding.tvLong.setText(stringBuffer.toString());
            commendLongAdapter.setDataList(zhCommend.getComments());
            commendLongAdapter.notifyDataSetChanged();
            totalComment += longcommend.size();
            activityCommentBinding.tvCommentAll.setText(totalComment + "条点评");
        });
        model.getmLiveObservableData_short().observe(this, zhCommend -> {
            shortcommend = zhCommend.getComments();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(shortcommend.size());
            stringBuffer.append("条短评");
            activityCommentBinding.tvShort.setText(stringBuffer.toString());
            commendShortAdapter.setDataList(zhCommend.getComments());
            commendShortAdapter.notifyDataSetChanged();
            totalComment += shortcommend.size();
            activityCommentBinding.tvCommentAll.setText(totalComment + "条点评");
        });
    }
}
