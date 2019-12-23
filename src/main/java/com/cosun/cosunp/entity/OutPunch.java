package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:homey Wong
 * @Date: 2019/11/16  下午 4:13
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class OutPunch implements Serializable {

    private static final long serialVersionUID = -6701336702244396168L;

    private Integer id;
    private String userid;
    private String groupname;
    private String checkin_type;
    private String exception_type;
    private Long checkin_time;
    private String location_title;
    private String location_detail;
    private String wifiname;
    private String notes;
    private String wifimac;
    private String[] mediaids; //

    private String checkin_timeStr;

    public String getCheckin_timeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (this.checkin_time != null) {
            Date date = new Date(this.checkin_time * 1000L);
            checkin_timeStr = sdf.format(date);
        }
        return checkin_timeStr;
    }

    public void setCheckin_timeStr(String checkin_timeStr) {
        this.checkin_timeStr = checkin_timeStr;
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

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getCheckin_type() {
        return checkin_type;
    }

    public void setCheckin_type(String checkin_type) {
        this.checkin_type = checkin_type;
    }

    public String getException_type() {
        return exception_type;
    }

    public void setException_type(String exception_type) {
        this.exception_type = exception_type;
    }

    public Long getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(Long checkin_time) {
        this.checkin_time = checkin_time;
    }

    public String getLocation_title() {
        return location_title;
    }

    public void setLocation_title(String location_title) {
        this.location_title = location_title;
    }

    public String getLocation_detail() {
        return location_detail;
    }

    public void setLocation_detail(String location_detail) {
        this.location_detail = location_detail;
    }

    public String getWifiname() {
        return wifiname;
    }

    public void setWifiname(String wifiname) {
        this.wifiname = wifiname;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWifimac() {
        return wifimac;
    }

    public void setWifimac(String wifimac) {
        this.wifimac = wifimac;
    }

    public String[] getMediaids() {
        return mediaids;
    }

    public void setMediaids(String[] mediaids) {
        this.mediaids = mediaids;
    }
}
