package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.DesignMaterialHead;
import com.cosun.cosunp.entity.DesignMaterialHeadProduct;
import com.cosun.cosunp.entity.DesignMaterialHeadProductItem;
import com.cosun.cosunp.entity.Employee;

import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/5/4 0004 下午 2:51
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public interface IDesignServ {

    List<Employee> getSalor() throws Exception;


    String transferExcelToPDF(String fileRoute) throws Exception;

    List<DesignMaterialHeadProduct> queryOrderHeadProductByCondition(DesignMaterialHeadProduct product) throws Exception;

    int queryOrderHeadProductByConditionCount(DesignMaterialHeadProduct product) throws Exception;

    List<Employee> getAllMaker() throws Exception;

    void deleteOrderItemByheadId(Integer id) throws Exception;

    List<String> getAllOrderNo(String empNo) throws Exception;

    int saveSJHeadByCopy(Integer fromId, String toOrderNo) throws Exception;

    List<DesignMaterialHead> queryOrderHeadBytoOrderNo(String loginName,String orderNo) throws Exception;

    int queryOrderHeadBytoOrderNoCount(String loginName,String orderNo) throws Exception;

    DesignMaterialHead getHeadIdandInfoById(Integer id) throws Exception;

    void deleteOrderItemHByheadId(Integer id) throws Exception;

    int saveSJIHeadPDateToMysql(DesignMaterialHeadProductItem item) throws Exception;

    int saveSJIListHeadPDateToMysql(List<DesignMaterialHeadProductItem> item) throws Exception;

    DesignMaterialHeadProductItem getCustomerNameAndProductNoByHeadId(Integer headId) throws Exception;


    DesignMaterialHeadProductItem getCustomerNameAndProductNoByHeadId2(Integer headId) throws Exception;

    List<DesignMaterialHeadProductItem> getAllDMHIPByCustomerNo(DesignMaterialHeadProductItem item) throws Exception;

    int getAllDMHIPByCustomerNoCount(DesignMaterialHeadProductItem item) throws Exception;

    List<String> getAllOrderArea() throws Exception;

    List<DesignMaterialHeadProductItem> queryOrderHeadProductItemByCondition(DesignMaterialHeadProductItem item) throws Exception;

    int queryOrderHeadProductItemByConditionCount(DesignMaterialHeadProductItem item) throws Exception;

    int saveSJHeadDateToMysql(DesignMaterialHead dmh) throws Exception;

    int saveSJHeadPDateToMysql(DesignMaterialHeadProduct pmhp) throws Exception;

    List<DesignMaterialHeadProduct> getAllDMHPByCustomerNo(DesignMaterialHeadProduct pmhp) throws Exception;

    int getAllDMHPByCustomerNoCount(DesignMaterialHeadProduct dmhp) throws Exception;

    List<DesignMaterialHead> getAllDMH(DesignMaterialHead orderHead) throws Exception;

    List<DesignMaterialHeadProduct> getAllDMHP(DesignMaterialHeadProduct orderHead) throws Exception;

    List<DesignMaterialHeadProductItem> getAllDMHPI(DesignMaterialHeadProductItem orderHead) throws Exception;

    int getAllDMHPCount(DesignMaterialHeadProduct head) throws Exception;

    int getAllDMHPICount(DesignMaterialHeadProductItem item) throws Exception;

    int  getAllDMHCount(DesignMaterialHead head) throws Exception;

    List<String> getAllproductNameList() throws Exception;

    List<DesignMaterialHead> queryOrderHeadByCondition(DesignMaterialHead head) throws Exception;

    int queryOrderHeadByConditionCount(DesignMaterialHead head) throws Exception;

    List<String> getAllproductNoList(String orderNo) throws Exception;

    DesignMaterialHeadProduct saveSJIHeadBYIdToMysql(String empNo,Integer fromId, String toOrderNo) throws Exception;

    List<DesignMaterialHeadProduct> queryOrderHeadProductOrderNoByCondition(String empNo,String toOrderNo) throws Exception;

    int queryOrderHeadProductOrderNoByConditionCount(String empNo,String toOrderNo) throws Exception;

    int saveSJIListHeadPDateToMysqlLinShi(List<DesignMaterialHeadProductItem> orderHeadList) throws Exception;

    void deleteOrderItemByheadIdItem(Integer id) throws Exception;

    List<String> getAllmaterialSpeciList() throws Exception;

    List<String> getAllmaterialNameList() throws Exception;

    List<String> getAllmateiralNoList() throws Exception;

    List<String> getAlldrawingNoList() throws Exception;





}
