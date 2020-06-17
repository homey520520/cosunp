package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/2/26 0026 上午 9:54
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class QYWXSPFROM implements Serializable {


    private static final long serialVersionUID = 7502024218764366502L;


    private String spname;
    private String apply_name;
    private String apply_org;
    private Integer sp_status;
    private Long sp_num;
    private String[] mediaids;
    private Integer apply_time;
    private String apply_user_id;
    private String bukaTime;
    private String bukaYearM;
    private String bukaReason;
    private String exceptReason;
    private String apply_timeStr;
    private Long bukaTimeLong;
    private Integer timeunit;
    private Integer leave_type;
    private String leave_typeStr;
    private Long start_time;
    private String start_timeStr;
    private Long end_time;
    private String end_timeStr;
    private Integer duration;
    private String reason;

    private String outAddr;

    public String getOutAddr() {
        return outAddr;
    }

    public void setOutAddr(String outAddr) {
        this.outAddr = outAddr;
    }

    public String getStart_timeStr() {
        return start_timeStr;
    }

    public void setStart_timeStr(String start_timeStr) {
        this.start_timeStr = start_timeStr;
    }

    public String getEnd_timeStr() {
        return end_timeStr;
    }

    public void setEnd_timeStr(String end_timeStr) {
        this.end_timeStr = end_timeStr;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLeave_typeStr() {
        return leave_typeStr;
    }

    public void setLeave_typeStr(String leave_typeStr) {
        this.leave_typeStr = leave_typeStr;
    }

    public Integer getTimeunit() {
        return timeunit;
    }

    public void setTimeunit(Integer timeunit) {
        this.timeunit = timeunit;
    }

    public Integer getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(Integer leave_type) {
        this.leave_type = leave_type;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getBukaTimeLong() {
        return bukaTimeLong;
    }

    public void setBukaTimeLong(Long bukaTimeLong) {
        this.bukaTimeLong = bukaTimeLong;
    }

    public String getSpname() {
        return spname;
    }

    public void setSpname(String spname) {
        this.spname = spname;
    }

    public String getApply_name() {
        return apply_name;
    }

    public void setApply_name(String apply_name) {
        this.apply_name = apply_name;
    }

    public String getApply_org() {
        return apply_org;
    }

    public void setApply_org(String apply_org) {
        this.apply_org = apply_org;
    }

    public Integer getSp_status() {
        return sp_status;
    }

    public void setSp_status(Integer sp_status) {
        this.sp_status = sp_status;
    }

    public Long getSp_num() {
        return sp_num;
    }

    public void setSp_num(Long sp_num) {
        this.sp_num = sp_num;
    }

    public String[] getMediaids() {
        return mediaids;
    }

    public void setMediaids(String[] mediaids) {
        this.mediaids = mediaids;
    }

    public Integer getApply_time() {
        return apply_time;
    }

    public void setApply_time(Integer apply_time) {
        this.apply_time = apply_time;
    }

    public String getApply_user_id() {
        return apply_user_id;
    }

    public void setApply_user_id(String apply_user_id) {
        this.apply_user_id = apply_user_id;
    }

    public String getBukaTime() {
        return bukaTime;
    }

    public void setBukaTime(String bukaTime) {
        this.bukaTime = bukaTime;
    }

    public String getBukaYearM() {
        return bukaYearM;
    }

    public void setBukaYearM(String bukaYearM) {
        this.bukaYearM = bukaYearM;
    }

    public String getBukaReason() {
        return bukaReason;
    }

    public void setBukaReason(String bukaReason) {
        this.bukaReason = bukaReason;
    }

    public String getExceptReason() {
        return exceptReason;
    }

    public void setExceptReason(String exceptReason) {
        this.exceptReason = exceptReason;
    }

    public String getApply_timeStr() {
        return apply_timeStr;
    }

    public void setApply_timeStr(String apply_timeStr) {
        this.apply_timeStr = apply_timeStr;
    }
}
