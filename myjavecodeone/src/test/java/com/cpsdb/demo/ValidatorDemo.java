package com.cpsdb.demo;

import java.util.Date;

public class ValidatorDemo {

    private  User user;

    public static void main(String[] args) throws IllegalAccessException {
        ValidatorHandler.validatorHandler(new ValidatorDemo().createUser());
    }

    public User createUser(){
        return user = new User().setAge(15)
                .setBirthday(new Date())
                .setCellphone("13730885730")
                .setCertificate("510106199007032916")
                .setHeight(178.0)
                .setWeight(60.0)
                .setName("刘超");
    }
}
