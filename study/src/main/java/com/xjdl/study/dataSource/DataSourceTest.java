package com.xjdl.study.dataSource;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@SpringBootTest
@Slf4j
public class DataSourceTest {
	@Resource
	DataSource dataSource;

	@Test
	public void contextLoads() {
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();

			log.info("数据源 {}", dataSource.getClass());
			log.info("连接 {}", connection);
			log.info("连接地址 {}", metaData.getURL());
			log.info("驱动名称 {}", metaData.getDriverName());
			log.info("驱动版本 {}", metaData.getDriverVersion());
			log.info("数据库名称 {}", metaData.getDatabaseProductName());
			log.info("数据库版本 {}", metaData.getDatabaseProductVersion());
			log.info("连接用户名称 {}", metaData.getUserName());
			log.info("数学函数 {}", metaData.getNumericFunctions());
			log.info("日期时间函数 {}", metaData.getTimeDateFunctions());
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
	}
}
