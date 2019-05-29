package com.example.baselib.netty.im;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工厂
 */
public class ExecutorServiceFactory {

    private ExecutorService bossPool;// 管理线程组，负责重连
    private ExecutorService workPool;// 工作线程组，负责心跳

    /**
     * 初始化boss线程池
     */
    public synchronized void initBossLoopGroup() {
        initBossLoopGroup(1);
    }

    /**
     * 初始化boss线程池
     * 重载
     *
     * @param size 线程池大小
     *
     *             Java通过Executors提供四种线程池，分别为：
     * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
     * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
     */
    public synchronized void initBossLoopGroup(int size) {
        destroyBossLoopGroup();
        bossPool = Executors.newFixedThreadPool(size);
    }

    /**
     * 初始化work线程池
     */
    public synchronized void initWorkLoopGroup() {
        initWorkLoopGroup(1);
    }

    /**
     * 初始化work线程池
     * 重载
     * @param size 线程池大小
     */
    public synchronized void initWorkLoopGroup(int size) {
        destroyWorkLoopGroup();
        workPool = Executors.newFixedThreadPool(size);
    }

    /**
     * 执行boss任务
     *
     * @param r
     */
    public void execBossTask(Runnable r) {
        if (bossPool == null) {
            initBossLoopGroup();
        }
        bossPool.execute(r);
    }

    /**
     * 执行work任务
     *
     * @param r
     */
    public void execWorkTask(Runnable r) {
        if (workPool == null) {
            initWorkLoopGroup();
        }
        workPool.execute(r);
    }

    /**
     * 释放boss线程池
     */
    public synchronized void destroyBossLoopGroup() {
        if (bossPool != null) {
            try {
                bossPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                bossPool = null;
            }
        }
    }

    /**
     * 释放work线程池
     */
    public synchronized void destroyWorkLoopGroup() {
        if (workPool != null) {
            try {
                workPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                workPool = null;
            }
        }
    }

    /**
     * 释放所有线程池
     */
    public synchronized void destroy() {
        destroyBossLoopGroup();
        destroyWorkLoopGroup();
    }
}
