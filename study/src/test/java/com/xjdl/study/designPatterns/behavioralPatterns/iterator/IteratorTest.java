package com.xjdl.study.designPatterns.behavioralPatterns.iterator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class IteratorTest {
    @Test
    void testforr() {
        for (MyIterator<Character> iterator = new LettersIterator(); iterator.handleBefore(); iterator.handleAfter()) {
            record(iterator.iterate());
        }
    }

    @Test
    void testforEachRemaining() {
        new LettersIterator().forEachRemaining(this::record);
    }

    @Test
    void testforLoop() {
        for (Character character : new NumberIterator()) {
            record(character);
        }
    }

    @Test
    void testforEach() {
        new NumberIterator().forEach(this::record);
    }

    public <E> void record(E element) {
        log.info("record hit {}", element);
    }
}