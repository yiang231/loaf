/**
 * 责任链模式：将请求的发送者和请求的处理者进行了解耦，拦截类都实现了统一的接口
 * Spring MVC HandlerExecutionChain 请求执行链， Spring Security FilterChain 过滤器链，日志记录器，多层审核
 * 缺点：责任链上成员调整是非动态的
 */
package com.xjdl.study.designPatterns.behavioralPatterns.chainOfResponsibility;