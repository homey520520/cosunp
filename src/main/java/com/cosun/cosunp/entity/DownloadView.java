package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/21 0021 上午 10:36
 * @Modified By:
 * @Modified-date:2018/12/21  上午 10:36
 */
public class DownloadView implements Serializable {

    private static final long serialVersionUID = 8178603164200909341L;
    private String random8;
    private Integer id;
    private Integer uId;
    private String userName;
    private String password;
    private String creator;
    private String lastUpdator;
    private String fileName;
    private String salor;
    private String orderNo;
    private String[] check_val;
    private String projectName;
    private String lastUpdateTime;
    private String fullName;
    private String createTime;
    private Integer totalUpdateNum;
    private String opRight;
    private String urlAddr;
    private String uploadPrivilege;
    private String updatePrivilege;
    private String downloadPrivilege;
    private String deletePrivilege;
    private String flag;
    private String remark;
    private String filedescribtion;
    private String engineer;
    private String existFileMessage;
    private String noExsitFileMessage;
    private Integer isExistNum;
    private String modifyReason;
    private Integer fileRightId;
    private Integer fileUrlId;
    private String folderOrFileName;
    private Date upTime;
    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 15;
    private int currentPageTotalNum;
    private int preCurrentPage;
    private int aftCurrentPage;
    private Date startNewestSaveDate;
    private Date endNewestSaveDate;
    private String startNewestSaveDateStr;
    private String endNewestSaveDateStr;
    private Integer singleFileUpdateNum;
    private String orderNoMessage;
    private String privilegeusers;
    private String oprighter;
    private String folderName;
    private List<String> orderNoS;
    private String saveFolderName;
    private String linshiId;
    private String filePathName;
    private String filePathNames;


    public Integer getFileUrlId() {
        return fileUrlId;
    }

    public void setFileUrlId(Integer fileUrlId) {
        this.fileUrlId = fileUrlId;
    }

    public String getFilePathNames() {
        return filePathNames;
    }

    public void setFilePathNames(String filePathNames) {
        this.filePathNames = filePathNames;
    }

    public String getRandom8() {
        return random8;
    }

