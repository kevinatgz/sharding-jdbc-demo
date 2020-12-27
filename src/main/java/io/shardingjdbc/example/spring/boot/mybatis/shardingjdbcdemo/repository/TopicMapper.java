package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Employee;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TopicMapper extends BaseMapper<Topic> {
    IPage<Map> selectMapWithPage(IPage iPage);
    Topic selectByTitle(@Param("title") String title);
    List<Map> selectTitleList();



}