package com.example.baselib.netty.im;

import com.example.baselib.netty.im.bean.MsgBean;
import com.example.baselib.netty.im.linstener.IOnEventListener;

/**
 * 消息转发器，负责将接收到的消息转发到应用层
 */
public class MsgDispatcher {

    private IOnEventListener mOnEventListener;

    public MsgDispatcher() {

    }

    public void setOnEventListener(IOnEventListener listener) {
        this.mOnEventListener = listener;
    }

    /**
     * 接收消息，并通过OnEventListener转发消息到应用层
     * @param msg
     */
    public void receivedMsg(MsgBean msg) {
        if(mOnEventListener == null) {
            return;
        }

        mOnEventListener.dispatchMsg(msg);
    }
}