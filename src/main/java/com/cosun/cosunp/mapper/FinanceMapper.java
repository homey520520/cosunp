package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.SessionScope;
import sun.awt.EmbeddedFrame;

import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/21 上午 9:52
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Repository
@Mapper
public interface FinanceMapper {

    @Delete("delete from salary ")
    void deleteAllSalaryData();

    @Delete("delete from emphours where yearMonth = #{yearMonth} ")
    void deleteAllEmpHoursByYearMonthData(String yearMonth);

    @Delete("delete from salarydataoutput where yearMonth = #{yearMonth} ")
    void deleteSalaryDataOutPutByYearMonth(String yearMonth);

    @Delete("delete from financeimportdata where id = #{id} ")
    void deleteFinanceImportDataById(Integer id);

    @Delete("delete from financeimportdata where yearMonth = #{yearMonth} ")
    void deleteAllsaveAllFinanceImportDataByYearMonthData(String yearMonth);

    @Delete("delete from emphours where id = #{id} ")
    void deleteEmployeeHoursByEmpno(Integer id);

    @Insert("insert into financeimportdata (empNo,\n" +
            "\t`name`,\n" +
            "\tdeptName,\n" +
            "\tlegalHolidWorkHours,\n" +
            "\tsellActual,\n" +
            "\tsellThreshold,\n" +
            "\tsellLevelSalary,\n" +
            "\thouseSubsidy,\n" +
            "\thotTempOrOtherAllow,\n" +
            "\tworkYearsSalary,\n" +
            "\tsellCommi,\n" +
            "\tyearMonth,speciAddDeductCost,remark,basicWorkHours) " +
            "values (" +
            "#{empNo},\n" +
            "\t#{name},\n" +
            "\t#{deptName},\n" +
            "\t#{legalHolidWorkHours},\n" +
            "\t#{sellActual},\n" +
            "\t#{sellThreshold},\n" +
            "\t#{sellLevelSalary},\n" +
            "\t#{houseSubsidy},\n" +
            "\t#{hotTempOrOtherAllow},\n" +
            "\t#{workYearsSalary},\n" +
            "\t#{sellCommi},\n" +
            "\t#{yearMonth},#{speciAddDeductCost},#{remark},#{basicWorkHours}" +
            ")")
    void saveFinanceImportData(FinanceImportData financeImportData);

    @Update("update financeimportdata set legalHolidWorkHours = #{legalHolidWorkHours},\n" +
            "\t sellActual = #{sellActual},\n" +
            "\t sellThreshold = #{sellThreshold},\n" +
            "\t sellLevelSalary = #{sellLevelSalary},\n" +
            "\t houseSubsidy = #{houseSubsidy},\n" +
            "\t hotTempOrOtherAllow = #{hotTempOrOtherAllow},\n" +
            "\t workYearsSalary = #{workYearsSalary},\n" +
            "\t sellCommi = #{sellCommi},speciAddDeductCost = #{speciAddDeductCost},remark = #{remark} \n" +
            "\t where id = #{id}")
    void updateFinanceImportDataByBean(FinanceImportData financeImportData);

    @Insert("insert into salary (empno,name,inComDate,compresalary,possalary,jobsalary,meritsalary,state) " +
            "values (#{empNo},#{name},#{inComDate},#{compreSalary},#{posSalary},#{jobSalary},#{meritSalary},#{state})")
    void saveSalary(Salary salary);


    @Insert("insert into emphours (\tNAME,\n" +
            "\tempNo,\n" +
            "\tdeptName,\n" +
            "\tzhengbanHours,\n" +
            "\tusualExtHours,\n" +
            "\tworkendHours,\n" +
            "\tchinaPaidLeave,\n" +
            "\totherPaidLeave,\n" +
            "\tleaveOfAbsense,\n" +
            "\tsickLeave,\n" +
            "\totherAllo,\n" +
            "\tfullWorkReword,\n" +
            "\tfoodExpense,\n" +
            "\troomOrWaterEleExpense,\n" +
            "\toldAgeINsuran,\n" +
            "\tmedicalInsuran,\n" +
            "\tunEmployeeInsur,\n" +
            "\taccumulaFund,\n" +
            "\terrorInWork,\n" +
            "\tmeritScore,yearMonth,sixDeductions) " +
            "values (#{name},#{empNo},#{deptName},#{zhengbanHours},#{usualExtHours}," +
            "#{workendHours},#{chinaPaidLeave},#{otherPaidLeave},#{leaveOfAbsense},#{sickLeave}," +
            "#{otherAllo},#{fullWorkReword},#{foodExpense},#{roomOrWaterEleExpense},#{oldAgeINsuran}," +
            "#{medicalInsuran},#{unEmployeeInsur},#{accumulaFund},#{errorInWork},#{meritScore},#{yearMonthStr},#{sixDeductions})")
    void saveEmpHours(EmpHours eh);

