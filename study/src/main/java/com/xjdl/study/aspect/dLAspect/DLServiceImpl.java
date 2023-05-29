package com.xjdl.study.aspect.dLAspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 切面方法内部调用不生效
 */
@Slf4j
@Service
public class DLServiceImpl implements DLService {
    @DL
    @Override
    public String print(String str) {
        return str;
    }
}
