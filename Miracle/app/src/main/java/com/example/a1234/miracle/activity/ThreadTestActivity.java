package com.example.a1234.miracle.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.a1234.miracle.R;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * author: wsBai
 * date: 2019/3/6
 */
public class ThreadTestActivity extends BaseActivity {
    //第一个参数是允许同时进行的线程数，第二个参数是是否保持线程的先进先得原则
    Semaphore semaphore = new Semaphore(1, true);
    private int x = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_thread_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        String a = "123";
        String b = "123";
        boolean xb = a==b;
        Log.d("sssss",xb+"");
        //
        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer","Roger Federer",
                "Andy Murray","Tomas Berdych",
                "Juan Martin Del Potro"};
        List<String> players =  Arrays.asList(atp);

// 以前的循环方式
        for (String player : players) {
            System.out.print(player + "; ");
        }

// 使用 lambda 表达式以及函数操作(functional operation)
        players.forEach((player) -> Log.d("ssssss",player));

// 在 Java 8 中使用双冒号操作符(double colon operator)
        players.forEach(System.out::println);
        //
        startSemaPhoreTest();
    }

    /**
     * 信号量锁测试
     * 不加信号量打印结果：
     * D/SemaPhoreTest:: Thread-6 1
     * 2019-03-06 17:00:13.810 8779-8863/com.example.a1234.miracle D/SemaPhoreTest:: Thread-8 2
     * 2019-03-06 17:00:13.814 8779-8862/com.example.a1234.miracle D/SemaPhoreTest:: Thread-7 3
     * 2019-03-06 17:00:13.814 8779-8865/com.example.a1234.miracle D/SemaPhoreTest:: Thread-10 6
     * 2019-03-06 17:00:13.814 8779-8866/com.example.a1234.miracle D/SemaPhoreTest:: Thread-11 6
     * 2019-03-06 17:00:13.815 8779-8867/com.example.a1234.miracle D/SemaPhoreTest:: Thread-12 6
     * 2019-03-06 17:00:13.815 8779-8864/com.example.a1234.miracle D/SemaPhoreTest:: Thread-9 7
     * 2019-03-06 17:00:13.817 8779-8871/com.example.a1234.miracle D/SemaPhoreTest:: Thread-16 9
     * 2019-03-06 17:00:13.817 8779-8868/com.example.a1234.miracle D/SemaPhoreTest:: Thread-13 8
     * 2019-03-06 17:00:13.818 8779-8876/com.example.a1234.miracle D/SemaPhoreTest:: Thread-21 10
     * 2019-03-06 17:00:13.818 8779-8870/com.example.a1234.miracle D/SemaPhoreTest:: Thread-15 12
     * 2019-03-06 17:00:13.818 8779-8875/com.example.a1234.miracle D/SemaPhoreTest:: Thread-20 13
     * 2019-03-06 17:00:13.818 8779-8869/com.example.a1234.miracle D/SemaPhoreTest:: Thread-14 13
     * 2019-03-06 17:00:13.819 8779-8874/com.example.a1234.miracle D/SemaPhoreTest:: Thread-19 15
     * 2019-03-06 17:00:13.819 8779-8873/com.example.a1234.miracle D/SemaPhoreTest:: Thread-18 15
     * 2019-03-06 17:00:13.820 8779-8872/com.example.a1234.miracle D/SemaPhoreTest:: Thread-17 16
     * 2019-03-06 17:00:13.820 8779-8877/com.example.a1234.miracle D/SemaPhoreTest:: Thread-22 18
     * 2019-03-06 17:00:13.820 8779-8878/com.example.a1234.miracle D/SemaPhoreTest:: Thread-23 18
     * 2019-03-06 17:00:13.821 8779-8879/com.example.a1234.miracle D/SemaPhoreTest:: Thread-24 19
     * 2019-03-06 17:00:13.824 8779-8880/com.example.a1234.miracle D/SemaPhoreTest:: Thread-25 20
     * 反之：
     * D/SemaPhoreTest:: Thread-5 1
     * 2019-03-06 17:01:47.032 9277-9339/com.example.a1234.miracle D/SemaPhoreTest:: Thread-10 2
     * 2019-03-06 17:01:47.032 9277-9347/com.example.a1234.miracle D/SemaPhoreTest:: Thread-18 3
     * 2019-03-06 17:01:47.032 9277-9344/com.example.a1234.miracle D/SemaPhoreTest:: Thread-15 4
     * 2019-03-06 17:01:47.033 9277-9338/com.example.a1234.miracle D/SemaPhoreTest:: Thread-9 5
     * 2019-03-06 17:01:47.034 9277-9337/com.example.a1234.miracle D/SemaPhoreTest:: Thread-8 6
     * 2019-03-06 17:01:47.034 9277-9342/com.example.a1234.miracle D/SemaPhoreTest:: Thread-13 7
     * 2019-03-06 17:01:47.035 9277-9341/com.example.a1234.miracle D/SemaPhoreTest:: Thread-12 8
     * 2019-03-06 17:01:47.036 9277-9343/com.example.a1234.miracle D/SemaPhoreTest:: Thread-14 9
     * 2019-03-06 17:01:47.038 9277-9345/com.example.a1234.miracle D/SemaPhoreTest:: Thread-16 10
     * 2019-03-06 17:01:47.039 9277-9336/com.example.a1234.miracle D/SemaPhoreTest:: Thread-7 11
     * 2019-03-06 17:01:47.039 9277-9340/com.example.a1234.miracle D/SemaPhoreTest:: Thread-11 12
     * 2019-03-06 17:01:47.044 9277-9348/com.example.a1234.miracle D/SemaPhoreTest:: Thread-19 13
     * 2019-03-06 17:01:47.047 9277-9335/com.example.a1234.miracle D/SemaPhoreTest:: Thread-6 14
     * 2019-03-06 17:01:47.047 9277-9346/com.example.a1234.miracle D/SemaPhoreTest:: Thread-17 15
     * 2019-03-06 17:01:47.047 9277-9349/com.example.a1234.miracle D/SemaPhoreTest:: Thread-20 16
     * 2019-03-06 17:01:47.048 9277-9350/com.example.a1234.miracle D/SemaPhoreTest:: Thread-21 17
     * 2019-03-06 17:01:47.048 9277-9352/com.example.a1234.miracle D/SemaPhoreTest:: Thread-23 18
     * 2019-03-06 17:01:47.048 9277-9351/com.example.a1234.miracle D/SemaPhoreTest:: Thread-22 19
     * 2019-03-06 17:01:47.048 9277-9353/com.example.a1234.miracle D/SemaPhoreTest:: Thread-24 20
     */
    private class SemaPhoreTest implements Runnable {
        @Override
        public void run() {
            //该方法不会抛出interruptiblyError错误
            semaphore.acquireUninterruptibly();
            x++;
            Log.d("SemaPhoreTest: ", Thread.currentThread().getName() + " " + x);
            semaphore.release();
        }
    }

    private void startSemaPhoreTest() {
        for (int i = 0; i < 20; i++) {
            new Thread(new SemaPhoreTest()).start();
        }
    }

}
