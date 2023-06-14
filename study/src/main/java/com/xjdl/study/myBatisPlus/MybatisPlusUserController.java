package com.xjdl.study.myBatisPlus;

import com.xjdl.study.exception.globalException.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mpuser")
@Slf4j
public class MybatisPlusUserController {
	@Resource
	MybatisPlusUserService service;

	/**
	 * 自定义xml文件写复杂的SQL语句
	 */
	@GetMapping({"/selectAll", "/query/"})
	public ResultResponse selectAll() {
		return ResultResponse.success(service.selectAll());
	}

	@GetMapping("/query/{searchText}")
	public ResultResponse query(@PathVariable String searchText) {
		return ResultResponse.success(service.query(searchText));
	}

	@GetMapping("/selectById/{id}")
	public ResultResponse selectById(@PathVariable String id) {
		return ResultResponse.success(service.selectById(id));
	}

	@PostMapping("/insert")
	public ResultResponse insert(MybatisPlusUser user) {
		service.insert(user);
		return ResultResponse.success();
	}

	@DeleteMapping("/deleteById/{id}")
	public ResultResponse deleteById(@PathVariable Long id) {
		service.deleteById(id);
		return ResultResponse.success();
	}
}
