package com.zero.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zero
 * @description LockSupportTest
 * @date 2022/8/29 15:34
 */
public class LockSupportTest {
    public static void main(String[] args) {
        // objectLock();
        // conditionLock();
        lockSupport();
    }

    // lockSupport只需要park/unpark方法对线程进行阻塞和唤醒，顺序无要求且无需在同步块中，但是许可证只有一个，并且不会累积
    private static void lockSupport() {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "被执行.....");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "被唤醒....");
        }, "t1");
        t1.start();
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "发出通知....");
            LockSupport.unpark(t1);
        },"t2").start();
    }

    // lock锁的await和signal必须出现再lock块中，成对出现且阻塞后唤醒，否则会抛出异常
    private static void conditionLock() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "被执行....");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "被唤醒....");
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "发出通知....");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    // object的wait和notify必须出现再同步代码块中，成对出现且阻塞后唤醒，否则会抛出异常
    private static void objectLock() {
        Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "被执行...");
                try {
                    // 挂起当前线程
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 当前线程继续执行
                System.out.println(Thread.currentThread().getName() + "被唤醒...");
            }
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            synchronized (lock) {
                lock.notify();
                System.out.println(Thread.currentThread().getName() + "发出通知...");
            }
        }, "t2").start();
    }
}
