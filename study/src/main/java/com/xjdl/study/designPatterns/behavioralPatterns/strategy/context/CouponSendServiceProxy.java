package com.xjdl.study.designPatterns.behavioralPatterns.strategy.context;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.CouponSendService;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.annotaion.CouponType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统一的动态代理实现，只负责把 sendCoupon(int) 的调用
 * 分派给对应的业务实现。
 */
public class CouponSendServiceProxy implements InvocationHandler {
    private final Map<Integer, CouponSendService> targetMap = new ConcurrentHashMap<>();

    public CouponSendServiceProxy(Set<CouponSendService> implementations) {
        for (CouponSendService impl : implementations) {
            CouponType anno = impl.getClass().getAnnotation(CouponType.class);
            if (anno == null) {
                throw new IllegalArgumentException("Implementation " + impl.getClass() + " must be annotated with @CouponType");
            }
            int code = anno.value().getCode();
            if (targetMap.putIfAbsent(code, impl) != null) {
                throw new IllegalStateException("Duplicate CouponSendService for code: " + code);
            }
        }
    }

    public static CouponSendService newProxyInstance(Set<CouponSendService> implementations) {
        CouponSendServiceProxy handler = new CouponSendServiceProxy(implementations);
        return (CouponSendService) Proxy.newProxyInstance(CouponSendService.class.getClassLoader(), new Class[]{CouponSendService.class}, handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("sendCoupon".equals(method.getName())
                && method.getParameterCount() == 1
                && args != null
                && args[0] instanceof Integer) {

            Integer code = (Integer) args[0];
            CouponSendService target = targetMap.get(code);
            if (target == null) {
                throw new IllegalArgumentException("Unsupported coupon type: " + code);
            }
            return method.invoke(target, args);
        }
        // 如果以后你在接口里加了别的默认方法，这里可以用反射调用默认实现
        throw new UnsupportedOperationException("Method " + method.getName() + " is not supported by proxy");
    }
}