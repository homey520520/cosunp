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
                new AccessTokenServlet().init();

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


//   文件管理平台交付说明
//
//        系统名称:文件管理平台
//        使用部门:设计部
//        程序目地：程序化管理设计图纸。
//        程序开发时间起：2018年12月
//        程序完成时间:2019年3月
//        程序使用载体:浏览器
//        程序功能说明:
//        1.上传
//        文件/文件夹上传
//        帐号初始化时时任何部门任何人都有上传权限
//
//        2.更新
//        文件/文件夹更新
//        何人更新何文件需有更新权限
//        更新与上传的文件需为同一帐号
//
//        3.下载
//        文件/文件夹下载
//        何人下载何文件需有下载权限
//
//        4.删除
//        文件/文件夹删除
//        何人删除何文件需有删除权限
//
//        5.查找
//        文件查找
//        任何人对任何文件都可进行在线查看
//
//        6.管理
//        更新/下载/删除权限的分配
//        经理级以上
//
//        流程说明:
//        1.每日上传已做好的图纸。
//        2.如若图纸发生变化可在更新页面进行更新操作。
//        3.如若图纸需踢除可在删除页面进行删除操作。
//        4.管理员/经理可对图纸的权限进行分配，即何人对何文件有何权限。
//
//
//
//
//
//
//
//
//
//
//
//
//        研发中心软件部                                  人事部门
//        交付人:                                         接收人:

//#!/bin/sh
//        # chkconfig: 2345 10 90
//        # description: Start and Stop redis
// cp -r /usr/local/redis/redis-server /usr/bin/redis-server
//        REDISPORT=6379
//        EXEC=/usr/local/redis/redis-server
//        CLIEXEC=/usr/local/redis/redis-cli
//
//        PIDFILE=/opt/mysrv/var/redis/run/redis_${REDISPORT}.pid
//        CONF="/opt/mysrv/etc/redis/redis.conf"
//
//        case "$1" in
//        start)
//        if [ -f $PIDFILE ]
//        then
//        echo "$PIDFILE exists, process is already running or crashed"
//        else
//        echo "Starting Redis server..."
//        $EXEC $CONF &
//        fi
//        ;;
//        stop)
//        if [ ! -f $PIDFILE ]
//        then
//        echo "$PIDFILE does not exist, process is not running"
//        else
//        PID=$(cat $PIDFILE)
//        echo "Stopping ..."
//        $CLIEXEC -p $REDISPORT shutdown
//        while [ -x /proc/${PID} ]
//        do
//        echo "Waiting for Redis to shutdown ..."
//        sleep 1
//        done
//        echo "Redis stopped"
//        fi
//        ;;
//        restart)
//        "$0" stop
//        sleep 3
//        "$0" start
//        ;;
//        *)
//        echo "Please use start or stop or restart as first argument"
//        ;;
//        esac

//
//
//    养一盆花
//    松土浇水
//    等花开满枝
//
//    读一本诗
//    感受那采菊东篱下的
//    淡然心境
//
//    种几亩薄田
//    撒下春的希望
//    收获秋的喜悦
//
//    持一蒲扇
//    扇来春风得意
//    扇走一身忧愁
//
//    握一杆钓
//    在风清云淡中
//    等鱼儿上钩
//
//    做一桌美味的佳肴
//    与亲朋好友
//    谈笑风声
//
//    在名利淡泊中看清
//    在生活体验中懂得
//    让我们把日子
//    过成诗
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