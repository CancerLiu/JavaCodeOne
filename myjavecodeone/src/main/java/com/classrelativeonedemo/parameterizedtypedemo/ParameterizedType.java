package com.classrelativeonedemo.parameterizedtypedemo;

import java.util.ArrayList;
import java.util.List;

public class ParameterizedType {

    public static <T> T getSomeThing(List<? super T> dest, List<T> src){
        T last = null;
        for(T ele:src){
            last = ele;
            dest.add(ele);
        }
        return last;
    }

    public static void main(String[] args) {
        List<Number> destList = new ArrayList<>();
        List<Integer> srcList = new ArrayList<>();

        destList.add(1);
        destList.add(2);
        destList.add(3);
        destList.add(4);

        srcList.add(5);
        srcList.add(6);
        srcList.add(7);
        srcList.add(8);

       Integer integer = ParameterizedType.getSomeThing(destList,srcList);
    }
}
