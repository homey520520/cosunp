package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.*;

import java.util.List;

public interface IProjectServ {

    List<ProjectHead> totalProjectNumByEmpNo(String empNo) throws Exception;

    List<DaysSet> getDaysSetNew() throws Exception;

    List<ProjectHeadOrder> getTotalProjectOrderByName(String userid,String projectName) throws Exception;

    List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderS(String userid, String orderNo) throws Exception;

    String returnNameByEmpNoStr(String empNoStr) throws Exception;

    ProjectHeadOrderItem getTotalProjectOrderITEMMoreByOrderS(String userid,String productName,String orderNo) throws Exception;

    int findNameRepeatOrNot(String userid, String projectName) throws Exception;

    void saveProjectByNameAndRemark(String userid, String projectName,String remark) throws Exception;

    List<China> getAllMainProvince() throws Exception;

    ProjectHeadOrder checkOrderNoRepeat(String userId,String orderNo) throws Exception;

    ProjectHead findPHbyName(String projectName,String userId) throws Exception;

    void saveProjectHeadByBean(Integer provinceId,String userId,String orderNo,String customerName,
                               String projectName,Integer id,Integer newOrOld) throws Exception;

    ProjectHeadOrderItem checkProductRepeatForOneOrder(Double hetongMoney,String orderNo,String productName) throws Exception;

    ProjectHeadOrder findPHObyOrderNo(String orderNo) throws Exception;

    void updateAlertSet(AlertSet as) throws Exception;

    void saveOrderItemMor(Integer id,ProjectHeadOrderItem phoi) throws Exception;

    ProjectHeadOrder getProjectOrderByOrderNo(String orderNo) throws Exception;

    AlertSet getAlertSet() throws Exception;

    List<Position> getAllAlertPositions() throws Exception;

    void updateOrderNoRepeat(ProjectHeadOrder pho) throws Exception;

    void updateAlertSet1(DaysSet ds) throws Exception;

    void updateAlertSet2(DaysSet ds) throws Exception;

    void updateProjectHeadOrderItem(ProjectHeadOrderItem phoi) throws Exception;

    void saveHereMoneyBYItemId(ProjectItemMoneyRecord pho) throws Exception;

    void deleteProjectRecordById(Integer id) throws Exception;

    void updateProjectRecordByBean(ProjectItemMoneyRecord record)  throws Exception;

    ProjectItemMoneyRecord getProjectRecordById(Integer id) throws Exception;

    void updatePHOIMoney(Integer itemId,Double hereMoney) throws Exception;

    List<ProjectItemMoneyRecord> getTotalProjectOrderMoneyRecordByNo(String orderNo) throws Exception;

    void saveProjectHeadItemByBean(Double hetongMoney,Integer id,String productName,String totalBao,String date) throws Exception;

    void deleteProjectDetai(String projectName,String userid) throws Exception;

    void deleteProjectDetaiItem(Integer id,String userid) throws Exception;

    int getCustomerNameByNameAndOrderNo(ProjectHeadOrder pho) throws Exception;

    ProjectHead getProjectByName(ProjectHead ph) throws Exception;

    int findNameRepeatOrNot2(ProjectHead ph) throws Exception;

    void updateProjectByNameAndRemark(ProjectHead ph) throws Exception;

    void updateProjectOrderItemByNameAndRemark(ProjectHeadOrderItem phoi) throws Exception;

    void deleteProjectDetai2(String orderNo,String userId) throws Exception;

    List<Employee> getSalorMItems() throws Exception;

    List<ProjectHeadOrderItem> getHistoryItemByProduct_NameAndOrderNo(String product_Name,String orderNo) throws Exception;

    List<Employee> getGenDanItems() throws Exception;

    List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderSAll() throws Exception;

    List<ProjectHead> getProjectBySearch(String projectName) throws Exception;

    ProjectHeadOrderItem getProjectItemHistoryById(Integer id) throws Exception;

    List<ProjectHeadOrderItem> getAllitemList() throws Exception;

    void updateProjectItemCheckById(Integer id,int checked) throws Exception;

    ProjectHeadOrderItem getProjectOrderItemByOrderNoAndProductName(ProjectHeadOrderItem phoi) throws Exception;
}
