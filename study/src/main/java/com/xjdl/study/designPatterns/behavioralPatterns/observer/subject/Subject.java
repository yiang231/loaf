package com.xjdl.study.designPatterns.behavioralPatterns.observer.subject;

import com.xjdl.study.designPatterns.behavioralPatterns.observer.observer.Observer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.event.AbstractApplicationEventMulticaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @see SpringApplication#asUnmodifiableOrderedSet(Collection)
 * @see AbstractApplicationEventMulticaster
 */
@Slf4j
public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void notifyAllObservers() {
        observers.forEach(Observer::update);
    }

    public void attach(Observer observer) {
        observers.add(observer);
        log.info("{}\thas been add an Observer\t[{}]", observers.getClass().getSimpleName(), observer.getClass().getSimpleName());
    }
}
