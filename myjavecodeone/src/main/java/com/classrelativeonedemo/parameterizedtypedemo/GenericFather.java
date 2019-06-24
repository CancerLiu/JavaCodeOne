package com.classrelativeonedemo.parameterizedtypedemo;

/**
 * 此处模拟的是在普通类中泛型方法的重写——此时泛型方法的重写和普通方法无区别
 */
public class GenericFather {

    public <T> void getAndPrint(T t) {
        System.out.println("来自父类" + t);
    }
}

class GenericSon extends GenericFather {

    public <T> void getAndPrint(T t) {
        System.out.println("来自子类" + t);
    }

    public static void main(String[] args) {
        GenericFather gen = new GenericSon();
        gen.getAndPrint("try");
    }
}

