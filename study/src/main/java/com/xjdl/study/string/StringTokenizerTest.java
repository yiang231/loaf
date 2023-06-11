package com.xjdl.study.string;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.StringTokenizer;

/**
 * java.util.StringTokenizer
 * <p>
 * countTokens() 返回 nextToken()被调用次数
 * hasMoreTokens() 是否还有分隔符
 * hasMoreElements() 是否还有元素
 * nextToken() 下一个分隔符
 * nextElement() 下一个元素
 * <p>
 * 分隔字符串
 */
@Slf4j
public class StringTokenizerTest {
    public static StringTokenizer STRING_TOKENIZER = null;
    public static final String str1 = "How are\tyou\n?\r!\f!";
    public static final String str2 = "My ,name ,is ,Lihua .";
    public static final String str3 = "I : am : fine : thank : you : .";

    /**
     * 去除默认分隔符，换行打印
     * <p>
     * " \t\n\r\f"
     * 包括空格【""】，制表符【\t】，换行符【\n】，回车符【\r】
     */
    @Test
    @DisplayName("第一种构造器")
    void test1() {
        STRING_TOKENIZER = new StringTokenizer(str1);
        print();
    }

    /**
     * 去除指定分隔符，换行打印
     */
    @Test
    @DisplayName("第二种构造器")
    void test2() {
        STRING_TOKENIZER = new StringTokenizer(str2, " ,");
        print();
    }

    /**
     * 打印每一个字符，包含分隔符
     */
    @Test
    @DisplayName("第三种构造器")
    void test3() {
        STRING_TOKENIZER = new StringTokenizer(str3, " :", true);
        print();
    }

    private static void print() {
        while (STRING_TOKENIZER.hasMoreTokens()) {
            log.info("{}", STRING_TOKENIZER.nextToken());
        }
    }
}
