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
public class ZhongKongBean implements Serializable {


    private static final long serialVersionUID = 7955171496150175628L;

    private Integer id;
    private String yearMonth;
    private String EnrollNumber;
    private String dateStr;
    private String timeStr;
    private Date timeTime;
    private Date date;
    private Integer machineNum;

    private Integer VerifyMode;
    private Integer InOutMode;
    private Integer Year;
    private String Month;
    private Integer Day;
    private Integer Minute;
    private Integer Second;
    private Integer Hour;
    private String Time;

    private String positionLevel;
    private Integer workType;

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getEnrollNumber() {
        return EnrollNumber;
    }

    public void setEnrollNumber(String enrollNumber) {
        EnrollNumber = enrollNumber;
    }

    public Integer getMachineNum() {
        return machineNum;
    }

    public void setMachineNum(Integer machineNum) {
        this.machineNum = machineNum;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Date getTimeTime() {
        return timeTime;
    }

    public void setTimeTime(Date timeTime) {
        this.timeTime = timeTime;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVerifyMode() {
        return VerifyMode;
    }

    public void setVerifyMode(Integer verifyMode) {
        VerifyMode = verifyMode;
    }

    public Integer getInOutMode() {
        return InOutMode;
    }

    public void setInOutMode(Integer inOutMode) {
        InOutMode = inOutMode;
    }


    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public Integer getDay() {
        return Day;
    }

    public void setDay(Integer day) {
        Day = day;
    }

    public Integer getMinute() {
        return Minute;
    }

    public void setMinute(Integer minute) {
        Minute = minute;
    }

    public Integer getSecond() {
        return Second;
    }

    public void setSecond(Integer second) {
        Second = second;
    }

    public Integer getHour() {
        return Hour;
    }

    public void setHour(Integer hour) {
        Hour = hour;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
