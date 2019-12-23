package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:homey Wong
 * @date:2019/3/25 0025 上午 11:34
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FilemanUpdateRecord implements Serializable {

    private static final long serialVersionUID = -2298107538697760983L;

    private Integer id;
    private Integer fileurlid;
    private String orderNum;
    private String projectName;
    private String fileName;
    private Integer updateUid;
    private String updateFullName;
    private String updateReason;
    private Date updateDate;
    private String updateDateStr;

    public String getUpdateFullName() {
        return updateFullName;
    }

    public void setUpdateFullName(String updateFullName) {
        this.updateFullName = updateFullName;
    }

    public Integer getFileurlid() {
        return fileurlid;
    }

    public void setFileurlid(Integer fileurlid) {
        this.fileurlid = fileurlid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }

    public String getUpdateReason() {
        return updateReason;
    }

    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateDateStr() {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(this.updateDate);
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }
}