    public void setRandom8(String random8) {
        this.random8 = random8;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFilePathName() {
        return filePathName;
    }

    public void setFilePathName(String filePathName) {
        this.filePathName = filePathName;
    }

    public String getLinshiId() {
        return linshiId;
    }

    public void setLinshiId(String linshiId) {
        this.linshiId = linshiId;
    }

    public String[] getCheck_val() {
        return check_val;
    }

    public String getFolderOrFileName() {
        return folderOrFileName;
    }

    public void setFolderOrFileName(String folderOrFileName) {
        this.folderOrFileName = folderOrFileName;
    }

    public List<String> getOrderNoS() {
        return orderNoS;
    }

    public void setOrderNoS(List<String> orderNoS) {
        this.orderNoS = orderNoS;
    }

    public void setCheck_val(String[] check_val) {
        this.check_val = check_val;
    }

    public String getSaveFolderName() {
        return saveFolderName;
    }

    public void setSaveFolderName(String saveFolderName) {
        this.saveFolderName = saveFolderName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Integer getFileRightId() {
        return fileRightId;
    }

    public void setFileRightId(Integer fileRightId) {
        this.fileRightId = fileRightId;
    }

    public String getOprighter() {
        return oprighter;
    }

    public void setOprighter(String oprighter) {
        this.oprighter = oprighter;
    }

    private Integer type;//用户权限  1代表有管理权限  0代表没有管理权限进不了管理页面

    public String getPrivilegeusers() {
        return privilegeusers;
    }

    public void setPrivilegeusers(String privilegeusers) {
        this.privilegeusers = privilegeusers;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public String getNoExsitFileMessage() {
        return noExsitFileMessage;
    }

    public void setNoExsitFileMessage(String noExsitFileMessage) {
        this.noExsitFileMessage = noExsitFileMessage;
    }

    public Integer getSingleFileUpdateNum() {
        return singleFileUpdateNum;
    }

    public void setSingleFileUpdateNum(Integer singleFileUpdateNum) {
        this.singleFileUpdateNum = singleFileUpdateNum;
    }

    public String getModifyReason() {
        return modifyReason;
    }

    public void setModifyReason(String modifyReason) {
        this.modifyReason = modifyReason;
    }

    public String getExistFileMessage() {
        return existFileMessage;
    }

    public void setExistFileMessage(String existFileMessage) {
        this.existFileMessage = existFileMessage;
    }

    public String getEngineer() {
        return engineer;
    }

    public String getOrderNoMessage() {
        return orderNoMessage;
    }

    public Integer getIsExistNum() {
        return isExistNum;
    }

    public void setIsExistNum(Integer isExistNum) {
        this.isExistNum = isExistNum;
    }

    public void setOrderNoMessage(String orderNoMessage) {
        this.orderNoMessage = orderNoMessage;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public Date getStartNewestSaveDate() {
        return startNewestSaveDate;
    }

    public void setStartNewestSaveDate(Date startNewestSaveDate) {
        this.startNewestSaveDate = startNewestSaveDate;
    }

    public Date getEndNewestSaveDate() {
        return endNewestSaveDate;
    }

    public void setEndNewestSaveDate(Date endNewestSaveDate) {
        this.endNewestSaveDate = endNewestSaveDate;
    }

    public String getStartNewestSaveDateStr() {
        if (startNewestSaveDateStr != null && startNewestSaveDateStr.trim().length() > 0)
            return startNewestSaveDateStr.toString() + " 00:00:00";
        return null;
    }

    public void setStartNewestSaveDateStr(String startNewestSaveDateStr) {
        this.startNewestSaveDateStr = startNewestSaveDateStr;
    }

    public String getEndNewestSaveDateStr() {
        if (endNewestSaveDateStr != null && endNewestSaveDateStr.trim().length() > 0)
            return endNewestSaveDateStr.toString() + " 23:59:59";
        return null;
    }

    public void setEndNewestSaveDateStr(String endNewestSaveDateStr) {
        this.endNewestSaveDateStr = endNewestSaveDateStr;
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

    public int getPreCurrentPage() {

        return currentPage - 1;
    }

    public void setPreCurrentPage(int preCurrentPage) {
        this.preCurrentPage = preCurrentPage;
    }

    public int getAftCurrentPage() {
        return currentPage + 1;
    }

    public void setAftCurrentPage(int aftCurrentPage) {
        this.aftCurrentPage = aftCurrentPage;
    }

    public int getCurrentPageTotalNum() {
        if(this.currentPage!=0)
        return (currentPage - 1) * pageSize;
        return 0;
    }

    public void setCurrentPageTotalNum(int currentPageTotalNum) {
        this.currentPageTotalNum = currentPageTotalNum;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUploadPrivilege() {
        if (opRight != null && opRight.trim().length() > 0 && opRight.contains("1")) {
            return "1";
        }
        return uploadPrivilege;
    }

    public void setUploadPrivilege(String uploadPrivilege) {
        this.uploadPrivilege = uploadPrivilege;
    }

    public String getDownloadPrivilege() {
        if (opRight != null && opRight.trim().length() > 0 && opRight.contains("3")) {
            return "3";
        }
        return downloadPrivilege;
    }

    public void setDownloadPrivilege(String downloadPrivilege) {
        this.downloadPrivilege = downloadPrivilege;
    }

    public String getDeletePrivilege() {
        if (opRight != null && opRight.trim().length() > 0 && opRight.contains("4")) {
            return "4";
        }
        return deletePrivilege;
    }

    public void setDeletePrivilege(String deletePrivilege) {
        this.deletePrivilege = deletePrivilege;
    }

    public String getUpdatePrivilege() {
        if (opRight != null && opRight.trim().length() > 0 && opRight.contains("2")) {
            return "2";
        }
        return updatePrivilege;

    }

    public void setUpdatePrivilege(String updatePrivilege) {
        this.updatePrivilege = updatePrivilege;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastUpdator() {
        return lastUpdator;
    }

    public void setLastUpdator(String lastUpdator) {
        this.lastUpdator = lastUpdator;
    }

    public String getFileName() {
        if (fileName != null)
            return fileName.trim();
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSalor() {
        if (salor != null)
            return salor.trim();
        return salor;
    }

    public void setSalor(String salor) {
        this.salor = salor;
    }

    public String getOrderNo() {
        if (orderNo != null) {
            return orderNo.trim();
        }
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProjectName() {
        if (projectName != null)
            return projectName.trim();
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getTotalUpdateNum() {
        return totalUpdateNum;
    }

    public void setTotalUpdateNum(Integer totalUpdateNum) {
        this.totalUpdateNum = totalUpdateNum;
    }

    public String getOpRight() {
        return opRight;
    }

    public void setOpRight(String opRight) {
        this.opRight = opRight;
    }

    public String getUrlAddr() {
        return urlAddr;
    }

    public void setUrlAddr(String urlAddr) {
        this.urlAddr = urlAddr;
    }
}
