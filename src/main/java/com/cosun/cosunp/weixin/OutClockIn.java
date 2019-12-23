package com.cosun.cosunp.weixin;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @Date: 2019/9/20  上午 8:55
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class OutClockIn implements Serializable {

    private static final long serialVersionUID = -9191419776663022642L;

    private Integer id;
    private String userid;
    private Date clockInDate;

    private Date clockInDateAMOn;
    private String clockInAddrAMOn;
    private String amOnUrl;

    private Date clockInDatePMOn;
    private String clockInAddrPMOn;
    private String pmOnUrl;

    private Date clockInDateNMOn;
    private String clockInAddNMOn;
    private String nmOnUrl;


    private String clockInDateAMOnStr;
    private String clockInDatePMOnStr;
    private String clockInDateNMOnStr;
    private String clockInDateStr;


    private String weixinNo;
    public String getWeixinNo() {
        return weixinNo;
    }

    public void setWeixinNo(String weixinNo) {
        this.weixinNo = weixinNo;
    }

    public String getAmOnUrl() {
        return amOnUrl;
    }

    public void setAmOnUrl(String amOnUrl) {
        this.amOnUrl = amOnUrl;
    }

    public String getPmOnUrl() {
        return pmOnUrl;
    }

    public void setPmOnUrl(String pmOnUrl) {
        this.pmOnUrl = pmOnUrl;
    }

    public String getNmOnUrl() {
        return nmOnUrl;
    }

    public void setNmOnUrl(String nmOnUrl) {
        this.nmOnUrl = nmOnUrl;
    }

    public Date getClockInDate() {
        return clockInDate;
    }

    public void setClockInDate(Date clockInDate) {
        this.clockInDate = clockInDate;
    }

    public String getClockInDateStr() {
        return clockInDateStr;
    }

    public void setClockInDateStr(String clockInDateStr) {
        this.clockInDateStr = clockInDateStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getClockInDateAMOn() {
        return clockInDateAMOn;
    }

    public void setClockInDateAMOn(Date clockInDateAMOn) {
        this.clockInDateAMOn = clockInDateAMOn;
    }

    public String getClockInAddrAMOn() {
        return clockInAddrAMOn;
    }

    public void setClockInAddrAMOn(String clockInAddrAMOn) {
        this.clockInAddrAMOn = clockInAddrAMOn;
    }

    public Date getClockInDatePMOn() {
        return clockInDatePMOn;
    }

    public void setClockInDatePMOn(Date clockInDatePMOn) {
        this.clockInDatePMOn = clockInDatePMOn;
    }

    public String getClockInAddrPMOn() {
        return clockInAddrPMOn;
    }

    public void setClockInAddrPMOn(String clockInAddrPMOn) {
        this.clockInAddrPMOn = clockInAddrPMOn;
    }

    public Date getClockInDateNMOn() {
        return clockInDateNMOn;
    }

    public void setClockInDateNMOn(Date clockInDateNMOn) {
        this.clockInDateNMOn = clockInDateNMOn;
    }

    public String getClockInAddNMOn() {
        return clockInAddNMOn;
    }

    public void setClockInAddNMOn(String clockInAddNMOn) {
        this.clockInAddNMOn = clockInAddNMOn;
    }

    public String getClockInDateAMOnStr() {
        return clockInDateAMOnStr;
    }

    public void setClockInDateAMOnStr(String clockInDateAMOnStr) {
        this.clockInDateAMOnStr = clockInDateAMOnStr;
    }

    public String getClockInDatePMOnStr() {
        return clockInDatePMOnStr;
    }

    public void setClockInDatePMOnStr(String clockInDatePMOnStr) {
        this.clockInDatePMOnStr = clockInDatePMOnStr;
    }

    public String getClockInDateNMOnStr() {
        return clockInDateNMOnStr;
    }

    public void setClockInDateNMOnStr(String clockInDateNMOnStr) {
        this.clockInDateNMOnStr = clockInDateNMOnStr;
    }
}
