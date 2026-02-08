package com.xjdl.study.designPatterns.behavioralPatterns.strategy.context;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.CouponTypeEnum;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.CouponSendService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@Slf4j
class CouponSendContextTest {
    @Test
    void testCouponSendContext() {
        CouponSendContext context = new CouponSendContext();

        CouponSendService discountService = context.getCouponSendService(CouponTypeEnum.DISCOUNT.getCode());
        String discountCoupon = discountService.sendCoupon();
        log.info("discountCoupon : {}", discountCoupon);

        CouponSendService fullReductionService = context.getCouponSendService(CouponTypeEnum.FULL_REDUCTION.getCode());
        String fullReductionCoupon = fullReductionService.sendCoupon();
        log.info("fullReductionCoupon : {}", fullReductionCoupon);

        assertThatIllegalArgumentException().isThrownBy(() -> context.getCouponSendService(9));
    }
}