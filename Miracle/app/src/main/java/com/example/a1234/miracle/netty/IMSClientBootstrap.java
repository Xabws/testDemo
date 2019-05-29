package com.example.a1234.miracle.netty;

import com.example.baselib.netty.im.interf.IClientInterface;
import com.example.baselib.netty.im.netty.NettyTcpClient;

/**
 * @author wsbai
 * @date 2019-05-28
 */
public class IMSClientBootstrap {
    private IClientInterface imsClient;
    private boolean isActive;
    private static IMSClientBootstrap Instance = new IMSClientBootstrap();

    public static IMSClientBootstrap getInstance() {
        return Instance;
    }

    public synchronized void init(String serverUrl, int appStatus) {
        if (!isActive()) {
            if (serverUrl == null || serverUrl.isEmpty()) {
                System.out.println("init IMLibClientBootstrap error,ims hosts is null");
                return;
            }

            isActive = true;
            if (null != imsClient) {
                imsClient.close();
            }
            imsClient = NettyTcpClient.getInstance();
            updateAppStatus(appStatus);
            imsClient.init(serverUrl, new IMSEventListener(), new IMSConnectStatusListener());
        }
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void updateAppStatus(int appStatus) {
        if (imsClient == null) {
            return;
        }

        imsClient.setAppStatus(appStatus);
    }
}
