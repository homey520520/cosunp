package com.cosun.cosunp.weixin;

import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.tool.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author:homey Wong
 * @Date: 2019/9/11 0011 上午 9:03
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@WebServlet(urlPatterns = "/weixin/hello")
public class WeiXinServlet extends HttpServlet {

    private static Logger logger = LogManager.getLogger(WeiXinServlet.class);

    @Autowired
    IPersonServ personServ;

    public static final String tooken = "homeyhomeyhomey";
    private static JedisPool pool;
    private static Jedis jedis;

    private static final String clockin = "clockin";
    private static final String clickquery = "clickquery";


    public WeiXinServlet() {
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("success");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter outPW = null;
        try {
            outPW = response.getWriter();
            if (CheckUtil.checkSignature(signature, timestamp, nonce, tooken)) {
                outPW.write(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outPW.close();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        System.out.println(jedis.get(Constants.accessToken));
        System.out.println(jedis.get(Constants.jsapi_ticket));
        try {
            PrintWriter out = response.getWriter();
            Map<String, String> map = WeiXinUtil.xmlToMap(request);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String key = map.get("EventKey");
            String message = null;
            StringBuilder returnMes = new StringBuilder();
            if ("event".equals(msgType)) {
                if (clickquery.equals(key)) {
                    List<OutClockIn> allOutClockIn = personServ.findAllOutClockInByOpenId(fromUserName);
                    if (allOutClockIn.size() > 0) {
                        for (OutClockIn on : allOutClockIn) {
                            returnMes.append(on.getClockInDateStr() + ":");
                            returnMes.append(on.getClockInDateAMOnStr() + ",");
                            returnMes.append(on.getClockInAddrAMOn() + ",");
                            if (on.getAmOnUrl() != null && on.getAmOnUrl().trim().length() > 0) {
                                returnMes.append("上午已摄像.");
                            } else {
                                returnMes.append("上午还未摄像 .");
                            }
                            returnMes.append(on.getClockInDatePMOnStr() + ",");
                            returnMes.append(on.getClockInAddrPMOn() + ".");
                            if (on.getPmOnUrl() != null && on.getPmOnUrl().trim().length() > 0) {
                                returnMes.append("下午已摄像.");
                            } else {
                                returnMes.append("下午还未摄像.");
                            }
                            returnMes.append(on.getClockInDateNMOnStr() + ",");
                            returnMes.append(on.getClockInAddNMOn() + ".");
                            if (on.getNmOnUrl() != null && on.getNmOnUrl().trim().length() > 0) {
                                returnMes.append("晚上已摄像.");
                            } else {
                                returnMes.append("晚上还未摄像.");
                            }
                        }
                    } else {
                        returnMes.append("暂无考勤信息");
                    }
                    InMsgEntity text = new InMsgEntity();
                    text.setFromUserName(toUserName);
                    text.setToUserName(fromUserName);
                    text.setMsgType("text");
                    text.setCreateTime(new Date().getTime());
                    text.setContent("您的考勤信息是：" + returnMes);
                    message = WeiXinUtil.textMessageToXml(text);
                }
            } else if ("text".equals(msgType)) {
                InMsgEntity text = new InMsgEntity();
                text.setFromUserName(toUserName);
                text.setToUserName(fromUserName);
                text.setMsgType("text");
                text.setCreateTime(new Date().getTime());
                text.setContent("您发送的信息是：" + content);
                message = WeiXinUtil.textMessageToXml(text);
            }
            out.print(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());

    }

    public void setRedisValue(AccessToken accessToken) {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        jedis.set(Constants.accessToken, accessToken.getAccessToken());
        jedis.set(Constants.expiresin, accessToken.getExpiresin() + "");
        //jedis.set(Constants.jsapi_ticket, accessToken.getJsapi_ticket());
    }

}
