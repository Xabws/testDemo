package com.example.baselib.retrofit.data;

import java.util.ArrayList;

/**
 * Created by a1234 on 2018/9/20.
 */

public class ZHCommend {
    private ArrayList<ZHCommendData> comments;

    public ArrayList<ZHCommendData> getComments() {
        return comments;
    }

    public void setComments(ArrayList<ZHCommendData> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ZHCommend{" +
                "comments=" + comments +
                '}';
    }
}
