package com.cosun.cosunp.scheduled;

import com.cosun.cosunp.tool.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author:homey Wong
 * @date:2019/07/27
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Component
@Configuration
@EnableScheduling
public class RestartLinuxTask {

    //@Scheduled(fixedRate=5000)
    @Scheduled(cron = "0 0 07 * * ?")
    private void configureTasks() {
        try {
            Runtime.getRuntime().exec("reboot");
           //String com  mand = "/opt/openoffice4/program/soffice --headless --accept=\"socket,host=0.0.0.0,port=8100;urp;\" --nofirststartwizard &";
            //Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
