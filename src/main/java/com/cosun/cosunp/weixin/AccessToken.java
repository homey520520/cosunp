package com.cosun.cosunp.weixin;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/9/16 0016 上午 11:25
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1635100667332681613L;

    private String accessToken;
    private int expiresin;
    private String jsapi_ticket;
    private String openId;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(int expiresin) {
        this.expiresin = expiresin;
    }
}
