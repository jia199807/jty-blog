package com.jty.controller;


import com.jty.annotation.SystemLog;
import com.jty.domain.entity.Comment;
import com.jty.response.ResponseResult;
import com.jty.service.CommentService;
import com.jty.system.SystemConstants;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2023-07-24 07:43:03
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/commentList")
    @SystemLog(businessName = "展示评论列表")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @PostMapping()
    @SystemLog(businessName = "添加评论")
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }


    @GetMapping("/linkCommentList")
    @SystemLog(businessName = "展示友链列表")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT, null, pageNum, pageSize);
    }


}

