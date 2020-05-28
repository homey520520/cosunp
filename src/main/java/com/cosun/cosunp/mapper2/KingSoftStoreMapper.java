package com.cosun.cosunp.mapper2;

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

    @SelectProvider(type = KingSoftStoreMapper.OrderDaoProvider.class, method = "queryMaterialSpecifiByConditionB")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByConditionB(@Param("charArray") List<String> charArray,@Param("materialSpefi")  String materialSpefi);

    @SelectProvider(type = KingSoftStoreMapper.OrderDaoProvider.class, method = "queryMaterialSpecifiByCondition")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByCondition(@Param("charArray") List<String> charArray);


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
