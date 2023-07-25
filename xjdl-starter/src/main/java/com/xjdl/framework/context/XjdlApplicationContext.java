package com.xjdl.framework.context;


import com.xjdl.framework.beans.factory.BeanNameAware;
import com.xjdl.framework.beans.factory.InitializingBean;
import com.xjdl.framework.beans.factory.annotation.Autowired;
import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.beans.factory.config.BeanPostProcessor;
import com.xjdl.framework.context.annotation.ComponentScan;
import com.xjdl.framework.context.annotation.Scope;
import com.xjdl.framework.stereotype.Component;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class XjdlApplicationContext {
    private Class<?> configClass;
    /**
     * 所有类文件信息
     */
    private List<File> classFiles = new ArrayList<>();
    /**
     * 所有类的全类名
     */
    private List<String> className = new ArrayList<>();
    /**
     * 所有实现了 BeanPostProcessor 的 bean
     */
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();
    private String scanPath;
    /**
     * 存放所有的 bean 信息
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    /**
     * 存放所有的 单例bean
     */
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    public XjdlApplicationContext(Class<?> configClass) {
        this.configClass = configClass;

        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = configClass.getAnnotation(ComponentScan.class);
            String componentValue = componentScanAnnotation.value();
            scanPath = componentValue.replace(".", "/");
            ClassLoader classLoader = getClass().getClassLoader();
            try {
                URL url = classLoader.getResource(scanPath);
                File file = new File(url.getFile());
                this.getClassFile(file);
                this.getClassName();

                for (String item : className) {
                    Class<?> clazz = null;
                    clazz = classLoader.loadClass(item);

                    if (clazz.isAnnotationPresent(Component.class)) {
                        // 类是否实现某个接口 isAssignableFrom
                        if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                            BeanPostProcessor instance = (BeanPostProcessor) clazz.newInstance();
                            beanPostProcessorList.add(instance);
                        }
                        String beanName = clazz.getAnnotation(Component.class).value();
                        // 给 bean 取一个默认的名字
                        if (beanName.equals("")) {
                            beanName = Introspector.decapitalize(clazz.getSimpleName());
                        }
                        // 设置 BeanDefinition 属性
                        BeanDefinition beanDefinition = new BeanDefinition();
                        if (clazz.isAnnotationPresent(Scope.class)) {
                            beanDefinition.setScope(clazz.getAnnotation(Scope.class).value());
                        } else {
                            beanDefinition.setScope("singleton");
                        }
                        beanDefinition.setType(clazz);
                        beanDefinitionMap.put(beanName, beanDefinition);
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        // 实例化单例 bean
        beanDefinitionMap.keySet().forEach(beanName -> {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = creatBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        });
    }

    private Object creatBean(String beanName, BeanDefinition beanDefinition) {
        Class<?> type = beanDefinition.getType();
        try {
            Object bean = type.getDeclaredConstructor().newInstance();
            // Aware 回调机制
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            // byName 依赖注入
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    field.set(bean, getBean(field.getName()));
                }
            }
            // 初始化前的操作
            for (BeanPostProcessor item : beanPostProcessorList) {
                bean = item.postProcessBeforeInitialize(beanName, bean);
            }
            // 初始化机制
            if (bean instanceof InitializingBean) {
                ((InitializingBean) bean).afterPropertiesSet();
            }
            // 初始化后的操作
            for (BeanPostProcessor item : beanPostProcessorList) {
                bean = item.postProcessAfterInitialize(beanName, bean);
            }
            return bean;
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void getClassName() {
        className = classFiles.stream().map(item -> {
            String path = item.getAbsolutePath();
            return path.substring(path.indexOf("com"), path.indexOf(".class")).replace("\\", ".");
        }).collect(Collectors.toList());
    }

    public void getClassFile(File file) {
        Arrays.stream(file.listFiles()).forEach(item -> {
            if (item.isDirectory()) {
                getClassFile(item);
            } else {
                if (item.getName().endsWith(".class")) {
                    classFiles.add(item);
                }
            }
        });
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            if (beanDefinition.getScope().equals("singleton")) {
                Object singletonBean = singletonObjects.get(beanName);
                if (singletonBean == null) {
                    singletonBean = creatBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, singletonBean);
                }
                return singletonBean;
            } else {
                return creatBean(beanName, beanDefinition);
            }
        }
    }
}
