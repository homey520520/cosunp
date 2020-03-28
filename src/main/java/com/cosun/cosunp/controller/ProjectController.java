package com.cosun.cosunp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.service.IProjectServ;
import com.cosun.cosunp.tool.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/3/23 0023 下午 5:30
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    private static Logger logger = LogManager.getLogger(ProjectController.class);

    @Autowired
    IProjectServ projectServ;
    private String str1;


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
    public void checkOrderNoRepeat(Integer provinceId, String userId, String orderNo, String customerName, String projectName, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrder count = projectServ.checkOrderNoRepeat(userId, orderNo);
            ProjectHead ph = projectServ.findPHbyName(projectName, userId);
            if (count == null)
                projectServ.saveProjectHeadByBean(provinceId, userId, orderNo, customerName, projectName, ph.getId());
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
    public void checkProductRepeatForOneOrder(String orderNo, String date, String productName, String totalBao, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrderItem count = projectServ.checkProductRepeatForOneOrder(orderNo, productName);
            ProjectHeadOrder ph = projectServ.findPHObyOrderNo(orderNo);
            if (count == null)
                projectServ.saveProjectHeadItemByBean(ph.getId(), productName, totalBao, date);
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
    @RequestMapping("/updateOrderNoRepeat")
    public void updateOrderNoRepeat(String pho, HttpServletResponse response) throws Exception {
        try {
            ProjectHeadOrder phoi = JSONUtils.toBean(pho, ProjectHeadOrder.class);
            projectServ.updateOrderNoRepeat(phoi);
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
