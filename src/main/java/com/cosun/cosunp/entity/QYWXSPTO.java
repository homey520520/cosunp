package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2020/2/26  上午 9:54
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class QYWXSPTO implements Serializable {

    private static final long serialVersionUID = 8238029659624401892L;

    private Long starttime;
    private Long endtime;
    private Long next_spnum;

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public Long getNext_spnum() {
        return next_spnum;
    }

    public void setNext_spnum(Long next_spnum) {
        this.next_spnum = next_spnum;
    }
}
