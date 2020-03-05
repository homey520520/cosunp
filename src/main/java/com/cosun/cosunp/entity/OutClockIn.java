package com.cosun.cosunp.entity;

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
    private String checkin_typeA;     //***   上班打卡
    private String exception_typeA;   //***   地点异常
    private String notesA;     //***  路上堵车，迟到了5分钟

    private Date clockInDatePMOn;
    private String clockInAddrPMOn;
    private String pmOnUrl;
    private String checkin_typeP;     //***   上班打卡
    private String exception_typeP;   //***   地点异常
    private String notesP;     //***  路上堵车，迟到了5分钟

    private Date clockInDateNMOn;
    private String clockInAddNMOn;
    private String nmOnUrl;
    private String checkin_typeN;     //***   上班打卡
    private String exception_typeN;   //***   地点异常
    private String notesN;     //***  路上堵车，迟到了5分钟


    private String clockInDateAMOnStr;
    private String clockInDatePMOnStr;
    private String clockInDateNMOnStr;
    private String clockInDateStr;


    private String weixinNo;
    public String getWeixinNo() {
        return weixinNo;
    }


    public String getCheckin_typeA() {
        return checkin_typeA;
    }

    public void setCheckin_typeA(String checkin_typeA) {
        this.checkin_typeA = checkin_typeA;
    }

    public String getException_typeA() {
        return exception_typeA;
    }

    public void setException_typeA(String exception_typeA) {
        this.exception_typeA = exception_typeA;
    }

    public String getNotesA() {
        return notesA;
    }

    public void setNotesA(String notesA) {
        this.notesA = notesA;
    }

    public String getCheckin_typeP() {
        return checkin_typeP;
    }

    public void setCheckin_typeP(String checkin_typeP) {
        this.checkin_typeP = checkin_typeP;
    }

    public String getException_typeP() {
        return exception_typeP;
    }

    public void setException_typeP(String exception_typeP) {
        this.exception_typeP = exception_typeP;
    }

    public String getNotesP() {
        return notesP;
    }

    public void setNotesP(String notesP) {
        this.notesP = notesP;
    }

    public String getCheckin_typeN() {
        return checkin_typeN;
    }

    public void setCheckin_typeN(String checkin_typeN) {
        this.checkin_typeN = checkin_typeN;
    }

    public String getException_typeN() {
        return exception_typeN;
    }

    public void setException_typeN(String exception_typeN) {
        this.exception_typeN = exception_typeN;
    }

    public String getNotesN() {
        return notesN;
    }

    public void setNotesN(String notesN) {
        this.notesN = notesN;
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
