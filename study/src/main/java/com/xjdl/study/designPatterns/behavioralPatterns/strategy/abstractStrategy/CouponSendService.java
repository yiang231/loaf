package com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy;

public interface CouponSendService {
    String sendCoupon(Integer couponType);

    /**
     * 对于 CouponSendServiceProxy 对象来说，这个方法意义不大，根据已知发券码来执行对应策略
     *
     * @return 券的类型码
     */
    int couponType();
}
