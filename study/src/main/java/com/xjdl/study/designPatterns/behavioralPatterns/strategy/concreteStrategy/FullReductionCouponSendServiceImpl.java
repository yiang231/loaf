package com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.CouponTypeEnum;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.CouponSendService;

public class FullReductionCouponSendServiceImpl implements CouponSendService {
    @Override
    public String sendCoupon() {
        return "发满减券";
    }

    @Override
    public Integer couponType() {
        return CouponTypeEnum.FULL_REDUCTION.getCode();
    }
}
