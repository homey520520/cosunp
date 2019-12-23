package com.cosun.cosunp.weixin;

/**
 * @author:homey Wong
 * @Date: 2019/9/20  下午 2:49
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ProjectConst {

    public static final String GET_WEIXIN_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
    public final static String GetPageUsersUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    public final static String GET_WEBAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public final static String Get_WEIXINPAGE_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=toselfURL&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


}
