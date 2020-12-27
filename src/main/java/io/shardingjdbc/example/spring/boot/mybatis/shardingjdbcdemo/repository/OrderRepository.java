package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository;

import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRepository {

    Long insert(Order model);

}
