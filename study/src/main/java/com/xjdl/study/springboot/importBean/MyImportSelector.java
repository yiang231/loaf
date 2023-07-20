package com.xjdl.study.springboot.importBean;

import com.xjdl.study.springboot.bootstrapRegistryInitializer.MyBootstrapRegistryInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * 注入 鸭脖 类
 * 注入自定义SPI文件内容
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
		List<String> mySPI = MyBootstrapRegistryInitializer.SPI_RESULT;
		mySPI.add("com.xjdl.outBean.Mouse");
		return  mySPI.toArray(new String[mySPI.size()]);
	}
}
