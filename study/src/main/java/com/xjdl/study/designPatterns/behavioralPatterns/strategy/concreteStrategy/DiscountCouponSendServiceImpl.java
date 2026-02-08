package com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.CouponTypeEnum;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.CouponSendService;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.annotaion.CouponType;

@CouponType(CouponTypeEnum.DISCOUNT)
public class DiscountCouponSendServiceImpl implements CouponSendService {
    @Override
    public String sendCoupon(Integer couponType) {
        return "发折扣券";
    }

    @Override
    public int couponType() {
        return CouponTypeEnum.DISCOUNT.getCode();
    }
}
