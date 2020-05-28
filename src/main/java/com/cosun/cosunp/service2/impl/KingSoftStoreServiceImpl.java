package com.cosun.cosunp.service2.impl;

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

import java.util.Arrays;
import java.util.List;

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
        if (materialName != null && materialName.trim().length() > 0)
            materialName = materialName.substring(1, materialName.length() - 1);
        if (materialName.contains(" ")) {
            String[] materialNameA = materialName.trim().split(" ");
            if (materialNameA != null && materialNameA.length > 1) {
                List<String> charArray = Arrays.asList(materialNameA[0].trim().split(""));
                return kingSoftStoreMapper.queryMaterialSpecifiByConditionB(charArray, materialNameA[1].trim());
            }
        } else {
            List<String> charArray = Arrays.asList(materialName.trim().split(""));
            return kingSoftStoreMapper.queryMaterialSpecifiByCondition(charArray);
        }
        return null;
    }


}
