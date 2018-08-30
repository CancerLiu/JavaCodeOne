package com.classrelativeonedemo;

public class ClassLoaderMySelf extends ClassLoader {

    public void checkLoadedClass(){
        Class<?> clazz = findLoadedClass("com.cpsdb.mytestcode.parameterizedtypedemo.WithGenericFather");
        System.out.println(clazz);
    }

    public static void main(String[] args) {
        ClassLoaderMySelf classLoaderMySelf = new ClassLoaderMySelf();
        classLoaderMySelf.checkLoadedClass();
    }

    //把书上的自定义代码实现了，试着写一个反编译器（*.class文件转换为代码）
}
