package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;
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
            "\tdm.customerNo,\n" +
            "\tdmh.productNo\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem item\n" +
            "LEFT JOIN designmaterialheadproduct dmh ON dmh.id = item.head_product_id\n" +
            "LEFT JOIN designmaterialhead dm ON dm.id = dmh.head_id\n" +
            "WHERE\n" +
            "\tdmh.id = #{headId} limit 1")
    DesignMaterialHeadProductItem getCustomerNameAndProductNoByHeadId(Integer headId);


    @Select("SELECT\n" +
            "\tdm.customerNo,\n" +
            "\tdmh.productNo\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmh\n" +
            "LEFT JOIN designmaterialhead dm ON dm.id = dmh.head_id\n" +
            "WHERE\n" +
            "\tdmh.id = #{headId}\n" +
            "LIMIT 1")
    DesignMaterialHeadProductItem getCustomerNameAndProductNoByHeadId2(Integer headId);

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
            "\tdesignmaterialhead  where orderMaker = #{empNo} \n" +
            "GROUP BY\n" +
            "\tcustomerNo\n" +
            "ORDER BY\n" +
            "\tcustomerNo DESC")
    List<String> getAllOrderNo(String empNo);


    @Select("SELECT\n" +
            "\tmaterialSpeci\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem\n" +
            "GROUP BY\n" +
            "\tmaterialSpeci\n" +
            "ORDER BY\n" +
            "\tmaterialSpeci ASC")
    List<String> getAllmaterialSpeciList();

    @Select("SELECT\n" +
            "\tmaterialName\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem\n" +
            "GROUP BY\n" +
            "\tmaterialName\n" +
            "ORDER BY\n" +
            "\tmaterialName ASC")
    List<String> getAllmaterialNameList();

    @Select("SELECT\n" +
            "\tmateiralNo\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem\n" +
            "GROUP BY\n" +
            "\tmateiralNo\n" +
            "ORDER BY\n" +
            "\tmateiralNo ASC")
    List<String> getAllmateiralNoList();


    @Select("SELECT\n" +
            "\tdrawingNo\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct\n" +
            "group by drawingNo\n" +
            "order by drawingNo asc")
    List<String> getAlldrawingNoList();


    @Select("SELECT\n" +
            "\tpd.productNo\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct pd\n" +
            "LEFT JOIN designmaterialhead dh ON pd.head_id = dh.id\n" +
            " where dh.customerNo = #{customerNo} " +
            "GROUP BY\n" +
            "\tproductNo\n" +
            "ORDER BY\n" +
            "\tproductNo ASC")
    List<String> getAllproductNoList(String customerNo);

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
            " where dmh.orderMaker = #{loginName} " +
            "ORDER BY\n" +
            "\tid desc  limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHead> getAllDMH(DesignMaterialHead orderHead);

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
            " where dmh.orderMaker = #{empNo} and dmh.customerNo = #{orderNo} limit 1 ")
    DesignMaterialHead getSJbyOrderNoAndEmpno(String empNo, String orderNo);

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
            " " +
            "ORDER BY\n" +
            "\tid desc  limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHead> getAllDMH22(DesignMaterialHead orderHead);


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
            "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker where dmh.id = #{id}")
    DesignMaterialHead getHeadIdandInfoById(Integer id);


    @Select("SELECT\n" +
            "\tdmh.id,\n" +
            "\tdmh.customerNo,\n" +
            "\tdmh.getOrderDate AS getOrderDateStr,\n" +
            "\tdmh.orderArea,\n" +
            "\tdmh.salorEmp,\n" +
            "\tdmh.deliveryOrderDate AS deliveryOrderDateStr,\n" +
            "\tdmh.orderMaker,\n" +
            "\tdmh.remark\n" +
            "FROM\n" +
            "\tdesignmaterialhead dmh\n" +
            "WHERE\n" +
            "\tdmh.id = #{id}")
    DesignMaterialHead getSJbyId(Integer id);

    @Select("SELECT\n" +
            "\tdmhp.head_id,\n" +
            "\tdmhp.id,\n" +
            "\tdmhp.productName,\n" +
            "\tdmhp.productNo,\n" +
            "\tdmhp.needNum,\n" +
            "\tdmhp.drawingNo,\n" +
            "\tdmhp.productRoute,\n" +
            "\tdmhp.productTypeName,\n" +
            "\tdmhp.productTypeRoute,\n" +
            "\tdmhp.remark\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id\n" +
            " where dmh.customerNo = #{customerNo} " +
            "order by dmh.id asc " +
            "limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHeadProduct> getAllDMHP2(DesignMaterialHeadProduct pmhp);


    @Select("SELECT\n" +
            "\tdmhp.head_id,\n" +
            "\tdmhp.id,\n" +
            "\tdmhp.productName,\n" +
            "\tdmhp.productNo,\n" +
            "\tdmhp.needNum,\n" +
            "\tdmhp.drawingNo,\n" +
            "\tdmhp.productRoute,\n" +
            "\tdmhp.productTypeNo,\n" +
            "\tdmhp.productTypeName,\n" +
            "\tdmhp.productTypeRoute,\n" +
            "\tdmhp.remark\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id\n" +
            " where dmhp.head_id = #{headId} ")
    List<DesignMaterialHeadProduct> getHeadIdProductById(Integer headId);

    @Select("SELECT\n" +
            "\tdmhp.head_id,\n" +
            "\tdmhp.id,\n" +
            "\tdmhp.productName,\n" +
            "\tdmhp.productNo,\n" +
            "\tdmhp.needNum,\n" +
            "\tdmhp.drawingNo,\n" +
            "\tdmhp.productRoute,\n" +
            "\tdmhp.productTypeNo,\n" +
            "\tdmhp.productTypeName,\n" +
            "\tdmhp.productTypeRoute,\n" +
            "\tdmhp.remark\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id\n" +
            " where dmhp.id = #{headId} ")
    DesignMaterialHeadProduct getHeadIdProductByIdOne(Integer headId);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\thead_id,\n" +
            "\tproductName,\n" +
            "\tproductNo,\n" +
            "\tneedNum,\n" +
            "\tdrawingNo,\n" +
            "\tproductRoute,\n" +
            "\tproductTypeNo,\n" +
            "\tproductTypeName,\n" +
            "\tproductTypeRoute,\n" +
            "\tremark\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct where head_id = #{headId}")
    List<DesignMaterialHeadProduct> getProductListByHeadId(Integer headId);

    @Select("SELECT\n" +
            "\tdmhp.head_id,\n" +
            "\tdmhp.id,\n" +
            "\tdmhp.productName,\n" +
            "\tdmhp.productNo,\n" +
            "\tdmhp.needNum,\n" +
            "\tdmhp.drawingNo,\n" +
            "\tdmhp.productRoute,\n" +
            "\tdmhp.productTypeName,\n" +
            "\tdmhp.productTypeRoute,\n" +
            "\tdmhp.remark\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id" +
            " where dmh.id = #{id} \n" +
            "order by dmhp.id asc " +
            "limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHeadProduct> getAllDMHP(DesignMaterialHeadProduct pmhp);

    @Delete("delete from designmaterialheadproductitem where id = #{id} ")
    void deleteOrderItemByheadIdItem(Integer id);


    @Select("SELECT\n" +
            "\tdmhi.id,\n" +
            "\tdmhi.head_product_id,\n" +
            "\tdmhi.mateiralNo,\n" +
            "\tdmhi.materialName,\n" +
            "\tdmhi.materialSpeci,\n" +
            "\tdmhi.unit,\n" +
            "\tdmhi.num,\n" +
            "\tdmhi.useDeptId,\n" +
            "\tdmhi.useDeptName,\n" +
            "\tdmhi.mateiralStock,\n" +
            "\tdmhi.remark,\n" +
            "\tdmhi.isCanUse as isCanUse2\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            " JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            " JOIN designmaterialhead dm ON dm.id = dmh.head_id" +
            " where dmh.head_id = #{head_product_id} \n" +
            "ORDER BY\n" +
            "\tdmhi.id ASC limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHeadProductItem> getAllDMHPI(DesignMaterialHeadProductItem pmhp);

    @Select("select * from (SELECT\n" +
            "\tdmhi.id,\n" +
            "\tdmhi.head_product_id,\n" +
            "\tdmhi.mateiralNo,\n" +
            "\tdmhi.materialName,\n" +
            "\tdmhi.materialSpeci,\n" +
            "\tdmhi.unit,\n" +
            "\tdmhi.num,\n" +
            "\tdmhi.useDeptId,\n" +
            "\tdmhi.useDeptName,\n" +
            "\tdmhi.mateiralStock,\n" +
            "\tdmhi.remark,\n" +
            "\tdmhi.isCanUse AS isCanUse2\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            "JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            "JOIN designmaterialhead dm ON dm.id = dmh.head_id\n" +
            "WHERE\n" +
            "\t dmhi.head_product_id = #{dhp} and dmhi.mateiralNo <> '' order by dmhi.id asc \n" +
            ") as a\n" +
            "UNION all\n" +
            "\n" +
            "select * from (SELECT\n" +
            "\tdmhi.id,\n" +
            "\tdmhi.head_product_id,\n" +
            "\tdmhi.mateiralNo,\n" +
            "\tdmhi.materialName,\n" +
            "\tdmhi.materialSpeci,\n" +
            "\tdmhi.unit,\n" +
            "\tdmhi.num,\n" +
            "\tdmhi.useDeptId,\n" +
            "\tdmhi.useDeptName,\n" +
            "\tdmhi.mateiralStock,\n" +
            "\tdmhi.remark,\n" +
            "\tdmhi.isCanUse AS isCanUse2\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            "JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            "JOIN designmaterialhead dm ON dm.id = dmh.head_id\n" +
            "WHERE\n" +
            "\tdmhi.head_product_id = #{dhp} and dmhi.mateiralNo = '' order by dmhi.id asc ) as b ")
    List<DesignMaterialHeadProductItem> getHeadIdProductItemByIdBig(Integer dhp);


    @Select("select * from (SELECT\n" +
            "\tdmhi.id,\n" +
            "\tdmhi.head_product_id,\n" +
            "\tdmhi.mateiralNo,\n" +
            "\tdmhi.materialName,\n" +
            "\tdmhi.materialSpeci,\n" +
            "\tdmhi.unit,\n" +
            "\tdmhi.num,\n" +
            "\tdmhi.useDeptId,\n" +
            "\tdmhi.useDeptName,\n" +
            "\tdmhi.mateiralStock,\n" +
            "\tdmhi.remark,\n" +
            "\tdmhi.isCanUse AS isCanUse2\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            "JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            "JOIN designmaterialhead dm ON dm.id = dmh.head_id\n" +
            "WHERE\n" +
            "\t dm.id = #{dhp} and dmhi.mateiralNo <> '' order by dmhi.id asc \n" +
            ") as a\n" +
            "UNION all\n" +
            "\n" +
            "select * from (SELECT\n" +
            "\tdmhi.id,\n" +
            "\tdmhi.head_product_id,\n" +
            "\tdmhi.mateiralNo,\n" +
            "\tdmhi.materialName,\n" +
            "\tdmhi.materialSpeci,\n" +
            "\tdmhi.unit,\n" +
            "\tdmhi.num,\n" +
            "\tdmhi.useDeptId,\n" +
            "\tdmhi.useDeptName,\n" +
            "\tdmhi.mateiralStock,\n" +
            "\tdmhi.remark,\n" +
            "\tdmhi.isCanUse AS isCanUse2\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            "JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            "JOIN designmaterialhead dm ON dm.id = dmh.head_id\n" +
            "WHERE\n" +
            "\tdm.id = #{dhp} and dmhi.mateiralNo = '' order by dmhi.id asc ) as b ")
    List<DesignMaterialHeadProductItem> getHeadIdProductItemById(Integer dhp);

    @Select("SELECT\n" +
            "\tdmhi.id,\n" +
            "\tdmhi.head_product_id,\n" +
            "\tdmhi.mateiralNo,\n" +
            "\tdmhi.materialName,\n" +
            "\tdmhi.materialSpeci,\n" +
            "\tdmhi.unit,\n" +
            "\tdmhi.num,\n" +
            "\tdmhi.useDeptId,\n" +
            "\tdmhi.useDeptName,\n" +
            "\tdmhi.mateiralStock,\n" +
            "\tdmhi.remark,\n" +
            "\tdmhi.isCanUse AS isCanUse2\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            "JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            "WHERE\n" +
            "\t dmhi.head_product_id = #{dhp} order by dmhi.id asc   ")
    List<DesignMaterialHeadProductItem> getHeadIdProductItemById2(Integer dhp);


    @Select("SELECT\n" +
            "\tdmhi.id,\n" +
            "\tdmhi.head_product_id,\n" +
            "\tdmhi.mateiralNo,\n" +
            "\tdmhi.materialName,\n" +
            "\tdmhi.materialSpeci,\n" +
            "\tdmhi.unit,\n" +
            "\tdmhi.num,\n" +
            "\tdmhi.useDeptId,\n" +
            "\tdmhi.useDeptName,\n" +
            "\tdmhi.mateiralStock,\n" +
            "\tdmhi.remark,\n" +
            "\tdmhi.isCanUse as isCanUse2\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            "LEFT JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            "LEFT JOIN designmaterialhead dm ON dm.id = dmh.head_id\n" +
            " where dmhi.head_product_id = #{head_product_id} " +
            "ORDER BY\n" +
            "\tdmhi.id ASC limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHeadProductItem> getAllDMHPIButId(DesignMaterialHeadProductItem item);


    @Select("SELECT" +
            " count(*) " +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            " JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            " JOIN designmaterialhead dm ON dm.id = dmh.head_id where dmh.head_id = #{head_product_id}")
    int getAllDMHPICount(DesignMaterialHeadProductItem item);

    @Select("SELECT" +
            " count(*) " +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            "LEFT JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            "LEFT JOIN designmaterialhead dm ON dm.id = dmh.head_id" +
            " where dmhi.head_product_id = #{headId} ")
    int getAllDMHPIButIdCount(Integer headId);

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
            " where dmh.customerNo = #{customerNo}" +
            " order by dmhp.id asc " +
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
            " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id where dmh.id = #{id} ")
    int getAllDMHPCount(DesignMaterialHeadProduct product);

    @Select("SELECT" +
            " count(*) " +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id " +
            "  where dmh.customerNo = #{customerNo}")
    int getAllDMHPCount2(DesignMaterialHeadProduct product);

    @Select("SELECT\n" +
            "  count(*) " +
            "FROM\n" +
            "\tdesignmaterialhead dmh\n" +
            "LEFT JOIN employee ee ON ee.empno = dmh.salorEmp\n" +
            "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker" +
            " where dmh.orderMaker = #{loginName} ")
    int getAllDMHCount(DesignMaterialHead head);

    @Select("SELECT\n" +
            "  count(*) " +
            "FROM\n" +
            "\tdesignmaterialhead dmh\n" +
            "LEFT JOIN employee ee ON ee.empno = dmh.salorEmp\n" +
            "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker" +
            "  ")
    int getAllDMHCount22(DesignMaterialHead head);


    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadByCondition")
    List<DesignMaterialHead> queryOrderHeadByCondition(DesignMaterialHead orderHead);


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
            "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker where dmh.orderMaker = #{loginName} " +
            "and  dmh.customerNo = #{orderNo} ")
    List<DesignMaterialHead> queryOrderHeadBytoOrderNo(String loginName, String orderNo);


    @Select("SELECT" +
            " count(*) " +
            "FROM\n" +
            "\tdesignmaterialhead dmh\n" +
            "LEFT JOIN employee ee ON ee.empno = dmh.salorEmp\n" +
            "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker where dmh.orderMaker = #{loginName} " +
            "and  dmh.customerNo = #{orderNo} ")
    int queryOrderHeadBytoOrderNoCount(String loginName, String orderNo);


    @Select("SELECT\n" +
            "\tdmhp.head_id,\n" +
            "\tdmhp.id,\n" +
            "\tdmhp.productName,\n" +
            "\tdmhp.productNo,\n" +
            "\tdmhp.needNum,\n" +
            "\tdmhp.drawingNo,\n" +
            "\tdmhp.productTypeName,\n" +
            "\tdmhp.productTypeRoute,\n" +
            "\tdmhp.productRoute,\n" +
            "\tdmhp.remark\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id where dmh.orderMaker = #{empNo} and  dmh.customerNo = #{orderNo} ")
    List<DesignMaterialHeadProduct> queryOrderHeadProductOrderNoByCondition(String empNo, String orderNo);

    @Select("SELECT" +
            " count(*) " +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id where dmh.orderMaker = #{empNo} and  dmh.customerNo = #{orderNo} ")
    int queryOrderHeadProductOrderNoByConditionCount(String empNo, String orderNo);

    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadByConditionCount")
    int queryOrderHeadByConditionCount(DesignMaterialHead orderHead);

    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadProductByCondition")
    List<DesignMaterialHeadProduct> queryOrderHeadProductByCondition(DesignMaterialHeadProduct orderHead);

    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadProductByConditionCount")
    int queryOrderHeadProductByConditionCount(DesignMaterialHeadProduct orderHead);

    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadProductItemByCondition")
    List<DesignMaterialHeadProductItem> queryOrderHeadProductItemByCondition(DesignMaterialHeadProductItem orderHead);

    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadProductItemByConditionCount")
    int queryOrderHeadProductItemByConditionCount(DesignMaterialHeadProductItem orderHead);

    @Select("select count(*) from designmaterialhead where customerNo = #{customerNo} ")
    int getSJHeadByOrderNo(String customerNo);

    @Select("select count(*) from designmaterialhead where customerNo = #{customerNo} and id <> #{id}")
    int getSJHeadByOrderNoButId(String customerNo, Integer id);

    @Select("SELECT\n" +
            "\tcount(item.id)\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem item\n" +
            "LEFT JOIN designmaterialheadproduct pro ON item.head_product_id = pro.id\n" +
            "LEFT JOIN designmaterialhead head ON head.customerNo = pro.head_id\n" +
            "WHERE\n" +
            "\tpro.productNo = #{productNo}\n" +
            "AND head.customerNo = #{customerNo}\n" +
            "AND item.mateiralNo = #{mateiralNo} ")
    int getSJIHeadByOrderNo(String customerNo, String productNo, String mateiralNo);


    @Select("SELECT\n" +
            "\tcount(item.id)\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem item\n" +
            "LEFT JOIN designmaterialheadproduct pro ON item.head_product_id = pro.id\n" +
            "LEFT JOIN designmaterialhead head ON head.customerNo = pro.head_id\n" +
            "WHERE\n" +
            " item.mateiralNo = #{mateiralNo} and item.id <> #{id} and item.head_product_id = #{headId} ")
    int getSJIHeadByOrderNoButId(String mateiralNo, Integer id, Integer headId);

    @Delete("delete from designmaterialhead where id = #{id}")
    void deleteOrderItemByheadId(Integer id);

    @Delete("delete from designmaterialheadproduct where id = #{id} ")
    void deleteOrderItemHByheadId(Integer id);

    @Select("SELECT\n" +
            "\tpro.id\n" +
            "FROM\n" +
            "\tdesignmaterialhead head \n" +
            "left JOIN designmaterialheadproduct pro ON head.id = pro.head_id\n" +
            "WHERE\n" +
            "\tpro.productNo = #{productNo}\n" +
            "AND head.customerNo = #{customerNo}\n" +
            "LIMIT 1")
    Integer getHeadIdByCustomerNoAndMaterialNo(String customerNo, String productNo);


    @Delete("DELETE\n" +
            "FROM\n" +
            "\tdesignmaterialheadproductitem\n" +
            "WHERE\n" +
            "\thead_product_id = #{head_id}\n" +
            "AND isLinshi = 1")
    void deleteItemByLinshi(Integer head_id);


    @Select("select head_product_id from designmaterialheadproductitem where id = #{itemId}")
    int getHeadIdByMaterialNoAndId(Integer itemId);

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
            "\tcount(phh.id) \n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct ph \n" +
            " join designmaterialhead phh on ph.head_id = phh.id\n" +
            "WHERE\n" +
            "\tph.productNo = #{productNo}\n" +
            "  and phh.customerNo = #{customerNo} and ph.id <> #{id} limit 1")
    Integer getSJHeadByProductNoButId(String productNo, String customerNo, Integer id);

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
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveSJHeadDateToMysql(DesignMaterialHead dmh);

    @Update("update designmaterialhead " +
            "set customerNo = #{customerNo}," +
            "getOrderDate = #{getOrderDateStr}," +
            "orderArea = #{orderArea}," +
            "salorEmp = #{salorEmp}," +
            "deliveryOrderDate = #{deliveryOrderDateStr}," +
            "orderMaker = #{orderMaker}," +
            "remark = #{remark}" +
            " where id = #{id}")
    void updateSJHeadDateToMysql(DesignMaterialHead dmh);

    @Insert("insert into designmaterialheadproduct (head_id,\n" +
            "\tproductName,\n" +
            "\tproductNo,\n" +
            "\tneedNum,\n" +
            "\tdrawingNo,\n" +
            "\tproductRoute,\n" +
            "\tproductTypeNo,\n" +
            "\tproductTypeName,\n" +
            "\tproductTypeRoute,\n" +
            "\tremark)" +
            " values " +
            " (" +
            "#{head_id},\n" +
            "\t#{productName},\n" +
            "\t#{productNo},\n" +
            "\t#{needNum},\n" +
            "\t#{drawingNo},\n" +
            "\t#{productRoute},\n" +
            "\t#{productTypeNo},\n" +
            "\t#{productTypeName},\n" +
            "\t#{productTypeRoute},\n" +
            "\t#{remark}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveSJHeadPDateToMysql(DesignMaterialHeadProduct dmhp);

    @Update("update designmaterialheadproduct " +
            "set productName = #{productName} ," +
            " productNo = #{productNo}," +
            " needNum = #{needNum}," +
            " drawingNo = #{drawingNo}," +
            " productRoute = #{productRoute}," +
            " productTypeNo = #{productTypeNo}," +
            " productTypeName = #{productTypeName}," +
            " productTypeRoute = #{productTypeRoute}," +
            " remark = #{remark} where id = #{id}")
    void updateSJHeadPDateToMysql(DesignMaterialHeadProduct dh);


    @Insert("insert into designmaterialheadproductitem (" +
            "head_product_id,\n" +
            "\tmateiralNo,\n" +
            "\tmaterialName,\n" +
            "\tmaterialSpeci,\n" +
            "\tunit,\n" +
            "\tnum,\n" +
            "\tuseDeptName,\n" +
            "\tmateiralStock,\n" +
            "\tremark,\n" +
            "\tisCanUse) " +
            "values (" +
            " #{head_product_id},\n" +
            "\t#{mateiralNo},\n" +
            "\t#{materialName},\n" +
            "\t#{materialSpeci},\n" +
            "\t#{unit},\n" +
            "\t#{num},\n" +
            "\t#{useDeptName},\n" +
            "\t#{mateiralStock},\n" +
            "\t#{remark},\n" +
            "\t#{isCanUse2}) ")
    void saveSJIHeadDateToMysql(DesignMaterialHeadProductItem item);


    @Insert("update designmaterialheadproductitem set " +
            " mateiralNo = #{mateiralNo}," +
            " materialName = #{materialName}," +
            " materialSpeci = #{materialSpeci}," +
            " unit = #{unit}," +
            " mateiralStock = #{mateiralStock}," +
            " num = #{num}," +
            " useDeptName = #{useDeptName}," +
            " remark = #{remark}" +
            " where id = #{id} ")
    void updateSJIHeadDateToMysql(DesignMaterialHeadProductItem item);


    class OrderDaoProvider {


        public String queryOrderHeadByConditionCount(DesignMaterialHead orderHead) {
            StringBuilder sb = new StringBuilder("SELECT" +
                    " count(*) " +
                    "FROM\n" +
                    "\tdesignmaterialhead dmh\n" +
                    "LEFT JOIN employee ee ON ee.empno = dmh.salorEmp\n" +
                    "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker  where dmh.orderMaker = #{loginName} ");

            if (orderHead.getCustomerNoList() != null && orderHead.getCustomerNoList().size() > 0) {
                if (orderHead.getCustomerNoList().size() == 1) {
                    sb.append(" and dmh.customerNo in  ('" + orderHead.getCustomerNoList().get(0) + "')");
                } else if (orderHead.getCustomerNoList().size() >= 2) {
                    sb.append("  and dmh.customerNo in (");
                    for (int i = 0; i < orderHead.getCustomerNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getCustomerNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getCustomerNoList().get(orderHead.getCustomerNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getGetOrderDateStr() != null && orderHead.getGetOrderDateStr().length() > 5) {
                sb.append(" and date_format(dmh.getOrderDate, '%Y-%m-%d') = #{getOrderDateStr}");
            }

            if (orderHead.getDeliveryOrderDateStr() != null && orderHead.getDeliveryOrderDateStr().length() > 5) {
                sb.append(" and date_format(dmh.deliveryOrderDate, '%Y-%m-%d') = #{deliveryOrderDateStr}");
            }

            if (orderHead.getOrderAreaList() != null && orderHead.getOrderAreaList().size() > 0) {
                if (orderHead.getOrderAreaList().size() == 1) {
                    sb.append(" and dmh.orderArea in  ('" + orderHead.getOrderAreaList().get(0) + "')");
                } else if (orderHead.getOrderAreaList().size() >= 2) {
                    sb.append("  and dmh.orderArea in (");
                    for (int i = 0; i < orderHead.getOrderAreaList().size() - 1; i++) {
                        sb.append("'" + orderHead.getOrderAreaList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getOrderAreaList().get(orderHead.getOrderAreaList().size() - 1) + "')");
                }
            }

            if (orderHead.getSalorEmpList() != null && orderHead.getSalorEmpList().size() > 0) {
                if (orderHead.getSalorEmpList().size() == 1) {
                    sb.append(" and dmh.salorEmp in  ('" + orderHead.getSalorEmpList().get(0) + "')");
                } else if (orderHead.getSalorEmpList().size() >= 2) {
                    sb.append("  and dmh.salorEmp in (");
                    for (int i = 0; i < orderHead.getSalorEmpList().size() - 1; i++) {
                        sb.append("'" + orderHead.getSalorEmpList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getSalorEmpList().get(orderHead.getSalorEmpList().size() - 1) + "')");
                }
            }

            if (orderHead.getMakerList() != null && orderHead.getMakerList().size() > 0) {
                if (orderHead.getMakerList().size() == 1) {
                    sb.append(" and dmh.orderMaker in  ('" + orderHead.getMakerList().get(0) + "')");
                } else if (orderHead.getMakerList().size() >= 2) {
                    sb.append("  and dmh.orderMaker in (");
                    for (int i = 0; i < orderHead.getMakerList().size() - 1; i++) {
                        sb.append("'" + orderHead.getMakerList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getMakerList().get(orderHead.getMakerList().size() - 1) + "')");
                }
            }

            return sb.toString();
        }

        public String queryOrderHeadByCondition(DesignMaterialHead orderHead) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
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
                    "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker where dmh.orderMaker = #{loginName} ");

            if (orderHead.getCustomerNoList() != null && orderHead.getCustomerNoList().size() > 0) {
                if (orderHead.getCustomerNoList().size() == 1) {
                    sb.append(" and dmh.customerNo in  ('" + orderHead.getCustomerNoList().get(0) + "')");
                } else if (orderHead.getCustomerNoList().size() >= 2) {
                    sb.append("  and dmh.customerNo in (");
                    for (int i = 0; i < orderHead.getCustomerNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getCustomerNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getCustomerNoList().get(orderHead.getCustomerNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getGetOrderDateStr() != null && orderHead.getGetOrderDateStr().length() > 5) {
                sb.append(" and date_format(dmh.getOrderDate, '%Y-%m-%d') = #{getOrderDateStr}");
            }

            if (orderHead.getDeliveryOrderDateStr() != null && orderHead.getDeliveryOrderDateStr().length() > 5) {
                sb.append(" and date_format(dmh.deliveryOrderDate, '%Y-%m-%d') = #{deliveryOrderDateStr}");
            }

            if (orderHead.getOrderAreaList() != null && orderHead.getOrderAreaList().size() > 0) {
                if (orderHead.getOrderAreaList().size() == 1) {
                    sb.append(" and dmh.orderArea in  ('" + orderHead.getOrderAreaList().get(0) + "')");
                } else if (orderHead.getOrderAreaList().size() >= 2) {
                    sb.append("  and dmh.orderArea in (");
                    for (int i = 0; i < orderHead.getOrderAreaList().size() - 1; i++) {
                        sb.append("'" + orderHead.getOrderAreaList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getOrderAreaList().get(orderHead.getOrderAreaList().size() - 1) + "')");
                }
            }

            if (orderHead.getSalorEmpList() != null && orderHead.getSalorEmpList().size() > 0) {
                if (orderHead.getSalorEmpList().size() == 1) {
                    sb.append(" and dmh.salorEmp in  ('" + orderHead.getSalorEmpList().get(0) + "')");
                } else if (orderHead.getSalorEmpList().size() >= 2) {
                    sb.append("  and dmh.salorEmp in (");
                    for (int i = 0; i < orderHead.getSalorEmpList().size() - 1; i++) {
                        sb.append("'" + orderHead.getSalorEmpList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getSalorEmpList().get(orderHead.getSalorEmpList().size() - 1) + "')");
                }
            }

            if (orderHead.getMakerList() != null && orderHead.getMakerList().size() > 0) {
                if (orderHead.getMakerList().size() == 1) {
                    sb.append(" and dmh.orderMaker in  ('" + orderHead.getMakerList().get(0) + "')");
                } else if (orderHead.getMakerList().size() >= 2) {
                    sb.append("  and dmh.orderMaker in (");
                    for (int i = 0; i < orderHead.getMakerList().size() - 1; i++) {
                        sb.append("'" + orderHead.getMakerList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getMakerList().get(orderHead.getMakerList().size() - 1) + "')");
                }
            }

            if (orderHead.getSortMethod() != null && !"undefined".equals(orderHead.getSortMethod()) && !"undefined".equals(orderHead.getSortByName()) && orderHead.getSortByName() != null) {
                if ("orderMaker".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmh.orderMaker ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("customerNo".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmh.customerNo ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("getOrderDate".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmh.getOrderDate ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("orderArea".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmh.orderArea ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("salorEmp".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmh.salorEmp ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deliveryOrderDate".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmh.deliveryOrderDate ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("remark".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmh.remark ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append("  order by dmh.id desc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryOrderHeadProductByCondition(DesignMaterialHeadProduct orderHead) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tdmhp.head_id,\n" +
                    "\tdmhp.id,\n" +
                    "\tdmhp.productName,\n" +
                    "\tdmhp.productNo,\n" +
                    "\tdmhp.needNum,\n" +
                    "\tdmhp.drawingNo,\n" +
                    "\tdmhp.productRoute,\n" +
                    "\tdmhp.productTypeName,\n" +
                    "\tdmhp.productTypeRoute,\n" +
                    "\tdmhp.remark\n" +
                    "FROM\n" +
                    "\tdesignmaterialheadproduct dmhp\n" +
                    " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id where 1=1 ");

            if (orderHead.getHead_id() != null) {
                sb.append("  and dmh.id = #{head_id} ");
            }

            if (orderHead.getProductNameList() != null && orderHead.getProductNameList().size() > 0) {
                if (orderHead.getProductNameList().size() == 1) {
                    sb.append(" and dmhp.productName in  ('" + orderHead.getProductNameList().get(0) + "')");
                } else if (orderHead.getProductNameList().size() >= 2) {
                    sb.append("  and dmhp.productName in (");
                    for (int i = 0; i < orderHead.getProductNameList().size() - 1; i++) {
                        sb.append("'" + orderHead.getProductNameList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getProductNameList().get(orderHead.getProductNameList().size() - 1) + "')");
                }
            }


            if (orderHead.getProductNoList() != null && orderHead.getProductNoList().size() > 0) {
                if (orderHead.getProductNoList().size() == 1) {
                    sb.append(" and dmhp.productNo in  ('" + orderHead.getProductNoList().get(0) + "')");
                } else if (orderHead.getProductNoList().size() >= 2) {
                    sb.append("  and dmhp.productNo in (");
                    for (int i = 0; i < orderHead.getProductNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getProductNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getProductNoList().get(orderHead.getProductNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getDrawingNoList() != null && orderHead.getDrawingNoList().size() > 0) {
                if (orderHead.getDrawingNoList().size() == 1) {
                    sb.append(" and dmhp.drawingNo in  ('" + orderHead.getDrawingNoList().get(0) + "')");
                } else if (orderHead.getDrawingNoList().size() >= 2) {
                    sb.append("  and dmhp.drawingNo in (");
                    for (int i = 0; i < orderHead.getDrawingNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getDrawingNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getDrawingNoList().get(orderHead.getDrawingNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getCustomerNoList() != null && orderHead.getCustomerNoList().size() > 0) {
                if (orderHead.getCustomerNoList().size() == 1) {
                    sb.append(" and dmh.customerNo in  ('" + orderHead.getCustomerNoList().get(0) + "')");
                } else if (orderHead.getCustomerNoList().size() >= 2) {
                    sb.append("  and dmh.customerNo in (");
                    for (int i = 0; i < orderHead.getCustomerNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getCustomerNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getCustomerNoList().get(orderHead.getCustomerNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getSortMethod() != null && !"undefined".equals(orderHead.getSortMethod()) && !"undefined".equals(orderHead.getSortByName()) && orderHead.getSortByName() != null) {
                if ("productName".equals(orderHead.getSortByName())) {
                    sb.append(" order  by dmhp.productName ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("productNo".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhp.productNo ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("drawingNo".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhp.drawingNo ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("needNum".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhp.needNum ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("productRoute".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhp.productRoute ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("remark".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhp.remark ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append("  ORDER BY\n" +
                        "\tdmhp.id ASC");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryOrderHeadProductByConditionCount(DesignMaterialHeadProduct orderHead) {
            StringBuilder sb = new StringBuilder("SELECT" +
                    " count(*) " +
                    "FROM\n" +
                    "\tdesignmaterialheadproduct dmhp\n" +
                    " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id  where 1=1 ");

            if (orderHead.getHead_id() != null) {
                sb.append("  and dmh.id = #{head_id} ");
            }

            if (orderHead.getProductNameList() != null && orderHead.getProductNameList().size() > 0) {
                if (orderHead.getProductNameList().size() == 1) {
                    sb.append(" and dmhp.productName in  ('" + orderHead.getProductNameList().get(0) + "')");
                } else if (orderHead.getProductNameList().size() >= 2) {
                    sb.append("  and dmhp.productName in (");
                    for (int i = 0; i < orderHead.getProductNameList().size() - 1; i++) {
                        sb.append("'" + orderHead.getProductNameList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getProductNameList().get(orderHead.getProductNameList().size() - 1) + "')");
                }
            }


            if (orderHead.getProductNoList() != null && orderHead.getProductNoList().size() > 0) {
                if (orderHead.getProductNoList().size() == 1) {
                    sb.append(" and dmhp.productNo in  ('" + orderHead.getProductNoList().get(0) + "')");
                } else if (orderHead.getProductNoList().size() >= 2) {
                    sb.append("  and dmhp.productNo in (");
                    for (int i = 0; i < orderHead.getProductNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getProductNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getProductNoList().get(orderHead.getProductNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getDrawingNoList() != null && orderHead.getDrawingNoList().size() > 0) {
                if (orderHead.getDrawingNoList().size() == 1) {
                    sb.append(" and dmhp.drawingNo in  ('" + orderHead.getDrawingNoList().get(0) + "')");
                } else if (orderHead.getDrawingNoList().size() >= 2) {
                    sb.append("  and dmhp.drawingNo in (");
                    for (int i = 0; i < orderHead.getDrawingNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getDrawingNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getDrawingNoList().get(orderHead.getDrawingNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getCustomerNoList() != null && orderHead.getCustomerNoList().size() > 0) {
                if (orderHead.getCustomerNoList().size() == 1) {
                    sb.append(" and dmh.customerNo in  ('" + orderHead.getCustomerNoList().get(0) + "')");
                } else if (orderHead.getCustomerNoList().size() >= 2) {
                    sb.append("  and dmh.customerNo in (");
                    for (int i = 0; i < orderHead.getCustomerNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getCustomerNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getCustomerNoList().get(orderHead.getCustomerNoList().size() - 1) + "')");
                }
            }
            return sb.toString();
        }

        public String queryOrderHeadProductItemByCondition(DesignMaterialHeadProductItem orderHead) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tdmhi.id,\n" +
                    "\tdmhi.head_product_id,\n" +
                    "\tdmhi.mateiralNo,\n" +
                    "\tdmhi.materialName,\n" +
                    "\tdmhi.materialSpeci,\n" +
                    "\tdmhi.unit,\n" +
                    "\tdmhi.num,\n" +
                    "\tdmhi.useDeptId,\n" +
                    "\tdmhi.useDeptName,\n" +
                    "\tdmhi.mateiralStock,\n" +
                    "\tdmhi.remark,\n" +
                    "\tdmhi.isCanUse AS isCanUse2\n" +
                    "FROM\n" +
                    "\tdesignmaterialheadproductitem dmhi\n" +
                    " left JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
                    " left JOIN designmaterialhead dm ON dm.id = dmh.head_id where 1=1 ");

            if (orderHead.getMaterialNameList2() != null && orderHead.getMaterialNameList2().size() > 0) {
                if (orderHead.getMaterialNameList2().size() == 1) {
                    sb.append(" and dmhi.materialName in  ('" + orderHead.getMaterialNameList2().get(0) + "')");
                } else if (orderHead.getMaterialNameList2().size() >= 2) {
                    sb.append("  and dmhi.materialName in (");
                    for (int i = 0; i < orderHead.getMaterialNameList2().size() - 1; i++) {
                        sb.append("'" + orderHead.getMaterialNameList2().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getMaterialNameList2().get(orderHead.getMaterialNameList2().size() - 1) + "')");
                }
            }


            if (orderHead.getMateiralNoList() != null && orderHead.getMateiralNoList().size() > 0) {
                if (orderHead.getMateiralNoList().size() == 1) {
                    sb.append(" and dmhi.mateiralNo in  ('" + orderHead.getMateiralNoList().get(0) + "')");
                } else if (orderHead.getMateiralNoList().size() >= 2) {
                    sb.append("  and dmhi.mateiralNo in (");
                    for (int i = 0; i < orderHead.getMateiralNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getMateiralNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getMateiralNoList().get(orderHead.getMateiralNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getMaterialSpeciList2() != null && orderHead.getMaterialSpeciList2().size() > 0) {
                if (orderHead.getMaterialSpeciList2().size() == 1) {
                    sb.append(" and dmhi.materialSpeci in  ('" + orderHead.getMaterialSpeciList2().get(0) + "')");
                } else if (orderHead.getMaterialSpeciList2().size() >= 2) {
                    sb.append("  and dmhi.materialSpeci in (");
                    for (int i = 0; i < orderHead.getMaterialSpeciList2().size() - 1; i++) {
                        sb.append("'" + orderHead.getMaterialSpeciList2().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getMaterialSpeciList2().get(orderHead.getMaterialSpeciList2().size() - 1) + "')");
                }
            }

            if (orderHead.getCustomerNoList() != null && orderHead.getCustomerNoList().size() > 0) {
                if (orderHead.getCustomerNoList().size() == 1) {
                    sb.append(" and dm.customerNo in  ('" + orderHead.getCustomerNoList().get(0) + "')");
                } else if (orderHead.getCustomerNoList().size() >= 2) {
                    sb.append("  and dm.customerNo in (");
                    for (int i = 0; i < orderHead.getCustomerNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getCustomerNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getCustomerNoList().get(orderHead.getCustomerNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getProductNoList() != null && orderHead.getProductNoList().size() > 0) {
                if (orderHead.getProductNoList().size() == 1) {
                    sb.append(" and dmh.productNo in  ('" + orderHead.getProductNoList().get(0) + "')");
                } else if (orderHead.getProductNoList().size() >= 2) {
                    sb.append("  and dmh.productNo in (");
                    for (int i = 0; i < orderHead.getProductNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getProductNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getProductNoList().get(orderHead.getProductNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getSortMethod() != null && !"undefined".equals(orderHead.getSortMethod()) && !"undefined".equals(orderHead.getSortByName()) && orderHead.getSortByName() != null) {
                if ("mateiralNo".equals(orderHead.getSortByName())) {
                    sb.append(" order  by dmhi.mateiralNo ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("materialName".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhi.materialName ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("materialSpeci".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhi.materialSpeci ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("unit".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhi.unit ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("mateiralStock".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhi.mateiralStock ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("num".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhi.num ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("useDeptName".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhi.useDeptName ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("isCanUse".equals(orderHead.getSortByName())) {
                    sb.append(" order by dmhi.isCanUse2 ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append("  ORDER BY\n" +
                        "\tdmhi.id ASC\n");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryOrderHeadProductItemByConditionCount(DesignMaterialHeadProductItem orderHead) {
            StringBuilder sb = new StringBuilder("SELECT" +
                    " count(*) " +
                    "FROM\n" +
                    "\tdesignmaterialheadproductitem dmhi\n" +
                    " left JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
                    " left JOIN designmaterialhead dm ON dm.id = dmh.head_id where 1=1 ");

            if (orderHead.getMaterialNameList2() != null && orderHead.getMaterialNameList2().size() > 0) {
                if (orderHead.getMaterialNameList2().size() == 1) {
                    sb.append(" and dmhi.materialName in  ('" + orderHead.getMaterialNameList2().get(0) + "')");
                } else if (orderHead.getMaterialNameList2().size() >= 2) {
                    sb.append("  and dmhi.materialName in (");
                    for (int i = 0; i < orderHead.getMaterialNameList2().size() - 1; i++) {
                        sb.append("'" + orderHead.getMaterialNameList2().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getMaterialNameList2().get(orderHead.getMaterialNameList2().size() - 1) + "')");
                }
            }


            if (orderHead.getMateiralNoList() != null && orderHead.getMateiralNoList().size() > 0) {
                if (orderHead.getMateiralNoList().size() == 1) {
                    sb.append(" and dmhi.mateiralNo in  ('" + orderHead.getMateiralNoList().get(0) + "')");
                } else if (orderHead.getMateiralNoList().size() >= 2) {
                    sb.append("  and dmhi.mateiralNo in (");
                    for (int i = 0; i < orderHead.getMateiralNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getMateiralNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getMateiralNoList().get(orderHead.getMateiralNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getMaterialSpeciList2() != null && orderHead.getMaterialSpeciList2().size() > 0) {
                if (orderHead.getMaterialSpeciList2().size() == 1) {
                    sb.append(" and dmhi.materialSpeci in  ('" + orderHead.getMaterialSpeciList2().get(0) + "')");
                } else if (orderHead.getMaterialSpeciList2().size() >= 2) {
                    sb.append("  and dmhi.materialSpeci in (");
                    for (int i = 0; i < orderHead.getMaterialSpeciList2().size() - 1; i++) {
                        sb.append("'" + orderHead.getMaterialSpeciList2().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getMaterialSpeciList2().get(orderHead.getMaterialSpeciList2().size() - 1) + "')");
                }
            }

            if (orderHead.getCustomerNoList() != null && orderHead.getCustomerNoList().size() > 0) {
                if (orderHead.getCustomerNoList().size() == 1) {
                    sb.append(" and dm.customerNo in  ('" + orderHead.getCustomerNoList().get(0) + "')");
                } else if (orderHead.getCustomerNoList().size() >= 2) {
                    sb.append("  and dm.customerNo in (");
                    for (int i = 0; i < orderHead.getCustomerNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getCustomerNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getCustomerNoList().get(orderHead.getCustomerNoList().size() - 1) + "')");
                }
            }

            if (orderHead.getProductNoList() != null && orderHead.getProductNoList().size() > 0) {
                if (orderHead.getProductNoList().size() == 1) {
                    sb.append(" and dmh.productNo in  ('" + orderHead.getProductNoList().get(0) + "')");
                } else if (orderHead.getProductNoList().size() >= 2) {
                    sb.append("  and dmh.productNo in (");
                    for (int i = 0; i < orderHead.getProductNoList().size() - 1; i++) {
                        sb.append("'" + orderHead.getProductNoList().get(i) + "'" + ",");
                    }
                    sb.append("'" + orderHead.getProductNoList().get(orderHead.getProductNoList().size() - 1) + "')");
                }
            }

            return sb.toString();
        }
    }
}



