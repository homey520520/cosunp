package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/25 0025 下午 2:01
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FinanceImportData implements Serializable {


    private static final long serialVersionUID = -8356527306598925137L;
    private Integer id;
    private String yearMonth;
    private String empNo;
    private String name;
    private String bigDeptName;
    private String deptName;
    private Double legalHolidWorkHours;
    private Double sellActual;
    private Double sellThreshold;
    private Double sellLevelSalary;
    private Double houseSubsidy;
    private Double hotTempOrOtherAllow;
    private Double workYearsSalary;
    private Double sellCommi;
    private Double speciAddDeductCost;
    private Double personIncomTax;
    private Double basicWorkHours;
    private String remark;

    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;

    private List<Integer> ids;
    private Integer type;


    public Double getBasicWorkHours() {
        return basicWorkHours;
    }

    public void setBasicWorkHours(Double basicWorkHours) {
        this.basicWorkHours = basicWorkHours;
    }

    public String getBigDeptName() {
        return bigDeptName;
    }

    public void setBigDeptName(String bigDeptName) {
        this.bigDeptName = bigDeptName;
    }

    public Double getPersonIncomTax() {
        return personIncomTax;
    }

    public void setPersonIncomTax(Double personIncomTax) {
        this.personIncomTax = personIncomTax;
    }

    public Double getSpeciAddDeductCost() {
        return speciAddDeductCost;
    }

    public void setSpeciAddDeductCost(Double speciAddDeductCost) {
        this.speciAddDeductCost = speciAddDeductCost;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
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

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
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

    public Double getLegalHolidWorkHours() {
        return legalHolidWorkHours;
    }

    public void setLegalHolidWorkHours(Double legalHolidWorkHours) {
        this.legalHolidWorkHours = legalHolidWorkHours;
    }

    public Double getSellActual() {
        return sellActual;
    }

    public void setSellActual(Double sellActual) {
        this.sellActual = sellActual;
    }

    public Double getSellThreshold() {
        return sellThreshold;
    }

    public void setSellThreshold(Double sellThreshold) {
        this.sellThreshold = sellThreshold;
    }

    public Double getSellLevelSalary() {
        return sellLevelSalary;
    }

    public void setSellLevelSalary(Double sellLevelSalary) {
        this.sellLevelSalary = sellLevelSalary;
    }

    public Double getHouseSubsidy() {
        return houseSubsidy;
    }

    public void setHouseSubsidy(Double houseSubsidy) {
        this.houseSubsidy = houseSubsidy;
    }

    public Double getHotTempOrOtherAllow() {
        return hotTempOrOtherAllow;
    }

    public void setHotTempOrOtherAllow(Double hotTempOrOtherAllow) {
        this.hotTempOrOtherAllow = hotTempOrOtherAllow;
    }

    public Double getWorkYearsSalary() {
        return workYearsSalary;
    }

    public void setWorkYearsSalary(Double workYearsSalary) {
        this.workYearsSalary = workYearsSalary;
    }

    public Double getSellCommi() {
        return sellCommi;
    }

    public void setSellCommi(Double sellCommi) {
        this.sellCommi = sellCommi;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
