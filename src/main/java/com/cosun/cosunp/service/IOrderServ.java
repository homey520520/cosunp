package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IOrderServ {

    List<Employee> findAllSalor() throws Exception;

    void addOrderHeadAndItemByBean(OrderHead orderHead, MultipartFile[] files) throws Exception;

    String findNewestOrderNoBySalor(String empNo, String startTime, String endTime) throws Exception;

    OrderHead getOrderHeadByOrderNo(String orderNo) throws Exception;

    void deleteOrderItemAppendByItemAppendIds(Integer[] ids, String orderNo) throws Exception;

    List<OrderItemAppend> findAllItemAppendByOrderNo(Integer headId) throws Exception;

    void addOrderAppendByOrderNo(MultipartFile[] file,String orderNo) throws Exception;

    List<String> findAllFileNameByOrderNo(String orderNo) throws Exception;

    List<OrderItemAppend> findAllItemAppendByOrderNoReal(String orderNo) throws Exception;

    String saveProjectData(OrderHead orderHead, List<OrderItem> ois, UserInfo userInfo, MultipartFile[] file) throws Exception;

    String findNewestFinishProdNoByOldFinishProdNo(String empNo, String startTime, String endTime) throws Exception;

    List<OrderHead> getOrderItemByHeadId(Integer id) throws Exception;

    OrderHead getOrderHeadByOrderNo2(String orderNo) throws Exception;

    String fillDataToModuleExcelByOrderId(Integer id) throws Exception;

    String transferExcelToPDF(String excelUrlName) throws Exception;

    OrderHead getOrderHeadByHeadId(Integer id) throws Exception;

    OrderItem getOrderItemById(Integer itemId) throws Exception;

    List<OrderHead> findAllOrderNo() throws Exception;

    void updateProjectData(OrderHead orderHead, List<OrderItem> ois, UserInfo userInfo, MultipartFile[] file) throws Exception;

    List<String> findAllProdName() throws Exception;

    List<OrderHead> findAllOrderHeadForPMC(OrderHead orderHead) throws Exception;

    List<String> findAllUrlByOrderNo(String orderNo) throws Exception;

    List<OrderHead> findAllOrderHead(OrderHead orderHead) throws Exception;

    void deleteAllOrderByHeadId(Integer headId) throws Exception;

    int findAllOrderHeadCount() throws Exception;

    void updateStateByOrderNo(OrderHead orderHead) throws Exception;

    List<OrderHead> queryOrderHeadByCondition(OrderHead orderHead) throws Exception;

    int queryOrderHeadByConditionCount(OrderHead orderHead) throws Exception;

    List<OrderHead> getOrderItemByOrderHeadId(Integer id) throws Exception;

    int deleteOrderItemByItemId(Integer id) throws Exception;


}
