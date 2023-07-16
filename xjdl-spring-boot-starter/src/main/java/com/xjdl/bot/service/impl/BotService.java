package com.xjdl.bot.service.impl;

import com.xjdl.bot.properties.BotProperties;
import com.xjdl.bot.service.IBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotService implements IBotService {
	@Autowired
	BotProperties botProperties;
	@Override
	public String greeting() {
		return botProperties.getWords();
	}
}
