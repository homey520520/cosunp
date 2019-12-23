package com.cosun.cosunp.weixin;

import com.alibaba.fastjson.JSON;
import com.aspose.cad.internal.bouncycastle.crypto.engines.AESEngine;
import com.cosun.cosunp.entity.QYweixinSend;
import com.cosun.cosunp.entity.WeiXinUsrId;
import com.cosun.cosunp.tool.Constants;
import com.cosun.cosunp.tool.JSONUtils;
import com.thoughtworks.xstream.XStream;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:homey Wong
 * @Date: 2019/9/11 0011 下午 2:13
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WeiXinUtil {


    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException,
            DocumentException {

        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        if (ins != null) {
            Document doc = reader.read(ins);

            Element root = doc.getRootElement();

            List<Element> list = root.elements();

            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            ins.close();

            return map;
        } else {
            return null;
        }
    }

    public static AccessToken getTheCode(String code, Jedis jedis) {
        Map<String, String> authInfo = new HashMap<>();
        AccessToken at = new AccessToken();
        String openId = "";
        if (code != null) {
            authInfo = getAuthInfo(code, jedis);
            openId = authInfo.get("Openid");
        }
        String accessToken = jedis.get(Constants.accessToken);
        at.setOpenId(openId);
        return at;
    }


    public static Map<String, String> oauth2GetOpenid(String code, Jedis jedis) {
        String appid = WeiXinConfig.appid;
        String appsecret = WeiXinConfig.secret;
        String requestUrl = ProjectConst.GET_WEBAUTH_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
        Map<String, String> result = new HashMap<String, String>();
        try {
            URL urlGet = new URL(requestUrl);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject OpenidJSONO = JSONObject.fromObject(message);
            String Openid = String.valueOf(OpenidJSONO.get("openid"));
            String AccessToken = String.valueOf(OpenidJSONO.get("access_token"));
            String Scope = String.valueOf(OpenidJSONO.get("scope"));
            String refresh_token = String.valueOf(OpenidJSONO.get("refresh_token"));
            result.put("Openid", Openid);
            result.put("AccessToken", AccessToken);
            result.put("scope", Scope);
            result.put("refresh_token", refresh_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Map<String, String> getAuthInfo(String code, Jedis jedis) {
        Map<String, String> result = oauth2GetOpenid(code, jedis);
        String openId = result.get("Openid");
        return result;
    }


    public static String textMessageToXml(InMsgEntity textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    public static String textMessageToXml2(QYweixinSend textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    public static boolean isChinese(String str) {
        boolean result = false;
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {
                result = true;
            }
        }
        return result;
    }


    public static List<WeiXinUsrId> getAddressBook(String access_token) {
        String ticket = null;
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=" + access_token + "&department_id=1&fetch_child=1";//这个url链接和参数不能变
        List<WeiXinUsrId> wechatUsers = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            InputStream is = http.getInputStream();
            String message = IOUtils.toString(is);
            JSONObject demoJson = JSONObject.fromObject(message);
            ticket = demoJson.getString("userlist");
            wechatUsers = JSONUtils.toList(ticket, WeiXinUsrId.class);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wechatUsers;
    }


    public static String getMedia_Phone(String access_token, String media_id) {
        String ticket = null;
        String url = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=" + access_token + "&media_id=" + media_id;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);

            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    public static String getTicket(String access_token) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";//这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    public static String getAccessToken(String appid, String secret) {
        String access_token = "";
        String grant_type = "client_credential";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + appid + "&secret=" + secret;
        String requestUrl = "";
        String oppid = "";
        JSONObject oppidObj = null;
        String openid = "";
        String requestUrl2 = "";
        String userInfoStr = "";
        JSONObject wxUserInfo = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            access_token = demoJson.getString("access_token");
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }


}
