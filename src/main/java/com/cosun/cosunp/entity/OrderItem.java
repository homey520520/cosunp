package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * @author:homey Wong
 * @Date: 2019/8/13 0013 上午 11:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 8538595894167763085L;

    private Integer id;
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
    private Date itemDeliverTime;
    private String productName;
    private Integer needNum;
    private Date itemCreateTime;
    private Date itemUpdateTime;
    private Integer updateItemTimes;
    private String productTotalName;
    private Date pmcDeliverTime;
    private String needAdvanceMaterials;
    private String outWorkFiles;
    private Date pmcRecordTime;
    private String pmcRecordEmpno;

    private String pmcDeliverTimeStr;
    private String pmcRecordTimeStr;
    private String pmcConfirmTimeStr;
    private String itemUpdateTimeStr;
    private String itemCreateTimeStr;
    private String itemDeliverTimeStr;


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

    public String getProductTotalName() {
        return productTotalName;
    }

    public void setProductTotalName(String productTotalName) {
        this.productTotalName = productTotalName;
    }

    public Integer getUpdateItemTimes() {
        return updateItemTimes;
    }

    public void setUpdateItemTimes(Integer updateItemTimes) {
        this.updateItemTimes = updateItemTimes;
    }

    public String getItemUpdateTimeStr() {
        return itemUpdateTimeStr;
    }

    public void setItemUpdateTimeStr(String itemUpdateTimeStr) {
        this.itemUpdateTimeStr = itemUpdateTimeStr;
    }

    public Date getItemCreateTime() {
        return itemCreateTime;
    }

    public void setItemCreateTime(Date itemCreateTime) {
        this.itemCreateTime = itemCreateTime;
    }

    public String getItemCreateTimeStr() {
        return itemCreateTimeStr;
    }

    public void setItemCreateTimeStr(String itemCreateTimeStr) {
        this.itemCreateTimeStr = itemCreateTimeStr;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getNeedNum() {
        return needNum;
    }

    public void setNeedNum(Integer needNum) {
        this.needNum = needNum;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getItemDeliverTime() {
        return itemDeliverTime;
    }

    public void setItemDeliverTime(Date itemDeliverTime) {
        this.itemDeliverTime = itemDeliverTime;
    }

    public String getItemDeliverTimeStr() {
        return itemDeliverTimeStr;
    }

    public void setItemDeliverTimeStr(String itemDeliverTimeStr) {
        this.itemDeliverTimeStr = itemDeliverTimeStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getItemUpdateTime() {
        return itemUpdateTime;
    }

    public void setItemUpdateTime(Date itemUpdateTime) {
        this.itemUpdateTime = itemUpdateTime;
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
}
