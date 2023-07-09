package com.xjdl.study.springboot.event;

import com.xjdl.study.springboot.value.ValueController;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;

/**
 * 应用事件
 *
 * @see ValueController#newperson(HttpServletRequest)
 * @see ValueController#event(MyApplicationEvent)
 */
public class MyApplicationEvent extends ApplicationEvent {
	public MyApplicationEvent(HttpServletRequest request) {
		super(request);
	}
}
