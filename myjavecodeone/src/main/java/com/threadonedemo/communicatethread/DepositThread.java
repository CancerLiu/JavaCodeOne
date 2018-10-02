package com.threadonedemo.communicatethread;

public class DepositThread extends Thread {
    //模拟用户账户
    private Account account;
    //当前存款线程所有存的钱数
    private double depositAmout;

    public DepositThread(String name, Account account, double depositAmout) {
        super(name);
        this.account = account;
        this.depositAmout = depositAmout;
    }

    //重复100次执行存款操作
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.deposit(depositAmout);
        }
    }
}
