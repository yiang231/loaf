package com.xjdl.study.springboot.value;

import com.xjdl.outBean.Duck;
import com.xjdl.outBean.Mouse;
import com.xjdl.study.exception.globalException.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@SpringBootTest
@RequestMapping("/value")
public class ValueController {
    @Autowired
    ValueDemo valueDemo;
    @Autowired
    NewPerson newPerson;
    @Autowired
    RemoteProperties remoteProperties;
    @Autowired
    RemoteYaml remoteYaml;
    @Autowired
    Dog dog;
    @Autowired
    Cat cat;
    @Resource
    Duck duck;
    /**
     * 引入的外部 bean ，使用 @Autowired 注解会爆红，不影响使用，可以用 @Resource 替换
     */
    @Autowired
    Mouse mouse;
    /**
     * 属性的全路径匹配
     */
    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String value() {
        log.info("{}", valueDemo);
        log.info("{}", port);
        return port;
    }

    /**
     * 自定义配置文件读取
     */
    @GetMapping("/newperson")
    public ResultResponse newperson() {
        log.info("{}", newPerson);
        return ResultResponse.success(newPerson);
    }

    /**
     * 通过 @Bean 的方式注入 dog
     */
    @GetMapping("/dog")
    public ResultResponse dog() {
        Dog a = dog;
        return ResultResponse.success(a);
    }

    /**
     * 通过 @Import(Class<?> class) 的方式注入 cat
     */
    @GetMapping("/cat")
    public ResultResponse cat() {
        return ResultResponse.success(cat);
    }

    @Test
    void remoteProperties() {
        log.info(remoteProperties.getTestName());
        log.info(remoteProperties.getTestPassword());
        log.info(remoteProperties.getTestValue());
    }

    @Test
    void remoteYaml() {
        log.info(remoteYaml.getTestName());
        log.info(remoteYaml.getTestPassword());
        log.info(remoteYaml.getTestValue());
    }

    /**
     * 通过 ImportSelector 的方式注入 mouse
     */
    @GetMapping("/mouse")
    public ResultResponse mouse() {
        return ResultResponse.success(mouse);
    }

    /**
     * 通过 ImportBeanDefinitionRegistrar 的方式注入 duck
     */
    @GetMapping("/duck")
    public ResultResponse duck() {
        return ResultResponse.success(duck);
    }
}
