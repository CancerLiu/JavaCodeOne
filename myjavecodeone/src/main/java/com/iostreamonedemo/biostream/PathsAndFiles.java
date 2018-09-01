package com.iostreamonedemo.biostream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PathsAndFiles {

    private void pathTest() {
        //以当前路径来创建Path对象
        Path path = Paths.get(".");
        System.out.println("path里包含的路径数量:" + path.getNameCount());
        System.out.println("path的根路径:" + path.getRoot());
        //获取path对应的绝对路径
        Path absolutePath = path.toAbsolutePath();
        System.out.println(absolutePath);
        //获取绝对路径所包含的路径数量
        System.out.println("absolutePath里包含的路径数量:" + absolutePath.getNameCount());
        System.out.println(absolutePath.getName(3));
        //以多个String来构建Path对象
        Path path2 = Paths.get("H:", "MyCode", "JavaCodeDocument");
        System.out.println(path2);
    }

    private void fileTest() throws IOException {
        //复制文件
        Files.copy(Paths.get("H:", "MyCode", "JavaCodeDocument", "FilesTest.txt"), new FileOutputStream("H:/MyCode/JavaCodeDocument/a.txt"));
        //判断FilesTest.java文件是否为隐藏文件
        Path path = Paths.get("H:", "MyCode", "JavaCodeDocument", "a.txt");
        System.out.println("当前文件是否是隐藏文件" + Files.isHidden(Paths.get("H:", "MyCode", "JavaCodeDocument", "a.txt")));
        //一次性读取FilesTest.java文件的所有行
        List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
        System.out.println(lines);
        //判断指定文件的大小
        System.out.println("a.txt的大小为:" + Files.size(path));
        //直接将多个文件写入指定文件正
        List<String> poem = new ArrayList<String>() {{
            add("古之成大事者");
            add("不唯有超凡脱世之才");
            add("亦有坚忍不拔之志");
        }};
        Files.write(path, poem, Charset.forName("UTF-8"));
        //使用Java8的Stream API列出当前目录下的所有文件和子目录
        Files.list(Paths.get(".")).forEach(System.out::println);
        //使用Java8新增的Stream API读取文件内容
        Files.lines(path, Charset.forName("UTF-8")).forEach(System.out::println);
        //获取相应文件磁盘等的信息
        FileStore cStore = Files.getFileStore(Paths.get("E:"));
        //判断E盘的总空间、可用空间
        System.out.println("E:共有空间:" + cStore.getTotalSpace());
        System.out.println("E:可用空间:" + cStore.getUsableSpace());
    }

    public static void main(String[] args) throws IOException {
        PathsAndFiles files = new PathsAndFiles();
//        files.pathTest();
        files.fileTest();
    }

}
