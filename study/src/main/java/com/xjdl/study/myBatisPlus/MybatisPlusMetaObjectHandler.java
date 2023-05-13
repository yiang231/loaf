package com.xjdl.study.myBatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatisplus自动填充功能
 */
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
	@Override
	//调用insert时调用，为当前对象某些属性自动赋值
	public void insertFill(MetaObject metaObject) {
		log.info("start insert fill ....");
		this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
		this.strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
		this.strictInsertFill(metaObject, "version", Integer.class, 0);
	}

	@Override
	//调用updateById时调用，为当前对象某些属性赋值
	public void updateFill(MetaObject metaObject) {
		log.info("start update fill ....");
		this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class);
	}
}
