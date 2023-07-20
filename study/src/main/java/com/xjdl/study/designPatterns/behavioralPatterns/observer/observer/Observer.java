package com.xjdl.study.designPatterns.behavioralPatterns.observer.observer;

import com.xjdl.study.designPatterns.behavioralPatterns.observer.subject.Subject;
import org.springframework.context.event.AbstractApplicationEventMulticaster;

/**
 * @see AbstractApplicationEventMulticaster
 */

public abstract class Observer {
    protected Subject subject;

    public abstract void update();
}
