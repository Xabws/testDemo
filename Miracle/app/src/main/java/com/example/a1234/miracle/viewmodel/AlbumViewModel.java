package com.example.a1234.miracle.viewmodel;

import android.app.Application;
import android.util.Log;

import com.example.a1234.miracle.MyApplication;
import com.example.baselib.arch.viewmodel.BaseViewModel;
import com.example.baselib.retrofit.data.ImageBean;
import com.example.baselib.retrofit.data.MediaBean;
import com.example.baselib.retrofit.data.MediaFolderBean;
import com.example.baselib.utils.AlbumController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author wsbai
 * @date 2019/3/29
 */
public class AlbumViewModel extends BaseViewModel<List<ImageBean>> {
    public static final int ALBUMTYPE_ALL_PICS = 0;
    public static final int ALBUMTYPE_ALL_FOLDERS = 1;

    private MutableLiveData<List<ImageBean>> imagelist_livedata;
    private MutableLiveData<ImageBean> photo_livedata;
    private ArrayList<MediaFolderBean> folderlist;
    private AlbumController albumController;
    private int currentType = ALBUMTYPE_ALL_FOLDERS;
    private int folderPosition = -1, picPosition = -1;

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        getPics();
    }

    private void getPics() {
        imagelist_livedata = new MutableLiveData<>();
        photo_livedata = new MutableLiveData<>();
        albumController = new AlbumController(new AlbumController.MediaCallback() {
            @Override
            public void onAllMedias(HashMap<String, List<MediaBean>> allPhotos) {
                //key:文件夹，value：文件夹内容
                folderlist = new ArrayList<>();
                for (Map.Entry<String, List<MediaBean>> entry : allPhotos.entrySet()) {
                    String FolerName = entry.getKey().substring(entry.getKey().lastIndexOf("/") + 1);
                    MediaFolderBean mediaFolderBean = new MediaFolderBean(FolerName, entry.getKey(), entry.getValue());
                    folderlist.add(mediaFolderBean);
                }
                ArrayList<ImageBean> list = new ArrayList<>();
                for (int i = 0; i < folderlist.size(); i++) {
                    ImageBean imageBean = new ImageBean(folderlist.get(i).getFloderName(), folderlist.get(i).getFolderContent().get(0).getPath(),true);
                    list.add(imageBean);
                }
                imagelist_livedata.postValue(list);
            }

            @Override
            public void onAllMediasWithoutFolder(List<MediaBean> allPhotos) {
                ArrayList<ImageBean> list = new ArrayList<>();
                for (int i = 0; i < allPhotos.size(); i++) {
                    ImageBean imageBean = new ImageBean(allPhotos.get(i).getDisplayname(), allPhotos.get(i).getPath(),false);
                    list.add(imageBean);
                }
                imagelist_livedata.postValue(list);
            }
        });
        // albumController.getAllMediaInfosWithoutFolder(MyApplication.getContext());
        albumController.getAllMediaInfos(MyApplication.getContext());
    }

    public int getCurrentType() {
        return currentType;
    }

    /**
     * 获取相册封面图片列表
     */
    public void getFolderList() {
        albumController.getAllMediaInfos(MyApplication.getContext());

    }

    /**
     * 获取所有图片列表
     */
    public void getAllPics() {
        albumController.getAllMediaInfosWithoutFolder(MyApplication.getContext());
    }

    /**
     * 获取相册文件夹内列表
     *
     */
    public void getPicList(String name) {
        for (int i=0;i<folderlist.size();i++){
            if (folderlist.get(i).getFloderUrl().contains(name)){
                this.folderPosition = i;
            }
        }
        ArrayList<ImageBean> list = new ArrayList<>();
        for (int i = 0; i < folderlist.get(folderPosition).getFolderContent().size(); i++) {
            ImageBean imageBean = new ImageBean(folderlist.get(folderPosition).getFolderContent().get(i).getDisplayname(), folderlist.get(folderPosition).getFolderContent().get(i).getPath(),false);
            list.add(imageBean);
        }
        imagelist_livedata.postValue(list);
    }

    /**
     * 获取文件夹内图片
     *
     * @param position
     */
    public void getPic(int position) {
        this.picPosition = position;
        ImageBean imageBean = new ImageBean(folderlist.get(folderPosition).getFolderContent().get(position).getDisplayname(), folderlist.get(folderPosition).getFolderContent().get(position).getPath(),false);
        photo_livedata.postValue(imageBean);

    }

    @Override
    public LiveData<List<ImageBean>> getLiveObservableData() {
        return imagelist_livedata;
    }

    public MutableLiveData<ImageBean> getPhoto_livedata() {
        return photo_livedata;
    }
}
