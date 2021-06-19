package com.ustc.project4.controller.discusspost;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ustc.project4.entity.Comment;
import com.ustc.project4.entity.DiscussPost;
import com.ustc.project4.entity.User;
import com.ustc.project4.service.CommentService;
import com.ustc.project4.service.DiscussPostService;
import com.ustc.project4.service.UserService;
import com.ustc.project4.util.HostHolder;
import com.ustc.project4.util.Project4Constant;
import com.ustc.project4.util.Project4Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class DiscussPostController implements Project4Constant {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/discussPost", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addDiscussPost(@RequestBody DiscussPost post) {
        User user = hostHolder.getUser();
        post.setUserId(user.getId());
        post.setCreateTime(new Date());
        discussPostService.saveDiscussPost(post);
        return Project4Util.getJSONString(CODE_SUCCESS, "发帖成功！", null);
    }


    @GetMapping(value = "/discussPosts/{userId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getDiscussPosts(@PathVariable("userId") int userId, @RequestParam("target") int target) {
        Page<DiscussPost> discussPostsPage = discussPostService.getDiscussPosts(userId, target);
        List<DiscussPost> discussPosts = discussPostsPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total", discussPostsPage.getTotal());
        List<Map<String, Object>> postsAndUsers = new ArrayList<>();
        for(DiscussPost post : discussPosts) {
            Map<String, Object> postAndUser = new HashMap<>();
            User user = userService.getUserById(post.getUserId());
            postAndUser.put("discussPost", post);
            postAndUser.put("user", user);
            postsAndUsers.add(postAndUser);
        }
        map.put("discussPosts", postsAndUsers);
        return Project4Util.getJSONString(CODE_SUCCESS, "ok!", map);
    }

    @GetMapping(value = "/discussPost/{discussPostId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, @RequestParam("target") int target) {
        Map<String, Object> map = new HashMap<>();

        //帖子信息
        DiscussPost discussPost = discussPostService.getDiscussPostById(discussPostId);
        map.put("discussPost", discussPost);

        //对于作者信息，可以使用下面的多次查询，也可以在mybatis处进行联合查询（使用mybatis对联合查询的支持）
        User user = userService.getUserById(discussPost.getUserId());
        map.put("user", user);

        //评论信息

        Page<Comment> commentPage = commentService.getCommentByEntity(ENTITY_TYPE_POST, discussPost.getId(), target);
        map.put("commentCount", commentPage.getTotal());   //评论总数
        List<Comment> commentList = commentPage.getRecords();

        //评论：给帖子的评论
        //回复：给评论的评论
        List<Map<String, Object>> commentVoList = new ArrayList<>();
        if(commentList != null && !commentList.isEmpty()) {
            for(Comment comment : commentList) {
                //评论Vo
                Map<String, Object> commentVo = new HashMap<>();
                //评论
                commentVo.put("comment",comment);
                //评论的作者
                commentVo.put("user",userService.getUserById(comment.getUserId()));

                //回复列表
                Page<Comment> replyPage = commentService.getCommentByEntity(ENTITY_TYPE_COMMENT, comment.getId(), -1);
                List<Comment> replyList = replyPage.getRecords();
                //回复Vo列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if(replyList != null && !replyList.isEmpty()) {
                    for(Comment reply : replyList) {
                        Map<String, Object> replyVo = new HashMap<>();
                        //回复
                        replyVo.put("reply", reply);
                        //作者
                        replyVo.put("user",userService.getUserById(reply.getUserId()));
                        //回复目标，可能是普通回复无目标，也可能有目标
                        User targetUser = reply.getTargetId() == 0 ? null :
                                userService.getUserById(reply.getTargetId());
                        replyVo.put("targetUser", targetUser);

                        replyVoList.add(replyVo);
                    }
                }
                commentVo.put("replys", replyVoList);

                //回复数量
                commentVo.put("replyCount",replyPage.getTotal());

                commentVoList.add(commentVo);
            }
        }

        map.put("comments",commentVoList);

        return Project4Util.getJSONString(CODE_SUCCESS, null, map);
    }
}
