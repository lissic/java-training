package com.zero.juc.thread;

/**
 * @author zero
 * @description BaseTest
 * @date 2022/8/25 16:51
 */
public class BaseTest {
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println("是否为用户线程："+ Thread.currentThread().isDaemon());
            while (true){ }
        });
        /**
         * 如果为守护线程（true），则子线程随着main线程执行结束而一起结束；
         * 如果为用户线程（false），则当main线程结束之后，子线程还在一直运行
         */
        thread.setDaemon(false);
        thread.start();
        System.out.println("主线程结束");
    }
}
