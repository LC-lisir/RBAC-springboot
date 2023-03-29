package com.example.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.vo.Result;
import com.example.sys.entity.User;
import com.example.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lisir
 * @since 2023-03-03
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("all")
    public Result<List<User>> getAllUser() {
        List<User> list = userService.list();
        return Result.success(list, "查询成功");
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = userService.login(user);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20002, "用户名或密码错误！");
    }
    @GetMapping("/info")
   public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
//    根据token获取用户信息
        Map<String, Object> data = userService.getUserInfo(token);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20003, "用户登录信息无效，重新登陆！");

    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token")String token){
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> getUserListPage(@RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "phone", required = false) String phone,
                                     @RequestParam("pageNo") Long pageNo,
                                     @RequestParam("pageSize") Long pageSize) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(username != null, User::getUsername, username);
        wrapper.eq(phone != null, User::getPhone, phone);
        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, wrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);
    }
    @PostMapping
    public Result<?> addUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return Result.success("新增用户成功！");

    }
    @PutMapping
    public Result<?> updataUser(@RequestBody User user){
        userService.updateById(user);
        return Result.success("修改用户成功！");
    }
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }
    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
        userService.removeById(id);
        return Result.success("用户删除成功");
    }


}
