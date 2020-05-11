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
                // wx16c9442a4b26e6ea
                //36e571b957c3705964b4398019482dde
            }
        };
    }

    // https://mp.weixin.qq.com/wxamp/wacodepage/getcodepage?token=2009826961&lang=zh_CN
    // /opt/mysrv/nginx/sbin/nginx -c /opt/mysrv/etc/nginx/nginx.conf
    // ./nginx -t

    //  redis-server /opt/mysrv/etc/redis/redis.conf
    //  /usr/java/redis-4.0.9/src/redis-cli
    //  /usr/java/redis-4.0.9/src/redis-server

    // mysqldump -h 127.0.0.1 -u root -B -p ext_upload_files > /user/ofs_v2.bak

    //  cp /usr/java/redis-4.0.9/src/redis-server /usr/local/redis/
    // cp /usr/java/redis-4.0.9/src/redis-cli /usr/local/redis/
    // cp /opt/mysrv/etc /redis/redis.conf /usr/local/redis/
    // ln -s /usr/local/redis/redis-cli /usr/bin/redis
    //
    //   ln -s /usr/java/redis-4.0.9/src/redis-server /usr/bin/redis-server
    // return 301 https://it.cosunsign.com;
    // lsof -i:22

    // https://www.cnblogs.com/ssrs-wanghao/articles/12703146.html

//    log_error = /usr/local/mysql-5.7.21/log/mysql-error.log

//     大地,我的母亲
//
//     小时候
//     我喜欢在您身上打滚
//     蹦跳
//     是田野上舞刀弄剑的快活
//     是青山绿水中游窜的撒欢
//
//     长大后
//     是满怀希望
//     是脚踏实地
//     是为了目标而奔走四方的豪情
//
//    再后来啊
//    我成了家
//    背负重担
//    我的每一步努力前行
//    都有您肯定的回应
//
//    祖祖辈辈
//    世世代代
//    都曾在你怀里来了又去
//
//    花花叶叶
//    草草木木
//    在你身上荣了又枯
//
//    你送来了春
//    那是清新泥土里钻出的嫩绿
//    是一簇一簇的花开
//
//    你邀来了夏
//    那是啃着瓜泡在溪流里的清凉
//    是蝉鸣响破了天空的蓝
//
//    你请来了秋
//    但见压弯了一树的硕果
//    是丰收的黄
//
//    你迎来了冬
//    是围炉里热滚滚的美味
//    是二两浊酒后的开怀大笑
//
//    大地啊!我的母亲
//    你看你
//    美得像一幅画
//    那青翠郁葱里跳跃的小鹿
//    那草长 莺飞
//    那碧波 微风
//    那融化在泥里的细雨
//    那一树一树的花开
//
//    大地啊!我的母亲
//    那蒙了灰的青叶
//    那刺鼻变了色的湖泊
//    那过度依赖化肥而硬化的泥土
//    那日渐绝迹的生灵植物
//    可否让您
//    皱了眉
//    伤了心
//
//    罢了
//    我是渺小的
//    我唯有保护你
//    才不可负你对我的养育
//
//    在这个节日里
//    人们都在歌颂母爱伟大
//    却冷落了你
//    那就让我
//    静悄悄的
//    表达此刻我对你
//    爱的无限的深情


}

//./mysqld --initialize --user=mysql --basedir=/usr/local/mysql-5.7.30/ --datadir=/usr/local/mysql-5.7.30/data/

//  ln -s /usr/local/mysql/bin/mysql-5.7.30 /usr/bin
