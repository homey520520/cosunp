package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/10/24  08:50
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class JiaBan implements Serializable {

    private static final long serialVersionUID = 8949575754573020841L;

    private Integer id;
    private String empNo;
    private Date extDateFrom;
    private Date extDateEnd;
    private Double extWorkHours;
    private Integer type;
    private String remark;

    private String positionLevel;



    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;


    private String name;
    private Integer deptId;
    private String typeStr;
    private String deptName;
    private Integer empId;
    private String extDateFromStr;
    private String extDateEndStr;


    private List<Integer> ids;
    private List<Integer> deptIds;
    private List<Integer> positionIds;
    private List<Integer> types;
    private List<Integer> names;


    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
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

    public Date getExtDateFrom() {
        return extDateFrom;
    }

    public void setExtDateFrom(Date extDateFrom) {
        this.extDateFrom = extDateFrom;
    }

    public Date getExtDateEnd() {
        return extDateEnd;
    }

    public void setExtDateEnd(Date extDateEnd) {
        this.extDateEnd = extDateEnd;
    }

    public Double getExtWorkHours() {
        return extWorkHours;
    }

    public void setExtWorkHours(Double extWorkHours) {
        this.extWorkHours = extWorkHours;
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

    public String getTypeStr() {
        if (type != null) {
            if (type == 1) {
                return "平时加班";
            } else if (type == 2) {
                return "周末加班";
            } else if (type == 3) {
                return "法定假日加班";
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

    public String getExtDateFromStr() {
        return extDateFromStr;
    }

    public void setExtDateFromStr(String extDateFromStr) {
        this.extDateFromStr = extDateFromStr;
    }

    public String getExtDateEndStr() {
        return extDateEndStr;
    }

    public void setExtDateEndStr(String extDateEndStr) {
        this.extDateEndStr = extDateEndStr;
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
