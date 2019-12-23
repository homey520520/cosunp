package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/12/11  下午 1:46
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Insurance implements Serializable {

    private static final long serialVersionUID = 6253490103244192177L;

    private Integer id;
    private String name;
    private String ID_NO;
    private Double endowInsur;
    private Double medicalInsur;
    private Double unWorkInsur;

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

    public Double getEndowInsur() {
        return endowInsur;
    }

    public void setEndowInsur(Double endowInsur) {
        this.endowInsur = endowInsur;
    }

    public Double getMedicalInsur() {
        return medicalInsur;
    }

    public void setMedicalInsur(Double medicalInsur) {
        this.medicalInsur = medicalInsur;
    }

    public Double getUnWorkInsur() {
        return unWorkInsur;
    }

    public void setUnWorkInsur(Double unWorkInsur) {
        this.unWorkInsur = unWorkInsur;
    }
}
