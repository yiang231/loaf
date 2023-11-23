package com.xjdl.test.ioc.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Person {
    private Car car;
    private String name;
}