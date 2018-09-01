package com.iostreamonedemo.niostream;

import java.io.IOException;
import java.nio.file.*;

public class WatchServiceMy {
    private void watchServiceWithMy() throws IOException, InterruptedException {
        //获取文件系统的WatchService对象
        WatchService watchService = FileSystems.getDefault().newWatchService();
        //为E:盘根路径注册监听
        Paths.get("E:/").register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        //死循环进行监听
        while (true) {
            //获取下一个文件变化事件
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(event.context() + "  文件发生了  " +
                        event.kind() + "事件！");
            }
            //重设WatchKey
            boolean valid = key.reset();
            //如果重设失败，退出监听
            if (!valid) {
                break;
            }
        }
    }
}
