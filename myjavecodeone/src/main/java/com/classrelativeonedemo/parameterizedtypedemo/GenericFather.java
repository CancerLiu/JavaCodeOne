package com.classrelativeonedemo.parameterizedtypedemo;

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

