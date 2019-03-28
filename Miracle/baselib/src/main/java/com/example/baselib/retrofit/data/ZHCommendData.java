package com.example.baselib.retrofit.data;

/**
 * Created by a1234 on 2018/9/20.
 "comments": [{
 "author": "June芳",
 "content": "坐月子的时候，妈妈和婆婆同在一屋檐下，那时候才真正体会真实上演的宫斗剧。",
 "avatar": "https:\/\/pic4.zhimg.com\/4452be317_s.jpg",
 "time": 1537456073,
 "id": 32313328,
 "likes": 0
 }, {
 */

public class ZHCommendData {
    private String avatar;
    private String content;
    private String author;
    private String time;
    private String id;
    private String likes;
    private ZHCommendReply zhCommendReply;


    private class ZHCommendReply {
        private String content;
        private int status;
        private String id;
        private long author;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getAuthor() {
            return author;
        }

        public void setAuthor(long author) {
            this.author = author;
        }

        @Override
        public String toString() {
            return "ZHCommendReply{" +
                    "content='" + content + '\'' +
                    ", status=" + status +
                    ", id='" + id + '\'' +
                    ", author=" + author +
                    '}';
        }
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public ZHCommendReply getZhCommendReply() {
        return zhCommendReply;
    }

    public void setZhCommendReply(ZHCommendReply zhCommendReply) {
        this.zhCommendReply = zhCommendReply;
    }

    @Override
    public String toString() {
        return "ZHCommendData{" +
                "avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                ", likes=" + likes +
                ", zhCommendReply=" + zhCommendReply +
                '}';
    }
}


