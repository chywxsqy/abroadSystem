package com.ustc.project4;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ustc.project4.entity.DiscussPost;
import com.ustc.project4.service.DiscussPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Project4Application.class)
public class PostAndCommentServiceTests {

    @Autowired
    private DiscussPostService discussPostService;

    @Test
    public void testGetPostRows() {
        System.out.println(discussPostService.getDiscussPostsRows(18));
        System.out.println(discussPostService.getDiscussPostsRows(19));
        System.out.println(discussPostService.getDiscussPostsRows(0));

    }

    @Test
    public void testGetPosts() {
        Page<DiscussPost> discussPosts = discussPostService.getDiscussPosts(0, 1);
        System.out.println(discussPosts.getTotal());
        List<DiscussPost> records = discussPosts.getRecords();
        for(DiscussPost post : records) {
            System.out.println(post);
        }
    }

    @Test
    public void testInsertComment() {

    }
}
