package com.xjdl.study.designPatterns.behavioralPatterns.iterator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseIterator implements MyIterator<Character> {
    protected final char[] letters;
    protected int index = 0;

    public BaseIterator(int len, char init) {
        log.info("Initializing {}", this.getClass().getSimpleName());
        char[] letters = new char[len];
        for (int i = 0; i < letters.length; i++) {
            letters[i] = (char) (init + i);
        }
        this.letters = letters;
    }

    public Boolean handleBefore() {
        log.info("handleBefore");
        return index < letters.length;
    }

    public Character iterate() {
        log.info("iterate");
        return letters[index];
    }

    public void handleAfter() {
        log.info("handleAfter");
        index += 1;
    }
}
