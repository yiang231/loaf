package com.xjdl.framework.aop.framework.autoproxy;

import com.xjdl.app.service.HelloWorldService;
import com.xjdl.app.service.IService;
import com.xjdl.app.service.OutputService;
import com.xjdl.framework.context.support.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultAdvisorAutoProxyCreatorTest {
    private ClassPathXmlApplicationContext applicationContext;

    @BeforeAll
    void beforeAll() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext-AutoProxy.xml");
    }

    @AfterAll
    void afterAll() {
        applicationContext.close();
    }

    /**
     * 被切到后创建的对象以及注入的依赖对象均为代理对象
     * 使用 JDK 动态代理时，普通的属性注入已经失效
     * CGLIB 更具适用性
     */
    @Test
    void testDefaultAdvisorAutoProxyCreator() {
        IService outputService = (IService) applicationContext.getBean("outputService");
        outputService.say();
        // 测试为代理对象填充属性
        Assertions.assertEquals("output", ((OutputService) outputService).getName());
        Assertions.assertEquals("Hello World!", ((OutputService) outputService).getHelloWorldService().getText());;

        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.getOutputService();
    }
}
