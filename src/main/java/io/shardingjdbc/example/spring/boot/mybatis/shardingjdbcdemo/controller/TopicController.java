package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Topic;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public Object list() {
        return topicService.list();
    }

    @GetMapping("/selectWithPage")
    public IPage<Topic> selectWithPage(Integer pageNo,
                                       Integer pageSize) {
        log.info("pageSize:"+pageSize);
        return topicService.selectWithPage(pageNo, pageSize);
    }

    @GetMapping("/selectByTitle")
    public Topic selectByTitle(String title) {
        log.info("pageSize:"+title);
        return topicService.selectByTitle(title);
    }

    @GetMapping("/selectMapWithPage")
    public IPage<Map> selectMapWithPage(Integer pageNo,
                                        Integer pageSize) {
        log.info("pageSize:"+pageSize);
        return topicService.selectMapWithPage(pageNo, pageSize);
    }
}