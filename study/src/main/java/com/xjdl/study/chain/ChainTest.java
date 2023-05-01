package com.xjdl.study.chain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 链式调用
 * #set($paramName = $helper.getParamName($field, $project))
 * public ##
 * #if($field.modifierStatic)
 * static void ##
 * #else
 * $classSignature ##
 * #end
 * set$StringUtil.capitalizeWithJavaBeanConvention($StringUtil.sanitizeJavaIdentifier($helper.getPropertyName($field, $project)))($field.type $paramName) {
 * #if ($field.name == $paramName)
 * #if (!$field.modifierStatic)
 * this.##
 * #else
 * $classname.##
 * #end
 * #end
 * $field.name = $paramName;
 * #if(!$field.modifierStatic)
 * return this;
 * #end
 * }
 */
@Slf4j
public class ChainTest {
	@Test
	void test1() {
		log.info(new Student().setId(12).setName("zhangsan").setGender("man").toString());
	}
}
