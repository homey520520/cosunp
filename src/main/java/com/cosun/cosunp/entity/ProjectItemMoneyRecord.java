package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @Date: 2020/4/7 0007 上午 10:15
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ProjectItemMoneyRecord implements Serializable {

    private static final long serialVersionUID = -283006113078465699L;

    private Integer id;
    private Integer item_id;
    private Double hereMoney;
    private Date date;
    private String imageUrl;
    private String fapiaoNo;
    private String remark;

    private String dateStr;
    private Double hetongMoney;
    private Double weiHuiMoney;

    public Double getWeiHuiMoney() {
        return weiHuiMoney;
    }

    public void setWeiHuiMoney(Double weiHuiMoney) {
        this.weiHuiMoney = weiHuiMoney;
    }

    public Double getHetongMoney() {
        return hetongMoney;
    }

    public void setHetongMoney(Double hetongMoney) {
        this.hetongMoney = hetongMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Double getHereMoney() {
        return hereMoney;
    }

    public void setHereMoney(Double hereMoney) {
        this.hereMoney = hereMoney;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFapiaoNo() {
        return fapiaoNo;
    }

    public void setFapiaoNo(String fapiaoNo) {
        this.fapiaoNo = fapiaoNo;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }
}
