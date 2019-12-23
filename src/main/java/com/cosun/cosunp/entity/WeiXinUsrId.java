package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/11/6 0006 上午 10:13
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WeiXinUsrId implements Serializable {

    private static final long serialVersionUID = -7651124652897525204L;

    private Integer id;
    private String userid;
    private String name;
    private String empNo;
    private List<Integer> department;


    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getDepartment() {
        return department;
    }

    public void setDepartment(List<Integer> department) {
        this.department = department;
    }
}
