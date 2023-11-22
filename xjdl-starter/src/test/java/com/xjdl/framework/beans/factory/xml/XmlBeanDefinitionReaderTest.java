package com.xjdl.framework.beans.factory.xml;

import com.xjdl.app.service.OutputService;
import com.xjdl.framework.beans.factory.support.BeanDefinitionReader;
import com.xjdl.framework.beans.factory.support.DefaultListableBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
class XmlBeanDefinitionReaderTest {
    @Test
    void testXmlBDR() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        BeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);
        int loaded = xmlBeanDefinitionReader.loadBeanDefinitions("classpath:application.xml");
        Assertions.assertTrue(loaded > 0);

        factory.getBean("outputService");
        OutputService outputService = (OutputService) factory.getBean("outputService");
        outputService.output("output");

        log.info(Arrays.toString(factory.getBeanDefinitionNames()));
        // fixme 为什么只会输出一个结果？
        log.info("{}", factory.getBeanDefinitionNames());
    }
}
