package com.xjdl.study.designPatterns.behavioralPatterns.strategy.context;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.CouponTypeEnum;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.CouponSendService;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy.DiscountCouponSendServiceImpl;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.concreteStrategy.FullReductionCouponSendServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@Slf4j
class CouponSendServiceProxyTest {
    @Test
    void testCouponSendServiceProxy() {
        Set<CouponSendService> impls = new HashSet<>();
        impls.add(new DiscountCouponSendServiceImpl());
        impls.add(new FullReductionCouponSendServiceImpl());

        CouponSendService couponSendService = CouponSendServiceProxy.newProxyInstance(impls);

        log.info("{}", couponSendService.sendCoupon(CouponTypeEnum.DISCOUNT.getCode()));
        log.info("{}", couponSendService.sendCoupon(CouponTypeEnum.FULL_REDUCTION.getCode()));

        assertThatIllegalArgumentException().isThrownBy(() -> couponSendService.sendCoupon(9));
    }
}