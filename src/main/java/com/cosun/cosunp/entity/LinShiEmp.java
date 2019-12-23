package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @Date: 2019/12/12  上午 10:17
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class LinShiEmp implements Serializable {

    private static final long serialVersionUID = 8933424089268820143L;

    private Integer id;
    private String name;
    private Integer sex;
    private Date incompdate;
    private Integer deptId;
    private Integer positionId;
    private Date birthDay;
    private String ID_NO;
    private String homeAddr;
    private Integer nativePla;
    private Integer nation;
    private String remark;
    private Integer workType;
    private Integer isQuit;

    private String sexStr;
    private String incompdateStr;
    private String deptName;
    private String positionName;
    private String birthDayStr;
    private String nativePlaStr;
    private String nationStr;

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Integer getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Integer isQuit) {
        this.isQuit = isQuit;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getIncompdateStr() {
        return incompdateStr;
    }

    public void setIncompdateStr(String incompdateStr) {
        this.incompdateStr = incompdateStr;
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

    public String getBirthDayStr() {
        return birthDayStr;
    }

    public void setBirthDayStr(String birthDayStr) {
        this.birthDayStr = birthDayStr;
    }

    public String getNativePlaStr() {
        return nativePlaStr;
    }

    public void setNativePlaStr(String nativePlaStr) {
        this.nativePlaStr = nativePlaStr;
    }

    public String getNationStr() {
        return nationStr;
    }

    public void setNationStr(String nationStr) {
        this.nationStr = nationStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getIncompdate() {
        return incompdate;
    }

    public void setIncompdate(Date incompdate) {
        this.incompdate = incompdate;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getID_NO() {
        return ID_NO;
    }

    public void setID_NO(String ID_NO) {
        this.ID_NO = ID_NO;
    }

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    public Integer getNativePla() {
        return nativePla;
    }

    public void setNativePla(Integer nativePla) {
        this.nativePla = nativePla;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
