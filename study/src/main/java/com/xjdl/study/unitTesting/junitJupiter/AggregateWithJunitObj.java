package com.xjdl.study.unitTesting.junitJupiter;

import org.junit.jupiter.params.aggregator.AggregateWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 复合参数注解 @AggregateWith(JunitObj.class)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@AggregateWith(JunitObj.class)
public @interface AggregateWithJunitObj {

}
