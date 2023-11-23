package com.xjdl.framework.beans.factory.support;

import com.xjdl.framework.beans.factory.BeanDefinitionStoreException;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.util.StringUtils;

/**
 * @see org.springframework.context.annotation.AnnotationBeanNameGenerator
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator {
    public static final String GENERATED_BEAN_NAME_SEPARATOR = "#";
    public static final DefaultBeanNameGenerator INSTANCE = new DefaultBeanNameGenerator();

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String generatedBeanName = definition.getBeanClassName();
        if (!StringUtils.hasText(generatedBeanName)) {
            throw new BeanDefinitionStoreException("Unnamed bean definition specifies - can't generate bean name");
        }
        return uniqueBeanName(generatedBeanName, registry);
    }

    /**
     * com.xjdl.XXX#0
     * com.xjdl.XXX1#0
     * com.xjdl.XXX1#1
     */
    private String uniqueBeanName(String beanName, BeanDefinitionRegistry registry) {
        String id = beanName;
        int counter = -1;
        String prefix = beanName + GENERATED_BEAN_NAME_SEPARATOR;
        while (counter == -1 || registry.containsBeanDefinition(id)) {
            counter++;
            id = prefix + counter;
        }
        return id;
    }
}