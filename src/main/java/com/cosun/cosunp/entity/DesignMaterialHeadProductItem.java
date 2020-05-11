package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2020/5/4 0004 下午 1:35
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DesignMaterialHeadProductItem implements Serializable {

    private static final long serialVersionUID = 7567720000349307877L;

    private Integer id;
    private String mateiralNo;
    private String materialName;
    private String materialSpeci;
    private String unit;
    private Double num;
    private Integer useDeptId;
    private String useDeptName;
    private Double mateiralStock;
    private String remark;
    private Integer head_product_id;


    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;
    private String sortMethod;
    private String sortByName;

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

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
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
