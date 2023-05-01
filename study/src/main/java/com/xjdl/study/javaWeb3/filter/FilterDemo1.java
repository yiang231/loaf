package com.xjdl.study.javaWeb3.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 登录请求或者资源的拦截与放行
 * 全局事物控制
 * <p>
 * 精确匹配 具体的地址
 * 模糊匹配 /*
 * 后缀匹配 *.jpg
 */
@WebFilter(filterName = "filterDemo1", urlPatterns = "/*")
@Slf4j
public class FilterDemo1 implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// filterConfig 类似于 servletConfig
		log.info("FilterDemo1 初始化");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		// 全局字符集处理
		servletRequest.setCharacterEncoding("UTF-8");
		servletResponse.setCharacterEncoding("UTF-8");
		servletResponse.setContentType("text/html;charset=UTF-8");

		log.info("FilterDemo1 放行");
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		log.info("FilterDemo1 被销毁");
	}
}
