package com.xjdl.study.rpc.entity;

import lombok.Data;
import org.springframework.http.HttpMethod;

/**
 * 外部　API 信息
 */
@Data
public class Api {
	String baseUrl;
	String data;
	HttpMethod method;
	String desc;
}
