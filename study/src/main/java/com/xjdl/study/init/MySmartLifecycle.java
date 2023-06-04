package com.xjdl.study.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * 实现 SmartLifecycle
 */
@Slf4j
@Component
public class MySmartLifecycle implements SmartLifecycle {

	public static boolean RUNNING = false;

	/**
	 * 容器启动后调用
	 */
	@Override
	public void start() {
		log.info("{}", "Lifecycle start");
		RUNNING = true;
	}

	/**
	 * 容器停止时调用
	 * 在这里处理业务逻辑
	 */
	@Override
	public void stop() {
		log.info("{}", "Lifecycle stop");
		RUNNING = true;
	}

	/**
	 * 检查组件运行状态
	 */
	@Override
	public boolean isRunning() {
//        log.info("{} {}", "Lifecycle isRunning", RUNNING);
		return RUNNING;
	}

	/**
	 * 是否希望在上下文中自动进行回调
	 *
	 * @return false 代表通过start()进行显式调用启动
	 */
	@Override
	public boolean isAutoStartup() {
		return true;
	}

	/**
	 * 容器停止时，回调该方法
	 */
	@Override
	public void stop(Runnable callback) {
		// 设置回调超时时间，默认30秒
//        DefaultLifecycleProcessor defaultLifecycleProcessor = new DefaultLifecycleProcessor();
//        defaultLifecycleProcessor.setTimeoutPerShutdownPhase(30000);

		log.info("{}", "SmartLifecycle stop");
		stop();
		// 通知容器组件已经停止
		callback.run();
	}

	/**
	 * 控制回调顺序，数字越小越先执行
	 */
	@Override
	public int getPhase() {
		return DEFAULT_PHASE;
	}
}
