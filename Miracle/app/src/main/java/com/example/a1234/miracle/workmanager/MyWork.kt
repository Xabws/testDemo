package com.example.a1234.miracle.workmanager

import android.content.Context
import androidx.work.*

/**
 * @author wsbai
 * @date 2019-06-24
 * Worker：指定我们需要执行的任务。 WorkManager API包含一个抽象的Worker类，我们需要继承这个类并且在这里执行工作。
 */
class MyWork(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        // 在这里完成工作——就这个例子而言是压缩存储的图像。
        // 本示例中并没有传递参数，因而该任务假设为“压缩整个图像库”。
        myCompress();

        // 根据您的返回值来标明任务是成功还是失败：
        return Result.success();

        // （返回 Result.retry() 会让 WorkManager 过会儿重试该任务；
        // 返回 Result.failure() 则是说明不需要重试。）
    }

    fun myCompress() {

    }
}

/*
class Demo() {
    //根据这个 Worker 来创建一个 OneTimeWorkRequest，并使用 WorkManager 来将其加入队列：
    fun startWork() {
        //设置约束条件：在网络连接时进行任务
        var constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

        var compressRequest = OneTimeWorkRequest.Builder(MyWork.class).setConstraints(constraints).build()

        //把任务加到队列中
        WorkManager.getInstance().enqueue(compressRequest);

        //在任务结束时更新UI
        WorkManager.getInstance()
                .getWorkInfoByIdLiveData(compressRequest.id)

    }
}*/
