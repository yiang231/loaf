package com.xjdl.study.rpc.restTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xjdl.study.rpc.entity.Lunardate;
import com.xjdl.study.rpc.service.RpcInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestTemplateController {
	@Autowired
	RpcInterface rpcInterface;

	@GetMapping({"/lunardate"})
	public Lunardate getLunardate() throws JsonProcessingException {
		return rpcInterface.getLunardate();
	}
}
