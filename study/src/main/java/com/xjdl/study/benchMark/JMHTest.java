package com.xjdl.study.benchMark;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Hello JMH
 */
@Slf4j
public class JMHTest {
    @Benchmark
    public String greeting() {
        return "Hello JMH";
    }

    @Test
    void jmhTest() throws RunnerException {
        new Runner(new OptionsBuilder()
                .include(JMHTest.class.getSimpleName())
                .forks(1)
                .mode(Mode.SingleShotTime)
                .timeUnit(TimeUnit.MICROSECONDS)
                .threads(Integer.parseInt(System.getenv("NUMBER_OF_PROCESSORS")))
                .build())
                .run();
    }
}
