package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/11/28  上午 10:34
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Out implements Serializable {

    private static final long serialVersionUID = 7309941662298074008L;

    private Integer id;
    private String empNo;
    private Date date;
    private String outaddr;
    private String outfor;
    private Date outtime;
    private Date realcomtime;
    private String remark;
    private Double interDays;

    private String name;
    private String deptName;
    private String dateStr;
    private String outtimeStr;
    private String realcomtimeStr;


    private List<Integer> deptIds;
    private List<Integer> positionIds;
    private List<Integer> names;
    private String beginOutStr;
    private String endOutStr;
    private Integer empId;

    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;


    public Double getInterDays() {
        return interDays;
    }

    public void setInterDays(Double interDays) {
        this.interDays = interDays;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<Integer> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<Integer> positionIds) {
        this.positionIds = positionIds;
    }

    public List<Integer> getNames() {
        return names;
    }

    public void setNames(List<Integer> names) {
        this.names = names;
    }

    public String getBeginOutStr() {
        return beginOutStr;
    }

    public void setBeginOutStr(String beginOutStr) {
        this.beginOutStr = beginOutStr;
    }

    public String getEndOutStr() {
        return endOutStr;
    }

    public void setEndOutStr(String endOutStr) {
        this.endOutStr = endOutStr;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOutaddr() {
        return outaddr;
    }

    public void setOutaddr(String outaddr) {
        this.outaddr = outaddr;
    }

    public String getOutfor() {
        return outfor;
    }

    public void setOutfor(String outfor) {
        this.outfor = outfor;
    }

    public Date getOuttime() {
        return outtime;
    }

    public void setOuttime(Date outtime) {
        this.outtime = outtime;
    }

    public Date getRealcomtime() {
        return realcomtime;
    }

    public void setRealcomtime(Date realcomtime) {
        this.realcomtime = realcomtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getOuttimeStr() {
        return outtimeStr;
    }

    public void setOuttimeStr(String outtimeStr) {
        this.outtimeStr = outtimeStr;
    }

    public String getRealcomtimeStr() {
        return realcomtimeStr;
    }

    public void setRealcomtimeStr(String realcomtimeStr) {
        this.realcomtimeStr = realcomtimeStr;
    }
}
