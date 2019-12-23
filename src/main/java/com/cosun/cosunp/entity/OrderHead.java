package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/8/13 0013 上午 11:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class OrderHead implements Serializable {

    private static final long serialVersionUID = 8581338015386967740L;

    private Integer id;
    private String orderNo;
    private String productTotalName;
    private String orderTimeStr;
    private Integer orderSetNum;
    private String SalorNo;
    private Integer state;
    private Integer singleOrProject;
    private Date headUpdateTime;
    private Integer updateHeadTimes;
    private String confirmEmpNo;
    private Date confirmTime;
    private String pmcConfirmEmpno;
    private Date pmcConfirmTime;

    private String confirmTimeStr;
    private Date itemUpdateTime;
    private String headUpdateTimeStr;
    private String itemUpdateTimeStr;

    private List<Integer> nameIds;
    private List<Integer> orderNos;
    private List<String> prodNames;
    private List<Integer> singleOrProjects;
    private List<Integer> states;

    private Date orderTime;
    private Date deliverTime;
    private String engName;

    private String pmcDeliverTimeStr;
    private String pmcRecordTimeStr;
    private String pmcConfirmTimeStr;
    private String itemDeliverTimeStr;

    private String salor;
    private String singleOrProjectStr;
    private String stateStr;
    private String itemCreateTimeStr;

    private Integer orderHeadId;
    private String productBigType;
    private String productMainShape;
    private String newFinishProudNo;
    private Double productSize;
    private Double edgeHightSize;
    private String mainMateriAndArt;
    private String backInstallSelect;
    private String electMateriNeeds;
    private String installTransfBacking;
    private String otherRemark;
    private String productName;
    private Integer needNum;
    private Date pmcDeliverTime;
    private String needAdvanceMaterials;
    private String outWorkFiles;
    private Date pmcRecordTime;
    private String pmcRecordEmpno;

    private String shortEngName;
    private Integer itemId;

    private String orderItemList;


    private String folderName;


    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;
    private String sortMethod;
    private String sortByName;

    public String getConfirmEmpNo() {
        return confirmEmpNo;
    }

    public String getPmcConfirmEmpno() {
        return pmcConfirmEmpno;
    }

    public void setPmcConfirmEmpno(String pmcConfirmEmpno) {
        this.pmcConfirmEmpno = pmcConfirmEmpno;
    }

    public Date getPmcConfirmTime() {
        return pmcConfirmTime;
    }

    public void setPmcConfirmTime(Date pmcConfirmTime) {
        this.pmcConfirmTime = pmcConfirmTime;
    }

    public String getPmcDeliverTimeStr() {
        return pmcDeliverTimeStr;
    }

    public void setPmcDeliverTimeStr(String pmcDeliverTimeStr) {
        this.pmcDeliverTimeStr = pmcDeliverTimeStr;
    }

    public String getPmcRecordTimeStr() {
        return pmcRecordTimeStr;
    }

    public void setPmcRecordTimeStr(String pmcRecordTimeStr) {
        this.pmcRecordTimeStr = pmcRecordTimeStr;
    }

    public String getPmcConfirmTimeStr() {
        return pmcConfirmTimeStr;
    }

    public void setPmcConfirmTimeStr(String pmcConfirmTimeStr) {
        this.pmcConfirmTimeStr = pmcConfirmTimeStr;
    }

    public Date getPmcDeliverTime() {
        return pmcDeliverTime;
    }

    public void setPmcDeliverTime(Date pmcDeliverTime) {
        this.pmcDeliverTime = pmcDeliverTime;
    }

    public String getNeedAdvanceMaterials() {
        return needAdvanceMaterials;
    }

    public void setNeedAdvanceMaterials(String needAdvanceMaterials) {
        this.needAdvanceMaterials = needAdvanceMaterials;
    }

    public String getOutWorkFiles() {
        return outWorkFiles;
    }

    public void setOutWorkFiles(String outWorkFiles) {
        this.outWorkFiles = outWorkFiles;
    }

    public Date getPmcRecordTime() {
        return pmcRecordTime;
    }

    public void setPmcRecordTime(Date pmcRecordTime) {
        this.pmcRecordTime = pmcRecordTime;
    }

    public String getPmcRecordEmpno() {
        return pmcRecordEmpno;
    }

    public void setPmcRecordEmpno(String pmcRecordEmpno) {
        this.pmcRecordEmpno = pmcRecordEmpno;
    }

    public void setConfirmEmpNo(String confirmEmpNo) {
        this.confirmEmpNo = confirmEmpNo;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmTimeStr() {
        return confirmTimeStr;
    }

    public void setConfirmTimeStr(String confirmTimeStr) {
        this.confirmTimeStr = confirmTimeStr;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Integer getUpdateHeadTimes() {
        return updateHeadTimes;
    }

    public void setUpdateHeadTimes(Integer updateHeadTimes) {
        this.updateHeadTimes = updateHeadTimes;
    }

    public String getHeadUpdateTimeStr() {
        return headUpdateTimeStr;
    }

    public void setHeadUpdateTimeStr(String headUpdateTimeStr) {
        this.headUpdateTimeStr = headUpdateTimeStr;
    }

    public String getItemUpdateTimeStr() {
        return itemUpdateTimeStr;
    }

    public void setItemUpdateTimeStr(String itemUpdateTimeStr) {
        this.itemUpdateTimeStr = itemUpdateTimeStr;
    }

    public Date getHeadUpdateTime() {
        return headUpdateTime;
    }

    public void setHeadUpdateTime(Date headUpdateTime) {
        this.headUpdateTime = headUpdateTime;
    }

    public Date getItemUpdateTime() {
        return itemUpdateTime;
    }

    public void setItemUpdateTime(Date itemUpdateTime) {
        this.itemUpdateTime = itemUpdateTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getItemCreateTimeStr() {
        return itemCreateTimeStr;
    }

    public void setItemCreateTimeStr(String itemCreateTimeStr) {
        this.itemCreateTimeStr = itemCreateTimeStr;
    }

    public List<Integer> getNameIds() {
        return nameIds;
    }

    public void setNameIds(List<Integer> nameIds) {
        this.nameIds = nameIds;
    }


    public Integer getNeedNum() {
        return needNum;
    }

    public void setNeedNum(Integer needNum) {
        this.needNum = needNum;
    }

    public List<Integer> getOrderNos() {
        return orderNos;
    }

    public void setOrderNos(List<Integer> orderNos) {
        this.orderNos = orderNos;
    }

    public List<String> getProdNames() {
        return prodNames;
    }

    public void setProdNames(List<String> prodNames) {
        this.prodNames = prodNames;
    }

    public List<Integer> getSingleOrProjects() {
        return singleOrProjects;
    }

    public void setSingleOrProjects(List<Integer> singleOrProjects) {
        this.singleOrProjects = singleOrProjects;
    }

    public List<Integer> getStates() {
        return states;
    }

    public void setStates(List<Integer> states) {
        this.states = states;
    }

    public String getStateStr() {
        if (this.state != null) {
            if (this.state == 0) {
                return "未审核";
            } else if (this.state == 1) {
                return "审核通过/下发";
            } else if (this.state == 2) {
                return "审核未通过";
            } else if(this.state==3) {
                return "暂停";
            } else if(this.state==4) {
                return "终止";
            }else if(this.state==5) {
                return "完成";
            }
        }
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getSingleOrProjectStr() {
        if (singleOrProject != null) {
            if (this.singleOrProject == 0) {
                return "单项订单";
            } else if (this.singleOrProject == 1) {
                return "项目订单";
            }
        }
        return singleOrProjectStr;
    }

    public void setSingleOrProjectStr(String singleOrProjectStr) {
        this.singleOrProjectStr = singleOrProjectStr;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public String getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getItemDeliverTimeStr() {
        return itemDeliverTimeStr;
    }

    public void setItemDeliverTimeStr(String itemDeliverTimeStr) {
        this.itemDeliverTimeStr = itemDeliverTimeStr;
    }

    public String getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(String orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getShortEngName() {
        return shortEngName;
    }

    public void setShortEngName(String shortEngName) {
        this.shortEngName = shortEngName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getSalor() {
        return salor;
    }

    public void setSalor(String salor) {
        this.salor = salor;
    }

    public Integer getOrderHeadId() {
        return orderHeadId;
    }

    public void setOrderHeadId(Integer orderHeadId) {
        this.orderHeadId = orderHeadId;
    }

    public String getProductBigType() {
        return productBigType;
    }

    public void setProductBigType(String productBigType) {
        this.productBigType = productBigType;
    }

    public String getProductMainShape() {
        return productMainShape;
    }

    public void setProductMainShape(String productMainShape) {
        this.productMainShape = productMainShape;
    }

    public String getNewFinishProudNo() {
        return newFinishProudNo;
    }

    public void setNewFinishProudNo(String newFinishProudNo) {
        this.newFinishProudNo = newFinishProudNo;
    }

    public Double getProductSize() {
        return productSize;
    }

    public void setProductSize(Double productSize) {
        this.productSize = productSize;
    }

    public Double getEdgeHightSize() {
        return edgeHightSize;
    }

    public void setEdgeHightSize(Double edgeHightSize) {
        this.edgeHightSize = edgeHightSize;
    }

    public String getMainMateriAndArt() {
        return mainMateriAndArt;
    }

    public void setMainMateriAndArt(String mainMateriAndArt) {
        this.mainMateriAndArt = mainMateriAndArt;
    }

    public String getBackInstallSelect() {
        return backInstallSelect;
    }

    public void setBackInstallSelect(String backInstallSelect) {
        this.backInstallSelect = backInstallSelect;
    }

    public String getElectMateriNeeds() {
        return electMateriNeeds;
    }

    public void setElectMateriNeeds(String electMateriNeeds) {
        this.electMateriNeeds = electMateriNeeds;
    }

    public String getInstallTransfBacking() {
        return installTransfBacking;
    }

    public void setInstallTransfBacking(String installTransfBacking) {
        this.installTransfBacking = installTransfBacking;
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public String getSalorNo() {
        return SalorNo;
    }

    public void setSalorNo(String salorNo) {
        SalorNo = salorNo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSingleOrProject() {
        return singleOrProject;
    }

    public void setSingleOrProject(Integer singleOrProject) {
        this.singleOrProject = singleOrProject;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductTotalName() {
        return productTotalName;
    }

    public void setProductTotalName(String productTotalName) {
        this.productTotalName = productTotalName;
    }

    public String getOrderTimeStr() {
        return orderTimeStr;
    }

    public void setOrderTimeStr(String orderTimeStr) {
        this.orderTimeStr = orderTimeStr;
    }

    public Integer getOrderSetNum() {
        return orderSetNum;
    }

    public void setOrderSetNum(Integer orderSetNum) {
        this.orderSetNum = orderSetNum;
    }
}
