package com.cosun.cosunp;

import com.cosun.cosunp.controller.PersonController;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.tool.DateUtil;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan({"com.cosun.cosunp.mapper", "com.cosun.cosunp.mapper2"})
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
//                fileUploadAndDownServ.deleteAll();
//                fileUploadAndDownServ.init();
                new AccessTokenServlet().init();

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            String beforeDay = DateUtil.getBeforeDay();
                            logger.error(new Date() + "start-----------------" + beforeDay);
                            new PersonController().getKQ(beforeDay);
                            logger.error(new Date() + "end-----------------" + beforeDay);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            logger.error("error*************************************");
                            e.printStackTrace();
                        }

                    }
                }, 3 * 1000, 24 * 60 * 60 * 1000);
                // wx16c9442a4b26e6ea
                //36e571b957c3705964b4398019482dde
            }
        };

    }


    // /opt/mysrv/nginx/sbin/nginx -c /opt/mysrv/etc/nginx/nginx.conf
    // ./nginx -t
    //  redis-server /opt/mysrv/etc/redis/redis.conf
    //./mysqld --initialize --user=mysql --basedir=/usr/local/mysql-5.7.30/ --datadir=/usr/local/mysql-5.7.30/data/
    //  ln -s /usr/local/mysql/bin/mysql-5.7.30 /usr/bin


}


// https://i.zhaopin.com/resume
//
//文件管理平台
//
//项目框架搭建采用：springboot+springMVC+ibaties+redis+maven+tomcat+FTP+js+json+ajax+css+html5+jquery+bootstrap等
//数据库：mysql
//线上系统：linux
//项目中配置了log4j日志方便线上BUG排错。事物,数据连接池等
//采用了控件webuploader等，修改部分源码迎合项目需要等.
//
//人事考勤系统
//项目框架搭建采用:springboot+springMVC+ibaties+redis+maven+tomcat+js+json+ajax+css+jquery+bootstrap等
//数据库:mysql
//线上系统：linux
//根据人事习惯量身订做的系统，集排单，数据管理，月考勤报表等等所有功能。
//
//微信小程序
//后端采用JAVA语言
//数据库:mysql
//后端线上系统：linux
//给项目中心全体人员量身订做的一款项目订单监控小程序+后台数据管理。集数据管理，数据更改或延期或报警消息通知，系统自动排单
//等数据智能化系统。
//
// 金蝶星空云二次开发
// 使用已有的系统金蝶去数据库
// 根据部门需要做二次开发
//
// 其它一些帮助部门节约时间的桌面程序开发等。
//