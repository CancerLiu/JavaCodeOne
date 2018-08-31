package com.iostreamonedemo.biostream.processstream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

//定义一个ReadStandard类，该类可以接收标准输入，并将标准输入写入out.txt中
public class ReadStandard {
    public static void main(String[] args) {
        System.out.println("123");
        try (
                //使用System.in创建Scanner对象，用于获取标准输入
                Scanner sc = new Scanner(System.in);
                PrintStream ps = new PrintStream(new FileOutputStream("E:/JavaCodeDocument/stream/out.txt"))
        ) {
            //增加下面一行只把回车作为分隔符
            sc.useDelimiter("\n");
            //判断是否还有下一个输入项
            while (sc.hasNext()) {
                //输出输入项
                ps.println("键盘输入的内容是：" + sc.next());
            }
        } catch (IOException os) {
            os.printStackTrace();
        }
    }
}