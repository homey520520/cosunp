package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/11/5  下午 2:06
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class QYweixinSend implements Serializable {

    private static final long serialVersionUID = -6907759248335147185L;

    private Integer opencheckindatatype;
    private Long starttime;
    private Long endtime;
    private List<String> useridlist;


    public Integer getOpencheckindatatype() {
        return opencheckindatatype;
    }

    public void setOpencheckindatatype(Integer opencheckindatatype) {
        this.opencheckindatatype = opencheckindatatype;
    }

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

    public List<String> getUseridlist() {
        return useridlist;
    }

    public void setUseridlist(List<String> useridlist) {
        this.useridlist = useridlist;
    }
}
