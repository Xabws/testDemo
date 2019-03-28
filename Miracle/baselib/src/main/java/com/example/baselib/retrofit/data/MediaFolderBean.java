package com.example.baselib.retrofit.data;

import java.util.ArrayList;
import java.util.List;

/**
 * author: wsBai
 * date: 2019/2/18
 */
public class MediaFolderBean {
    private String FloderName;
    private String FloderUrl;
    private List<MediaBean> folderContent;

    public MediaFolderBean(String floderName, String floderUrl, List<MediaBean> folderContent) {
        FloderName = floderName;
        FloderUrl = floderUrl;
        this.folderContent = folderContent;
    }

    public String getFloderName() {
        return FloderName;
    }

    public void setFloderName(String floderName) {
        FloderName = floderName;
    }

    public String getFloderUrl() {
        return FloderUrl;
    }

    public void setFloderUrl(String floderUrl) {
        FloderUrl = floderUrl;
    }

    public List<MediaBean> getFolderContent() {
        return folderContent;
    }

    public void setFolderContent(ArrayList<MediaBean> folderContent) {
        this.folderContent = folderContent;
    }

    @Override
    public String
    toString() {
        return "MediaFolderBean{" +
                "FloderName='" + FloderName + '\'' +
                ", FloderUrl='" + FloderUrl + '\'' +
                ", folderContent=" + folderContent +
                '}';
    }
}
