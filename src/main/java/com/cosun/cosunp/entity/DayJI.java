package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/12/6 0006 下午 2:21
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DayJI implements Serializable {


    private static final long serialVersionUID = 49446661303889949L;

    private Integer dayJiAM;
    private Integer dayJiPM;
    private Double dayJiAMRemark;
    private Double dayJiPMRemark;
    private Double dayJiExHours;


    public Double getDayJiExHours() {
        return dayJiExHours;
    }

    public void setDayJiExHours(Double dayJiExHours) {
        this.dayJiExHours = dayJiExHours;
    }

    public Integer getDayJiAM() {
        return dayJiAM;
    }

    public void setDayJiAM(Integer dayJiAM) {
        this.dayJiAM = dayJiAM;
    }

    public Integer getDayJiPM() {
        return dayJiPM;
    }

    public void setDayJiPM(Integer dayJiPM) {
        this.dayJiPM = dayJiPM;
    }

    public Double getDayJiAMRemark() {
        return dayJiAMRemark;
    }

    public void setDayJiAMRemark(Double dayJiAMRemark) {
        this.dayJiAMRemark = dayJiAMRemark;
    }

    public Double getDayJiPMRemark() {
        return dayJiPMRemark;
    }

    public void setDayJiPMRemark(Double dayJiPMRemark) {
        this.dayJiPMRemark = dayJiPMRemark;
    }
}
