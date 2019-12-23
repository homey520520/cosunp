package com.cosun.cosunp.scheduled;

import com.cosun.cosunp.weixin.WeiXinController;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author:homey Wong
 * @date:2019-09-26
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Component
@Configuration
@EnableScheduling
public class SendWeiXinMesTask {

    //@Scheduled(fixedRate=5000)
    @Scheduled(cron = "0 0 18 ? * Fri")
    private void configureTasks() {
        try {
            new WeiXinController().sendTextToOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
