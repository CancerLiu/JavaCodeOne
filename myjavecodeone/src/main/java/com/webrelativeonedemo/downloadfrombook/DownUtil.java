package com.webrelativeonedemo.downloadfrombook;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

public class DownUtil {
    //资源路径
    private String path;
    //保存位置
    private String targetFile;
    //线程数量
    private int threadNum;
    //线程对象集合
    private DownThread[] threads;
    //文件总大小
    private int fileSize;

    public DownUtil(String path, String targetFile, int threadNum) {
        this.path = path;
        this.targetFile = targetFile;
        this.threadNum = threadNum;
        this.threads = new DownThread[threadNum];
    }

    public void download() throws IOException {
        URL url = new URL(path);
        //HttpURLConnection是URLConnection的子类，其中有一些Http相关的属性
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //设置Http的请求头相关属性
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "image/gif,image/jpeg,image/pjpeg," +
                "application/x-shockwave-flash,application/xmal+xml," + "application/vnd.ms-xpsdocument,application/xaml+xml,"
                + "application/x-ms-application,application/vnd.ms-excel," + "application/vnd.ms-powerpoint,application/msword,*/*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");

        //得到文件大小
        fileSize = conn.getContentLength();
        //得到总文件大小之后就立马关闭连接，因为是分线程下载，所以此处的链接的作用仅仅用于得到文件大小，用于创建下载临时文件使用；
        conn.disconnect();
        int currentPartSize = fileSize / threadNum + 1;

        //设置临时文件(使用随机读取类)
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        file.setLength(fileSize);
        //设置临时文件大小之后，关闭流
        file.close();

        for (int i = 0; i < threadNum; i++) {
            //每个线程的开始下载位置。
            int startPos = i * currentPartSize;
            //每个线程使用自己的RandomAccessFile进行下载
            RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw");

            randomAccessFile.seek(startPos);
            //使用下载工具类创建当前的下载线程
            threads[i] = new DownThread(path, startPos, currentPartSize, randomAccessFile);

            threads[i].start();
        }
    }

    public double getCompleteRate() {
        //统计多个线程已经下载的总大小
        int sumSize = 0;
        for (int i = 0; i < threadNum; i++) {
            sumSize += threads[i].length;
        }
        return (sumSize * 1.0 / fileSize);

    }

    public static void main(String[] args) throws IOException {
        //初始化DownUtil对象
        final DownUtil downUtil = new DownUtil("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528954143389&di=4997575a0cd54314d4eb4479b2a0793e&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F96dda144ad345982b391b10900f431adcbef8415.jpg",
                "E:/JavaDocument/picture/first_picture_for_thread.jpg", 4);

        downUtil.download();
        //然后专门开一个线程用来查看当前的下载进度
        /*（1）使用Executors线程类进行线程的管理*/
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(() -> {
//            while (downUtil.getCompleteRate() < 1) {
//                System.out.println("已完成:" + downUtil.getCompleteRate());
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        executorService.shutdown();

        /*（2）使用自己创建线程池而不是工具类创建的方法*/
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 5000;

        //创建线程工厂类
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("\"Orders-%d\"").build();

        ExecutorService singleThreadPool = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<Runnable>(), threadFactory);

        //特别注意此种方法，如果在execute中不进行异常捕捉的话，即使出现异常，外部也不知道（异常不会抛出）;
        singleThreadPool.execute(() -> {
            while (downUtil.getCompleteRate() < 1) {
                System.out.println("已完成:" + downUtil.getCompleteRate());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        singleThreadPool.shutdown();


        /*（3）自己写线程并开启*/
//        new Thread(() -> {
//            while (downUtil.getCompleteRate() < 1) {
//                System.out.println("已完成：" + downUtil.getCompleteRate());
//                try {
//                    Thread.sleep(10);
//                } catch (Exception e) {
//                }
//            }
//        }).start();

    }
}

class DownThread extends Thread {
    //资源路径
    private String path;
    //当前线程下载的位置
    private int startPos;
    //定义当前线程负责下载的文件大小
    private int currentPartSize;
    //当前线程需要下载的文件块
    private RandomAccessFile currentPart;
    //定义该线程已下载的字节数
    public int length;

    DownThread(String path, int startPos, int currentPartSize, RandomAccessFile currentPart) {
        this.path = path;
        this.startPos = startPos;
        this.currentPartSize = currentPartSize;
        this.currentPart = currentPart;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "image/gif,image/jpeg,image/pjpeg," +
                    "application/x-shockwave-flash,application/xmal+xml," + "application/vnd.ms-xpsdocument,application/xaml+xml,"
                    + "application/x-ms-application,application/vnd.ms-excel," + "application/vnd.ms-powerpoint,application/msword,*/*");
            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Charset", "UTF-8");

            InputStream inStream = conn.getInputStream();

            inStream.skip(this.startPos);
            byte[] bytes = new byte[1024];
            int hasRead = 0;
            //当前线程读取网路数据，并写入本地文件
            while (length < currentPartSize && (hasRead = inStream.read(bytes)) != -1) {
                currentPart.write(bytes, 0, hasRead);
                //累积该线程下载的总大小
                length += hasRead;
            }
            currentPart.close();
            inStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
