package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/9/19 0019 下午 3:07
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class GongZhongHao implements Serializable {

    private static final long serialVersionUID = -5041859208046044726L;

    private Integer id;
    private String empNo;
    private String gongzhonghaoId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getGongzhonghaoId() {
        return gongzhonghaoId;
    }

    public void setGongzhonghaoId(String gongzhonghaoId) {
        this.gongzhonghaoId = gongzhonghaoId;
    }
}
