package com.xjdl.study.designPatterns.behavioralPatterns.strategy.annotaion;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.CouponTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CouponType {
    // 对应 CouponTypeEnum 的 code
    CouponTypeEnum value();
}