    @Insert("insert into emphours (\tNAME,\n" +
            "\tempNo,\n" +
            "\tdeptName,\n" +
            "\tzhengbanHours,\n" +
            "\tusualExtHours,\n" +
            "\tworkendHours,\n" +
            "\tchinaPaidLeave,\n" +
            "\totherPaidLeave,\n" +
            "\tleaveOfAbsense,\n" +
            "\tsickLeave,\n" +
            "\totherAllo,\n" +
            "\tfullWorkReword,\n" +
            "\tfoodExpense,\n" +
            "\troomOrWaterEleExpense,\n" +
            "\toldAgeINsuran,\n" +
            "\tmedicalInsuran,\n" +
            "\tunEmployeeInsur,\n" +
            "\taccumulaFund,\n" +
            "\terrorInWork,\n" +
            "\tmeritScore,yearMonth,remark,sixDeductions) " +
            "values (#{name},#{empNo},#{deptName},#{zhengbanHours},#{usualExtHours}," +
            "#{workendHours},#{chinaPaidLeave},#{otherPaidLeave},#{leaveOfAbsense},#{sickLeave}," +
            "#{otherAllo},#{fullWorkReword},#{foodExpense},#{roomOrWaterEleExpense},#{oldAgeINsuran}," +
            "#{medicalInsuran},#{unEmployeeInsur},#{accumulaFund},#{errorInWork},#{meritScore},#{yearMonth},#{remark},#{sixDeductions})")
    void addEmpHoursByBean(EmpHours empHours);


    @Update("update emphours set zhengbanHours = #{zhengbanHours}," +
            " usualExtHours = #{usualExtHours}, " +
            " workendHours = #{workendHours}, " +
            " chinaPaidLeave = #{chinaPaidLeave}, " +
            " otherPaidLeave = #{otherPaidLeave}, " +
            " leaveOfAbsense = #{leaveOfAbsense}, " +
            " sickLeave = #{sickLeave}, " +
            " otherAllo = #{otherAllo}, " +
            " fullWorkReword = #{fullWorkReword}, " +
            " foodExpense = #{foodExpense}, " +
            " roomOrWaterEleExpense = #{roomOrWaterEleExpense}, " +
            " oldAgeINsuran = #{oldAgeINsuran}, " +
            " medicalInsuran = #{medicalInsuran}, " +
            " unEmployeeInsur = #{unEmployeeInsur}, " +
            " accumulaFund = #{accumulaFund}, " +
            " errorInWork = #{errorInWork}, " +
            " meritScore = #{meritScore},remark = #{remark},sixDeductions = #{sixDeductions} " +
            " where empNo = #{empNo} and  yearMonth = #{yearMonth}")
    void updateEmpHoursByBean(EmpHours empHours);

    @Insert("insert into financesetupdata " +
            " (norAttendHoursSample,norAttendSalarySample,norExtraMutiple,weekEndWorkMutiple," +
            "legalWorkMutiple,meritScoreSample,basicWorkHours)" +
            " values (#{norAttendHoursSample}," +
            "#{norAttendSalarySample}," +
            "#{norExtraMutiple}," +
            " #{weekEndWorkMutiple}," +
            "#{legalWorkMutiple}," +
            " #{meritScoreSample},#{basicWorkHours})")
    void saveFinanceSetUp(FinanceSetUpData financeSetUpData);

