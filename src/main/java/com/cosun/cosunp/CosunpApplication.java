package com.cosun.cosunp;

import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.weixin.AccessTokenServlet;
import com.cosun.cosunp.weixin.AccessTokenServletSP;
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
                //wx16c9442a4b26e6ea
                //36e571b957c3705964b4398019482dde
                //https://mp.weixin.qq.com/wxamp/devprofile/get_profile?token=1695344288&lang=zh_CN
            }
        };
    }


}










































//1.对于离职的期限，正常来说是与合同一致，是1个月，但在柯赛，离职人员离职期限却是即空想象，长则一个月，短则三天，即是根据职位是否核心，忙闲程度，或已无用，或还有价值，这种单方面的被动...。
//2.绩效，绩效占工资比率的标准是什么？如某某工资3W+，绩效额度为4K，而某人工资不足1W，绩效份额却为2.4K，标准？额度占比率算法?
//3.人虽生而不平等，一个部门的所有人也是不平等的，不仅是薪资，还是受上级嘉爱度，这个可全靠自己，不能怪他人，但公司某部门人人都有的福利为啥却要歧视那么一二个人？
//4.本人合同试用期为2个月，试用期内表现也还合格。却拿了3个月试用期（正负2K），上级领导也说是他忘记写调薪单，并承诺一年内可通过100+的绩效分补完。
//我本不同意，这种方式太不靠谱，并提出解决方法，就是向更上一级打报告说明情况与解决问题，却没被采纳，于是就等啊等，2K，就只补了100多而已，
//后面就是不了了之。我的问题是，为什么领导做错的事，却需要员工来承担，特别是，这还是薪资的事。

//柯赛谈改革，最重要的是改人心。而不是偏某个部门人心，或某个部门某个人的人心。人心齐，则泰山移矣。