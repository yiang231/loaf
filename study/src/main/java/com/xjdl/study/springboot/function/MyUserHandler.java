package com.xjdl.study.springboot.function;

import com.xjdl.study.jackson.JacksonUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class MyUserHandler {
	public ServerResponse getUser(ServerRequest serverRequest) {
		String id = serverRequest.pathVariable("id");
		log.info("要查询的用户id为 {}", id);
		return ServerResponse.ok().body(new JacksonUser(Long.parseLong(id), "zhangsan"));
	}

	public ServerResponse deleteUser(ServerRequest serverRequest) {
		String id = serverRequest.pathVariable("id");
		log.info("要删除的用户id为 {}", id);
		return ServerResponse.ok().build();
	}

	public ServerResponse getUsers(ServerRequest serverRequest) {
		List<JacksonUser> jacksonUserList = Arrays.asList(
				new JacksonUser(1L, "a")
				, new JacksonUser(2L, "b")
		);
		return ServerResponse.ok().body(jacksonUserList);
	}

	public ServerResponse saveUser(ServerRequest serverRequest) throws ServletException, IOException {
		JacksonUser user = serverRequest.body(JacksonUser.class);
		log.info("保存用户信息 {}", user);
		return ServerResponse.ok().build();
	}

	public ServerResponse updateUser(ServerRequest serverRequest) throws ServletException, IOException {
		JacksonUser user = serverRequest.body(JacksonUser.class);
		log.info("更新用户信息 {}", user);
		return ServerResponse.ok().build();
	}
}
