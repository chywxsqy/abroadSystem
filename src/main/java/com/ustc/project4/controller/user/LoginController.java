package com.ustc.project4.controller.user;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.code.kaptcha.Producer;
import com.ustc.project4.entity.User;
import com.ustc.project4.service.UserService;
import com.ustc.project4.util.JWTUtil;
import com.ustc.project4.util.Project4Constant;
import com.ustc.project4.util.Project4Util;
import com.ustc.project4.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController implements Project4Constant {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Value("${rsa.privatekey}")
    private String privateKey;

    //Content-Type:application/x-www-form-urlencoded
    @PostMapping("/testRSA")
    @ResponseBody
    public String test(@RequestParam("password") String password) throws Exception {
        System.out.println(password);
        byte[] bytes = RSAUtil.decryptByPrivateKey(password, privateKey);
        return Project4Util.getJSONString(CODE_SUCCESS, "解密成功！", "uncrypted",new String(bytes));
    }

    @GetMapping("/testdemo")
    public String testdemo2() {
        return "/demo";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user){
        Map<String, Object> map = userService.register(user);
        if(map == null || map.isEmpty()) {
            String msg = "注册成功，我们已向您的邮箱发送了激活邮件，请尽快激活！";
            return Project4Util.getJSONString(CODE_SUCCESS, msg, null);
        } else {
            return Project4Util.getJSONString(CODE_FAILURE, null, map);
        }
    }

//    @PostMapping("/registerS")
//    @ResponseBody
//    public String registerS(@RequestParam("email") String email, @RequestParam("password") String password,
//                            @RequestParam("username") String username) {
//        try {
//            password = new String(RSAUtil.decryptByPrivateKey(password, privateKey));
//            User user = new User();
//            user.setEmail(email);
//            user.setPassword(password);
//            user.setUsername(username);
//            return register(user);
//        } catch (Exception e) {
//            return Project4Util.getJSONString(CODE_SERVER_FAILURE, "服务器解密失败，请重试！", null);
//        }
//    }

    @PostMapping("/registerS")
    @ResponseBody
    public String hello(@RequestBody User user) {
        try {
            String password = new String(RSAUtil.decryptByPrivateKey(user.getPassword(), privateKey));
            user.setPassword(password);
            return register(user);
        } catch (Exception e) {
            return Project4Util.getJSONString(CODE_SERVER_FAILURE, "服务器解密失败，请重试！", null);
        }
    }

    @GetMapping("/activation/{userId}/{activationCode}")
    @ResponseBody
    public String activation(@PathVariable("userId") int userId, @PathVariable("activationCode") String activationCode) {
        int result = userService.activation(userId, activationCode);
        int code = CODE_SUCCESS;
        String msg = "";
        if(result == ACTIVATION_SUCCESS) {
            code = CODE_SUCCESS;
            msg = "激活成功，您的账号已经可以正常使用了！";
        } else if(result == ACTIVATION_REPEATE) {
            code = CODE_FAILURE;
            msg = "该账号已被激活！";
        } else {
            code = CODE_FAILURE;
            msg = "激活失败，您提供的激活码不对！";
        }
        return Project4Util.getJSONString(code, msg, null);
    }

    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //验证码存入session
        session.setAttribute("kaptcha", text);
        //图片输出给浏览器，不用关闭流，springmvc会自动做
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            //javax的用于图片输出的工具
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            logger.error("响应验证码失败:" + e.getMessage());
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Map<String, String> params, HttpSession session) {
        String email = params.get("email");
        String password = params.get("password");
        String code = params.get("code");
        boolean rememberme = params.get("rememberme").equals("false");

        //验证码
        String kaptcha = (String) session.getAttribute("kaptcha");

        if(StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            Map<String, Object> map = new HashMap<>();
            map.put("codeMsg", "验证码不正确！");
            return Project4Util.getJSONString(CODE_FAILURE, null, map);
        }

        //账号密码
        int expiredSeconds = rememberme ? REMEMBERME_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(email, password);
        if(map.containsKey("loginUser")) {

            User loginUser = (User)map.get("loginUser");
            Map<String, String> payload = new HashMap<>();
            payload.put("id", loginUser.getId().toString());
            String token = JWTUtil.getToken(payload, expiredSeconds);

            map = new HashMap<>();
            map.put("token", token);

            //login时就先存一下认证信息
//            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, loginUser.getPassword(),
//                    loginUser.getAuthorities());
//            SecurityContextHolder.setContext(new SecurityContextImpl(authentication));

            return Project4Util.getJSONString(CODE_SUCCESS, "成功登录！", map);
        } else {
            return Project4Util.getJSONString(CODE_FAILURE, null, map);
        }
    }

    @PostMapping("/loginS")
    @ResponseBody
    public String loginS(@RequestBody Map<String, String> params, HttpSession session) {
        try{
            String password = params.get("password");
            password = new String(RSAUtil.decryptByPrivateKey(password, privateKey));
            params.put("password", password);
            return login(params, session);
        } catch (Exception e) {
            return Project4Util.getJSONString(CODE_SERVER_FAILURE, "服务器解密失败，请重试！", null);
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpSession session) {
        session.removeAttribute("SPRING_SECURITY_CONTEXT");
        SecurityContextHolder.clearContext();

        return Project4Util.getJSONString(CODE_SUCCESS, null, null);
    }
}
