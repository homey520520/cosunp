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
    @Scheduled(cron = "0 06 09 ? * *")
    private void configureTasks() {
        try {
//            String beforDayB = "2020-03-";
            String beforeDay = "2020-03-04";
//            String beforeDay;
//            for (int a = 1; a <= 3; a++) {
//                if (a < 10) {
//                    beforeDay = "0" + a;
//                } else {
//                    beforeDay = "" + a;
//
//                }

            //new PersonController().getBeforeDayZhongKongData(beforeDay);
            //new PersonController().getBeforeDayQYWCData(beforeDay);
           // new PersonController().getBeforeDayQYWCDataAAA(beforeDay);
           // new PersonController().getBeforeDayQYWXSPData(beforeDay);
            //new PersonController().fillEmptyZKWhenNull();
            new PersonController().getAllWeiXinUser();
            // new Per sonController().fillEmpNoWhenQYWXNull();
            //new PersonController().getKQBean();
            //new PersonController().fillRightDeptIdToEmployee();
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
