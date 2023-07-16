package com.xjdl.bot.controller;

import com.xjdl.bot.service.IBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/bot")
public class BotController {
	@Autowired
	IBotService botService;

	@RequestMapping("/greeting")
	public String greeting() {
		String words = botService.greeting();
		log.info("{}", words);
		return "<h1 style=\"text-align: center\">" + words + "</h1>";
	}
}
