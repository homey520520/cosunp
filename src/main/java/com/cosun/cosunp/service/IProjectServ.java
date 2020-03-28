package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.China;
import com.cosun.cosunp.entity.ProjectHead;
import com.cosun.cosunp.entity.ProjectHeadOrder;
import com.cosun.cosunp.entity.ProjectHeadOrderItem;

import java.util.List;

public interface IProjectServ {

    List<ProjectHead> totalProjectNumByEmpNo(String empNo) throws Exception;

    List<ProjectHeadOrder> getTotalProjectOrderByName(String userid,String projectName) throws Exception;

    List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderS(String userid, String orderNo) throws Exception;

    ProjectHeadOrderItem getTotalProjectOrderITEMMoreByOrderS(String userid,String productName,String orderNo) throws Exception;

    int findNameRepeatOrNot(String userid, String projectName) throws Exception;

    void saveProjectByNameAndRemark(String userid, String projectName,String remark) throws Exception;

    List<China> getAllMainProvince() throws Exception;

    ProjectHeadOrder checkOrderNoRepeat(String userId,String orderNo) throws Exception;

    ProjectHead findPHbyName(String projectName,String userId) throws Exception;

    void saveProjectHeadByBean(Integer provinceId,String userId,String orderNo,String customerName,String projectName,Integer id) throws Exception;

    ProjectHeadOrderItem checkProductRepeatForOneOrder(String orderNo,String productName) throws Exception;

    ProjectHeadOrder findPHObyOrderNo(String orderNo) throws Exception;

    void saveOrderItemMor(Integer id,ProjectHeadOrderItem phoi) throws Exception;

    ProjectHeadOrder getProjectOrderByOrderNo(String orderNo) throws Exception;

    void updateOrderNoRepeat(ProjectHeadOrder pho) throws Exception;

    void saveProjectHeadItemByBean(Integer id,String productName,String totalBao,String date) throws Exception;

}
