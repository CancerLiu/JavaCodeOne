package com.classrelativeonedemo.reflectdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    public void testOne() throws NoSuchMethodException {
        Class<Manager> clazz = Manager.class;
        Method m = clazz.getMethod("setWorkContent", String.class);
        m.getParameters();
    }

    public static void main(String[] args) throws NoSuchMethodException {
//        ReflectTestDemo.checkDeclareAndNone();
        ReflectTestDemo demo = new ReflectTestDemo();
        demo.testOne();
    }
}
