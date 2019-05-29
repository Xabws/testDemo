package com.example.baselib.netty.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.baselib.netty.im.bean.MsgBean;
import com.example.baselib.netty.im.netty.NettyTcpClient;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wsbai
 * @date 2019-05-28
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient imsClient;

    public LoginAuthRespHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MsgBean handshakeRespMsg = (MsgBean) msg;
        if (handshakeRespMsg == null || handshakeRespMsg.getHead() == null) {
            return;
        }

        MsgBean handshakeMsg = imsClient.getHandshakeMsg();
        if (handshakeMsg == null || handshakeMsg.getHead() == null) {
            return;
        }

        int handshakeMsgType = handshakeMsg.getHead().getMsgType();
        if (handshakeMsgType == handshakeRespMsg.getHead().getMsgType()) {
            System.out.println("收到服务端握手响应消息，message=" + handshakeRespMsg);
            int status = -1;
            try {
                JSONObject jsonObj = JSON.parseObject(handshakeRespMsg.getHead().getExtend());
                status = jsonObj.getIntValue("status");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (status == 1) {
                    // 握手成功，马上先发送一条心跳消息，至于心跳机制管理，交由HeartbeatHandler
                   MsgBean heartbeatMsg = imsClient.getHeartbeatMsg();
                    if (heartbeatMsg == null) {
                        return;
                    }

                    // 握手成功，检查消息发送超时管理器里是否有发送超时的消息，如果有，则全部重发
                    imsClient.getMsgTimeoutTimerManager().onResetConnected();

                    System.out.println("发送心跳消息：" + heartbeatMsg + "当前心跳间隔为：" + imsClient.getHeartbeatInterval() + "ms\n");
                    imsClient.sendMsg(heartbeatMsg);

                    // 添加心跳消息管理handler
                    imsClient.addHeartbeatHandler();
                } else {
                    imsClient.resetConnect(false);// 握手失败，触发重连
                }
            }
        } else {
            // 消息透传
            ctx.fireChannelRead(msg);
        }
    }
}

