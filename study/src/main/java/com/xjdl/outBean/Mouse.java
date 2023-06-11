package com.xjdl.outBean;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@Data
public class Mouse {
    String name = "我是鸭脖";
    Integer age;

    @Bean
    @ConditionalOnMissingBean
    public Mouse mouse() {
        return new Mouse();
    }
}
