package com.cosun.cosunp.weixin;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author:homey Wong
 * @Date: 2019/9/11 0011 上午 9:10
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class CheckUtil {

    public static boolean checkSignature(String singnature, String timestamp, String nonce, String tooken) {
        String[] arr = {tooken, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        String temp = getSha1(sb.toString());
        return temp.equals(singnature);
    }

    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");

            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(buf);

        } catch (Exception e) {
            return null;

        }
    }


}
