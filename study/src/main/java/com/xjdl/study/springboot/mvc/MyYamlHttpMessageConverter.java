package com.xjdl.study.springboot.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 实现返回yaml格式的消息协商
 * 同时添加 spring.mvc.contentnegotiation 相关配置
 * @see WebMvcConfigurationSupport
 */
//@Configuration
public class MyYamlHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
	private ObjectMapper objectMapper; //把对象转成yaml

	public MyYamlHttpMessageConverter() {
		//告诉SpringBoot这个MessageConverter支持哪种媒体类型
		super(new MediaType("text", "yaml", StandardCharsets.UTF_8));
		YAMLFactory yamlFactory = new YAMLFactory()
				// 去除yaml文档分隔符 ---
				.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
		this.objectMapper = new ObjectMapper(yamlFactory);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		//只要是对象类型，不是基本类型
		return true;
	}

	@Override  // @RequestBody
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return null;
	}

	@Override // @ResponseBody 把对象怎么写出去
	protected void writeInternal(Object methodReturnValue, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		//try-with写法，自动关流
		try (OutputStream outputStream = outputMessage.getBody()) {
			this.objectMapper.writeValue(outputStream, methodReturnValue);
		}
	}
}
