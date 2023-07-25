package com.jty.controller;

import com.jty.annotation.SystemLog;
import com.jty.response.ResponseResult;
import com.jty.service.LinkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(businessName = "展示友链")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
