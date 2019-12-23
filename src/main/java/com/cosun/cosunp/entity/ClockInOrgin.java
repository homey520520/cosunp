package com.cosun.cosunp.entity;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/16 0016 上午 9:59
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ClockInOrgin {

    private Integer attendMachineId;
    private String employeeName;
    private String deptName;
    private String dateStr;
    private String[] times;
    private List<Time> timeList;
    private String timeStr;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public List<Time> getTimeList() {
       if(this.times!=null) {
           timeList = new ArrayList<Time>();
           for(int i = 0 ;i < times.length;i++) {
               SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
               try {
                   Date d = format.parse(times[i]);
                   timeList.add(new java.sql.Time(d.getTime()));
               }catch (Exception e) {
                   e.printStackTrace();
               }

           }
           return timeList;
       }
       return timeList;
    }

    public void setTimeList(List<Time> timeList) {
        this.timeList = timeList;
    }

    public Integer getAttendMachineId() {
        return attendMachineId;
    }

    public void setAttendMachineId(Integer attendMachineId) {
        this.attendMachineId = attendMachineId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String[] getTimes() {
        return times;
    }

    public void setTimes(String[] times) {
        this.times = times;
    }
}
