package com.cosun.cosunp.service2;

import com.cosun.cosunp.entity.DesignMaterialHeadProductItem;

import java.util.List;

public interface IKingSoftStoreServ {

    List<DesignMaterialHeadProductItem> getAllMateiralNameList();

    List<DesignMaterialHeadProductItem> queryMaterialSpecifiByCondition(String materialName);
}
