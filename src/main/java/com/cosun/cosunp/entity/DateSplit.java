package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.sql.Time;

/**
 * @author:homey Wong
 * @Date: 2019/10/29  上午 11:41
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DateSplit implements Serializable {

    private static final long serialVersionUID = 458723323935454104L;

    private String date;
    private Time dateFrom;
    private Time dateEnd;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Time getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Time dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Time getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Time dateEnd) {
        this.dateEnd = dateEnd;
    }
}
