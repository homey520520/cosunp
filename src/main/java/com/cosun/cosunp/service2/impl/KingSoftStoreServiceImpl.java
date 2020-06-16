package com.cosun.cosunp.service2.impl;

import com.cosun.cosunp.entity.DesignMaterialHeadProduct;
import com.cosun.cosunp.entity.DesignMaterialHeadProductItem;
import com.cosun.cosunp.mapper.DesignMapper;
import com.cosun.cosunp.mapper2.KingSoftStoreMapper;
import com.cosun.cosunp.service.impl.DesignServiceImpl;
import com.cosun.cosunp.service2.IKingSoftStoreServ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author:homey Wong
 * @Date: 2020/5/12 0012 上午 10:50
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Service
@Transactional(value = "test2TransactionManager", rollbackFor = Exception.class)
public class KingSoftStoreServiceImpl implements IKingSoftStoreServ {

    private static Logger logger = LogManager.getLogger(KingSoftStoreServiceImpl.class);

    @Autowired
    KingSoftStoreMapper kingSoftStoreMapper;

    public List<DesignMaterialHeadProductItem> getAllMateiralNameList() {
        return kingSoftStoreMapper.getAllMateiralNameList();
    }

    public List<DesignMaterialHeadProductItem> queryMaterialSpecifiByCondition(String materialName) {
        List<DesignMaterialHeadProductItem> item = new ArrayList<DesignMaterialHeadProductItem>();
        DesignMaterialHeadProductItem pitem = new DesignMaterialHeadProductItem();
        List<DesignMaterialHeadProductItem> headItemList = new ArrayList<DesignMaterialHeadProductItem>();
        if (materialName != null && materialName.trim().length() > 0)
            materialName = materialName.substring(1, materialName.length() - 1);
        if (materialName.contains(" ")) {
            String[] materialNameA = materialName.trim().split(" ");
            if (materialNameA != null && materialNameA.length > 1) {
                List<String> charArray = Arrays.asList(materialNameA[0].trim().split(""));
                headItemList = kingSoftStoreMapper.queryMaterialSpecifiByConditionWF(charArray, materialNameA[1].trim());
                pitem.setDhpHeadItemList(headItemList);
                if (headItemList != null && headItemList.size() > 0) {
                    List<DesignMaterialHeadProductItem> dphItemList = kingSoftStoreMapper.queryMaterialSpecifiByConditionWWW(headItemList.get(0).getMaterialName(), materialNameA[1].trim());
                    pitem.setDhpItemList(dphItemList);

                }
                item.add(pitem);
                return item;
            }
        } else {

            List<DesignMaterialHeadProductItem> itemm = null;
            if (materialName != null) {
                itemm = kingSoftStoreMapper.queryMaterialSpecifiByConditionWW(materialName.trim());
                if (itemm != null && itemm.size() > 0) {
                    headItemList.add(itemm.get(0));
                }
            }
            List<String> charArray = Arrays.asList(materialName.trim().split(""));
            headItemList.addAll(kingSoftStoreMapper.queryMaterialSpecifiByConditionW(charArray, materialName.trim()));
            pitem.setDhpHeadItemList(headItemList);
            if (headItemList != null && headItemList.size() > 0) {
                List<DesignMaterialHeadProductItem> dphItemList = kingSoftStoreMapper.queryMaterialSpecifiByConditionWW(headItemList.get(0).getMaterialName());
                pitem.setDhpItemList(dphItemList);
            }
            item.add(pitem);
            return item;
        }
        return null;
    }


    public DesignMaterialHeadProductItem getMaterialNameSpeifiById(String materialNo) {
        return kingSoftStoreMapper.queryMaterilNameByNo(materialNo);

    }

    public List<DesignMaterialHeadProduct> queryRouteSpecifiByCondition(String materialName) throws Exception {
        List<String> charArray = null;
        if (materialName != null) {
            materialName = materialName.substring(1, materialName.length() - 1);
            charArray = Arrays.asList(materialName.trim().split(""));
        }
        return kingSoftStoreMapper.queryRouteSpecifiByCondition(charArray);
    }


    public List<DesignMaterialHeadProduct> getAllproductRoute() throws Exception {
        return null;
    }

    public List<DesignMaterialHeadProductItem> queryMaterialSpecifiByConditionab(String materialName) {
        List<DesignMaterialHeadProductItem> item = new ArrayList<DesignMaterialHeadProductItem>();
        DesignMaterialHeadProductItem pitem = new DesignMaterialHeadProductItem();
        if (materialName != null) {
            materialName = materialName.substring(1, materialName.length() - 1);
        }
        List<DesignMaterialHeadProductItem> dphItemList = kingSoftStoreMapper.queryMaterialSpecifiByConditionWW(materialName);
        pitem.setDhpItemList(dphItemList);
        item.add(pitem);
        return item;

    }


}
