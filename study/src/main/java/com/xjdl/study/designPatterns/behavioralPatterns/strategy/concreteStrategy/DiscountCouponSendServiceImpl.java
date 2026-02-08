package com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.CouponTypeEnum;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.CouponSendService;

public class DiscountCouponSendServiceImpl implements CouponSendService {
    @Override
    public String sendCoupon() {
        return "发折扣券";
    }

    @Override
    public Integer couponType() {
        return CouponTypeEnum.DISCOUNT.getCode();
    }
}
