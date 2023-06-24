package com.xjdl.study.exception.globalException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 自定义异常处理器
 * <p>
 * 缺点只会拦截传递到Controller层的异常
 * <p>
 * RequestMapping("${server.error.path:${error.path:/error}}") 默认的错误处理路径
 *
 * @see ErrorMvcAutoConfiguration
 * @see DefaultErrorViewResolver#resolveErrorView(HttpServletRequest, HttpStatus, Map)
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = TrueException.class)
	public ResultResponse trueExceptionHandler(TrueException e) {
		log.error(e.getMessage(), e);
		return ResultResponse.error(e.getCode(), e.getMsg());
	}

	@ExceptionHandler(value = NullPointerException.class)
	public ResultResponse trueExceptionHandler(NullPointerException e) {
		log.error(e.getMessage(), e);
		return ResultResponse.error(ExceptionEnum.NOT_FOUND);
	}

	@ExceptionHandler(value = Exception.class)
	public ResultResponse trueExceptionHandler(Exception e) {
		log.error(e.getMessage(), e);
		return ResultResponse.error(ExceptionEnum.SERVER_BUSY);
	}
}
