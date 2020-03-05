package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @Date: 2019/10/7  下午 1:37
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ZhongKongBeanQY implements Serializable {


    private static final long serialVersionUID = 7955171496150175628L;

    private Integer id;
    private String yearMonth;
    private String userId;
    private String dateStr;
    private String timeStr;
    private Date timeTime;
    private Date date;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Date getTimeTime() {
        return timeTime;
    }

    public void setTimeTime(Date timeTime) {
        this.timeTime = timeTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
