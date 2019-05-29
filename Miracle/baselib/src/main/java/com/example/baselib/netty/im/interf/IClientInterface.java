package com.example.baselib.netty.im.interf;

import com.example.baselib.netty.im.MsgDispatcher;
import com.example.baselib.netty.im.MsgTimeoutTimerManager;
import com.example.baselib.netty.im.bean.MsgBean;
import com.example.baselib.netty.im.linstener.IMConnectStatusCallback;
import com.example.baselib.netty.im.linstener.IOnEventListener;

import java.util.Vector;

/**
 * @author wsbai
 * @date 2019-05-27
 */
public interface IClientInterface {
    /**
     * 初始化
     *
     * @param serverUrl 服务器地址
     * @param listener      与应用层交互的listener
     * @param callback  ims连接状态回调
     */
    void init(String serverUrl, IOnEventListener listener, IMConnectStatusCallback callback);

    /**
     * 重置连接，也就是重连
     * 首次连接也可认为是重连
     */
    void resetConnect();

    /**
     * 重置连接，也就是重连
     * 首次连接也可认为是重连
     * 重载
     *
     * @param isFirst 是否首次连接
     */
    void resetConnect(boolean isFirst);

    /**
     * 关闭连接，同时释放资源
     */
    void close();

    /**
     * 标识ims是否已关闭
     *
     * @return
     */
    boolean isClosed();

    /**
     * 发送消息
     *
     * @param msg
     */
    void sendMsg(MsgBean msg);

    /**
     * 发送消息
     * 重载
     *
     * @param msg
     * @param isJoinTimeoutManager 是否加入发送超时管理器
     */
    void sendMsg(MsgBean msg, boolean isJoinTimeoutManager);

    /**
     * 获取重连间隔时长
     *
     * @return
     */
    int getReconnectInterval();

    /**
     * 获取连接超时时长
     *
     * @return
     */
    int getConnectTimeout();

    /**
     * 获取应用在前台时心跳间隔时间
     *
     * @return
     */
    int getForegroundHeartbeatInterval();

    /**
     * 获取应用在后台时心跳间隔时间
     *
     * @return
     */
    int getBackgroundHeartbeatInterval();

    /**
     * 设置app前后台状态
     *
     * @param appStatus
     */
    void setAppStatus(int appStatus);

    /**
     * 获取由应用层构造的握手消息
     *
     * @return
     */
    MsgBean getHandshakeMsg();

    /**
     * 获取由应用层构造的心跳消息
     *
     * @return
     */
    MsgBean getHeartbeatMsg();

    /**
     * 获取应用层消息发送状态报告消息类型
     *
     * @return
     */
    int getServerSentReportMsgType();

    /**
     * 获取应用层消息接收状态报告消息类型
     *
     * @return
     */
    int getClientReceivedReportMsgType();

    /**
     * 获取应用层消息发送超时重发次数
     *
     * @return
     */
    int getResendCount();

    /**
     * 获取应用层消息发送超时重发间隔
     *
     * @return
     */
    int getResendInterval();

    /**
     * 获取消息转发器
     *
     * @return
     */
    MsgDispatcher getMsgDispatcher();

    /**
     * 获取消息发送超时定时器
     *
     * @return
     */
    MsgTimeoutTimerManager getMsgTimeoutTimerManager();
}
