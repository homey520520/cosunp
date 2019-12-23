package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/7/22 0022 下午 5:03
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Extension implements Serializable {

    private static final long serialVersionUID = 2991867317957948479L;

    private Integer id;
    private String extension;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
