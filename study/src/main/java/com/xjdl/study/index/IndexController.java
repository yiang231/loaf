package com.xjdl.study.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


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
