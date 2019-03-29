package com.example.a1234.miracle.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.CommentListLayoutBinding;
import com.example.a1234.miracle.utils.DateFormatUtil;
import com.example.baselib.retrofit.data.ZHCommendData;

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
        try {
            String time = null;
            time = DateFormatUtil.transForDate2(Integer.parseInt(zhCommendData.getTime()));
            zhCommendData.setTime(time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        viewHolder.bindView.setZhcommendData(zhCommendData);
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
        /**
         * Android P http网络请求的问题
         * Google表示，为保证用户数据和设备的安全，针对下一代 Android 系统(Android P) 的应用程序，将要求默认使用加密连接，这意味着 Android P 将禁止 App 使用所有未加密的连接，
         * 因此运行 Android P 系统的安卓设备无论是接收或者发送流量，未来都不能明码传输，需要使用下一代(Transport Layer Security)传输层安全协议，而 Android Nougat 和 Oreo 则不受影响。
         */
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("https:");
        stringBuffer.append(url.substring(5));
        String newurl = stringBuffer.toString();
        Log.e("TAG", url + "   -添加https " + newurl);
        Glide.with(imageView.getContext()).load(newurl).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop())).placeholder(R.mipmap.ic_launcher).into(imageView);
    }

}
