package com.xjdl.juc.lock_8;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 8锁问题==是否持有同一把锁
 * 1. 标准访问，先打印短信还是邮件    ------短信
 * 2. 停4秒在短信方法内，先打印短信还是邮件  ----短信
 * 3. 停4秒在短信方法内     普通的hello方法，是先打短信还是hello    hello
 * 4. 停4秒在短信方法内     现在有两部手机，先打印短信还是邮件   邮件
 * --------------------是否是同一把锁的问题-------加了static表示Phone.class字节码文件，独一份，同一把锁
 * 1. 停4秒在短信方法内      两个静态同步方法，1部手机，先打印短信还是邮件  短信
 * 2. 停4秒在短信方法内      两个静态同步方法，2部手机，先打印短信还是邮件  短信	同一把锁
 * 3. 停4秒在短信方法内      1个静态同步方法，1个普通同步方法，1部手机，先打印短信还是邮件  邮件  非同一把锁
 * 4. 停4秒在短信方法内      1个静态同步方法，1个普通同步方法，2部手机，先打印短信还是邮件  邮件
 */
public class Lock_8_Demo {
	@Test
	void test() throws InterruptedException {
		Phone phone1 = new Phone();
		Phone phone2 = new Phone();
		new Thread(() -> {
			try {
				phone1.sendMsg();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "短信线程").start();
		TimeUnit.SECONDS.sleep(1);
		new Thread(() -> {
			//phone1.sendEmail();
			phone2.sendEmail();
		}, "邮件线程").start();
		//phone1.sayHello();
	}
}

