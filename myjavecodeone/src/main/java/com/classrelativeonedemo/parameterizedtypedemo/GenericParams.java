package com.classrelativeonedemo.parameterizedtypedemo;

public class GenericParams<T> {

    public T t;

    public <T> void getAndPrint(T t) {
        System.out.println("输出T" + t);
    }



    public static void main(String[] args) {
        GenericParams<String> gen = new GenericParams<>();
        gen.getAndPrint(new Integer(1));
    }
}
