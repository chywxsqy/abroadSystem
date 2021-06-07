package com.ustc.project4.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ustc.project4.entity.User;
import com.ustc.project4.mapper.UserMapper;
import com.ustc.project4.util.MailClient;
import com.ustc.project4.util.Project4Constant;
import com.ustc.project4.util.Project4Util;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements Project4Constant {

    @Resource
    private UserMapper userMapper;

    @Value("${project4.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    public Map<String,Object> register(@NonNull User user) {
        Map<String,Object> map = new HashMap<>();

        if(StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg","邮箱不能为空！");
            return map;
        }

        if(StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg","昵称不能为空！");
            return map;
        }

        if(StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg","密码不能为空！");
            return map;
        }

        //验证邮箱
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", user.getEmail());
        User u = userMapper.selectOne(wrapper);
        if(u != null) {
            map.put("emailMsg","该邮箱已被注册！");
            return map;
        }

        //验证昵称
        wrapper.clear();
        wrapper.eq("username", user.getUsername());
        u = userMapper.selectOne(wrapper);
        if(u != null) {
            map.put("usernameMsg","该昵称已存在！");
            return map;
        }

        //注册
        user.setPassword(Project4Util.md5(user.getPassword()));
        user.setAddtime(new Date());
        user.setActivationCode(Project4Util.generateUUID());
        user.setAvatar(domain + "/img/th.jpg");

        userMapper.insert(user);

        //发送激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        context.setVariable("url", domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode());
        String content  = templateEngine.process("/activation", context);
//        mailClient.sendMail(user.getEmail(), "激活账号", content);  //测试环境用单线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                mailClient.sendMail(user.getEmail(), "激活账号", content);
            }
        }).start(); //由于发送邮件太慢，直接交给多线程去处理

        return map;
    }

    public int activation(int userId, String activationCode) {
        User user = userMapper.selectById(userId);
        if(user.getStatus()== 1) {
            return ACTIVATION_REPEATE;
        } else if(user.getActivationCode().equals(activationCode)) {
            user.setStatus(1);
            userMapper.updateById(user);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    public Map<String,Object> login(String email, String password) {
        Map<String, Object> map = new HashMap<>();

        //空值
        if(StringUtils.isBlank(email)) {
            map.put("emailMsg", "邮箱不能为空！");
            return map;
        }
        if(StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空！");
            return map;
        }

        //验证
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if(user == null) {
            map.put("emailMsg", "该账号不存在！");
            return map;
        }
        if(user.getStatus() == 0) {
            map.put("emailMsg", "该账号尚未激活！");
            return map;
        }
        password = Project4Util.md5(password);
        if(!password.equals(user.getPassword())) {
            map.put("passwordMsg", "密码错误！");
            return map;
        }

        //登录检测通过，返回空map
        map.put("loginUser", user);
        return map;
    }
}
