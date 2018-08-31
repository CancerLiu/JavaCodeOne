package com.iostreamonedemo.biostream;

import java.io.*;
import java.util.Scanner;

public class IOStreamTest {

    /**
     * 读入字符流
     *
     * @throws IOException
     */
    private void readChar() throws IOException {
        File file = new File("H:/MyCode/JavaCodeDocument/IOReadDemo.txt");
        FileReader reader = new FileReader(file);
        char[] chars = new char[32];
        int hasRead = 0;
        while ((hasRead = reader.read(chars)) > 0) {
            System.out.println(new String(chars, 0, hasRead));
        }
        reader.close();
    }

    /**
     * 字节流输入
     *
     * @throws IOException
     */
    private void inputStreamMy() throws IOException {
        File file = new File("H:/MyCode/JavaCodeDocument/IOInputAndWriterDemo.txt");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[64];
        int len = 0;
        while ((len = inputStream.read(bytes)) > 0) {
            System.out.println(new String(bytes, 0, len, "UTF-8"));
        }
        inputStream.close();
    }

    /**
     * 复制文件
     *
     * @throws IOException
     */
    private void copyMy() throws IOException {
        File inFile = new File("H:/MyCode/JavaCodeDocument/IOReadDemo.txt");
        File outFile = new File("H:/MyCode/JavaCodeDocument/IOWriteDemo.txt");

        FileInputStream fileInputStream = new FileInputStream(inFile);
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);

        byte[] bytes = new byte[32];

        Integer hasRead = 0;

        while ((hasRead = fileInputStream.read(bytes)) > 0) {
            fileOutputStream.write(bytes, 0, hasRead);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }


    /**
     * 输出字符流，生成文件
     *
     * @throws IOException
     */
    private void writeChar() throws IOException {
        File file = new File("H:/MyCode/JavaCodeDocument/IOWriterDemo.txt");
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("锦瑟无端五十年");
        fileWriter.flush();
        fileWriter.close();

    }

    /**
     * 转换流
     */
    private void convertStream() throws IOException {
        //将打印输入流（打印流只有字节的）转化为输入字符流，然后再转换为缓冲流，使用缓冲流的readLine()；
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String hasRead = null;
        while ((hasRead = bufferedReader.readLine()) != null) {
            if ("exit".equals(hasRead)) {
                System.exit(1);
            }
            System.out.println(hasRead);
        }
    }

    /**
     * 缓冲流输出
     */
    private void bufferWriter() throws IOException {
        FileWriter fileWriter = new FileWriter(new File("H:/MyCode/JavaCodeDocument/IOInputAndWriterDemo.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String writerString = scan.next();
            if ("exit".equals(writerString)) {
                bufferedWriter.close();
                System.exit(1);
            }
            bufferedWriter.write(writerString);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        bufferedWriter.close();
    }

    /**
     * 缓冲流输入
     *
     * @throws IOException
     */
    private void bufferReader() throws IOException {
        FileReader fileReader = new FileReader(new File("H:/MyCode/JavaCodeDocument/IOInputAndWriterDemo.txt"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        char[] chars = new char[32];
        int len = 0;
        while ((len = bufferedReader.read(chars)) > 0) {
            System.out.println(new String(chars, 0, len));
        }
        bufferedReader.close();
    }

    /**
     * 推回输入流，实现了只打印目标字符串前面内容的功能，此处的目标字符串为first
     */
    private void pushBackStream() throws IOException {
        //指定推回缓冲区的长度为32字节
        PushbackReader pushbackReader = new PushbackReader(new FileReader("E:/JavaCodeDocument/stream/reader.txt"), 32);

        char[] chars = new char[16];
        //用以保存上次读取的字符串内容
        String lastContent = "";
        int hasRead = 0;
        while ((hasRead = pushbackReader.read(chars)) > 0) {
            //正常读取，将读取到的字符数组转换为字符串
            String content = new String(chars, 0, hasRead);
            //用于记录目标字符在推回缓冲区中出现的位置
            int targerIndex = 0;
            //如果读取到了目标字符，则将内容加入推回缓冲区中
            if ((targerIndex = (lastContent + content).indexOf("first")) > 0) {
                pushbackReader.unread((lastContent + content).toCharArray());
                if (targerIndex > 16) {
                    chars = new char[targerIndex];
                }
                //读取目标字符之前的内容到chars字符数组中，因为读到了就可以退出程序了
                pushbackReader.read(chars, 0, targerIndex);
                System.out.println(new String(chars, 0, targerIndex));
                System.exit(0);
            } else {
                System.out.println(lastContent);
                lastContent = content;
            }
        }
    }

    /**
     * 标准输入输出流的重定向，此处样例将输出到控制台的标准输出流重定向到相应的文件中
     */
    private void printStreamRedirection() throws FileNotFoundException {
        File file = new File("E:/JavaCodeDocument/stream/redirection.txt");
        PrintStream printStream = new PrintStream(new FileOutputStream(file));
        System.setOut(printStream);
        //之后正常输出打印内容就不会显示在控制台了，而会输出到重定向的相应文件中
        System.out.println("来自答应的内容，应该会输出到相应的文件");
        printStream.close();
    }


    public static void main(String[] args) throws IOException {
        IOStreamTest ioStreamTest = new IOStreamTest();
//        ioStreamTest.readChar();
//        ioStreamTest.copyMy();
//        ioStreamTest.writeChar();
//        ioStreamTest.convertStream();
//        ioStreamTest.pushBackStream();
//         ioStreamTest.bufferWriter();
//        ioStreamTest.inputStreamMy();
//        ioStreamTest.bufferReader();
        ioStreamTest.printStreamRedirection();
    }
}
