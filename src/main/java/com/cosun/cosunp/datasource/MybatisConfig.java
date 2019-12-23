package com.cosun.cosunp.datasource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author:homey Wong
 * @date:2019/2/26 0026 下午 7:56
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Configuration
@MapperScan(basePackages = {"com.cosun.cosunp.mapper"})
public class MybatisConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
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

}
