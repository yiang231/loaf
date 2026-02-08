package com.xjdl.study.designPatterns.behavioralPatterns.strategy.context;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.CouponSendService;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy.DiscountCouponSendServiceImpl;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy.FullReductionCouponSendServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CouponSendContext {
    // 通过注入的方式获取List<CouponSendService>，再填充到Map中去
    private static final Map<Integer, CouponSendService> couponSendServiceMap = new ConcurrentHashMap<>();

    static {
        CouponSendService discountService = new DiscountCouponSendServiceImpl();
        CouponSendService fullReductionService = new FullReductionCouponSendServiceImpl();
        couponSendServiceMap.put(discountService.couponType(), discountService);
        couponSendServiceMap.put(fullReductionService.couponType(), fullReductionService);
    }

    public CouponSendService getCouponSendService(Integer code) {
        CouponSendService couponSendService = couponSendServiceMap.get(code);
        if (couponSendService == null) {
            throw new IllegalArgumentException("CouponSendService is not supported.");
        }
        return couponSendService;
    }
}
