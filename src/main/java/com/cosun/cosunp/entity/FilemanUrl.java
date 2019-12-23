package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

public class FilemanUrl implements Serializable {

    private static final long serialVersionUID = -8416020544103530588L;
    private Integer id;
    private Integer fileInfoId;
    private String userName;
    private String orginName;
    private Date upTime;
    private String opRight;
    private String logur1;
    private Integer singleFileUpdateNum;
    private String modifyReason;
    private Date updateTime;
    private String updateuser;
    private Integer uId;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getSingleFileUpdateNum() {
        if(singleFileUpdateNum==null){
            return 0;
        }
        return singleFileUpdateNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    public String getModifyReason() {
        return modifyReason;
    }

    public void setModifyReason(String modifyReason) {
        this.modifyReason = modifyReason;
    }

    public void setSingleFileUpdateNum(Integer singleFileUpdateNum) {
        this.singleFileUpdateNum = singleFileUpdateNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(Integer fileInfoId) {
        this.fileInfoId = fileInfoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrginName() {

        return orginName;
    }

    public void setOrginName(String orginName) {
        this.orginName = orginName;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public String getOpRight() {

        return opRight;
    }

    public void setOpRight(String opRight) {
        this.opRight = opRight;
    }

    public String getLogur1() {
        return logur1;
    }

    public void setLogur1(String logur1) {
        this.logur1 = logur1;
    }
}
