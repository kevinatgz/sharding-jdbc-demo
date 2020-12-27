package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmplopyeeDao extends BaseMapper<Employee> {
    // 分页查询
    @Select("<script>" +
            " select t.* " +
            " from tb_employee t " +
            " where <![CDATA[t.gender <> 1]]>" +
            " <if test='nameCn != null and nameCn.trim() != \"\"'>" +
            " AND t.last_name like CONCAT('%',#{nameCn},'%')" +
            " </if>" +
            " </script>")
    IPage<Employee> selectEmployeeByPage(Page<Employee> page,
                                        @Param("nameCn") String nameCn);
}