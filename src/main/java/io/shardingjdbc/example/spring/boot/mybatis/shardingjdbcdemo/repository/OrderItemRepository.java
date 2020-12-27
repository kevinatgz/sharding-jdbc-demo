package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository;

import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemRepository {

    Long insert(OrderItem model);

}