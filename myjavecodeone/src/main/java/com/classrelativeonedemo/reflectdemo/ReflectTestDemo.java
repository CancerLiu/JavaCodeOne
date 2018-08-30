package com.classrelativeonedemo.reflectdemo;

import java.lang.reflect.Field;

public class ReflectTestDemo {

    public static void checkDeclareAndNone() {
        Class<Manager> clazz = Manager.class;
        Field[] fieldNoneArrays = clazz.getFields();
        Field[] fieldDelcareArrays = clazz.getDeclaredFields();

        System.out.println("有declare");
        for (int i = 0; i < fieldDelcareArrays.length; i++) {
            System.out.println(fieldDelcareArrays[i].getName());
        }
        System.out.println();
        System.out.println("无declare");
        for (int i = 0; i < fieldNoneArrays.length; i++) {
            System.out.println(fieldNoneArrays[i].getName());
        }
    }

    public static void main(String[] args) {
        ReflectTestDemo.checkDeclareAndNone();
    }
}
