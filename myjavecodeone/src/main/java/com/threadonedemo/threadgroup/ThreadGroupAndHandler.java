package com.threadonedemo.threadgroup;

public class ThreadGroupAndHandler implements  Thread.UncaughtExceptionHandler {
    //该方法将处理线程的未处理异常
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t+" 线程出现了异常:"+e);
    }
}

class ExHandler{
    public static void main(String[] args) {
        //设置主线程的异常处理器
        Thread.currentThread().setUncaughtExceptionHandler(new ThreadGroupAndHandler());
        int a =5/0;
        System.out.println("程序正常结束");
    }
}
