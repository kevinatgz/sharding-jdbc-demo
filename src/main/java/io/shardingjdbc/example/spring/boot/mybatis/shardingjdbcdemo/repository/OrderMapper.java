package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface OrderMapper extends BaseMapper<Order>
{
    List<Order> selectOrder();
    int addOrder(Order order);

    //多表联合查询 按条件orderID
    @Select("select t1.*,t2.order_item_id from t_order t1 LEFT JOIN  t_order_item t2  ON t1.order_id =t2.order_id WHERE t1.user_id= #{id}")
    Page<Map<String,Object>> orderItems(Page<Map<String,Object>> page, @Param("id") String id);

}
