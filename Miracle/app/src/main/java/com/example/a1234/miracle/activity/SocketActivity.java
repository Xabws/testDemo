package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.AcitivityNettyBinding;
import com.example.a1234.miracle.websocket.JWebSocketClient;
import com.example.baselib.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import io.socket.emitter.Emitter;

/**
 * @author wsbai
 * @date 2019-05-28
 */
public class SocketActivity extends BaseActivity {
    private AcitivityNettyBinding acitivityNettyBinding;
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
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        acitivityNettyBinding = DataBindingUtil.setContentView(this, getContentViewId());
        acitivityNettyBinding.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册到socket服务器
                URI uri = URI.create("wss://echo.websocket.org");
                JWebSocketClient client = new JWebSocketClient(uri){
                    @Override
                    public void onMessage(String message) {
                        super.onMessage(message);
                        Log.d("ws::", message);
                    }
                };
                try {
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    }
}
