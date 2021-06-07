package com.ustc.project4;


import com.ustc.codegenerate.mapper.CollegeMapper;
import com.ustc.codegenerate.service.CollegeMyService;
import com.ustc.codegenerate.service.impl.CollegeMyServiceImpl;
import com.ustc.codegenerate.entity.College;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Project4Application.class)
@WebAppConfiguration
class Project4ApplicationTests {

    @Autowired(required = false)
    public CollegeMapper collegeMapper;


    @Autowired(required = false)
    public CollegeMyServiceImpl collegeMyServiceimpl;

    @Test
    void contextLoads() {
    }


    @Test
    public void testDao(){
//        CollegeMyServiceImpl cml = new CollegeMyServiceImpl(collegeMapper);
        List<College> colleges = collegeMyServiceimpl.selectAllInfo();
        colleges.forEach(System.out::println);
    }

    @Test
    public void test(){

    }


}
