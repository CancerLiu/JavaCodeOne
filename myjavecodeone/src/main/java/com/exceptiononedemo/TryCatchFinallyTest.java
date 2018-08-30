package com.exceptiononedemo;

public class TryCatchFinallyTest {

    public static String tryReturn() {
        try {
            Integer ints = 1 / 0;
            System.out.println("aaa");
            return "from try";
        } catch (Exception e) {
            e.printStackTrace();
            return "from catch";
        }
    }

    public static void main(String[] args) {
        System.out.println(TryCatchFinallyTest.tryReturn());
    }
}
