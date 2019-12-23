package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.FinanceMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IFinanceServ;
import jxl.Cell;
import jxl.CellType;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/21 0021 上午 9:52
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class FinanceServiceImpl implements IFinanceServ {

    @Autowired
    private FinanceMapper financeMapper;

    private static Logger logger = LogManager.getLogger(FinanceServiceImpl.class);

    String empNoTitle = "工号";
    Integer empNoTitleIndex;
    String incomDateTitle = "入职日期";
    Integer incomDateTitleIndex;
    String compreSalaryTitle = "综合技能";
    Integer compreSalaryTitleIndex;
    String posSalaryTitle = "岗位工资";
    Integer posSalaryTitleIndex;
    String jobSalaryTitle = "职务工资";
    Integer jobSalaryTitleIndex;
    String meritSalaryTitle = "绩效工资";
    Integer meritSalaryTitleIndex;
    String salaryNameTitle = "姓名";
    Integer salaryNameTitleIndex;


    private String nameTitle = "姓名";
    Integer nameTitleIndex;
    private String empNoTitle2 = "工号";
    Integer empNoTitle2Index;
    private String deptNameTitle = "部门";
    Integer deptNameTitleIndex;
    private String zhengbanHoursTitle = "正班出勤工时";
    Integer zhengbanHoursTitleIndex;
    private String usualExtHoursTitle = "平时加班";
    Integer usualExtHoursTitleIndex;
    private String workendHoursTitle = "周末加班";
    Integer workendHoursTitleIndex;
    private String chinaPaidLeaveTitle = "国家有薪假";
    Integer chinaPaidLeaveTitleIndex;
    private String otherPaidLeaveTitle = "其它有薪假";
    Integer otherPaidLeaveTitleIndex;
    private String leaveOfAbsenseTitle = "事假";
    Integer leaveOfAbsenseTitleIndex;
    private String sickLeaveTitle = "病假";
    Integer sickLeaveTitleIndex;
    private String otherAlloTitle = "其它补贴（出差/夜班）";
    Integer otherAlloTitleIndex;
    private String fullWorkRewordTitle = "全勤奖";
    Integer fullWorkRewordTitleIndex;
    private String foodExpenseTitle = "伙食费";
    Integer foodExpenseTitleIndex;
    private String roomOrWaterEleExpenseTitle = "房租/水电费";
    Integer roomOrWaterEleExpenseTitleIndex;
    private String oldAgeINsuranTitle = "扣代付养老险";
    Integer oldAgeINsuranTitleIndex;
    private String medicalInsuranTitle = "扣代付医疗险";
    Integer medicalInsuranTitleIndex;
    private String unEmployeeInsurTitle = "扣代付失业险";
    Integer unEmployeeInsurTitleIndex;
    private String accumulaFundTitle = "扣代付公积金";
    Integer accumulaFundTitleIndex;
    private String errorInWorkTitle = "工作失误";
    Integer errorInWorkTitleIndex;
    private String meritScoreTitle = "绩效分";
    Integer meritScoreTitleIndex;


    private String empNoTitle3 = "工号";
    Integer empNoTitle3Index;
    private String nameTitle3 = "姓名";
    Integer nameTitle3Index;
    private String bigDeptNameTitle = "大部门";//大部门
    Integer bigDeptNameTitleIndex;
    private String deptNameTitle3 = "部门";//部门
    Integer deptNameTitle3Index;
    private String legalHolidWorkHoursTitle = "法定节假日加班工时";//法定节假日加班工时
    Integer legalHolidWorkHoursTitleIndex;
    private String sellActualTitle = "业务实际";//业务实际
    Integer sellActualTitleIndex;
    private String sellThresholdTitle = "业务阈值";//业务阈值
    Integer sellThresholdTitleIndex;
    private String sellLevelSalaryTitle = "业务等级工资";//业务等级工资
    Integer sellLevelSalaryTitleIndex;
    private String houseSubsidyTitle = "房补";//房补
    Integer houseSubsidyTitleIndex;
    private String hotTempOrOtherAllowTitle = "高温等其它补贴";//高温等其它补贴
    Integer hotTempOrOtherAllowTitleIndex;
    private String workYearsSalaryTitle = "工龄工资";//工龄工资
    Integer workYearsSalaryTitleIndex;
    private String sellCommiTitle = "业务提成";//业务提成
    Integer sellCommiTitleIndex;
    private String speciAddDeductCostTitle = "专项附加扣除";
    Integer speciAddDeductCostTitleIndex;
    private String personIncomTax = "个税";
    Integer personIncomTaxIndex;
    private String basicWorkHoursTitle = "基本工时";
    Integer basicWorkHoursTitleIndex;


    private String empHoursBigTitle;
    private Integer empHoursBigTitleIndex = 0;
    private String salaryBigTitle;
    private Integer salaryBigTitleIndex = 0;
    private String financeImportDataBigTitle;
    private Integer financeImportDataBigTitleIndex = 0;

    String errorExcel = "";
    String errorTitle = "";

    public List<Salary> translateExcelToBean(MultipartFile file) throws Exception {
        List<Salary> salaryList = new ArrayList<Salary>();
        String empNo = null;
        try {
            WorkbookSettings ws = new WorkbookSettings();
            jxl.Sheet xlsfSheet = null;
            jxl.Workbook Workbook = jxl.Workbook.getWorkbook(file.getInputStream(), ws);
            if (Workbook != null) {
                jxl.Sheet[] sheets = Workbook.getSheets();
                xlsfSheet = sheets[0];
            }
            Salary sa = null;
            int rowNums = xlsfSheet.getRows();
            jxl.Cell[] cell = null;
            Cell cella;
            if (rowNums > 0) {
                errorExcel = "工资结构表";
                cell = xlsfSheet.getRow(0);
                int coloumNum = cell.length;
                for (int ab = 0; ab < coloumNum; ab++) {
                    cella = cell[ab];
                    if (empNoTitle.equals(cella.getContents().trim())) {
                        if (salaryBigTitleIndex < ab) {
                            salaryBigTitleIndex = ab;
                            salaryBigTitle = empNoTitle;
                        }
                        empNoTitleIndex = ab;
                    }
                    if (incomDateTitle.equals(cella.getContents().trim())) {
                        if (salaryBigTitleIndex < ab) {
                            salaryBigTitleIndex = ab;
                            salaryBigTitle = incomDateTitle;
                        }
                        incomDateTitleIndex = ab;
                    }
                    if (compreSalaryTitle.equals(cella.getContents().trim())) {
                        if (salaryBigTitleIndex < ab) {
                            salaryBigTitleIndex = ab;
                            salaryBigTitle = compreSalaryTitle;
                        }
                        compreSalaryTitleIndex = ab;
                    }
                    if (posSalaryTitle.equals(cella.getContents().trim())) {
                        if (salaryBigTitleIndex < ab) {
                            salaryBigTitleIndex = ab;
                            salaryBigTitle = posSalaryTitle;
                        }
                        posSalaryTitleIndex = ab;
                    }
                    if (jobSalaryTitle.equals(cella.getContents().trim())) {
                        if (salaryBigTitleIndex < ab) {
                            salaryBigTitleIndex = ab;
                            salaryBigTitle = jobSalaryTitle;
                        }
                        jobSalaryTitleIndex = ab;
                    }
                    if (meritSalaryTitle.equals(cella.getContents().trim())) {
                        if (salaryBigTitleIndex < ab) {
                            salaryBigTitleIndex = ab;
                            salaryBigTitle = meritSalaryTitle;
                        }
                        meritSalaryTitleIndex = ab;

                    }
                    if (salaryNameTitle.equals(cella.getContents().trim())) {
                        if (salaryBigTitleIndex < ab) {
                            salaryBigTitleIndex = ab;
                            salaryBigTitle = salaryNameTitle;
                        }
                        salaryNameTitleIndex = ab;

                    }
                }
            }

            for (int i = 1; i < rowNums; i++) {
                cell = xlsfSheet.getRow(i);
                int len = cell.length - 1;
                if (cell != null && cell.length > 0) {
                   empNo = cell[empNoTitleIndex].getContents().trim();
                    if (empNo.length() > 0) {
                        sa = new Salary();
                        errorTitle = empNoTitle;
                        sa.setEmpNo(cell[empNoTitleIndex].getContents().trim());
                        errorTitle = salaryNameTitle;
                        sa.setName(cell[salaryNameTitleIndex].getContents().trim());
                        errorTitle = incomDateTitle;
                        sa.setInComDate(cell[incomDateTitleIndex].getType() == CellType.EMPTY ? "9999-12-31" : cell[incomDateTitleIndex].getContents().trim());
                        errorTitle = compreSalaryTitle;
                        if (salaryBigTitle.equals(compreSalaryTitle) && len < meritSalaryTitleIndex) {
                            sa.setCompreSalary(1.0);
                        } else {
                            sa.setCompreSalary(cell[compreSalaryTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[compreSalaryTitleIndex].getContents().trim()));
                        }
                        errorTitle = posSalaryTitle;
                        if (salaryBigTitle.equals(posSalaryTitle) && len < meritSalaryTitleIndex) {
                            sa.setPosSalary(1.0);
                        } else {
                            sa.setPosSalary(cell[posSalaryTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[posSalaryTitleIndex].getContents().trim()));
                        }
                        errorTitle = jobSalaryTitle;
                        if (salaryBigTitle.equals(jobSalaryTitle) && len < meritSalaryTitleIndex) {
                            sa.setJobSalary(1.0);
                        } else {
                            sa.setJobSalary(cell[jobSalaryTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[jobSalaryTitleIndex].getContents().trim()));
                        }
                        errorTitle = meritSalaryTitle;
                        if (salaryBigTitle.equals(meritSalaryTitle) && len < meritSalaryTitleIndex) {
                            sa.setMeritSalary(1.0);
                        } else {
                            sa.setMeritSalary(cell[meritSalaryTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[meritSalaryTitleIndex].getContents().trim()));
                        }
                        salaryList.add(sa);
                    }
                }
            }
            return salaryList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NumberFormatException("["+errorExcel+"]工号为"+empNo+errorTitle+"标题栏数据出现错误(空值/或本该数字却是字符串)");

        }
    }

    public void saveAllSalaryData(List<Salary> salaryList) throws Exception {
        financeMapper.deleteAllSalaryData();
        Employee employee;
        for (Salary sa : salaryList) {
            //employee = financeMapper.getEmployeeByEmpNoAndName(sa.getEmpNo(), sa.getName());
           // if (employee != null) {
                sa.setState(0);
                financeMapper.saveSalary(sa);
           // }
        }

    }

    public List<FinanceImportData> queryFinanceImportDataByCondition(Employee employee) throws Exception {
        return financeMapper.queryFinanceImportDataByCondition(employee);
    }

    public int queryFinanceImportDataByConditionCount(Employee employee) throws Exception {
        return financeMapper.queryFinanceImportDataByConditionCount(employee);
    }

    public int findAllFinanceImportDataCount() throws Exception {
        return financeMapper.findAllFinanceImportDataCount();
    }

    public void addSalaryByBean(Employee employee) throws Exception {
        Salary sa = financeMapper.getSalaryByEmpnoA(employee.getEmpNo());
        if (sa != null) {
            financeMapper.updateSalaryByBean(employee);
        } else {
            financeMapper.addSalaryByBean(employee);

        }
    }

    public void deleteEmpSalaryByBatch(Employee employee) throws Exception {
        financeMapper.deleteEmpSalaryByBatch(employee.getIds());
    }


    public List<EmpHours> translateExcelToBeanEmpHours(MultipartFile file1, String yearMonth) throws Exception {
        List<EmpHours> salaryList = new ArrayList<EmpHours>();
        String empNo = null;
        try {
            WorkbookSettings ws = new WorkbookSettings();
            jxl.Sheet xlsfSheet = null;
            jxl.Workbook Workbook = jxl.Workbook.getWorkbook(file1.getInputStream(), ws);
            if (Workbook != null) {
                jxl.Sheet[] sheets = Workbook.getSheets();
                xlsfSheet = sheets[0];
            }
            EmpHours sa = null;
            int rowNums = xlsfSheet.getRows();
            jxl.Cell[] cell = null;
            Cell cella;
            if (rowNums > 0) {
                errorExcel = "人事工时表";
                cell = xlsfSheet.getRow(0);
                int coloumNum = cell.length;
                for (int ab = 0; ab < coloumNum; ab++) {
                    cella = cell[ab];
                    if (nameTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = nameTitle;
                        }
                        nameTitleIndex = ab;
                    } else if (empNoTitle2.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = empNoTitle2;
                        }
                        empNoTitle2Index = ab;
                    } else if (deptNameTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = deptNameTitle;
                        }
                        deptNameTitleIndex = ab;
                    } else if (zhengbanHoursTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = zhengbanHoursTitle;
                        }
                        zhengbanHoursTitleIndex = ab;
                    } else if (usualExtHoursTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = usualExtHoursTitle;
                        }
                        usualExtHoursTitleIndex = ab;
                    } else if (workendHoursTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = workendHoursTitle;
                        }
                        workendHoursTitleIndex = ab;
                    } else if (chinaPaidLeaveTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = chinaPaidLeaveTitle;
                        }
                        chinaPaidLeaveTitleIndex = ab;
                    } else if (otherPaidLeaveTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = otherPaidLeaveTitle;
                        }
                        otherPaidLeaveTitleIndex = ab;
                    } else if (leaveOfAbsenseTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = leaveOfAbsenseTitle;
                        }
                        leaveOfAbsenseTitleIndex = ab;
                    } else if (sickLeaveTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = sickLeaveTitle;
                        }
                        sickLeaveTitleIndex = ab;
                    } else if (otherAlloTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = otherAlloTitle;
                        }
                        otherAlloTitleIndex = ab;
                    } else if (fullWorkRewordTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = fullWorkRewordTitle;
                        }
                        fullWorkRewordTitleIndex = ab;
                    } else if (foodExpenseTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = foodExpenseTitle;
                        }
                        foodExpenseTitleIndex = ab;
                    } else if (roomOrWaterEleExpenseTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = roomOrWaterEleExpenseTitle;
                        }
                        roomOrWaterEleExpenseTitleIndex = ab;
                    } else if (oldAgeINsuranTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = oldAgeINsuranTitle;
                        }
                        oldAgeINsuranTitleIndex = ab;

                    } else if (medicalInsuranTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = medicalInsuranTitle;
                        }
                        medicalInsuranTitleIndex = ab;
                    } else if (unEmployeeInsurTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = unEmployeeInsurTitle;
                        }
                        unEmployeeInsurTitleIndex = ab;
                    } else if (accumulaFundTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = accumulaFundTitle;
                        }
                        accumulaFundTitleIndex = ab;
                    } else if (errorInWorkTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = errorInWorkTitle;
                        }
                        errorInWorkTitleIndex = ab;
                    } else if (meritScoreTitle.equals(cella.getContents().trim())) {
                        if (empHoursBigTitleIndex < ab) {
                            empHoursBigTitleIndex = ab;
                            empHoursBigTitle = meritScoreTitle;
                        }
                        meritScoreTitleIndex = ab;
                    }
                }
            }

            for (int i = 1; i < rowNums; i++) {
                int len = cell.length - 1;
                cell = xlsfSheet.getRow(i);
                if (cell != null && cell.length > 0) {
                    empNo = cell[empNoTitle2Index].getContents().trim();
                    if (empNo.length() > 0) {
                        sa = new EmpHours();
                        sa.setYearMonthStr(yearMonth);
                        errorTitle = nameTitle;
                        sa.setName(cell[nameTitleIndex].getContents().trim());
                        errorTitle = empNoTitle2;
                        sa.setEmpNo(cell[empNoTitle2Index].getContents().trim());
                        errorTitle = deptNameTitle;
                        sa.setDeptName(cell[deptNameTitleIndex].getContents().trim());
                        errorTitle = zhengbanHoursTitle;
                        if (empHoursBigTitle.equals(zhengbanHoursTitle) && len < empHoursBigTitleIndex) {
                            sa.setZhengbanHours(0.0);
                        } else {
                            sa.setZhengbanHours(cell[zhengbanHoursTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[zhengbanHoursTitleIndex].getContents().trim()));
                        }
                        errorTitle = usualExtHoursTitle;
                        if (empHoursBigTitle.equals(usualExtHoursTitle) && len < empHoursBigTitleIndex) {
                            sa.setUsualExtHours(0.0);
                        } else {
                            sa.setUsualExtHours(cell[usualExtHoursTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[usualExtHoursTitleIndex].getContents().trim()));
                        }
                        errorTitle = workendHoursTitle;
                        if (empHoursBigTitle.equals(workendHoursTitle) && len < empHoursBigTitleIndex) {
                            sa.setWorkendHours(0.0);
                        } else {
                            sa.setWorkendHours(cell[workendHoursTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[workendHoursTitleIndex].getContents().trim()));
                        }
                        errorTitle = chinaPaidLeaveTitle;
                        if (empHoursBigTitle.equals(chinaPaidLeaveTitle) && len < empHoursBigTitleIndex) {
                            sa.setChinaPaidLeave(0.0);
                        } else {
                            sa.setChinaPaidLeave(cell[chinaPaidLeaveTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[chinaPaidLeaveTitleIndex].getContents().trim()));
                        }
                        errorTitle = otherPaidLeaveTitle;
                        if (empHoursBigTitle.equals(otherPaidLeaveTitle) && len < empHoursBigTitleIndex) {
                            sa.setOtherPaidLeave(0.0);
                        } else {
                            sa.setOtherPaidLeave(cell[otherPaidLeaveTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[otherPaidLeaveTitleIndex].getContents().trim()));
                        }
                        errorTitle = leaveOfAbsenseTitle;
                        if (empHoursBigTitle.equals(leaveOfAbsenseTitle) && len < empHoursBigTitleIndex) {
                            sa.setLeaveOfAbsense(0.0);
                        } else {
                            sa.setLeaveOfAbsense(cell[leaveOfAbsenseTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[leaveOfAbsenseTitleIndex].getContents().trim()));
                        }
                        errorTitle = sickLeaveTitle;
                        if (empHoursBigTitle.equals(sickLeaveTitle) && len < empHoursBigTitleIndex) {
                            sa.setSickLeave(0.0);
                        } else {
                            sa.setSickLeave(cell[sickLeaveTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[sickLeaveTitleIndex].getContents().trim()));
                        }
                        errorTitle = otherAlloTitle;
                        if (empHoursBigTitle.equals(otherAlloTitle) && len < empHoursBigTitleIndex) {
                            sa.setOtherAllo(0.0);
                        } else {
                            sa.setOtherAllo(cell[otherAlloTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[otherAlloTitleIndex].getContents().trim()));
                        }
                        errorTitle = fullWorkRewordTitle;
                        if (empHoursBigTitle.equals(fullWorkRewordTitle) && len < empHoursBigTitleIndex) {
                            sa.setFullWorkReword(0.0);
                        } else {
                            sa.setFullWorkReword(cell[fullWorkRewordTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[fullWorkRewordTitleIndex].getContents().trim()));
                        }
                        errorTitle = foodExpenseTitle;
                        if (empHoursBigTitle.equals(foodExpenseTitle) && len < empHoursBigTitleIndex) {
                            sa.setFoodExpense(0.0);
                        } else {
                            sa.setFoodExpense(cell[foodExpenseTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[foodExpenseTitleIndex].getContents().trim()));
                        }
                        errorTitle = roomOrWaterEleExpenseTitle;
                        if (empHoursBigTitle.equals(roomOrWaterEleExpenseTitle) && len < empHoursBigTitleIndex) {
                            sa.setRoomOrWaterEleExpense(0.0);
                        } else {
                            sa.setRoomOrWaterEleExpense(cell[roomOrWaterEleExpenseTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[roomOrWaterEleExpenseTitleIndex].getContents().trim()));
                        }
                        errorTitle = oldAgeINsuranTitle;
                        if (empHoursBigTitle.equals(oldAgeINsuranTitle) && len < empHoursBigTitleIndex) {
                            sa.setOldAgeINsuran(0.0);
                        } else {
                            sa.setOldAgeINsuran(cell[oldAgeINsuranTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[oldAgeINsuranTitleIndex].getContents().trim()));
                        }
                        errorTitle = medicalInsuranTitle;
                        if (empHoursBigTitle.equals(medicalInsuranTitle) && len < empHoursBigTitleIndex) {
                            sa.setMedicalInsuran(0.0);
                        } else {
                            sa.setMedicalInsuran(cell[medicalInsuranTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[medicalInsuranTitleIndex].getContents().trim()));
                        }
                        errorTitle = unEmployeeInsurTitle;
                        if (empHoursBigTitle.equals(unEmployeeInsurTitle) && len < empHoursBigTitleIndex) {
                            sa.setUnEmployeeInsur(0.0);
                        } else {
                            sa.setUnEmployeeInsur(cell[unEmployeeInsurTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[unEmployeeInsurTitleIndex].getContents().trim()));
                        }
                        errorTitle = accumulaFundTitle;
                        if (empHoursBigTitle.equals(accumulaFundTitle) && len < empHoursBigTitleIndex) {
                            sa.setAccumulaFund(0.0);
                        } else {
                            sa.setAccumulaFund(cell[accumulaFundTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[accumulaFundTitleIndex].getContents().trim()));
                        }
                        errorTitle = errorInWorkTitle;
                        if (empHoursBigTitle.equals(errorInWorkTitle) && len < empHoursBigTitleIndex) {
                            sa.setErrorInWork(0.0);
                        } else {
                            sa.setErrorInWork(cell[errorInWorkTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[errorInWorkTitleIndex].getContents().trim()));
                        }
                        errorTitle = meritScoreTitle;
                        if (empHoursBigTitle.equals(meritScoreTitle) && len < empHoursBigTitleIndex) {
                            sa.setMeritScore(0.0);
                        } else {
                            sa.setMeritScore(cell[meritScoreTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[meritScoreTitleIndex].getContents().trim()));
                        }
                        salaryList.add(sa);
                    }
                }
            }
            return salaryList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NumberFormatException("["+errorExcel+"]工号为"+empNo+errorTitle+"标题栏数据出现错误(空值/或本该数字却是字符串)");
        }
    }

    public void saveAllEmpHours(List<EmpHours> empHoursList, String yearMonth) throws Exception {
        financeMapper.deleteAllEmpHoursByYearMonthData(yearMonth);
        Employee employee;
        for (EmpHours em : empHoursList) {
          //  employee = financeMapper.getEmployeeByEmpNoAndName(em.getEmpNo(), em.getName());
           // if (employee != null) {
                financeMapper.saveEmpHours(em);
           // }
        }
    }

    public List<EmpHours> findAllEmpHours(Employee employee) throws Exception {
        return financeMapper.findAllEmpHours(employee);
    }

    public int findAllEmpHoursHours() throws Exception {
        return financeMapper.findAllEmpHoursHours();
    }

    public List<EmpHours> queryEmployeeHoursByCondition(Employee employee) throws Exception {
        return financeMapper.queryEmployeeHoursByCondition(employee);
    }

    public void deleteFinanceImportDataByBatch(Employee employee) throws Exception {
        financeMapper.deleteFinanceImportDataByBatch(employee.getIds());
    }

    public void deleteFinanceImportDataById(Integer id) throws Exception {
        financeMapper.deleteFinanceImportDataById(id);
    }

    public int checkFinanceImportNoandYearMonthIsExsit(EmpHours empHours) throws Exception {
        return financeMapper.checkFinanceImportNoandYearMonthIsExsit(empHours);
    }

    public void addFinanceImportDataByBean(FinanceImportData financeImportData) throws Exception {
        financeMapper.saveFinanceImportData(financeImportData);
    }

    public void updateFinanceImportDataByBean(FinanceImportData financeImportData) throws Exception {
        financeMapper.updateFinanceImportDataByBean(financeImportData);
    }

    public FinanceImportData getFinanceImportDataById(Integer id) throws Exception {
        return financeMapper.getFinanceImportDataById(id);
    }


    public List<FinanceImportData> findAllFinanceImportData(Employee employee) throws Exception {
        return financeMapper.findAllFinanceImportData(employee);
    }


    public int queryEmployeeHoursByConditionCount(Employee employee) throws Exception {
        return financeMapper.queryEmployeeHoursByConditionCount(employee);
    }

    public void deleteEmployeeHoursByEmpno(Integer id) throws Exception {
        financeMapper.deleteEmployeeHoursByEmpno(id);
    }

    public void deleteEmpHoursByBatch(Employee employee) throws Exception {
        financeMapper.deleteEmpHoursByBatch(employee.getIds());
    }

    public EmpHours getEmpHoursByEmpNo(Integer id) throws Exception {
        return financeMapper.getEmpHoursByEmpNo(id);
    }

    public void updateEmpHoursByBean(EmpHours empHours) throws Exception {
        financeMapper.updateEmpHoursByBean(empHours);
    }

    public int checkEmpNoandYearMonthIsExsit(EmpHours empHours) throws Exception {
        return financeMapper.checkEmpNoandYearMonthIsExsit(empHours);
    }

    public void addEmpHoursByBean(EmpHours empHours) throws Exception {
        financeMapper.addEmpHoursByBean(empHours);
    }

    public FinanceSetUpData findFinanceSetUpData() throws Exception {
        return financeMapper.findFinanceSetUpData();
    }

    public List<FinanceImportData> translateExcelToBeanFinanceImportData(MultipartFile file1, String yearMonth) throws Exception {
        List<FinanceImportData> financeImportDataList = new ArrayList<FinanceImportData>();
        String empNo = null;
        try {
            WorkbookSettings ws = new WorkbookSettings();
            jxl.Sheet xlsfSheet = null;
            jxl.Workbook Workbook = jxl.Workbook.getWorkbook(file1.getInputStream(), ws);
            if (Workbook != null) {
                jxl.Sheet[] sheets = Workbook.getSheets();
                xlsfSheet = sheets[0];
            }
            FinanceImportData sa = null;
            int rowNums = xlsfSheet.getRows();
            jxl.Cell[] cell = null;
            Cell cella;
            if (rowNums > 0) {
                errorExcel = "财务提供数据表";
                cell = xlsfSheet.getRow(0);
                int coloumNum = cell.length;
                for (int ab = 0; ab < coloumNum; ab++) {
                    cella = cell[ab];
                    if (empNoTitle3.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = empNoTitle3;
                        }
                        empNoTitle3Index = ab;
                    } else if (nameTitle3.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = nameTitle3;
                        }
                        nameTitle3Index = ab;
                    } else if (bigDeptNameTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = bigDeptNameTitle;
                        }
                        bigDeptNameTitleIndex = ab;
                    } else if (deptNameTitle3.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = deptNameTitle3;
                        }
                        deptNameTitle3Index = ab;
                    } else if (legalHolidWorkHoursTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = legalHolidWorkHoursTitle;
                        }
                        legalHolidWorkHoursTitleIndex = ab;
                    } else if (sellActualTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = sellActualTitle;
                        }
                        sellActualTitleIndex = ab;
                    } else if (sellThresholdTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = sellThresholdTitle;
                        }
                        sellThresholdTitleIndex = ab;
                    } else if (sellLevelSalaryTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = sellLevelSalaryTitle;
                        }
                        sellLevelSalaryTitleIndex = ab;
                    } else if (houseSubsidyTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = houseSubsidyTitle;
                        }
                        houseSubsidyTitleIndex = ab;
                    } else if (hotTempOrOtherAllowTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = hotTempOrOtherAllowTitle;
                        }
                        hotTempOrOtherAllowTitleIndex = ab;
                    } else if (workYearsSalaryTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = workYearsSalaryTitle;
                        }
                        workYearsSalaryTitleIndex = ab;
                    } else if (sellCommiTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = sellCommiTitle;
                        }
                        sellCommiTitleIndex = ab;
                    } else if (speciAddDeductCostTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = speciAddDeductCostTitle;
                        }
                        speciAddDeductCostTitleIndex = ab;
                    } else if (personIncomTax.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = personIncomTax;
                        }
                        personIncomTaxIndex = ab;
                    } else if (basicWorkHoursTitle.equals(cella.getContents().trim())) {
                        if (financeImportDataBigTitleIndex < ab) {
                            financeImportDataBigTitleIndex = ab;
                            financeImportDataBigTitle = basicWorkHoursTitle;
                        }
                        basicWorkHoursTitleIndex = ab;
                    }
                }
            }

            for (int i = 1; i < rowNums; i++) {
                cell = xlsfSheet.getRow(i);
                int len = cell.length - 1;
                if (cell != null && cell.length > 0) {
                    empNo = cell[empNoTitle3Index].getContents().trim();
                    if (empNo.length() > 0) {
                        sa = new FinanceImportData();
                        sa.setYearMonth(yearMonth);
                        errorTitle = nameTitle3;
                        sa.setName(cell[nameTitle3Index].getContents().trim());
                        errorTitle = empNoTitle3;
                        sa.setEmpNo(cell[empNoTitle3Index].getContents().trim());
                        errorTitle = deptNameTitle3;
                        sa.setDeptName(cell[deptNameTitle3Index].getContents().trim());
                        errorTitle = bigDeptNameTitle;
                        sa.setBigDeptName(cell[bigDeptNameTitleIndex].getContents().trim());
                        errorTitle = legalHolidWorkHoursTitle;
                        if (financeImportDataBigTitle.equals(legalHolidWorkHoursTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setLegalHolidWorkHours(0.0);
                        } else {
                            sa.setLegalHolidWorkHours(cell[legalHolidWorkHoursTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[legalHolidWorkHoursTitleIndex].getContents().trim()));
                        }
                        errorTitle = sellActualTitle;
                        if (financeImportDataBigTitle.equals(sellActualTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setSellActual(0.0);
                        } else {
                            sa.setSellActual(cell[sellActualTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[sellActualTitleIndex].getContents().trim()));
                        }
                        errorTitle = sellThresholdTitle;
                        if (financeImportDataBigTitle.equals(sellThresholdTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setSellThreshold(0.0);
                        } else {
                            sa.setSellThreshold(cell[sellThresholdTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[sellThresholdTitleIndex].getContents().trim()));
                        }
                        errorTitle = sellLevelSalaryTitle;
                        if (financeImportDataBigTitle.equals(sellLevelSalaryTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setSellLevelSalary(0.0);
                        } else {
                            sa.setSellLevelSalary(cell[sellLevelSalaryTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[sellLevelSalaryTitleIndex].getContents().trim()));
                        }
                        errorTitle = houseSubsidyTitle;
                        if (financeImportDataBigTitle.equals(houseSubsidyTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setHouseSubsidy(0.0);
                        } else {
                            sa.setHouseSubsidy(cell[houseSubsidyTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[houseSubsidyTitleIndex].getContents().trim()));
                        }
                        errorTitle = hotTempOrOtherAllowTitle;
                        if (financeImportDataBigTitle.equals(hotTempOrOtherAllowTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setHotTempOrOtherAllow(0.0);
                        } else {
                            sa.setHotTempOrOtherAllow(cell[hotTempOrOtherAllowTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[hotTempOrOtherAllowTitleIndex].getContents().trim()));
                        }
                        errorTitle = workYearsSalaryTitle;
                        if (financeImportDataBigTitle.equals(workYearsSalaryTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setWorkYearsSalary(0.0);
                        } else {
                            sa.setWorkYearsSalary(cell[workYearsSalaryTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[workYearsSalaryTitleIndex].getContents().trim()));
                        }
                        errorTitle = sellCommiTitle;
                        if (financeImportDataBigTitle.equals(sellCommiTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setSellCommi(0.0);
                        } else {
                            sa.setSellCommi(cell[sellCommiTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[sellCommiTitleIndex].getContents().trim()));
                        }
                        errorTitle = speciAddDeductCostTitle;
                        if (financeImportDataBigTitle.equals(speciAddDeductCostTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setSpeciAddDeductCost(0.0);
                        } else {
                            sa.setSpeciAddDeductCost(cell[speciAddDeductCostTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[speciAddDeductCostTitleIndex].getContents().trim()));
                        }
                        errorTitle = personIncomTax;
                        if (financeImportDataBigTitle.equals(personIncomTax) && len < financeImportDataBigTitleIndex) {
                            sa.setPersonIncomTax(0.0);
                        } else {
                            sa.setPersonIncomTax(cell[personIncomTaxIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[personIncomTaxIndex].getContents().trim()));
                        }
                        errorTitle = basicWorkHoursTitle;
                        if (financeImportDataBigTitle.equals(basicWorkHoursTitle) && len < financeImportDataBigTitleIndex) {
                            sa.setBasicWorkHours(0.0);
                        } else {
                            sa.setBasicWorkHours(cell[basicWorkHoursTitleIndex].getType() == CellType.EMPTY ? 0.0 : Double.valueOf(cell[basicWorkHoursTitleIndex].getContents().trim()));
                        }
                        financeImportDataList.add(sa);
                    }
                }
            }
            return financeImportDataList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NumberFormatException("["+errorExcel+"]工号为"+empNo+errorTitle+"标题栏数据出现错误(空值/或本该数字却是字符串)");
        }
    }

    public void saveAllFinanceImportData(List<FinanceImportData> financeImportDataList, String yearMonth) throws Exception {
        financeMapper.deleteAllsaveAllFinanceImportDataByYearMonthData(yearMonth);
        Employee employee;
        for (FinanceImportData fid : financeImportDataList) {
            //employee = financeMapper.getEmployeeByEmpNoAndName(fid.getEmpNo(), fid.getName());
           // if (employee != null) {
                fid.setYearMonth(yearMonth);
                financeMapper.saveFinanceImportData(fid);
           // }
        }
    }

    public List<SalaryDataOutPut> querySalaryDataOutPutByCondition(Employee employee) throws Exception {
        return financeMapper.querySalaryDataOutPutByCondition(employee);
    }

    public int querySalaryDataOutPutByConditionCount(Employee employee) throws Exception {
        return financeMapper.querySalaryDataOutPutByConditionCount(employee);
    }


    public void saveFinanceSetUp(FinanceSetUpData financeSetUpData) throws Exception {
        int count = financeMapper.findFinanceSetUpDataCount();
        if (count == 0) {
            financeMapper.saveFinanceSetUp(financeSetUpData);
        } else {
            financeMapper.updateFinanceSetUp(financeSetUpData);

        }
    }

    public List<SalaryDataOutPut> findAllSalaryDataOutPut(Employee employee) throws Exception {
        return financeMapper.findAllSalaryDataOutPut(employee);
    }

    public int findAllSalaryDataOutPutCount() throws Exception {
        return financeMapper.findAllSalaryDataOutPutCount();
    }

    public void saveSalaryDataOutPutsList(List<SalaryDataOutPut> salaryDataOutPutList, String yearMonth) throws Exception {
        financeMapper.deleteSalaryDataOutPutByYearMonth(yearMonth);
        for (SalaryDataOutPut sdo : salaryDataOutPutList) {
            financeMapper.saveSalaryDataOutPut(sdo);
        }
    }


    public List<SalaryDataOutPut> computeSalaryData(String yearMonth) throws Exception {
        List<SalaryDataOutPut> salaryDataOutPutList = new ArrayList<SalaryDataOutPut>();
        SalaryDataOutPut sdo;
        List<EmpHours> ehList = financeMapper.getAllEmpHoursByYearMonth(yearMonth);
        FinanceSetUpData financeSetUpData = financeMapper.findFinanceSetUpData();
        Salary sa;
        Employee ee;
        FinanceImportData fid;
        Double extWorkFee;
        Double after = 0.0;
        String empNo;
        String name;
        for (EmpHours eh : ehList) {
            empNo = eh.getEmpNo();
            name = eh.getName();
           // ee = financeMapper.getEmployeeByEmpno(eh.getEmpNo());
            sa = financeMapper.getSalaryByEmpno(eh.getEmpNo(),eh.getName());
            fid = financeMapper.getFinanceImportDataByEmpNoandYearMonth(eh.getEmpNo(),eh.getName(), yearMonth);
            Double basicTotalHours = eh.getZhengbanHours() + eh.getChinaPaidLeave() +  eh.getOtherPaidLeave();
            if(fid!=null && sa!=null) {
                if (empNo.equals(fid.getEmpNo()) && name.equals(fid.getName()) && basicTotalHours <= financeSetUpData.getBasicWorkHours()) {
                    sdo = new SalaryDataOutPut();
                    sdo.setEmpNo(sa.getEmpNo());
                    sdo.setIncomDate(sa.getInComDate());
                    sdo.setBigDeptName(fid.getBigDeptName());
                    sdo.setYearMonth(new Date().toString());
                    sdo.setDeptName(eh.getDeptName());
                    sdo.setName(eh.getName());
                    sdo.setPositionName("");
                    sdo.setPositionAttrName("");
                    sdo.setBasickWorkHours(fid.getBasicWorkHours() == null ? 0 : fid.getBasicWorkHours());
                    sdo.setNorAttenHours(eh.getZhengbanHours() == null ? 0 : eh.getZhengbanHours());
                    sdo.setNorAttendSalary(financeSetUpData.getNorAttendSalarySample() /
                            financeSetUpData.getNorAttendHoursSample() * eh.getZhengbanHours());
                    sdo.setChinaPailLeavHours(eh.getChinaPaidLeave() == null ? 0 : eh.getChinaPaidLeave());
                    sdo.setChinaPaidLeavSalary(financeSetUpData.getNorAttendSalarySample() /
                            financeSetUpData.getNorAttendHoursSample() * eh.getChinaPaidLeave());
                    sdo.setOtherPaidLeavHours(eh.getOtherPaidLeave() == null ? 0 : eh.getOtherPaidLeave());
                    sdo.setOtherPaidLeavSalary(financeSetUpData.getNorAttendSalarySample() /
                            financeSetUpData.getNorAttendHoursSample() * eh.getOtherPaidLeave());
                    sdo.setBasicSalarySubTotal(sdo.getChinaPaidLeavSalary() + sdo.getNorAttendSalary() +
                            sdo.getOtherPaidLeavSalary());

                    sdo.setUsualExtraHours(eh.getUsualExtHours() == null ? 0 : eh.getUsualExtHours());
                    sdo.setUsralExtraSalary(financeSetUpData.getNorAttendSalarySample() /
                            financeSetUpData.getNorAttendHoursSample() * sdo.getUsualExtraHours() *
                            financeSetUpData.getNorExtraMutiple());
                    sdo.setWeekendWorkHours(eh.getWorkendHours() == null ? 0 : eh.getWorkendHours());
                    sdo.setWeekendWorkSalary(financeSetUpData.getNorAttendSalarySample() /
                            financeSetUpData.getNorAttendHoursSample() * sdo.getWeekendWorkHours() *
                            financeSetUpData.getWeekEndWorkMutiple());
                    sdo.setChinaHoliWorkHours(fid.getLegalHolidWorkHours() == null ? 0 : fid.getLegalHolidWorkHours());
                    sdo.setChinaHoliWorkSalary(financeSetUpData.getNorAttendSalarySample() /
                            financeSetUpData.getNorAttendHoursSample() * sdo.getChinaHoliWorkHours() *
                            financeSetUpData.getLegalWorkMutiple());
                    sdo.setCompressSalary(sa.getCompreSalary() == null ? 0 : sa.getCompreSalary());
                    sdo.setJobSalary(sa.getJobSalary() == null ? 0 : sa.getJobSalary());
                    sdo.setPositionSalary(sa.getPosSalary() == null ? 0 : sa.getPosSalary());
                    sdo.setMeritSalary(sa.getMeritSalary() == null ? 0 : sa.getMeritSalary());
                    sdo.setMeritScore(eh.getMeritScore() == null ? 0 : eh.getMeritScore());
                    sdo.setSubbonusTotal((sdo.getCompressSalary() + sdo.getJobSalary() + sdo.getPositionSalary()) /
                            sdo.getBasickWorkHours() * (sdo.getNorAttenHours() + sdo.getChinaPailLeavHours())
                            + sdo.getMeritSalary() * eh.getMeritScore() / financeSetUpData.getMeritScoreSample() *
                            (sdo.getNorAttenHours() + sdo.getChinaPailLeavHours()) / sdo.getBasickWorkHours());
                    extWorkFee = sdo.getUsralExtraSalary() + sdo.getWeekendWorkSalary() + sdo.getChinaHoliWorkSalary();
                    sdo.setSalorLevelSalary(fid.getSellLevelSalary() == null ? 0 : fid.getSellLevelSalary());
                    after = fid.getSellActual() / fid.getSellThreshold();
                    sdo.setSalrActuGetSalary((after < 1 ? after : 1) * sdo.getSalorLevelSalary());
                    sdo.setHouseOrTELSubsidy(fid.getHouseSubsidy() == null ? 0 : fid.getHotTempOrOtherAllow());
                    sdo.setHotTempOrOtherAllow(fid.getHotTempOrOtherAllow() == null ? 0 : fid.getHotTempOrOtherAllow());
                    sdo.setFullWorkReword(eh.getFullWorkReword() == null ? 0 : eh.getFullWorkReword());
                    sdo.setWorkYearsSalary(fid.getWorkYearsSalary() == null ? 0 : fid.getWorkYearsSalary());
                    sdo.setSellCommi(fid.getSellCommi() == null ? 0 : fid.getSellCommi());
                    sdo.setCompreSalary(sdo.getBasicSalarySubTotal() + sdo.getSubbonusTotal() + sdo.getSalrActuGetSalary() +
                            sdo.getHouseOrTELSubsidy() + sdo.getHotTempOrOtherAllow() + sdo.getFullWorkReword() +
                            sdo.getWorkYearsSalary() + sdo.getSellCommi() + extWorkFee);
                    sdo.setBuckFoodCost(eh.getFoodExpense() == null ? 0 : eh.getFoodExpense());
                    sdo.setBuckWaterEleCost(eh.getRoomOrWaterEleExpense() == null ? 0 : eh.getRoomOrWaterEleExpense());
                    sdo.setBuckOldAgeInsurCost(eh.getOldAgeINsuran() == null ? 0 : eh.getOldAgeINsuran());
                    sdo.setBuckMedicInsurCost(eh.getMedicalInsuran() == null ? 0 : eh.getMedicalInsuran());
                    sdo.setBuckUnEmployCost(eh.getUnEmployeeInsur() == null ? 0 : eh.getUnEmployeeInsur());
                    sdo.setBuckAccumCost(eh.getAccumulaFund() == null ? 0 : eh.getAccumulaFund());
                    sdo.setOtherBuckCost(eh.getErrorInWork() == null ? 0 : eh.getErrorInWork());
                    sdo.setSixDeducCost(fid.getSpeciAddDeductCost() == null ? 0 : fid.getSpeciAddDeductCost());
                    sdo.setPersonIncomTaxCost(fid.getPersonIncomTax() == null ? 0 : fid.getPersonIncomTax());
                    sdo.setNetPaySalary(sdo.getCompreSalary() - sdo.getBuckFoodCost() - sdo.getBuckWaterEleCost()
                            - sdo.getBuckOldAgeInsurCost() - sdo.getBuckMedicInsurCost() - sdo.getBuckUnEmployCost()
                            - sdo.getBuckAccumCost() - sdo.getOtherBuckCost() - sdo.getPersonIncomTaxCost());

                    salaryDataOutPutList.add(sdo);
                }
            }
        }

        return salaryDataOutPutList;
    }


}
