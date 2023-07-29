package com.xjdl.study.springboot.value;

import com.xjdl.study.springboot.importBean.MyImportBeanDefinitionRegistrar;
import com.xjdl.study.springboot.importBean.MyImportSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * 通过配置类注入Bean
 * <p>
 * proxyBeanMethods参数设置为true时即为：Full 全模式。 该模式下注入容器中的同一个组件无论被取出多少次都是同一个bean实例，即单实例对象，在该模式下SpringBoot每次启动都会判断检查容器中是否存在该组件
 * <p>
 * proxyBeanMethods参数设置为false时即为：Lite 轻量级模式。该模式下注入容器中的同一个组件无论被取出多少次都是不同的bean实例，即多实例对象，在该模式下SpringBoot每次启动会跳过检查容器中是否存在该组件
 */
@Slf4j
@Configuration(proxyBeanMethods = true)
@Import({
        com.xjdl.study.springboot.value.Cat.class
        , MyImportSelector.class
        , MyImportBeanDefinitionRegistrar.class
})
// 在配置类上使用此注解可以直接注入一个属性绑定好的Bean，一般用于第三方组件的配置绑定。原本的实体类就不用 @Component
// 和 @Import 使用场景都是导入第三方的组件，区别是是否进行了属性绑定
@EnableConfigurationProperties(value = NewPerson.class)
public class MyConfig {
    /**
     * bean的名称默认是方法名
     */
    @Bean("miniDog")
    @Scope("prototype")
    public Dog dog() {
        Dog dog = new Dog();
        dog.setName("duke");
        dog.setAge(3);
        return dog;
    }
}
