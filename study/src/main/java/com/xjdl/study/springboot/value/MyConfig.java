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
 */
@Slf4j
@Configuration
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
