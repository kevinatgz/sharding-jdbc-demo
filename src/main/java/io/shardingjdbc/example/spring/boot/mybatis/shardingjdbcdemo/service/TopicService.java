package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Topic;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TopicService {

    @Autowired
    private TopicMapper topicMapper;

    public List<Topic> list() {
        return topicMapper.selectList(new QueryWrapper<>());
    }
    public IPage<Topic> selectWithPage(Integer pageNo, Integer pageSize) {
        QueryWrapper<Topic> wrapper = new QueryWrapper<>();
        IPage<Topic> iPage = new Page<>(pageNo, pageSize);
        return topicMapper.selectPage(iPage, wrapper);
    }

    public IPage<Map> selectMapWithPage(Integer pageNo, Integer pageSize) {
        IPage<Map> iPage = new Page<Map>(pageNo, pageSize);
        iPage = topicMapper.selectMapWithPage(iPage);
        return iPage;
    }

    public Topic selectByTitle(String title){
        return topicMapper.selectByTitle(title);
    }

}