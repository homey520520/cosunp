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

//                Timer timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        try {
//                            String beforeDay = DateUtil.getBeforeDay();
//                            logger.error(new Date() + "start-----------------" + beforeDay);
//                            new PersonController().getKQ(beforeDay);
//                            logger.error(new Date() + "end-----------------" + beforeDay);
//                        } catch (Exception e) {
//                            logger.error(e.getMessage(), e);
//                            logger.error("error*************************************");
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, 3 * 1000, 24 * 60 * 60 * 1000);
            }
        };

    }

//    劝君莫赌博
//
//            世人皆言麻将妙
//    为你痴来为你狂
//            方方正正清一色
//    统一着装底面绝
//            要说发明是明清
//    有万有条又有圆
//            组成话来不用学
//    五六七万就要得
//            麻将麻将人人爱
//    老来把玩小来学
//            一逢大风雷雨天
//    麻将馆里声震天
//            只闻牌声小儿哭
//    不见阿婆把饭喂
//            小儿若有意外险
//    父母老人哭不回
//            更有好吃懒做人
//    输的裤子都不剩
//            此劝老幼年青人
//    莫把麻将当饭食
//            人生区区几十载
//    圆梦赚钱是正道
//            麻将祸害真不少
//    我给大家捋一捋
//            东边王二输套房
//    西边李四妻子散
//            南边张三败光家
//    莫要到时沦到你
//            网上赌博陷阱多
//    劝君莫拿身去试
//            一试上瘾难戒除
//    害你家破害你亡
//            若是小打怡怡情
//    叫上亲朋就可行
//            莫要贪来莫要恋
//    小钱即可众乐乐
//            祝愿大家心健康
//    永不赌来永快乐
//
// alter table b_warehouse_message add column entity_warehouse_no_test varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '实体仓库代码';
    // mysqldump -u root -p root ext_upload_files project_head_order > project_head_order.sql
//cat /var/log/syslog
    //https://www.jianshu.com/p/776b9455c4d8
    // https://blog.csdn.net/qq_38737992/article/details/81090373
    // /opt/mysrv/nginx/sbin/nginx -c /opt/mysrv/etc/nginx/nginx.conf
    // ./nginx -t
    //  redis-server /opt/mysrv/etc/redis/redis.conf
    //     ubuntu
    //  redis-server /usr/local/redis/redis.conf
    //./mysqld --initialize --user=mysql --basedir=/usr/local/mysql-5.7.30/ --datadir=/usr/local/mysql-5.7.30/data/
    //  ln -s /usr/local/mysql/bin/mysql-5.7.30 /usr/bin
// mysqld --initialize --user=mysql --datadir=/opt/mysql/data
    //sudo mysqld --initialize --user=mysql  --datadir=/etc/mysql/data mysqld --initialize --user=mysql

//    SELECT * FROM [AIS20190910163600].dbo.T_BAS_ASSISTANTDATAENTRY where FID ='5ebd0236b59a8a'
//            5ebd0236b59a8a是固定的，先在T_BAS_ASSISTANTDATAENTRY中选取FID为5ebd0236b59a8a的数据，
//    再筛选出来的数据中取所有数据的FENTRYID值，去T_BAS_ASSISTANTDATAENTRY_L中选出FDATAVALUE和FDESCRIPTION即为所需，FDATAVALUE为产品类型，FDESCRIPTION为工艺路线说明
}


