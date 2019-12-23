package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/5/17 0017 上午 10:07
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class OutPutWorkData implements Serializable {

    private static final long serialVersionUID = -2840312720095130869L;

    private String employeeName;
    private String empNo;
    private String deptName;
    private String positionName;
    private String positionLevel;
    private String workDate;
    private String workInDate;

    private String workSetAOn;
    private String workSetAonFroml;
    private String workSetAOnEnd;
    private String clockAOn;
    private String isAonOk;

    private String workSetAOff;
    private String workSetAOffFrom;
    private String workSetAOffEnd;
    private String clockAOff;
    private String isAoffOk;

    private String workSetPOn;
    private String workSetPOnFrom;
    private String workSetPOnEnd;
    private String clockPOn;
    private String isPOnOk;

    private String workSetPOff;
    private String workSetPOffForm;
    private String workSetPOffEnd;
    private String clockPOff;
    private String isPOffOk;

    private String workSetExtOn;
    private String clockExtOn;
    private String isExtOnOk;

    private String workSetExtOff;
    private String clockExtOff;
    private String isExtOffOk;

    private String remark;
    private String yearMonth;

    private String orginClockInStr;

    private Double extHours;

    private String leaveDateStart;
    private String leaveDateEnd;

    private String isLeaveOrOther;
    private String errorMessage;

    private Integer workType;//0 正常上班 1.周末加班 2.法定节假日

    private String yearMonthDay;
    private Integer workArea;

    private Integer isNoWorkSetButWork;

    public Integer getIsNoWorkSetButWork() {
        return isNoWorkSetButWork;
    }

    public void setIsNoWorkSetButWork(Integer isNoWorkSetButWork) {
        this.isNoWorkSetButWork = isNoWorkSetButWork;
    }

    public Integer getWorkArea() {
        return workArea;
    }

    public void setWorkArea(Integer workArea) {
        this.workArea = workArea;
    }

    public String getYearMonthDay() {
        return yearMonthDay;
    }

    public void setYearMonthDay(String yearMonthDay) {
        this.yearMonthDay = yearMonthDay;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getWorkSetAonFroml() {
        return workSetAonFroml;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setWorkSetAonFroml(String workSetAonFroml) {
        this.workSetAonFroml = workSetAonFroml;
    }

    public String getWorkSetAOnEnd() {
        return workSetAOnEnd;
    }

    public void setWorkSetAOnEnd(String workSetAOnEnd) {
        this.workSetAOnEnd = workSetAOnEnd;
    }

    public String getWorkSetAOffFrom() {
        return workSetAOffFrom;
    }

    public void setWorkSetAOffFrom(String workSetAOffFrom) {
        this.workSetAOffFrom = workSetAOffFrom;
    }

    public String getWorkSetAOffEnd() {
        return workSetAOffEnd;
    }

    public void setWorkSetAOffEnd(String workSetAOffEnd) {
        this.workSetAOffEnd = workSetAOffEnd;
    }

    public String getWorkSetPOnFrom() {
        return workSetPOnFrom;
    }

    public void setWorkSetPOnFrom(String workSetPOnFrom) {
        this.workSetPOnFrom = workSetPOnFrom;
    }

    public String getWorkSetPOnEnd() {
        return workSetPOnEnd;
    }

    public void setWorkSetPOnEnd(String workSetPOnEnd) {
        this.workSetPOnEnd = workSetPOnEnd;
    }

    public String getWorkSetPOffForm() {
        return workSetPOffForm;
    }

    public void setWorkSetPOffForm(String workSetPOffForm) {
        this.workSetPOffForm = workSetPOffForm;
    }

    public String getWorkSetPOffEnd() {
        return workSetPOffEnd;
    }

    public void setWorkSetPOffEnd(String workSetPOffEnd) {
        this.workSetPOffEnd = workSetPOffEnd;
    }

    public String getOrginClockInStr() {
        return orginClockInStr;
    }

    public void setOrginClockInStr(String orginClockInStr) {
        this.orginClockInStr = orginClockInStr;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getIsLeaveOrOther() {
        return isLeaveOrOther;
    }

    public void setIsLeaveOrOther(String isLeaveOrOther) {
        this.isLeaveOrOther = isLeaveOrOther;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getWorkInDate() {
        return workInDate;
    }

    public void setWorkInDate(String workInDate) {
        this.workInDate = workInDate;
    }

    public String getWorkSetAOn() {
        return workSetAOn;
    }

    public void setWorkSetAOn(String workSetAOn) {
        this.workSetAOn = workSetAOn;
    }

    public String getClockAOn() {
        return clockAOn;
    }

    public void setClockAOn(String clockAOn) {
        this.clockAOn = clockAOn;
    }

    public String getIsAonOk() {
        return isAonOk;
    }

    public void setIsAonOk(String isAonOk) {
        this.isAonOk = isAonOk;
    }

    public String getWorkSetAOff() {
        return workSetAOff;
    }

    public void setWorkSetAOff(String workSetAOff) {
        this.workSetAOff = workSetAOff;
    }

    public String getClockAOff() {
        return clockAOff;
    }

    public void setClockAOff(String clockAOff) {
        this.clockAOff = clockAOff;
    }

    public String getIsAoffOk() {
        return isAoffOk;
    }

    public void setIsAoffOk(String isAoffOk) {
        this.isAoffOk = isAoffOk;
    }

    public String getWorkSetPOn() {
        return workSetPOn;
    }

    public void setWorkSetPOn(String workSetPOn) {
        this.workSetPOn = workSetPOn;
    }

    public String getClockPOn() {
        return clockPOn;
    }

    public void setClockPOn(String clockPOn) {
        this.clockPOn = clockPOn;
    }

    public String getIsPOnOk() {
        return isPOnOk;
    }

    public void setIsPOnOk(String isPOnOk) {
        this.isPOnOk = isPOnOk;
    }

    public String getWorkSetPOff() {
        return workSetPOff;
    }

    public void setWorkSetPOff(String workSetPOff) {
        this.workSetPOff = workSetPOff;
    }

    public String getClockPOff() {
        return clockPOff;
    }

    public void setClockPOff(String clockPOff) {
        this.clockPOff = clockPOff;
    }

    public String getIsPOffOk() {
        return isPOffOk;
    }

    public void setIsPOffOk(String isPOffOk) {
        this.isPOffOk = isPOffOk;
    }

    public String getWorkSetExtOn() {
        return workSetExtOn;
    }

    public void setWorkSetExtOn(String workSetExtOn) {
        this.workSetExtOn = workSetExtOn;
    }

    public String getClockExtOn() {
        return clockExtOn;
    }

    public void setClockExtOn(String clockExtOn) {
        this.clockExtOn = clockExtOn;
    }

    public String getIsExtOnOk() {
        return isExtOnOk;
    }

    public void setIsExtOnOk(String isExtOnOk) {
        this.isExtOnOk = isExtOnOk;
    }

    public String getWorkSetExtOff() {
        return workSetExtOff;
    }

    public void setWorkSetExtOff(String workSetExtOff) {
        this.workSetExtOff = workSetExtOff;
    }

    public String getClockExtOff() {
        return clockExtOff;
    }

    public void setClockExtOff(String clockExtOff) {
        this.clockExtOff = clockExtOff;
    }

    public String getIsExtOffOk() {
        return isExtOffOk;
    }

    public void setIsExtOffOk(String isExtOffOk) {
        this.isExtOffOk = isExtOffOk;
    }

    public Double getExtHours() {
        return extHours;
    }

    public void setExtHours(Double extHours) {
        this.extHours = extHours;
    }

    public String getLeaveDateStart() {
        return leaveDateStart;
    }

    public void setLeaveDateStart(String leaveDateStart) {
        this.leaveDateStart = leaveDateStart;
    }

    public String getLeaveDateEnd() {
        return leaveDateEnd;
    }

    public void setLeaveDateEnd(String leaveDateEnd) {
        this.leaveDateEnd = leaveDateEnd;
    }
}