    @Insert("insert into salarydataoutput " +
            " (\t bigDeptName," +
            "deptName,\n" +
            "\tpositionName,\n" +
            "\tpositionAttrName,\n" +
            "\tempNo,\n" +
            "\t`name`,\n" +
            "\tinCompDate,\n" +
            "\tbasickWorkHours,\n" +
            "\tnorAttenHours,\n" +
            "\tnorAttendSalary,\n" +
            "\tchinaPailLeavHours,\n" +
            "\tchinaPaidLeavSalary,\n" +
            "\totherPaidLeavHours,\n" +
            "\totherPaidLeavSalary,\n" +
            "\tbasicSalarySubTotal,\n" +
            "\tusualExtraHours,\n" +
            "\tusralExtraSalary,\n" +
            "\tweekendWorkHours,\n" +
            "\tweekendWorkSalary,\n" +
            "\tchinaHoliWorkHours,\n" +
            "\tchinaHoliWorkSalary,\n" +
            "\tcompressSalary,\n" +
            "\tjobSalary,\n" +
            "\tpositionSalary,\n" +
            "\tmeritSalary,\n" +
            "\tmeritScore,\n" +
            "\tsubbonusTotal,\n" +
            "\tsalorLevelSalary,\n" +
            "\tsalrActuGetSalary,\n" +
            "\thouseOrTELSubsidy,\n" +
            "\thotTempOrOtherAllow,\n" +
            "\tfullWorkReword,\n" +
            "\tworkYearsSalary,\n" +
            "\tsellCommi,\n" +
            "\tcompreSalary,\n" +
            "\tbuckFoodCost,\n" +
            "\tbuckWaterEleCost,\n" +
            "\tbuckOldAgeInsurCost,\n" +
            "\tbuckMedicInsurCost,\n" +
            "\tbuckUnEmployCost,\n" +
            "\tbuckAccumCost,\n" +
            "\totherBuckCost,\n" +
            "\tsixDeducCost,\n" +
            "\tpersonIncomTaxCost,\n" +
            "\tnetPaySalary,\n" +
            "\tyearMonth )" +
            " values (\t" +
            " #{bigDeptName}," +
            "#{deptName},\n" +
            "\t#{positionName},\n" +
            "\t#{positionAttrName},\n" +
            "\t#{empNo},\n" +
            "\t#{name},\n" +
            "\t#{inCompDate},\n" +
            "\t#{basickWorkHours},\n" +
            "\t#{norAttenHours},\n" +
            "\t#{norAttendSalary},\n" +
            "\t#{chinaPailLeavHours},\n" +
            "\t#{chinaPaidLeavSalary},\n" +
            "\t#{otherPaidLeavHours},\n" +
            "\t#{otherPaidLeavSalary},\n" +
            "\t#{basicSalarySubTotal},\n" +
            "\t#{usualExtraHours},\n" +
            "\t#{usralExtraSalary},\n" +
            "\t#{weekendWorkHours},\n" +
            "\t#{weekendWorkSalary},\n" +
            "\t#{chinaHoliWorkHours},\n" +
            "\t#{chinaHoliWorkSalary},\n" +
            "\t#{compressSalary},\n" +
            "\t#{jobSalary},\n" +
            "\t#{positionSalary},\n" +
            "\t#{meritSalary},\n" +
            "\t#{meritScore},\n" +
            "\t#{subbonusTotal},\n" +
            "\t#{salorLevelSalary},\n" +
            "\t#{salrActuGetSalary},\n" +
            "\t#{houseOrTELSubsidy},\n" +
            "\t#{hotTempOrOtherAllow},\n" +
            "\t#{fullWorkReword},\n" +
            "\t#{workYearsSalary},\n" +
            "\t#{sellCommi},\n" +
            "\t#{compreSalary},\n" +
            "\t#{buckFoodCost},\n" +
            "\t#{buckWaterEleCost},\n" +
            "\t#{buckOldAgeInsurCost},\n" +
            "\t#{buckMedicInsurCost},\n" +
            "\t#{buckUnEmployCost},\n" +
            "\t#{buckAccumCost},\n" +
            "\t#{otherBuckCost},\n" +
            "\t#{sixDeducCost},\n" +
            "\t#{personIncomTaxCost},\n" +
            "\t#{netPaySalary},\n" +
            "\t#{yearMonth} )")
    void saveSalaryDataOutPut(SalaryDataOutPut salaryDataOutPut);


