package com.xjdl.study.rpc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xjdl.study.rpc.entity.Lunardate;
import reactor.core.publisher.Mono;

public interface RpcInterface {
	Lunardate getLunardate() throws JsonProcessingException;

	Mono<String> getLunardate(String date);

	/**
	 * 方法重载，返回值不同，方法参数名不同都不算
	 */
	Lunardate forLunardate(String date) throws JsonProcessingException;

	String nstool();
}
