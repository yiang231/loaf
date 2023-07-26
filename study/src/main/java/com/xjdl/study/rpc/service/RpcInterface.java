package com.xjdl.study.rpc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xjdl.study.rpc.entity.Lunardate;
import reactor.core.publisher.Mono;

public interface RpcInterface {
	Lunardate getLunardate() throws JsonProcessingException;

	Mono<String> getLunardate(String date);
}
