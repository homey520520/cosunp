package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectMapper {


    @Select(" select * from project_head  where salor = #{empNo} ")
    List<ProjectHead> totalProjectNumByEmpNo(String empNo);


    @Select(" select * from project_head  where projectName like CONCAT('%',#{projectName},'%')  ")
    List<ProjectHead> getProjectBySearch(String projectName);

    @Select("SELECT\n" +
            "\tpho.id AS id,\n" +
            "\tpho.customer_Name AS customerName,\n" +
            "\ta.`Name` AS provinceStr,\n" +
            "\tpho.ordeNo AS orderNo,\n" +
            "\tpho.newOrOld AS newOrOld,\n" +
            "\tifnull(sum(phoi.hetongMoney),0.0) AS hetongMoney,\n" +
            "  ifnull(sum(phoi.weiHuiMoney),0.0) as weiHuiMoney\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "left JOIN project_head ph ON ph.id = pho.head_id\n" +
            "left JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "LEFT JOIN china a ON a.id = pho.province\n" +
            "WHERE\n" +
            "\tph.salor = #{empNo} \n" +
            "AND ph.projectName = #{projectName}\n" +
            "group by pho.ordeNo  ")
    List<ProjectHeadOrder> getTotalProjectOrderByName(String empNo, String projectName);


    @Select("select * from daysset order by type desc ")
    List<DaysSet> getDaysSetNew();


    @Select("SELECT\n" +
            "\tphoi.delivery_Date AS delivery_DateStr,\n" +
            "\tphoi.totalBao AS totalBao,\n" +
            "\tphoi.product_Name AS product_Name,\n" +
            "\tIFNULL(phoi.weiHuiMoney, 0.0) AS weiHuiMoney,\n" +
            "\tIFNULL(phoi.hetongMoney, 0.0) AS hetongMoney,\n" +
            "\tpho.ordeNo AS orderNo,\n" +
            "\tphoi.id AS id,\n" +
            "\tphoi.checked AS checked,\n" +
            "\tphoi.status AS status,\n" +
            "\tIFNULL(phoi.gendan,'') AS gendan,\n" +
            "\tph.projectName AS projectName,\n" +
            "\tphoi.order_id AS order_Id,\n" +
            "\tIFNULL(phoi.saleManager,'') AS saleManager," +
            "\tphoi.remark AS remark," +
            "(cast(phoi.version as decimal(11,2))-1)*10 as historyGe\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "JOIN project_head ph ON ph.id = pho.head_id\n" +
            "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE\n" +
            "\tph.salor = #{empNo}\n" +
            "AND pho.ordeNo = #{projectName}\n" +
            "and\n" +
            "\tphoi.id IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tmax(item2.id)\n" +
            "\t\tFROM\n" +
            "\t\t\tproject_head_order_item item2\n" +
            "\t\tGROUP BY\n" +
            "\t\t\titem2.product_Name\n" +
            "\t)")
    List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderS(String empNo, String projectName);


    @Select("SELECT\n" +
            "\tphoi.delivery_Date AS delivery_DateStr,\n" +
            "\tphoi.totalBao AS totalBao,\n" +
            "\tphoi.product_Name AS product_Name,\n" +
            "\tIFNULL(phoi.weiHuiMoney, 0.0) AS weiHuiMoney,\n" +
            "\tpho.ordeNo AS orderNo,\n" +
            "\tphoi.id AS id,\n" +
            "\tphoi.status AS status,\n" +
            "\tphoi.remark AS remark," +
            "\tph.projectName AS projectName,\n" +
            "\tphoi.gendan AS gendan,\n" +
            "\tphoi.checked AS checked,\n" +
            "\tphoi.order_id AS order_Id,\n" +
            "\tphoi.saleManager AS saleManager," +
            "(cast(phoi.version as decimal(11,2))-1)*10 as historyGe\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "JOIN project_head ph ON ph.id = pho.head_id\n" +
            "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE phoi.id IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tmax(item2.id)\n" +
            "\t\tFROM\n" +
            "\t\t\tproject_head_order_item item2\n" +
            "\t\tGROUP BY\n" +
            "\t\t\titem2.product_Name\n" +
            "\t) order by checked asc ")
    List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderSAll();

    @Update("update project_head_order_item set checked = #{checked} where id = #{id} ")
    void updateProjectItemCheckById(Integer id, int checked);


    @Select("SELECT\n" +
            "\tphoi.getOrder_Date_Plan as getOrder_Date_PlanStr,\n" +
            "\tphoi.getOrder_Date_Accu as getOrder_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Date_Plan as zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu as zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan as outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu as outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan as program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu as program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan as giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu as giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan as delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu as delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan as delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu as delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan as install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu as install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan as yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu as yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan as jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu as jieSuan_Date_AccuStr,\n" +
            "\tIFNULL(phoi.hetongMoney,0.0) as hetongMoney,\n" +
            "\tIFNULL(phoi.hereMoney,0.0) as hereMoney,\n" +
            "\tIFNULL(phoi.weiHuiMoney,0.0) as weiHuiMoney,\n" +
            "\tIFNULL(phoi.jindu_remark,'') as jindu_remark,\n" +
            "\tIFNULL(phoi.remark,'') as remark,\n" +
            "\tphoi.id as id,\n" +
            "\tpho.ordeNo as orderNo \n" +
            " FROM\n" +
            "\tproject_head ph\n" +
            "LEFT JOIN project_head_order pho ON ph.id = pho.head_id\n" +
            "LEFT JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE " +
            "\tph.salor = #{empNo}\n" +
            "AND pho.ordeNo = #{orderNo} \n" +
            "and phoi.product_Name = #{projectName} order by phoi.version desc limit 1")
    ProjectHeadOrderItem getTotalProjectOrderITEMMoreByOrderS(String empNo, String projectName, String orderNo);


    @Select("SELECT\n" +
            "\tphoi.getOrder_Date_Plan as getOrder_Date_PlanStr,\n" +
            "\tphoi.getOrder_Date_Accu as getOrder_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Date_Plan as zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu as zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan as outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu as outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan as program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu as program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan as giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu as giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan as delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu as delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan as delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu as delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan as install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu as install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan as yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu as yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan as jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu as jieSuan_Date_AccuStr,\n" +
            "\tIFNULL(phoi.hetongMoney,0.0) as hetongMoney,\n" +
            "\tIFNULL(phoi.hereMoney,0.0) as hereMoney,\n" +
            "\tIFNULL(phoi.weiHuiMoney,0.0) as weiHuiMoney,\n" +
            "\tIFNULL(phoi.jindu_remark,'') as jindu_remark,\n" +
            "\tIFNULL(phoi.remark,'') as remark,\n" +
            "\tph.projectName as projectName,\n" +
            "\tph.salor as salor,\n" +
            "\tpho.customer_Name as customer_Name,\n" +
            "\tphoi.id as id,\n" +
            "\tphoi.gendan as gendansStr,\n" +
            "\tphoi.saleManager as salorsStr,\n" +
            "\tphoi.delivery_Date as delivery_DateStr,\n" +
            "\tpho.ordeNo as orderNo \n" +
            " FROM\n" +
            "\tproject_head ph\n" +
            "LEFT JOIN project_head_order pho ON ph.id = pho.head_id\n" +
            "LEFT JOIN project_head_order_item phoi ON phoi.order_id = pho.id ")
    List<ProjectHeadOrderItem> getAllitemList();

    @Select("SELECT\n" +
            "\tphoi.getOrder_Date_Plan as getOrder_Date_PlanStr,\n" +
            "\tphoi.getOrder_Date_Accu as getOrder_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Date_Plan as zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu as zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan as outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu as outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan as program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu as program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan as giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu as giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan as delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu as delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan as delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu as delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan as install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu as install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan as yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu as yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan as jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu as jieSuan_Date_AccuStr,\n" +
            "\tIFNULL(phoi.hetongMoney,0.0) as hetongMoney,\n" +
            "\tIFNULL(phoi.hereMoney,0.0) as hereMoney,\n" +
            "\tIFNULL(phoi.weiHuiMoney,0.0) as weiHuiMoney,\n" +
            "\tIFNULL(phoi.jindu_remark,'') as jindu_remark,\n" +
            "\tIFNULL(phoi.remark,'') as remark,\n" +
            "\tphoi.id as id,\n" +
            "\tpho.ordeNo as orderNo \n" +
            " FROM\n" +
            "\tproject_head ph\n" +
            "LEFT JOIN project_head_order pho ON ph.id = pho.head_id\n" +
            "LEFT JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE " +
            "phoi.id = #{id}")
    ProjectHeadOrderItem getProjectItemHistoryById(Integer id);

    @Select("SELECT\n" +
            "\tphoi.getOrder_Date_Plan as getOrder_Date_PlanStr,\n" +
            "\tphoi.getOrder_Date_Accu as getOrder_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Date_Plan as zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu as zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan as outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu as outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan as program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu as program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan as giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu as giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan as delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu as delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan as delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu as delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan as install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu as install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan as yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu as yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan as jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu as jieSuan_Date_AccuStr,\n" +
            "\tphoi.order_id as order_Id," +
            "\tphoi.delivery_Date as delivery_DateStr,\n" +
            "\tphoi.totalBao as totalBao ,\n" +
            "\tphoi.product_Name as product_Name,\n" +
            "\tphoi.version as version,\n" +
            "\tphoi.checked as checked,\n" +
            "\tphoi.saleManager as saleManager,\n" +
            "\tphoi.gendan as gendan,\n" +
            "\tIFNULL(phoi.hetongMoney,0.0) as hetongMoney,\n" +
            "\tIFNULL(phoi.hereMoney,0.0) as hereMoney,\n" +
            "\tIFNULL(phoi.weiHuiMoney,0.0) as weiHuiMoney,\n" +
            "\tIFNULL(phoi.jindu_remark,'') as jindu_remark,\n" +
            "\tIFNULL(phoi.remark,'') as remark,\n" +
            "\tphoi.id as id\n" +
            " FROM\n" +
            "\t  project_head_order_item phoi " +
            "WHERE " +
            "phoi.id = #{id}")
    ProjectHeadOrderItem getOldHeadOrderItemByPhoi(ProjectHeadOrderItem item);


    @Insert("insert into project_head_order_item" +
            " (" +
            "getOrder_Date_Plan," +
            "getOrder_Date_Accu," +
            "zhanCha_Date_Plan," +
            "zhanCha_Date_Accu," +
            "outDraw_Date_Plan," +
            "outDraw_Date_Accu," +
            "program_confir_Date_Plan," +
            "program_confir_Date_Accu," +
            "giveOrder_Date_Plan," +
            "giveOrder_Date_Accu," +
            "delivery_Goods_Date_Plan," +
            "delivery_Goods_Date_Accu," +
            "install_Date_Plan," +
            "install_Date_Accu," +
            "yanShou_Date_Plan," +
            "yanShou_Date_Accu," +
            "jieSuan_Date_Plan," +
            "jieSuan_Date_Accu," +
            "order_Id," +
            "delivery_Date," +
            "totalBao," +
            "product_Name," +
            "version," +
            "checked," +
            "saleManager," +
            "gendan," +
            "hetongMoney," +
            "hereMoney," +
            "weiHuiMoney," +
            "jindu_remark," +
            "remark)" +
            " values(" +
            "#{getOrder_Date_PlanStr}," +
            "#{getOrder_Date_AccuStr}," +
            "#{zhanCha_Date_PlanStr}," +
            "#{zhanCha_Date_AccuStr}," +
            "#{outDraw_Date_PlanStr}," +
            "#{outDraw_Date_AccuStr}," +
            "#{program_confir_Date_PlanStr}," +
            "#{program_confir_Date_AccuStr}," +
            "#{giveOrder_Date_PlanStr}," +
            "#{giveOrder_Date_AccuStr}," +
            "#{delivery_Goods_Date_PlanStr}," +
            "#{delivery_Goods_Date_AccuStr}," +
            "#{install_Date_PlanStr}," +
            "#{install_Date_AccuStr}," +
            "#{yanShou_Date_PlanStr}," +
            "#{yanShou_Date_AccuStr}," +
            "#{jieSuan_Date_PlanStr}," +
            "#{jieSuan_Date_AccuStr}," +
            "#{order_Id}," +
            "#{delivery_DateStr}," +
            "#{totalBao}," +
            "#{product_Name}," +
            "#{version}," +
            "#{checked}," +
            "#{saleManager}," +
            "#{gendan}," +
            "#{hetongMoney}," +
            "#{hereMoney}," +
            "#{weiHuiMoney}," +
            "#{jindu_remark}," +
            "#{remark})")
    void saveOrderItemMor(ProjectHeadOrderItem item);

    @Select("select count(*) from project_head where projectName = #{projectName} and salor = #{userid} ")
    int findNameRepeatOrNot(String userid, String projectName);


    @Select("select count(*) from project_head where projectName = #{projectName} and salor = #{salor} and id <> #{id} ")
    int findNameRepeatOrNot2(ProjectHead ph);

    @Update("update project_head  set projectName = #{projectName} , remark = #{remark}  where id = #{id} ")
    void updateProjectByNameAndRemark(ProjectHead ph);

    @Insert("insert into project_head (projectName,salor,remark) values(#{projectName},#{userId},#{remark})")
    void saveProjectByNameAndRemark(String userId, String projectName, String remark);

    @Select("select id,`Name` from china where pid = 0 and id <>0")
    List<China> getAllMainProvince();

    @Select("SELECT\n" +
            "\tph.id as id,pho.ordeNo\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "LEFT JOIN project_head ph ON ph.id = pho.head_id\n" +
            "WHERE\n" +
            "\tpho.ordeNo = #{orderNo}\n" +
            "AND ph.salor = #{userId}")
    ProjectHeadOrder checkOrderNoRepeat(String userId, String orderNo);

    @Select("SELECT\n" +
            "count(*) " +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            " WHERE\n" +
            "\tpho.ordeNo = #{orderNo}\n" +
            "AND pho.customer_Name = #{customerName}")
    int getCustomerNameByNameAndOrderNo(ProjectHeadOrder pho);

    @Select(" select id from project_head where projectName = #{projectName} and salor = #{salor}")
    ProjectHead findPHbyName(String projectName, String salor);

    @Select("SELECT\n" +
            "\tn.id as id,\n" +
            "\tn.positionName,\n" +
            "\tn.positionLevel\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "LEFT JOIN dept t ON ee.deptId = t.id\n" +
            "LEFT JOIN position n ON n.id = ee.positionId\n" +
            "WHERE\n" +
            "\tt.deptname LIKE '%项目中心%'\n" +
            "AND ee.isQuit = 0\n" +
            "group by n.positionName")
    List<Position> getAllAlertPositions();


    @Select("SELECT\n" +
            "\tid,\n" +
            "\tnorAlertDays,\n" +
            "\timpAlertDays,\n" +
            "\talertTime as alertTimeStr,\n" +
            "\talertInterHours,\n" +
            "  alertTimes,\n" +
            "\talertMode,\n" +
            "\talertToPositions,\n" +
            "\tremark\n" +
            "FROM\n" +
            "\talertset")
    AlertSet getAlertSet();

    @Update("update alertset " +
            "set " +
            "norAlertDays = #{norAlertDays}, " +
            "impAlertDays = #{impAlertDays}," +
            "alertTime = #{alertTimeStr}," +
            "alertTimes = #{alertTimes}," +
            "alertMode = #{alertMode}," +
            "alertToPositions= #{alertToPositions}," +
            "remark = #{remark}")
    void updateAlertSet(AlertSet as);


    @Update("update daysSet set " +
            "\tzhanchaDays = #{zhanchaDays},\n" +
            "\toutDrawDays = #{outDrawDays},\n" +
            "\tfanAnConfDays = #{fanAnConfDays},\n" +
            "\tprodDays = #{prodDays},\n" +
            "\tanzhuangDays = #{anzhuangDays},\n" +
            "\tyanshouDays = #{yanshouDays},\n" +
            "\tjiesuanDays = #{jiesuanDays},\n" +
            "\ttype = #{type},\n" +
            "\tremark = #{remark} " +
            "where id = #{id}")
    void updateAlertSet1(DaysSet ds);


    @Update("update daysSet set " +
            "\tfanAnConfDays = #{fanAnConfDays},\n" +
            "\tprodDays = #{prodDays},\n" +
            "\tanzhuangDays = #{anzhuangDays},\n" +
            "\tyanshouDays = #{yanshouDays},\n" +
            "\tjiesuanDays = #{jiesuanDays},\n" +
            "\ttype = #{type},\n" +
            "\tremark = #{remark} " +
            "where id = #{id}")
    void updateAlertSet2(DaysSet ds);

    @Insert(" insert into project_head_order (head_id,province,customer_Name,ordeNo,newOrOld) values" +
            " (#{id},#{provinceId},#{customerName},#{orderNo},#{newOrOld}) ")
    void saveProjectHeadByBean(Integer provinceId, String userId, String orderNo, String customerName,
                               String projectName, Integer id, Integer newOrOld);


    @Select("SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "LEFT JOIN project_head_order_item phoi ON pho.id = phoi.order_id\n" +
            "WHERE\n" +
            "\tpho.ordeNo = #{orderNo} \n" +
            "AND phoi.product_Name = #{productName}  ")
    ProjectHeadOrderItem checkProductRepeatForOneOrder(Double hetongMoney, String orderNo, String productName);


    @Select("SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "WHERE\n" +
            "\tpho.ordeNo = #{orderNo}\n")
    ProjectHeadOrder findPHObyOrderNo(String orderNo);


    @Select("SELECT\n" +
            " \tid,\n" +
            "\tprovince as province,\n" +
            "\tcustomer_Name as customerName,\n" +
            "\tnewOrOld as newOrOld,\n" +
            "\tordeNo as orderNo,\n" +
            "\tremark " +
            "FROM \n" +
            "\tproject_head_order pho \n" +
            "WHERE\n" +
            "\tpho.ordeNo = #{orderNo}\n")
    ProjectHeadOrder getProjectOrderByOrderNo(String orderNo);


    @Select("SELECT\n" +
            "\tpom.id AS id,\n" +
            "\tDATE_FORMAT(pom.date, '%Y-%m-%d') AS dateStr,\n" +
            "\tpom.fapiaoNo,\n" +
            "\tpom.hereMoney,\n" +
            "\tpom.imageUrl,\n" +
            "\tpom.remark,\n" +
            "\titem.hetongMoney,\n" +
            "\titem.weiHuiMoney\n" +
            "FROM\n" +
            "\tproject_oi_moneyrecord pom\n" +
            "LEFT JOIN project_head_order_item item ON pom.item_id = item.id\n" +
            "WHERE\n" +
            "\tpom.item_id = #{id}")
    List<ProjectItemMoneyRecord> getTotalProjectOrderMoneyRecordByNo(String id);


    @Select("SELECT\n" +
            "\tpom.id AS id,\n" +
            "\tDATE_FORMAT(pom.date, '%Y-%m-%d') AS dateStr,\n" +
            "\tpom.fapiaoNo,\n" +
            "\tpom.hereMoney,\n" +
            "\tpom.imageUrl,\n" +
            "\tpom.remark,\n" +
            "\tpom.item_id,\n" +
            "\titem.hetongMoney,\n" +
            "\titem.weiHuiMoney\n" +
            "FROM\n" +
            "\tproject_oi_moneyrecord pom\n" +
            "LEFT JOIN project_head_order_item item ON pom.item_id = item.id\n" +
            "WHERE\n" +
            "\tpom.id = #{id}")
    ProjectItemMoneyRecord getProjectRecordById(Integer id);


    @Select("SELECT\n" +
            " \tid,\n" +
            "\tprojectName,\n" +
            "\tsalor,\n" +
            "\tremark \n" +
            "FROM \n" +
            "\tproject_head pho \n" +
            "WHERE\n" +
            "\tpho.projectName = #{projectName} and salor = #{salor} \n")
    ProjectHead getProjectByName(ProjectHead ph);

    @Insert(" insert into project_head_order_item (order_id,delivery_Date,totalBao,product_Name,hetongMoney) values" +
            " (#{id},#{date},#{totalBao},#{productName},#{hetongMoney}) ")
    void saveProjectHeadItemByBean(Double hetongMoney, Integer id, String productName, String totalBao, String date);

    @Insert("insert into project_oi_moneyrecord (" +
            "\titem_id,\n" +
            "\tdate,\n" +
            "\tfapiaoNo,\n" +
            "\thereMoney,\n" +
            "\timageUrl,\n" +
            "\tremark" +
            ") values (" +
            "\t#{item_id},\n" +
            "\t#{dateStr},\n" +
            "\t#{fapiaoNo},\n" +
            "\t#{hereMoney},\n" +
            "\t#{imageUrl},\n" +
            "\t#{remark}" +
            ")")
    void saveHereMoneyBYItemId(ProjectItemMoneyRecord pimr);

    @Delete("delete from project_oi_moneyrecord where id = #{id}")
    void deleteProjectRecordById(Integer id);

    @Update("update project_head_order_item  set hereMoney = (hereMoney - #{hereMoney})" +
            ",weiHuiMoney=(weiHuiMoney+#{hereMoney}) where id = #{itemId}")
    void updatePHOIMoney(Integer itemId, Double hereMoney);

    @Update("update project_head_order_item  set hereMoney =" +
            " (select sum(hereMoney) as hereMoney from project_oi_moneyrecord  where item_id = #{item_id})" +
            ",weiHuiMoney=(hetongMoney-" +
            "(select sum(hereMoney) from project_oi_moneyrecord  where item_id = #{item_id})) where id = #{item_id}")
    void updateProjectItemByItemId(ProjectItemMoneyRecord record);


    @Update("update project_oi_moneyrecord  set hereMoney =  #{hereMoney}" +
            ",fapiaoNo = #{fapiaoNo},remark = #{remark}  where id = #{id}")
    void updateProjectRecordByBean(ProjectItemMoneyRecord record);

    @Update("update project_head_order_item  set " +
            "getOrder_Date_Plan = #{getOrder_Date_PlanStr}," +
            "getOrder_Date_Accu = #{getOrder_Date_AccuStr}," +
            "zhanCha_Date_Plan = #{zhanCha_Date_PlanStr}," +
            "zhanCha_Date_Accu = #{zhanCha_Date_AccuStr}," +
            "outDraw_Date_Plan = #{outDraw_Date_PlanStr}," +
            "outDraw_Date_Accu = #{outDraw_Date_AccuStr}," +
            "program_confir_Date_Plan = #{program_confir_Date_PlanStr}," +
            "program_confir_Date_Accu = #{program_confir_Date_AccuStr}," +
            "giveOrder_Date_Plan = #{giveOrder_Date_PlanStr}," +
            "giveOrder_Date_Accu = #{giveOrder_Date_AccuStr}," +
            "delivery_Goods_Date_Plan = #{delivery_Goods_Date_PlanStr}," +
            "delivery_Goods_Date_Accu = #{delivery_Goods_Date_AccuStr}," +
            "install_Date_Plan = #{install_Date_PlanStr}," +
            "install_Date_Accu = #{install_Date_AccuStr}," +
            "yanShou_Date_Plan = #{yanShou_Date_PlanStr}," +
            "yanShou_Date_Accu = #{yanShou_Date_AccuStr}," +
            "jieSuan_Date_Plan = #{jieSuan_Date_PlanStr}," +
            "version = #{version}," +
            "jieSuan_Date_Accu = #{jieSuan_Date_AccuStr}" +
            "  where id = #{id}")
    void updateOrderItemMor(ProjectHeadOrderItem phoi);


    @Update("update project_head_order set province = #{province},\n" +
            "\tcustomer_Name = #{customerName},\n" +
            "\tordeNo = #{orderNo}," +
            "\tnewOrOld = #{newOrOld}" +
            " where id = #{id} ")
    void updateOrderNoRepeat(ProjectHeadOrder pho);


    @Delete("delete from project_head where projectName = #{projectName} and salor = #{userid}")
    void deleteProjectDetai(String projectName, String userid);


    @Delete("delete from project_head_order where ordeNo = #{orderNo}")
    void deleteProjectDetai2(String orderNo, String userId);


    @Delete(" update project_head_order_item set delivery_Date = #{delivery_DateStr}," +
            "          totalBao = #{totalBao} " +
            "           where id = #{id}  ")
    void updateProjectOrderItemByNameAndRemark(ProjectHeadOrderItem phoi);


    @Delete(" update project_head_order_item set delivery_Date = #{delivery_DateStr}," +
            "          totalBao = #{totalBao}, hetongMoney = #{hetongMoney} ,hereMoney = #{hereMoney} ," +
            " weiHuiMoney = #{weiHuiMoney},saleManager = #{salorsStr}, " +
            "       gendan = #{gendansStr} ,status=#{status}   where id = #{id}  ")
    void updateProjectHeadOrderItem(ProjectHeadOrderItem phoi);


    @Select("SELECT\n" +
            "\tphoi.delivery_Date AS delivery_DateStr,\n" +
            "\tphoi.totalBao," +
            "phoi.product_Name as productName,\n" +
            "pho.ordeNo as orderNo,\n" +
            "phoi.hetongMoney as hetongMoney,\n" +
            "phoi.hereMoney as hereMoney,\n" +
            "phoi.saleManager as salorsStr,\n" +
            "phoi.gendan as gendansStr,\n" +
            "phoi.status as status,\n" +
            "phoi.id as id \n" +
            "FROM\n" +
            "\tproject_head_order_item phoi\n" +
            "JOIN project_head_order pho ON phoi.order_id = pho.id\n" +
            "WHERE\n" +
            "\tpho.ordeNo = #{orderNo}\n" +
            "AND phoi.product_Name = #{productName} order by phoi.version desc limit 1 ")
    ProjectHeadOrderItem getProjectOrderItemByOrderNoAndProductName(ProjectHeadOrderItem item);

    @Select("SELECT\n" +
            "\tphoi.delivery_Date AS delivery_DateStr,\n" +
            "\tphoi.totalBao AS totalBao,\n" +
            "\tphoi.product_Name AS product_Name,\n" +
            "\tIFNULL(phoi.weiHuiMoney, 0.0) AS weiHuiMoney,\n" +
            "\tpho.ordeNo AS orderNo,\n" +
            "\tphoi.id AS id,\n" +
            "\tphoi.gendan AS gendan,\n" +
            "\tphoi.saleManager AS saleManager,\n" +
            " FORMAT(phoi.version,1) as versionStr,\n" +
            "  phoi.remark\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "JOIN project_head ph ON ph.id = pho.head_id\n" +
            "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE\n" +
            "\t pho.ordeNo = #{orderNo}\n" +
            "and phoi.product_Name = #{productName}\n" +
            "and\n" +
            "\tphoi.id not IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tmax(item2.id)\n" +
            "\t\tFROM\n" +
            "\t\t\tproject_head_order_item item2\n" +
            "\t\tGROUP BY\n" +
            "\t\t\titem2.product_Name\n" +
            "\t)")
    List<ProjectHeadOrderItem> getHistoryItemByProduct_NameAndOrderNo(String productName, String orderNo);


    @Delete("delete from project_head_order_item where id = #{id}")
    void deleteProjectDetaiItem(Integer id, String userId);

    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "ee.empno\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "JOIN dept t ON ee.deptId = t.id\n" +
            "JOIN position n ON n.id = ee.positionId\n" +
            "WHERE\n" +
            "\tt.deptname = '项目中心'\n" +
            "AND n.positionName = '项目经理' and ee.isQuit = 0")
    List<Employee> getSalorMItems();

    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "ee.empno\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "JOIN dept t ON ee.deptId = t.id\n" +
            "JOIN position n ON n.id = ee.positionId\n" +
            "WHERE\n" +
            "\tt.deptname = '项目中心'\n" +
            "AND n.positionName = '跟单员' and ee.isQuit = 0")
    List<Employee> getGenDanItems();


    @Select("call splitString(#{empNos},\",\");")
    void returnNameByEmpNoStrBefore(String empNos);

    @Select(" select group_concat(name,'') from employee where empno in(select * from tmp_split);  ")
    String returnNameByEmpNoStr(String empNos);


}

