package com.xjdl.study.springboot.springApplicationRunListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

/**
 * SpringBoot 应用全阶段生命周期监听器，功能强大
 */
@Slf4j
public class MySpringApplicationRunListener implements SpringApplicationRunListener {
	/**
	 * 2.7.* 版本 必须有这个构造器
	 *
	 * @see SpringApplication#getRunListeners(String[])
	 */
	public MySpringApplicationRunListener(SpringApplication application, String[] args) {

	}

	/**
	 * run()执行，创建引导上下文，引导阶段已经开始，获取所有 SpringApplicationRunListeners 之后。
	 */
	@Override
	public void starting(ConfigurableBootstrapContext bootstrapContext) {
		// fixme 被拒绝使用日志输出 org.springframework.boot.logging.DeferredLog
		System.out.println("com.xjdl.study.springboot.springApplicationRunListener.MySpringApplicationRunListener.starting");
	}

	/**
	 * 环境准备好，但是 IOC 还没有创建
	 * <p>
	 * {@code SpringApplication#prepareEnvironment(SpringApplicationRunListeners, DefaultBootstrapContext, ApplicationArguments)}
	 */
	@Override
	public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
		log.info("{}", "com.xjdl.study.springboot.springApplicationRunListener.MySpringApplicationRunListener.environmentPrepared");
	}

	/**
	 * IOC 容器创建并准备好，启动阶段已开始，但是主配置类没加载，然后关闭引导上下文，引导阶段结束
	 * <p>
	 * {@code SpringApplication#prepareContext(DefaultBootstrapContext, ConfigurableApplicationContext, ConfigurableEnvironment, SpringApplicationRunListeners, ApplicationArguments, Banner)}
	 */
	@Override
	public void contextPrepared(ConfigurableApplicationContext context) {
		log.info("{}", "com.xjdl.study.springboot.springApplicationRunListener.MySpringApplicationRunListener.contextPrepared");
	}

	/**
	 * IOC 容器创建并准备好，主配置类加载进去了，但是 IOC 容器还没刷新。
	 * <p>
	 * {@code SpringApplication#prepareContext(DefaultBootstrapContext, ConfigurableApplicationContext, ConfigurableEnvironment, SpringApplicationRunListeners, ApplicationArguments, Banner)}
	 */
	@Override
	public void contextLoaded(ConfigurableApplicationContext context) {
		log.info("{}", "com.xjdl.study.springboot.springApplicationRunListener.MySpringApplicationRunListener.contextLoaded");
	}

	/**
	 * IOC 容器已刷新，CommandLineRunners 和 ApplicationRunners 还未被调用
	 */
	@Override
	public void started(ConfigurableApplicationContext context, Duration timeTaken) {
		log.info("{}", "com.xjdl.study.springboot.springApplicationRunListener.MySpringApplicationRunListener.started");
	}

	/**
	 * CommandLineRunners 和 ApplicationRunners 调用完毕，启动阶段结束
	 */
	@Override
	public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
		log.info("{}", "com.xjdl.study.springboot.springApplicationRunListener.MySpringApplicationRunListener.ready");
	}

	/**
	 * starting 之后，出现任何问题时
	 */
	@Override
	public void failed(ConfigurableApplicationContext context, Throwable exception) {
		log.info("{}", "com.xjdl.study.springboot.springApplicationRunListener.MySpringApplicationRunListener.failed");
	}
}
