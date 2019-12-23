package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.OrderHead;
import com.cosun.cosunp.entity.OrderItem;
import com.cosun.cosunp.entity.OrderItemAppend;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderMapper {


    @Select("SELECT\n" +
            "\te.empno,e.`name`\n" +
            "FROM\n" +
            "\temployee e\n" +
            "LEFT JOIN dept t ON t.id = e.deptId\n" +
            "WHERE\n" +
            "\tt.deptname IN (\n" +
            "\t\t'销售中心',\n" +
            "\t\t'项目中心'\n" +
            "\t)")
    List<Employee> findAllSalor();


    @Insert(" INSERT INTO orderhead (\n" +
            "\t\tsingleOrProject,\n" +
            "\t\torderNo,\n" +
            "\t\tproductTotalName,\n" +
            "\t\torderTime,\n" +
            "\t\torderSetNum,\n" +
            "\t\tSalorNo,\n" +
            "state" +
            "\t)\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{singleOrProject},\n" +
            "\t\t#{orderNo},\n" +
            "\t\t#{productTotalName},\n" +
            "\t\t#{orderTimeStr},\n" +
            "\t\t#{orderSetNum},\n" +
            "\t\t#{SalorNo},\n" +
            "\t\t#{state}\n" +
            "\t) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addOrderHeadByBean(OrderHead orderHead);

    @Insert(" INSERT INTO orderitem (\n" +
            "\torderHeadId,\n" +
            "\tneedNum,\n" +
            "\titemCreateTime,\n" +
            "\tproductBigType,\n" +
            "\tproductName,\n" +
            "\tproductMainShape,\n" +
            "\tnewFinishProudNo,\n" +
            "\tproductSize,\n" +
            "\tedgeHightSize,\n" +
            "\titemDeliverTime,\n" +
            "\tmainMateriAndArt,\n" +
            "\tbackInstallSelect,\n" +
            "\telectMateriNeeds,\n" +
            "\tinstallTransfBacking,\n" +
            "\totherRemark\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{orderHeadId},\n" +
            "\t\t#{needNum},\n" +
            "\t\t#{itemCreateTimeStr},\n" +
            "\t\t#{productBigType},\n" +
            "\t\t#{productName},\n" +
            "\t\t#{productMainShape},\n" +
            "\t\t#{newFinishProudNo},\n" +
            "\t\t#{productSize},\n" +
            "\t\t#{edgeHightSize},\n" +
            "\t\t#{itemDeliverTimeStr},\n" +
            "\t\t#{mainMateriAndArt},\n" +
            "\t\t#{backInstallSelect},\n" +
            "\t\t#{electMateriNeeds},\n" +
            "\t\t#{installTransfBacking},\n" +
            "\t\t#{otherRemark}\n" +
            "\t) ")
    @Options(useGeneratedKeys = true, keyProperty = "itemId", keyColumn = "id")
    void addOrderItemByBean(OrderHead orderHead);


    @Insert(" INSERT INTO orderitem (\n" +
            "\torderHeadId,\n" +
            "\titemCreateTime,\n" +
            "\tneedNum,\n" +
            "\tproductName,\n" +
            "\tproductBigType,\n" +
            "\tproductMainShape,\n" +
            "\tnewFinishProudNo,\n" +
            "\tproductSize,\n" +
            "\tedgeHightSize,\n" +
            "\tmainMateriAndArt,\n" +
            "\tbackInstallSelect,\n" +
            "\telectMateriNeeds,\n" +
            "\tinstallTransfBacking,\n" +
            "\totherRemark," +
            "itemDeliverTime\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t#{orderHeadId},\n" +
            "\t\t#{itemCreateTimeStr},\n" +
            "\t\t#{needNum},\n" +
            "\t\t#{productName},\n" +
            "\t\t#{productBigType},\n" +
            "\t\t#{productMainShape},\n" +
            "\t\t#{newFinishProudNo},\n" +
            "\t\t#{productSize},\n" +
            "\t\t#{edgeHightSize},\n" +
            "\t\t#{mainMateriAndArt},\n" +
            "\t\t#{backInstallSelect},\n" +
            "\t\t#{electMateriNeeds},\n" +
            "\t\t#{installTransfBacking},\n" +
            "\t\t#{otherRemark}," +
            "\n#{itemDeliverTimeStr}" +
            "\t) ")
    @Options(useGeneratedKeys = true, keyProperty = "itemId", keyColumn = "id")
    void addOrderItemByItemBean(OrderItem orderItem);

    @Insert("INSERT INTO orderitemappend (\n" +
            "\theadId,\n" +
            "\torderNo,\n" +
            "\tfileName,\n" +
            "\turlName\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\t" +
            "#{headId},\n" +
            "\t#{orderNo},\n" +
            "\t#{fileName},\n" +
            "\t#{urlName})")
    void saveOrderItemAppend(OrderItemAppend orderItemAppend);

    @Select("SELECT\n" +
            "\toh.id,\n" +
            "\tuo.engName,\n" +
            "\toh.orderNo\n" +
            "FROM\n" +
            "\torderhead oh\n" +
            "LEFT JOIN userinfo uo ON oh.SalorNo = uo.empno\n" +
            "WHERE\n" +
            "\torderNo = #{orderNo} ")
    OrderHead getOldHeadByOrderNo2(String orderNo);

    @Select("select * from orderhead where orderNo = #{orderNo} ")
    OrderHead getOldHeadByOrderNo(String orderNo);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\torderHeadId,\n" +
            "\tproductBigType,\n" +
            "\tproductMainShape,\n" +
            "\tnewFinishProudNo,\n" +
            "\tproductSize,\n" +
            "\tedgeHightSize,\n" +
            "\tmainMateriAndArt,\n" +
            "\tbackInstallSelect,\n" +
            "\telectMateriNeeds,\n" +
            "\tinstallTransfBacking,\n" +
            "\totherRemark,\n" +
            "\titemDeliverTime as itemDeliverTimeStr,\n" +
            "\tproductName,\n" +
            "\tneedNum,\n" +
            "\titemCreateTime as itemCreateTimeStr,\n" +
            "\titemUpdateTime as itemUpdateTimeStr\n" +
            "FROM\n" +
            "\torderitem" +
            " where orderHeadId = #{orderHeadId} and newFinishProudNo = #{newFinishProudNo}")
    OrderItem getOldItemByOrderIdandNewestFinishNo(OrderItem item);

    @Update("update orderhead set productTotalName = #{productTotalName},orderSetNum = #{orderSetNum}," +
            "headUpdateTime = #{headUpdateTimeStr},updateHeadTimes = #{updateHeadTimes} where orderNo = #{orderNo} ")
    void updateOrderHeadByOrderNo(OrderHead head);

    @Update("update orderitem set \n" +
            "\tproductBigType = #{productBigType},\n" +
            "\tproductMainShape = #{productMainShape},\n" +
            "\tnewFinishProudNo = #{newFinishProudNo},\n" +
            "\tproductSize = #{productSize},\n" +
            "\tedgeHightSize = #{edgeHightSize},\n" +
            "\tmainMateriAndArt = #{mainMateriAndArt},\n" +
            "\tbackInstallSelect = #{backInstallSelect},\n" +
            "\telectMateriNeeds = #{electMateriNeeds},\n" +
            "\tinstallTransfBacking = #{installTransfBacking},\n" +
            "\totherRemark = #{otherRemark},\n" +
            "\titemDeliverTime = #{itemDeliverTimeStr},\n" +
            "\tproductName = #{productName},\n" +
            "\tneedNum = #{needNum},\n" +
            "\titemUpdateTime = #{itemUpdateTimeStr},\n" +
            "\tupdateItemTimes = #{updateItemTimes} \n" +
            " where id = #{id}")
    void updateOrderItemById(OrderItem item);

    @Select("select orderNo from orderhead where SalorNo = #{empNo} and orderTime >= #{startTime} " +
            "AND orderTime <= #{endTime} order by id desc limit 1 ")
    String findNewestOrderNoBySalor(String empNo, String startTime, String endTime);

    @Select("select urlName from orderitemappend where orderNo = #{orderNo} ")
    List<String> findAllUrlByOrderNo(String orderNo);

    @Select("select * from orderhead where orderno = #{orderno} ")
    OrderHead getOrderHeadByOrderNo(String orderNo);

    @Select("select * from orderitemappend where id = #{id}")
    OrderItemAppend getOrderItemAppendById(Integer id);

    @Delete("delete from orderitemappend where id = #{id} ")
    void deleteOrderItemAppendByAppendId(Integer id);

    @Select("select fileName from orderitemappend where orderNo = #{orderNo} ")
    List<String> findAllFileNameByOrderNo(String orderNo);

    @Select("select * from orderitemappend where headId = #{id} order by fileName asc ")
    List<OrderItemAppend> findAllItemAppendByOrderNo(Integer id);

    @Select("select * from orderitemappend where orderNo = #{orderNo} order by fileName asc ")
    List<OrderItemAppend> findAllItemAppendByOrderNoReal(String orderNo);

    @Update("update orderhead set state = #{state},confirmEmpNo = #{confirmEmpNo},confirmTime = #{confirmTimeStr} where orderNo = #{orderNo}")
    void updateStateByOrderNo(OrderHead orderHead);

    @Select("select id,orderNo from orderhead order by orderNo desc")
    List<OrderHead> findAllOrderNo();

    @Select("select productName from orderitem order by productName desc")
    List<String> findAllProdName();

    @Select("SELECT\n" +
            "\toi.newFinishProudNo\n" +
            "FROM\n" +
            "\torderitem oi left join orderhead oh on oi.orderHeadId = oh.id\n" +
            "WHERE\n" +
            "\toh.SalorNo = #{empNo} and oh.orderTime >= #{startTime} AND oh.orderTime <= #{endTime} \n" +
            "ORDER BY\n" +
            "\toi.id DESC\n" +
            "LIMIT 1 ")
    String findNewestFinishProdNoByOldFinishProdNo(String empNo, String startTime, String endTime);

    @Select("select * from orderitem where newFinishProudNo = #{prodNo} limit 1 ")
    OrderItem getProductNoByProductNoBeforSave(String prodNo);

    @Select("SELECT\n" +
            "\toh.orderNo,\n" +
            "\toi.productName,\n" +
            "\toi.newFinishProudNo,\n" +
            "\toi.productBigType,\n" +
            "\toi.productMainShape,\n" +
            "\toi.needNum,\n" +
            "\toi.productSize,\n" +
            "\toi.edgeHightSize,\n" +
            "\toi.itemDeliverTime as itemDeliverTimeStr,\n" +
            "\toi.backInstallSelect,\n" +
            "\toi.mainMateriAndArt,\n" +
            "\toi.electMateriNeeds,\n" +
            "\toi.installTransfBacking,\n" +
            "\toi.otherRemark\n" +
            "FROM\n" +
            "\torderhead oh\n" +
            "LEFT JOIN orderitem oi ON oh.id = oi.orderHeadId where oh.id = #{id}" +
            " ORDER BY\n" +
            "\toi.productName ASC")
    List<OrderHead> getOrderItemByOrderHeadId(Integer id);

    @Delete("delete from orderitem where id = #{id}")
    void deleteOrderItemByItemId(Integer id);

    @Delete("delete from orderhead where id = #{headId}")
    void deleAllOrderItemByHeadId(Integer headId);

    @Select("select * from orderitem where id = #{itemId}")
    OrderItem getOrderItemById(Integer itemId);

    @Delete("delete from orderitem where orderHeadId = #{headId}")
    void deleteAllOrderHeadById(Integer headId);

    @Update("update orderhead set orderSetNum = #{totalNum} where id = #{headId} ")
    void updateOrderHeadTotalNum(Integer headId, Integer totalNum);

    @Delete("delete from orderhead where id = (select orderheadid from orderitem where id = #{itemId} limit 1 )")
    void deleteAllOrderHeadAndItemByItemId(Integer itemId);

    @Select("SELECT\n" +
            "oi.id," +
            "oh.singleOrProject AS singleOrProject,\n" +
            "\toh.orderNo AS orderNo,\n" +
            "\toh.orderTime AS orderTimeStr,\n" +
            "\toh.orderSetNum AS orderSetNum,\n" +
            "\tee.`name` AS salor,\n" +
            "\toi.productName,\n" +
            "\toi.newFinishProudNo,\n" +
            "\toi.productBigType,\n" +
            "\toi.productMainShape,\n" +
            "\toi.needNum,\n" +
            "\toi.productSize,\n" +
            "\toi.edgeHightSize,\n" +
            "\tdate_format(oi.itemDeliverTime, '%Y-%m-%d')  as itemDeliverTimeStr,\n" +
            "\toi.backInstallSelect,\n" +
            "\toi.mainMateriAndArt,\n" +
            "\toi.electMateriNeeds,\n" +
            "\toi.installTransfBacking,\n" +
            "\toh.productTotalName as productTotalName,\n" +
            "\toi.otherRemark\n" +
            "FROM\n" +
            "\torderhead oh\n" +
            "LEFT JOIN orderitem oi ON oh.id = oi.orderHeadId " +
            "LEFT JOIN employee ee ON oh.SalorNo = ee.empno" +
            " where oh.id = #{id}" +
            " ORDER BY\n" +
            "\toi.productName ASC")
    List<OrderHead> getOrderItemByHeadId(Integer id);

    @Select("SELECT\n" +
            "oh.singleOrProject AS singleOrProject,\n" +
            "\toh.orderNo AS orderNo,\n" +
            "\toh.productTotalName AS productTotalName,\n" +
            "\toh.orderTime AS orderTimeStr,\n" +
            "\toh.orderSetNum AS orderSetNum,\n" +
            "\tee.`name` AS salor," +
            "\toh.id AS id" +
            " FROM\n" +
            "\torderhead oh\n" +
            "LEFT JOIN employee ee ON oh.SalorNo = ee.empno" +
            " where oh.orderNo = #{orderNo} ")
    OrderHead getOrderHeadByOrderNo2(String orderNo);

    @Select("SELECT\n" +
            "oh.singleOrProject AS singleOrProject,\n" +
            "\toh.orderNo AS orderNo,\n" +
            "\toh.productTotalName AS productTotalName,\n" +
            "\toh.orderTime AS orderTimeStr,\n" +
            "\toh.orderSetNum AS orderSetNum,\n" +
            "\tee.`name` AS salor," +
            "\toh.id AS id" +
            " FROM\n" +
            "\torderhead oh\n" +
            "LEFT JOIN employee ee ON oh.SalorNo = ee.empno" +
            " where oh.id = #{id} ")
    OrderHead getOrderHeadByHeadId(Integer id);

    @Select("SELECT\n" +
            "\toh.orderNo,\n" +
            "  uo.engName\n" +
            "FROM\n" +
            "\torderhead oh left join userinfo uo on oh.SalorNo = uo.empno\n" +
            "WHERE\n" +
            "\toh.id = #{id} ")
    OrderHead getOrderHeadByHeadId2(Integer id);


    @Select("select *  from orderitem where orderHeadId =(  select orderheadid from orderitem where id = #{itemId} limit 1)")
    List<OrderItem> getAllOrderItemBy(Integer itemId);


    @Select("select * from orderhead where orderNo = #{orderNo} limit 1 ")
    OrderHead getOrderNoBYOrderNo(String orderNo);

    @Select("SELECT\n" +
            "\toh.id,\n" +
            "\toh.singleOrProject,\n" +
            "\toh.orderNo,\n" +
            "\toh.orderTime as orderTimeStr,\n" +
            "\toh.orderSetNum as orderSetNum,\n" +
            "\tep.fullname AS salor,\n" +
            "\toh.state\n" +
            "FROM\n" +
            "\torderhead oh\n" +
            "LEFT JOIN userinfo ep ON oh.SalorNo = ep.empno order by  oh.orderNo desc " +
            "limit #{currentPageTotalNum},#{pageSize}")
    List<OrderHead> findAllOrderHead(OrderHead orderHead);

    @Select("SELECT\n" +
            "\toh.id,\n" +
            "\toh.singleOrProject,\n" +
            "\toh.orderNo,\n" +
            "\toh.orderTime as orderTimeStr,\n" +
            "\toh.orderSetNum as orderSetNum,\n" +
            "\tep.fullname AS salor,\n" +
            "\toh.state\n" +
            "FROM\n" +
            "\torderhead oh\n" +
            "LEFT JOIN userinfo ep ON oh.SalorNo = ep.empno where oh.state = 1 order by  oh.orderNo desc " +
            "limit #{currentPageTotalNum},#{pageSize}")
    List<OrderHead> findAllOrderHeadForPMC(OrderHead orderHead);

    @Select("select count(*) from orderhead ")
    int findAllOrderHeadCount();


    @SelectProvider(type = OrderMapper.OrderDaoProvider.class, method = "queryOrderHeadByCondition")
    List<OrderHead> queryOrderHeadByCondition(OrderHead orderHead);

    @SelectProvider(type = OrderMapper.OrderDaoProvider.class, method = "queryOrderHeadByConditionCount")
    int queryOrderHeadByConditionCount(OrderHead orderHead);

    class OrderDaoProvider {

        public String queryOrderHeadByCondition(OrderHead orderHead) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\toh.id,\n" +
                    "\toh.singleOrProject,\n" +
                    "\toh.orderNo,\n" +
                    "\toh.orderTime as orderTimeStr,\n" +
                    "\tep.name AS salor,\n" +
                    "oh.orderSetNum," +
                    "\toh.state\n" +
                    "FROM\n" +
                    "\torderhead oh join orderitem oi on oi.orderHeadId = oh.id  \n" +
                    "LEFT JOIN employee ep ON oh.SalorNo = ep.empno where 1=1");
            if (orderHead.getNameIds() != null && orderHead.getNameIds().size() > 0) {
                sb.append(" and ep.id in (" + StringUtils.strip(orderHead.getNameIds().toString(), "[]") + ") ");

            }

            if (orderHead.getOrderNos() != null && orderHead.getOrderNos().size() > 0) {
                sb.append(" and oh.id in (" + StringUtils.strip(orderHead.getOrderNos().toString(), "[]") + ") ");
            }

            if (orderHead.getProductTotalName() != null && orderHead.getProductTotalName().trim().length() > 0) {
                sb.append(" and oi.productName  like  CONCAT('%',#{productTotalName},'%') ");
            }

            if (orderHead.getSingleOrProjects() != null && orderHead.getSingleOrProjects().size() > 0) {
                sb.append(" and oh.singleOrProject in (" + StringUtils.strip(orderHead.getSingleOrProjects().toString(), "[]") + ") ");
            }

            if (orderHead.getStates() != null && orderHead.getStates().size() > 0) {
                sb.append(" and oh.state in (" + StringUtils.strip(orderHead.getStates().toString(), "[]") + ") ");
            }

            if (orderHead.getOrderTimeStr() != null && orderHead.getOrderTimeStr().length() > 0) {
                sb.append(" and date_format(oh.orderTime, '%Y-%m-%d') = #{orderTimeStr}");
            }

            if (orderHead.getItemDeliverTimeStr() != null && orderHead.getItemDeliverTimeStr().length() > 0) {
                sb.append(" and date_format(oi.itemDeliverTime, '%Y-%m-%d') = #{itemDeliverTimeStr}");
            }

            if (orderHead.getSortMethod() != null && !"undefined".equals(orderHead.getSortMethod()) && !"undefined".equals(orderHead.getSortByName()) && orderHead.getSortByName() != null) {
                if ("orderNo".equals(orderHead.getSortByName())) {
                    sb.append(" order by oh.orderNo ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("salor".equals(orderHead.getSortByName())) {
                    sb.append(" order by ep.name ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("orderTime".equals(orderHead.getSortByName())) {
                    sb.append(" order by oh.orderTime ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deleverDate".equals(orderHead.getSortByName())) {
                    sb.append(" order by oh.deliverTime ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("orderNum".equals(orderHead.getSortByName())) {
                    sb.append(" order by oh.orderSetNum ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("orderType".equals(orderHead.getSortByName())) {
                    sb.append(" order by oh.singleOrProject ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("state".equals(orderHead.getSortByName())) {
                    sb.append(" order by oh.state ");
                    if ("asc".equals(orderHead.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(orderHead.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }
            } else {
                sb.append(" GROUP BY oh.orderNo order by oh.orderNo desc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryOrderHeadByConditionCount(OrderHead orderHead) {
            StringBuilder sb = new StringBuilder("select count(*) from (SELECT count(*) " +
                    "FROM\n" +
                    "\torderhead oh join orderitem oi on oh.id = oi.orderHeadId \n" +
                    " left JOIN employee ep ON oh.SalorNo = ep.empno where 1=1");
            if (orderHead.getNameIds() != null && orderHead.getNameIds().size() > 0) {
                sb.append(" and ep.id in (" + StringUtils.strip(orderHead.getNameIds().toString(), "[]") + ") ");

            }

            if (orderHead.getOrderNos() != null && orderHead.getOrderNos().size() > 0) {
                sb.append(" and oh.id in (" + StringUtils.strip(orderHead.getOrderNos().toString(), "[]") + ") ");
            }

            if (orderHead.getProductTotalName() != null && orderHead.getProductTotalName().trim().length() > 0) {
                sb.append(" and oi.productName  like  CONCAT('%',#{productTotalName},'%') ");
            }

            if (orderHead.getSingleOrProjects() != null && orderHead.getSingleOrProjects().size() > 0) {
                sb.append(" and oh.singleOrProject in (" + StringUtils.strip(orderHead.getSingleOrProjects().toString(), "[]") + ") ");
            }

            if (orderHead.getStates() != null && orderHead.getStates().size() > 0) {
                sb.append(" and oh.state in (" + StringUtils.strip(orderHead.getStates().toString(), "[]") + ") ");
            }

            if (orderHead.getOrderTimeStr() != null && orderHead.getOrderTimeStr().length() > 0) {
                sb.append(" and date_format(oh.orderTime, '%Y-%m-%d') = #{orderTimeStr}");
            }

            if (orderHead.getItemDeliverTimeStr() != null && orderHead.getItemDeliverTimeStr().length() > 0) {
                sb.append(" and date_format(oi.itemDeliverTime, '%Y-%m-%d') = #{itemDeliverTimeStr}");
            }

            sb.append(" GROUP BY oh.orderNo) as a ");
            return sb.toString();
        }


    }
}
