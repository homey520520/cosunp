package com.cosun.cosunp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.service.IProjectServ;
import com.cosun.cosunp.tool.*;
import com.cosun.cosunp.weixin.NetWorkHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:homey Wong
 * @Date: 2020/3/23 0023 下午 5:30
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

    private static Logger logger = LogManager.getLogger(ProjectController.class);

    @Autowired
    IProjectServ projectServ;

    @Autowired
    IPersonServ personServ;

    private static JedisPool pool;
    private static Jedis jedis;

    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();


    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;


    @ResponseBody
    @RequestMapping("/getTotalProject")
    public void getTotalProject(String userid, Integer useractor, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            List<ProjectHead> totalLianSuo = null;
            if (useractor == 2) {
                totalLianSuo = projectServ.totalProjectNumByEmpNo(userid);
            } else {
                totalLianSuo = projectServ.totalProjectNumBy();
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(totalLianSuo);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/getTotalProjectOrderByName")
    public void getTotalProjectOrderByName(String userid, String projectName, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            List<ProjectHeadOrder> projectHeadOrderList = projectServ.getTotalProjectOrderByName(userid, projectName);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(projectHeadOrderList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getTotalProjectOrderITEMByOrderS")
    public void getTotalProjectOrderITEMByOrderS(String customer_Name, String userid, String orderNo, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            List<ProjectHeadOrderItem> projectHeadOrderList = projectServ.getTotalProjectOrderITEMByOrderS(customer_Name, userid, orderNo);
            for (int i = 0; i < projectHeadOrderList.size(); i++) {
                projectHeadOrderList.get(i).setGendansStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getDelivery_Goods_Emp()));
                projectHeadOrderList.get(i).setSalorsStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getZhanCha_Emp()));

                if (projectHeadOrderList.get(i).getJieSuan_Date_AccuStr() != null) {
                    projectHeadOrderList.get(i).setProgressCent(100);
                    continue;
                }

                if (projectHeadOrderList.get(i).getYanShou_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((6 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((8 / 9) * 100);
                    }
                    continue;
                }


                if (projectHeadOrderList.get(i).getInstall_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((5 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((7 / 9) * 100);
                    }
                    continue;
                }


                if (projectHeadOrderList.get(i).getDelivery_Goods_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((4 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((6 / 9) * 100);
                    }
                    continue;
                }

                if (projectHeadOrderList.get(i).getGiveOrder_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((3 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((5 / 9) * 100);
                    }
                    continue;
                }


                if (projectHeadOrderList.get(i).getProgram_confir_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((2 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((4 / 9) * 100);
                    }
                    continue;
                }
                if (projectHeadOrderList.get(i).getNewOrOld() == 1) {
                    if (projectHeadOrderList.get(i).getOutDraw_Date_AccuStr() != null) {
                        projectHeadOrderList.get(i).setProgressCent((3 / 9) * 100);
                        continue;
                    }

                    if (projectHeadOrderList.get(i).getZhanCha_Date_AccuStr() != null) {
                        projectHeadOrderList.get(i).setProgressCent((2 / 9) * 100);
                        continue;
                    }
                }

                if (projectHeadOrderList.get(i).getGetOrder_Date_PlanStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((1 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((1 / 9) * 100);
                    }
                    continue;
                } else {
                    projectHeadOrderList.get(i).setProgressCent(0);
                }
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(projectHeadOrderList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/getTotalProjectOrderITEMMoreByOrderS")
    public void getTotalProjectOrderITEMMoreByOrderS(String userid, String productName, String projectName, String customer_Name, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem projectHeadOrder = projectServ.getTotalProjectOrderITEMMoreByOrderS(userid, projectName, customer_Name, productName);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(projectHeadOrder);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getTotalProjectOrderITEMMoreById")
    public void getTotalProjectOrderITEMMoreById(Integer id, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem projectHeadOrder = projectServ.getTotalProjectOrderITEMMoreById(id);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(projectHeadOrder);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getTotalProjectOrderITEMMoreByOrderSById")
    public void getTotalProjectOrderITEMMoreByOrderSById(Integer id, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem projectHeadOrder = projectServ.getProjectItemHistoryById(id);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(projectHeadOrder);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/findNameRepeatOrNot")
    public void findNameRepeatOrNot(String userid, String projectName, String remark, HttpServletResponse response) throws Exception {
        try {
            int count = projectServ.findNameRepeatOrNot(userid, projectName);
            if (count == 0) {
                projectServ.saveProjectByNameAndRemark(userid, projectName, remark);
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(count);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getAllProvince")
    public void getAllProvince(String userId, HttpServletResponse response) throws Exception {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<China> chinaList = projectServ.getAllMainProvince();
            List<Position> pho = projectServ.getAllAlertPositions();
            List<Employee> employeeList = projectServ.getGenDanItems();
            List<Employee> item = projectServ.getSalorMItems();
            List<Employee> shejiList = projectServ.getSheJiItems();
            List<Employee> anzhuangList = projectServ.getAnzhuangList();
            List<Employee> yewuList = projectServ.getYeWuList();
            Integer useractor = projectServ.getUserActorByUserId(userId);
            String empNo = projectServ.getEmpNoByUserId(userId);
            List<ProjectHeadOrderItem> itemList = null;
            List<ProjectHeadOrderItem> selectList = new ArrayList<ProjectHeadOrderItem>();

            if (useractor != 1) {
                itemList = projectServ.getAllItemByUserIdAndNoFinish(empNo);
                for (ProjectHeadOrderItem itemm : itemList) {
                    if (itemm.getZhanCha_Date_AccuStr() == null && itemm.getZhanCha_Emp().contains(empNo)) {
                        itemm.setTitleName("勘察");
                        itemm.setDelivery_DateStr(itemm.getZhanCha_Date_PlanStr() == null ? "" : itemm.getZhanCha_Date_PlanStr());
                        itemm.setId(itemm.getId());
                        itemm.setCustomer_Name(itemm.getCustomer_Name());
                        itemm.setNewOrOld(itemm.getNewOrOld());
                        selectList.add(itemm);
                        continue;
                    }

                    if (itemm.getOutDraw_Emp() != null)
                        if (itemm.getOutDraw_Date_AccuStr() == null && itemm.getOutDraw_Emp().contains(empNo)) {
                            itemm.setTitleName("出图");
                            itemm.setDelivery_DateStr(itemm.getOutDraw_Date_PlanStr() == null ? "" : itemm.getOutDraw_Date_PlanStr());
                            itemm.setId(itemm.getId());
                            itemm.setCustomer_Name(itemm.getCustomer_Name());
                            itemm.setNewOrOld(itemm.getNewOrOld());
                            selectList.add(itemm);
                            continue;
                        }

                    if (itemm.getProgram_confir_Date_AccuStr() == null && itemm.getProgram_confir_Emp().contains(empNo)) {
                        itemm.setTitleName("方案确认");
                        itemm.setDelivery_DateStr(itemm.getProgram_confir_Date_PlanStr() == null ? "" : itemm.getProgram_confir_Date_PlanStr());
                        itemm.setId(itemm.getId());
                        itemm.setCustomer_Name(itemm.getCustomer_Name());
                        itemm.setNewOrOld(itemm.getNewOrOld());
                        selectList.add(itemm);
                        continue;
                    }


                    if (itemm.getXiaDanQianDuan_Date_AccuStr() == null && itemm.getXiaDanQianDuan_Emp().contains(empNo)) {
                        itemm.setTitleName("下单(前端)");
                        itemm.setDelivery_DateStr(itemm.getXiaDanQianDuan_Date_PlanStr() == null ? "" : itemm.getXiaDanQianDuan_Date_PlanStr());
                        itemm.setId(itemm.getId());
                        itemm.setCustomer_Name(itemm.getCustomer_Name());
                        itemm.setNewOrOld(itemm.getNewOrOld());
                        selectList.add(itemm);
                        continue;
                    }

                    if (itemm.getXiaDanShengChan_Date_AccuStr() == null && itemm.getXiaDanShengChan_Emp().contains(empNo)) {
                        itemm.setTitleName("下单(生产)");
                        itemm.setDelivery_DateStr(itemm.getXiaDanShengChan_Date_PlanStr() == null ? "" : itemm.getXiaDanShengChan_Date_PlanStr());
                        itemm.setId(itemm.getId());
                        itemm.setCustomer_Name(itemm.getCustomer_Name());
                        itemm.setNewOrOld(itemm.getNewOrOld());
                        selectList.add(itemm);
                        continue;
                    }

//                    if (itemm.getGiveOrder_Date_AccuStr() == null && itemm.getGiveOrder_Emp().contains(empNo)) {
//                        itemm.setTitleName("下单");
//                        itemm.setDelivery_DateStr(itemm.getGiveOrder_Date_PlanStr() == null ? "" : itemm.getGiveOrder_Date_PlanStr());
//                        itemm.setId(itemm.getId());
//                        itemm.setCustomer_Name(itemm.getCustomer_Name());
//                        itemm.setNewOrOld(itemm.getNewOrOld());
//                        selectList.add(itemm);
//                        continue;
//                    }


                    if (itemm.getDelivery_Goods_Date_AccuStr() == null && itemm.getDelivery_Goods_Emp().contains(empNo)) {
                        itemm.setTitleName("交货");
                        itemm.setDelivery_DateStr(itemm.getDelivery_Goods_Date_PlanStr() == null ? "" : itemm.getDelivery_Goods_Date_PlanStr());
                        itemm.setId(itemm.getId());
                        itemm.setCustomer_Name(itemm.getCustomer_Name());
                        itemm.setNewOrOld(itemm.getNewOrOld());
                        selectList.add(itemm);
                        continue;
                    }


                    if (itemm.getInstall_Emp() != null)
                        if (itemm.getInstall_Date_AccuStr() == null && itemm.getInstall_Emp().contains(empNo)) {
                            itemm.setTitleName("安装");
                            itemm.setDelivery_DateStr(itemm.getInstall_Date_PlanStr() == null ? "" : itemm.getInstall_Date_PlanStr());
                            itemm.setId(itemm.getId());
                            itemm.setCustomer_Name(itemm.getCustomer_Name());
                            itemm.setNewOrOld(itemm.getNewOrOld());
                            selectList.add(itemm);
                            continue;
                        }


                    if (itemm.getYanShou_Date_AccuStr() == null && itemm.getYanShou_Emp().contains(empNo)) {
                        itemm.setTitleName("验收");
                        itemm.setDelivery_DateStr(itemm.getYanShou_Date_PlanStr() == null ? "" : itemm.getYanShou_Date_PlanStr());
                        itemm.setId(itemm.getId());
                        itemm.setCustomer_Name(itemm.getCustomer_Name());
                        itemm.setNewOrOld(itemm.getNewOrOld());
                        selectList.add(itemm);
                        continue;
                    }


                    if (itemm.getJieSuan_Date_AccuStr() == null && itemm.getJieSuan_Emp().contains(empNo)) {
                        itemm.setTitleName("结算");
                        itemm.setDelivery_DateStr(itemm.getJieSuan_Date_PlanStr() == null ? "" : itemm.getJieSuan_Date_PlanStr());
                        itemm.setId(itemm.getId());
                        itemm.setCustomer_Name(itemm.getCustomer_Name());
                        itemm.setNewOrOld(itemm.getNewOrOld());
                        selectList.add(itemm);
                        continue;
                    }
                }
            } else {
                itemList = projectServ.getAllItemByUserIdAndNoFinish2(empNo);
                for (ProjectHeadOrderItem itemm : itemList) {
                    itemm.setTitleName("审核");
                    itemm.setDelivery_DateStr(itemm.getJieSuan_Date_PlanStr() == null ? "" : itemm.getJieSuan_Date_PlanStr());
                    itemm.setId(itemm.getId());
                    itemm.setCustomer_Name(itemm.getCustomer_Name());
                    itemm.setNewOrOld(itemm.getNewOrOld());
                    selectList.add(itemm);
                }
            }


            ProjectHeadOrderItem temp;
            for (int i = 0; i < selectList.size() - 1; i++) {
                for (int j = 0; j < selectList.size() - 1 - i; j++) {
                    if (selectList.get(j).getDelivery_DateStr().trim().length() > 0 && selectList.get(j + 1).getDelivery_DateStr().trim().length() > 0)
                        if (sdf.parse(selectList.get(j).getDelivery_DateStr()).after(sdf.parse(selectList.get(j + 1).getDelivery_DateStr()))) {
                            temp = selectList.get(j);
                            selectList.set(j, selectList.get(j + 1));
                            selectList.set(j + 1, temp);
                        }
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("chinaList", chinaList);
            map.put("pho", pho);
            map.put("employeeList", employeeList);
            map.put("item", item);
            map.put("shejiList", shejiList);
            map.put("yewuList", yewuList);
            map.put("anzhuangList", anzhuangList);
            map.put("useractor", useractor);
            map.put("itemList", selectList);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(map);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping("/getAllProvince1")
    public void getAllProvince1(String userInfo, String iv, HttpServletResponse response, HttpServletRequest request) throws Exception {
        try {
            HttpSession session = request.getSession();
            String session_key = (String) session.getAttribute("session_key");
            UserInfo info = new UserInfo();
            String userInfoAll = JiaMi.getUserInfo(userInfo, session_key, iv);
            JSONObject json = JSON.parseObject(userInfoAll);
            String userId = json.getString("userid");
            String userName = json.getString("name");
            info.setUserid(userId);
            info.setUserName(userName);

            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(info);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping("/checkOrderNoRepeat")
    public void checkOrderNoRepeat(Integer provinceId, String userId, String orderNo, String customerName,
                                   String projectName, Integer newOrOld, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrder count = projectServ.checkOrderNoRepeat(userId, orderNo, projectName, customerName);
            ProjectHead ph = projectServ.findPHbyName(projectName, userId);
            if (count == null)
                projectServ.saveProjectHeadByBean(provinceId, userId, orderNo, customerName, projectName, ph.getId(), newOrOld);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(count == null ? 0 : 1);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping("/checkProductRepeatForOneOrder")
    public void checkProductRepeatForOneOrder(Integer headId, Double hetongMoney, String orderNo, String date, String productName, String totalBao, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem count = projectServ.checkProductRepeatForOneOrder(hetongMoney, orderNo, productName);
            projectServ.saveProjectHeadItemByBean(hetongMoney, headId, productName, totalBao, date);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(count == null ? 0 : 1);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping("/updateAlertSet")
    public void updateAlertSet(String pho, HttpServletResponse response) throws Exception {
        try {
            AlertSet ph = JSONUtils.toBean(pho, AlertSet.class);
            ph.setAlertToPositions(StringUtils.join(ph.getAlertToPositionsArr2(), ","));
            projectServ.updateAlertSet(ph);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/updateAlertSet1")
    public void updateAlertSet1(String phn, HttpServletResponse response) throws Exception {
        try {
            DaysSet phnn = JSONUtils.toBean(phn, DaysSet.class);
            projectServ.updateAlertSet1(phnn);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/updateAlertSet2")
    public void updateAlertSet2(String pho, HttpServletResponse response) throws Exception {
        try {
            DaysSet phnn = JSONUtils.toBean(pho, DaysSet.class);
            projectServ.updateAlertSet2(phnn);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/PaiDanByAuto")
    public void PaiDanByAuto(String titleName, String pho, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem phoi = JSONUtils.toBean(pho, ProjectHeadOrderItem.class);
            phoi.setZhanCha_Emp(StringUtils.join(phoi.getZhanCha_Emps2(), ","));
            phoi.setOutDraw_Emp(StringUtils.join(phoi.getOutDraw_Emps2(), ","));
            phoi.setProgram_confir_Emp(StringUtils.join(phoi.getProgram_confir_Emps2(), ","));
            phoi.setXiaDanQianDuan_Emp(StringUtils.join(phoi.getXiaDanQianDuan_Emps2(), ","));
            phoi.setXiaDanShengChan_Emp(StringUtils.join(phoi.getXiaDanShengChan_Emps2(), ","));
            phoi.setDelivery_Goods_Emp(StringUtils.join(phoi.getDelivery_Goods_Emps2(), ","));
            phoi.setInstall_Emp(StringUtils.join(phoi.getInstall_Emps2(), ","));
            phoi.setYanShou_Emp(StringUtils.join(phoi.getYanShou_Emps2(), ","));
            phoi.setJieSuan_Emp(StringUtils.join(phoi.getJieSuan_Emps2(), ","));

            DaysSet ds = projectServ.getDaysSetByType(phoi.getNewOrOld());
            List<String> faDingList = projectServ.getFaDingList();
            int sundayge = 0;
            int qingjiaday = 0;
            int fadingday = 0;
            int qingJiaName = 0;
            String note = null;
            Map<String, String> map = null;
            if (phoi.getNewOrOld() == 1) {
                if (phoi.getTitleName().equals("getOrder_Date_PlanStr")) {
                    map = new DateUtil().getAfterDay2(phoi.getGetOrder_Date_PlanStr(), ds.getZhanchaDays(), faDingList, phoi.getZhanCha_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setZhanCha_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getZhanCha_Date_PlanStr(), ds.getOutDrawDays(), faDingList, phoi.getOutDraw_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setOutDraw_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getOutDraw_Date_PlanStr(), ds.getFanAnConfDays(), faDingList, phoi.getProgram_confir_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setProgram_confir_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_PlanStr(), ds.getXiaDanQianDuanDays(), faDingList, phoi.getXiaDanQianDuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanQianDuan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_PlanStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                    note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                    phoi.setNote(note);

                } else if (phoi.getTitleName().equals("zhanCha_Date_AccuStr")) {

                    map = new DateUtil().getAfterDay2(phoi.getZhanCha_Date_AccuStr(), ds.getOutDrawDays(), faDingList, phoi.getOutDraw_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setOutDraw_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getOutDraw_Date_PlanStr(), ds.getFanAnConfDays(), faDingList, phoi.getProgram_confir_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setProgram_confir_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_PlanStr(), ds.getXiaDanQianDuanDays(), faDingList, phoi.getXiaDanQianDuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanQianDuan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_PlanStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                    note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                    phoi.setNote(note);

                } else if ("zhanCha_Date_PlanStr".equals(phoi.getTitleName())) {
                    map = new DateUtil().getAfterDay2(phoi.getZhanCha_Date_PlanStr(), ds.getOutDrawDays(), faDingList, phoi.getOutDraw_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setOutDraw_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getOutDraw_Date_PlanStr(), ds.getFanAnConfDays(), faDingList, phoi.getProgram_confir_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setProgram_confir_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_PlanStr(), ds.getXiaDanQianDuanDays(), faDingList, phoi.getXiaDanQianDuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanQianDuan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_PlanStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                    note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;


                    phoi.setNote(note);
                } else if ("outDraw_Date_PlanStr".equals(phoi.getTitleName())) {
                    map = new DateUtil().getAfterDay2(phoi.getOutDraw_Date_PlanStr(), ds.getFanAnConfDays(), faDingList, phoi.getProgram_confir_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setProgram_confir_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_PlanStr(), ds.getXiaDanQianDuanDays(), faDingList, phoi.getXiaDanQianDuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanQianDuan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_PlanStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                    note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                    phoi.setNote(note);
                } else if ("outDraw_Date_AccuStr".equals(phoi.getTitleName())) {
                    map = new DateUtil().getAfterDay2(phoi.getOutDraw_Date_AccuStr(), ds.getFanAnConfDays(), faDingList, phoi.getProgram_confir_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setProgram_confir_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_PlanStr(), ds.getXiaDanQianDuanDays(), faDingList, phoi.getXiaDanQianDuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanQianDuan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_PlanStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                    note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                    phoi.setNote(note);
                }
            } else {

                if (phoi.getTitleName().equals("getOrder_Date_PlanStr")) {
                    map = new DateUtil().getAfterDay2(phoi.getGetOrder_Date_PlanStr(), ds.getFanAnConfDays(), faDingList, phoi.getZhanCha_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setProgram_confir_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_PlanStr(), ds.getXiaDanQianDuanDays(), faDingList, phoi.getXiaDanQianDuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanQianDuan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_PlanStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                    map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                    map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                    sundayge += Integer.valueOf(map.get("sundayge"));
                    qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                    fadingday += Integer.valueOf(map.get("fadingday"));
                    phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                    note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                    phoi.setNote(note);

                }
            }
            if ("program_confir_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_PlanStr(), ds.getXiaDanQianDuanDays(), faDingList, phoi.getXiaDanQianDuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setXiaDanQianDuan_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_PlanStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("program_confir_Date_AccuStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_AccuStr(), ds.getXiaDanQianDuanDays(), faDingList, phoi.getXiaDanQianDuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setXiaDanQianDuan_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_PlanStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("xiaDanQianDuan_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_PlanStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("xiaDanQianDuan_Date_AccuStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getXiaDanQianDuan_Date_AccuStr(), ds.getXiaDanShengChanDays(), faDingList, phoi.getXiaDanShengChan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setXiaDanShengChan_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("xiaDanShengChan_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_PlanStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("xiaDanShengChan_Date_AccuStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getXiaDanShengChan_Date_AccuStr(), ds.getProdDays(), faDingList, phoi.getDelivery_Goods_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            }else if ("delivery_Goods_Date_AccuStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_AccuStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("delivery_Goods_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays(), faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("install_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("install_Date_AccuStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_AccuStr(), ds.getYanshouDays(), faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("yanShou_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("yanShou_Date_AccuStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_AccuStr(), ds.getJiesuanDays(), faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            }

            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(phoi);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/PaiDanByAutoOld")
    public void PaiDanByAutoOld(String titleName, String pho, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem phoi = JSONUtils.toBean(pho, ProjectHeadOrderItem.class);
            phoi.setProgram_confir_Emp(StringUtils.join(phoi.getProgram_confir_Emps2(), ","));
            phoi.setGiveOrder_Emp(StringUtils.join(phoi.getGiveOrder_Emps2(), ","));
            phoi.setDelivery_Goods_Emp(StringUtils.join(phoi.getDelivery_Goods_Emps2(), ","));
            phoi.setInstall_Emp(StringUtils.join(phoi.getInstall_Emps2(), ","));
            phoi.setYanShou_Emp(StringUtils.join(phoi.getYanShou_Emps2(), ","));
            phoi.setJieSuan_Emp(StringUtils.join(phoi.getJieSuan_Emps2(), ","));

            ProjectHeadOrder ph = projectServ.getProjectOrderById(phoi.getOrder_Id());
            DaysSet ds = projectServ.getDaysSetByType(ph.getNewOrOld());
            List<String> faDingList = projectServ.getFaDingList();
            int sundayge = 0;
            int qingjiaday = 0;
            int fadingday = 0;
            int qingJiaName = 0;
            String note = null;
            Map<String, String> map = null;
            if (phoi.getTitleName().equals("getOrder_Date_PlanStr")) {
                map = new DateUtil().getAfterDay2(phoi.getGetOrder_Date_PlanStr(), ds.getFanAnConfDays() + 1, faDingList, phoi.getProgram_confir_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setProgram_confir_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_PlanStr(), 1 + 1, faDingList, phoi.getGiveOrder_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setGiveOrder_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getGiveOrder_Date_PlanStr(), ds.getProdDays() + 1, faDingList, phoi.getDelivery_Goods_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays() + 1, faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);

            } else if ("program_confir_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getProgram_confir_Date_PlanStr(), 1 + 1, faDingList, phoi.getGiveOrder_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setGiveOrder_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getGiveOrder_Date_PlanStr(), ds.getProdDays() + 1, faDingList, phoi.getDelivery_Goods_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays() + 1, faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("giveOrder_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getGiveOrder_Date_PlanStr(), ds.getProdDays() + 1, faDingList, phoi.getDelivery_Goods_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays() + 1, faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("delivery_Goods_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays() + 1, faDingList, phoi.getInstall_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setInstall_Date_PlanStr(map.get("nextDay"));

                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("install_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, phoi.getYanShou_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setYanShou_Date_PlanStr(map.get("nextDay"));


                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            } else if ("yanShou_Date_PlanStr".equals(phoi.getTitleName())) {
                map = new DateUtil().getAfterDay2(phoi.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, phoi.getJieSuan_Emps2());
                sundayge += Integer.valueOf(map.get("sundayge"));
                qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                fadingday += Integer.valueOf(map.get("fadingday"));
                phoi.setJieSuan_Date_PlanStr(map.get("nextDay"));

                note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;

                phoi.setNote(note);
            }

            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(phoi);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    public void sendMessageByAllQueryAlert(String beforeDay) throws Exception {
        IProjectServ projectServa = SpringUtil.getBean(IProjectServ.class);
        net.sf.json.JSONObject insertdata = new net.sf.json.JSONObject();
        net.sf.json.JSONObject content = new net.sf.json.JSONObject();
        LinkedHashSet<String> hashSet = null;
        ArrayList<String> listWithoutDuplicates = null;
        List<String> userIdList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        List<String> sendEmpList = new ArrayList<String>();
        List<String> nameList = null;
        AlertSet alertSet = projectServa.getAlertSet();
        List<ProjectHeadOrderItem> itemList = projectServa.getProjectItemAllByCondi2();
        for (ProjectHeadOrderItem item : itemList) {
            hashSet = null;
            listWithoutDuplicates = null;
            userIdList = new ArrayList<String>();
            sb = new StringBuilder();
            sendEmpList = new ArrayList<String>();
            nameList = null;
            if (item.getZhanCha_Date_AccuStr() == null && item.getZhanCha_Date_PlanStr() != null) {
                if (sdf.parse(item.getZhanCha_Date_PlanStr()).before(new Date())) {
                    if (item.getZhanCha_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getZhanCha_Emps()));
                    if (item.getJieSuan_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getJieSuan_Emps()));
                    if (sendEmpList != null && sendEmpList.size() != 0)
                        nameList = projectServa.getNamesByEmpNos(sendEmpList);
                    sb.append("订单延迟报警\n");
                    sb.append((item.getOrdeNo() == null ? "" : item.getOrderNo()) + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("勘察计划日期:" + item.getZhanCha_Date_PlanStr() + "\n");
                    sb.append("负责人员" + nameList.toString() + "\n");
                    sb.append("至今还未填写勘察实际日期,如延期,请填写勘察计划日期,系统会自动给您这个订单重新排单.");


                    hashSet = new LinkedHashSet<>(sendEmpList);
                    listWithoutDuplicates = new ArrayList<>(hashSet);
                    userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
                    userIdList.add("WangHongMei");
                    content.put("content", sb.toString());
                    insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                    insertdata.put("msgtype", "text");
                    insertdata.put("agentid", 1000003);
                    insertdata.put("text", content);
                    insertdata.put("safe", 0);
                    QYWX.SendMsgtoBody(insertdata);
                }
                continue;
            }


            if (item.getOutDraw_Date_AccuStr() == null && item.getOutDraw_Date_PlanStr() != null) {
                if (sdf.parse(item.getOutDraw_Date_PlanStr()).before(new Date())) {
                    if (item.getOutDraw_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getOutDraw_Emps()));
                    if (item.getJieSuan_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getJieSuan_Emps()));
                    if (sendEmpList != null && sendEmpList.size() != 0)
                        nameList = projectServa.getNamesByEmpNos(sendEmpList);
                    sb.append("订单延迟报警\n");
                    sb.append((item.getOrdeNo() == null ? "" : item.getOrderNo()) + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("出图计划日期:" + item.getOutDraw_Date_PlanStr() + "\n");
                    sb.append("负责人员" + nameList.toString() + "\n");
                    sb.append("至今还未填写出图实际日期,如延期,请填写出图计划日期,系统会自动给您这个订单重新排单.");


                    hashSet = new LinkedHashSet<>(sendEmpList);
                    listWithoutDuplicates = new ArrayList<>(hashSet);
                    userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
                    userIdList.add("WangHongMei");
                    content.put("content", sb.toString());
                    insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                    insertdata.put("msgtype", "text");
                    insertdata.put("agentid", 1000003);
                    insertdata.put("text", content);
                    insertdata.put("safe", 0);
                    QYWX.SendMsgtoBody(insertdata);
                }
                continue;
            }


            if (item.getProgram_confir_Date_AccuStr() == null && item.getProgram_confir_Date_PlanStr() != null) {
                if (sdf.parse(item.getProgram_confir_Date_PlanStr()).before(new Date())) {
                    if (item.getProgram_confir_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getProgram_confir_Emps()));
                    if (item.getJieSuan_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getProgram_confir_Emps()));
                    if (sendEmpList != null && sendEmpList.size() != 0)
                        nameList = projectServa.getNamesByEmpNos(sendEmpList);
                    sb.append("订单延迟报警\n");
                    sb.append((item.getOrdeNo() == null ? "" : item.getOrderNo()) + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("方案确认计划日期:" + item.getProgram_confir_Date_PlanStr() + "\n");
                    sb.append("负责人员" + nameList.toString() + "\n");
                    sb.append("至今还未填写方案确认实际日期,如延期,请填写方案确认计划日期,系统会自动给您这个订单重新排单.");


                    hashSet = new LinkedHashSet<>(sendEmpList);
                    listWithoutDuplicates = new ArrayList<>(hashSet);
                    userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
                    userIdList.add("WangHongMei");
                    content.put("content", sb.toString());
                    insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                    insertdata.put("msgtype", "text");
                    insertdata.put("agentid", 1000003);
                    insertdata.put("text", content);
                    insertdata.put("safe", 0);
                    QYWX.SendMsgtoBody(insertdata);
                }
                continue;
            }


            if (item.getGiveOrder_Date_AccuStr() == null && item.getGiveOrder_Date_PlanStr() != null) {
                if (sdf.parse(item.getGiveOrder_Date_PlanStr()).before(new Date())) {
                    if (item.getGiveOrder_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getGiveOrder_Emps()));
                    if (item.getJieSuan_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getJieSuan_Emps()));
                    if (sendEmpList != null && sendEmpList.size() != 0)
                        nameList = projectServa.getNamesByEmpNos(sendEmpList);
                    sb.append("订单延迟报警\n");
                    sb.append((item.getOrdeNo() == null ? "" : item.getOrderNo()) + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("下单计划日期:" + item.getGiveOrder_Date_PlanStr() + "\n");
                    sb.append("负责人员" + nameList.toString() + "\n");
                    sb.append("至今还未填写下单实际日期,如延期,请填写下单计划日期,系统会自动给您这个订单重新排单.");


                    hashSet = new LinkedHashSet<>(sendEmpList);
                    listWithoutDuplicates = new ArrayList<>(hashSet);
                    userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
                    userIdList.add("WangHongMei");
                    content.put("content", sb.toString());
                    insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                    insertdata.put("msgtype", "text");
                    insertdata.put("agentid", 1000003);
                    insertdata.put("text", content);
                    insertdata.put("safe", 0);
                    QYWX.SendMsgtoBody(insertdata);
                }
                continue;
            }


            if (item.getDelivery_Goods_Date_AccuStr() == null && item.getDelivery_Goods_Date_PlanStr() != null) {
                if (sdf.parse(item.getDelivery_Goods_Date_PlanStr()).before(new Date())) {
                    if (item.getDelivery_Goods_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getDelivery_Goods_Emps()));
                    if (item.getJieSuan_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getJieSuan_Emps()));
                    if (sendEmpList != null && sendEmpList.size() != 0)
                        nameList = projectServa.getNamesByEmpNos(sendEmpList);
                    sb.append("订单延迟报警\n");
                    sb.append((item.getOrdeNo() == null ? "" : item.getOrderNo()) + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("交货计划日期:" + item.getDelivery_Goods_Date_PlanStr() + "\n");
                    sb.append("负责人员" + nameList.toString() + "\n");
                    sb.append("至今还未填写交货实际日期,如延期,请填写交货计划日期,系统会自动给您这个订单重新排单.");


                    hashSet = new LinkedHashSet<>(sendEmpList);
                    listWithoutDuplicates = new ArrayList<>(hashSet);
                    userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
                    userIdList.add("WangHongMei");
                    content.put("content", sb.toString());
                    insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                    insertdata.put("msgtype", "text");
                    insertdata.put("agentid", 1000003);
                    insertdata.put("text", content);
                    insertdata.put("safe", 0);
                    QYWX.SendMsgtoBody(insertdata);
                }
                continue;
            }


            if (item.getInstall_Date_AccuStr() == null && item.getInstall_Date_PlanStr() != null) {
                if (sdf.parse(item.getInstall_Date_PlanStr()).before(new Date())) {
                    if (item.getInstall_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getInstall_Emps()));
                    if (item.getJieSuan_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getJieSuan_Emps()));
                    if (sendEmpList != null && sendEmpList.size() != 0)
                        nameList = projectServa.getNamesByEmpNos(sendEmpList);
                    sb.append("订单延迟报警\n");
                    sb.append((item.getOrdeNo() == null ? "" : item.getOrderNo()) + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("安装计划日期:" + item.getInstall_Date_PlanStr() + "\n");
                    sb.append("负责人员" + nameList.toString() + "\n");
                    sb.append("至今还未填写安装实际日期,如延期,请填写安装计划日期,系统会自动给您这个订单重新排单.");


                    hashSet = new LinkedHashSet<>(sendEmpList);
                    listWithoutDuplicates = new ArrayList<>(hashSet);
                    userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
                    userIdList.add("WangHongMei");
                    content.put("content", sb.toString());
                    insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                    insertdata.put("msgtype", "text");
                    insertdata.put("agentid", 1000003);
                    insertdata.put("text", content);
                    insertdata.put("safe", 0);
                    QYWX.SendMsgtoBody(insertdata);
                }
                continue;
            }


            if (item.getYanShou_Date_AccuStr() == null && item.getYanShou_Date_PlanStr() != null) {
                if (sdf.parse(item.getYanShou_Date_PlanStr()).before(new Date())) {
                    if (item.getYanShou_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getYanShou_Emps()));
                    if (item.getJieSuan_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getJieSuan_Emps()));
                    if (sendEmpList != null && sendEmpList.size() != 0)
                        nameList = projectServa.getNamesByEmpNos(sendEmpList);
                    sb.append("订单延迟报警\n");
                    sb.append((item.getOrdeNo() == null ? "" : item.getOrderNo()) + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("验收计划日期:" + item.getYanShou_Date_PlanStr() + "\n");
                    sb.append("负责人员" + nameList.toString() + "\n");
                    sb.append("至今还未填写验收实际日期，如延期,请填写验收计划日期,系统会自动给您这个订单重新排单.");


                    hashSet = new LinkedHashSet<>(sendEmpList);
                    listWithoutDuplicates = new ArrayList<>(hashSet);
                    userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
                    userIdList.add("WangHongMei");
                    content.put("content", sb.toString());
                    insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                    insertdata.put("msgtype", "text");
                    insertdata.put("agentid", 1000003);
                    insertdata.put("text", content);
                    insertdata.put("safe", 0);
                    QYWX.SendMsgtoBody(insertdata);
                }
                continue;
            }


            if (item.getJieSuan_Date_AccuStr() == null && item.getJieSuan_Date_PlanStr() != null) {
                if (sdf.parse(item.getJieSuan_Date_PlanStr()).before(new Date())) {
                    if (item.getJieSuan_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getJieSuan_Emps()));
                    if (item.getJieSuan_Emp() != null)
                        sendEmpList.addAll(Arrays.asList(item.getJieSuan_Emps()));
                    if (sendEmpList != null && sendEmpList.size() != 0)
                        nameList = projectServa.getNamesByEmpNos(sendEmpList);
                    sb.append("订单延迟报警\n");
                    sb.append((item.getOrdeNo() == null ? "" : item.getOrderNo()) + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("结算计划日期:" + item.getJieSuan_Date_PlanStr() + "\n");
                    sb.append("负责人员" + nameList.toString() + "\n");
                    sb.append("至今还未填写结算实际日期,如延期,请填写结算计划日期,系统会自动给您这个订单重新排单.");


                    hashSet = new LinkedHashSet<>(sendEmpList);
                    listWithoutDuplicates = new ArrayList<>(hashSet);
                    userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
                    userIdList.add("WangHongMei");
                    content.put("content", sb.toString());
                    insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                    insertdata.put("msgtype", "text");
                    insertdata.put("agentid", 1000003);
                    insertdata.put("text", content);
                    insertdata.put("safe", 0);
                    QYWX.SendMsgtoBody(insertdata);
                }
                continue;
            }
        }
    }

    public void sendMessageByAllQueryYQ(String beoreDay) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        IProjectServ projectServa = SpringUtil.getBean(IProjectServ.class);
        List<String> faDingList = projectServa.getFaDingList();
        StringBuilder sb = null;
        List<String> sendEmpList = new ArrayList<String>();
        DaysSet ds = null;
        AlertSet alertSet = projectServa.getAlertSet();
        List<ProjectHeadOrderItem> itemList = projectServa.getProjectItemAllByCondi();
        for (ProjectHeadOrderItem item : itemList) {
            sb = new StringBuilder();
            ds = projectServa.getDaysSetByType(item.getNewOrOld());
            if (item.getJieSuan_Emp() != null)
                sendEmpList.addAll(Arrays.asList(item.getJieSuan_Emps()));
            if (item.getInstall_Emp() != null)
                sendEmpList.addAll(Arrays.asList(item.getInstall_Emps()));
            if (DateUtil.getDistanceOfTwoDate(new Date(), sdf.parse(item.getDelivery_DateStr())) < alertSet.getNorAlertDays()
                    && DateUtil.getDistanceOfTwoDate(new Date(), sdf.parse(item.getDelivery_DateStr())) >= 0) {
                if (DateUtil.getDistanceOfTwoDate(new Date(), sdf.parse(item.getDelivery_DateStr())) < alertSet.getImpAlertDays()) {
                    sb.append("订单特大报警\n");
                    sb.append(item.getOrdeNo() + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("交货日期:" + item.getDelivery_DateStr() + "\n");
                    sb.append("离交店时间只剩" + DateUtil.getDistanceOfTwoDate(new Date(), sdf.parse(item.getDelivery_DateStr())) + "天,安装阶段还未完成。");

                } else {
                    sb.append("订单重大报警\n");
                    sb.append(item.getOrdeNo() + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("交货日期:" + item.getDelivery_DateStr() + "\n");
                    sb.append("离交店时间只剩" + DateUtil.getDistanceOfTwoDate(new Date(), sdf.parse(item.getDelivery_DateStr())) + "天,安装阶段还未完成");
                }
            } else {
                if (DateUtil.getDistanceOfTwoDate(new Date(), sdf.parse(item.getDelivery_DateStr())) < 0) {
                    sb.append("订单异常报警\n");
                    sb.append(item.getOrdeNo() + "\n");
                    sb.append(item.getProjectName() + "\n");
                    sb.append(item.getCustomer_Name() + "\n");
                    sb.append(item.getProduct_Name() + "\n");
                    sb.append("交货日期:" + item.getDelivery_DateStr() + "\n");
                    sb.append("请检查该订单的交货时间设置,安装还未完成,但交货日期竟在过去");
                }
            }

            if (sendEmpList != null && sendEmpList.size() > 0) {
                LinkedHashSet<String> hashSet = new LinkedHashSet<>(sendEmpList);
                ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);
                List<String> userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
                userIdList.add("WangHongMei");
                net.sf.json.JSONObject insertdata = new net.sf.json.JSONObject();
                net.sf.json.JSONObject content = new net.sf.json.JSONObject();
                content.put("content", sb.toString());
                insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                insertdata.put("msgtype", "text");
                insertdata.put("agentid", 1000003);
                insertdata.put("text", content);
                insertdata.put("safe", 0);
                QYWX.SendMsgtoBody(insertdata);
                sendEmpList.clear();
            }
        }
    }


    public void sendMessageByAllQueryQJ(String beforeDay) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        List<String> sendEmpList = new ArrayList<String>();
        IProjectServ projectServa = SpringUtil.getBean(IProjectServ.class);
        List<Leave> leaveList = projectServa.getAllLeaveDataByBeBoreDayApply(beforeDay);
        double leaveDays = 0;
        List<String> faDingList = projectServa.getFaDingList();
        DaysSet ds = null;
        List<ProjectHeadOrderItem> itemList = null;
        Map<String, String> map = new HashMap<String, String>();
        int sundayge = 0;
        int qingjiaday = 0;
        int fadingday = 0;
        int qingJiaName = 0;
        String note = null;
        for (Leave leave : leaveList) {
            itemList = projectServa.getItemListByOrderStatusAndCheckAndEmpnoIn(leave.getEmpNo());
            leaveDays = DateUtil.getDistanceOfTwoDate2(leave.getBeginLeaveStr(), leave.getEndLeaveStr());
            for (ProjectHeadOrderItem item : itemList) {
                ds = projectServa.getDaysSetByType(item.getNewOrOld());
                if (item.getZhanCha_Emp().contains(leave.getEmpNo())) {
                    if (item.getZhanCha_Date_AccuStr() == null) {
                        if (sdf.parse(leave.getBeginLeaveStr()).before(sdf.parse(item.getZhanCha_Date_PlanStr()))
                                && sdf.parse(leave.getBeginLeaveStr()).after(sdf.parse(item.getGetOrder_Date_PlanStr()))) {
                            map = new DateUtil().getAfterDay3(item.getZhanCha_Date_PlanStr(), (int) Math.round(leaveDays), faDingList, leave.getEmpNo());
                            item.setZhanCha_Date_PlanStr(map.get("nextDay"));
                            sendEmpList.addAll(Arrays.asList(item.getZhanCha_Emps()));
                            sb.append(leave.getName() + "请假" + leaveDays + "天\n" + "导致\n" +
                                    item.getOrdeNo() + "\n" + item.getProduct_Name() + "\n从勘察计划日期开始延期,原交期为" + item.getJieSuan_Date_PlanStr());

                            map = new DateUtil().getAfterDay2(item.getZhanCha_Date_PlanStr(), ds.getOutDrawDays() + 1, faDingList, item.getOutDraw_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setOutDraw_Date_PlanStr(map.get("nextDay"));


                            map = new DateUtil().getAfterDay2(item.getOutDraw_Date_PlanStr(), ds.getFanAnConfDays() + 1, faDingList, item.getProgram_confir_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setProgram_confir_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getProgram_confir_Date_PlanStr(), 1 + 1, faDingList, item.getGiveOrder_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setGiveOrder_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getGiveOrder_Date_PlanStr(), ds.getProdDays() + 1, faDingList, item.getDelivery_Goods_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));


                            map = new DateUtil().getAfterDay2(item.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays() + 1, faDingList, item.getInstall_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setInstall_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, item.getYanShou_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setYanShou_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, item.getJieSuan_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setJieSuan_Date_PlanStr(map.get("nextDay"));

                            sb.append("\n现计划交期为:" + item.getJieSuan_Date_PlanStr());
                            note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;
                            item.setNote(note);
                            item.setUpdateDateStr(sdf.format(new Date()));
                            item.setUpdateUserId("WangHongMei");
                            item.setVersion(item.getVersion() + 0.1);
                            projectServa.updateProjectItemByBeanOnlyDateAndNote(item);
                        }
                    }
                }

                if (item.getOutDraw_Emp().contains(leave.getEmpNo())) {
                    if (item.getOutDraw_Date_AccuStr() == null) {
                        if (sdf.parse(leave.getBeginLeaveStr()).before(sdf.parse(item.getOutDraw_Date_PlanStr()))
                                && sdf.parse(leave.getBeginLeaveStr()).after(sdf.parse(item.getOutDraw_Date_PlanStr()))) {
                            map = new DateUtil().getAfterDay3(item.getOutDraw_Date_PlanStr(), (int) Math.round(leaveDays), faDingList, leave.getEmpNo());
                            item.setOutDraw_Date_PlanStr(map.get("nextDay"));
                            sendEmpList.addAll(Arrays.asList(item.getOutDraw_Emps()));
                            sb.append(leave.getName() + "请假" + leaveDays + "天\n" + "导致\n" +
                                    item.getOrdeNo() + "\n" + item.getProduct_Name() + "\n从出图计划日期开始延期,原交期为" + item.getJieSuan_Date_PlanStr());

                            map = new DateUtil().getAfterDay2(item.getOutDraw_Date_PlanStr(), ds.getFanAnConfDays() + 1, faDingList, item.getProgram_confir_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setProgram_confir_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getProgram_confir_Date_PlanStr(), 1 + 1, faDingList, item.getGiveOrder_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setGiveOrder_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getGiveOrder_Date_PlanStr(), ds.getProdDays() + 1, faDingList, item.getDelivery_Goods_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));


                            map = new DateUtil().getAfterDay2(item.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays() + 1, faDingList, item.getInstall_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setInstall_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, item.getYanShou_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setYanShou_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, item.getJieSuan_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setJieSuan_Date_PlanStr(map.get("nextDay"));

                            sb.append("\n现计划交期为:" + item.getJieSuan_Date_PlanStr());
                            note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;
                            item.setNote(note);
                            item.setUpdateDateStr(sdf.format(new Date()));
                            item.setUpdateUserId("WangHongMei");
                            item.setVersion(item.getVersion() + 0.1);
                            projectServa.updateProjectItemByBeanOnlyDateAndNote(item);
                        }
                    }
                }


                if (item.getProgram_confir_Emp().contains(leave.getEmpNo())) {
                    if (item.getProgram_confir_Date_AccuStr() == null) {
                        if (sdf.parse(leave.getBeginLeaveStr()).before(sdf.parse(item.getProgram_confir_Date_PlanStr()))
                                && sdf.parse(leave.getBeginLeaveStr()).after(sdf.parse(item.getProgram_confir_Date_PlanStr()))) {
                            map = new DateUtil().getAfterDay3(item.getProgram_confir_Date_PlanStr(), (int) Math.round(leaveDays), faDingList, leave.getEmpNo());
                            item.setProgram_confir_Date_PlanStr(map.get("nextDay"));
                            sendEmpList.addAll(Arrays.asList(item.getProgram_confir_Emps()));
                            sb.append(leave.getName() + "请假" + leaveDays + "天\n" + "导致\n" +
                                    item.getOrdeNo() + "\n" + item.getProduct_Name() + "\n从方案确认计划日期开始延期,原交期为" + item.getJieSuan_Date_PlanStr());

                            map = new DateUtil().getAfterDay2(item.getProgram_confir_Date_PlanStr(), 1 + 1, faDingList, item.getGiveOrder_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setGiveOrder_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getGiveOrder_Date_PlanStr(), ds.getProdDays() + 1, faDingList, item.getDelivery_Goods_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));


                            map = new DateUtil().getAfterDay2(item.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays() + 1, faDingList, item.getInstall_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setInstall_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, item.getYanShou_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setYanShou_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, item.getJieSuan_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setJieSuan_Date_PlanStr(map.get("nextDay"));

                            sb.append("\n现计划交期为:" + item.getJieSuan_Date_PlanStr());
                            note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;
                            item.setNote(note);
                            item.setUpdateDateStr(sdf.format(new Date()));
                            item.setUpdateUserId("WangHongMei");
                            item.setVersion(item.getVersion() + 0.1);
                            projectServa.updateProjectItemByBeanOnlyDateAndNote(item);
                        }
                    }
                }


                if (item.getGiveOrder_Emp().contains(leave.getEmpNo())) {
                    if (item.getGiveOrder_Date_AccuStr() == null) {
                        if (sdf.parse(leave.getBeginLeaveStr()).before(sdf.parse(item.getGiveOrder_Date_PlanStr()))
                                && sdf.parse(leave.getBeginLeaveStr()).after(sdf.parse(item.getGiveOrder_Date_PlanStr()))) {
                            map = new DateUtil().getAfterDay3(item.getGiveOrder_Date_PlanStr(), (int) Math.round(leaveDays), faDingList, leave.getEmpNo());
                            item.setGiveOrder_Date_PlanStr(map.get("nextDay"));
                            sendEmpList.addAll(Arrays.asList(item.getGiveOrder_Emps()));
                            sb.append(leave.getName() + "请假" + leaveDays + "天\n" + "导致\n" +
                                    item.getOrdeNo() + "\n" + item.getProduct_Name() + "\n从下单日期开始延期,原交期为" + item.getJieSuan_Date_PlanStr());

                            map = new DateUtil().getAfterDay2(item.getGiveOrder_Date_PlanStr(), ds.getProdDays() + 1, faDingList, item.getDelivery_Goods_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));


                            map = new DateUtil().getAfterDay2(item.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays() + 1, faDingList, item.getInstall_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setInstall_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, item.getYanShou_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setYanShou_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, item.getJieSuan_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setJieSuan_Date_PlanStr(map.get("nextDay"));

                            sb.append("\n现计划交期为:" + item.getJieSuan_Date_PlanStr());
                            note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;
                            item.setNote(note);
                            item.setUpdateDateStr(sdf.format(new Date()));
                            item.setUpdateUserId("WangHongMei");
                            item.setVersion(item.getVersion() + 0.1);
                            projectServa.updateProjectItemByBeanOnlyDateAndNote(item);
                        }
                    }
                }


                if (item.getDelivery_Goods_Emp().contains(leave.getEmpNo())) {
                    if (item.getDelivery_Goods_Date_AccuStr() == null) {
                        if (sdf.parse(leave.getBeginLeaveStr()).before(sdf.parse(item.getDelivery_Goods_Date_PlanStr()))
                                && sdf.parse(leave.getBeginLeaveStr()).after(sdf.parse(item.getDelivery_Goods_Date_PlanStr()))) {
                            map = new DateUtil().getAfterDay3(item.getDelivery_Goods_Date_PlanStr(), (int) Math.round(leaveDays), faDingList, leave.getEmpNo());
                            item.setDelivery_Goods_Date_PlanStr(map.get("nextDay"));
                            sendEmpList.addAll(Arrays.asList(item.getDelivery_Goods_Emps()));
                            sb.append(leave.getName() + "请假" + leaveDays + "天\n" + "导致\n" +
                                    item.getOrdeNo() + "\n" + item.getProduct_Name() + "\n从生产交货日期开始延期,原交期为" + item.getJieSuan_Date_PlanStr());

                            map = new DateUtil().getAfterDay2(item.getDelivery_Goods_Date_PlanStr(), ds.getAnzhuangDays() + 1, faDingList, item.getInstall_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setInstall_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, item.getYanShou_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setYanShou_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, item.getJieSuan_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setJieSuan_Date_PlanStr(map.get("nextDay"));

                            sb.append("\n现计划交期为:" + item.getJieSuan_Date_PlanStr());
                            note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;
                            item.setNote(note);
                            item.setUpdateDateStr(sdf.format(new Date()));
                            item.setUpdateUserId("WangHongMei");
                            item.setVersion(item.getVersion() + 0.1);
                            projectServa.updateProjectItemByBeanOnlyDateAndNote(item);
                        }
                    }
                }


                if (item.getInstall_Emp().contains(leave.getEmpNo())) {
                    if (item.getInstall_Date_AccuStr() == null) {
                        if (sdf.parse(leave.getBeginLeaveStr()).before(sdf.parse(item.getInstall_Date_PlanStr()))
                                && sdf.parse(leave.getBeginLeaveStr()).after(sdf.parse(item.getInstall_Date_PlanStr()))) {
                            map = new DateUtil().getAfterDay3(item.getInstall_Date_PlanStr(), (int) Math.round(leaveDays), faDingList, leave.getEmpNo());
                            item.setInstall_Date_PlanStr(map.get("nextDay"));
                            sendEmpList.addAll(Arrays.asList(item.getInstall_Emps()));
                            sb.append(leave.getName() + "请假" + leaveDays + "天\n" + "导致\n" +
                                    item.getOrdeNo() + "\n" + item.getProduct_Name() + "\n从安装日期开始延期,原交期为" + item.getJieSuan_Date_PlanStr());

                            map = new DateUtil().getAfterDay2(item.getInstall_Date_PlanStr(), ds.getYanshouDays() + 1, faDingList, item.getYanShou_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setYanShou_Date_PlanStr(map.get("nextDay"));

                            map = new DateUtil().getAfterDay2(item.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, item.getJieSuan_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setJieSuan_Date_PlanStr(map.get("nextDay"));

                            sb.append("\n现计划交期为:" + item.getJieSuan_Date_PlanStr());
                            note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;
                            item.setNote(note);
                            item.setUpdateDateStr(sdf.format(new Date()));
                            item.setUpdateUserId("WangHongMei");
                            item.setVersion(item.getVersion() + 0.1);
                            projectServa.updateProjectItemByBeanOnlyDateAndNote(item);
                        }
                    }
                }


                if (item.getYanShou_Emp().contains(leave.getEmpNo())) {
                    if (item.getYanShou_Date_AccuStr() == null) {
                        if (sdf.parse(leave.getBeginLeaveStr()).before(sdf.parse(item.getYanShou_Date_PlanStr()))
                                && sdf.parse(leave.getBeginLeaveStr()).after(sdf.parse(item.getYanShou_Date_PlanStr()))) {
                            map = new DateUtil().getAfterDay3(item.getYanShou_Date_PlanStr(), (int) Math.round(leaveDays), faDingList, leave.getEmpNo());
                            item.setYanShou_Date_PlanStr(map.get("nextDay"));
                            sendEmpList.addAll(Arrays.asList(item.getYanShou_Emps()));
                            sb.append(leave.getName() + "请假" + leaveDays + "天\n" + "导致\n" +
                                    item.getOrdeNo() + "\n" + item.getProduct_Name() + "\n从验收日期开始延期,原交期为" + item.getJieSuan_Date_PlanStr());


                            map = new DateUtil().getAfterDay2(item.getYanShou_Date_PlanStr(), ds.getJiesuanDays() + 1, faDingList, item.getJieSuan_Emps());
                            sundayge += Integer.valueOf(map.get("sundayge"));
                            qingjiaday += Integer.valueOf(map.get("qingjiaday"));
                            fadingday += Integer.valueOf(map.get("fadingday"));
                            item.setJieSuan_Date_PlanStr(map.get("nextDay"));

                            sb.append("\n现计划交期为:" + item.getJieSuan_Date_PlanStr());
                            note = "整个项目周期内周日有" + sundayge + "天,法定假日有" + fadingday + "天,请假有" + qingjiaday + "天,请假工号为:" + qingJiaName;
                            item.setNote(note);
                            item.setUpdateDateStr(sdf.format(new Date()));
                            item.setUpdateUserId("WangHongMei");
                            item.setVersion(item.getVersion() + 0.1);
                            projectServa.updateProjectItemByBeanOnlyDateAndNote(item);
                        }
                    }
                }

            }

        }


        LinkedHashSet<String> hashSet = new LinkedHashSet<>(sendEmpList);
        ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);
        List<String> userIdList = null;
        if (listWithoutDuplicates != null && listWithoutDuplicates.size() != 0) {
            userIdList = projectServa.getUserIdByEmpNos(listWithoutDuplicates);
            userIdList.add("|WangHongMei");
            net.sf.json.JSONObject insertdata = new net.sf.json.JSONObject();
            net.sf.json.JSONObject content = new net.sf.json.JSONObject();
            content.put("content", sb.toString());
            insertdata.put("touser", org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
            insertdata.put("msgtype", "text");
            insertdata.put("agentid", 1000003);
            insertdata.put("text", content);
            insertdata.put("safe", 0);
            QYWX.SendMsgtoBody(insertdata);
        }

    }


    @ResponseBody
    @RequestMapping("/saveOrderItemMore")
    public void saveOrderItemMore(String pho, HttpServletResponse response) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            net.sf.json.JSONObject insertdata = new net.sf.json.JSONObject();
            net.sf.json.JSONObject content = new net.sf.json.JSONObject();
            ProjectHeadOrderItem phoi = JSONUtils.toBean(pho, ProjectHeadOrderItem.class);
            phoi.setZhanCha_Emp(StringUtils.join(phoi.getZhanCha_Emps2(), ","));
            phoi.setOutDraw_Emp(StringUtils.join(phoi.getOutDraw_Emps2(), ","));
            phoi.setProgram_confir_Emp(StringUtils.join(phoi.getProgram_confir_Emps2(), ","));
            phoi.setGiveOrder_Emp(StringUtils.join(phoi.getGiveOrder_Emps2(), ","));
            phoi.setDelivery_Goods_Emp(StringUtils.join(phoi.getDelivery_Goods_Emps2(), ","));
            phoi.setInstall_Emp(StringUtils.join(phoi.getInstall_Emps2(), ","));
            phoi.setYanShou_Emp(StringUtils.join(phoi.getYanShou_Emps2(), ","));
            phoi.setJieSuan_Emp(StringUtils.join(phoi.getJieSuan_Emps2(), ","));
            int flag = 0;
            boolean flagemp2 = false;
            boolean flagemp3 = false;
            boolean flagemp4 = false;
            boolean flagemp5 = false;
            boolean flagemp6 = false;
            boolean flagemp7 = false;
            boolean flagemp8 = false;
            boolean flagemp9 = false;

            List<String> addnameList = null;
            List<String> deletenameList = null;
            phoi.setUpdateDateStr(sdf.format(new Date()));
            ProjectHeadOrder phoa = projectServ.getProjectOrderById(phoi.getOrder_Id());
            ProjectHeadOrderItem phoiOld = projectServ.saveOrderItemMor(0, phoi);
            Map<String, String> map = null;
            Map<String, List<String>> mapEmp = null;
            StringBuilder sb = new StringBuilder("注意:" + phoi.getUserName() + "刚刚更改了您有关的订单:\n" + phoiOld.getOrdeNo() + "\n" + phoiOld.getProjectName()
                    + "\n" + phoa.getCustomerName() + "\n" + phoiOld.getProduct_Name() + "\n交期:" + (phoiOld.getDelivery_DateStr() == null ? "" : phoiOld.getDelivery_DateStr()));

            //接单
            if (phoi.getGetOrder_Date_PlanStr() != null && phoiOld.getGetOrder_Date_PlanStr() != null)
                if (phoi.getGetOrder_Date_PlanStrChange() == 1) {
                    flag = 1;
                    map = DateUtil.spareDates(phoi.getGetOrder_Date_PlanStr(), phoiOld.getGetOrder_Date_PlanStr());
                    sb.append("\n接单时间" + map.get("isDelay") + map.get("howdays"));
                }

            //勘察
            if (phoi.getZhanCha_Date_PlanStr() != null && phoiOld.getZhanCha_Date_PlanStr() != null)
                if (phoi.getZhanCha_Date_PlanStrChange() == 1) {
                    if (flag == 0)
                        flag = 2;
                    map = DateUtil.spareDates(phoi.getZhanCha_Date_PlanStr(), phoiOld.getZhanCha_Date_PlanStr());
                    sb.append("\n勘查计划时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getZhanCha_Date_AccuStr() != null)
                if (phoi.getZhanCha_Date_AccuStrChange() == 1) {
                    if (flag == 0)
                        flag = 2;
                    map = DateUtil.spareDates(phoi.getZhanCha_Date_AccuStr(), phoi.getZhanCha_Date_PlanStr());
                    sb.append("\n勘查实际时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getZhanCha_Emp() != null)
                if (!phoi.getZhanCha_Emp().equals(phoiOld.getZhanCha_Emp())) {
                    mapEmp = StringUtil.compareEmpNOs(phoi.getZhanCha_Emps(), phoiOld.getZhanCha_Emps());
                    if (mapEmp.get("addEmp") != null) {
                        flagemp2 = true;
                        addnameList = projectServ.getNamesByEmpNos(mapEmp.get("addEmp"));
                        sb.append("\n勘查人员新增了:" + StringUtils.join(addnameList, ","));
                    }
                    if (mapEmp.get("deleteEmp") != null) {
                        flagemp2 = true;
                        deletenameList = projectServ.getNamesByEmpNos(mapEmp.get("deleteEmp"));
                        sb.append("\n勘查人员取消了:" + StringUtils.join(deletenameList, ","));
                    }
                }


            //出图
            if (phoi.getOutDraw_Date_PlanStr() != null && phoiOld.getOutDraw_Date_PlanStr() != null)
                if (phoi.getOutDraw_Date_PlanStrChange() == 1) {
                    if (flag == 0)
                        flag = 3;
                    map = DateUtil.spareDates(phoi.getOutDraw_Date_PlanStr(), phoiOld.getOutDraw_Date_PlanStr());
                    sb.append("\n出图计划时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getOutDraw_Date_AccuStr() != null)
                if (phoi.getOutDraw_Date_AccuStrChange() == 1) {
                    if (flag == 0)
                        flag = 3;
                    map = DateUtil.spareDates(phoi.getOutDraw_Date_AccuStr(), phoi.getOutDraw_Date_PlanStr());
                    sb.append("\n出图实际时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getOutDraw_Emp() != null)
                if (!phoi.getOutDraw_Emp().equals(phoiOld.getOutDraw_Emp())) {
                    mapEmp = StringUtil.compareEmpNOs(phoi.getOutDraw_Emps(), phoiOld.getOutDraw_Emps());
                    if (mapEmp.get("addEmp") != null) {
                        flagemp3 = true;
                        addnameList = projectServ.getNamesByEmpNos(mapEmp.get("addEmp"));
                        sb.append("\n出图人员新增了:" + StringUtils.join(addnameList, ","));
                    }
                    if (mapEmp.get("deleteEmp") != null) {
                        flagemp3 = true;
                        deletenameList = projectServ.getNamesByEmpNos(mapEmp.get("deleteEmp"));
                        sb.append("\n出图人员取消了:" + StringUtils.join(deletenameList, ","));
                    }
                }


            //方案
            if (phoi.getProgram_confir_Date_PlanStr() != null && phoiOld.getProgram_confir_Date_PlanStr() != null)
                if (phoi.getProgram_confir_Date_PlanStrChange() == 1) {
                    if (flag == 0)
                        flag = 4;
                    map = DateUtil.spareDates(phoi.getProgram_confir_Date_PlanStr(), phoiOld.getProgram_confir_Date_PlanStr());
                    sb.append("\n方案确认计划时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getProgram_confir_Date_AccuStr() != null)
                if (phoi.getProgram_confir_Date_AccuStrChange() == 1) {
                    if (flag == 0)
                        flag = 4;
                    map = DateUtil.spareDates(phoi.getProgram_confir_Date_AccuStr(), phoi.getProgram_confir_Date_PlanStr());
                    sb.append("\n方案确认实际时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getProgram_confir_Emp() != null)
                if (!phoi.getProgram_confir_Emp().equals(phoiOld.getProgram_confir_Emp())) {
                    mapEmp = StringUtil.compareEmpNOs(phoi.getProgram_confir_Emps(), phoiOld.getProgram_confir_Emps());
                    if (mapEmp.get("addEmp") != null) {
                        flagemp4 = true;
                        addnameList = projectServ.getNamesByEmpNos(mapEmp.get("addEmp"));
                        sb.append("\n方案确认人员新增了:" + StringUtils.join(addnameList, ","));
                    }
                    if (mapEmp.get("deleteEmp") != null) {
                        flagemp4 = true;
                        deletenameList = projectServ.getNamesByEmpNos(mapEmp.get("deleteEmp"));
                        sb.append("\n方案确认人员取消了:" + StringUtils.join(deletenameList, ","));
                    }
                }


            //下单
            if (phoi.getGiveOrder_Date_PlanStr() != null && phoiOld.getGiveOrder_Date_PlanStr() != null)
                if (phoi.getGiveOrder_Date_PlanStrChange() == 1) {
                    if (flag == 0)
                        flag = 5;
                    map = DateUtil.spareDates(phoi.getGiveOrder_Date_PlanStr(), phoiOld.getGiveOrder_Date_PlanStr());
                    sb.append("\n下单计划时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getGiveOrder_Date_AccuStr() != null)
                if (phoi.getGiveOrder_Date_AccuStrChange() == 1) {
                    if (flag == 0)
                        flag = 5;
                    map = DateUtil.spareDates(phoi.getGiveOrder_Date_AccuStr(), phoi.getGiveOrder_Date_PlanStr());
                    sb.append("\n下单实际时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getGiveOrder_Emp() != null)
                if (!phoi.getGiveOrder_Emp().equals(phoiOld.getGiveOrder_Emp())) {
                    mapEmp = StringUtil.compareEmpNOs(phoi.getGiveOrder_Emps(), phoiOld.getGiveOrder_Emps());
                    if (mapEmp.get("addEmp") != null) {
                        flagemp5 = true;
                        addnameList = projectServ.getNamesByEmpNos(mapEmp.get("addEmp"));
                        sb.append("\n下单人员新增了:" + StringUtils.join(addnameList, ","));
                    }
                    if (mapEmp.get("deleteEmp") != null) {
                        flagemp5 = true;
                        deletenameList = projectServ.getNamesByEmpNos(mapEmp.get("deleteEmp"));
                        sb.append("\n下单人员取消了:" + StringUtils.join(deletenameList, ","));
                    }
                }


            //交货
            if (phoi.getDelivery_Goods_Date_PlanStr() != null && phoiOld.getDelivery_Goods_Date_PlanStr() != null)
                if (phoi.getDelivery_Goods_Date_PlanStrChange() == 1) {
                    if (flag == 0)
                        flag = 6;
                    map = DateUtil.spareDates(phoi.getDelivery_Goods_Date_PlanStr(), phoiOld.getDelivery_Goods_Date_PlanStr());
                    sb.append("\n交货计划时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getDelivery_Goods_Date_AccuStr() != null)
                if (phoi.getDelivery_Goods_Date_AccuStrChange() == 1) {
                    if (flag == 0)
                        flag = 6;
                    map = DateUtil.spareDates(phoi.getDelivery_Goods_Date_AccuStr(), phoi.getDelivery_Goods_Date_PlanStr());
                    sb.append("\n交货实际时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getDelivery_Goods_Emp() != null)
                if (!phoi.getDelivery_Goods_Emp().equals(phoiOld.getDelivery_Goods_Emp())) {
                    mapEmp = StringUtil.compareEmpNOs(phoi.getDelivery_Goods_Emps(), phoiOld.getDelivery_Goods_Emps());
                    if (mapEmp.get("addEmp") != null) {
                        flagemp6 = true;
                        addnameList = projectServ.getNamesByEmpNos(mapEmp.get("addEmp"));
                        sb.append("\n交货人员新增了:" + StringUtils.join(addnameList, ","));
                    }
                    if (mapEmp.get("deleteEmp") != null) {
                        flagemp6 = true;
                        deletenameList = projectServ.getNamesByEmpNos(mapEmp.get("deleteEmp"));
                        sb.append("\n交货人员取消了:" + StringUtils.join(deletenameList, ","));
                    }
                }


            //安装
            if (phoi.getInstall_Date_PlanStr() != null && phoiOld.getInstall_Date_PlanStr() != null)
                if (phoi.getInstall_Date_PlanStrChange() == 1) {
                    if (flag == 0)
                        flag = 7;
                    map = DateUtil.spareDates(phoi.getInstall_Date_PlanStr(), phoiOld.getInstall_Date_PlanStr());
                    sb.append("\n安装计划时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getInstall_Date_AccuStr() != null)
                if (phoi.getInstall_Date_AccuStrChange() == 1) {
                    if (flag == 0)
                        flag = 7;
                    map = DateUtil.spareDates(phoi.getInstall_Date_AccuStr(), phoi.getInstall_Date_PlanStr());
                    sb.append("\n安装实际时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getInstall_Emp() != null)
                if (!phoi.getInstall_Emp().equals(phoiOld.getInstall_Emp())) {
                    mapEmp = StringUtil.compareEmpNOs(phoi.getInstall_Emps(), phoiOld.getInstall_Emps());
                    if (mapEmp.get("addEmp") != null) {
                        flagemp7 = true;
                        addnameList = projectServ.getNamesByEmpNos(mapEmp.get("addEmp"));
                        sb.append("\n安装人员新增了:" + StringUtils.join(addnameList, ","));
                    }
                    if (mapEmp.get("deleteEmp") != null) {
                        flagemp7 = true;
                        deletenameList = projectServ.getNamesByEmpNos(mapEmp.get("deleteEmp"));
                        sb.append("\n安装人员取消了:" + StringUtils.join(deletenameList, ","));
                    }
                }


            //验收
            if (phoi.getYanShou_Date_PlanStr() != null && phoiOld.getYanShou_Date_PlanStr() != null)
                if (phoi.getYanShou_Date_PlanStrChange() == 1) {
                    if (flag == 0)
                        flag = 8;
                    map = DateUtil.spareDates(phoi.getYanShou_Date_PlanStr(), phoiOld.getYanShou_Date_PlanStr());
                    sb.append("\n验收计划时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getYanShou_Date_AccuStr() != null)
                if (phoi.getYanShou_Date_AccuStrChange() == 1) {
                    if (flag == 0)
                        flag = 8;
                    map = DateUtil.spareDates(phoi.getYanShou_Date_AccuStr(), phoi.getYanShou_Date_PlanStr());
                    sb.append("\n验收实际时间" + map.get("isDelay") + map.get("howdays"));
                }

            if (phoi.getYanShou_Emp() != null)
                if (!phoi.getYanShou_Emp().equals(phoiOld.getYanShou_Emp())) {
                    mapEmp = StringUtil.compareEmpNOs(phoi.getYanShou_Emps(), phoiOld.getYanShou_Emps());
                    if (mapEmp.get("addEmp") != null) {
                        flagemp8 = true;
                        addnameList = projectServ.getNamesByEmpNos(mapEmp.get("addEmp"));
                        sb.append("\n验收人员新增了:" + StringUtils.join(addnameList, ","));
                    }
                    if (mapEmp.get("deleteEmp") != null) {
                        flagemp8 = true;
                        deletenameList = projectServ.getNamesByEmpNos(mapEmp.get("deleteEmp"));
                        sb.append("\n验收人员取消了:" + StringUtils.join(deletenameList, ","));
                    }
                }

            if (map != null || mapEmp != null) {
                StringBuilder sbb = new StringBuilder();
                List<String> sendUsers = new ArrayList<String>();
                if (flag == 1) {
                    if (phoi.getZhanCha_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getZhanCha_Emps()));
                    if (phoi.getOutDraw_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getOutDraw_Emps()));
                    if (phoi.getProgram_confir_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getProgram_confir_Emps()));
                    if (phoi.getGiveOrder_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getGiveOrder_Emps()));
                    if (phoi.getDelivery_Goods_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getDelivery_Goods_Emps()));
                    if (phoi.getInstall_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getInstall_Emps()));
                    if (phoi.getYanShou_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getYanShou_Emps()));
                    if (phoi.getJieSuan_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getJieSuan_Emps()));
                } else if (flag == 2) {
                    if (phoi.getOutDraw_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getOutDraw_Emps()));
                    if (phoi.getProgram_confir_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getProgram_confir_Emps()));
                    if (phoi.getGiveOrder_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getGiveOrder_Emps()));
                    if (phoi.getDelivery_Goods_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getDelivery_Goods_Emps()));
                    if (phoi.getInstall_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getInstall_Emps()));
                    if (phoi.getYanShou_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getYanShou_Emps()));
                    if (phoi.getJieSuan_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getJieSuan_Emps()));
                } else if (flag == 3) {
                    if (phoi.getProgram_confir_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getProgram_confir_Emps()));
                    if (phoi.getGiveOrder_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getGiveOrder_Emps()));
                    if (phoi.getDelivery_Goods_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getDelivery_Goods_Emps()));
                    if (phoi.getInstall_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getInstall_Emps()));
                    if (phoi.getYanShou_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getYanShou_Emps()));
                    if (phoi.getJieSuan_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getJieSuan_Emps()));
                } else if (flag == 4) {
                    if (phoi.getGiveOrder_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getGiveOrder_Emps()));
                    if (phoi.getDelivery_Goods_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getDelivery_Goods_Emps()));
                    if (phoi.getInstall_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getInstall_Emps()));
                    if (phoi.getYanShou_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getYanShou_Emps()));
                    if (phoi.getJieSuan_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getJieSuan_Emps()));
                } else if (flag == 5) {
                    if (phoi.getDelivery_Goods_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getDelivery_Goods_Emps()));
                    if (phoi.getInstall_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getInstall_Emps()));
                    if (phoi.getYanShou_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getYanShou_Emps()));
                    if (phoi.getJieSuan_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getJieSuan_Emps()));
                } else if (flag == 6) {
                    if (phoi.getInstall_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getInstall_Emps()));
                    if (phoi.getYanShou_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getYanShou_Emps()));
                    if (phoi.getJieSuan_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getJieSuan_Emps()));
                } else if (flag == 7) {
                    if (phoi.getYanShou_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getYanShou_Emps()));
                    if (phoi.getJieSuan_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getJieSuan_Emps()));
                } else if (flag == 8) {
                    if (phoi.getJieSuan_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getJieSuan_Emps()));
                }

                if (flagemp2) {
                    if (phoi.getZhanCha_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getZhanCha_Emps()));
                    if (phoiOld.getZhanCha_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoiOld.getZhanCha_Emps()));
                }
                if (flagemp3) {
                    if (phoi.getOutDraw_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getOutDraw_Emps()));
                    if (phoiOld.getOutDraw_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoiOld.getOutDraw_Emps()));
                }
                if (flagemp4) {
                    if (phoi.getProgram_confir_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getProgram_confir_Emps()));
                    if (phoiOld.getProgram_confir_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoiOld.getProgram_confir_Emps()));
                }
                if (flagemp5) {
                    if (phoi.getGiveOrder_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getGiveOrder_Emps()));
                    if (phoiOld.getGiveOrder_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoiOld.getGiveOrder_Emps()));
                }
                if (flagemp6) {
                    if (phoi.getDelivery_Goods_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getDelivery_Goods_Emps()));
                    if (phoiOld.getDelivery_Goods_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoiOld.getDelivery_Goods_Emps()));
                }
                if (flagemp7) {
                    if (phoi.getInstall_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getInstall_Emps()));
                    if (phoiOld.getInstall_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoiOld.getInstall_Emps()));
                }
                if (flagemp8) {
                    if (phoi.getYanShou_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getYanShou_Emps()));
                    if (phoiOld.getYanShou_Emps() != null)
                        sendUsers.addAll(Arrays.asList(phoiOld.getYanShou_Emps()));
                }
                if (flagemp9) {
                    if (phoi.getJieSuan_Emps2() != null)
                        sendUsers.addAll(Arrays.asList(phoi.getJieSuan_Emps2()));
                    if (phoi.getJieSuan_Emps2() != null)
                        sendUsers.addAll(Arrays.asList(phoiOld.getJieSuan_Emps2()));
                }


                LinkedHashSet<String> hashSet = new LinkedHashSet<>(sendUsers);
                List<String> userIdList = new ArrayList<String>();
                ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);
                if (listWithoutDuplicates != null && listWithoutDuplicates.size() > 0)
                    userIdList = projectServ.getUserIdByEmpNos(listWithoutDuplicates);
                userIdList.add("WangHongMei");
                sbb.append(org.apache.commons.lang.StringUtils.join(userIdList.toArray(), "|"));
                content.put("content", sb.toString());
                insertdata.put("touser", sbb.toString());
                insertdata.put("msgtype", "text");
                insertdata.put("agentid", 1000003);
                insertdata.put("text", content);
                insertdata.put("safe", 0);
                QYWX.SendMsgtoBody(insertdata);
            }

            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/saveOrderItemMoreOld")
    public void saveOrderItemMoreOld(String pho, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem phoi = JSONUtils.toBean(pho, ProjectHeadOrderItem.class);
            phoi.setProgram_confir_Emp(StringUtils.join(phoi.getProgram_confir_Emps2(), ","));
            phoi.setGiveOrder_Emp(StringUtils.join(phoi.getGiveOrder_Emps2(), ","));
            phoi.setDelivery_Goods_Emp(StringUtils.join(phoi.getDelivery_Goods_Emps2(), ","));
            phoi.setInstall_Emp(StringUtils.join(phoi.getInstall_Emps2(), ","));
            phoi.setYanShou_Emp(StringUtils.join(phoi.getYanShou_Emps2(), ","));
            phoi.setJieSuan_Emp(StringUtils.join(phoi.getJieSuan_Emps2(), ","));
            projectServ.saveOrderItemMorOld(0, phoi);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getAllProvinceAndPHO")
    public void getAllProvinceAndPHO(String projectName, String customerName, String orderNo, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrder pho = projectServ.getProjectOrderByOrderNo(projectName, customerName, orderNo);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(pho);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getAlertSet")
    public void getAlertSet(HttpServletResponse response) throws Exception {
        try {
            AlertSet ph = projectServ.getAlertSet();
            List<DaysSet> phn = projectServ.getDaysSetNew();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ph", ph);
            map.put("phn", phn.get(0));
            map.put("pho", phn.get(1));
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(map);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getUserInfo")
    public void getUserInfo(String code, HttpServletResponse response, HttpServletRequest request) throws Exception {
        try {
            pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
            jedis = pool.getResource();
            NetWorkHelper netHelper = new NetWorkHelper();
            HttpSession session = request.getSession();
            // String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/service/miniprogram/jscode2session?access_token=%s&js_code=%s&grant_type=authorization_code", jedis.get(Constants.accessTokenqywx), code);
            String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/miniprogram/jscode2session?access_token=%s&js_code=%s&grant_type=authorization_code", jedis.get(Constants.accessTokenqywxtx), code);
            String result = netHelper.getHttpsResponse(Url, "");
            JSONObject json = JSON.parseObject(result);
            String session_key = json.getString("session_key");
            String userid = json.getString("userid");
            session.setAttribute("session_key", session_key);
            String name = projectServ.getUserNameByUserId(userid);
            UserInfo info = new UserInfo();
            info.setUserid(userid);
            info.setUserName(name);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(info);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/getAllAlertPositions")
    public void getAllAlertPositions(HttpServletResponse response) throws Exception {
        try {
            List<Position> pho = projectServ.getAllAlertPositions();
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(pho);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/updateOrderNoRepeat")
    public void updateOrderNoRepeat(String pho, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrder phoi = JSONUtils.toBean(pho, ProjectHeadOrder.class);
            int isExsit = projectServ.getCustomerNameByNameAndOrderNo(phoi);
            if (isExsit != 0) {
                projectServ.updateOrderNoRepeat(phoi);
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(isExsit);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/updateOrderNoRepeat22")
    public void updateOrderNoRepeat22(String pho, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem phoi = JSONUtils.toBean(pho, ProjectHeadOrderItem.class);
            phoi.setSalorsStr(StringUtils.join(phoi.getSalors2(), ","));
            phoi.setGendansStr(StringUtils.join(phoi.getGendans2(), ","));
            projectServ.updateProjectHeadOrderItem(phoi);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/saveHereMoneyBYItemId")
    public void saveHereMoneyBYItemId(Integer itemId, Double hereMoney, String fapiaoNo, String remark, HttpServletResponse response) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStart = sdf.format(new Date());
            ProjectItemMoneyRecord phoi = new ProjectItemMoneyRecord();
            phoi.setItem_id(itemId);
            phoi.setHereMoney(hereMoney);
            phoi.setFapiaoNo(fapiaoNo);
            phoi.setRemark(remark);
            phoi.setDateStr(dateStart);
            projectServ.saveHereMoneyBYItemId(phoi);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/deleteProjectRecord")
    public void deleteProjectRecord(Integer id, Integer itemId, Double hereMoney, HttpServletResponse response) throws Exception {
        try {
            projectServ.deleteProjectRecordById(id);
            projectServ.updatePHOIMoney(itemId, hereMoney);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping("/getTotalProjectOrderMoneyRecordByNo")
    public void getTotalProjectOrderMoneyRecordByNo(String itemId, HttpServletResponse response) throws Exception {
        try {
            List<ProjectItemMoneyRecord> piList = projectServ.getTotalProjectOrderMoneyRecordByNo(itemId);

            for (int a = 0; a < piList.size(); a++) {
                File f = new File(this.finalDirPath + "wx/" + piList.get(a).getImageUrl());
                BufferedImage bi = ImageIO.read(f);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bi, "jpg", baos);
                byte[] bytes = baos.toByteArray();
                baos.close();
                piList.get(a).setImageUrl(encoder.encodeBuffer(bytes).trim());
            }

            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(piList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getProjectByName")
    public void getProjectByName(String ph, HttpServletResponse response) throws Exception {
        try {
            ProjectHead phoi = JSONUtils.toBean(ph, ProjectHead.class);
            ProjectHead phh = projectServ.getProjectByName(phoi);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(phh);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/deleteProjectDetai")
    public void deleteProjectDetai(String projectName, String userid, HttpServletResponse response) throws Exception {
        try {
            projectServ.deleteProjectDetai(projectName, userid);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping("/deleteProjectDetaiItem")
    public void deleteProjectDetaiItem(Integer id, String userid, HttpServletResponse response) throws Exception {
        try {
            projectServ.deleteProjectDetaiItem(id, userid);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping("/deleteProjectDetai2")
    public void deleteProjectDetai2(String orderNo, String userid, HttpServletResponse response) throws Exception {
        try {
            projectServ.deleteProjectDetai2(orderNo, userid);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping("/findNameRepeatOrNot2")
    public void findNameRepeatOrNot2(String ph, HttpServletResponse response) throws Exception {
        try {
            ProjectHead phoi = JSONUtils.toBean(ph, ProjectHead.class);
            int count = projectServ.findNameRepeatOrNot2(phoi);
            if (count == 0) {
                projectServ.updateProjectByNameAndRemark(phoi);
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(count);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/getProjectOrderItemByOrderNoAndProductName")
    public void getProjectOrderItemByOrderNoAndProductName(String pho, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem phoi = JSONUtils.toBean(pho, ProjectHeadOrderItem.class);
            ProjectHeadOrderItem item = projectServ.getProjectOrderItemByOrderNoAndProductName(phoi);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(item);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/WXBD")
    public void WXBD(String obj, HttpServletResponse response) throws Exception {
        try {
            WeiXinUsrId phoi = JSONUtils.toBean(obj, WeiXinUsrId.class);
            //ProjectHeadOrderItem item = projectServ.getProjectOrderItemByOrderNoAndProductName(phoi);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/getGenDanItems")
    public void getGenDanItems(HttpServletResponse response) throws Exception {
        try {

            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(null);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getSalorMItems")
    public void getSalorMItems(HttpServletResponse response) throws Exception {
        try {
            List<Employee> item = projectServ.getSalorMItems();
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(item);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getTotalProjectOrderProductRecordByOrderNoProductName")
    public void getTotalProjectOrderProductRecordByOrderNoProductName(String product_Name, String orderNo, String projectName, String customer_Name, HttpServletResponse response) throws Exception {
        try {
            List<ProjectHeadOrderItem> item = projectServ.getHistoryItemByProduct_NameAndOrderNo(product_Name, orderNo, projectName, customer_Name);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(item);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/getProjectItemHistoryById")
    public void getProjectItemHistoryById(Integer id, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem item = projectServ.getProjectItemHistoryById(id);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(item);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/updateProjectItemCheckById")
    public void updateProjectItemCheckById(Integer id, Integer oldstatus, int checked, HttpServletResponse response) throws Exception {
        try {
            projectServ.updateProjectItemCheckById(id, checked);
            sendMessageByCheckOperat(id, oldstatus, checked);
            List<ProjectHeadOrderItem> projectHeadOrderList = projectServ.getTotalProjectOrderITEMByOrderSAll();
            for (int i = 0; i < projectHeadOrderList.size(); i++) {

                projectHeadOrderList.get(i).setGendansStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getDelivery_Goods_Emp()));
                projectHeadOrderList.get(i).setSalorsStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getZhanCha_Emp()));

                if (projectHeadOrderList.get(i).getJieSuan_Date_AccuStr() != null) {
                    projectHeadOrderList.get(i).setProgressCent(100);
                    continue;
                }

                if (projectHeadOrderList.get(i).getYanShou_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((6 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((8 / 9) * 100);
                    }
                    continue;
                }


                if (projectHeadOrderList.get(i).getInstall_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((5 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((7 / 9) * 100);
                    }
                    continue;
                }


                if (projectHeadOrderList.get(i).getDelivery_Goods_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((4 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((6 / 9) * 100);
                    }
                    continue;
                }

                if (projectHeadOrderList.get(i).getGiveOrder_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((3 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((5 / 9) * 100);
                    }
                    continue;
                }


                if (projectHeadOrderList.get(i).getProgram_confir_Date_AccuStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((2 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((4 / 9) * 100);
                    }
                    continue;
                }
                if (projectHeadOrderList.get(i).getNewOrOld() == 1) {
                    if (projectHeadOrderList.get(i).getOutDraw_Date_AccuStr() != null) {
                        projectHeadOrderList.get(i).setProgressCent((3 / 9) * 100);
                        continue;
                    }

                    if (projectHeadOrderList.get(i).getZhanCha_Date_AccuStr() != null) {
                        projectHeadOrderList.get(i).setProgressCent((2 / 9) * 100);
                        continue;
                    }
                }

                if (projectHeadOrderList.get(i).getGetOrder_Date_PlanStr() != null) {
                    if (projectHeadOrderList.get(i).getNewOrOld() == 0) {
                        projectHeadOrderList.get(i).setProgressCent((1 / 7) * 100);
                    } else {
                        projectHeadOrderList.get(i).setProgressCent((1 / 9) * 100);
                    }
                    continue;
                } else {
                    projectHeadOrderList.get(i).setProgressCent(0);
                }
            }
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(projectHeadOrderList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/getTotalProjectOrderITEMByOrderSAll")
    public void getTotalProjectOrderITEMByOrderSAll(String userid, HttpServletResponse response) throws Exception {
        try {
            logger.error("i love you  mat");
            List<ProjectHeadOrderItem> projectHeadOrderList = projectServ.getTotalProjectOrderITEMByOrderSAll();
            for (int i = 0; i < projectHeadOrderList.size(); i++) {
                projectHeadOrderList.get(i).setGendansStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getDelivery_Goods_Emp()));
                logger.error(projectHeadOrderList.get(i).getDelivery_Goods_Emp() + " ****");
                projectHeadOrderList.get(i).setSalorsStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getZhanCha_Emp()));
                logger.error(projectHeadOrderList.get(i).getZhanCha_Emp() + " ****");

            }
            logger.error("end =====");

            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(projectHeadOrderList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.fillInStackTrace());
            logger.error(e.fillInStackTrace());
            throw e;
        }
    }


    public void sendMessageByCheckOperat(Integer id, Integer oldstatus, Integer checked) throws
            Exception {
        net.sf.json.JSONObject insertdata = new net.sf.json.JSONObject();
        net.sf.json.JSONObject content = new net.sf.json.JSONObject();
        IProjectServ testDomainMapper = SpringUtil.getBean(IProjectServ.class);
        ProjectHeadOrderItem item = null;
        StringBuilder sb = new StringBuilder();
        if (oldstatus != checked) {
            item = testDomainMapper.getProjectOrderItemById(id);
            item.setChecked(oldstatus);
            sb.append(item.getName() + ":您的订单:\n" + item.getOrdeNo() + "\n" + item.getProjectName() + "\n" + item.getProduct_Name() + "\n" +
                    "原审核状态为:" + item.getCheckedStr() + ",\n现已被凡总");
            item.setChecked(checked);
            sb.append(item.getCheckedStr());
        }
        content.put("content", sb.toString());
        insertdata.put("touser", item.getUserid() + "|" + "|WangHongMei");
        insertdata.put("msgtype", "text");
        insertdata.put("agentid", 1000003);
        insertdata.put("text", content);
        insertdata.put("safe", 0);
        QYWX.SendMsgtoBody(insertdata);
    }


    @ResponseBody
    @RequestMapping("/getProjectBySearch")
    public void getProjectBySearch(String projectName, HttpServletResponse response) throws Exception {
        try {
            List<ProjectHead> totalLianSuo = projectServ.getProjectBySearch(projectName);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(totalLianSuo);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getProjectRecordById")
    public void getProjectRecordById(Integer id, HttpServletResponse response) throws Exception {
        try {
            ProjectItemMoneyRecord pho = projectServ.getProjectRecordById(id);
            File f = new File(this.finalDirPath + "wx/" + pho.getImageUrl());
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            baos.close();
            pho.setImageUrl(encoder.encodeBuffer(bytes).trim());
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(pho);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getPicTureByteById")
    public void getPicTureByteById(Integer id, HttpServletResponse response) throws Exception {
        try {
            ProjectItemMoneyRecord pho = projectServ.getProjectRecordById(id);
            File f = new File(this.finalDirPath + "wx/" + pho.getImageUrl());
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            baos.close();
            pho.setImageUrl(encoder.encodeBuffer(bytes).trim());
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(pho.getImageUrl());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> upload(Integer itemId, Double hereMoney, String fapiaoNo, String remark, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStart = sdf.format(new Date());
        ProjectItemMoneyRecord phoi = new ProjectItemMoneyRecord();
        phoi.setItem_id(itemId);
        phoi.setHereMoney(hereMoney);
        phoi.setFapiaoNo(fapiaoNo);
        phoi.setRemark(remark);
        phoi.setDateStr(dateStart);

        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = req.getFile("file");
        String realPath = this.finalDirPath + "wx/";//服务器存放图片地址
        try {
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            String newPath = System.currentTimeMillis() + "" + (int) (1 + Math.random() * (10000 - 1 + 1)) + ".jpg";//图片名称是毫秒数加1-10000的随机数
            File file = new File(realPath, newPath);
            multipartFile.transferTo(file);
            phoi.setImageUrl(newPath);
            projectServ.saveHereMoneyBYItemId(phoi);
            return rtnParam(0, file.getPath());    // 返回图片上传到服务器上的地址
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return rtnParam(0, "error");     //方法错误   rtnParam是继承base类中的方法
    }


    @ResponseBody
    @RequestMapping("/updateHereMoneyBYId")
    public void updateHereMoneyBYId(String pho, HttpServletResponse response) throws Exception {
        try {
            ProjectItemMoneyRecord phoi = JSONUtils.toBean(pho, ProjectItemMoneyRecord.class);
            projectServ.updateProjectRecordByBean(phoi);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(0);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping("/getNameByUserId")
    public void getNameByUserId(String pho, HttpServletResponse response) throws Exception {
        try {
            String userName = projectServ.getUserNameByUserId(pho);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(userName);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/exportPHOIByCondition", method = RequestMethod.GET)
    public void exportPHOIByCondition(ProjectHeadOrderItem kqBean, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws
            Exception {
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

        try {
            String filename = new Date().getTime() + ".xls";
            kqBean.setPageSize(10000);
            List<ProjectHeadOrderItem> financeImportDataList = projectServ.queryProjectOrderItemByCondition(kqBean);
            new PJExcelUtil().writePJdataTOExcel(financeImportDataList, this.finalDirPath + "linshi/", filename);
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryProjectOrderItemByCondition", method = RequestMethod.GET)
    public ModelAndView queryProjectOrderItemByCondition(ProjectHeadOrderItem kqBean, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            ModelAndView mav = new ModelAndView("projectcenterorder");
            ProjectHeadOrderItem orderHead = new ProjectHeadOrderItem();
            List<Employee> salorList = projectServ.findAllProjectSalorByDeptName1();
            List<String> projectNameList = projectServ.getAllProjectName();
            List<String> orderNoList = projectServ.getAllOrderNoList();
            List<String> customerNameList = projectServ.getAllCustomerName();
            List<ProjectHeadOrderItem> financeImportDataList = projectServ.queryProjectOrderItemByCondition(kqBean);
            int recordCount = projectServ.queryProjectOrderItemByConditionCount(kqBean);
            int maxPage = recordCount % kqBean.getPageSize() == 0 ? recordCount / kqBean.getPageSize() : recordCount / kqBean.getPageSize() + 1;
            orderHead.setMaxPage(maxPage);
            orderHead.setRecordCount(recordCount);
            orderHead.setCurrentPage(kqBean.getCurrentPage());
            mav.addObject("salorList", salorList);
            mav.addObject("projectNameList", projectNameList);
            mav.addObject("orderNoList", orderNoList);
            mav.addObject("customerNameList", customerNameList);
            mav.addObject("orderList", financeImportDataList);
            mav.addObject("flag", 0);
            mav.addObject("orderHead", orderHead);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toProjectOrderPage")
    public ModelAndView toProjectOrderPage(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("projectcenterorder");
        ProjectHeadOrderItem orderHead = new ProjectHeadOrderItem();
        List<Employee> salorList = projectServ.findAllProjectSalorByDeptName1();
        List<String> projectNameList = projectServ.getAllProjectName();
        List<String> orderNoList = projectServ.getAllOrderNoList();
        List<String> customerNameList = projectServ.getAllCustomerName();

        List<ProjectHeadOrderItem> allItem = projectServ.findAllProjecHOI(orderHead);
        int recordCount = projectServ.findAllProjecHOICount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("projectNameList", projectNameList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("customerNameList", customerNameList);
        mav.addObject("orderList", allItem);
        mav.addObject("flag", 0);
        mav.addObject("orderHead", orderHead);
        return mav;
    }


    @ResponseBody
    @RequestMapping("/tomainpage")
    public ModelAndView tomainpage(HttpSession session) throws Exception {
        try {
            ModelAndView view = new ModelAndView("projectcomputeworkdate");
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/dataInSql", method = RequestMethod.POST)
    public ModelAndView dataInSql(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws
            Exception {
        try {
            ModelAndView view = new ModelAndView("projectcomputeworkdate");
            projectServ.translateTabletoEmployeeBeanProject(file);
            view.addObject("flag", 1);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


}
