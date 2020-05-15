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

    List<DesignMaterialHeadProduct> queryOrderHeadProductByCondition(DesignMaterialHeadProduct product) throws Exception;

    int queryOrderHeadProductByConditionCount(DesignMaterialHeadProduct product) throws Exception;

    List<Employee> getAllMaker() throws Exception;

    void deleteOrderItemByheadId(Integer id) throws Exception;

    List<String> getAllOrderNo() throws Exception;

    void deleteOrderItemHByheadId(Integer id) throws Exception;

    int saveSJIHeadPDateToMysql(DesignMaterialHeadProductItem item) throws Exception;

    List<DesignMaterialHeadProductItem> getAllDMHIPByCustomerNo(DesignMaterialHeadProductItem item) throws Exception;

    int getAllDMHIPByCustomerNoCount(DesignMaterialHeadProductItem item) throws Exception;

    List<String> getAllOrderArea() throws Exception;

    int saveSJHeadDateToMysql(DesignMaterialHead dmh) throws Exception;

    int saveSJHeadPDateToMysql(DesignMaterialHeadProduct pmhp) throws Exception;

    List<DesignMaterialHeadProduct> getAllDMHPByCustomerNo(DesignMaterialHeadProduct pmhp) throws Exception;

    int getAllDMHPByCustomerNoCount(DesignMaterialHeadProduct dmhp) throws Exception;

    List<DesignMaterialHead> getAllDMH(DesignMaterialHead orderHead) throws Exception;

    List<DesignMaterialHeadProduct> getAllDMHP(DesignMaterialHeadProduct orderHead) throws Exception;

    List<DesignMaterialHeadProductItem> getAllDMHPI(DesignMaterialHeadProductItem orderHead) throws Exception;

    int getAllDMHPCount(DesignMaterialHeadProduct head) throws Exception;

    int getAllDMHPICount() throws Exception;

    int  getAllDMHCount() throws Exception;

    List<String> getAllproductNameList() throws Exception;

    List<DesignMaterialHead> queryOrderHeadByCondition(DesignMaterialHead head) throws Exception;

    int queryOrderHeadByConditionCount(DesignMaterialHead head) throws Exception;

    List<String> getAllproductNoList() throws Exception;

    List<String> getAllmaterialSpeciList() throws Exception;

    List<String> getAllmaterialNameList() throws Exception;

    List<String> getAllmateiralNoList() throws Exception;

    List<String> getAlldrawingNoList() throws Exception;





}
