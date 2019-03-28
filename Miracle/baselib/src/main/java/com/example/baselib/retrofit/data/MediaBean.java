package com.example.baselib.retrofit.data;

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

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
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
