package com.classrelativeonedemo.typetestdemo;

import java.util.List;
import java.util.Map;

public class GenericArrayTypeBean<T> {

    //注意泛型数组只能声明，不能分配空间。
    private List<String>[] pTypeArray;

    private T[] vTypeArray;

    private Map<T, Integer> mapArray;

    private List<String> list;

    private String[] strings;

    private User[] userArray;


    public void test(List<String>[] pTypeArray, T[] vTypeArray,
                     List<String> list, String[] strings, User[] userArray) {
    }

    public List<String>[] getpTypeArray() {
        return pTypeArray;
    }

    public GenericArrayTypeBean setpTypeArray(List<String>[] pTypeArray) {
        this.pTypeArray = pTypeArray;
        return this;
    }

    public T[] getvTypeArray() {
        return vTypeArray;
    }

    public GenericArrayTypeBean setvTypeArray(T[] vTypeArray) {
        this.vTypeArray = vTypeArray;
        return this;
    }

    public List<String> getList() {
        return list;
    }

    public GenericArrayTypeBean setList(List<String> list) {
        this.list = list;
        return this;
    }

    public String[] getStrings() {
        return strings;
    }

    public GenericArrayTypeBean setStrings(String[] strings) {
        this.strings = strings;
        return this;
    }

    public User[] getUserArray() {
        return userArray;
    }

    public GenericArrayTypeBean setUserArray(User[] userArray) {
        this.userArray = userArray;
        return this;
    }

    public Map<T, Integer> getMapArray() {
        return mapArray;
    }

    public GenericArrayTypeBean<T> setMapArray(Map<T, Integer> mapArray) {
        this.mapArray = mapArray;
        return this;
    }
}
