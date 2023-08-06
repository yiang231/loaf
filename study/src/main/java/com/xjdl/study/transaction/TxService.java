package com.xjdl.study.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 事务失效
 * <p>
 * controller 调用 service 中的方法，实际是先进入代理类，执行切面逻辑，开启事务，修改属性，它会解析方法上的注解，事务注解会起作用
 * <p>
 * 然后执行 service 普通对象的 useless 方法，执行插入语句，再执行 ext 方法，此时一直是普通对象在执行，ext 方法上的注解不会起作用
 */
@Configuration(proxyBeanMethods = true)
public class TxService {
	public static final String DML = "insert into mp_user (name, age, email, update_time, create_time, address, gender, qq, phone, user_name) values ('hello', '12', '21@qq.com', current_timestamp, current_timestamp, 'aaa', '男', '211', '985','hello');";
	/**
	 * autocommit 默认为 true
	 * <p>
	 * 加了 @Transactional 注解 AOP 逻辑会修改此属性
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;
	/**
	 * 编程式事务，更小粒度地控制事务的范围，更直观
	 */
	@Autowired
	TransactionTemplate transactionTemplate;
	/**
	 * 自己注入自己，用注入的对象调用有事务注解的方法，会执行 AOP 逻辑，事务生效
	 * <p>
	 * 或者注入别的 bean 对象，将 ext() 方法转移到该 bean 中，目的是执行代理对象的 AOP 逻辑
	 * <p>
	 * 或者 AopContext.currentProxy();
	 */
	@Autowired
	@Lazy
	TxService txService;

	@Transactional
	public void useless() {
		jdbcTemplate.execute(DML);
		// 事务失效，没有异常出现
		// TxService 的普通对象在执行 ext() 方法，@Transactional(propagation = Propagation.NEVER) 注解不会生效
		ext();
	}

	@Transactional
	public void use() {
		jdbcTemplate.execute(DML);
		//  事务生效 Existing transaction found for transaction marked with propagation 'never'
		// TxService 的代理对象在执行 ext() 方法
		txService.ext();

		// 当前对象的代理对象
//		AopContext.currentProxy();
	}

	@Transactional(propagation = Propagation.NEVER) // throw an exception if a transaction exists
	public void ext() {
		jdbcTemplate.execute(DML);
	}

	/**
	 * 使用 @Configuration 注解，保证拿到同一事务管理数据源
	 */
	@Transactional
	public void extNp() {
		jdbcTemplate.execute(DML);
		// 没有 @Configuration 注解时，直接调用该方法，会插入数据，并且抛出异常
		throw new NullPointerException();
	}
}
