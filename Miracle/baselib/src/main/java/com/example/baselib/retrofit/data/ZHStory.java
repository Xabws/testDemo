package com.example.baselib.retrofit.data;

import java.util.ArrayList;

/**
 * Created by a1234 on 2018/9/10.
 * [{
 "images": ["https:\/\/pic3.zhimg.com\/v2-bc8e09a58987836ad042ce1b44c9551e.jpg"],
 "type": 0,
 "id": 9695360,
 "ga_prefix": "091013",
 "title": "特别版瞎扯 · 老师放他以前上课的视频给我们上课"
 }{
 "image": "https:\/\/pic3.zhimg.com\/v2-cb4dd26154a8e31137a88bfe024c9a72.jpg",
 "type": 0,
 "id": 9695099,
 "ga_prefix": "090909",
 "title": "坐拥 9000 辆汽车、1000 列火车、40 架飞机……不够，还不够"
 }]
 */

public class ZHStory {
    ArrayList<String> images;
    int type;
    int id;
    String ga_prefix;
    String title;

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ZHStory{" +
                "images=" + images +
                ", type=" + type +
                ", id='" + id + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
