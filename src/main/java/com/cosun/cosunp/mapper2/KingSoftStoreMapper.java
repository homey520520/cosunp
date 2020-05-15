package com.cosun.cosunp.mapper2;

import com.cosun.cosunp.entity.DesignMaterialHeadProductItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface KingSoftStoreMapper {

    @Select("select  FNAME as materialName from T_BD_MATERIAL_L  group by  FNAME order by  FNAME asc")
    List<DesignMaterialHeadProductItem> getAllMateiralNameList();

    @Select("select FNAME from T_BD_MATERIAL_L where FNAME like CONCAT('%',#{materialName},'%') and FSPECIFICATION like CONCAT('%',#{materialSpefi},'%')")
    List<String> queryMaterialSpecifiByConditionA(String materialName, String materialSpefi);

    @Select("select \n" +
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
            " where tbm.FNAME like CONCAT('%',#{materialName},'%') and tbm.FSPECIFICATION like CONCAT('%',#{materialSpefi},'%') " +
            " group by tt.FNUMBER,tbm.FNAME,tbm.FSPECIFICATION,tbma.FBASEUNITID,tbma.FISPURCHASE\n" +
            " order by  tbm.FNAME,tbm.FSPECIFICATION")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByConditionB(String materialName, String materialSpefi);

    @Select("select \n" +
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
            " where tbm.FNAME like CONCAT('%',#{materialName},'%')  " +
            " group by tt.FNUMBER,tbm.FNAME,tbm.FSPECIFICATION,tbma.FBASEUNITID,tbma.FISPURCHASE\n" +
            " order by  tbm.FNAME,tbm.FSPECIFICATION")
    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByCondition(String materialName);

}
