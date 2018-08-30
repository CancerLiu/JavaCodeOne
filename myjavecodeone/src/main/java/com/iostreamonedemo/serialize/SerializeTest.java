package com.iostreamonedemo.serialize;

import com.cpsdb.base.mapper.JsonMapper;
import com.iostreamonedemo.objectresource.User;

import java.io.*;

public class SerializeTest {

    private void objectOutput() throws IOException, ClassNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("H:\\MyData\\IOObjectDemo.txt"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        User user1 = new User().setName("liuchao").setHeight(178);
        user1.setAgeStu(28);
        user1.setNameStu("wangyin");
        User user2 = new User().setName("liyin").setHeight(180);
        objectOutputStream.writeObject(user1);
        objectOutputStream.writeObject(user2);
    }

    private void objectInput() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("H:\\MyData\\IOObjectDemo.txt"));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        User user1 = (User) objectInputStream.readObject();
        User user2 = (User) objectInputStream.readObject();
        System.out.println(user1.getAgeStu());
        System.out.println(JsonMapper.buildNonNullMapper().toJson(user1));
        System.out.println(JsonMapper.buildNonNullMapper().toJson(user2));
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerializeTest serializeTest = new SerializeTest();
//        serializeTest.objectOutput();
        serializeTest.objectInput();
    }
}
