package com.example.baselib.netty.im.linstener;

/**
 * @author wsbai
 * @date 2019-05-27
 */
public interface IMConnectStatusCallback {
    /**
     * ims连接中
     */
    void onConnecting();

    /**
     * ims连接成功
     */
    void onConnected();

    /**
     * ims连接失败
     */
    void onConnectFailed();
}
