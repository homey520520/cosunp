package com.cosun.cosunp.scheduled;

import com.cosun.cosunp.controller.PersonController;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    @Scheduled(cron = "0 45 08 ? * *")
    private void configureTasks() {
        try {
            //String beforDayB = "2020-03-";
            String beforeDay = "2020-03-18";
//            String beforeDay;
//            for (int a = 1; a <= 17; a++) {
//                if (a < 10) {
//                    beforeDay = "0" + a;
//                } else {
//                    beforeDay = "" + a;
//
//                }

            new PersonController().getBeforeDayZhongKongData(beforeDay);
            new PersonController().getBeforeDayQYWCData(beforeDay);
            new PersonController().getBeforeDayQYWCDataAAA(beforeDay);
            new PersonController().getBeforeDayQYWXSPData(beforeDay);
//            new PersonController().fillEmptyZKWhenNull();
//            new PersonController().getAllWeiXinUser();
            //new PersonController().fillEmpNoWhenQYWXNull();
//            new PersonController().getKQBean();
//            new PersonController().fillRightDeptIdToEmployee();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}



