package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2020/3/23  下午 4:30
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ProjectHeadOrder implements Serializable {


    private static final long serialVersionUID = 8640041627410617190L;

    private Integer id;
    private Integer head_Id;
    private Integer province;
    private String customerName;
    private String orderNo;
    private String remark;

    private String deliverStr;
    private Double yiShouAmount;


    private String provinceStr;

    public String getProvinceStr() {
        return provinceStr;
    }

    public void setProvinceStr(String provinceStr) {
        this.provinceStr = provinceStr;
    }

    public String getDeliverStr() {
        return deliverStr;
    }

    public void setDeliverStr(String deliverStr) {
        this.deliverStr = deliverStr;
    }

    public Double getYiShouAmount() {
        return yiShouAmount;
    }

    public void setYiShouAmount(Double yiShouAmount) {
        this.yiShouAmount = yiShouAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHead_Id() {
        return head_Id;
    }

    public void setHead_Id(Integer head_Id) {
        this.head_Id = head_Id;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
