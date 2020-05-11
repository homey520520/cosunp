package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.DesignMaterialHead;
import com.cosun.cosunp.entity.DesignMaterialHeadProduct;
import com.cosun.cosunp.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DesignMapper {

    @Select("SELECT\n" +
            "\tee.id,\n" +
            "\tee.`name`,\n" +
            "\tee.empno\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "LEFT JOIN dept t ON t.id = ee.deptId\n" +
            "LEFT JOIN position n ON n.id = ee.positionId\n" +
            "WHERE\n" +
            "\tt.deptname IN ('项目中心')\n" +
            "AND n.positionName IN (\n" +
            "\t'业务经理',\n" +
            "\t'大客户经理',\n" +
            "\t'外贸业务员'\n" +
            ")\n" +
            "OR (\n" +
            "\tt.deptname = '销售中心'\n" +
            "\tAND (\n" +
            "\t\tn.positionName NOT LIKE '%跟单%'\n" +
            "\t\tand n.positionName NOT LIKE '%助理%'\n" +
            "\t)\n" +
            ")\n" +
            "AND ee.isQuit = 0")
    List<Employee> getSalor();

    @Select("SELECT\n" +
            "\tee.id,\n" +
            "\tee.`name`,\n" +
            "\tee.empno,\n" +
            "\tn.positionName\n" +
            "FROM\n" +
            "\temployee ee\n" +
            "LEFT JOIN dept t ON t.id = ee.deptId\n" +
            "LEFT JOIN position n ON n.id = ee.positionId\n" +
            "WHERE\n" +
            "\tt.deptname IN ('设计部')\n" +
            "AND ee.isQuit = 0")
    List<Employee> getAllMaker();


    @Select("SELECT\n" +
            "\tcustomerNo\n" +
            "FROM\n" +
            "\tdesignmaterialhead\n" +
            "GROUP BY\n" +
            "\tcustomerNo\n" +
            "ORDER BY\n" +
            "\tcustomerNo DESC")
    List<String> getAllOrderNo();

    @Select("SELECT\n" +
            "\tdrawingNo\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct\n" +
            "group by drawingNo\n" +
            "order by drawingNo asc")
    List<String> getAlldrawingNoList();


    @Select("SELECT\n" +
            "\tproductNo\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct\n" +
            "group by productNo\n" +
            "order by productNo asc")
    List<String> getAllproductNoList();

    @Select("SELECT\n" +
            "\tproductName\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct\n" +
            "group by productName\n" +
            "order by productName asc")
    List<String> getAllproductNameList();


    @Select("SELECT\n" +
            "\torderArea\n" +
            "FROM\n" +
            "\tdesignmaterialhead\n" +
            "GROUP BY\n" +
            "\torderArea\n" +
            "ORDER BY\n" +
            "\torderArea asc")
    List<String> getAllOrderArea();

    @Select("SELECT\n" +
            "\tdmh.id,\n" +
            "\tdmh.customerNo,\n" +
            "\tdmh.getOrderDate AS getOrderDateStr,\n" +
            "\tdmh.orderArea,\n" +
            "\tee. NAME AS salorEmpStr,\n" +
            "\tdmh.deliveryOrderDate AS deliveryOrderDateStr,\n" +
            "\teee. NAME AS orderMakerStr,\n" +
            "\tdmh.remark\n" +
            "FROM\n" +
            "\tdesignmaterialhead dmh\n" +
            "LEFT JOIN employee ee ON ee.empno = dmh.salorEmp\n" +
            "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker\n" +
            "ORDER BY\n" +
            "\tid DESC limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHead> getAllDMH(DesignMaterialHead orderHead);


    @Select("SELECT\n" +
            "\tdmhp.head_id,\n" +
            "\tdmhp.id,\n" +
            "\tdmhp.productName,\n" +
            "\tdmhp.productNo,\n" +
            "\tdmhp.needNum,\n" +
            "\tdmhp.drawingNo,\n" +
            "\tdmhp.productRoute,\n" +
            "\tdmhp.remark\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            "LEFT JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id\n" +
            "order by dmh.customerNo desc,dmhp.productNo asc " +
            "limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHeadProduct> getAllDMHP(DesignMaterialHeadProduct pmhp);

    @Select("SELECT\n" +
            "\tdmhp.head_id,\n" +
            "\tdmhp.id,\n" +
            "\tdmhp.productName,\n" +
            "\tdmhp.productNo,\n" +
            "\tdmhp.needNum,\n" +
            "\tdmhp.drawingNo,\n" +
            "\tdmhp.productRoute,\n" +
            "\tdmhp.remark\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            "LEFT JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id" +
            " where dmh.customerNo = #{customerNo} " +
            "order by dmh.customerNo desc,dmhp.productNo asc " +
            "limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHeadProduct> getAllDMHPByCustomerNo(DesignMaterialHeadProduct pmhp);


    @Select("SELECT" +
            "  count(*) " +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            "LEFT JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id" +
            " where dmh.customerNo = #{customerNo}")
    int getAllDMHPByCustomerNoCountCount(DesignMaterialHeadProduct pmhp);

    @Select("SELECT" +
            " count(*) " +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            "LEFT JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id")
    int getAllDMHPCount();

    @Select("SELECT\n" +
            "  count(*) " +
            "FROM\n" +
            "\tdesignmaterialhead dmh\n" +
            "LEFT JOIN employee ee ON ee.empno = dmh.salorEmp\n" +
            "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker")
    int getAllDMHCount();

    @Select("select count(*) from designmaterialhead where customerNo = #{customerNo} ")
    int getSJHeadByOrderNo(String customerNo);

    @Select("SELECT\n" +
            "\tcount(phh.id) \n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct ph \n" +
            " join designmaterialhead phh on ph.head_id = phh.id\n" +
            "WHERE\n" +
            "\tph.productNo = #{productNo}\n" +
            "  and phh.customerNo = #{customerNo} limit 1")
    Integer getSJHeadByProductNo(String productNo, String customerNo);

    @Select("SELECT\n" +
            "\tphh.id \n" +
            "FROM\n" +
            " designmaterialhead phh " +
            "WHERE\n" +
            " phh.customerNo = #{customerNo} limit 1")
    Integer getHeadIdByCustomerNo(String customerNo);

    @Insert("insert into designmaterialhead (customerNo,\n" +
            "\tgetOrderDate,\n" +
            "\torderArea,\n" +
            "\tsalorEmp,\n" +
            "\tdeliveryOrderDate,\n" +
            "\torderMaker,\n" +
            "\tremark) " +
            "values" +
            "(" +
            "\t#{customerNo},\n" +
            "\t#{getOrderDateStr},\n" +
            "\t#{orderArea},\n" +
            "\t#{salorEmp},\n" +
            "\t#{deliveryOrderDateStr},\n" +
            "\t#{orderMaker},\n" +
            "\t#{remark}\n" +
            ")")
    void saveSJHeadDateToMysql(DesignMaterialHead dmh);

    @Insert("insert into designmaterialheadproduct (head_id,\n" +
            "\tproductName,\n" +
            "\tproductNo,\n" +
            "\tneedNum,\n" +
            "\tdrawingNo,\n" +
            "\tproductRoute,\n" +
            "\tremark)" +
            " values " +
            " (" +
            "#{head_id},\n" +
            "\t#{productName},\n" +
            "\t#{productNo},\n" +
            "\t#{needNum},\n" +
            "\t#{drawingNo},\n" +
            "\t#{productRoute},\n" +
            "\t#{remark}" +
            ")")
    void saveSJHeadPDateToMysql(DesignMaterialHeadProduct dmhp);


}



