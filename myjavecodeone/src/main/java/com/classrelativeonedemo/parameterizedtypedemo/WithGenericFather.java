package com.classrelativeonedemo.parameterizedtypedemo;


/**
 * 此处模拟的是带泛型类形参的方法的重写
 *
 * @param <T>
 */
public class WithGenericFather<T> {
    public void getAndPrint(T value) {
        System.out.println("父类的" + value);
    }
}

class WithGenericSon extends WithGenericFather<String> {

    public void getAndPrint(String value) {
        System.out.println("子类的" + value);
    }

    public static void main(String[] args) {
        WithGenericFather<String> gen = new WithGenericSon();
        gen.getAndPrint("123");
    }
}