    @Select("SELECT \n" +
            "\tdeptName,\n" +
            "\tpositionName,\n" +
            "\tpositionAttrName,\n" +
            "\tempNo,\n" +
            "\t`name`,\n" +
            "\tinCompDate,\n" +
            "\tbasickWorkHours,\n" +
            "\tnorAttenHours,\n" +
            "\tnorAttendSalary,\n" +
            "\tchinaPailLeavHours,\n" +
            "\tchinaPaidLeavSalary,\n" +
            "\totherPaidLeavHours,\n" +
            "\totherPaidLeavSalary,\n" +
            "\tbasicSalarySubTotal,\n" +
            "\tusualExtraHours,\n" +
            "\tusralExtraSalary,\n" +
            "\tweekendWorkHours,\n" +
            "\tweekendWorkSalary,\n" +
            "\tchinaHoliWorkHours,\n" +
            "\tchinaHoliWorkSalary,\n" +
            "\tcompressSalary,\n" +
            "\tjobSalary,\n" +
            "\tpositionSalary,\n" +
            "\tmeritSalary,\n" +
            "\tmeritScore,\n" +
            "\tsubbonusTotal,\n" +
            "\tsalorLevelSalary,\n" +
            "\tsalrActuGetSalary,\n" +
            "\thouseOrTELSubsidy,\n" +
            "\thotTempOrOtherAllow,\n" +
            "\tfullWorkReword,\n" +
            "\tworkYearsSalary,\n" +
            "\tsellCommi,\n" +
            "\tcompreSalary,\n" +
            "\tbuckFoodCost,\n" +
            "\tbuckWaterEleCost,\n" +
            "\tbuckOldAgeInsurCost,\n" +
            "\tbuckMedicInsurCost,\n" +
            "\tbuckUnEmployCost,\n" +
            "\tbuckAccumCost,\n" +
            "\totherBuckCost,\n" +
            "\tsixDeducCost,\n" +
            "\tpersonIncomTaxCost,\n" +
            "\tnetPaySalary,\n" +
            "\tyearMonth \n" +
            "FROM\n" +
            "\tsalarydataoutput " +
            "\t\tORDER BY\n" +
            "\t\t\t empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<SalaryDataOutPut> findAllSalaryDataOutPut(Employee employee);

    @Select("SELECT count(*) \n" +
            "FROM\n" +
            "\tsalarydataoutput")
    int findAllSalaryDataOutPutCount();


    @Update("update  financesetupdata " +
            " set norAttendHoursSample = #{norAttendHoursSample}," +
            "norAttendSalarySample = #{norAttendSalarySample}," +
            "norExtraMutiple = #{norExtraMutiple}," +
            "weekEndWorkMutiple =  #{weekEndWorkMutiple}," +
            "legalWorkMutiple = #{legalWorkMutiple}," +
            " meritScoreSample = #{meritScoreSample},basicWorkHours = #{basicWorkHours}")
    void updateFinanceSetUp(FinanceSetUpData financeSetUpData);

    @Select("select count(*) from emphours where empNo = #{empNo} and yearMonth=#{yearMonth} ")
    int checkEmpNoandYearMonthIsExsit(EmpHours empHours);

    @Select("select count(*) from financeimportdata where empNo = #{empNo} and yearMonth=#{yearMonth} ")
    int checkFinanceImportNoandYearMonthIsExsit(EmpHours empHours);

    @Select("select count(*) from financesetupdata")
    int findFinanceSetUpDataCount();

    @Select("select * from financesetupdata")
    FinanceSetUpData findFinanceSetUpData();

    @Insert("insert into salary (empno,compresalary,possalary,jobsalary,meritsalary,remark,state) " +
            "values (#{empNo},#{compreSalary},#{posSalary},#{jobSalary},#{meritSalary},#{remark},#{state})")
    void addSalaryByBean(Employee employee);

    @Update("update salary set compreSalary = #{compreSalary},posSalary = #{posSalary} , " +
            " jobSalary = #{jobSalary},meritSalary = #{meritSalary},remark = #{remark},state = #{state} " +
            " where empno = #{empNo} ")
    void updateSalaryByBean(Employee employee);

    @Select("select * from salary where empno = #{empno} and name = #{name}")
    Salary getSalaryByEmpno(String empno,String name);


    @Select("select * from salary where empno = #{empno} limit 1  ")
    Salary getSalaryByEmpnoA(String empno);


    @Select("select e.positionAttrId,n.positionName,e.name,e.incompdate from employee e" +
            " left join position n on e.positionid = n.id where e.empno = #{empno}")
    Employee getEmployeeByEmpno(String empno);

    @Select("select * from employee  where empno = #{empNo} and name = #{name} limit 1 ")
    Employee getEmployeeByEmpNoAndName(String empNo,String name);


    @Select("SELECT count(*) " +
            "\t\tFROM\n" +
            "\t\t\tfinanceimportdata ")
    int findAllFinanceImportDataCount();

    @Select("SELECT id," +
            "\tNAME,\n" +
            "\tempNo,\n" +
            "\tdeptName,\n" +
            "\tzhengbanHours,\n" +
            "\tusualExtHours,\n" +
            "\tworkendHours,\n" +
            "\tchinaPaidLeave,\n" +
            "\totherPaidLeave,\n" +
            "\tleaveOfAbsense,\n" +
            "\tsickLeave,\n" +
            "\totherAllo,\n" +
            "\tfullWorkReword,\n" +
            "\tfoodExpense,\n" +
            "\troomOrWaterEleExpense,\n" +
            "\toldAgeINsuran,\n" +
            "\tmedicalInsuran,\n" +
            "\tunEmployeeInsur,\n" +
            "\taccumulaFund,\n" +
            "\terrorInWork,\n" +
            "\tmeritScore,\n" +
            "\tyearMonth,sixDeductions " +
            "\t\tFROM\n" +
            "\t\t\temphours " +
            "\t\tORDER BY\n" +
            "\t\t\t empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<EmpHours> findAllEmpHours(Employee employee);

    @Select("SELECT id," +
            "\tNAME,\n" +
            "\tempNo,\n" +
            "\tdeptName,\n" +
            "\tzhengbanHours,\n" +
            "\tusualExtHours,\n" +
            "\tworkendHours,\n" +
            "\tchinaPaidLeave,\n" +
            "\totherPaidLeave,\n" +
            "\tleaveOfAbsense,\n" +
            "\tsickLeave,\n" +
            "\totherAllo,\n" +
            "\tfullWorkReword,\n" +
            "\tfoodExpense,\n" +
            "\troomOrWaterEleExpense,\n" +
            "\toldAgeINsuran,\n" +
            "\tmedicalInsuran,\n" +
            "\tunEmployeeInsur,\n" +
            "\taccumulaFund,\n" +
            "\terrorInWork,\n" +
            "\tmeritScore,\n" +
            "\tyearMonth," +
            "sixDeductions " +
            "\t\tFROM\n" +
            "\t\t\temphours " +
            " where yearMonth = #{yearMonth}")
    List<EmpHours> getAllEmpHoursByYearMonth(String yearMonth);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\tempNo,\n" +
            "\t`name`,\n" +
            "\tdeptName,\n" +
            "\tlegalHolidWorkHours,\n" +
            "\tsellActual,\n" +
            "\tsellThreshold,\n" +
            "\tsellLevelSalary,\n" +
            "\thouseSubsidy,\n" +
            "\thotTempOrOtherAllow,\n" +
            "\tworkYearsSalary,\n" +
            "\tsellCommi,\n" +
            "\tyearMonth,speciAddDeductCost, \n" +
            "\tremark\n" +
            "FROM\n" +
            "\tfinanceimportdata " +
            "\tORDER BY\n" +
            "\t empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<FinanceImportData> findAllFinanceImportData(Employee employee);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\tempNo,\n" +
            "\t`name`,\n" +
            "\tdeptName,\n" +
            "\tlegalHolidWorkHours,\n" +
            "\tsellActual,\n" +
            "\tsellThreshold,\n" +
            "\tsellLevelSalary,\n" +
            "\thouseSubsidy,\n" +
            "\thotTempOrOtherAllow,\n" +
            "\tworkYearsSalary,\n" +
            "\tsellCommi,\n" +
            "\tyearMonth,speciAddDeductCost,\n" +
            "\tremark\n" +
            "FROM\n" +
            "\tfinanceimportdata " +
            "\t where id = #{id}")
    FinanceImportData getFinanceImportDataById(Integer id);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\tempNo,\n" +
            "\t`name`,\n" +
            "\tdeptName,\n" +
            "\tlegalHolidWorkHours,basicWorkHours,\n" +
            "\tsellActual,\n" +
            "\tsellThreshold,\n" +
            "\tsellLevelSalary,\n" +
            "\thouseSubsidy,\n" +
            "\thotTempOrOtherAllow,\n" +
            "\tworkYearsSalary,\n" +
            "\tsellCommi,\n" +
            "\tyearMonth,speciAddDeductCost,\n" +
            "\tremark\n" +
            "FROM\n" +
            "\tfinanceimportdata " +
            "\t where empNo = #{empNo} and yearMonth = #{yearMonth} and name = #{name} ")
    FinanceImportData getFinanceImportDataByEmpNoandYearMonth(String empNo,String name,String yearMonth);

