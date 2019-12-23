package com.cosun.cosunp.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/20  下午 2:06
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Rules implements Serializable {

    private static final long serialVersionUID = -3761627936696949450L;

    private Integer id;
    private String deptStr;
    private Integer deptId;
    private String deptName;
    private Integer uploaderId;
    private String uploaderName;
    private Date uploadDate;
    private String uploadDateStr;
    private String titleName;
    private String remark;
    private String fileName;
    private String loginName;
    private MultipartFile uploadRuleFile;
    private String fileDir;
    private Integer userActor;


    private Integer firstShow;
    private List<Integer> deptIds;
    private List<Integer> ids;
    private String ftpDir;


    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;


    public String getFtpDir() {
        return ftpDir;
    }

    public void setFtpDir(String ftpDir) {
        this.ftpDir = ftpDir;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getFirstShow() {
        return firstShow;
    }

    public void setFirstShow(Integer firstShow) {
        this.firstShow = firstShow;
    }

    public String getDeptStr() {
        if (deptId == 1) {
            return "总经办";
        }
        if (deptId == 2) {
            return "财务部";
        }
        if (deptId == 3) {
            return "人事行政部";
        }
        if (deptId == 4) {
            return "项目中心";
        }
        if (deptId == 5) {
            return "销售中心";
        }
        if (deptId == 6) {
            return "采购部";
        }
        if (deptId == 7) {
            return "数字研发中心";
        }
        if(deptId==8) {
            return "生产制造";
        }
        return null;
    }

    public void setDeptStr(String deptStr) {
        this.deptStr = deptStr;
    }

    public Integer getUserActor() {
        return userActor;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public void setUserActor(Integer userActor) {
        this.userActor = userActor;
    }

    public String getFileName() {
        return fileName;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public MultipartFile getUploadRuleFile() {
        return uploadRuleFile;
    }

    public void setUploadRuleFile(MultipartFile uploadRuleFile) {
        this.uploadRuleFile = uploadRuleFile;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadDateStr() {
        return uploadDateStr;
    }

    public void setUploadDateStr(String uploadDateStr) {
        this.uploadDateStr = uploadDateStr;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
