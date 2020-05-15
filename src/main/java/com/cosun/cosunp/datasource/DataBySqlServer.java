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
 * @Date: 2020/5/11 0011 上午 11:11
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Configuration
@MapperScan(basePackages = {"com.cosun.cosunp.service2.impl","com.cosun.cosunp.mapper2"}, sqlSessionFactoryRef = "test2SqlSessionFactory")
public class DataBySqlServer {

    @Bean(name = "test2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test2")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test2SqlSessionFactory")
    public SqlSessionFactoryBean testSqlSessionFactory(@Qualifier("test2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        try {
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setTypeAliasesPackage("com.cosun.cosunp.entity");
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:com/cosun/cosunp/mapper2/*.java");
            sessionFactory.setMapperLocations(resources);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return sessionFactory;
    }

    @Bean(name = "test2TransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("test2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "test2SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
