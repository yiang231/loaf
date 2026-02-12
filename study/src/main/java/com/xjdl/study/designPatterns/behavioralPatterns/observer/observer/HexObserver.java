package com.xjdl.study.designPatterns.behavioralPatterns.observer.observer;

import com.xjdl.study.designPatterns.behavioralPatterns.observer.observable.Subject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HexObserver extends Observer {
    public HexObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        log.info("Hex\t{}", Integer.toHexString(subject.getState()));
    }
}
