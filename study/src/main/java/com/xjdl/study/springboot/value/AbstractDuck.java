package com.xjdl.study.springboot.value;

import com.xjdl.outBean.Duck;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDuck {
	/**
	 * spring 方法注入
	 */
	@Lookup
	public abstract Duck methodInjection();
}
