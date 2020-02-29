package com.example.baselib.retrofit.data;

/**
 * @author wsbai
 * @date 2019/3/29
 */
public class ImageBean {
    private String name;
    private String url;
    private boolean isfloder = false;


    public ImageBean(String name, String url,boolean isfloder) {
        this.name = name;
        this.url = url;
        this.isfloder = isfloder;
    }

    public boolean isIsfloder() {
        return isfloder;
    }

    public void setIsfloder(boolean isfloder) {
        this.isfloder = isfloder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name;
    }
}
