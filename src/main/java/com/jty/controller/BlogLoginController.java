package com.jty.controller;

import com.jty.annotation.SystemLog;
import com.jty.response.ResponseResult;
import com.jty.service.BlogLoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BlogLoginController {
    @Resource
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @SystemLog(businessName = "登录")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/api/v1/auth/authenticate");
        requestDispatcher.forward(request, response);
        // return blogLoginService.login(user, session);
    }

    @PostMapping("/logout")
    @SystemLog(businessName = "注销")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }
}