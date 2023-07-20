package com.xjdl.study.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * WelcomePage 相关
 * <p>
 * 页面直接在 static 目录下，若放到相关目录下，则需指定位置，添加配置spring.web.resources.static-locations=classpath:/
 */
@Controller
public class IndexController {
	@RequestMapping("/")
	public String toIndex() {
		return "list.html";
	}

	@RequestMapping("/login")
	public String toLogin() {
		return "login.html";
	}
}
