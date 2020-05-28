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
    @Scheduled(cron = "00 30 09 ? * *")
    private void configureTasks() {
        try {
            //String beforDay  B = "2020-03-";
            String beforeDay = "2020-05-27";
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


//   时间会告诉我们答案
//
// 你是否常挑灯夜读
// 让自己的羽翼更为丰满
// 幻想在广阔蔚蓝的天空中
// 展翅高飞

// 你是否为情所困
// 那得不到的
// 那心心念而不能的
// 让你心愈挫落
//
// 你是否一直坚持自己笃定的目标
// 曾重重摔倒
// 或又头破血流
//
// 你是否曾在某个十字路口
// 茫然四顾
// 不知所措
//
// 你是否常常为自己空有能力
// 而没有任何赏识的机会
//
// 你受着的这些
// 时间终会告诉你
//
// 时间会抚平
// 所有的伤痛
//
// 时间会见证
// 你翱翔天空
//
// 时间会给你
// 合适的爱
//
// 时间会清晰
// 方向
//
// 那些



