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

        CouponTypeEnum discount = CouponTypeEnum.DISCOUNT;
        CouponSendService discountService = context.getCouponSendService(discount.getCode());
        String discountCoupon = discountService.sendCoupon(discount.getCode());
        log.info("discountCoupon : {}", discountCoupon);

        CouponTypeEnum fullReduction = CouponTypeEnum.FULL_REDUCTION;
        CouponSendService fullReductionService = context.getCouponSendService(fullReduction.getCode());
        String fullReductionCoupon = fullReductionService.sendCoupon(fullReduction.getCode());
        log.info("fullReductionCoupon : {}", fullReductionCoupon);

        assertThatIllegalArgumentException().isThrownBy(() -> context.getCouponSendService(9));
    }
}