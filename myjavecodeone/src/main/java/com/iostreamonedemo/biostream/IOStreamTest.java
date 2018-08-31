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
            if (hasRead.equals("exit")) {
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
     * 推回输入流
     */
    private void pushBackStream() throws IOException {
        //指定推回缓冲区的大小为32字节
        PushbackReader pushbackReader = new PushbackReader(new FileReader("H:/MyCode/JavaCodeDocument/IOReadDemo.txt"), 32);

        char[] chars = new char[16];
        String lastContent = "";
        int hasRead = 0;
        while ((hasRead = pushbackReader.read(chars)) > 0) {
            String content = new String(chars, 0, hasRead);
            int targerIndex = 0;
            if ((targerIndex = (lastContent + content).indexOf("first")) > 0) {
                pushbackReader.unread((lastContent + content).toCharArray());
                if (targerIndex > 16) {
                    chars = new char[targerIndex];
                }
                pushbackReader.read(chars, 0, targerIndex);
                System.out.println(new String(chars, 0, targerIndex));
                System.exit(0);
            } else {
                System.out.println(lastContent);
                lastContent = content;
            }

        }
    }

    public static void main(String[] args) throws IOException {
        IOStreamTest ioStreamTest = new IOStreamTest();
//        ioStreamTest.readChar();
//        ioStreamTest.copyMy();
//        ioStreamTest.writeChar();
//        ioStreamTest.convertStream();
//        ioStreamTest.pushBackStream();
//        ioStreamTest.bufferWriter();
//        ioStreamTest.inputStreamMy();
        ioStreamTest.bufferReader();
    }
}
