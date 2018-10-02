package com.threadonedemo.basethreadmethod;

/**
 * 采用实现Runnable接口的方式，可以共享线程类中的资源。
 */
public class ImplementRunnable implements Runnable {
    private int i;

    @Override
    public void run() {
        for (; i < 100; i++) {
            //当线程类实现Runnable接口时，通过Thread.currentThread()方法获得当前线程
            System.out.println(Thread.currentThread().getName() + "   " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "   " + i);
            if (i == 20) {
                ImplementRunnable implementRunnable = new ImplementRunnable();
                //通过new Thread()并传入线程名和Runnable实例对象来创建线程
                new Thread(implementRunnable, "线程1").start();
                new Thread(implementRunnable, "线程2").start();
            }
        }
    }

}

