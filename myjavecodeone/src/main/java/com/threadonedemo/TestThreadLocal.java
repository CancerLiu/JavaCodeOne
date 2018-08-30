package com.threadonedemo;

public class TestThreadLocal {

    private static final ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    public static void main(String[] args) {
        //方法（1）——直接通过Thread的start方法启动
        //相对于方法（2），因为i是局部变量，所以是线程安全的;
        //通过方法（1）可知各个线程在从ThreadLocal取值的时候是相互独立的，
        for (int i = 0; i < 5; i++) {
            new Thread(new MyThread(i)).start();
        }
        //方法（2）——通过线程池
        //因为Lambda表达式中不允许修改变量，所以此处将变量作为元素放在数组中;
        //当然此种方法此处有问题，因为通过线程池来管理线程，所以其实质是非阻塞的。即第一个线程交给线程池后，就开始下一个线程了，此时交给线程池管理的第一个线程可能
        //还没有跑完第一个for循环（即还没有执行ints[0] = ints[0] + 1），第二个线程就又用没有增加的ints[0]第二次进入for循环了。所以导致了这种线程名重复的情况;
        //第一种方法之所以可以避免这种情况在于其i值是线程安全的；
//        final Vector<Integer> vector = new Vector<>(1);
//        vector.addElement(0);
//        for (; vector.get(0) < 5; vector.setElementAt(vector.get(0) + 1, 0)) {
//
//            Executors.newSingleThreadExecutor().execute(() -> {
//                try {
////                    Thread.sleep(10);
//                    System.out.println("线程 " + vector.get(0) + " 的初始value是: " + value.get());
//                    long random = Math.round((Math.random() * 10));
//                    for (int j = 0; j < random; j++) {
//                        value.set(value.get() + j);
//                    }
//                    System.out.println("线程 " + vector.get(0) + " 的累加value是: " + value.get());
//                    System.out.println("线程 " + vector.get(0) + " 的随机数是" + random);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });
//        }

    }

    static class MyThread implements Runnable {
        private Integer index;

        public MyThread(Integer index) {
            this.index = index;
        }

        @Override
        public void run() {
            System.out.println("线程 " + index + " 的初始value是: " + value.get());
            long random = Math.round((Math.random() * 10));
            for (int j = 0; j < random; j++) {
                value.set(value.get() + j);
            }
            System.out.println("线程 " + index + " 的累加value是: " + value.get());
            System.out.println("线程 " + index + " 的随机数是" + random);
        }
    }

}

