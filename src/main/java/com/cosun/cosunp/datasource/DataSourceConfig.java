package com.cosun.cosunp.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author:homey Wong
 * @date:2019/2/26 0026 下午 7:54
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Configuration
public class DataSourceConfig {
    @ConfigurationProperties(prefix = "c3p0.datasource")
    @Primary
    @Bean(name="dataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
    }

}
