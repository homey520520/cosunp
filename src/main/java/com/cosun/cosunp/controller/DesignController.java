package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IDesignServ;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.service.IProjectServ;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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


    private static JedisPool pool;
    private static Jedis jedis;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;


    @ResponseBody
    @RequestMapping(value = "/toHeadProduct")
    public ModelAndView toHeadProduct(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("designmaterialneedHP");
        DesignMaterialHeadProduct orderHead = new DesignMaterialHeadProduct();
        List<String> orderNoList = IDesignServ.getAllOrderNo();
        List<String> productNameList = IDesignServ.getAllproductNameList();
        List<String> productNoList = IDesignServ.getAllproductNoList();
        List<String> drawingNoList = IDesignServ.getAlldrawingNoList();
        List<DesignMaterialHeadProduct> orderList = IDesignServ.getAllDMHP(orderHead);
        int recordCount = IDesignServ.getAllDMHPCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("orderHead", orderHead);
        mav.addObject("orderList", orderList);
        mav.addObject("productNameList", productNameList);
        mav.addObject("productNoList", productNoList);
        mav.addObject("drawingNoList", drawingNoList);
        mav.addObject("orderNoList", orderNoList);
        return mav;
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
