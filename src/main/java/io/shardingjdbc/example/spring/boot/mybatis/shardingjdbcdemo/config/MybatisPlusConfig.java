package io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import javax.sql.DataSource;

@Configuration
@MapperScan("io.shardingjdbc.example.spring.boot.mybatis.shardingjdbcdemo.repository")
public class MybatisPlusConfig {
    /**
     *   mybatis-plus分页插件
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor page = new PaginationInterceptor();
//        page.setDialectType("mysql");
//        return page;
//    }

    @Bean("mybatisSqlSession")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        //*注册Map 下划线转驼峰*
        configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());

        // 添加数据源
        sqlSessionFactory.setDataSource(dataSource);

        sqlSessionFactory.setConfiguration(configuration);

        //添加分页插件
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor});

        return sqlSessionFactory.getObject();
    }
}

