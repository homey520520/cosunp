package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/10/10  上午 9:21
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class KQBean implements Serializable {

    private static final long serialVersionUID = -4963980055770899153L;

    private Integer machineNum;
    private Integer id;
    private Integer deptId;
    private String empNo;
    private String yearMonth;
    private String dateStr;
    private Integer week;
    private String timeStr;
    private Integer clockResult;
    private Double extWorkHours;
    private String remark;
    private Integer clockResultByRenShi;
    private String havePinShi;

    private String positionLevel;
    private Integer workType;

    public Integer getMachineNum() {
        return machineNum;
    }

    public void setMachineNum(Integer machineNum) {
        this.machineNum = machineNum;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    private Integer empId;

    private String nameReal;

    private String aOnTime;
    private String aOnRemark;

    private String aOffTime;
    private String aOffRemark;

    private String pOnTime;
    private String pOnRemark;

    private String pOffTime;
    private String pOffRemark;

    private String extWorkOnTime;
    private String extWorkOnRemark;

    private String extWorkOffTime;
    private String extWorkOffRemark;

    private Integer rensheCheck;//人事复核状态，代表某条数据启用
    private String enrollNumber;

    private String name; //姓名
    private String deptName; //部门名
    private String weekStr;
    private String clockResultStr;
    private String rensheCheckStr;
    private String[] times;
    private List<Time> timeList;


    // 分页属性
    private int currentPage = 1;// 用于接收页面传过来的当前页数
    private int maxPage;// 最大页数
    private int recordCount;// 总记录数
    private int pageSize = 10;
    private int currentPageTotalNum;
    private String sortMethod;
    private String sortByName;


    private List<Integer> deptIds;
    private List<Integer> positionIds;
    private List<Integer> nameIds;
    private List<Integer> workTypes;
    private List<String> clockDates;


    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getHavePinShi() {
        return havePinShi;
    }

    public void setHavePinShi(String havePinShi) {
        this.havePinShi = havePinShi;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getNameReal() {
        return nameReal;
    }

    public void setNameReal(String nameReal) {
        this.nameReal = nameReal;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<Integer> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<Integer> positionIds) {
        this.positionIds = positionIds;
    }

    public List<Integer> getNameIds() {
        return nameIds;
    }

    public void setNameIds(List<Integer> nameIds) {
        this.nameIds = nameIds;
    }

    public List<Integer> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<Integer> workTypes) {
        this.workTypes = workTypes;
    }

    public List<String> getClockDates() {
        return clockDates;
    }

    public void setClockDates(List<String> clockDates) {
        this.clockDates = clockDates;
    }

    public Integer getClockResultByRenShi() {
        return clockResultByRenShi;
    }

    public void setClockResultByRenShi(Integer clockResultByRenShi) {
        this.clockResultByRenShi = clockResultByRenShi;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageTotalNum() {
        if (this.currentPage != 0)
            return (currentPage - 1) * pageSize;
        return 0;
    }

    public void setCurrentPageTotalNum(int currentPageTotalNum) {
        this.currentPageTotalNum = currentPageTotalNum;
    }

    public String getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getaOnRemark() {
        return aOnRemark;
    }

    public void setaOnRemark(String aOnRemark) {
        this.aOnRemark = aOnRemark;
    }

    public String getaOffRemark() {
        return aOffRemark;
    }

    public void setaOffRemark(String aOffRemark) {
        this.aOffRemark = aOffRemark;
    }

    public String getpOnRemark() {
        return pOnRemark;
    }

    public void setpOnRemark(String pOnRemark) {
        this.pOnRemark = pOnRemark;
    }

    public String getpOffRemark() {
        return pOffRemark;
    }

    public void setpOffRemark(String pOffRemark) {
        this.pOffRemark = pOffRemark;
    }

    public String getExtWorkOnRemark() {
        return extWorkOnRemark;
    }

    public void setExtWorkOnRemark(String extWorkOnRemark) {
        this.extWorkOnRemark = extWorkOnRemark;
    }

    public String getExtWorkOffRemark() {
        return extWorkOffRemark;
    }

    public void setExtWorkOffRemark(String extWorkOffRemark) {
        this.extWorkOffRemark = extWorkOffRemark;
    }

    public String[] getTimes() {
        return times;
    }

    public void setTimes(String[] times) {
        this.times = times;
    }

    public List<Time> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Time> timeList) {
        this.timeList = timeList;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(String enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Integer getClockResult() {
        return clockResult;
    }

    public void setClockResult(Integer clockResult) {
        this.clockResult = clockResult;
    }

    public Double getExtWorkHours() {
        return extWorkHours;
    }

    public void setExtWorkHours(Double extWorkHours) {
        this.extWorkHours = extWorkHours;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getaOnTime() {
        return aOnTime;
    }

    public void setaOnTime(String aOnTime) {
        this.aOnTime = aOnTime;
    }

    public String getaOffTime() {
        return aOffTime;
    }

    public void setaOffTime(String aOffTime) {
        this.aOffTime = aOffTime;
    }

    public String getpOnTime() {
        return pOnTime;
    }

    public void setpOnTime(String pOnTime) {
        this.pOnTime = pOnTime;
    }

    public String getpOffTime() {
        return pOffTime;
    }

    public void setpOffTime(String pOffTime) {
        this.pOffTime = pOffTime;
    }

    public String getExtWorkOnTime() {
        return extWorkOnTime;
    }

    public void setExtWorkOnTime(String extWorkOnTime) {
        this.extWorkOnTime = extWorkOnTime;
    }

    public String getExtWorkOffTime() {
        return extWorkOffTime;
    }

    public void setExtWorkOffTime(String extWorkOffTime) {
        this.extWorkOffTime = extWorkOffTime;
    }

    public Integer getRensheCheck() {
        return rensheCheck;
    }

    public void setRensheCheck(Integer rensheCheck) {
        this.rensheCheck = rensheCheck;
    }

    public String getWeekStr() {
        if (this.week != null) {
            if (week.equals(1)) {
                return "星期一";
            } else if (week.equals(2)) {
                return "星期二";
            } else if (week.equals(3)) {
                return "星期三";
            } else if (week.equals(4)) {
                return "星期四";
            } else if (week.equals(5)) {
                return "星期五";
            } else if (week.equals(6)) {
                return "星期六";
            } else if (week.equals(7)) {
                return "星期天";
            }
        }
        return weekStr;
    }

    public void setWeekStr(String weekStr) {
        this.weekStr = weekStr;
    }

    public String getClockResultStr() {
        if (this.clockResult != null) {
            if (clockResult == 1) {
                return "正常出勤";
            } else if (clockResult == 2) {
                return "因公外出(省内)";
            } else if (clockResult == 3) {
                return "带薪年假";
            } else if (clockResult == 4) {
                return "法定节假日";
            } else if (clockResult == 5) {
                return "放假";
            } else if (clockResult == 6) {
                return "请假(事假)";
            } else if (clockResult == 7) {
                return "未正常打卡";
            } else if (clockResult == 8) {
                return "旷工";
            } else if (clockResult == 9) {
                return "法定节假日加班";
            } else if (clockResult == 10) {
                return "无此人打卡记录";
            } else if (clockResult == 11) {
                return "请假(病假)";
            } else if (clockResult == 12) {
                return "因公外出(省外)";
            } else if (clockResult == 13) {
                return "夜班";
            } else if (clockResult == 16) {
                return "正班未满勤但有请假条";
            } else if (clockResult == 17) {
                return "正班未满勤无请假条";
            } else if (clockResult == 18) {
                return "婚假";
            } else if (clockResult == 19) {
                return "丧假";
            } else if (clockResult == 20) {
                return "产假";
            } else if (clockResult == 21) {
                return "陪产假";
            }
        }
        return clockResultStr;
    }

    public void setClockResultStr(String clockResultStr) {
        this.clockResultStr = clockResultStr;
    }

    public String getRensheCheckStr() {
        if (rensheCheck != null) {
            if (rensheCheck == 1) {
                return "已复核";
            } else {
                return "未复核";
            }
        }
        return rensheCheckStr;
    }

    public void setRensheCheckStr(String rensheCheckStr) {
        this.rensheCheckStr = rensheCheckStr;
    }
}
