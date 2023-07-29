package com.xjdl.study.pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式匹配问题
 */
@Slf4j
public class PatternDemo {
    /**
     * 匹配到就返回
     */
    @Test
    void find() {
        Pattern compile = Pattern.compile("([a-z]+)(\\d+)");
        Matcher matcher = compile.matcher("aaa2223bb");

        log.info("{}", matcher.find()); // true 是否匹配到
        log.info("{}", matcher.groupCount()); // 2 匹配到的子字符串组数
        log.info("{}", matcher.start(1)); // 0 第一组匹配到的子字符串 第一位 在文本中的索引位置
        log.info("{}", matcher.end(2)); // 7 第二组匹配到的子字符串 最后一位 在文本中的索引位置
        log.info("{}", matcher.group()); // aaa2223 完整匹配结果
        log.info("{}", matcher.group(0)); // aaa2223 完整匹配结果
        log.info("{}", matcher.group(1)); // aaa 第一组匹配到的子字符串
        log.info("{}", matcher.group(2)); // 2223 第一组匹配到的子字符串
    }

    /**
     * 完全匹配结果
     * fixme error字符串调用 find() 不能匹配两个结果
     */
    @Test
    void matches() {
        String input = "src='https://xjdl.eu.org/'";
        String error = "src='https://yjdl.eu.org/' & src='https://baidu.com/'";
        Matcher matchers = Pattern.compile("src='(http[^']+)'").matcher(input);

//        log.info("{}", matchers.replaceAll("aa")); // input 内容替换为 aa
        log.info("{}", matchers.matches());
        log.info("{}", matchers.groupCount());
        log.info("{}", matchers.group(1)); // https://xjdl.eu.org/
        log.info("{}", matchers.group()); // src='https://xjdl.eu.org/'
    }
}
