package com.example.a1234.miracle.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.a1234.miracle.R;
import com.example.a1234.miracle.databinding.AcitivityDesignmodeBinding;
import com.example.a1234.miracle.observermodetest.ObserverTest;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author wsbai
 * @date 2019-06-15
 */
public class DesignModeActivity extends BaseActivity {
    private AcitivityDesignmodeBinding acitivityDesignmodeBinding;
    private MqttAndroidClient mClient;
    @Override
    public int getContentViewId() {
        return R.layout.acitivity_designmode;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        acitivityDesignmodeBinding  = DataBindingUtil.setContentView(this,getContentViewId());
        acitivityDesignmodeBinding.observer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObserverTest observerTest = new ObserverTest();
                observerTest.start();
            }
        });

    }

    private void initClient() {
        //配置连接
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName("1111");
        options.setPassword("Zjitd123456.".toCharArray());
        options.setAutomaticReconnect(true);
        //配置客户端离线或者断开连接的选项
        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
        disconnectedBufferOptions.setBufferEnabled(true);
        disconnectedBufferOptions.setBufferSize(5000);
        disconnectedBufferOptions.setDeleteOldestMessages(true);
        disconnectedBufferOptions.setPersistBuffer(true);

        mClient.setBufferOpts(disconnectedBufferOptions);
        String serverURI = "tcp://192.0.108.1:1883";
        String clientId = "your client id";
        mClient = new MqttAndroidClient(getApplicationContext(), serverURI, clientId);

        mClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
    private void connect(MqttConnectOptions options) throws MqttException {
        mClient.connect(options, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {

            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }

    private void subscribe() throws MqttException {
        mClient.subscribe("topic", 1, null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {

            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }

    private void publish() throws MqttException {
        mClient.publish("topic", "payload".getBytes(), 1, false, null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {

            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }

    private void unsubscribe() throws MqttException {
        mClient.unsubscribe("topic", null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {

            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }

    private void disconnect() throws MqttException {
        mClient.disconnect(null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {

            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }
}
