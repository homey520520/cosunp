package com.cosun.cosunp.tool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/11 上午 9:57
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Test {

    static String json = "{\r\n" +
            "	\"ftp://admin:FL33771@192.168.0.152/admin/201906/曾红红/21430637/COSUN20190613WW01/000001 (1).exe\":\"E:/myFile/111/000001 (1).exe\",\r\n" +
            "	\"ftp://admin:FL33771@192.168.0.152/admin/201906/凡钟俊/03063247/COSUN20190108WW26/IntelliJ IDEA 2018.5.zip\":\"E:/myFile/111/IntelliJ IDEA 2018.5.zip\"\r\n}";

    private static JedisPool pool;
    private static Jedis jedis;

    public static void main(String[] arg) {

        try {
            //   File file = new File("E:\\WAMP\\wamp\\www\\cosunp\\src\\main\\resources\\templates");
            //System.out.println(getProjectFileNumber(file, "html"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String toBase64(byte[] imgData) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(imgData);
    }

    public static int getProjectFileNumber(File file, String endsWith) throws IOException {
        int number = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File subFile : file.listFiles()) {
                    number += getProjectFileNumber(subFile, endsWith);
                }
            } else if (file.isFile() && file.getName().endsWith(endsWith)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while (br.readLine() != null) {
                    number += 1;
                }
            } else {
                System.out.println("===" + file.getAbsolutePath());
            }
        }
        return number;
    }

}

