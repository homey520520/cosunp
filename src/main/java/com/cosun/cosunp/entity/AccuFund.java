package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/12/11  下午 1:51
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class AccuFund implements Serializable {

    private static final long serialVersionUID = 6337369112700998109L;

    private Integer id;
    private String name;
    private String ID_NO;
    private Double accuFundFee;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID_NO() {
        return ID_NO;
    }

    public void setID_NO(String ID_NO) {
        this.ID_NO = ID_NO;
    }

    public Double getAccuFundFee() {
        return accuFundFee;
    }

    public void setAccuFundFee(Double accuFundFee) {
        this.accuFundFee = accuFundFee;
    }
}
