package com.example.a1234.miracle.adapter;

import android.content.Context;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a1234.miracle.R;
import com.example.a1234.miracle.data.MediaFolderBean;
import com.example.a1234.miracle.utils.DeviceUtil;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: wsBai
 * date: 2019/2/18
 */
public class AlbumAdapter extends RecyclerView.Adapter {
    public static final int TYPE_FOLDER = 0;//文件夹目录
    public static final int TYPE_PHOTO = 1;//图片目录
    private Context context;
    private ArrayList<MediaFolderBean> ablumList;
    private ArrayList<String> currentList;
    private OnItemClickListener onItemClickListener;
    private int currentPosition = 0;
    private int currentType = TYPE_FOLDER;

    public AlbumAdapter(Context context, ArrayList ablumList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.ablumList = ablumList;
        this.onItemClickListener = onItemClickListener;
        currentList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(context).inflate(R.layout.album_layout
                , parent, false);
        viewHolder = new AlbumAdapter.DrawerHolder(view);
        return viewHolder;
    }

    public void setType(int Type) {
        currentList.clear();
        currentType = Type;
        switch (Type) {
            case TYPE_FOLDER:
                for (int i = 0; i < ablumList.size(); i++) {
                    currentList.add(ablumList.get(i).getFolderContent().get(0).getPath());
                }
                notifyDataSetChanged();
                break;
            case TYPE_PHOTO:
                for (int i = 0; i < ablumList.get(currentPosition).getFolderContent().size(); i++) {
                    currentList.add(ablumList.get(currentPosition).getFolderContent().get(i).getPath());
                }
                break;
        }
        notifyDataSetChanged();
    }

    public int getCurrentType() {
        return currentType;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public ArrayList<String> getCurrentList() {
        return currentList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DrawerHolder bannerViewHolder = (DrawerHolder) holder;
        Size s = DeviceUtil.getScreeWH(context);
        bannerViewHolder.textView.setVisibility(currentType == TYPE_FOLDER ? View.VISIBLE : View.GONE);
        /*ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) bannerViewHolder.image.getLayoutParams();
        lp.width = s.getWidth() / 3;
        lp.height = s.getWidth() / 3;*/

        bannerViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentType == TYPE_FOLDER) {
                    currentPosition = position;
                    setType(TYPE_PHOTO);
                } else {
                    onItemClickListener.onItemClick(position,currentList.get(position));
                }

            }
        });
        if (bannerViewHolder.textView.getVisibility() == View.VISIBLE)
            bannerViewHolder.textView.setText(ablumList.get(position).getFloderName());

        Glide.with(context).load(currentList.get(position)).override(s.getWidth() / 3, s.getWidth() / 3).into(bannerViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return currentList != null ? currentList.size() : 0;
    }

    static class DrawerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.folder_name)
        TextView textView;

        public DrawerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @OnClick({R.id.image})
    public void onViewClicked() {

    }

    public void setList(ArrayList<MediaFolderBean> ablumList) {
        this.ablumList = ablumList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position,String url);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
