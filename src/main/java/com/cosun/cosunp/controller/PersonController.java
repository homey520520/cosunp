package com.cosun.cosunp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFinanceServ;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.tool.*;
import com.cosun.cosunp.weixin.NetWorkHelper;
import com.cosun.cosunp.weixin.WeiXinUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:homey Wong
 * @date:2019/5/8 下午 2:55
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/person")
public class PersonController {

    private static Logger logger = LogManager.getLogger(PersonController.class);

    private static String zkIP01 = "192.168.2.12";
    private static String zkIP02 = "192.168.2.11";
    private static String zkIP03 = "192.168.2.10";
    private static String zkIP04 = "192.168.1.18";
    private static Integer zkPort = 4370;


    private static JedisPool pool;
    private static Jedis jedis;

    @Autowired
    IPersonServ personServ;

    @Autowired
    IFinanceServ financeServ;

    private Integer flag;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;


    public void getKQBean() throws Exception {
        IPersonServ testDomainMapper = SpringUtil.getBean(IPersonServ.class);
        List<OutClockIn> clockDates = new ArrayList<OutClockIn>();
        OutClockIn oci = null;
        try {
            String day = null;
            for (int a = 1; a <= 31; a++) {
                if (a < 10) {
                    day = "0" + a;
                } else {
                    day = "" + a;
                }
                oci = new OutClockIn();
                oci.setClockInDateStr("2019-10-" + day);
                clockDates.add(oci);
                java.util.List<KQBean> kqBeans = testDomainMapper.getAllKQDataByYearMonthDay("2019-10-" + day);
                List<KQBean> newKQBeans = testDomainMapper.getAfterOperatorDataByOriginData(clockDates, kqBeans);
                testDomainMapper.saveAllNewKQBeansToMysql(newKQBeans);
                clockDates.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillEmptyZKWhenNull() throws Exception {
        String beforDay = "2019-12-";
        String[] afterDay = beforDay.split("-");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        List<ZhongKongBean> toDataBaseList = null;
        IPersonServ testDomainMapper = SpringUtil.getBean(IPersonServ.class);
        List<Employee> employeeList = testDomainMapper.findAllEmployeeNotIsQuitandhaveEnrollNum();
        Integer nu = null;
        ZhongKongBean zkbb = null;
        String day = null;
        boolean isComin = false;
        for (int a = 1; a <= 12; a++) {
            if (a < 10) {
                day = "0" + a;
            } else {
                day = "" + a;
            }


            toDataBaseList = testDomainMapper.getAllKQDataByYearMonthDayA(beforDay + day);
            for (Employee ee : employeeList) {
                isComin = false;
                for (ZhongKongBean zk : toDataBaseList) {
                    if (ee.getEnrollNumber().equals(zk.getEnrollNumber())) {
                        nu = zk.getMachineNum();
                        isComin = true;
                        break;
                    }
                }
                if (!isComin) {
                    zkbb = new ZhongKongBean();
                    zkbb.setEnrollNumber(ee.getEnrollNumber().toString());
                    zkbb.setYearMonth(afterDay[0] + "-" + afterDay[1]);
                    zkbb.setDateStr(beforDay + day);
                    zkbb.setTimeStr("");
                    zkbb.setMachineNum(nu);
                    toDataBaseList.add(zkbb);
                }
            }
            testDomainMapper.saveBeforeDayZhongKongData(toDataBaseList);
            List<OutClockIn> ociList = new ArrayList<OutClockIn>();
            OutClockIn oc = new OutClockIn();
            oc.setClockInDateStr(toDataBaseList.get(0).getDateStr());
            ociList.add(oc);
            List<KQBean> kqBeanList = new ArrayList<KQBean>();
            KQBean kq = null;
            List<KQBean> kqBeans = testDomainMapper.getAllKQDataByYearMonthDay(toDataBaseList.get(0).getDateStr());
            List<KQBean> newKQBeans = testDomainMapper.getAfterOperatorDataByOriginData(ociList, kqBeans);
            testDomainMapper.saveAllNewKQBeansToMysql(newKQBeans);
            ociList.clear();
        }
    }

    public void getBeforeDayZhongKongData(String beforDay) throws Exception {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        String[] afterDay = beforDay.split("-");
        Map<String, Object> map = new HashMap<String, Object>();
        List<ZhongKongBean> strList = new ArrayList<ZhongKongBean>();
        boolean connFlag = ZkemSDKUtils.connect("192.168.2.12", 4370);
        if (connFlag) {
            boolean flag = ZkemSDKUtils.readGeneralLogData();
            strList.addAll(ZkemSDKUtils.getGeneralLogData(beforDay, 4));
        }

        boolean connFlag1 = ZkemSDKUtils.connect("192.168.2.10", 4370);
        if (connFlag1) {
            boolean flag = ZkemSDKUtils.readGeneralLogData();
            strList.addAll(ZkemSDKUtils.getGeneralLogData(beforDay, 2));
        }

        boolean connFlag2 = ZkemSDKUtils.connect("192.168.2.11", 4370);
        if (connFlag2) {
            boolean flag = ZkemSDKUtils.readGeneralLogData();
            strList.addAll(ZkemSDKUtils.getGeneralLogData(beforDay, 3));
        }

        boolean connFlag3 = ZkemSDKUtils.connect("192.168.1.18", 4370);
        if (connFlag3) {
            boolean flag = ZkemSDKUtils.readGeneralLogData();
            strList.addAll(ZkemSDKUtils.getGeneralLogData(beforDay, 1));
        }

        List<ZhongKongBean> newZkbList = new ArrayList<ZhongKongBean>();
        List<ZhongKongBean> secZkbList = new ArrayList<ZhongKongBean>();
        String encroNum1 = null;
        String encroNum2 = null;
        List<String> alreadyHaveNum = new ArrayList<String>();
        for (int a = 0; a < strList.size(); a++) {
            encroNum1 = strList.get(a).getEnrollNumber();
            if (!alreadyHaveNum.contains(encroNum1)) {
                for (int b = 0; b < strList.size(); b++) {
                    encroNum2 = strList.get(b).getEnrollNumber();
                    if (encroNum1.equals(encroNum2)) {
                        secZkbList.add(strList.get(b));
                    }
                }

                for (int i = 0; i < secZkbList.size() - 1; i++) {
                    for (int j = 0; j < secZkbList.size() - 1 - i; j++) {
                        if (secZkbList.get(j).getTimeTime().after(secZkbList.get(j + 1).getTimeTime())) {
                            ZhongKongBean temp = secZkbList.get(j + 1);
                            secZkbList.set(j + 1, secZkbList.get(j));
                            secZkbList.set(j, temp);
                        }
                    }
                }
                alreadyHaveNum.add(encroNum1);
                newZkbList.addAll(secZkbList);
                secZkbList.clear();
            }
            secZkbList.clear();
        }

        ZhongKongBean zkb01 = null;
        ZhongKongBean zkb02 = null;
        ZhongKongBean zkb = null;
        List<ZhongKongBean> toDataBaseList = new ArrayList<ZhongKongBean>();
        boolean isHave;
        for (int a = 0; a < newZkbList.size(); a++) {
            zkb01 = newZkbList.get(a);
            isHave = false;
            for (int b = 0; b < toDataBaseList.size(); b++) {
                zkb02 = toDataBaseList.get(b);
                if (zkb01.getEnrollNumber().equals(zkb02.getEnrollNumber()) && zkb01.getDateStr().equals(zkb02.getDateStr())) {
                    isHave = true;
                    zkb02.setTimeStr(zkb02.getTimeStr() + " " + zkb01.getTimeStr());
                }
            }
            if (!isHave) {
                zkb = new ZhongKongBean();
                zkb.setEnrollNumber(zkb01.getEnrollNumber());
                zkb.setMachineNum(zkb01.getMachineNum());
                zkb.setDateStr(zkb01.getDateStr());
                zkb.setTimeStr(zkb01.getTimeStr());
                zkb.setYearMonth(zkb01.getYearMonth());
                toDataBaseList.add(zkb);
            }
        }
        IPersonServ testDomainMapper = SpringUtil.getBean(IPersonServ.class);


        List<Employee> employeeList = testDomainMapper.findAllEmployeeNotIsQuitandhaveEnrollNum();
        boolean isComin = false;
        Integer nu = null;
        ZhongKongBean zkbb = null;
        for (Employee ee : employeeList) {
            isComin = false;
            for (ZhongKongBean zk : toDataBaseList) {
                if (ee.getEnrollNumber().equals(zk.getEnrollNumber())) {
                    nu = zk.getMachineNum();
                    isComin = true;
                    break;
                }
            }
            if (!isComin) {
                zkbb = new ZhongKongBean();
                zkbb.setEnrollNumber(ee.getEnrollNumber().toString());
                zkbb.setYearMonth(afterDay[0] + "-" + afterDay[1]);
                zkbb.setDateStr(beforDay);
                zkbb.setTimeStr("");
                zkbb.setMachineNum(nu);
                toDataBaseList.add(zkbb);
            }
        }

        testDomainMapper.saveBeforeDayZhongKongData(toDataBaseList);

        List<OutClockIn> ociList = new ArrayList<OutClockIn>();
        OutClockIn oc = new OutClockIn();
        oc.setClockInDateStr(toDataBaseList.get(0).getDateStr());
        ociList.add(oc);
        List<KQBean> kqBeanList = new ArrayList<KQBean>();
        KQBean kq = null;
        List<KQBean> kqBeans = testDomainMapper.getAllKQDataByYearMonthDay(toDataBaseList.get(0).getDateStr());
        List<KQBean> newKQBeans = testDomainMapper.getAfterOperatorDataByOriginData(ociList, kqBeans);
        testDomainMapper.saveAllNewKQBeansToMysql(newKQBeans);
        ociList.clear();

    }


    public void fillRightDeptIdToEmployee() throws Exception {
        IPersonServ testDomainMapper = SpringUtil.getBean(IPersonServ.class);
        List<Employee> employeeList = testDomainMapper.findAllEmployeeAll();
        for (Employee ee : employeeList) {
            ee.setDeptId(testDomainMapper.getDeptIdByDeptName(ee.getDeptName()));
            testDomainMapper.updateEmployeeDeptIdById(ee.getId(), ee.getDeptId());
        }
    }


    public void fillEmpNoWhenQYWXNull() throws Exception {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        IPersonServ ps = SpringUtil.getBean(IPersonServ.class);
        ps.fillEmpNoWhenQYWXNull();
    }


    public void getAllWeiXinUser() throws Exception {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        IPersonServ ps = SpringUtil.getBean(IPersonServ.class);
        List<WeiXinUsrId> userList = WeiXinUtil.getAddressBook(jedis.get(Constants.accessToken));
        WeiXinUsrId oldWX = null;
        List<String> haves = new ArrayList<String>();
        haves.clear();
        List<WeiXinUsrId> newUserList = new ArrayList<WeiXinUsrId>();
        for (WeiXinUsrId ww : userList) {
            if (!haves.contains(ww.getUserid())) {
                newUserList.add(ww);
                haves.add(ww.getUserid());
            }
        }
        for (WeiXinUsrId wx : newUserList) {
            oldWX = ps.getUserIdByUSerId(wx.getUserid());
            if (oldWX == null) {
                ps.saveWeiXinUserIdByBean(wx);
            }
        }


    }

    public void getBeforeDayQYWCData(String beforDay) throws Exception {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        IPersonServ testDomainMapper = SpringUtil.getBean(IPersonServ.class);
        String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=%s", jedis.get(Constants.accessToken));
        List<String> userList = testDomainMapper.getAllUserName();
        List<OutClockIn> outClockInList = new ArrayList<OutClockIn>();
        List<String> haveUserId = new ArrayList<String>();
        List<String> afterUserList1 = userList.subList(0, 99);
        List<String> afterUserList2 = userList.subList(99, 199);
        List<String> afterUserList3 = userList.subList(199, 299);
        List<String> afterUserList4 = userList.subList(299, userList.size());

        List<List<String>> all = new ArrayList<List<String>>();
        all.add(afterUserList1);
        all.add(afterUserList2);
        all.add(afterUserList3);
        all.add(afterUserList4);
        String day = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = sdf.parse(beforDay + " 00:00:00");
        Date dateEnd = sdf.parse(beforDay + " 23:59:59");
        QYweixinSend text = new QYweixinSend();
        text.setOpencheckindatatype(2);
        text.setStarttime(dateStart.getTime() / 1000);
        text.setEndtime(dateEnd.getTime() / 1000);
        NetWorkHelper netHelper = new NetWorkHelper();
        for (int aa = 0; aa < all.size(); aa++) {
            text.setUseridlist(all.get(aa));
            String result = netHelper.getHttpsResponse2(Url, text, "POST");
            JSONObject json = JSON.parseObject(result);
            String checkindata = String.valueOf(json.get("checkindata"));
            com.alibaba.fastjson.JSONArray checkindataStr = JSON.parseArray(checkindata);
            List<OutPunch> outPunchList = JSONUtils.toList(checkindataStr, OutPunch.class);
            OutClockIn oc = null;
            String hourStr = null;
            Integer hour = null;
            String userId = null;
            for (int a = 0; a < outPunchList.size(); a++) {
                userId = outPunchList.get(a).getUserid();
                if (haveUserId.contains(userId))
                    continue;
                oc = new OutClockIn();
                oc.setUserid(userId);
                oc.setClockInDateStr(outPunchList.get(a).getCheckin_timeStr().split(" ")[0]);
                for (OutPunch op : outPunchList) {
                    if (op.getUserid().equals(userId)) {
                        hourStr = op.getCheckin_timeStr().split(" ")[1];
                        hour = Integer.valueOf(hourStr.split(":")[0]);
                        if (hour < 12 && hour >= 0) {
                            oc.setClockInDateAMOnStr(op.getCheckin_timeStr());
                            oc.setClockInAddrAMOn(op.getLocation_detail());
                            oc.setAmOnUrl((op == null || op.getMediaids() == null || op.getMediaids().length == 0 || op.getMediaids()[0] == null) ? "" : op.getMediaids()[0]);
                            oc.setCheckin_typeA(op == null || op.getCheckin_type() == null ? "" : op.getCheckin_type());
                            oc.setException_typeA(op == null || op.getException_type() == null ? "" : op.getException_type());
                            oc.setNotesA(op == null || op.getNotes() == null ? "" : op.getNotes());
                        } else if (hour >= 12 && hour <= 18) {
                            oc.setClockInDatePMOnStr(op.getCheckin_timeStr());
                            oc.setClockInAddrPMOn(op.getLocation_detail());
                            oc.setPmOnUrl((op == null || op.getMediaids() == null || op.getMediaids().length == 0 || op.getMediaids()[0] == null) ? "" : op.getMediaids()[0]);
                            oc.setCheckin_typeP(op == null || op.getCheckin_type() == null ? "" : op.getCheckin_type());
                            oc.setException_typeP(op == null || op.getException_type() == null ? "" : op.getException_type());
                            oc.setNotesP(op == null || op.getNotes() == null ? "" : op.getNotes());
                        } else if (hour > 18 && hour <= 24) {
                            oc.setClockInDateNMOnStr(op.getCheckin_timeStr());
                            oc.setClockInAddNMOn(op.getLocation_detail());
                            oc.setNmOnUrl((op == null || op.getMediaids() == null || op.getMediaids().length == 0 || op.getMediaids()[0] == null) ? "" : op.getMediaids()[0]);
                            oc.setCheckin_typeN(op == null || op.getCheckin_type() == null ? "" : op.getCheckin_type());
                            oc.setException_typeN(op == null || op.getException_type() == null ? "" : op.getException_type());
                            oc.setNotesN(op == null || op.getNotes() == null ? "" : op.getNotes());
                        }
                    }
                }
                outClockInList.add(oc);
                haveUserId.add(userId);
            }
        }
        testDomainMapper.saveOutClockInList(outClockInList);
    }


    public void getBeforeDayQYWXSPData(String beforDay) throws Exception {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<QYWXSPFROM> fromList = new ArrayList<QYWXSPFROM>();
        jedis = pool.getResource();
        IPersonServ testDomainMapper = SpringUtil.getBean(IPersonServ.class);
        String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/corp/getapprovaldata?access_token=%s", jedis.get(Constants.accessTokensp));
        //String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/corp/getapprovaldata?access_token=%s", "rP8GeAzA2vzzsV0jCqT3thu2REEDpW0sP6ef1r67vp2717iLeN89XWBndcGxxG22eQPvRz7PLZu80gmQ_Actlp2kJxxfM8Um5EUY1mTbRDuOHdmsVzcPUoRi3yvQd0atkEWu4B7v-FfFN9_02lnJSN-YPo2GrUpwOW8_HNXE_tleal38nN4Co85YyrRpmd0x7-5ap3IlGjpAyg-OJn93PA");
        Date dateStart = sdf.parse(beforDay + " 00:00:00");
        Date dateEnd = sdf.parse(beforDay + " 23:59:59");
        QYWXSPTO text = new QYWXSPTO();
        text.setStarttime(dateStart.getTime() / 1000);
        text.setEndtime(dateEnd.getTime() / 1000);
        text.setNext_spnum(null);
        NetWorkHelper netHelper = new NetWorkHelper();
        String result = netHelper.getHttpsResponse3(Url, text, "POST");
        JSONObject json = JSON.parseObject(result);
        com.alibaba.fastjson.JSONArray jsonArrayData = json.getJSONArray("data");
        com.alibaba.fastjson.JSONArray jsonArrayData2 = null;
        com.alibaba.fastjson.JSONArray jsonArrayData3 = null;
        JSONObject data = null;
        JSONObject data2 = null;
        JSONObject data3 = null;
        JSONObject data4 = null;
        QYWXSPFROM from = null;
        String title;
        String spname;
        for (int i = 0; i < jsonArrayData.size(); i++) {
            data = jsonArrayData.getJSONObject(i);
            spname = (String) data.get("spname");
            if ("补卡申请".equals(spname)) {
                from = new QYWXSPFROM();
                from.setSpname(spname);
                from.setApply_name((String) data.get("apply_name"));
                from.setApply_org((String) data.get("apply_org"));
                from.setSp_status((Integer) data.get("sp_status"));
                from.setSp_num((Long) data.get("sp_num"));
                from.setApply_time((Integer) data.get("apply_time"));
                from.setApply_user_id((String) data.get("apply_user_id"));
                data2 = data.getJSONObject("comm");
                jsonArrayData2 = data2.getJSONArray("apply_data");
                for (int j = 0; j < jsonArrayData2.size(); j++) {
                    data3 = jsonArrayData2.getJSONObject(j);
                    title = (String) data3.get("title");
                    if (title.equals("补卡时间")) {
                        from.setBukaTimeLong((Long) data3.get("value"));
                        Date date = new Date(from.getBukaTimeLong());
                        from.setBukaTime(sdf.format(date).split(" ")[1]);
                        from.setBukaYearM(sdf.format(date).split(" ")[0]);
                    }
                    if (title.equals("补卡事由")) {
                        from.setBukaReason((String) data3.get("value"));
                    }
                }
                if (2 == (Integer) data.get("sp_status")) {
                    fromList.add(from);
                }
            }

            if (spname.contains("请假")) {
                from = new QYWXSPFROM();
                from.setSpname("请假");
                from.setApply_name((String) data.get("apply_name"));
                from.setApply_org((String) data.get("apply_org"));
                from.setSp_status((Integer) data.get("sp_status"));
                from.setSp_num((Long) data.get("sp_num"));
                from.setApply_time((Integer) data.get("apply_time"));
                from.setApply_user_id((String) data.get("apply_user_id"));
                data2 = data.getJSONObject("comm");
                jsonArrayData2 = data2.getJSONArray("apply_data");

                for (int j = 0; j < jsonArrayData2.size(); j++) {
                    data3 = jsonArrayData2.getJSONObject(j);
                    title = (String) data3.get("title");
                    if (title.equals("请假类型")) {
                        from.setLeave_type(StringUtil.LeaveTypeTransf((String) data3.get("value")));
                    }
                    if (title.equals("请假事由")) {
                        from.setBukaReason((String) data3.get("value"));
                    }

                    if (title.equals("请假")) {
                        jsonArrayData3 = data3.getJSONArray("value");
                        for (int a = 0; a < jsonArrayData3.size(); a++) {
                            data4 = jsonArrayData3.getJSONObject(a);
                            title = (String) data4.get("title");
                            if (title.equals("开始时间")) {
                                from.setStart_time((Long) data4.get("value"));
                                Date date = new Date(from.getStart_time());
                                from.setStart_timeStr(sdf.format((date)));
                            }
                            if (title.equals("结束时间")) {
                                from.setEnd_time((Long) data4.get("value"));
                                Date date = new Date(from.getEnd_time());
                                from.setEnd_timeStr(sdf.format((date)));
                            }
                            if (title.equals("请假时长")) {
                                from.setDuration((Integer) data4.get("value"));
                            }

                        }
                    }
                }

                if (2 == (Integer) data.get("sp_status")) {
                    fromList.add(from);
                }
            }

            if (spname.contains("外出申请")) {
                from = new QYWXSPFROM();
                from.setSpname("外出申请");
                from.setApply_name((String) data.get("apply_name"));
                from.setApply_org((String) data.get("apply_org"));
                from.setSp_status((Integer) data.get("sp_status"));
                from.setSp_num((Long) data.get("sp_num"));
                from.setApply_time((Integer) data.get("apply_time"));
                Date date = new Date(from.getApply_time());
                from.setApply_timeStr(sdf.format((date)));
                from.setApply_user_id((String) data.get("apply_user_id"));
                data2 = data.getJSONObject("comm");
                jsonArrayData2 = data2.getJSONArray("apply_data");

                for (int j = 0; j < jsonArrayData2.size(); j++) {
                    data3 = jsonArrayData2.getJSONObject(j);
                    title = (String) data3.get("title");
                    if (title.equals("外出地点")) {
                        from.setOutAddr((String) data3.get("value"));
                    }
                    if (title.equals("外出事由")) {
                        from.setBukaReason((String) data3.get("value"));
                    }

                    if (title.equals("外出")) {
                        jsonArrayData3 = data3.getJSONArray("value");
                        for (int a = 0; a < jsonArrayData3.size(); a++) {
                            data4 = jsonArrayData3.getJSONObject(a);
                            title = (String) data4.get("title");
                            if (title.equals("开始时间")) {
                                from.setStart_time((Long) data4.get("value"));
                                date = new Date(from.getStart_time());
                                from.setStart_timeStr(sdf.format((date)));
                            }
                            if (title.equals("结束时间")) {
                                from.setEnd_time((Long) data4.get("value"));
                                date = new Date(from.getEnd_time());
                                from.setEnd_timeStr(sdf.format((date)));
                            }
                            if (title.equals("外出时长")) {
                                from.setDuration((Integer) data4.get("value"));
                            }

                        }
                    }
                }

                if (2 == (Integer) data.get("sp_status")) {
                    fromList.add(from);
                }
            }


            if (spname.contains("出差")) {
                from = new QYWXSPFROM();
                from.setSpname("出差");
                from.setApply_name((String) data.get("apply_name"));
                from.setApply_org((String) data.get("apply_org"));
                from.setSp_status((Integer) data.get("sp_status"));
                from.setSp_num((Long) data.get("sp_num"));
                from.setApply_time((Integer) data.get("apply_time"));
                Date date = new Date(from.getApply_time());
                from.setApply_timeStr(sdf.format((date)));
                from.setApply_user_id((String) data.get("apply_user_id"));
                data2 = data.getJSONObject("comm");
                jsonArrayData2 = data2.getJSONArray("apply_data");

                for (int j = 0; j < jsonArrayData2.size(); j++) {
                    data3 = jsonArrayData2.getJSONObject(j);
                    title = (String) data3.get("title");
                    if (title.equals("出差地点")) {
                        from.setOutAddr((String) data3.get("value"));
                    }
                    if (title.equals("出差事由")) {
                        from.setBukaReason((String) data3.get("value"));
                    }

                    if (title.equals("出差")) {
                        jsonArrayData3 = data3.getJSONArray("value");
                        for (int a = 0; a < jsonArrayData3.size(); a++) {
                            data4 = jsonArrayData3.getJSONObject(a);
                            title = (String) data4.get("title");
                            if (title.equals("开始时间")) {
                                from.setStart_time((Long) data4.get("value"));
                                date = new Date(from.getStart_time());
                                from.setStart_timeStr(sdf.format((date)));
                            }
                            if (title.equals("结束时间")) {
                                from.setEnd_time((Long) data4.get("value"));
                                date = new Date(from.getEnd_time());
                                from.setEnd_timeStr(sdf.format((date)));
                            }
                            if (title.equals("出差时长")) {
                                from.setDuration((Integer) data4.get("value"));
                            }

                        }
                    }
                }

                if (2 == (Integer) data.get("sp_status")) {
                    fromList.add(from);
                }
            }


        }
        testDomainMapper.saveQKData(fromList);
    }

    public void getBeforeDayQYWCDataAAA(String beforDay) throws Exception {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        IPersonServ testDomainMapper = SpringUtil.getBean(IPersonServ.class);
        String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=%s", jedis.get(Constants.accessToken));
        List<String> userList = testDomainMapper.getAllUserName();
        List<ZhongKongBeanQY> outClockInList = new ArrayList<ZhongKongBeanQY>();
        List<String> haveUserId = new ArrayList<String>();
        List<String> afterUserList1 = userList.subList(0, 99);
        List<String> afterUserList2 = userList.subList(99, 199);
        List<String> afterUserList3 = userList.subList(199, 299);
        List<String> afterUserList4 = userList.subList(299, userList.size());

        List<List<String>> all = new ArrayList<List<String>>();
        all.add(afterUserList1);
        all.add(afterUserList2);
        all.add(afterUserList3);
        all.add(afterUserList4);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart = sdf.parse(beforDay + " 00:00:00");
        Date dateEnd = sdf.parse(beforDay + " 23:59:59");
        QYweixinSend text = new QYweixinSend();
        text.setOpencheckindatatype(1);
        text.setStarttime(dateStart.getTime() / 1000);
        text.setEndtime(dateEnd.getTime() / 1000);
        NetWorkHelper netHelper = new NetWorkHelper();
        for (int aa = 0; aa < all.size(); aa++) {
            text.setUseridlist(all.get(aa));
            String result = netHelper.getHttpsResponse2(Url, text, "POST");
            JSONObject json = JSON.parseObject(result);
            String checkindata = String.valueOf(json.get("checkindata"));
            com.alibaba.fastjson.JSONArray checkindataStr = JSON.parseArray(checkindata);
            List<OutPunch> outPunchList = JSONUtils.toList(checkindataStr, OutPunch.class);
            ZhongKongBeanQY oc = null;
            String hourStr = null;
            Integer hour = null;
            String userId = null;
            for (int a = 0; a < outPunchList.size(); a++) {
                userId = outPunchList.get(a).getUserid();
                if (haveUserId.contains(userId))
                    continue;
                oc = new ZhongKongBeanQY();
                oc.setUserId(userId);
                oc.setDateStr(outPunchList.get(a).getCheckin_timeStr().split(" ")[0]);
                oc.setYearMonth(beforDay.split("-")[0] + "-" + beforDay.split("-")[1]);
                for (OutPunch op : outPunchList) {
                    if (op.getUserid().equals(userId)) {
                        if (op != null && (op.getException_type() == null || "".equals(op.getException_type()) || "时间异常".equals(op.getException_type()))) {
                            oc.setTimeStr((oc.getTimeStr() == null ? "" : oc.getTimeStr()) + op.getCheckin_timeStr().split(" ")[1] + " ");
                            oc.setRemark((oc.getRemark() == null ? "" : oc.getRemark()) + op.getNotes());
                        }
                    }
                }
                outClockInList.add(oc);
                haveUserId.add(userId);
            }
        }
        //outClockInList  = new ArrayList<ZhongKongBeanQY>(new HashSet<ZhongKongBeanQY>(outClockInList));
        testDomainMapper.saveZKQYList(outClockInList);
    }


    @ResponseBody
    @RequestMapping(value = "/queryAttendance", method = RequestMethod.POST)
    public String queryAttendance(@RequestBody String params, HttpServletRequest request, HttpServletResponse
            response) {
        return null;
    }


    @ResponseBody
    @RequestMapping("/toworkdatepage")
    public ModelAndView toworkdatepage() throws Exception {
        ModelAndView view = new ModelAndView("workdatepage");
        List<Employee> empList = personServ.findAllEmployeeAll();
        view.addObject("empList", empList);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/queryZKOUTDataByCondition", method = RequestMethod.POST)
    public void queryZKOUTDataByCondition(Employee kqBean, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            List<Employee> financeImportDataList = personServ.findAllZKAndOutDataCondition(kqBean);
            int recordCount = personServ.findAllZKAndOutDataConditionCount(kqBean);
            int maxPage = recordCount % kqBean.getPageSize() == 0 ? recordCount / kqBean.getPageSize() : recordCount / kqBean.getPageSize() + 1;
            if (financeImportDataList.size() > 0) {
                financeImportDataList.get(0).setMaxPage(maxPage);
                financeImportDataList.get(0).setRecordCount(recordCount);
                financeImportDataList.get(0).setCurrentPage(kqBean.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(financeImportDataList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/showDateAByMonthAAA", method = RequestMethod.POST)
    public void showDateAByMonthAAA(Employee kqBean, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            List<String> dateStr = personServ.getAllKQDateListABC(kqBean.getYearMonth());
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(dateStr);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryLSZKOUTDataByCondition", method = RequestMethod.POST)
    public void queryLSZKOUTDataByCondition(Employee kqBean, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            List<Employee> financeImportDataList = personServ.findAllLSZKAndOutDataCondition(kqBean);
            int recordCount = personServ.findAllLSZKAndOutDataConditionCount(kqBean);
            int maxPage = recordCount % kqBean.getPageSize() == 0 ? recordCount / kqBean.getPageSize() : recordCount / kqBean.getPageSize() + 1;
            if (financeImportDataList.size() > 0) {
                financeImportDataList.get(0).setMaxPage(maxPage);
                financeImportDataList.get(0).setRecordCount(recordCount);
                financeImportDataList.get(0).setCurrentPage(kqBean.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(financeImportDataList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/importMKDataByDates")
    public void importMKDataByDates(Employee employee, HttpServletResponse response, HttpSession
            session, HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("downloadstatus")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        String filename = new Date().getTime() + ".xls";
        employee.setPageSize(10000);
        List<MonthKQInfo> financeImportDataList = personServ.findAllMonthKQDataByConditionABC(employee);

        List<MonthKQInfo> financeImportDataList1 = personServ.findAllMonthKQDataByConditionBDF(employee);

        String wd = personServ.getWorkDateByMonthC(employee.getClockDateArray().get(0));
        String wd2 = personServ.getWorkDateByMonthD(employee.getClockDateArray().get(0));
        new MKExcelUtil().writeMKdataTOExcel(financeImportDataList, financeImportDataList1, this.finalDirPath + "linshi/", filename, employee.getClockDateArray().get(0), wd, wd2);
        final File result = new File(this.finalDirPath + "linshi/" + filename);
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
            byte[] buff = new byte[1024];
            outputStream = response.getOutputStream();
            FileInputStream fis = new FileInputStream(result);
            bufferedInputStream = new BufferedInputStream(fis);
            int num = bufferedInputStream.read(buff);
            Cookie cookie = new Cookie("downloadstatus", String.valueOf(new Date().getTime()));
            cookie.setMaxAge(5 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            while (num != -1) {
                outputStream.write(buff, 0, num);
                outputStream.flush();
                num = bufferedInputStream.read(buff);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        } finally {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
        }
    }


    @ResponseBody
    @RequestMapping(value = "/computeSignKQByMonth", method = RequestMethod.POST)
    public void computeSignKQByMonth(MonthKQInfo mk, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            boolean isSuccess = personServ.computeSignKQByMonth(mk != null ? mk.getYearMonth() : null);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSuccess);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/checkMKDataByDates")
    public ModelAndView checkMKDataByDates(Employee employee, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("monthkqinfo");
        try {
            personServ.updateRenShiByDates(employee.getClockDates());
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            employee.setPageSize(3);
            List<Position> positionList = personServ.findAllPositionAll();
            List<String> kqDateList = personServ.getAllKQDateList();
            List<String> kqMonthList = personServ.getAllMKMonthList();
            String[] yearMonth = null;
            String today = null;
            if (kqMonthList != null) {
                today = kqDateList.get(0);
                yearMonth = today.split("-");
            }
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            employee.setYearMonth(yearMonth[0] + "-" + yearMonth[1]);
            List<MonthKQInfo> financeImportDataList = personServ.findAllMonthKQDataByCondition(employee);
            int recordCount = personServ.findAllMonthKQDataCountByCondition(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("financeImportDataList", financeImportDataList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            view.addObject("kqDateList", kqDateList);
            view.addObject("kqMonthList", kqMonthList);
            view.addObject("today", today);
            view.addObject("flag", 1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
        return view;
    }

    @ResponseBody
    @RequestMapping("/queryMonthKQListByCondition")
    public ModelAndView queryMonthKQListByCondition(Employee employee, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("monthkqinfo");
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            employee.setPageSize(3);
            List<Position> positionList = personServ.findAllPositionAll();
            List<String> kqDateList = personServ.getAllKQDateList();
            List<String> kqMonthList = personServ.getAllMKMonthList();
            String[] yearMonth = null;
            String today = null;
            if (kqMonthList != null) {
                today = kqDateList.get(0);
                yearMonth = today.split("-");
            }
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeLinShiAll();
            employee.setYearMonth(yearMonth[0] + "-" + yearMonth[1]);
            List<MonthKQInfo> financeImportDataList = personServ.findAllMonthKQDataByCondition(employee);
            int recordCount = personServ.findAllMonthKQDataCountByCondition(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("financeImportDataList", financeImportDataList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            view.addObject("kqDateList", kqDateList);
            view.addObject("kqMonthList", kqMonthList);
            view.addObject("today", today);
            view.addObject("flag", 2);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
        return view;
    }

    @ResponseBody
    @RequestMapping("/toMonthKQList")
    public ModelAndView toMonthKQList(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("monthkqinfo");
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            Employee employee = new Employee();
            employee.setPageSize(3);
            List<Position> positionList = personServ.findAllPositionAll();
            List<String> kqDateList = personServ.getAllKQDateListAAA();
            List<String> kqMonthList = personServ.getAllMKMonthList();
            String[] yearMonth = null;
            String today = null;
            if (kqMonthList != null) {
                today = kqDateList.get(0);
                yearMonth = today.split("-");
            }
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeLinShiAll();
            List<MonthKQInfo> financeImportDataList = personServ.findAllMonthKQData(yearMonth[0] + "-" + yearMonth[1], employee);
            int recordCount = personServ.findAllMonthKQDataCount(yearMonth[0] + "-" + yearMonth[1]);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("financeImportDataList", financeImportDataList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            view.addObject("kqDateList", kqDateList);
            view.addObject("kqMonthList", kqMonthList);
            view.addObject("today", today);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/queryKQBDataByCondition", method = RequestMethod.POST)
    public void queryKQBDataByCondition(Employee kqBean, HttpServletResponse response, HttpSession session) throws
            Exception {
        List<KQBean> financeImportDataList = null;
        List<Integer> nameIds;
        List<Integer> nameIds2;
        int recordCount = 0;
        try {

            if (kqBean.getClockDates().size() > 0 || kqBean.getNameIds().size() > 0 ||
                    (kqBean.getEmpNo() != null && kqBean.getEmpNo().trim().length() > 0)
                    || kqBean.getDeptIds().size() > 0 || kqBean.getWorkTypes().size() > 0 || kqBean.getPositionIds().size() > 0
                    || (kqBean.getSortMethod() != null && !"undefined".equals(kqBean.getSortMethod())
                    && !"undefined".equals(kqBean.getSortByName()) && kqBean.getSortByName() != null)) {
                if (kqBean.getNameIds().size() > 0) {
                    nameIds2 = new ArrayList<Integer>();
                    nameIds = new ArrayList<Integer>();
                    for (Integer id : kqBean.getNameIds()) {
                        if (id >= 10000) {
                            nameIds2.add(id);
                        } else {
                            nameIds.add(id);
                        }
                    }
                    kqBean.setNameIds(nameIds);
                    kqBean.setNameIds2(nameIds2);
                }
                financeImportDataList = personServ.findAllKQBDataCondition(kqBean);
                recordCount = personServ.findAllKQBDataConditionCount(kqBean);
            } else {
                financeImportDataList = personServ.findAllKQBData(kqBean);
                recordCount = personServ.findAllKQBDataCount();
            }
            int maxPage = recordCount % kqBean.getPageSize() == 0 ? recordCount / kqBean.getPageSize() : recordCount / kqBean.getPageSize() + 1;
            if (financeImportDataList.size() > 0) {
                financeImportDataList.get(0).setMaxPage(maxPage);
                financeImportDataList.get(0).setRecordCount(recordCount);
                financeImportDataList.get(0).setCurrentPage(kqBean.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(financeImportDataList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/toCompreAttenRecordPage")
    public ModelAndView toCompreAttenRecordPage(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("compreattenrecorddata");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<String> kqDateList = personServ.getAllKQDateList();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<String> kqMonthList = personServ.getAllKQMonthListKQBean();
        List<Employee> empList = personServ.findAllEmployeeLinShiAll();
        List<KQBean> financeImportDataList = personServ.findAllKQBData(employee);
        int recordCount = personServ.findAllKQBDataCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("financeImportDataList", financeImportDataList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("kqMonthList", kqMonthList);
        view.addObject("userInfo", userInfo);
        view.addObject("kqDateList", kqDateList);
        return view;
    }


    @ResponseBody
    @RequestMapping("/checkAttenClockByDate")
    public ModelAndView checkAttenClockByDate(String[] clockDateArray, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("compreattenrecorddata");
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<OutClockIn> clockDates = new ArrayList<OutClockIn>();
            OutClockIn oc = null;
            StringBuilder sb = new StringBuilder();
            for (String str : clockDateArray) {
                oc = new OutClockIn();
                oc.setClockInDateStr(str);
                clockDates.add(oc);
                sb.append(str + " ");
            }
            personServ.saveCheckKQBeanListByDates(clockDates);
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<String> kqDateList = personServ.getAllKQDateList();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<KQBean> financeImportDataList = personServ.findAllKQBData(employee);
            int recordCount = personServ.findAllKQBDataCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("financeImportDataList", financeImportDataList) ;
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            view.addObject("kqDateList", kqDateList);
            view.addObject("flagb", "启用" + sb.toString() + "考勤成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return view;
    }

    @ResponseBody
    @RequestMapping("/reComputeAttenClockByDate")
    public ModelAndView reComputeAttenClockByDate(String[] clockDateArray, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("compreattenrecorddata");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<OutClockIn> clockDates = new ArrayList<OutClockIn>();
        OutClockIn oc = null;
        StringBuilder sb = new StringBuilder();
        for (String str : clockDateArray) {
            oc = new OutClockIn();
            oc.setClockInDateStr(str);
            clockDates.add(oc);
            sb.append(str + " ");
        }

        String isAlreadyCheck = personServ.getAlReadyCheckDatestr(clockDates);
        if (isAlreadyCheck == null || isAlreadyCheck.trim().length() == 0) {
            personServ.deleteKQBeanOlderDateByDates(clockDates);
            List<KQBean> kqBeans = personServ.getAllKQDataByYearMonthDays(clockDates);
            List<KQBean> newKQBeans = personServ.getAfterOperatorDataByOriginData(clockDates, kqBeans);
            List<KQBean> linshiKQBeans = personServ.getgetAfterOperatorDataByOriginDataLinShi(clockDates, kqBeans);
            //List<KQBean> zhongdianKQBeans = personServ.getgetAfterOperatorDataByOriginDataZhongDian(clockDates, kqBeans);
            newKQBeans.addAll(linshiKQBeans);
            //newKQBeans.addAll(zhongdianKQBeans);
            personServ.saveAllNewKQBeansToMysql(newKQBeans);
            view.addObject("flagb", "重新计算" + sb.toString() + "考勤成功!");
        } else {
            sb = new StringBuilder(isAlreadyCheck);
            view.addObject("flagb", "无法重新计算,因为" + sb.toString() + "的考勤已启用");
        }

        try {
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<String> kqDateList = personServ.getAllKQDateList();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<String> kqMonthList = personServ.getAllKQMonthListKQBean();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<KQBean> financeImportDataList = personServ.findAllKQBData(employee);
            int recordCount = personServ.findAllKQBDataCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("financeImportDataList", financeImportDataList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            view.addObject("kqDateList", kqDateList);
            view.addObject("kqMonthList", kqMonthList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return view;
    }

    @ResponseBody
    @RequestMapping("/toLinShiKaoQin")
    public ModelAndView toLinShiKaoQin(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("linshizk");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllLinShiEmpAll();
        List<String> zkYearMonthList = personServ.findAllZKYearMonthList();
        List<Employee> financeImportDataList = personServ.findAllLSZKAndOutData(employee);
        int recordCount = personServ.findAllLSZKAndOutDataCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("financeImportDataList", financeImportDataList);
        view.addObject("empList", empList);
        view.addObject("kqMonthList", zkYearMonthList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping("/toZKandOutDataAll")
    public ModelAndView toZKandOutDataAll(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("zkandoutdata");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<String> zkYearMonthList = personServ.findAllZKYearMonthList();
        List<Employee> financeImportDataList = personServ.findAllZKAndOutData(employee);
        int recordCount = personServ.findAllZKAndOutDataCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("financeImportDataList", financeImportDataList);
        view.addObject("empList", empList);
        view.addObject("kqMonthList", zkYearMonthList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping("/toQuickQuery")
    public ModelAndView toQuickQuery(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("quickquery");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Employee> empList = personServ.findEmpAndLinS();
        view.addObject("empList", empList);
        view.addObject("userInfo", userInfo);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/queryQQByCondition", method = RequestMethod.POST)
    public void queryQQByCondition(KQBean kqb, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        StringBuilder sb = new StringBuilder();
        List<ReturnString> rsList = personServ.getAllDataByName(kqb.getName(), kqb.getDateStr());
        String str1 = null;
        ObjectMapper x = new ObjectMapper();
//        for (int a = 0; a < rsList.size(); a++) {
//            sb.append("<font color=\"#FF0000\" size=\"3\" th:align=\"right\">" + rsList.get(a).getTitleName() + "</font><br>");
//            sb.append("<font color=\"black\" size=\"2\" th:align=\"right\">" + rsList.get(a).getNeirong() + "</font><br>");
//            sb.append("<font color=\"black\" size=\"2\" th:align=\"right\">" + rsList.get(a).getRemark() + "</font><br><p>");
//        }
        try {
            str1 = x.writeValueAsString(rsList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/updateKQBeanDataByRenShi")
    public ModelAndView updateKQBeanDataByRenShi(KQBean kqBean, HttpSession session) throws Exception {
        Employee employee = new Employee();
        personServ.updateKQBeanDataByRenShi(kqBean.getId(), kqBean.getExtWorkHours(), kqBean.getClockResultByRenShi());
        employee.setCurrentPage(kqBean.getCurrentPage());
        ModelAndView view = new ModelAndView("compreattenrecorddata");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<KQBean> financeImportDataList = personServ.findAllKQBData(employee);
        int recordCount = personServ.findAllKQBDataCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("financeImportDataList", financeImportDataList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        view.addObject("flag", 3);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/saveQianKaDateToMysql", method = RequestMethod.POST)
    public void saveQianKaDateToMysql(QianKa qianKa, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            int isSave = personServ.saveQianKaDateToMysql(qianKa);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveYeBanDateToMysql", method = RequestMethod.POST)
    public void saveYeBanDateToMysql(YeBan yeBan, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            int isSave = personServ.saveYeBanDateToMysql(yeBan);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/saveLianBanDateToMysql", method = RequestMethod.POST)
    public void saveLianBanDateToMysql(LianBan lianBan, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            int isSave = personServ.saveLianBanDateToMysql(lianBan);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/saveLinShiDateToMysql", method = RequestMethod.POST)
    public void saveLinShiDateToMysql(LinShiHours lsh, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            int isSave = personServ.saveLinShiDateToMysql(lsh);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveOutDateToMysql", method = RequestMethod.POST)
    public void saveOutDateToMysql(Out out, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            int isSave = personServ.saveOutDateToMysql(out);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/savePinShiDateToMysql", method = RequestMethod.POST)
    public void savePinShiDateToMysql(PinShiJiaBanBGS pinShiJiaBanBGS, HttpServletResponse response, HttpSession
            session) throws Exception {
        try {
            int isSave = personServ.savePinShiDateToMysql(pinShiJiaBanBGS);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/saveTiaoXiuDateToMysql", method = RequestMethod.POST)
    public void saveTiaoXiuDateToMysql(TiaoXiu tiaoXiu, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            int isSave = personServ.saveTiaoXiuDateToMysql(tiaoXiu);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveJiaBanDateToMysql", method = RequestMethod.POST)
    public void saveJiaBanDateToMysql(JiaBan jiaBan, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            int isSave = personServ.saveJiaBanDateToMysql(jiaBan);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveClockInSetUp", method = RequestMethod.POST)
    public void saveClockInSetUp(ClockInSetUp clockInSetUp, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            boolean isSave = personServ.saveClockInSetUp(clockInSetUp);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteClockInDataByOutDays")
    public ModelAndView deleteClockInDataByOutDays(ClockInSetUp clockInSetUp) throws Exception {
        ModelAndView view = new ModelAndView("outclockinset");
        personServ.deleteClockSetInByOutDays(clockInSetUp.getOutDays());
        List<ClockInSetUp> clockInSetUpList = personServ.findAllOutClockInSetUp();
        view.addObject("flag", 2);
        view.addObject("clockInSetUpList", clockInSetUpList);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/tooutsetpage")
    public ModelAndView tooutsetpage() throws Exception {
        ModelAndView view = new ModelAndView("outclockinset");
        List<ClockInSetUp> clockInSetUpList = personServ.findAllOutClockInSetUp();
        view.addObject("flag", 0);
        view.addObject("clockInSetUpList", clockInSetUpList);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/saveDAPCSetUp", method = RequestMethod.POST)
    public void saveDAPCSetUp(DaKaPianCha daKaPianCha, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            personServ.saveDAPCSetUp(daKaPianCha);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(1);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/toDaKaPianCha")
    public ModelAndView toDaKaPianCha() throws Exception {
        ModelAndView view = new ModelAndView("dakapiancha");
        DaKaPianCha dkpc = personServ.getDaKaPianCha();
        view.addObject("flag", 0);
        view.addObject("dkpc", dkpc);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateZhongKongNumByEmpNo")
    public ModelAndView saveOrUpdateZhongKongNumByEmpNo(WeiXinUsrId weiXinUsrId, HttpSession session) throws
            Exception {
        ModelAndView view = new ModelAndView("zhongkong");
        try {
            int isSaveOrUpdate = personServ.saveOrUpdateZhongKongIdByEmpNo(weiXinUsrId);
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Employee> employeeList = personServ.findAllEmployeeZhongKong(employee);
            int recordCount = personServ.findAllEmployeeZhongKongCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            view.addObject("flag", isSaveOrUpdate);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateGongZhongHaoIdByEmpNo")
    public ModelAndView saveOrUpdateGongZhongHaoIdByEmpNo(GongZhongHao gongZhongHao, HttpSession session) throws
            Exception {
        ModelAndView view = new ModelAndView("gongzhonghao");
        try {
            int isSaveOrUpdate = personServ.saveOrUpdateGongZhongHaoIdByEmpNo(gongZhongHao);
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Employee> employeeList = personServ.findAllEmployee(employee);
            int recordCount = personServ.findAllEmployeeCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            view.addObject("flag", isSaveOrUpdate);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/togongzhonghao")
    public ModelAndView togongzhonghao(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("gongzhonghao");
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Employee> employeeList = personServ.findAllEmployee(employee);
            int recordCount = personServ.findAllEmployeeCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/toZhongKong")
    public ModelAndView toZhongKong(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("zhongkong");
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Employee> employeeList = personServ.findAllEmployeeZhongKong(employee);
            int recordCount = personServ.findAllEmployeeZhongKongCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/toOutClockInListQuery")
    public ModelAndView toOutClockInListQuery(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("outclockin");
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Employee> employeeList = personServ.findAllEmployeeOutClockIn(employee);
            int recordCount = personServ.findAllEmployeeOutClockInCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/tocomputeworkdate")
    public ModelAndView tocomputeworkdate() throws Exception {
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            FileSystemView fsv = FileSystemView.getFileSystemView();
            File com = fsv.getHomeDirectory();
            Compute compute = new Compute();
            compute.setFileLocation(com.getPath());
            view.addObject("compute", compute);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toworksetpage")
    public ModelAndView toworksetpage() throws Exception {
        try {
            ModelAndView view = new ModelAndView("worksetpage");
            WorkSet workSet = new WorkSet();
            List<WorkSet> workSetList = personServ.findAllWorkSet(workSet);
            int recordCount = personServ.findAllWorkSetCount(workSet);
            int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
            workSet.setMaxPage(maxPage);
            workSet.setRecordCount(recordCount);
            view.addObject("workSet", workSet);
            view.addObject("workSetList", workSetList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/tolinshigongpage")
    public ModelAndView tolinshigongpage(HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("linshi");
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllLinShiEmpAll();
            List<Employee> employeeList = personServ.findAllLinShiEmp(employee);
            int recordCount = personServ.findAllLinShiEmpCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/tomainpage")
    public ModelAndView toUploadPage(HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("person");
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Employee> employeeList = personServ.findAllEmployee(employee);
            int recordCount = personServ.findAllEmployeeCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/toplusworkdan")
    public ModelAndView toplusworkdan() throws Exception {
        try {
            ModelAndView view = new ModelAndView("jiaban");
            JiaBan jiaBan = new JiaBan();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<JiaBan> jiaBanList = personServ.findAllJiaBan(jiaBan);
            int recordCount = personServ.findAllJiaBanCount();
            int maxPage = recordCount % jiaBan.getPageSize() == 0 ? recordCount / jiaBan.getPageSize() : recordCount / jiaBan.getPageSize() + 1;
            jiaBan.setMaxPage(maxPage);
            jiaBan.setRecordCount(recordCount);
            view.addObject("jiaBanList", jiaBanList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("jiaBan", jiaBan);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/deleteTiaoXiuDateToMysql")
    public ModelAndView deleteTiaoXiuDateToMysql(TiaoXiu tiaoXiu) throws Exception {
        try {
            personServ.deleteTiaoXiuById(tiaoXiu.getId());
            ModelAndView view = new ModelAndView("tiaoxiu");
            TiaoXiu jiaBan = new TiaoXiu();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<TiaoXiu> tiaoXiuList = personServ.findAllTiaoXiu(jiaBan);
            int recordCount = personServ.findAllTiaoXiuCount();
            int maxPage = recordCount % jiaBan.getPageSize() == 0 ? recordCount / jiaBan.getPageSize() : recordCount / jiaBan.getPageSize() + 1;
            jiaBan.setMaxPage(maxPage);
            jiaBan.setRecordCount(recordCount);
            view.addObject("tiaoXiuList", tiaoXiuList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("jiaBan", jiaBan);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/totiaoxiu")
    public ModelAndView totiaoxiu() throws Exception {
        try {
            ModelAndView view = new ModelAndView("tiaoxiu");
            TiaoXiu jiaBan = new TiaoXiu();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<TiaoXiu> tiaoXiuList = personServ.findAllTiaoXiu(jiaBan);
            int recordCount = personServ.findAllTiaoXiuCount();
            int maxPage = recordCount % jiaBan.getPageSize() == 0 ? recordCount / jiaBan.getPageSize() : recordCount / jiaBan.getPageSize() + 1;
            jiaBan.setMaxPage(maxPage);
            jiaBan.setRecordCount(recordCount);
            view.addObject("tiaoXiuList", tiaoXiuList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("jiaBan", jiaBan);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/tonightworkdan")
    public ModelAndView tonightworkdan() throws Exception {
        try {
            ModelAndView view = new ModelAndView("yeban");
            YeBan yeBan = new YeBan();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<YeBan> yeBanList = personServ.findAllYeBan(yeBan);
            int recordCount = personServ.findAllYeBanCount();
            int maxPage = recordCount % yeBan.getPageSize() == 0 ? recordCount / yeBan.getPageSize() : recordCount / yeBan.getPageSize() + 1;
            yeBan.setMaxPage(maxPage);
            yeBan.setRecordCount(recordCount);
            view.addObject("yeBanList", yeBanList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("yeBan", yeBan);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/deleteLinShiDateToMysql")
    public ModelAndView deleteLinShiDateToMysql(LinShiHours linShiHours) throws Exception {
        try {
            personServ.deleteLinShiDateToMysql(linShiHours);
            ModelAndView view = new ModelAndView("linshidan");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllLinShiEmpAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<LinShiHours> linShiHoursList = personServ.findAllLinShiDan(linShiHours);
            int recordCount = personServ.findAllLinShiDanCount();
            int maxPage = recordCount % linShiHours.getPageSize() == 0 ? recordCount / linShiHours.getPageSize() : recordCount / linShiHours.getPageSize() + 1;
            linShiHours.setMaxPage(maxPage);
            linShiHours.setRecordCount(recordCount);
            view.addObject("linShiHoursList", linShiHoursList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("lianBan", linShiHours);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/tolinshidan")
    public ModelAndView tolinshidan() throws Exception {
        try {
            ModelAndView view = new ModelAndView("linshidan");
            LinShiHours linShiHours = new LinShiHours();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllLinShiEmpAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<LinShiHours> linShiHoursList = personServ.findAllLinShiDan(linShiHours);
            int recordCount = personServ.findAllLinShiDanCount();
            int maxPage = recordCount % linShiHours.getPageSize() == 0 ? recordCount / linShiHours.getPageSize() : recordCount / linShiHours.getPageSize() + 1;
            linShiHours.setMaxPage(maxPage);
            linShiHours.setRecordCount(recordCount);
            view.addObject("linShiHoursList", linShiHoursList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("lianBan", linShiHours);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/tolianbandan")
    public ModelAndView tolianbandan() throws Exception {
        try {
            ModelAndView view = new ModelAndView("lianban");
            LianBan lianBan = new LianBan();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<LianBan> lianBanList = personServ.findAllLianBan(lianBan);
            int recordCount = personServ.findAllLianBanCount();
            int maxPage = recordCount % lianBan.getPageSize() == 0 ? recordCount / lianBan.getPageSize() : recordCount / lianBan.getPageSize() + 1;
            lianBan.setMaxPage(maxPage);
            lianBan.setRecordCount(recordCount);
            view.addObject("lianBanList", lianBanList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("lianBan", lianBan);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/toBanGongShiPinShiJiaBanRenYuan")
    public ModelAndView toBanGongShiPinShiJiaBanRenYuan() throws Exception {
        try {
            ModelAndView view = new ModelAndView("pingshijiabanBGS");
            PinShiJiaBanBGS pinShiJiaBanBGS = new PinShiJiaBanBGS();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAllOnlyBanGong();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<PinShiJiaBanBGS> pinShiJiaBanBGSList = personServ.findAllPinShi(pinShiJiaBanBGS);
            int recordCount = personServ.findAllPinShiCount();
            int maxPage = recordCount % pinShiJiaBanBGS.getPageSize() == 0 ? recordCount / pinShiJiaBanBGS.getPageSize() : recordCount / pinShiJiaBanBGS.getPageSize() + 1;
            pinShiJiaBanBGS.setMaxPage(maxPage);
            pinShiJiaBanBGS.setRecordCount(recordCount);
            view.addObject("pinShiJiaBanBGSList", pinShiJiaBanBGSList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("pinShiJiaBanBGS", pinShiJiaBanBGS);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toqiankadan")
    public ModelAndView toqiankadan() throws Exception {
        try {
            ModelAndView view = new ModelAndView("qianka");
            QianKa qianKa = new QianKa();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<QianKa> qianKaList = personServ.findAllQianKa(qianKa);
            int recordCount = personServ.findAllQianKaCount();
            int maxPage = recordCount % qianKa.getPageSize() == 0 ? recordCount / qianKa.getPageSize() : recordCount / qianKa.getPageSize() + 1;
            qianKa.setMaxPage(maxPage);
            qianKa.setRecordCount(recordCount);
            view.addObject("qianKaList", qianKaList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("qianKa", qianKa);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteJiaBanDateToMysql")
    public ModelAndView deleteJiaBanDateToMysql(JiaBan jiaBan) throws Exception {
        try {
            personServ.deleteJiaBanDateToMysql(jiaBan.getId());
            ModelAndView view = new ModelAndView("jiaban");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<JiaBan> jiaBanList = personServ.findAllJiaBan(jiaBan);
            int recordCount = personServ.findAllJiaBanCount();
            int maxPage = recordCount % jiaBan.getPageSize() == 0 ? recordCount / jiaBan.getPageSize() : recordCount / jiaBan.getPageSize() + 1;
            jiaBan.setMaxPage(maxPage);
            jiaBan.setRecordCount(recordCount);
            view.addObject("jiaBanList", jiaBanList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("jiaBan", jiaBan);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/deleteLianBanDateToMysql")
    public ModelAndView deleteLianBanDateToMysql(LianBan lianBan) throws Exception {
        try {
            personServ.deleteLianBanDateToMysql(lianBan.getId());
            ModelAndView view = new ModelAndView("lianban");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<LianBan> lianBanList = personServ.findAllLianBan(lianBan);
            int recordCount = personServ.findAllLianBanCount();
            int maxPage = recordCount % lianBan.getPageSize() == 0 ? recordCount / lianBan.getPageSize() : recordCount / lianBan.getPageSize() + 1;
            lianBan.setMaxPage(maxPage);
            lianBan.setRecordCount(recordCount);
            view.addObject("lianBanList", lianBanList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("lianBan", lianBan);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/deletePinShiDateToMysql")
    public ModelAndView deletePinShiDateToMysql(PinShiJiaBanBGS pinShiJiaBanBGS) throws Exception {
        try {
            personServ.deletePinShiDateToMysql(pinShiJiaBanBGS.getId());
            ModelAndView view = new ModelAndView("pingshijiabanBGS");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAllOnlyBanGong();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<PinShiJiaBanBGS> pinShiJiaBanBGSList = personServ.findAllPinShi(pinShiJiaBanBGS);
            int recordCount = personServ.findAllPinShiCount();
            int maxPage = recordCount % pinShiJiaBanBGS.getPageSize() == 0 ? recordCount / pinShiJiaBanBGS.getPageSize() : recordCount / pinShiJiaBanBGS.getPageSize() + 1;
            pinShiJiaBanBGS.setMaxPage(maxPage);
            pinShiJiaBanBGS.setRecordCount(recordCount);
            view.addObject("pinShiJiaBanBGSList", pinShiJiaBanBGSList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("pinShiJiaBanBGS", pinShiJiaBanBGS);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/deleteYeBanDateToMysql")
    public ModelAndView deleteYeBanDateToMysql(YeBan yeBan) throws Exception {
        try {
            personServ.deleteYeBanDateToMysql(yeBan.getId());
            ModelAndView view = new ModelAndView("yeban");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<YeBan> yeBanList = personServ.findAllYeBan(yeBan);
            int recordCount = personServ.findAllYeBanCount();
            int maxPage = recordCount % yeBan.getPageSize() == 0 ? recordCount / yeBan.getPageSize() : recordCount / yeBan.getPageSize() + 1;
            yeBan.setMaxPage(maxPage);
            yeBan.setRecordCount(recordCount);
            view.addObject("yeBanList", yeBanList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("yeBan", yeBan);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/deleteQianKaDateToMysql")
    public ModelAndView deleteQianKaDateToMysql(QianKa qianKa) throws Exception {
        try {
            personServ.deleteQianKaDateToMysql(qianKa.getId());
            ModelAndView view = new ModelAndView("qianka");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            List<Dept> deptList = personServ.findAllDeptAll();
            List<QianKa> qianKaList = personServ.findAllQianKa(qianKa);
            int recordCount = personServ.findAllQianKaCount();
            int maxPage = recordCount % qianKa.getPageSize() == 0 ? recordCount / qianKa.getPageSize() : recordCount / qianKa.getPageSize() + 1;
            qianKa.setMaxPage(maxPage);
            qianKa.setRecordCount(recordCount);
            view.addObject("qianKaList", qianKaList);
            view.addObject("empList1", empList1);
            view.addObject("empList", empList);
            view.addObject("qianKa", qianKa);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/deleteOutDateToMysql")
    public ModelAndView deleteOutDateToMysql(Out out) throws Exception {
        try {
            personServ.deleteOutDateToMysql(out.getId());
            ModelAndView view = new ModelAndView("out");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Out> outList = personServ.findAllOut(out);
            int recordCount = personServ.findAllLeaveCount();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            int maxPage = recordCount % out.getPageSize() == 0 ? recordCount / out.getPageSize() : recordCount / out.getPageSize() + 1;
            out.setMaxPage(maxPage);
            out.setRecordCount(recordCount);
            view.addObject("outList", outList);
            view.addObject("empList", empList);
            view.addObject("leave", out);
            view.addObject("positionList", positionList);
            view.addObject("empList1", empList1);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/tooutdan")
    public ModelAndView tooutdan() throws Exception {
        try {
            ModelAndView view = new ModelAndView("out");
            Out out = new Out();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Out> outList = personServ.findAllOut(out);
            int recordCount = personServ.findAllLeaveCount();
            JSONArray empList1 = JSONArray.fromObject(empList.toArray());
            int maxPage = recordCount % out.getPageSize() == 0 ? recordCount / out.getPageSize() : recordCount / out.getPageSize() + 1;
            out.setMaxPage(maxPage);
            out.setRecordCount(recordCount);
            view.addObject("outList", outList);
            view.addObject("empList", empList);
            view.addObject("leave", out);
            view.addObject("positionList", positionList);
            view.addObject("empList1", empList1);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toleavepage")
    public ModelAndView toleavepage() throws Exception {
        try {
            ModelAndView view = new ModelAndView("leave");
            Leave leave = new Leave();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Leave> leaveList = personServ.findAllLeave(leave);
            int recordCount = personServ.findAllLeaveCount();
            int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
            leave.setMaxPage(maxPage);
            leave.setRecordCount(recordCount);
            view.addObject("leaveList", leaveList);
            view.addObject("empList", empList);
            view.addObject("leave", leave);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toaddleavepage")
    public ModelAndView toaddleavepage() throws Exception {
        try {
            ModelAndView view = new ModelAndView("addleavepage");
            List<Employee> employeeList = personServ.findAllEmployees();
            view.addObject("leave", new Leave());
            view.addObject("employeeList", employeeList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toaddworksetpage")
    public ModelAndView toaddworksetpage() throws Exception {
        ModelAndView view = new ModelAndView("addworksetpage");
        view.addObject("workSet", new WorkSet());
        return view;
    }

    @ResponseBody
    @RequestMapping("/toUpdateLeaveById")
    public ModelAndView toUpdateLeaveById(Integer id) throws Exception {
        try {
            ModelAndView view = new ModelAndView("updateleavepage");
            List<Employee> employeeList = personServ.findAllEmployees();
            Leave leave = personServ.getLeaveById(id);
            view.addObject("leave", leave);
            view.addObject("employeeList", employeeList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/updateLeaveToMysql")
    public ModelAndView updateLeaveToMysql(Leave leavea) throws Exception {
        try {
            personServ.updateLeaveToMysql(leavea);
            ModelAndView view = new ModelAndView("leave");
            Leave leave = new Leave();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Leave> leaveList = personServ.findAllLeave(leave);
            int recordCount = personServ.findAllLeaveCount();
            int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
            leave.setMaxPage(maxPage);
            leave.setRecordCount(recordCount);
            view.addObject("leaveList", leaveList);
            view.addObject("leave", leave);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteLeaveByBatch")
    public ModelAndView deleteLeaveByBatch(Leave leave) throws Exception {
        try {
            personServ.deleteLeaveByBatch(leave.getIds());
            ModelAndView view = new ModelAndView("leave");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Leave> leaveList = personServ.findAllLeave(leave);
            int recordCount = personServ.findAllLeaveCount();
            int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
            leave.setMaxPage(maxPage);
            leave.setRecordCount(recordCount);
            view.addObject("leaveList", leaveList);
            view.addObject("leave", leave);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteLeaveById")
    public ModelAndView deleteLeaveById(Integer id) throws Exception {
        try {
            personServ.deleteLeaveById(id);
            ModelAndView view = new ModelAndView("leave");
            Leave leave = new Leave();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Leave> leaveList = personServ.findAllLeave(leave);
            int recordCount = personServ.findAllLeaveCount();
            int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
            leave.setMaxPage(maxPage);
            leave.setRecordCount(recordCount);
            view.addObject("leaveList", leaveList);
            view.addObject("leave", leave);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toaddpersonpage")
    public ModelAndView toaddpersonpage(HttpSession session) throws Exception {
        try {
            ModelAndView mav = new ModelAndView("register");
            List<Employee> employeeList = personServ.findAllEmployees();
            mav.addObject("employeeList", employeeList);
            UserInfo info = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("addpersonpage");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("employee", new Employee());
            view.addObject("userInfo", info);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/topositanddeptpage")
    public ModelAndView topositanddeptpage(Position positiona, Integer flagb) throws Exception {
        try {
            ModelAndView view = new ModelAndView("positionpage");
            Position position = new Position();
            List<Position> positionList = personServ.findAllPosition(positiona);
            int recordCount = personServ.findAllPositionConditionCount();
            int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
            position.setMaxPage(maxPage);
            position.setRecordCount(recordCount);
            view.addObject("position", position);
            view.addObject("positionList", positionList);
            view.addObject("flag", 0);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/topositanddeptpage2")
    public ModelAndView topositanddeptpage2(Dept dept) throws Exception {
        try {
            ModelAndView view = new ModelAndView("deptpage");
            List<Dept> deptList = personServ.findAllDept(dept);
            int recordCount = personServ.queryDeptCountByNameA(dept);
            int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
            dept.setMaxPage(maxPage);
            dept.setRecordCount(recordCount);
            view.addObject("deptList", deptList);
            view.addObject("dept", dept);
            view.addObject("flag", 0);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/computeWorkEmpHours", method = RequestMethod.POST)
    public ModelAndView computeWorkEmpHours(@RequestParam("file") MultipartFile file, HttpServletResponse
            resp, HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("downloadstatus")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    resp.addCookie(cookie);
                    break;
                }
            }
        }
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            Compute compute = new Compute();
            List<ClockInOrgin> clockInOrginList = personServ.translateTabletoBean(file);
            List<OutPutWorkData> outPutWorkDataList = personServ.computeTableListData(clockInOrginList);
            List<Employee> employeeList = personServ.findAllEmployeeAll();
            List<SubEmphours> subEmphoursList = MathUtil.computeSubEmpHours(outPutWorkDataList, employeeList);
            String outpathname = "计算完成，请下载查看!";
            if (outPutWorkDataList.get(0).getErrorMessage() == null || outPutWorkDataList.get(0).getErrorMessage().trim().length() <= 0) {
                resp.setHeader("content-type", "application/octet-stream");
                resp.setContentType("application/octet-stream");
                List<String> pathName = ExcelUtil.writeExcelSubWorkHours(subEmphoursList, outPutWorkDataList.get(0).getYearMonth(), finalDirPath);
                resp.setHeader("Content-Disposition", "attachment;filename=" + new String(pathName.get(0).getBytes(), "iso-8859-1"));
                byte[] buff = new byte[1024];
                BufferedInputStream bufferedInputStream = null;
                OutputStream outputStream = null;
                Cookie cookie = new Cookie("downloadstatus", String.valueOf(new Date().getTime()));
                cookie.setMaxAge(5 * 60);
                cookie.setPath("/");
                resp.addCookie(cookie);
                try {
                    outputStream = resp.getOutputStream();
                    File fi = new File(pathName.get(1));
                    FileInputStream fis = new FileInputStream(fi);
                    bufferedInputStream = new BufferedInputStream(fis);
                    int num = bufferedInputStream.read(buff);
                    while (num != -1) {
                        outputStream.write(buff, 0, num);
                        outputStream.flush();
                        num = bufferedInputStream.read(buff);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                } finally {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                        outputStream.close();
                    }
                }
            }
            view.addObject("flag2", outpathname);
            view.addObject("errorMessage", outPutWorkDataList.get(0).getErrorMessage());
            view.addObject("compute", compute);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/computeWorkTable", method = RequestMethod.POST)
    public ModelAndView computeWorkTable(@RequestParam("file") MultipartFile file, HttpServletResponse
            resp, HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("downloadstatus")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    resp.addCookie(cookie);
                    break;
                }
            }
        }
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            Compute compute = new Compute();
            List<ClockInOrgin> clockInOrginList = personServ.translateTabletoBean(file);
            List<OutPutWorkData> outPutWorkDataList = personServ.computeTableListData(clockInOrginList);
            String outpathname = "计算完成，请下载查看!";
            if (outPutWorkDataList.get(0).getErrorMessage() == null || outPutWorkDataList.get(0).getErrorMessage().trim().length() <= 0) {
                resp.setHeader("content-type", "application/octet-stream");
                resp.setContentType("application/octet-stream");
                List<String> pathName = ExcelUtil.writeExcel(outPutWorkDataList, finalDirPath);
                resp.setHeader("Content-Disposition", "attachment;filename=" + new String(pathName.get(0).getBytes(), "iso-8859-1"));
                byte[] buff = new byte[1024];
                BufferedInputStream bufferedInputStream = null;
                OutputStream outputStream = null;
                Cookie cookie = new Cookie("downloadstatus", String.valueOf(new Date().getTime()));
                cookie.setMaxAge(5 * 60);// 设置为30min
                cookie.setPath("/");
                resp.addCookie(cookie);
                try {
                    outputStream = resp.getOutputStream();
                    File fi = new File(pathName.get(1));
                    FileInputStream fis = new FileInputStream(fi);
                    bufferedInputStream = new BufferedInputStream(fis);
                    int num = bufferedInputStream.read(buff);
                    while (num != -1) {
                        outputStream.write(buff, 0, num);
                        outputStream.flush();
                        num = bufferedInputStream.read(buff);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                } finally {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                        outputStream.close();
                    }
                }
            }
            view.addObject("flag2", outpathname);
            view.addObject("errorMessage", outPutWorkDataList.get(0).getErrorMessage());
            view.addObject("compute", compute);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/outDataInSql", method = RequestMethod.POST)
    public ModelAndView outDataInSql(@RequestParam("file8") MultipartFile file) throws Exception {
        ModelAndView view = new ModelAndView("computeworkdate");
        try {
            personServ.translateAllTable(file);
            view.addObject("compute", new Compute());
            view.addObject("flag88", 10);
            return view;
        } catch (Exception e) {
            view.addObject("compute", new Compute());
            view.addObject("flag88", 10);
            view.addObject("flag88", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return view;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/dataInMysql", method = RequestMethod.POST)
    public ModelAndView dataInMysql(@RequestParam("file1") MultipartFile file1, HttpServletResponse response) throws
            Exception {
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            List<Employee> employeeList = personServ.translateTabletoEmployeeBean(file1);
            String isRepeatData = personServ.checkEmpNoOrEmpNameRepeat(employeeList);
            if (isRepeatData.trim().length() > 0) {
                view.addObject("flag1", isRepeatData);
                return view;
            } else {
                personServ.saveDeptNameAndPositionNameAndEms(employeeList);
            }

            view.addObject("compute", new Compute());
            view.addObject("flag1", 10);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/dataInMysqlOut", method = RequestMethod.POST)
    public ModelAndView dataInMysqlOut(@RequestParam("file5") MultipartFile file1, HttpServletResponse response) throws
            Exception {
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            List<Out> outList = personServ.translateTabletoOutBean(file1);
            String isRepeatData = personServ.checkOutRepeat(outList);
            if (isRepeatData != null && isRepeatData.trim().length() > 0) {
                view.addObject("flag5", isRepeatData);
                return view;
            } else {
                personServ.saveOutList(outList);
            }

            view.addObject("compute", new Compute());
            view.addObject("flag5", 10);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/dataInMysqlAccuFund", method = RequestMethod.POST)
    public ModelAndView dataInMysqlAccuFund(@RequestParam("file7") MultipartFile file1, HttpServletResponse
            response) throws
            Exception {
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            List<AccuFund> outList = personServ.translateTabletoAccuFundBean(file1);
            personServ.saveAccuFundList(outList);
            view.addObject("compute", new Compute());
            view.addObject("flag7", 12);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/dataInMysqlsurance", method = RequestMethod.POST)
    public ModelAndView dataInMysqlsurance(@RequestParam("file6") MultipartFile file1, HttpServletResponse response) throws
            Exception {
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            List<Insurance> outList = personServ.translateTabletoInsuranceBean(file1);
            personServ.saveInsuranceList(outList);
            view.addObject("compute", new Compute());
            view.addObject("flag6", 11);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/dataInMysqlZK", method = RequestMethod.POST)
    public ModelAndView dataInMysqlZK(@RequestParam("file3") List<MultipartFile> file1, HttpServletResponse
            response) throws Exception {
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            List<Employee> employeeList = personServ.translateTabletoEmployeeBeanZK(file1);
            personServ.saveZKNumEmpNoBangDing(employeeList);
            view.addObject("compute", new Compute());
            view.addObject("flag3", 11);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDeptNameByEmployId", method = RequestMethod.POST)
    public void getDeptNameByEmployId(QianKa qianKa, HttpSession session, HttpServletResponse response) throws
            Exception {
        try {
            String deptName = personServ.getDeptNameByEmployId(qianKa.getId());
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(deptName);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/getTotalHoursByDateStr", method = RequestMethod.POST)
    public void getTotalHoursByDateStr(TiaoXiu tiaoXiu, HttpSession session, HttpServletResponse response) throws
            Exception {
        try {
            Double totalHours = personServ.getTotalHoursByDateStr(tiaoXiu);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(totalHours);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkAndSave", method = RequestMethod.POST)
    public void checkAndSave(Position position, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            int isSave = personServ.checkAndSavePosition(position);
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getWorkDatesAndPositionsByData", method = RequestMethod.POST)
    public void getWorkDatesAndPositionsByData(WorkDate workDate, HttpServletResponse response) throws Exception {
        try {
            WorkSet workSet = personServ.getWorkSetByMonthAndPositionLevel(workDate);
            String workDatesAndPositionNames;
            if (workSet == null) {
                WorkDate workDate1 = personServ.getWorkDateByMonth2(workDate);
                String positionStr = personServ.getPositionNamesByPositionLevel(workDate.getPositionLevel());
                if (workDate1 != null && positionStr != null) {
                    workDatesAndPositionNames = workDate1.getWorkDate() + "$" + positionStr;
                } else {
                    if (workDate1 != null) {
                        workDatesAndPositionNames = "您所有的职位信息没有" + workDate.getPositionLevel() + "职位类别的数据，请前往职位模块设置;";
                    } else if (positionStr != null) {
                        workDatesAndPositionNames = "您的排单表中没有" + workDate.getMonth() + "月份" + workDate.getPositionLevel() + "职位类别的数据，请前往排单设置新增;";
                    } else {
                        workDatesAndPositionNames = "您的排单表中没有" + workDate.getMonth() + "月份和" + workDate.getPositionLevel() + "职位类别的数据，请前往排单设置新增;";
                        workDatesAndPositionNames += "您所有的职位信息没有" + workDate.getPositionLevel() + "职位类别的数据，请前往职位模块设置;";

                    }
                }
            } else {
                workDatesAndPositionNames = "系统中已存在" + workDate.getMonth() + "月份和" + workDate.getPositionLevel() + "职位类别的数据，不可重复增加;";

            }
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(workDatesAndPositionNames);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/getWorkDatesAndPositionsByData2", method = RequestMethod.POST)
    public void getWorkDatesAndPositionsByData2(WorkDate workDate, HttpServletResponse response) throws Exception {
        try {
            WorkSet workSet = personServ.getWorkSetByMonthAndPositionLevel(workDate);
            String workDatesAndPositionNames = "";
            if (workSet != null) {
                WorkDate workDate1 = personServ.getWorkDateByMonth2(workDate);
                String positionStr = personServ.getPositionNamesByPositionLevel(workDate.getPositionLevel());
                if (workDate1 != null && positionStr != null) {
                    workDatesAndPositionNames = workDate1.getWorkDate() + "$" + positionStr;
                } else {
                    if (workDate1 != null) {
                        workDatesAndPositionNames = "您所有的职位信息没有" + workDate.getPositionLevel() + "职位类别的数据，请前往职位模块设置;";
                    } else if (positionStr != null) {
                        workDatesAndPositionNames = "您的排单表中没有" + workDate.getMonth() + "月份" + workDate.getPositionLevel() + "职位类别的数据，请前往排单设置新增;";
                    } else {
                        workDatesAndPositionNames = "您的排单表中没有" + workDate.getMonth() + "月份和" + workDate.getPositionLevel() + "职位类别的数据，请前往排单设置新增;";
                        workDatesAndPositionNames += "您所有的职位信息没有" + workDate.getPositionLevel() + "职位类别的数据，请前往职位模块设置;";

                    }
                }
            }
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(workDatesAndPositionNames);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateWorkDate", method = RequestMethod.POST)
    public void saveOrUpdateWorkDate(WorkDate workDate, HttpServletResponse response) throws Exception {
        try {
            String str = "";
            for (int i = 0; i < workDate.getWorkDates().length - 1; i++) {
                str += workDate.getWorkDates()[i].trim() + ",";
            }
            str += workDate.getWorkDates()[workDate.getWorkDates().length - 1].trim();
            workDate.setWorkDate(str);
            personServ.saveOrUpdateWorkData(workDate);
            workDate = personServ.getWorkDateByMonth(workDate);
            List<WorkDate> workDateList = new ArrayList<WorkDate>();
            workDateList.add(workDate);
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(workDateList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getWorkDateByMonth", method = RequestMethod.POST)
    public void getWorkDateByMonth(WorkDate workDate, HttpServletResponse response) throws Exception {
        try {
            List<WorkDate> workDateList = new ArrayList<WorkDate>();
            workDate = personServ.getWorkDateByMonth(workDate);
            List<SmallEmployee> empList = null;
            if (workDate != null && workDate.getPositionLevel() != null) {
                empList = personServ.findAllEmployeeByPositionLevel(workDate.getPositionLevel());
            }
            if (workDate != null) {
                if (empList != null) {
                    workDate.setEmpList(empList);
                }
                workDateList.add(workDate);
            } else {
                WorkDate w = new WorkDate();
                w.setWorkDate("");
                if (empList != null) {
                    w.setEmpList(empList);
                }
                workDateList.add(w);
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(workDateList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getemployeebyId", method = RequestMethod.POST)
    public void getemployeebyId(@RequestBody String employeeId, HttpServletResponse response) throws Exception {
        try {
            List<Employee> employees = personServ.getEmployeeById(employeeId);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employees);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addWorkSet")
    public ModelAndView addWorkSet(WorkSet workSet) throws Exception {
        try {
            ModelAndView view = new ModelAndView("worksetpage");
            workSet.setUpdateDate(new Date());
            workSet.setMorningOnStr(workSet.getMorningOnStr().split(":")[0] + ":" + workSet.getMorningOnStr().split(":")[1] + ":59");
            workSet.setMorningOnEndStr(workSet.getMorningOnEndStr().split(":")[0] + ":" + workSet.getMorningOnEndStr().split(":")[1] + ":59");
            workSet.setMorningOffStr(workSet.getMorningOffStr().split(":")[0] + ":" + workSet.getMorningOffStr().split(":")[1] + ":59");
            workSet.setMorningOffEndStr(workSet.getMorningOffEndStr().split(":")[0] + ":" + workSet.getMorningOffEndStr().split(":")[1] + ":59");
            workSet.setNoonOnStr(workSet.getNoonOnStr().split(":")[0] + ":" + workSet.getNoonOnStr().split(":")[1] + ":59");
            workSet.setNoonOnEndStr(workSet.getNoonOnEndStr().split(":")[0] + ":" + workSet.getNoonOnEndStr().split(":")[1] + ":59");
            workSet.setNoonOffStr(workSet.getNoonOffStr().split(":")[0] + ":" + workSet.getNoonOffStr().split(":")[1] + ":59");
            workSet.setNoonOffEndStr(workSet.getNoonOffEndStr().split(":")[0] + ":" + workSet.getNoonOffEndStr().split(":")[1] + ":59");
            workSet.setExtworkonStr(workSet.getExtworkonStr().split(":")[0] + ":" + workSet.getExtworkonStr().split(":")[1] + ":59");
            workSet.setExtworkonEndStr(workSet.getExtworkonEndStr().split(":")[0] + ":" + workSet.getExtworkonEndStr().split(":")[1] + ":59");
            personServ.addWorkSetData(workSet);
            List<WorkSet> workSetList = personServ.findAllWorkSet(workSet);
            int recordCount = personServ.findAllWorkSetCount(workSet);
            int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
            workSet.setMaxPage(maxPage);
            workSet.setRecordCount(recordCount);
            view.addObject("workSetList", workSetList);
            view.addObject("flag", 1);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/updateWorkSet")
    public ModelAndView updateWorkSet(WorkSet workSet) throws Exception {
        ModelAndView view = new ModelAndView("worksetpage");
        try {
            workSet.setUpdateDate(new Date());
            workSet.setMorningOnStr(workSet.getMorningOnStr().split(":")[0] + ":" + workSet.getMorningOnStr().split(":")[1] + ":59");
            workSet.setMorningOnEndStr(workSet.getMorningOnEndStr().split(":")[0] + ":" + workSet.getMorningOnEndStr().split(":")[1] + ":59");
            workSet.setMorningOffStr(workSet.getMorningOffStr().split(":")[0] + ":" + workSet.getMorningOffStr().split(":")[1] + ":59");
            workSet.setMorningOffEndStr(workSet.getMorningOffEndStr().split(":")[0] + ":" + workSet.getMorningOffEndStr().split(":")[1] + ":59");
            workSet.setNoonOnStr(workSet.getNoonOnStr().split(":")[0] + ":" + workSet.getNoonOnStr().split(":")[1] + ":59");
            workSet.setNoonOnEndStr(workSet.getNoonOnEndStr().split(":")[0] + ":" + workSet.getNoonOnEndStr().split(":")[1] + ":59");
            workSet.setNoonOffStr(workSet.getNoonOffStr().split(":")[0] + ":" + workSet.getNoonOffStr().split(":")[1] + ":59");
            workSet.setNoonOffEndStr(workSet.getNoonOffEndStr().split(":")[0] + ":" + workSet.getNoonOffEndStr().split(":")[1] + ":59");
            workSet.setExtworkonStr(workSet.getExtworkonStr().split(":")[0] + ":" + workSet.getExtworkonStr().split(":")[1] + ":59");
            workSet.setExtworkonEndStr(workSet.getExtworkonEndStr().split(":")[0] + ":" + workSet.getExtworkonEndStr().split(":")[1] + ":59");
            personServ.updateWorkSetDataById(workSet);
            List<WorkSet> workSetList = personServ.findAllWorkSet(workSet);
            int recordCount = personServ.findAllWorkSetCount(workSet);
            int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
            workSet.setMaxPage(maxPage);
            workSet.setRecordCount(recordCount);
            view.addObject("workSetList", workSetList);
            view.addObject("flag", 3);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteWorkSetById", method = RequestMethod.GET)
    public ModelAndView deleteWorkSetById(WorkSet workSet) throws Exception {
        try {
            ModelAndView view = new ModelAndView("worksetpage");
            personServ.deleteWorkSetById(workSet);
            List<WorkSet> workSetList = personServ.findAllWorkSet(workSet);
            int recordCount = personServ.findAllWorkSetCount(workSet);
            int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
            workSet.setMaxPage(maxPage);
            workSet.setRecordCount(recordCount);
            view.addObject("workSetList", workSetList);
            view.addObject("flag", 2);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/addLeaveToMysql", method = RequestMethod.GET)
    public ModelAndView addLeaveToMysql(Leave leavea) throws Exception {
        try {
            ModelAndView view = new ModelAndView("leave");
            personServ.addLeaveData(leavea);
            Leave leave = new Leave();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Leave> leaveList = personServ.findAllLeave(leave);
            int recordCount = personServ.findAllLeaveCount();
            int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
            leave.setMaxPage(maxPage);
            leave.setRecordCount(recordCount);
            view.addObject("leaveList", leaveList);
            view.addObject("leave", leave);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/checkAndSave2", method = RequestMethod.POST)
    public void checkAndSave2(@RequestBody String deptname, HttpSession session, HttpServletResponse response) throws
            Exception {
        try {
            int isSave = personServ.checkAndSaveDept(deptname);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkEmployNoIsExsit", method = RequestMethod.POST)
    public void checkEmployNoIsExsit(@RequestBody String empoyeeNo, HttpServletResponse response) throws Exception {
        try {
            int isSave = personServ.checkEmployNoIsExsit(empoyeeNo);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkEmployIsExsit", method = RequestMethod.POST)
    public void checkEmployIsExsit(@RequestBody String name, HttpServletResponse response) throws Exception {
        try {
            int isSave = personServ.checkEmployIsExsit(name);
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deletePositionByBatch", method = RequestMethod.GET)
    public ModelAndView deletePositionByBatch(Position position) throws Exception {
        try {
            ModelAndView view = new ModelAndView("positionpage");
            personServ.deletePositionByIdBatch(position.getIds());
            List<Position> positionList = new ArrayList<Position>();
            positionList = personServ.findAllPosition(position);
            int recordCount = personServ.queryPositionCountByNameA(position);
            int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
            position.setMaxPage(maxPage);
            position.setRecordCount(recordCount);
            view.addObject("positionList", positionList);
            view.addObject("position", position);
            view.addObject("flag", 4);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/deletePosition", method = RequestMethod.GET)
    public ModelAndView deletePosition(Integer id, Position position) throws Exception {
        try {
            ModelAndView view = new ModelAndView("positionpage");
            personServ.deletePositionById(id);
            List<Position> positionList = new ArrayList<Position>();
            positionList = personServ.findAllPosition(position);
            int recordCount = personServ.queryPositionCountByNameA(position);
            int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
            position.setMaxPage(maxPage);
            position.setRecordCount(recordCount);
            view.addObject("positionList", positionList);
            view.addObject("position", position);
            view.addObject("flag", 4);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDept", method = RequestMethod.GET)
    public ModelAndView deleteDept(Integer id, Dept dept) throws Exception {
        try {
            ModelAndView view = new ModelAndView("deptpage");
            personServ.deleteDeptById(id);
            List<Dept> deptList = personServ.findAllDept(dept);
            int recordCount = personServ.queryDeptCountByNameA(dept);
            int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
            dept.setMaxPage(maxPage);
            dept.setRecordCount(recordCount);
            view.addObject("deptList", deptList);
            view.addObject("dept", dept);
            view.addObject("flag", 4);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDeptByBatch", method = RequestMethod.GET)
    public ModelAndView deleteDeptByBatch(Dept dept) throws Exception {
        try {
            ModelAndView view = new ModelAndView("deptpage");
            personServ.deleteDeptByBatch(dept.getIds());
            List<Dept> deptList = personServ.findAllDept(dept);
            int recordCount = personServ.queryDeptCountByNameA(dept);
            int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
            dept.setMaxPage(maxPage);
            dept.setRecordCount(recordCount);
            view.addObject("deptList", deptList);
            view.addObject("dept", dept);
            view.addObject("flag", 4);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryPositionByName", method = RequestMethod.GET)
    public ModelAndView queryPositionByName(String positionName, String positionLevel, Integer currentpage) throws
            Exception {
        try {
            ModelAndView view = new ModelAndView("positionpage");
            Position position = new Position();
            if (currentpage == null)
                currentpage = 1;
            position.setCurrentPage(currentpage);
            position.setPositionName(positionName);
            if (!"0".equals(positionLevel)) {
                position.setPositionLevel(positionLevel);
            }
            List<Position> positionList = personServ.queryPositionByNameA(position);
            int recordCount = personServ.queryPositionCountByNameA(position);
            int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
            position.setMaxPage(maxPage);
            position.setRecordCount(recordCount);

            view.addObject("positionName", positionName);
            view.addObject("positionList", positionList);
            view.addObject("position", position);
            view.addObject("flag", 3);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping(value = "/queryDeptByName", method = RequestMethod.GET)
    public ModelAndView queryDeptByName(String deptName, Integer currentpage, Integer showFlag) throws Exception {
        try {
            ModelAndView view = new ModelAndView("deptpage");
            Dept dept = new Dept();
            Position position = new Position();
            if (currentpage == null)
                currentpage = 1;
            dept.setCurrentPage(currentpage);
            dept.setDeptname(deptName);
            List<Dept> deptList = personServ.queryDeptByNameA(dept);
            int recordCount = personServ.queryDeptCountByNameA(dept);
            int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
            dept.setMaxPage(maxPage);
            dept.setRecordCount(recordCount);


            view.addObject("deptName", deptName);
            view.addObject("deptList", deptList);
            view.addObject("dept", dept);
            view.addObject("flag", 3);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public void checkPositionName(Position position, HttpServletResponse response) throws Exception {
        try {
            int isExsit = personServ.checkIfExsit(position.getPositionName());
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(isExsit);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/saveUpdateData", method = RequestMethod.GET)
    public ModelAndView saveUpdateData(Integer id, String positionName, String positionLevel) throws Exception {
        try {
            ModelAndView view = new ModelAndView("positionpage");
            Dept dept = new Dept();
            Position position = new Position();
            int isExsit = personServ.checkIfExsit(positionName);
            if (isExsit == 0) {
                personServ.saveUpdateData(id, positionName, positionLevel);
                view.addObject("flag", 1);
            } else {
                view.addObject("flag", 5);
            }
            List<Position> positionList = personServ.findAllPosition(position);
            int recordCount = personServ.findAllPositionConditionCount();
            int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
            position.setMaxPage(maxPage);
            position.setRecordCount(recordCount);
            view.addObject("position", position);
            view.addObject("positionList", positionList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkDept", method = RequestMethod.POST)
    public void checkDept(Dept dept, HttpServletResponse response) throws Exception {
        try {
            int isExsit = personServ.checkIfExsit2(dept.getDeptname());
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(isExsit);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveUpdateData2", method = RequestMethod.GET)
    public ModelAndView saveUpdateData2(Integer id, String deptname) throws Exception {
        try {
            ModelAndView view = new ModelAndView("deptpage");
            Position position = new Position();
            Dept dept = new Dept();
            int isExsit = personServ.checkIfExsit2(deptname);
            if (isExsit == 0) {
                personServ.saveUpdateData2(id, deptname);
                view.addObject("flag", 1);
            } else {
                view.addObject("flag", 5);
            }
            List<Dept> deptList = personServ.findAllDept(dept);
            int recordCount = personServ.findAllDeptConditionCount(dept);
            int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
            dept.setMaxPage(maxPage);
            dept.setRecordCount(recordCount);
            view.addObject("dept", dept);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/showImageByClockInId")
    public void showImageByClockInId(Integer id, Integer type, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
        OutClockIn ee = personServ.getOutClockInById(id);
        String pathName = "";
        if (type == 1) {
            pathName = ee.getAmOnUrl();
        } else if (type == 2) {
            pathName = ee.getPmOnUrl();
        } else if (type == 3) {
            pathName = ee.getNmOnUrl();
        }


        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s", jedis.get(Constants.accessToken), pathName);

        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(Url);
        HttpResponse response1 = client.execute(get);
        InputStream fis = response1.getEntity().getContent();
        OutputStream os = response.getOutputStream();
        try {
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1)
                os.write(buffer, 0, count);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                os.close();
            if (fis != null)
                fis.close();
        }
    }


    @ResponseBody
    @RequestMapping(value = "/showImage")
    public void showImage(Integer id, Integer type, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
        List<Employee> ee = personServ.getEmployeeById(id);
        String pathName = "";
        if (type == 1) {
            pathName = ee.get(0).getEducationLeUrl();
        } else if (type == 2) {
            pathName = ee.get(0).getSateListAndLeaCertiUrl();
        } else if (type == 3) {
            pathName = ee.get(0).getOtherCertiUrl();
        }
        FileInputStream fis = new FileInputStream(pathName);
        OutputStream os = response.getOutputStream();
        try {
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1)
                os.write(buffer, 0, count);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                os.close();
            if (fis != null)
                fis.close();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ModelAndView addEmployee(@RequestParam("educationLeFile") MultipartFile educationLeFile,
                                    @RequestParam("sateListAndLeaCertiFile") MultipartFile sateListAndLeaCertiFile,
                                    @RequestParam("otherCertiFile") MultipartFile otherCertiFile,
                                    Employee employee1, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("person");
            personServ.addEmployeeData(educationLeFile, sateListAndLeaCertiFile, otherCertiFile, employee1);
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> employeeList = personServ.findAllEmployee(employee);
            int recordCount = personServ.findAllEmployeeCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 1);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toupdateEmployeeById", method = RequestMethod.GET)
    public ModelAndView toupdateEmployeeById(Employee employee) throws Exception {
        try {
            ModelAndView view = new ModelAndView("updatepersonpage");
            List<Employee> employees = personServ.getEmployeeById(employee.getId());
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("employee", employees.get(0));
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping(value = "/toupdateWorkSetById", method = RequestMethod.GET)
    public ModelAndView toupdateWorkSetById(WorkSet workSet) throws Exception {
        try {
            ModelAndView view = new ModelAndView("updateworksetpage");
            workSet = personServ.getWorkSetById(workSet.getId());
            WorkDate a = new WorkDate();
            a.setMonth(workSet.getMonth());
            a.setPositionLevel(workSet.getWorkLevel());
            a = personServ.getWorkDateByMonth2(a);
            workSet.setWorkDate(a.getWorkDate());
            workSet.setWorkLevelStr(personServ.getPositionNamesByPositionLevel(workSet.getWorkLevel()));
            view.addObject("workSet", workSet);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/updatePersonToMysql", method = RequestMethod.POST)
    public ModelAndView updatePersonToMysql(@RequestParam("educationLeFile") MultipartFile educationLeFile,
                                            @RequestParam("sateListAndLeaCertiFile") MultipartFile sateListAndLeaCertiFile,
                                            @RequestParam("otherCertiFile") MultipartFile otherCertiFile,
                                            Employee employee1, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("person");
            personServ.updateEmployeeData(educationLeFile, sateListAndLeaCertiFile, otherCertiFile, employee1);
            List<Employee> empList = personServ.findAllEmployees();
            Employee employee = new Employee();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> employeeList = personServ.findAllEmployee(employee);
            int recordCount = personServ.findAllEmployeeCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 3);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/queryGongZhongHaoByCondition", method = RequestMethod.POST)
    public void queryGongZhongHaoByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<Employee> employeeList = personServ.queryGongZhongHaoByCondition(employee);
            int recordCount = personServ.queryGongZhongHaoByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (employeeList.size() > 0) {
                employeeList.get(0).setMaxPage(maxPage);
                employeeList.get(0).setRecordCount(recordCount);
                employeeList.get(0).setCurrentPage(employee.getCurrentPage());
                employeeList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employeeList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryZhongKongByCondition", method = RequestMethod.POST)
    public void queryZhongKongByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<Employee> employeeList = personServ.queryZhongKongByCondition(employee);
            int recordCount = personServ.queryZhongKongByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (employeeList.size() > 0) {
                employeeList.get(0).setMaxPage(maxPage);
                employeeList.get(0).setRecordCount(recordCount);
                employeeList.get(0).setCurrentPage(employee.getCurrentPage());
                employeeList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employeeList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryOutClockInByCondition", method = RequestMethod.POST)
    public void queryOutClockInByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<Employee> employeeList = personServ.queryOutClockInByCondition(employee);
            int recordCount = personServ.queryOutClockInByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (employeeList.size() > 0) {
                employeeList.get(0).setMaxPage(maxPage);
                employeeList.get(0).setRecordCount(recordCount);
                employeeList.get(0).setCurrentPage(employee.getCurrentPage());
                employeeList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employeeList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryEmployeeByCondition", method = RequestMethod.POST)
    public void queryEmployeeByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<Employee> employeeList = personServ.queryEmployeeByCondition(employee);
            int recordCount = personServ.queryEmployeeByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (employeeList.size() > 0) {
                employeeList.get(0).setMaxPage(maxPage);
                employeeList.get(0).setRecordCount(recordCount);
                employeeList.get(0).setCurrentPage(employee.getCurrentPage());
                employeeList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employeeList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryLinShiByCondition", method = RequestMethod.POST)
    public void queryLinShiByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<Employee> employeeList = personServ.queryLinShiByCondition(employee);
            int recordCount = personServ.queryLinShiByConditionConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (employeeList.size() > 0) {
                employeeList.get(0).setMaxPage(maxPage);
                employeeList.get(0).setRecordCount(recordCount);
                employeeList.get(0).setCurrentPage(employee.getCurrentPage());
                employeeList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(employeeList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryWorkSetByCondition", method = RequestMethod.POST)
    public void queryWorkSetByCondition(WorkSet workSet, HttpServletResponse response) throws Exception {
        try {
            List<WorkSet> workSetList = personServ.queryWorkSetByCondition(workSet);
            int recordCount = personServ.queryWorkSetByConditionCount(workSet);
            int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
            if (workSetList.size() > 0) {
                workSetList.get(0).setMaxPage(maxPage);
                workSetList.get(0).setRecordCount(recordCount);
                workSetList.get(0).setCurrentPage(workSet.getCurrentPage());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(workSetList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkBeginLeaveRight", method = RequestMethod.POST)
    public void checkBeginLeaveRight(Leave leave, HttpServletResponse response) throws Exception {
        try {
            int isright = personServ.checkBeginLeaveRight(leave);
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(isright);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryQKByCondition", method = RequestMethod.POST)
    public void queryQKByCondition(QianKa qianKa, HttpServletResponse response) throws Exception {
        try {
            List<QianKa> leaveList = personServ.queryQKByCondition(qianKa);
            int recordCount = personServ.queryQKByConditionCount(qianKa);
            int maxPage = recordCount % qianKa.getPageSize() == 0 ? recordCount / qianKa.getPageSize() : recordCount / qianKa.getPageSize() + 1;
            if (leaveList.size() > 0) {
                leaveList.get(0).setMaxPage(maxPage);
                leaveList.get(0).setRecordCount(recordCount);
                leaveList.get(0).setCurrentPage(qianKa.getCurrentPage());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(leaveList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryYBByCondition", method = RequestMethod.POST)
    public void queryYBByCondition(YeBan yeBan, HttpServletResponse response) throws Exception {
        try {
            List<YeBan> leaveList = personServ.queryYBByCondition(yeBan);
            int recordCount = personServ.queryYBByConditionCount(yeBan);
            int maxPage = recordCount % yeBan.getPageSize() == 0 ? recordCount / yeBan.getPageSize() : recordCount / yeBan.getPageSize() + 1;
            if (leaveList.size() > 0) {
                leaveList.get(0).setMaxPage(maxPage);
                leaveList.get(0).setRecordCount(recordCount);
                leaveList.get(0).setCurrentPage(yeBan.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(leaveList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryPSByCondition", method = RequestMethod.POST)
    public void queryPSByCondition(PinShiJiaBanBGS pinShiJiaBanBGS, HttpServletResponse response) throws Exception {
        try {
            List<PinShiJiaBanBGS> pinShiJiaBanBGSList = personServ.queryPSByCondition(pinShiJiaBanBGS);
            int recordCount = personServ.queryPSByConditionCount(pinShiJiaBanBGS);
            int maxPage = recordCount % pinShiJiaBanBGS.getPageSize() == 0 ? recordCount / pinShiJiaBanBGS.getPageSize() : recordCount / pinShiJiaBanBGS.getPageSize() + 1;
            if (pinShiJiaBanBGSList.size() > 0) {
                pinShiJiaBanBGSList.get(0).setMaxPage(maxPage);
                pinShiJiaBanBGSList.get(0).setRecordCount(recordCount);
                pinShiJiaBanBGSList.get(0).setCurrentPage(pinShiJiaBanBGS.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(pinShiJiaBanBGSList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryLBByCondition", method = RequestMethod.POST)
    public void queryLBByCondition(LianBan lianBan, HttpServletResponse response) throws Exception {
        try {
            List<LianBan> leaveList = personServ.queryLBByCondition(lianBan);
            int recordCount = personServ.queryLBByConditionCount(lianBan);
            int maxPage = recordCount % lianBan.getPageSize() == 0 ? recordCount / lianBan.getPageSize() : recordCount / lianBan.getPageSize() + 1;
            if (leaveList.size() > 0) {
                leaveList.get(0).setMaxPage(maxPage);
                leaveList.get(0).setRecordCount(recordCount);
                leaveList.get(0).setCurrentPage(lianBan.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(leaveList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryLSHByCondition", method = RequestMethod.POST)
    public void queryLSHByCondition(LinShiHours linShiHours, HttpServletResponse response) throws Exception {
        try {
            List<LinShiHours> leaveList = personServ.queryLSHByCondition(linShiHours);
            int recordCount = personServ.queryLSHByConditionCount(linShiHours);
            int maxPage = recordCount % linShiHours.getPageSize() == 0 ? recordCount / linShiHours.getPageSize() : recordCount / linShiHours.getPageSize() + 1;
            if (leaveList.size() > 0) {
                leaveList.get(0).setMaxPage(maxPage);
                leaveList.get(0).setRecordCount(recordCount);
                leaveList.get(0).setCurrentPage(linShiHours.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(leaveList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryTXByCondition", method = RequestMethod.POST)
    public void queryTXByCondition(TiaoXiu jiaBan, HttpServletResponse response) throws Exception {
        try {
            List<TiaoXiu> leaveList = personServ.queryTXByCondition(jiaBan);
            int recordCount = personServ.queryTXByConditionCount(jiaBan);
            int maxPage = recordCount % jiaBan.getPageSize() == 0 ? recordCount / jiaBan.getPageSize() : recordCount / jiaBan.getPageSize() + 1;
            if (leaveList.size() > 0) {
                leaveList.get(0).setMaxPage(maxPage);
                leaveList.get(0).setRecordCount(recordCount);
                leaveList.get(0).setCurrentPage(jiaBan.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(leaveList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryJBByCondition", method = RequestMethod.POST)
    public void queryJBByCondition(JiaBan jiaBan, HttpServletResponse response) throws Exception {
        try {
            List<JiaBan> leaveList = personServ.queryJBByCondition(jiaBan);
            int recordCount = personServ.queryJBByConditionCount(jiaBan);
            int maxPage = recordCount % jiaBan.getPageSize() == 0 ? recordCount / jiaBan.getPageSize() : recordCount / jiaBan.getPageSize() + 1;
            if (leaveList.size() > 0) {
                leaveList.get(0).setMaxPage(maxPage);
                leaveList.get(0).setRecordCount(recordCount);
                leaveList.get(0).setCurrentPage(jiaBan.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(leaveList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryOutByCondition", method = RequestMethod.POST)
    public void queryOutByCondition(Out out, HttpServletResponse response) throws Exception {
        try {
            List<Out> outList = personServ.queryOutByCondition(out);
            int recordCount = personServ.queryOutByConditionCount(out);
            int maxPage = recordCount % out.getPageSize() == 0 ? recordCount / out.getPageSize() : recordCount / out.getPageSize() + 1;
            if (outList.size() > 0) {
                outList.get(0).setMaxPage(maxPage);
                outList.get(0).setRecordCount(recordCount);
                outList.get(0).setCurrentPage(out.getCurrentPage());
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(outList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryLeaveByCondition", method = RequestMethod.POST)
    public void queryLeaveByCondition(Leave leave, HttpServletResponse response) throws Exception {
        try {
            List<Leave> leaveList = personServ.queryLeaveByCondition(leave);
            int recordCount = personServ.queryLeaveByConditionCount(leave);
            int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
            if (leaveList.size() > 0) {
                leaveList.get(0).setMaxPage(maxPage);
                leaveList.get(0).setRecordCount(recordCount);
                leaveList.get(0).setCurrentPage(leave.getCurrentPage());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();

            str1 = x.writeValueAsString(leaveList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmpByBatch", method = RequestMethod.GET)
    public ModelAndView deleteEmpByBatch(Employee employee, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("person");
            personServ.deleteEmpByBatch(employee.getIds());
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> employeeList = personServ.findAllEmployee(employee);
            int recordCount = personServ.findAllEmployeeCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmployeeById", method = RequestMethod.GET)
    public ModelAndView deleteEmployeeById(Integer id, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("person");
            personServ.deleteEmployeetById(id);
            Employee employee = new Employee();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> employeeList = personServ.findAllEmployee(employee);
            int recordCount = personServ.findAllEmployeeCount();
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            employee.setMaxPage(maxPage);
            employee.setRecordCount(recordCount);
            view.addObject("employeeList", employeeList);
            view.addObject("empList", empList);
            view.addObject("employee", employee);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("flag", 2);
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


}
