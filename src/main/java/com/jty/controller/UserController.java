// package com.jty.controller;
//
//
// import com.jty.annotation.SystemLog;
// import com.jty.domain.entity.User;
// import com.jty.response.ResponseResult;
// import com.jty.service.UserService;
// import jakarta.annotation.Resource;
// import org.springframework.web.bind.annotation.*;
//
// /**
//  * 用户表(User)表控制层
//  *
//  * @author makejava
//  * @since 2023-07-22 03:44:28
//  */
// @RestController
// @RequestMapping("user")
// public class UserController {
//     @Resource
//     private UserService userService;
//
//     @GetMapping("/userInfo")
//     @SystemLog(businessName = "展示用户信息")
//     public ResponseResult userInfo() {
//         return userService.userInfo();
//     }
//
//     @PutMapping("/userInfo")
//     @SystemLog(businessName = "更新用户信息")
//     public ResponseResult updateUserInfo(@RequestBody User user) {
//         return userService.updateUserInfo(user);
//     }
//
//     @PostMapping("/register")
//     @SystemLog(businessName = "注册")
//     public ResponseResult register(@RequestBody User user) {
//         return userService.register(user);
//     }
//
// }
//
