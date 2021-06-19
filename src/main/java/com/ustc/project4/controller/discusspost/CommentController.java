package com.ustc.project4.controller.discusspost;

import com.ustc.project4.entity.Comment;
import com.ustc.project4.entity.DiscussPost;
import com.ustc.project4.entity.User;
import com.ustc.project4.service.CommentService;
import com.ustc.project4.util.HostHolder;
import com.ustc.project4.util.Project4Constant;
import com.ustc.project4.util.Project4Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController implements Project4Constant {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addDiscussPost(@RequestBody Comment comment) {
        User user = hostHolder.getUser();
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.saveComment(comment);
        return Project4Util.getJSONString(CODE_SUCCESS, "评论成功！", null);
    }
}
