package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/12/25  上午 9:24
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ReturnBean implements Serializable {

    private static final long serialVersionUID = -6557892900371055656L;

    private Integer id;
    private String dayNum;
    private String dayNumRemark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDayNum() {
        return dayNum;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public String getDayNumRemark() {
        return dayNumRemark;
    }

    public void setDayNumRemark(String dayNumRemark) {
        this.dayNumRemark = dayNumRemark;
    }
}
