package com.example.baselib.retrofit.data;

import java.util.ArrayList;

/**
 * Created by a1234 on 2018/9/10.
 * {
 * {
 * "body": "能性： < strong > 整个线粒体不会处处都是 5<script type=“text\/javascript”>window.daily=true<\/script>",
 * "image_source": "新发现 \/ 知乎",
 * "title": "人的体温才 37°C ？我在人身体里工作，经常冲到 50°C",
 * "image": "https:\/\/pic2.zhimg.com\/v2-7f09e3dee2635b00b1773692f1493eb9.jpg",
 * "share_url": "http:\/\/daily.zhihu.com\/story\/9695222",
 * "js": [],
 * "ga_prefix": "091016",
 * "images": ["https:\/\/pic3.zhimg.com\/v2-4dd31906a34bfb906e23f07c613abf9a.jpg"],
 * "type": 0,
 * "id": 9695222,
 * "css": ["http:\/\/news-at.zhihu.com\/css\/news_qa.auto.css?v=4b3e3"]
 * }
 */

public class ZHContent {
    String body;
    String image_source;
    String title;
    String image;
   String share_url;
    ArrayList<String> js;
    String ga_prefix;
    ArrayList<String> images;
    int type;
    String id;
    ArrayList<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public ArrayList<String> getJs() {
        return js;
    }

    public void setJs(ArrayList<String> js) {
        this.js = js;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getCss() {
        return css;
    }

    public void setCss(ArrayList<String> css) {
        this.css = css;
    }

    @Override
    public String toString() {
        return "ZHContent{" +
                "body='" + body + '\'' +
                ", image_source='" + image_source + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", share_url='" + share_url + '\'' +
                ", js=" + js +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", images=" + images +
                ", type=" + type +
                ", id='" + id + '\'' +
                ", css=" + css +
                '}';
    }
}
