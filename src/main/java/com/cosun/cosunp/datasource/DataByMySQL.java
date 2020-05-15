package com.cosun.cosunp.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author:homey Wong
 * @Date: 2020/5/11  上午 11:10
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Configuration
@MapperScan(basePackages = {"com.cosun.cosunp.service.impl","com.cosun.cosunp.mapper"},sqlSessionFactoryRef = "test1SqlSessionFactory")
public class DataByMySQL {

    @Bean(name="test1DataSource")
    @ConfigurationProperties(prefix ="spring.datasource.test1")
    public DataSource testDataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean(name="test1SqlSessionFactory")
    public SqlSessionFactoryBean testSqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource)throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        try {
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setTypeAliasesPackage("com.cosun.cosunp.entity");
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:com/cosun/cosunp/mapper/*.java");
            sessionFactory.setMapperLocations(resources);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return sessionFactory;
    }
    @Bean(name = "test1TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test1DataSource")DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean(name="test1SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test1SqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }



}
