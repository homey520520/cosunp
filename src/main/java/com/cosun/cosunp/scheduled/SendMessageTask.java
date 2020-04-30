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
public class SendMessageTask {

    @Scheduled(cron = "0 30 15 ? * *")
    private void configureTasks() {
        try {
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String beforeDay = dft.format(date);
           new ProjectController().sendMessageByAllQueryQJ(beforeDay);
           new ProjectController().sendMessageByAllQueryYQ(beforeDay);
           new ProjectController().sendMessageByAllQueryAlert(beforeDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}



