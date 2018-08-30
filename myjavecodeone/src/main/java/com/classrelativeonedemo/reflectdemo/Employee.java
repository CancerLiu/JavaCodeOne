package com.classrelativeonedemo.reflectdemo;

import java.util.Date;

public class Employee {
    public String name;
    public Double salary;
    public Date startDate;

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public Double getSalary() {
        return salary;
    }

    public Employee setSalary(Double salary) {
        this.salary = salary;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Employee setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }
}
