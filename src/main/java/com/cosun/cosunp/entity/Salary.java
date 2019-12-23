package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/6/15 0015 上午 11:25
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Salary implements Serializable {

    private static final long serialVersionUID = 3941220683407513988L;


    private String name;
    private String empNo;
    private String inComDate;
    private Double compreSalary;
    private Double posSalary;
    private Double jobSalary;
    private Double meritSalary;
    private Integer state;
    private String remark;


    public String getInComDate() {
        return inComDate;
    }

    public void setInComDate(String inComDate) {
        this.inComDate = inComDate;
    }

    public Integer getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Double getCompreSalary() {
        return compreSalary;
    }

    public void setCompreSalary(Double compreSalary) {
        this.compreSalary = compreSalary;
    }

    public Double getPosSalary() {
        return posSalary;
    }

    public void setPosSalary(Double posSalary) {
        this.posSalary = posSalary;
    }

    public Double getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(Double jobSalary) {
        this.jobSalary = jobSalary;
    }

    public Double getMeritSalary() {
        return meritSalary;
    }

    public void setMeritSalary(Double meritSalary) {
        this.meritSalary = meritSalary;
    }
}
