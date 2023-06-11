package com.xjdl.study.springboot.importBean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注入 鸭脖 类
 * <p>
 * 全类名方式注入 bean
 * <p>
 * <code>
 * 批量导入自动配置类
 * Import(AutoConfigurationImportSelector.class) public @interface EnableAutoConfiguration {}
 * </code>
 */
@Slf4j
public class MyImportSelector implements ImportSelector {
	/**
	 * @param importingClassMetadata 当前被 @Import 注解给标注的所有注解信息
	 * @return 要导入的组件的全类名
	 */
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[]{"com.xjdl.outBean.Mouse"};
	}
}
