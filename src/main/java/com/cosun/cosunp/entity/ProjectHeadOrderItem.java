package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @Date: 2020/3/23  下午 4:30
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ProjectHeadOrderItem implements Serializable {


    private static final long serialVersionUID = -4140054436131666981L;

    private Integer id;
    private Integer order_Id;
    private Date delivery_Date;
    private String totalBao;
    private String product_Name;
    private Date getOrder_Date_Plan;
    private Date getOrder_Date_Accu;
    private Date zhanCha_Date_Plan;
    private Date zhanCha_Date_Accu;
    private Date outDraw_Date_Plan;
    private Date outDraw_Date_Accu;
    private Date program_confir_Date_Plan;
    private Date program_confir_Date_Accu;
    private Date giveOrder_Date_Plan;
    private Date giveOrder_Date_Accu;
    private Date delivery_Goods_Date_Plan;
    private Date delivery_Goods_Date_Accu;
    private Date install_Date_Plan;
    private Date install_Date_Accu;
    private Date yanShou_Date_Plan;
    private Date yanShou_Date_Accu;
    private Date jieSuan_Date_Plan;
    private Date jieSuan_Date_Accu;
    private String jindu_remark;
    private Double hetong_money;
    private Double daokuan_money;
    private Double weihuikuan_money;
    private String remark;
    private String project_Manager1;
    private String project_Manager2;
    private String project_Manager3;


    private String delivery_DateStr;
    private String getOrder_Date_PlanStr;
    private String getOrder_Date_AccuStr;
    private String zhanCha_Date_PlanStr;
    private String zhanCha_Date_AccuStr;
    private String outDraw_Date_PlanStr;
    private String outDraw_Date_AccuStr;
    private String program_confir_Date_PlanStr;
    private String program_confir_Date_AccuStr;
    private String giveOrder_Date_PlanStr;
    private String giveOrder_Date_AccuStr;
    private String delivery_Goods_Date_PlanStr;
    private String delivery_Goods_Date_AccuStr;
    private String install_Date_PlanStr;
    private String install_Date_AccuStr;
    private String yanShou_Date_PlanStr;
    private String yanShou_Date_AccuStr;
    private String jieSuan_Date_PlanStr;
    private String jieSuan_Date_AccuStr;


    public String getDelivery_DateStr() {
        return delivery_DateStr;
    }

    public void setDelivery_DateStr(String delivery_DateStr) {
        this.delivery_DateStr = delivery_DateStr;
    }

    public String getGetOrder_Date_PlanStr() {
        return getOrder_Date_PlanStr;
    }

    public void setGetOrder_Date_PlanStr(String getOrder_Date_PlanStr) {
        this.getOrder_Date_PlanStr = getOrder_Date_PlanStr;
    }

    public String getGetOrder_Date_AccuStr() {
        return getOrder_Date_AccuStr;
    }

    public void setGetOrder_Date_AccuStr(String getOrder_Date_AccuStr) {
        this.getOrder_Date_AccuStr = getOrder_Date_AccuStr;
    }

    public String getZhanCha_Date_PlanStr() {
        return zhanCha_Date_PlanStr;
    }

    public void setZhanCha_Date_PlanStr(String zhanCha_Date_PlanStr) {
        this.zhanCha_Date_PlanStr = zhanCha_Date_PlanStr;
    }

    public String getZhanCha_Date_AccuStr() {
        return zhanCha_Date_AccuStr;
    }

    public void setZhanCha_Date_AccuStr(String zhanCha_Date_AccuStr) {
        this.zhanCha_Date_AccuStr = zhanCha_Date_AccuStr;
    }

    public String getOutDraw_Date_PlanStr() {
        return outDraw_Date_PlanStr;
    }

    public void setOutDraw_Date_PlanStr(String outDraw_Date_PlanStr) {
        this.outDraw_Date_PlanStr = outDraw_Date_PlanStr;
    }

    public String getOutDraw_Date_AccuStr() {
        return outDraw_Date_AccuStr;
    }

    public void setOutDraw_Date_AccuStr(String outDraw_Date_AccuStr) {
        this.outDraw_Date_AccuStr = outDraw_Date_AccuStr;
    }

    public String getProgram_confir_Date_PlanStr() {
        return program_confir_Date_PlanStr;
    }

    public void setProgram_confir_Date_PlanStr(String program_confir_Date_PlanStr) {
        this.program_confir_Date_PlanStr = program_confir_Date_PlanStr;
    }

    public String getProgram_confir_Date_AccuStr() {
        return program_confir_Date_AccuStr;
    }

    public void setProgram_confir_Date_AccuStr(String program_confir_Date_AccuStr) {
        this.program_confir_Date_AccuStr = program_confir_Date_AccuStr;
    }

    public String getGiveOrder_Date_PlanStr() {
        return giveOrder_Date_PlanStr;
    }

    public void setGiveOrder_Date_PlanStr(String giveOrder_Date_PlanStr) {
        this.giveOrder_Date_PlanStr = giveOrder_Date_PlanStr;
    }

    public String getGiveOrder_Date_AccuStr() {
        return giveOrder_Date_AccuStr;
    }

    public void setGiveOrder_Date_AccuStr(String giveOrder_Date_AccuStr) {
        this.giveOrder_Date_AccuStr = giveOrder_Date_AccuStr;
    }

    public String getDelivery_Goods_Date_PlanStr() {
        return delivery_Goods_Date_PlanStr;
    }

    public void setDelivery_Goods_Date_PlanStr(String delivery_Goods_Date_PlanStr) {
        this.delivery_Goods_Date_PlanStr = delivery_Goods_Date_PlanStr;
    }

    public String getDelivery_Goods_Date_AccuStr() {
        return delivery_Goods_Date_AccuStr;
    }

    public void setDelivery_Goods_Date_AccuStr(String delivery_Goods_Date_AccuStr) {
        this.delivery_Goods_Date_AccuStr = delivery_Goods_Date_AccuStr;
    }

    public String getInstall_Date_PlanStr() {
        return install_Date_PlanStr;
    }

    public void setInstall_Date_PlanStr(String install_Date_PlanStr) {
        this.install_Date_PlanStr = install_Date_PlanStr;
    }

    public String getInstall_Date_AccuStr() {
        return install_Date_AccuStr;
    }

    public void setInstall_Date_AccuStr(String install_Date_AccuStr) {
        this.install_Date_AccuStr = install_Date_AccuStr;
    }

    public String getYanShou_Date_PlanStr() {
        return yanShou_Date_PlanStr;
    }

    public void setYanShou_Date_PlanStr(String yanShou_Date_PlanStr) {
        this.yanShou_Date_PlanStr = yanShou_Date_PlanStr;
    }

    public String getYanShou_Date_AccuStr() {
        return yanShou_Date_AccuStr;
    }

    public void setYanShou_Date_AccuStr(String yanShou_Date_AccuStr) {
        this.yanShou_Date_AccuStr = yanShou_Date_AccuStr;
    }

    public String getJieSuan_Date_PlanStr() {
        return jieSuan_Date_PlanStr;
    }

    public void setJieSuan_Date_PlanStr(String jieSuan_Date_PlanStr) {
        this.jieSuan_Date_PlanStr = jieSuan_Date_PlanStr;
    }

    public String getJieSuan_Date_AccuStr() {
        return jieSuan_Date_AccuStr;
    }

    public void setJieSuan_Date_AccuStr(String jieSuan_Date_AccuStr) {
        this.jieSuan_Date_AccuStr = jieSuan_Date_AccuStr;
    }

    public String getProject_Manager1() {
        return project_Manager1;
    }

    public void setProject_Manager1(String project_Manager1) {
        this.project_Manager1 = project_Manager1;
    }

    public String getProject_Manager2() {
        return project_Manager2;
    }

    public void setProject_Manager2(String project_Manager2) {
        this.project_Manager2 = project_Manager2;
    }

    public String getProject_Manager3() {
        return project_Manager3;
    }

    public void setProject_Manager3(String project_Manager3) {
        this.project_Manager3 = project_Manager3;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder_Id() {
        return order_Id;
    }

    public void setOrder_Id(Integer order_Id) {
        this.order_Id = order_Id;
    }

    public Date getDelivery_Date() {
        return delivery_Date;
    }

    public void setDelivery_Date(Date delivery_Date) {
        this.delivery_Date = delivery_Date;
    }

    public String getTotalBao() {
        return totalBao;
    }

    public void setTotalBao(String totalBao) {
        this.totalBao = totalBao;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public Date getGetOrder_Date_Plan() {
        return getOrder_Date_Plan;
    }

    public void setGetOrder_Date_Plan(Date getOrder_Date_Plan) {
        this.getOrder_Date_Plan = getOrder_Date_Plan;
    }

    public Date getGetOrder_Date_Accu() {
        return getOrder_Date_Accu;
    }

    public void setGetOrder_Date_Accu(Date getOrder_Date_Accu) {
        this.getOrder_Date_Accu = getOrder_Date_Accu;
    }

    public Date getZhanCha_Date_Plan() {
        return zhanCha_Date_Plan;
    }

    public void setZhanCha_Date_Plan(Date zhanCha_Date_Plan) {
        this.zhanCha_Date_Plan = zhanCha_Date_Plan;
    }

    public Date getZhanCha_Date_Accu() {
        return zhanCha_Date_Accu;
    }

    public void setZhanCha_Date_Accu(Date zhanCha_Date_Accu) {
        this.zhanCha_Date_Accu = zhanCha_Date_Accu;
    }

    public Date getOutDraw_Date_Plan() {
        return outDraw_Date_Plan;
    }

    public void setOutDraw_Date_Plan(Date outDraw_Date_Plan) {
        this.outDraw_Date_Plan = outDraw_Date_Plan;
    }

    public Date getOutDraw_Date_Accu() {
        return outDraw_Date_Accu;
    }

    public void setOutDraw_Date_Accu(Date outDraw_Date_Accu) {
        this.outDraw_Date_Accu = outDraw_Date_Accu;
    }

    public Date getProgram_confir_Date_Plan() {
        return program_confir_Date_Plan;
    }

    public void setProgram_confir_Date_Plan(Date program_confir_Date_Plan) {
        this.program_confir_Date_Plan = program_confir_Date_Plan;
    }

    public Date getProgram_confir_Date_Accu() {
        return program_confir_Date_Accu;
    }

    public void setProgram_confir_Date_Accu(Date program_confir_Date_Accu) {
        this.program_confir_Date_Accu = program_confir_Date_Accu;
    }

    public Date getGiveOrder_Date_Plan() {
        return giveOrder_Date_Plan;
    }

    public void setGiveOrder_Date_Plan(Date giveOrder_Date_Plan) {
        this.giveOrder_Date_Plan = giveOrder_Date_Plan;
    }

    public Date getGiveOrder_Date_Accu() {
        return giveOrder_Date_Accu;
    }

    public void setGiveOrder_Date_Accu(Date giveOrder_Date_Accu) {
        this.giveOrder_Date_Accu = giveOrder_Date_Accu;
    }

    public Date getDelivery_Goods_Date_Plan() {
        return delivery_Goods_Date_Plan;
    }

    public void setDelivery_Goods_Date_Plan(Date delivery_Goods_Date_Plan) {
        this.delivery_Goods_Date_Plan = delivery_Goods_Date_Plan;
    }

    public Date getDelivery_Goods_Date_Accu() {
        return delivery_Goods_Date_Accu;
    }

    public void setDelivery_Goods_Date_Accu(Date delivery_Goods_Date_Accu) {
        this.delivery_Goods_Date_Accu = delivery_Goods_Date_Accu;
    }

    public Date getInstall_Date_Plan() {
        return install_Date_Plan;
    }

    public void setInstall_Date_Plan(Date install_Date_Plan) {
        this.install_Date_Plan = install_Date_Plan;
    }

    public Date getInstall_Date_Accu() {
        return install_Date_Accu;
    }

    public void setInstall_Date_Accu(Date install_Date_Accu) {
        this.install_Date_Accu = install_Date_Accu;
    }

    public Date getYanShou_Date_Plan() {
        return yanShou_Date_Plan;
    }

    public void setYanShou_Date_Plan(Date yanShou_Date_Plan) {
        this.yanShou_Date_Plan = yanShou_Date_Plan;
    }

    public Date getYanShou_Date_Accu() {
        return yanShou_Date_Accu;
    }

    public void setYanShou_Date_Accu(Date yanShou_Date_Accu) {
        this.yanShou_Date_Accu = yanShou_Date_Accu;
    }

    public Date getJieSuan_Date_Plan() {
        return jieSuan_Date_Plan;
    }

    public void setJieSuan_Date_Plan(Date jieSuan_Date_Plan) {
        this.jieSuan_Date_Plan = jieSuan_Date_Plan;
    }

    public Date getJieSuan_Date_Accu() {
        return jieSuan_Date_Accu;
    }

    public void setJieSuan_Date_Accu(Date jieSuan_Date_Accu) {
        this.jieSuan_Date_Accu = jieSuan_Date_Accu;
    }

    public String getJindu_remark() {
        return jindu_remark;
    }

    public void setJindu_remark(String jindu_remark) {
        this.jindu_remark = jindu_remark;
    }

    public Double getHetong_money() {
        return hetong_money;
    }

    public void setHetong_money(Double hetong_money) {
        this.hetong_money = hetong_money;
    }

    public Double getDaokuan_money() {
        return daokuan_money;
    }

    public void setDaokuan_money(Double daokuan_money) {
        this.daokuan_money = daokuan_money;
    }

    public Double getWeihuikuan_money() {
        return weihuikuan_money;
    }

    public void setWeihuikuan_money(Double weihuikuan_money) {
        this.weihuikuan_money = weihuikuan_money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
