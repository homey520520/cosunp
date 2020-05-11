package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectMapper {

//
//    @Select(" SELECT\n" +
//            "\tph.*\n" +
//            "FROM\n" +
//            "\tproject_head ph\n" +
//            " JOIN qyweixinbd bd ON ph.salor = bd.userid\n" +
//            "WHERE\n" +
//            "\tbd.userid = #{empNo} ")
//    List<ProjectHead> totalProjectNumByEmpNo(String empNo);


    @Select({
            "<script>",
            "select e.name ",
            "FROM employee e left join dept t on e.deptId = t.id left join position n on n.id = e.positionId",
            " where e.empNo in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<String> getNamesByEmpNos(@Param("ids") List<String> ids);


    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "  ee.empno,\n" +
            "\tdate_format(ld.beginleave, '%Y-%m-%d') as beginleaveStr,\n" +
            "\tdate_format(ld.endleave, '%Y-%m-%d') as endleaveStr \n" +
            "FROM\n" +
            "\tleavedata ld\n" +
            "LEFT JOIN employee ee ON ld.empNo = ee.empNo\n" +
            " where date_format(ld.beginleave, '%Y-%m-%d') = #{day}")
    List<Leave> getAllLeaveDataByBeBoreDayApply(String day);


    @Select("SELECT\n" +
            "  phoi.id,\n" +
            "\tph.projectName,\n" +
            "\tpho.ordeNo,\n" +
            "\tphoi.product_Name,\n" +
            "\tpho.newOrOld,\n" +
            "\tphoi.version,\n" +
            "\tpho.customer_Name,\n" +
            "\tph.salor,\n" +
            "\tphoi.getOrder_Date_Plan AS getOrder_Date_PlanStr,\n" +
            "\tphoi.getOrder_Date_Accu AS getOrder_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Date_Plan AS zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu AS zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan AS outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu AS outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan AS program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu AS program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan AS giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu AS giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan AS install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu AS install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan AS yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu AS yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan AS jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu AS jieSuan_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp\n" +
            "FROM\n" +
            "\tproject_head_order_item phoi\n" +
            "LEFT JOIN project_head_order pho ON pho.id = phoi.order_id\n" +
            "LEFT JOIN project_head ph ON ph.id = pho.head_id\n" +
            "WHERE\n" +
            "\tphoi.`status` = 0\n" +
            "AND phoi.checked = 2\n" +
            "AND (\n" +
            "\tphoi.zhanCha_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\tOR phoi.outDraw_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\tOR phoi.program_confir_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\tOR phoi.giveOrder_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\tOR phoi.delivery_Goods_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\tOR phoi.install_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\tOR phoi.yanShou_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            ")")
    List<ProjectHeadOrderItem> getItemListByOrderStatusAndCheckAndEmpnoIn(String empNo);


    @Select({
            "<script>",
            "select t.userid as userId ",
            "FROM employee e left join qyweixinbd t on e.empNo = t.empNo " +
                    "left join position n on n.id = e.positionId",
            " where e.empNo in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<String> getUserIdByEmpNos(@Param("ids") List<String> ids);


    @Select(" SELECT\n" +
            "\tph.*\n" +
            "FROM\n" +
            "\tproject_head ph  where ph.salor = #{empNo}  ")
    List<ProjectHead> totalProjectNumByEmpNo(String empNo);


    @Select(" SELECT\n" +
            "\tph.*\n" +
            "FROM\n" +
            "\tproject_head ph")
    List<ProjectHead> totalProjectNumBy();


    @Select("select empNo from qyweixinbd where userid = #{userId} limit 1")
    String getEmpNoByUserId(String userId);


    @Select(" select * from project_head  where projectName like CONCAT('%',#{projectName},'%')  ")
    List<ProjectHead> getProjectBySearch(String projectName);

    @Select("SELECT\n" +
            "\tpho.id AS id,\n" +
            "\tpho.customer_Name AS customerName,\n" +
            "\ta.`Name` AS provinceStr,\n" +
            "\tpho.ordeNo AS orderNo,\n" +
            "\tpho.newOrOld AS newOrOld,\n" +
            "\tifnull(sum(phoi.hetongMoney), 0.0) AS hetongMoney,\n" +
            "\tifnull(sum(phoi.hetongMoney), 0.0) - ifnull(\n" +
            "\t\tsum(\n" +
            "\t\t\t(\n" +
            "\t\t\t\tSELECT\n" +
            "\t\t\t\t\tsum(hereMoney)\n" +
            "\t\t\t\tFROM\n" +
            "\t\t\t\t\tproject_oi_moneyrecord\n" +
            "\t\t\t\tWHERE\n" +
            "\t\t\t\t\titem_id = phoi.id\n" +
            "\t\t\t)\n" +
            "\t\t),\n" +
            "\t\t0.0\n" +
            "\t) AS weiHuiMoney\n" +
            "FROM\n" +
            "\tproject_head ph\n" +
            "JOIN project_head_order pho ON ph.id = pho.head_id\n" +
            "LEFT JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "JOIN china a ON a.id = pho.province\n" +
            "WHERE\n" +
            "\tph.projectName = #{projectName} \n" +
            "AND (\n" +
            "\tphoi.id IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tmax(item2.id)\n" +
            "\t\tFROM\n" +
            "\t\t\tproject_head_order_item item2\n" +
            "\t\tGROUP BY\n" +
            "\t\t\titem2.order_id,item2.product_Name\n" +
            "\t)\n" +
            "\tOR phoi.id IS NULL\n" +
            ")\n" +
            "GROUP BY\n" +
            "\tpho.ordeNo,pho.customer_Name ")
    List<ProjectHeadOrder> getTotalProjectOrderByName(String empNo, String projectName);


    @Select("select * from daysset order by type desc ")
    List<DaysSet> getDaysSetNew();


    @Select("SELECT\n" +
            "\tphoi.delivery_Date AS delivery_DateStr,\n" +
            "\tphoi.totalBao AS totalBao,\n" +
            "\tphoi.product_Name AS product_Name,\n" +
            "\tifnull((phoi.hetongMoney), 0.0) - ifnull((select sum(hereMoney) from project_oi_moneyrecord where item_id = phoi.id), 0.0) AS weiHuiMoney, " +
            "\tIFNULL(phoi.hetongMoney, 0.0) AS hetongMoney,\n" +
            "\tpho.ordeNo AS orderNo,\n" +
            "\tphoi.id AS id,\n" +
            "\tphoi.zhanCha_Date_Plan AS zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu AS zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan AS outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu AS outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan AS program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu AS program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan AS giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu AS giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan AS install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu AS install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan AS yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu AS yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan AS jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu AS jieSuan_Date_AccuStr,\n" +
            "\tphoi.getOrder_Date_Plan AS getOrder_Date_PlanStr,\n" +
            "\tphoi.checked AS checked,\n" +
            "\tphoi.status AS status,\n" +
            "\tIFNULL(phoi.gendan,'') AS gendan,\n" +
            "\tph.projectName AS projectName,\n" +
            "\tphoi.order_id AS order_Id,\n" +
            "\tIFNULL(phoi.saleManager,'') AS saleManager," +
            "\tifnull(phoi.remark,'') AS remark," +
            "phoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp,\n" +
            "\tphoi.jieSuan_Emp," +
            "\tpho.newOrOld as newOrOld," +
            "(cast(phoi.version as decimal(11,2))-1)*10 as historyGe\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "JOIN project_head ph ON ph.id = pho.head_id\n" +
            "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE\n" +
            "pho.ordeNo = #{projectName} and pho.customer_Name = #{customer_Name} \n" +
            "and\n" +
            "\tphoi.id IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tmax(item2.id)\n" +
            "\t\tFROM\n" +
            "\t\t\tproject_head_order_item item2\n" +
            "\t\tGROUP BY\n" +
            "\t\t\titem2.order_id,item2.product_Name\n" +
            "\t)")
    List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderS(String customer_Name, String empNo, String projectName);


    @Select("SELECT\n" +
            "\tphoi.id AS id,\n" +
            "\tpho.ordeNo AS ordeNo,\n" +
            "\tpho.customer_Name,\n" +
            "\tpho.newOrOld AS newOrOld,\n" +
            "\tphoi.zhanCha_Date_Plan AS zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu AS zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan AS outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu AS outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan AS program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu AS program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan AS giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu AS giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan AS install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu AS install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan AS yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu AS yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan AS jieSuan_Date_PlanStr,\n" +
            "phoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp,\n" +
            "\tphoi.jieSuan_Emp," +
            "\tphoi.jieSuan_Date_Accu AS jieSuan_Date_AccuStr\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "JOIN project_head ph ON ph.id = pho.head_id\n" +
            "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE\n" +
            "\tphoi.`status` = 0\n" +
            "AND phoi.checked = 2\n" +
            "AND phoi.id IN (\n" +
            "\tSELECT\n" +
            "\t\tmax(item2.id)\n" +
            "\tFROM\n" +
            "\t\tproject_head_order_item item2\n" +
            "\tGROUP BY\n" +
            "\t\titem2.order_id,item2.product_Name\n" +
            ")\n" +
            "AND (\n" +
            "\t(\n" +
            "\t\tphoi.zhanCha_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\t\tAND phoi.zhanCha_Date_Accu IS NULL\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tphoi.outDraw_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\t\tAND phoi.outDraw_Date_Accu IS NULL\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tphoi.program_confir_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\t\tAND phoi.program_confir_Date_Accu IS NULL\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tphoi.giveOrder_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\t\tAND phoi.giveOrder_Date_Accu IS NULL\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tphoi.delivery_Goods_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\t\tAND phoi.delivery_Goods_Date_Accu IS NULL\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tphoi.install_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\t\tAND phoi.install_Date_Accu IS NULL\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tphoi.yanShou_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\t\tAND phoi.yanShou_Date_Accu IS NULL\n" +
            "\t)\n" +
            "\tOR (\n" +
            "\t\tphoi.jieSuan_Emp LIKE CONCAT('%',#{empNo},'%')\n" +
            "\t\tAND phoi.jieSuan_Date_Accu IS NULL\n" +
            "\t)\n" +
            ")")
    List<ProjectHeadOrderItem> getAllItemByUserIdAndNoFinish(String empNo);


    @Select("SELECT\n" +
            "\tphoi.id AS id,\n" +
            "\tpho.ordeNo AS ordeNo,\n" +
            "\tpho.customer_Name,\n" +
            "\tpho.newOrOld AS newOrOld,\n" +
            "\tphoi.zhanCha_Date_Plan AS zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu AS zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan AS outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu AS outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan AS program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu AS program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan AS giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu AS giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan AS install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu AS install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan AS yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu AS yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan AS jieSuan_Date_PlanStr,\n" +
            "phoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp,\n" +
            "\tphoi.jieSuan_Emp," +
            "\tphoi.jieSuan_Date_Accu AS jieSuan_Date_AccuStr\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "JOIN project_head ph ON ph.id = pho.head_id\n" +
            "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE\n" +
            "\tphoi.`status` = 0\n" +
            "AND phoi.checked = 0\n" +
            "AND phoi.id IN (\n" +
            "\tSELECT\n" +
            "\t\tmax(item2.id)\n" +
            "\tFROM\n" +
            "\t\tproject_head_order_item item2\n" +
            "\tGROUP BY\n" +
            "\t\titem2.order_id,item2.product_Name\n" +
            ")")
    List<ProjectHeadOrderItem> getAllItemByUserIdAndNoFinish2(String empNo);

    @Select("SELECT\n" +
            "\tcustomer_Name\n" +
            "FROM\n" +
            "\tproject_head_order\n" +
            "GROUP BY\n" +
            "\tcustomer_Name\n" +
            "ORDER BY\n" +
            "\tcustomer_Name ASC")
    List<String> getAllCustomerName();

    @Select("SELECT\n" +
            "\tprojectName\n" +
            "FROM\n" +
            "\tproject_head\n" +
            "GROUP BY\n" +
            "\tprojectName\n" +
            "ORDER BY\n" +
            "\tprojectName ASC")
    List<String> getAllProjectName();


    @Select("SELECT\n" +
            "\tordeNo\n" +
            "FROM\n" +
            "\tproject_head_order\n" +
            "GROUP BY\n" +
            "\tordeNo\n" +
            "ORDER BY\n" +
            "\tordeNo ASC")
    List<String> getAllOrderNoList();

    @Select("SELECT\n" +
            "\tphoi.id,\n" +
            "\tifnull(pho.ordeNo,'') as ordeNo,\n" +
            "\tifnull(ph.projectName,'') as projectName,\n" +
            "\tifnull(phoi.product_Name,'') as product_Name,\n" +
            "\tifnull(pho.customer_Name,'') as customer_Name,\n" +
            "\tph.salor,\n" +
            "\tpho.newOrOld,\n" +
            "\tphoi.delivery_Date AS delivery_DateStr,\n" +
            "\tphoi.getOrder_Date_Plan AS getOrder_Date_PlanStr,\n" +
            "\tphoi.getOrder_Date_Accu AS getOrder_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Date_Plan AS zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu AS zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan AS outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu AS outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan AS program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu AS program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan AS giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu AS giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan AS install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu AS install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan AS yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu AS yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan AS jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu AS jieSuan_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp\n" +
            "FROM\n" +
            "\tproject_head_order_item phoi\n" +
            "LEFT JOIN project_head_order pho ON pho.id = phoi.order_id\n" +
            "LEFT JOIN project_head ph ON ph.id = pho.head_id\n" +
            "WHERE\n" +
            "\tphoi.`status` = 0\n" +
            "AND phoi.checked = 2\n" +
            "AND phoi.install_Date_Accu IS NULL\n" +
            "AND phoi.id IN (\n" +
            "\tSELECT\n" +
            "\t\tmax(item2.id)\n" +
            "\tFROM\n" +
            "\t\tproject_head_order_item item2\n" +
            "\tGROUP BY\n" +
            "\t\titem2.order_id,item2.product_Name\n" +
            ")")
    List<ProjectHeadOrderItem> getProjectItemAllByCondi();


    @Select("SELECT\n" +
            "\tphoi.id,\n" +
            "\tifnull(pho.ordeNo,'') as orderNo,\n" +
            "\tifnull(ph.projectName,'') as projectName,\n" +
            "\tifnull(phoi.product_Name,'') as product_Name,\n" +
            "\tifnull(pho.customer_Name,'') as customer_Name,\n" +
            "\tph.salor,\n" +
            "\tpho.newOrOld,\n" +
            "\tphoi.delivery_Date AS delivery_DateStr,\n" +
            "\tphoi.getOrder_Date_Plan AS getOrder_Date_PlanStr,\n" +
            "\tphoi.getOrder_Date_Accu AS getOrder_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Date_Plan AS zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu AS zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan AS outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu AS outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan AS program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu AS program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan AS giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu AS giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan AS install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu AS install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan AS yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu AS yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan AS jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu AS jieSuan_Date_AccuStr,\n" +
            "\tphoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp\n" +
            "FROM\n" +
            "\tproject_head_order_item phoi\n" +
            "LEFT JOIN project_head_order pho ON pho.id = phoi.order_id\n" +
            "LEFT JOIN project_head ph ON ph.id = pho.head_id\n" +
            "WHERE\n" +
            "\tphoi.`status` = 0\n" +
            "AND phoi.checked = 2\n" +
            "AND (\n" +
            "\tphoi.zhanCha_Date_Accu IS NULL\n" +
            "\tOR phoi.outDraw_Date_Accu IS NULL\n" +
            "\tOR phoi.program_confir_Date_Accu IS NULL\n" +
            "\tOR phoi.giveOrder_Date_Accu IS NULL\n" +
            "\tOR phoi.delivery_Goods_Date_Accu IS NULL\n" +
            "\tOR phoi.install_Date_Accu IS NULL\n" +
            "\tOR phoi.yanShou_Date_Accu IS NULL\n" +
            "\tOR phoi.jieSuan_Date_Accu IS NULL\n" +
            ")\n" +
            "AND phoi.id IN (\n" +
            "\tSELECT\n" +
            "\t\tmax(item2.id)\n" +
            "\tFROM\n" +
            "\t\tproject_head_order_item item2\n" +
            "\tGROUP BY\n" +
            "\t\titem2.order_id,item2.product_Name\n" +
            ")")
    List<ProjectHeadOrderItem> getProjectItemAllByCondi2();


    @Select("SELECT\n" +
            "\tphoi.delivery_Date AS delivery_DateStr,\n" +
            "\tpho.newOrOld AS newOrOld,\n" +
            "\tphoi.totalBao AS totalBao,\n" +
            "\tphoi.product_Name AS product_Name,\n" +
            "\tIFNULL(phoi.weiHuiMoney, 0.0) AS weiHuiMoney,\n" +
            "\tpho.ordeNo AS orderNo,\n" +
            "\tpho.customer_Name AS customer_Name,\n" +
            "\tphoi.zhanCha_Date_Plan AS zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu AS zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan AS outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu AS outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan AS program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu AS program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan AS giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu AS giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan AS install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu AS install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan AS yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu AS yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan AS jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu AS jieSuan_Date_AccuStr,\n" +
            "\tphoi.getOrder_Date_Plan AS getOrder_Date_PlanStr,\n" +
            "phoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp,\n" +
            "\tphoi.jieSuan_Emp," +
            "\tphoi.id AS id,\n" +
            "\tphoi.status AS status,\n" +
            "\tifnull(phoi.remark,'') AS remark," +
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
            "\t\t\titem2.order_id,item2.product_Name\n" +
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
            "phoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp,\n" +
            "\tphoi.jieSuan_Emp," +
            "\tpho.newOrOld as newOrOld," +
            "\tifnull(phoi.note,'') as note," +
            "\tphoi.order_id as order_id," +
            "\tpho.ordeNo as orderNo \n" +
            " FROM\n" +
            "\tproject_head ph\n" +
            "LEFT JOIN project_head_order pho ON ph.id = pho.head_id\n" +
            "LEFT JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE " +
            " pho.customer_Name = #{customerName} \n" +
            "and phoi.product_Name = #{productName} " +
            "and ph.projectName = #{projectName} " +
            "order by phoi.version desc limit 1")
    ProjectHeadOrderItem getTotalProjectOrderITEMMoreByOrderS(String empNo, String projectName, String customerName, String productName);


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
            "phoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp,\n" +
            "\tphoi.jieSuan_Emp," +
            "\tifnull(phoi.note,'') as note," +
            "\tphoi.order_id as order_id," +
            "\tpho.newOrOld as newOrOld," +
            "\tpho.ordeNo as orderNo \n" +
            " FROM\n" +
            "\tproject_head ph\n" +
            " JOIN project_head_order pho ON ph.id = pho.head_id\n" +
            " JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE " +
            " phoi.id = #{id}")
    ProjectHeadOrderItem getTotalProjectOrderITEMMoreById(Integer id);


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
            "\tifnull(phoi.product_Name,'') as product_Name,\n" +
            "\tphoi.version as version,\n" +
            "\tphoi.checked as checked,\n" +
            "\tphoi.saleManager as saleManager,\n" +
            "\tphoi.gendan as gendan,\n" +
            "\tphoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp,\n" +
            "\tphoi.jieSuan_Emp," +
            "\tifnull(phoi.note,'') as note," +
            "\tIFNULL(phoi.hetongMoney,0.0) as hetongMoney,\n" +
            "\tIFNULL(phoi.hereMoney,0.0) as hereMoney,\n" +
            "\tIFNULL(phoi.weiHuiMoney,0.0) as weiHuiMoney,\n" +
            "\tIFNULL(phoi.jindu_remark,'') as jindu_remark,\n" +
            "\tIFNULL(phoi.remark,'') as remark,\n" +
            "\tphoi.id as id," +
            "\tph.projectName,\n" +
            "\tifnull(pho.ordeNo,'') as ordeNo,\n" +
            "\tifnull(phoi.product_Name,'') as product_Name \n" +
            " FROM\n" +
            "\t  project_head_order_item phoi " +
            " LEFT JOIN project_head_order pho ON phoi.order_id = pho.id\n" +
            "LEFT JOIN project_head ph ON ph.id = pho.head_id" +
            " WHERE " +
            "phoi.id = #{id}")
    ProjectHeadOrderItem getOldHeadOrderItemByPhoi(ProjectHeadOrderItem item);


    @Insert("insert into project_head_order_item" +
            " (" +
            "getOrder_Date_Plan," +
            "getOrder_Date_Accu," +
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
            "\tprogram_confir_Emp,\n" +
            "\tgiveOrder_Emp,\n" +
            "\tdelivery_Goods_Emp,\n" +
            "\tinstall_Emp,\n" +
            "\tyanShou_Emp,\n" +
            "\tjieSuan_Emp," +
            "\tnote," +
            "remark)" +
            " values(" +
            "#{getOrder_Date_PlanStr}," +
            "#{getOrder_Date_AccuStr}," +
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
            "\t#{program_confir_Emp},\n" +
            "\t#{giveOrder_Emp},\n" +
            "\t#{delivery_Goods_Emp},\n" +
            "\t#{install_Emp},\n" +
            "\t#{yanShou_Emp},\n" +
            "\t#{jieSuan_Emp}," +
            "\t#{note}," +
            "#{remark})")
    void saveOrderItemMorOld(ProjectHeadOrderItem item);


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
            "\tzhanCha_Emp,\n" +
            "\toutDraw_Emp,\n" +
            "\tprogram_confir_Emp,\n" +
            "\tgiveOrder_Emp,\n" +
            "\tdelivery_Goods_Emp,\n" +
            "\tinstall_Emp,\n" +
            "\tyanShou_Emp,\n" +
            "\tjieSuan_Emp," +
            "\tnote," +
            "\tstatus," +
            "\tupdateUserId," +
            "\tupdateDate," +
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
            "\t#{zhanCha_Emp},\n" +
            "\t#{outDraw_Emp},\n" +
            "\t#{program_confir_Emp},\n" +
            "\t#{giveOrder_Emp},\n" +
            "\t#{delivery_Goods_Emp},\n" +
            "\t#{install_Emp},\n" +
            "\t#{yanShou_Emp},\n" +
            "\t#{jieSuan_Emp}," +
            "\t#{note}," +
            "\t#{status}," +
            "\t#{updateUserId}," +
            "\t#{updateDateStr}," +
            "#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveOrderItemMor(ProjectHeadOrderItem item);


    @Update("UPDATE project_oi_moneyrecord set item_id = #{newId} where item_id = #{oldId}")
    void updateProjectImage(Integer oldId, Integer newId);

    @Select("select count(*) from project_head where projectName = #{projectName} and salor = #{userid} ")
    int findNameRepeatOrNot(String userid, String projectName);


    @Select("select count(*) from project_head where projectName = #{projectName} and salor = #{salor} and id <> #{id} ")
    int findNameRepeatOrNot2(ProjectHead ph);

    @Update("update project_head  set projectName = #{projectName} , remark = #{remark}  where id = #{id} ")
    void updateProjectByNameAndRemark(ProjectHead ph);

    @Insert("insert into project_head (projectName,salor,remark) values(#{projectName},#{userId},#{remark})")
    void saveProjectByNameAndRemark(String userId, String projectName, String remark);


    @Insert("insert into project_head (projectName,salor,remark) values(#{projectName},#{salor},#{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveProjectHeadByBeanA(ProjectHead ph);


    @Select("  select id from project_head where projectName = #{projectName} and salor = #{salor} ")
    ProjectHead getProjectByNameAndSalor(String projectName, String salor);

    @Select("select id,`Name` from china where pid = 0 and id <>0")
    List<China> getAllMainProvince();

    @Select("select id from china where name like CONCAT('%',#{name},'%') limit 1")
    Integer getProvinceIdByName(String name);

    @Select("SELECT\n" +
            "\tph.id as id,pho.ordeNo\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "LEFT JOIN project_head ph ON ph.id = pho.head_id\n" +
            "WHERE\n" +
            "\tpho.customer_Name = #{customerName}\n" +
            "\t and ph.projectName = #{projectName}\n" +
            "AND ph.salor = #{userId}")
    ProjectHeadOrder checkOrderNoRepeat(String userId, String orderNo, String projectName, String customerName);

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
            "\tifnull(remark,'') as remark \n" +
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

    @Insert(" insert into project_head_order (head_id,province,customer_Name,ordeNo,newOrOld,remark) values" +
            " (#{head_Id},#{province},#{customerName},#{orderNo},#{newOrOld},#{remark}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void savePHOByBean(ProjectHeadOrder pho);


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
            "\tpho.id,\n" +
            "\tpho.province AS province,\n" +
            "\tpho.customer_Name AS customerName,\n" +
            "\tpho.newOrOld AS newOrOld,\n" +
            "\tpho.ordeNo AS orderNo,\n" +
            "\tifnull(pho.remark,'') as remark \n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "left join project_head ph on pho.head_id = ph.id\n" +
            "WHERE\n" +
            "\tpho.customer_Name = #{customerName} and ph.projectName = #{projectName} ")
    ProjectHeadOrder getProjectOrderByOrderNo(String projectName, String customerName, String orderNo);

    @Select("SELECT\n" +
            " \tid,\n" +
            "\tprovince as province,\n" +
            "\tcustomer_Name as customerName,\n" +
            "\tnewOrOld as newOrOld,\n" +
            "\tordeNo as orderNo,\n" +
            "\tifnull(remark,'') as remark " +
            "FROM \n" +
            "\tproject_head_order pho \n" +
            "WHERE\n" +
            "\tpho.ordeNo = #{orderNo} and pho.head_id = #{headId} and pho.customer_Name = #{custormerName} \n")
    ProjectHeadOrder getPHOByHeadIdAndOrderNoAndCstomerName(Integer headId, String custormerName, String orderNo);


    @Select("SELECT\n" +
            "\tui.useractor\n" +
            "FROM\n" +
            "\temployee ee\n" +
            " JOIN position n ON ee.positionId = n.id\n" +
            " JOIN dept t ON t.id = ee.deptId\n" +
            " join userinfo ui on ui.empno = ee.empno\n" +
            " join qyweixinbd bd on bd.empNo = ee.empno\n" +
            "WHERE\n" +
            "\tt.deptname LIKE \"%项目中心%\"\n" +
            "AND ee.isQuit = 0 and bd.userid = #{id}")
    Integer getUserActorByUserId(String id);


    @Select("SELECT\n" +
            "\tpom.id AS id,\n" +
            "\tDATE_FORMAT(pom.date, '%Y-%m-%d') AS dateStr,\n" +
            "\tpom.fapiaoNo,\n" +
            "\tpom.hereMoney,\n" +
            "\tpom.imageUrl,\n" +
            "\tifnull(pom.remark,'') as remark,\n" +
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
            "\tifnull(pom.remark,'') as remark,\n" +
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
            "\tprovince as province,\n" +
            "\tcustomer_Name as customerName,\n" +
            "\tnewOrOld as newOrOld,\n" +
            "\tordeNo as orderNo,\n" +
            "\tifnull(remark,'') as remark " +
            "FROM \n" +
            "\tproject_head_order pho \n" +
            "WHERE\n" +
            "\tpho.id = #{id}\n")
    ProjectHeadOrder getProjectOrderById(Integer id);


    @Select("select * from daysset where type = #{type}")
    DaysSet getDaysSetByType(int type);


    @Select("select faday from fading order by faday asc")
    List<String> getFaDingList();

    @Select(" select e.* from employee e LEFT JOIN  dept d on e.deptId = d.id\n" +
            " where d.deptname in (\"销售中心\") ")
    List<Employee> findAllProjectSalorByDeptName();


    @Select(" SELECT\n" +
            "\te.*\n" +
            "FROM\n" +
            "\temployee e \n" +
            "join userinfo info on info.empno = e.empno\n" +
            "LEFT JOIN dept d ON e.deptId = d.id\n" +
            "WHERE\n" +
            "\td.deptname IN (\"项目中心\") and info.useractor = 2")
    List<Employee> findAllProjectSalorByDeptName1();

    @Select("select `name` from qyweixinbd where userid = #{userId}")
    String getUserNameByUserId(String userId);


    @Select("SELECT\n" +
            "\tphoi.delivery_Date AS delivery_DateStr,\n" +
            "\tphoi.totalBao AS totalBao,\n" +
            "\tpho.customer_Name AS customer_Name,\n" +
            "\tphoi.product_Name AS product_Name,\n" +
            "\tifnull((phoi.hetongMoney), 0.0) - ifnull(\n" +
            "\t\t(\n" +
            "\t\t\tSELECT\n" +
            "\t\t\t\tsum(hereMoney)\n" +
            "\t\t\tFROM\n" +
            "\t\t\t\tproject_oi_moneyrecord\n" +
            "\t\t\tWHERE\n" +
            "\t\t\t\titem_id = phoi.id\n" +
            "\t\t),\n" +
            "\t\t0.0\n" +
            "\t) AS weiHuiMoney,\n" +
            "\tIFNULL(phoi.hetongMoney, 0.0) AS hetongMoney,\n" +
            "\tpho.ordeNo AS orderNo,\n" +
            "\tphoi.id AS id,\n" +
            "\tphoi.zhanCha_Date_Plan AS zhanCha_Date_PlanStr,\n" +
            "\tphoi.zhanCha_Date_Accu AS zhanCha_Date_AccuStr,\n" +
            "\tphoi.outDraw_Date_Plan AS outDraw_Date_PlanStr,\n" +
            "\tphoi.outDraw_Date_Accu AS outDraw_Date_AccuStr,\n" +
            "\tphoi.program_confir_Date_Plan AS program_confir_Date_PlanStr,\n" +
            "\tphoi.program_confir_Date_Accu AS program_confir_Date_AccuStr,\n" +
            "\tphoi.giveOrder_Date_Plan AS giveOrder_Date_PlanStr,\n" +
            "\tphoi.giveOrder_Date_Accu AS giveOrder_Date_AccuStr,\n" +
            "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
            "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
            "\tphoi.install_Date_Plan AS install_Date_PlanStr,\n" +
            "\tphoi.install_Date_Accu AS install_Date_AccuStr,\n" +
            "\tphoi.yanShou_Date_Plan AS yanShou_Date_PlanStr,\n" +
            "\tphoi.yanShou_Date_Accu AS yanShou_Date_AccuStr,\n" +
            "\tphoi.jieSuan_Date_Plan AS jieSuan_Date_PlanStr,\n" +
            "\tphoi.jieSuan_Date_Accu AS jieSuan_Date_AccuStr,\n" +
            "\tphoi.getOrder_Date_Plan AS getOrder_Date_PlanStr,\n" +
            "\tphoi.checked AS checked,\n" +
            "\tphoi. STATUS AS STATUS,\n" +
            "\tIFNULL(phoi.delivery_Goods_Emp, '') AS gendan,\n" +
            "\tph.projectName AS projectName,\n" +
            "\tphoi.order_id AS order_Id,\n" +
            "\tIFNULL(phoi.zhanCha_Emp, '') AS saleManager,\n" +
            "\tifnull(phoi.remark,'') AS remark,\n" +
            "\tphoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp,\n" +
            "\tphoi.jieSuan_Emp,\n" +
            "\tifnull(phoi.jindu_remark,'') as jindu_remark,\n" +
            "\tph.salor,\n" +
            "\tcha. NAME AS provinceStr,\n" +
            "\tph.projectName,\n" +
            "\tpho.newOrOld AS newOrOld,\n" +
            "\t(\n" +
            "\t\tcast(\n" +
            "\t\t\tphoi.version AS DECIMAL (11, 2)\n" +
            "\t\t) - 1\n" +
            "\t) * 10 AS historyGe\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "JOIN project_head ph ON ph.id = pho.head_id\n" +
            "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "LEFT JOIN china cha ON pho.province = cha.id\n" +
            "WHERE\n" +
            "\tphoi.id IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tmax(item2.id)\n" +
            "\t\tFROM\n" +
            "\t\t\tproject_head_order_item item2\n" +
            "\t\tGROUP BY\n" +
            "\t\t\titem2.order_id,item2.product_Name\n" +
            "\t) limit #{currentPageTotalNum},#{pageSize}")
    List<ProjectHeadOrderItem> findAllProjecHOI(ProjectHeadOrderItem item);


    @SelectProvider(type = ProjectMapper.ProjectDaoProvider.class, method = "queryProjectOrderItemByCondition")
    List<ProjectHeadOrderItem> queryProjectOrderItemByCondition(ProjectHeadOrderItem item);

    @SelectProvider(type = ProjectMapper.ProjectDaoProvider.class, method = "queryProjectOrderItemByConditionCount")
    int queryProjectOrderItemByConditionCount(ProjectHeadOrderItem item);

    @Select("SELECT  " +
            " count(*) " +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "JOIN project_head ph ON ph.id = pho.head_id\n" +
            "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "LEFT JOIN china cha ON pho.province = cha.id\n" +
            "WHERE\n" +
            "\tphoi.id IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tmax(item2.id)\n" +
            "\t\tFROM\n" +
            "\t\t\tproject_head_order_item item2\n" +
            "\t\tGROUP BY\n" +
            "\t\t\titem2.order_id,item2.product_Name\n" +
            "\t)")
    int findAllProjecHOICount();

    @Select("SELECT\n" +
            " \tid,\n" +
            "\tprojectName,\n" +
            "\tsalor,\n" +
            "\tifnull(remark,'') as remark \n" +
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
            "\tprogram_confir_Emp  = #{program_confir_Emp},\n" +
            "\tgiveOrder_Emp  = #{giveOrder_Emp},\n" +
            "\tdelivery_Goods_Emp  = #{delivery_Goods_Emp},\n" +
            "\tinstall_Emp  = #{install_Emp},\n" +
            "\tyanShou_Emp  = #{yanShou_Emp},\n" +
            "\tjieSuan_Emp  = #{jieSuan_Emp}," +
            "jieSuan_Date_Accu = #{jieSuan_Date_AccuStr}" +
            "  where id = #{id}")
    void updateOrderItemMorOld(ProjectHeadOrderItem phoi);

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
            "jieSuan_Date_Accu = #{jieSuan_Date_AccuStr}," +
            "version = #{version}  where id = #{id}")
    void updateProjectItemByBeanOnlyDateAndNote(ProjectHeadOrderItem phoi);

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
            "\tzhanCha_Emp = #{zhanCha_Emp},\n" +
            "\toutDraw_Emp  = #{outDraw_Emp},\n" +
            "\tprogram_confir_Emp  = #{program_confir_Emp},\n" +
            "\tgiveOrder_Emp  = #{giveOrder_Emp},\n" +
            "\tdelivery_Goods_Emp  = #{delivery_Goods_Emp},\n" +
            "\tinstall_Emp  = #{install_Emp},\n" +
            "\tyanShou_Emp  = #{yanShou_Emp},\n" +
            "\tupdateUserId  = #{updateUserId}," +
            "\tupdateDate  = #{updateDateStr}," +
            "\tjieSuan_Emp  = #{jieSuan_Emp}," +
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
            "phoi.zhanCha_Emp,\n" +
            "\tphoi.outDraw_Emp,\n" +
            "\tphoi.program_confir_Emp,\n" +
            "\tphoi.giveOrder_Emp,\n" +
            "\tphoi.delivery_Goods_Emp,\n" +
            "\tphoi.install_Emp,\n" +
            "\tphoi.yanShou_Emp,\n" +
            "\tphoi.jieSuan_Emp," +
            "phoi.id as id \n" +
            "FROM\n" +
            "\tproject_head_order_item phoi\n" +
            "JOIN project_head_order pho ON phoi.order_id = pho.id\n" +
            "WHERE\n" +
            " phoi.id = #{id} ")
    ProjectHeadOrderItem getProjectOrderItemByOrderNoAndProductName(ProjectHeadOrderItem item);


    @Select("SELECT\n" +
            "\tph.projectName,\n" +
            "\tpho.ordeNo,\n" +
            "\tphoi.product_Name,\n" +
            "\tbd.`name`,\n" +
            "\tbd.userid,\n" +
            "\tphoi.checked\n" +
            "FROM\n" +
            "\tproject_head_order_item phoi\n" +
            "LEFT JOIN project_head_order pho ON phoi.order_id = pho.id\n" +
            "LEFT JOIN project_head ph ON ph.id = pho.head_id\n" +
            "LEFT JOIN qyweixinbd bd ON bd.userid = ph.salor" +
            " where phoi.id = #{id}")
    ProjectHeadOrderItem getProjectOrderItemById(Integer id);

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
            "  ifnull(phoi.remark,'') as remark \n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "JOIN project_head ph ON ph.id = pho.head_id\n" +
            "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE\n" +
            "\t pho.customer_Name = #{customerName}\n" +
            "and phoi.product_Name = #{productName}\n" +
            "and ph.projectName = #{projectName}\n" +
            "and\n" +
            "\tphoi.id not IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tmin(item2.id)\n" +
            "\t\tFROM\n" +
            "\t\t\tproject_head_order_item item2\n" +
            "\t\tGROUP BY\n" +
            "\t\t\titem2.order_id,item2.product_Name\n" +
            "\t)")
    List<ProjectHeadOrderItem> getHistoryItemByProduct_NameAndOrderNo(String productName, String orderNo, String projectName, String customerName);


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


    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "ee.empno\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "JOIN dept t ON ee.deptId = t.id\n" +
            "JOIN position n ON n.id = ee.positionId\n" +
            "WHERE\n" +
            "\tt.deptname = '项目中心'\n" +
            "AND n.positionName like '%设计%' and ee.isQuit = 0")
    List<Employee> getSheJiItems();


    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "ee.empno\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "JOIN dept t ON ee.deptId = t.id\n" +
            "JOIN position n ON n.id = ee.positionId\n" +
            "WHERE\n" +
            "\tt.deptname = '项目中心'\n" +
            "AND n.positionName like '%安装%' and ee.isQuit = 0")
    List<Employee> getAnzhuangList();

    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "ee.empno\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "JOIN dept t ON ee.deptId = t.id\n" +
            "JOIN position n ON n.id = ee.positionId\n" +
            "WHERE\n" +
            "\tt.deptname = '项目中心'\n" +
            "AND n.positionName like '%业务%' or n.positionName like '%大客户%' and ee.isQuit = 0")
    List<Employee> getYeWuList();


    @Select("call splitString(#{empNos},\",\");")
    void returnNameByEmpNoStrBefore(String empNos);

    //    @Select(" select group_concat(name,'') from employee where empno in(select * from tmp_split);  ")
    @SelectProvider(type = ProjectMapper.ProjectDaoProvider.class, method = "returnNameByEmpNoStr")
    String returnNameByEmpNoStr(@Param("empNoList") List<String> empNoList);

    class ProjectDaoProvider {

        public String queryProjectOrderItemByCondition(ProjectHeadOrderItem item) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tphoi.delivery_Date AS delivery_DateStr,\n" +
                    "\tphoi.totalBao AS totalBao,\n" +
                    "\tpho.customer_Name AS customer_Name,\n" +
                    "\tphoi.product_Name AS product_Name,\n" +
                    "\tifnull((phoi.hetongMoney), 0.0) - ifnull(\n" +
                    "\t\t(\n" +
                    "\t\t\tSELECT\n" +
                    "\t\t\t\tsum(hereMoney)\n" +
                    "\t\t\tFROM\n" +
                    "\t\t\t\tproject_oi_moneyrecord\n" +
                    "\t\t\tWHERE\n" +
                    "\t\t\t\titem_id = phoi.id\n" +
                    "\t\t),\n" +
                    "\t\t0.0\n" +
                    "\t) AS weiHuiMoney,\n" +
                    "\tIFNULL(phoi.hetongMoney, 0.0) AS hetongMoney,\n" +
                    "\tpho.ordeNo AS orderNo,\n" +
                    "\tphoi.id AS id,\n" +
                    "\tphoi.zhanCha_Date_Plan AS zhanCha_Date_PlanStr,\n" +
                    "\tphoi.zhanCha_Date_Accu AS zhanCha_Date_AccuStr,\n" +
                    "\tphoi.outDraw_Date_Plan AS outDraw_Date_PlanStr,\n" +
                    "\tphoi.outDraw_Date_Accu AS outDraw_Date_AccuStr,\n" +
                    "\tphoi.program_confir_Date_Plan AS program_confir_Date_PlanStr,\n" +
                    "\tphoi.program_confir_Date_Accu AS program_confir_Date_AccuStr,\n" +
                    "\tphoi.giveOrder_Date_Plan AS giveOrder_Date_PlanStr,\n" +
                    "\tphoi.giveOrder_Date_Accu AS giveOrder_Date_AccuStr,\n" +
                    "\tphoi.delivery_Goods_Date_Plan AS delivery_Goods_Date_PlanStr,\n" +
                    "\tphoi.delivery_Goods_Date_Accu AS delivery_Goods_Date_AccuStr,\n" +
                    "\tphoi.install_Date_Plan AS install_Date_PlanStr,\n" +
                    "\tphoi.install_Date_Accu AS install_Date_AccuStr,\n" +
                    "\tphoi.yanShou_Date_Plan AS yanShou_Date_PlanStr,\n" +
                    "\tphoi.yanShou_Date_Accu AS yanShou_Date_AccuStr,\n" +
                    "\tphoi.jieSuan_Date_Plan AS jieSuan_Date_PlanStr,\n" +
                    "\tphoi.jieSuan_Date_Accu AS jieSuan_Date_AccuStr,\n" +
                    "\tphoi.getOrder_Date_Plan AS getOrder_Date_PlanStr,\n" +
                    "\tphoi.checked AS checked,\n" +
                    "\tphoi. STATUS AS STATUS,\n" +
                    "\tIFNULL(phoi.delivery_Goods_Emp, '') AS gendan,\n" +
                    "\tph.projectName AS projectName,\n" +
                    "\tphoi.order_id AS order_Id,\n" +
                    "\tIFNULL(phoi.zhanCha_Emp, '') AS saleManager,\n" +
                    "\tifnull(phoi.remark, '') AS remark,\n" +
                    "\tphoi.zhanCha_Emp,\n" +
                    "\tphoi.outDraw_Emp,\n" +
                    "\tphoi.program_confir_Emp,\n" +
                    "\tphoi.giveOrder_Emp,\n" +
                    "\tphoi.delivery_Goods_Emp,\n" +
                    "\tphoi.install_Emp,\n" +
                    "\tphoi.yanShou_Emp,\n" +
                    "\tphoi.jieSuan_Emp,\n" +
                    "\tifnull(phoi.jindu_remark, '') AS jindu_remark,\n" +
                    "\tph.salor,\n" +
                    "\tcha. NAME AS provinceStr,\n" +
                    "\tph.projectName,\n" +
                    "\tpho.newOrOld AS newOrOld,\n" +
                    "\t(\n" +
                    "\t\tcast(\n" +
                    "\t\t\tphoi.version AS DECIMAL (11, 2)\n" +
                    "\t\t) - 1\n" +
                    "\t) * 10 AS historyGe\n" +
                    "FROM\n" +
                    "\tproject_head_order pho\n" +
                    "JOIN project_head ph ON ph.id = pho.head_id\n" +
                    "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
                    "LEFT JOIN china cha ON pho.province = cha.id\n" +
                    "join qyweixinbd qy on qy.userid = ph.salor\n" +
                    "WHERE\n" +
                    "\tphoi.id IN (\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tmax(item2.id)\n" +
                    "\t\tFROM\n" +
                    "\t\t\tproject_head_order_item item2\n" +
                    "\t\tGROUP BY\n" +
                    "\t\t\titem2.order_id,\n" +
                    "\t\t\titem2.product_Name\n" +
                    "\t)\n");

            if (item.getNameIds() != null && item.getNameIds().size() > 0) {
                if (item.getNameIds().size() == 1) {
                    sb.append("  and qy.name in ('" + item.getNameIds().get(0) + "')");
                } else if (item.getNameIds().size() >= 2) {
                    sb.append("  and qy.name in (");
                    for (int i = 0; i < item.getNameIds().size() - 1; i++) {
                        sb.append("'" + item.getNameIds().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getNameIds().get(item.getNameIds().size() - 1) + "')");
                }
            }


            if (item.getOrderNos() != null && item.getOrderNos().size() > 0) {
                if (item.getOrderNos().size() == 1) {
                    sb.append("  and pho.ordeNo in ('" + item.getOrderNos().get(0) + "')");
                } else if (item.getOrderNos().size() >= 2) {
                    sb.append("  and pho.ordeNo in (");
                    for (int i = 0; i < item.getOrderNos().size() - 1; i++) {
                        sb.append("'" + item.getOrderNos().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getOrderNos().get(item.getOrderNos().size() - 1) + "')");
                }
            }


            if (item.getProjectNames() != null && item.getProjectNames().size() > 0) {
                if (item.getProjectNames().size() == 1) {
                    sb.append("  and ph.projectName in ('" + item.getProjectNames().get(0) + "')");
                } else if (item.getProjectNames().size() >= 2) {
                    sb.append("  and ph.projectName in (");
                    for (int i = 0; i < item.getProjectNames().size() - 1; i++) {
                        sb.append("'" + item.getProjectNames().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getProjectNames().get(item.getProjectNames().size() - 1) + "')");
                }
            }


            if (item.getCustomerNames() != null && item.getCustomerNames().size() > 0) {
                if (item.getCustomerNames().size() == 1) {
                    sb.append("  and pho.customer_Name in ('" + item.getCustomerNames().get(0) + "')");
                } else if (item.getCustomerNames().size() >= 2) {
                    sb.append("  and pho.customer_Name in (");
                    for (int i = 0; i < item.getCustomerNames().size() - 1; i++) {
                        sb.append("'" + item.getCustomerNames().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getCustomerNames().get(item.getCustomerNames().size() - 1) + "')");
                }
            }


            if (item.getCheckeds() != null && item.getCheckeds().size() > 0) {
                if (item.getCheckeds().size() == 1) {
                    sb.append("  and phoi.checked in ('" + item.getCheckeds().get(0) + "')");
                } else if (item.getCheckeds().size() >= 2) {
                    sb.append("  and phoi.checked in (");
                    for (int i = 0; i < item.getCheckeds().size() - 1; i++) {
                        sb.append("'" + item.getCheckeds().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getCheckeds().get(item.getCheckeds().size() - 1) + "')");
                }
            }


            if (item.getStatuss() != null && item.getStatuss().size() > 0) {
                if (item.getStatuss().size() == 1) {
                    sb.append("  and phoi.status in ('" + item.getStatuss().get(0) + "')");
                } else if (item.getStatuss().size() >= 2) {
                    sb.append("  and phoi.status in (");
                    for (int i = 0; i < item.getStatuss().size() - 1; i++) {
                        sb.append("'" + item.getStatuss().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getStatuss().get(item.getStatuss().size() - 1) + "')");
                }
            }


            if (item.getGetOrder_Date_PlanStr() != null && item.getGetOrder_Date_PlanStr().length() > 0) {
                sb.append(" and phoi.getOrder_Date_Plan = #{getOrder_Date_PlanStr}");
            }

            if (item.getDelivery_DateStr() != null && item.getDelivery_DateStr().length() > 0) {
                sb.append(" and phoi.delivery_Date = #{delivery_DateStr}");
            }

            // provinceStr customer_Name orderNo product_Name delivery_DateStr getOrder_Date_PlanStr zhanCha_Date_PlanStr
            // outDraw_Date_PlanStr  program_confir_Date_PlanStr giveOrder_Date_PlanStr delivery_Goods_Date_PlanStr
            // install_Date_PlanStr yanShou_Date_PlanStr isYanShou jindu_remark totalBao hetongMoney
            // hereMoney weiHuiMoney statusStr checkedStr remark

            if (item.getSortMethod() != null && !"undefined".equals(item.getSortMethod()) && !"undefined".equals(item.getSortByName()) && item.getSortByName() != null) {
                if ("provinceStr".equals(item.getSortByName())) {
                    sb.append(" order by pho.province ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("remark".equals(item.getSortByName())) {
                    sb.append(" order by phoi.remark ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("checkedStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.checkedStr ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("statusStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.statusStr ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("weiHuiMoney".equals(item.getSortByName())) {
                    sb.append(" order by phoi.weiHuiMoney ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("hereMoney".equals(item.getSortByName())) {
                    sb.append(" order by phoi.hereMoney ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("hetongMoney".equals(item.getSortByName())) {
                    sb.append(" order by phoi.hetongMoney ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("totalBao".equals(item.getSortByName())) {
                    sb.append(" order by phoi.totalBao ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("jindu_remark".equals(item.getSortByName())) {
                    sb.append(" order by phoi.jindu_remark ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yanShou_Date_PlanStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.yanShou_Date_Plan ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("install_Date_PlanStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.install_Date_Plan ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("delivery_Goods_Date_PlanStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.delivery_Goods_Date_Plan ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("giveOrder_Date_PlanStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.giveOrder_Date_Plan ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("program_confir_Date_PlanStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.program_confir_Date_Plan ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("outDraw_Date_PlanStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.outDraw_Date_Plan ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("getOrder_Date_PlanStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.getOrder_Date_Plan ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("zhanCha_Date_PlanStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.zhanCha_Date_Plan ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("customer_Name".equals(item.getSortByName())) {
                    sb.append(" order by pho.customer_Name ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("orderNo".equals(item.getSortByName())) {
                    sb.append(" order by pho.ordeNo ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("product_Name".equals(item.getSortByName())) {
                    sb.append(" order by phoi.product_Name ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("delivery_DateStr".equals(item.getSortByName())) {
                    sb.append(" order by phoi.delivery_Date ");
                    if ("asc".equals(item.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(item.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append("order by ph.projectName,pho.customer_Name,phoi.product_Name ");
            }

            sb.append("  limit #{currentPageTotalNum},#{pageSize}");

            return sb.toString();
        }

        public String queryProjectOrderItemByConditionCount(ProjectHeadOrderItem item) {
            StringBuilder sb = new StringBuilder("SELECT" +
                    " count(*) " +
                    "FROM\n" +
                    "\tproject_head_order pho\n" +
                    "JOIN project_head ph ON ph.id = pho.head_id\n" +
                    "JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
                    "LEFT JOIN china cha ON pho.province = cha.id\n" +
                    "join qyweixinbd qy on qy.userid = ph.salor\n" +
                    "WHERE\n" +
                    "\tphoi.id IN (\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tmax(item2.id)\n" +
                    "\t\tFROM\n" +
                    "\t\t\tproject_head_order_item item2\n" +
                    "\t\tGROUP BY\n" +
                    "\t\t\titem2.order_id,\n" +
                    "\t\t\titem2.product_Name\n" +
                    "\t)\n");


            if (item.getNameIds() != null && item.getNameIds().size() > 0) {
                if (item.getNameIds().size() == 1) {
                    sb.append("  and qy.name in ('" + item.getNameIds().get(0) + "')");
                } else if (item.getNameIds().size() >= 2) {
                    sb.append("  and qy.name in (");
                    for (int i = 0; i < item.getNameIds().size() - 1; i++) {
                        sb.append("'" + item.getNameIds().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getNameIds().get(item.getNameIds().size() - 1) + "')");
                }
            }


            if (item.getOrderNos() != null && item.getOrderNos().size() > 0) {
                if (item.getOrderNos().size() == 1) {
                    sb.append("  and pho.ordeNo in ('" + item.getOrderNos().get(0) + "')");
                } else if (item.getOrderNos().size() >= 2) {
                    sb.append("  and pho.ordeNo in (");
                    for (int i = 0; i < item.getOrderNos().size() - 1; i++) {
                        sb.append("'" + item.getOrderNos().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getOrderNos().get(item.getOrderNos().size() - 1) + "')");
                }
            }


            if (item.getProjectNames() != null && item.getProjectNames().size() > 0) {
                if (item.getProjectNames().size() == 1) {
                    sb.append("  and ph.projectName in ('" + item.getProjectNames().get(0) + "')");
                } else if (item.getProjectNames().size() >= 2) {
                    sb.append("  and ph.projectName in (");
                    for (int i = 0; i < item.getProjectNames().size() - 1; i++) {
                        sb.append("'" + item.getProjectNames().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getProjectNames().get(item.getProjectNames().size() - 1) + "')");
                }
            }


            if (item.getCustomerNames() != null && item.getCustomerNames().size() > 0) {
                if (item.getCustomerNames().size() == 1) {
                    sb.append("  and pho.customer_Name in ('" + item.getCustomerNames().get(0) + "')");
                } else if (item.getCustomerNames().size() >= 2) {
                    sb.append("  and pho.customer_Name in (");
                    for (int i = 0; i < item.getCustomerNames().size() - 1; i++) {
                        sb.append("'" + item.getCustomerNames().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getCustomerNames().get(item.getCustomerNames().size() - 1) + "')");
                }
            }


            if (item.getCheckeds() != null && item.getCheckeds().size() > 0) {
                if (item.getCheckeds().size() == 1) {
                    sb.append("  and phoi.checked in ('" + item.getCheckeds().get(0) + "')");
                } else if (item.getCheckeds().size() >= 2) {
                    sb.append("  and phoi.checked in (");
                    for (int i = 0; i < item.getCheckeds().size() - 1; i++) {
                        sb.append("'" + item.getCheckeds().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getCheckeds().get(item.getCheckeds().size() - 1) + "')");
                }
            }


            if (item.getStatuss() != null && item.getStatuss().size() > 0) {
                if (item.getStatuss().size() == 1) {
                    sb.append("  and phoi.status in ('" + item.getStatuss().get(0) + "')");
                } else if (item.getStatuss().size() >= 2) {
                    sb.append("  and phoi.status in (");
                    for (int i = 0; i < item.getStatuss().size() - 1; i++) {
                        sb.append("'" + item.getStatuss().get(i) + "'" + ",");
                    }
                    sb.append("'" + item.getStatuss().get(item.getStatuss().size() - 1) + "')");
                }
            }

            if (item.getGetOrder_Date_PlanStr() != null && item.getGetOrder_Date_PlanStr().length() > 0) {
                sb.append(" and phoi.getOrder_Date_Plan >= #{getOrder_Date_PlanStr}");
            }

            if (item.getDelivery_DateStr() != null && item.getDelivery_DateStr().length() > 0) {
                sb.append(" and phoi.delivery_Date >= #{delivery_DateStr}");
            }

            return sb.toString();
        }

        public String returnNameByEmpNoStr(List<String> empNoList) {
            //@Select(" select group_concat(name,'') from employee where empno in(select * from tmp_split)
            StringBuilder sb = new StringBuilder("select group_concat(name,'') from employee where 1=1 ");
            if (empNoList != null && empNoList.size() > 0) {
                if (empNoList.size() == 1) {
                    sb.append(" and empno in  ('" + empNoList.get(0) + "')");
                    System.out.println(empNoList.get(0));
                } else if (empNoList.size() >= 2) {
                    sb.append("  and empno in (");
                    for (int i = 0; i < empNoList.size() - 1; i++) {
                        sb.append("'" + empNoList.get(i) + "'" + ",");
                    }
                    sb.append("'" + empNoList.get(empNoList.size() - 1) + "')");
                }
            }
            return sb.toString();
        }

    }
}



