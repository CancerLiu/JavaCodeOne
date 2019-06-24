package com.javacollectionframeworkdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class test {

    public void testOne() {
        List<String> list = new ArrayList<>();
        list.add("listOne");
        list.add("listTwo");
        list.add("listThree");

        Stream.Builder b = Stream.builder();
    }
}
