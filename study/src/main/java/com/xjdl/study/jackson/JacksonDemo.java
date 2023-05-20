package com.xjdl.study.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjdl.study.utils.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@Slf4j
public class JacksonDemo {
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * JSON 转对象
	 */
	@Test
	void test1() {
		String json = "[\n" +
				"  {\n" +
				"    \"id\": 3,\n" +
				"    \"name\": \"Virginia Roberts\"\n" +
				"  },\n" +
				"  {\n" +
				"    \"id\": 7,\n" +
				"    \"name\": \"Harry Salazar\"\n" +
				"  }\n" +
				"]\n";
		try {
			List<JacksonUser> jacksonUserList = objectMapper.readValue(json, new TypeReference<List<JacksonUser>>() {
			});
			log.info("读取json字符串转为对象 {}", jacksonUserList);

			Page page = objectMapper.readValue(new File(MyUtils.getResourcePath("json/page.json")), Page.class);
			log.info("读取json文件转为对象 {}", page);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 对象转 JSON
	 */
	@Test
	void test2() {
		try {
			JacksonUser JacksonUser = new JacksonUser(21L, "zhangsan");
			String userStr = objectMapper.writeValueAsString(JacksonUser);
			byte[] userBytes = objectMapper.writeValueAsBytes(JacksonUser);
			log.info("对象转字节数组 {}", userBytes);
			log.info("对象转字符串 {}", userStr);

			objectMapper.writeValue(new File(MyUtils.getResourcePath("json/user.json")), JacksonUser);
			log.info("对象转字符串，并且写出文件成功");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
