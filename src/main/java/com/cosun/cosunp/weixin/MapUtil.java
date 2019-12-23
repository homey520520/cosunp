package com.cosun.cosunp.weixin;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author:homey Wong
 * @Date: 2019/9/17 0017 下午 6:49
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class MapUtil {

    private static final String KEY = "MiMff2zfRb5PMTSdB5GFeHX4GLGwisda";
    private static final String OUTPUT = "json";
    private static final String GET_LNG_LAT_URL = "http://api.map.baidu.com/reverse_geocoding/v3/";
    private static final String GET_ADDRESS_URL = "http://api.map.baidu.com/reverse_geocoding/v3/";
    private static final String coordtype = "wgs84ll";



    public static Map<String, String> getCityByLonLat(double lng, double lat) {
        String location  = lng + "," + lat;
        Map<String, String> params = new HashMap<>();
        params.put("location", location);
        params.put("coordtype", coordtype);
        try {
            String url = joinUrl(params, OUTPUT, KEY, GET_ADDRESS_URL);
            JSONObject result = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(HttpClientUtils.doGet(url)).
                    getString("result")).getString("addressComponent"));
            Map<String, String> area = new HashMap<>();
            area.put("province", result.getString("province"));
            area.put("city", result.getString("city"));
            area.put("district", result.getString("district"));
            area.put("street", result.getString("street"));
            area.put("street_number", result.getString("street_number"));
            return area;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String joinUrl(Map<String, String> params, String output, String key, String url) throws IOException {
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(url);

        int index = 0;
        Set<Map.Entry<String, String>> entrys = params.entrySet();
        for (Map.Entry<String, String> param : entrys) {
            if (index == 0) {
                baseUrl.append("?");
            } else {
                baseUrl.append("&");
            }
            baseUrl.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "utf-8"));
            index++;
        }
        baseUrl.append("&output=").append(output).append("&ak=").append(key);

        return baseUrl.toString();
    }

}
