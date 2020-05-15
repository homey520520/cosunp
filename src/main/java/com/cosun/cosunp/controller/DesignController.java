package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IDesignServ;
import com.cosun.cosunp.service2.IKingSoftStoreServ;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/5/4  上午 11:08
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Controller
@RequestMapping("/design")
public class DesignController {

    private static Logger logger = LogManager.getLogger(DesignController.class);

    @Autowired
    IDesignServ IDesignServ;

    @Autowired
    IKingSoftStoreServ IKingSoftStoreServ;


    private static JedisPool pool;
    private static Jedis jedis;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;


    @ResponseBody
    @RequestMapping(value = "/queryMaterialSpecifiByCondition", method = RequestMethod.POST)
    public void queryMaterialSpecifiByCondition(@RequestBody(required = true) String materiName, HttpSession session, HttpServletResponse response) throws
            Exception {
        try {
            List<DesignMaterialHeadProductItem> item = IKingSoftStoreServ.queryMaterialSpecifiByCondition(materiName);
            ObjectMapper x = new ObjectMapper();
            String str1 = x.writeValueAsString(item);
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
    @RequestMapping(value = "/deleteOrderItemByheadIdItem")
    public ModelAndView deleteOrderItemByheadIdItem(Integer id, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("designmaterialneedHPITEM");
        //IDesignServ.deleteOrderItemByheadIdItem(id);
        DesignMaterialHeadProductItem orderHead = new DesignMaterialHeadProductItem();
        List<String> customerNoList = IDesignServ.getAllOrderNo();
        List<String> productNoList = IDesignServ.getAllproductNoList();
        List<String> materialSpeciList = IDesignServ.getAllmaterialSpeciList();
        List<String> pmaterialNameList = IDesignServ.getAllmaterialNameList();
        List<String> mateiralNoList = IDesignServ.getAllmateiralNoList();
        List<DesignMaterialHeadProductItem> orderList = IDesignServ.getAllDMHPI(orderHead);
        List<DesignMaterialHeadProductItem> item = IKingSoftStoreServ.queryMaterialSpecifiByCondition("");
        JSONArray salorEmpList1 = JSONArray.fromObject(item.toArray());
        int recordCount = IDesignServ.getAllDMHPICount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("orderHead", orderHead);
        mav.addObject("orderList", orderList);
        mav.addObject("customerNoList", customerNoList);
        mav.addObject("productNoList", productNoList);
        mav.addObject("materialSpeciList", materialSpeciList);
        mav.addObject("pmaterialNameList", pmaterialNameList);
        mav.addObject("mateiralNoList", mateiralNoList);
        mav.addObject("flag", 2);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/toHeadProductItem")
    public ModelAndView toHeadProductItem(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("designmaterialneedHPITEM");
        DesignMaterialHeadProductItem orderHead = new DesignMaterialHeadProductItem();
        List<String> customerNoList = IDesignServ.getAllOrderNo();
        List<String> productNoList = IDesignServ.getAllproductNoList();
        List<String> materialSpeciList = IDesignServ.getAllmaterialSpeciList();
        List<String> pmaterialNameList = IDesignServ.getAllmaterialNameList();
        List<String> mateiralNoList = IDesignServ.getAllmateiralNoList();
        List<DesignMaterialHeadProductItem> orderList = IDesignServ.getAllDMHPI(orderHead);
//        List<DesignMaterialHeadProductItem> item = IKingSoftStoreServ.queryMaterialSpecifiByCondition("");
//        JSONArray salorEmpList1 = JSONArray.fromObject(item.toArray());
        int recordCount = IDesignServ.getAllDMHPICount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("orderHead", orderHead);
        mav.addObject("orderList", orderList);
        mav.addObject("customerNoList", customerNoList);
        mav.addObject("productNoList", productNoList);
        mav.addObject("materialSpeciList", materialSpeciList);
        mav.addObject("pmaterialNameList", pmaterialNameList);
        mav.addObject("mateiralNoList", mateiralNoList);
//        mav.addObject("salorEmpListA", salorEmpList1);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/toHeadProduct")
    public ModelAndView toHeadProduct(String customerNo, HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("designmaterialneedHP");
        DesignMaterialHeadProduct orderHead = new DesignMaterialHeadProduct();
        List<String> orderNoList = IDesignServ.getAllOrderNo();
        List<String> productNameList = IDesignServ.getAllproductNameList();
        List<String> productNoList = IDesignServ.getAllproductNoList();
        List<String> drawingNoList = IDesignServ.getAlldrawingNoList();
        orderHead.setCustomerNo(customerNo);
        List<DesignMaterialHeadProduct> orderList = IDesignServ.getAllDMHP(orderHead);
        int recordCount = IDesignServ.getAllDMHPCount(orderHead);
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("orderHead", orderHead);
        mav.addObject("orderList", orderList);
        mav.addObject("productNameList", productNameList);
        mav.addObject("productNoList", productNoList);
        mav.addObject("drawingNoList", drawingNoList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("customerNo", customerNo);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/deleteOrderItemByheadId")
    public ModelAndView deleteOrderItemByheadId(Integer id, HttpSession session) throws Exception {
        try {
            IDesignServ.deleteOrderItemByheadId(id);
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView mav = new ModelAndView("designmaterialneed");
            DesignMaterialHead orderHead = new DesignMaterialHead();
            List<Employee> salorEmpList = IDesignServ.getSalor();
            JSONArray salorEmpList1 = JSONArray.fromObject(salorEmpList.toArray());
            List<String> orderNoList = IDesignServ.getAllOrderNo();
            List<String> orderAreaList = IDesignServ.getAllOrderArea();
            List<Employee> orderMakerList = IDesignServ.getAllMaker();
            List<DesignMaterialHead> orderList = IDesignServ.getAllDMH(orderHead);
            int recordCount = IDesignServ.getAllDMHCount();
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            orderHead.setMaxPage(maxPage);
            orderHead.setRecordCount(recordCount);
            mav.addObject("orderHead", orderHead);
            mav.addObject("salorEmpList", salorEmpList);
            mav.addObject("salorEmpList1", salorEmpList1);
            mav.addObject("orderNoList", orderNoList);
            mav.addObject("orderAreaList", orderAreaList);
            mav.addObject("orderMakerList", orderMakerList);
            mav.addObject("orderList", orderList);
            mav.addObject("userName", userInfo.getUserName());
            mav.addObject("flag", 2);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveSJIHeadPDateToMysql")
    public ModelAndView saveSJIHeadPDateToMysql(DesignMaterialHeadProductItem orderHead, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            int isSave = IDesignServ.saveSJIHeadPDateToMysql(orderHead);
            ModelAndView mav = new ModelAndView("designmaterialneedHPITEM");
            List<String> customerNoList = IDesignServ.getAllOrderNo();
            List<String> productNoList = IDesignServ.getAllproductNoList();
            List<String> materialSpeciList = IDesignServ.getAllmaterialSpeciList();
            List<String> pmaterialNameList = IDesignServ.getAllmaterialNameList();
            List<String> mateiralNoList = IDesignServ.getAllmateiralNoList();
            List<DesignMaterialHeadProductItem> orderList = IDesignServ.getAllDMHPI(orderHead);
            int recordCount = IDesignServ.getAllDMHPICount();
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            orderHead.setMaxPage(maxPage);
            orderHead.setRecordCount(recordCount);
            mav.addObject("orderHead", orderHead);
            mav.addObject("orderList", orderList);
            mav.addObject("customerNoList", customerNoList);
            mav.addObject("productNoList", productNoList);
            mav.addObject("materialSpeciList", materialSpeciList);
            mav.addObject("pmaterialNameList", pmaterialNameList);
            mav.addObject("mateiralNoList", mateiralNoList);
            mav.addObject("flag", isSave);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/deleteOrderItemHByheadId")
    public ModelAndView deleteOrderItemHByheadId(DesignMaterialHeadProduct orderHead, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            IDesignServ.deleteOrderItemHByheadId(orderHead.getId());
            ModelAndView mav = new ModelAndView("designmaterialneedHP");
            List<String> orderNoList = IDesignServ.getAllOrderNo();
            List<String> productNameList = IDesignServ.getAllproductNameList();
            List<String> productNoList = IDesignServ.getAllproductNoList();
            List<String> drawingNoList = IDesignServ.getAlldrawingNoList();
            List<DesignMaterialHeadProduct> orderList = IDesignServ.getAllDMHPByCustomerNo(orderHead);
            int recordCount = IDesignServ.getAllDMHPByCustomerNoCount(orderHead);
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            orderHead.setMaxPage(maxPage);
            orderHead.setRecordCount(recordCount);
            mav.addObject("orderHead", orderHead);
            mav.addObject("orderList", orderList);
            mav.addObject("productNameList", productNameList);
            mav.addObject("productNoList", productNoList);
            mav.addObject("drawingNoList", drawingNoList);
            mav.addObject("flag", 2);
            mav.addObject("orderNoList", orderNoList);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/saveSJHeadPDateToMysql")
    public ModelAndView saveSJHeadPDateToMysql(DesignMaterialHeadProduct orderHead, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            int isSave = IDesignServ.saveSJHeadPDateToMysql(orderHead);
            ModelAndView mav = new ModelAndView("designmaterialneedHP");
            List<String> orderNoList = IDesignServ.getAllOrderNo();
            List<String> productNameList = IDesignServ.getAllproductNameList();
            List<String> productNoList = IDesignServ.getAllproductNoList();
            List<String> drawingNoList = IDesignServ.getAlldrawingNoList();
            List<DesignMaterialHeadProduct> orderList = IDesignServ.getAllDMHPByCustomerNo(orderHead);
            int recordCount = IDesignServ.getAllDMHPByCustomerNoCount(orderHead);
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            orderHead.setMaxPage(maxPage);
            orderHead.setRecordCount(recordCount);
            mav.addObject("orderHead", orderHead);
            mav.addObject("orderList", orderList);
            mav.addObject("productNameList", productNameList);
            mav.addObject("productNoList", productNoList);
            mav.addObject("drawingNoList", drawingNoList);
            mav.addObject("flag", isSave);
            mav.addObject("orderNoList", orderNoList);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/toMaterialNeed")
    public ModelAndView toMaterialNeed(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("designmaterialneed");
        DesignMaterialHead orderHead = new DesignMaterialHead();
        List<Employee> salorEmpList = IDesignServ.getSalor();
        JSONArray salorEmpList1 = JSONArray.fromObject(salorEmpList.toArray());
        List<String> orderNoList = IDesignServ.getAllOrderNo();
        List<String> orderAreaList = IDesignServ.getAllOrderArea();
        List<Employee> orderMakerList = IDesignServ.getAllMaker();
        List<DesignMaterialHead> orderList = IDesignServ.getAllDMH(orderHead);
        int recordCount = IDesignServ.getAllDMHCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("orderHead", orderHead);
        mav.addObject("salorEmpList", salorEmpList);
        mav.addObject("salorEmpList1", salorEmpList1);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("orderAreaList", orderAreaList);
        mav.addObject("orderMakerList", orderMakerList);
        mav.addObject("orderList", orderList);
        mav.addObject("userName", userInfo.getUserName());
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/queryOrderHeadProductByCondition", method = RequestMethod.POST)
    public void queryOrderHeadProductByCondition(DesignMaterialHeadProduct orderHead, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            List<DesignMaterialHeadProduct> orderList = IDesignServ.queryOrderHeadProductByCondition(orderHead);
            int recordCount = IDesignServ.queryOrderHeadProductByConditionCount(orderHead);
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            if (orderList.size() > 0) {
                orderList.get(0).setMaxPage(maxPage);
                orderList.get(0).setRecordCount(recordCount);
                orderList.get(0).setCurrentPage(orderHead.getCurrentPage());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(orderList);
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
    @RequestMapping(value = "/queryOrderHeadByCondition", method = RequestMethod.POST)
    public void queryOrderHeadByCondition(DesignMaterialHead orderHead, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            List<DesignMaterialHead> orderList = IDesignServ.queryOrderHeadByCondition(orderHead);
            int recordCount = IDesignServ.queryOrderHeadByConditionCount(orderHead);
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            if (orderList.size() > 0) {
                orderList.get(0).setMaxPage(maxPage);
                orderList.get(0).setRecordCount(recordCount);
                orderList.get(0).setCurrentPage(orderHead.getCurrentPage());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(orderList);
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
    @RequestMapping(value = "/saveSJHeadDateToMysql")
    public ModelAndView saveSJHeadDateToMysql(DesignMaterialHead designMaterialHead, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            designMaterialHead.setOrderMaker(userInfo.getEmpNo());
            int isSave = IDesignServ.saveSJHeadDateToMysql(designMaterialHead);
            ModelAndView mav = new ModelAndView("designmaterialneed");
            DesignMaterialHead orderHead = new DesignMaterialHead();
            List<Employee> salorEmpList = IDesignServ.getSalor();
            JSONArray salorEmpList1 = JSONArray.fromObject(salorEmpList.toArray());
            List<String> orderNoList = IDesignServ.getAllOrderNo();
            List<String> orderAreaList = IDesignServ.getAllOrderArea();
            List<Employee> orderMakerList = IDesignServ.getAllMaker();
            List<DesignMaterialHead> orderList = IDesignServ.getAllDMH(orderHead);
            int recordCount = IDesignServ.getAllDMHCount();
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            orderHead.setMaxPage(maxPage);
            orderHead.setRecordCount(recordCount);
            mav.addObject("orderHead", orderHead);
            mav.addObject("salorEmpList", salorEmpList);
            mav.addObject("salorEmpList1", salorEmpList1);
            mav.addObject("orderNoList", orderNoList);
            mav.addObject("orderAreaList", orderAreaList);
            mav.addObject("orderMakerList", orderMakerList);
            mav.addObject("orderList", orderList);
            mav.addObject("flag", isSave);
            mav.addObject("userName", userInfo.getUserName());
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }
}
