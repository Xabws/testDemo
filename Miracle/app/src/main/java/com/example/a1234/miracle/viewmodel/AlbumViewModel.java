package com.example.a1234.miracle.viewmodel;

import android.app.Application;

import com.example.a1234.miracle.utils.AlbumController;
import com.example.baselib.arch.viewmodel.BaseViewModel;
import com.example.baselib.retrofit.data.ImageBean;
import com.example.baselib.retrofit.data.MediaBean;
import com.example.baselib.retrofit.data.MediaFolderBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * @author wsbai
 * @date 2019/3/29
 */
public class AlbumViewModel extends BaseViewModel<ArrayList<ImageBean>> {
    private MutableLiveData<ArrayList<ImageBean>> imagelist_livedata;
    private ArrayList<MediaFolderBean> folderlist;

    public AlbumViewModel(@NonNull Application application) {
        super(application);
        getPics();
    }

    private void getPics() {
        imagelist_livedata = new MutableLiveData<>();
        AlbumController albumController = new AlbumController(allPhotos -> {
            //key:文件夹，value：文件夹内容
            folderlist = new ArrayList<>();
            for (Map.Entry<String, List<MediaBean>> entry : allPhotos.entrySet()) {
                String FolerName = entry.getKey().substring(entry.getKey().lastIndexOf("/") + 1);
                MediaFolderBean mediaFolderBean = new MediaFolderBean(FolerName, entry.getKey(), entry.getValue());
                folderlist.add(mediaFolderBean);
            }
            getFolderList();
        });
        albumController.getAllMediaInfos();
    }

    public void getFolderList() {
        ArrayList<ImageBean> list = new ArrayList<>();
        for (int i = 0; i < folderlist.size(); i++) {
            ImageBean imageBean = new ImageBean(folderlist.get(i).getFloderName(), folderlist.get(i).getFolderContent().get(0).getPath());
            list.add(imageBean);
        }
        imagelist_livedata.postValue(list);
    }

    public void getPicList(int position) {
        ArrayList<ImageBean> list = new ArrayList<>();
        for (int i = 0; i < folderlist.size(); i++) {
            ImageBean imageBean = new ImageBean(folderlist.get(position).getFolderContent().get(i).getDisplayname(), folderlist.get(position).getFolderContent().get(i).getPath());
            list.add(imageBean);
        }
        imagelist_livedata.postValue(list);
    }

    @Override
    public LiveData<ArrayList<ImageBean>> getLiveObservableData() {
        return imagelist_livedata;
    }
}
