package com.cosun.cosunp.scheduled;

import com.cosun.cosunp.controller.PersonController;
import com.cosun.cosunp.weixin.WeiXinController;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    //@Scheduled(fixedRate = 100000)
    //@Scheduled(cron = "0 0 05 * * ?")
    @Scheduled(cron = "0 58 08 ? * *")
    private void configureTasks() {
        try {
            new PersonController().getBeforeDayZhongKongData();
            new PersonController().getBeforeDayQYWCData();
            //new PersonController().fillEmptyZKWhenNull();
            //new PersonController().getAllWeiXinUser();
            //new PersonController().getKQBean();
            //new PersonController().fillRightDeptIdToEmployee();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
