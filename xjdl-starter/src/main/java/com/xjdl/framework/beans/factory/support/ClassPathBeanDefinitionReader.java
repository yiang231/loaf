package com.xjdl.framework.beans.factory.support;


import com.xjdl.framework.beans.factory.config.BeanDefinition;
import com.xjdl.framework.context.annotation.Scope;
import com.xjdl.framework.context.annotation.ScopeType;
import com.xjdl.framework.core.io.ResourceLoader;
import com.xjdl.framework.exception.FrameException;
import com.xjdl.framework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;

/**
 * 在类路径下扫描并且加载 BeanDefinition
 */
@Slf4j
public class ClassPathBeanDefinitionReader extends AbstractBeanDefinitionReader {
	public static final String DEFAULT_COMPONENT_NAME = "";
	public static final String CLASS_FILE_SUFFIX = ".class";
	public static final String PACKAGE_SEPARATOR = ".";
	public static final String PATH_SEPARATOR = "/";
	public static final String NESTED_CLASS_SEPARATOR = "$";

	public ClassPathBeanDefinitionReader(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	@Override
	public void loadBeanDefinitions(String scanPackage) throws Exception {
		this.doScan(scanPackage);
		this.registerBeanDefinitions();
		this.log("扫描路径下的全类名");
	}

	private void log(String description) {
		log.debug(description);
		if (this.getRegistryComponentClassesSet().size() != 0) {
			for (String clazz : this.getRegistryComponentClassesSet()) {
				log.debug("\t[{}]", clazz);
			}
		}
	}

	public void doScan(String scanPackage) {
		// 将包名转为文件路径
		URL url = getResourceLoader().getUrl(scanPackage.replaceAll("\\.", PATH_SEPARATOR));
		File classPath = new File(url.getFile());
		File[] files = classPath.listFiles();
		if (files == null) {
			throw new FrameException("Package files is empty!");
		}
		for (File file : files) {
			String fileName = file.getName();
			if (file.isDirectory()) {
//                if (recursiveCall) {
				doScan(scanPackage + PACKAGE_SEPARATOR + fileName);
//                }
			} else {
				if (!fileName.endsWith(CLASS_FILE_SUFFIX) || fileName.contains(NESTED_CLASS_SEPARATOR)) {
					// 如果不是class文件则跳过，内部类也跳过
					continue;
				}
				String className = scanPackage + PACKAGE_SEPARATOR + fileName.replace(CLASS_FILE_SUFFIX, "");
				this.getRegistryComponentClassesSet().add(className);
			}
		}
	}

	public void registerBeanDefinitions() {
		try {
			for (String className : this.getRegistryComponentClassesSet()) {
				// FIXME servlet 相关组件会找不到，报错 java.lang.NoClassDefFoundError
				Class<?> beanClass = getResourceLoader().getContextClassLoader().loadClass(className);
				if (beanClass.getSimpleName().isEmpty()
						|| Modifier.isAbstract(beanClass.getModifiers())
						|| Modifier.isFinal(beanClass.getModifiers())
						|| beanClass.isInterface()
						|| beanClass.isEnum()
						|| beanClass.isAnnotation()) {
					continue;
				}
				// TODO 目前仅识别 @Component 注解标识的组件，对于工厂核心类组件，配置类组件暂不做实现
				if (beanClass.isAnnotationPresent(Component.class)) {
					BeanDefinition beanDefinition = createBeanDefinition(beanClass);
					this.getRegistryBeanDefinition().put(beanDefinition.getComponentName(), beanDefinition);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new FrameException(e);
		}
	}

	private ScopeType getScope(Class<?> beanClass) {
		ScopeType scopeType = ScopeType.singleton;
		if (beanClass.isAnnotationPresent(Scope.class)) {
			scopeType = beanClass.getAnnotation(Scope.class).type();
		}
		return scopeType;
	}

	private String getComponentName(Class<?> beanClass) {
		String componentName = beanClass.getAnnotation(Component.class).value();
		if (componentName.equals(DEFAULT_COMPONENT_NAME)) {
			// 借助 Java 内部实现获取组件名
			componentName = getDefaultComponentName(beanClass);
			// 默认行为重名检查
			return duplicatedNameCheck(componentName, beanClass);
		}
		// 自行定义名称的重名检查
		return duplicatedNameCheckWithValue(componentName);
	}

	/**
	 * 对于使用 @Component(value = "相同组件名") 这种方式引起的组件名重复，使用者自行处理错误
	 */
	private String duplicatedNameCheckWithValue(String componentName) {
		if (this.getBeanDefinition(componentName) != null) {
			throw new FrameException("[ " + componentName + " ]组件名重复");
		}
		return componentName;
	}

	/**
	 * FIXME 弃案，勿删除
	 */
//    private String duplicatedNameCheck(String componentName) {
//        if (this.getBeanDefinition(componentName) != null) {
//            // 0 - ff
//            componentName = componentName + "@" + Integer.toHexString(new Random().nextInt(256));
//            duplicatedNameCheck(componentName);
//        }
//        return componentName;
//    }
	private BeanDefinition createBeanDefinition(Class<?> beanClass) {
		ScopeType scope = getScope(beanClass);
		String componentName = getComponentName(beanClass);
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setScope(scope);
		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setComponentName(componentName);
		beanDefinition.setClazzName(beanClass.getName());
		return beanDefinition;
	}

	/**
	 * 对于不同包下相同短组件名的情况，解决办法是对于重复组件采用全类名的方式命名
	 */
	private String duplicatedNameCheck(String componentName, Class<?> beanClass) {
		if (this.getBeanDefinition(componentName) != null) {
			componentName = beanClass.getName();
			/*
			 * 极端情况。P代表包，Q代表全类名，S代表短名称
			 * 1、P1 ComponentA(Q P1 ComponentC)
			 * 2、P1 ComponentB(S P1 ComponentC)
			 * 3、P1 ComponentC
			 * */
			componentName = this.duplicatedNameCheckWithValue(componentName);
		}
		return componentName;
	}

	private String getDefaultComponentName(Class<?> beanClass) {
		return Introspector.decapitalize(beanClass.getSimpleName());
	}
}

