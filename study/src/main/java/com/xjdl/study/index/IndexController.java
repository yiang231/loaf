package com.xjdl.study.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * WelcomePage 相关
 * <p>
 * 页面直接在 static 目录下，若放到相关目录下，则需指定位置，添加配置spring.web.resources.static-locations=classpath:/
 */
@Controller
@Slf4j
public class IndexController {
	/**
	 * 获取请求域中的值
	 *
	 * @param index 请求域中的值名称
	 */
	@RequestMapping("/")
	public String toIndex(@RequestAttribute(required = false) String index) {
		if (StringUtils.hasText(index) && log.isDebugEnabled()) {
			log.debug("{}", index);
		}
		return "list.html";
	}

	@RequestMapping({"/index", "/index.html"})
	public String toIndex(HttpServletRequest request) {
		request.setAttribute("index", "toRoot");
		return "forward:/";
	}

	@RequestMapping("/login")
	public String toLogin() {
		return "login.html";
	}

	@RequestMapping("/404")
	public String toError() {
		return "/error/404.html";
	}
}
