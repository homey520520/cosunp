package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.DesignMaterialHead;
import com.cosun.cosunp.entity.DesignMaterialHeadProduct;
import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.mapper.DesignMapper;
import com.cosun.cosunp.mapper.ProjectMapper;
import com.cosun.cosunp.service.IDesignServ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/3/23  下午 5:33
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DesignServiceImpl implements IDesignServ {

    private static Logger logger = LogManager.getLogger(DesignServiceImpl.class);

    @Autowired
    DesignMapper designMapper;


    public List<Employee> getSalor() throws Exception {
        return designMapper.getSalor();
    }

    public List<Employee> getAllMaker() throws Exception {
        return designMapper.getAllMaker();
    }

    public List<String> getAllOrderNo() throws Exception {
        return designMapper.getAllOrderNo();
    }

    public List<String> getAllOrderArea() throws Exception {
        return designMapper.getAllOrderArea();
    }

    public List<DesignMaterialHead> getAllDMH(DesignMaterialHead orderHead) throws Exception {
        return designMapper.getAllDMH(orderHead);
    }

    public int getAllDMHCount() throws Exception {
        return designMapper.getAllDMHCount();
    }

    public int saveSJHeadDateToMysql(DesignMaterialHead dmh) throws Exception {
        int isExsit = designMapper.getSJHeadByOrderNo(dmh.getCustomerNo());
        if (isExsit == 0) {
            designMapper.saveSJHeadDateToMysql(dmh);
            isExsit = 1;
        } else {
            isExsit = 5;
        }
        return isExsit;
    }

    public List<DesignMaterialHeadProduct> getAllDMHP(DesignMaterialHeadProduct orderHead) throws Exception {
        return designMapper.getAllDMHP(orderHead);
    }

    public int getAllDMHPCount() throws Exception {
        return designMapper.getAllDMHPCount();
    }

    public List<String> getAllproductNameList() throws Exception {
        return designMapper.getAllproductNameList();
    }

    public List<String> getAllproductNoList() throws Exception {
        return designMapper.getAllproductNoList();
    }

    public List<String> getAlldrawingNoList() throws Exception {
        return designMapper.getAlldrawingNoList();
    }

    public int saveSJHeadPDateToMysql(DesignMaterialHeadProduct pmhp) throws Exception {
        Integer isExsit = designMapper.getSJHeadByProductNo(pmhp.getProductNo(),pmhp.getCustomerNo());
        if (isExsit == null || isExsit == 0 ) {
            Integer head_Id = designMapper.getHeadIdByCustomerNo(pmhp.getCustomerNo());
            pmhp.setHead_id(head_Id);
            designMapper.saveSJHeadPDateToMysql(pmhp);
            isExsit = 1;
        } else {
            isExsit = 5;
        }
        return isExsit;
    }

    public List<DesignMaterialHeadProduct> getAllDMHPByCustomerNo(DesignMaterialHeadProduct pmhp) throws Exception {
            return designMapper.getAllDMHPByCustomerNo(pmhp);
    }

    public int getAllDMHPByCustomerNoCount(DesignMaterialHeadProduct dmhp) throws Exception {
           return designMapper.getAllDMHPByCustomerNoCountCount(dmhp);
    }


}
