package com.classrelativeonedemo.parameterizedtypedemo;

public class GenericParams<T> {

    public T t;

    public <T> void getAndPrint(T t) {
        System.out.println("输出T" + t);
    }


    public static void main(String[] args) {
        //这里泛型方法的类型形参把类泛型类的类型形参覆盖了
        GenericParams<String> gen = new GenericParams<>();
        gen.getAndPrint(1);
    }
}
