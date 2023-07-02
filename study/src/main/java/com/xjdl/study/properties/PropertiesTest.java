package com.xjdl.study.properties;

import com.xjdl.study.utils.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @see Properties
 */
@Slf4j
public class PropertiesTest {
	/**
	 * properties写入文件
	 */
	@Test
	void writeProperties() {
		try (FileOutputStream fileOutputStream = new FileOutputStream(MyUtils.getResourcePath("properties/System.properties"), false);) {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
			Properties properties = getSystemProperties();
			properties.setProperty("1+1", "2");
			properties.store(outputStreamWriter, null);
			log.info("{}", "系统配置信息写入完成，在 target 文件夹下查看");
		} catch (IOException e) {
			log.error("{}", e.getMessage());
		}
	}

	/**
	 * 读取文件的配置
	 */
	@Test
	void readProperties() {
		String exist = "org.quartz.jobStore.class";
		String noexist = "zhangsan";

		try {
			FileReader fileReader = new FileReader(MyUtils.getResourcePath("quartz.properties"));
			Properties properties = new Properties();
			properties.load(fileReader);
			log.info("{}={}", exist, properties.getProperty(exist));
			log.info("{}={}", noexist, properties.getProperty(noexist, "lisi"));
			properties.list(System.out);
		} catch (IOException e) {
			log.error("{}", e.getMessage());
		}
	}

	/**
	 * 获取系统配置
	 */
	@Test
	void systemProperties() {
		getSystemProperties().list(System.out);
	}

	private static Properties getSystemProperties() {
		return System.getProperties();
	}
}
