package com.xjdl.study.designPatterns.behavioralPatterns.observer.observer;

import com.xjdl.study.designPatterns.behavioralPatterns.observer.subject.Subject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinaryObserver extends Observer {
    public BinaryObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        log.info("Binary\t{}", Integer.toBinaryString(subject.getState()));
    }
}
