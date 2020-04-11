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

    //@Scheduled(fixedRate = 100000)
    //@Scheduled(cron = "0 0 05 * * ?")
    @Scheduled(cron = "0 55 08 ? * *")
    private void configureTasks() {
        try {
            //String beforDay B = "2020-03-";
            String beforeDay = "2020-04-10";
//            String beforeDay;
//            for (int a = 1; a <= 17; a++) {
//                if (a < 10) {
//                    beforeDay = "0" + a;
//                } else {
//                    beforeDay = "" + a;
//
////                }
//            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = new Date();
//            String beforeDay = dft.format(date);

            //new ProjectController().sendMessageToUserId();

            new PersonController().getKQ(beforeDay);
//            new PersonController().getAllWeiXinUser();
//            new PersonController().fillEmpNoWhenQYWXNull();
//
//            new PersonController().getBeforeDayZhongKongData(beforeDay);
//            new PersonController().getBeforeDayQYWCData(beforeDay);
//            new PersonController().getBeforeDayQYWCDataAAA(beforeDay);
//            new PersonController().getBeforeDayQYWXSPData(beforeDay);


//            new PersonController().fillEmptyZKWhenNull();
//            new PersonController().getKQBean();
//            new PersonController().fillRightDeptIdToEmployee();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}



