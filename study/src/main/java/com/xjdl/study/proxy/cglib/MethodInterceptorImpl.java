package com.xjdl.study.proxy.cglib;

import com.xjdl.study.proxy.RealSubject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
@Data
@AllArgsConstructor
public class MethodInterceptorImpl implements MethodInterceptor {
	public RealSubject realSubject;

	/**
	 * invokeSuper调用的是被代理类的方法, 但只有代理类才存在基类, 必须使用代理类作为obj参数调用
	 * invoke调用的是增强方法, 必须使用被代理类的对象调用, 使用代理类会造成OOM
	 *
	 * @param proxy       代理对象，CGLib动态生成的代理类实例
	 * @param method      目标对象的方法，上文中实体类所调用的被代理的方法引用
	 * @param args        目标对象方法的参数列表，参数值列表
	 * @param methodProxy 代理对象的方法，生成的代理类对方法的代理引用
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		log.info("前置方法执行");
		log.info("代理类的实例化对象 {}", proxy.getClass());
//		这里必须传入代理对象，因为目标对象不能强转成代理类，而抛出ClassCastException异常
//		这里猜测可能代理类生成的class的这个方法中就是通过super.xxx() 直接调用父类方法
		Object invoke = methodProxy.invokeSuper(proxy, args);// 代理对象，代理对象的方法
//		这里的obj应该传入的是目标对象或者其它目标对象(能调用当前这个方法的)
//		而绝对不能是代理对象，因为如果是代理对象，那么因为代理对象重写了这个方法，那么代理对象就会执行重写的方法，
//		而重写的方法又去调用MethodInterceptor里的方法，MethodInterceptor里的方法又调用这个方法，
//		这个方法又因为传过来的是代理对象，就会又调用代理对象重写的方法，从而出现死循环的情况
//		Object invoke = methodProxy.invoke(realSubject, args);// 被代理对象，代理对象的方法
//		Object invoke = method.invoke(realSubject, args);// 被代理对象，原方法
		log.info("后置方法执行");
		return invoke;
	}
}
