package com.xjdl.study.springboot.value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/value")
public class ValueController {
    @Resource
    ValueDemo valueDemo;
    /**
     * 属性的全路径匹配
     */
    @Value("${server.port}")
    private String port;

    @GetMapping
    public String value() {
        log.info("{}", valueDemo);
        log.info(port);
        return port;
    }
}
