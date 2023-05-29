package com.xjdl.study.aspect.dLAspect;

import com.xjdl.study.exception.globalException.ResultResponse;
import lombok.extern.slf4j.Slf4j;
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
//        return ResultResponse.success(dLService.print(null));
        return ResultResponse.success(dLService.print("DLTest"));
    }
}