    @Select("SELECT id," +
            "\tNAME,\n" +
            "\tempNo,\n" +
            "\tdeptName,\n" +
            "\tzhengbanHours,\n" +
            "\tusualExtHours,\n" +
            "\tworkendHours,\n" +
            "\tchinaPaidLeave,\n" +
            "\totherPaidLeave,\n" +
            "\tleaveOfAbsense,\n" +
            "\tsickLeave,\n" +
            "\totherAllo,\n" +
            "\tfullWorkReword,\n" +
            "\tfoodExpense,\n" +
            "\troomOrWaterEleExpense,\n" +
            "\toldAgeINsuran,\n" +
            "\tmedicalInsuran,\n" +
            "\tunEmployeeInsur,\n" +
            "\taccumulaFund,\n" +
            "\terrorInWork,\n" +
            "\tmeritScore,\n" +
            "\tyearMonth,sixDeductions " +
            "\t\tFROM\n" +
            "\t\t\temphours where id = #{id} ")
    EmpHours getEmpHoursByEmpNo(Integer id);

    @Select("SELECT count(*) " +
            "\t\tFROM\n" +
            "\t\t\temphours ")
    int findAllEmpHoursHours();

    @Delete({
            "<script>",
            "delete",
            "from salary",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteEmpSalaryByBatch(@Param("ids") List<Integer> ids);

    @Delete({
            "<script>",
            "delete",
            "from financeimportdata",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteFinanceImportDataByBatch(@Param("ids") List<Integer> ids);

    @Delete({
            "<script>",
            "delete",
            "from emphours",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteEmpHoursByBatch(@Param("ids") List<Integer> ids);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "queryFinanceImportDataByCondition")
    List<FinanceImportData> queryFinanceImportDataByCondition(Employee employee);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "queryFinanceImportDataByConditionCount")
    int queryFinanceImportDataByConditionCount(Employee employee);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "queryEmployeeHoursByCondition")
    List<EmpHours> queryEmployeeHoursByCondition(Employee employee);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "querySalaryDataOutPutByCondition")
    List<SalaryDataOutPut> querySalaryDataOutPutByCondition(Employee employee);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "querySalaryDataOutPutByConditionCount")
    int querySalaryDataOutPutByConditionCount(Employee employee);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "queryEmployeeHoursByConditionCount")
    int queryEmployeeHoursByConditionCount(Employee employee);

