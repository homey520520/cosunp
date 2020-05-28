package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IDesignServ;
import com.cosun.cosunp.service2.IKingSoftStoreServ;
import com.cosun.cosunp.tool.MKExcelUtil;
import com.cosun.cosunp.tool.WordToPDF;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    @RequestMapping(value = "/transferDataToPDFFile", method = RequestMethod.GET)
    public void transferDataToPDFFile(@RequestParam("id") Integer id, HttpServletResponse response) throws Exception {
        DesignMaterialHead financeImportDataList = IDesignServ.getHeadIdandInfoById(id);
        String filename = financeImportDataList.getCustomerNo() + ".xls";
        new DesignExcel().writeDesigndataTOExcel(financeImportDataList, this.finalDirPath + "linshi/", filename);
        String pdfurl = IDesignServ.transferExcelToPDF(filename);
        FileInputStream input = null;
        try {
            File file = new File(pdfurl);
            if (file.exists()) {
                byte[] data = null;
                input = new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
    public ModelAndView deleteOrderItemByheadIdItem(String productNo, String customerNo, Integer id, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("designmaterialneedHPITEM");
        IDesignServ.deleteOrderItemByheadIdItem(id);
        DesignMaterialHeadProductItem orderHead = new DesignMaterialHeadProductItem();
        List<String> list = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        if (productNo != null && productNo.trim().length() > 0) {
            list.add(customerNo);
            orderHead.setCustomerNoList(list);
        }
        if (customerNo != null && customerNo.trim().length() > 0) {
            list2.add(productNo);
            orderHead.setProductNoList(list2);
        }
        List<String> customerNoList = IDesignServ.getAllOrderNo();
        List<String> productNoList = IDesignServ.getAllproductNoList();
        List<String> materialSpeciList = IDesignServ.getAllmaterialSpeciList();
        List<String> pmaterialNameList = IDesignServ.getAllmaterialNameList();
        List<String> mateiralNoList = IDesignServ.getAllmateiralNoList();
        List<DesignMaterialHeadProductItem> orderList = IDesignServ.queryOrderHeadProductItemByCondition(orderHead);
        int recordCount = IDesignServ.queryOrderHeadProductItemByConditionCount(orderHead);
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
        mav.addObject("productNo", productNo);
        mav.addObject("customerNo", customerNo);
        mav.addObject("flag", 2);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/toHeadProductItem")
    public ModelAndView toHeadProductItem(Integer headId, HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("designmaterialneedHPITEM");
        DesignMaterialHeadProductItem orderHead = new DesignMaterialHeadProductItem();
        DesignMaterialHeadProductItem orderHead2 = new DesignMaterialHeadProductItem();
        List<String> customerNoList = IDesignServ.getAllOrderNo();
        List<String> productNoList = IDesignServ.getAllproductNoList();
        List<String> materialSpeciList = IDesignServ.getAllmaterialSpeciList();
        List<String> pmaterialNameList = IDesignServ.getAllmaterialNameList();
        List<String> mateiralNoList = IDesignServ.getAllmateiralNoList();
        if (headId != null) {
            orderHead.setHead_product_id(headId);
            orderHead2 = IDesignServ.getCustomerNameAndProductNoByHeadId(headId);
        }
        List<DesignMaterialHeadProductItem> orderList = IDesignServ.getAllDMHPI(orderHead);
        int recordCount = IDesignServ.getAllDMHPICount(orderHead);
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
        if (headId != null && orderHead2 != null) {
            mav.addObject("productNo", orderHead2.getProductNo());
            mav.addObject("customerNo", orderHead2.getCustomerNo());
        } else if (headId != null) {
            orderHead2 = IDesignServ.getCustomerNameAndProductNoByHeadId2(headId);
            mav.addObject("productNo", orderHead2.getProductNo());
            mav.addObject("customerNo", orderHead2.getCustomerNo());
        }
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
    @RequestMapping(value = "/queryOrderHeadProductItemByCondition", method = RequestMethod.POST)
    public void queryOrderHeadProductItemByCondition(DesignMaterialHeadProductItem orderHead, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            List<DesignMaterialHeadProductItem> orderList = IDesignServ.queryOrderHeadProductItemByCondition(orderHead);
            int recordCount = IDesignServ.queryOrderHeadProductItemByConditionCount(orderHead);
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
    @RequestMapping(value = "/saveSJIHeadPDateToMysql")
    public void saveSJIHeadPDateToMysql(@RequestBody(required = true) DesignMaterialHeadProductItem orderHead, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            DesignMaterialHeadProductItem item = new DesignMaterialHeadProductItem();
            List<String> list = new ArrayList<String>();
            List<String> list2 = new ArrayList<String>();
            list.add(orderHead.getCustomerNo());
            list2.add(orderHead.getProductNo());
            list.add(orderHead.getProductNo());
            item.setCustomerNoList(list);
            item.setProductNoList(list2);
            item.setCurrentPage(orderHead.getCurrentPage());
            int isSave = IDesignServ.saveSJIHeadPDateToMysql(orderHead);
            List<DesignMaterialHeadProductItem> orderList = IDesignServ.queryOrderHeadProductItemByCondition(item);
            int recordCount = IDesignServ.queryOrderHeadProductItemByConditionCount(item);
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            if (orderList.size() > 0) {
                orderList.get(0).setMaxPage(maxPage);
                orderList.get(0).setRecordCount(recordCount);
                orderList.get(0).setCurrentPage(orderHead.getCurrentPage());
                orderList.get(0).setFlag(isSave);

            }else {
                orderList = new ArrayList<DesignMaterialHeadProductItem>();
                item.setMaxPage(maxPage);
                item.setRecordCount(recordCount);
                item.setCurrentPage(item.getCurrentPage());
                item.setFlag(isSave);
                orderList.add(item);
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
    @RequestMapping(value = "/saveSJIHeadPDateToMysql2")
    public void saveSJIHeadPDateToMysql2(@RequestBody(required = true) List<DesignMaterialHeadProductItem> orderHeadList, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            DesignMaterialHeadProductItem item = new DesignMaterialHeadProductItem();
            List<String> list = new ArrayList<String>();
            List<String> list2 = new ArrayList<String>();
            list.add(orderHeadList.get(0).getCustomerNo().trim());
            list2.add(orderHeadList.get(0).getProductNo().trim());
            item.setCustomerNoList(list);
            item.setProductNoList(list2);
            item.setCurrentPage(orderHeadList.get(0).getCurrentPage());
            int isSave = IDesignServ.saveSJIListHeadPDateToMysql(orderHeadList);
            List<DesignMaterialHeadProductItem> orderList = IDesignServ.queryOrderHeadProductItemByCondition(item);
            int recordCount = IDesignServ.queryOrderHeadProductItemByConditionCount(item);
            int maxPage = recordCount % item.getPageSize() == 0 ? recordCount / item.getPageSize() : recordCount / item.getPageSize() + 1;
            if (orderList.size() > 0) {
                orderList.get(0).setMaxPage(maxPage);
                orderList.get(0).setRecordCount(recordCount);
                orderList.get(0).setCurrentPage(item.getCurrentPage());
                orderList.get(0).setFlag(isSave);

            } else {
                orderList = new ArrayList<DesignMaterialHeadProductItem>();
                item.setMaxPage(maxPage);
                item.setRecordCount(recordCount);
                item.setCurrentPage(item.getCurrentPage());
                item.setFlag(isSave);
                orderList.add(item);
            }
            String str1;
            ObjectMapper x = new ObjectMapper();
            str1 = x.writeValueAsString(orderList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1);

        } catch (SecurityException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
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
            mav.addObject("customerNo", orderHead.getCustomerNo());
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/importDesigeHeadById")
    public void importDesigeHeadById(Integer headId, HttpServletResponse response, HttpSession
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

        DesignMaterialHead financeImportDataList = IDesignServ.getHeadIdandInfoById(headId);
        String filename = financeImportDataList.getCustomerNo() + ".xls";
        new DesignExcel().writeDesigndataTOExcel(financeImportDataList, this.finalDirPath + "linshi/", filename);
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
    @RequestMapping(value = "/importDesigeHeadPDFById")
    public void importDesigeHeadPDFById(Integer headId, HttpServletResponse response, HttpSession
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

        DesignMaterialHead financeImportDataList = IDesignServ.getHeadIdandInfoById(headId);
        String filename = financeImportDataList.getCustomerNo() + ".xls";
        new DesignExcel().writeDesigndataTOExcel(financeImportDataList, this.finalDirPath + "linshi/", filename);
        WordToPDF.WordToPDFOrder(filename, financeImportDataList.getCustomerNo().concat(".pdf"), finalDirPath);
        final File result = new File(this.finalDirPath + "linshi/" + financeImportDataList.getCustomerNo().concat(".pdf"));
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(financeImportDataList.getCustomerNo().concat(".pdf").getBytes(), "iso-8859-1"));
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
    @RequestMapping(value = "/saveSJHeadPDateToMysql")
    public ModelAndView saveSJHeadPDateToMysql(DesignMaterialHeadProduct orderHead, HttpServletResponse response, HttpSession session) throws
            Exception {
        try {
            if (orderHead != null && orderHead.getProductRoute() != null && orderHead.getProductRoute().contains(",")) {
                orderHead.setProductRoute(orderHead.getProductRoute().replace(",", "-"));
            }
            int isSave = IDesignServ.saveSJHeadPDateToMysql(orderHead);
            ModelAndView mav = new ModelAndView("designmaterialneedHP");
            List<String> orderNoList = IDesignServ.getAllOrderNo();
            List<String> productNameList = IDesignServ.getAllproductNameList();
            List<String> productNoList = IDesignServ.getAllproductNoList();
            List<String> drawingNoList = IDesignServ.getAlldrawingNoList();
            List<String> list2 = new ArrayList<String>();
            if (orderHead != null && orderHead.getCustomerNo() != null) {
                list2.add(orderHead.getCustomerNo());
                orderHead.setCustomerNoList(list2);
            }
            List<DesignMaterialHeadProduct> orderList = IDesignServ.queryOrderHeadProductByCondition(orderHead);
            int recordCount = IDesignServ.queryOrderHeadProductByConditionCount(orderHead);
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
            mav.addObject("customerNo", orderHead.getCustomerNo());
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
            List<DesignMaterialHead> orderList = IDesignServ.queryOrderHeadByCondition(designMaterialHead);
            int recordCount = IDesignServ.queryOrderHeadByConditionCount(designMaterialHead);
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
            mav.addObject("customerNo", designMaterialHead.getCustomerNo());
            mav.addObject("userName", userInfo.getUserName());
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }
}
