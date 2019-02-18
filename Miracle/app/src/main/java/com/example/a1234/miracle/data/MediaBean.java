package com.example.a1234.miracle.data;

import android.provider.MediaStore;

/**
 * author: wsBai
 * date: 2019/2/18
 */
public class MediaBean {
    private int mType;
    private String path;
    private String thumbPath;
    private long duration;
    private long size;
    private String displayname;

    public MediaBean(int mType, String path, String thumbPath, long duration, long size, String displayname) {
        this.mType = mType;
        this.path = path;
        this.thumbPath = thumbPath;
        this.duration = duration;
        this.size = size;
        this.displayname = displayname;
    }

    public MediaBean(int mType, String path, long size, String displayname) {
        this.mType = mType;
        this.path = path;
        this.thumbPath = thumbPath;
        this.size = size;
        this.displayname = displayname;
    }

    public static class Type {
        public static int Image = 0;
        public static int Video = 1;

    }

    @Override
    public String toString() {
        return "MediaBean{" +
                "mType=" + mType +
                ", path='" + path + '\'' +
                ", thumbPath='" + thumbPath + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", displayname='" + displayname + '\'' +
                '}';
    }
}
