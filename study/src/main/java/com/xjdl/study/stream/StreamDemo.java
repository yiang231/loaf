package com.xjdl.study.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
class User {
    private String name;
    private int age;
    private int salary;
}

/**
 * stream流操作
 * <p>
 * Lambda表达式的使用条件
 * 首先，都是接口
 * 其次，接口中有且只有一个接口，才可以使用lambda表达式
 * 1.接口中只有一个抽象方法的接口，叫做函数式接口
 * 2.如果是函数式接口，那么就可以用@FunctionalInterface注解标识
 */
@Slf4j
public class StreamDemo {
    public static final List<Integer> NUM_LIST = Arrays.asList(1, 4, 7, 3, 9);
    public static final List<String> SENTENCES_LIST = Arrays.asList("hello world", "aaa bbbb cccc dddd");
    public static final List<User> USER_LIST = Arrays.asList(
            new User("zhangsan", 23, 2000),
            new User("lisi", 43, 22000),
            new User("goushdan", 54, 12000)
    );

    /**
     * 使用peek【中间方法】进行遍历处理
     */
    @Test
    public void peek() {
        // 演示点1： 仅peek操作，最终不会执行
        log.info("----before peek----");
        SENTENCES_LIST.stream().peek(item -> log.info("{}", item));
        log.info("----after peek----");
        // 演示点2： 仅foreach操作，最终会执行
        log.info("----before foreach----");
        SENTENCES_LIST.forEach(item -> log.info("{}", item));
        log.info("----after foreach----");
        // 演示点3：peek操作后面增加终止操作，peek会执行
        log.info("----before peek and count----");
        SENTENCES_LIST.stream().peek(item -> log.info("{}", item)).count();
        log.info("----after peek and count----");
    }

    /**
     * flatMap 每个元素返回一个或者多个元素
     * <p>
     * flatMap操作的时候其实是先每个元素处理并返回一个新的Stream，然后将多个Stream展开合并为了一个完整的新的Stream
     */
    @Test
    public void flatMap() {
        // 使用流操作
        List<String> results = SENTENCES_LIST.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .collect(Collectors.toList());
        log.info("{}", results);
    }

    /**
     * 合并
     */
    @Test
    public void combine() {
        Stream.of(USER_LIST, NUM_LIST)
                .forEach(item -> log.info("{}", item));
    }

    /**
     * 跳过前几个元素
     */
    @Test
    public void skip() {
        List<User> collect = USER_LIST.stream()
                .skip(2)
                .collect(Collectors.toList());
        log.info("{}", collect);
    }

    /**
     * 每个元素返回一个结果
     */
    @Test
    public void map() {
        NUM_LIST.stream()
                .map(item -> item + 12)
                .forEach(item -> log.info("{}", item));
    }

    /**
     * 所有结果进行合并操作
     */
    @Test
    public void reduce() {
        Integer integer = NUM_LIST.stream()
//				.reduce((a, b) -> a + b)
                .reduce(Integer::sum)
                .get();
        log.info("{}", integer);
    }

    /**
     * 长度，极值，统计
     */
    @Test
    public void statistics() {
        // 返回Optional对象
        Integer integer = NUM_LIST.stream()
                .max(Integer::compareTo)
                .get();

        long count = NUM_LIST.stream().count();

        IntSummaryStatistics intSummaryStatistics = NUM_LIST.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();

        log.info("{}", intSummaryStatistics.getCount());
        log.info("{}", intSummaryStatistics.getAverage());
        log.info("{}", intSummaryStatistics.getMax());
        log.info("{}", intSummaryStatistics.getMin());
        log.info("{}", intSummaryStatistics.getSum());
        log.info("{}", count);
        log.info("{}", integer);
    }

    /**
     * 截取
     */
    @Test
    public void limit() {
        List<User> collect = USER_LIST.stream()
                .limit(2)
                .collect(Collectors.toList());
        log.info("{}", collect);
    }

    /**
     * 过滤
     */
    @Test
    public void filter() {
        Predicate<User> age = user -> user.getAge() < 50;
        Predicate<User> salary = user -> user.getSalary() > 3000;

        List<User> collect = USER_LIST.stream()
                .filter(age.and(salary))
                .collect(Collectors.toList());
        log.info("{}", collect);
    }

    /**
     * 使用 Collectors.groupingBy() 分组聚合
     * {54=[User(name=goushdan, age=54, salary=12000)], 23=[User(name=zhangsan, age=23, salary=2000)], 43=[User(name=lisi, age=43, salary=22000)]}
     */
    @Test
    void groupingBy() {
        Map<Integer, List<User>> result = USER_LIST.stream()
                .collect(Collectors.groupingBy(User::getAge));
        log.info("{}", result);
    }

    /**
     * 使用 stream.reduce() 分组聚合
     * 第一个参数 identity reduce() 方法的初始值，流为空时，作为结果返回
     * 第二个参数 accumulator 进行运算的结果以及参与运算的元素
     * 第三个参数 combiner 当归约是并行化或者累加器参数的类型和累加器实现的类型不匹配时，用于合并归约操作结果的函数
     */
    @Test
    void groupingReduce() {
        Map<Integer, List<User>> result = USER_LIST.stream()
                .reduce(new HashMap<>()
                        , (resultMap, user) -> {
                            resultMap.put(user.getAge(), Collections.singletonList(user));
                            return resultMap;
                        }
                        , (combinerMap, collectMap) -> {
//                            combinerMap.putAll(collectMap);
                            return new HashMap<>();
                        });
        log.info("{}", result);
    }
}
