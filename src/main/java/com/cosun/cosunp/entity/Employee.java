package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/3/5 0005 上午 11:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 3941220683407513983L;

    private Integer outDanId;

    private String outDanIdStr;

    private String EnrollNumber;

    private String enrollNumber1;

    private String yearMonth;
    private String dateStr;
    private String timeStr;

    private Integer id;
    private String name;
    private Integer sex;
    private String sexStr;
    private Integer deptId;
    private String empNo;
    private Integer positionId;
    private Date incompdate;
    private Date conExpDate;
    private Date birthDay;
    private String ID_NO;
    private Integer nativePla;
    private String homeAddr;
    private Date valiPeriodOfID;
    private Integer nation;
    private Integer marriaged;
    private String contactPhone;
    private Integer educationLe;
    private Integer screAgreement;
    private Integer healthCerti;
    private Integer sateListAndLeaCerti;
    private Integer otherCerti;
    private Integer positionAttrId;
    private Integer type;
    private Integer workType;
    private Integer state;
    private Integer isQuit;
    private String isQuitStr;


    private String gongzhonghaoId;
    private List<Integer> isgongzhonghaoBangDing;
    private List<Integer> isZhongKongBangDing;

    private String remark;
    private String educationLeUrl;
    private String sateListAndLeaCertiUrl;
    private String otherCertiUrl;

    private Integer educationLeUrlInt;
    private Integer sateListAndLeaCertiUrlInt;
    private Integer otherCertiUrlInt;


    private String username;
    private String passowrd;
    private Double posSalary;
    private Double jobSalary;
    private Double meritSalary;
    private Double compreSalary;


    private String marriagedStr;
    private String educationLeStr;
    private String screAgreementStr;
    private String healthCertiStr;
    private String sateListAndLeaCertiStr;
    private String otherCertiStr;
    private String birthDayStr;
    private String conExpDateStr;
    private String incompdateStr;
    private String positionAttrIdStr;
    private String valiPeriodOfIDStr;


    private String incomdateStr;
    private String deptName;
    private String positionName;
    private String positionLevel;
    private String endLeaveStr;
    private String beginLeaveStr;
    private List<Integer> deptIds;
    private List<Integer> sexIds;
    private List<Integer> positionIds;
    private List<Integer> ids;
    private List<Integer> nameIds;
    private List<Integer> nameIds2;



    private List<Integer> workTypes;
    private List<Integer> isQuits;
    private List<String> empNos;
    private List<String> clockDateArray;
    private List<String> clockDates;
    private String startIncomDateStr;
    private String endIncomDateStr;
    private String deptIdsstr;
    private String positionIdsstr;
    private String nativePlaStr;
    private String nationStr;
    private Double allMoney;

    private String namea;
    private String passowrd22;
    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;
    private String sortMethod;
    private String sortByName;

    private Integer salaryId;

    private String stateStr;

    private String outLeaveSheet;
    private String clockInDateStr;
    private String clockInDateAMOnStr;
    private String clockInAddrAMOn;
    private String amOnUrl;
    private String clockInDatePMOnStr;
    private String clockInAddrPMOn;
    private String pmOnUrl;
    private String clockInDateNMOnStr;
    private String clockInAddNMOn;
    private String nmOnUrl;

    private Integer amOnUrlInt;
    private Integer pmOnUrlInt;
    private Integer nmOnUrlInt;

    public List<Integer> getNameIds2() {
        return nameIds2;
    }

    public void setNameIds2(List<Integer> nameIds2) {
        this.nameIds2 = nameIds2;
    }

    public List<String> getClockDates() {
        return clockDates;
    }

    public void setClockDates(List<String> clockDates) {
        this.clockDates = clockDates;
    }

    public String getOutDanIdStr() {
        if (this.outDanId == null)
            return "无";
        return "有";
    }

    public void setOutDanIdStr(String outDanIdStr) {
        this.outDanIdStr = outDanIdStr;
    }

    public Integer getOutDanId() {
        return outDanId;
    }

    public void setOutDanId(Integer outDanId) {
        this.outDanId = outDanId;
    }

    public List<String> getClockDateArray() {
        return clockDateArray;
    }

    public void setClockDateArray(List<String> clockDateArray) {
        this.clockDateArray = clockDateArray;
    }

    public String getEnrollNumber() {
        return EnrollNumber;
    }

    public void setEnrollNumber(String enrollNumber) {
        EnrollNumber = enrollNumber;
    }

    public String getEnrollNumber1() {
        return enrollNumber1;
    }

    public void setEnrollNumber1(String enrollNumber1) {
        this.enrollNumber1 = enrollNumber1;
    }

    public List<Integer> getIsZhongKongBangDing() {
        return isZhongKongBangDing;
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

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public void setIsZhongKongBangDing(List<Integer> isZhongKongBangDing) {
        this.isZhongKongBangDing = isZhongKongBangDing;
    }


    public Integer getAmOnUrlInt() {
        return amOnUrlInt;
    }

    public void setAmOnUrlInt(Integer amOnUrlInt) {
        this.amOnUrlInt = amOnUrlInt;
    }

    public Integer getPmOnUrlInt() {
        return pmOnUrlInt;
    }

    public void setPmOnUrlInt(Integer pmOnUrlInt) {
        this.pmOnUrlInt = pmOnUrlInt;
    }

    public Integer getNmOnUrlInt() {
        return nmOnUrlInt;
    }

    public void setNmOnUrlInt(Integer nmOnUrlInt) {
        this.nmOnUrlInt = nmOnUrlInt;
    }

    public String getOutLeaveSheet() {
        return outLeaveSheet;
    }

    public void setOutLeaveSheet(String outLeaveSheet) {
        this.outLeaveSheet = outLeaveSheet;
    }

    public String getClockInDateStr() {
        return clockInDateStr;
    }

    public void setClockInDateStr(String clockInDateStr) {
        this.clockInDateStr = clockInDateStr;
    }

    public String getClockInDateAMOnStr() {
        return clockInDateAMOnStr;
    }

    public void setClockInDateAMOnStr(String clockInDateAMOnStr) {
        this.clockInDateAMOnStr = clockInDateAMOnStr;
    }

    public String getClockInAddrAMOn() {
        return clockInAddrAMOn;
    }

    public void setClockInAddrAMOn(String clockInAddrAMOn) {
        this.clockInAddrAMOn = clockInAddrAMOn;
    }

    public String getAmOnUrl() {
        return amOnUrl;
    }

    public void setAmOnUrl(String amOnUrl) {
        this.amOnUrl = amOnUrl;
    }

    public String getClockInDatePMOnStr() {
        return clockInDatePMOnStr;
    }

    public void setClockInDatePMOnStr(String clockInDatePMOnStr) {
        this.clockInDatePMOnStr = clockInDatePMOnStr;
    }

    public String getClockInAddrPMOn() {
        return clockInAddrPMOn;
    }

    public void setClockInAddrPMOn(String clockInAddrPMOn) {
        this.clockInAddrPMOn = clockInAddrPMOn;
    }

    public String getPmOnUrl() {
        return pmOnUrl;
    }

    public void setPmOnUrl(String pmOnUrl) {
        this.pmOnUrl = pmOnUrl;
    }

    public String getClockInDateNMOnStr() {
        return clockInDateNMOnStr;
    }

    public void setClockInDateNMOnStr(String clockInDateNMOnStr) {
        this.clockInDateNMOnStr = clockInDateNMOnStr;
    }

    public String getClockInAddNMOn() {
        return clockInAddNMOn;
    }

    public void setClockInAddNMOn(String clockInAddNMOn) {
        this.clockInAddNMOn = clockInAddNMOn;
    }

    public String getNmOnUrl() {
        return nmOnUrl;
    }

    public void setNmOnUrl(String nmOnUrl) {
        this.nmOnUrl = nmOnUrl;
    }

    public List<Integer> getIsgongzhonghaoBangDing() {
        return isgongzhonghaoBangDing;
    }

    public void setIsgongzhonghaoBangDing(List<Integer> isgongzhonghaoBangDing) {
        this.isgongzhonghaoBangDing = isgongzhonghaoBangDing;
    }

    public String getGongzhonghaoId() {
        return gongzhonghaoId;
    }

    public void setGongzhonghaoId(String gongzhonghaoId) {
        this.gongzhonghaoId = gongzhonghaoId;
    }

    public Integer getIsQuit() {
        return isQuit;
    }

    public void setIsQuit(Integer isQuit) {
        this.isQuit = isQuit;
    }

    public List<Integer> getIsQuits() {
        return isQuits;
    }

    public void setIsQuits(List<Integer> isQuits) {
        this.isQuits = isQuits;
    }

    public String getIsQuitStr() {
        if (isQuit != null) {
            if (this.isQuit == 0) {
                return "在职";
            } else if (this.isQuit == 1) {
                return "离职";
            }
        }
        return isQuitStr;
    }

    public void setIsQuitStr(String isQuitStr) {
        this.isQuitStr = isQuitStr;
    }

    public Integer getEducationLeUrlInt() {
        if (this.getEducationLeUrl() == null || this.getEducationLeUrl().trim().length() <= 0 || "0".equals(this.getEducationLeUrl().trim())) {
            return 0;
        }
        return 1;
    }

    public void setEducationLeUrlInt(Integer educationLeUrlInt) {
        this.educationLeUrlInt = educationLeUrlInt;
    }

    public Integer getSateListAndLeaCertiUrlInt() {
        if (this.getSateListAndLeaCertiUrl() == null || this.getSateListAndLeaCertiUrl().trim().length() <= 0 || "0".equals(this.getSateListAndLeaCertiUrl().trim())) {
            return 0;
        }
        return 1;
    }

    public void setSateListAndLeaCertiUrlInt(Integer sateListAndLeaCertiUrlInt) {
        this.sateListAndLeaCertiUrlInt = sateListAndLeaCertiUrlInt;
    }

    public Integer getOtherCertiUrlInt() {
        if (this.otherCerti != null) {
            if (this.getOtherCertiUrl().trim().length() <= 0 || "0".equals(this.getOtherCertiUrl().trim())) {
                return 0;
            }
            return 1;
        }
        return 0;
    }

    public void setOtherCertiUrlInt(Integer otherCertiUrlInt) {
        this.otherCertiUrlInt = otherCertiUrlInt;
    }

    public List<String> getEmpNos() {
        return empNos;
    }

    public void setEmpNos(List<String> empNos) {
        this.empNos = empNos;
    }

    public String getStateStr() {
        if (this.state != null) {
            if (this.state == 0) {
                return "未审核";
            } else if (this.state == 1) {
                return "审核通过";
            } else if (this.state == 2) {
                return "审核未通过";
            }
        }
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Integer salaryId) {
        this.salaryId = salaryId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Integer> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<Integer> workTypes) {
        this.workTypes = workTypes;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getAllMoney() {
        Double all = 0.0;
        if (this.getCompreSalary() != null)
            all += this.getCompreSalary();
        if (this.getPosSalary() != null)
            all += this.getPosSalary();
        if (this.getJobSalary() != null)
            all += this.getJobSalary();
        if (this.getMeritSalary() != null)
            all += this.getMeritSalary();
        return all;
    }

    public void setAllMoney(Double allMoney) {
        this.allMoney = allMoney;
    }

    public String getNamea() {
        return namea;
    }

    public void setNamea(String namea) {
        this.namea = namea;
    }

    public String getPassowrd22() {
        return passowrd22;
    }

    public void setPassowrd22(String passowrd22) {
        this.passowrd22 = passowrd22;
    }

    public String getNativePlaStr() {
        if (nativePla != null) {
            if (nativePla == 1) {
                return "北京";
            } else if (nativePla == 2) {
                return "上海";
            } else if (nativePla == 3) {
                return "广东";
            } else if (nativePla == 4) {
                return "河北";
            } else if (nativePla == 5) {
                return "山西";
            } else if (nativePla == 6) {
                return "辽宁";
            } else if (nativePla == 7) {
                return "吉林";
            } else if (nativePla == 8) {
                return "黑龙江";
            } else if (nativePla == 9) {
                return "江苏";
            } else if (nativePla == 10) {
                return "浙江";
            } else if (nativePla == 11) {
                return "安徽";
            } else if (nativePla == 12) {
                return "福建";
            } else if (nativePla == 13) {
                return "江西";
            } else if (nativePla == 14) {
                return "山东";
            } else if (nativePla == 15) {
                return "河南";
            } else if (nativePla == 16) {
                return "湖北";
            } else if (nativePla == 17) {
                return "湖南";
            } else if (nativePla == 18) {
                return "天津";
            } else if (nativePla == 19) {
                return "陕西";
            } else if (nativePla == 20) {
                return "四川";
            } else if (nativePla == 21) {
                return "台湾";
            } else if (nativePla == 22) {
                return "云南";
            } else if (nativePla == 23) {
                return "青海";
            } else if (nativePla == 24) {
                return "甘肃";
            } else if (nativePla == 25) {
                return "海南";
            } else if (nativePla == 26) {
                return "贵州";
            } else if (nativePla == 27) {
                return "重庆";
            } else if (nativePla == 28) {
                return "新疆";
            } else if (nativePla == 29) {
                return "广西";
            } else if (nativePla == 30) {
                return "宁夏";
            } else if (nativePla == 31) {
                return "内蒙古";
            } else if (nativePla == 32) {
                return "西藏";
            }
        }
        return nativePlaStr;
    }

    public void setNativePlaStr(String nativePlaStr) {
        this.nativePlaStr = nativePlaStr;
    }

    public void setValiPeriodOfID(Date valiPeriodOfID) {
        this.valiPeriodOfID = valiPeriodOfID;
    }

    public String getValiPeriodOfIDStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.valiPeriodOfID != null)
            return formatter.format(this.valiPeriodOfID);
        return valiPeriodOfIDStr;
    }

    public void setValiPeriodOfIDStr(String valiPeriodOfIDStr) {
        this.valiPeriodOfIDStr = valiPeriodOfIDStr;
    }

    public String getEducationLeUrl() {
        return educationLeUrl;
    }

    public void setEducationLeUrl(String educationLeUrl) {
        this.educationLeUrl = educationLeUrl;
    }

    public String getSateListAndLeaCertiUrl() {
        return sateListAndLeaCertiUrl;
    }

    public void setSateListAndLeaCertiUrl(String sateListAndLeaCertiUrl) {
        this.sateListAndLeaCertiUrl = sateListAndLeaCertiUrl;
    }

    public String getOtherCertiUrl() {
        return otherCertiUrl;
    }

    public void setOtherCertiUrl(String otherCertiUrl) {
        this.otherCertiUrl = otherCertiUrl;
    }

    public Double getPosSalary() {
        if (this.posSalary == null) {
            return 0.0;
        }
        return posSalary;
    }

    public void setPosSalary(Double posSalary) {
        this.posSalary = posSalary;
    }

    public Double getJobSalary() {
        if (jobSalary == null) {
            return 0.0;
        }
        return jobSalary;
    }

    public void setJobSalary(Double jobSalary) {
        this.jobSalary = jobSalary;
    }

    public Double getMeritSalary() {
        if (meritSalary == null) {
            return 0.0;
        }
        return meritSalary;
    }

    public void setMeritSalary(Double meritSalary) {
        this.meritSalary = meritSalary;
    }

    public Double getCompreSalary() {
        if (compreSalary == null) {
            return 0.0;
        }
        return compreSalary;
    }

    public void setCompreSalary(Double compreSalary) {
        this.compreSalary = compreSalary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public Integer getPositionAttrId() {
        return positionAttrId;
    }

    public void setPositionAttrId(Integer positionAttrId) {
        this.positionAttrId = positionAttrId;
    }

    public String getPositionAttrIdStr() {

        if (positionAttrId != null) {
            if (positionAttrId == 1) {
                return "总监级别";
            } else if (positionAttrId == 2) {
                return "总经理级别";
            } else if (positionAttrId == 3) {
                return "副总经理级别";
            } else if (positionAttrId == 4) {
                return "经理级别";
            } else if (positionAttrId == 5) {
                return "主管级别";
            } else if (positionAttrId == 6) {
                return "组长级别";
            } else if (positionAttrId == 7) {
                return "职员级别";
            }
        }
        return positionAttrIdStr;
    }

    public void setPositionAttrIdStr(String positionAttrIdStr) {
        this.positionAttrIdStr = positionAttrIdStr;
    }

    public String getMarriagedStr() {
        if (marriaged != null) {
            if (this.marriaged == 1) {
                return "已";
            } else if (this.marriaged == 0) {
                return "未";
            } else if (this.marriaged == 2) {
                return "离";
            }else if (this.marriaged == 3) {
                return "丧偶";
            }
        }
        return marriagedStr;
    }

    public void setMarriagedStr(String marriagedStr) {
        this.marriagedStr = marriagedStr;
    }

    public String getEducationLeStr() {
        if (educationLe != null) {
            if (this.getEducationLe() == 1) {
                return "小学";
            } else if (this.getEducationLe() == 2) {
                return "初中";
            } else if (this.getEducationLe() == 3) {
                return "高中";
            } else if (this.getEducationLe() == 4) {
                return "技校";
            } else if (this.getEducationLe() == 5) {
                return "中技";
            } else if (this.getEducationLe() == 6) {
                return "中专";
            } else if (this.getEducationLe() == 7) {
                return "大专";
            } else if (this.getEducationLe() == 8) {
                return "本科";
            } else if (this.getEducationLe() == 9) {
                return "研究生";
            } else if (this.getEducationLe() == 10) {
                return "硕士";
            } else if (this.getEducationLe() == 11) {
                return "博士";
            } else if (this.getEducationLe() == 12) {
                return "MBA";
            }
        }

        return educationLeStr;
    }

    public void setEducationLeStr(String educationLeStr) {
        this.educationLeStr = educationLeStr;
    }

    public String getScreAgreementStr() {
        if (screAgreement != null) {
            if (this.screAgreement == 1) {
                return "是";
            } else if (this.screAgreement == 0) {
                return "否";
            }
        }
        return screAgreementStr;
    }

    public void setScreAgreementStr(String screAgreementStr) {
        this.screAgreementStr = screAgreementStr;
    }

    public String getHealthCertiStr() {
        if (this.getHealthCerti() != null) {
            if (this.getHealthCerti() == 0) {
                return "无";
            } else if (this.getHealthCerti() == 1) {
                return "健康证";
            } else if (this.getHealthCerti() == 2) {
                return "体检单";
            } else if (this.getHealthCerti() == 3) {
                return "职业病体检";
            }
        }
        return healthCertiStr;
    }

    public void setHealthCertiStr(String healthCertiStr) {
        this.healthCertiStr = healthCertiStr;
    }

    public String getSateListAndLeaCertiStr() {
        if (sateListAndLeaCerti != null) {
            if (this.getSateListAndLeaCerti() == 1) {
                return "离职和社保";
            } else if (this.getSateListAndLeaCerti() == 0) {
                return "无";
            } else if (this.getSateListAndLeaCerti() == 2) {
                return "社保";
            } else if (this.getSateListAndLeaCerti() == 3) {
                return "离职";
            }
        }
        return sateListAndLeaCertiStr;
    }

    public void setSateListAndLeaCertiStr(String sateListAndLeaCertiStr) {
        this.sateListAndLeaCertiStr = sateListAndLeaCertiStr;
    }

    public String getOtherCertiStr() {
        if (otherCerti != null) {
            if (this.otherCerti == 1) {
                return "毕业证";
            } else if (this.otherCerti == 2) {
                return "电工证";
            } else if (this.otherCerti == 3) {
                return "焊工证";
            } else if (this.otherCerti == 4) {
                return "安全主任证书";
            }
        }
        return otherCertiStr;
    }

    public void setOtherCertiStr(String otherCertiStr) {
        this.otherCertiStr = otherCertiStr;
    }

    public String getBirthDayStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.birthDay != null)
            return formatter.format(this.birthDay);
        return this.birthDayStr;
    }

    public void setBirthDayStr(String birthDayStr) {
        this.birthDayStr = birthDayStr;
    }

    public String getConExpDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.conExpDate != null)
            return formatter.format(this.conExpDate);
        return this.conExpDateStr;
    }

    public void setConExpDateStr(String conExpDateStr) {
        this.conExpDateStr = conExpDateStr;
    }

    public String getIncompdateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.incompdate != null)
            return formatter.format(this.incompdate);
        return this.incompdateStr;
    }

    public void setIncompdateStr(String incompdateStr) {
        this.incompdateStr = incompdateStr;
    }

    public Date getConExpDate() {
        return conExpDate;
    }

    public void setConExpDate(Date conExpDate) {
        this.conExpDate = conExpDate;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getID_NO() {
        return ID_NO;
    }

    public void setID_NO(String ID_NO) {
        this.ID_NO = ID_NO;
    }

    public Integer getNativePla() {
        return nativePla;
    }

    public void setNativePla(Integer nativePla) {
        this.nativePla = nativePla;
    }

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    public Date getValiPeriodOfID() {
        return valiPeriodOfID;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public String getNationStr() {
        return nationStr;
    }

    public void setNationStr(String nationStr) {
        this.nationStr = nationStr;
    }

    public Integer getMarriaged() {
        return marriaged;
    }

    public void setMarriaged(Integer marriaged) {
        this.marriaged = marriaged;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getEducationLe() {
        return educationLe;
    }

    public void setEducationLe(Integer educationLe) {
        this.educationLe = educationLe;
    }

    public Integer getScreAgreement() {
        return screAgreement;
    }

    public void setScreAgreement(Integer screAgreement) {
        this.screAgreement = screAgreement;
    }

    public Integer getHealthCerti() {
        return healthCerti;
    }

    public void setHealthCerti(Integer healthCerti) {
        this.healthCerti = healthCerti;
    }

    public Integer getSateListAndLeaCerti() {
        return sateListAndLeaCerti;
    }

    public void setSateListAndLeaCerti(Integer sateListAndLeaCerti) {
        this.sateListAndLeaCerti = sateListAndLeaCerti;
    }

    public Integer getOtherCerti() {
        return otherCerti;
    }

    public void setOtherCerti(Integer otherCerti) {
        this.otherCerti = otherCerti;
    }

    public List<Integer> getSexIds() {
        return sexIds;
    }

    public void setSexIds(List<Integer> sexIds) {
        this.sexIds = sexIds;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getEndLeaveStr() {
        return endLeaveStr;
    }

    public List<Integer> getNameIds() {
        return nameIds;
    }

    public void setNameIds(List<Integer> nameIds) {
        this.nameIds = nameIds;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public void setEndLeaveStr(String endLeaveStr) {
        this.endLeaveStr = endLeaveStr;
    }

    public String getBeginLeaveStr() {
        return beginLeaveStr;
    }

    public void setBeginLeaveStr(String beginLeaveStr) {
        this.beginLeaveStr = beginLeaveStr;
    }

    public String getDeptIdsstr() {
        return deptIdsstr;
    }

    public void setDeptIdsstr(String deptIdsstr) {
        this.deptIdsstr = deptIdsstr;
    }

    public String getPositionIdsstr() {
        return positionIdsstr;
    }

    public void setPositionIdsstr(String positionIdsstr) {
        this.positionIdsstr = positionIdsstr;
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

    public String getStartIncomDateStr() {
        return startIncomDateStr;
    }

    public void setStartIncomDateStr(String startIncomDateStr) {
        this.startIncomDateStr = startIncomDateStr;
    }

    public String getEndIncomDateStr() {
        return endIncomDateStr;
    }

    public void setEndIncomDateStr(String endIncomDateStr) {
        this.endIncomDateStr = endIncomDateStr;
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

    public String getSexStr() {
        if (this.sex != null && this.sex == 1) {
            return "男";
        } else if (this.sex != null && this.sex == 0) {
            return "女";
        }
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getIncomdateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.incompdate != null)
            return formatter.format(this.incompdate);
        return this.incomdateStr;
    }

    public void setIncomdateStr(String incomdateStr) {
        this.incomdateStr = incomdateStr;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Date getIncompdate() {
        return incompdate;
    }

    public void setIncompdate(Date incompdate) {
        this.incompdate = incompdate;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
