package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2020/4/11  上午 9:31
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DaysSet implements Serializable {

    private static final long serialVersionUID = 103694804089062220L;

    private Integer id;
    private Integer zhanchaDays;
    private Integer outDrawDays;
    private Integer fanAnConfDays;
    private Integer prodDays;
    private Integer anzhuangDays;
    private Integer yanshouDays;
    private Integer jiesuanDays;
    private Integer type;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZhanchaDays() {
        return zhanchaDays;
    }

    public void setZhanchaDays(Integer zhanchaDays) {
        this.zhanchaDays = zhanchaDays;
    }

    public Integer getOutDrawDays() {
        return outDrawDays;
    }

    public void setOutDrawDays(Integer outDrawDays) {
        this.outDrawDays = outDrawDays;
    }

    public Integer getFanAnConfDays() {
        return fanAnConfDays;
    }

    public void setFanAnConfDays(Integer fanAnConfDays) {
        this.fanAnConfDays = fanAnConfDays;
    }

    public Integer getProdDays() {
        return prodDays;
    }

    public void setProdDays(Integer prodDays) {
        this.prodDays = prodDays;
    }

    public Integer getAnzhuangDays() {
        return anzhuangDays;
    }

    public void setAnzhuangDays(Integer anzhuangDays) {
        this.anzhuangDays = anzhuangDays;
    }

    public Integer getYanshouDays() {
        return yanshouDays;
    }

    public void setYanshouDays(Integer yanshouDays) {
        this.yanshouDays = yanshouDays;
    }

    public Integer getJiesuanDays() {
        return jiesuanDays;
    }

    public void setJiesuanDays(Integer jiesuanDays) {
        this.jiesuanDays = jiesuanDays;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}