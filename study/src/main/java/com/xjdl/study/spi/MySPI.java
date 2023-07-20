package com.xjdl.study.spi;

import com.xjdl.study.springboot.bootstrapRegistryInitializer.MyBootstrapRegistryInitializer;
import com.xjdl.study.springboot.importBean.MyImportSelector;
import com.xjdl.study.springboot.value.MyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 仿 SpringBoot SPI 测试类，已注入IOC容器
 *
 * @see MyBootstrapRegistryInitializer#initialize(BootstrapRegistry)
 * @see MyImportSelector#selectImports(AnnotationMetadata)
 * @see MyConfig
 */
@XJDL
@Slf4j
public class MySPI {
	public MySPI() {
		log.info("The @Import annotation be used ! {}.component load success !", XJDL.class.getName());
	}
}
