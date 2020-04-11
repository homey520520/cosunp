package com.cosun.cosunp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.service.IProjectServ;
import com.cosun.cosunp.tool.JSONUtils;
import com.cosun.cosunp.tool.QYWX;
import com.cosun.cosunp.tool.SpringUtil;
import com.cosun.cosunp.tool.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();


    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;


    @ResponseBody
    @RequestMapping("/getTotalProject")
    public void getTotalProject(String userid, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            List<ProjectHead> totalLianSuo = projectServ.totalProjectNumByEmpNo(userid);
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
    public void getTotalProjectOrderITEMByOrderS(String userid, String orderNo, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            List<ProjectHeadOrderItem> projectHeadOrderList = projectServ.getTotalProjectOrderITEMByOrderS(userid, orderNo);
            for (int i = 0; i < projectHeadOrderList.size(); i++) {
                projectHeadOrderList.get(i).setGendansStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getGendan()));
                projectHeadOrderList.get(i).setSalorsStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getSaleManager()));
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
    public void getTotalProjectOrderITEMMoreByOrderS(String userid, String productName, String orderNo, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem projectHeadOrder = projectServ.getTotalProjectOrderITEMMoreByOrderS(userid, productName, orderNo);
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
            List<China> chinaList = projectServ.getAllMainProvince();
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(chinaList);
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
            ProjectHeadOrder count = projectServ.checkOrderNoRepeat(userId, orderNo);
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
    public void checkProductRepeatForOneOrder(Double hetongMoney, String orderNo, String date, String productName, String totalBao, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem count = projectServ.checkProductRepeatForOneOrder(hetongMoney, orderNo, productName);
            ProjectHeadOrder ph = projectServ.findPHObyOrderNo(orderNo);
            if (count == null)
                projectServ.saveProjectHeadItemByBean(hetongMoney, ph.getId(), productName, totalBao, date);
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
    @RequestMapping("/saveOrderItemMore")
    public void saveOrderItemMore(String moment_list2, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem phoi = JSONUtils.toBean(moment_list2, ProjectHeadOrderItem.class);
            projectServ.saveOrderItemMor(0, phoi);
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
    public void getAllProvinceAndPHO(String orderNo, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrder pho = projectServ.getProjectOrderByOrderNo(orderNo);
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
            phoi.setWeiHuiMoney(phoi.getHetongMoney() - phoi.getHereMoney());
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
            List<Employee> employeeList = projectServ.getGenDanItems();
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(employeeList);
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
    public void getTotalProjectOrderProductRecordByOrderNoProductName(String product_Name, String orderNo, HttpServletResponse response) throws Exception {
        try {
            List<ProjectHeadOrderItem> item = projectServ.getHistoryItemByProduct_NameAndOrderNo(product_Name, orderNo);
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
    public void updateProjectItemCheckById(Integer id, int checked, HttpServletResponse response) throws Exception {
        try {
            projectServ.updateProjectItemCheckById(id, checked);
            List<ProjectHeadOrderItem> projectHeadOrderList = projectServ.getTotalProjectOrderITEMByOrderSAll();
            for (int i = 0; i < projectHeadOrderList.size(); i++) {
                projectHeadOrderList.get(i).setGendansStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getGendan()));
                projectHeadOrderList.get(i).setSalorsStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getSaleManager()));
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
            List<ProjectHeadOrderItem> projectHeadOrderList = projectServ.getTotalProjectOrderITEMByOrderSAll();
            for (int i = 0; i < projectHeadOrderList.size(); i++) {
                projectHeadOrderList.get(i).setGendansStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getGendan()));
                projectHeadOrderList.get(i).setSalorsStr(projectServ.returnNameByEmpNoStr(projectHeadOrderList.get(i).getSaleManager()));
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
    @RequestMapping(value = "/sendMessageToUserId", method = RequestMethod.POST)
    public void sendMessageToUserId() throws
            Exception {
        net.sf.json.JSONObject insertdata = new net.sf.json.JSONObject();
        net.sf.json.JSONObject content = new net.sf.json.JSONObject();
        IProjectServ testDomainMapper = SpringUtil.getBean(IProjectServ.class);
        //List<ProjectHeadOrderItem> itemList = testDomainMapper.getAllitemList();

        content.put("content", "小主们，马上就快吃中饭啦，系统测试发送，不用回复");
        insertdata.put("touser", "WangHongMei|LuoYangHua");
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


}
