package com.xjdl.study.exception.globalException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义异常测试类
 */
@Slf4j
@RestController
@RequestMapping("/exception")
public class ExceptionTest {
    @PostMapping("/post")
    public ResultResponse post() {
        throw new TrueException("-1", "抛一个业务异常");
    }

    @PutMapping("/put")
    public ResultResponse put() {
        throw new NullPointerException();
    }

    @DeleteMapping("/delete")
    public ResultResponse delete() {
        throw new ArithmeticException();
    }

    @GetMapping("/get")
    public ResultResponse get() {
        return ResultResponse.success();
    }
}
