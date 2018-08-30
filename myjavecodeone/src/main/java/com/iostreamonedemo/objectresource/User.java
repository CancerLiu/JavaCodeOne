package com.iostreamonedemo.objectresource;

import java.io.Serializable;

public class User extends Student implements Serializable  {

    private String name;

    private Integer height;


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




}
