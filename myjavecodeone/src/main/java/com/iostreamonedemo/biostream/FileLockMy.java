package com.iostreamonedemo.biostream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLockMy {

    public static void main(String[] args) throws FileNotFoundException {
        FileChannel channel = new FileOutputStream(new File("E:/JavaCodeDocument/stream/fileLock.txt")).getChannel();

        try {
            //得到文件锁
            FileLock fileLock = channel.tryLock();
            //暂停10s(根据文件锁的定义，这10s内该文件就是不能修改的)
            Thread.sleep(10000);
            //释放锁
            fileLock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
