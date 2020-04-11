package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.PersonMapper;
import com.cosun.cosunp.mapper.ProjectMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IProjectServ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2020/3/23 0023 下午 5:33
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectServiceImpl implements IProjectServ {

    private static Logger logger = LogManager.getLogger(ProjectServiceImpl.class);

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<ProjectHead> totalProjectNumByEmpNo(String empNo) throws Exception {
        return projectMapper.totalProjectNumByEmpNo(empNo);
    }

    @Override
    public List<DaysSet> getDaysSetNew() throws Exception {
        return projectMapper.getDaysSetNew();
    }

    @Override
    public List<ProjectHeadOrder> getTotalProjectOrderByName(String userid, String projectName) throws Exception {
        return projectMapper.getTotalProjectOrderByName(userid, projectName);
    }

    @Override
    public void updateAlertSet2(DaysSet ds) throws Exception {
        projectMapper.updateAlertSet2(ds);
    }

    @Override
    public void updateAlertSet1(DaysSet ds) throws Exception {
        projectMapper.updateAlertSet1(ds);
    }

    @Override
    public List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderS(String userid, String orderNo) throws Exception {
        return projectMapper.getTotalProjectOrderITEMByOrderS(userid, orderNo);
    }

    @Override
    public ProjectHeadOrderItem getTotalProjectOrderITEMMoreByOrderS(String userid, String productName, String orderNo) throws Exception {
        return projectMapper.getTotalProjectOrderITEMMoreByOrderS(userid, productName, orderNo);
    }

    @Override
    public int findNameRepeatOrNot(String userid, String projectName) throws Exception {
        return projectMapper.findNameRepeatOrNot(userid, projectName);
    }

    @Override
    public void saveProjectByNameAndRemark(String userid, String projectName, String remark) throws Exception {
        projectMapper.saveProjectByNameAndRemark(userid, projectName, remark);
    }

    @Override
    public List<China> getAllMainProvince() throws Exception {
        return projectMapper.getAllMainProvince();
    }

    @Override
    public ProjectHeadOrder checkOrderNoRepeat(String userId, String orderNo) throws Exception {
        return projectMapper.checkOrderNoRepeat(userId, orderNo);
    }

    @Override
    public void saveProjectHeadByBean(Integer provinceId, String userId, String orderNo, String customerName,
                                      String projectName, Integer id, Integer newOrOld) throws Exception {
        projectMapper.saveProjectHeadByBean(provinceId, userId, orderNo, customerName, projectName, id, newOrOld);
    }

    @Override
    public List<Position> getAllAlertPositions() throws Exception {
        return projectMapper.getAllAlertPositions();
    }

    @Override
    public void updateAlertSet(AlertSet as) throws Exception {
        projectMapper.updateAlertSet(as);
    }

    @Override
    public AlertSet getAlertSet() throws Exception {
        return projectMapper.getAlertSet();
    }

    @Override
    public ProjectHead findPHbyName(String projectName, String userId) throws Exception {
        return projectMapper.findPHbyName(projectName, userId);
    }

    @Override
    public ProjectHeadOrderItem checkProductRepeatForOneOrder(Double hetongMoney, String orderNo, String productName) throws Exception {
        return projectMapper.checkProductRepeatForOneOrder(hetongMoney, orderNo, productName);
    }

    @Override
    public ProjectHeadOrder findPHObyOrderNo(String orderNo) throws Exception {
        return projectMapper.findPHObyOrderNo(orderNo);
    }

    @Override
    public void saveProjectHeadItemByBean(Double hetongMoney, Integer id, String productName, String totalBao, String date) throws Exception {
        projectMapper.saveProjectHeadItemByBean(hetongMoney, id, productName, totalBao, date);
    }

    @Override
    public void saveOrderItemMor(Integer id, ProjectHeadOrderItem phoi) throws Exception {
        ProjectHeadOrderItem oldItem = projectMapper.getOldHeadOrderItemByPhoi(phoi);
        boolean isHaveDate = false;
        out:
        if (oldItem != null) {
            if (oldItem.getGetOrder_Date_PlanStr() != null && oldItem.getGetOrder_Date_PlanStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getGetOrder_Date_AccuStr() != null && oldItem.getGetOrder_Date_AccuStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getZhanCha_Date_PlanStr() != null && oldItem.getZhanCha_Date_PlanStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getZhanCha_Date_AccuStr() != null && oldItem.getZhanCha_Date_AccuStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getOutDraw_Date_AccuStr() != null && oldItem.getOutDraw_Date_AccuStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getOutDraw_Date_PlanStr() != null && oldItem.getOutDraw_Date_PlanStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getProgram_confir_Date_AccuStr() != null && oldItem.getProgram_confir_Date_AccuStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getProgram_confir_Date_PlanStr() != null && oldItem.getProgram_confir_Date_PlanStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getGiveOrder_Date_AccuStr() != null && oldItem.getGiveOrder_Date_AccuStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getGiveOrder_Date_PlanStr() != null && oldItem.getGiveOrder_Date_PlanStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getDelivery_Goods_Date_AccuStr() != null && oldItem.getDelivery_Goods_Date_AccuStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getDelivery_Goods_Date_PlanStr() != null && oldItem.getDelivery_Goods_Date_PlanStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getInstall_Date_AccuStr() != null && oldItem.getInstall_Date_AccuStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getInstall_Date_PlanStr() != null && oldItem.getInstall_Date_PlanStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getYanShou_Date_AccuStr() != null && oldItem.getYanShou_Date_AccuStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getYanShou_Date_PlanStr() != null && oldItem.getYanShou_Date_PlanStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getJieSuan_Date_AccuStr() != null && oldItem.getJieSuan_Date_AccuStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            } else if (oldItem.getJieSuan_Date_PlanStr() != null && oldItem.getJieSuan_Date_PlanStr().trim().length() > 0) {
                isHaveDate = true;
                break out;
            }

        }

        if (isHaveDate) {
            phoi.setVersion(oldItem.getVersion() + 0.1);
            phoi.setSaleManager(oldItem.getSaleManager());
            phoi.setGendan(oldItem.getGendan());
            phoi.setRemark(oldItem.getRemark());
            phoi.setJindu_remark(oldItem.getJindu_remark());
            phoi.setWeiHuiMoney(oldItem.getWeiHuiMoney());
            phoi.setHereMoney(oldItem.getHereMoney());
            phoi.setHetongMoney(oldItem.getHetongMoney());
            phoi.setChecked(oldItem.getChecked());
            phoi.setProduct_Name(oldItem.getProduct_Name());
            phoi.setTotalBao(oldItem.getTotalBao());
            phoi.setDelivery_DateStr(oldItem.getDelivery_DateStr());
            phoi.setOrder_Id(oldItem.getOrder_Id());
            projectMapper.saveOrderItemMor(phoi);
        } else {
            phoi.setVersion(1.0);
            projectMapper.updateOrderItemMor(phoi);
        }


    }

    @Override
    public ProjectHeadOrder getProjectOrderByOrderNo(String orderNo) throws Exception {
        return projectMapper.getProjectOrderByOrderNo(orderNo);
    }

    @Override
    public void updateOrderNoRepeat(ProjectHeadOrder pho) throws Exception {
        projectMapper.updateOrderNoRepeat(pho);
    }

    @Override
    public void deleteProjectDetai(String projectName, String userid) throws Exception {
        projectMapper.deleteProjectDetai(projectName, userid);
    }

    @Override
    public ProjectHead getProjectByName(ProjectHead ph) throws Exception {
        return projectMapper.getProjectByName(ph);
    }

    @Override
    public int findNameRepeatOrNot2(ProjectHead ph) throws Exception {
        return projectMapper.findNameRepeatOrNot2(ph);
    }

    @Override
    public void updateProjectByNameAndRemark(ProjectHead ph) throws Exception {
        projectMapper.updateProjectByNameAndRemark(ph);
    }

    @Override
    public void deleteProjectDetai2(String orderNo, String userId) throws Exception {
        projectMapper.deleteProjectDetai2(orderNo, userId);
    }

    @Override
    public void updateProjectOrderItemByNameAndRemark(ProjectHeadOrderItem item) throws Exception {
        projectMapper.updateProjectOrderItemByNameAndRemark(item);
    }

    @Override
    public ProjectHeadOrderItem getProjectOrderItemByOrderNoAndProductName(ProjectHeadOrderItem phoi) throws Exception {
        return projectMapper.getProjectOrderItemByOrderNoAndProductName(phoi);
    }


    @Override
    public void deleteProjectDetaiItem(Integer id, String userid) throws Exception {
        projectMapper.deleteProjectDetaiItem(id, userid);
    }

    @Override
    public void updateProjectHeadOrderItem(ProjectHeadOrderItem phoi) throws Exception {
        projectMapper.updateProjectHeadOrderItem(phoi);
    }

    @Override
    public List<Employee> getSalorMItems() throws Exception {
        return projectMapper.getSalorMItems();
    }

    @Override
    public List<Employee> getGenDanItems() throws Exception {
        return projectMapper.getGenDanItems();
    }

    @Override
    public String returnNameByEmpNoStr(String empNoStr) throws Exception {
        projectMapper.returnNameByEmpNoStrBefore(empNoStr);
        return projectMapper.returnNameByEmpNoStr(empNoStr);
    }

    @Override
    public List<ProjectHeadOrderItem> getHistoryItemByProduct_NameAndOrderNo(String product_Name, String orderNo) throws Exception {
        return projectMapper.getHistoryItemByProduct_NameAndOrderNo(product_Name, orderNo);
    }

    @Override
    public ProjectHeadOrderItem getProjectItemHistoryById(Integer id) throws Exception {
        return projectMapper.getProjectItemHistoryById(id);
    }

    @Override
    public List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderSAll() throws Exception {
        return projectMapper.getTotalProjectOrderITEMByOrderSAll();
    }

    @Override
    public void updateProjectItemCheckById(Integer id, int checked) throws Exception {
        projectMapper.updateProjectItemCheckById(id, checked);
    }

    @Override
    public List<ProjectHeadOrderItem> getAllitemList() throws Exception {
        return projectMapper.getAllitemList();
    }

    public List<ProjectHead> getProjectBySearch(String projectName) throws Exception {
        return projectMapper.getProjectBySearch(projectName);
    }

    public int getCustomerNameByNameAndOrderNo(ProjectHeadOrder pho) throws Exception {
        return projectMapper.getCustomerNameByNameAndOrderNo(pho);
    }

    public List<ProjectItemMoneyRecord> getTotalProjectOrderMoneyRecordByNo(String orderNo) throws Exception {
        return projectMapper.getTotalProjectOrderMoneyRecordByNo(orderNo);
    }

    public void saveHereMoneyBYItemId(ProjectItemMoneyRecord pho) throws Exception {
        projectMapper.saveHereMoneyBYItemId(pho);
    }

    public void deleteProjectRecordById(Integer id) throws Exception {
        projectMapper.deleteProjectRecordById(id);
    }

    public void updatePHOIMoney(Integer itemId, Double hereMoney) throws Exception {
        projectMapper.updatePHOIMoney(itemId, hereMoney);
    }

    public void updateProjectRecordByBean(ProjectItemMoneyRecord record) throws Exception {
        projectMapper.updateProjectRecordByBean(record);
        projectMapper.updateProjectItemByItemId(record);

    }

    public ProjectItemMoneyRecord getProjectRecordById(Integer id) throws Exception {
        return projectMapper.getProjectRecordById(id);
    }
}
