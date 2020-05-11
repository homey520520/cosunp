package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.DesignMaterialHead;
import com.cosun.cosunp.entity.DesignMaterialHeadProduct;
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

    List<Employee> getAllMaker() throws Exception;

    List<String> getAllOrderNo() throws Exception;

    List<String> getAllOrderArea() throws Exception;

    int saveSJHeadDateToMysql(DesignMaterialHead dmh) throws Exception;

    int saveSJHeadPDateToMysql(DesignMaterialHeadProduct pmhp) throws Exception;

    List<DesignMaterialHeadProduct> getAllDMHPByCustomerNo(DesignMaterialHeadProduct pmhp) throws Exception;

    int getAllDMHPByCustomerNoCount(DesignMaterialHeadProduct dmhp) throws Exception;

    List<DesignMaterialHead> getAllDMH(DesignMaterialHead orderHead) throws Exception;

    List<DesignMaterialHeadProduct> getAllDMHP(DesignMaterialHeadProduct orderHead) throws Exception;

    int getAllDMHPCount() throws Exception;

    int  getAllDMHCount() throws Exception;

    List<String> getAllproductNameList() throws Exception;

    List<String> getAllproductNoList() throws Exception;

    List<String> getAlldrawingNoList() throws Exception;





}
