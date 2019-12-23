package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/14 0014 上午 11:30
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WorkDate implements Serializable {

    private static final long serialVersionUID = 5202823010119902688L;

    private Integer id;
    private String month;
    private String workDate;
    private String remark;
    private String positionLevel;
    private String empNostr;
    private String[] workDates;
    private String[] workDatess;
    private List<String> empNos;
    private Integer type;
    private List<String> positionLevels;

    private List<SmallEmployee> empList;

    public String getEmpNostr() {
        return empNostr;
    }

    public List<SmallEmployee> getEmpList() {
        return empList;
    }

    public void setEmpList(List<SmallEmployee> empList) {
        this.empList = empList;
    }

    public void setEmpNostr(String empNostr) {
        this.empNostr = empNostr;
    }

    public List<String> getEmpNos() {
        return empNos;
    }

    public void setEmpNos(List<String> empNos) {
        this.empNos = empNos;
    }

    public List<String> getPositionLevels() {
        return positionLevels;
    }

    public void setPositionLevels(List<String> positionLevels) {
        this.positionLevels = positionLevels;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String[] getWorkDatess() {
        if (workDate != null) {
            return this.workDate.split(",");
        }
        return null;
    }

    public void setWorkDatess(String[] workDatess) {
        this.workDatess = workDatess;
    }

    public Integer getId() {
        return id;
    }

    public String[] getWorkDates() {
        return workDates;
    }

    public void setWorkDates(String[] workDates) {
        this.workDates = workDates;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
