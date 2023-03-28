package com.dl.test.javaWeb3.servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 继承GenericServlet来创建Servlet
 * 使用web.xml配置Servlet
 */
public class ServletDemo2 extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("ServletDemo2 服务执行");
        // 从request属性域中获取数据
        String attribute = (String) servletRequest.getAttribute("attrName");
        PrintWriter writer = servletResponse.getWriter();
        // fixme 读取文件响应写回找不到文件
        writer.write("attribute = " + attribute);
        writer.close();
    }
}
