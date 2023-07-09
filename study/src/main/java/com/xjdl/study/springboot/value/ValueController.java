package com.xjdl.study.springboot.value;

import com.xjdl.outBean.Duck;
import com.xjdl.outBean.Mouse;
import com.xjdl.study.exception.globalException.ResultResponse;
import com.xjdl.study.springboot.event.MyApplicationEvent;
import com.xjdl.study.springboot.event.MyApplicationEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/spring")
public class ValueController {
	@Autowired
	MyApplicationEventPublisher eventPublisher;
	@Value("${ver}")
	String ver;
	@Autowired
	ValueDemo valueDemo;
	@Autowired
	NewPerson newPerson;
	@Autowired
	RemoteProperties remoteProperties;
	@Autowired
	RemoteYaml remoteYaml;
	@Autowired
	Dog dog;
	@Autowired
	Cat cat;
	@Resource
	Duck duck;
	/**
	 * 引入的外部 bean ，使用 @Autowired 注解会爆红，不影响使用，可以用 @Resource 替换
	 */
	@Autowired
	Mouse mouse;
	/**
	 * 属性的全路径匹配
	 */
	@Value("${server.port}")
	private String port;

	@GetMapping("/port")
	public String value() {
		log.info("{}", valueDemo);
		log.info("{}", port);
		return port;
	}

	/**
	 * 自定义配置文件读取
	 * <p>
	 * 注入自定义事件发布器 MyApplicationEventPublisher 发布事件
	 */
	@GetMapping("/newperson")
	public ResultResponse newperson(HttpServletRequest request) {
		eventPublisher.sendEvent(new MyApplicationEvent(request));
		return ResultResponse.success(newPerson);
	}

	/**
	 * 使用 @EventListener 注解的方式感知事件
	 *
	 * @param myApplicationEvent 要感知的事件
	 */
	@EventListener
	public void event(MyApplicationEvent myApplicationEvent) {
		receiveEvent((HttpServletRequest) myApplicationEvent.getSource());
	}

	public void receiveEvent(HttpServletRequest req) {
		log.debug("{}", req);
	}

	/**
	 * 通过 @Bean 的方式注入 dog
	 */
	@GetMapping("/dog")
	public ResultResponse dog() {
		Dog a = dog;
		return ResultResponse.success(a);
	}

	/**
	 * 通过 @Import(Class<?> class) 的方式注入 cat
	 */
	@GetMapping("/cat")
	public ResultResponse cat() {
		return ResultResponse.success(cat);
	}

	@GetMapping("/remoteProperties")
	public void remoteProperties() {
		log.info(remoteProperties.getTestName());
		log.info(remoteProperties.getTestPassword());
		log.info(remoteProperties.getTestValue());
	}

	@GetMapping("/remoteYaml")
	public void remoteYaml() {
		System.out.println(ver);
		log.info(remoteYaml.getTestName());
		log.info(remoteYaml.getTestPassword());
		log.info(remoteYaml.getTestValue());
	}

	/**
	 * 通过 ImportSelector 的方式注入 mouse
	 */
	@GetMapping("/mouse")
	public ResultResponse mouse() {
		return ResultResponse.success(mouse);
	}

	/**
	 * 通过 ImportBeanDefinitionRegistrar 的方式注入 duck
	 */
	@GetMapping("/duck")
	public ResultResponse duck() {
		return ResultResponse.success(duck);
	}

	@GetMapping("/placeholder")
	public String placeholder() {
		return ver;
	}
}
