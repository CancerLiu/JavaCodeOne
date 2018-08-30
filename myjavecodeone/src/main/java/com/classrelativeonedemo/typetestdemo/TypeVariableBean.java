package com.classrelativeonedemo.typetestdemo;

import java.io.Closeable;
import java.util.List;

public class TypeVariableBean<K extends User & Closeable,V> {

    private K studentUser;

    private V value;

    private V[] values;

    private String str;

    List<K> kList;

    public K getStudentUser() {
        return studentUser;
    }

    public TypeVariableBean setStudentUser(K studentUser) {
        this.studentUser = studentUser;
        return this;
    }

    public V getValue() {
        return value;
    }

    public TypeVariableBean setValue(V value) {
        this.value = value;
        return this;
    }

    public V[] getValues() {
        return values;
    }

    public TypeVariableBean setValues(V[] values) {
        this.values = values;
        return this;
    }

    public String getStr() {
        return str;
    }

    public TypeVariableBean setStr(String str) {
        this.str = str;
        return this;
    }

    public List<K> getkList() {
        return kList;
    }

    public TypeVariableBean setkList(List<K> kList) {
        this.kList = kList;
        return this;
    }
}
