package com.xjdl.study.myBatisPlus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Data
@TableName("mp_user")
public class MybatisPlusUser {
	@TableId(type = IdType.ASSIGN_ID)//第一种 默认主键生成策略Long型，来自twitter的雪花算法，数据库中用bigint类型
//	@TableId(type = IdType.ASSIGN_UUID)//第二种 UUID去掉连接线长度32 实体类用String 数据库中用varchar至少32长度
//	@TableId(type = IdType.INPUT)//第三种 自定义主键
//	@TableId(type = IdType.AUTO)//第四种 类型中的id属性必须是整型
	private Long id;
	@TableField
	private String name;
	@TableField
	private String gender;
	@TableField
	private String address;
	@TableField
	private Integer age;
	// 标示此属性不在数据库对应表中存在
	@TableField(exist = false)
	private String exist;
	@TableField
	private String qq;
	@TableField
	private String email;
	@TableField
	private String userName;
	@TableField
	private String phone;

	//自带全自动驼峰映射
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;//insert时赋值
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;//insert以及update时赋值

	//数据库中version可以是整型或者时间类型
	@Version//表示这是乐观锁字段
	@TableField(fill = FieldFill.INSERT)
	private Integer version;

	@TableLogic//逻辑删除注解
	private Integer isDeleted;

	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		log.info("{}", uuid);
		//50183492-25a9-42ec-9a78-45c6d2b4a966
		log.info("{}", uuid.toString().length());//36
	}
}
