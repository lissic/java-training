package com.zero.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author zero
 * @description DeadLockTest
 * @date 2022/8/28 21:22
 */
public class DeadLockTest {
    // 死锁测试及检测方法
    // 使用jstack或jconsole检测死锁
    public static void main(String[] args) {
        Object lockA = new Object();
        Object lockB = new Object();
        new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "\t" + "获取锁A");
                try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "\t" + "获取锁B");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t" + "获取锁B");
                synchronized (lockA) {
                    System.out.println(Thread.currentThread().getName() + "\t" + "获取锁A");
                }
            }
        }).start();
    }
}
