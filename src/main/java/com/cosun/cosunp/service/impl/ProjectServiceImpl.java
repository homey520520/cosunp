package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.PersonMapper;
import com.cosun.cosunp.mapper.ProjectMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IProjectServ;
import com.cosun.cosunp.tool.StringUtil;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.WorkbookSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

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


    //getNamesByEmpNos


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
    public List<Employee> getSheJiItems() throws Exception {
        return projectMapper.getSheJiItems();
    }

    @Override
    public List<Employee> getAnzhuangList() throws Exception {
        return projectMapper.getAnzhuangList();
    }

    @Override
    public List<Employee> getYeWuList() throws Exception {
        return projectMapper.getYeWuList();
    }

    @Override
    public List<ProjectHeadOrderItem> getTotalProjectOrderITEMByOrderS(String customer_Name, String userid, String orderNo) throws Exception {
        return projectMapper.getTotalProjectOrderITEMByOrderS(customer_Name, userid, orderNo);
    }

    @Override
    public ProjectHeadOrderItem getTotalProjectOrderITEMMoreByOrderS(String userid, String projectName, String customer_Name, String productName) throws Exception {
        return projectMapper.getTotalProjectOrderITEMMoreByOrderS(userid, projectName, customer_Name, productName);
    }


    @Override
    public ProjectHeadOrderItem getTotalProjectOrderITEMMoreById(Integer id) throws Exception {
        return projectMapper.getTotalProjectOrderITEMMoreById(id);
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
    public ProjectHeadOrder checkOrderNoRepeat(String projectName, String customerName, String userId, String orderNo) throws Exception {
        return projectMapper.checkOrderNoRepeat(projectName, customerName, userId, orderNo);
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
    public ProjectHeadOrderItem saveOrderItemMor(Integer id, ProjectHeadOrderItem phoi) throws Exception {
        ProjectHeadOrderItem oldItem = projectMapper.getOldHeadOrderItemByPhoi(phoi);
        Integer newItem = null;
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
            projectMapper.updateProjectImage(oldItem.getId(), phoi.getId());
            return oldItem;
        } else {
            phoi.setVersion(1.0);
            projectMapper.updateOrderItemMor(phoi);
            return oldItem;
        }

    }


    @Override
    public List<String> getNamesByEmpNos(List<String> nameStr) throws Exception {
        return projectMapper.getNamesByEmpNos(nameStr);
    }


    @Override
    public void updateProjectItemByBeanOnlyDateAndNote(ProjectHeadOrderItem item) throws Exception {
        projectMapper.updateProjectItemByBeanOnlyDateAndNote(item);
    }


    @Override
    public List<ProjectHeadOrderItem> getProjectItemAllByCondi() throws Exception {
        return projectMapper.getProjectItemAllByCondi();
    }

    @Override
    public List<ProjectHeadOrderItem> getItemListByOrderStatusAndCheckAndEmpnoIn(String empNo) throws Exception {
        return projectMapper.getItemListByOrderStatusAndCheckAndEmpnoIn(empNo);
    }


    @Override
    public List<Leave> getAllLeaveDataByBeBoreDayApply(String day) throws Exception {
        return projectMapper.getAllLeaveDataByBeBoreDayApply(day);
    }


    @Override
    public List<ProjectHeadOrderItem> getProjectItemAllByCondi2() throws Exception {
        return projectMapper.getProjectItemAllByCondi2();
    }

    @Override
    public List<String> getUserIdByEmpNos(List<String> empNoList) throws Exception {
        return projectMapper.getUserIdByEmpNos(empNoList);
    }

    @Override
    public String getEmpNoByUserId(String userId) throws Exception {
        return projectMapper.getEmpNoByUserId(userId);
    }


    @Override
    public List<ProjectHeadOrderItem> getAllItemByUserIdAndNoFinish(String userId) throws Exception {
        return projectMapper.getAllItemByUserIdAndNoFinish(userId);
    }


    @Override
    public List<ProjectHeadOrderItem> getAllItemByUserIdAndNoFinish2(String userId) throws Exception {
        return projectMapper.getAllItemByUserIdAndNoFinish2(userId);
    }


    @Override
    public List<String> getAllCustomerName() throws Exception {
        return projectMapper.getAllCustomerName();
    }

    @Override
    public List<String> getAllProjectName() throws Exception {
        return projectMapper.getAllProjectName();
    }

    @Override
    public List<Employee> findAllProjectSalorByDeptName1() throws Exception {
        return projectMapper.findAllProjectSalorByDeptName1();
    }

    @Override
    public List<String> getAllOrderNoList() throws Exception {
        return projectMapper.getAllOrderNoList();
    }


    @Override
    public List<ProjectHeadOrderItem> queryProjectOrderItemByCondition(ProjectHeadOrderItem item) throws Exception {
        return projectMapper.queryProjectOrderItemByCondition(item);
    }

    @Override
    public int queryProjectOrderItemByConditionCount(ProjectHeadOrderItem item) throws Exception {
        return projectMapper.queryProjectOrderItemByConditionCount(item);
    }


    @Override
    public void saveOrderItemMorOld(Integer id, ProjectHeadOrderItem phoi) throws Exception {
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
            projectMapper.saveOrderItemMorOld(phoi);
        } else {
            phoi.setVersion(1.0);
            projectMapper.updateOrderItemMorOld(phoi);
        }
    }


    @Override
    public Integer getUserActorByUserId(String userId) throws Exception {
        return projectMapper.getUserActorByUserId(userId);
    }


    @Override
    public ProjectHeadOrderItem getProjectOrderItemById(Integer id) throws Exception {
        return projectMapper.getProjectOrderItemById(id);
    }

    @Override
    public ProjectHeadOrder getProjectOrderByOrderNo(String projectName, String customerName, String orderNo) throws Exception {
        return projectMapper.getProjectOrderByOrderNo(projectName, customerName, orderNo);
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
        //projectMapper.returnNameByEmpNoStrBefore(empNoStr);
        List<String> allEmpNlist = null;
        if (empNoStr != null && empNoStr.trim().length() > 0) {
            allEmpNlist = Arrays.asList(empNoStr.trim().split(","));
            return projectMapper.returnNameByEmpNoStr(allEmpNlist);
        }
        return "";
    }

    @Override
    public List<ProjectHeadOrderItem> getHistoryItemByProduct_NameAndOrderNo(String product_Name, String orderNo, String projectName, String customerName) throws Exception {
        return projectMapper.getHistoryItemByProduct_NameAndOrderNo(product_Name, orderNo, projectName, customerName);
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

    public List<ProjectHead> totalProjectNumBy() throws Exception {
        return projectMapper.totalProjectNumBy();
    }

    public ProjectItemMoneyRecord getProjectRecordById(Integer id) throws Exception {
        return projectMapper.getProjectRecordById(id);
    }


    @Override
    public ProjectHeadOrder getProjectOrderById(Integer id) throws Exception {
        return projectMapper.getProjectOrderById(id);
    }

    @Override
    public DaysSet getDaysSetByType(int type) throws Exception {
        return projectMapper.getDaysSetByType(type);
    }

    @Override
    public List<String> getFaDingList() throws Exception {
        return projectMapper.getFaDingList();
    }

    public List<Employee> findAllProjectSalorByDeptName() throws Exception {
        return projectMapper.findAllProjectSalorByDeptName();
    }

    @Override
    public String getUserNameByUserId(String userId) throws Exception {
        return projectMapper.getUserNameByUserId(userId);
    }

    @Override
    public void translateTabletoEmployeeBeanProject(MultipartFile file) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        WorkbookSettings ws = new WorkbookSettings();
        String fileName = file.getOriginalFilename();
        ws.setCellValidationDisabled(true);
        jxl.Workbook Workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (fileType.equals("xls")) {
            Workbook = jxl.Workbook.getWorkbook(file.getInputStream(), ws);
        } else {
            throw new Exception("文档格式后缀不正确!!！只接受xls格式.");
        }
        jxl.Sheet xlsfSheet = null;
        jxl.Sheet xlsfSheet2 = null;
        jxl.Sheet xlsfSheet3 = null;
        if (Workbook != null) {
            jxl.Sheet[] sheets = Workbook.getSheets();
            xlsfSheet = sheets[0];
            xlsfSheet2 = sheets[1];
            xlsfSheet3 = sheets[2];
        }
        List<ProjectHeadOrderItem> itemList = new ArrayList<ProjectHeadOrderItem>();
        ProjectHeadOrderItem item = null;
        int rowNums = xlsfSheet.getRows();
        int rowNums2 = xlsfSheet2.getRows();
        int rowNums3 = xlsfSheet3.getRows();
        jxl.Cell[] cell = null;
        jxl.Cell[] cella = null;
        Date transferDate = null;
        DateCell dc = null;
        Date date = null;
        Calendar c = null;
        ProjectHead ph = null;
        ProjectHeadOrder pho = null;

        if (rowNums > 0) {
            for (int i = 3; i < rowNums; i++) {
                cell = xlsfSheet.getRow(i);
                cella = xlsfSheet.getRow(++i);
                if (cell != null && cell.length > 0) {
                    item = new ProjectHeadOrderItem();
                    item.setProvinceStr(cell[1].getContents().trim());
                    item.setProjectName("海底捞");
                    item.setSalor("LiangQiuLiang");
                    item.setCustomer_Name(cell[2].getContents().trim());
                    if (cell[3].getType() != CellType.EMPTY)
                        item.setOrderNo(cell[3].getContents().trim());
                    if (cell[4].getType() != CellType.EMPTY)
                        item.setProduct_Name(cell[4].getContents().trim());
                    if (cell[5].getType() != CellType.EMPTY)
                        item.setDelivery_DateStr(changeNumToDate(cell[5].getContents().trim()));
                    if (cell[7].getType() != CellType.EMPTY)
                        item.setGetOrder_Date_PlanStr(changeNumToDate(cell[7].getContents().trim()));
                    if (cell[8].getType() != CellType.EMPTY)
                        item.setZhanCha_Date_PlanStr(changeNumToDate(cell[8].getContents().trim()));
                    if (cella[8].getType() != CellType.EMPTY)
                        item.setZhanCha_Date_AccuStr(changeNumToDate(cella[8].getContents().trim()));
                    if (cell[9].getType() != CellType.EMPTY)
                        item.setOutDraw_Date_PlanStr(changeNumToDate(cell[9].getContents().trim()));
                    if (cella[9].getType() != CellType.EMPTY)
                        item.setOutDraw_Date_AccuStr(changeNumToDate(cella[9].getContents().trim()));
                    if (cell[10].getType() != CellType.EMPTY)
                        item.setProgram_confir_Date_PlanStr(changeNumToDate(cell[10].getContents().trim()));
                    if (cella[10].getType() != CellType.EMPTY)
                        item.setProgram_confir_Date_AccuStr(changeNumToDate(cella[10].getContents().trim()));
                    if (cell[11].getType() != CellType.EMPTY)
                        item.setGiveOrder_Date_PlanStr(changeNumToDate(cell[11].getContents().trim()));
                    if (cella[11].getType() != CellType.EMPTY)
                        item.setGiveOrder_Date_AccuStr(changeNumToDate(cella[11].getContents().trim()));
                    if (cell[12].getType() != CellType.EMPTY)
                        item.setDelivery_Goods_Date_PlanStr(changeNumToDate(cell[12].getContents().trim()));
                    if (cella[12].getType() != CellType.EMPTY)
                        item.setDelivery_Goods_Date_AccuStr(changeNumToDate(cella[12].getContents().trim()));
                    if (cell[13].getType() != CellType.EMPTY)
                        item.setInstall_Date_PlanStr(changeNumToDate(cell[13].getContents().trim()));
                    if (cella[13].getType() != CellType.EMPTY)
                        item.setInstall_Date_AccuStr(changeNumToDate(cella[13].getContents().trim()));
                    if (cell[14].getType() != CellType.EMPTY)
                        item.setYanShou_Date_PlanStr(changeNumToDate(cell[14].getContents().trim()));
                    if (cella[14].getType() != CellType.EMPTY)
                        item.setYanShou_Date_AccuStr(changeNumToDate(cella[14].getContents().trim()));
                    if (cell[16].getType() != CellType.EMPTY)
                        item.setJindu_remark(cell[16].getContents().trim());
                    if (cell[17].getType() != CellType.EMPTY)
                        item.setTotalBao(cell[17].getContents().trim());
                    if (cell[18].getType() != CellType.EMPTY)
                        item.setHetongMoney(Double.valueOf(cell[18].getContents().trim()));
                    if (cell[19].getType() != CellType.EMPTY)
                        item.setHereMoney(Double.valueOf(cell[19].getContents().trim()));
                    if (cell[20].getType() != CellType.EMPTY)
                        item.setWeiHuiMoney(Double.valueOf(cell[20].getContents().trim()));
                    if (cell[21].getType() != CellType.EMPTY)
                        item.setRemark(cell[21].getContents().trim());
                }
                itemList.add(item);

            }
        }


        for (ProjectHeadOrderItem ite : itemList) {
            ph = projectMapper.getProjectByNameAndSalor(ite.getProjectName(), ite.getSalor());
            if (ph == null) {
                ph = new ProjectHead();
                ph.setProjectName(ite.getProjectName());
                ph.setSalor(ite.getSalor());
                projectMapper.saveProjectHeadByBeanA(ph);
            }

            pho = projectMapper.getPHOByHeadIdAndOrderNoAndCstomerName(ph.getId(), ite.getCustomer_Name(), ite.getOrderNo());
            if (pho == null) {
                pho = new ProjectHeadOrder();
                pho.setHead_Id(ph.getId());
                pho.setCustomerName(ite.getCustomer_Name());
                pho.setNewOrOld(1);
                pho.setOrderNo(ite.getOrderNo() == null ? "" : ite.getOrderNo());
                pho.setProvince(projectMapper.getProvinceIdByName(ite.getProvinceStr().trim()));
                pho.setRemark(ite.getRemark());
                projectMapper.savePHOByBean(pho);
            }


            item = new ProjectHeadOrderItem();
            item.setOrder_Id(pho.getId());
            item.setProduct_Name(ite.getProduct_Name());
            item.setDelivery_DateStr(ite.getDelivery_DateStr());
            item.setGetOrder_Date_PlanStr(ite.getGetOrder_Date_PlanStr());
            item.setZhanCha_Date_PlanStr(ite.getZhanCha_Date_PlanStr());
            item.setZhanCha_Date_AccuStr(ite.getZhanCha_Date_AccuStr());
            item.setOutDraw_Date_PlanStr(ite.getOutDraw_Date_PlanStr());
            item.setOutDraw_Date_AccuStr(ite.getOutDraw_Date_AccuStr());
            item.setProgram_confir_Date_PlanStr(ite.getProgram_confir_Date_PlanStr());
            item.setProgram_confir_Date_AccuStr(ite.getProgram_confir_Date_AccuStr());
            item.setGiveOrder_Date_PlanStr(ite.getGiveOrder_Date_PlanStr());
            item.setGiveOrder_Date_AccuStr(ite.getGiveOrder_Date_AccuStr());
            item.setDelivery_Goods_Date_PlanStr(ite.getDelivery_Goods_Date_PlanStr());
            item.setDelivery_Goods_Date_AccuStr(ite.getDelivery_Goods_Date_AccuStr());
            item.setInstall_Date_PlanStr(ite.getInstall_Date_PlanStr());
            item.setInstall_Date_AccuStr(ite.getInstall_Date_AccuStr());
            item.setYanShou_Date_PlanStr(ite.getYanShou_Date_PlanStr());
            item.setYanShou_Date_AccuStr(ite.getYanShou_Date_AccuStr());
            item.setJindu_remark(ite.getJindu_remark());
            item.setTotalBao(ite.getTotalBao());
            item.setHetongMoney(ite.getHetongMoney());
            item.setHereMoney(ite.getHereMoney());
            item.setWeiHuiMoney(ite.getWeiHuiMoney());
            item.setRemark(ite.getRemark());
            item.setVersion(1.0);
            projectMapper.saveOrderItemMor(item);
        }

        itemList.clear();
        if (rowNums2 > 0) {
            for (int i = 3; i < rowNums2; i++) {
                cell = xlsfSheet2.getRow(i);
                if (cell != null && cell.length > 0) {
                    item = new ProjectHeadOrderItem();
                    item.setProvinceStr(cell[1].getContents().trim());
                    item.setProjectName("海底捞");
                    item.setSalor("LiangQiuLiang");
                    item.setCustomer_Name(cell[2].getContents().trim());
                    if (cell[3].getType() != CellType.EMPTY)
                        item.setOrderNo(cell[3].getContents().trim());
                    if (cell[4].getType() != CellType.EMPTY)
                        item.setProduct_Name(cell[4].getContents().trim());
                    if (cell[5].getType() != CellType.EMPTY)
                        item.setDelivery_DateStr(changeNumToDate(cell[5].getContents().trim()));
                    if (cell[7].getType() != CellType.EMPTY)
                        item.setGetOrder_Date_PlanStr(changeNumToDate(cell[7].getContents().trim()));
                    if (cell[8].getType() != CellType.EMPTY)
                        item.setZhanCha_Date_AccuStr(changeNumToDate(cell[8].getContents().trim()));
                    if (cell[9].getType() != CellType.EMPTY)
                        item.setOutDraw_Date_AccuStr(changeNumToDate(cell[9].getContents().trim()));
                    if (cell[10].getType() != CellType.EMPTY)
                        item.setProgram_confir_Date_AccuStr(changeNumToDate(cell[10].getContents().trim()));
                    if (cell[11].getType() != CellType.EMPTY)
                        item.setGiveOrder_Date_AccuStr(changeNumToDate(cell[11].getContents().trim()));
                    if (cell[12].getType() != CellType.EMPTY)
                        item.setDelivery_Goods_Date_AccuStr(changeNumToDate(cell[12].getContents().trim()));
                    if (cell[13].getType() != CellType.EMPTY)
                        item.setInstall_Date_AccuStr(changeNumToDate(cell[13].getContents().trim()));
                    if (cell[14].getType() != CellType.EMPTY)
                        item.setYanShou_Date_AccuStr(changeNumToDate(cell[14].getContents().trim()));
                    if (cell[15].getType() != CellType.EMPTY)
                        item.setStatus(cell[15].getContents().trim().equals("是") ? 1 : 0);
                    if (cell[16].getType() != CellType.EMPTY)
                        item.setJindu_remark(cell[16].getContents().trim());
                    if (cell[17].getType() != CellType.EMPTY)
                        item.setTotalBao(cell[17].getContents().trim());
                    if (cell[18].getType() != CellType.EMPTY)
                        item.setHetongMoney(Double.valueOf(cell[18].getContents().trim()));
                    if (cell[19].getType() != CellType.EMPTY)
                        item.setHereMoney(Double.valueOf(cell[19].getContents().trim()));
                    if (cell[20].getType() != CellType.EMPTY)
                        item.setWeiHuiMoney(Double.valueOf(cell[20].getContents().trim()));
                    if (cell[21].getType() != CellType.EMPTY)
                        item.setRemark(cell[21].getContents().trim());
                }
                itemList.add(item);
            }
        }


        for (ProjectHeadOrderItem ite : itemList) {
            ph = projectMapper.getProjectByNameAndSalor(ite.getProjectName(), ite.getSalor());
            if (ph == null) {
                ph = new ProjectHead();
                ph.setProjectName(ite.getProjectName());
                ph.setSalor(ite.getSalor());
                projectMapper.saveProjectHeadByBeanA(ph);
            }

            pho = projectMapper.getPHOByHeadIdAndOrderNoAndCstomerName(ph.getId(), ite.getCustomer_Name(), ite.getOrderNo());
            if (pho == null) {
                pho = new ProjectHeadOrder();
                pho.setHead_Id(ph.getId());
                pho.setCustomerName(ite.getCustomer_Name());
                pho.setNewOrOld(1);
                pho.setOrderNo(ite.getOrderNo() == null ? "" : ite.getOrderNo());
                pho.setProvince(projectMapper.getProvinceIdByName(ite.getProvinceStr().trim()));
                pho.setRemark(ite.getRemark());
                projectMapper.savePHOByBean(pho);
            }


            item = new ProjectHeadOrderItem();
            item.setOrder_Id(pho.getId());
            item.setProduct_Name(ite.getProduct_Name());
            item.setDelivery_DateStr(ite.getDelivery_DateStr());
            item.setGetOrder_Date_PlanStr(ite.getGetOrder_Date_PlanStr());
            item.setZhanCha_Date_PlanStr(ite.getZhanCha_Date_PlanStr());
            item.setZhanCha_Date_AccuStr(ite.getZhanCha_Date_AccuStr());
            item.setOutDraw_Date_PlanStr(ite.getOutDraw_Date_PlanStr());
            item.setOutDraw_Date_AccuStr(ite.getOutDraw_Date_AccuStr());
            item.setProgram_confir_Date_PlanStr(ite.getProgram_confir_Date_PlanStr());
            item.setProgram_confir_Date_AccuStr(ite.getProgram_confir_Date_AccuStr());
            item.setGiveOrder_Date_PlanStr(ite.getGiveOrder_Date_PlanStr());
            item.setGiveOrder_Date_AccuStr(ite.getGiveOrder_Date_AccuStr());
            item.setDelivery_Goods_Date_PlanStr(ite.getDelivery_Goods_Date_PlanStr());
            item.setDelivery_Goods_Date_AccuStr(ite.getDelivery_Goods_Date_AccuStr());
            item.setInstall_Date_PlanStr(ite.getInstall_Date_PlanStr());
            item.setInstall_Date_AccuStr(ite.getInstall_Date_AccuStr());
            item.setYanShou_Date_PlanStr(ite.getYanShou_Date_PlanStr());
            item.setYanShou_Date_AccuStr(ite.getYanShou_Date_AccuStr());
            item.setJindu_remark(ite.getJindu_remark());
            item.setTotalBao(ite.getTotalBao());
            item.setHetongMoney(ite.getHetongMoney());
            item.setHereMoney(ite.getHereMoney());
            item.setWeiHuiMoney(ite.getWeiHuiMoney());
            item.setRemark(ite.getRemark());
            item.setVersion(1.0);
            projectMapper.saveOrderItemMor(item);
        }

        itemList.clear();
        if (rowNums3 > 0) {
            for (int i = 3; i < rowNums3; i++) {
                cell = xlsfSheet3.getRow(i);
                if (cell != null && cell.length > 0) {
                    item = new ProjectHeadOrderItem();
                    item.setProvinceStr(cell[1].getContents().trim());
                    item.setProjectName("海底捞");
                    item.setSalor("LiangQiuLiang");
                    item.setCustomer_Name(cell[2].getContents().trim());
                    if (cell[3].getType() != CellType.EMPTY)
                        item.setDelivery_DateStr(changeNumToDate(cell[3].getContents().trim()));
                    if (cell[4].getType() != CellType.EMPTY)
                        item.setProduct_Name(cell[4].getContents().trim());
                    item.setStatus(1);
                    if (cell[14].getType() != CellType.EMPTY)
                        item.setJindu_remark(cell[14].getContents().trim());
                    if (cell[15].getType() != CellType.EMPTY)
                        item.setTotalBao(cell[15].getContents().trim());
                    if (cell[16].getType() != CellType.EMPTY)
                        item.setHetongMoney(Double.valueOf(cell[16].getContents().trim()));
                    if (cell[17].getType() != CellType.EMPTY)
                        item.setHereMoney(Double.valueOf(cell[17].getContents().trim()));
                    if (cell[18].getType() != CellType.EMPTY)
                        item.setWeiHuiMoney(Double.valueOf(cell[18].getContents().trim()));
                    if (cell[20].getType() != CellType.EMPTY)
                        item.setRemark(cell[20].getContents().trim());
                }
                itemList.add(item);
            }
        }


        for (ProjectHeadOrderItem ite : itemList) {
            ph = projectMapper.getProjectByNameAndSalor(ite.getProjectName(), ite.getSalor());
            if (ph == null) {
                ph = new ProjectHead();
                ph.setProjectName(ite.getProjectName());
                ph.setSalor(ite.getSalor());
                projectMapper.saveProjectHeadByBeanA(ph);
            }

            pho = projectMapper.getPHOByHeadIdAndOrderNoAndCstomerName(ph.getId(), ite.getCustomer_Name(), ite.getOrderNo());
            if (pho == null) {
                pho = new ProjectHeadOrder();
                pho.setHead_Id(ph.getId());
                pho.setCustomerName(ite.getCustomer_Name());
                pho.setNewOrOld(1);
                pho.setOrderNo(ite.getOrderNo() == null ? "" : ite.getOrderNo());
                pho.setProvince(projectMapper.getProvinceIdByName(ite.getProvinceStr().trim()));
                pho.setRemark(ite.getRemark());
                projectMapper.savePHOByBean(pho);
            }


            item = new ProjectHeadOrderItem();
            item.setOrder_Id(pho.getId());
            item.setProduct_Name(ite.getProduct_Name());
            item.setDelivery_DateStr(ite.getDelivery_DateStr());
            item.setGetOrder_Date_PlanStr(ite.getGetOrder_Date_PlanStr());
            item.setZhanCha_Date_PlanStr(ite.getZhanCha_Date_PlanStr());
            item.setZhanCha_Date_AccuStr(ite.getZhanCha_Date_AccuStr());
            item.setOutDraw_Date_PlanStr(ite.getOutDraw_Date_PlanStr());
            item.setOutDraw_Date_AccuStr(ite.getOutDraw_Date_AccuStr());
            item.setProgram_confir_Date_PlanStr(ite.getProgram_confir_Date_PlanStr());
            item.setProgram_confir_Date_AccuStr(ite.getProgram_confir_Date_AccuStr());
            item.setGiveOrder_Date_PlanStr(ite.getGiveOrder_Date_PlanStr());
            item.setGiveOrder_Date_AccuStr(ite.getGiveOrder_Date_AccuStr());
            item.setDelivery_Goods_Date_PlanStr(ite.getDelivery_Goods_Date_PlanStr());
            item.setDelivery_Goods_Date_AccuStr(ite.getDelivery_Goods_Date_AccuStr());
            item.setInstall_Date_PlanStr(ite.getInstall_Date_PlanStr());
            item.setInstall_Date_AccuStr(ite.getInstall_Date_AccuStr());
            item.setYanShou_Date_PlanStr(ite.getYanShou_Date_PlanStr());
            item.setYanShou_Date_AccuStr(ite.getYanShou_Date_AccuStr());
            item.setJindu_remark(ite.getJindu_remark());
            item.setTotalBao(ite.getTotalBao());
            item.setHetongMoney(ite.getHetongMoney());
            item.setHereMoney(ite.getHereMoney());
            item.setWeiHuiMoney(ite.getWeiHuiMoney());
            item.setRemark(ite.getRemark());
            item.setVersion(1.0);
            projectMapper.saveOrderItemMor(item);
        }

    }

    public int findAllProjecHOICount() throws Exception {
        return projectMapper.findAllProjecHOICount();
    }


    public List<ProjectHeadOrderItem> findAllProjecHOI(ProjectHeadOrderItem item) throws Exception {
        return projectMapper.findAllProjecHOI(item);
    }


    public static String changeNumToDate(String s) {
        String rtn = "1900-01-01";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date1 = new java.util.Date();
            date1 = format.parse("1900-01-01");
            long i1 = date1.getTime();
            i1 = i1 / 1000 + ((Long.parseLong(s) - 2) * 24 * 3600);
            date1.setTime(i1 * 1000);
            rtn = format.format(date1);
            System.out.println("rtn=" + rtn);
        } catch (Exception e) {
            rtn = "1900-01-01";
        }
        return rtn;

    }
}
