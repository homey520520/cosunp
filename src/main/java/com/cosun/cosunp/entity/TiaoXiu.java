package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/12/19  下午 3:02
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class TiaoXiu implements Serializable {

    private static final long serialVersionUID = 2484144458078726969L;

    private Integer id;
    private String empNo;
    private String name;
    private Date fromDate;
    private Double totalHours;
    private Double hours;
    private Date toDate;
    private Integer fromDateWeek;
    private Integer toDateWeek;
    private Integer type;
    private Integer usaged;
    private String remark;

    private String fromDateStr;
    private String toDateStr;
    private String typeStr;
    private String usagedStr;

    private String fromDateFromStr;
    private String fromDateEndStr;
    private String toDateFromStr;
    private String toDateEndStr;

    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;

    private List<Integer> ids;
    private List<String> empNoList;

    public String getFromDateFromStr() {
        return fromDateFromStr;
    }

    public void setFromDateFromStr(String fromDateFromStr) {
        this.fromDateFromStr = fromDateFromStr;
    }

    public String getFromDateEndStr() {
        return fromDateEndStr;
    }

    public void setFromDateEndStr(String fromDateEndStr) {
        this.fromDateEndStr = fromDateEndStr;
    }

    public String getToDateFromStr() {
        return toDateFromStr;
    }

    public void setToDateFromStr(String toDateFromStr) {
        this.toDateFromStr = toDateFromStr;
    }

    public String getToDateEndStr() {
        return toDateEndStr;
    }

    public void setToDateEndStr(String toDateEndStr) {
        this.toDateEndStr = toDateEndStr;
    }

    public List<String> getEmpNoList() {
        return empNoList;
    }

    public void setEmpNoList(List<String> empNoList) {
        this.empNoList = empNoList;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public String getTypeStr() {
        if (type == 0)
            return "公调";
        return "私调";
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getUsagedStr() {
        if (this.usaged == 0)
            return "未使用";
        return "已使用";
    }

    public void setUsagedStr(String usagedStr) {
        this.usagedStr = usagedStr;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getFromDateWeek() {
        return fromDateWeek;
    }

    public void setFromDateWeek(Integer fromDateWeek) {
        this.fromDateWeek = fromDateWeek;
    }

    public Integer getToDateWeek() {
        return toDateWeek;
    }

    public void setToDateWeek(Integer toDateWeek) {
        this.toDateWeek = toDateWeek;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUsaged() {
        return usaged;
    }

    public void setUsaged(Integer usaged) {
        this.usaged = usaged;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFromDateStr() {
        return fromDateStr;
    }

    public void setFromDateStr(String fromDateStr) {
        this.fromDateStr = fromDateStr;
    }

    public String getToDateStr() {
        return toDateStr;
    }

    public void setToDateStr(String toDateStr) {
        this.toDateStr = toDateStr;
    }
}
