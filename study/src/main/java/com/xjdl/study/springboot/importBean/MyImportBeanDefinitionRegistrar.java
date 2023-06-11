package com.xjdl.study.springboot.importBean;

import com.xjdl.outBean.Duck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注入 鼠鼠 类
 * <p>
 * 手动注册 bean 到IOC容器
 */
@Slf4j
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * @param importingClassMetadata annotation metadata of the importing class
     * @param registry               current bean definition registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 定义bean的信息
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Duck.class);
        rootBeanDefinition.setScope("prototype");
        // 指定bean的名字，注册bean
        registry.registerBeanDefinition("thisIsMouse", rootBeanDefinition);
    }
}
