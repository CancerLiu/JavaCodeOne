package com.threadonedemo.blockqueuemethod;

import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {
    private BlockingQueue<String> bq;

    public Producer(BlockingQueue<String> bq) {
        this.bq = bq;
    }

    @Override
    public void run() {
        String[] strArr = new String[]{
                "Java",
                "Strings",
                "Spring"
        };
        for (int i = 0; i < 999999999; i++) {
            System.out.println(getName() + "生产者准备生产集合元素！");
            try {
                Thread.sleep(200);
                //尝试放入元素，如果队列已满，则线程被阻塞
                bq.put(strArr[i % 3]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println(getName() + "生产完成:" + bq);
        }
    }
}
