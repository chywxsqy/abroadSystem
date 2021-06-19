package com.ustc.project4;

import com.ustc.project4.entity.DiscussPost;
import com.ustc.project4.mapper.DiscussPostMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Project4Application.class)
public class PostAndCommentMapperTests {

    @Resource
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testIntert() {
        for(int i = 0; i < 20; i++) {
            DiscussPost post = new DiscussPost();
            post.setUserId(18);
            post.setTitle("测试帖子hello");
            post.setContent("第一条帖子");
            post.setCreateTime(new Date());
            discussPostMapper.insert(post);
        }
    }

}
