package com.xjdl.study.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;

/**
 * PersonAnnotation注解值的容器
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ANNOTATION_TYPE, FIELD})
@Deprecated
public @interface PersonsAnnotation {
    PersonAnnotation[] value() default {};
}
