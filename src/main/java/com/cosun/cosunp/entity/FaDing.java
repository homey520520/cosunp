package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @Date: 2020/4/13 0013 下午 4:18
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FaDing implements Serializable {

    private Integer id;
    private Date faday;

    private String fadayStr;

    public Date getFaday() {
        return faday;
    }

    public void setFaday(Date faday) {
        this.faday = faday;
    }

    public String getFadayStr() {
        return fadayStr;
    }

    public void setFadayStr(String fadayStr) {
        this.fadayStr = fadayStr;
    }
}
