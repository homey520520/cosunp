package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/10/22  13:50
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class LianBan implements Serializable {

    private static final long serialVersionUID = 8949575754573020841L;

    private Integer id;
    private String empNo;
    private Date date;
    private Double noonHours;
    private Double nightHours;
    private Integer type;
    private String remark;

    private String dateStr;

    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;


    private String name;
    private Integer deptId;
    private String beginLianBanStr;
    private String endLianBanStr;
    private String typeStr;
    private String deptName;
    private Integer empId;


    private List<Integer> ids;
    private List<Integer> deptIds;
    private List<Integer> positionIds;
    private List<Integer> types;
    private List<Integer> names;


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getNoonHours() {
        return noonHours;
    }

    public void setNoonHours(Double noonHours) {
        this.noonHours = noonHours;
    }

    public Double getNightHours() {
        return nightHours;
    }

    public void setNightHours(Double nightHours) {
        this.nightHours = nightHours;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getBeginLianBanStr() {
        return beginLianBanStr;
    }

    public void setBeginLianBanStr(String beginLianBanStr) {
        this.beginLianBanStr = beginLianBanStr;
    }

    public String getEndLianBanStr() {
        return endLianBanStr;
    }

    public void setEndLianBanStr(String endLianBanStr) {
        this.endLianBanStr = endLianBanStr;
    }

    public String getTypeStr() {
        if (type != null) {
            if (type == 1) {
                return "厂内连班";
            } else if (type == 2) {
                return "厂外连班";
            } else if (type == 3) {
                return "其它连班";
            }
        }
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
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

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public List<Integer> getNames() {
        return names;
    }

    public void setNames(List<Integer> names) {
        this.names = names;
    }
}
