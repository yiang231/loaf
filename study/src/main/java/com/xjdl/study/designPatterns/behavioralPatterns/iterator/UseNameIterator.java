package com.xjdl.study.designPatterns.behavioralPatterns.iterator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseNameIterator {
    @Test
    void test() {
        Container repository = new NameRepository();

        for (Iterator iterator = repository.getIterator(); iterator.hasNext(); ) {
            log.info("{}", iterator.next());
        }
    }
}
