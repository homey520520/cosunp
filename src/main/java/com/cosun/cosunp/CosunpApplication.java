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
                //new AccessTokenServlet().init();

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


//
//   被管理者感想
//
//
//     这么多年，我一直是被管理者，首先，对于我一直是名被管理的身份，略感羞意，毕竟，成为领导是各方面能力优秀的体现，我好像
// 差了点什么。不过正因为这么多年专职于被管理者，我觉得，我能代表诸多被管理者发言。
//     算下来，柯赛应该是我从业的第五家公司，我初来柯赛时，不知是多年没工作原因，还是其它，我初次对柯赛的感觉是严谨，
// 而这严谨，步步为营的态度，首先来自于我们的钟总。钟总确实是在我这么多年的打工生涯中，所见到过的最严厉，用心，用力的
// 老板，我个人觉得这对于企业的生存是极好的。
//     我见过柯赛的众多领导。
//     或是神定气闲冷静自若，这方面的代表比如品质黄经理，又或是气势汹汹风急火热，这方面的代表就不说了。又或是一视同仁体恤手下
// ，这方面的代表比如亚克力钟主管，或者是区别对待只顾自己的利益，这方面的代表我也就不说了。
//     我个人比较欣赏亚克力钟主管。
//     我虽不是钟主管的属下，但是看到他对属下那细致关怀，加上平时对他的稍观察，他确认就是我心目中领导的样子。
//
//
//
//
//
//
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