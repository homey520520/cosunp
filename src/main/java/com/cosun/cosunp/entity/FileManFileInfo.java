package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

public class FileManFileInfo implements Serializable {


    private static final long serialVersionUID = -862527980577520420L;
    private Integer id;
    private Integer uId;
    private String userName;
    private String fileName;
    private String orderNum;
    private String projectName;
    private String createUser;
    private String updateUser;
    private Date createTime;
    private Date updateTime;
    private Integer updateCount;
    private Integer totalFilesNum;
    private String extInfo1;
    private String extInfo2;
    private String extInfo3;
    private String remark;
    private String filedescribtion;

    public Integer getTotalFilesNum() {
        return totalFilesNum;
    }

    public void setTotalFilesNum(Integer totalFilesNum) {
        this.totalFilesNum = totalFilesNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFiledescribtion() {
        return filedescribtion;
    }

    public void setFiledescribtion(String filedescribtion) {
        this.filedescribtion = filedescribtion;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateCount() {
        if(this.updateCount==null){
            return 0;
        }
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }

    public String getExtInfo1() {
        return extInfo1;
    }

    public void setExtInfo1(String extInfo1) {
        this.extInfo1 = extInfo1;
    }

    public String getExtInfo2() {
        return extInfo2;
    }

    public void setExtInfo2(String extInfo2) {
        this.extInfo2 = extInfo2;
    }

    public String getExtInfo3() {
        return extInfo3;
    }

    public void setExtInfo3(String extInfo3) {
        this.extInfo3 = extInfo3;
    }
}
