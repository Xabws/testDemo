package com.example.a1234.animdemo.data;

/**
 * Created by a1234 on 2018/9/11.
 *  {
 long_comments : 长评论总数
 popularity : 点赞总数
 short_comments : 短评论总数
 comments : 评论总数
 }

 */

public class ZHNewsExtra {
    int long_comments;
    int popularity;
    int short_comments;
    int comments;

    public int getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ZHNewsExtra{" +
                "long_comments=" + long_comments +
                ", popularity=" + popularity +
                ", short_comments=" + short_comments +
                ", comments=" + comments +
                '}';
    }
}
