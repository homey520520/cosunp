package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/3/23  下午 4:30
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ProjectHeadOrderItem implements Serializable {


    private static final long serialVersionUID = -4140054436131666981L;

    private List<String> nameIds;
    private List<String> orderNos;
    private List<String> projectNames;
    private List<String> customerNames;
    private List<Integer> statuss;
    private List<Integer> checkeds;

    private String sortMethod;
    private String sortByName;




    private Integer id;
    private String name;
    private String note;
    private int newOrOld;
    private String titleName;
    private Integer order_Id;
    private Date delivery_Date;
    private String totalBao;
    private String product_Name;
    private int historyGe;
    private String versionStr;
    private String projectName;
    private String customer_Name;
    private String ordeNo;
    private String userid;
    private String userName;
    private int progressCent;
    private Integer province;
    private String provinceStr;

    private Date getOrder_Date_Plan;
    private Date getOrder_Date_Accu;
    private Date zhanCha_Date_Plan;
    private Date zhanCha_Date_Accu;
    private Date outDraw_Date_Plan;
    private Date outDraw_Date_Accu;
    private Date program_confir_Date_Plan;
    private Date program_confir_Date_Accu;
    private Date giveOrder_Date_Plan;
    private Date giveOrder_Date_Accu;
    private Date delivery_Goods_Date_Plan;
    private Date delivery_Goods_Date_Accu;
    private Date install_Date_Plan;
    private Date install_Date_Accu;
    private Date yanShou_Date_Plan;
    private Date yanShou_Date_Accu;
    private Date jieSuan_Date_Plan;
    private Date jieSuan_Date_Accu;

    private String updateUserId;
    private String updateDate;
    private String updateDateStr;

    private String jindu_remark;
    private Double hetong_money;
    private Double daokuan_money;
    private Double weihuikuan_money;
    private String remark;
    private String project_Manager1;
    private String project_Manager2;
    private String project_Manager3;
    private Double version;



    private String salor;
    private String[] salors;
    private String[] gendans;
    private String[] salors2;
    private String[] gendans2;
    private String salorsStr;
    private String gendansStr;


    private String[] zhanCha_Emps;
    private String[] outDraw_Emps;
    private String[] program_confir_Emps;
    private String[] giveOrder_Emps;
    private String[] delivery_Goods_Emps;
    private String[] install_Emps;
    private String[] yanShou_Emps;
    private String[] jieSuan_Emps;


    private String[] zhanCha_Emps2;
    private String[] outDraw_Emps2;
    private String[] program_confir_Emps2;
    private String[] giveOrder_Emps2;
    private String[] delivery_Goods_Emps2;
    private String[] install_Emps2;
    private String[] yanShou_Emps2;
    private String[] jieSuan_Emps2;

    private String productName;
    private String orderNo;
    private String checkedStr;
    private Double nowhereMoney;
    private String fapiaoNo;
    private int status;
    private int checked;

    private Integer statusIn;
    private Integer checkedIn;

    private String statusStr;



    private String saleManager;
    private String gendan;
    private String zhanCha_Emp;
    private String outDraw_Emp;
    private String program_confir_Emp;
    private String giveOrder_Emp;
    private String delivery_Goods_Emp;
    private String install_Emp;
    private String yanShou_Emp;
    private String jieSuan_Emp;


    private Double hetongMoney;
    private Double hereMoney;
    private Double weiHuiMoney;

    private int isYanShou;


    private String delivery_DateStr;
    private String getOrder_Date_PlanStr;
    private String getOrder_Date_AccuStr;
    private String zhanCha_Date_PlanStr;
    private String zhanCha_Date_AccuStr;
    private String outDraw_Date_PlanStr;
    private String outDraw_Date_AccuStr;
    private String program_confir_Date_PlanStr;
    private String program_confir_Date_AccuStr;
    private String giveOrder_Date_PlanStr;
    private String giveOrder_Date_AccuStr;
    private String delivery_Goods_Date_PlanStr;
    private String delivery_Goods_Date_AccuStr;
    private String install_Date_PlanStr;
    private String install_Date_AccuStr;
    private String yanShou_Date_PlanStr;
    private String yanShou_Date_AccuStr;
    private String jieSuan_Date_PlanStr;
    private String jieSuan_Date_AccuStr;


    private int getOrder_Date_PlanStrChange;
    private int zhanCha_Date_PlanStrChange;
    private int zhanCha_Date_AccuStrChange;
    private int outDraw_Date_PlanStrChange;
    private int outDraw_Date_AccuStrChange;
    private int program_confir_Date_PlanStrChange;
    private int program_confir_Date_AccuStrChange;
    private int giveOrder_Date_PlanStrChange;
    private int giveOrder_Date_AccuStrChange;
    private int delivery_Goods_Date_PlanStrChange;
    private int delivery_Goods_Date_AccuStrChange;
    private int install_Date_PlanStrChange;
    private int install_Date_AccuStrChange;
    private int yanShou_Date_PlanStrChange;
    private int yanShou_Date_AccuStrChange;
    private int jieSuan_Date_PlanStrChange;
    private int jieSuan_Date_AccuStrChange;


    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;


    private String isYanShouStr;



    public List<Integer> getStatuss() {
        return statuss;
    }

    public void setStatuss(List<Integer> statuss) {
        this.statuss = statuss;
    }

    public List<Integer> getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(List<Integer> checkeds) {
        this.checkeds = checkeds;
    }

    public String getIsYanShouStr() {
        if (this.getYanShou_Date_AccuStr() != null) {
            return "已验收";
        }
        return "未验收";
    }

    public Integer getStatusIn() {
        return statusIn;
    }

    public void setStatusIn(Integer statusIn) {
        this.statusIn = statusIn;
    }

    public Integer getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(Integer checkedIn) {
        this.checkedIn = checkedIn;
    }


    public List<String> getNameIds() {
        return nameIds;
    }

    public void setNameIds(List<String> nameIds) {
        this.nameIds = nameIds;
    }

    public List<String> getOrderNos() {
        return orderNos;
    }

    public void setOrderNos(List<String> orderNos) {
        this.orderNos = orderNos;
    }

    public List<String> getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(List<String> projectNames) {
        this.projectNames = projectNames;
    }

    public List<String> getCustomerNames() {
        return customerNames;
    }

    public void setCustomerNames(List<String> customerNames) {
        this.customerNames = customerNames;
    }

    public void setIsYanShouStr(String isYanShouStr) {
        this.isYanShouStr = isYanShouStr;
    }

    public int getIsYanShou() {
        return isYanShou;
    }

    public void setIsYanShou(int isYanShou) {
        this.isYanShou = isYanShou;
    }

    public int getCurrentPage() {
        return currentPage;
    }


    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public String getProvinceStr() {
        return provinceStr;
    }

    public void setProvinceStr(String provinceStr) {
        this.provinceStr = provinceStr;
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

    public int getProgressCent() {
        return progressCent;
    }

    public void setProgressCent(int progressCent) {
        this.progressCent = progressCent;
    }

    public int getGetOrder_Date_PlanStrChange() {
        return getOrder_Date_PlanStrChange;
    }

    public void setGetOrder_Date_PlanStrChange(int getOrder_Date_PlanStrChange) {
        this.getOrder_Date_PlanStrChange = getOrder_Date_PlanStrChange;
    }

    public int getZhanCha_Date_PlanStrChange() {
        return zhanCha_Date_PlanStrChange;
    }

    public void setZhanCha_Date_PlanStrChange(int zhanCha_Date_PlanStrChange) {
        this.zhanCha_Date_PlanStrChange = zhanCha_Date_PlanStrChange;
    }

    public int getZhanCha_Date_AccuStrChange() {
        return zhanCha_Date_AccuStrChange;
    }

    public void setZhanCha_Date_AccuStrChange(int zhanCha_Date_AccuStrChange) {
        this.zhanCha_Date_AccuStrChange = zhanCha_Date_AccuStrChange;
    }

    public int getOutDraw_Date_PlanStrChange() {
        return outDraw_Date_PlanStrChange;
    }

    public void setOutDraw_Date_PlanStrChange(int outDraw_Date_PlanStrChange) {
        this.outDraw_Date_PlanStrChange = outDraw_Date_PlanStrChange;
    }

    public int getOutDraw_Date_AccuStrChange() {
        return outDraw_Date_AccuStrChange;
    }

    public void setOutDraw_Date_AccuStrChange(int outDraw_Date_AccuStrChange) {
        this.outDraw_Date_AccuStrChange = outDraw_Date_AccuStrChange;
    }

    public int getProgram_confir_Date_PlanStrChange() {
        return program_confir_Date_PlanStrChange;
    }

    public void setProgram_confir_Date_PlanStrChange(int program_confir_Date_PlanStrChange) {
        this.program_confir_Date_PlanStrChange = program_confir_Date_PlanStrChange;
    }

    public int getProgram_confir_Date_AccuStrChange() {
        return program_confir_Date_AccuStrChange;
    }

    public void setProgram_confir_Date_AccuStrChange(int program_confir_Date_AccuStrChange) {
        this.program_confir_Date_AccuStrChange = program_confir_Date_AccuStrChange;
    }

    public int getGiveOrder_Date_PlanStrChange() {
        return giveOrder_Date_PlanStrChange;
    }

    public void setGiveOrder_Date_PlanStrChange(int giveOrder_Date_PlanStrChange) {
        this.giveOrder_Date_PlanStrChange = giveOrder_Date_PlanStrChange;
    }

    public int getGiveOrder_Date_AccuStrChange() {
        return giveOrder_Date_AccuStrChange;
    }

    public void setGiveOrder_Date_AccuStrChange(int giveOrder_Date_AccuStrChange) {
        this.giveOrder_Date_AccuStrChange = giveOrder_Date_AccuStrChange;
    }

    public int getDelivery_Goods_Date_PlanStrChange() {
        return delivery_Goods_Date_PlanStrChange;
    }

    public void setDelivery_Goods_Date_PlanStrChange(int delivery_Goods_Date_PlanStrChange) {
        this.delivery_Goods_Date_PlanStrChange = delivery_Goods_Date_PlanStrChange;
    }

    public int getDelivery_Goods_Date_AccuStrChange() {
        return delivery_Goods_Date_AccuStrChange;
    }

    public void setDelivery_Goods_Date_AccuStrChange(int delivery_Goods_Date_AccuStrChange) {
        this.delivery_Goods_Date_AccuStrChange = delivery_Goods_Date_AccuStrChange;
    }

    public int getInstall_Date_PlanStrChange() {
        return install_Date_PlanStrChange;
    }

    public void setInstall_Date_PlanStrChange(int install_Date_PlanStrChange) {
        this.install_Date_PlanStrChange = install_Date_PlanStrChange;
    }

    public int getInstall_Date_AccuStrChange() {
        return install_Date_AccuStrChange;
    }

    public void setInstall_Date_AccuStrChange(int install_Date_AccuStrChange) {
        this.install_Date_AccuStrChange = install_Date_AccuStrChange;
    }

    public int getYanShou_Date_PlanStrChange() {
        return yanShou_Date_PlanStrChange;
    }

    public void setYanShou_Date_PlanStrChange(int yanShou_Date_PlanStrChange) {
        this.yanShou_Date_PlanStrChange = yanShou_Date_PlanStrChange;
    }

    public int getYanShou_Date_AccuStrChange() {
        return yanShou_Date_AccuStrChange;
    }

    public void setYanShou_Date_AccuStrChange(int yanShou_Date_AccuStrChange) {
        this.yanShou_Date_AccuStrChange = yanShou_Date_AccuStrChange;
    }

    public int getJieSuan_Date_PlanStrChange() {
        return jieSuan_Date_PlanStrChange;
    }

    public void setJieSuan_Date_PlanStrChange(int jieSuan_Date_PlanStrChange) {
        this.jieSuan_Date_PlanStrChange = jieSuan_Date_PlanStrChange;
    }

    public int getJieSuan_Date_AccuStrChange() {
        return jieSuan_Date_AccuStrChange;
    }

    public void setJieSuan_Date_AccuStrChange(int jieSuan_Date_AccuStrChange) {
        this.jieSuan_Date_AccuStrChange = jieSuan_Date_AccuStrChange;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrdeNo() {
        return ordeNo;
    }

    public void setOrdeNo(String ordeNo) {
        this.ordeNo = ordeNo;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public int getNewOrOld() {
        return newOrOld;
    }

    public void setNewOrOld(int newOrOld) {
        this.newOrOld = newOrOld;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String[] getZhanCha_Emps() {
        if (this.zhanCha_Emp != null) {
            return zhanCha_Emp.split(",");
        }
        return zhanCha_Emps;

    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public void setZhanCha_Emps(String[] zhanCha_Emps) {
        this.zhanCha_Emps = zhanCha_Emps;
    }

    public String[] getOutDraw_Emps() {
        if (this.outDraw_Emp != null) {
            return outDraw_Emp.split(",");
        }
        return outDraw_Emps;
    }

    public void setOutDraw_Emps(String[] outDraw_Emps) {
        this.outDraw_Emps = outDraw_Emps;
    }

    public String[] getProgram_confir_Emps() {
        if (this.program_confir_Emp != null) {
            return program_confir_Emp.split(",");
        }
        return program_confir_Emps;
    }

    public void setProgram_confir_Emps(String[] program_confir_Emps) {
        this.program_confir_Emps = program_confir_Emps;
    }

    public String[] getGiveOrder_Emps() {
        if (this.giveOrder_Emp != null) {
            return giveOrder_Emp.split(",");
        }
        return program_confir_Emps;
    }

    public void setGiveOrder_Emps(String[] giveOrder_Emps) {
        this.giveOrder_Emps = giveOrder_Emps;
    }

    public String[] getDelivery_Goods_Emps() {
        if (this.delivery_Goods_Emp != null) {
            return delivery_Goods_Emp.split(",");
        }
        return delivery_Goods_Emps;
    }

    public void setDelivery_Goods_Emps(String[] delivery_Goods_Emps) {
        this.delivery_Goods_Emps = delivery_Goods_Emps;
    }

    public String[] getInstall_Emps() {
        if (this.install_Emp != null) {
            return install_Emp.split(",");
        }
        return install_Emps;
    }

    public void setInstall_Emps(String[] install_Emps) {
        this.install_Emps = install_Emps;
    }

    public String[] getYanShou_Emps() {
        if (this.yanShou_Emp != null) {
            return yanShou_Emp.split(",");
        }
        return yanShou_Emps;
    }

    public void setYanShou_Emps(String[] yanShou_Emps) {
        this.yanShou_Emps = yanShou_Emps;
    }

    public String[] getJieSuan_Emps() {
        if (this.jieSuan_Emp != null) {
            return jieSuan_Emp.split(",");
        }
        return jieSuan_Emps;
    }

    public void setJieSuan_Emps(String[] jieSuan_Emps) {
        this.jieSuan_Emps = jieSuan_Emps;
    }

    public String[] getZhanCha_Emps2() {
        return zhanCha_Emps2;
    }

    public void setZhanCha_Emps2(String[] zhanCha_Emps2) {
        this.zhanCha_Emps2 = zhanCha_Emps2;
    }

    public String[] getOutDraw_Emps2() {
        return outDraw_Emps2;
    }

    public void setOutDraw_Emps2(String[] outDraw_Emps2) {
        this.outDraw_Emps2 = outDraw_Emps2;
    }

    public String[] getProgram_confir_Emps2() {
        return program_confir_Emps2;
    }

    public void setProgram_confir_Emps2(String[] program_confir_Emps2) {
        this.program_confir_Emps2 = program_confir_Emps2;
    }

    public String[] getGiveOrder_Emps2() {
        return giveOrder_Emps2;
    }

    public void setGiveOrder_Emps2(String[] giveOrder_Emps2) {
        this.giveOrder_Emps2 = giveOrder_Emps2;
    }

    public String[] getDelivery_Goods_Emps2() {
        return delivery_Goods_Emps2;
    }

    public void setDelivery_Goods_Emps2(String[] delivery_Goods_Emps2) {
        this.delivery_Goods_Emps2 = delivery_Goods_Emps2;
    }

    public String[] getInstall_Emps2() {
        return install_Emps2;
    }

    public void setInstall_Emps2(String[] install_Emps2) {
        this.install_Emps2 = install_Emps2;
    }

    public String[] getYanShou_Emps2() {
        return yanShou_Emps2;
    }

    public void setYanShou_Emps2(String[] yanShou_Emps2) {
        this.yanShou_Emps2 = yanShou_Emps2;
    }

    public String[] getJieSuan_Emps2() {
        return jieSuan_Emps2;
    }

    public void setJieSuan_Emps2(String[] jieSuan_Emps2) {
        this.jieSuan_Emps2 = jieSuan_Emps2;
    }

    public String getZhanCha_Emp() {
        return zhanCha_Emp;
    }

    public void setZhanCha_Emp(String zhanCha_Emp) {
        this.zhanCha_Emp = zhanCha_Emp;
    }

    public String getOutDraw_Emp() {
        return outDraw_Emp;
    }

    public void setOutDraw_Emp(String outDraw_Emp) {
        this.outDraw_Emp = outDraw_Emp;
    }

    public String getProgram_confir_Emp() {
        return program_confir_Emp;
    }

    public void setProgram_confir_Emp(String program_confir_Emp) {
        this.program_confir_Emp = program_confir_Emp;
    }

    public String getGiveOrder_Emp() {
        return giveOrder_Emp;
    }

    public void setGiveOrder_Emp(String giveOrder_Emp) {
        this.giveOrder_Emp = giveOrder_Emp;
    }

    public String getDelivery_Goods_Emp() {
        return delivery_Goods_Emp;
    }

    public void setDelivery_Goods_Emp(String delivery_Goods_Emp) {
        this.delivery_Goods_Emp = delivery_Goods_Emp;
    }

    public String getInstall_Emp() {
        return install_Emp;
    }

    public void setInstall_Emp(String install_Emp) {
        this.install_Emp = install_Emp;
    }

    public String getYanShou_Emp() {
        return yanShou_Emp;
    }

    public void setYanShou_Emp(String yanShou_Emp) {
        this.yanShou_Emp = yanShou_Emp;
    }

    public String getJieSuan_Emp() {
        return jieSuan_Emp;
    }

    public void setJieSuan_Emp(String jieSuan_Emp) {
        this.jieSuan_Emp = jieSuan_Emp;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    public String getSalor() {
        return salor;
    }

    public void setSalor(String salor) {
        this.salor = salor;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVersionStr() {
        return versionStr;
    }

    public void setVersionStr(String versionStr) {
        this.versionStr = versionStr;
    }


    public String getFapiaoNo() {
        return fapiaoNo;
    }

    public void setFapiaoNo(String fapiaoNo) {
        this.fapiaoNo = fapiaoNo;
    }

    public Double getNowhereMoney() {
        return nowhereMoney;
    }

    public void setNowhereMoney(Double nowhereMoney) {
        this.nowhereMoney = nowhereMoney;
    }

    public int getHistoryGe() {
        return historyGe;
    }

    public void setHistoryGe(int historyGe) {
        this.historyGe = historyGe;
    }

    public String getCheckedStr() {
        if (this.checked == 0) {
            return "未审核";
        } else if (this.checked == 2) {
            return "审核通过";
        } else if (this.checked == 3) {
            return "审核撤销";
        } else if (this.checked == 1) {
            return "审核未通过";
        }
        return "";
    }

    public String getStatusStr() {
        if (this.status == 0) {
            return "进行中";
        } else if (this.status == 1) {
            return "已完成";
        } else if (this.status == 2) {
            return "暂停";
        } else if (this.status == 3) {
            return "作废";
        }
        return "";
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCheckedStr(String checkedStr) {
        this.checkedStr = checkedStr;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }


    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getSalorsStr() {
        if (salorsStr == null)
            return "";
        return salorsStr;
    }

    public String[] getSalors2() {
        return salors2;
    }

    public void setSalors2(String[] salors2) {
        this.salors2 = salors2;
    }

    public String[] getGendans2() {
        return gendans2;
    }

    public void setGendans2(String[] gendans2) {
        this.gendans2 = gendans2;
    }

    public void setSalorsStr(String salorsStr) {
        this.salorsStr = salorsStr;
    }

    public String getGendansStr() {
        if (gendansStr == null)
            return "";
        return gendansStr;
    }

    public void setGendansStr(String gendansStr) {
        this.gendansStr = gendansStr;
    }

    public String[] getSalors() {
        if (this.salorsStr != null) {
            return salorsStr.split(",");
        }
        return salors;
    }

    public void setSalors(String[] salors) {
        this.salors = salors;
    }

    public String[] getGendans() {
        if (this.gendansStr != null) {
            return gendansStr.split(",");
        }
        return gendans;
    }

    public void setGendans(String[] gendans) {
        this.gendans = gendans;
    }

    public String getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(String saleManager) {
        this.saleManager = saleManager;
    }

    public String getGendan() {
        return gendan;
    }

    public void setGendan(String gendan) {
        this.gendan = gendan;
    }

    public Double getHetongMoney() {
        return hetongMoney;
    }

    public void setHetongMoney(Double hetongMoney) {
        this.hetongMoney = hetongMoney;
    }

    public Double getHereMoney() {
        return hereMoney;
    }

    public void setHereMoney(Double hereMoney) {
        this.hereMoney = hereMoney;
    }

    public Double getWeiHuiMoney() {
        return weiHuiMoney;
    }

    public void setWeiHuiMoney(Double weiHuiMoney) {
        this.weiHuiMoney = weiHuiMoney;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDelivery_DateStr() {
        return delivery_DateStr;
    }

    public void setDelivery_DateStr(String delivery_DateStr) {
        this.delivery_DateStr = delivery_DateStr;
    }

    public String getGetOrder_Date_PlanStr() {
        return getOrder_Date_PlanStr;
    }

    public void setGetOrder_Date_PlanStr(String getOrder_Date_PlanStr) {
        this.getOrder_Date_PlanStr = getOrder_Date_PlanStr;
    }

    public String getGetOrder_Date_AccuStr() {
        return getOrder_Date_AccuStr;
    }

    public void setGetOrder_Date_AccuStr(String getOrder_Date_AccuStr) {
        this.getOrder_Date_AccuStr = getOrder_Date_AccuStr;
    }

    public String getZhanCha_Date_PlanStr() {
        return zhanCha_Date_PlanStr;
    }

    public void setZhanCha_Date_PlanStr(String zhanCha_Date_PlanStr) {
        this.zhanCha_Date_PlanStr = zhanCha_Date_PlanStr;
    }

    public String getZhanCha_Date_AccuStr() {
        return zhanCha_Date_AccuStr;
    }

    public void setZhanCha_Date_AccuStr(String zhanCha_Date_AccuStr) {
        this.zhanCha_Date_AccuStr = zhanCha_Date_AccuStr;
    }

    public String getOutDraw_Date_PlanStr() {
        return outDraw_Date_PlanStr;
    }

    public void setOutDraw_Date_PlanStr(String outDraw_Date_PlanStr) {
        this.outDraw_Date_PlanStr = outDraw_Date_PlanStr;
    }

    public String getOutDraw_Date_AccuStr() {
        return outDraw_Date_AccuStr;
    }

    public void setOutDraw_Date_AccuStr(String outDraw_Date_AccuStr) {
        this.outDraw_Date_AccuStr = outDraw_Date_AccuStr;
    }

    public String getProgram_confir_Date_PlanStr() {
        return program_confir_Date_PlanStr;
    }

    public void setProgram_confir_Date_PlanStr(String program_confir_Date_PlanStr) {
        this.program_confir_Date_PlanStr = program_confir_Date_PlanStr;
    }

    public String getProgram_confir_Date_AccuStr() {
        return program_confir_Date_AccuStr;
    }

    public void setProgram_confir_Date_AccuStr(String program_confir_Date_AccuStr) {
        this.program_confir_Date_AccuStr = program_confir_Date_AccuStr;
    }

    public String getGiveOrder_Date_PlanStr() {
        return giveOrder_Date_PlanStr;
    }

    public void setGiveOrder_Date_PlanStr(String giveOrder_Date_PlanStr) {
        this.giveOrder_Date_PlanStr = giveOrder_Date_PlanStr;
    }

    public String getGiveOrder_Date_AccuStr() {
        return giveOrder_Date_AccuStr;
    }

    public void setGiveOrder_Date_AccuStr(String giveOrder_Date_AccuStr) {
        this.giveOrder_Date_AccuStr = giveOrder_Date_AccuStr;
    }

    public String getDelivery_Goods_Date_PlanStr() {
        return delivery_Goods_Date_PlanStr;
    }

    public void setDelivery_Goods_Date_PlanStr(String delivery_Goods_Date_PlanStr) {
        this.delivery_Goods_Date_PlanStr = delivery_Goods_Date_PlanStr;
    }

    public String getDelivery_Goods_Date_AccuStr() {
        return delivery_Goods_Date_AccuStr;
    }

    public void setDelivery_Goods_Date_AccuStr(String delivery_Goods_Date_AccuStr) {
        this.delivery_Goods_Date_AccuStr = delivery_Goods_Date_AccuStr;
    }

    public String getInstall_Date_PlanStr() {
        return install_Date_PlanStr;
    }

    public void setInstall_Date_PlanStr(String install_Date_PlanStr) {
        this.install_Date_PlanStr = install_Date_PlanStr;
    }

    public String getInstall_Date_AccuStr() {
        return install_Date_AccuStr;
    }

    public void setInstall_Date_AccuStr(String install_Date_AccuStr) {
        this.install_Date_AccuStr = install_Date_AccuStr;
    }

    public String getYanShou_Date_PlanStr() {
        return yanShou_Date_PlanStr;
    }

    public void setYanShou_Date_PlanStr(String yanShou_Date_PlanStr) {
        this.yanShou_Date_PlanStr = yanShou_Date_PlanStr;
    }

    public String getYanShou_Date_AccuStr() {
        return yanShou_Date_AccuStr;
    }

    public void setYanShou_Date_AccuStr(String yanShou_Date_AccuStr) {
        this.yanShou_Date_AccuStr = yanShou_Date_AccuStr;
    }

    public String getJieSuan_Date_PlanStr() {
        return jieSuan_Date_PlanStr;
    }

    public void setJieSuan_Date_PlanStr(String jieSuan_Date_PlanStr) {
        this.jieSuan_Date_PlanStr = jieSuan_Date_PlanStr;
    }

    public String getJieSuan_Date_AccuStr() {
        return jieSuan_Date_AccuStr;
    }

    public void setJieSuan_Date_AccuStr(String jieSuan_Date_AccuStr) {
        this.jieSuan_Date_AccuStr = jieSuan_Date_AccuStr;
    }

    public String getProject_Manager1() {
        return project_Manager1;
    }

    public void setProject_Manager1(String project_Manager1) {
        this.project_Manager1 = project_Manager1;
    }

    public String getProject_Manager2() {
        return project_Manager2;
    }

    public void setProject_Manager2(String project_Manager2) {
        this.project_Manager2 = project_Manager2;
    }

    public String getProject_Manager3() {
        return project_Manager3;
    }

    public void setProject_Manager3(String project_Manager3) {
        this.project_Manager3 = project_Manager3;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(Integer order_Id) {
        this.order_Id = order_Id;
    }

    public Date getDelivery_Date() {
        return delivery_Date;
    }

    public void setDelivery_Date(Date delivery_Date) {
        this.delivery_Date = delivery_Date;
    }

    public String getTotalBao() {
        return totalBao;
    }

    public void setTotalBao(String totalBao) {
        this.totalBao = totalBao;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public Date getGetOrder_Date_Plan() {
        return getOrder_Date_Plan;
    }

    public void setGetOrder_Date_Plan(Date getOrder_Date_Plan) {
        this.getOrder_Date_Plan = getOrder_Date_Plan;
    }

    public Date getGetOrder_Date_Accu() {
        return getOrder_Date_Accu;
    }

    public void setGetOrder_Date_Accu(Date getOrder_Date_Accu) {
        this.getOrder_Date_Accu = getOrder_Date_Accu;
    }

    public Date getZhanCha_Date_Plan() {
        return zhanCha_Date_Plan;
    }

    public void setZhanCha_Date_Plan(Date zhanCha_Date_Plan) {
        this.zhanCha_Date_Plan = zhanCha_Date_Plan;
    }

    public Date getZhanCha_Date_Accu() {
        return zhanCha_Date_Accu;
    }

    public void setZhanCha_Date_Accu(Date zhanCha_Date_Accu) {
        this.zhanCha_Date_Accu = zhanCha_Date_Accu;
    }

    public Date getOutDraw_Date_Plan() {
        return outDraw_Date_Plan;
    }

    public void setOutDraw_Date_Plan(Date outDraw_Date_Plan) {
        this.outDraw_Date_Plan = outDraw_Date_Plan;
    }

    public Date getOutDraw_Date_Accu() {
        return outDraw_Date_Accu;
    }

    public void setOutDraw_Date_Accu(Date outDraw_Date_Accu) {
        this.outDraw_Date_Accu = outDraw_Date_Accu;
    }

    public Date getProgram_confir_Date_Plan() {
        return program_confir_Date_Plan;
    }

    public void setProgram_confir_Date_Plan(Date program_confir_Date_Plan) {
        this.program_confir_Date_Plan = program_confir_Date_Plan;
    }

    public Date getProgram_confir_Date_Accu() {
        return program_confir_Date_Accu;
    }

    public void setProgram_confir_Date_Accu(Date program_confir_Date_Accu) {
        this.program_confir_Date_Accu = program_confir_Date_Accu;
    }

    public Date getGiveOrder_Date_Plan() {
        return giveOrder_Date_Plan;
    }

    public void setGiveOrder_Date_Plan(Date giveOrder_Date_Plan) {
        this.giveOrder_Date_Plan = giveOrder_Date_Plan;
    }

    public Date getGiveOrder_Date_Accu() {
        return giveOrder_Date_Accu;
    }

    public void setGiveOrder_Date_Accu(Date giveOrder_Date_Accu) {
        this.giveOrder_Date_Accu = giveOrder_Date_Accu;
    }

    public Date getDelivery_Goods_Date_Plan() {
        return delivery_Goods_Date_Plan;
    }

    public void setDelivery_Goods_Date_Plan(Date delivery_Goods_Date_Plan) {
        this.delivery_Goods_Date_Plan = delivery_Goods_Date_Plan;
    }

    public Date getDelivery_Goods_Date_Accu() {
        return delivery_Goods_Date_Accu;
    }

    public void setDelivery_Goods_Date_Accu(Date delivery_Goods_Date_Accu) {
        this.delivery_Goods_Date_Accu = delivery_Goods_Date_Accu;
    }

    public Date getInstall_Date_Plan() {
        return install_Date_Plan;
    }

    public void setInstall_Date_Plan(Date install_Date_Plan) {
        this.install_Date_Plan = install_Date_Plan;
    }

    public Date getInstall_Date_Accu() {
        return install_Date_Accu;
    }

    public void setInstall_Date_Accu(Date install_Date_Accu) {
        this.install_Date_Accu = install_Date_Accu;
    }

    public Date getYanShou_Date_Plan() {
        return yanShou_Date_Plan;
    }

    public void setYanShou_Date_Plan(Date yanShou_Date_Plan) {
        this.yanShou_Date_Plan = yanShou_Date_Plan;
    }

    public Date getYanShou_Date_Accu() {
        return yanShou_Date_Accu;
    }

    public void setYanShou_Date_Accu(Date yanShou_Date_Accu) {
        this.yanShou_Date_Accu = yanShou_Date_Accu;
    }

    public Date getJieSuan_Date_Plan() {
        return jieSuan_Date_Plan;
    }

    public void setJieSuan_Date_Plan(Date jieSuan_Date_Plan) {
        this.jieSuan_Date_Plan = jieSuan_Date_Plan;
    }

    public Date getJieSuan_Date_Accu() {
        return jieSuan_Date_Accu;
    }

    public void setJieSuan_Date_Accu(Date jieSuan_Date_Accu) {
        this.jieSuan_Date_Accu = jieSuan_Date_Accu;
    }

    public String getJindu_remark() {
        return jindu_remark;
    }

    public void setJindu_remark(String jindu_remark) {
        this.jindu_remark = jindu_remark;
    }

    public Double getHetong_money() {
        return hetong_money;
    }

    public void setHetong_money(Double hetong_money) {
        this.hetong_money = hetong_money;
    }

    public Double getDaokuan_money() {
        return daokuan_money;
    }

    public void setDaokuan_money(Double daokuan_money) {
        this.daokuan_money = daokuan_money;
    }

    public Double getWeihuikuan_money() {
        return weihuikuan_money;
    }

    public void setWeihuikuan_money(Double weihuikuan_money) {
        this.weihuikuan_money = weihuikuan_money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
