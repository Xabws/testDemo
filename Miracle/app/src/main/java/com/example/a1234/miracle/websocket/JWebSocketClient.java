package com.example.a1234.miracle.websocket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author wsbai
 * @date 2019-09-12
 */
public class JWebSocketClient extends WebSocketClient {
    //构造方法中的new Draft_6455()代表使用的协议版本，这里可以不写或者写成这样即可
    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.e("JWebSocketClient", "onOpen()");
    }

    @Override
    public void onMessage(String message) {
        Log.e("JWebSocketClient", "onMessage()");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("JWebSocketClient", "onClose() "+code+" "+reason+" "+remote);
    }

    @Override
    public void onError(Exception ex) {
        Log.e("JWebSocketClient", "onError()");
    }
}