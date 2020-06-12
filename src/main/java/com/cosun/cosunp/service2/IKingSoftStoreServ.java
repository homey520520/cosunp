package com.cosun.cosunp.service2;

import com.cosun.cosunp.entity.DesignMaterialHeadProduct;
import com.cosun.cosunp.entity.DesignMaterialHeadProductItem;

import java.util.List;
import java.util.Map;

public interface IKingSoftStoreServ {

    List<DesignMaterialHeadProductItem> getAllMateiralNameList();

    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByCondition(String materialName);

    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByConditionab(String materialName);

    List<DesignMaterialHeadProduct> queryRouteSpecifiByCondition(String routName) throws Exception;

    DesignMaterialHeadProductItem getMaterialNameSpeifiById(String materialNo);

    List<DesignMaterialHeadProduct> getAllproductRoute() throws Exception;
}
