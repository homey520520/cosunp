package com.cosun.cosunp.entity;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/3/14 0014 下午 5:18
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class MultipartFileParam implements Serializable {

    private static final long serialVersionUID = 3659911280472399948L;

    private String orderNo;
    private String salor;
    private String webkitRelativePath;
    private String projectName;
    private String filedescribtion;
    private String remark;
    private String modifyreason;
    private String md5;
    private String webkitRelativePaths;
    private String fileNames;
    private String randomNum;
    private  String saveFolderName;
    private String filefolderNames;
    private String uid;
    private String id;
    private int chunks;
    private int chunk;
    private long size = 0L;
    private String name;
    private MultipartFile file;


    public String getFilefolderNames() {
        return filefolderNames;
    }

    public void setFilefolderNames(String filefolderNames) {
        this.filefolderNames = filefolderNames;
    }

    public String getModifyreason() {
        return modifyreason;
    }

    public void setModifyreason(String modifyreason) {
        this.modifyreason = modifyreason;
    }

    public String getSaveFolderName() {
        return saveFolderName;
    }

    public void setSaveFolderName(String saveFolderName) {
        this.saveFolderName = saveFolderName;
    }

    public String getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(String randomNum) {
        this.randomNum = randomNum;
    }

    public String getWebkitRelativePaths() {
        return webkitRelativePaths;
    }

    public void setWebkitRelativePaths(String webkitRelativePaths) {
        this.webkitRelativePaths = webkitRelativePaths;
    }

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }

    public String getSalor() {
        return salor;
    }

    public void setSalor(String salor) {
        this.salor = salor;
    }

    public String getFiledescribtion() {
        return filedescribtion;
    }

    public void setFiledescribtion(String filedescribtion) {
        this.filedescribtion = filedescribtion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getMd5() {
        return md5;
    }

    public String getWebkitRelativePath() {
        return webkitRelativePath;
    }

    public void setWebkitRelativePath(String webkitRelativePath) {
        this.webkitRelativePath = webkitRelativePath;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }


}
