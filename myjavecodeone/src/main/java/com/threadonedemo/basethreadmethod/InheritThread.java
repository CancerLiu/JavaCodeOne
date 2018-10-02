package com.threadonedemo.basethreadmethod;

/**
 * 繼承Thread类的方法不会共享线程类中的方法
 */
public class InheritThread extends Thread {
    private int i;

    @Override
    public void run() {
        for (; i < 100; i++) {
            //当线程继承Thread类时，直接使用this即可获取当前线程
            System.out.println(getName() + "  " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            //调用Thread的currentThread()方法获取当前线程
            System.out.println(Thread.currentThread().getName() + "   " + i);
            if (i == 20) {
                //创建第一个线程
                new InheritThread().start();
                //创建第二个线程
                new InheritThread().start();
            }
        }
    }
}
