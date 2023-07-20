package com.xjdl.study.designPatterns.behavioralPatterns.observer;

import com.xjdl.study.designPatterns.behavioralPatterns.observer.javaRaw.Watched;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.javaRaw.Watcher;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.observer.BinaryObserver;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.observer.HexObserver;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.observer.OctalObserver;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.subject.Subject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Observer;

@Slf4j
public class UseObserver {
    @Test
    void test1() {
        Subject subject = new Subject();

        new HexObserver(subject);
        new OctalObserver(subject);
        new BinaryObserver(subject);

        subject.setState(12);
        subject.setState(99);
        subject.setState(43);
    }

    @Test
    void test2() {
        Watched watched = new Watched();

        Observer watcher = new Watcher(watched);

        watched.changeData("In C, we create bugs.");
        watched.changeData("In Java, we inherit bugs.");
        watched.changeData("In Java, we inherit bugs.");
        watched.changeData("In Visual Basic, we visualize bugs.");
    }
}
