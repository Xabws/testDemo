package com.example.a1234.miracle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.AcitivityNettyBinding;
import com.example.a1234.miracle.netty.IMSClientBootstrap;
import com.example.a1234.miracle.utils.LogUtils;
import com.example.baselib.netty.im.IMSConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @author wsbai
 * @date 2019-05-28
 */
public class SocketActivity extends BaseActivity {
    private AcitivityNettyBinding acitivityNettyBinding;
    private IMSClientBootstrap imsClientBootstrap;
    private Socket mSocket;
    public static final String CHAT_SERVER_URL = "https://socket-io-chat.now.sh/";
    private String TAG = "SocketActivity";
    private Boolean isConnected = true;

    @Override
    public int getContentViewId() {
        return R.layout.acitivity_netty;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSocket();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        acitivityNettyBinding = DataBindingUtil.setContentView(this, getContentViewId());
        acitivityNettyBinding.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册到socket服务器
                mSocket.emit("add user", System.currentTimeMillis());
              /*  imsClientBootstrap = IMSClientBootstrap.getInstance();
                imsClientBootstrap.init("192.168.2.171", IMSConfig.APP_STATUS_FOREGROUND);*/
            }
        });
        acitivityNettyBinding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = acitivityNettyBinding.etInput.getText().toString();
                if (text.isEmpty()) {
                    return;
                }
                acitivityNettyBinding.etInput.setText("");
                mSocket.emit("new message", text);
            }
        });
       /* acitivityNettyBinding.etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mSocket.connected()) return;


                mSocket.emit("typing");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

    }

    protected void initSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = IO.socket(CHAT_SERVER_URL);
                } catch (URISyntaxException e) {
                    LogUtils.d(e.toString());
                    throw new RuntimeException(e);
                }
                mSocket.on(Socket.EVENT_CONNECT, onConnect);
                mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
                mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
                mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
                mSocket.on("new message", onNewMessage);
                mSocket.on("user joined", onUserJoined);
                mSocket.on("user left", onUserLeft);
                mSocket.on("typing", onTyping);
                mSocket.on("stop typing", onStopTyping);
                mSocket.connect();
            }
        }).start();
    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            int numUsers;
            try {
                numUsers = data.getInt("numUsers");
            } catch (JSONException e) {
                return;
            }
        }
    };

    /**
     * 连接成功：第一次握手
     */
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isConnected) {
                        LogUtils.d("连接成功");
//                            mSocket.emit("hand shake", System.currentTimeMillis());
                        Toast.makeText(getApplicationContext(),
                                "连接成功", Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    /**
     * 断开连接
     */
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "diconnected");
                    isConnected = false;
                    Toast.makeText(getApplicationContext(),
                            "断开连接", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    /**
     * 连接失败
     */
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "error :" + args.toString());
                    Toast.makeText(getApplicationContext(),
                            "连接错误", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    /**
     * 获取到新消息
     */
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    acitivityNettyBinding.tv.setText(data.toString());
                }
            });
        }
    };

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("new message", onNewMessage);
      /*  mSocket.off("user joined", onUserJoined);
        mSocket.off("user left", onUserLeft);
        mSocket.off("typing", onTyping);
        mSocket.off("stop typing", onStopTyping);*/
    }
}
