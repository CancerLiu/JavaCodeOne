package com.lambdaonedemo.optionalandstreamdemo;

import java.util.Date;

public class User {
    private String name;

    private Integer height;

    private Date birthday;

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

    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }
}
