package com.example.baselib.netty.im.netty;

import com.example.baselib.netty.im.IMSConfig;
import com.example.baselib.netty.im.bean.Head;
import com.example.baselib.netty.im.bean.MsgBean;


import java.util.UUID;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;

/**
 * 消息接收处理handler
 */
public class TCPReadHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient imsClient;

    public TCPReadHandler(NettyTcpClient imsClient) {
        this.imsClient = imsClient;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.err.println("TCPReadHandler channelInactive()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }

        // 触发重连
        imsClient.resetConnect(false);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.err.println("TCPReadHandler exceptionCaught()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }

        // 触发重连
        imsClient.resetConnect(false);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MsgBean message = (MsgBean) msg;
        if (message == null || message.getHead() == null) {
            return;
        }

        int msgType = message.getHead().getMsgType();
        if (msgType == imsClient.getServerSentReportMsgType()) {
            int statusReport = message.getHead().getStatusReport();
            System.out.println(String.format("服务端状态报告：「%d」, 1代表成功，0代表失败", statusReport));
            if (statusReport == IMSConfig.DEFAULT_REPORT_SERVER_SEND_MSG_SUCCESSFUL) {
                System.out.println("收到服务端消息发送状态报告，message=" + message + "，从超时管理器移除");
                imsClient.getMsgTimeoutTimerManager().remove(message.getHead().getMsgId());
            }
        } else {
            // 其它消息
            // 收到消息后，立马给服务端回一条消息接收状态报告
            System.out.println("收到消息，message=" + message);
            MsgBean receivedReportMsg = buildReceivedReportMsg(message.getHead().getMsgId());
            if (receivedReportMsg != null) {
                imsClient.sendMsg(receivedReportMsg);
            }
        }

        // 接收消息，由消息转发器转发到应用层
        imsClient.getMsgDispatcher().receivedMsg(message);
    }

    /**
     * 构建客户端消息接收状态报告
     *
     * @param msgId
     * @return
     */
    private MsgBean buildReceivedReportMsg(String msgId) {
        if (StringUtil.isNullOrEmpty(msgId)) {
            return null;
        }
        MsgBean msgBean = new MsgBean();
        Head head = new Head();
        head.setMsgId(UUID.randomUUID().toString());
        head.setMsgType(imsClient.getClientReceivedReportMsgType());
        head.setTimestamp(System.currentTimeMillis());

        msgBean.setHead(head);
        return msgBean;
    }
}
