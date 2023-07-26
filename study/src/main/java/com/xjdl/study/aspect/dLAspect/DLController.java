package com.xjdl.study.aspect.dLAspect;

import com.xjdl.study.exception.globalException.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/aspect")
public class DLController {
    @Resource
    DLService dLService;

    @GetMapping("/dLTest")
    public ResultResponse dLTest() {
        // 如何获得 bean 代理对象的普通对象，普通对象不会被切面切到
        DLService target = (DLService) AopProxyUtils.getSingletonTarget(dLService);
        log.info("{}", target.print("DLService true target DLServiceImpl"));
//        return ResultResponse.success(dLService.print(null));
        return ResultResponse.success(dLService.print("DLTest"));
    }
}
