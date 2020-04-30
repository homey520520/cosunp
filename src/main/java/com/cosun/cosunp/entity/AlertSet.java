package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.sql.Time;

/**
 * @author:homey Wong
 * @Date: 2020/4/10  下午 3:54
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class AlertSet implements Serializable {

    private static final long serialVersionUID = 8708102843096067330L;

    private Integer id;
    private Integer norAlertDays;
    private Integer impAlertDays;
    private Time alertTime;
    private Integer alertTimes;
    private Integer alertInterHours;
    private Integer alertMode;
    private String alertToPositions;
    private String remark;

    private String alertTimeStr;
    private String alertModeStr;
    private String[] alertToPositionsArr;

    private String[] alertToPositionsArr2;

    public String[] getAlertToPositionsArr2() {
        return alertToPositionsArr2;
    }

    public void setAlertToPositionsArr2(String[] alertToPositionsArr2) {
        this.alertToPositionsArr2 = alertToPositionsArr2;
    }

    public String getAlertTimeStr() {
        return alertTimeStr;
    }

    public void setAlertTimeStr(String alertTimeStr) {
        this.alertTimeStr = alertTimeStr;
    }

    public String getAlertModeStr() {
        if (alertMode != null) {
            if (alertMode == 0) {
                return "暴力型";
            } else if (alertMode == 1) {
                return "斯文型";
            } else if (alertMode == 2) {
                return "搞笑型";
            }
        }
        return alertModeStr;
    }

    public void setAlertModeStr(String alertModeStr) {
        this.alertModeStr = alertModeStr;
    }

    public String[] getAlertToPositionsArr() {
        if (alertToPositions != null ) {
            return alertToPositions.split(",");
        }
        return alertToPositionsArr;
    }

    public void setAlertToPositionsArr(String[] alertToPositionsArr) {
        this.alertToPositionsArr = alertToPositionsArr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNorAlertDays() {
        return norAlertDays;
    }

    public void setNorAlertDays(Integer norAlertDays) {
        this.norAlertDays = norAlertDays;
    }

    public Integer getImpAlertDays() {
        return impAlertDays;
    }

    public void setImpAlertDays(Integer impAlertDays) {
        this.impAlertDays = impAlertDays;
    }

    public Time getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Time alertTime) {
        this.alertTime = alertTime;
    }

    public Integer getAlertTimes() {
        return alertTimes;
    }

    public void setAlertTimes(Integer alertTimes) {
        this.alertTimes = alertTimes;
    }

    public Integer getAlertInterHours() {
        return alertInterHours;
    }

    public void setAlertInterHours(Integer alertInterHours) {
        this.alertInterHours = alertInterHours;
    }

    public Integer getAlertMode() {
        return alertMode;
    }

    public void setAlertMode(Integer alertMode) {
        this.alertMode = alertMode;
    }

    public String getAlertToPositions() {
        return alertToPositions;
    }

    public void setAlertToPositions(String alertToPositions) {
        this.alertToPositions = alertToPositions;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
