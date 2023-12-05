package com.xjdl.framework.aop.framework.autoproxy;

import com.xjdl.app.service.HelloWorldService;
import com.xjdl.app.service.IService;
import com.xjdl.framework.context.support.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.AfterAll;
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
        // 预实例化后并没有进入单例池
        IService outputService = (IService) applicationContext.getBean("outputService");
        outputService.say();

        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.getOutputService();
    }
}