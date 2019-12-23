package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/15 上午 9:02
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WorkSet implements Serializable {

    private static final long serialVersionUID = 4889657535999033371L;

    private Integer id;
    private String workLevel;
    private String workLevelStr;
    private String month;
    private Date updateDate;
    private Time morningOn;
    private Time morningOnFrom;
    private Time morningOnEnd;
    private Time morningOff;
    private Time morningOffFrom;
    private Time morningOffEnd;
    private Time noonOn;
    private Time noonOnFrom;
    private Time noonOnEnd;
    private Time noonOff;
    private Time noonOffFrom;
    private Time noonOffEnd;
    private Time extworkon;
    private Time extworkonFrom;
    private Time extworkonEnd;
    private Time extworkoff;
    private String remark;

    private String updateDateStr;
    private String morningOnStr;
    private String morningOnFromStr;
    private String morningOnEndStr;
    private String morningOffStr;
    private String morningOffFromStr;
    private String morningOffEndStr;
    private String noonOnStr;
    private String noonOnFromStr;
    private String noonOnEndStr;
    private String noonOffStr;
    private String noonOffFromStr;
    private String noonOffEndStr;
    private String extworkonStr;
    private String extworkoffStr;
    private String extworkonFromStr;
    private String extworkonEndStr;

    private List<String> months;
    private List<String> workLevels;

    private String workDate;

    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;


    public String getWorkLevelStr() {
        return workLevelStr;
    }

    public void setWorkLevelStr(String workLevelStr) {
        this.workLevelStr = workLevelStr;
    }

    public Time getExtworkonFrom() {
        return extworkonFrom;
    }

    public void setExtworkonFrom(Time extworkonFrom) {
        this.extworkonFrom = extworkonFrom;
    }

    public Time getExtworkonEnd() {
        return extworkonEnd;
    }

    public void setExtworkonEnd(Time extworkonEnd) {
        this.extworkonEnd = extworkonEnd;
    }

    public String getExtworkonFromStr() {
        return extworkonFromStr;
    }

    public void setExtworkonFromStr(String extworkonFromStr) {
        this.extworkonFromStr = extworkonFromStr;
    }

    public String getExtworkonEndStr() {
        return extworkonEndStr;
    }

    public void setExtworkonEndStr(String extworkonEndStr) {
        this.extworkonEndStr = extworkonEndStr;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    public List<String> getWorkLevels() {
        return workLevels;
    }

    public void setWorkLevels(List<String> workLevels) {
        this.workLevels = workLevels;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public String getMorningOnStr() {
        return morningOnStr;
    }

    public void setMorningOnStr(String morningOnStr) {
        this.morningOnStr = morningOnStr;
    }

    public String getMorningOnFromStr() {
        return morningOnFromStr;
    }

    public void setMorningOnFromStr(String morningOnFromStr) {
        this.morningOnFromStr = morningOnFromStr;
    }

    public String getMorningOnEndStr() {
        return morningOnEndStr;
    }

    public void setMorningOnEndStr(String morningOnEndStr) {
        this.morningOnEndStr = morningOnEndStr;
    }

    public String getMorningOffStr() {
        return morningOffStr;
    }

    public void setMorningOffStr(String morningOffStr) {
        this.morningOffStr = morningOffStr;
    }

    public String getMorningOffFromStr() {
        return morningOffFromStr;
    }

    public void setMorningOffFromStr(String morningOffFromStr) {
        this.morningOffFromStr = morningOffFromStr;
    }

    public String getMorningOffEndStr() {
        return morningOffEndStr;
    }

    public void setMorningOffEndStr(String morningOffEndStr) {
        this.morningOffEndStr = morningOffEndStr;
    }

    public String getNoonOnStr() {
        return noonOnStr;
    }

    public void setNoonOnStr(String noonOnStr) {
        this.noonOnStr = noonOnStr;
    }

    public String getNoonOnFromStr() {
        return noonOnFromStr;
    }

    public void setNoonOnFromStr(String noonOnFromStr) {
        this.noonOnFromStr = noonOnFromStr;
    }

    public String getNoonOnEndStr() {
        return noonOnEndStr;
    }

    public void setNoonOnEndStr(String noonOnEndStr) {
        this.noonOnEndStr = noonOnEndStr;
    }

    public String getNoonOffStr() {
        return noonOffStr;
    }

    public void setNoonOffStr(String noonOffStr) {
        this.noonOffStr = noonOffStr;
    }

    public String getNoonOffFromStr() {
        return noonOffFromStr;
    }

    public void setNoonOffFromStr(String noonOffFromStr) {
        this.noonOffFromStr = noonOffFromStr;
    }


    public String getExtworkonStr() {
        return extworkonStr;
    }

    public void setExtworkonStr(String extworkonStr) {
        this.extworkonStr = extworkonStr;
    }

    public String getExtworkoffStr() {
        return extworkoffStr;
    }

    public void setExtworkoffStr(String extworkoffStr) {
        this.extworkoffStr = extworkoffStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkLevel() {
        return workLevel;
    }

    public void setWorkLevel(String workLevel) {
        this.workLevel = workLevel;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Time getMorningOn() {
        return morningOn;
    }

    public void setMorningOn(Time morningOn) {
        this.morningOn = morningOn;
    }

    public Time getMorningOnFrom() {
        return morningOnFrom;
    }

    public void setMorningOnFrom(Time morningOnFrom) {
        this.morningOnFrom = morningOnFrom;
    }

    public Time getMorningOnEnd() {
        return morningOnEnd;
    }

    public void setMorningOnEnd(Time morningOnEnd) {
        this.morningOnEnd = morningOnEnd;
    }

    public Time getMorningOff() {
        return morningOff;
    }

    public void setMorningOff(Time morningOff) {
        this.morningOff = morningOff;
    }

    public Time getMorningOffFrom() {
        return morningOffFrom;
    }

    public void setMorningOffFrom(Time morningOffFrom) {
        this.morningOffFrom = morningOffFrom;
    }

    public Time getMorningOffEnd() {
        return morningOffEnd;
    }

    public void setMorningOffEnd(Time morningOffEnd) {
        this.morningOffEnd = morningOffEnd;
    }

    public Time getNoonOn() {
        return noonOn;
    }

    public void setNoonOn(Time noonOn) {
        this.noonOn = noonOn;
    }

    public Time getNoonOnFrom() {
        return noonOnFrom;
    }

    public void setNoonOnFrom(Time noonOnFrom) {
        this.noonOnFrom = noonOnFrom;
    }

    public Time getNoonOnEnd() {
        return noonOnEnd;
    }

    public void setNoonOnEnd(Time noonOnEnd) {
        this.noonOnEnd = noonOnEnd;
    }

    public Time getNoonOff() {
        return noonOff;
    }

    public void setNoonOff(Time noonOff) {
        this.noonOff = noonOff;
    }

    public Time getNoonOffFrom() {
        return noonOffFrom;
    }

    public void setNoonOffFrom(Time noonOffFrom) {
        this.noonOffFrom = noonOffFrom;
    }

    public Time getNoonOffEnd() {
        return noonOffEnd;
    }

    public void setNoonOffEnd(Time noonOffEnd) {
        this.noonOffEnd = noonOffEnd;
    }

    public Time getExtworkon() {
        return extworkon;
    }

    public void setExtworkon(Time extworkon) {
        this.extworkon = extworkon;
    }

    public Time getExtworkoff() {
        return extworkoff;
    }

    public void setExtworkoff(Time extworkoff) {
        this.extworkoff = extworkoff;
    }

    public String getNoonOffEndStr() {
        return noonOffEndStr;
    }

    public void setNoonOffEndStr(String noonOffEndStr) {
        this.noonOffEndStr = noonOffEndStr;
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
        return currentPageTotalNum;
    }

    public void setCurrentPageTotalNum(int currentPageTotalNum) {
        this.currentPageTotalNum = currentPageTotalNum;
    }
}
