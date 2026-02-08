package com.xjdl.study.designPatterns.behavioralPatterns.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponTypeEnum {
    DISCOUNT("折扣", 95),
    FULL_REDUCTION("满减", 99);
    private final String type;
    private final int code;
}
