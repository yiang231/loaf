package com.xjdl.study.lambda;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda表达式的使用条件
 * 首先，都是接口
 * 其次，接口中有且只有一个接口，才可以使用lambda表达式
 * 1.接口中只有一个抽象方法的接口，叫做函数式接口
 * 2.如果是函数式接口，那么就可以用@FunctionalInterface注解标识
 */
@Slf4j
@Data
public class LambdaTest {
    private List<String> list = Arrays.asList("1", "2", "3");
    /**
     * 常见用法
     */
    @Test
    void use() {
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                log.info(s);
            }
        });

        list.forEach(s -> {
            log.info(s);
        });

        list.forEach(s -> log.info(s));

        list.forEach(log::info);
    }

    public void log(String str) {
        log.info(str);
    }

    /**
     * 生产型接口
     * 无参有返回值
     */
    @Test
    void supplier() {
        log(supplier(() -> "Supplier<T> T get()"));
    }

    <E> E supplier(Supplier<E> supplier) {
        return supplier.get();
    }

    /**
     * 消费型接口
     * 有参无返回值
     */
    @Test
    void consumer() {
        consumer(log::info, "Consumer<T> void accept(T t)");

        consumer(consumerAndThen(integer -> {
            integer += 2;
            log.info("{}", integer);
        }, integer -> {
            integer *= 3;
            log.info("{}", integer);
        }), 43);
    }

    <E> void consumer(Consumer<E> consumer, E obj) {
        consumer.accept(obj);
    }

    /**
     * raw 接收处理一次
     * after 接收处理一次
     */
    <E> Consumer<E> consumerAndThen(Consumer<E> raw, Consumer<E> after) {
        return raw.andThen(after);
    }

    /**
     * 函数型接口
     * 接收一个类型值，返回一个类型值
     */
    @Test
    void function() {
        log(function(s1 -> s1, "Function<T, R> R apply(T t)"));

        log(function(Function.identity(), "Function<T, R> static <T> Function<T, T> identity()"));

        log(function(functionAndThen(Integer::valueOf, integer -> {
            integer *= integer;
            return String.valueOf(integer);
        }), "100"));

        log(function(functionCompose(new Function<Object, String>() {
            @Override
            public String apply(Object o) {
                return String.valueOf(o);
            }
        }, integer -> integer + 3), 32));
    }

    <E, F> F function(Function<E, F> function, E e) {
        return function.apply(e);
    }

    /**
     * raw先，after后，接收处理
     */
    <F, E, R> Function<E, F> functionAndThen(Function<E, R> raw, Function<R, F> after) {
        return raw.andThen(after);
    }

    /**
     * before先，raw后，接收处理
     */
    <E, T, F> Function<E, F> functionCompose(Function<T, F> raw, Function<E, T> before) {
        return raw.compose(before);
    }

    /**
     * 断言型接口
     * 接收参数，返回判断结果
     */
    @Test
    void predicate() {
        log.info("{}", predicate(integer -> integer == 0, 0));

        log.info("{}", predicate(predicateNegate(integer -> integer != 0), 0));
    }

    <T> boolean predicate(Predicate<T> predicate, T t) {
        return predicate.test(t);
    }

    <T> Predicate<T> predicateNegate(Predicate<T> predicate) {
        return predicate.negate();
    }
}
