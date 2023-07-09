package com.xjdl.study.javaWeb3.servletContainerInitializer;


import com.xjdl.study.javaWeb3.servlet.ServletDemo1;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import java.util.HashMap;
import java.util.Set;

/**
 * Servlet容器 最先被加载
 * 同时需要创建好路径以及文件    META-INF/services/javax.servlet.ServletContainerInitializer
 * 内容是实现ServletContainerInitializer的类的全类名
 * <p>
 * 这是 Java SPI思想 动态地发现和加载组件 的实现
 */
@Slf4j
@HandlesTypes(value = {}) // 里面的参数传到了set中，是ServletContainerInitializer可以处理的类型
public class ServletContainerInitializerDemo1 implements ServletContainerInitializer {
	@Override
	public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
		log.info("Servlet容器启动时操作");
		log.info("set = " + set + ", servletContext = " + servletContext);
		// 注册组件
		ServletRegistration.Dynamic servletDemo1 = servletContext.addServlet("servletDemo1", new ServletDemo1());
		// 配置映射信息
		servletDemo1.addMapping("/servletDemo1");
		// 创建时机，不配置创建时机则会懒加载
//        servletDemo1.setLoadOnStartup(10);
		servletDemo1.setInitParameters(new HashMap<String, String>() {{
			put("name1", "value1");
			put("name2", "value2");
		}});

//        ServletRegistration.Dynamic servletDemo2 = servletContext.addServlet("servletDemo2", new ServletDemo2());
//        servletDemo2.addMapping("/servletDemo2");

//        ServletRegistration.Dynamic servletDemo3 = servletContext.addServlet("servletDemo3", new ServletDemo3());
//        servletDemo3.addMapping("/servletDemo3");
	}
}
