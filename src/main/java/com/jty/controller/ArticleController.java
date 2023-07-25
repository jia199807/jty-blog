package com.jty.controller;


import com.jty.annotation.SystemLog;
import com.jty.domain.entity.Article;
import com.jty.response.ResponseResult;
import com.jty.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 文章表(Article)表控制层
 *
 * @author makejava
 * @since 2023-07-21 00:16:07
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/list")
    @SystemLog(businessName = "文章列表")
    public List<Article> test(){
        return articleService.list();
    }

    @GetMapping("/hotArticleList")
    @SystemLog(businessName = "展示热门文章列表")
    public ResponseResult hotArticleList(){

        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    @SystemLog(businessName = "展示文章列表（分页）")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    @SystemLog(businessName = "获取用户详情")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}

