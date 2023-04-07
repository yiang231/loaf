package com.dl.test.javaWeb3.servlet;

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
public class ServletDemo1 implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("ServletDemo1 初始化");

        String servletName = servletConfig.getServletName();
        System.out.println("servletName = " + servletName);

        ServletContext servletContext = servletConfig.getServletContext();
        System.out.println("上下文对象 = " + servletContext);
        String contextPath = servletContext.getContextPath();
        System.out.println("上下文路径 = " + contextPath);
        String realPath = servletContext.getRealPath("/index.html");
        System.out.println("真实路径 = " + realPath);
        String servletContextInitParameter = servletContext.getInitParameter("key");
        System.out.println("全局初始化参数 = " + servletContextInitParameter);

        String initParameter = servletConfig.getInitParameter("name1");
        System.out.println("servlet初始化参数 = " + initParameter);
        Enumeration<String> initParameterNames = servletConfig.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String key = initParameterNames.nextElement();
            System.out.println(key);
            System.out.println(servletConfig.getInitParameter(key));
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("ServletDemo1 服务执行");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("ServletDemo1 销毁");
    }
}
