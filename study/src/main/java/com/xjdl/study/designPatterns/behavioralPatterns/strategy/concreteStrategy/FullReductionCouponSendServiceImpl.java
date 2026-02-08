package com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.CouponTypeEnum;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.CouponSendService;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.annotaion.CouponType;

@CouponType(CouponTypeEnum.FULL_REDUCTION)
public class FullReductionCouponSendServiceImpl implements CouponSendService {
    @Override
    public String sendCoupon(Integer couponType) {
        return "发满减券";
    }

    @Override
    public int couponType() {
        return CouponTypeEnum.FULL_REDUCTION.getCode();
    }
}
