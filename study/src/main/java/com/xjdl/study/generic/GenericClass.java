package com.xjdl.study.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 泛型类
 *
 * @param <T>
 */
@Slf4j
@Data
@AllArgsConstructor
public class GenericClass<T> {
    private T value;
}
