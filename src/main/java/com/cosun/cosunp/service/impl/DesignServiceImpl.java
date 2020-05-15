package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.DesignMaterialHead;
import com.cosun.cosunp.entity.DesignMaterialHeadProduct;
import com.cosun.cosunp.entity.DesignMaterialHeadProductItem;
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
@Transactional(value = "test1TransactionManager", rollbackFor = Exception.class)
public class DesignServiceImpl implements IDesignServ {

    private static Logger logger = LogManager.getLogger(DesignServiceImpl.class);

    @Autowired
    DesignMapper designMapper;


    public List<String> getAllmaterialSpeciList() throws Exception {
        return designMapper.getAllmaterialSpeciList();
    }

    public List<String> getAllmaterialNameList() throws Exception {
        return designMapper.getAllmaterialNameList();
    }

    public List<String> getAllmateiralNoList() throws Exception {
        return designMapper.getAllmateiralNoList();

    }

    public List<DesignMaterialHeadProductItem> getAllDMHPI(DesignMaterialHeadProductItem orderHead) throws Exception {
        return designMapper.getAllDMHPI(orderHead);

    }

    public int getAllDMHPICount() throws Exception {
        return designMapper.getAllDMHPICount();
    }

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

    public void deleteOrderItemHByheadId(Integer id) throws Exception {
        designMapper.deleteOrderItemHByheadId(id);
    }

    public int getAllDMHCount() throws Exception {
        return designMapper.getAllDMHCount();
    }

    public List<DesignMaterialHead> queryOrderHeadByCondition(DesignMaterialHead head) throws Exception {
        return designMapper.queryOrderHeadByCondition(head);
    }

    public int queryOrderHeadByConditionCount(DesignMaterialHead head) throws Exception {
        return designMapper.queryOrderHeadByConditionCount(head);
    }

    public int saveSJHeadDateToMysql(DesignMaterialHead dmh) throws Exception {
        if (dmh.getId() == 0) {
            int isExsit = designMapper.getSJHeadByOrderNo(dmh.getCustomerNo());
            if (isExsit == 0) {
                designMapper.saveSJHeadDateToMysql(dmh);
                isExsit = 1;
            } else {
                isExsit = 5;
            }
            return isExsit;
        } else {
            int isExsit = designMapper.getSJHeadByOrderNoButId(dmh.getCustomerNo(), dmh.getId());
            if (isExsit == 0) {
                designMapper.updateSJHeadDateToMysql(dmh);
                isExsit = 3;
            } else {
                isExsit = 5;
            }
            return isExsit;
        }
    }

    public void deleteOrderItemByheadId(Integer id) throws Exception {
        designMapper.deleteOrderItemByheadId(id);
    }

    public int saveSJIHeadPDateToMysql(DesignMaterialHeadProductItem item) throws Exception {
        int isExsit = designMapper.getSJIHeadByOrderNo(item.getCustomerNo(), item.getProductNo(), item.getMateiralNo());
        if (isExsit == 0) {
            int head_id = designMapper.getHeadIdByCustomerNoAndMaterialNo(item.getCustomerNo(), item.getProductNo());
            item.setHead_product_id(head_id);
            designMapper.saveSJIHeadDateToMysql(item);
            isExsit = 1;
        } else {
            isExsit = 5;
        }
        return isExsit;
    }

    public List<DesignMaterialHeadProductItem> getAllDMHIPByCustomerNo(DesignMaterialHeadProductItem item) throws Exception {
        //return designMapper.getAllDMHIPByCustomerNo(item);
        return null;
    }

    public int getAllDMHIPByCustomerNoCount(DesignMaterialHeadProductItem item) throws Exception {
        // return designMapper.getAllDMHIPByCustomerNoCount(item);
        return 0;
    }

    public List<DesignMaterialHeadProduct> getAllDMHP(DesignMaterialHeadProduct orderHead) throws Exception {
       if(orderHead!=null && orderHead.getCustomerNo()!=null) {
           return designMapper.getAllDMHP2(orderHead);
       }else{
           return designMapper.getAllDMHP(orderHead);
        }
    }

    public int getAllDMHPCount(DesignMaterialHeadProduct orderHead) throws Exception {
        if(orderHead!=null && orderHead.getCustomerNo()!=null) {
            return designMapper.getAllDMHPCount2(orderHead);
        }else{
            return designMapper.getAllDMHPCount();
        }
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

    public List<DesignMaterialHeadProduct> queryOrderHeadProductByCondition(DesignMaterialHeadProduct product) throws Exception {
        return designMapper.queryOrderHeadProductByCondition(product);
    }

    public int queryOrderHeadProductByConditionCount(DesignMaterialHeadProduct product) throws Exception {
        return designMapper.queryOrderHeadProductByConditionCount(product);
    }

    public int saveSJHeadPDateToMysql(DesignMaterialHeadProduct pmhp) throws Exception {
        if(pmhp.getId() == 0) {
            Integer isExsit = designMapper.getSJHeadByProductNo(pmhp.getProductNo(), pmhp.getCustomerNo());
            if (isExsit == null || isExsit == 0) {
                Integer head_Id = designMapper.getHeadIdByCustomerNo(pmhp.getCustomerNo());
                pmhp.setHead_id(head_Id);
                designMapper.saveSJHeadPDateToMysql(pmhp);
                isExsit = 1;
            } else {
                 isExsit = 5;
            }
            return isExsit;
        }else {
            Integer isExsit = designMapper.getSJHeadByProductNoButId(pmhp.getProductNo(), pmhp.getCustomerNo(),pmhp.getId());
            if (isExsit == null || isExsit == 0) {
                Integer head_Id = designMapper.getHeadIdByCustomerNo(pmhp.getCustomerNo());
                pmhp.setHead_id(head_Id);
                designMapper.updateSJHeadPDateToMysql(pmhp);
                isExsit = 3;
            } else {
                isExsit = 5;
            }
            return isExsit;
        }
    }

    public List<DesignMaterialHeadProduct> getAllDMHPByCustomerNo(DesignMaterialHeadProduct pmhp) throws Exception {
        return designMapper.getAllDMHPByCustomerNo(pmhp);
    }

    public int getAllDMHPByCustomerNoCount(DesignMaterialHeadProduct dmhp) throws Exception {
        return designMapper.getAllDMHPByCustomerNoCountCount(dmhp);
    }


}
