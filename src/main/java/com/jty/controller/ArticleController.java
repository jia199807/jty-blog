package com.jty.controller;


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
    public List<Article> test(){
        return articleService.list();
    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}

