package com.xjdl.study.designPatterns.behavioralPatterns.observer.observer;

import com.xjdl.study.designPatterns.behavioralPatterns.observer.observable.Subject;
import org.springframework.context.event.AbstractApplicationEventMulticaster;

/**
 * @see AbstractApplicationEventMulticaster
 */
public abstract class Observer {
    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public abstract void update();
}
