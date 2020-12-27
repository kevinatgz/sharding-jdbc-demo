package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class Topic {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;
    private String content;
    private String tag;
    private Date inTime;

}
