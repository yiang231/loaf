package com.xjdl.study.designPatterns.behavioralPatterns.template;

import com.xjdl.study.designPatterns.behavioralPatterns.template.common.Basketball;
import com.xjdl.study.designPatterns.behavioralPatterns.template.common.Sing;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class UseTemplate {
    @Test
    void test() {
        Game sing = new Sing();
        sing.play();

        Game basketball = new Basketball();
        basketball.play();
        basketball.remake();
    }
}
