package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.entity.OutClockIn;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPersonServ {

    String getLinShiInComDateByName(String name) throws Exception;

    int checkAndSavePosition(Position position) throws Exception;

    List<String> getAllUserName() throws Exception;

    void fillEmpNoWhenQYWXNull() throws Exception;

    List<ClockInOrgin> translateTabletoBean(MultipartFile file) throws Exception;

    List<ZhongKongBean> getAllKQDataByYearMonthDayA(String date) throws Exception;

    List<OutPutWorkData> computeTableListData(List<ClockInOrgin> clockInOrginList) throws Exception;

    List<Employee> translateTabletoEmployeeBean(MultipartFile file) throws Exception;

    void saveDeptNameAndPositionNameAndEms(List<Employee> employeeList) throws Exception;

    String checkEmpNoOrEmpNameRepeat(List<Employee> employeeList) throws Exception;

    void translateAllTable(MultipartFile file) throws Exception;

    void saveZKNumEmpNoBangDing(List<Employee> employeeList) throws Exception;

    List<Employee> translateTabletoEmployeeBeanZK(List<MultipartFile> files) throws Exception;

    List<Out> translateTabletoOutBean(MultipartFile mf) throws Exception;

    List<AccuFund> translateTabletoAccuFundBean(MultipartFile mf) throws Exception;

    void saveAccuFundList(List<AccuFund> accuFundList) throws Exception;

    List<Insurance> translateTabletoInsuranceBean(MultipartFile file) throws Exception;

    AccuFund getAFByEmpNo(String empNo) throws Exception;

    Insurance getISByEmpNo(String empNo) throws Exception;

    void saveInsuranceList(List<Insurance> insuranceList) throws Exception;

    String checkOutRepeat(List<Out> outList) throws Exception;

    Insurance getISByName(String name) throws Exception;

    AccuFund getAFByName(String name) throws Exception;

    void saveOutList(List<Out> outList) throws Exception;

    String getDeptNameByEmployId(Integer id) throws Exception;

    Double getTotalHoursByDateStr(TiaoXiu tiaoXiu) throws Exception;

    WorkDate getWorkDateByMonth(WorkDate workDate) throws Exception;

    List<SmallEmployee> findAllEmployeeByPositionLevel(String positionLevel) throws Exception;

    WorkSet getWorkSetByMonthAndPositionLevel(WorkDate workDate) throws Exception;

    String getPositionNamesByPositionLevel(String positionLevel) throws Exception;

    WorkDate getWorkDateByMonth2(WorkDate workDate) throws Exception;

    void saveOrUpdateWorkData(WorkDate workDate) throws Exception;

    int checkAndSaveDept(String deptName) throws Exception;

    int checkEmployIsExsit(String name) throws Exception;

    int checkEmployNoIsExsit(String empoyeeNo) throws Exception;

    List<Position> findAllPositionAll() throws Exception;

    void deleteTiaoXiuById(Integer id) throws Exception;

    void deleteLinShiDateToMysql(LinShiHours lsh) throws Exception;

    List<LinShiHours> findAllLinShiDan(LinShiHours linShiHours) throws Exception;

    int findAllLinShiDanCount() throws Exception;

    List<Employee> findAllEmployeeLinShiAll() throws Exception;

    void updateRenShiByDates(List<String> dates) throws Exception;

    void deleteOutDateToMysql(Integer id) throws Exception;

    void deleteJiaBanDateToMysql(Integer id) throws Exception;

    void deleteLianBanDateToMysql(Integer id) throws Exception;

    String getWorkDateByMonthC(String yearMonth) throws Exception;

    String getWorkDateByMonthD(String yearMonth) throws Exception;

    void deletePinShiDateToMysql(Integer id) throws Exception;

    void deleteYeBanDateToMysql(Integer id) throws Exception;

    void deleteQianKaDateToMysql(Integer id) throws Exception;

    List<MonthKQInfo> findAllMonthKQData(String yearMonth, Employee employee) throws Exception;

    boolean computeSignKQByMonth(String yearMonth) throws Exception;

    List<MonthKQInfo> findAllMonthKQDataByCondition(Employee employee) throws Exception;

    List<MonthKQInfo> findAllMonthKQDataByConditionBDF(Employee ee) throws Exception;

    List<MonthKQInfo> findAllMonthKQDataByConditionABC(Employee ee) throws Exception;

    int findAllMonthKQDataCountByCondition(Employee employee) throws Exception;

    List<MonthKQInfo> queryMKDataByCondition(Employee employee) throws Exception;

    int queryMKDataByConditionCount(Employee employee) throws Exception;

    int findAllMonthKQDataCount(String yearMonth) throws Exception;

    List<KQBean> findAllKQBDataCondition(Employee employee) throws Exception;

    int findAllKQBDataConditionCount(Employee employee) throws Exception;

    void saveCheckKQBeanListByDates(List<OutClockIn> outClockIns) throws Exception;

    void saveMonthKQInfoByCheckKQBean(List<OutClockIn> outClockInList) throws Exception;

    List<String> getAllKQDateList() throws Exception;

    List<String> getAllKQDateListAAA() throws Exception;

    List<String> getAllMKMonthList() throws Exception;

    List<String> getAllKQMonthList() throws Exception;

    void updateKQBeanDataByRenShi(Integer id, Double extHours, Integer state) throws Exception;

    List<ReturnString> getAllDataByName(String name,String dateStr) throws Exception;

    int saveOrUpdateZhongKongIdByEmpNo(WeiXinUsrId zhongKongEmployee) throws Exception;

    void deleteLeaveByBatch(List<Integer> ids) throws Exception;

    int saveOrUpdateGongZhongHaoIdByEmpNo(GongZhongHao gongZhongHao) throws Exception;

    void updateLeaveToMysql(Leave leave) throws Exception;

    List<Employee> findAllEmployees() throws Exception;

    Leave getLeaveById(Integer id) throws Exception;

    List<Dept> findAllDeptAll() throws Exception;

    List<Employee> findEmpAndLinS() throws Exception;

    List<TiaoXiu> findAllTiaoXiu(TiaoXiu tiaoXiu) throws Exception;

    int findAllTiaoXiuCount() throws Exception;

    List<String> getAllKQMonthListKQBean() throws Exception;

    List<Out> findAllOut(Out out) throws Exception;

    List<QianKa> findAllQianKa(QianKa qianKa) throws Exception;

    int findAllQianKaCount() throws Exception;

    void deleteClockSetInByOutDays(Double outDays) throws Exception;

    List<ClockInSetUp> findAllOutClockInSetUp() throws Exception;

    void saveDAPCSetUp(DaKaPianCha daKaPianCha) throws Exception;

    void deleteLeaveById(Integer id) throws Exception;

    DaKaPianCha getDaKaPianCha() throws Exception;

    void updateEmployeeData(MultipartFile educationLeFile, MultipartFile sateListAndLeaCertiFile, MultipartFile otherCertiFile, Employee employee) throws Exception;

    List<Position> findAllPosition(Position position) throws Exception;

    void addEmployeeData(MultipartFile educationLeFile, MultipartFile sateListAndLeaCertiFile, MultipartFile otherCertiFile, Employee employee) throws Exception;

    List<Employee> findAllEmployeeAll() throws Exception;

    List<String> findAllZKYearMonthList() throws Exception;

    List<Employee> findAllZKAndOutDataCondition(Employee employee) throws Exception;

    int findAllZKAndOutDataConditionCount(Employee ee) throws Exception;

    List<String> getAllKQDateListABC(String yearMonth) throws Exception;

    List<Employee> findAllLSZKAndOutDataCondition(Employee employee) throws Exception;

    int findAllLSZKAndOutDataConditionCount(Employee employee) throws Exception;

    Integer getDeptIdByDeptName(String deptName) throws Exception;

    void updateEmployeeDeptIdById(Integer empId, Integer deptId) throws Exception;

    List<PinShiJiaBanBGS> findAllPinShi(PinShiJiaBanBGS ps) throws Exception;

    List<Employee> findAllEmployeeAllOnlyBanGong() throws Exception;

    int findAllPinShiCount() throws Exception;

    List<YeBan> findAllYeBan(YeBan yeBan) throws Exception;

    int findAllYeBanCount() throws Exception;

    List<LianBan> findAllLianBan(LianBan lianBan) throws Exception;

    int findAllLianBanCount() throws Exception;

    List<JiaBan> findAllJiaBan(JiaBan jiaBan) throws Exception;

    int findAllJiaBanCount() throws Exception;

    List<KQBean> queryKQBeanDataByCondition(KQBean kqBean) throws Exception;

    int queryKQBeanDataByConditionCount(KQBean kqBean) throws Exception;

    List<KQBean> findAllKQBData(Employee employee) throws Exception;

    int findAllKQBDataCount() throws Exception;

    List<WorkSet> getAllWorkSetListByYearMonth(String yearMonth) throws Exception;

    List<WorkDate> getAllWorkDateListByYearMonth(String yearMonth) throws Exception;

    List<KQBean> getAllKQDataByYearMonthDay(String date) throws Exception;

    void saveOutClockInList(List<OutClockIn> outClockInList,List<OutClockAll> outClockAllList) throws Exception;

    void saveZKQYList(List<ZhongKongBeanQY> qyList) throws Exception;

    void saveQKData(List<QYWXSPFROM> fromList) throws Exception;

    void getKQ(String beforDay) throws Exception;

    List<KQBean> getAfterOperatorDataByOriginData(List<OutClockIn> clockDates, List<KQBean> kqBeans) throws Exception;

    List<KQBean> getgetAfterOperatorDataByOriginDataLinShi(List<OutClockIn> clockDates, List<KQBean> kqBeans) throws Exception;

    List<KQBean> getgetAfterOperatorDataByOriginDataZhongDian(List<OutClockIn> clockDates, List<KQBean> kqBeans) throws Exception;

    List<KQBean> getAllKQDataByYearMonthDays(List<OutClockIn> clockDates) throws Exception;

    WeiXinUsrId getUserIdByUSerId(String userId) throws Exception;

    String getTimeStrByDateStrAndEmpNo(String empNo,String ymdStr) throws Exception;

    String getTimeStrByDateStrAndNameLinShiGong(String name,String ymdStr) throws Exception;

    void saveWeiXinUserIdByBean(WeiXinUsrId wx) throws Exception;

    String getAlReadyCheckDatestr(List<OutClockIn> clockDates) throws Exception;

    void deleteKQBeanOlderDateByDates(List<OutClockIn> clockDates) throws Exception;

    void saveAllNewKQBeansToMysql(List<KQBean> kqBeanList) throws Exception;

    List<Employee> queryZKOUTDataByCondition(Employee employee) throws Exception;

    int queryZKOUTDataByConditionCount(Employee employee) throws Exception;

    List<Employee> findAllZKAndOutData(Employee employee) throws Exception;

    int findAllZKAndOutDataCount() throws Exception;

    List<Employee> findAllEmployeeZhongKong(Employee employee) throws Exception;

    int findAllEmployeeZhongKongCount() throws Exception;

    void saveBeforeDayZhongKongData(List<ZhongKongBean> zkbList) throws Exception;

    List<Employee> findAllEmployeeNotIsQuitandhaveEnrollNum() throws Exception;

    List<ClockInSetUp> findAllCLockInSetUp() throws Exception;

    List<Leave> findAllLeaveByWeiXinId(String openId) throws Exception;

    String getNameByWeiXinId(String openId) throws Exception;

    List<Employee> findAllEmployeeOutClockIn(Employee employee) throws Exception;

    int findAllEmployeeOutClockInCount(Employee employee) throws Exception;

    OutClockIn getOutClockInById(Integer id) throws Exception;

    boolean saveClockInSetUp(ClockInSetUp clockInSetUp) throws Exception;

    int saveQianKaDateToMysql(QianKa qianKa) throws Exception;

    int saveLianBanDateToMysql(LianBan lianBan) throws Exception;

    int saveLinShiDateToMysql(LinShiHours lsh) throws Exception;

    int saveOutDateToMysql(Out out) throws Exception;

    int savePinShiDateToMysql(PinShiJiaBanBGS pinShiJiaBanBGS) throws Exception;

    int saveJiaBanDateToMysql(JiaBan jiaBan) throws Exception;

    int saveYeBanDateToMysql(YeBan yeBan) throws Exception;

    int saveTiaoXiuDateToMysql(TiaoXiu tiaoXiu) throws Exception;

    Employee getEmployeeByEmpno(String empNo) throws Exception;

    void deleteEmployeeSalaryByEmpno(String empNo) throws Exception;

    List<Employee> findAllEmployeeFinance(Employee employee) throws Exception;

    List<Employee> findAllEmployee(Employee employee) throws Exception;

    List<WorkSet> findAllWorkSet(WorkSet workSet) throws Exception;

    List<Employee> findAllLinShiEmpAll() throws Exception;

    List<Employee> findAllLSZKAndOutData(Employee employee) throws Exception;

    int findAllLSZKAndOutDataCount() throws Exception;

    List<Employee> findAllLinShiEmp(Employee ee) throws Exception;

    int findAllLinShiEmpCount() throws Exception;

    List<Employee> queryEmployeeSalaryByCondition(Employee employee) throws Exception;

    int queryEmployeeSalaryByConditionCount(Employee employee) throws Exception;

    void updateWorkSetDataById(WorkSet workSet) throws Exception;

    void deleteWorkSetById(WorkSet workSet) throws Exception;

    int findAllWorkSetCount(WorkSet workSet) throws Exception;

    List<Employee> getEmployeeById(Integer id) throws Exception;


    List<Employee> getEmployeeById(String id) throws Exception;

    WorkSet getWorkSetById(Integer id) throws Exception;

    void addWorkSetData(WorkSet workSet) throws Exception;

    void saveOrUpdateOutClockInDataUrl(OutClockIn outClockIn) throws Exception;

    List<Employee> queryEmployeeByCondition(Employee employee) throws Exception;

    List<Employee> queryLinShiByCondition(Employee ee) throws Exception;

    int queryLinShiByConditionConditionCount(Employee ee) throws Exception;

    List<Employee> queryGongZhongHaoByCondition(Employee employee) throws Exception;

    List<Employee> queryZhongKongByCondition(Employee employee) throws Exception;

    int queryZhongKongByConditionCount(Employee employee) throws Exception;

    List<Employee> queryOutClockInByCondition(Employee employee) throws Exception;

    int queryOutClockInByConditionCount(Employee employee) throws Exception;

    List<OutClockIn> findAllOutClockInByOpenId(String openId) throws Exception;

    int queryGongZhongHaoByConditionCount(Employee employee) throws Exception;

    List<String> findAllOpenId() throws Exception;

    List<Employee> findLeaveDataUionOutClockData(String monday, String tuesday, String wednesday, String thurday, String today) throws Exception;

    List<ClockInSetUp> findAllClockInSetUp() throws Exception;

    List<Leave> findAllLeave(String monday, String tuesday, String wednesday, String thurday, String today) throws Exception;

    List<WorkSet> queryWorkSetByCondition(WorkSet workSet) throws Exception;

    int isClockInAlready(String openId, String dateStr, String titileName) throws Exception;

    void saveOrUpdateOutClockInData(OutClockIn outClockIn) throws Exception;

    int queryWorkSetByConditionCount(WorkSet workSet) throws Exception;

    List<Leave> queryLeaveByCondition(Leave leave) throws Exception;

    void deleteEmpByBatch(List<Integer> ids) throws Exception;

    int queryLeaveByConditionCount(Leave leave) throws Exception;

    int queryEmployeeByConditionCount(Employee employee) throws Exception;

    List<Dept> findAllDept(Dept dept) throws Exception;

    void deleteDeptByBatch(List<Integer> ids) throws Exception;

    int checkBeginLeaveRight(Leave leave) throws Exception;

    List<QianKa> queryQKByCondition(QianKa qianKa) throws Exception;

    List<LianBan> queryLBByCondition(LianBan lianBan) throws Exception;


    List<LinShiHours> queryLSHByCondition(LinShiHours lsh) throws Exception;

    int queryLSHByConditionCount(LinShiHours lsh) throws Exception;

    List<JiaBan> queryJBByCondition(JiaBan jiaBan) throws Exception;

    List<TiaoXiu> queryTXByCondition(TiaoXiu tiaoXiu) throws Exception;

    int queryTXByConditionCount(TiaoXiu tiaoXiu) throws Exception;

    List<Out> queryOutByCondition(Out out) throws Exception;

    int queryOutByConditionCount(Out out) throws Exception;

    int queryJBByConditionCount(JiaBan jiaBan) throws Exception;

    int queryLBByConditionCount(LianBan lianBan) throws Exception;

    int queryQKByConditionCount(QianKa qianKa) throws Exception;


    List<YeBan> queryYBByCondition(YeBan yeBan) throws Exception;


    List<PinShiJiaBanBGS> queryPSByCondition(PinShiJiaBanBGS pinShiJiaBanBGS) throws Exception;

    int queryPSByConditionCount(PinShiJiaBanBGS pinShiJiaBanBGS) throws Exception;

    int queryYBByConditionCount(YeBan yeBan) throws Exception;

    void deleteEmployeetById(Integer deleteEmployeetById) throws Exception;

    int findAllEmployeeCount() throws Exception;

    List<Leave> findAllLeave(Leave leave) throws Exception;

    void addLeaveData(Leave leave) throws Exception;

    int findAllLeaveCount() throws Exception;

    int findAllDeptConditionCount(Dept dep) throws Exception;

    void deletePositionById(Integer id) throws Exception;

    void deletePositionByIdBatch(List<Integer> ids) throws Exception;

    void deleteDeptById(Integer id) throws Exception;

    List<Position> queryPositionByName(String positionName) throws Exception;

    List<Position> queryPositionByNameA(Position position) throws Exception;

    List<Dept> queryDeptByNameA(Dept dept) throws Exception;

    int queryPositionCountByNameA(Position position) throws Exception;

    int queryDeptCountByNameA(Dept dept) throws Exception;

    void saveUpdateData(Integer id, String positionName, String positionLevel) throws Exception;

    void saveUpdateData2(Integer id, String deptname) throws Exception;

    int findAllPositionConditionCount() throws Exception;

    int checkIfExsit(String positionName) throws Exception;

    int checkIfExsit2(String deptName) throws Exception;
}
