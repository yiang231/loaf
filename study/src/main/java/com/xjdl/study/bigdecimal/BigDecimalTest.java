package com.xjdl.study.bigdecimal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class BigDecimalTest {
    /**
     * 比较大小
     */
    @Test
    void t1() {
        BigDecimal bd1 = new BigDecimal(2);
        BigDecimal bd2 = new BigDecimal(2);
        BigDecimal bd3 = new BigDecimal(3);
        int a = bd2.compareTo(bd3);
        int b = bd1.compareTo(bd2);
        int c = bd3.compareTo(bd1);

        log.info("小于{} 等于{} 大于{}", a, b, c);
    }

    /**
     * 取余数
     */
    @Test
    void t2() {
        BigDecimal bd25 = new BigDecimal(25);
        BigDecimal bd6 = new BigDecimal(6);
        BigDecimal[] result = bd25.divideAndRemainder(bd6);

        log.info("结果中的元素个数为 {}", result.length);
        log.info("第一个元素商 {}", result[0]);
        log.info("第二个元素余数 {}", result[1]);
    }

    /**
     * ROUND_UP
     * 进位制：不管保留数字后面是大是小 (0 除外) 都会进 1。结果会向原点的反方向对齐，正数向正无穷方向对齐，负数向负无穷方向对齐。
     */
    @Test
    void up0() {
        log.info("{}", new BigDecimal("0.098").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("0.094").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("-0.098").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("-0.094").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("-0.090").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("0.090").setScale(2, RoundingMode.UP));
    }

    /**
     * ROUND_DOWN
     * 舍去制，截断操作，后面所有数字直接去除。结果会向原点方向对齐。
     */
    @Test
    void down1() {
        log.info("{}", new BigDecimal("0.098").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("0.094").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("-0.098").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("-0.094").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("-0.090").setScale(2, RoundingMode.UP));
        log.info("{}", new BigDecimal("0.090").setScale(2, RoundingMode.UP));
    }

    /**
     * ROUND_CEILING
     * 向正无穷方向对齐，转换为正无穷方向最接近的数值。如果为正数，行为和 ROUND_UP 一样；如果为负数，行为和 ROUND_DOWN 一样。此模式不会减少数值大小。
     */
    @Test
    void celling2() {
        log.info("{}", new BigDecimal("0.098").setScale(2, RoundingMode.CEILING));
        log.info("{}", new BigDecimal("0.094").setScale(2, RoundingMode.CEILING));
        log.info("{}", new BigDecimal("-0.098").setScale(2, RoundingMode.CEILING));
        log.info("{}", new BigDecimal("-0.094").setScale(2, RoundingMode.CEILING));
        log.info("{}", new BigDecimal("-0.090").setScale(2, RoundingMode.CEILING));
        log.info("{}", new BigDecimal("0.090").setScale(2, RoundingMode.CEILING));
    }

    /**
     * ROUND_FLOOR
     * 向负无穷方向对齐。如果为正数，行为和 ROUND_DOWN 一样；如果为负数，行为和 ROUND_UP 一样。此模式不会增加数值大小。
     */
    @Test
    void floor3() {
        log.info("{}", new BigDecimal("0.098").setScale(2, RoundingMode.FLOOR));
        log.info("{}", new BigDecimal("0.094").setScale(2, RoundingMode.FLOOR));
        log.info("{}", new BigDecimal("-0.098").setScale(2, RoundingMode.FLOOR));
        log.info("{}", new BigDecimal("-0.094").setScale(2, RoundingMode.FLOOR));
        log.info("{}", new BigDecimal("-0.090").setScale(2, RoundingMode.FLOOR));
        log.info("{}", new BigDecimal("0.090").setScale(2, RoundingMode.FLOOR));
    }

    /**
     * ROUND_HALF_UP
     * 根据保留数字后一位 >=5 进行四舍五入。如果舍弃部分的最高位大于等于 5，向原点反方向对齐，否则向原点方向对齐。
     */
    @Test
    void half_up4() {
        log.info("{}", new BigDecimal("0.095").setScale(2, RoundingMode.HALF_UP));
        log.info("{}", new BigDecimal("0.094").setScale(2, RoundingMode.HALF_UP));
        log.info("{}", new BigDecimal("-0.095").setScale(2, RoundingMode.HALF_UP));
        log.info("{}", new BigDecimal("-0.094").setScale(2, RoundingMode.HALF_UP));
        log.info("{}", new BigDecimal("-0.090").setScale(2, RoundingMode.HALF_UP));
        log.info("{}", new BigDecimal("-0.098").setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * ROUND_HALF_DOWN
     * 根据保留数字后一位 >5 进行五舍六入。如果舍弃部分的最高位大于 5，向原点反方向对齐，否则向原点方向对齐。这种模式也就是我们常说的 “五舍六入”。
     */
    @Test
    void half_down5() {
        log.info("{}", new BigDecimal("0.096").setScale(2, RoundingMode.HALF_DOWN));
        log.info("{}", new BigDecimal("0.095").setScale(2, RoundingMode.HALF_DOWN));
        log.info("{}", new BigDecimal("-0.096").setScale(2, RoundingMode.HALF_DOWN));
        log.info("{}", new BigDecimal("-0.095").setScale(2, RoundingMode.HALF_DOWN));
        log.info("{}", new BigDecimal("-0.094").setScale(2, RoundingMode.HALF_DOWN));
    }

    /**
     * ROUND_HALF_EVEN
     * 四舍六入五成双，如果舍弃部分的最高位大于等于六，或等于五并且前一位是奇数，向原点反方向对齐，否则向原点方向对齐。
     * 如果舍弃部分左边的数字为奇数，则作 ROUND_HALF_UP；如果为偶数，则作 ROUND_HALF_DOWN。
     */
    @Test
    void half_even6() {
        log.info("{}", new BigDecimal("0.095").setScale(2, RoundingMode.HALF_EVEN));
        log.info("{}", new BigDecimal("0.094").setScale(2, RoundingMode.HALF_EVEN));
        log.info("{}", new BigDecimal("-0.095").setScale(2, RoundingMode.HALF_EVEN));
        log.info("{}", new BigDecimal("-0.094").setScale(2, RoundingMode.HALF_EVEN));
        log.info("{}", new BigDecimal("-0.085").setScale(2, RoundingMode.HALF_EVEN));
        log.info("{}", new BigDecimal("-0.084").setScale(2, RoundingMode.HALF_EVEN));
        log.info("{}", new BigDecimal("-0.086").setScale(2, RoundingMode.HALF_EVEN));
    }

    /**
     * ROUND_UNNECESSARY
     * 断言请求的操作具有精确的结果，因此不需要舍入。如果对获得非精确结果的操作指定此舍入模式，则抛出 ArithmeticException。
     */
    @Test
    void unnecessary7() {
        try {
            log.info("{}", new BigDecimal("-1.000").setScale(2, RoundingMode.UNNECESSARY));
            log.info("{}", new BigDecimal("2.000").setScale(2, RoundingMode.UNNECESSARY));
            log.info("{}", new BigDecimal("-0.086").setScale(2, RoundingMode.UNNECESSARY));
        } catch (Exception e) {
            log.error("不需要舍入，否则抛出异常", e);
        }
    }
}
