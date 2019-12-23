package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/6/25 0025 上午 11:38
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FinanceSetUpData implements Serializable {

    private static final long serialVersionUID = -4539469468195439633L;

    private  Integer id;
    private Double basicWorkHours;
    private Double norAttendHoursSample;
    private Double norAttendSalarySample;
    private Double norExtraMutiple;
    private Double weekEndWorkMutiple;
    private Double legalWorkMutiple;
    private Double meritScoreSample;



    public Double getBasicWorkHours() {
        return basicWorkHours;
    }

    public void setBasicWorkHours(Double basicWorkHours) {
        this.basicWorkHours = basicWorkHours;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getNorAttendHoursSample() {
        return norAttendHoursSample;
    }

    public void setNorAttendHoursSample(Double norAttendHoursSample) {
        this.norAttendHoursSample = norAttendHoursSample;
    }

    public Double getNorAttendSalarySample() {
        return norAttendSalarySample;
    }

    public void setNorAttendSalarySample(Double norAttendSalarySample) {
        this.norAttendSalarySample = norAttendSalarySample;
    }

    public Double getNorExtraMutiple() {
        return norExtraMutiple;
    }

    public void setNorExtraMutiple(Double norExtraMutiple) {
        this.norExtraMutiple = norExtraMutiple;
    }

    public Double getWeekEndWorkMutiple() {
        return weekEndWorkMutiple;
    }

    public void setWeekEndWorkMutiple(Double weekEndWorkMutiple) {
        this.weekEndWorkMutiple = weekEndWorkMutiple;
    }

    public Double getLegalWorkMutiple() {
        return legalWorkMutiple;
    }

    public void setLegalWorkMutiple(Double legalWorkMutiple) {
        this.legalWorkMutiple = legalWorkMutiple;
    }

    public Double getMeritScoreSample() {
        return meritScoreSample;
    }

    public void setMeritScoreSample(Double meritScoreSample) {
        this.meritScoreSample = meritScoreSample;
    }
}
