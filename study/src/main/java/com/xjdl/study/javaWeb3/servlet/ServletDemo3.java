package com.xjdl.study.javaWeb3.servlet;


import com.xjdl.study.javaWeb3.listener.HttpSessionActivationListenerDemo;
import com.xjdl.study.javaWeb3.listener.HttpSessionBindingListenerDemo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

/**
 * 继承HttpServlet来创建Servlet
 * 使用WebServlet注解配置Servlet
 */
@WebServlet(name = "servletDemo3"
		, urlPatterns = {"/servletDemo3"}
//        , value = "/等同于urlPatterns"
//        , initParams = {
//        @WebInitParam(name = "name", value = "value")
//}
//        , loadOnStartup = 10
)
@Slf4j
public class ServletDemo3 extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("ServletDemo3 处理POST请求");
		// POST请求乱码解决
		req.setCharacterEncoding("UTF-8");

		// 获取请求参数
		for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
			log.info(entry.getKey() + ", " + Arrays.toString(entry.getValue()));
		}

		// url参数
		log.info(req.getContextPath());
		log.info("{}", req.getServerPort()); // 端口号
		log.info(req.getServerName()); // 主机名
		log.info(req.getScheme()); // 协议

		// 请求头
		log.info(req.getHeader("User-Agent"));
		log.info(req.getHeader("Referer")); // 上个页面的地址

		// Cookie
		Cookie newCookie = new Cookie("cookieName", "cookieValue");
		newCookie.setMaxAge(10);
		newCookie.setPath(req.getContextPath() + "/cookie");
		resp.addCookie(newCookie);

		if (req.getCookies() != null) {
			for (Cookie cookie : req.getCookies()) {
				log.info(cookie.getName() + "=" + cookie.getValue());
			}
		}

		// 从session中交换数据
		// 验证HttpSessionBindingListener
		HttpSessionBindingListenerDemo httpSessionBindingListenerDemo = new HttpSessionBindingListenerDemo();
		HttpSession session = req.getSession();
		httpSessionBindingListenerDemo.setName("zhangsan");
		httpSessionBindingListenerDemo.setAge("12");
		session.setAttribute("zhangsan", httpSessionBindingListenerDemo);
		session.removeAttribute("zhangsan");
		// 验证HttpSessionActivationListener
		// 配置web/META-INF/context.xml
		HttpSessionActivationListenerDemo httpSessionActivationListenerDemo = new HttpSessionActivationListenerDemo();
		httpSessionActivationListenerDemo.setName("lisi");
		httpSessionActivationListenerDemo.setId("25");
		session.setAttribute("lisi", httpSessionActivationListenerDemo);
		HttpSessionActivationListenerDemo attribute = (HttpSessionActivationListenerDemo) session.getAttribute("lisi");
		log.info(attribute.getName() + " | " + attribute.getId());

		// 请求转发 以“/”开始表示项目根路径
//		req.getRequestDispatcher("password.jsp").forward(req, resp);
		//将数据保存到request对象的属性域中
		req.setAttribute("attrName", "attrValueInRequest");
		//两个Servlet要想共享request对象中的数据，必须是转发的关系
		req.getRequestDispatcher("servletDemo2").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("ServletDemo3 处理GET请求");
		// GET请求乱码 需要在server.xml文件修改Connector标签，添加URIEncoding="utf-8"属性

		// 重定向请求 方法一
//		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
//		resp.setHeader("Location", "servletDemo2");

		// 重定向请求 方法二
		resp.sendRedirect("servletDemo2");
//		resp.sendRedirect(req.getContextPath() + "/servletDemo2");

		// 解决响应乱码 方法一
		resp.setHeader("Content-Type", "text/html;charset=UTF-8");
		// 解决响应乱码 方法二
		resp.setContentType("text/html;charset=UTF-8");

		// 数据响应
		// 上面的重定向之后，响应数据不会生效
		PrintWriter writer = resp.getWriter();
		writer.write("servlet响应数据");
		writer.close();
	}
}

