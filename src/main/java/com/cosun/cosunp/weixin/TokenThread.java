package com.cosun.cosunp.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author:homey Wong
 * @Date: 2019/9/16 0016 上午 11:29
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class TokenThread implements Runnable {


    public static String appId = "";
    public static String appSecret = "";
    public static String appSecretsp = "";
    public static AccessToken accessToken = null;
    public static AccessToken accessTokensp = null;
    public static String jsapi_ticket = "";

    public void run() {
        while (true) {
            try {
                accessToken = this.getAccessToken();
                accessTokensp = this.getAccessTokenSP();
                if (null != accessToken || null != accessTokensp) {
                    if (null != accessToken) {
                        new WeiXinServlet().setRedisValue(accessToken);
                    }
                    if (null != accessTokensp) {
                        new WeiXinServlet().setRedisValueSP(accessTokensp);
                    }
                    Thread.sleep(2 * 60 * 60 * 1000);
                } else {
                    Thread.sleep(1000 * 3);
                }

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000 * 10);
                } catch (Exception e1) {

                }
            }
        }

    }

    /**
     * @return
     */
    private AccessToken getAccessToken() {
        NetWorkHelper netHelper = new NetWorkHelper();
        //String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", this.appId, this.appSecret);
        String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s", this.appId, this.appSecret);
        String result = netHelper.getHttpsResponse(Url, "");
        //response.getWriter().println(result);
        JSONObject json = JSON.parseObject(result);
        AccessToken token = new AccessToken();
        token.setAccessToken(json.getString("access_token"));
        token.setExpiresin(json.getInteger("expires_in"));
        return token;
    }


    /**
     * @return
     */
    private AccessToken getAccessTokenSP() {
        NetWorkHelper netHelper = new NetWorkHelper();
        //String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", this.appId, this.appSecret);
        String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s", this.appId, this.appSecretsp);
        String result = netHelper.getHttpsResponse(Url, "");
        //response.getWriter().println(result);
        JSONObject json = JSON.parseObject(result);
        AccessToken token = new AccessToken();
        token.setAccessToken(json.getString("access_token"));
        token.setExpiresin(json.getInteger("expires_in"));
        return token;
    }

}
