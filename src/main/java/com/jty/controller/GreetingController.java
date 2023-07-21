package com.jty.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 测试接口
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/3/2 20:27
 */
@RestController
@RequestMapping("/api/v1/")
public class GreetingController {

    @GetMapping(value = "/hello")
    public ResponseEntity<String> sayHello() {
        String message = "Hello World!";
        return ResponseEntity.ok(message);
    }
    
}
