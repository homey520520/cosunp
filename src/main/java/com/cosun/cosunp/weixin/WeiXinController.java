package com.cosun.cosunp.weixin;

import com.cosun.cosunp.entity.ClockInSetUp;
import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.Leave;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.tool.Constants;
import com.cosun.cosunp.tool.DateUtil;
import com.cosun.cosunp.tool.HttpUtil;
import com.cosun.cosunp.tool.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:homey Wong
 * @Date: 2019/9/12  下午 5:10
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/weixin")
public class WeiXinController {

    private static Logger logger = LogManager.getLogger(WeiXinController.class);

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @Autowired
    IPersonServ personServ;

    private static JedisPool pool;
    private static Jedis jedis;

    @ResponseBody
    @RequestMapping(value = "/getMobileLocate")
    public ModelAndView getMobileLocate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("weixin");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        view.addObject("bean", accessToken.getOpenId());
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/getCamera")
    public ModelAndView getCamera(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("camera");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        view.addObject("bean", accessToken.getOpenId());
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/getMobileLocateReal")
    public void getMobileLocateReal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String wxMsgXml = IOUtils.toString(request.getInputStream(), "utf-8");
        String url = request.getParameter("wxurl");
        String openId = request.getParameter("bean");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        try {
            String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            System.out.println("accessToken:" + jedis.get(Constants.accessToken) + "\njsapi_ticket:" + jedis.get(Constants.jsapi_ticket) + "\n时间戳：" + timestamp + "\n随机字符串：" + noncestr);
            String str = "jsapi_ticket=" + jedis.get(Constants.jsapi_ticket) + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            String signature = CheckUtil.getSha1(str);
            List l_data = new ArrayList();
            l_data.add(timestamp);
            l_data.add(noncestr);
            l_data.add(signature);
            l_data.add(url);
            l_data.add(WeiXinConfig.appid);
            l_data.add(openId);
            JSONArray l_jsonarrary = JSONArray.fromObject(l_data);
            String l_jsonstring = l_jsonarrary.toString();
            response.getWriter().print(l_jsonstring);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MassMsgResult sendTextToOpenid() {
        MassMsgResult result = null;
        try {
            pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
            jedis = pool.getResource();
            String accessToken = jedis.get(Constants.accessToken);
            List<String> allOpenIds = personServ.findAllOpenId();
            TreeMap<String, String> params = new TreeMap<>();
            params.put("access_token", accessToken);
            String preDay01;
            String preDay02;
            String preDay03;
            String preDay04;
            String today;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -4);
            Date time = c.getTime();
            preDay01 = sdf.format(time);
            c.add(Calendar.DATE, -3);
            time = c.getTime();
            preDay02 = sdf.format(time);
            c.add(Calendar.DATE, -2);
            time = c.getTime();
            preDay03 = sdf.format(time);
            c.add(Calendar.DATE, -1);
            time = c.getTime();
            preDay04 = sdf.format(time);
            c.add(Calendar.DATE, 0);
            time = c.getTime();
            today = sdf.format(time);
            List<Employee> employeeList = personServ.findLeaveDataUionOutClockData(preDay01, preDay02, preDay03, preDay04, today);
            List<ClockInSetUp> clockInSetUpList = personServ.findAllClockInSetUp();
            List<Leave> leaveList = personServ.findAllLeave(preDay01, preDay02, preDay03, preDay04, today);
            Employee ee = null;
            List<String> leaveDateStrs = new ArrayList<String>();
            Map<String, String> sb = new TreeMap<String, String>();
            String name;
            String empNo;
            int clockTimes;
            Long xiangChaDays;
            String leaveDate;
            boolean isClockIn = false;
            int dayClockTimes = 0;
            String openId = null;
            for (int i = 0; i < leaveList.size(); i++) {
                name = leaveList.get(i).getName();
                empNo = leaveList.get(i).getEmpNo();
                xiangChaDays = DateUtil.startToEnd(leaveList.get(0).getBeginLeave(), leaveList.get(0).getEndLeave());
                clockTimes = ComputeClockTimesByLeaveDays(xiangChaDays, clockInSetUpList);
                leaveDateStrs.addAll(DateUtil.toDatePriodTranstoDays(leaveList.get(0).getBeginLeaveStr(), leaveList.get(0).getEndLeaveStr()));
                StringBuilder sbStr = new StringBuilder("");
                for (int a = 0; a < leaveDateStrs.size(); a++) {
                    isClockIn = false;
                    leaveDate = leaveDateStrs.get(a);
                    for (int j = 0; j < employeeList.size(); j++) {
                        ee = employeeList.get(j);
                        openId = ee.getGongzhonghaoId();
                        if (leaveDate.equals(ee.getClockInDateStr()) && empNo.equals(ee.getEmpNo())) {
                            isClockIn = true;
                            if (ee.getClockInDateAMOnStr() != null || ee.getClockInAddrAMOn() != null) {
                                dayClockTimes++;
                            }
                            if (ee.getClockInDatePMOnStr() != null || ee.getClockInAddrPMOn() != null) {
                                dayClockTimes++;
                            }
                            if (ee.getClockInDateNMOnStr() != null || ee.getClockInAddNMOn() != null) {
                                dayClockTimes++;
                            }
                        }
                    }
                    if (!isClockIn) {
                        sbStr.append(leaveDate + "一整天未打卡");
                    } else if (dayClockTimes < clockTimes) {
                        sbStr.append(leaveDate + "规定打卡" + clockTimes + "次,但您只打卡" + dayClockTimes + "次");
                    }

                }
                sb.put(openId + " " + name, sbStr.toString());
            }
            System.out.println(sb.toString());
            Map<String, Object> textParams = new HashMap<>();
            textParams.put("content", sb);
            TreeMap<String, Object> dataParams = new TreeMap<>();
            dataParams.put("touser", allOpenIds);
            dataParams.put("text", textParams);
            dataParams.put("msgtype", "text");
            String data = JSONUtils.toJSONString(dataParams);
            String path = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + accessToken;
            String json = HttpUtil.HttpsDefaultExecute(path, "POST", params, data);
            result = JSONUtils.toBean(json, MassMsgResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int ComputeClockTimesByLeaveDays(Long xiangChaDays, List<ClockInSetUp> cis) {
        for (int a = 0; a < cis.size(); a++) {
            if (cis.get(a).getOutDays() >= xiangChaDays) {
                return cis.get(a).getDayClockInTimes();
            }
        }
        return 0;
    }

    @ResponseBody
    @RequestMapping(value = "/saveOutPhoto")
    public ModelAndView saveOutPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        String openId = request.getParameter("openId");
        String serverId = request.getParameter("serverId");
        ModelAndView mav = new ModelAndView("success");
        Date date = new Date();
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat todaytime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr1 = today.format(date);
        String todaytimeStr = todaytime.format(date);
        String hourStr = todaytimeStr.split(" ")[1];
        Integer hour = Integer.valueOf(hourStr.split(":")[0]);
        String folderName = "weixin/" + dateStr1 + "/";
        ImageUtils.saveImage(jedis.get(Constants.accessToken), finalDirPath + folderName, serverId, serverId + ".jpg");
        OutClockIn outClockIn = new OutClockIn();
        outClockIn.setClockInDateStr(dateStr1);
        outClockIn.setWeixinNo(openId);
        if (hour < 12 && hour >= 6) {
            outClockIn.setAmOnUrl(folderName + serverId + ".jpg");
            int isPhotoInAlready = personServ.isClockInAlready(openId, dateStr1, "amOnUrl");
            if (isPhotoInAlready == 0) {
                personServ.saveOrUpdateOutClockInDataUrl(outClockIn);
            } else {
                mav = new ModelAndView("failed");
                return mav;
            }
        } else if (hour >= 12 && hour <= 18) {
            outClockIn.setPmOnUrl(folderName + serverId + ".jpg");
            int isPhotoInAlready = personServ.isClockInAlready(openId, dateStr1, "pmOnUrl");
            if (isPhotoInAlready == 0) {
                personServ.saveOrUpdateOutClockInDataUrl(outClockIn);
            } else {
                mav = new ModelAndView("failed");
                return mav;
            }
        } else if (hour > 18 && hour <= 24) {
            outClockIn.setNmOnUrl(folderName + serverId + ".jpg");
            int isPhotoInAlready = personServ.isClockInAlready(openId, dateStr1, "nmOnUrl");
            if (isPhotoInAlready == 0) {
                personServ.saveOrUpdateOutClockInDataUrl(outClockIn);
            } else {
                mav = new ModelAndView("failed");
                return mav;
            }
        }
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/punchClock")
    public void punchClock(@RequestBody(required = true) Location location, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        int isClock = 0;
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        String addr = null;
        Map<String, String> address = MapUtil.getCityByLonLat(location.getLatitude(), location.getLongitude());
        List<Location> locations = new ArrayList<Location>();
        if (address != null) {
            OutClockIn outClockIn = new OutClockIn();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = format.format(date);
            String dateStr1 = format1.format(date);
            String hourStr = dateStr.split(" ")[1];
            addr = address.get("province") + address.get("city") + address.get("district") +
                    address.get("street");
            Integer hour = Integer.valueOf(hourStr.split(":")[0]);
            outClockIn.setClockInDateStr(dateStr1);
            outClockIn.setWeixinNo(location.getOpenId());
            if (hour < 12 && hour >= 6) {
                outClockIn.setClockInDateAMOnStr(dateStr);
                outClockIn.setClockInAddrAMOn(addr);
                int isClockInAlready = personServ.isClockInAlready(location.getOpenId(), dateStr1, "clockInDateAMOn");
                if (isClockInAlready == 0) {
                    personServ.saveOrUpdateOutClockInData(outClockIn);
                } else {
                    isClock = 1;
                }
            } else if (hour >= 12 && hour <= 18) {
                outClockIn.setClockInDatePMOnStr(dateStr);
                outClockIn.setClockInAddrPMOn(addr);
                int isClockInAlready = personServ.isClockInAlready(location.getOpenId(), dateStr1, "clockInDatePMOn");
                if (isClockInAlready == 0) {
                    personServ.saveOrUpdateOutClockInData(outClockIn);
                } else {
                    isClock = 1;
                }
            } else if (hour > 18 && hour <= 24) {
                outClockIn.setClockInDateNMOnStr(dateStr);
                outClockIn.setClockInAddNMOn(addr);
                int isClockInAlready = personServ.isClockInAlready(location.getOpenId(), dateStr1, "clockInDateNMOn");
                if (isClockInAlready == 0) {
                    personServ.saveOrUpdateOutClockInData(outClockIn);
                } else {
                    isClock = 1;
                }
            } else {
                isClock = 1;
            }
        } else {
            isClock = 0;
        }
        location.setIsClock(isClock);
        location.setAddress(addr);
        locations.add(location);
        String str = null;
        ObjectMapper x = new ObjectMapper();
        try {
            str = x.writeValueAsString(locations);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str);
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryLeaveSheet")
    public ModelAndView queryLeaveSheet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("leavesheet");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        String name = personServ.getNameByWeiXinId(accessToken.getOpenId());
        List<Leave> leaveList = personServ.findAllLeaveByWeiXinId(accessToken.getOpenId());
        view.addObject("leaveList", leaveList);
        view.addObject("name", name);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/outClockInNote")
    public ModelAndView outClockInNote(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("outclockinnote");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        view.addObject("bean", accessToken.getOpenId());
        List<ClockInSetUp> clockInSetUpList = personServ.findAllCLockInSetUp();
        view.addObject("clockInSetUpList", clockInSetUpList);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/queryOutClockIn")
    public ModelAndView queryOutClockIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("queryOutClockIn");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        List<OutClockIn> outClockInList = personServ.findAllOutClockInByOpenId(accessToken.getOpenId());
        String name = personServ.getNameByWeiXinId(accessToken.getOpenId());
        view.addObject("outClockInList", outClockInList);
        view.addObject("name", name);
        return view;
    }


}
