package com.example.a1234.miracle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.a1234.miracle.IMyAidlInterface;
import com.example.a1234.miracle.websocket.JWebSocketClient;

import androidx.annotation.Nullable;

import java.net.URI;

/**
 * author: wsBai
 * date: 2019/3/14
 */
public class MiracleProcessService extends Service {
    IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int add(int arg0, int arg1) throws RemoteException {
            Log.d("Service_Processing: ",arg0+" "+arg1);
            return arg0 + arg1;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 如果在系统显示调用stopService和stopSelf之前终 止服务，service再次重启，
     * onStartCommand会被调用，重启服务时依次执行onStartCommand，onStart
     * onStartComand使用时，返回的是一个(int)整形。
     * 这个整形可以有四个返回值：start_sticky、start_no_sticky、START_REDELIVER_INTENT、START_STICKY_COMPATIBILITY。
     * 它们的含义分别是：
     * 1):START_STICKY： 如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，由 于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传 递到service，那么参数Intent将为null。
     * 2):START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务
     * 3):START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
     * 4):START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MiracleProcessService::", "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
