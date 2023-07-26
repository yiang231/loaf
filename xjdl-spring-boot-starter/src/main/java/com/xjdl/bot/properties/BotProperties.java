package com.xjdl.bot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "xjdl.bot")
public class BotProperties {
	private String words;
}
