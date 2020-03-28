package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.China;
import com.cosun.cosunp.entity.ProjectHead;
import com.cosun.cosunp.entity.ProjectHeadOrder;
import com.cosun.cosunp.entity.ProjectHeadOrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectMapper {


    @Select(" select * from project_head  where salor = #{empNo} ")
    List<ProjectHead> totalProjectNumByEmpNo(String empNo);

    @Select("SELECT\n" +
            "\tpho.id as id,\n" +
            "\tpho.customer_Name as customerName,\n" +
            "\ta.`Name` as provinceStr,\n" +
            "\tpho.ordeNo as orderNo,\n" +
            "  '2020-09-20' as deliverStr,\n" +
            "  20000 as yiShouAmount\n" +
            "FROM\n" +
            "\tproject_head ph\n" +
            "LEFT JOIN project_head_order pho ON ph.id = pho.head_id\n" +
            "left join china a on a.id = pho.province\n" +
            "WHERE\n" +
            "\tph.salor = #{empNo} \n" +
            "AND ph.projectName = #{projectName} ")
    List<ProjectHeadOrder> getTotalProjectOrderByName(String empNo, String projectName);


    @Select("SELECT\n" +
            "\tphoi.delivery_Date as delivery_DateStr,\n" +
            "\tphoi.totalBao as totalBao,\n" +
            "\tphoi.product_Name as product_Name,\n" +
            "\tpho.ordeNo as orderNo\n" +
            "FROM\n" +
            "\tproject_head ph\n" +
            "LEFT JOIN project_head_order pho ON ph.id = pho.head_id\n" +
            "LEFT JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE\n" +
            "\tph.salor = #{empNo}\n" +
            "AND pho.ordeNo = #{projectName}")
    List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderS(String empNo, String projectName);


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
            "\tphoi.id as id,\n" +
            "\tpho.ordeNo as orderNo \n" +
            " FROM\n" +
            "\tproject_head ph\n" +
            "LEFT JOIN project_head_order pho ON ph.id = pho.head_id\n" +
            "LEFT JOIN project_head_order_item phoi ON phoi.order_id = pho.id\n" +
            "WHERE " +
            "\tph.salor = #{empNo}\n" +
            "AND pho.ordeNo = #{orderNo} \n" +
            "and phoi.product_Name = #{projectName}")
    ProjectHeadOrderItem getTotalProjectOrderITEMMoreByOrderS(String empNo, String projectName, String orderNo);

    @Select("select count(*) from project_head where projectName = #{projectName} and salor = #{userid} ")
    int findNameRepeatOrNot(String userid, String projectName);

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

    @Select(" select id from project_head where projectName = #{projectName} and salor = #{salor}")
    ProjectHead findPHbyName(String projectName, String salor);

    @Insert(" insert into project_head_order (head_id,province,customer_Name,ordeNo) values" +
            " (#{id},#{provinceId},#{customerName},#{orderNo}) ")
    void saveProjectHeadByBean(Integer provinceId, String userId, String orderNo, String customerName, String projectName, Integer id);


    @Select("SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\tproject_head_order pho\n" +
            "LEFT JOIN project_head_order_item phoi ON pho.id = phoi.order_id\n" +
            "WHERE\n" +
            "\tpho.ordeNo = #{orderNo} \n" +
            "AND phoi.product_Name = #{productName} ")
    ProjectHeadOrderItem checkProductRepeatForOneOrder(String orderNo, String productName);


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
            "\tordeNo as orderNo,\n" +
            "\tremark " +
            "FROM \n" +
            "\tproject_head_order pho \n" +
            "WHERE\n" +
            "\tpho.ordeNo = #{orderNo}\n")
    ProjectHeadOrder getProjectOrderByOrderNo(String orderNo);

    @Insert(" insert into project_head_order_item (order_id,delivery_Date,totalBao,product_Name) values" +
            " (#{id},#{date},#{totalBao},#{productName}) ")
    void saveProjectHeadItemByBean(Integer id, String productName, String totalBao, String date);


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
            "jieSuan_Date_Accu = #{jieSuan_Date_AccuStr}" +
            "  where id = #{id}")
    void saveOrderItemMor(ProjectHeadOrderItem phoi);

    @Update("update project_head_order set province = #{province},\n" +
            "\tcustomer_Name = #{customerName},\n" +
            "\tordeNo = #{orderNo}" +
            "where id = #{id} ")
    void updateOrderNoRepeat(ProjectHeadOrder pho);


}

