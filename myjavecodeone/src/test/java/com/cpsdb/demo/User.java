package com.cpsdb.demo;

import java.util.Date;

public class User {

    @ValidatorAnnotation(fieldLength = 20)
    private String name;

    @ValidatorAnnotation(fieldLength = 18)
    private String certificate;

    @ValidatorAnnotation(fieldLength = 18)
    private String cellphone;

    private Double height;
    private Double weight;
    private Date birthday;
    private Integer age;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getCertificate() {
        return certificate;
    }

    public User setCertificate(String certificate) {
        this.certificate = certificate;
        return this;
    }

    public String getCellphone() {
        return cellphone;
    }

    public User setCellphone(String cellphone) {
        this.cellphone = cellphone;
        return this;
    }

    public Double getHeight() {
        return height;
    }

    public User setHeight(Double height) {
        this.height = height;
        return this;
    }

    public Double getWeight() {
        return weight;
    }

    public User setWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }
}
