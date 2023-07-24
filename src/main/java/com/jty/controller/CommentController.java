package com.jty.controller;


import com.jty.domain.entity.Comment;
import com.jty.response.ResponseResult;
import com.jty.service.CommentService;
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
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(articleId, pageNum, pageSize);
    }

    @PostMapping()
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }


}

