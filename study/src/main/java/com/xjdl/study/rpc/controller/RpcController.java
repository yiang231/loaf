package com.xjdl.study.rpc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xjdl.study.rpc.entity.Lunardate;
import com.xjdl.study.rpc.service.RpcInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * rpc 通用接口
 */
@RestController
@RequestMapping("/api")
public class RpcController {
	@Autowired
	RpcInterface rpcInterface;

	/**
	 * todo 单路径，无论@PathVariable注解required值为ture或false，都不可以接收空值，如果URL没带值，则报404
	 */
	@GetMapping({"/lunardate/{date}", "/lunardate"})
	public Lunardate forLunardate(@PathVariable(required = false) String date) throws JsonProcessingException {
		return rpcInterface.forLunardate(date);
	}

	@GetMapping("/nstool")
	public String nstool() {
		return rpcInterface.nstool();
	}
}
