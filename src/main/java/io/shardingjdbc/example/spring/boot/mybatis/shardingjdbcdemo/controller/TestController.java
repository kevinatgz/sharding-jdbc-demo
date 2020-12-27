package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.controller;

import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
@Slf4j
public class TestController {

    @Autowired
    private DemoService demoService;

    @PostMapping
    public String insertOrder(Integer userId) {
        log.info("userId:"+userId);
        return demoService.insert(userId);
    }
}