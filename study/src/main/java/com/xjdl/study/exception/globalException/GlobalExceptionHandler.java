package com.xjdl.study.exception.globalException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常处理器
 *
 * 缺点只会拦截传递到Controller层的异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = TrueException.class)
    @ResponseBody
    public ResultResponse trueExceptionHandler( TrueException e) {
        log.error(e.getMessage(), e);
        return ResultResponse.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResultResponse trueExceptionHandler( NullPointerException e) {
        log.error(e.getMessage(), e);
        return ResultResponse.error(ExceptionEnum.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultResponse trueExceptionHandler( Exception e) {
        log.error(e.getMessage(), e);
        return ResultResponse.error(ExceptionEnum.SERVER_BUSY);
    }
}
