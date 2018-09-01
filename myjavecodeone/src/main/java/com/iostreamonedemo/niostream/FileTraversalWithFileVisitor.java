package com.iostreamonedemo.niostream;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileTraversalWithFileVisitor {
    private void traversalWithFileVisitor() throws IOException {
        //遍历相应目录下的所有文件和子目录
        Files.walkFileTree(Paths.get("E:/JavaCodeDocument"), new SimpleFileVisitor<Path>() {
            //访问文件时触发该方法
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("正在访问" + file + "文件");
                //找到相关文件
                if (file.endsWith("channelInput.txt")) {
                    System.out.println("--已经找到目标文件--");
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }

            //开始访问目录时触发该方法
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("正在访问" + dir + "  路径");
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void main(String[] args) throws IOException {
        FileTraversalWithFileVisitor file = new FileTraversalWithFileVisitor();
        file.traversalWithFileVisitor();
    }
}
