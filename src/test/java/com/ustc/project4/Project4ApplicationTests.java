package com.ustc.project4;


import com.ustc.project4.mapper.CollegeMapper;
import com.ustc.project4.entity.College;
import com.ustc.project4.service.CollegeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Project4Application.class)
@WebAppConfiguration
class Project4ApplicationTests {

    @Autowired(required = false)
    public CollegeMapper collegeMapper;


    @Autowired(required = false)
    public CollegeService collegeService;

    @Test
    void contextLoads() {
    }


    @Test
    public void testDao(){
        List<College> collegeList = collegeService.selectByName("人",1,10);
        collegeList.forEach(System.out::println);
    }

    @Test
    public void test(){

    }


}
