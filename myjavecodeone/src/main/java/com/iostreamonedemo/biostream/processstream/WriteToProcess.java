
package com.iostreamonedemo.biostream.processstream;

import java.io.IOException;
import java.io.PrintStream;

public class WriteToProcess {
    //该类用于在Java程序中启动Java虚拟机运行另一个Java程序并向另一个Java程序输入数据
    //正常来说应该是启动该类的main函数，然后主函数中会启动ReadStandard类并向目标目录输出txt文件。这里不知道啥原因失败了//todo 查错误
    public static void main(String[] args) throws IOException {
        //运行java ReadStandard命令，返回运行该命令的子进程
        Process p = Runtime.getRuntime().exec("java ReadStandard -classpath" + System.getProperty("java.class.path"));
//        OutputStream out = p.getOutputStream();
//        out.write("E:\r\n".getBytes());
//        out.write("cd E:\\MyJavaCode\\JavaCodeOne\\myjavecodeone\\src\\main\\java\\com\\iostreamonedemo\\biostream\r\n".getBytes());
//        out.write("javac ReadStandard.java\r\n".getBytes());
//        out.write("java ReadStandard\r\n".getBytes());
//        out.flush();
        try (
                //以p进程的输出流创建PrintStream对象
                //这个输出流对本程序是输出流，对p进程则是输入流(本class对应的程序为主进程程序，为输出流;p进程对应的子
                // 进程为输入流，p进程也是在本主进程中开的子进程)
                PrintStream ps = new PrintStream(p.getOutputStream());
        ) {
            //使用，向ReadStandard程序写入内容，这些内容将会被ReadStandard读取
            ps.println("普通字符串");
            ps.println(new WriteToProcess());
        }
    }
}

