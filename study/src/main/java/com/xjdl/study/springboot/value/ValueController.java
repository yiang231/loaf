package com.xjdl.study.springboot.value;

import com.xjdl.outBean.Duck;
import com.xjdl.outBean.Mouse;
import com.xjdl.study.Study;
import com.xjdl.study.exception.globalException.ResultResponse;
import com.xjdl.study.rpc.config.Xjdl;
import com.xjdl.study.springboot.event.MyApplicationEvent;
import com.xjdl.study.springboot.event.MyApplicationEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/spring")
public class ValueController {
	@Autowired
	Banner springBootBanner;
	@Resource
	ConfigurableEnvironment configurableEnvironment;
	@Autowired
	List<ApplicationListener<ApplicationEvent>> applicationListeners;
	@Autowired
	MyApplicationEventPublisher eventPublisher;
	@Autowired
	Xjdl xjdl;
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
	 * 特殊的依赖注入
	 * <p>
	 * 获取容器中所有实现 ApplicationListener 接口的 bean 组件
	 */
	@GetMapping("/applicationListeners")
	public void applicationListeners() {
		applicationListeners.forEach(listener -> log.info("{}", listener.getClass().getName()));
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
		return xjdl.getVer();
	}

	@GetMapping("/banner")
	public void banner() {
		springBootBanner.printBanner(configurableEnvironment, Study.class, System.out);
	}

	/**
	 * 直接将请求变量封装到集合中
	 *
	 * @param pathVariable  ky形式存放路径变量
	 * @param requestHeader ky形式存请求头
	 * @param requestParam  ky形式存放路径变量
	 * @param list          List形式封装参数
	 * @param cookie        存放cookie
	 */
	@GetMapping("/{name}/{gender}/requestParam")
	public void get(@PathVariable Map<String, String> pathVariable
			, @RequestHeader Map<String, String> requestHeader
			, @RequestParam Map<String, String> requestParam
			, @RequestParam List<String> list
			, @CookieValue("Idea-a07204d5") String cookie) {
		log.info("{}", pathVariable);
		log.info("{}", requestHeader);
		log.info("{}", requestParam);
		log.info("{}", list);
		log.info("{}", cookie);
	}

	/**
	 * 获取表单提交内容
	 *
	 * @param content ky结构表单提交内容
	 */
	@PostMapping("/post")
	public void post(@RequestBody String content) {
		log.info("表单提交内容 {}", content);
	}
}
