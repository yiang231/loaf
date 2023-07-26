package com.xjdl.study.rpc.webClient;

import com.xjdl.study.rpc.service.RpcInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * webclient https 请求处理
 */
@RestController
@RequestMapping("/api")
public class WebClientController {
	@Autowired
	RpcInterface rpcInterface;

	/**
	 * todo 单路径，无论@PathVariable注解required值为ture或false，都不可以接收空值，如果URL没带值，则报404
	 */
	@GetMapping({"/lunardate/{date}"})
	public Mono<String> getLunardate(@PathVariable(required = false) String date) {
		return rpcInterface.getLunardate(date);
	}
}
