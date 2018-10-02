package com.threadonedemo.communicatethread;

public class Account {
    //封装账户编号、账户余额的两个成员变量
    private String accountNo;
    private double balance;

    //标识账户中是否已有存款的旗标
    private boolean flag = false;

    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Account setAccountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isFlag() {
        return flag;
    }

    public Account setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public synchronized void draw(double drawAmount) {
        try {
            //如果flag为假，表明账户中还没有人存钱进去，取钱方法阻塞
            if (!flag) {
                wait();
            } else {
                //执行取钱操作
                System.out.println(Thread.currentThread().getName() + " 取钱：" + drawAmount);
                balance -= drawAmount;
                System.out.println("账户余额为:" + balance);
                //将标识账户是否已有存款的旗标设为false
                flag = false;
                //唤醒其他线程
                notifyAll();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void deposit(double depositAmount) {
        try{
            //如果flag为真，表明账户中已有人存钱进去，存钱方法阻塞
            if(flag){
                wait();
            }else{
                //执行存款操作
                System.out.println(Thread.currentThread().getName()+" 存款："+depositAmount);
                balance+=depositAmount;
                System.out.println("账户余额为: "+balance);
                //将表示账户是否已有存款的旗标设为true
                flag=true;
                //唤醒其他线程
                notifyAll();
            }
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}

/**
 * 此处除了使用常规的wait()、notify()和notifyAll()方法外，还可以使用Lock对象和Condition类对象来进行，具体方法如下——
 * 先定义Lock对象
 * //同步锁
 * private final Lock lock =new ReentrantLock();
 * //获得指定Lock对象对应的Condition对象
 * private final Condition cond = lock.newCondition();
 *
 * 之后就很简单了，将synchronized修饰的方法中的代码的起始和结束处分别加上lock.lock()上锁和lock.unlock()解锁
 * 然后将之前是wait()、notify()和notifyAll()方法全部依次替换为cond.await()、cond.signal()和cond.signalAll()
 **/
