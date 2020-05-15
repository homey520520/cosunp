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
            " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id\n" +
            " where dmh.customerNo = #{customerNo} " +
            "order by dmh.customerNo desc,dmhp.productNo asc " +
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
            "\tdmhp.remark\n" +
            "FROM\n" +
            "\tdesignmaterialheadproduct dmhp\n" +
            "LEFT JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id\n" +
            "order by dmh.customerNo desc,dmhp.productNo asc " +
            "limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHeadProduct> getAllDMHP(DesignMaterialHeadProduct pmhp);


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
            "ORDER BY\n" +
            "\tdm.customerNo DESC,\n" +
            "\tdmh.productNo ASC,\n" +
            "\tdmhi.mateiralNo ASC limit #{currentPageTotalNum},#{pageSize}")
    List<DesignMaterialHeadProductItem> getAllDMHPI(DesignMaterialHeadProductItem pmhp);


    @Select("SELECT" +
            " count(*) " +
            "FROM\n" +
            "\tdesignmaterialheadproductitem dmhi\n" +
            "LEFT JOIN designmaterialheadproduct dmh ON dmhi.head_product_id = dmh.id\n" +
            "LEFT JOIN designmaterialhead dm ON dm.id = dmh.head_id")
    int getAllDMHPICount();

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
            " order by dmh.customerNo desc,dmhp.productNo asc " +
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
            "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker")
    int getAllDMHCount();


    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadByCondition")
    List<DesignMaterialHead> queryOrderHeadByCondition(DesignMaterialHead orderHead);

    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadByConditionCount")
    int queryOrderHeadByConditionCount(DesignMaterialHead orderHead);

    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadProductByCondition")
    List<DesignMaterialHeadProduct> queryOrderHeadProductByCondition(DesignMaterialHeadProduct orderHead);

    @SelectProvider(type = DesignMapper.OrderDaoProvider.class, method = "queryOrderHeadProductByConditionCount")
    int queryOrderHeadProductByConditionCount(DesignMaterialHeadProduct orderHead);

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
    int getHeadIdByCustomerNoAndMaterialNo(String customerNo, String productNo);

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

    @Update("update designmaterialheadproduct " +
            "set productName = #{productName} ," +
            " productNo = #{productNo}," +
            " needNum = #{needNum}," +
            " drawingNo = #{drawingNo}," +
            " productRoute = #{productRoute}," +
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


    class OrderDaoProvider {


        public String queryOrderHeadByConditionCount(DesignMaterialHead orderHead) {
            StringBuilder sb = new StringBuilder("SELECT" +
                    " count(*) " +
                    "FROM\n" +
                    "\tdesignmaterialhead dmh\n" +
                    "LEFT JOIN employee ee ON ee.empno = dmh.salorEmp\n" +
                    "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker where 1=1");

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

            if (orderHead.getGetOrderDateStr() != null && orderHead.getGetOrderDateStr().length() > 0) {
                sb.append(" and date_format(dmh.getOrderDate, '%Y-%m-%d') = #{getOrderDateStr}");
            }

            if (orderHead.getDeliveryOrderDateStr() != null && orderHead.getDeliveryOrderDateStr().length() > 0) {
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
                    "LEFT JOIN employee eee ON eee.empno = dmh.orderMaker where 1=1");

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

            if (orderHead.getGetOrderDateStr() != null && orderHead.getGetOrderDateStr().length() > 0) {
                sb.append(" and date_format(dmh.getOrderDate, '%Y-%m-%d') = #{getOrderDateStr}");
            }

            if (orderHead.getDeliveryOrderDateStr() != null && orderHead.getDeliveryOrderDateStr().length() > 0) {
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
                    "\tdmhp.remark\n" +
                    "FROM\n" +
                    "\tdesignmaterialheadproduct dmhp\n" +
                    " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id");

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
                        "\tdmh.customerNo DESC,\n" +
                        "\tdmhp.productNo ASC");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryOrderHeadProductByConditionCount(DesignMaterialHeadProduct orderHead) {
            StringBuilder sb = new StringBuilder("SELECT" +
                    " count(*) " +
                    "FROM\n" +
                    "\tdesignmaterialheadproduct dmhp\n" +
                    " JOIN designmaterialhead dmh ON dmhp.head_id = dmh.id");

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
    }
}



