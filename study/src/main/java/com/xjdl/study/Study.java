package com.xjdl.study;

import com.xjdl.bot.annotaion.EnableBot;
import com.xjdl.study.log.Log4j2OutputStream;
import com.xjdl.study.springboot.applicationListener.MyApplicationListener;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.LogManager;

/**
 * proxyTargetClass为true则是基于类的代理将起作用（需要cglib库），为false或者省略这个属性，则标准的JDK基于接口的代理将起作用
 * <p>
 * 四大资源文件夹访问顺序，配置项 spring.web.resources.static-locations
 * <p>
 * org.springframework.boot.autoconfigure.web.WebProperties.Resources#CLASSPATH_RESOURCE_LOCATIONS 更新至 2.7.13 版本
 * <p>
 * "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"
 * <p>
 * {@code org.springframework.context.support.AbstractApplicationContext#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)} Bean工厂的后置处理器加载自动配置类以及主程序下的组件
 * {@code @Import(AutoConfigurationImportSelector.class)} 扫描 SPI 文件里的自动配置类
 * {@code @Import(AutoConfigurationPackages.Registrar.class)} 导入主程序包下的组件
 * {@code org.springframework.context.annotation.ComponentScan} 按照规则扫描组件，排除已经扫描到的配置类
 * {@code org.springframework.context.support.AbstractApplicationContext#finishBeanFactoryInitialization(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)} 创建所有扫描到的组件
 * <p>
 *
 * @see AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory)
 * @see AutoConfigurationImportSelector#getCandidateConfigurations(AnnotationMetadata, AnnotationAttributes)
 * @see ImportCandidates#load(Class, ClassLoader)
 * @see AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)
 */
@ServletComponentScan(basePackages = {
		"com.xjdl.study.javaWeb"
		, "com.xjdl.study.init"
		, "com.xjdl.study.undertow"
})
@SpringBootApplication
//默认访问当前包及其子包下的注解
@EnableTransactionManagement
@MapperScan({
		"com.xjdl.study.myBatisPlus.mapper"
})
@CrossOrigin
@EnableScheduling
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = false)
@EnableCaching
@EnableBot
public class Study {
	public static final String LOG4J2_PROPERTIES = "log4j2-spring.xml";

	public static void main(String[] args) {
//		setLog4j2PrintStream();
		SpringApplication springApplication = new SpringApplication(Study.class);
		springApplication.addListeners(new MyApplicationListener());
		// IOC 容器
		ConfigurableApplicationContext ioc = springApplication.run(args);
		log.info("{}", PACKAGE_COMMON.PACKAGE_INFO_CONST);
		log.info("validate @Scope(\"prototype\") {}", ioc.getBean("miniDog") == ioc.getBean("miniDog"));
	}

	/**
	 * System.out 输出到 log4j2
	 */
	private static void setLog4j2PrintStream() {
		try {
			LogManager.getLogManager().readConfiguration(Study.class.getClassLoader().getResourceAsStream(LOG4J2_PROPERTIES));
		} catch (IOException e) {
			log.error("{} file not found !", LOG4J2_PROPERTIES);
		}
		System.setOut(new PrintStream(new Log4j2OutputStream(log), true));
	}
	/**
	 * FluentBuilder API
	 */
//	public static void main(String[] args) {
//		new SpringApplicationBuilder().sources(Study.class).run(args);
//	}
}
