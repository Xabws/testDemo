package com.example.baselib.retrofit.data;

/**
 * @author wsbai
 * @date 2019/3/27
 */
public class ZHNewsDetail {
    private ZHContent zhContent;
    private ZHNewsExtra zhNewsExtra;

    public ZHContent getZhContent() {
        return zhContent;
    }

    public void setZhContent(ZHContent zhContent) {
        this.zhContent = zhContent;
    }

    public ZHNewsExtra getZhNewsExtra() {
        return zhNewsExtra;
    }

    public void setZhNewsExtra(ZHNewsExtra zhNewsExtra) {
        this.zhNewsExtra = zhNewsExtra;
    }
}
