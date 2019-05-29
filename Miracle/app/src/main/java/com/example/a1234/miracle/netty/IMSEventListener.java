package com.example.a1234.miracle.netty;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alibaba.fastjson.JSONObject;
import com.example.a1234.miracle.MyApplication;
import com.example.a1234.miracle.utils.LogUtils;
import com.example.baselib.netty.im.bean.Head;
import com.example.baselib.netty.im.bean.MsgBean;
import com.example.baselib.netty.im.linstener.IOnEventListener;

import java.util.UUID;

import io.netty.channel.ChannelOutboundBuffer;

/**
 * 与服务器交互的listener
 */
public class IMSEventListener implements IOnEventListener {
    public IMSEventListener() {

    }

    /**
     * 接收ims转发过来的消息
     *
     * @param msg
     */
    @Override
    public void dispatchMsg(MsgBean msg) {
        LogUtils.d("接收ims转发过来的消息: "+msg);
       /* MessageProcessor.getInstance().receiveMsg(MessageBuilder.getMessageByProtobuf(msg));*/
    }

    /**
     * 网络是否可用
     *
     * @return
     */
    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 设置ims重连间隔时长，0表示默认使用ims的值
     *
     * @return
     */
    @Override
    public int getReconnectInterval() {
        return 0;
    }

    /**
     * 设置ims连接超时时长，0表示默认使用ims的值
     *
     * @return
     */
    @Override
    public int getConnectTimeout() {
        return 0;
    }

    /**
     * 设置应用在前台时ims心跳间隔时长，0表示默认使用ims的值
     *
     * @return
     */
    @Override
    public int getForegroundHeartbeatInterval() {
        return 0;
    }

    /**
     * 设置应用在后台时ims心跳间隔时长，0表示默认使用ims的值
     *
     * @return
     */
    @Override
    public int getBackgroundHeartbeatInterval() {
        return 0;
    }

    /**
     * 构建握手消息
     *
     * @return
     */
    @Override
    public MsgBean getHandshakeMsg() {
        MsgBean msgBean = new MsgBean();
        Head head = new Head();
        head.setMsgId(UUID.randomUUID().toString());
        head.setMsgType(MessageType.HANDSHAKE.getMsgType());
        head.setTimestamp(System.currentTimeMillis());
        msgBean.setHead(head);
        return msgBean;
    }

    /**
     * 构建心跳消息
     *
     * @return
     */
    @Override
    public MsgBean getHeartbeatMsg() {
        MsgBean msgBean = new MsgBean();
        Head head = new Head();
        head.setMsgId(UUID.randomUUID().toString());
        head.setMsgType(MessageType.HEARTBEAT.getMsgType());
        head.setTimestamp(System.currentTimeMillis());
        msgBean.setHead(head);

        return msgBean;
    }

    /**
     * 服务端返回的消息发送状态报告消息类型
     *
     * @return
     */
    @Override
    public int getServerSentReportMsgType() {
        return MessageType.SERVER_MSG_SENT_STATUS_REPORT.getMsgType();
    }

    /**
     * 客户端提交的消息接收状态报告消息类型
     *
     * @return
     */
    @Override
    public int getClientReceivedReportMsgType() {
        return MessageType.CLIENT_MSG_RECEIVED_STATUS_REPORT.getMsgType();
    }

    /**
     * 设置ims消息发送超时重发次数，0表示默认使用ims的值
     *
     * @return
     */
    @Override
    public int getResendCount() {
        return 0;
    }

    /**
     * 设置ims消息发送超时重发间隔时长，0表示默认使用ims的值
     *
     * @return
     */
    @Override
    public int getResendInterval() {
        return 0;
    }
}
