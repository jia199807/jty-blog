package com.jty.controller;

import com.jty.annotation.SystemLog;
import com.jty.domain.entity.User;
import com.jty.response.ResponseResult;
import com.jty.service.BlogLoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {
    @Resource
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "登录")
    public ResponseResult login(@RequestBody User user, HttpSession session) {
        return blogLoginService.login(user, session);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "注销")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }
}