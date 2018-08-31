package com.iostreamonedemo.biostream;


import java.io.*;

public class RandomAccessStream {

    /**
     * 用于读取文本中间内容，具体从哪里开始读取可以自己通过指针指定。
     *
     * @throws IOException
     */
    public void readMiddleContent() throws IOException {
        File file = new File("E:/JavaCodeDocument/stream/randomaccess.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "r");

        System.out.println("RandomAccessFile文件指针的初始位置:" + raf.getFilePointer());
        //修改指针位置到300，然后开始正常读取
        raf.seek(301);
        String str = null;
        while ((str = raf.readLine()) != null) {
            byte[] bytes = str.getBytes("iso8859-1");
            System.out.println(new String(bytes));
        }
        System.out.println("RandomAccessFile文件指针的结束位置:" + raf.getFilePointer());
        raf.close();
    }

    /**
     * 向文本追加内容。因为中间插入内容时，加入的内容会将当前位置之后的内容覆盖，所以需要缓存待加入内容位置之后的
     * 内容来避免此种情况
     */
    private void appendStream() throws IOException {
        File file = new File("E:/JavaCodeDocument/stream/randomaccess.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        //先将指针移动到
        raf.seek(raf.getFilePointer());
    }

    private void insertNotCover(String fileName, long pos, String insertContent) throws IOException {
        File tmp = File.createTempFile("tmp", "txt", new File("E:/JavaCodeDocument/stream"));
        tmp.deleteOnExit();

        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        //使用临时文件来保存插入点后的数据
        FileOutputStream tmpOut = new FileOutputStream(tmp);
        FileInputStream tmpIn = new FileInputStream(tmp);

        //定位并获取当前指针出的内容，然后将之后的内容放入临时文件。
        // 再在位置处插入新数据，而后将临时文件中缓存的内容放入新加内容之后
        raf.seek(pos);
        byte[] bytesRead = new byte[32];
        int wttmp = 0;
        while ((wttmp = raf.read(bytesRead)) > 0) {
            tmpOut.write(bytesRead, 0, wttmp);
        }

        //加入新内容
        raf.seek(pos);
        raf.write(insertContent.getBytes());
        //然后追加缓存内容
        byte[] bytesWrite = new byte[32];
        int rdtmp = 0;
        while ((rdtmp = tmpIn.read(bytesWrite)) > 0) {
            raf.write(bytesWrite, 0, rdtmp);
        }

    }


    public static void main(String[] args) throws IOException {
        RandomAccessStream simple = new RandomAccessStream();
//        simple.readMiddleContent();
        simple.insertNotCover("E:/JavaCodeDocument/stream/randomaccess.txt", 301, "刘超新增内容");
    }
}
