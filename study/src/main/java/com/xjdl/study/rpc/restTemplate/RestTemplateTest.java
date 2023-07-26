package com.xjdl.study.rpc.restTemplate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用RestTemplate发起请求
 */
@Slf4j
@SpringBootTest
public class RestTemplateTest {
	@Autowired
	RestTemplate restTemplate;
	@Value("${server.port}")
	Integer port;

	/**
	 * GET请求
	 * 支持占位符参数
	 */
	@Test
	void get() {
		String url = "http://localhost:" + port + "/jpa/userByName/{username}";

		Map<String, String> multiValueMap = new HashMap<>();
		multiValueMap.put("username", "MaruyamaHana");

//        // http://localhost:8080/jpa/userByName/%7Busername%7D?username=MaruyamaHana
//        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
//        multiValueMap.set("username", "MaruyamaHana");
//        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
//        URI uri = uriComponentsBuilder.queryParams(multiValueMap).build().encode().toUri();
//        log.info("{}", uri);

		String body = restTemplate.getForObject(url, String.class, multiValueMap);
		log.info(body);

		ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class, multiValueMap);
		log.info("{}", entity.getStatusCode());
		entity.getHeaders().forEach((key, value) -> log.info("{} = {}", key, value));
		log.info(entity.getBody());
	}

	/**
	 * POST请求
	 */
	@Test
	void post() {
		String url = "http://localhost:" + port + "/exception/post";

		// 模拟表单请求
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.set("a", "a");
		multiValueMap.set("b", "b");
		multiValueMap.set("c", "c");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(multiValueMap, headers);

		String result = restTemplate.postForObject(url, request, String.class);
		log.info(result);

		ResponseEntity<String> entity = restTemplate.postForEntity(url, request, String.class);
		log.info("{}", entity.getStatusCode());
		entity.getHeaders().forEach((key, value) -> log.info("{} = {}", key, value));
		log.info(entity.getBody());

		URI uri = restTemplate.postForLocation(url, request);
		log.info("{}", uri);
	}
}
