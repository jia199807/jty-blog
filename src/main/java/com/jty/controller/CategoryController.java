package com.jty.controller;

import com.jty.annotation.SystemLog;
import com.jty.response.ResponseResult;
import com.jty.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @SystemLog(businessName = "分类列表")
    public ResponseResult getCategoryList(){
       return categoryService.getCategoryList();
    }
}