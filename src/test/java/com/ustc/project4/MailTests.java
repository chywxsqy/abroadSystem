package com.ustc.project4;

import com.ustc.project4.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Project4Application.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void testMailSend() {
        mailClient.sendMail("Lanjun1998@163.com", "Test", "Test mail send");
    }

//    @Test
//    public void testHtmlMailSend() {
//        Context context = new Context();
//        context.setVariable("username", "张三");
//
//        String content = templateEngine.process("/mail/avtivation", context);
//
//        mailClient.sendMail("Lanjun1998@163.com", "Test", content);
//    }
}