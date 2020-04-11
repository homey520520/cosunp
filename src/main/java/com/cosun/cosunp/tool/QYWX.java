package com.cosun.cosunp.tool;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author:homey Wong
 * @Date: 2020/4/2 0002 上午 10:06
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class QYWX {


    private static JedisPool  pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
    private static Jedis jedis = pool.getResource();

    public static String SendMsgtoBody(JSONObject outputStr) throws Exception {
        String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
        String requestMethod = "POST";
        StringBuffer buffer = null;

        //创建SSLContext
        SSLContext sslContext = SSLContext.getInstance("SSL");
        TrustManager[] tm = {new MyX509trustManager()};
        //初始化
        sslContext.init(null, tm, new java.security.SecureRandom());
        ;
        //获取SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        System.out.println(jedis.get(Constants.accessTokenqywx)+"****************");
        URL url = new URL(requestUrl + "?access_token=" + jedis.get(Constants.accessTokenqywx));
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod(requestMethod);
        //设置当前实例使用的SSLSoctetFactory
        conn.setSSLSocketFactory(ssf);
        conn.connect();
        //往服务器端写内容
        if (null != outputStr) {
            OutputStream os = conn.getOutputStream();
            os.write(outputStr.toString().getBytes("utf-8"));
            os.close();
        }
        //读取服务器端返回的内容
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        buffer = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        JSONObject json1 = JSONObject.fromObject(buffer.toString());
        System.out.println(json1);
        String msg = json1.getString("errmsg");
        return msg;

    }


}
