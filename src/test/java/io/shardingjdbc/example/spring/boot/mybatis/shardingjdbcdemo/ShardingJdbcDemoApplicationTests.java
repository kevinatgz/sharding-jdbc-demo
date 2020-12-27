package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.github.pagehelper.Page;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Employee;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Order;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository.EmplopyeeDao;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository.OrderMapper;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@SpringBootTest
public class ShardingJdbcDemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private EmplopyeeDao emplopyeeDao;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testInsert(){
        Employee employee = new Employee();
        employee.setLastName("东方不败");
        employee.setEmail("dfbb@163.com");
        employee.setGender(1);
        employee.setAge(20);
        emplopyeeDao.insert(employee);
        //mybatisplus会自动把当前插入对象在数据库中的id写回到该实体中
        System.out.println(employee.getId());
    }

    @Test
    public void testUpdate(){
        Employee employee = new Employee();
        employee.setId(1);
        employee.setLastName("更新测试");
        emplopyeeDao.updateById(employee);//根据id进行更新，没有传值的属性就不会更新
//        emplopyeeDao.updateAllColumnById(employee);//根据id进行更新，没传值的属性就更新为null
    }

    @Test
    public void testSelect(){
//        Employee employeeCondition = new Employee();
//        employeeCondition.setId(1);
//        employeeCondition.setLastName("更新测试");

        LambdaQueryWrapper<Employee> query = new QueryWrapper<Employee>().lambda();
        query.eq(Employee::getId,1);
//        Wrapper<Employee> queryWrapper = Wrappers.<Employee>lambdaQuery().eq(Employee::getId, 1);
//        queryWrapper.eq("name", "lqf");
//若是数据库中符合传入的条件的记录有多条，那就不能用这个方法，会报错
        Employee employee = emplopyeeDao.selectOne(query);
        log.info(""+employee);

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("last_name","东方不败");//写表中的列名
        columnMap.put("gender","1");
        List<Employee> employees = emplopyeeDao.selectByMap(columnMap);
        System.out.println(employees.size());


        List<Integer> idList = new ArrayList<>();
        idList.add(1);
        idList.add(2);

        idList.add(3);
        List<Employee> employees2 = emplopyeeDao.selectBatchIds(idList);
        System.out.println(employees2);

        Page<Employee> employees3 = emplopyeeDao.selectPage(new Page<Employee>(1,2),null);
        System.out.println(employees3);

        Page<Employee> employees5 = emplopyeeDao.selectPage(new Page<Employee>(1,3),
                new QueryWrapper<Employee>().lambda()
                        .between(Employee::getAge,18,50)
                        .eq(Employee::getGender,0)
                        .eq(Employee::getLastName,"老师")
        );
        List<Employee> records  = employees5.getRecords();
        System.out.println(records);

        List<Employee> employees6 = emplopyeeDao.selectList(
                new QueryWrapper<Employee>().lambda()
                        .eq(Employee::getGender,0)
                        .like(Employee::getLastName,"老师")
                        //.or()//和or new 区别不大
//                        .or()
//                        .like(Employee::getEmail,"a")
        );
        System.out.println(employees6);

//        emplopyeeDao.deleteById(1);

//        Map<String,Object> columnMap2 = new HashMap<>();
//        columnMap2.put("gender",0);
//        columnMap2.put("age",18);
//        emplopyeeDao.deleteByMap(columnMap2);

//        List<Integer> idList2 = new ArrayList<>();
//        idList2.add(1);
//        idList2.add(2);
//        emplopyeeDao.deleteBatchIds(idList2);
    }

    @Test
    public void testQueryWrapperUpdate(){
        Employee employee = new Employee();
        employee.setLastName("苍老师");
        employee.setEmail("cjk@sina.com");
        employee.setGender(0);
        emplopyeeDao.update(employee,
                new QueryWrapper<Employee>()
                        .eq("last_name","tom")
                        .eq("age",25)
        );

        emplopyeeDao.delete(
                new QueryWrapper<Employee>()
                        .eq("last_name","tom")
                        .eq("age",16)
        );
    }


    @Test
    public void testPage() {
        //配置了分页插件后，还是和以前一样的使用selectpage方法，
        //但是现在就是真正的物理分页了，sql语句中有limit了
        Page<Employee> page = new Page<>(1, 2);
        Page<Employee> employeeList =
                emplopyeeDao.selectPage(page, null);
        System.out.println(employeeList);
        System.out.println("================= 相关的分页信息 ==================");
        System.out.println("总条数:" + page.getTotal());
        System.out.println("当前页码:" + page.getCurrent());
        System.out.println("总页数:" + page.getPages());
        System.out.println("每页显示条数:" + page.getSize());
        System.out.println("是否有上一页:" + page.hasPrevious());
        System.out.println("是否有下一页:" + page.hasNext());
        //还可以将查询到的结果set进page对象中
        page.setRecords(employeeList.getRecords());
    }

    @Test
    public void testPageConditions() {
        //配置了分页插件后，还是和以前一样的使用selectpage方法，
        //但是现在就是真正的物理分页了，sql语句中有limit了
        Page<Employee> page = new Page<>(2, 2);
        IPage<Employee> employeeList =
                emplopyeeDao.selectEmployeeByPage(page, null);
        System.out.println(employeeList);
        System.out.println("================= 相关的分页信息 ==================");
        System.out.println("总条数:" + page.getTotal());
        System.out.println("当前页码:" + page.getCurrent());
        System.out.println("总页数:" + page.getPages());
        System.out.println("每页显示条数:" + page.getSize());
        System.out.println("是否有上一页:" + page.hasPrevious());
        System.out.println("是否有下一页:" + page.hasNext());
        //还可以将查询到的结果set进page对象中
        page.setRecords(employeeList.getRecords());
        log.info(""+page.getRecords());
    }

    @Test
    public void testPageJoin() {
        //配置了分页插件后，还是和以前一样的使用selectpage方法，
        //但是现在就是真正的物理分页了，sql语句中有limit了
        Page<Map<String,Object>> page = new Page<>(1, 2);
        Page<Map<String,Object>> employeeList =
                orderMapper.orderItems(page, "1");
        System.out.println(employeeList);
        System.out.println("================= 相关的分页信息 ==================");
        System.out.println("总条数:" + page.getTotal());
        System.out.println("当前页码:" + page.getCurrent());
        System.out.println("总页数:" + page.getPages());
        System.out.println("每页显示条数:" + page.getSize());
        System.out.println("是否有上一页:" + page.hasPrevious());
        System.out.println("是否有下一页:" + page.hasNext());
        //还可以将查询到的结果set进page对象中
        page.setRecords(employeeList.getRecords());
        log.info(""+employeeList.getRecords());

        Order order = new Order();
        order.setUserId(1);
        order.setStatus("test");
        orderRepository.insert(order);
    }


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;
    @Test
    public void testDataSource() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

}
