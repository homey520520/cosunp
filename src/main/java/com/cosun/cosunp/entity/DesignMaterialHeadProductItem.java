package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/5/4 0004 下午 1:35
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DesignMaterialHeadProductItem implements Serializable {

    private static final long serialVersionUID = 7567720000349307877L;

    private String customerNo;
    private String productNo;

    private Integer id;
    private String mateiralNo;
    private String materialName;
    private String materialSpeci;
    private String unit;
    private String num;
    private Integer useDeptId;
    private String productName;
    private String useDeptName;
    private Double mateiralStock;
    private String remark;
    private Integer head_product_id;
    private Integer isCanUse;
    private String isCanUse2;
    private String isCanUseStr;
    private boolean isCanPurchase;
    private int flag;
    private int isLinshi;


    List<String> materialNameList2;
    List<String> mateiralNoList;
    List<String> materialSpeciList2;
    List<String> customerNoList;
    List<String> productNoList;


    private List<String> materialNameList;
    private List<String> materialSpeciList;


    List<DesignMaterialHeadProductItem> dhpItemList;

    List<DesignMaterialHeadProductItem> dhpHeadItemList;

    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 100;
    private int currentPageTotalNum;
    private String sortMethod;
    private String sortByName;


    public int getIsLinshi() {
        return isLinshi;
    }

    public void setIsLinshi(int isLinshi) {
        this.isLinshi = isLinshi;
    }

    public List<DesignMaterialHeadProductItem> getDhpHeadItemList() {
        return dhpHeadItemList;
    }

    public void setDhpHeadItemList(List<DesignMaterialHeadProductItem> dhpHeadItemList) {
        this.dhpHeadItemList = dhpHeadItemList;
    }

    public List<DesignMaterialHeadProductItem> getDhpItemList() {
        return dhpItemList;
    }

    public void setDhpItemList(List<DesignMaterialHeadProductItem> dhpItemList) {
        this.dhpItemList = dhpItemList;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<String> getMaterialNameList2() {
        return materialNameList2;
    }

    public void setMaterialNameList2(List<String> materialNameList2) {
        this.materialNameList2 = materialNameList2;
    }

    public List<String> getMateiralNoList() {
        return mateiralNoList;
    }

    public void setMateiralNoList(List<String> mateiralNoList) {
        this.mateiralNoList = mateiralNoList;
    }

    public List<String> getMaterialSpeciList2() {
        return materialSpeciList2;
    }

    public void setMaterialSpeciList2(List<String> materialSpeciList2) {
        this.materialSpeciList2 = materialSpeciList2;
    }

    public List<String> getCustomerNoList() {
        return customerNoList;
    }

    public void setCustomerNoList(List<String> customerNoList) {
        this.customerNoList = customerNoList;
    }

    public List<String> getProductNoList() {
        return productNoList;
    }

    public void setProductNoList(List<String> productNoList) {
        this.productNoList = productNoList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIsCanUse2() {
        return isCanUse2;
    }

    public void setIsCanUse2(String isCanUse2) {
        this.isCanUse2 = isCanUse2;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public boolean isCanPurchase() {
        return isCanPurchase;
    }

    public void setCanPurchase(boolean canPurchase) {
        isCanPurchase = canPurchase;
    }

    public List<String> getMaterialNameList() {
        return materialNameList;
    }

    public void setMaterialNameList(List<String> materialNameList) {
        this.materialNameList = materialNameList;
    }

    public List<String> getMaterialSpeciList() {
        return materialSpeciList;
    }

    public void setMaterialSpeciList(List<String> materialSpeciList) {
        this.materialSpeciList = materialSpeciList;
    }

    public String getIsCanUseStr() {
        if (mateiralStock != null)
            if ((this.mateiralStock == 0.0 && this.isCanPurchase)) {
                return "不可用";
            }
        return "可用";
    }

    public void setIsCanUseStr(String isCanUseStr) {
        this.isCanUseStr = isCanUseStr;
    }

    public Integer getIsCanUse() {
        if (this.mateiralStock != null)
            if (this.mateiralStock == 0.0 && this.isCanPurchase) {
                return 0;
            }
        return 1;
    }

    public void setIsCanUse(Integer isCanUse) {
        this.isCanUse = isCanUse;
    }

    public Integer getHead_product_id() {
        return head_product_id;
    }

    public void setHead_product_id(Integer head_product_id) {
        this.head_product_id = head_product_id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMateiralNo() {
        return mateiralNo;
    }

    public void setMateiralNo(String mateiralNo) {
        this.mateiralNo = mateiralNo;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialSpeci() {
        return materialSpeci;
    }

    public void setMaterialSpeci(String materialSpeci) {
        this.materialSpeci = materialSpeci;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public Integer getUseDeptId() {
        return useDeptId;
    }

    public void setUseDeptId(Integer useDeptId) {
        this.useDeptId = useDeptId;
    }

    public String getUseDeptName() {
        return useDeptName;
    }

    public void setUseDeptName(String useDeptName) {
        this.useDeptName = useDeptName;
    }

    public Double getMateiralStock() {
        return mateiralStock;
    }

    public void setMateiralStock(Double mateiralStock) {
        this.mateiralStock = mateiralStock;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
