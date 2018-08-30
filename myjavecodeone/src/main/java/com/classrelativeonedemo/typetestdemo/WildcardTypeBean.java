package com.classrelativeonedemo.typetestdemo;

import java.util.List;

public class WildcardTypeBean {

    private List<? extends Number> a;

    private List<? super String> b;

    private List<String> c;

    private Class<?> d;

    public List<? extends Number> getA() {
        return a;
    }

    public WildcardTypeBean setA(List<? extends Number> a) {
        this.a = a;
        return this;
    }

    public List<? super String> getB() {
        return b;
    }

    public WildcardTypeBean setB(List<? super String> b) {
        this.b = b;
        return this;
    }

    public List<String> getC() {
        return c;
    }

    public WildcardTypeBean setC(List<String> c) {
        this.c = c;
        return this;
    }

    public Class<?> getD() {
        return d;
    }

    public WildcardTypeBean setD(Class<?> d) {
        this.d = d;
        return this;
    }
}
