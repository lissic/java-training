package com.zero.juc.thread;

/**
 * @author zero
 * @description InterruptTest
 * @date 2022/8/29 15:05
 */
public class InterruptTest {
    public static void main(String[] args) {
        // 获取当前线程的中断状态并重置中断标志
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println("------------1");
        // 向当前线程发送中断标识，不代表当前线程立即中断；
        // 如果再其他线程种设置该线程的中断标志且该线程处于阻塞状态，则会抛出中断异常
        Thread.currentThread().interrupt();
        System.out.println("------------2");
        System.out.println("当前线程是否中断：" + Thread.currentThread().isInterrupted());
        // 获取当前线程的中断状态并重置中断标志
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
    }
}
