package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.service.IOrderServ;
import com.cosun.cosunp.tool.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/1/4 0004 上午 10:40
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static Logger logger = LogManager.getLogger(OrderController.class);


    @Autowired
    IOrderServ orderServ;

    @Autowired
    private IFileUploadAndDownServ fileUploadAndDownServ;


    @ResponseBody
    @RequestMapping(value = "/findNextFoldersByQueryParam")
    public void findNextFoldersByQueryParam(@RequestBody(required = true) OrderHead orderHead, HttpSession session, HttpServletResponse response) throws Exception {
        List<String> urls = orderServ.findAllUrlByOrderNo(orderHead.getOrderNo());
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer index = null;
        Integer lastIndex = null;
        for (String s : urls) {
            index = s.indexOf("\\" + orderHead.getFolderName() + "\\");
            lastIndex = s.indexOf("\\", index + 2 + orderHead.getFolderName().length());
            if (lastIndex == -1) {
                lastIndex = s.length();
            }
            if (index != -1) {
                folderOrFiles.add(s.substring(index + 2 + orderHead.getFolderName().length(), lastIndex));
            }
        }

        for (String s : folderOrFiles) {
            if (!norepeatFoldorFile.contains(s)) {
                norepeatFoldorFile.add(s);
            }
        }
        List<String> names = new ArrayList<String>();
        for (String s : norepeatFoldorFile) {
            if (!s.contains(".")) {
                names.add(s);
            }
        }
        for (String s : norepeatFoldorFile) {
            if (s.contains(".")) {
                names.add(s);
            }
        }

        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();
            try {
                str = x.writeValueAsString(names);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str);
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/createsinglegoods")
    public ModelAndView toCreateSingleGoodsPage(HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            DownloadView view = new DownloadView();
            ModelAndView mav = new ModelAndView("singleorder");
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sDateFormat.format(new Date());
            String orderNo = orderServ.findNewestOrderNoBySalor(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
            String newestOrderNo = StringUtil.increateOrderByOlderOrderNo(orderNo, userInfo.getShortOrderName());
            String oldFinishProductNo = orderServ.findNewestFinishProdNoByOldFinishProdNo(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
            String newestFinishProdNo = StringUtil.increateFinishiNoByOrldFinishiNo(oldFinishProductNo, userInfo.getShortOrderName());
            view.setUserName(userInfo.getUserName());
            view.setPassword(userInfo.getUserPwd());
            view.setFullName(userInfo.getFullName());
            OrderHead orderHead = new OrderHead();
            orderHead.setSalor(userInfo.getFullName());
            orderHead.setNewFinishProudNo(newestFinishProdNo);
            orderHead.setOrderNo(newestOrderNo);
            mav.addObject("view", view);
            mav.addObject("orderHead", orderHead);
            List<OrderItem> orderItemList = new ArrayList<OrderItem>();
            mav.addObject("orderItemList", orderItemList);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toOrderAppendPage")
    public ModelAndView toOrderAppendPage(String orderNo, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("updateorderappend");
        OrderHead orderHead = orderServ.getOrderHeadByOrderNo(orderNo);
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        List<OrderItemAppend> orderItemAppendList = orderServ.findAllItemAppendByOrderNo(orderHead.getId());
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        List<String> oldFileLists = orderServ.findAllFileNameByOrderNo(orderNo);
        JSONArray oldFileList = JSONArray.fromObject(oldFileLists.toArray());
        mav.addObject("orderHead", orderHead);
        mav.addObject("extensionList", extensionList);
        mav.addObject("flag", 0);
        mav.addObject("orderItemAppendList", orderItemAppendList);
        mav.addObject("oldFileList", oldFileList);
        return mav;

    }

    @ResponseBody
    @RequestMapping(value = "/deleteOrderItemAppendByIds")
    public ModelAndView deleteOrderItemAppendByIds(Integer[] ids, String orderNo, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("updateorderappend");
        OrderHead orderHead = orderServ.getOrderHeadByOrderNo(orderNo);
        orderServ.deleteOrderItemAppendByItemAppendIds(ids, orderNo);
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        List<OrderItemAppend> orderItemAppendList = orderServ.findAllItemAppendByOrderNo(orderHead.getId());
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        List<String> oldFileLists = orderServ.findAllFileNameByOrderNo(orderNo);
        JSONArray oldFileList = JSONArray.fromObject(oldFileLists.toArray());
        mav.addObject("orderHead", orderHead);
        mav.addObject("extensionList", extensionList);
        mav.addObject("flag", 2);
        mav.addObject("orderItemAppendList", orderItemAppendList);
        mav.addObject("oldFileList", oldFileList);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/updateStateByOrderNo")
    public ModelAndView updateStateByOrderNo(OrderHead orderHead1, HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(new Date());
        ModelAndView mav = new ModelAndView("order");
        orderHead1.setConfirmEmpNo(userInfo.getEmpNo());
        orderHead1.setConfirmTimeStr(dateStr);
        orderServ.updateStateByOrderNo(orderHead1);
        OrderHead orderHead = new OrderHead();
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 5);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/toOrderPage")
    public ModelAndView toOrderPage(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("order");
        OrderHead orderHead = new OrderHead();
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 0);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/queryOrderHeadByCondition", method = RequestMethod.POST)
    public void queryOrderHeadByCondition(OrderHead orderHead, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            List<OrderHead> orderList = orderServ.queryOrderHeadByCondition(orderHead);
            int recordCount = orderServ.queryOrderHeadByConditionCount(orderHead);
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
    @RequestMapping(value = "/showOrderItemByOrderHeadId")
    public void showOrderItemByOrderHeadId(@RequestBody(required = true) OrderHead orderHead, HttpSession session, HttpServletResponse response) throws Exception {
        List<OrderHead> orderItemList = orderServ.getOrderItemByOrderHeadId(orderHead.getId());
        String str = null;
        ObjectMapper x = new ObjectMapper();
        try {
            str = x.writeValueAsString(orderItemList);
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
    @RequestMapping(value = "/backUpdatePageByOrderNo")
    public ModelAndView backUpdatePageByOrderNo(HttpSession session, OrderHead orderHead) throws Exception {
        orderHead = orderServ.getOrderHeadByOrderNo2(orderHead.getOrderNo());
        ModelAndView mav = new ModelAndView("updateorder");
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        List<OrderHead> orderHeadList = orderServ.getOrderItemByHeadId(orderHead.getId());
        mav.addObject("orderHeadList", orderHeadList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 0);
        mav.addObject("extensionList", extensionList);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/toUpdateOrderHeadItemByheadId")
    public ModelAndView toUpdateOrderHeadItemByheadId(HttpSession session, OrderHead orderHead) throws Exception {
        Integer id = orderHead.getId();
        orderHead = orderServ.getOrderHeadByHeadId(orderHead.getId());
        ModelAndView mav = new ModelAndView("updateorder");
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        List<OrderHead> orderHeadList = orderServ.getOrderItemByHeadId(id);
        mav.addObject("orderHeadList", orderHeadList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 0);
        mav.addObject("extensionList", extensionList);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/showOrderItemDivByOrderItemId")
    public void showOrderItemDivByOrderItemId(@RequestBody(required = true) OrderItem orderItem, HttpSession session, HttpServletResponse response) throws Exception {
        orderItem = orderServ.getOrderItemById(orderItem.getId());
        String str = null;
        ObjectMapper x = new ObjectMapper();
        try {
            str = x.writeValueAsString(orderItem);
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
    @RequestMapping(value = "/toDeleteOrderHeadItemByheadId")
    public ModelAndView toDeleteOrderHeadItemByheadId(HttpSession session, OrderHead orderHead) throws Exception {
        Integer id = orderHead.getId();
        orderHead = orderServ.getOrderHeadByHeadId(orderHead.getId());
        ModelAndView mav = new ModelAndView("deleteorder");
        List<OrderHead> orderHeadList = orderServ.getOrderItemByHeadId(id);
        mav.addObject("orderHeadList", orderHeadList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 0);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/deleteOrderItemByItemId", method = RequestMethod.POST)
    public void deleteOrderItemByItemId(OrderHead orderHead, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            List<OrderHead> orderList = orderServ.queryOrderHeadByCondition(orderHead);
            int recordCount = orderServ.queryOrderHeadByConditionCount(orderHead);
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
    @RequestMapping(value = "/toDeleteOrderHeadItemByItemid")
    public ModelAndView toDeleteOrderHeadItemByItemid(HttpSession session, OrderHead orderHead) throws Exception {
        Integer id = orderHead.getId();
        int isdeleteall = orderServ.deleteOrderItemByItemId(orderHead.getId());
        if (isdeleteall == 0) {
            orderHead = orderServ.getOrderHeadByHeadId(orderHead.getId());
            ModelAndView mav = new ModelAndView("deleteorder");
            List<OrderHead> orderHeadList = orderServ.getOrderItemByHeadId(id);
            mav.addObject("orderHeadList", orderHeadList);
            mav.addObject("orderHead", orderHead);
            mav.addObject("flag", 2);
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("order");
            List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
            List<OrderHead> orderNoList = orderServ.findAllOrderNo();
            List<String> prodNameList = orderServ.findAllProdName();
            List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
            int recordCount = orderServ.findAllOrderHeadCount();
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            orderHead.setMaxPage(maxPage);
            orderHead.setRecordCount(recordCount);
            mav.addObject("salorList", salorList);
            mav.addObject("orderNoList", orderNoList);
            mav.addObject("prodNameList", prodNameList);
            mav.addObject("orderList", orderList);
            mav.addObject("orderHead", orderHead);
            mav.addObject("flag", 2);
            return mav;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toDeleteAllOrderByHeadId")
    public ModelAndView toDeleteAllOrderByHeadId(HttpSession session, OrderHead orderHead) throws Exception {
        Integer id = orderHead.getId();
        orderServ.deleteAllOrderByHeadId(orderHead.getId());
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("order");
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 2);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/createprojectgoods")
    public ModelAndView createprojectgoods(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        OrderHead orderHead = new OrderHead();
        ModelAndView mav = new ModelAndView("projectorder");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sDateFormat.format(new Date());
        String oldFinishProductNo = orderServ.findNewestFinishProdNoByOldFinishProdNo(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
        String newestFinishProdNo = StringUtil.increateFinishiNoByOrldFinishiNo(oldFinishProductNo, userInfo.getShortOrderName());
        String orderNo = orderServ.findNewestOrderNoBySalor(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
        String newestOrderNo = StringUtil.increateOrderByOlderOrderNo(orderNo, userInfo.getShortOrderName());
        orderHead.setOrderNo(newestOrderNo);
        orderHead.setShortEngName(userInfo.getShortOrderName());
        orderHead.setNewFinishProudNo(newestFinishProdNo);
        orderHead.setSalor(userInfo.getFullName());
        mav.addObject("orderHead", orderHead);
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        mav.addObject("orderItemList", orderItemList);
        mav.addObject("flag", 0);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/updateProjectOrderToMysql")
    public ModelAndView updateProjectOrderToMysql(@RequestParam("file") MultipartFile[] file, OrderHead orderHead, HttpSession session, HttpServletRequest request) throws Exception {
        JSONArray jsonArray = JSONArray.fromObject(orderHead.getOrderItemList());
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Object o;
        JSONObject jsonObject2;
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        OrderItem oi = null;
        OrderHead oh = null;
        for (int a = 0; a < jsonArray.size(); a++) {
            oi = new OrderItem();
            o = jsonArray.get(a);
            jsonObject2 = JSONObject.fromObject(o);
            oh = (OrderHead) JSONObject.toBean(jsonObject2, OrderHead.class);
            if (oh != null) {
                oi.setProductBigType(oh.getProductBigType());
                oi.setProductMainShape(oh.getProductMainShape());
                oi.setNewFinishProudNo(oh.getNewFinishProudNo());
                oi.setProductSize(oh.getProductSize());
                oi.setEdgeHightSize(oh.getEdgeHightSize());
                oi.setMainMateriAndArt(oh.getMainMateriAndArt());
                oi.setBackInstallSelect(oh.getBackInstallSelect());
                oi.setElectMateriNeeds(oh.getElectMateriNeeds());
                oi.setInstallTransfBacking(oh.getInstallTransfBacking());
                oi.setItemDeliverTimeStr(oh.getItemDeliverTimeStr());
                oi.setOtherRemark(oh.getOtherRemark());
                oi.setNeedNum(oh.getNeedNum());
                oi.setProductName(oh.getProductName());
                orderItemList.add(oi);
            }
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(new Date());
        oh.setOrderTimeStr(dateStr);
        oh.setState(0);
        orderServ.updateProjectData(oh, orderItemList, userInfo, file);
        ModelAndView mav = new ModelAndView("order");
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 3);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/addOrderAppendByOrderNo")
    public ModelAndView addOrderAppendByOrderNo(@RequestParam("file") MultipartFile[] file, OrderHead orderHead, HttpSession session, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("updateorderappend");
        orderServ.addOrderAppendByOrderNo(file, orderHead.getOrderNo());
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        List<OrderItemAppend> orderItemAppendList = orderServ.findAllItemAppendByOrderNoReal(orderHead.getOrderNo());
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        List<String> oldFileLists = orderServ.findAllFileNameByOrderNo(orderHead.getOrderNo());
        JSONArray oldFileList = JSONArray.fromObject(oldFileLists.toArray());
        mav.addObject("orderHead", orderHead);
        mav.addObject("extensionList", extensionList);
        mav.addObject("flag", 1);
        mav.addObject("orderItemAppendList", orderItemAppendList);
        mav.addObject("oldFileList", oldFileList);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/transferDataToPDFFile", method = RequestMethod.GET)
    public void transferDataToPDFFile(@RequestParam("id") Integer id, HttpServletResponse response) throws Exception {
        String excelurl = orderServ.fillDataToModuleExcelByOrderId(id);
        String pdfurl = orderServ.transferExcelToPDF(excelurl);
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
    @RequestMapping(value = "/addProjectOrderToMysql")
    public ModelAndView addProjectOrderToMysql(@RequestParam("file") MultipartFile[] file, OrderHead orderHead, HttpSession session, HttpServletRequest request) throws Exception {
        JSONArray jsonArray = JSONArray.fromObject(orderHead.getOrderItemList());
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Object o;
        JSONObject jsonObject2;
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        OrderItem oi = null;
        OrderHead oh = null;
        for (int a = 0; a < jsonArray.size(); a++) {
            oi = new OrderItem();
            o = jsonArray.get(a);
            jsonObject2 = JSONObject.fromObject(o);
            oh = (OrderHead) JSONObject.toBean(jsonObject2, OrderHead.class);
            if (oh != null) {
                oi.setProductBigType(oh.getProductBigType());
                oi.setProductMainShape(oh.getProductMainShape());
                oi.setNewFinishProudNo(oh.getNewFinishProudNo());
                oi.setProductSize(oh.getProductSize());
                oi.setEdgeHightSize(oh.getEdgeHightSize());
                oi.setMainMateriAndArt(oh.getMainMateriAndArt());
                oi.setBackInstallSelect(oh.getBackInstallSelect());
                oi.setElectMateriNeeds(oh.getElectMateriNeeds());
                oi.setInstallTransfBacking(oh.getInstallTransfBacking());
                oi.setItemDeliverTimeStr(oh.getItemDeliverTimeStr());
                oi.setOtherRemark(oh.getOtherRemark());
                oi.setNeedNum(oh.getNeedNum());
                oi.setProductName(oh.getProductName());
                orderItemList.add(oi);
            }
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(new Date());
        oh.setOrderTimeStr(dateStr);
        oh.setState(0);
        String returnmessage = orderServ.saveProjectData(oh, orderItemList, userInfo, file);
        ModelAndView mav = new ModelAndView("order");
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 1);
        mav.addObject("returnmessage", returnmessage);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/addOrderToMysql")
    public ModelAndView addOrderToMysql(@RequestParam("file") MultipartFile[] file, HttpSession session, OrderHead orderHead) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("order");
        orderHead.setSingleOrProject(0);
        orderHead.setSalorNo(userInfo.getEmpNo());
        orderHead.setEngName(userInfo.getEngName());
        orderServ.addOrderHeadAndItemByBean(orderHead, file);
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 1);
        return mav;
    }

}
