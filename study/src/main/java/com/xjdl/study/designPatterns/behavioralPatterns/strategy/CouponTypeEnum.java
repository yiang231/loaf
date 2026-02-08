package com.xjdl.study.designPatterns.behavioralPatterns.strategy;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponTypeEnum {
    DISCOUNT("折扣", 1),
    FULL_REDUCTION("满减", 2);
    private final String type;
    private final Integer code;
}
