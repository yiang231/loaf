package com.xjdl.study.sqlite3.withJpa;


import com.xjdl.study.sqlite3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sqlite3")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/init")
    public String init() {
        User user;
        for (int i = 0; i < 10; i++) {
            user = new User();
            user.setName("test" + i);
            userService.save(user);
        }
        return "初始化完成";
    }

    @GetMapping("/userByName/{username}")
    public User getUserByName(@PathVariable("username") String username) {
        return userService.getByName(username);
    }

    @GetMapping("/userById/{userid}")
    public User getUserById(@PathVariable("userid") Long userid) {
        return userService.getUserByID(userid);
    }

    @GetMapping("/page")
    public Page<User> getPage() {
        return userService.findPage();
    }

    @GetMapping("/page/{maxID}")
    public Page<User> getPageByMaxID(@PathVariable("maxID") Long maxID) {
        return userService.find(maxID);
    }

    @PutMapping("/update/{id}/{name}")
    public User update(@PathVariable Long id, @PathVariable String name) {
        return userService.update(id, name);
    }

    @PutMapping("/update/{id}")
    public int updateById(@PathVariable Long id) {
        return userService.updateById("newName", id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
