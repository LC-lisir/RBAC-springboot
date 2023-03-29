package com.example;

import com.example.sys.entity.User;
import com.example.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SpringbootAdminApplicationTests {
    @Resource
    private UserMapper userMapper;
    @Test
    void testMapper() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

    }

}
