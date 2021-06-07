package com.ustc.project4;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.ustc.project4.entity.User;
import com.ustc.project4.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Project4Application.class)
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("bbb");
        user.setPassword("123");
        user.setAddtime(new Date());
        user.setEmail("1234@qq.com");
        user.setActivationCode("123123");
        user.setAvatar("localhost/123123");
        userMapper.insert(user);
    }

    @Test
    public void testSelect() {
        User user = userMapper.selectById(11);
        System.out.println(user);
    }

    @Test
    public void testUpdate() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>();
//        wrapper.eq("username", "aaa");
//        User user = new User();
//        user.setUsername("bbb");
//        userMapper.update(user, wrapper);
//        System.out.println(userMapper.selectById(12));

        wrapper.eq("username", "bbb").set("username", "ccc");
        userMapper.update(null, wrapper);
        System.out.println(userMapper.selectById(12));
    }

    @Test
    public void testDelete() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", 12);
        System.out.println(userMapper.delete(wrapper));
    }

}
