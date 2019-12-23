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
 * @date:2019/6/20  下午 5:05
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Component
@Configuration
@EnableScheduling
public class SaticScheduleTask {

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    //@Scheduled(cron = "0/5 * * * * ?")
    //@Scheduled(fixedRate=5000)
    @Scheduled(cron = "0 0 23 * * ?")
    private void configureTasks() {
        try {
            FileUtil.delFolder(finalDirPath + "linshi");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
