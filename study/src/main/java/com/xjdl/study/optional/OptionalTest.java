package com.xjdl.study.optional;

import com.xjdl.study.exception.globalException.TrueException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * java.util.Optional使用
 */
@Slf4j
public class OptionalTest {
    public static final Optional<String> OBJ = Optional.ofNullable("lisi");
    public static final Optional<Object> NULL_OBJ = Optional.ofNullable(null);

    /**
     * 创建Optional对象
     * 使用of()方法传入空值会出异常
     * 一般用ofNullable()
     */
    @Test()
    void create() {
        Optional<Object> ofNullable = NULL_OBJ;
        Assertions.assertThrows(NullPointerException.class
                , () -> Optional.of(null)
                , "of方法传入null时，会出NPE");
    }

    /**
     * 获取Optional对象，使用get()方法
     * 配合ifPresent()使用，不为空时获取
     */
    @Test
    void get() {
        log.info("{}", OBJ.get());
        OBJ.ifPresent(o -> log.info(OBJ.get()));

        NULL_OBJ.ifPresent(o -> log.info("{}", NULL_OBJ.get()));

        Assertions.assertThrows(NoSuchElementException.class
                , NULL_OBJ::get
                , "传入null时，获取对象会出异常");
    }

    /**
     * 避免空指针，为空时返回orElse()中的值
     * 即使不为空，也会执行orElse中的代码
     */
    @Test
    void orElse() {
        log.info("{}", OBJ.orElse(returnStr()));
        Object orElse = NULL_OBJ.orElse(returnStr());
        log.info("{}", orElse);
    }

    /**
     * orElse() 辅助测试方法
     */
    public String returnStr() {
        log.info("{}", "orElse中的方法调用");
        return "orElse";
    }

    /**
     * 避免空指针，为空时返回orElseGet()中的值
     * 为空时才会执行orElseGet()中的代码
     */
    @Test
    void orElseGet() {
        log.info("{}",
                OBJ.orElseGet(() -> {
                    log.info("orElseGet 元素不为空时获得元素值");
                    return "orElseGet";
                }));

        log.info("{}",
                NULL_OBJ.orElseGet(() -> {
                    log.info("orElseGet 元素为空时获得orElseGet返回的值");
                    return "orElseGetNull";
                }));
    }

    /**
     * 为空时抛出自定义的异常
     */
    @Test
    void orElseThrow() throws Throwable {
        log.info("{}",
                OBJ.orElseThrow((Supplier<Throwable>) () -> {
                    throw new TrueException();
                }));
        Assertions.assertThrows(NoSuchElementException.class
                , () -> log.info("{}",
                        NULL_OBJ.orElseThrow((Supplier<Throwable>) () -> {
                            throw new TrueException("抛出 NullPointerException, NoSuchElementException 之外的异常");
                        }))
                , "获取不到时出现异常");
    }
}
