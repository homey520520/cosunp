package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2020/3/5 0005 下午 2:24
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ReturnString implements Serializable {

    private static final long serialVersionUID = 3098295625025658559L;

    private String titleName;
    private String neirong;
    private String remark;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
