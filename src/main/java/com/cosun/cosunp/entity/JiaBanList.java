package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/12/26  上午 10:59
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class JiaBanList implements Serializable {

    private static final long serialVersionUID = -5019403452847318612L;

    private Integer id;
    private String empNo;
    private String yearMonth;
    private List<String> dayJiStr;

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

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public List<String> getDayJiStr() {
        return dayJiStr;
    }

    public void setDayJiStr(List<String> dayJiStr) {
        this.dayJiStr = dayJiStr;
    }
}



