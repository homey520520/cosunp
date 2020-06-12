package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/5/4 0004 下午 1:35
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DesignMaterialHead implements Serializable {

    private static final long serialVersionUID = -387228519140413980L;


    private Integer id;
    private String customerNo;
    private Date getOrderDate;
    private String orderArea;
    private String salorEmp;
    private Date deliveryOrderDate;
    private String orderMaker;
    private String remark;

    private String loginName;

    private String getOrderDateStr;
    private String deliveryOrderDateStr;
    private String salorEmpStr;
    private String orderMakerStr;


    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 8;
    private int currentPageTotalNum;
    private String sortMethod;
    private String sortByName;

    private List<String> customerNoList;
    private List<String> orderAreaList;
    private List<String> salorEmpList;
    private List<String> makerList;

    private List<DesignMaterialHeadProduct> productList;
    private List<DesignMaterialHeadProductItem> productItemList;

    public List<DesignMaterialHeadProductItem> getProductItemList() {
        return productItemList;
    }

    public void setProductItemList(List<DesignMaterialHeadProductItem> productItemList) {
        this.productItemList = productItemList;
    }

    public List<DesignMaterialHeadProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<DesignMaterialHeadProduct> productList) {
        this.productList = productList;
    }

    public List<String> getCustomerNoList() {
        return customerNoList;
    }

    public void setCustomerNoList(List<String> customerNoList) {
        this.customerNoList = customerNoList;
    }

    public List<String> getOrderAreaList() {
        return orderAreaList;
    }

    public void setOrderAreaList(List<String> orderAreaList) {
        this.orderAreaList = orderAreaList;
    }

    public List<String> getSalorEmpList() {
        return salorEmpList;
    }

    public void setSalorEmpList(List<String> salorEmpList) {
        this.salorEmpList = salorEmpList;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public List<String> getMakerList() {
        return makerList;
    }

    public void setMakerList(List<String> makerList) {
        this.makerList = makerList;
    }

    public String getSalorEmpStr() {
        return salorEmpStr;
    }

    public void setSalorEmpStr(String salorEmpStr) {
        this.salorEmpStr = salorEmpStr;
    }

    public String getOrderMakerStr() {
        return orderMakerStr;
    }

    public void setOrderMakerStr(String orderMakerStr) {
        this.orderMakerStr = orderMakerStr;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageTotalNum() {
        if (this.currentPage != 0)
            return (currentPage - 1) * pageSize;
        return 0;
    }

    public void setCurrentPageTotalNum(int currentPageTotalNum) {
        this.currentPageTotalNum = currentPageTotalNum;
    }

    public String getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getGetOrderDateStr() {
        return getOrderDateStr;
    }

    public void setGetOrderDateStr(String getOrderDateStr) {
        this.getOrderDateStr = getOrderDateStr;
    }

    public String getDeliveryOrderDateStr() {
        return deliveryOrderDateStr;
    }

    public void setDeliveryOrderDateStr(String deliveryOrderDateStr) {
        this.deliveryOrderDateStr = deliveryOrderDateStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Date getGetOrderDate() {
        return getOrderDate;
    }

    public void setGetOrderDate(Date getOrderDate) {
        this.getOrderDate = getOrderDate;
    }

    public String getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(String orderArea) {
        this.orderArea = orderArea;
    }

    public String getSalorEmp() {
        return salorEmp;
    }

    public void setSalorEmp(String salorEmp) {
        this.salorEmp = salorEmp;
    }

    public Date getDeliveryOrderDate() {
        return deliveryOrderDate;
    }

    public void setDeliveryOrderDate(Date deliveryOrderDate) {
        this.deliveryOrderDate = deliveryOrderDate;
    }

    public String getOrderMaker() {
        return orderMaker;
    }

    public void setOrderMaker(String orderMaker) {
        this.orderMaker = orderMaker;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
