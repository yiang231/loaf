package com.xjdl.study.designPatterns.behavioralPatterns.observer;

import com.xjdl.study.designPatterns.behavioralPatterns.observer.javaRaw.Watched;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.javaRaw.Watcher;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.observer.BinaryObserver;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.observer.HexObserver;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.observer.OctalObserver;
import com.xjdl.study.designPatterns.behavioralPatterns.observer.observable.Subject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ObserverTest {
    @Test
    void testSubject() {
        Subject subject = new Subject();

        new HexObserver(subject);
        new OctalObserver(subject);
        new BinaryObserver(subject);

        subject.setState(10);
        subject.setState(50);
        subject.setState(100);
    }

    @Test
    void testRaw() {
        Watched watched = new Watched();

        new Watcher(watched);

        watched.changeData("In C, we create bugs.");
        watched.changeData("In Java, we inherit bugs.");
        watched.changeData("In Java, we inherit bugs.");
        watched.changeData("In Visual Basic, we visualize bugs.");
    }
}