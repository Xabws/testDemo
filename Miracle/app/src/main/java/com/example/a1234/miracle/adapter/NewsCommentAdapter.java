package com.example.a1234.miracle.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.CommentListLayoutBinding;
import com.example.baselib.retrofit.data.ZHCommendData;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.databinding.BindingAdapter;

/**
 * @author wsbai
 * @date 2019/3/28
 */
public class NewsCommentAdapter extends CommonAdapter<CommentListLayoutBinding, ZHCommendData> {

    public NewsCommentAdapter(Context context, List<ZHCommendData> dataList, IAdapterClickInterface<CommentListLayoutBinding, ZHCommendData> adapterClickInterface) {
        super(context, dataList, adapterClickInterface);
    }

    public NewsCommentAdapter(Context context, IAdapterClickInterface<CommentListLayoutBinding, ZHCommendData> adapterClickInterface) {
        super(context, adapterClickInterface);
    }

    @Override
    public int getLayoutId() {
        return R.layout.comment_list_layout;
    }

    @Override
    public void bindView(CommonViewHolder viewHolder, ZHCommendData zhCommendData, int position) {
        // 将数据加载进databinding绑定的xml中
        viewHolder.bindView.setZhcommendData(dataList.get(position));
        viewHolder.bindView.rlMain.setOnClickListener(v -> adapterClickInterface.onItemClick(viewHolder.bindView, zhCommendData, position));
    }

    /**
     * 绑定xml中的imageview(因为需要用到Glide单独绑定)
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"avatarUrl"})
    public static void loadimage(ImageView imageView, String url) {
        Log.e("TAG", url + "   -走到这里了 "+imageView.getId());
        Glide.with(imageView.getContext()).load(url).centerCrop().placeholder(R.mipmap.ic_launcher).into(imageView);
    }

}
