package com.xjdl.study.designPatterns.behavioralPatterns.observer.observer;

import com.xjdl.study.designPatterns.behavioralPatterns.observer.subject.Subject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OctalObserver extends Observer {
    public OctalObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        log.info("Octal\t{}", Integer.toOctalString(subject.getState()));
    }
}
