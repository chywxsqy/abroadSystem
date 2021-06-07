package com.ustc.project4;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ustc.project4.mapper")
public class Project4Application {

    public static void main(String[] args) {
        SpringApplication.run(Project4Application.class, args);
        if (args != null) {
            for (String arg : args) {
                System.out.println("arg = " + arg);
            }
        }
        System.out.println("args = " + args);
    }



}
