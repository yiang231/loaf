package com.xjdl.study.designPatterns.behavioralPatterns.observer.javaRaw;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Observer;

@Slf4j
public class Watcher implements Observer {
    public Watcher(Watched watched) {
        watched.addObserver(this);
        log.info("{}\thas been add an Observer\t[{}]", watched.getClass().getSimpleName(), this.getClass().getSimpleName());
    }

    public void update(Observable ob, Object arg) {
        log.info("Data has been changed to\t{}", ((Watched) ob).retrieveData());
    }
}
