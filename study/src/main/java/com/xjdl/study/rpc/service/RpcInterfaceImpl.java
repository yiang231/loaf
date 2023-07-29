package com.xjdl.study.rpc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjdl.study.rpc.config.Xjdl;
import com.xjdl.study.rpc.entity.Api;
import com.xjdl.study.rpc.entity.Lunardate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class RpcInterfaceImpl implements RpcInterface {
	// 当年第一天
	public static final LocalDate FIRST_DAY_OF_YEAR = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
	// 当年最后一天
	public static final LocalDate LAST_DAY_OF_YEAR = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
	@Autowired
	WebClient webClient;
	@Autowired
	Xjdl xjdl;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public Lunardate forLunardate(String date) throws JsonProcessingException {
		Api lunardate = xjdl.getApis().get("lunardate");
		String baseUrl = lunardate.getBaseUrl();
		String queryParam = lunardate.getData();
		String result;
		if (StringUtils.isEmpty(date)) {
			ResponseEntity<String> entity = restTemplate
					.getForEntity(getUri(baseUrl, queryParam, LocalDate.now()), String.class);
			result = entity.getBody();
		} else {
			Mono<String> ret = webClient.method(lunardate.getMethod())
					.uri(getUri(baseUrl, queryParam, getValidDate(date)))
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(String.class);
			result = ret.block(Duration.ofSeconds(5));
		}
		return objectMapper.readValue(result, Lunardate.class);
	}

	@Override
	public String nstool() {
		Api nstool = xjdl.getApis().get("nstool");
		String ret = restTemplate.getForEntity(getUri(nstool.getBaseUrl()), String.class).getBody();
		Matcher matcher = Pattern.compile("(http[^']+)").matcher(ret);
		boolean matchesResult = matcher.find();
		String url = matcher.group();
		if (log.isDebugEnabled()) {
			log.debug("[{}] url[{}] match {} completely!", nstool, url, matchesResult ? "success" : "failed");
		}
		String back = webClient.method(nstool.getMethod())
				.uri(getUri(url))
				.accept(MediaType.TEXT_HTML)
				.retrieve()
				.bodyToMono(String.class)
				.block(Duration.ofSeconds(10));
		return back.replace("网易", "星际大陆");
	}

	private URI getUri(String baseUrl) {
		return getUriComponentsBuilder(baseUrl)
				.build().encode().toUri();
	}

	private URI getUri(String baseUrl, String queryParam, Object variables) {
		return getUriComponentsBuilder(baseUrl)
				.queryParam(queryParam, variables)
				.build().encode().toUri();
	}

	private UriComponentsBuilder getUriComponentsBuilder(String baseUrl) {
		return UriComponentsBuilder.fromUriString(baseUrl);
	}

	@Override
	public Lunardate getLunardate() throws JsonProcessingException {
		Api lunardate = xjdl.getApis().get("lunardate");
		URI uri = getUriComponentsBuilder(lunardate.getBaseUrl())
				.queryParam(lunardate.getData(), LocalDate.now())
				.build().encode().toUri();
		ResponseEntity<String> entity = restTemplate.getForEntity(uri, String.class);
		return objectMapper.readValue(entity.getBody(), Lunardate.class);
	}

	@Override
	public Mono<String> getLunardate(String date) {
		Api lunardate = xjdl.getApis().get("lunardate");
		Mono<String> result = webClient.get()
				.uri(lunardate.getBaseUrl(), uriBuilder -> uriBuilder
						.queryParam(lunardate.getData(), getValidDate(date))
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(String.class);
		return result;
	}

	private LocalDate getValidDate(String date) {
		LocalDate today = LocalDate.now();
		// 获取所有的数字
		String digitsDate = StringUtils.getDigits(date);
		if (StringUtils.isEmpty(digitsDate)) {
			return today;
		}
		if (digitsDate.length() > 8) {
			digitsDate = digitsDate.substring(0, 8);
		}
		// 字符串转日期
		LocalDate validDate;
		try {
			validDate = LocalDate.parse(digitsDate, DateTimeFormatter.BASIC_ISO_DATE);
		} catch (DateTimeParseException exception) {
			throw new RuntimeException("您输入了有误的查询日期，请按照" + today + "的格式输入。", exception);
		}
		if (validDate.isBefore(FIRST_DAY_OF_YEAR) || validDate.isAfter(LAST_DAY_OF_YEAR)) {
			throw new IllegalArgumentException("系统暂不支持当年以外的日期查询。");
		}
		// 日期转字符串
//		validDate.format(DateTimeFormatter.BASIC_ISO_DATE);
		return validDate;
	}
}
