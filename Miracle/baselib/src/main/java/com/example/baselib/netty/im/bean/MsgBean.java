package com.example.baselib.netty.im.bean;

/**
 * @author wsbai
 * @date 2019-05-28
 * <p>
 * message Msg {
 * Head head = 1;// 消息头
 * string body = 2;// 消息体
 * }
 * <p>
 * message Head {
 * string msgId = 1;// 消息id
 * int32 msgType = 2;// 消息类型
 * int32 msgContentType = 3;// 消息内容类型
 * string fromId = 4;// 消息发送者id
 * string toId = 5;// 消息接收者id
 * int64 timestamp = 6;// 消息时间戳
 * int32 statusReport = 7;// 状态报告
 * string extend = 8;// 扩展字段，以key/value形式存放的json
 */
public class MsgBean {
  private  Head head;
   private  String body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MsgBean{" +
                "head=" + head +
                ", body='" + body + '\'' +
                '}';
    }
}
