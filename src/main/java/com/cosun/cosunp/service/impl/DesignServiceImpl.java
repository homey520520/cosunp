package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.DesignMapper;
import com.cosun.cosunp.mapper.ProjectMapper;
import com.cosun.cosunp.mapper2.KingSoftStoreMapper;
import com.cosun.cosunp.service.IDesignServ;
import com.cosun.cosunp.tool.WordToPDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    KingSoftStoreMapper kingSoftStoreMapper;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

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
        if (orderHead != null && orderHead.getHead_product_id() != null) {
            return designMapper.getAllDMHPIButId(orderHead);
        }
        return designMapper.getAllDMHPI(orderHead);
    }

    public int getAllDMHPICount(DesignMaterialHeadProductItem orderHead) throws Exception {
        if (orderHead != null && orderHead.getHead_product_id() != null) {
            return designMapper.getAllDMHPIButIdCount(orderHead.getHead_product_id());
        }
        return designMapper.getAllDMHPICount(orderHead);
    }

    public DesignMaterialHeadProductItem getCustomerNameAndProductNoByHeadId(Integer headId) throws Exception {
        return designMapper.getCustomerNameAndProductNoByHeadId(headId);
    }

    public DesignMaterialHead getHeadIdandInfoById(Integer id) throws Exception {
        DesignMaterialHead head = designMapper.getHeadIdandInfoById(id);
        List<DesignMaterialHeadProduct> headProduct = designMapper.getHeadIdProductById(id);
        List<DesignMaterialHeadProductItem> headProductItemList = designMapper.getHeadIdProductItemById(id);
        head.setProductList(headProduct);
        head.setProductItemList(headProductItemList);
        return head;

    }

    public DesignMaterialHeadProductItem getCustomerNameAndProductNoByHeadId2(Integer headId) throws Exception {
        return designMapper.getCustomerNameAndProductNoByHeadId2(headId);
    }

    public List<Employee> getSalor() throws Exception {
        return designMapper.getSalor();
    }

    public List<Employee> getAllMaker() throws Exception {
        return designMapper.getAllMaker();
    }

    public List<String> getAllOrderNo(String empNo) throws Exception {
        return designMapper.getAllOrderNo(empNo);
    }

    public String transferExcelToPDF(String excelUrlName) throws Exception {
        int index = excelUrlName.lastIndexOf(".");
        String centerName = excelUrlName.substring(0, index);
        WordToPDF.WordToPDFOrder(excelUrlName, centerName.concat(".pdf"), finalDirPath);
        return finalDirPath + "linshi/" + centerName.concat(".pdf");
    }

    public List<String> getAllOrderArea() throws Exception {
        return designMapper.getAllOrderArea();
    }

    public List<DesignMaterialHead> getAllDMH(DesignMaterialHead orderHead) throws Exception {
        if (orderHead.getLoginName().equals("CS2004112500")) {
            return designMapper.getAllDMH22(orderHead);
        } else {
            return designMapper.getAllDMH(orderHead);
        }
    }

    public void deleteOrderItemHByheadId(Integer id) throws Exception {
        designMapper.deleteOrderItemHByheadId(id);
    }

    public int getAllDMHCount(DesignMaterialHead head) throws Exception {
        if (head.getLoginName().equals("CS2004112500")) {
            return designMapper.getAllDMHCount22(head);
        } else {
            return designMapper.getAllDMHCount(head);
        }
    }

    public List<DesignMaterialHead> queryOrderHeadByCondition(DesignMaterialHead head) throws Exception {
        return designMapper.queryOrderHeadByCondition(head);
    }

    public int queryOrderHeadByConditionCount(DesignMaterialHead head) throws Exception {
        return designMapper.queryOrderHeadByConditionCount(head);
    }

    public int saveSJHeadDateToMysql(DesignMaterialHead dmh) throws Exception {
        if (dmh.getId() == 0) {
            int isExsit = 0;
            // int isExsit = designMapper.getSJHeadByOrderNo(dmh.getCustomerNo());
            if (isExsit == 0) {
                designMapper.saveSJHeadDateToMysql(dmh);
                isExsit = 1;
            } else {
                isExsit = 5;
            }
            return isExsit;
        } else {
            int isExsit = 0;
            //int isExsit = designMapper.getSJHeadByOrderNoButId(dmh.getCustomerNo(), dmh.getId());
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
        int isExsit = 0;
        if (item.getId() != null && item.getId() != 0) {
            int head_id = designMapper.getHeadIdByMaterialNoAndId(item.getId());
            if (item.getMateiralNo() != null && item.getMateiralNo().trim().length() > 0)
                // isExsit = designMapper.getSJIHeadByOrderNoButId(item.getMateiralNo(), item.getId(), item.getHead_product_id());
                if (isExsit == 0) {
                    item.setHead_product_id(head_id);
                    designMapper.updateSJIHeadDateToMysql(item);
                    isExsit = 3;
                } else {
                    isExsit = 5;
                }
            return isExsit;
        } else {
            if (item.getMateiralNo() != null && item.getMateiralNo().trim().length() > 0)
                // isExsit = designMapper.getSJIHeadByOrderNo(item.getCustomerNo(), item.getProductNo(), item.getMateiralNo());
                if (isExsit == 0) {
                    Integer head_id = designMapper.getHeadIdByCustomerNoAndMaterialNo(item.getCustomerNo(), item.getProductNo());
                    if (head_id == null) {
                        isExsit = 6;
                        return isExsit;
                    }
                    item.setHead_product_id(head_id);
                    designMapper.saveSJIHeadDateToMysql(item);
                    isExsit = 1;
                } else {
                    isExsit = 5;
                }
            return isExsit;
        }
    }

    public int saveSJHeadByCopy(Integer fromId, String toOrderNo) throws Exception {
        DesignMaterialHead head = designMapper.getSJbyId(fromId);
        head.setCustomerNo(toOrderNo);
        designMapper.saveSJHeadDateToMysql(head);
        List<DesignMaterialHeadProductItem> itemList = null;
        List<DesignMaterialHeadProduct> productList = designMapper.getProductListByHeadId(fromId);
        DesignMaterialHeadProduct product = null;
        DesignMaterialHeadProductItem item = null;
        for (int i = 0; i < productList.size(); i++) {
            product = productList.get(i);
            itemList = designMapper.getHeadIdProductItemById2(product.getId());
            product.setHead_id(head.getId());
            designMapper.saveSJHeadPDateToMysql(product);
            for (int j = 0; j < itemList.size(); j++) {
                item = itemList.get(j);
                item.setHead_product_id(product.getId());
                item.setMateiralStock(kingSoftStoreMapper.getNewStockByMateriId(item.getMateiralNo()));
                designMapper.saveSJIHeadDateToMysql(item);
            }
        }
        return 0;
    }


    public DesignMaterialHeadProduct saveSJIHeadBYIdToMysql(String empNo, Integer fromId, String toOrderNo) throws Exception {
        DesignMaterialHead head = designMapper.getSJbyOrderNoAndEmpno(empNo, toOrderNo);
        DesignMaterialHeadProduct product = designMapper.getHeadIdProductByIdOne(fromId);
        List<DesignMaterialHeadProductItem> itemList = null;
        DesignMaterialHeadProductItem item = null;
        if (product != null && head != null) {
            product.setHead_id(head.getId());
            designMapper.saveSJHeadPDateToMysql(product);
            itemList = designMapper.getHeadIdProductItemByIdBig(fromId);
            for (int j = 0; j < itemList.size(); j++) {
                item = itemList.get(j);
                item.setHead_product_id(product.getId());
                item.setMateiralStock(kingSoftStoreMapper.getNewStockByMateriId(item.getMateiralNo()));
                designMapper.saveSJIHeadDateToMysql(item);
            }
        }
        return product;
    }

    public List<DesignMaterialHeadProduct> queryOrderHeadProductOrderNoByCondition(String empNo, String toOrderNo) throws Exception {
        return designMapper.queryOrderHeadProductOrderNoByCondition(empNo, toOrderNo);
    }

    public int queryOrderHeadProductOrderNoByConditionCount(String empNo, String toOrderNo) throws Exception {
        return designMapper.queryOrderHeadProductOrderNoByConditionCount(empNo, toOrderNo);
    }


    public List<DesignMaterialHead> queryOrderHeadBytoOrderNo(String loginName, String orderNo) throws Exception {
        return designMapper.queryOrderHeadBytoOrderNo(loginName, orderNo);
    }

    public int queryOrderHeadBytoOrderNoCount(String loginName, String orderNo) throws Exception {
        return designMapper.queryOrderHeadBytoOrderNoCount(loginName, orderNo);
    }

    public int saveSJIListHeadPDateToMysqlLinShi(List<DesignMaterialHeadProductItem> itemList) throws Exception {
        int isExsit = 0;
        DesignMaterialHeadProductItem item;
        Integer head_id = designMapper.getHeadIdByCustomerNoAndMaterialNo(itemList.get(0).getCustomerNo().trim(), itemList.get(0).getProductNo().trim());
        if (head_id == null) {
            isExsit = 6;
            return isExsit;
        }
        designMapper.deleteItemByLinshi(head_id);
        for (int i = itemList.size() - 1; i >= 0; i--) {
            item = itemList.get(i);
            item.setHead_product_id(head_id);
            if (item.getId() != null && item.getId() != 0) {
                designMapper.updateSJIHeadDateToMysql(item);
                if (isExsit == 1)
                    isExsit = 7;
                if (isExsit != 7)
                    isExsit = 2;
            } else {
                designMapper.saveSJIHeadDateToMysql(item);
                if (isExsit == 2)
                    isExsit = 7;
                if (isExsit != 7)
                    isExsit = 1;
            }


        }
        return isExsit;
    }


    public int saveSJIListHeadPDateToMysql(List<DesignMaterialHeadProductItem> itemList) throws Exception {
        int isExsit = 0;
        DesignMaterialHeadProductItem item;
        Integer head_id = designMapper.getHeadIdByCustomerNoAndMaterialNo(itemList.get(0).getCustomerNo().trim(), itemList.get(0).getProductNo().trim());
        if (head_id == null) {
            isExsit = 6;
            return isExsit;
        }
        for (int i = itemList.size() - 1; i >= 0; i--) {
            item = itemList.get(i);
            item.setHead_product_id(head_id);
            if (item.getNum() != null && item.getNum().trim().length() == 0) {
                item.setNum("0.0");
            }
            if (item.getId() != null && item.getId() != 0) {
                designMapper.updateSJIHeadDateToMysql(item);
                if (isExsit == 1)
                    isExsit = 7;
                if (isExsit != 7)
                    isExsit = 2;
            } else {
                designMapper.saveSJIHeadDateToMysql(item);
                if (isExsit == 2)
                    isExsit = 7;
                if (isExsit != 7)
                    isExsit = 1;
            }
        }
        return isExsit;
    }


    public List<DesignMaterialHeadProductItem> getAllDMHIPByCustomerNo(DesignMaterialHeadProductItem item) throws
            Exception {
        //return designMapper.getAllDMHIPByCustomerNo(item);
        return null;
    }


    public List<DesignMaterialHeadProductItem> queryOrderHeadProductItemByCondition(DesignMaterialHeadProductItem
                                                                                            item) throws Exception {
        return designMapper.queryOrderHeadProductItemByCondition(item);
    }

    public int queryOrderHeadProductItemByConditionCount(DesignMaterialHeadProductItem item) throws Exception {
        return designMapper.queryOrderHeadProductItemByConditionCount(item);
    }

    public int getAllDMHIPByCustomerNoCount(DesignMaterialHeadProductItem item) throws Exception {
        // return designMapper.getAllDMHIPByCustomerNoCount(item);
        return 0;
    }

    public List<DesignMaterialHeadProduct> getAllDMHP(DesignMaterialHeadProduct orderHead) throws Exception {
        if (orderHead != null && orderHead.getCustomerNo() != null) {
            return designMapper.getAllDMHP2(orderHead);
        } else {
            return designMapper.getAllDMHP(orderHead);
        }
    }

    public void deleteOrderItemByheadIdItem(Integer id) throws Exception {
        designMapper.deleteOrderItemByheadIdItem(id);
    }

    public int getAllDMHPCount(DesignMaterialHeadProduct orderHead) throws Exception {
        if (orderHead != null && orderHead.getCustomerNo() != null) {
            return designMapper.getAllDMHPCount2(orderHead);
        } else {
            return designMapper.getAllDMHPCount(orderHead);
        }
    }

    public List<String> getAllproductNameList() throws Exception {
        return designMapper.getAllproductNameList();
    }

    public List<String> getAllproductNoList(String customerNo) throws Exception {
        return designMapper.getAllproductNoList(customerNo);
    }

    public List<String> getAlldrawingNoList() throws Exception {
        return designMapper.getAlldrawingNoList();
    }

    public List<DesignMaterialHeadProduct> queryOrderHeadProductByCondition(DesignMaterialHeadProduct product) throws
            Exception {
        return designMapper.queryOrderHeadProductByCondition(product);
    }

    public int queryOrderHeadProductByConditionCount(DesignMaterialHeadProduct product) throws Exception {
        return designMapper.queryOrderHeadProductByConditionCount(product);
    }

    public int saveSJHeadPDateToMysql(DesignMaterialHeadProduct pmhp) throws Exception {
        if (pmhp != null && pmhp.getProductTypeName() != null && pmhp.getProductTypeName().trim().length() > 0) {
            pmhp.setProductTypeNo(kingSoftStoreMapper.getProductNoByName(pmhp.getProductTypeName().trim()));
        }
        if (pmhp.getId() == 0) {
            Integer isExsit = 0;
            if (isExsit == null || isExsit == 0) {
                pmhp.setHead_id(pmhp.getHead_id());
                designMapper.saveSJHeadPDateToMysql(pmhp);
                isExsit = 1;
            } else {
                isExsit = 5;
            }
            return isExsit;
        } else {
            Integer isExsit = 0;
            if (isExsit == null || isExsit == 0) {
                pmhp.setHead_id(pmhp.getHead_id());
                designMapper.updateSJHeadPDateToMysql(pmhp);
                isExsit = 3;
            } else {
                isExsit = 5;
            }
            return isExsit;
        }
    }

    public List<DesignMaterialHeadProduct> getAllDMHPByCustomerNo(DesignMaterialHeadProduct pmhp) throws Exception {
        pmhp.setId(pmhp.getHead_id());
        return designMapper.getAllDMHP(pmhp);
    }

    public int getAllDMHPByCustomerNoCount(DesignMaterialHeadProduct pmhp) throws Exception {
        pmhp.setId(pmhp.getHead_id());
        return designMapper.getAllDMHPCount(pmhp);

    }


}
