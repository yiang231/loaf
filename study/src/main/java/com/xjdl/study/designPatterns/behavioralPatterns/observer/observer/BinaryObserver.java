package com.xjdl.study.designPatterns.behavioralPatterns.observer.observer;

import com.xjdl.study.designPatterns.behavioralPatterns.observer.observable.Subject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinaryObserver extends Observer {
    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        log.info("Binary\t{}", Integer.toBinaryString(subject.getState()));
    }
}
