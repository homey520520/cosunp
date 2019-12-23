package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/9/19 上午 10:09
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ClockInSetUp implements Serializable {

    private static final long serialVersionUID = -28841521194242514L;

    private Integer id;
    private Double outDays;
    private Integer dayClockInTimes;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getOutDays() {
        return outDays;
    }

    public void setOutDays(Double outDays) {
        this.outDays = outDays;
    }

    public Integer getDayClockInTimes() {
        return dayClockInTimes;
    }

    public void setDayClockInTimes(Integer dayClockInTimes) {
        this.dayClockInTimes = dayClockInTimes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
