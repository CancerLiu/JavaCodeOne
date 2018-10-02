package com.threadonedemo.threadgroup;

public class ThreadGroupTest {

    public static void main(String[] args) {
        //获取主线程所在的线程组，这是所有线程默认的线程组
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        System.out.println("主线程组的名字:" + mainGroup.getName());
        System.out.println("主线程组是否是后台线程组:" + mainGroup.isDaemon());
        new MyThreadGroup("主线程组的线程").start();
        ThreadGroup tg = new ThreadGroup("新线程组");
        tg.setDaemon(true);
        System.out.println("tg线程组是否是后台线程组:" + tg.isDaemon());
        MyThreadGroup tt = new MyThreadGroup(tg, "tg组的线程甲");
        tt.start();
        new MyThreadGroup(tg, "tg组的线程乙").start();
    }
}
