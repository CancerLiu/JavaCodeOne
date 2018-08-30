package com.classrelativeonedemo.reflectdemo;

public class Manager extends Employee {
    public String workContent;

    public String getWorkContent() {
        return workContent;
    }

    public Manager setWorkContent(String workContent) {
        this.workContent = workContent;
        return this;
    }
}
