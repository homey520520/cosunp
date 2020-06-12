package com.cosun.cosunp.mapper2;

import com.cosun.cosunp.entity.DesignMaterialHeadProduct;
import com.cosun.cosunp.entity.DesignMaterialHeadProductItem;
import com.cosun.cosunp.mapper.DesignMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface KingSoftStoreMapper {

    @Select("select  FNAME as materialName from T_BD_MATERIAL_L  group by  FNAME order by  FNAME asc")
    List<DesignMaterialHeadProductItem> getAllMateiralNameList();

    @Select("select FNAME from T_BD_MATERIAL_L where FNAME like CONCAT('%',#{materialName},'%') and FSPECIFICATION like CONCAT('%',#{materialSpefi},'%')")
    List<String> queryMaterialSpecifiByConditionA(String materialName, String materialSpefi);

    @Select("SELECT top 1\n" +
            "\ttbm.FNAME AS materialName,\n" +
            "\ttbm.FSPECIFICATION AS materialSpeci\n" +
            "FROM\n" +
            "\tT_BD_MATERIAL tt\n" +
            "JOIN T_BD_MATERIAL_L tbm ON tbm.FMATERIALID = tt.FMATERIALID\n" +
            "join T_STK_INVENTORY tsi on tsi.FMATERIALID = tbm.FMATERIALID " +
            "AND PATINDEX ('[1-5]%', tt.FNUMBER) > 0 where tt.FNUMBER = #{materialNo} ")
    DesignMaterialHeadProductItem queryMaterilNameByNo(String materialNo);

    @Select("SELECT top 1\n" +
            "Convert(decimal(18,2),sum(tsi.FBASEQTY)) as mateiralStock " +
            "FROM\n" +
            "\tT_BD_MATERIAL tt\n" +
            "JOIN T_BD_MATERIAL_L tbm ON tbm.FMATERIALID = tt.FMATERIALID\n" +
            "join T_STK_INVENTORY tsi on tsi.FMATERIALID = tbm.FMATERIALID " +
            "AND PATINDEX ('[1-5]%', tt.FNUMBER) > 0 where tt.FNUMBER = #{materialNo} ")
    Double getNewStockByMateriId(String materialNo);


    @Select("SELECT\n" +
            "   tba.fnumber\n" +
            "\t\n" +
            "FROM\n" +
            "\tT_BAS_ASSISTANTDATAENTRY tba\n" +
            "LEFT JOIN T_BAS_ASSISTANTDATAENTRY_L tbal ON tba.FENTRYID = tbal.FENTRYID\n" +
            "WHERE\n" +
            "\ttba.FID = '5ebd0236b59a8a' and tbal.FDATAVALUE = #{materiName} ")
    String getProductNoByName(String materiName);



    @SelectProvider(type = KingSoftStoreMapper.OrderDaoProvider.class, method = "queryRouteSpecifiByCondition")
    List<DesignMaterialHeadProduct> queryRouteSpecifiByCondition(@Param("charArray") List<String> charArray);

    @SelectProvider(type = KingSoftStoreMapper.OrderDaoProvider.class, method = "queryMaterialSpecifiByConditionWW")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByConditionWW(String materialName);

    @SelectProvider(type = KingSoftStoreMapper.OrderDaoProvider.class, method = "queryMaterialSpecifiByConditionWWW")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByConditionWWW(String materialName, String spefication);

    @SelectProvider(type = KingSoftStoreMapper.OrderDaoProvider.class, method = "queryMaterialSpecifiByConditionB")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByConditionB(@Param("charArray") List<String> charArray, @Param("materialSpefi") String materialSpefi);

    @SelectProvider(type = KingSoftStoreMapper.OrderDaoProvider.class, method = "queryMaterialSpecifiByCondition")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByCondition(@Param("charArray") List<String> charArray);

    @SelectProvider(type = KingSoftStoreMapper.OrderDaoProvider.class, method = "queryMaterialSpecifiByConditionW")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByConditionW(@Param("charArray") List<String> charArray);


    @SelectProvider(type = KingSoftStoreMapper.OrderDaoProvider.class, method = "queryMaterialSpecifiByConditionWF")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByConditionWF(@Param("charArray") List<String> charArray, @Param("materialSpefi") String materialSpefi);


    class OrderDaoProvider {


        public String queryMaterialSpecifiByCondition(List<String> charArray) {
            StringBuilder sb = new StringBuilder("select \n" +
                    "tt.FNUMBER as mateiralNo,\n" +
                    "tbm.FNAME as materialName,\n" +
                    "tbm.FSPECIFICATION as materialSpeci,\n" +
                    "CASE tbma.FBASEUNITID\n" +
                    "         WHEN '10087' THEN '米'\n" +
                    "         WHEN '10095' THEN '千克'\n" +
                    "\t\t  WHEN '10099' THEN '升'\n" +
                    "\t\t  WHEN '10101' THEN 'pcs'\n" +
                    "\t\t  WHEN '10149' THEN '套'\n" +
                    "\t\t  WHEN '110486' THEN '平方'\n" +
                    "ELSE '其他' END as unit,\n" +
                    "tbma.FISPURCHASE as isCanPurchase,\n" +
                    " Convert(decimal(18,2),sum(tsi.FBASEQTY)) as mateiralStock\n" +
                    "from  T_BD_MATERIAL tt\n" +
                    " join T_BD_MATERIAL_L tbm  on tbm.FMATERIALID = tt.FMATERIALID and   PATINDEX( '[1-5]%', tt.FNUMBER ) > 0\n" +
                    " join T_BD_MATERIALBASE tbma on tbm.FMATERIALID = tbma.FMATERIALID\n" +
                    " join T_STK_INVENTORY tsi on tsi.FMATERIALID = tbm.FMATERIALID " +
                    " where 1=1 ");

            if (charArray != null && charArray.size() > 0) {
                for (int i = 0; i < charArray.size(); i++) {
                    sb.append(" and tbm.FNAME like CONCAT('%','" + charArray.get(i) + "','%')");
                }
            }

            sb.append(" group by tt.FNUMBER,tbm.FNAME,tbm.FSPECIFICATION,tbma.FBASEUNITID,tbma.FISPURCHASE\n" +
                    " order by  tbm.FNAME,tbm.FSPECIFICATION");

            return sb.toString();
        }

        public String queryRouteSpecifiByCondition(List<String> charArray ) {

            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "   tba.fnumber as productTypeNo,\n" +
                    "\ttbal.FDATAVALUE as productTypeName,\n" +
                    "\ttbal.FDESCRIPTION as productTypeRoute\n" +
                    "FROM\n" +
                    "\tT_BAS_ASSISTANTDATAENTRY tba\n" +
                    "LEFT JOIN T_BAS_ASSISTANTDATAENTRY_L tbal ON tba.FENTRYID = tbal.FENTRYID\n" +
                    "WHERE\n" +
                    "\ttba.FID = '5ebd0236b59a8a'  ");

            if (charArray != null && charArray.size() > 0) {
                for (int i = 0; i < charArray.size(); i++) {
                    sb.append(" and tbal.FDATAVALUE like CONCAT('%','" + charArray.get(i) + "','%')");
                }
            }


            return sb.toString();
        }

        public String queryMaterialSpecifiByConditionWW(String materialName) {
            StringBuilder sb = new StringBuilder("select \n" +
                    "tt.FNUMBER as mateiralNo,\n" +
                    "tbm.FNAME as materialName,\n" +
                    "tbm.FSPECIFICATION as materialSpeci,\n" +
                    "CASE tbma.FBASEUNITID\n" +
                    "         WHEN '10087' THEN '米'\n" +
                    "         WHEN '10095' THEN '千克'\n" +
                    "\t\t  WHEN '10099' THEN '升'\n" +
                    "\t\t  WHEN '10101' THEN 'pcs'\n" +
                    "\t\t  WHEN '10149' THEN '套'\n" +
                    "\t\t  WHEN '110486' THEN '平方'\n" +
                    "ELSE '其他' END as unit,\n" +
                    "tbma.FISPURCHASE as isCanPurchase,\n" +
                    " Convert(decimal(18,2),sum(tsi.FBASEQTY)) as mateiralStock\n" +
                    "from  T_BD_MATERIAL tt\n" +
                    " join T_BD_MATERIAL_L tbm  on tbm.FMATERIALID = tt.FMATERIALID and   PATINDEX( '[1-5]%', tt.FNUMBER ) > 0\n" +
                    " join T_BD_MATERIALBASE tbma on tbm.FMATERIALID = tbma.FMATERIALID\n" +
                    " join T_STK_INVENTORY tsi on tsi.FMATERIALID = tbm.FMATERIALID " +
                    " where  tbm.FNAME = #{materialName} ");

            sb.append(" group by tt.FNUMBER,tbm.FNAME,tbm.FSPECIFICATION,tbma.FBASEUNITID,tbma.FISPURCHASE\n" +
                    " order by  tbm.FNAME,tbm.FSPECIFICATION");

            return sb.toString();
        }

        public String queryMaterialSpecifiByConditionWWW(String materialName, String spefication) {
            StringBuilder sb = new StringBuilder("select \n" +
                    "tt.FNUMBER as mateiralNo,\n" +
                    "tbm.FNAME as materialName,\n" +
                    "tbm.FSPECIFICATION as materialSpeci,\n" +
                    "CASE tbma.FBASEUNITID\n" +
                    "         WHEN '10087' THEN '米'\n" +
                    "         WHEN '10095' THEN '千克'\n" +
                    "\t\t  WHEN '10099' THEN '升'\n" +
                    "\t\t  WHEN '10101' THEN 'pcs'\n" +
                    "\t\t  WHEN '10149' THEN '套'\n" +
                    "\t\t  WHEN '110486' THEN '平方'\n" +
                    "ELSE '其他' END as unit,\n" +
                    "tbma.FISPURCHASE as isCanPurchase,\n" +
                    " Convert(decimal(18,2),sum(tsi.FBASEQTY)) as mateiralStock\n" +
                    "from  T_BD_MATERIAL tt\n" +
                    " join T_BD_MATERIAL_L tbm  on tbm.FMATERIALID = tt.FMATERIALID and   PATINDEX( '[1-5]%', tt.FNUMBER ) > 0\n" +
                    " join T_BD_MATERIALBASE tbma on tbm.FMATERIALID = tbma.FMATERIALID\n" +
                    " join T_STK_INVENTORY tsi on tsi.FMATERIALID = tbm.FMATERIALID " +
                    " where  tbm.FNAME = #{materialName} and  tbm.FSPECIFICATION like CONCAT('%','" + spefication + "','%') ");

            sb.append(" group by tt.FNUMBER,tbm.FNAME,tbm.FSPECIFICATION,tbma.FBASEUNITID,tbma.FISPURCHASE\n" +
                    " order by  tbm.FNAME,tbm.FSPECIFICATION");

            return sb.toString();
        }

        public String queryMaterialSpecifiByConditionW(List<String> charArray) {
            StringBuilder sb = new StringBuilder("select" +
                    " tbm.FNAME as materialName " +
                    "from  T_BD_MATERIAL tt\n" +
                    " join T_BD_MATERIAL_L tbm  on tbm.FMATERIALID = tt.FMATERIALID and   PATINDEX( '[1-5]%', tt.FNUMBER ) > 0\n" +
                    " join T_BD_MATERIALBASE tbma on tbm.FMATERIALID = tbma.FMATERIALID\n" +
                    " join T_STK_INVENTORY tsi on tsi.FMATERIALID = tbm.FMATERIALID " +
                    " where 1=1 ");

            if (charArray != null && charArray.size() > 0) {
                for (int i = 0; i < charArray.size(); i++) {
                    sb.append(" and tbm.FNAME like CONCAT('%','" + charArray.get(i) + "','%')");
                }
            }

            sb.append(" group by tbm.FNAME " +
                    " order by  tbm.FNAME asc ");

            return sb.toString();
        }


        public String queryMaterialSpecifiByConditionWF(List<String> charArray, String materialSpefi) {
            StringBuilder sb = new StringBuilder("select" +
                    " tbm.FNAME as materialName " +
                    "from  T_BD_MATERIAL tt\n" +
                    " join T_BD_MATERIAL_L tbm  on tbm.FMATERIALID = tt.FMATERIALID and   PATINDEX( '[1-5]%', tt.FNUMBER ) > 0\n" +
                    " join T_BD_MATERIALBASE tbma on tbm.FMATERIALID = tbma.FMATERIALID\n" +
                    " join T_STK_INVENTORY tsi on tsi.FMATERIALID = tbm.FMATERIALID " +
                    " where 1=1 ");

            if (charArray != null && charArray.size() > 0) {
                for (int i = 0; i < charArray.size(); i++) {
                    sb.append(" and tbm.FNAME like CONCAT('%','" + charArray.get(i) + "','%')");
                }
            }

            sb.append(" and tbm.FSPECIFICATION like CONCAT('%','" + materialSpefi + "','%') group by tbm.FNAME " +
                    " order by  tbm.FNAME asc ");

            return sb.toString();
        }


        public String queryMaterialSpecifiByConditionB(List<String> charArray, String materialSpefi) {
            StringBuilder sb = new StringBuilder("select \n" +
                    "tt.FNUMBER as mateiralNo,\n" +
                    "tbm.FNAME as materialName,\n" +
                    "tbm.FSPECIFICATION as materialSpeci,\n" +
                    "CASE tbma.FBASEUNITID\n" +
                    "         WHEN '10087' THEN '米'\n" +
                    "         WHEN '10095' THEN '千克'\n" +
                    "\t\t  WHEN '10099' THEN '升'\n" +
                    "\t\t  WHEN '10101' THEN 'pcs'\n" +
                    "\t\t  WHEN '10149' THEN '套'\n" +
                    "\t\t  WHEN '110486' THEN '平方'\n" +
                    "ELSE '其他' END as unit,\n" +
                    "tbma.FISPURCHASE as isCanPurchase,\n" +
                    " Convert(decimal(18,2),sum(tsi.FBASEQTY)) as mateiralStock\n" +
                    "from  T_BD_MATERIAL tt\n" +
                    " join T_BD_MATERIAL_L tbm  on tbm.FMATERIALID = tt.FMATERIALID and   PATINDEX( '[1-5]%', tt.FNUMBER ) > 0\n" +
                    " join T_BD_MATERIALBASE tbma on tbm.FMATERIALID = tbma.FMATERIALID\n" +
                    " join T_STK_INVENTORY tsi on tsi.FMATERIALID = tbm.FMATERIALID " +
                    " where tbm.FSPECIFICATION like CONCAT('%',#{materialSpefi},'%') ");

            if (charArray != null && charArray.size() > 0) {
                for (int i = 0; i < charArray.size(); i++) {
                    sb.append(" and tbm.FNAME like CONCAT('%','" + charArray.get(i) + "','%')");
                }
            }

            sb.append(" group by tt.FNUMBER,tbm.FNAME,tbm.FSPECIFICATION,tbma.FBASEUNITID,tbma.FISPURCHASE\n" +
                    " order by  tbm.FNAME,tbm.FSPECIFICATION");

            return sb.toString();
        }
    }


}
