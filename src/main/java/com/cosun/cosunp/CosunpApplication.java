package com.cosun.cosunp;

import com.cosun.cosunp.controller.PersonController;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.weixin.AccessTokenServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.cosun.cosunp.mapper")
@EnableCaching
@ServletComponentScan
public class CosunpApplication extends SpringBootServletInitializer {


    public final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CosunpApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CosunpApplication.class, args);
    }


    @Bean
    public CommandLineRunner init(final IFileUploadAndDownServ fileUploadAndDownServ) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                fileUploadAndDownServ.deleteAll();
                fileUploadAndDownServ.init();
                new AccessTokenServlet().init();

                // /usr/java/apache-tomcat-8.5.35
                // /usr/java/jdk1.8.0_191
                // spring-boot使用两个数据库mysql 和 sqlserver
                //wx16c9442a4b26e6ea
                //36e571b957c3705964b4398019482dde
            }
        };
    }


    // https://mp.weixin.qq.com/wxamp/wacodepage/getcodepage?token=2009826961&lang=zh_CN
    // /opt/mysrv/nginx/sbin/nginx -c /opt/mysrv/etc/nginx/nginx.conf
    // ./nginx -t

//  /opt/mysrv/etc/redis/redis.conf
//  /usr/java/redis-4.0.9/src/redis-cli
//  /usr/java/redis-4.0.9/src/redis-server

    //  cp /usr/java/redis-4.0.9/src/redis-server /usr/local/redis/
    // cp /usr/java/redis-4.0.9/src/redis-cli /usr/local/redis/
    // cp /opt/mysrv/etc /redis/redis.conf /usr/local/redis/
    // ln -s /usr/local/redis/redis-cli /usr/bin/redis
//    redis-server /opt/mysrv/etc/redis/redis.conf
    //   ln -s /usr/java/redis-4.0.9/src/redis-server /usr/bin/redis-server
// return 301 https://it.cosunsign.com;
//    C:\Users\Administrator\Desktop\it.cosunsign.com\Nginx\1_it.cosunsign.com_bundle.crt
//    C:\Users\Administrator\Desktop\it.cosunsign.com\Nginx\2_it.cosunsign.com.key
    // lsof -i:22

//
//      希望
//
//    是熬过黑夜
//    守来的黎明
//    是苦研多日
//    盼来舒心笑颜
//    还是那铁片
//    那铜块
//    叮叮当当
//    咚咚叮叮
//    鬼斧神工地
//    发出了光的亮
//    有了标致的身
//    赋予高端的魂
//    对
//    是标识
//    承载着柯赛人的汗水、智慧
//    指引着人们脚下的方向

}