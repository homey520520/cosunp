package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/8/2 0002 上午 10:15
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class SmallEmployee implements Serializable {


    private static final long serialVersionUID = -4298851204398990647L;

    private String empNo;
    private String name;

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
