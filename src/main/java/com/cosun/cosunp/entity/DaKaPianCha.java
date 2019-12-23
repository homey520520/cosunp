package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/11/1 0001 上午 9:51
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DaKaPianCha implements Serializable {

    private static final long serialVersionUID = 5122575576194629518L;

    private Integer id;
    private Integer pianChaMin;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPianChaMin() {
        return pianChaMin;
    }

    public void setPianChaMin(Integer pianChaMin) {
        this.pianChaMin = pianChaMin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
