package com.example.a1234.animdemo.data;

import java.util.ArrayList;

/**
 * Created by a1234 on 2018/9/10.
 * {
 "date": "20180910",
 "stories": [{
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
 }
 */
public class ZHNewsListData {
    String date;
    ArrayList<ZHStory>stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZHStory> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZHStory> stories) {
        this.stories = stories;
    }

    @Override
    public String toString() {
        return "ZHNewsListData{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                '}';
    }
}
