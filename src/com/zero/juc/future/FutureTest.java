package com.zero.juc.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author zero
 * @description FutureTest
 * @date 2022/8/26 10:57
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("task1 is starting.....");
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            return "task1";
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("main线程结束.....");
        // 如果需要获取当前异步任务是否执行完毕，需要调用isDone()方法，如果使用该方式一直轮询获取任务执行状态，会导致CPU空转，造成CPU资源浪费
        System.out.println(futureTask.isDone());
        // 如果要获取异步任务结果，需要调用FutureTask的get()方法，该方法会阻塞
        System.out.println(futureTask.get());
    }
}
