package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * 实现ServletContextAware接口并重写其setServletContext方法
 */
@Component
@Slf4j
public class MyServletContextAware implements ServletContextAware {
	/**
	 * 在填充普通bean属性之后但在初始化之前调用
	 * 类似于InitializingBean.afterPropertiesSet或自定义init方法的回调
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		log.info("{}", "com.xjdl.study.init.MyServletContextAware.setServletContext");
	}
}
