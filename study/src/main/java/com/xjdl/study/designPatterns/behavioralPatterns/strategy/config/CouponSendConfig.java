package com.xjdl.study.designPatterns.behavioralPatterns.strategy.config;

import com.xjdl.study.designPatterns.behavioralPatterns.strategy.abstractStrategy.CouponSendService;
import com.xjdl.study.designPatterns.behavioralPatterns.strategy.context.CouponSendServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;

@Configuration
public class CouponSendConfig {
    @Bean
    public CouponSendService couponSendService(@Autowired List<CouponSendService> implList) {
        // implList 包含所有被 Spring 扫描到的实现类
        return CouponSendServiceProxy.newProxyInstance(new HashSet<>(implList));
    }
}