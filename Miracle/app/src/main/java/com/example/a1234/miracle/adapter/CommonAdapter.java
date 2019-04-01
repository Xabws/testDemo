package com.example.a1234.miracle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 通过databinding绑定的通用adapter
 *
 * @param <DB>xml布局文件绑定的ViewDataBinding
 * @param <T>数据bean
 */
public abstract class CommonAdapter<DB extends ViewDataBinding, T> extends RecyclerView.Adapter {
    /**
     * Item点击回调
     */
    protected IAdapterClickInterface adapterClickInterface;
    protected ArrayList<T> dataList;

    private Context context;

    public ArrayList<T> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public CommonAdapter(Context context, IAdapterClickInterface<DB, T> adapterClickInterface) {
        this.context = context;
        this.adapterClickInterface = adapterClickInterface;
    }


    public CommonAdapter(Context context, ArrayList<T> dataList, IAdapterClickInterface<DB, T> adapterClickInterface) {
        this.dataList = dataList;
        this.context = context;
        this.adapterClickInterface = adapterClickInterface;
    }

    // 抽象函数  获取布局资源id
    public abstract int getLayoutId();

    // 抽象函数  通过databinding为布局设置数据
    public abstract void bindView(CommonViewHolder viewHolder, T t, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 生成DB对象 (这个方法是不是和View.inflate()很像？)
        DB bindView = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), parent, false);
        return new CommonViewHolder(bindView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T t = dataList.get(position);
        // 调用抽象函数，将holder强转为CommonViewHodler，供子类Adapter使用其成员对象bindView；
        bindView((CommonViewHolder) holder, t, position);
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class CommonViewHolder extends RecyclerView.ViewHolder {

        public DB bindView;
        // 每一个item都必须持有的一个ViewDataBinding子类对象

        public CommonViewHolder(DB bindView) {
            super(bindView.getRoot());
            this.bindView = bindView;
        }
    }
}
