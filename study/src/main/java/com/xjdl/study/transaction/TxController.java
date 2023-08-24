package com.xjdl.study.transaction;

import com.xjdl.study.springboot.value.ValueController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring/tx")
public class TxController {
	TxService txService;

	/**
	 * 构造注入
	 *
	 * @see ValueController#print()
	 */
	@Autowired
	public TxController(TxService txService) {
		this.txService = txService;
	}

	@GetMapping("/use")
	public void use() {
		// 注入 TxService 先是代理对象在执行 use() 方法，实际上是 TxService 的普通对象在执行 use() 方法
		txService.use();
	}

	@GetMapping("/useless")
	public void useless() {
		// 注入 TxService 先是代理对象在执行 useless() 方法，实际上是 TxService 的普通对象在执行 useless() 方法
		txService.useless();
	}

	@GetMapping("/extNp")
	public void extNp() {
		txService.extNp();
	}
}
