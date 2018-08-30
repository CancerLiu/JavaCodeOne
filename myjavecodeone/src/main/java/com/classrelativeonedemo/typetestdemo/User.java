package com.classrelativeonedemo.typetestdemo;

public class User {
    private  String name;
    private Integer height;
    private Integer weight;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public User setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public User setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public static void main(String[] args) {
    }
}
