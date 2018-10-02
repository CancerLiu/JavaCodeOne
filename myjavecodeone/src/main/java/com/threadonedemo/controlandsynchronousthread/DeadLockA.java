package com.threadonedemo.controlandsynchronousthread;

public class DeadLockA {

    public synchronized void foo(DeadLockB b) {
        System.out.println("当前线程名: " + Thread.currentThread().getName() + " 进入DeadLockA实例的foo()方法");

        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("当前线程名: " + Thread.currentThread().getName() + " 企图调用DeadLockB实例的last()方法");
        b.last();
    }

    public synchronized void last() {
        System.out.println("进入DeadLockA类的last()方法内部");
    }
}

class DeadLockB {
    public synchronized void bar(DeadLockA a) {
        System.out.println("当前线程名: " + Thread.currentThread().getName() + "  进入了DeadLockB实例的bar()方法");
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("当前线程名:" + Thread.currentThread().getName() + " 企图调用DeadLockA实例的last()方法");
        a.last();
    }

    public synchronized void last() {
        System.out.println("进入了DeadLockB类的last()方法内部");
    }
}

class DeadLock implements Runnable {
    DeadLockA a = new DeadLockA();
    DeadLockB b = new DeadLockB();

    @Override
    public void run() {
        Thread.currentThread().setName("副线程");
        //调用对象的bar()方法
        b.bar(a);
        System.out.println("进入了主线程之后");
    }

    public void init() {
        Thread.currentThread().setName("主线程");
        //调用a对象的foo()方法
        a.foo(b);
        System.out.println("进入了主线程之后");
    }


    public static void main(String[] args) {
        DeadLock dl = new DeadLock();
        //以dl为target启动新线程
        new Thread(dl).start();
        //调用init()方法
        dl.init();
    }
    /**
     **最终结果如下，可知是先调用的init()方法，推测在启动线程的时候被代表main线程的init()方法抢占了资源
    当前线程名: 主线程 进入DeadLockA实例的foo()方法
     当前线程名: 副线程  进入了DeadLockB实例的bar()方法
     当前线程名: 主线程 企图调用DeadLockB实例的last()方法
     当前线程名:副线程 企图调用DeadLockA实例的last()方法
    * */
}
