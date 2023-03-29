package com.example.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sys.entity.Menu;
import com.example.sys.entity.User;
import com.example.sys.mapper.UserMapper;
import com.example.sys.service.IMenuService;
import com.example.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lisir
 * @since 2023-03-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;
@Autowired
    private IMenuService menuService;
    @Override
    public Map<String, Object> login(User user) {
//        根据用户名和密码查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());
        User loginUser =  this.baseMapper.selectOne(wrapper);
        if (loginUser!=null){
//            生成Token，暂时用uuid
            String key = "user:"+ UUID.randomUUID();
            //存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,1, TimeUnit.DAYS);
            //返回数据
            HashMap<String,Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
//        如果结果不为空，则生成Token,并将用户信息存入redis
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj!=null){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);
            HashMap<String,Object> data = new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());
//          查询角色
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);
//          查询权限列表
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList",menuList);
            return  data;
        }

        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

}
