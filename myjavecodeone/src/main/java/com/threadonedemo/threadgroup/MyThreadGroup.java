package com.threadonedemo.threadgroup;

public class MyThreadGroup extends Thread {
    //提供指定线程名的构造器
    public MyThreadGroup(String name){
        super(name);
    }
    //提供指定线程名、线程组的构造器
    public MyThreadGroup(ThreadGroup group,String name){
        super(group,name);
    }
    @Override
    public void run(){
        for(int i=0;i<20;i++){
            System.out.println(getName()+" 线程的i变量"+i);
        }
    }
}
