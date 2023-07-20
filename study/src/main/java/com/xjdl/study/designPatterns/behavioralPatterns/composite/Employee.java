package com.xjdl.study.designPatterns.behavioralPatterns.composite;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自己套自己
 */
@Slf4j
public class Employee {
    private String name;
    private String dept;
    private List<Employee> subEmployee;

    public Employee(String name, String dept) {
        this.name = name;
        this.dept = dept;
        this.subEmployee = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
//                ", subEmployee=" + subEmployee +
                '}';
    }

    public List<Employee> getSubEmployee() {
        return subEmployee;
    }

    public void remove(Employee... employee) {
        subEmployee.removeAll(Arrays.asList(employee));
    }

    public void add(Employee... employee) {
        subEmployee.addAll(Arrays.asList(employee));
    }
}
