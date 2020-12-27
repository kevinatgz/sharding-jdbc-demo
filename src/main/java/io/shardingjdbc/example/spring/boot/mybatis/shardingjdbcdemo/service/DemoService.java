package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.service;

import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Order;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.OrderItem;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository.EmplopyeeDao;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository.OrderItemRepository;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderItemRepository orderItemRepository;

    @Resource
    private EmplopyeeDao emplopyeeDao;

    public String insert(Integer userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("INSERT_TEST");
        orderRepository.insert(order);

        long orderId = order.getOrderId();
        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        item.setUserId(userId);
        item.setStatus("INSERT_TEST");
        orderItemRepository.insert(item);

        return orderId + "|" + item.getOrderItemId();
    }
}