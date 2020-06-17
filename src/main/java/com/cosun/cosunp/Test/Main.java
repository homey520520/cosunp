package com.cosun.cosunp.Test;

import com.cosun.cosunp.entity.MonthKQInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {



        ZkemSDK sdk = new ZkemSDK();
        boolean connFlag = sdk.connect("192.168.1.201", 4370);
        if (connFlag) {
            System.out.println(sdk.readLastestLogData(new Date()));
            System.out.println(sdk.getGeneralLogData().size());
            List<Map<String, Object>> listMap = sdk.getUserInfo();
            for (int i = 0; i < listMap.size(); i++) {
                System.out.println(listMap.get(i));
            }
            System.out.println(sdk.getUserInfoByNumber("2"));
            sdk.disConnect();
        } else {
        }
    }

}
