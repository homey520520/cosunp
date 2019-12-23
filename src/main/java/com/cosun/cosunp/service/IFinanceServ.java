package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFinanceServ {

    List<Salary> translateExcelToBean(MultipartFile file1) throws Exception;

    List<SalaryDataOutPut> querySalaryDataOutPutByCondition(Employee employee) throws Exception;

    int querySalaryDataOutPutByConditionCount(Employee employee) throws Exception;

    void saveAllSalaryData(List<Salary> salaryList) throws Exception;

    void addSalaryByBean(Employee employee) throws Exception;

    void deleteEmpSalaryByBatch(Employee employee) throws Exception;

    void deleteEmpHoursByBatch(Employee employee) throws Exception;

    List<EmpHours> translateExcelToBeanEmpHours(MultipartFile file1,String yearMonth) throws Exception;

    void saveAllEmpHours(List<EmpHours> empHoursList,String yearMonth) throws Exception;

    List<EmpHours> findAllEmpHours(Employee employee) throws Exception;

    List<FinanceImportData> findAllFinanceImportData(Employee employee) throws Exception;

    void deleteFinanceImportDataById(Integer id) throws Exception;

    void updateFinanceImportDataByBean(FinanceImportData financeImportData) throws Exception;

    List<SalaryDataOutPut> computeSalaryData(String yearMonth) throws Exception;

    void saveSalaryDataOutPutsList(List<SalaryDataOutPut> salaryDataOutPutList,String yearMonth) throws Exception;

    void addFinanceImportDataByBean(FinanceImportData financeImportData) throws Exception;

    List<FinanceImportData> queryFinanceImportDataByCondition(Employee employee) throws Exception;

    int queryFinanceImportDataByConditionCount(Employee employee) throws Exception;

    int findAllFinanceImportDataCount() throws Exception;

    List<SalaryDataOutPut> findAllSalaryDataOutPut(Employee employee) throws Exception;

    int findAllSalaryDataOutPutCount() throws Exception;

    void deleteFinanceImportDataByBatch(Employee employee) throws Exception;

    void updateEmpHoursByBean(EmpHours empHours) throws Exception;

    int findAllEmpHoursHours() throws Exception;

    FinanceSetUpData findFinanceSetUpData() throws Exception;

    void saveFinanceSetUp(FinanceSetUpData financeSetUpData) throws Exception;

    void addEmpHoursByBean(EmpHours empHours) throws Exception;

    int checkEmpNoandYearMonthIsExsit(EmpHours empHours) throws Exception;

    int checkFinanceImportNoandYearMonthIsExsit(EmpHours empHours) throws Exception;

    List<EmpHours> queryEmployeeHoursByCondition(Employee employee) throws Exception;

    int queryEmployeeHoursByConditionCount(Employee employee) throws Exception;

    void deleteEmployeeHoursByEmpno(Integer id) throws Exception;

    EmpHours getEmpHoursByEmpNo(Integer id) throws Exception;

    FinanceImportData getFinanceImportDataById(Integer id) throws Exception;

    void saveAllFinanceImportData(List<FinanceImportData> financeImportDataList,String yearMonth) throws Exception;

    List<FinanceImportData> translateExcelToBeanFinanceImportData(MultipartFile file1,String yearMonth) throws Exception;

}
