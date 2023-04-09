package com.xjdl.study.javaWeb3.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 实现Servlet来创建Servlet
 * 实现ServletContainerInitializer来配置Servlet
 */
@Slf4j
public class ServletDemo1 implements Servlet {
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		log.info("ServletDemo1 初始化");

		String servletName = servletConfig.getServletName();
		log.info("servletName = {}", servletName);

		ServletContext servletContext = servletConfig.getServletContext();
		log.info("上下文对象 = {}", servletContext);
		String contextPath = servletContext.getContextPath();
		log.info("上下文路径 = {}", contextPath);
		String realPath = servletContext.getRealPath("/index.html");
		log.info("真实路径 = {}", realPath);
		String servletContextInitParameter = servletContext.getInitParameter("key");
		log.info("全局初始化参数 = {}", servletContextInitParameter);

		String initParameter = servletConfig.getInitParameter("name1");
		log.info("servlet初始化参数 = {}", initParameter);
		Enumeration<String> initParameterNames = servletConfig.getInitParameterNames();
		while (initParameterNames.hasMoreElements()) {
			String key = initParameterNames.nextElement();
			log.info(key);
			log.info(servletConfig.getInitParameter(key));
		}
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		log.info("ServletDemo1 服务执行");
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void destroy() {
		log.info("ServletDemo1 销毁");
	}
}
