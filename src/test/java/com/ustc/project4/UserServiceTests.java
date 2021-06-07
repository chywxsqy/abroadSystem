package com.ustc.project4;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ustc.project4.entity.User;
import com.ustc.project4.service.UserService;
import com.ustc.project4.util.JWTUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Project4Application.class)
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void testRegister() throws InterruptedException {
        User user = new User();
        user.setUsername("crea");
        user.setPassword("123");
        user.setEmail("Lanjun1998@163.com");
        userService.register(user);
        System.out.println(user);
//        Thread.sleep(8000); //发邮箱是多线程，测试环境时需要等待
    }

    @Test
    public void testLogin() {
        String email = "Lanjun1998@163.com";
        String password = "123";
        Map<String, Object> map = userService.login(email, password);
        System.out.println(map);

        email = "Lanjun";
        password = "123";
        map = userService.login(email, password);
        System.out.println(map);

        email = "Lanjun1998@163.com";
        password = "123456";
        map = userService.login(email, password);
        System.out.println(map);
    }

    @Test
    public void testJWT() throws InterruptedException {
        /*
        生成的token包括header、payload、signature
         */
        Map<String, String> map = new HashMap<>();
        map.put("userId", "1");
        map.put("email", "123@qq.com");
        String token = JWTUtil.getToken(map, 3);
        System.out.println(token);

        /*
        检验token正确性并获取payload
         */
//        使用错误的token会抛异常
//        token += "hi";
//        try {
//            JWTUtil.verify(token);
//        } catch (SignatureVerificationException e) {
//            System.out.println("token无效！");
//        }
//        token过期也会抛异常
        Thread.sleep(5000);
        DecodedJWT decodedToken = JWTUtil.getDecodedToken(token);
        System.out.println(decodedToken.getClaim("userId").asString());
        System.out.println(decodedToken.getClaim("email").asString());
        System.out.println(decodedToken.getExpiresAt());
    }
}
