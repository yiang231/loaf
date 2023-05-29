package com.xjdl.study.aspect.dLAspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;

/**
 * 自定义的切面类
 */
@Slf4j
@Aspect
@Component
public class DLAspect {
    /**
     * execution表达式
     * <p>
     * 切入点是某包某类的无参数的方法:
     * <p>
     * execution(* com..类名.*())
     * <p>
     * 切入点是某包某类带有参数的方法
     * execution(* com..类名.*(..))
     * <p>
     * 切入点是某包某类的某个同名的所有方法
     * 示例：..  表示任意个数任意类型的参数
     * execution(* com..类名.方法名(..))
     * <p>
     * 切入点是某包下的某类的所有方法
     * 示例：*表示任意的类名，方法名，包名
     * execution(* com..类名.*(..))
     * <p>
     * 切入点是某包下的所有类的所有方法
     * 示例：* 表示任意的类名，方法名，包名
     * execution(* com..*.*(..))
     * <p>
     * execution(* *..*.*(..))
     * execution(* com..*.*(..))
     */
    @Pointcut("@annotation(com.xjdl.study.aspect.dLAspect.DL)")
    public void pointCut() {

    }

    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String kind = joinPoint.getKind();
        Object aThis = joinPoint.getThis();
        SourceLocation sourceLocation = joinPoint.getSourceLocation();
        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
        log.info("@Before 普通的前置通知执行 \t\n getTarget {}\t\n getArgs {}\t\n getSignature {}\t\n getKind {}\t\n getThis {}\t\n getSourceLocation {}\t\n getStaticPart {}\t"
                , target, args, signature, kind, aThis, sourceLocation, staticPart);
    }

    /**
     * 后置返回通知
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning：限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，
     * 对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     */
    @AfterReturning(value = "pointCut()", returning = "keys")
    public void doAfterReturningAdvice1(JoinPoint joinPoint, Object keys) {
        log.info("第一种后置返回通知的返回值：{}", keys);
    }

    @AfterReturning(value = "pointCut()", returning = "keys", argNames = "keys")
    public void doAfterReturningAdvice2(String keys) {
        log.info("第二种后置返回通知的返回值：{}", keys);
    }

    /**
     * 后置异常通知
     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     * throwing:限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     * 对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     */
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        log.info(joinPoint.getSignature().getName());
        if (exception instanceof NullPointerException) {
            log.error("发生了空指针异常!!!!!");
        }
    }

    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     */
    @After(value = "pointCut()")
    public void doAfterAdvice(JoinPoint joinPoint) {
        log.info("后置最终通知执行了!!!!");
    }

    /**
     * 环绕通知：
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around(value = "pointCut()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("环绕通知输出的目标方法签名 {}", proceedingJoinPoint.getSignature());
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            if (args == null) {
                return proceedingJoinPoint.proceed(new Object[]{"切面方法参数为空"});
            }
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
