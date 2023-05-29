package com.xjdl.study.aspect.proxy;

import com.xjdl.study.aspect.proxy.jdk.Subject;
import lombok.extern.slf4j.Slf4j;

/**
 * 真实类
 * 【被】代理对象
 */
@Slf4j
public class RealSubject implements Subject {
	@Override
	public void req(String value) {
		log.info("有参无返回值方法被调用了 {}", value);
	}

	@Override
	public String resp() {
		return "无参有返回值方法被调用了";
	}
}