    class FinanceDaoProvider {

        public String queryEmployeeHoursByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) FROM \n" +
                    "\temphours e\n" +
                    "LEFT JOIN employee ee ON ee.empno = e.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }

            return sb.toString();
        }

        public String queryFinanceImportDataByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tf.id,\n" +
                    "\tf.empNo,\n" +
                    "\tf.`name`,\n" +
                    "\tf.deptName,\n" +
                    "\tf.legalHolidWorkHours,\n" +
                    "\tf.sellActual,\n" +
                    "\tf.sellThreshold,\n" +
                    "\tf.sellLevelSalary,\n" +
                    "\tf.houseSubsidy,\n" +
                    "\tf.hotTempOrOtherAllow,\n" +
                    "\tf.workYearsSalary,\n" +
                    "\tf.sellCommi,\n" +
                    "\tf.yearMonth,f.speciAddDeductCost,\n" +
                    "\tf.remark\n" +
                    "FROM\n" +
                    "\tfinanceimportdata f " +
                    "LEFT JOIN employee ee ON ee.empno = f.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and f.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by f.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(employee.getSortByName())) {
                    sb.append(" order by f.yearMonth ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by f.empNo ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by f.deptName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("legalHolidWorkHours".equals(employee.getSortByName())) {
                    sb.append(" order by f.legalHolidWorkHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sellActual".equals(employee.getSortByName())) {
                    sb.append(" order by f.sellActual ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sellThreshold".equals(employee.getSortByName())) {
                    sb.append(" order by f.sellThreshold ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sellLevelSalary".equals(employee.getSortByName())) {
                    sb.append(" order by f.sellLevelSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("houseSubsidy".equals(employee.getSortByName())) {
                    sb.append(" order by f.houseSubsidy ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("hotTempOrOtherAllow".equals(employee.getSortByName())) {
                    sb.append(" order by f.hotTempOrOtherAllow ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("workYearsSalary".equals(employee.getSortByName())) {
                    sb.append(" order by f.workYearsSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sellCommi".equals(employee.getSortByName())) {
                    sb.append(" order by f.sellCommi ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(employee.getSortByName())) {
                    sb.append(" order by f.yearMonth ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("remark".equals(employee.getSortByName())) {
                    sb.append(" order by f.remark ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("speciAddDeductCost".equals(employee.getSortByName())) {
                    sb.append(" order by f.speciAddDeductCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }

            } else {
                sb.append(" order by f.empno asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryFinanceImportDataByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) \n" +
                    "FROM\n" +
                    "\tfinanceimportdata f " +
                    "LEFT JOIN employee ee ON ee.empno = f.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and f.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }

            return sb.toString();
        }


        public String querySalaryDataOutPutByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*)  " +
                    "\t\t FROM \n" +
                    "\tsalarydataoutput s \n" +
                    "LEFT JOIN employee ee ON ee.empno = s.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and s.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }
            return sb.toString();
        }

        public String querySalaryDataOutPutByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT s.deptName,\n" +
                    "\ts.positionName,\n" +
                    "\ts.positionAttrName,\n" +
                    "\ts.empNo,\n" +
                    "\ts.`name`,\n" +
                    "\ts.inCompDate,\n" +
                    "\ts.basickWorkHours,\n" +
                    "\ts.norAttenHours,\n" +
                    "\ts.norAttendSalary,\n" +
                    "\ts.chinaPailLeavHours,\n" +
                    "\ts.chinaPaidLeavSalary,\n" +
                    "\ts.otherPaidLeavHours,\n" +
                    "\ts.otherPaidLeavSalary,\n" +
                    "\ts.basicSalarySubTotal,\n" +
                    "\ts.usualExtraHours,\n" +
                    "\ts.usralExtraSalary,\n" +
                    "\ts.weekendWorkHours,\n" +
                    "\ts.weekendWorkSalary,\n" +
                    "\ts.chinaHoliWorkHours,\n" +
                    "\ts.chinaHoliWorkSalary,\n" +
                    "\ts.compressSalary,\n" +
                    "\ts.jobSalary,\n" +
                    "\ts.positionSalary,\n" +
                    "\ts.meritSalary,\n" +
                    "\ts.meritScore,\n" +
                    "\ts.subbonusTotal,\n" +
                    "\ts.salorLevelSalary,\n" +
                    "\ts.salrActuGetSalary,\n" +
                    "\ts.houseOrTELSubsidy,\n" +
                    "\ts.hotTempOrOtherAllow,\n" +
                    "\ts.fullWorkReword,\n" +
                    "\ts.workYearsSalary,\n" +
                    "\ts.sellCommi,\n" +
                    "\ts.compreSalary,\n" +
                    "\ts.buckFoodCost,\n" +
                    "\ts.buckWaterEleCost,\n" +
                    "\ts.buckOldAgeInsurCost,\n" +
                    "\ts.buckMedicInsurCost,\n" +
                    "\ts.buckUnEmployCost,\n" +
                    "\ts.buckAccumCost,\n" +
                    "\ts.otherBuckCost,\n" +
                    "\ts.sixDeducCost,\n" +
                    "\ts.personIncomTaxCost,\n" +
                    "\ts.netPaySalary,\n" +
                    "\ts.yearMonth  " +
                    "\t\t FROM \n" +
                    "\tsalarydataoutput s \n" +
                    "LEFT JOIN employee ee ON ee.empno = s.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and s.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by s.deptName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by s.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionAttrName".equals(employee.getSortByName())) {
                    sb.append(" order by s.positionAttrName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by s.empNo ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by s.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("inCompDate".equals(employee.getSortByName())) {
                    sb.append(" order by s.inCompDate ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("basickWorkHours".equals(employee.getSortByName())) {
                    sb.append(" order by s.basickWorkHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("norAttenHours".equals(employee.getSortByName())) {
                    sb.append(" order by s.norAttenHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("norAttendSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.norAttendSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("chinaPailLeavHours".equals(employee.getSortByName())) {
                    sb.append(" order by s.chinaPailLeavHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("chinaPaidLeavSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.chinaPaidLeavSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherPaidLeavHours".equals(employee.getSortByName())) {
                    sb.append(" order by s.otherPaidLeavHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherPaidLeavSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.otherPaidLeavSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("basicSalarySubTotal".equals(employee.getSortByName())) {
                    sb.append(" order by s.basicSalarySubTotal ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("usualExtraHours".equals(employee.getSortByName())) {
                    sb.append(" order by s.usualExtraHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("usralExtraSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.usralExtraSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("weekendWorkHours".equals(employee.getSortByName())) {
                    sb.append(" order by s.weekendWorkHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("weekendWorkSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.weekendWorkSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("chinaHoliWorkHours".equals(employee.getSortByName())) {
                    sb.append(" order by s.chinaHoliWorkHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("chinaHoliWorkSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.chinaHoliWorkSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("compressSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.compressSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("jobSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.jobSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("positionSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.positionSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("meritSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.meritSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("meritScore".equals(employee.getSortByName())) {
                    sb.append(" order by s.meritScore ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("subbonusTotal".equals(employee.getSortByName())) {
                    sb.append(" order by s.subbonusTotal ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("salorLevelSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.salorLevelSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("salrActuGetSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.salrActuGetSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("houseOrTELSubsidy".equals(employee.getSortByName())) {
                    sb.append(" order by s.houseOrTELSubsidy ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("hotTempOrOtherAllow".equals(employee.getSortByName())) {
                    sb.append(" order by s.hotTempOrOtherAllow ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("fullWorkReword".equals(employee.getSortByName())) {
                    sb.append(" order by s.fullWorkReword ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("workYearsSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.workYearsSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("sellCommi".equals(employee.getSortByName())) {
                    sb.append(" order by s.sellCommi ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("compreSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.compreSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("buckFoodCost".equals(employee.getSortByName())) {
                    sb.append(" order by s.buckFoodCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("buckWaterEleCost".equals(employee.getSortByName())) {
                    sb.append(" order by s.buckWaterEleCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("buckOldAgeInsurCost".equals(employee.getSortByName())) {
                    sb.append(" order by s.buckOldAgeInsurCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("buckMedicInsurCost".equals(employee.getSortByName())) {
                    sb.append(" order by s.buckMedicInsurCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("buckUnEmployCost".equals(employee.getSortByName())) {
                    sb.append(" order by s.buckUnEmployCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("buckAccumCost".equals(employee.getSortByName())) {
                    sb.append(" order by s.buckAccumCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("otherBuckCost".equals(employee.getSortByName())) {
                    sb.append(" order by s.otherBuckCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("sixDeducCost".equals(employee.getSortByName())) {
                    sb.append(" order by s.sixDeducCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("personIncomTaxCost".equals(employee.getSortByName())) {
                    sb.append(" order by s.personIncomTaxCost ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("netPaySalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.netPaySalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("yearMonth".equals(employee.getSortByName())) {
                    sb.append(" order by s.yearMonth ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }

            } else {
                sb.append(" order by s.empno asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryEmployeeHoursByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT e.id," +
                    "\te.NAME,\n" +
                    "\te.empNo,\n" +
                    "\te.deptName,\n" +
                    "\te.zhengbanHours,\n" +
                    "\te.usualExtHours,\n" +
                    "\te.workendHours,\n" +
                    "\te.chinaPaidLeave,\n" +
                    "\te.otherPaidLeave,\n" +
                    "\te.leaveOfAbsense,\n" +
                    "\te.sickLeave,\n" +
                    "\te.otherAllo,\n" +
                    "\te.fullWorkReword,\n" +
                    "\te.foodExpense,\n" +
                    "\te.roomOrWaterEleExpense,\n" +
                    "\te.oldAgeINsuran,\n" +
                    "\te.medicalInsuran,\n" +
                    "\te.unEmployeeInsur,\n" +
                    "\te.accumulaFund,\n" +
                    "\te.errorInWork,\n" +
                    "\te.meritScore,\n" +
                    "\te.yearMonth,e.sixDeductions " +
                    "\t\t FROM \n" +
                    "\temphours e\n" +
                    "LEFT JOIN employee ee ON ee.empno = e.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(employee.getSortByName())) {
                    sb.append(" order by e.yearMonth ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empNo ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by e.deptName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("zhengbanHours".equals(employee.getSortByName())) {
                    sb.append(" order by e.zhengbanHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("usualExtHours".equals(employee.getSortByName())) {
                    sb.append(" order by e.usualExtHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("workendHours".equals(employee.getSortByName())) {
                    sb.append(" order by e.workendHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("chinaPaidLeave".equals(employee.getSortByName())) {
                    sb.append(" order by e.chinaPaidLeave ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherPaidLeave".equals(employee.getSortByName())) {
                    sb.append(" order by e.otherPaidLeave ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("leaveOfAbsense".equals(employee.getSortByName())) {
                    sb.append(" order by e.leaveOfAbsense ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sickLeave".equals(employee.getSortByName())) {
                    sb.append(" order by e.sickLeave ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherAllo".equals(employee.getSortByName())) {
                    sb.append(" order by e.otherAllo ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("fullWorkReword".equals(employee.getSortByName())) {
                    sb.append(" order by e.fullWorkReword ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("foodExpense".equals(employee.getSortByName())) {
                    sb.append(" order by e.foodExpense ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("roomOrWaterEleExpense".equals(employee.getSortByName())) {
                    sb.append(" order by e.roomOrWaterEleExpense ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("oldAgeINsuran".equals(employee.getSortByName())) {
                    sb.append(" order by e.oldAgeINsuran ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("medicalInsuran".equals(employee.getSortByName())) {
                    sb.append(" order by e.medicalInsuran ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("unEmployeeInsur".equals(employee.getSortByName())) {
                    sb.append(" order by e.unEmployeeInsur ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("accumulaFund".equals(employee.getSortByName())) {
                    sb.append(" order by e.accumulaFund ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("errorInWork".equals(employee.getSortByName())) {
                    sb.append(" order by e.errorInWork ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("meritScore".equals(employee.getSortByName())) {
                    sb.append(" order by e.meritScore ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("remark".equals(employee.getSortByName())) {
                    sb.append(" order by e.remark ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }else if ("sixDeductions".equals(employee.getSortByName())) {
                    sb.append(" order by e.sixDeductions ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }

            } else {
                sb.append(" order by e.empno asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }
    }


}
