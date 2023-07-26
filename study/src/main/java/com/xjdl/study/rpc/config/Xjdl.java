package com.xjdl.study.rpc.config;

import com.xjdl.study.rpc.entity.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@ConfigurationProperties("xjdl")
@Component
public class Xjdl {
	Map<String, Api> apis;
	String ver;

	public List<String> getApiNames() {
		return new ArrayList<>(apis.keySet());
	}
}

