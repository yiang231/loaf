package com.xjdl.study.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * Lombok常用注解
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString//(callSuper = true) // 父类的属性也重写到方法中
@Builder// 链式调用，生成ClassNameBuilder
@Accessors//(chain = true) // setField()返回实体类本身，而不是void
@Setter
@Getter
public class LombokTest {
    private String name;
    private int age;
}
