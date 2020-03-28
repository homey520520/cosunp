package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2020/3/23  下午 4:30
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ProjectHead implements Serializable {

    private static final long serialVersionUID = -3386399660717660326L;

    private Integer id;
    private String projectName;
    private String salor;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSalor() {
        return salor;
    }

    public void setSalor(String salor) {
        this.salor = salor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
