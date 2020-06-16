package com.cosun.cosunp.scheduled;

import com.cosun.cosunp.controller.PersonController;
import com.cosun.cosunp.controller.ProjectController;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:homey Wong
 * @date:2019/10/07
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Component
@Configuration
@EnableScheduling
public class ZhongKongDATAToSQLTask {

    // @Scheduled(cron = "00 43 08 ? * *")
    private void configureTasks() {
        try {
            String beforeDay = "2020-06-14";
            new PersonController().getKQ(beforeDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





