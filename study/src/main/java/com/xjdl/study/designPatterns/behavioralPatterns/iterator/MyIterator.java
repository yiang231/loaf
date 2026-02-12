package com.xjdl.study.designPatterns.behavioralPatterns.iterator;

import java.util.Objects;
import java.util.function.Consumer;

public interface MyIterator<T> {
    Boolean handleBefore();

    T iterate();

    void handleAfter();

    default void forEachRemaining(Consumer<T> action) {
        Objects.requireNonNull(action);
        while (handleBefore()) {
            action.accept(iterate());
            handleAfter();
        }
    }
}
