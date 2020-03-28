package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.PersonMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.tool.*;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:homey Wong
 * @date:2019/5/9 下午 5:03
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PersonServiceImpl implements IPersonServ {

    private static Logger logger = LogManager.getLogger(PersonServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @Autowired
    PersonMapper personMapper;


    String EnrollNumberTitle = "考勤号码";
    Integer EnrollNumberTitleIndex;
    String nameTitle = "姓名";//姓名
    Integer nameTitleIndex;
    String sexTitle = "性别";//性别
    Integer sexTitleIndex;
    String deptIdTitle = "部门";//部门编号
    Integer deptIdTitleIndex;
    String empNoTitle = "编号";//工号
    Integer empNoTitleIndex;
    String positionIdTitle = "职务";//职位ID
    Integer positionIdTitleIndex;
    String positionAttrIdTitle = "职位";
    Integer positionAttrIdTitleIndex;
    String incompdateTitle = "到职日期";//入厂时间
    Integer incompdateTitleIndex;
    String conExpDateTitle = "合同到期时间";//合同到期时间
    Integer conExpDateTitleIndex;
    String birthDayTitle = "出生日期";//出生日期
    Integer birthDayTitleIndex;
    String ID_NOTitle = "身份证号码";//身份证号码
    Integer ID_NOTitleIndex;
    String nativePlaTitle = "籍贯";//籍guan
    Integer nativePlaTitleIndex;
    String homeAddrTtile = "家庭住址";//家庭住址
    Integer homeAddrTtileIndex;
    String valiPeriodOfIDTitle = "身份证到期时间";//身份证有效期
    Integer valiPeriodOfIDTitleIndex;
    String nationTitle = "民族";//民族
    Integer nationTitleIndex;
    String marriagedTitle = "婚否";//婚否
    Integer marriagedTitleIndex;
    String contactPhoneTitle = "联系电话";//联系电话
    Integer contactPhoneTitleIndex;
    String educationLeTitle = "学历";//学历
    Integer educationLeTitleIndex;
    String screAgreementTitle = "保密协议";//保密协议
    Integer screAgreementTitleIndex;
    String healthCertiTitle = "健康证";//健康证
    Integer healthCertiTitleIndex;
    String sateListAndLeaCertiTitle = "社保清单或离职证明";//社保清单或离职证明
    Integer sateListAndLeaCertiTitleIndex;
    String otherCertiTitle = "其他证件";//其它证件
    Integer otherCertiTitleIndex;
    String attributeTitle = "属性";
    Integer attributeTitleIndex;
    String remarkTitle = "备注";
    Integer remarkTitleIndex;


    private String timeStrDy2;

    public List<String> getAllKQDateListABC(String yearMonth) throws Exception {
        return personMapper.getAllKQDateListABC(yearMonth);
    }

    public void saveZKNumEmpNoBangDing(List<Employee> employeeList) throws Exception {
        List<Employee> singDataList = new ArrayList<Employee>();
        List<String> numList = new ArrayList<String>();
        for (Employee ee : employeeList) {
            if (!numList.contains(ee.getEnrollNumber())) {
                singDataList.add(ee);
                numList.add(ee.getEnrollNumber());
            }
        }


        for (Employee ee : singDataList) {
            String empNo = personMapper.getEmpNoByDeptNameAndName(ee.getName().trim(), ee.getDeptName().trim());
            if (empNo != null) {
                ee.setEmpNo(empNo);
                personMapper.saveZKBandDing(ee);
            } else {
                List<String> empNoList = personMapper.getEmpNoByName(ee.getName().trim());
                if (empNoList.size() == 1) {
                    ee.setEmpNo(empNoList.get(0));
                    personMapper.saveZKBandDing(ee);
                } else {
                    System.out.println(ee.getName());
                }
            }
        }
    }

    public void deletePositionByIdBatch(List<Integer> ids) throws Exception {
        personMapper.deletePositionByIdBatch(ids);
    }

    public void deleteDeptByBatch(List<Integer> ids) throws Exception {
        personMapper.deleteDeptByBatch(ids);
    }

    public void deleteEmpByBatch(List<Integer> ids) throws Exception {
        List<Employee> emlist = personMapper.getEmployeeByIds(ids);
        for (Employee ee : emlist) {
            String folderName = finalDirPath + "append/" + ee.getEmpNo() + "/";
            personMapper.deleteSalaryByEmpno(ee.getEmpNo());
            personMapper.deleteUserInfoByEmpNo(ee.getEmpNo());
            FileUtil.delFolderNew(folderName);
        }
        personMapper.deleteEmpByBatch(ids);

    }

    public boolean saveClockInSetUp(ClockInSetUp clockInSetUp) throws Exception {
        int len = personMapper.findIfExsit(clockInSetUp);
        if (len == 0) {
            personMapper.saveClockInSetUp(clockInSetUp);
            return true;
        } else {
            personMapper.updateClockInSetUp(clockInSetUp);
            return false;
        }
    }

    public List<ClockInSetUp> findAllOutClockInSetUp() throws Exception {
        return personMapper.findAllOutClockInSetUp();
    }

    public List<Employee> queryGongZhongHaoByCondition(Employee employee) throws Exception {
        return personMapper.queryGongZhongHaoByCondition(employee);
    }

    public int queryGongZhongHaoByConditionCount(Employee employee) throws Exception {
        return personMapper.queryGongZhongHaoByConditionCount(employee);
    }

    public int isClockInAlready(String openId, String dateStr, String titileName) throws Exception {
        return personMapper.isClockInAlready(openId, dateStr, titileName);
    }

    public void saveOrUpdateOutClockInDataUrl(OutClockIn outClockIn) throws Exception {
        OutClockIn orginal = personMapper.getOutClockInByDateAndWeiXinId(outClockIn);
        if (orginal == null) {
            personMapper.addPhotoOutClockInDateByBean(outClockIn);
        } else {
            if (outClockIn.getAmOnUrl() != null) {
                personMapper.updatePhotoClockInDateByAM(outClockIn);
            } else if (outClockIn.getPmOnUrl() != null) {
                personMapper.updatePhotoClockInDateByPM(outClockIn);
            } else if (outClockIn.getNmOnUrl() != null) {
                personMapper.updatePhotoClockInDateByNM(outClockIn);
            }
        }
    }

    public List<Leave> findAllLeaveByWeiXinId(String openId) throws Exception {
        return personMapper.findAllLeaveByWeiXinId(openId);
    }

    public String getNameByWeiXinId(String openId) throws Exception {
        return personMapper.getNameByWeiXinId(openId);
    }

    public List<ClockInSetUp> findAllCLockInSetUp() throws Exception {
        return personMapper.findAllCLockInSetUp();
    }

    public List<Employee> findAllEmployeeOutClockIn(Employee employee) throws Exception {
        return personMapper.findAllEmployeeOutClockIn(employee);
    }

    public int findAllEmployeeOutClockInCount(Employee employee) throws Exception {
        return personMapper.findAllEmployeeOutClockInCount(employee);
    }

    public List<String> findAllOpenId() throws Exception {
        return personMapper.findAllOpenId();
    }

    public List<Leave> findAllLeave(String monday, String tuesday, String wednesday, String thurday, String today) throws Exception {
        return personMapper.findAllLeaveA(monday, tuesday, wednesday, thurday, today);
    }

    public List<ClockInSetUp> findAllClockInSetUp() throws Exception {
        return personMapper.findAllClockInSetUp();
    }

    public List<Employee> findLeaveDataUionOutClockData(String monday, String tuesday, String wednesday, String thurday, String today) throws Exception {
        return personMapper.findLeaveDataUionOutClockData(monday, tuesday, wednesday, thurday, today);
    }

    public List<Out> findAllOut(Out out) throws Exception {
        return personMapper.findAllOut(out);
    }

    public int saveOutDateToMysql(Out out) throws Exception {
        DateFormat dftdatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Employee ee = personMapper.getEmployeeOneById(out.getEmpId());
        out.setEmpNo(ee.getEmpNo());
        Out lb = personMapper.getOutByEmpNoAndDateStr(ee.getEmpNo(), out.getDateStr());
        if (lb == null) {
            out.setInterDays(DateUtil.getDistanceOfTwoDate(dftdatetime.parse(out.getOuttimeStr()), dftdatetime.parse(out.getRealcomtimeStr())));
            personMapper.saveOutBeanToSql(out);
            return 1;
        } else {
            out.setInterDays(DateUtil.getDistanceOfTwoDate(dftdatetime.parse(out.getOuttimeStr()), dftdatetime.parse(out.getRealcomtimeStr())));
            personMapper.updateOutBean(out);
            return 2;
        }
    }

    public List<Out> queryOutByCondition(Out out) throws Exception {
        return personMapper.queryOutByCondition(out);
    }

    public int queryOutByConditionCount(Out out) throws Exception {
        return personMapper.queryOutByConditionCount(out);
    }

    public void deleteOutDateToMysql(Integer id) throws Exception {
        personMapper.deleteOutDateToMysql(id);
    }

    public OutClockIn getOutClockInById(Integer id) throws Exception {
        return personMapper.getOutClockInById(id);
    }

    public void saveOutList(List<Out> outList) throws Exception {
        String empNo = null;
        for (Out ou : outList) {
            empNo = personMapper.getEmpNoByNameA(ou.getName());
            ou.setEmpNo(empNo);
            personMapper.saveOutBeanToSql(ou);
        }
    }

    public String checkOutRepeat(List<Out> outList) throws Exception {
        StringBuilder sb = new StringBuilder("");
        Out oldOut = null;
        for (Out ou : outList) {
            oldOut = personMapper.getOutDanByNameAndDateStr(ou);
            if (oldOut != null) {
                sb.append(ou.getDateStr() + ":" + ou.getName() + ";");
            }
        }

        if (sb.toString().trim().length() > 0) {
            return sb.toString();
        }

        return null;
    }

    public List<Out> translateTabletoOutBean(MultipartFile file) throws Exception {
        DateFormat dftdatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dftdate = new SimpleDateFormat("yyyy-MM-dd");
        WorkbookSettings ws = new WorkbookSettings();
        String fileName = file.getOriginalFilename();
        ws.setCellValidationDisabled(true);
        jxl.Workbook Workbook = null;//.xlsx
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (fileType.equals("xls")) {
            Workbook = jxl.Workbook.getWorkbook(file.getInputStream(), ws);
        } else {
            throw new Exception("文档格式后缀不正确!!！只接受xls格式.");
        }
        jxl.Sheet xlsfSheet = null;
        if (Workbook != null) {
            jxl.Sheet[] sheets = Workbook.getSheets();
            xlsfSheet = sheets[0];
        }
        List<Out> outList = new ArrayList<Out>();
        Out cio = null;
        int rowNums = xlsfSheet.getRows();
        jxl.Cell[] cell = null;
        String timestr;
        Date transferDate = null;
        DateCell dc = null;
        Date date = null;
        Calendar c = null;
        for (int i = 2; i < rowNums; i++) {
            cell = xlsfSheet.getRow(i);
            if (cell != null && cell.length > 0) {
                cio = new Out();
                try {
                    if (cell[0].getType() == CellType.DATE) {
                        dc = (DateCell) cell[0];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        cio.setDate(transferDate);
                        cio.setDateStr(dftdate.format(transferDate));
                    }

                    cio.setName(cell[1].getContents().trim());
                    cio.setOutaddr(cell[2].getContents().trim());
                    cio.setOutfor(cell[3].getContents().trim());

                    if (cell[4].getType() == CellType.DATE) {
                        dc = (DateCell) cell[4];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        cio.setOuttime(transferDate);
                        cio.setOuttimeStr(dftdatetime.format(transferDate));
                    }

                    if (cell[5].getType() == CellType.DATE) {
                        dc = (DateCell) cell[5];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        cio.setRealcomtime(transferDate);
                        cio.setRealcomtimeStr(dftdatetime.format(transferDate));
                    }
                    cio.setRemark(cell[6].getContents().trim());
                    cio.setInterDays(DateUtil.getDistanceOfTwoDate(cio.getOuttime(), cio.getRealcomtime()));
                    outList.add(cio);
                } catch (Exception e) {
                    throw new Exception("表格第" + (i + 1) + "行日期有错误!");
                }
            }
        }
        return outList;
    }


    @Transactional
    public void translateAllTable(MultipartFile file) throws Exception {
        try {
            Map<String, List<?>> mapList = ExcelUtil.translateAllTable(file);
            int isEmploeeExsit = 0;
            int isExsit = 0;
            if (mapList != null) {
                if (mapList.get("leave") != null) {
                    List<Leave> leaveList = (List<Leave>) mapList.get("leave");
                    for (Leave leave : leaveList) {
                        if ("CS".contains(leave.getEmpNo())) {
                            isEmploeeExsit = personMapper.checkEmployNoIsExsit(leave.getEmpNo());
                        } else {
                            isEmploeeExsit = personMapper.checkEmployLSIsExsit(leave.getEmpNo());
                        }
                        if (isEmploeeExsit == 0) {
                            throw new Exception("请假单表格" + leave.getName() + "的员工不存在!");
                        }
                        isExsit = personMapper.checkBeginLeaveRight2(leave);
                        if (isExsit != 0) {
                            throw new Exception("请假单表格" + leave.getName() + "的请假单系统已存在或时间重复!");
                        }
                        personMapper.addLeaveData(leave);
                    }
                }

                if (mapList.get("qianka") != null) {
                    List<QianKa> qianKaList = (List<QianKa>) mapList.get("qianka");
                    QianKa qianKaOld = null;
                    for (QianKa qianKq : qianKaList) {
                        if ("CS".contains(qianKq.getEmpNo())) {
                            isEmploeeExsit = personMapper.checkEmployNoIsExsit(qianKq.getEmpNo());
                        } else {
                            isEmploeeExsit = personMapper.checkEmployLSIsExsit(qianKq.getEmpNo());
                        }
                        if (isEmploeeExsit == 0) {
                            throw new Exception("签卡单表格" + qianKq.getName() + "的员工不存在!");
                        }
                        qianKaOld = personMapper.getQianKaByDateAndEmpno(qianKq);
                        if (qianKaOld == null) {
                            personMapper.saveQianKa(qianKq);
                        } else {
                            String timeStr = StringUtil.sortTimes(qianKaOld.getTimeStr(), qianKq.getTimeStr());
                            qianKq.setTimeStr(timeStr);
                            qianKq.setId(qianKaOld.getId());
                            personMapper.updateQianKa(qianKq);
                        }
                    }
                }

                if (mapList.get("lianban") != null) {
                    List<LianBan> lianBanList = (List<LianBan>) mapList.get("lianban");
                    for (LianBan lianBan : lianBanList) {
                        if ("CS".contains(lianBan.getEmpNo())) {
                            isEmploeeExsit = personMapper.checkEmployNoIsExsit(lianBan.getEmpNo());
                        } else {
                            isEmploeeExsit = personMapper.checkEmployLSIsExsit(lianBan.getEmpNo());
                        }
                        if (isEmploeeExsit == 0) {
                            throw new Exception("连班单表格" + lianBan.getName() + "的员工不存在!");
                        }

                        isExsit = personMapper.checkIfExsitLianBan(lianBan.getEmpNo(), lianBan.getDateStr());
                        if (isExsit == 0) {
                            personMapper.saveLianBanBeanToSql(lianBan);
                        } else {
                            throw new Exception("连班单表格" + lianBan.getName() + "的员工" + lianBan.getDateStr() + "已存在!");
                        }
                    }
                }

                if (mapList.get("jiaban") != null) {
                    List<JiaBan> jiaBanList = (List<JiaBan>) mapList.get("jiaban");
                    for (JiaBan jiaBan : jiaBanList) {
                        if ("CS".contains(jiaBan.getEmpNo())) {
                            isEmploeeExsit = personMapper.checkEmployNoIsExsit(jiaBan.getEmpNo());
                        } else {
                            isEmploeeExsit = personMapper.checkEmployLSIsExsit(jiaBan.getEmpNo());
                        }
                        if (isEmploeeExsit == 0) {
                            throw new Exception("加班单表格" + jiaBan.getName() + "的员工不存在!");
                        }
                        isExsit = personMapper.checkBeginLeaveRight3(jiaBan);
                        if (isExsit != 0) {
                            throw new Exception("加班单表格" + jiaBan.getName() + "系统已存在或时间重复!");
                        }
                        personMapper.saveJiaBanDateToMysql(jiaBan);
                    }
                }

                if (mapList.get("yeban") != null) {
                    List<YeBan> yeBanList = (List<YeBan>) mapList.get("yeban");
                    for (YeBan yeBan : yeBanList) {
                        if ("CS".contains(yeBan.getEmpNo())) {
                            isEmploeeExsit = personMapper.checkEmployNoIsExsit(yeBan.getEmpNo());
                        } else {
                            isEmploeeExsit = personMapper.checkEmployLSIsExsit(yeBan.getEmpNo());
                        }
                        if (isEmploeeExsit == 0) {
                            throw new Exception("夜班单表格" + yeBan.getName() + "的员工不存在!");
                        }
                        isExsit = personMapper.checkIfExsitYeBan(yeBan.getEmpNo(), yeBan.getDateStr());
                        if (isExsit == 0) {
                            personMapper.saveYeBanDateToMysql(yeBan);
                        } else {
                            throw new Exception("夜班单表格" + yeBan.getName() + "的员工" + yeBan.getDateStr() + "已存在!");
                        }

                    }
                }

                if (mapList.get("tiaoxiu") != null) {
                    List<TiaoXiu> tiaoXiuList = (List<TiaoXiu>) mapList.get("tiaoxiu");
                    for (TiaoXiu tiaoXiu : tiaoXiuList) {
                        tiaoXiu.setUsaged(0);
                        tiaoXiu.setAusaged(0);
                        tiaoXiu.setTotalHours(0D);
                        tiaoXiu.setType(0);
                        tiaoXiu.setFromDateWeek(DateUtil.getWeek(tiaoXiu.getFromDateStr()));
                        tiaoXiu.setToDateWeek(DateUtil.getWeek(tiaoXiu.getToDateStr()));
                        personMapper.saveTiaoXiuDateToMysql(tiaoXiu);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<OutClockIn> findAllOutClockInByOpenId(String openId) throws Exception {
        return personMapper.findAllOutClockInByOpenId(openId);
    }

    public List<Employee> queryOutClockInByCondition(Employee employee) throws Exception {
        return personMapper.queryOutClockInByCondition(employee);
    }

    public int queryOutClockInByConditionCount(Employee employee) throws Exception {
        return personMapper.queryOutClockInByConditionCount(employee);
    }

    public void saveOrUpdateOutClockInData(OutClockIn outClockIn) throws Exception {
        OutClockIn orginal = personMapper.getOutClockInByDateAndWeiXinId(outClockIn);
        if (orginal == null) {
            personMapper.addOutClockInDateByBean(outClockIn);
        } else {
            if (outClockIn.getClockInDateAMOnStr() != null) {
                personMapper.updateClockInDateByAM(outClockIn);
            } else if (outClockIn.getClockInDatePMOnStr() != null) {
                personMapper.updateClockInDateByPM(outClockIn);
            } else if (outClockIn.getClockInDateNMOnStr() != null) {
                personMapper.updateClockInDateByNM(outClockIn);
            }
        }

    }

    public int saveOrUpdateGongZhongHaoIdByEmpNo(GongZhongHao gongZhongHao) throws Exception {
        int isExsit = personMapper.getGongZhongHaoByEmpNo(gongZhongHao.getEmpNo());
        if (gongZhongHao.getGongzhonghaoId().trim().length() == 0) {
            personMapper.deleteGongZhongHaoByEmpNo(gongZhongHao.getEmpNo());
            return 2;
        }
        if (isExsit == 0) {
            personMapper.saveGongZhongHaoByBean(gongZhongHao);
            return 1;
        } else {
            personMapper.updateGongZhongHaoByEmpNo(gongZhongHao);
            return 3;
        }
    }

    public void deleteClockSetInByOutDays(Double outDays) throws Exception {
        personMapper.deleteClockSetInByOutDays(outDays);
    }

    public void deleteLeaveByBatch(List<Integer> ids) throws Exception {
        personMapper.deleteLeaveByBatch(ids);
    }


    public int checkAndSavePosition(Position position) throws Exception {
        int isExist = personMapper.findSaveOrNot(position);
        if (isExist > 0) {
            return isExist;
        } else {
            personMapper.savePosition(position);
        }
        return isExist;
    }

    public int checkAndSaveDept(String deptName) throws Exception {
        int isExist = personMapper.findSaveOrNot2(deptName);
        if (isExist > 0) {
            return isExist;
        } else {
            personMapper.saveDept(deptName);
        }
        return isExist;
    }

    public List<Position> findAllPosition(Position position) throws Exception {
        return personMapper.findAllPosition(position);
    }

    public List<Dept> findAllDept(Dept dept) throws Exception {
        return personMapper.findAllDept(dept);
    }

    public int findAllDeptConditionCount(Dept dep) throws Exception {
        return personMapper.findAllDeptConditionCount();
    }

    public void deletePositionById(Integer id) throws Exception {
        personMapper.deletePositionById(id);
    }

    public void deleteDeptById(Integer id) throws Exception {
        personMapper.deleteDeptById(id);
    }

    public int queryDeptCountByNameA(Dept dept) throws Exception {
        if (dept.getDeptname() == null || dept.getDeptname().trim().length() == 0) {
            return personMapper.findAllDeptConditionCount();
        } else {
            return personMapper.findAllDeptByConditionCount(dept);
        }
    }

    public List<Position> queryPositionByName(String positionName) throws Exception {
        return personMapper.queryPositionByName(positionName);
    }


    public void saveUpdateData(Integer id, String positionName, String positionLevel) throws Exception {
        personMapper.saveUpdateData(id, positionName, positionLevel);
    }

    public void saveUpdateData2(Integer id, String deptName) throws Exception {
        personMapper.saveUpdateData2(id, deptName);
    }

    public int findAllPositionConditionCount() throws Exception {
        return personMapper.findAllPositionConditionCount();
    }

    public int checkIfExsit(String positionName) throws Exception {
        return personMapper.checkIfExsit(positionName);
    }

    public int checkIfExsit2(String deptName) throws Exception {
        return personMapper.checkIfExsit2(deptName);
    }

    public List<Position> queryPositionByNameA(Position position) throws Exception {
        return personMapper.queryPositionByNameA(position);
    }

    public List<Dept> queryDeptByNameA(Dept dept) throws Exception {
        if (dept.getDeptname() == null || dept.getDeptname().trim().length() == 0) {
            return personMapper.findAllDept(dept);
        } else {
            return personMapper.queryDeptByNameA(dept);
        }
    }

    public void saveBeforeDayZhongKongData(List<ZhongKongBean> zkbList) throws Exception {
        for (ZhongKongBean zkb : zkbList) {
            personMapper.saveBeforeDayZhongKongData(zkb);
        }
    }

    public List<Employee> findAllZKAndOutData(Employee employee) throws Exception {
        return personMapper.findAllZKAndOutData(employee);
    }

    public List<Employee> queryZKOUTDataByCondition(Employee employee) throws Exception {
        return personMapper.queryZKOUTDataByCondition(employee);
    }

    public List<KQBean> queryKQBeanDataByCondition(KQBean kqBean) throws Exception {
        return personMapper.queryKQBeanDataByCondition(kqBean);
    }

    public int queryKQBeanDataByConditionCount(KQBean kqBean) throws Exception {
        return personMapper.queryKQBeanDataByConditionCount(kqBean);
    }

    public int savePinShiDateToMysql(PinShiJiaBanBGS pinShiJiaBanBGS) throws Exception {
        Employee ee = personMapper.getEmployeeOneById(pinShiJiaBanBGS.getEmpId());
        pinShiJiaBanBGS.setEmpNo(ee.getEmpNo());
        PinShiJiaBanBGS oldPS = personMapper.getPinShiByEmpNo(pinShiJiaBanBGS.getEmpNo());
        if (oldPS == null) {
            personMapper.savePinShiDateToMysql(pinShiJiaBanBGS);
            return 1;
        } else {
            personMapper.updatePinShiDateToMysql(pinShiJiaBanBGS);
            return 2;
        }
    }


    public void deletePinShiDateToMysql(Integer id) throws Exception {
        personMapper.deletePinShiDateToMysql(id);
    }

    public int queryZKOUTDataByConditionCount(Employee employee) throws Exception {
        return personMapper.queryZKOUTDataByConditionCount(employee);
    }

    public List<WorkSet> getAllWorkSetListByYearMonth(String yearMonth) throws Exception {
        return personMapper.getAllWorkSetListByYearMonth(yearMonth);
    }

    public List<PinShiJiaBanBGS> queryPSByCondition(PinShiJiaBanBGS pinShiJiaBanBGS) throws Exception {
        return personMapper.queryPSByCondition(pinShiJiaBanBGS);
    }

    public int queryPSByConditionCount(PinShiJiaBanBGS pinShiJiaBanBGS) throws Exception {
        return personMapper.queryPSByConditionCount(pinShiJiaBanBGS);
    }

    public List<Employee> findAllEmployeeAllOnlyBanGong() throws Exception {
        return personMapper.findAllEmployeeAllOnlyBanGong();
    }

    public List<WorkDate> getAllWorkDateListByYearMonth(String yearMonth) throws Exception {
        return personMapper.getAllWorkDateListByYearMonth(yearMonth);
    }

    public int findAllZKAndOutDataCount() throws Exception {
        return personMapper.findAllZKAndOutDataCount();
    }

    public List<PinShiJiaBanBGS> findAllPinShi(PinShiJiaBanBGS ps) throws Exception {
        return personMapper.findAllPinShi(ps);
    }

    public int findAllPinShiCount() throws Exception {
        return personMapper.findAllPinShiCount();
    }

    public List<Employee> translateTabletoEmployeeBeanZK(List<MultipartFile> files) throws Exception {
        WorkbookSettings ws;
        jxl.Workbook Workbook = null;
        String fileName;
        String fileType;
        List<jxl.Sheet> xlsfSheetList = new ArrayList<jxl.Sheet>();
        jxl.Sheet[] sheets = null;
        for (MultipartFile file : files) {
            ws = new WorkbookSettings();
            fileName = file.getOriginalFilename();
            ws.setCellValidationDisabled(true);
            fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if (fileType.equals("xls")) {
                Workbook = jxl.Workbook.getWorkbook(file.getInputStream(), ws);
            } else {
                throw new Exception("文档格式后缀不正确!!！只接受xls格式.");
            }
            if (Workbook != null) {
                sheets = Workbook.getSheets();
                xlsfSheetList.add(sheets[0]);
            }
        }
        List<Employee> employeeList = new ArrayList<Employee>();
        Employee em = null;
        jxl.Cell[] cell = null;
        String name = null;
        Cell cella;
        for (jxl.Sheet sheet : xlsfSheetList) {
            int rowNums = sheet.getRows();
            if (rowNums > 0) {
                cell = sheet.getRow(0);
                int coloumNum = cell.length;
                for (int ab = 0; ab < coloumNum; ab++) {
                    cella = cell[ab];
                    if (nameTitle.equals(cella.getContents().trim())) {
                        nameTitleIndex = ab;
                    }
                    if (deptIdTitle.equals(cella.getContents().trim())) {
                        deptIdTitleIndex = ab;
                    }
                    if (EnrollNumberTitle.equals(cella.getContents().trim())) {
                        EnrollNumberTitleIndex = ab;
                    }
                }
            }
            for (int i = 1; i < rowNums; i++) {
                cell = sheet.getRow(i);
                if (cell != null && cell.length > 0) {
                    name = cell[nameTitleIndex].getContents().trim();
                    if (name.length() > 0) {
                        em = new Employee();
                        em.setName(cell[nameTitleIndex].getContents().trim());
                        em.setDeptName(cell[deptIdTitleIndex].getContents().trim());
                        em.setEnrollNumber(cell[EnrollNumberTitleIndex].getContents().trim());
                        employeeList.add(em);
                    }
                }
            }
        }
        return employeeList;
    }


    public List<Employee> queryZhongKongByCondition(Employee employee) throws Exception {
        return personMapper.queryZhongKongByCondition(employee);
    }

    public int queryZhongKongByConditionCount(Employee employee) throws Exception {
        return personMapper.queryZhongKongByConditionCount(employee);
    }


    public int queryPositionCountByNameA(Position position) throws Exception {
        return personMapper.findAllPositionByConditionCount(position);
    }

    public List<Position> findAllPositionAll() throws Exception {
        return personMapper.findAllPositionAll();
    }

    public List<Dept> findAllDeptAll() throws Exception {
        return personMapper.findAllDeptAll();
    }

    public List<Employee> findAllEmployeeZhongKong(Employee employee) throws Exception {
        return personMapper.findAllEmployeeZhongKong(employee);
    }

    public int findAllEmployeeZhongKongCount() throws Exception {
        return personMapper.findAllEmployeeZhongKongCount();
    }


    public List<Employee> findAllZKAndOutDataCondition(Employee employee) throws Exception {
        return personMapper.findAllZKAndOutDataCondition(employee);
    }


    public List<KQBean> findAllKQBDataCondition(Employee employee) throws Exception {
        return personMapper.findAllKQBDataCondition(employee);
    }

    public int findAllKQBDataConditionCount(Employee employee) throws Exception {
        return personMapper.findAllKQBDataConditionCount(employee);
    }

    public List<String> getAllMKMonthList() throws Exception {
        return personMapper.getAllMKMonthList();
    }

    public int findAllZKAndOutDataConditionCount(Employee employee) throws Exception {
        return personMapper.findAllZKAndOutDataConditionCount(employee);

    }

    public List<String> findAllZKYearMonthList() throws Exception {
        return personMapper.findAllZKYearMonthList();
    }

    public int saveOrUpdateZhongKongIdByEmpNo(WeiXinUsrId zhongKongEmployee) throws Exception {
        int isExsit = personMapper.getZhongKongByEmpNo(zhongKongEmployee.getEmpNo());
        if (zhongKongEmployee.getUserid() == null || zhongKongEmployee.getUserid().trim().length() == 0) {
            personMapper.deleteZhongKongByEmpNo(zhongKongEmployee.getEmpNo());
            return 2;
        }
        if (isExsit == 0) {
            int isNumExsit = personMapper.getBeanByEnrollNumber(zhongKongEmployee.getUserid());
            if (isNumExsit == 0) {
                personMapper.saveZhongKongByBean(zhongKongEmployee);
                return 1;
            } else {
                return 9;
            }
        } else {
            int isNumExsit = personMapper.getBeanByEnrollNumber(zhongKongEmployee.getUserid());
            if (isNumExsit == 0) {
                personMapper.updateZhongKongByEmpNo(zhongKongEmployee);
                return 3;
            } else {
                return 9;
            }
        }
    }

    public void addEmployeeData(MultipartFile educationLeFile, MultipartFile sateListAndLeaCertiFile, MultipartFile otherCertiFile, Employee employee) throws Exception {
        String fileName;
        String folderName = finalDirPath + "append/" + employee.getEmpNo() + "/";
        UserInfo userInfo;
        if (employee.getEducationLe() > 7) {
            FileUtil.uploadFileForEmployeeAppend(educationLeFile, folderName);
            fileName = educationLeFile.getOriginalFilename();
            employee.setEducationLeUrl(folderName + fileName);
        } else {
            employee.setEducationLeUrl("");
        }
        if (employee.getSateListAndLeaCerti() > 0) {
            FileUtil.uploadFileForEmployeeAppend(sateListAndLeaCertiFile, folderName);
            fileName = sateListAndLeaCertiFile.getOriginalFilename();
            employee.setSateListAndLeaCertiUrl(folderName + fileName);
        } else {
            employee.setSateListAndLeaCertiUrl("");
        }
        if (employee.getOtherCerti() > 0) {
            FileUtil.uploadFileForEmployeeAppend(otherCertiFile, folderName);
            fileName = otherCertiFile.getOriginalFilename();
            employee.setOtherCertiUrl(folderName + fileName);
        } else {
            employee.setOtherCertiUrl("");

        }

        if (employee.getUsername() != null && employee.getPassowrd() != null && employee.getUsername().trim().length() > 0 && employee.getPassowrd().trim().length() > 0) {
            userInfo = new UserInfo();
            userInfo.setEmpNo(employee.getEmpNo());
            userInfo.setFullName(employee.getName());
            userInfo.setUserName(employee.getUsername());
            userInfo.setUserPwd(employee.getPassowrd());
            userInfo.setState(0);
            userInfo.setUseruploadright(1);
            userInfo.setUserActor(employee.getPositionAttrId());
            userInfoMapper.saveUserInfoByBean(userInfo);
        }
        employee.setIsQuit(0);
        personMapper.addEmployeeData(employee);
        personMapper.addSalaryData(employee.getEmpNo(), employee.getCompreSalary(), employee.getPosSalary(), employee.getJobSalary(), employee.getMeritSalary());
    }

    public List<Employee> findAllEmployee(Employee employee) throws Exception {
        return personMapper.findAllEmployee(employee);
    }

    public int findAllEmployeeCount() throws Exception {
        return personMapper.findAllEmployeeCount();
    }

    public int checkEmployIsExsit(String name) throws Exception {
        return personMapper.checkEmployIsExsit(name);
    }

    public int checkEmployNoIsExsit(String empoyeeNo) throws Exception {
        return personMapper.checkEmployNoIsExsit(empoyeeNo);
    }

    public List<Employee> queryEmployeeByCondition(Employee employee) throws Exception {
        return personMapper.queryEmployeeByCondition(employee);
    }

    public int queryEmployeeByConditionCount(Employee employee) throws Exception {
        return personMapper.queryEmployeeByConditionCount(employee);

    }

    public void deleteEmployeetById(Integer id) throws Exception {
        List<Employee> emlist = personMapper.getEmployeeById(id);
        String folderName = finalDirPath + "append/" + emlist.get(0).getEmpNo() + "/";
        personMapper.deleteEmployeetById(id);
        personMapper.deleteSalaryByEmpno(emlist.get(0).getEmpNo());
        personMapper.deleteUserInfoByEmpNo(emlist.get(0).getEmpNo());
        FileUtil.delFolderNew(folderName);
    }

    public List<Employee> getEmployeeById(Integer id) throws Exception {
        return personMapper.getEmployeeById(id);
    }

    public List<Employee> getEmployeeById(String id) throws Exception {
        return personMapper.getEmployeeByIdA(id);
    }

    public void addLeaveData(Leave leave) throws Exception {
        personMapper.addLeaveData(leave);
    }

    public void deleteLeaveById(Integer id) throws Exception {
        personMapper.deleteLeaveById(id);
    }

    public Leave getLeaveById(Integer id) throws Exception {
        return personMapper.getLeaveById(id);
    }

    public void addWorkSetData(WorkSet workSet) throws Exception {
        personMapper.addWorkSetData(workSet);
    }

    public WorkSet getWorkSetByMonthAndPositionLevel(WorkDate workDate) throws Exception {
        return personMapper.getWorkSetByMonthAndPositionLevel(workDate);
    }

    public List<WorkSet> findAllWorkSet(WorkSet workSet) throws Exception {
        return personMapper.findAllWorkSet(workSet);
    }

    public int findAllWorkSetCount(WorkSet workSet) throws Exception {
        return personMapper.findAllWorkSetCount(workSet);
    }

    public void deleteWorkSetById(WorkSet workSet) throws Exception {
        personMapper.deleteWorkSetById(workSet);
    }


    public List<WorkSet> queryWorkSetByCondition(WorkSet workSet) throws Exception {
        return personMapper.queryWorkSetByCondition(workSet);
    }

    public int queryWorkSetByConditionCount(WorkSet workSet) throws Exception {
        return personMapper.queryWorkSetByConditionCount(workSet);

    }

    public void updateWorkSetDataById(WorkSet workSet) throws Exception {
        personMapper.updateWorkSetDataById(workSet);
    }


    public WorkSet getWorkSetById(Integer id) throws Exception {
        return personMapper.getWorkSetById(id);
    }


    public String getPositionNamesByPositionLevel(String positionLevel) throws Exception {
        return personMapper.getPositionNamesByPositionLevel(positionLevel);
    }


    public void saveOrUpdateWorkData(WorkDate workDate) throws Exception {
        WorkDate num = personMapper.getWorkDateByMonth(workDate);
        if (num == null) {
            if (workDate.getEmpNos() != null && workDate.getEmpNos().size() > 0)
                workDate.setEmpNostr(workDate.getEmpNos().toString());
            personMapper.saveWorkData(workDate);
        } else {
            if (workDate.getEmpNos() != null && workDate.getEmpNos().size() > 0)
                workDate.setEmpNostr(workDate.getEmpNos().toString());
            personMapper.updateWorkData(workDate);
        }

    }

    public void fillEmpNoWhenQYWXNull() throws Exception {
        List<WeiXinUsrId> allWXUserId = personMapper.getAllWeiXinUserId();
        String empNo;
        for (WeiXinUsrId wxu : allWXUserId) {
            if (wxu.getEmpNo() == null || wxu.getEmpNo().trim().length() == 0) {
                empNo = personMapper.getEmpNoByNameA(wxu.getName());
                if (empNo != null) {
                    personMapper.saveEmpNoByUserId(wxu.getUserid(), empNo);
                }
            }
        }
    }


    public void updateEmployeeData(MultipartFile educationLeFile, MultipartFile sateListAndLeaCertiFile,
                                   MultipartFile otherCertiFile, Employee employee) throws Exception {
        String fileName;
        String folderName = finalDirPath + "append/" + employee.getEmpNo() + "/";
        UserInfo userInfo;
        Employee eee = personMapper.getEmployeeByEmpNoo(employee.getEmpNo());
        if (employee.getEducationLe() <= 7) {
            FileUtil.delFile(eee.getEducationLeUrl());
            employee.setEducationLeUrl("");
        } else {
            fileName = educationLeFile.getOriginalFilename();
            if (fileName.trim().length() > 0) {
                FileUtil.delFile(eee.getEducationLeUrl());
                FileUtil.uploadFileForEmployeeAppend(educationLeFile, folderName);
                employee.setEducationLeUrl(folderName + fileName);
            } else {
                employee.setEducationLeUrl(eee.getEducationLeUrl());
            }

        }
        if (employee.getSateListAndLeaCerti() == 0) {
            FileUtil.delFile(eee.getSateListAndLeaCertiUrl());
            employee.setSateListAndLeaCertiUrl("");
        } else {
            fileName = sateListAndLeaCertiFile.getOriginalFilename();
            if (fileName.trim().length() > 0) {
                FileUtil.delFile(eee.getSateListAndLeaCertiUrl());
                FileUtil.uploadFileForEmployeeAppend(sateListAndLeaCertiFile, folderName);
                employee.setSateListAndLeaCertiUrl(folderName + fileName);
            } else {
                employee.setSateListAndLeaCertiUrl(eee.getSateListAndLeaCertiUrl());
            }
        }
        if (employee.getOtherCerti() == 0) {
            FileUtil.delFile(eee.getOtherCertiUrl());
            employee.setOtherCertiUrl("");
        } else {
            fileName = otherCertiFile.getOriginalFilename();
            if (fileName.trim().length() > 0) {
                FileUtil.delFile(eee.getOtherCertiUrl());
                FileUtil.uploadFileForEmployeeAppend(otherCertiFile, folderName);
                employee.setOtherCertiUrl(folderName + fileName);
            } else {
                employee.setOtherCertiUrl(eee.getOtherCertiUrl());
            }
        }

        if (employee.getUsername() != null) {
            userInfo = new UserInfo();
            userInfo.setEmpNo(employee.getEmpNo());
            userInfo.setUserName(employee.getUsername());
            userInfo.setUserPwd(employee.getPassowrd());
            userInfoMapper.updateUserInfoByBean(userInfo);
        }
        personMapper.updateEmployeeData(employee);
        personMapper.updateSalaryData(employee.getEmpNo(), employee.getCompreSalary(), employee.getPosSalary(), employee.getJobSalary(), employee.getMeritSalary());
    }

    public void updateLeaveToMysql(Leave leave) throws Exception {
        personMapper.updateLeaveToMysql(leave);
    }

    public List<Leave> queryLeaveByCondition(Leave leave) throws Exception {
        return personMapper.queryLeaveByCondition(leave);
    }

    public List<LianBan> findAllLianBan(LianBan lianBan) throws Exception {
        return personMapper.findAllLianBan(lianBan);
    }

    public int saveLianBanDateToMysql(LianBan lianBan) throws Exception {
        Employee ee = personMapper.getEmployeeOneById(lianBan.getEmpId());
        lianBan.setEmpNo(ee.getEmpNo());
        LianBan lb = personMapper.getLianBanByEmpNoAndDateStr(ee.getEmpNo(), lianBan.getDateStr());
        if (lb == null) {
            personMapper.saveLianBanBeanToSql(lianBan);
            return 1;
        } else {
            personMapper.updateLianBanBean(lianBan);
            return 2;
        }
    }


    public List<LianBan> queryLBByCondition(LianBan lianBan) throws Exception {
        return personMapper.queryLBByCondition(lianBan);
    }


    public List<JiaBan> findAllJiaBan(JiaBan jiaBan) throws Exception {
        return personMapper.findAllJiaBan(jiaBan);
    }

    public int findAllJiaBanCount() throws Exception {
        return personMapper.findAllJiaBanCount();
    }


    public int queryLBByConditionCount(LianBan lianBan) throws Exception {
        return personMapper.queryLBByConditionCount(lianBan);
    }

    public void deleteLianBanDateToMysql(Integer id) throws Exception {
        personMapper.deleteLianBanDateToMysql(id);
    }

    public int findAllLianBanCount() throws Exception {
        return personMapper.findAllLianBanCount();
    }

    public int checkBeginLeaveRight(Leave leave) throws Exception {
        return personMapper.checkBeginLeaveRight(leave);
    }


    public List<JiaBan> queryJBByCondition(JiaBan jiaBan) throws Exception {
        return personMapper.queryJBByCondition(jiaBan);
    }

    public int queryJBByConditionCount(JiaBan jiaBan) throws Exception {
        return personMapper.queryJBByConditionCount(jiaBan);
    }

    public void deleteJiaBanDateToMysql(Integer id) throws Exception {
        personMapper.deleteJiaBanDateToMysql(id);
    }

    public int saveJiaBanDateToMysql(JiaBan jiaBan) throws Exception {
        int oldJiaBanCount = personMapper.getJiaBanDanByEmpIdAndFromDateAndEndDate(jiaBan.getEmpId(), jiaBan.getExtDateFromStr(), jiaBan.getExtDateEndStr());
        Employee employee = personMapper.getEmployeeOneById(jiaBan.getEmpId());
        if (oldJiaBanCount == 0) {
            jiaBan.setEmpNo(employee.getEmpNo());
            personMapper.saveJiaBanDateToMysql(jiaBan);
            return 1;
        } else {
            return 2;
        }
    }


    public List<YeBan> findAllYeBan(YeBan yeBan) throws Exception {
        return personMapper.findAllYeBan(yeBan);
    }

    public int saveYeBanDateToMysql(YeBan yeBan) throws Exception {
        Employee ee = personMapper.getEmployeeOneById(yeBan.getEmpId());
        yeBan.setEmpNo(ee.getEmpNo());
        int num = personMapper.getYeBanByEmpNoAndDateStr(ee.getEmpNo(), yeBan.getDateStr());
        if (num == 0) {
            personMapper.saveYeBanDateToMysql(yeBan);
            return 1;
        } else {
            personMapper.updateYeBanDateToMysql(yeBan);
            return 2;
        }
    }

    public void deleteYeBanDateToMysql(Integer id) throws Exception {
        personMapper.deleteYeBanDateToMysql(id);
    }

    public int findAllYeBanCount() throws Exception {
        return personMapper.findAllYeBanCount();
    }

    public int queryLeaveByConditionCount(Leave leave) throws Exception {
        return personMapper.queryLeaveByConditionCount(leave);
    }

    public List<Leave> findAllLeave(Leave leave) throws Exception {
        return personMapper.findAllLeave(leave);
    }

    public int findAllLeaveCount() throws Exception {
        return personMapper.findAllLeaveCount();
    }

    public List<Employee> findAllEmployees() throws Exception {
        return personMapper.findAllEmployees();
    }


    public WorkDate getWorkDateByMonth(WorkDate workDate) throws Exception {
        return personMapper.getWorkDateByMonth(workDate);
    }

    public WorkDate getWorkDateByMonth2(WorkDate workDate) throws Exception {
        return personMapper.getWorkDateByMonth2(workDate);
    }


    public List<ClockInOrgin> translateTabletoBean(MultipartFile file) throws Exception {
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
        if (Workbook != null) {
            jxl.Sheet[] sheets = Workbook.getSheets();
            xlsfSheet = sheets[0];
        }
        List<ClockInOrgin> clockInOrgins = new ArrayList<ClockInOrgin>();
        ClockInOrgin cio = null;
        int rowNums = xlsfSheet.getRows();
        jxl.Cell[] cell = null;
        String timestr;
        for (int i = 1; i < rowNums; i++) {
            cell = xlsfSheet.getRow(i);
            if (cell != null && cell.length > 0) {
                cio = new ClockInOrgin();
                cio.setAttendMachineId(Integer.valueOf(cell[0].getContents().trim()));
                cio.setEmployeeName(cell[1].getContents().trim());
                cio.setDeptName(cell[2].getContents().trim());
                cio.setDateStr(cell[3].getContents().trim());
                timestr = cell[4].getContents().trim();
                cio.setTimeStr(timestr);
                if (timestr != null && timestr.length() > 0) {
                    cio.setTimes(timestr.split(" "));
                }
                clockInOrgins.add(cio);
            }
        }
        return clockInOrgins;
    }

    public List<Employee> translateTabletoEmployeeBean(MultipartFile file) throws Exception {
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
            xlsfSheet3 = sheets[3];
        }
        List<Employee> employeeList = new ArrayList<Employee>();
        Employee em = null;
        int rowNums = xlsfSheet.getRows();
        jxl.Cell[] cell = null;
        String name = null;
        Cell cella;
        if (rowNums > 0) {
            cell = xlsfSheet.getRow(1);
            int coloumNum = cell.length;
            for (int ab = 0; ab < coloumNum; ab++) {
                cella = cell[ab];
                if (nameTitle.equals(cella.getContents().trim())) {
                    nameTitleIndex = ab;
                }
                if (sexTitle.equals(cella.getContents().trim())) {
                    sexTitleIndex = ab;
                }
                if (deptIdTitle.equals(cella.getContents().trim())) {
                    deptIdTitleIndex = ab;
                }
                if (empNoTitle.equals(cella.getContents().trim())) {
                    empNoTitleIndex = ab;
                }

                if (positionIdTitle.equals(cella.getContents().trim())) {
                    positionIdTitleIndex = ab;
                }
                if (incompdateTitle.equals(cella.getContents().trim())) {
                    incompdateTitleIndex = ab;
                }

                if (conExpDateTitle.equals(cella.getContents().trim())) {
                    conExpDateTitleIndex = ab;
                }

                if (birthDayTitle.equals(cella.getContents().trim())) {
                    birthDayTitleIndex = ab;
                }

                if (ID_NOTitle.equals(cella.getContents().trim())) {
                    ID_NOTitleIndex = ab;
                }

                if (nativePlaTitle.equals(cella.getContents().trim())) {
                    nativePlaTitleIndex = ab;
                }

                if (homeAddrTtile.equals(cella.getContents().trim())) {
                    homeAddrTtileIndex = ab;
                }
                if (valiPeriodOfIDTitle.equals(cella.getContents().trim())) {
                    valiPeriodOfIDTitleIndex = ab;
                }
                if (nationTitle.equals(cella.getContents().trim())) {
                    nationTitleIndex = ab;
                }
                if (marriagedTitle.equals(cella.getContents().trim())) {
                    marriagedTitleIndex = ab;
                }

                if (contactPhoneTitle.equals(cella.getContents().trim())) {
                    contactPhoneTitleIndex = ab;
                }

                if (educationLeTitle.equals(cella.getContents().trim())) {
                    educationLeTitleIndex = ab;
                }
                if (screAgreementTitle.equals(cella.getContents().trim())) {
                    screAgreementTitleIndex = ab;
                }
                if (healthCertiTitle.equals(cella.getContents().trim())) {
                    healthCertiTitleIndex = ab;
                }

                if (sateListAndLeaCertiTitle.equals(cella.getContents().trim())) {
                    sateListAndLeaCertiTitleIndex = ab;
                }
                if (otherCertiTitle.equals(cella.getContents().trim())) {
                    otherCertiTitleIndex = ab;
                }
                if (positionAttrIdTitle.equals(cella.getContents().trim())) {
                    positionAttrIdTitleIndex = ab;
                }
            }
        }
        for (int i = 2; i < rowNums; i++) {
            cell = xlsfSheet.getRow(i);
            if (cell != null && cell.length > 0) {
                name = cell[nameTitleIndex].getContents().trim();
                if (name.length() > 0) {
                    em = new Employee();
                    em.setEmpNo(cell[empNoTitleIndex].getContents().trim());
                    em.setName(cell[nameTitleIndex].getContents().trim());
                    em.setDeptName(cell[deptIdTitleIndex].getContents().trim());
                    em.setPositionName(cell[positionIdTitleIndex].getContents().trim());
                    em.setSexStr(cell[sexTitleIndex].getContents().trim());
                    em.setBirthDayStr(cell[birthDayTitleIndex].getContents().trim());
                    em.setID_NO(cell[ID_NOTitleIndex].getContents().trim());
                    em.setNativePlaStr(cell[nativePlaTitleIndex].getContents().trim());
                    em.setHomeAddr(cell[homeAddrTtileIndex].getContents().trim());
                    System.out.println("=====================" + cell[valiPeriodOfIDTitleIndex].getContents().length());
                    if ((cell[valiPeriodOfIDTitleIndex] != null || cell[valiPeriodOfIDTitleIndex].getContents() != null) && cell[valiPeriodOfIDTitleIndex].getContents().length() > 1) {
                        em.setValiPeriodOfIDStr(cell[valiPeriodOfIDTitleIndex].getContents().trim());
                    } else {
                        em.setValiPeriodOfIDStr("9999-12-31");
                    }
                    em.setNationStr(cell[nationTitleIndex].getContents().trim());
                    em.setMarriagedStr(cell[marriagedTitleIndex].getContents().trim());
                    em.setPositionAttrIdStr(cell[positionAttrIdTitleIndex].getContents().trim());
                    em.setContactPhone(cell[contactPhoneTitleIndex].getContents().trim());
                    em.setEducationLeStr(cell[educationLeTitleIndex].getContents().trim());
                    em.setScreAgreementStr(cell[screAgreementTitleIndex].getContents().trim());
                    em.setHealthCertiStr(cell[healthCertiTitleIndex].getContents().trim());
                    if (cell[sateListAndLeaCertiTitleIndex] != null || cell[sateListAndLeaCertiTitleIndex].getContents() != null)
                        em.setSateListAndLeaCertiStr(cell[sateListAndLeaCertiTitleIndex].getContents().trim());
                    em.setOtherCertiStr(cell[otherCertiTitleIndex].getContents().trim());
                    em.setIncompdateStr(cell[incompdateTitleIndex].getContents().trim());

                    if (!"长期".equals(cell[conExpDateTitleIndex].getContents().trim())) {
                        em.setConExpDateStr(cell[conExpDateTitleIndex].getContents().trim());
                    } else {
                        em.setConExpDateStr("9999-12-31");
                    }
                    if (attributeTitleIndex != null) {
                        em.setPositionLevel(cell[attributeTitleIndex].getContents().trim());
                    }
                    em.setWorkType(1);
                    em.setPositionLevel("A");
                    em.setIsQuit(0);
                    employeeList.add(em);
                }
            }
        }

        int rowNums2 = xlsfSheet2.getRows();
        jxl.Cell[] cell2 = null;
        String name2 = null;
        Cell cella2;
        if (rowNums2 > 0) {
            cell2 = xlsfSheet2.getRow(1);
            int coloumNum2 = cell2.length;
            for (int ab = 0; ab < coloumNum2; ab++) {
                cella2 = cell2[ab];
                if (nameTitle.equals(cella2.getContents().trim())) {
                    nameTitleIndex = ab;
                }
                if (sexTitle.equals(cella2.getContents().trim())) {
                    sexTitleIndex = ab;
                }
                if (deptIdTitle.equals(cella2.getContents().trim())) {
                    deptIdTitleIndex = ab;
                }
                if (empNoTitle.equals(cella2.getContents().trim())) {
                    empNoTitleIndex = ab;
                }

                if (positionIdTitle.equals(cella2.getContents().trim())) {
                    positionIdTitleIndex = ab;
                }
                if (incompdateTitle.equals(cella2.getContents().trim())) {
                    incompdateTitleIndex = ab;
                }

                if (conExpDateTitle.equals(cella2.getContents().trim())) {
                    conExpDateTitleIndex = ab;
                }

                if (birthDayTitle.equals(cella2.getContents().trim())) {
                    birthDayTitleIndex = ab;
                }

                if (ID_NOTitle.equals(cella2.getContents().trim())) {
                    ID_NOTitleIndex = ab;
                }

                if (nativePlaTitle.equals(cella2.getContents().trim())) {
                    nativePlaTitleIndex = ab;
                }

                if (homeAddrTtile.equals(cella2.getContents().trim())) {
                    homeAddrTtileIndex = ab;
                }
                if (valiPeriodOfIDTitle.equals(cella2.getContents().trim())) {
                    valiPeriodOfIDTitleIndex = ab;
                }
                if (nationTitle.equals(cella2.getContents().trim())) {
                    nationTitleIndex = ab;
                }
                if (marriagedTitle.equals(cella2.getContents().trim())) {
                    marriagedTitleIndex = ab;
                }

                if (contactPhoneTitle.equals(cella2.getContents().trim())) {
                    contactPhoneTitleIndex = ab;
                }

                if (educationLeTitle.equals(cella2.getContents().trim())) {
                    educationLeTitleIndex = ab;
                }
                if (screAgreementTitle.equals(cella2.getContents().trim())) {
                    screAgreementTitleIndex = ab;
                }
                if (healthCertiTitle.equals(cella2.getContents().trim())) {
                    healthCertiTitleIndex = ab;
                }

                if (sateListAndLeaCertiTitle.equals(cella2.getContents().trim())) {
                    sateListAndLeaCertiTitleIndex = ab;
                }
                if (otherCertiTitle.equals(cella2.getContents().trim())) {
                    otherCertiTitleIndex = ab;
                }
                if (positionAttrIdTitle.equals(cella2.getContents().trim())) {
                    positionAttrIdTitleIndex = ab;
                }
            }
        }
        for (int i = 2; i < rowNums2; i++) {
            cell2 = xlsfSheet2.getRow(i);
            if (cell2 != null && cell2.length > 0) {
                name2 = cell2[1].getContents().trim();
                if (name2.length() > 0) {
                    em = new Employee();
                    em.setEmpNo(cell2[empNoTitleIndex].getContents().trim());
                    em.setName(cell2[nameTitleIndex].getContents().trim());
                    em.setDeptName(cell2[deptIdTitleIndex].getContents().trim());
                    em.setPositionName(cell2[positionIdTitleIndex].getContents().trim());
                    em.setSexStr(cell2[sexTitleIndex].getContents().trim());
                    em.setBirthDayStr(cell2[birthDayTitleIndex].getContents().trim());
                    em.setID_NO(cell2[ID_NOTitleIndex].getContents().trim());
                    em.setNativePlaStr(cell2[nativePlaTitleIndex].getContents().trim());
                    em.setHomeAddr(cell2[homeAddrTtileIndex].getContents().trim());
                    if ((cell2[valiPeriodOfIDTitleIndex] != null || cell2[valiPeriodOfIDTitleIndex].getContents() != null) && cell2[valiPeriodOfIDTitleIndex].getContents().length() > 1) {
                        em.setValiPeriodOfIDStr(cell2[valiPeriodOfIDTitleIndex].getContents().trim());
                    } else {
                        em.setValiPeriodOfIDStr("9999-12-31");
                    }
                    em.setNationStr(cell2[nationTitleIndex].getContents().trim());
                    em.setMarriagedStr(cell2[marriagedTitleIndex].getContents().trim());
                    em.setPositionAttrIdStr(cell2[positionAttrIdTitleIndex].getContents().trim());
                    em.setContactPhone(cell2[contactPhoneTitleIndex].getContents().trim());
                    em.setEducationLeStr(cell2[educationLeTitleIndex].getContents().trim());
                    em.setScreAgreementStr(cell2[screAgreementTitleIndex].getContents().trim());
                    em.setHealthCertiStr(cell2[healthCertiTitleIndex].getContents().trim());
                    if (cell2[sateListAndLeaCertiTitleIndex] != null || cell2[sateListAndLeaCertiTitleIndex].getContents() != null)
                        em.setSateListAndLeaCertiStr(cell2[sateListAndLeaCertiTitleIndex].getContents().trim());
                    em.setOtherCertiStr(cell2[otherCertiTitleIndex].getContents().trim());
                    em.setIncompdateStr(cell2[incompdateTitleIndex].getContents().trim());

                    if (!"长期".equals(cell2[conExpDateTitleIndex].getContents().trim())) {
                        em.setConExpDateStr(cell2[conExpDateTitleIndex].getContents().trim());
                    } else {
                        em.setConExpDateStr("9999-12-31");
                    }
                    if (attributeTitleIndex != null) {
                        em.setPositionLevel(cell2[attributeTitleIndex].getContents().trim());
                    }
                    em.setWorkType(0);
                    em.setPositionLevel("B");
                    em.setIsQuit(0);
                    employeeList.add(em);
                }
            }
        }


        int rowNums3 = xlsfSheet3.getRows();
        jxl.Cell[] cell3 = null;
        String name3 = null;
        Cell cella3;
        if (rowNums3 > 0) {
            cell3 = xlsfSheet3.getRow(1);
            int coloumNum3 = cell3.length;
            for (int ab = 0; ab < coloumNum3; ab++) {
                cella3 = cell3[ab];
                if (nameTitle.equals(cella3.getContents().trim())) {
                    nameTitleIndex = ab;
                }
                if (sexTitle.equals(cella3.getContents().trim())) {
                    sexTitleIndex = ab;
                }
                if (deptIdTitle.equals(cella3.getContents().trim())) {
                    deptIdTitleIndex = ab;
                }

                if (positionIdTitle.equals(cella3.getContents().trim())) {
                    positionIdTitleIndex = ab;
                }
                if (incompdateTitle.equals(cella3.getContents().trim())) {
                    incompdateTitleIndex = ab;
                }

                if (birthDayTitle.equals(cella3.getContents().trim())) {
                    birthDayTitleIndex = ab;
                }

                if (ID_NOTitle.equals(cella3.getContents().trim())) {
                    ID_NOTitleIndex = ab;
                }

                if (nativePlaTitle.equals(cella3.getContents().trim())) {
                    nativePlaTitleIndex = ab;
                }

                if (homeAddrTtile.equals(cella3.getContents().trim())) {
                    homeAddrTtileIndex = ab;
                }
                if (nationTitle.equals(cella3.getContents().trim())) {
                    nationTitleIndex = ab;
                }
                if (remarkTitle.equals(cella3.getContents().trim())) {
                    remarkTitleIndex = ab;
                }

            }
        }
        int i = 0;
        for (i = 2; i < rowNums3; i++) {
            cell3 = xlsfSheet3.getRow(i);
            if (cell3 != null && cell3.length > 0) {
                name3 = cell3[1].getContents().trim();
                if (name3.length() > 0 && cell3[nameTitleIndex].getType() != CellType.EMPTY) {
                    em = new Employee();
                    em.setName(cell3[nameTitleIndex].getContents().trim());
                    em.setDeptName(cell3[deptIdTitleIndex].getContents().trim());
                    em.setPositionName(cell3[positionIdTitleIndex].getContents().trim());
                    em.setSexStr(cell3[sexTitleIndex].getContents().trim());
                    em.setBirthDayStr(cell3[birthDayTitleIndex].getContents().trim());
                    em.setID_NO(cell3[ID_NOTitleIndex].getContents().trim());
                    em.setNativePlaStr(cell3[nativePlaTitleIndex].getContents().trim());
                    em.setHomeAddr(cell3[homeAddrTtileIndex].getContents().trim());
                    em.setNationStr(cell3[nationTitleIndex].getContents().trim());
                    em.setIncompdateStr(cell3[incompdateTitleIndex].getContents().trim());
                    //  em.setRemark(cell3[remarkTitleIndex].getContents().trim());
                    em.setWorkType(2);
                    em.setPositionLevel("B");
                    em.setIsQuit(0);
                    employeeList.add(em);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        i = i + 12;
        for (int a = i; a < rowNums3; a++) {
            cell3 = xlsfSheet3.getRow(a);
            if (cell3 != null && cell3.length > 0) {
                name3 = cell3[1].getContents().trim();
                if (name3.length() > 0 && cell3[nameTitleIndex].getType() != CellType.EMPTY) {
                    em = new Employee();
                    em.setName(cell3[nameTitleIndex].getContents().trim());
                    em.setDeptName(cell3[deptIdTitleIndex].getContents().trim());
                    em.setPositionName(cell3[positionIdTitleIndex].getContents().trim());
                    em.setSexStr(cell3[sexTitleIndex].getContents().trim());
                    em.setBirthDayStr(cell3[birthDayTitleIndex].getContents().trim());
                    em.setID_NO(cell3[ID_NOTitleIndex].getContents().trim());
                    em.setNativePlaStr(cell3[nativePlaTitleIndex].getContents().trim());
                    em.setHomeAddr(cell3[homeAddrTtileIndex].getContents().trim());
                    em.setNationStr(cell3[nationTitleIndex].getContents().trim());
                    em.setIncompdateStr(cell3[incompdateTitleIndex].getContents().trim());
                    //em.setRemark(cell3[remarkTitleIndex].getContents().trim());
                    em.setWorkType(3);
                    em.setPositionLevel("B");
                    em.setIsQuit(0);
                    employeeList.add(em);
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        return employeeList;
    }

    public String checkEmpNoOrEmpNameRepeat(List<Employee> employeeList) throws Exception {
        int size = employeeList.size();
        List<Employee> newEm = new ArrayList<Employee>();
        String repeatData = "";
        Employee e1 = null;
        Employee e2 = null;
        int repeatnum;
        int repeatnum2;
        for (int i = 0; i < size; i++) {
            repeatnum = 0;
            repeatnum2 = 0;
            e1 = employeeList.get(i);
            if (e1.getEmpNo() != null) {
                for (int j = 0; j < size; j++) {
                    e2 = employeeList.get(j);
                    if (e2.getEmpNo() != null) {
                        if (e1.getName().equals(e2.getName())) {
                            repeatnum += 1;
                            if (repeatnum == 2) {
                                repeatData += "重复的姓名比如:" + e1.getName();
                                return repeatData;
                            }
                        }
                        if (e1.getEmpNo().equals(e2.getEmpNo())) {
                            repeatnum2 += 1;
                            if (repeatnum2 == 2) {
                                repeatData += "重复的员工编号比如:" + e1.getEmpNo();
                                return repeatData;
                            }
                        }
                    }
                }
            }
        }

        return repeatData;
    }


    public void saveDeptNameAndPositionNameAndEms(List<Employee> employeeList) throws Exception {
        try {
            personMapper.updateAllEmployeeNotExist();
            personMapper.updateAllEmployeeNotLSExist();
            Employee em = null;
            List<String> depts = personMapper.findAllDeptA();
            List<String> positions = personMapper.findAllPositionAllA();
            int size = employeeList.size();
            Employee mysqlEm = null;
            for (int i = 0; i < size; i++) {
                em = employeeList.get(i);
                if (!depts.contains(em.getDeptName())) {
                    personMapper.addDeptByDeptName(em.getDeptName());
                    depts.add(em.getDeptName());
                }
                if (!positions.contains(em.getPositionName())) {
                    if (em.getPositionLevel() != null) {
                        personMapper.addPositionByNameandPositionLevel(em.getPositionName(), em.getPositionLevel());
                    } else {
                        personMapper.addPositionByName(em.getPositionName());
                    }
                    positions.add(em.getPositionName());
                }
            }

            for (Employee emm : employeeList) {
                if (emm.getEmpNo() != null && emm.getEmpNo().trim().length() > 0) {
                    mysqlEm = personMapper.getEmployeeByEmpNoo(emm.getEmpNo());
                    if (mysqlEm != null) {
                        personMapper.updateEmployeeIsQuitExsit(mysqlEm.getEmpNo());
                    } else {
                        Dept dept = personMapper.getDeptByName(emm.getDeptName());
                        Position position = personMapper.getPositionByName(emm.getPositionName());
                        emm.setDeptId(dept.getId());
                        emm.setPositionId(position.getId());
                        emm.setSex("男".equals(emm.getSexStr()) ? 1 : 0);

                        if ("已".equals(emm.getMarriagedStr())) {
                            emm.setMarriaged(1);
                        } else if ("未".equals(emm.getMarriagedStr()) || "否".equals(emm.getMarriagedStr())) {
                            emm.setMarriaged(0);
                        } else if ("离".equals(emm.getMarriagedStr())) {
                            emm.setMarriaged(2);
                        }

                        if ("壮".equals(emm.getNationStr())) {
                            emm.setNation(1);
                        } else if ("藏".equals(emm.getNationStr())) {
                            emm.setNation(2);
                        } else if ("裕固".equals(emm.getNationStr())) {
                            emm.setNation(3);
                        } else if ("彝".equals(emm.getNationStr())) {
                            emm.setNation(4);
                        } else if ("瑶".equals(emm.getNationStr())) {
                            emm.setNation(5);
                        } else if ("锡伯".equals(emm.getNationStr())) {
                            emm.setNation(6);
                        } else if ("乌孜别克".equals(emm.getNationStr())) {
                            emm.setNation(7);
                        } else if ("维吾尔".equals(emm.getNationStr())) {
                            emm.setNation(8);
                        } else if ("佤".equals(emm.getNationStr())) {
                            emm.setNation(9);
                        } else if ("土家".equals(emm.getNationStr())) {
                            emm.setNation(10);
                        } else if ("土".equals(emm.getNationStr())) {
                            emm.setNation(11);
                        } else if ("塔塔尔".equals(emm.getNationStr())) {
                            emm.setNation(12);
                        } else if ("塔吉克".equals(emm.getNationStr())) {
                            emm.setNation(13);
                        } else if ("水".equals(emm.getNationStr())) {
                            emm.setNation(14);
                        } else if ("畲".equals(emm.getNationStr())) {
                            emm.setNation(15);
                        } else if ("撒拉".equals(emm.getNationStr())) {
                            emm.setNation(16);
                        } else if ("羌".equals(emm.getNationStr())) {
                            emm.setNation(17);
                        } else if ("普米".equals(emm.getNationStr())) {
                            emm.setNation(18);
                        } else if ("怒".equals(emm.getNationStr())) {
                            emm.setNation(19);
                        } else if ("纳西".equals(emm.getNationStr())) {
                            emm.setNation(20);
                        } else if ("仫佬".equals(emm.getNationStr())) {
                            emm.setNation(21);
                        } else if ("苗".equals(emm.getNationStr())) {
                            emm.setNation(22);
                        } else if ("蒙古".equals(emm.getNationStr())) {
                            emm.setNation(23);
                        } else if ("门巴".equals(emm.getNationStr())) {
                            emm.setNation(24);
                        } else if ("毛南".equals(emm.getNationStr())) {
                            emm.setNation(25);
                        } else if ("满".equals(emm.getNationStr())) {
                            emm.setNation(26);
                        } else if ("珞巴".equals(emm.getNationStr())) {
                            emm.setNation(27);
                        } else if ("僳僳".equals(emm.getNationStr())) {
                            emm.setNation(28);
                        } else if ("黎".equals(emm.getNationStr())) {
                            emm.setNation(29);
                        } else if ("拉祜".equals(emm.getNationStr())) {
                            emm.setNation(30);
                        } else if ("柯尔克孜".equals(emm.getNationStr())) {
                            emm.setNation(31);
                        } else if ("景颇".equals(emm.getNationStr())) {
                            emm.setNation(32);
                        } else if ("京".equals(emm.getNationStr())) {
                            emm.setNation(33);
                        } else if ("基诺".equals(emm.getNationStr())) {
                            emm.setNation(34);
                        } else if ("回".equals(emm.getNationStr())) {
                            emm.setNation(35);
                        } else if ("赫哲".equals(emm.getNationStr())) {
                            emm.setNation(36);
                        } else if ("哈萨克".equals(emm.getNationStr())) {
                            emm.setNation(37);
                        } else if ("哈尼".equals(emm.getNationStr())) {
                            emm.setNation(38);
                        } else if ("仡佬".equals(emm.getNationStr())) {
                            emm.setNation(39);
                        } else if ("高山".equals(emm.getNationStr())) {
                            emm.setNation(40);
                        } else if ("鄂温克".equals(emm.getNationStr())) {
                            emm.setNation(41);
                        } else if ("俄罗斯".equals(emm.getNationStr())) {
                            emm.setNation(42);
                        } else if ("鄂伦春".equals(emm.getNationStr())) {
                            emm.setNation(43);
                        } else if ("独龙".equals(emm.getNationStr())) {
                            emm.setNation(44);
                        } else if ("东乡".equals(emm.getNationStr())) {
                            emm.setNation(45);
                        } else if ("侗".equals(emm.getNationStr())) {
                            emm.setNation(46);
                        } else if ("德昂".equals(emm.getNationStr())) {
                            emm.setNation(47);
                        } else if ("傣".equals(emm.getNationStr())) {
                            emm.setNation(48);
                        } else if ("达斡尔".equals(emm.getNationStr())) {
                            emm.setNation(49);
                        } else if ("朝鲜".equals(emm.getNationStr())) {
                            emm.setNation(50);
                        } else if ("布依".equals(emm.getNationStr())) {
                            emm.setNation(51);
                        } else if ("布朗".equals(emm.getNationStr())) {
                            emm.setNation(52);
                        } else if ("保安".equals(emm.getNationStr())) {
                            emm.setNation(53);
                        } else if ("白".equals(emm.getNationStr())) {
                            emm.setNation(54);
                        } else if ("阿昌".equals(emm.getNationStr())) {
                            emm.setNation(55);
                        } else if ("汉".equals(emm.getNationStr())) {
                            emm.setNation(56);
                        }


                        if ("北京".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(1);
                        } else if ("上海".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(2);
                        } else if ("广东".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(3);
                        } else if ("河北".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(4);
                        } else if ("山西".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(5);
                        } else if ("辽宁".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(6);
                        } else if ("吉林".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(7);
                        } else if ("黑龙江".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(8);
                        } else if ("江苏".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(9);
                        } else if ("浙江".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(10);
                        } else if ("安徽".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(11);
                        } else if ("福建".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(12);
                        } else if ("江西".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(13);
                        } else if ("山东".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(14);
                        } else if ("河南".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(15);
                        } else if ("湖北".equals(emm.getNativePlaStr()) || "武汉".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(16);
                        } else if ("湖南".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(17);
                        } else if ("天津".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(18);
                        } else if ("陕西".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(19);
                        } else if ("四川".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(20);
                        } else if ("台湾".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(21);
                        } else if ("云南".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(22);
                        } else if ("青海".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(23);
                        } else if ("甘肃".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(24);
                        } else if ("海南".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(25);
                        } else if ("贵州".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(26);
                        } else if ("重庆".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(27);
                        } else if ("新疆".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(28);
                        } else if ("广西".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(29);
                        } else if ("宁夏".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(30);
                        } else if ("内蒙古".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(31);
                        } else if ("西藏".equals(emm.getNativePlaStr())) {
                            emm.setNativePla(32);
                        }

                        if ("小学".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(1);
                        } else if ("初中".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(2);
                        } else if ("高中".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(3);
                        } else if ("技校".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(4);
                        } else if ("中技".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(5);
                        } else if ("中专".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(6);
                        } else if ("大专".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(7);
                        } else if ("本科".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(8);
                        } else if ("研究生".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(9);
                        } else if ("硕士".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(10);
                        } else if ("博士".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(11);
                        } else if ("MBA".equals(emm.getEducationLeStr())) {
                            emm.setEducationLe(12);
                        }

                        emm.setScreAgreement("有".equals(emm.getScreAgreementStr()) || "是".equals(emm.getScreAgreementStr()) ? 1 : 0);

                        if ("离职和社保".equals(emm.getSateListAndLeaCertiStr()) || "是".equals(emm.getSateListAndLeaCertiStr())) {
                            emm.setSateListAndLeaCerti(1);
                        } else if ("无".equals(emm.getSateListAndLeaCertiStr())) {
                            emm.setSateListAndLeaCerti(0);
                        } else if ("社保".equals(emm.getSateListAndLeaCertiStr())) {
                            emm.setSateListAndLeaCerti(2);
                        } else if ("离职".equals(emm.getSateListAndLeaCertiStr())) {
                            emm.setSateListAndLeaCerti(3);
                        }

                        if ("毕业证".equals(emm.getOtherCertiStr())) {
                            emm.setOtherCerti(1);
                        } else if ("电工证".equals(emm.getOtherCertiStr())) {
                            emm.setOtherCerti(2);
                        } else if ("焊工证".equals(emm.getOtherCertiStr())) {
                            emm.setOtherCerti(3);
                        } else if ("结婚证".equals(emm.getOtherCertiStr())) {
                            emm.setOtherCerti(4);
                        }


                        if ("健康证".equals(emm.getHealthCertiStr()) || "有".equals(emm.getHealthCertiStr()) || "是".equals(emm.getHealthCertiStr())) {
                            emm.setHealthCerti(1);
                        } else if ("体检单".equals(emm.getHealthCertiStr())) {
                            emm.setHealthCerti(2);
                        } else if ("职业病体检".equals(emm.getHealthCertiStr())) {
                            emm.setHealthCerti(3);
                        } else if ("无".equals(emm.getHealthCertiStr())) {
                            emm.setHealthCerti(0);
                        } else if (emm.getSateListAndLeaCertiStr() == null) {
                            emm.setHealthCerti(0);
                        }

                        if ("总监".equals(emm.getPositionAttrIdStr())) {
                            emm.setPositionAttrId(1);
                        } else if ("总经理".equals(emm.getPositionAttrIdStr())) {
                            emm.setPositionAttrId(2);
                        } else if ("副总经理".equals(emm.getPositionAttrIdStr())) {
                            emm.setPositionAttrId(3);
                        } else if ("经理".equals(emm.getPositionAttrIdStr())) {
                            emm.setPositionAttrId(4);
                        } else if ("主管".equals(emm.getPositionAttrIdStr())) {
                            emm.setPositionAttrId(5);
                        } else if ("组长".equals(emm.getPositionAttrIdStr())) {
                            emm.setPositionAttrId(6);
                        } else if ("职员".equals(emm.getPositionAttrIdStr())) {
                            emm.setPositionAttrId(7);
                        }
                        UserInfo userInfo = personMapper.getUserInfoByEmpno(emm.getEmpNo());
                        if (userInfo == null) {
                            userInfo = new UserInfo();
                            userInfo.setEmpNo(emm.getEmpNo());
                            userInfo.setFullName(emm.getName());
                            userInfo.setUserName(emm.getEmpNo());
                            userInfo.setUserPwd("cosun888");
                            userInfo.setState(0);
                            userInfo.setUseruploadright(1);
                            userInfo.setUserActor(emm.getPositionAttrId());
                            userInfoMapper.saveUserInfoByBean(userInfo);
                        }

                        personMapper.saveEmployeeByBean(emm);
                    }
                }
            }

            LinShiEmp lse = null;
            Dept dept = null;
            Position position = null;
            for (Employee emm : employeeList) {
                if (emm.getEmpNo() == null) {
                    lse = personMapper.getLinShiEmpByName(emm.getName());
                    if (lse != null) {
                        personMapper.updateLinShiIsQuitExsit(lse.getName());
                    } else {
                        lse = new LinShiEmp();
                        lse.setName(emm.getName());
                        lse.setIncompdateStr(emm.getIncompdateStr());
                        lse.setID_NO(emm.getID_NO());
                        lse.setBirthDayStr(emm.getBirthDayStr().equals("") ? null : emm.getBirthDayStr());
                        lse.setHomeAddr(emm.getHomeAddr());
                        lse.setRemark(emm.getRemark());
                        lse.setWorkType(emm.getWorkType());
                        lse.setIsQuit(emm.getIsQuit());
                        dept = personMapper.getDeptByName(emm.getDeptName());
                        position = personMapper.getPositionByName(emm.getPositionName());
                        lse.setDeptId(dept.getId());
                        lse.setPositionId(position.getId());
                        lse.setSex("男".equals(emm.getSexStr()) ? 1 : 0);
                        if ("壮".equals(emm.getNationStr())) {
                            lse.setNation(1);
                        } else if ("藏".equals(emm.getNationStr())) {
                            lse.setNation(2);
                        } else if ("裕固".equals(emm.getNationStr())) {
                            lse.setNation(3);
                        } else if ("彝".equals(emm.getNationStr())) {
                            lse.setNation(4);
                        } else if ("瑶".equals(emm.getNationStr())) {
                            lse.setNation(5);
                        } else if ("锡伯".equals(emm.getNationStr())) {
                            lse.setNation(6);
                        } else if ("乌孜别克".equals(emm.getNationStr())) {
                            lse.setNation(7);
                        } else if ("维吾尔".equals(emm.getNationStr())) {
                            lse.setNation(8);
                        } else if ("佤".equals(emm.getNationStr())) {
                            lse.setNation(9);
                        } else if ("土家".equals(emm.getNationStr())) {
                            lse.setNation(10);
                        } else if ("土".equals(emm.getNationStr())) {
                            lse.setNation(11);
                        } else if ("塔塔尔".equals(emm.getNationStr())) {
                            lse.setNation(12);
                        } else if ("塔吉克".equals(emm.getNationStr())) {
                            lse.setNation(13);
                        } else if ("水".equals(emm.getNationStr())) {
                            lse.setNation(14);
                        } else if ("畲".equals(emm.getNationStr())) {
                            lse.setNation(15);
                        } else if ("撒拉".equals(emm.getNationStr())) {
                            lse.setNation(16);
                        } else if ("羌".equals(emm.getNationStr())) {
                            lse.setNation(17);
                        } else if ("普米".equals(emm.getNationStr())) {
                            lse.setNation(18);
                        } else if ("怒".equals(emm.getNationStr())) {
                            lse.setNation(19);
                        } else if ("纳西".equals(emm.getNationStr())) {
                            lse.setNation(20);
                        } else if ("仫佬".equals(emm.getNationStr())) {
                            lse.setNation(21);
                        } else if ("苗".equals(emm.getNationStr())) {
                            lse.setNation(22);
                        } else if ("蒙古".equals(emm.getNationStr())) {
                            lse.setNation(23);
                        } else if ("门巴".equals(emm.getNationStr())) {
                            lse.setNation(24);
                        } else if ("毛南".equals(emm.getNationStr())) {
                            lse.setNation(25);
                        } else if ("满".equals(emm.getNationStr())) {
                            lse.setNation(26);
                        } else if ("珞巴".equals(emm.getNationStr())) {
                            lse.setNation(27);
                        } else if ("僳僳".equals(emm.getNationStr())) {
                            lse.setNation(28);
                        } else if ("黎".equals(emm.getNationStr())) {
                            lse.setNation(29);
                        } else if ("拉祜".equals(emm.getNationStr())) {
                            lse.setNation(30);
                        } else if ("柯尔克孜".equals(emm.getNationStr())) {
                            lse.setNation(31);
                        } else if ("景颇".equals(emm.getNationStr())) {
                            lse.setNation(32);
                        } else if ("京".equals(emm.getNationStr())) {
                            lse.setNation(33);
                        } else if ("基诺".equals(emm.getNationStr())) {
                            lse.setNation(34);
                        } else if ("回".equals(emm.getNationStr())) {
                            lse.setNation(35);
                        } else if ("赫哲".equals(emm.getNationStr())) {
                            lse.setNation(36);
                        } else if ("哈萨克".equals(emm.getNationStr())) {
                            lse.setNation(37);
                        } else if ("哈尼".equals(emm.getNationStr())) {
                            lse.setNation(38);
                        } else if ("仡佬".equals(emm.getNationStr())) {
                            lse.setNation(39);
                        } else if ("高山".equals(emm.getNationStr())) {
                            lse.setNation(40);
                        } else if ("鄂温克".equals(emm.getNationStr())) {
                            lse.setNation(41);
                        } else if ("俄罗斯".equals(emm.getNationStr())) {
                            lse.setNation(42);
                        } else if ("鄂伦春".equals(emm.getNationStr())) {
                            lse.setNation(43);
                        } else if ("独龙".equals(emm.getNationStr())) {
                            lse.setNation(44);
                        } else if ("东乡".equals(emm.getNationStr())) {
                            lse.setNation(45);
                        } else if ("侗".equals(emm.getNationStr())) {
                            lse.setNation(46);
                        } else if ("德昂".equals(emm.getNationStr())) {
                            lse.setNation(47);
                        } else if ("傣".equals(emm.getNationStr())) {
                            lse.setNation(48);
                        } else if ("达斡尔".equals(emm.getNationStr())) {
                            lse.setNation(49);
                        } else if ("朝鲜".equals(emm.getNationStr())) {
                            lse.setNation(50);
                        } else if ("布依".equals(emm.getNationStr())) {
                            lse.setNation(51);
                        } else if ("布朗".equals(emm.getNationStr())) {
                            lse.setNation(52);
                        } else if ("保安".equals(emm.getNationStr())) {
                            lse.setNation(53);
                        } else if ("白".equals(emm.getNationStr())) {
                            lse.setNation(54);
                        } else if ("阿昌".equals(emm.getNationStr())) {
                            lse.setNation(55);
                        } else if ("汉".equals(emm.getNationStr())) {
                            lse.setNation(56);
                        }


                        if ("北京".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(1);
                        } else if ("上海".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(2);
                        } else if ("广东".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(3);
                        } else if ("河北".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(4);
                        } else if ("山西".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(5);
                        } else if ("辽宁".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(6);
                        } else if ("吉林".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(7);
                        } else if ("黑龙江".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(8);
                        } else if ("江苏".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(9);
                        } else if ("浙江".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(10);
                        } else if ("安徽".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(11);
                        } else if ("福建".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(12);
                        } else if ("江西".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(13);
                        } else if ("山东".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(14);
                        } else if ("河南".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(15);
                        } else if ("湖北".equals(emm.getNativePlaStr()) || "武汉".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(16);
                        } else if ("湖南".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(17);
                        } else if ("天津".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(18);
                        } else if ("陕西".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(19);
                        } else if ("四川".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(20);
                        } else if ("台湾".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(21);
                        } else if ("云南".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(22);
                        } else if ("青海".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(23);
                        } else if ("甘肃".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(24);
                        } else if ("海南".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(25);
                        } else if ("贵州".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(26);
                        } else if ("重庆".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(27);
                        } else if ("新疆".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(28);
                        } else if ("广西".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(29);
                        } else if ("宁夏".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(30);
                        } else if ("内蒙古".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(31);
                        } else if ("西藏".equals(emm.getNativePlaStr())) {
                            lse.setNativePla(32);
                        }
                        personMapper.saveLinShiEmpByBean(lse);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<Time> getTimeList(String timeStr) {
        if (timeStr != null && timeStr.trim().length() > 0) {
            String[] times = timeStr.split(" ");
            if (times != null) {
                List<Time> timeList = new ArrayList<Time>();
                for (int i = 0; i < times.length; i++) {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    try {
                        Date d = format.parse(times[i] + ":00");
                        timeList.add(new java.sql.Time(d.getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                return timeList;
            }
        }
        return null;
    }

    public List<SmallEmployee> findAllEmployeeByPositionLevel(String positionLevel) throws Exception {
        return personMapper.findAllEmployeeByPositionLevel(positionLevel);
    }

    @Transactional
    public void saveOutClockInList(List<OutClockIn> outClockInList, List<OutClockAll> outClockAllList) throws Exception {
        for (OutClockIn oc : outClockInList) {
            OutClockIn orginal = personMapper.getOutClockInByDateAndWeiXinId(oc);
            if (orginal == null)
                personMapper.addOutClockInDateByBean(oc);
        }
        for (OutClockAll oc : outClockAllList) {
            OutClockAll orginal = personMapper.getOutClockAllByDateAndWeiXinId(oc);
            if (orginal == null)
                personMapper.addOutClockAllDateByBean(oc);
        }
    }

    @Transactional
    public void saveZKQYList(List<ZhongKongBeanQY> qyList) throws Exception {
        ZhongKongBean zkb;
        ZhongKongBeanQY zkbq;
        for (ZhongKongBeanQY oc : qyList) {
            personMapper.addZKQY(oc);
            zkb = personMapper.getZKBByUserIdAndDateStr(oc.getUserId(), oc.getDateStr());
            if (zkb == null) {
                zkb = new ZhongKongBean();
                zkb.setEnrollNumber(oc.getUserId());
                zkb.setDateStr(oc.getDateStr());
                zkb.setTimeStr(oc.getTimeStr());
                zkb.setYearMonth(oc.getYearMonth());
                zkb.setRemark(oc.getRemark());
                personMapper.saveBeforeDayZhongKongData(zkb);
            } else {
                if (zkb.getTimeStr() == null || zkb.getTimeStr().trim().length() == 0) {
                    zkb = new ZhongKongBean();
                    zkb.setEnrollNumber(oc.getUserId());
                    zkb.setDateStr(oc.getDateStr());
                    zkb.setTimeStr(oc.getTimeStr());
                    zkb.setYearMonth(oc.getYearMonth());
                    zkb.setRemark(oc.getTimeStr());
                    personMapper.updateBeforeDayZhongKongData(zkb);
                } else {
                    String sortTime = StringUtil.sortTimes2(zkb.getTimeStr(), oc.getTimeStr());
                    zkb = new ZhongKongBean();
                    zkb.setEnrollNumber(oc.getUserId());
                    zkb.setDateStr(oc.getDateStr());
                    zkb.setTimeStr(sortTime);
                    zkb.setYearMonth(oc.getYearMonth());
                    zkb.setRemark(oc.getTimeStr());
                    personMapper.updateBeforeDayZhongKongData(zkb);
                }
            }
        }
    }

    public List<KQBean> getAllKQDataByYearMonthDay(String date) throws Exception {
        return personMapper.getAllKQDataByYearMonthDay(date);
    }

    public List<OutPutWorkData> computeTableListData(List<ClockInOrgin> clockInOrginList) throws Exception {
        List<OutPutWorkData> outPutWorkData = new ArrayList<OutPutWorkData>();
        OutPutWorkData aaa = new OutPutWorkData();
        String errorMessage = "";
        List<Employee> employeeList = personMapper.findAllEmployeeAll();
        String[] months;
        String monstr = "";
        String yearMonth = "";
        List<String> monthList = new ArrayList<String>();
        for (ClockInOrgin c : clockInOrginList) {
            months = c.getDateStr().split("/");
            if (Integer.valueOf(months[1]) < 10) {
                months[1] = 0 + months[1];
            }
            monstr = months[0] + "-" + months[1] + "-" + months[2];
            yearMonth = months[0] + "-" + months[1];

            if (!monthList.contains(yearMonth)) {
                monthList.add(yearMonth);
                if (monthList.size() >= 2) {
                    errorMessage = "单次计算考勤只能计算同一月份，不能出现两个或两个以后的月份。比如这次您有" + monthList.toString() + "这几个月份";
                    aaa.setErrorMessage(errorMessage);
                    outPutWorkData.add(aaa);
                    return outPutWorkData;
                }
            }
        }
        String month = monthList.get(0);
        Employee em = null;
        ClockInOrgin co = null;
        int emLen = employeeList.size();
        String date = null;
        int clLen = clockInOrginList.size();
        WorkSet workSet = null;
        List<WorkDate> workDateList = null;
        boolean aon = false;
        boolean aoff = false;
        boolean pon = false;
        boolean poff = false;
        OutPutWorkData otw = null;
        for (int i = 0; i < emLen; i++) {
            em = employeeList.get(i);
            workDateList = personMapper.getWorkDateByMonthAnPositionLevelList(yearMonth, em.getPositionLevel());
            for (WorkDate workDate : workDateList) {
                int type = workDate.getType();
                if (workDate != null) {
                    for (int j = 0; j < workDate.getWorkDatess().length; j++) {
                        date = workDate.getWorkDatess()[j];
                        boolean isWeekEnd = DateUtil.isWeekend(yearMonth + "-" + date);
                        for (int k = 0; k < clLen; k++) {
                            aon = false;
                            aoff = false;
                            pon = false;
                            poff = false;
                            co = clockInOrginList.get(k);
                            String[] coDay = co.getDateStr().split("/");
                            if (em.getName().equals(co.getEmployeeName()) && date.equals(coDay[2])) {
                                if (workDate.getEmpNostr() == null || workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                    otw = new OutPutWorkData();
                                    otw.setWorkArea(em.getWorkType());
                                    otw.setEmployeeName(em.getName());
                                    otw.setYearMonthDay(yearMonth + "-" + date);
                                    otw.setEmpNo(em.getEmpNo());
                                    otw.setPositionName(em.getPositionName());
                                    otw.setPositionLevel(em.getPositionLevel());
                                    otw.setDeptName(em.getDeptName());
                                    otw.setWorkDate(workDate.getWorkDate());
                                    otw.setWorkInDate(co.getDateStr());
                                    otw.setYearMonth(yearMonth);
                                    workSet = personMapper.getWorkSetByMonthAndPositionLevel2(yearMonth, em.getPositionLevel());
                                    Time time = null;
                                    if (workSet != null) {
                                        if (clockInOrginList.get(k).getTimes() != null && clockInOrginList.get(k).getTimes().length > 0) {
                                            otw.setOrginClockInStr(clockInOrginList.get(k).getTimeStr());
                                            List<Time> timeList = this.getTimeList(clockInOrginList.get(k).getTimeStr());
                                            for (int a = 0; a < timeList.size(); a++) {
                                                time = timeList.get(a);
                                                if (workSet.getMorningOn() != null) {
                                                    if (workSet.getMorningOnFrom() != null && workSet.getMorningOnEnd() != null) {
                                                        if (time.after(workSet.getMorningOnFrom()) && !time.after(workSet.getMorningOnEnd()) || (time.equals(workSet.getMorningOn()))) {
                                                            aon = true;
                                                            otw.setWorkSetAOn(workSet.getMorningOn().toString());
                                                            otw.setWorkSetAonFroml(workSet.getMorningOnFrom().toString());
                                                            otw.setWorkSetAOnEnd(workSet.getMorningOnEnd().toString());
                                                            otw.setClockAOn(time.toString());
                                                            if (type == 0) {
                                                                if (isWeekEnd && em.getWorkType() == 0) {
                                                                    otw.setIsAonOk("周六加班");
                                                                } else {
                                                                    otw.setIsAonOk("正常");
                                                                }
                                                            } else if (type == 1) {
                                                                if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                    otw.setIsAonOk("周末加班");
                                                                } else {
                                                                    otw.setIsAonOk("周末不上班");
                                                                }
                                                            } else if (type == 2) {
                                                                otw.setIsAonOk("法定假日加班");
                                                            }
                                                        }
                                                    } else {
                                                        errorMessage = em.getName() + "没有设置早上上班打卡时间段";
                                                        aaa.setErrorMessage(errorMessage);
                                                        outPutWorkData.add(aaa);
                                                        return outPutWorkData;
                                                    }
                                                } else {
                                                    errorMessage = em.getName() + "没有设置早上上班时间";
                                                    aaa.setErrorMessage(errorMessage);
                                                    outPutWorkData.add(aaa);
                                                    return outPutWorkData;
                                                }
                                                if (workSet.getMorningOff() != null) {
                                                    if (workSet.getMorningOffFrom() != null && workSet.getMorningOffEnd() != null) {
                                                        if (time.after(workSet.getMorningOffFrom()) && !time.after(workSet.getMorningOffEnd()) || (time.equals(workSet.getMorningOff()))) {
                                                            aoff = true;
                                                            otw.setWorkSetAOff(workSet.getMorningOff().toString());
                                                            otw.setWorkSetAOffFrom(workSet.getMorningOffFrom().toString());
                                                            otw.setWorkSetAOffEnd(workSet.getMorningOffEnd().toString());
                                                            otw.setClockAOff(time.toString());
                                                            if (type == 0) {
                                                                if (isWeekEnd && em.getWorkType() == 0) {
                                                                    otw.setIsAoffOk("周六加班");
                                                                } else {
                                                                    otw.setIsAoffOk("正常");
                                                                }
                                                            } else if (type == 1) {
                                                                if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                    otw.setIsAoffOk("周末加班");
                                                                } else {
                                                                    otw.setIsAoffOk("周末不上班");
                                                                }
                                                            } else if (type == 2) {
                                                                otw.setIsAoffOk("法定假日加班");
                                                            }
                                                        }
                                                    } else {
                                                        aoff = true;
                                                    }
                                                } else {
                                                    aoff = true;
                                                }
                                                if (workSet.getNoonOn() != null) {
                                                    if (workSet.getNoonOnFrom() != null && workSet.getNoonOnEnd() != null) {
                                                        if (time.after(workSet.getNoonOnFrom()) && !time.after(workSet.getNoonOnEnd()) || (time.equals(workSet.getNoonOn()))) {
                                                            pon = true;
                                                            otw.setWorkSetPOn(workSet.getNoonOn().toString());
                                                            otw.setWorkSetPOnFrom(workSet.getNoonOnFrom().toString());
                                                            otw.setWorkSetPOnEnd(workSet.getNoonOnEnd().toString());
                                                            otw.setClockPOn(time.toString());
                                                            if (type == 0) {
                                                                if (isWeekEnd && em.getWorkType() == 0) {
                                                                    otw.setIsPOnOk("周六加班");
                                                                } else {
                                                                    otw.setIsPOnOk("正常");
                                                                }
                                                            } else if (type == 1) {
                                                                if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                    otw.setIsPOnOk("周末加班");
                                                                } else {
                                                                    otw.setIsPOnOk("周末不上班");
                                                                }
                                                            } else if (type == 2) {
                                                                otw.setIsPOnOk("法定假日加班");
                                                            }
                                                        }
                                                    } else {
                                                        pon = true;
                                                    }
                                                } else {
                                                    pon = true;
                                                }
                                                if (workSet.getNoonOff() != null) {
                                                    if (workSet.getNoonOffFrom() != null) {
                                                        if (time.after(workSet.getNoonOffFrom()) || (time.equals(workSet.getNoonOff()))) {
                                                            poff = true;
                                                            otw.setWorkSetPOff(workSet.getNoonOff().toString());
                                                            otw.setWorkSetPOffForm(workSet.getNoonOffFrom().toString());
                                                            otw.setWorkSetPOffEnd(workSet.getNoonOffEnd().toString());
                                                            otw.setClockPOff(time.toString());
                                                            if (type == 0) {
                                                                if (isWeekEnd && em.getWorkType() == 0) {
                                                                    otw.setIsPOffOk("周六加班");
                                                                } else {
                                                                    otw.setIsPOffOk("正常");
                                                                }
                                                            } else if (type == 1) {
                                                                if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                    otw.setIsPOffOk("周末加班");
                                                                } else {
                                                                    otw.setIsPOffOk("周末不上班");
                                                                }
                                                            } else if (type == 2) {
                                                                otw.setIsPOffOk("法定假日加班");
                                                            }
                                                        }
                                                    } else {
                                                        poff = true;
                                                    }
                                                } else {
                                                    poff = true;
                                                }
                                                Double extHours = 0.0;
                                                String abcd = null;
                                                String[] allNum = null;
                                                String intNum = "";
                                                String deciNum = "";
                                                if (workSet.getExtworkon() != null) {
                                                    Time lastT = timeList.get(timeList.size() - 1);
                                                    if (lastT.after(workSet.getExtworkon())) {
                                                        if (lastT.after(workSet.getExtworkoff())) {
                                                            lastT = workSet.getExtworkoff();
                                                        }
                                                        Long abc = lastT.getTime() - workSet.getExtworkon().getTime();
                                                        extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                        abcd = String.format("%.1f", extHours);
                                                        if (abcd != null && !extHours.equals("0.0")) {
                                                            String ccc = String.valueOf(abcd);
                                                            allNum = String.valueOf(abcd).split("\\.");
                                                            intNum = allNum[0];
                                                            deciNum = allNum[1];
                                                            if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                deciNum = "0";
                                                            } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                deciNum = "5";
                                                            }
                                                            extHours = Double.valueOf(intNum + "." + deciNum);
                                                        }
                                                        otw.setWorkSetExtOn(workSet.getExtworkon().toString());
                                                        otw.setWorkSetExtOff(lastT.toString());
                                                        if (extHours >= 0) {
                                                            otw.setExtHours(extHours);
                                                        }
                                                    }
                                                }
                                            }

                                            if (!aon) {
                                                Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsAonOk("正常请假");
                                                        otw.setRemark("正常请假");
                                                    } else if (leave.getType() == 1) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsAonOk("因公外出");
                                                        otw.setRemark("因公外出");
                                                    } else if (leave.getType() == 2) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsAonOk("带薪年假");
                                                        otw.setRemark("带薪年假");
                                                    }
                                                } else {
                                                    otw.setIsAonOk("上午上班");
                                                    otw.setRemark("打卡时间不在规定时间范围内");
                                                }
                                            }
                                            if (!aoff) {
                                                Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsAoffOk("正常请假");
                                                        otw.setRemark("正常请假");
                                                    } else if (leave.getType() == 1) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsAoffOk("因公外出");
                                                        otw.setRemark("因公外出");
                                                    } else if (leave.getType() == 2) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsAoffOk("带薪年假");
                                                        otw.setRemark("带薪年假");
                                                    }
                                                } else {
                                                    otw.setIsAoffOk("上午下班");
                                                    otw.setRemark("打卡时间不在规定时间范围内");
                                                }
                                            }

                                            if (!pon) {
                                                Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsPOnOk("正常请假");
                                                        otw.setRemark("正常请假");
                                                    } else if (leave.getType() == 1) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsPOnOk("因公外出");
                                                        otw.setRemark("因公外出");
                                                    } else if (leave.getType() == 2) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsPOnOk("带薪年假");
                                                        otw.setRemark("带薪年假");
                                                    }
                                                } else {
                                                    otw.setIsPOnOk("下午上班");
                                                    otw.setRemark("打卡时间不在规定时间范围内");
                                                }
                                            }

                                            if (!poff) {
                                                Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsPOffOk("正常请假");
                                                        otw.setRemark("正常请假");
                                                    } else if (leave.getType() == 1) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsPOffOk("因公外出");
                                                        otw.setRemark("因公外出");
                                                    } else if (leave.getType() == 2) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsPOffOk("带薪年假");
                                                        otw.setRemark("带薪年假");
                                                    }
                                                } else {
                                                    otw.setIsPOffOk("下午下班");
                                                    otw.setRemark("打卡时间不在规定时间范围内");
                                                }
                                            }

                                        } else {
                                            Leave leave = personMapper.getLeaveByEmIdAndMonth(em.getId(), yearMonth + "-" + date + " " + "08:00:00", yearMonth + "-" + date + " " + "17:30:00");
                                            if (leave != null) {
                                                if (leave.getType() == 0) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setRemark("正常请假");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setRemark("因公外出");
                                                } else if (leave.getType() == 2) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setRemark("带薪年假");
                                                }
                                            } else {
                                                if (type == 0) {
                                                    otw.setRemark("旷工");
                                                } else if (type == 1) {
                                                    otw.setRemark("周末不上班");
                                                } else if (type == 2) {
                                                    otw.setRemark("法定带薪假日");
                                                }
                                            }
                                            leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                            if (leave != null) {
                                                if (leave.getType() == 0) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAonOk("正常请假");
                                                    otw.setRemark("正常请假");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAonOk("因公外出");
                                                    otw.setRemark("因公外出");
                                                } else if (leave.getType() == 2) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAonOk("带薪年假");
                                                    otw.setRemark("带薪年假");
                                                }
                                            } else {
                                                if (type == 0) {
                                                    otw.setIsAonOk("上午旷工");
                                                    otw.setRemark("旷工");
                                                } else if (type == 1) {
                                                    otw.setIsAonOk("周末不上班");
                                                    otw.setRemark("周末不上班");
                                                } else if (type == 2) {
                                                    otw.setIsAonOk("法定带薪假");
                                                    otw.setRemark("法定带薪假");
                                                }
                                            }
                                            if (workSet.getMorningOff() != null) {
                                                leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setRemark("正常请假");
                                                        otw.setIsAoffOk("正常请假");
                                                    } else if (leave.getType() == 1) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setRemark("因公外出");
                                                        otw.setIsAoffOk("因公外出");
                                                    } else if (leave.getType() == 2) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsAoffOk("带薪年假");
                                                        otw.setRemark("带薪年假");
                                                    }
                                                } else {
                                                    if (type == 0) {
                                                        otw.setIsAoffOk("上午旷工");
                                                        otw.setRemark("旷工");
                                                    } else if (type == 1) {
                                                        otw.setIsAoffOk("周末不上班");
                                                        otw.setRemark("周末不上班");
                                                    } else if (type == 2) {
                                                        otw.setIsAoffOk("法定带薪假");
                                                        otw.setRemark("法定带薪假");
                                                    }
                                                }
                                            }
                                            if (workSet.getNoonOn() != null) {
                                                leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setRemark("正常请假");
                                                        otw.setIsPOnOk("正常请假");
                                                    } else if (leave.getType() == 1) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setRemark("因公外出");
                                                        otw.setIsPOnOk("因公外出");
                                                    } else if (leave.getType() == 2) {
                                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                        otw.setIsPOnOk("带薪年假");
                                                        otw.setRemark("带薪年假");
                                                    }
                                                } else {
                                                    if (type == 0) {
                                                        otw.setIsPOnOk("下午旷工");
                                                        otw.setRemark("旷工");
                                                    } else if (type == 1) {
                                                        otw.setIsPOnOk("周末不上班");
                                                        otw.setRemark("周末不上班");
                                                    } else if (type == 2) {
                                                        otw.setIsPOnOk("法定带薪假");
                                                        otw.setRemark("法定带薪假");
                                                    }
                                                }
                                            }
                                            leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                            if (leave != null) {
                                                if (leave.getType() == 0) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOffOk("正常请假");
                                                    otw.setRemark("正常请假");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOffOk("因公外出");
                                                    otw.setRemark("因公外出");
                                                } else if (leave.getType() == 2) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOffOk("带薪年假");
                                                    otw.setRemark("带薪年假");
                                                }
                                            } else {
                                                if (type == 0) {
                                                    otw.setIsPOffOk("下午旷工");
                                                    otw.setRemark("旷工");
                                                } else if (type == 1) {
                                                    otw.setIsPOffOk("周末不上班");
                                                    otw.setRemark("周末不上班");
                                                } else if (type == 2) {
                                                    otw.setIsPOffOk("法定带薪假");
                                                    otw.setRemark("法定带薪假");
                                                }
                                            }
                                        }
                                    } else {
                                        otw.setRemark("没有此人打卡记录");
                                    }
                                    if (otw.getEmployeeName() != null && otw.getEmployeeName().trim().length() > 0) {
                                        otw.setWorkType(type);
                                        if (otw.getRemark() == null) {
                                            otw.setRemark("正常");
                                        }
                                        outPutWorkData.add(otw);
                                    }
                                }
                            }

                        }
                    }


                } else {
                    errorMessage = "您没有" + yearMonth + "年月份" + em.getName() + "人" + em.getPositionLevel() + "属性的排班表，请帮排单设置中设置。";
                    aaa.setErrorMessage(errorMessage);
                    outPutWorkData.add(aaa);
                    return outPutWorkData;
                }
            }


        }
        return outPutWorkData;

    }

    public List<Employee> findAllEmployeeAll() throws Exception {
        return personMapper.findAllEmployeeAll();
    }

    public List<Employee> findAllEmployeeFinance(Employee employee) throws Exception {
        return personMapper.findAllEmployeeFinance(employee);
    }

    public List<Employee> queryEmployeeSalaryByCondition(Employee employee) throws Exception {
        return personMapper.queryEmployeeSalaryByCondition(employee);
    }

    public int queryEmployeeSalaryByConditionCount(Employee employee) throws Exception {
        return personMapper.queryEmployeeSalaryByConditionCount(employee);
    }

    public void deleteEmployeeSalaryByEmpno(String empNo) throws Exception {
        personMapper.deleteEmployeeSalaryByEmpno(empNo);
    }

    public Employee getEmployeeByEmpno(String empNo) throws Exception {
        return personMapper.getEmployeeByEmpno(empNo);
    }

    public List<Employee> findAllEmployeeNotIsQuitandhaveEnrollNum() throws Exception {
        return personMapper.findAllEmployeeNotIsQuitandhaveEnrollNum();
    }

    public String getWorkDateByMonthC(String yearMonth) throws Exception {
        return personMapper.getWorkDateByMonthC(yearMonth);
    }

    public String getWorkDateByMonthD(String yearMonth) throws Exception {
        return personMapper.getWorkDateByMonthD(yearMonth);
    }


    @Transactional
    public List<KQBean> getgetAfterOperatorDataByOriginDataZhongDian(List<OutClockIn> clockDates, List<KQBean> kqBeans) throws Exception {
        List<KQBean> kqBeanList = new ArrayList<KQBean>();
        KQBean aaa = new KQBean();
        List<Employee> employeeList = personMapper.findAllEmployeeAllLinShi();
        String monstr = "";
        String yearMonth = kqBeans.get(0).getYearMonth();
        Employee em = null;
        KQBean co = null;
        ZhongKongBean co2 = null;
        int emLen = employeeList.size();
        Integer date = null;
        int clLen = kqBeans.size();
        WorkSet workSet = null;
        WorkSet afterWS = null;
        List<WorkDate> workDateList = null;
        boolean aon = false;
        boolean aoff = false;
        boolean pon = false;
        boolean poff = false;
        KQBean otw = null;
        OutClockIn outClockIn = null;
        String outtimeStr = "";
        YeBan yb = null;
        String abcd = null;
        String[] allNum = null;
        String intNum = "";
        String deciNum = "";
        Double aHours = 0.0;
        Double pHours = 0.0;
        Integer clockRes = 17;
        Double linchenJiaBan = 0.0;
        LinShiHours lsh = null;
        DaKaPianCha dkpc = personMapper.getDaKaPianCha();

        for (int i = 0; i < emLen; i++) {
            em = employeeList.get(i);
            workDateList = personMapper.getWorkDateByMonthAnPositionLevelList(yearMonth, em.getPositionLevel());
            for (WorkDate workDate : workDateList) {
                int type = workDate.getType();
                if (workDate != null) {
                    for (int j = 0; j < workDate.getWorkDatess().length; j++) {
                        date = Integer.valueOf(workDate.getWorkDatess()[j]);
                        boolean isWeekEnd = DateUtil.isWeekend(yearMonth + "-" + date);
                        for (int k = 0; k < clLen; k++) {
                            aon = false;
                            aoff = false;
                            pon = false;
                            poff = false;
                            aHours = 0.0;
                            pHours = 0.0;
                            clockRes = 17;
                            linchenJiaBan = 0.0;
                            co = kqBeans.get(k);
                            lsh = personMapper.getLSHByNameAndDateStr(co.getName(), co.getDateStr());
                            String[] coDay = co.getDateStr().split("-");
                            if (lsh != null) {
                                otw = new KQBean();
                                otw.setName(em.getName());
                                otw.setYearMonth(yearMonth + "-" + date);
                                otw.setEmpNo(em.getEmpNo() == null ? "" : em.getEmpNo());
                                otw.setDeptName(em.getDeptName() == null ? "" : em.getDeptName());
                                otw.setYearMonth(yearMonth);
                                otw.setWeek(DateUtil.getWeek(kqBeans.get(k).getDateStr()));
                                otw.setEnrollNumber(kqBeans.get(k).getEnrollNumber());
                                otw.setDateStr(kqBeans.get(k).getDateStr());
                                otw.setExtWorkHours(lsh.getHours());
                                otw.setClockResult(206);
                            } else {
                                if (em.getName() != null && co.getName() != null && em.getName().equals(co.getName()) && date.equals(Integer.valueOf(coDay[2]))) {
                                    if (workDate.getEmpNostr() == null || workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                        otw = new KQBean();
                                        otw.setName(em.getName());
                                        otw.setYearMonth(yearMonth + "-" + date);
                                        otw.setEmpNo(em.getEmpNo() == null ? "" : em.getEmpNo());
                                        otw.setDeptName(em.getDeptName() == null ? "" : em.getDeptName());
                                        otw.setYearMonth(yearMonth);
                                        otw.setWeek(DateUtil.getWeek(kqBeans.get(k).getDateStr()));
                                        otw.setEnrollNumber(kqBeans.get(k).getEnrollNumber());
                                        otw.setDateStr(kqBeans.get(k).getDateStr());
                                        workSet = personMapper.getWorkSetByMonthAndPositionLevel2(yearMonth, em.getPositionLevel());
                                        afterWS = StringUtil.plusPianCha(workSet, dkpc);
                                        Time time = null;
                                        yb = personMapper.getYeBanByEmpNoAndDateStrNew(co.getName(), co.getDateStr());
                                        if (yb != null) {
                                            Double zhengBanHours = 0.0;
                                            Double extHourss = 0.0;
                                            String remark = "";
                                            List<Time> timeListYe = getTimeList(co.getTimeStr());
                                            for (Time tim : timeListYe) {
                                                if (tim.before(workSet.getMorningOnEnd()) && tim.after(workSet.getMorningOnFrom())) {
                                                    aon = true;
                                                }
                                                if (tim.before(workSet.getMorningOffEnd()) && tim.after(workSet.getMorningOffFrom())) {
                                                    aoff = true;
                                                }
                                                if (tim.before(workSet.getNoonOnEnd()) && tim.after(workSet.getNoonOnFrom())) {
                                                    pon = true;
                                                }
                                                if (tim.before(workSet.getNoonOffEnd()) && tim.after(workSet.getNoonOffFrom())) {
                                                    poff = true;
                                                }

                                            }

                                            if (aon && (aoff || pon || poff)) {
                                                zhengBanHours = 4.0;
                                                Time offTime = null;
                                                for (int aa = timeListYe.size() - 1; aa >= 0; aa--) {
                                                    if (timeListYe.get(aa).before(workSet.getNoonOffEnd())) {
                                                        offTime = timeListYe.get(aa);
                                                        break;
                                                    }
                                                }
                                                if (offTime != null) {
                                                    Long abc = offTime.getTime() - workSet.getNoonOn().getTime();
                                                    extHourss = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                    abcd = String.format("%.1f", extHourss);
                                                    if (abcd != null && !extHourss.equals("0.0")) {
                                                        String ccc = String.valueOf(abcd);
                                                        allNum = String.valueOf(abcd).split("\\.");
                                                        intNum = allNum[0];
                                                        deciNum = allNum[1];
                                                        if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                            deciNum = "0";
                                                        } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                            deciNum = "5";
                                                        }
                                                        zhengBanHours += Double.valueOf(intNum + "." + deciNum);
                                                    }
                                                }
                                            }

                                            Time yeBanStar = timeListYe.get(timeListYe.size() - 1);
                                            Time yeBanEnd = null;
                                            String day2 = DateUtil.getAfterDay(co.getDateStr());
                                            ZhongKongBean timeStrDy2 = personMapper.getZKBeanByEmpNoAndDateStrLinShi(co.getName(), day2);
                                            if (timeStrDy2 != null && timeStrDy2.getTimeStr() != null && timeStrDy2.getTimeStr().trim().length() > 0) {
                                                List<Time> timeList2 = getTimeList(timeStrDy2.getTimeStr());
                                                yeBanEnd = timeList2.get(0);
                                            }

                                            if (yeBanEnd != null) {
                                                String dayTimeBefor = co.getDateStr() + " " + yeBanStar;
                                                String dayTimeAfter = day2 + " " + yeBanEnd;
                                                DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                Date dayBeF = dft.parse(dayTimeBefor);
                                                Date dayAfT = dft.parse(dayTimeAfter);
                                                Long abc = dayAfT.getTime() - dayBeF.getTime();
                                                extHourss = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                abcd = String.format("%.1f", extHourss);
                                                if (abcd != null && !extHourss.equals("0.0")) {
                                                    String ccc = String.valueOf(abcd);
                                                    allNum = String.valueOf(abcd).split("\\.");
                                                    intNum = allNum[0];
                                                    deciNum = allNum[1];
                                                    if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                        deciNum = "0";
                                                    } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                        deciNum = "5";
                                                    }
                                                    zhengBanHours += Double.valueOf(intNum + "." + deciNum);
                                                }
                                            }
                                            otw.setClockResult(13);
                                            otw.setExtWorkHours(zhengBanHours);
                                            otw.setTimeStr(co.getTimeStr());
                                            kqBeanList.add(otw);
                                        } else {
                                            if (workSet != null) {
                                                if (kqBeans.get(k).getTimeStr() != null && kqBeans.get(k).getTimeStr().length() > 0) {
                                                    otw.setTimeStr(kqBeans.get(k).getTimeStr());
                                                    List<Time> timeList = this.getTimeList(kqBeans.get(k).getTimeStr());
                                                    for (int a = 0; a < timeList.size(); a++) {
                                                        time = timeList.get(a);
                                                        if (workSet.getMorningOn() != null) {
                                                            if (workSet.getMorningOnFrom() != null && workSet.getMorningOnEnd() != null) {
                                                                if (time.after(workSet.getMorningOnFrom()) && !time.after(workSet.getMorningOnEnd()) || (time.equals(workSet.getMorningOn()))) {
                                                                    aon = true;
                                                                    otw.setaOnTime(time.toString());
                                                                    if (type == 0) {
                                                                        if (isWeekEnd && em.getWorkType() == 0) {
                                                                            otw.setaOnRemark("周六加班");
                                                                            otw.setClockResult(1);
                                                                        } else {
                                                                            otw.setaOnRemark("正常");
                                                                            otw.setClockResult(1);
                                                                        }
                                                                    } else if (type == 1) {
                                                                        if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                            otw.setaOnRemark("周末加班");
                                                                            otw.setClockResult(1);
                                                                        } else {
                                                                            otw.setaOnRemark("周末不上班");
                                                                            otw.setClockResult(5);
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                kqBeanList.add(aaa);
                                                                return kqBeanList;
                                                            }
                                                        } else {
                                                            kqBeanList.add(aaa);
                                                            return kqBeanList;
                                                        }
                                                        if (workSet.getMorningOff() != null) {
                                                            if (workSet.getMorningOffFrom() != null && workSet.getMorningOffEnd() != null) {
                                                                if (time.after(workSet.getMorningOffFrom()) && !time.after(workSet.getMorningOffEnd()) || (time.equals(workSet.getMorningOff()))) {
                                                                    aoff = true;
                                                                    otw.setaOffTime(time.toString());
                                                                    if (type == 0) {
                                                                        if (isWeekEnd && em.getWorkType() == 0) {
                                                                            otw.setaOffRemark("周六加班");
                                                                            otw.setClockResult(1);
                                                                        } else {
                                                                            otw.setaOffRemark("正常");
                                                                            otw.setClockResult(1);
                                                                        }
                                                                    } else if (type == 1) {
                                                                        if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                            otw.setaOffRemark("周末加班");
                                                                            otw.setClockResult(1);
                                                                        } else {
                                                                            otw.setaOffRemark("周末不上班");
                                                                            otw.setClockResult(5);
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                aoff = true;
                                                            }
                                                        } else {
                                                            aoff = true;
                                                        }
                                                        if (workSet.getNoonOn() != null) {
                                                            if (workSet.getNoonOnFrom() != null && workSet.getNoonOnEnd() != null) {
                                                                if (time.after(workSet.getNoonOnFrom()) && !time.after(workSet.getNoonOnEnd()) || (time.equals(workSet.getNoonOn()))) {
                                                                    pon = true;
                                                                    otw.setpOnTime(time.toString());
                                                                    if (type == 0) {
                                                                        if (isWeekEnd && em.getWorkType() == 0) {
                                                                            otw.setpOnRemark("周六加班");
                                                                            otw.setClockResult(1);
                                                                        } else {
                                                                            otw.setpOnRemark("正常");
                                                                            otw.setClockResult(1);
                                                                        }
                                                                    } else if (type == 1) {
                                                                        if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                            otw.setpOnRemark("周末加班");
                                                                            otw.setClockResult(1);
                                                                        } else {
                                                                            otw.setpOnRemark("周末不上班");
                                                                            otw.setClockResult(5);
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                pon = true;
                                                            }
                                                        } else {
                                                            pon = true;
                                                        }
                                                        if (workSet.getNoonOff() != null) {
                                                            if (workSet.getNoonOffFrom() != null) {
                                                                if (time.after(workSet.getNoonOffFrom()) || (time.equals(workSet.getNoonOff()))) {
                                                                    poff = true;
                                                                    otw.setpOffTime(time.toString());
                                                                    if (type == 0) {
                                                                        if (isWeekEnd && em.getWorkType() == 0) {
                                                                            otw.setpOffRemark("周六加班");
                                                                            otw.setClockResult(1);
                                                                        } else {
                                                                            otw.setpOffRemark("正常");
                                                                            otw.setClockResult(1);
                                                                        }
                                                                    } else if (type == 1) {
                                                                        if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                            otw.setpOffRemark("周末加班");
                                                                            otw.setClockResult(1);
                                                                        } else {
                                                                            otw.setpOffRemark("周末不上班");
                                                                            otw.setClockResult(5);
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                poff = true;
                                                            }
                                                        } else {
                                                            poff = true;
                                                        }
                                                        Double extHours = 0.0;
                                                        abcd = null;
                                                        allNum = null;
                                                        intNum = "";
                                                        deciNum = "";
                                                        if (workSet.getExtworkon() != null) {
                                                            Time lastT = timeList.get(timeList.size() - 1);
                                                            if (lastT.after(workSet.getExtworkon())) {
                                                                if (lastT.after(workSet.getExtworkoff())) {
                                                                    lastT = workSet.getExtworkoff();
                                                                }
                                                                Long abc = lastT.getTime() - workSet.getExtworkon().getTime();
                                                                extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                abcd = String.format("%.1f", extHours);
                                                                if (abcd != null && !extHours.equals("0.0")) {
                                                                    String ccc = String.valueOf(abcd);
                                                                    allNum = String.valueOf(abcd).split("\\.");
                                                                    intNum = allNum[0];
                                                                    deciNum = allNum[1];
                                                                    if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                        deciNum = "0";
                                                                    } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                        deciNum = "5";
                                                                    }
                                                                    extHours = Double.valueOf(intNum + "." + deciNum);
                                                                }
                                                                otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                otw.setExtWorkOffTime(lastT.toString());
                                                                if (extHours >= 0) {
                                                                    otw.setExtWorkHours(extHours);
                                                                }
                                                            }

                                                            if (extHours <= 0) {
                                                                DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                                                Date d2400 = dft.parse(co.getDateStr() + " " + "24:00:00");
                                                                Date d0000 = dft.parse(co.getDateStr() + " " + "00:00:00");
                                                                Date dd = format.parse("05:00:00");
                                                                String dateTomor = DateUtil.getAfterDay(co.getDateStr());
                                                                Time sam = new java.sql.Time(dd.getTime());
                                                                if (otw.getWeek().intValue() == 5 || otw.getWeek() == 6 || otw.getWeek().intValue() == 7) {
                                                                    co2 = personMapper.getZhongKongBeanByNumAndDate(co.getEnrollNumber(), dateTomor);
                                                                    if (co2 != null && co2.getTimeStr() != null && co2.getTimeStr().length() > 0) {
                                                                        String afterTime = dateTomor + " " + co2.getTimeStr().split(" ")[0];
                                                                        String beforeTime = co.getDateStr() + " " + workSet.getExtworkon();
                                                                        Date afterT = dft.parse(afterTime);
                                                                        Date beforTime = dft.parse(beforeTime);
                                                                        String afterTimem = co2.getTimeStr().split(" ")[0];
                                                                        Date d = format.parse(afterTimem);
                                                                        Time tii = new java.sql.Time(d.getTime());
                                                                        if (tii.before(sam)) {
                                                                            Long abc = d2400.getTime() - beforTime.getTime();
                                                                            extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                            abcd = String.format("%.1f", extHours);
                                                                            if (abcd != null && !extHours.equals("0.0")) {
                                                                                String ccc = String.valueOf(abcd);
                                                                                allNum = String.valueOf(abcd).split("\\.");
                                                                                intNum = allNum[0];
                                                                                deciNum = allNum[1];
                                                                                if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                                    deciNum = "0";
                                                                                } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                                    deciNum = "5";
                                                                                }
                                                                                extHours = Double.valueOf(intNum + "." + deciNum);
                                                                            }
                                                                            otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                            otw.setExtWorkOffTime(lastT.toString());
                                                                            if (extHours >= 0) {
                                                                                otw.setExtWorkHours(extHours);
                                                                            }
                                                                        }
                                                                    }

                                                                    if (extHours <= 0) {
                                                                        String firstTimeStr = co.getTimeStr().split(" ")[0];
                                                                        Date d = format.parse(firstTimeStr);
                                                                        Time tii = new java.sql.Time(d.getTime());
                                                                        if (tii.before(sam)) {
                                                                            Long abc = d.getTime() - d0000.getTime();
                                                                            extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                            abcd = String.format("%.1f", extHours);
                                                                            if (abcd != null && !extHours.equals("0.0")) {
                                                                                String ccc = String.valueOf(abcd);
                                                                                allNum = String.valueOf(abcd).split("\\.");
                                                                                intNum = allNum[0];
                                                                                deciNum = allNum[1];
                                                                                if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                                    deciNum = "0";
                                                                                } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                                    deciNum = "5";
                                                                                }
                                                                                extHours = Double.valueOf(intNum + "." + deciNum);
                                                                            }
                                                                            otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                            otw.setExtWorkOffTime(lastT.toString());
                                                                            if (extHours >= 0) {
                                                                                otw.setExtWorkHours(extHours);
                                                                            }
                                                                        }
                                                                    }
                                                                } else {
                                                                    co2 = personMapper.getZhongKongBeanByNumAndDate(co.getEnrollNumber(), dateTomor);
                                                                    if (co2 != null && co2.getTimeStr() != null && co2.getTimeStr().length() > 0) {
                                                                        String afterTime = dateTomor + " " + co2.getTimeStr().split(" ")[0];
                                                                        String beforeTime = co.getDateStr() + " " + workSet.getExtworkon();
                                                                        Date afterT = dft.parse(afterTime);
                                                                        Date beforTime = dft.parse(beforeTime);
                                                                        String afterTimem = co2.getTimeStr().split(" ")[0];
                                                                        Date d = format.parse(afterTimem);
                                                                        Time tii = new java.sql.Time(d.getTime());
                                                                        if (tii.before(sam)) {
                                                                            Long abc = afterT.getTime() - beforTime.getTime();
                                                                            extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                            abcd = String.format("%.1f", extHours);
                                                                            if (abcd != null && !extHours.equals("0.0")) {
                                                                                String ccc = String.valueOf(abcd);
                                                                                allNum = String.valueOf(abcd).split("\\.");
                                                                                intNum = allNum[0];
                                                                                deciNum = allNum[1];
                                                                                if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                                    deciNum = "0";
                                                                                } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                                    deciNum = "5";
                                                                                }
                                                                                extHours = Double.valueOf(intNum + "." + deciNum);
                                                                            }
                                                                            otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                            otw.setExtWorkOffTime(lastT.toString());
                                                                            if (extHours >= 0) {
                                                                                otw.setExtWorkHours(extHours);
                                                                            }
                                                                        }
                                                                    }
                                                                }

                                                            }
                                                        }
                                                    }

                                                    if (!aon) {
                                                        Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                                        if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                            if (leave != null) {
                                                                if (leave.getType() == 0) {
                                                                    otw.setaOnRemark("正常请假");
                                                                    otw.setRemark("正常请假");
                                                                    otw.setClockResult(6);
                                                                } else if (leave.getType() == 1) {
                                                                    outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                    if (outClockIn != null) {
                                                                        outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                    }
                                                                    otw.setTimeStr(outtimeStr);
                                                                    otw.setaOnRemark("因公外出");
                                                                    otw.setRemark("因公外出");
                                                                    otw.setClockResult(2);
                                                                }
                                                            }
                                                        } else {
                                                            boolean isAOnH = false;
                                                            a:
                                                            for (Time tii : timeList) {
                                                                if (tii.after(afterWS.getMorningOn()) && !tii.after(afterWS.getMorningOff())) {
                                                                    if (pon) {
                                                                        aHours = DateUtil.calcuHours(tii, workSet.getMorningOff());
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        if (pon || poff) {
                                                                            aHours = DateUtil.calcuHours(tii, workSet.getMorningOff());
                                                                            Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                            if (le != null) {
                                                                                clockRes = 16;
                                                                            }
                                                                            break a;
                                                                        } else {
                                                                            for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                                if (timeList.get(tiii).after(afterWS.getMorningOn()) && !timeList.get(tiii).after(afterWS.getMorningOff())) {
                                                                                    aHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                    isAOnH = true;
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }

                                                                            if (!isAOnH) {
                                                                                for (Time tiii : timeList) {
                                                                                    if (tiii.after(afterWS.getMorningOn())) {
                                                                                        aHours = DateUtil.calcuHours(tiii, workSet.getMorningOff());
                                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tiii, em.getId());
                                                                                        if (le != null) {
                                                                                            clockRes = 16;
                                                                                        }
                                                                                        break a;
                                                                                    }
                                                                                }
                                                                            }

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            if (aHours != 0.0) {
                                                                if (em.getWorkType() == 1) {
                                                                    aHours += 0.5;
                                                                }
                                                                otw.setaOnRemark(aHours.toString());
                                                                otw.setRemark(aHours.toString() + "," + pHours.toString());
                                                                otw.setClockResult(clockRes);
                                                            } else {
                                                                otw.setaOnRemark("上午上班");
                                                                otw.setRemark("打卡时间不在规定时间范围内");
                                                                otw.setClockResult(7);
                                                            }
                                                        }

                                                    }
                                                    if (!aoff) {
                                                        Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                                        if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                            if (leave != null) {
                                                                if (leave.getType() == 0) {
                                                                    otw.setaOnRemark("正常请假");
                                                                    otw.setRemark("正常请假");
                                                                    otw.setClockResult(6);
                                                                } else if (leave.getType() == 1) {
                                                                    outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                    if (outClockIn != null) {
                                                                        outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                    }
                                                                    otw.setTimeStr(outtimeStr);
                                                                    otw.setaOffRemark("因公外出");
                                                                    otw.setRemark("因公外出");
                                                                    otw.setClockResult(2);
                                                                }
                                                            }
                                                        } else {
                                                            boolean isAOnH = false;
                                                            a:
                                                            for (Time tii : timeList) {
                                                                if (tii.after(afterWS.getMorningOn()) && !tii.after(afterWS.getMorningOff())) {
                                                                    if (aon) {
                                                                        aHours = DateUtil.calcuHours(workSet.getMorningOn(), tii);
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        if (pon || poff) {
                                                                            aHours = DateUtil.calcuHours(workSet.getMorningOn(), tii);
                                                                            Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                            if (le != null) {
                                                                                clockRes = 16;
                                                                            }
                                                                            break a;
                                                                        } else {
                                                                            for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                                if (timeList.get(tiii).after(afterWS.getMorningOn()) && !timeList.get(tiii).after(afterWS.getMorningOff())) {
                                                                                    aHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                    isAOnH = true;
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + timeList.get(tiii), yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }

                                                                            if (!isAOnH) {
                                                                                for (Time tiii : timeList) {
                                                                                    if (tiii.after(afterWS.getMorningOn())) {
                                                                                        aHours = DateUtil.calcuHours(tiii, workSet.getMorningOff());
                                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tiii, em.getId());
                                                                                        if (le != null) {
                                                                                            clockRes = 16;
                                                                                        }
                                                                                        break a;
                                                                                    }
                                                                                }
                                                                            }

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            if (aHours != 0.0) {
                                                                if (em.getWorkType() == 1) {
                                                                    aHours += 0.5;
                                                                }
                                                                otw.setaOnRemark(aHours.toString());
                                                                otw.setRemark(aHours.toString() + "," + pHours.toString());
                                                                otw.setClockResult(clockRes);
                                                            } else {
                                                                otw.setaOffRemark("上午下班");
                                                                otw.setRemark("打卡时间不在规定时间范围内");
                                                                otw.setClockResult(7);
                                                            }
                                                        }
                                                    }

                                                    if (!pon) {
                                                        Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                                        if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                            if (leave != null) {
                                                                if (leave.getType() == 0) {
                                                                    otw.setpOnRemark("正常请假");
                                                                    otw.setRemark("正常请假");
                                                                    otw.setClockResult(6);
                                                                } else if (leave.getType() == 1) {
                                                                    outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                    if (outClockIn != null) {
                                                                        outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                    }
                                                                    otw.setTimeStr(outtimeStr);
                                                                    otw.setpOnRemark("因公外出");
                                                                    otw.setRemark("因公外出");
                                                                    otw.setClockResult(2);
                                                                }
                                                            }
                                                        } else {
                                                            boolean isAOnH = false;
                                                            a:
                                                            for (Time tii : timeList) {
                                                                if (tii.after(afterWS.getNoonOn()) && !tii.after(afterWS.getNoonOff())) {
                                                                    if (poff) {
                                                                        pHours = DateUtil.calcuHours(tii, workSet.getNoonOff());
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        if (aon || aoff) {
                                                                            pHours = DateUtil.calcuHours(tii, workSet.getNoonOff());
                                                                            Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                            if (le != null) {
                                                                                clockRes = 16;
                                                                            }
                                                                            break a;
                                                                        } else {
                                                                            for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                                if (timeList.get(tiii).after(afterWS.getNoonOn()) && !timeList.get(tiii).after(afterWS.getNoonOff())) {
                                                                                    pHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                    isAOnH = true;
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }
                                                                            if (!isAOnH) {
                                                                                for (Time tiii : timeList) {
                                                                                    if (tiii.after(afterWS.getNoonOn())) {
                                                                                        pHours = DateUtil.calcuHours(tiii, workSet.getNoonOff());
                                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tiii, em.getId());
                                                                                        if (le != null) {
                                                                                            clockRes = 16;
                                                                                        }
                                                                                        break a;
                                                                                    }
                                                                                }
                                                                            }

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            if (pHours != 0.0) {
                                                                otw.setaOnRemark(pHours.toString());
                                                                otw.setRemark(aHours + "," + pHours.toString());
                                                                otw.setClockResult(clockRes);
                                                            } else {
                                                                otw.setpOnRemark("下午上班");
                                                                otw.setRemark("打卡时间不在规定时间范围内");
                                                                otw.setClockResult(7);
                                                            }
                                                        }
                                                    }

                                                    if (!poff) {
                                                        Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                                        if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                            if (leave != null) {
                                                                if (leave.getType() == 0) {
                                                                    otw.setpOffRemark("正常请假");
                                                                    otw.setRemark("正常请假");
                                                                    otw.setClockResult(6);
                                                                } else if (leave.getType() == 1) {
                                                                    outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                    if (outClockIn != null) {
                                                                        outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                    }
                                                                    otw.setTimeStr(outtimeStr);
                                                                    otw.setpOffRemark("因公外出");
                                                                    otw.setRemark("因公外出");
                                                                    otw.setClockResult(2);
                                                                }
                                                            }
                                                        } else {
                                                            boolean isAOnH = false;
                                                            a:
                                                            for (Time tii : timeList) {
                                                                if (tii.after(afterWS.getNoonOn()) && !tii.after(afterWS.getNoonOff())) {
                                                                    if (pon) {
                                                                        pHours = DateUtil.calcuHours(workSet.getNoonOn(), tii);
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        if (aon || aoff) {
                                                                            pHours = DateUtil.calcuHours(workSet.getNoonOn(), tii);
                                                                            Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                            if (le != null) {
                                                                                clockRes = 16;
                                                                            }
                                                                            break a;
                                                                        } else {
                                                                            for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                                if (timeList.get(tiii).after(afterWS.getNoonOn()) && !timeList.get(tiii).after(afterWS.getNoonOff())) {
                                                                                    pHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                    isAOnH = true;
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + timeList.get(tiii), yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            if (pHours != 0.0) {
                                                                otw.setaOnRemark(pHours.toString());
                                                                otw.setRemark(aHours + "," + pHours.toString());
                                                                otw.setClockResult(clockRes);
                                                            } else {
                                                                otw.setpOffRemark("下午下班");
                                                                otw.setRemark("打卡时间不在规定时间范围内");
                                                                otw.setClockResult(7);
                                                            }
                                                        }
                                                    }
                                                    int rightclocknum = (aon == true ? 1 : 0) + (aoff == true ? 1 : 0) + (pon == true ? 1 : 0) + (poff == true ? 1 : 0);
                                                    if (rightclocknum < 2) {
                                                        if (DateUtil.getWeek(kqBeans.get(k).getDateStr()) == 6 || DateUtil.getWeek(kqBeans.get(k).getDateStr()) == 7) {
                                                            QianKa qqk = personMapper.getQianKaByDateAndEmpnoA(otw.getEmpNo(), otw.getDateStr());
                                                            if (qqk != null) {
                                                                otw.setRemark("打卡时间不在规定时间内");
                                                                otw.setClockResult(7);
                                                            } else {
                                                                otw.setRemark("放假");
                                                                otw.setClockResult(5);
                                                            }
                                                        } else {
                                                            Leave a = personMapper.getLeaveByEmIdAndMonthAB(otw.getEmpNo(), otw.getDateStr());
                                                            if (a != null) {
                                                                //otw.setClockResult(16);
                                                            } else {
                                                                //otw.setClockResult(17);
                                                            }
                                                        }
                                                    }

                                                    Double extHours = 0.0;
                                                    DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                                    Date d2400 = dft.parse(co.getDateStr() + " " + "24:00:00");
                                                    Date d0000 = dft.parse(co.getDateStr() + " " + "00:00:00");
                                                    Date dd = format.parse("05:00:00");
                                                    String dateTomor = DateUtil.getAfterDay(co.getDateStr());
                                                    Time sam = new java.sql.Time(dd.getTime());
                                                    String firstTimeStr = co.getTimeStr().split(" ")[0];
                                                    Date d = format.parse(firstTimeStr);
                                                    Date df = dft.parse(co.getDateStr() + " " + firstTimeStr);
                                                    Time tii = new java.sql.Time(d.getTime());
                                                    if (tii.before(sam)) {
                                                        Long abc = df.getTime() - d0000.getTime();
                                                        extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                        abcd = String.format("%.1f", extHours);
                                                        if (abcd != null && !extHours.equals("0.0")) {
                                                            String ccc = String.valueOf(abcd);
                                                            allNum = String.valueOf(abcd).split("\\.");
                                                            intNum = allNum[0];
                                                            deciNum = allNum[1];
                                                            if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                deciNum = "0";
                                                            } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                deciNum = "5";
                                                            }
                                                            extHours = Double.valueOf(intNum + "." + deciNum);
                                                        }
                                                        otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                        otw.setExtWorkOffTime(firstTimeStr.toString());
                                                        if (extHours >= 0) {
                                                            otw.setExtWorkHours((otw.getExtWorkHours() == null ? 0.0 : otw.getExtWorkHours()) + extHours);
                                                        }
                                                    }

                                                } else {
                                                    Leave leave = personMapper.getLeaveByEmIdAndMonth(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + workSet.getNoonOff());
                                                    if (leave != null) {
                                                        if (leave.getType() == 0) {
                                                            otw.setRemark("正常请假");
                                                            otw.setClockResult(6);
                                                        } else if (leave.getType() == 1) {
                                                            outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                            if (outClockIn != null) {
                                                                outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                            }
                                                            otw.setTimeStr(outtimeStr);
                                                            otw.setRemark("因公外出");
                                                            otw.setClockResult(2);
                                                        }
                                                    } else {
                                                        if (type == 0) {
                                                            otw.setRemark("不上班");
                                                            otw.setClockResult(5);
                                                        } else if (type == 1) {
                                                            otw.setRemark("周末不上班");
                                                            otw.setClockResult(5);
                                                        }
                                                    }
                                                    leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                                    if (leave != null) {
                                                        if (leave.getType() == 0) {
                                                            otw.setaOnRemark("正常请假");
                                                            otw.setRemark("正常请假");
                                                            otw.setClockResult(6);
                                                        } else if (leave.getType() == 1) {
                                                            outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                            if (outClockIn != null) {
                                                                outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                            }
                                                            otw.setTimeStr(outtimeStr);
                                                            otw.setaOnRemark("因公外出");
                                                            otw.setRemark("因公外出");
                                                            otw.setClockResult(2);
                                                        }
                                                    } else {
                                                        if (type == 0) {
                                                            otw.setaOnRemark("不上班");
                                                            otw.setRemark("不上班");
                                                            otw.setClockResult(5);
                                                        } else if (type == 1) {
                                                            otw.setaOnRemark("周末不上班");
                                                            otw.setRemark("周末不上班");
                                                            otw.setClockResult(5);
                                                        }
                                                    }
                                                    if (workSet.getMorningOff() != null) {
                                                        leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setRemark("正常请假");
                                                                otw.setaOffRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setRemark("因公外出");
                                                                otw.setaOffRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            }
                                                        } else {
                                                            if (type == 0) {
                                                                otw.setaOffRemark("不上班");
                                                                otw.setRemark("不上班");
                                                                otw.setClockResult(5);
                                                            } else if (type == 1) {
                                                                otw.setaOffRemark("周末不上班");
                                                                otw.setRemark("周末不上班");
                                                                otw.setClockResult(5);
                                                            }
                                                        }
                                                    }
                                                    if (workSet.getNoonOn() != null) {
                                                        leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setRemark("正常请假");
                                                                otw.setpOnRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setRemark("因公外出");
                                                                otw.setpOnRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            }
                                                        } else {
                                                            if (type == 0) {
                                                                otw.setpOnRemark("不上班");
                                                                otw.setRemark("不上班");
                                                                otw.setClockResult(5);
                                                            } else if (type == 1) {
                                                                otw.setpOnRemark("周末不上班");
                                                                otw.setRemark("周末不上班");
                                                                otw.setClockResult(5);
                                                            }
                                                        }
                                                    }
                                                    leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                                    if (leave != null) {
                                                        if (leave.getType() == 0) {
                                                            otw.setpOffRemark("正常请假");
                                                            otw.setRemark("正常请假");
                                                            otw.setClockResult(6);
                                                        } else if (leave.getType() == 1) {
                                                            outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                            if (outClockIn != null) {
                                                                outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                            }
                                                            otw.setTimeStr(outtimeStr);
                                                            otw.setpOffRemark("因公外出");
                                                            otw.setRemark("因公外出");
                                                            otw.setClockResult(2);
                                                        }
                                                    } else {
                                                        if (type == 0) {
                                                            otw.setpOffRemark("不上班");
                                                            otw.setRemark("不上班");
                                                            otw.setClockResult(5);
                                                        } else if (type == 1) {
                                                            otw.setpOffRemark("周末不上班");
                                                            otw.setRemark("周末不上班");
                                                            otw.setClockResult(5);
                                                        }
                                                    }
                                                }
                                            } else {
                                                otw.setRemark("没有此人打卡记录");
                                                otw.setClockResult(10);
                                            }
                                            if (otw.getName() != null && otw.getName().trim().length() > 0) {
                                                if (otw.getRemark() == null) {
                                                    otw.setRemark("正常");
                                                    otw.setClockResult(1);
                                                }
                                                kqBeanList.add(otw);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    kqBeanList.add(aaa);
                    return kqBeanList;
                }
            }
        }
        return kqBeanList;
    }

    @Transactional
    public List<KQBean> getgetAfterOperatorDataByOriginDataLinShi(List<OutClockIn> clockDates, List<KQBean> kqBeans) throws Exception {
        List<KQBean> kqBeanList = new ArrayList<KQBean>();
        KQBean aaa = new KQBean();
        List<Employee> employeeList = personMapper.findAllEmployeeAllLinShi();
        String monstr = "";
        String yearMonth = kqBeans.get(0).getYearMonth();
        Employee em = null;
        KQBean co = null;
        ZhongKongBean co2 = null;
        int emLen = employeeList.size();
        Integer date = null;
        int clLen = kqBeans.size();
        WorkSet workSet = null;
        WorkSet afterWS = null;
        List<WorkDate> workDateList = null;
        boolean aon = false;
        boolean aoff = false;
        boolean pon = false;
        boolean poff = false;
        KQBean otw = null;
        OutClockIn outClockIn = null;
        SimpleDateFormat dft2 = new SimpleDateFormat("yyyy-MM-dd");
        String outtimeStr = "";
        YeBan yb = null;
        String abcd = null;
        String[] allNum = null;
        String intNum = "";
        String deciNum = "";
        Double aHours = 0.0;
        Double pHours = 0.0;
        Integer clockRes = 17;
        Double linchenJiaBan = 0.0;

        DaKaPianCha dkpc = personMapper.getDaKaPianCha();

        for (int i = 0; i < emLen; i++) {
            em = employeeList.get(i);
            workDateList = personMapper.getWorkDateByMonthAnPositionLevelList(yearMonth, em.getPositionLevel());
            for (WorkDate workDate : workDateList) {
                int type = workDate.getType();
                if (workDate != null) {
                    for (int j = 0; j < workDate.getWorkDatess().length; j++) {
                        date = Integer.valueOf(workDate.getWorkDatess()[j]);
                        boolean isWeekEnd = DateUtil.isWeekend(yearMonth + "-" + date);
                        if (dft2.parse(yearMonth + "-" + date).before(dft2.parse(em.getIncompdateStr())))
                            continue;
                        for (int k = 0; k < clLen; k++) {
                            aon = false;
                            aoff = false;
                            pon = false;
                            poff = false;
                            aHours = 0.0;
                            pHours = 0.0;
                            clockRes = 17;
                            linchenJiaBan = 0.0;
                            co = kqBeans.get(k);
                            String[] coDay = co.getDateStr().split("-");
                            if (em.getName() != null && co.getName() != null && em.getName().equals(co.getName()) && date.equals(Integer.valueOf(coDay[2]))) {
                                if (workDate.getEmpNostr() == null || workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                    otw = new KQBean();
                                    otw.setName(em.getName());
                                    otw.setYearMonth(yearMonth + "-" + date);
                                    otw.setEmpNo(em.getEmpNo() == null ? "" : em.getEmpNo());
                                    otw.setDeptName(em.getDeptName() == null ? "" : em.getDeptName());
                                    otw.setYearMonth(yearMonth);
                                    otw.setWeek(DateUtil.getWeek(kqBeans.get(k).getDateStr()));
                                    otw.setEnrollNumber(kqBeans.get(k).getEnrollNumber());
                                    otw.setDateStr(kqBeans.get(k).getDateStr());
                                    workSet = personMapper.getWorkSetByMonthAndPositionLevel2(yearMonth, em.getPositionLevel());
                                    afterWS = StringUtil.plusPianCha(workSet, dkpc);
                                    Time time = null;
                                    yb = personMapper.getYeBanByEmpNoAndDateStrNew(co.getName(), co.getDateStr());
                                    if (yb != null) {
                                        Double zhengBanHours = 0.0;
                                        Double extHourss = 0.0;
                                        String remark = "";
                                        List<Time> timeListYe = getTimeList(co.getTimeStr());
                                        for (Time tim : timeListYe) {
                                            if (tim.before(workSet.getMorningOnEnd()) && tim.after(workSet.getMorningOnFrom())) {
                                                aon = true;
                                            }
                                            if (tim.before(workSet.getMorningOffEnd()) && tim.after(workSet.getMorningOffFrom())) {
                                                aoff = true;
                                            }
                                            if (tim.before(workSet.getNoonOnEnd()) && tim.after(workSet.getNoonOnFrom())) {
                                                pon = true;
                                            }
                                            if (tim.before(workSet.getNoonOffEnd()) && tim.after(workSet.getNoonOffFrom())) {
                                                poff = true;
                                            }

                                        }

                                        if (aon && (aoff || pon || poff)) {
                                            zhengBanHours = 4.0;
                                            Time offTime = null;
                                            for (int aa = timeListYe.size() - 1; aa >= 0; aa--) {
                                                if (timeListYe.get(aa).before(workSet.getNoonOffEnd())) {
                                                    offTime = timeListYe.get(aa);
                                                    break;
                                                }
                                            }
                                            if (offTime != null) {
                                                Long abc = offTime.getTime() - workSet.getNoonOn().getTime();
                                                extHourss = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                abcd = String.format("%.1f", extHourss);
                                                if (abcd != null && !extHourss.equals("0.0")) {
                                                    String ccc = String.valueOf(abcd);
                                                    allNum = String.valueOf(abcd).split("\\.");
                                                    intNum = allNum[0];
                                                    deciNum = allNum[1];
                                                    if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                        deciNum = "0";
                                                    } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                        deciNum = "5";
                                                    }
                                                    zhengBanHours += Double.valueOf(intNum + "." + deciNum);
                                                }
                                            }
                                        }

                                        Time yeBanStar = timeListYe.get(timeListYe.size() - 1);
                                        Time yeBanEnd = null;
                                        String day2 = DateUtil.getAfterDay(co.getDateStr());
                                        ZhongKongBean timeStrDy2 = personMapper.getZKBeanByEmpNoAndDateStrLinShi(co.getName(), day2);
                                        if (timeStrDy2 != null && timeStrDy2.getTimeStr() != null && timeStrDy2.getTimeStr().trim().length() > 0) {
                                            List<Time> timeList2 = getTimeList(timeStrDy2.getTimeStr());
                                            yeBanEnd = timeList2.get(0);
                                        }

                                        if (yeBanEnd != null) {
                                            String dayTimeBefor = co.getDateStr() + " " + yeBanStar;
                                            String dayTimeAfter = day2 + " " + yeBanEnd;
                                            DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            Date dayBeF = dft.parse(dayTimeBefor);
                                            Date dayAfT = dft.parse(dayTimeAfter);
                                            Long abc = dayAfT.getTime() - dayBeF.getTime();
                                            extHourss = ((abc.doubleValue()) / (1000 * 60 * 60));
                                            abcd = String.format("%.1f", extHourss);
                                            if (abcd != null && !extHourss.equals("0.0")) {
                                                String ccc = String.valueOf(abcd);
                                                allNum = String.valueOf(abcd).split("\\.");
                                                intNum = allNum[0];
                                                deciNum = allNum[1];
                                                if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                    deciNum = "0";
                                                } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                    deciNum = "5";
                                                }
                                                zhengBanHours += Double.valueOf(intNum + "." + deciNum);
                                            }
                                        }
                                        otw.setClockResult(13);
                                        otw.setExtWorkHours(zhengBanHours);
                                        otw.setTimeStr(co.getTimeStr());
                                        kqBeanList.add(otw);
                                    } else {
                                        if (workSet != null) {
                                            if (kqBeans.get(k).getTimeStr() != null && kqBeans.get(k).getTimeStr().length() > 0) {
                                                otw.setTimeStr(kqBeans.get(k).getTimeStr());
                                                List<Time> timeList = this.getTimeList(kqBeans.get(k).getTimeStr());
                                                for (int a = 0; a < timeList.size(); a++) {
                                                    time = timeList.get(a);
                                                    if (workSet.getMorningOn() != null) {
                                                        if (workSet.getMorningOnFrom() != null && workSet.getMorningOnEnd() != null) {
                                                            if (time.after(workSet.getMorningOnFrom()) && !time.after(workSet.getMorningOnEnd()) || (time.equals(workSet.getMorningOn()))) {
                                                                aon = true;
                                                                otw.setaOnTime(time.toString());
                                                                if (type == 0) {
                                                                    if (isWeekEnd && em.getWorkType() == 0) {
                                                                        otw.setaOnRemark("周六加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setaOnRemark("正常");
                                                                        otw.setClockResult(1);
                                                                    }
                                                                } else if (type == 1) {
                                                                    if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                        otw.setaOnRemark("周末加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setaOnRemark("周末不上班");
                                                                        otw.setClockResult(5);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            kqBeanList.add(aaa);
                                                            return kqBeanList;
                                                        }
                                                    } else {
                                                        kqBeanList.add(aaa);
                                                        return kqBeanList;
                                                    }
                                                    if (workSet.getMorningOff() != null) {
                                                        if (workSet.getMorningOffFrom() != null && workSet.getMorningOffEnd() != null) {
                                                            if (time.after(workSet.getMorningOffFrom()) && !time.after(workSet.getMorningOffEnd()) || (time.equals(workSet.getMorningOff()))) {
                                                                aoff = true;
                                                                otw.setaOffTime(time.toString());
                                                                if (type == 0) {
                                                                    if (isWeekEnd && em.getWorkType() == 0) {
                                                                        otw.setaOffRemark("周六加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setaOffRemark("正常");
                                                                        otw.setClockResult(1);
                                                                    }
                                                                } else if (type == 1) {
                                                                    if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                        otw.setaOffRemark("周末加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setaOffRemark("周末不上班");
                                                                        otw.setClockResult(5);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            aoff = true;
                                                        }
                                                    } else {
                                                        aoff = true;
                                                    }
                                                    if (workSet.getNoonOn() != null) {
                                                        if (workSet.getNoonOnFrom() != null && workSet.getNoonOnEnd() != null) {
                                                            if (time.after(workSet.getNoonOnFrom()) && !time.after(workSet.getNoonOnEnd()) || (time.equals(workSet.getNoonOn()))) {
                                                                pon = true;
                                                                otw.setpOnTime(time.toString());
                                                                if (type == 0) {
                                                                    if (isWeekEnd && em.getWorkType() == 0) {
                                                                        otw.setpOnRemark("周六加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setpOnRemark("正常");
                                                                        otw.setClockResult(1);
                                                                    }
                                                                } else if (type == 1) {
                                                                    if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                        otw.setpOnRemark("周末加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setpOnRemark("周末不上班");
                                                                        otw.setClockResult(5);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            pon = true;
                                                        }
                                                    } else {
                                                        pon = true;
                                                    }
                                                    if (workSet.getNoonOff() != null) {
                                                        if (workSet.getNoonOffFrom() != null) {
                                                            if (time.after(workSet.getNoonOffFrom()) || (time.equals(workSet.getNoonOff()))) {
                                                                poff = true;
                                                                otw.setpOffTime(time.toString());
                                                                if (type == 0) {
                                                                    if (isWeekEnd && em.getWorkType() == 0) {
                                                                        otw.setpOffRemark("周六加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setpOffRemark("正常");
                                                                        otw.setClockResult(1);
                                                                    }
                                                                } else if (type == 1) {
                                                                    if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                        otw.setpOffRemark("周末加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setpOffRemark("周末不上班");
                                                                        otw.setClockResult(5);
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            poff = true;
                                                        }
                                                    } else {
                                                        poff = true;
                                                    }
                                                    Double extHours = 0.0;
                                                    abcd = null;
                                                    allNum = null;
                                                    intNum = "";
                                                    deciNum = "";
                                                    if (workSet.getExtworkon() != null) {
                                                        Time lastT = timeList.get(timeList.size() - 1);
                                                        QianKa qqq = personMapper.getQianKaByDateAndEmpnoA(otw.getName(), otw.getDateStr());
                                                        String[] timeStrs = null;
                                                        if (qqq != null && qqq.getTimeStr() != null) {
                                                            timeStrs = qqq.getTimeStr().trim().split(" ");
                                                            if (timeStrs != null || timeStrs.length > 0) {
                                                                lastT = StringUtil.returnLargeTime(timeList.get(timeList.size() - 1), timeStrs[timeStrs.length - 1]);
                                                            }
                                                        }
                                                        //Time lastT = timeList.get(timeList.size() - 1);
                                                        if (lastT.after(workSet.getExtworkon())) {
                                                            if (lastT.after(workSet.getExtworkoff())) {
                                                                lastT = workSet.getExtworkoff();
                                                            }
                                                            Long abc = lastT.getTime() - workSet.getExtworkon().getTime();
                                                            extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                            abcd = String.format("%.1f", extHours);
                                                            if (abcd != null && !extHours.equals("0.0")) {
                                                                String ccc = String.valueOf(abcd);
                                                                allNum = String.valueOf(abcd).split("\\.");
                                                                intNum = allNum[0];
                                                                deciNum = allNum[1];
                                                                if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                    deciNum = "0";
                                                                } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                    deciNum = "5";
                                                                }
                                                                extHours = Double.valueOf(intNum + "." + deciNum);
                                                            }
                                                            otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                            otw.setExtWorkOffTime(lastT.toString());
                                                            if (extHours >= 0) {
                                                                otw.setExtWorkHours(extHours);
                                                            }
                                                        }

                                                        if (extHours <= 0) {
                                                            DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                                            Date d2400 = dft.parse(co.getDateStr() + " " + "24:00:00");
                                                            Date d0000 = dft.parse(co.getDateStr() + " " + "00:00:00");
                                                            Date dd = format.parse("05:00:00");
                                                            String dateTomor = DateUtil.getAfterDay(co.getDateStr());
                                                            Time sam = new java.sql.Time(dd.getTime());
                                                            if (otw.getWeek().intValue() == 5 || otw.getWeek() == 6 || otw.getWeek().intValue() == 7) {
                                                                co2 = personMapper.getZhongKongBeanByNumAndDate(co.getEnrollNumber(), dateTomor);
                                                                if (co2 != null && co2.getTimeStr() != null && co2.getTimeStr().length() > 0) {
                                                                    String afterTime = dateTomor + " " + co2.getTimeStr().split(" ")[0];
                                                                    String beforeTime = co.getDateStr() + " " + workSet.getExtworkon();
                                                                    Date afterT = dft.parse(afterTime);
                                                                    Date beforTime = dft.parse(beforeTime);
                                                                    String afterTimem = co2.getTimeStr().split(" ")[0];
                                                                    Date d = format.parse(afterTimem);
                                                                    Time tii = new java.sql.Time(d.getTime());
                                                                    if (tii.before(sam)) {
                                                                        Long abc = d2400.getTime() - beforTime.getTime();
                                                                        extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                        abcd = String.format("%.1f", extHours);
                                                                        if (abcd != null && !extHours.equals("0.0")) {
                                                                            String ccc = String.valueOf(abcd);
                                                                            allNum = String.valueOf(abcd).split("\\.");
                                                                            intNum = allNum[0];
                                                                            deciNum = allNum[1];
                                                                            if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                                deciNum = "0";
                                                                            } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                                deciNum = "5";
                                                                            }
                                                                            extHours = Double.valueOf(intNum + "." + deciNum);
                                                                        }
                                                                        otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                        otw.setExtWorkOffTime(lastT.toString());
                                                                        if (extHours >= 0) {
                                                                            otw.setExtWorkHours(extHours);
                                                                        }
                                                                    }
                                                                }

                                                                if (extHours <= 0) {
                                                                    String firstTimeStr = co.getTimeStr().split(" ")[0];
                                                                    Date d = format.parse(firstTimeStr);
                                                                    Time tii = new java.sql.Time(d.getTime());
                                                                    if (tii.before(sam)) {
                                                                        Long abc = d.getTime() - d0000.getTime();
                                                                        extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                        abcd = String.format("%.1f", extHours);
                                                                        if (abcd != null && !extHours.equals("0.0")) {
                                                                            String ccc = String.valueOf(abcd);
                                                                            allNum = String.valueOf(abcd).split("\\.");
                                                                            intNum = allNum[0];
                                                                            deciNum = allNum[1];
                                                                            if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                                deciNum = "0";
                                                                            } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                                deciNum = "5";
                                                                            }
                                                                            extHours = Double.valueOf(intNum + "." + deciNum);
                                                                        }
                                                                        otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                        otw.setExtWorkOffTime(lastT.toString());
                                                                        if (extHours >= 0) {
                                                                            otw.setExtWorkHours(extHours);
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                co2 = personMapper.getZhongKongBeanByNumAndDate(co.getEnrollNumber(), dateTomor);
                                                                if (co2 != null && co2.getTimeStr() != null && co2.getTimeStr().length() > 0) {
                                                                    String afterTime = dateTomor + " " + co2.getTimeStr().split(" ")[0];
                                                                    String beforeTime = co.getDateStr() + " " + workSet.getExtworkon();
                                                                    Date afterT = dft.parse(afterTime);
                                                                    Date beforTime = dft.parse(beforeTime);
                                                                    String afterTimem = co2.getTimeStr().split(" ")[0];
                                                                    Date d = format.parse(afterTimem);
                                                                    Time tii = new java.sql.Time(d.getTime());
                                                                    if (tii.before(sam)) {
                                                                        Long abc = afterT.getTime() - beforTime.getTime();
                                                                        extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                        abcd = String.format("%.1f", extHours);
                                                                        if (abcd != null && !extHours.equals("0.0")) {
                                                                            String ccc = String.valueOf(abcd);
                                                                            allNum = String.valueOf(abcd).split("\\.");
                                                                            intNum = allNum[0];
                                                                            deciNum = allNum[1];
                                                                            if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                                deciNum = "0";
                                                                            } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                                deciNum = "5";
                                                                            }
                                                                            extHours = Double.valueOf(intNum + "." + deciNum);
                                                                        }
                                                                        otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                        otw.setExtWorkOffTime(lastT.toString());
                                                                        if (extHours >= 0) {
                                                                            otw.setExtWorkHours(extHours);
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                }

                                                if (!aon) {
                                                    Leave leave = personMapper.getLeaveByEmIdAndMonthALinShi(em.getName(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                                    if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setaOnRemark("正常请假");
                                                                otw.setRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setaOnRemark("因公外出");
                                                                otw.setRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            }
                                                        }
                                                    } else {
                                                        boolean isAOnH = false;
                                                        a:
                                                        for (Time tii : timeList) {
                                                            if (tii.after(afterWS.getMorningOn()) && !tii.after(afterWS.getMorningOff())) {
                                                                if (pon) {
                                                                    aHours = DateUtil.calcuHours(tii, workSet.getMorningOff());
                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tii, em.getName());
                                                                    if (le != null) {
                                                                        clockRes = 16;
                                                                    }
                                                                    break a;
                                                                } else {
                                                                    if (pon || poff) {
                                                                        aHours = DateUtil.calcuHours(tii, workSet.getMorningOff());
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tii, em.getName());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                            if (timeList.get(tiii).after(afterWS.getMorningOn()) && !timeList.get(tiii).after(afterWS.getMorningOff())) {
                                                                                aHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                isAOnH = true;
                                                                                Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tii, em.getName());
                                                                                if (le != null) {
                                                                                    clockRes = 16;
                                                                                }
                                                                                break a;
                                                                            }
                                                                        }

                                                                        if (!isAOnH) {
                                                                            for (Time tiii : timeList) {
                                                                                if (tiii.after(afterWS.getMorningOn())) {
                                                                                    aHours = DateUtil.calcuHours(tiii, workSet.getMorningOff());
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tiii, em.getName());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (aHours != 0.0) {
                                                            if (em.getWorkType() == 1) {
                                                                aHours += 0.5;
                                                            }
                                                            otw.setaOnRemark(aHours.toString());
                                                            otw.setRemark(aHours.toString() + "," + pHours.toString());
                                                            otw.setClockResult(clockRes);
                                                        } else {
                                                            otw.setaOnRemark("上午上班");
                                                            otw.setRemark("打卡时间不在规定时间范围内");
                                                            otw.setClockResult(7);
                                                        }
                                                    }

                                                }
                                                if (!aoff) {
                                                    Leave leave = personMapper.getLeaveByEmIdAndMonthALinShi(em.getName(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                                    if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setaOnRemark("正常请假");
                                                                otw.setRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setaOffRemark("因公外出");
                                                                otw.setRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            }
                                                        }
                                                    } else {
                                                        boolean isAOnH = false;
                                                        a:
                                                        for (Time tii : timeList) {
                                                            if (tii.after(afterWS.getMorningOn()) && !tii.after(afterWS.getMorningOff())) {
                                                                if (aon) {
                                                                    aHours = DateUtil.calcuHours(workSet.getMorningOn(), tii);
                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getName());
                                                                    if (le != null) {
                                                                        clockRes = 16;
                                                                    }
                                                                    break a;
                                                                } else {
                                                                    if (pon || poff) {
                                                                        aHours = DateUtil.calcuHours(workSet.getMorningOn(), tii);
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getName());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                            if (timeList.get(tiii).after(afterWS.getMorningOn()) && !timeList.get(tiii).after(afterWS.getMorningOff())) {
                                                                                aHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                isAOnH = true;
                                                                                Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + timeList.get(tiii), yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getName());
                                                                                if (le != null) {
                                                                                    clockRes = 16;
                                                                                }
                                                                                break a;
                                                                            }
                                                                        }

                                                                        if (!isAOnH) {
                                                                            for (Time tiii : timeList) {
                                                                                if (tiii.after(afterWS.getMorningOn())) {
                                                                                    aHours = DateUtil.calcuHours(tiii, workSet.getMorningOff());
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tiii, em.getName());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (aHours != 0.0) {
                                                            if (em.getWorkType() == 1) {
                                                                aHours += 0.5;
                                                            }
                                                            otw.setaOnRemark(aHours.toString());
                                                            otw.setRemark(aHours.toString() + "," + pHours.toString());
                                                            otw.setClockResult(clockRes);
                                                        } else {
                                                            if (((aon ? 1 : 0) + (aoff ? 1 : 0) + (pon ? 1 : 0) + (poff ? 1 : 0)) <= 1) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else if (!aon && !aoff) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else {
                                                                otw.setaOffRemark("上午下班");
                                                                otw.setRemark("打卡时间不在规定时间范围内");
                                                                otw.setClockResult(7);
                                                            }
                                                        }
                                                    }
                                                }

                                                if (!pon) {
                                                    Leave leave = personMapper.getLeaveByEmIdAndMonthALinShi(em.getName(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                                    if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setpOnRemark("正常请假");
                                                                otw.setRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setpOnRemark("因公外出");
                                                                otw.setRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            }
                                                        }
                                                    } else {
                                                        boolean isAOnH = false;
                                                        a:
                                                        for (Time tii : timeList) {
                                                            if (tii.after(afterWS.getNoonOn()) && !tii.after(afterWS.getNoonOff())) {
                                                                if (poff) {
                                                                    pHours = DateUtil.calcuHours(tii, workSet.getNoonOff());
                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tii, em.getName());
                                                                    if (le != null) {
                                                                        clockRes = 16;
                                                                    }
                                                                    break a;
                                                                } else {
                                                                    if (aon || aoff) {
                                                                        pHours = DateUtil.calcuHours(tii, workSet.getNoonOff());
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tii, em.getName());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                            if (timeList.get(tiii).after(afterWS.getNoonOn()) && !timeList.get(tiii).after(afterWS.getNoonOff())) {
                                                                                pHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                isAOnH = true;
                                                                                Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tii, em.getName());
                                                                                if (le != null) {
                                                                                    clockRes = 16;
                                                                                }
                                                                                break a;
                                                                            }
                                                                        }
                                                                        if (!isAOnH) {
                                                                            for (Time tiii : timeList) {
                                                                                if (tiii.after(afterWS.getNoonOn())) {
                                                                                    pHours = DateUtil.calcuHours(tiii, workSet.getNoonOff());
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tiii, em.getName());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (pHours != 0.0) {
                                                            otw.setaOnRemark(pHours.toString());
                                                            otw.setRemark(aHours + "," + pHours.toString());
                                                            otw.setClockResult(clockRes);
                                                        } else {
                                                            otw.setpOnRemark("下午上班");
                                                            otw.setRemark("打卡时间不在规定时间范围内");
                                                            otw.setClockResult(7);
                                                        }
                                                    }
                                                }

                                                if (!poff) {
                                                    Leave leave = personMapper.getLeaveByEmIdAndMonthALinShi(em.getName(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                                    if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setpOffRemark("正常请假");
                                                                otw.setRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setpOffRemark("因公外出");
                                                                otw.setRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            }
                                                        }
                                                    } else {
                                                        boolean isAOnH = false;
                                                        a:
                                                        for (Time tii : timeList) {
                                                            if (tii.after(afterWS.getNoonOn()) && !tii.after(afterWS.getNoonOff())) {
                                                                if (pon) {
                                                                    pHours = DateUtil.calcuHours(workSet.getNoonOn(), tii);
                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getName());
                                                                    if (le != null) {
                                                                        clockRes = 16;
                                                                    }
                                                                    break a;
                                                                } else {
                                                                    if (aon || aoff) {
                                                                        pHours = DateUtil.calcuHours(workSet.getNoonOn(), tii);
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getName());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                            if (timeList.get(tiii).after(afterWS.getNoonOn()) && !timeList.get(tiii).after(afterWS.getNoonOff())) {
                                                                                pHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                isAOnH = true;
                                                                                Leave le = personMapper.getLeaveByEmpIdAndDateStrLinShi(yearMonth + "-" + date + " " + timeList.get(tiii), yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getName());
                                                                                if (le != null) {
                                                                                    clockRes = 16;
                                                                                }
                                                                                break a;
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (pHours != 0.0) {
                                                            otw.setaOnRemark(pHours.toString());
                                                            otw.setRemark(aHours + "," + pHours.toString());
                                                            otw.setClockResult(clockRes);
                                                        } else {
                                                            if (((aon ? 1 : 0) + (aoff ? 1 : 0) + (pon ? 1 : 0) + (poff ? 1 : 0)) <= 1) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else if (!pon && !poff) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else {
                                                                otw.setpOffRemark("下午下班");
                                                                otw.setRemark("打卡时间不在规定时间范围内");
                                                                otw.setClockResult(7);
                                                            }
                                                        }
                                                    }
                                                }
                                                int rightclocknum = (aon == true ? 1 : 0) + (aoff == true ? 1 : 0) + (pon == true ? 1 : 0) + (poff == true ? 1 : 0);
                                                if (rightclocknum < 2) {
                                                    if (DateUtil.getWeek(kqBeans.get(k).getDateStr()) == 6 || DateUtil.getWeek(kqBeans.get(k).getDateStr()) == 7) {
                                                        QianKa qqk = personMapper.getQianKaByDateAndEmpnoA(otw.getEmpNo(), otw.getDateStr());
                                                        if (qqk != null) {
                                                            otw.setRemark("打卡时间不在规定时间内");
                                                            otw.setClockResult(7);
                                                        } else {
                                                            otw.setRemark("放假");
                                                            otw.setClockResult(5);
                                                        }
                                                    } else {
                                                        Leave a = personMapper.getLeaveByEmIdAndMonthABLinShi(otw.getEmpNo(), otw.getDateStr());
                                                        if (a != null) {
                                                            //otw.setClockResult(16);
                                                        } else {
                                                            otw.setaOnRemark(pHours.toString());
                                                            otw.setRemark(aHours + "," + pHours);
                                                            otw.setClockResult(clockRes);
                                                        }
                                                    }
                                                }

                                                Double extHours = 0.0;
                                                DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                                Date d2400 = dft.parse(co.getDateStr() + " " + "24:00:00");
                                                Date d0000 = dft.parse(co.getDateStr() + " " + "00:00:00");
                                                Date dd = format.parse("05:00:00");
                                                String dateTomor = DateUtil.getAfterDay(co.getDateStr());
                                                Time sam = new java.sql.Time(dd.getTime());
                                                String firstTimeStr = co.getTimeStr().split(" ")[0];
                                                Date d = format.parse(firstTimeStr);
                                                Date df = dft.parse(co.getDateStr() + " " + firstTimeStr);
                                                Time tii = new java.sql.Time(d.getTime());
                                                if (tii.before(sam)) {
                                                    Long abc = df.getTime() - d0000.getTime();
                                                    extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                    abcd = String.format("%.1f", extHours);
                                                    if (abcd != null && !extHours.equals("0.0")) {
                                                        String ccc = String.valueOf(abcd);
                                                        allNum = String.valueOf(abcd).split("\\.");
                                                        intNum = allNum[0];
                                                        deciNum = allNum[1];
                                                        if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                            deciNum = "0";
                                                        } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                            deciNum = "5";
                                                        }
                                                        extHours = Double.valueOf(intNum + "." + deciNum);
                                                    }
                                                    otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                    otw.setExtWorkOffTime(firstTimeStr.toString());
                                                    if (extHours >= 0) {
                                                        otw.setExtWorkHours((otw.getExtWorkHours() == null ? 0.0 : otw.getExtWorkHours()) + extHours);
                                                    }
                                                }

                                            } else {
                                                Leave leave = personMapper.getLeaveByEmIdAndMonthLinShi(em.getName(), yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + workSet.getNoonOff());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setRemark("正常请假");
                                                        otw.setClockResult(6);
                                                    } else if (leave.getType() == 1) {
                                                        outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                        if (outClockIn != null) {
                                                            outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                        }
                                                        otw.setTimeStr(outtimeStr);
                                                        otw.setRemark("因公外出");
                                                        otw.setClockResult(2);
                                                    }
                                                } else {
                                                    if (type == 0) {
                                                        otw.setRemark("不上班");
                                                        otw.setClockResult(5);
                                                    } else if (type == 1) {
                                                        otw.setRemark("周末不上班");
                                                        otw.setClockResult(5);
                                                    }
                                                }
                                                leave = personMapper.getLeaveByEmIdAndMonthALinShi(em.getName(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setaOnRemark("正常请假");
                                                        otw.setRemark("正常请假");
                                                        otw.setClockResult(6);
                                                    } else if (leave.getType() == 1) {
                                                        outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                        if (outClockIn != null) {
                                                            outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                        }
                                                        otw.setTimeStr(outtimeStr);
                                                        otw.setaOnRemark("因公外出");
                                                        otw.setRemark("因公外出");
                                                        otw.setClockResult(2);
                                                    }
                                                } else {
                                                    if (type == 0) {
                                                        otw.setaOnRemark("不上班");
                                                        otw.setRemark("不上班");
                                                        otw.setClockResult(5);
                                                    } else if (type == 1) {
                                                        otw.setaOnRemark("周末不上班");
                                                        otw.setRemark("周末不上班");
                                                        otw.setClockResult(5);
                                                    }
                                                }
                                                if (workSet.getMorningOff() != null) {
                                                    leave = personMapper.getLeaveByEmIdAndMonthALinShi(em.getName(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                                    if (leave != null) {
                                                        if (leave.getType() == 0) {
                                                            otw.setRemark("正常请假");
                                                            otw.setaOffRemark("正常请假");
                                                            otw.setClockResult(6);
                                                        } else if (leave.getType() == 1) {
                                                            outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                            if (outClockIn != null) {
                                                                outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                            }
                                                            otw.setTimeStr(outtimeStr);
                                                            otw.setRemark("因公外出");
                                                            otw.setaOffRemark("因公外出");
                                                            otw.setClockResult(2);
                                                        }
                                                    } else {
                                                        if (type == 0) {
                                                            otw.setaOffRemark("不上班");
                                                            otw.setRemark("不上班");
                                                            otw.setClockResult(5);
                                                        } else if (type == 1) {
                                                            otw.setaOffRemark("周末不上班");
                                                            otw.setRemark("周末不上班");
                                                            otw.setClockResult(5);
                                                        }
                                                    }
                                                }
                                                if (workSet.getNoonOn() != null) {
                                                    leave = personMapper.getLeaveByEmIdAndMonthALinShi(em.getName(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                                    if (leave != null) {
                                                        if (leave.getType() == 0) {
                                                            otw.setRemark("正常请假");
                                                            otw.setpOnRemark("正常请假");
                                                            otw.setClockResult(6);
                                                        } else if (leave.getType() == 1) {
                                                            outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                            if (outClockIn != null) {
                                                                outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                            }
                                                            otw.setTimeStr(outtimeStr);
                                                            otw.setRemark("因公外出");
                                                            otw.setpOnRemark("因公外出");
                                                            otw.setClockResult(2);
                                                        }
                                                    } else {
                                                        if (type == 0) {
                                                            otw.setpOnRemark("不上班");
                                                            otw.setRemark("不上班");
                                                            otw.setClockResult(5);
                                                        } else if (type == 1) {
                                                            otw.setpOnRemark("周末不上班");
                                                            otw.setRemark("周末不上班");
                                                            otw.setClockResult(5);
                                                        }
                                                    }
                                                }
                                                leave = personMapper.getLeaveByEmIdAndMonthALinShi(em.getName(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setpOffRemark("正常请假");
                                                        otw.setRemark("正常请假");
                                                        otw.setClockResult(6);
                                                    } else if (leave.getType() == 1) {
                                                        outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                        if (outClockIn != null) {
                                                            outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                        }
                                                        otw.setTimeStr(outtimeStr);
                                                        otw.setpOffRemark("因公外出");
                                                        otw.setRemark("因公外出");
                                                        otw.setClockResult(2);
                                                    }
                                                } else {
                                                    if (type == 0) {
                                                        otw.setpOffRemark("不上班");
                                                        otw.setRemark("不上班");
                                                        otw.setClockResult(5);
                                                    } else if (type == 1) {
                                                        otw.setpOffRemark("周末不上班");
                                                        otw.setRemark("周末不上班");
                                                        otw.setClockResult(5);
                                                    }
                                                }
                                            }
                                        } else {
                                            otw.setRemark("没有此人打卡记录");
                                            otw.setClockResult(10);
                                        }
                                        if (otw.getName() != null && otw.getName().trim().length() > 0) {
                                            if (otw.getRemark() == null) {
                                                otw.setRemark("正常");
                                                otw.setClockResult(1);
                                            }
                                            kqBeanList.add(otw);
                                        }
                                    }
                                }
                            }

                        }
                    }
                } else {
                    kqBeanList.add(aaa);
                    return kqBeanList;
                }
            }
        }
        return kqBeanList;
    }

    @Transactional
    public List<KQBean> getAfterOperatorDataByOriginData(List<OutClockIn> clockDates, List<KQBean> kqBeans) throws Exception {
        List<KQBean> kqBeanList = new ArrayList<KQBean>();
        KQBean aaa = new KQBean();
        List<Employee> employeeList = personMapper.findAllEmployeeAll();
        String monstr = "";
        String yearMonth = kqBeans.get(0).getYearMonth();
        Employee em = null;
        KQBean co = null;
        ZhongKongBean co2 = null;
        int emLen = employeeList.size();
        Integer date = null;
        int clLen = kqBeans.size();
        WorkSet workSet = null;
        WorkSet afterWS = null;
        List<WorkDate> workDateList = null;
        boolean aon = false;
        boolean aoff = false;
        boolean pon = false;
        boolean poff = false;
        KQBean otw = null;
        OutClockIn outClockIn = null;
        String outtimeStr = "";
        YeBan yb = null;
        String abcd = null;
        String[] allNum = null;
        String intNum = "";
        String deciNum = "";
        Double aHours = 0.0;
        Double pHours = 0.0;
        Integer clockRes = 17;
        Double linchenJiaBan = 0.0;
        SimpleDateFormat dft2 = new SimpleDateFormat("yyyy-MM-dd");

        DaKaPianCha dkpc = personMapper.getDaKaPianCha();
        String dateStrrrr = null;
        for (int i = 0; i < emLen; i++) {
            em = employeeList.get(i);
            workDateList = personMapper.getWorkDateByMonthAnPositionLevelList(yearMonth, em.getPositionLevel());
            for (WorkDate workDate : workDateList) {
                int type = workDate.getType();
                if (workDate != null) {
                    for (int j = 0; j < workDate.getWorkDatess().length; j++) {
                        date = Integer.valueOf(workDate.getWorkDatess()[j]);
                        boolean isWeekEnd = DateUtil.isWeekend(yearMonth + "-" + date);
                        dateStrrrr = yearMonth + "-" + date;
                        if (dateStrrrr != null) {
                            if (dft2.parse(dateStrrrr).before(dft2.parse(em.getIncompdateStr())))
                                continue;
                        }
                        for (int k = 0; k < clLen; k++) {
                            aon = false;
                            aoff = false;
                            pon = false;
                            poff = false;
                            aHours = 0.0;
                            pHours = 0.0;
                            clockRes = 17;
                            linchenJiaBan = 0.0;
                            co = kqBeans.get(k);
                            String[] coDay = co.getDateStr().split("-");
                            if (em.getName().equals(co.getName()) && date.equals(Integer.valueOf(coDay[2]))) {
                                if (workDate.getEmpNostr() == null || workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                    otw = new KQBean();
                                    otw.setName(em.getName());
                                    otw.setYearMonth(yearMonth + "-" + date);
                                    otw.setEmpNo(em.getEmpNo());
                                    otw.setDeptName(em.getDeptName());
                                    otw.setYearMonth(yearMonth);
                                    otw.setWeek(DateUtil.getWeek(kqBeans.get(k).getDateStr()));
                                    otw.setEnrollNumber(kqBeans.get(k).getEnrollNumber());
                                    otw.setDateStr(kqBeans.get(k).getDateStr());
                                    workSet = personMapper.getWorkSetByMonthAndPositionLevel2(yearMonth, em.getPositionLevel());
                                    afterWS = StringUtil.plusPianCha(workSet, dkpc);
                                    Time time = null;

                                    yb = personMapper.getYeBanByEmpNoAndDateStrNew(co.getEmpNo(), co.getDateStr());
                                    if (yb != null) {
                                        Double zhengBanHours = 0.0;
                                        Double extHourss = 0.0;
                                        String remark = "";
                                        List<Time> timeListYe = getTimeList(co.getTimeStr());
                                        for (Time tim : timeListYe) {
                                            if (tim.before(workSet.getMorningOnEnd()) && tim.after(workSet.getMorningOnFrom())) {
                                                aon = true;
                                            }
                                            if (tim.before(workSet.getMorningOffEnd()) && tim.after(workSet.getMorningOffFrom())) {
                                                aoff = true;
                                            }
                                            if (tim.before(workSet.getNoonOnEnd()) && tim.after(workSet.getNoonOnFrom())) {
                                                pon = true;
                                            }
                                            if (tim.before(workSet.getNoonOffEnd()) && tim.after(workSet.getNoonOffFrom())) {
                                                poff = true;
                                            }

                                        }

                                        if (aon && (aoff || pon || poff)) {
                                            zhengBanHours = 4.0;
                                            Time offTime = null;
                                            for (int aa = timeListYe.size() - 1; aa >= 0; aa--) {
                                                if (timeListYe.get(aa).before(workSet.getNoonOffEnd())) {
                                                    offTime = timeListYe.get(aa);
                                                    break;
                                                }
                                            }
                                            if (offTime != null) {
                                                Long abc = offTime.getTime() - workSet.getNoonOn().getTime();
                                                extHourss = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                abcd = String.format("%.1f", extHourss);
                                                if (abcd != null && !extHourss.equals("0.0")) {
                                                    String ccc = String.valueOf(abcd);
                                                    allNum = String.valueOf(abcd).split("\\.");
                                                    intNum = allNum[0];
                                                    deciNum = allNum[1];
                                                    if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                        deciNum = "0";
                                                    } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                        deciNum = "5";
                                                    }
                                                    zhengBanHours += Double.valueOf(intNum + "." + deciNum);
                                                }
                                            }
                                        }

                                        Time yeBanStar = timeListYe.get(timeListYe.size() - 1);
                                        Time yeBanEnd = null;
                                        String day2 = DateUtil.getAfterDay(co.getDateStr());
                                        ZhongKongBean timeStrDy2 = personMapper.getZKBeanByEmpNoAndDateStr(co.getEmpNo(), day2);
                                        if (timeStrDy2 != null && timeStrDy2.getTimeStr() != null && timeStrDy2.getTimeStr().trim().length() > 0) {
                                            List<Time> timeList2 = getTimeList(timeStrDy2.getTimeStr());
                                            yeBanEnd = timeList2.get(0);
                                        }

                                        if (yeBanEnd != null) {
                                            String dayTimeBefor = co.getDateStr() + " " + yeBanStar;
                                            String dayTimeAfter = day2 + " " + yeBanEnd;
                                            DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            Date dayBeF = dft.parse(dayTimeBefor);
                                            Date dayAfT = dft.parse(dayTimeAfter);
                                            Long abc = dayAfT.getTime() - dayBeF.getTime();
                                            extHourss = ((abc.doubleValue()) / (1000 * 60 * 60));
                                            abcd = String.format("%.1f", extHourss);
                                            if (abcd != null && !extHourss.equals("0.0")) {
                                                String ccc = String.valueOf(abcd);
                                                allNum = String.valueOf(abcd).split("\\.");
                                                intNum = allNum[0];
                                                deciNum = allNum[1];
                                                if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                    deciNum = "0";
                                                } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                    deciNum = "5";
                                                }
                                                zhengBanHours += Double.valueOf(intNum + "." + deciNum);
                                            }
                                        }
                                        otw.setClockResult(13);
                                        otw.setExtWorkHours(zhengBanHours);
                                        otw.setTimeStr(co.getTimeStr());
                                        kqBeanList.add(otw);
                                    } else {
                                        if (workDate.getType() == 2) {
                                            otw.setRemark("法定假日");
                                            otw.setClockResult(4);
                                            otw.setaOnRemark("法定假日");
                                            otw.setClockResult(4);
                                            otw.setaOffRemark("法定假日");
                                            otw.setClockResult(4);
                                            otw.setpOnRemark("法定假日");
                                            otw.setClockResult(4);
                                            otw.setpOffRemark("法定假日");
                                            otw.setClockResult(4);
                                            kqBeanList.add(otw);
                                            continue;
                                        }
                                        if (workSet != null) {
                                            if (kqBeans.get(k).getTimeStr() != null && kqBeans.get(k).getTimeStr().length() > 0) {
                                                otw.setTimeStr(kqBeans.get(k).getTimeStr());
                                                List<Time> timeList = this.getTimeList(kqBeans.get(k).getTimeStr());
                                                for (int a = 0; a < timeList.size(); a++) {
                                                    time = timeList.get(a);
                                                    if (workSet.getMorningOn() != null) {
                                                        if (workSet.getMorningOnFrom() != null && workSet.getMorningOnEnd() != null) {
                                                            if (time.after(workSet.getMorningOnFrom()) && !time.after(workSet.getMorningOnEnd()) || (time.equals(workSet.getMorningOn()))) {
                                                                aon = true;
                                                                otw.setaOnTime(time.toString());
                                                                if (type == 0) {
                                                                    if (isWeekEnd && em.getWorkType() == 0) {
                                                                        otw.setaOnRemark("周六加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setaOnRemark("正常");
                                                                        otw.setClockResult(1);
                                                                    }
                                                                } else if (type == 1) {
                                                                    if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                        otw.setaOnRemark("周末加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setaOnRemark("周末不上班");
                                                                        otw.setClockResult(5);
                                                                    }
                                                                } else if (type == 2) {
                                                                    otw.setaOnRemark("法定假日加班");
                                                                    otw.setClockResult(9);
                                                                }
                                                            }
                                                        } else {
                                                            kqBeanList.add(aaa);
                                                            return kqBeanList;
                                                        }
                                                    } else {
                                                        kqBeanList.add(aaa);
                                                        return kqBeanList;
                                                    }
                                                    if (workSet.getMorningOff() != null) {
                                                        if (workSet.getMorningOffFrom() != null && workSet.getMorningOffEnd() != null) {
                                                            if (time.after(workSet.getMorningOffFrom()) && !time.after(workSet.getMorningOffEnd()) || (time.equals(workSet.getMorningOff()))) {
                                                                aoff = true;
                                                                otw.setaOffTime(time.toString());
                                                                if (type == 0) {
                                                                    if (isWeekEnd && em.getWorkType() == 0) {
                                                                        otw.setaOffRemark("周六加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setaOffRemark("正常");
                                                                        otw.setClockResult(1);
                                                                    }
                                                                } else if (type == 1) {
                                                                    if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                        otw.setaOffRemark("周末加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setaOffRemark("周末不上班");
                                                                        otw.setClockResult(5);
                                                                    }
                                                                } else if (type == 2) {
                                                                    otw.setaOffRemark("法定假日加班");
                                                                    otw.setClockResult(9);
                                                                }
                                                            }
                                                        } else {
                                                            aoff = true;
                                                        }
                                                    } else {
                                                        aoff = true;
                                                    }
                                                    if (workSet.getNoonOn() != null) {
                                                        if (workSet.getNoonOnFrom() != null && workSet.getNoonOnEnd() != null) {
                                                            if (time.after(workSet.getNoonOnFrom()) && !time.after(workSet.getNoonOnEnd()) || (time.equals(workSet.getNoonOn()))) {
                                                                pon = true;
                                                                otw.setpOnTime(time.toString());
                                                                if (type == 0) {
                                                                    if (isWeekEnd && em.getWorkType() == 0) {
                                                                        otw.setpOnRemark("周六加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setpOnRemark("正常");
                                                                        otw.setClockResult(1);
                                                                    }
                                                                } else if (type == 1) {
                                                                    if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                        otw.setpOnRemark("周末加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setpOnRemark("周末不上班");
                                                                        otw.setClockResult(5);
                                                                    }
                                                                } else if (type == 2) {
                                                                    otw.setpOnRemark("法定假日加班");
                                                                    otw.setClockResult(9);
                                                                }
                                                            }
                                                        } else {
                                                            pon = true;
                                                        }
                                                    } else {
                                                        pon = true;
                                                    }
                                                    if (workSet.getNoonOff() != null) {
                                                        if (workSet.getNoonOffFrom() != null) {
                                                            if (time.after(workSet.getNoonOffFrom()) || (time.equals(workSet.getNoonOff()))) {
                                                                poff = true;
                                                                otw.setpOffTime(time.toString());
                                                                if (type == 0) {
                                                                    if (isWeekEnd && em.getWorkType() == 0) {
                                                                        otw.setpOffRemark("周六加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setpOffRemark("正常");
                                                                        otw.setClockResult(1);
                                                                    }
                                                                } else if (type == 1) {
                                                                    if (workDate.getEmpNostr() != null && workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                        otw.setpOffRemark("周末加班");
                                                                        otw.setClockResult(1);
                                                                    } else {
                                                                        otw.setpOffRemark("周末不上班");
                                                                        otw.setClockResult(5);
                                                                    }
                                                                } else if (type == 2) {
                                                                    otw.setpOffRemark("法定假日加班");
                                                                    otw.setClockResult(9);
                                                                }
                                                            }
                                                        } else {
                                                            poff = true;
                                                        }
                                                    } else {
                                                        poff = true;
                                                    }
                                                    Double extHours = 0.0;
                                                    abcd = null;
                                                    allNum = null;
                                                    intNum = "";
                                                    deciNum = "";
                                                    if (workSet.getExtworkon() != null) {
                                                        Time lastT = timeList.get(timeList.size() - 1);
                                                        QianKa qqq = personMapper.getQianKaByDateAndEmpnoA(otw.getEmpNo(), otw.getDateStr());
                                                        String[] timeStrs = null;
                                                        if (qqq != null && qqq.getTimeStr() != null) {
                                                            timeStrs = qqq.getTimeStr().trim().split(" ");
                                                            if (timeStrs != null || timeStrs.length > 0) {
                                                                lastT = StringUtil.returnLargeTime(timeList.get(timeList.size() - 1), timeStrs[timeStrs.length - 1]);
                                                            }
                                                        }
                                                        // Time lastT = timeList.get(timeList.size() - 1);
                                                        if (lastT.after(workSet.getExtworkon())) {
                                                            if (lastT.after(workSet.getExtworkoff())) {
                                                                lastT = workSet.getExtworkoff();
                                                            }
                                                            Long abc = lastT.getTime() - workSet.getExtworkon().getTime();
                                                            extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                            abcd = String.format("%.1f", extHours);
                                                            if (abcd != null && !extHours.equals("0.0")) {
                                                                String ccc = String.valueOf(abcd);
                                                                allNum = String.valueOf(abcd).split("\\.");
                                                                intNum = allNum[0];
                                                                deciNum = allNum[1];
                                                                if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                    deciNum = "0";
                                                                } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                    deciNum = "5";
                                                                }
                                                                extHours = Double.valueOf(intNum + "." + deciNum);
                                                            }
                                                            otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                            otw.setExtWorkOffTime(lastT.toString());
                                                            if (extHours >= 0) {
                                                                otw.setExtWorkHours(extHours);
                                                            }
                                                        }

                                                        if (extHours <= 0) {
                                                            DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                                            Date d2400 = dft.parse(co.getDateStr() + " " + "24:00:00");
                                                            Date d0000 = dft.parse(co.getDateStr() + " " + "00:00:00");
                                                            Date dd = format.parse("05:00:00");
                                                            String dateTomor = DateUtil.getAfterDay(co.getDateStr());
                                                            Time sam = new java.sql.Time(dd.getTime());
                                                            if (otw.getWeek().intValue() == 5 || otw.getWeek() == 6 || otw.getWeek().intValue() == 7) {
                                                                co2 = personMapper.getZhongKongBeanByNumAndDate(co.getEnrollNumber(), dateTomor);
                                                                if (co2 != null && co2.getTimeStr() != null && co2.getTimeStr().length() > 0) {
                                                                    String afterTime = dateTomor + " " + co2.getTimeStr().split(" ")[0];
                                                                    String beforeTime = co.getDateStr() + " " + workSet.getExtworkon();
                                                                    Date afterT = dft.parse(afterTime);
                                                                    Date beforTime = dft.parse(beforeTime);
                                                                    String afterTimem = co2.getTimeStr().split(" ")[0];
                                                                    Date d = format.parse(afterTimem);
                                                                    Time tii = new java.sql.Time(d.getTime());
                                                                    if (tii.before(sam)) {
                                                                        Long abc = d2400.getTime() - beforTime.getTime();
                                                                        extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                        abcd = String.format("%.1f", extHours);
                                                                        if (abcd != null && !extHours.equals("0.0")) {
                                                                            String ccc = String.valueOf(abcd);
                                                                            allNum = String.valueOf(abcd).split("\\.");
                                                                            intNum = allNum[0];
                                                                            deciNum = allNum[1];
                                                                            if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                                deciNum = "0";
                                                                            } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                                deciNum = "5";
                                                                            }
                                                                            extHours = Double.valueOf(intNum + "." + deciNum);
                                                                        }
                                                                        otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                        otw.setExtWorkOffTime(lastT.toString());
                                                                        if (extHours >= 0) {
                                                                            otw.setExtWorkHours(extHours);
                                                                        }
                                                                    }
                                                                }

                                                                if (extHours <= 0) {
                                                                    String firstTimeStr = co.getTimeStr().split(" ")[0];
                                                                    Date d = format.parse(firstTimeStr);
                                                                    Time tii = new java.sql.Time(d.getTime());
                                                                    if (tii.before(sam)) {
                                                                        Long abc = d.getTime() - d0000.getTime();
                                                                        extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                        abcd = String.format("%.1f", extHours);
                                                                        if (abcd != null && !extHours.equals("0.0")) {
                                                                            String ccc = String.valueOf(abcd);
                                                                            allNum = String.valueOf(abcd).split("\\.");
                                                                            intNum = allNum[0];
                                                                            deciNum = allNum[1];
                                                                            if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                                deciNum = "0";
                                                                            } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                                deciNum = "5";
                                                                            }
                                                                            extHours = Double.valueOf(intNum + "." + deciNum);
                                                                        }
                                                                        otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                        otw.setExtWorkOffTime(lastT.toString());
                                                                        if (extHours >= 0) {
                                                                            otw.setExtWorkHours(extHours);
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                co2 = personMapper.getZhongKongBeanByNumAndDate(co.getEnrollNumber(), dateTomor);
                                                                if (co2 != null && co2.getTimeStr() != null && co2.getTimeStr().length() > 0) {
                                                                    String afterTime = dateTomor + " " + co2.getTimeStr().split(" ")[0];
                                                                    String beforeTime = co.getDateStr() + " " + workSet.getExtworkon();
                                                                    Date afterT = dft.parse(afterTime);
                                                                    Date beforTime = dft.parse(beforeTime);
                                                                    String afterTimem = co2.getTimeStr().split(" ")[0];
                                                                    Date d = format.parse(afterTimem);
                                                                    Time tii = new java.sql.Time(d.getTime());
                                                                    if (tii.before(sam)) {
                                                                        Long abc = afterT.getTime() - beforTime.getTime();
                                                                        extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                                        abcd = String.format("%.1f", extHours);
                                                                        if (abcd != null && !extHours.equals("0.0")) {
                                                                            String ccc = String.valueOf(abcd);
                                                                            allNum = String.valueOf(abcd).split("\\.");
                                                                            intNum = allNum[0];
                                                                            deciNum = allNum[1];
                                                                            if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                                                deciNum = "0";
                                                                            } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                                                deciNum = "5";
                                                                            }
                                                                            extHours = Double.valueOf(intNum + "." + deciNum);
                                                                        }
                                                                        otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                                        otw.setExtWorkOffTime(lastT.toString());
                                                                        if (extHours >= 0) {
                                                                            otw.setExtWorkHours(extHours);
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                }

                                                if (!aon) {
                                                    Leave leave = personMapper.getLeaveByEmIdAndMonthAFFF(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                                    if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setaOnRemark("正常请假");
                                                                otw.setRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setaOnRemark("因公外出");
                                                                otw.setRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            } else if (leave.getType() == 2) {
                                                                otw.setaOnRemark("带薪年假");
                                                                otw.setRemark("带薪年假");
                                                                otw.setClockResult(3);
                                                            } else if (leave.getType() == 3) {
                                                                otw.setRemark("丧假");
                                                                otw.setClockResult(19);
                                                            } else if (leave.getType() == 4) {
                                                                otw.setRemark("婚假");
                                                                otw.setClockResult(18);
                                                            } else if (leave.getType() == 5) {
                                                                otw.setRemark("产假");
                                                                otw.setClockResult(20);
                                                            } else if (leave.getType() == 6) {
                                                                otw.setRemark("陪产假");
                                                                otw.setClockResult(21);
                                                            }
                                                        }
                                                    } else {
                                                        boolean isAOnH = false;
                                                        a:
                                                        for (Time tii : timeList) {
                                                            if (tii.after(afterWS.getMorningOn()) && !tii.after(afterWS.getMorningOff())) {
                                                                if (pon) {
                                                                    aHours = DateUtil.calcuHours(tii, workSet.getMorningOff());
                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                    if (le != null) {
                                                                        clockRes = 16;
                                                                    }
                                                                    break a;
                                                                } else {
                                                                    if (pon || poff) {
                                                                        aHours = DateUtil.calcuHours(tii, workSet.getMorningOff());
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                            if (timeList.get(tiii).after(afterWS.getMorningOn()) && !timeList.get(tiii).after(afterWS.getMorningOff())) {
                                                                                aHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                isAOnH = true;
                                                                                Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                                if (le != null) {
                                                                                    clockRes = 16;
                                                                                }
                                                                                break a;
                                                                            }
                                                                        }

                                                                        if (!isAOnH) {
                                                                            for (Time tiii : timeList) {
                                                                                if (tiii.after(afterWS.getMorningOn())) {
                                                                                    aHours = DateUtil.calcuHours(tiii, workSet.getMorningOff());
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tiii, em.getId());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (aHours != 0.0) {
                                                            if (em.getWorkType() == 1) {
                                                                aHours += 0.5;
                                                            }
                                                            otw.setaOnRemark(aHours.toString());
                                                            otw.setRemark(aHours.toString() + "," + pHours.toString());
                                                            otw.setClockResult(clockRes);
                                                        } else {
                                                            if (((aon ? 1 : 0) + (aoff ? 1 : 0) + (pon ? 1 : 0) + (poff ? 1 : 0)) <= 1) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else {
                                                                otw.setaOnRemark("上午上班");
                                                                otw.setRemark("打卡时间不在规定时间范围内");
                                                                otw.setClockResult(7);
                                                            }
                                                        }
                                                    }

                                                }
                                                if (!aoff) {
                                                    Leave leave = personMapper.getLeaveByEmIdAndMonthAFFF(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                                    if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setaOnRemark("正常请假");
                                                                otw.setRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setaOffRemark("因公外出");
                                                                otw.setRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            } else if (leave.getType() == 2) {
                                                                otw.setaOffRemark("带薪年假");
                                                                otw.setRemark("带薪年假");
                                                                otw.setClockResult(3);
                                                            } else if (leave.getType() == 3) {
                                                                otw.setRemark("丧假");
                                                                otw.setClockResult(19);
                                                            } else if (leave.getType() == 4) {
                                                                otw.setRemark("婚假");
                                                                otw.setClockResult(18);
                                                            } else if (leave.getType() == 5) {
                                                                otw.setRemark("产假");
                                                                otw.setClockResult(20);
                                                            } else if (leave.getType() == 6) {
                                                                otw.setRemark("陪产假");
                                                                otw.setClockResult(21);
                                                            }
                                                        }
                                                    } else {
                                                        boolean isAOnH = false;
                                                        a:
                                                        for (Time tii : timeList) {
                                                            if (tii.after(afterWS.getMorningOn()) && !tii.after(afterWS.getMorningOff())) {
                                                                if (aon) {
                                                                    aHours = DateUtil.calcuHours(workSet.getMorningOn(), tii);
                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                    if (le != null) {
                                                                        clockRes = 16;
                                                                    }
                                                                    break a;
                                                                } else {
                                                                    if (pon || poff) {
                                                                        aHours = DateUtil.calcuHours(tii, workSet.getMorningOff());
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                            if (timeList.get(tiii).after(afterWS.getMorningOn()) && !timeList.get(tiii).after(afterWS.getMorningOff())) {
                                                                                aHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                isAOnH = true;
                                                                                Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + timeList.get(tiii), yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                                if (le != null) {
                                                                                    clockRes = 16;
                                                                                }
                                                                                break a;
                                                                            }
                                                                        }

                                                                        if (!isAOnH) {
                                                                            for (Time tiii : timeList) {
                                                                                if (tiii.after(afterWS.getMorningOn())) {
                                                                                    aHours = DateUtil.calcuHours(tiii, workSet.getMorningOff());
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + tiii, em.getId());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (aHours != 0.0) {
                                                            if (em.getWorkType() == 1) {
                                                                aHours += 0.5;
                                                            }
                                                            otw.setaOnRemark(aHours.toString());
                                                            otw.setRemark(aHours.toString() + "," + pHours.toString());
                                                            otw.setClockResult(clockRes);
                                                        } else {
                                                            if (((aon ? 1 : 0) + (aoff ? 1 : 0) + (pon ? 1 : 0) + (poff ? 1 : 0)) <= 1) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else if (!aon && !aoff) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else {
                                                                otw.setaOffRemark("上午下班");
                                                                otw.setRemark("打卡时间不在规定时间范围内");
                                                                otw.setClockResult(7);
                                                            }
                                                        }
                                                    }
                                                }

                                                if (!pon) {
                                                    Leave leave = personMapper.getLeaveByEmIdAndMonthAFFF(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                                    if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setpOnRemark("正常请假");
                                                                otw.setRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setpOnRemark("因公外出");
                                                                otw.setRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            } else if (leave.getType() == 2) {
                                                                otw.setpOnRemark("带薪年假");
                                                                otw.setRemark("带薪年假");
                                                                otw.setClockResult(3);
                                                            } else if (leave.getType() == 3) {
                                                                otw.setRemark("丧假");
                                                                otw.setClockResult(19);
                                                            } else if (leave.getType() == 4) {
                                                                otw.setRemark("婚假");
                                                                otw.setClockResult(18);
                                                            } else if (leave.getType() == 5) {
                                                                otw.setRemark("产假");
                                                                otw.setClockResult(20);
                                                            } else if (leave.getType() == 6) {
                                                                otw.setRemark("陪产假");
                                                                otw.setClockResult(21);
                                                            }
                                                        }
                                                    } else {
                                                        boolean isAOnH = false;
                                                        a:
                                                        for (Time tii : timeList) {
                                                            if (tii.after(afterWS.getNoonOn()) && !tii.after(afterWS.getNoonOff())) {
                                                                if (poff) {
                                                                    pHours = DateUtil.calcuHours(tii, workSet.getNoonOff());
                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                    if (le != null) {
                                                                        clockRes = 16;
                                                                    }
                                                                    break a;
                                                                } else {
                                                                    if (aon || aoff) {
                                                                        pHours = DateUtil.calcuHours(tii, workSet.getNoonOff());
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                            if (timeList.get(tiii).after(afterWS.getNoonOn()) && !timeList.get(tiii).after(afterWS.getNoonOff())) {
                                                                                pHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                isAOnH = true;
                                                                                Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tii, em.getId());
                                                                                if (le != null) {
                                                                                    clockRes = 16;
                                                                                }
                                                                                break a;
                                                                            }
                                                                        }
                                                                        if (!isAOnH) {
                                                                            for (Time tiii : timeList) {
                                                                                if (tiii.after(afterWS.getNoonOn())) {
                                                                                    pHours = DateUtil.calcuHours(tiii, workSet.getNoonOff());
                                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + workSet.getNoonOn(), yearMonth + "-" + date + " " + tiii, em.getId());
                                                                                    if (le != null) {
                                                                                        clockRes = 16;
                                                                                    }
                                                                                    break a;
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (pHours != 0.0) {
                                                            if (aon || aoff) {
                                                                otw.setaOnRemark(pHours.toString());
                                                                otw.setRemark(aHours + "," + pHours.toString());
                                                                otw.setClockResult(clockRes);
                                                            } else {
                                                                otw.setaOnRemark(pHours.toString());
                                                                otw.setRemark(' ' + "," + pHours.toString());
                                                                otw.setClockResult(clockRes);
                                                            }

                                                        } else {
                                                            if (((aon ? 1 : 0) + (aoff ? 1 : 0) + (pon ? 1 : 0) + (poff ? 1 : 0)) <= 1) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else {
                                                                if (aHours != 0.0) {
                                                                    otw.setaOnRemark(pHours.toString());
                                                                    otw.setRemark(aHours + "," + pHours.toString());
                                                                    otw.setClockResult(clockRes);
                                                                } else {
                                                                    otw.setpOnRemark("下午上班");
                                                                    otw.setRemark("打卡时间不在规定时间范围内");
                                                                    otw.setClockResult(7);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                if (!poff) {
                                                    Leave leave = personMapper.getLeaveByEmIdAndMonthAFFF(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                                    if (co.getTimeStr() == null || co.getTimeStr().trim().length() == 0) {
                                                        if (leave != null) {
                                                            if (leave.getType() == 0) {
                                                                otw.setpOffRemark("正常请假");
                                                                otw.setRemark("正常请假");
                                                                otw.setClockResult(6);
                                                            } else if (leave.getType() == 1) {
                                                                outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                                if (outClockIn != null) {
                                                                    outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                                }
                                                                otw.setTimeStr(outtimeStr);
                                                                otw.setpOffRemark("因公外出");
                                                                otw.setRemark("因公外出");
                                                                otw.setClockResult(2);
                                                            } else if (leave.getType() == 2) {
                                                                otw.setpOffRemark("带薪年假");
                                                                otw.setRemark("带薪年假");
                                                                otw.setClockResult(3);
                                                            } else if (leave.getType() == 3) {
                                                                otw.setRemark("丧假");
                                                                otw.setClockResult(19);
                                                            } else if (leave.getType() == 4) {
                                                                otw.setRemark("婚假");
                                                                otw.setClockResult(18);
                                                            } else if (leave.getType() == 5) {
                                                                otw.setRemark("产假");
                                                                otw.setClockResult(20);
                                                            } else if (leave.getType() == 6) {
                                                                otw.setRemark("陪产假");
                                                                otw.setClockResult(21);
                                                            }
                                                        }
                                                    } else {
                                                        boolean isAOnH = false;
                                                        a:
                                                        for (Time tii : timeList) {
                                                            if (tii.after(afterWS.getNoonOn()) && !tii.after(afterWS.getNoonOff())) {
                                                                if (pon) {
                                                                    pHours = DateUtil.calcuHours(workSet.getNoonOn(), tii);
                                                                    Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                    if (le != null) {
                                                                        clockRes = 16;
                                                                    }
                                                                    break a;
                                                                } else {
                                                                    if (aon || aoff) {
                                                                        pHours = DateUtil.calcuHours(workSet.getNoonOn(), tii);
                                                                        Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + tii, yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                        if (le != null) {
                                                                            clockRes = 16;
                                                                        }
                                                                        break a;
                                                                    } else {
                                                                        for (int tiii = timeList.size() - 1; tiii < timeList.size(); tiii--) {
                                                                            if (timeList.get(tiii).after(afterWS.getNoonOn()) && !timeList.get(tiii).after(afterWS.getNoonOff())) {
                                                                                pHours = DateUtil.calcuHours(tii, timeList.get(tiii));
                                                                                isAOnH = true;
                                                                                Leave le = personMapper.getLeaveByEmpIdAndDateStr(yearMonth + "-" + date + " " + timeList.get(tiii), yearMonth + "-" + date + " " + workSet.getNoonOff(), em.getId());
                                                                                if (le != null) {
                                                                                    clockRes = 16;
                                                                                }
                                                                                break a;
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (pHours != 0.0) {
                                                            otw.setaOnRemark(pHours.toString());
                                                            otw.setRemark(aHours + "," + pHours.toString());
                                                            otw.setClockResult(clockRes);
                                                        } else {
                                                            if (((aon ? 1 : 0) + (aoff ? 1 : 0) + (pon ? 1 : 0) + (poff ? 1 : 0)) <= 1) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else if (!pon && !poff) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else {
                                                                otw.setpOffRemark("下午下班");
                                                                otw.setRemark("打卡时间不在规定时间范围内");
                                                                otw.setClockResult(7);
                                                            }
                                                        }
                                                    }
                                                }
                                                int rightclocknum = (aon == true ? 1 : 0) + (aoff == true ? 1 : 0) + (pon == true ? 1 : 0) + (poff == true ? 1 : 0);
                                                if (rightclocknum < 2) {
                                                    if (DateUtil.getWeek(kqBeans.get(k).getDateStr()) == 6 || DateUtil.getWeek(kqBeans.get(k).getDateStr()) == 7) {
                                                        QianKa qqk = personMapper.getQianKaByDateAndEmpnoA(otw.getEmpNo(), otw.getDateStr());
                                                        if (qqk != null) {
                                                            if (((aon ? 1 : 0) + (aoff ? 1 : 0) + (pon ? 1 : 0) + (poff ? 1 : 0)) <= 1) {
                                                                otw.setClockResult(8);
                                                                otw.setRemark("旷工");
                                                            } else {
                                                                otw.setRemark("打卡时间不在规定时间内");
                                                                otw.setClockResult(7);
                                                            }
                                                        } else {
                                                            otw.setRemark("放假");
                                                            otw.setClockResult(5);
                                                        }
                                                    } else {
                                                        Leave a = personMapper.getLeaveByEmIdAndMonthAB(otw.getEmpNo(), otw.getDateStr());
                                                        if (a != null) {
                                                            //otw.setClockResult(16);
                                                        } else {
                                                            //otw.setClockResult(17);
                                                        }
                                                    }
                                                }

                                                Double extHours = 0.0;
                                                DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                                                Date d2400 = dft.parse(co.getDateStr() + " " + "24:00:00");
                                                Date d0000 = dft.parse(co.getDateStr() + " " + "00:00:00");
                                                Date dd = format.parse("05:00:00");
                                                String dateTomor = DateUtil.getAfterDay(co.getDateStr());
                                                Time sam = new java.sql.Time(dd.getTime());
                                                String firstTimeStr = co.getTimeStr().split(" ")[0];
                                                Date d = format.parse(firstTimeStr);
                                                Date df = dft.parse(co.getDateStr() + " " + firstTimeStr);
                                                Time tii = new java.sql.Time(d.getTime());
                                                if (tii.before(sam)) {
                                                    Long abc = df.getTime() - d0000.getTime();
                                                    extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                    abcd = String.format("%.1f", extHours);
                                                    if (abcd != null && !extHours.equals("0.0")) {
                                                        String ccc = String.valueOf(abcd);
                                                        allNum = String.valueOf(abcd).split("\\.");
                                                        intNum = allNum[0];
                                                        deciNum = allNum[1];
                                                        if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                            deciNum = "0";
                                                        } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                            deciNum = "5";
                                                        }
                                                        extHours = Double.valueOf(intNum + "." + deciNum);
                                                    }
                                                    otw.setExtWorkOnTime(workSet.getExtworkon().toString());
                                                    otw.setExtWorkOffTime(firstTimeStr.toString());
                                                    if (extHours >= 0) {
                                                        otw.setExtWorkHours((otw.getExtWorkHours() == null ? 0.0 : otw.getExtWorkHours()) + extHours);
                                                    }
                                                }

                                            } else {
                                                Leave leave = personMapper.getLeaveByEmIdAndMonth(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn(), yearMonth + "-" + date + " " + workSet.getNoonOff());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setRemark("正常请假");
                                                        otw.setClockResult(6);
                                                    } else if (leave.getType() == 1) {
                                                        outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                        if (outClockIn != null) {
                                                            outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                        }
                                                        otw.setTimeStr(outtimeStr);
                                                        otw.setRemark("因公外出");
                                                        otw.setClockResult(2);
                                                    } else if (leave.getType() == 2) {
                                                        otw.setRemark("带薪年假");
                                                        otw.setClockResult(3);
                                                    } else if (leave.getType() == 3) {
                                                        otw.setRemark("丧假");
                                                        otw.setClockResult(19);
                                                    } else if (leave.getType() == 4) {
                                                        otw.setRemark("婚假");
                                                        otw.setClockResult(18);
                                                    } else if (leave.getType() == 5) {
                                                        otw.setRemark("产假");
                                                        otw.setClockResult(20);
                                                    } else if (leave.getType() == 6) {
                                                        otw.setRemark("陪产假");
                                                        otw.setClockResult(21);
                                                    }
                                                } else {
                                                    if (type == 0) {
                                                        otw.setRemark("旷工");
                                                        otw.setClockResult(8);
                                                    } else if (type == 1) {
                                                        otw.setRemark("周末不上班");
                                                        otw.setClockResult(5);
                                                    } else if (type == 2) {
                                                        otw.setRemark("法定带薪假日");
                                                        otw.setClockResult(4);
                                                    }
                                                }
                                                leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setaOnRemark("正常请假");
                                                        otw.setRemark("正常请假");
                                                        otw.setClockResult(6);
                                                    } else if (leave.getType() == 1) {
                                                        outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                        if (outClockIn != null) {
                                                            outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                        }
                                                        otw.setTimeStr(outtimeStr);
                                                        otw.setaOnRemark("因公外出");
                                                        otw.setRemark("因公外出");
                                                        otw.setClockResult(2);
                                                    } else if (leave.getType() == 2) {
                                                        otw.setaOnRemark("带薪年假");
                                                        otw.setRemark("带薪年假");
                                                        otw.setClockResult(3);
                                                    } else if (leave.getType() == 3) {
                                                        otw.setRemark("丧假");
                                                        otw.setClockResult(19);
                                                    } else if (leave.getType() == 4) {
                                                        otw.setRemark("婚假");
                                                        otw.setClockResult(18);
                                                    } else if (leave.getType() == 5) {
                                                        otw.setRemark("产假");
                                                        otw.setClockResult(20);
                                                    } else if (leave.getType() == 6) {
                                                        otw.setRemark("陪产假");
                                                        otw.setClockResult(21);
                                                    }
                                                } else {
                                                    if (type == 0) {
                                                        otw.setaOnRemark("上午旷工");
                                                        otw.setRemark("旷工");
                                                        otw.setClockResult(8);
                                                    } else if (type == 1) {
                                                        otw.setaOnRemark("周末不上班");
                                                        otw.setRemark("周末不上班");
                                                        otw.setClockResult(5);
                                                    } else if (type == 2) {
                                                        otw.setaOnRemark("法定带薪假");
                                                        otw.setRemark("法定带薪假");
                                                        otw.setClockResult(4);
                                                    }
                                                }
                                                if (workSet.getMorningOff() != null) {
                                                    leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                                    if (leave != null) {
                                                        if (leave.getType() == 0) {
                                                            otw.setRemark("正常请假");
                                                            otw.setaOffRemark("正常请假");
                                                            otw.setClockResult(6);
                                                        } else if (leave.getType() == 1) {
                                                            outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                            if (outClockIn != null) {
                                                                outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                            }
                                                            otw.setTimeStr(outtimeStr);
                                                            otw.setRemark("因公外出");
                                                            otw.setaOffRemark("因公外出");
                                                            otw.setClockResult(2);
                                                        } else if (leave.getType() == 2) {
                                                            otw.setaOffRemark("带薪年假");
                                                            otw.setRemark("带薪年假");
                                                            otw.setClockResult(3);
                                                        } else if (leave.getType() == 3) {
                                                            otw.setRemark("丧假");
                                                            otw.setClockResult(19);
                                                        } else if (leave.getType() == 4) {
                                                            otw.setRemark("婚假");
                                                            otw.setClockResult(18);
                                                        } else if (leave.getType() == 5) {
                                                            otw.setRemark("产假");
                                                            otw.setClockResult(20);
                                                        } else if (leave.getType() == 6) {
                                                            otw.setRemark("陪产假");
                                                            otw.setClockResult(21);
                                                        }
                                                    } else {
                                                        if (type == 0) {
                                                            otw.setaOffRemark("上午旷工");
                                                            otw.setRemark("旷工");
                                                            otw.setClockResult(8);
                                                        } else if (type == 1) {
                                                            otw.setaOffRemark("周末不上班");
                                                            otw.setRemark("周末不上班");
                                                            otw.setClockResult(5);
                                                        } else if (type == 2) {
                                                            otw.setaOffRemark("法定带薪假");
                                                            otw.setRemark("法定带薪假");
                                                            otw.setClockResult(4);
                                                        }
                                                    }
                                                }
                                                if (workSet.getNoonOn() != null) {
                                                    leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                                    if (leave != null) {
                                                        if (leave.getType() == 0) {
                                                            otw.setRemark("正常请假");
                                                            otw.setpOnRemark("正常请假");
                                                            otw.setClockResult(6);
                                                        } else if (leave.getType() == 1) {
                                                            outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                            if (outClockIn != null) {
                                                                outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                            }
                                                            otw.setTimeStr(outtimeStr);
                                                            otw.setRemark("因公外出");
                                                            otw.setpOnRemark("因公外出");
                                                            otw.setClockResult(2);
                                                        } else if (leave.getType() == 2) {
                                                            otw.setpOnRemark("带薪年假");
                                                            otw.setRemark("带薪年假");
                                                            otw.setClockResult(3);
                                                        } else if (leave.getType() == 3) {
                                                            otw.setRemark("丧假");
                                                            otw.setClockResult(19);
                                                        } else if (leave.getType() == 4) {
                                                            otw.setRemark("婚假");
                                                            otw.setClockResult(18);
                                                        } else if (leave.getType() == 5) {
                                                            otw.setRemark("产假");
                                                            otw.setClockResult(20);
                                                        } else if (leave.getType() == 6) {
                                                            otw.setRemark("陪产假");
                                                            otw.setClockResult(21);
                                                        }
                                                    } else {
                                                        if (type == 0) {
                                                            otw.setpOnRemark("下午旷工");
                                                            otw.setRemark("旷工");
                                                            otw.setClockResult(8);
                                                        } else if (type == 1) {
                                                            otw.setpOnRemark("周末不上班");
                                                            otw.setRemark("周末不上班");
                                                            otw.setClockResult(5);
                                                        } else if (type == 2) {
                                                            otw.setpOnRemark("法定带薪假");
                                                            otw.setRemark("法定带薪假");
                                                            otw.setClockResult(4);
                                                        }
                                                    }
                                                }
                                                leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                                if (leave != null) {
                                                    if (leave.getType() == 0) {
                                                        otw.setpOffRemark("正常请假");
                                                        otw.setRemark("正常请假");
                                                        otw.setClockResult(6);
                                                    } else if (leave.getType() == 1) {
                                                        outClockIn = personMapper.getOutClockInByEmpNoandDate(leave.getEmployeeId(), yearMonth + "-" + date);
                                                        if (outClockIn != null) {
                                                            outtimeStr = StringUtil.onlyTimeStr(outClockIn);
                                                        }
                                                        otw.setTimeStr(outtimeStr);
                                                        otw.setpOffRemark("因公外出");
                                                        otw.setRemark("因公外出");
                                                        otw.setClockResult(2);
                                                    } else if (leave.getType() == 2) {
                                                        otw.setpOffRemark("带薪年假");
                                                        otw.setRemark("带薪年假");
                                                        otw.setClockResult(3);
                                                    } else if (leave.getType() == 3) {
                                                        otw.setRemark("丧假");
                                                        otw.setClockResult(19);
                                                    } else if (leave.getType() == 4) {
                                                        otw.setRemark("婚假");
                                                        otw.setClockResult(18);
                                                    } else if (leave.getType() == 5) {
                                                        otw.setRemark("产假");
                                                        otw.setClockResult(20);
                                                    } else if (leave.getType() == 6) {
                                                        otw.setRemark("陪产假");
                                                        otw.setClockResult(21);
                                                    }
                                                } else {
                                                    if (type == 0) {
                                                        otw.setpOffRemark("下午旷工");
                                                        otw.setRemark("旷工");
                                                        otw.setClockResult(8);
                                                    } else if (type == 1) {
                                                        otw.setpOffRemark("周末不上班");
                                                        otw.setRemark("周末不上班");
                                                        otw.setClockResult(5);
                                                    } else if (type == 2) {
                                                        otw.setpOffRemark("法定带薪假");
                                                        otw.setRemark("法定带薪假");
                                                        otw.setClockResult(3);
                                                    }
                                                }
                                            }
                                        } else {
                                            otw.setRemark("没有此人打卡记录");
                                            otw.setClockResult(10);
                                        }

//                                        if (((aon ? 1 : 0) + (aoff ? 1 : 0) + (pon ? 1 : 0) + (poff ? 1 : 0)) <= 1) {
//                                            otw.setClockResult(8);
//                                            otw.setRemark("旷工");
//                                        }

                                        if (otw.getName() != null && otw.getName().trim().length() > 0) {
                                            if (otw.getRemark() == null) {
                                                otw.setRemark("正常");
                                                otw.setClockResult(1);
                                            }
                                            kqBeanList.add(otw);
                                        }
                                    }
                                }
                            }

                        }
                    }
                } else {
                    kqBeanList.add(aaa);
                    return kqBeanList;
                }
            }
        }
        return kqBeanList;
    }

    @Transactional
    public void saveAllNewKQBeansToMysql(List<KQBean> kqBeanList) throws Exception {
        for (KQBean kqBean : kqBeanList) {
            personMapper.saveAllNewKQBeansToMysql(kqBean);
        }
    }

    public List<KQBean> findAllKQBData(Employee employee) throws Exception {
        return personMapper.findAllKQBData(employee);
    }

    public List<String> getAllKQMonthListKQBean() throws Exception {
        return personMapper.getAllKQMonthListKQBean();
    }

    public List<String> getAllKQDateList() throws Exception {
        return personMapper.getAllKQDateList();
    }


    public List<String> getAllKQDateListAAA() throws Exception {
        return personMapper.getAllKQDateListAAA();
    }

    @Transactional
    public void saveCheckKQBeanListByDates(List<OutClockIn> outClockIns) throws Exception {
        //personMapper.saveCheckKQBeanListByDates(outClockIns);
        saveMonthKQInfoByCheckKQBean(outClockIns);
    }


    @Transactional
    public List<String> getAllUserName() throws Exception {
        return personMapper.getAllUserName();
    }

    public String getAlReadyCheckDatestr(List<OutClockIn> clockDates) throws Exception {
        return personMapper.getAlReadyCheckDatestr(clockDates);
    }

    public void deleteKQBeanOlderDateByDates(List<OutClockIn> dateStr) throws Exception {
        personMapper.deleteKQBeanOlderDateByDates(dateStr);
    }

    public List<KQBean> getAllKQDataByYearMonthDays(List<OutClockIn> clockDates) throws Exception {
        return personMapper.getAllKQDataByYearMonthDays(clockDates);
    }

    public int findAllKQBDataCount() throws Exception {
        return personMapper.findAllKQBDataCount();
    }

    public List<MonthKQInfo> findAllMonthKQDataByCondition(Employee employee) throws Exception {
        List<Integer> nameIds2 = null;
        List<Integer> nameIds = null;
        if (employee.getNameIds().size() > 0) {
            nameIds2 = new ArrayList<Integer>();
            nameIds = new ArrayList<Integer>();
            for (Integer id : employee.getNameIds()) {
                if (id >= 10000) {
                    nameIds2.add(id);
                } else {
                    nameIds.add(id);
                }
            }
            employee.setNameIds(nameIds);
            employee.setNameIds2(nameIds2);
        }
        return personMapper.findAllMonthKQDataByCondition(employee);
    }

    public Double getTotalHoursByDateStr(TiaoXiu tiaoXiu) throws Exception {
        String[] arrays = null;
        Double totalHours = 0.0;
        TiaoXiu oldTiaoXiu = personMapper.getOldTiaoXiuDanByEmpNoandDateStr(tiaoXiu);
        MonthKQInfo mk = personMapper.getMKbyEmpNoAndYearMonth(tiaoXiu.getEmpNo(), tiaoXiu.getFromDateStr().substring(0, 7), "day".concat(tiaoXiu.getFromDateStr().split("-")[2]), "day".concat(tiaoXiu.getFromDateStr().split("-")[2]).concat("Remark"));
        if (oldTiaoXiu != null) {
            return oldTiaoXiu.getHours();
        } else {
            List<Integer> zhengChangDaKa = new ArrayList<Integer>();
            zhengChangDaKa.add(1);
            zhengChangDaKa.add(17);
            zhengChangDaKa.add(13);
            zhengChangDaKa.add(67);
            zhengChangDaKa.add(61);
            zhengChangDaKa.add(2);
            zhengChangDaKa.add(12);
            zhengChangDaKa.add(106);
            zhengChangDaKa.add(107);
            zhengChangDaKa.add(108);
            if (mk != null && mk.getEmpNo() != null) {
                arrays = mk.getEmpNo().split(",");
                if (zhengChangDaKa.contains(Integer.valueOf(arrays[0]))) {
                    totalHours += 4;
                }

                if (zhengChangDaKa.contains(Integer.valueOf(arrays[1]))) {
                    totalHours += 4;
                }
                totalHours += Double.valueOf(arrays[2]);
            }
            return totalHours;
        }
    }

    public void deleteTiaoXiuById(Integer id) throws Exception {
        personMapper.deleteTiaoXiuById(id);
    }


    public List<TiaoXiu> queryTXByCondition(TiaoXiu tiaoXiu) throws Exception {
        return personMapper.queryTXByCondition(tiaoXiu);
    }

    public int queryTXByConditionCount(TiaoXiu tiaoXiu) throws Exception {
        return personMapper.queryTXByConditionCount(tiaoXiu);
    }


    public int saveTiaoXiuDateToMysql(TiaoXiu tiaoXiu) throws Exception {
        if (tiaoXiu.getEmpNoList() != null) {
            if (tiaoXiu.getEmpNoList().size() == 1) {
                tiaoXiu.setEmpNo(tiaoXiu.getEmpNoList().get(0));
                tiaoXiu.setName(personMapper.getNameByEmpNo(tiaoXiu.getEmpNo()));
                tiaoXiu.setUsaged(0);
                tiaoXiu.setAusaged(0);
                tiaoXiu.setFromDateWeek(DateUtil.getWeek(tiaoXiu.getFromDateStr()));
                tiaoXiu.setToDateWeek(DateUtil.getWeek(tiaoXiu.getToDateStr()));
                personMapper.saveTiaoXiuDateToMysql(tiaoXiu);
            } else {
                for (String str : tiaoXiu.getEmpNoList()) {
                    tiaoXiu.setEmpNo(str);
                    tiaoXiu.setName(personMapper.getNameByEmpNo(tiaoXiu.getEmpNo()));
                    tiaoXiu.setUsaged(0);
                    tiaoXiu.setAusaged(0);
                    tiaoXiu.setTotalHours(0D);
                    tiaoXiu.setHours(0D);
                    tiaoXiu.setType(0);
                    tiaoXiu.setFromDateWeek(DateUtil.getWeek(tiaoXiu.getFromDateStr()));
                    tiaoXiu.setToDateWeek(DateUtil.getWeek(tiaoXiu.getToDateStr()));
                    personMapper.saveTiaoXiuDateToMysql(tiaoXiu);
                }
            }
        }
        return 1;
    }

    public AccuFund getAFByName(String name) throws Exception {
        return personMapper.getAFByName(name);
    }

    public List<TiaoXiu> findAllTiaoXiu(TiaoXiu tiaoXiu) throws Exception {
        return personMapper.findAllTiaoXiu(tiaoXiu);
    }

    public int findAllTiaoXiuCount() throws Exception {
        return personMapper.findAllTiaoXiuCount();
    }

    public Insurance getISByName(String name) throws Exception {
        return personMapper.getISByName(name);
    }

    public List<MonthKQInfo> findAllMonthKQDataByConditionBDF(Employee ee) throws Exception {
        return personMapper.findAllMonthKQDataByConditionBDF(ee);
    }

    public List<MonthKQInfo> findAllMonthKQDataByConditionABC(Employee ee) throws Exception {
        return personMapper.findAllMonthKQDataByConditionABC(ee);
    }

    public String getLinShiInComDateByName(String name) throws Exception {
        return personMapper.getLinShiInComDateByName(name);
    }

    public void updateRenShiByDates(List<String> dates) throws Exception {
        personMapper.updateRenShiByDates(dates);
    }

    public int findAllMonthKQDataCountByCondition(Employee employee) throws Exception {
        return personMapper.findAllMonthKQDataCountByCondition(employee);
    }

    public void updateKQBeanDataByRenShi(Integer id, Double extHours, Integer state) throws Exception {
        personMapper.updateKQBeanDataByRenShi(id, extHours, state);
    }

    public List<MonthKQInfo> findAllMonthKQData(String yearMonth, Employee employee) throws Exception {
        return personMapper.findAllMonthKQData(yearMonth, employee.getCurrentPageTotalNum(), employee.getPageSize());
    }

    public int findAllMonthKQDataCount(String yearMonth) throws Exception {
        return personMapper.findAllMonthKQDataCount(yearMonth);
    }


    public List<MonthKQInfo> queryMKDataByCondition(Employee employee) throws Exception {
        return personMapper.queryMKDataByCondition(employee);
    }

    public int queryMKDataByConditionCount(Employee employee) throws Exception {
        return personMapper.queryMKDataByConditionCount(employee);

    }


    public List<String> getAllKQMonthList() throws Exception {
        return personMapper.getAllKQMonthList();
    }


    public void deleteLinShiDateToMysql(LinShiHours lsh) throws Exception {
        personMapper.deleteLinShiDateToMysql(lsh);
    }

    public int saveLinShiDateToMysql(LinShiHours lsh) throws Exception {
        LinShiEmp ee = personMapper.getLinShiEmpById(lsh.getEmpId());
        if (lsh.getDateStr() != null) {
            lsh.setYearMonth(lsh.getDateStr().split("-")[0] + "-" + lsh.getDateStr().split("-")[1]);
        }
        lsh.setName(ee.getName());
        LinShiHours lb = personMapper.getLSHByNameAndDateStr(ee.getName(), lsh.getDateStr());
        if (lb == null) {
            personMapper.saveLinShiDateToMysql(lsh);
            return 1;
        } else {
            personMapper.updateLinShiDateToMysql(lsh);
            return 2;
        }
    }

    public List<LinShiHours> queryLSHByCondition(LinShiHours lsh) throws Exception {
        return personMapper.queryLSHByCondition(lsh);
    }

    public int queryLSHByConditionCount(LinShiHours lsh) throws Exception {
        return personMapper.queryLSHByConditionCount(lsh);
    }


    public void saveMonthKQInfoByCheckKQBean(List<OutClockIn> outClockInList) throws Exception {
        String day = "";
        String yearMonth = "";
        String fromDay = "";
        String fromYM = "";
        String dayNumRemark = "";
        ReturnBean rb = null;
        String daytitleSqlTo = "";
        String daytitleSqlReTo = "";
        List<TiaoXiu> tiaoXiuList = null;
        List<TiaoXiu> tiaoXiuList1 = null;
        for (OutClockIn oci : outClockInList) {
            personMapper.updateFromDateStatus(oci.getClockInDateStr());
            personMapper.updateToDateStatus(oci.getClockInDateStr());
        }
        List<KQBean> kqBeanList = personMapper.deleteKQBeanByDateStrs(outClockInList);
        List<MonthKQInfo> monthKQInfoList = new ArrayList<MonthKQInfo>();
        String[] yearMos = outClockInList.get(0).getClockInDateStr().split("-");
        WorkDate date = personMapper.addWorkDateByMonth3(yearMos[0] + "-" + yearMos[1], "B", 1);
        String[] days = date.getWorkDatess();
        List<String> youGuess = new ArrayList<String>();
        String da = null;
        List<String> linShiEmpList = personMapper.getAllZhongDianName();

        for (int a = 0; a < days.length; a++) {
            if (Integer.valueOf(days[a]) < 10) {
                da = "0" + days[a];
            } else {
                da = days[a];
            }
            if (!DateUtil.isWeekend(yearMos[0] + "-" + yearMos[1] + "-" + da)) {
                youGuess.add(yearMos[0] + "-" + yearMos[1] + "-" + da);
            }
        }
        List<String> timeList;
        Double totalOneDayHours = 0.0;
        String aOnStr = null;
        String aOffStr = null;
        String pOnStr = null;
        String pOffStr = null;
        String aOffStrRe = null;
        String pOffStrRe = null;
        Double totalTiaoXiuByDay = 0.0;
        String beFStr = "";
        StringBuilder sb = null;
        String[] timeStr = null;
        WorkSet ws = null;
        QianKa qk = null;
        Double hh = 0.0;
        Out od = null;
        LianBan lianBan = null;
        Double lianBanTotalH = 0.0;
        String fromDateStrs = "";
        String toDateStrs = "";
        String asplit[] = null;

        String[] splitDouble = null;
        Double extHours = 0.0;
        List<JiaBan> jiaBanList = null;
        ZhongKongBean zkb = null;
        MonthKQInfo mkf = null;
        String dayStr = null;
        String daytitleSql;
        String daytitleSqlRe;
        String[] yearM = null;
        String dayNum;
        List<DateSplit> dateSplitList = null;
        String jiabanHours = null;
        Double jiaHours = 0.0;
        DateSplit nowDate = null;
        QianKa qkk = null;
        WorkSet wsw = null;
        List<JiaBanList> jiaBanLists = new ArrayList<>();
        List<String> sqlTitleList = null;
        JiaBanList jiaBanList1 = null;
        boolean comin = false;
        for (OutClockIn ock : outClockInList) {
            jiaBanList = personMapper.getJiaBanDanByDateStr(ock.getClockInDateStr());
            for (JiaBan jb : jiaBanList) {
                tiaoXiuList1 = personMapper.getTiaoXiuDanByEmpNoAndFromDateOrToDate(jb.getEmpNo(), ock.getClockInDateStr());
                sb = new StringBuilder();
                comin = false;
                jiaHours = 0.0;
                jiabanHours = "";
                sqlTitleList = new ArrayList<>();
                dateSplitList = DateUtil.splitDateToDateArray(jb.getExtDateFrom(), jb.getExtDateEnd());
                zkb = personMapper.getZKBeanByEmpNoAndDateStr(jb.getEmpNo(), ock.getClockInDateStr());
                qkk = personMapper.getQianKaByDateAndEmpnoA(jb.getEmpNo(), ock.getClockInDateStr());
                if (zkb != null) {
                    if (qkk != null)
                        zkb.setTimeStr(StringUtil.sortTimes2(zkb.getTimeStr(), qkk.getTimeStr()));
                    dayStr = ock.getClockInDateStr().split("-")[2];
                    yearM = ock.getClockInDateStr().split("-");
                    dayNum = "";
                    mkf = personMapper.getMonthKqInfoByEmpNoandYearMonth(jb.getEmpNo(), yearM[0] + "-" + yearM[1]);
                    if (mkf == null)
                        mkf = new MonthKQInfo();
                    daytitleSql = "day".concat(dayStr);
                    daytitleSqlRe = daytitleSql.concat("Remark");
                    for (DateSplit ds : dateSplitList) {
                        if (ds.getDate().equals(ock.getClockInDateStr())) {
                            nowDate = ds;
                            break;
                        }
                    }
                    if (zkb.getTimeStr() != null && zkb.getTimeStr().trim().length() > 0) {
                        wsw = personMapper.getWorkSetByMonthAndPositionLevelA(zkb.getYearMonth(), zkb.getPositionLevel());
                        jiabanHours = DateUtil.getJiaBanHoursByDateSplitAndTimeStr(nowDate, zkb.getTimeStr(), wsw, zkb.getWorkType());
                        jiaHours = Double.valueOf(jiabanHours.split(",")[2]);
                        dayNum = jiabanHours;
                        mkf.setWorkendHours(mkf.getWorkendHours() == null ? 0.0 : mkf.getWorkendHours() + jiaHours);
                    } else {
                        dayNum = "";
                        mkf.setWorkendHours(mkf.getWorkendHours() == null ? 0.0 : mkf.getWorkendHours() + 0.0);
                    }
                    mkf.setEmpNo(jb.getEmpNo());
                    mkf.setDaytitleSql(daytitleSql);
                    mkf.setDaytitleSqlRemark(daytitleSqlRe);
                    mkf.setDayNum(dayNum);
                    mkf.setYearMonth(yearM[0] + "-" + yearM[1]);
                    monthKQInfoList.add(mkf);

                    sqlTitleList.add(mkf.getDaytitleSql());

                    for (JiaBanList jbl : jiaBanLists) {
                        if (jbl.getEmpNo().equals(mkf.getEmpNo()) && jbl.getYearMonth().equals(mkf.getYearMonth())) {
                            sqlTitleList.addAll(jbl.getDayJiStr());
                            sqlTitleList.add(mkf.getDaytitleSql());
                            jbl.setDayJiStr(sqlTitleList);
                            comin = true;
                        }
                    }

                    if (!comin) {
                        jiaBanList1 = new JiaBanList();
                        jiaBanList1.setEmpNo(mkf.getEmpNo());
                        jiaBanList1.setYearMonth(mkf.getYearMonth());
                        jiaBanList1.setDayJiStr(sqlTitleList);
                        jiaBanLists.add(jiaBanList1);
                    }

                    if (mkf.getId() != null) {
                        mkf.setNameReal(jb.getName());
                        personMapper.updateMonthKQInfoByCheckKQBean(mkf);
                    } else {
                        mkf.setNameReal(jb.getName());
                        personMapper.saveMonthKQInfoByCheckKQBean(mkf);
                    }

                    for (TiaoXiu tiaoXiu : tiaoXiuList1) {
                        rb = personMapper.getMkfByEmpNoAndYearMonthAndDayNumAndDayNumRemark(mkf.getEmpNo(), mkf.getYearMonth(), daytitleSql,
                                daytitleSqlRe);
                        if (rb != null) {
                            totalTiaoXiuByDay -= (tiaoXiu.getHours() == 0 ? tiaoXiu.getTotalHours() : tiaoXiu.getHours());
                            beFStr = "3";
                            fromDateStrs += tiaoXiu.getToDateStr() + " ";
                            sb.append("被调休" + totalTiaoXiuByDay + "个小时 参考" + fromDateStrs);
                            asplit = rb.getDayNum().split(",");
                            hh = Double.valueOf(asplit[2]) - (tiaoXiu.getHours() == 0 ? tiaoXiu.getTotalHours() : tiaoXiu.getHours());
                            personMapper.updateMKbyEmpNoAndYMAndDayNumAndDayNumReMark(mkf.getEmpNo(), mkf.getYearMonth(), daytitleSql
                                    , daytitleSqlRe, beFStr.concat(asplit[0]).concat(",").concat(beFStr).concat(asplit[1]).concat(",").concat(hh + ""), rb.getDayNumRemark());
                            personMapper.updateUsagedStatusByIdFromDate(tiaoXiu.getId());
                        }
                    }

                } else if (zkb == null && qkk != null) {

                }
            }

        }


        for (KQBean kqb : kqBeanList) {
            splitDouble = null;
            extHours = 0.0;
            hh = 0.0;
            sb = new StringBuilder();
            totalTiaoXiuByDay = 0.0;
            totalOneDayHours = 0.0;
            lianBanTotalH = 0.0;
            fromDateStrs = "";
            toDateStrs = "";
            beFStr = "";
            timeList = new ArrayList<String>();
            tiaoXiuList = personMapper.getTiaoXiuDanByEmpNoAndFromDateOrToDate(kqb.getEmpNo(), kqb.getDateStr());
            List<Time> times = null;
            dayNum = "";
            timeStr = null;
            lianBan = personMapper.getLianBanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr());
            mkf = personMapper.getMonthKqInfoByEmpNoandYearMonth(kqb.getEmpNo(), kqb.getYearMonth());
            if (mkf == null) {
                mkf = new MonthKQInfo();
            }
            dayStr = kqb.getDateStr().split("-")[2];
            mkf.setEmpNo(kqb.getEmpNo());
            mkf.setName(kqb.getName());
            mkf.setYearMonth(kqb.getYearMonth());
            if (lianBan != null) {
                if (lianBan.getNoonHours() != null) {
                    lianBanTotalH += lianBan.getNoonHours();
                }
                if (lianBan.getNightHours() != null) {
                    lianBanTotalH += lianBan.getNightHours();
                }
            }
            mkf.setRemark(kqb.getRemark());
            daytitleSql = "day".concat(dayStr);
            daytitleSqlRe = daytitleSql.concat("Remark");
            LinShiHours lsh = personMapper.getLinShiHoursByNameAndDateStr(kqb.getName(), kqb.getDateStr());
            if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                for (TiaoXiu tiaoXiu : tiaoXiuList) {
                    if (tiaoXiu.getFromDateStr().equals(kqb.getDateStr())) {
                        totalTiaoXiuByDay -= (tiaoXiu.getHours() == 0 ? tiaoXiu.getTotalHours() : tiaoXiu.getHours());
                        beFStr = "3";
                        fromDateStrs += tiaoXiu.getToDateStr() + " ";
                        sb.append("被调休" + totalTiaoXiuByDay + "个小时 参考" + fromDateStrs);
                        personMapper.updateUsagedStatusByIdFromDate(tiaoXiu.getId());
                    }

                    if (tiaoXiu.getToDateStr().equals(kqb.getDateStr())) {
                        totalTiaoXiuByDay += (tiaoXiu.getHours() == 0 ? tiaoXiu.getTotalHours() : tiaoXiu.getHours());
                        beFStr = "6";
                        toDateStrs += tiaoXiu.getFromDateStr() + " ";
                        sb.append("调休了" + totalTiaoXiuByDay + "个小时 参考" + toDateStrs);
                        personMapper.updateUsagedStatusByIdToDate(tiaoXiu.getId());
                    }
                }
            }

            if (linShiEmpList.contains(kqb.getNameReal())) {
                if (lsh != null) {
                    dayNum = "206,206," + lsh.getHours();
                } else {
                    if (kqb.getClockResult() == 1) {
                        dayNum = "208,208," + (8.0 + kqb.getExtWorkHours());
                    } else if (kqb.getClockResult() == 7) {
                        if (kqb.getaOnTime() != null && kqb.getaOffTime() != null) {
                            totalOneDayHours += 4;
                        }
                        if (kqb.getpOnTime() != null && kqb.getpOffTime() != null) {
                            totalOneDayHours += 4;
                        }
                        totalOneDayHours += (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours());
                        dayNum = "208,208," + totalOneDayHours;
                    } else if (kqb.getClockResult() == 16) {
                        String[] ab = kqb.getRemark().split(",");
                        if (ab[0].equals("0.0")) {
                            if (kqb.getaOnTime() != null && kqb.getaOffTime() != null) {
                                totalOneDayHours += 4.0;
                            }
                        } else {
                            totalOneDayHours += Double.valueOf(ab[0]);
                        }

                        if (ab[1].equals("0.0")) {
                            if (kqb.getpOnTime() != null && kqb.getpOffTime() != null) {
                                totalOneDayHours += 4.0;
                            }
                        } else {
                            totalOneDayHours += Double.valueOf(ab[1]);
                        }
                        totalOneDayHours += kqb.getExtWorkHours();
                        dayNum = "208,208," + totalOneDayHours;
                    } else if (kqb.getClockResult() == 17) {
                        String[] plusValue = kqb.getRemark().split(",");
                        Double ona = 0.0;
                        Double onb = 0.0;
                        if (Double.valueOf(plusValue[0]) == 0.0) {
                            ona = 0.0;
                        } else {
                            ona = Double.valueOf(plusValue[0]);
                        }
                        if (Double.valueOf(plusValue[1]) == 0.0) {
                            onb = 0.0;
                        } else {
                            onb = Double.valueOf(plusValue[1]);
                        }

                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("18,").concat(beFStr.concat("18,"));
                            dayNum = dayNum.concat((lianBanTotalH + (ona + onb + totalTiaoXiuByDay) + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                        } else {
                            dayNum = "18,18,";
                            dayNum = dayNum.concat((lianBanTotalH + ona + onb + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                        }
                    }

                }

                mkf.setDayNum(dayNum);
                mkf.setDaytitleSql(daytitleSql);
                mkf.setDaytitleSqlRemark(daytitleSqlRe);
                mkf.setDayNumRemark(kqb.getRemark());
            } else {
                if (kqb.getWeek() == 6 || kqb.getWeek() == 7) {
                    if (kqb.getClockResult() == 1) {
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("18,").concat(beFStr.concat("18,"));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + (8.0 + totalTiaoXiuByDay) + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat((8.0 + totalTiaoXiuByDay) + "");
                            }
                        } else {
                            dayNum = "18,18,";
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("8.0");
                            }
                        }
                    } else if (kqb.getClockResult() == 4) {
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("04,").concat(beFStr.concat("04,"));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat((8.0 + totalTiaoXiuByDay) + "");
                            }
                        } else {
                            dayNum = "4,4,";
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("8.0");
                            }
                        }
                    } else if (kqb.getClockResult() == 6) {
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("18,").concat(beFStr.concat("18,"));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 0.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat((0.0 + totalTiaoXiuByDay) + "");
                            }
                        } else {
                            dayNum = "18,18,";
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 0.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("0.0");
                            }
                        }
                    } else if (kqb.getClockResult() == 13) {
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("13,").concat(beFStr.concat("13,"));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + (8.0 + totalTiaoXiuByDay) + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat((8.0 + totalTiaoXiuByDay) + "");
                            }
                        } else {
                            dayNum = "13,13,";
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("8.0");
                            }
                        }
                    } else if (kqb.getClockResult() == 11) {
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("11,").concat(beFStr.concat("11,"));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 0.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat((0.0 + totalTiaoXiuByDay) + "");
                            }
                        } else {
                            dayNum = "11,11,";
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 0.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("0.0");
                            }
                        }
                    } else if (kqb.getClockResult() == 2) {
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("02,").concat(beFStr.concat("02,"));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("8.0");
                            }
                        } else {
                            dayNum = "2,2,";
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("8.0");
                            }
                        }
                    } else if (kqb.getClockResult() == 12) {
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("12,").concat(beFStr.concat("12,"));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("8.0");
                            }
                        } else {
                            dayNum = "12,12,";
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("8.0");
                            }
                        }
                    } else if (kqb.getClockResult() == 3) {
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("03,").concat(beFStr.concat("03,"));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("8.0");
                            }
                        } else {
                            dayNum = "3,3,";
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                            } else {
                                dayNum = dayNum.concat("8.0");
                            }
                        }
                    } else if (kqb.getClockResult() == 7) {
                        aOnStr = "77,";
                        aOffStr = "77,";
                        boolean isComin = false;
                        qk = personMapper.getQianKaByDateAndEmpnoA(kqb.getEmpNo(), kqb.getDateStr());
                        ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                        if (qk != null && qk.getTimeStr() != null) {
                            timeStr = qk.getTimeStr().split(" ");
                            for (String str : timeStr) {
                                timeList.add(str.trim());
                            }
                            times = StringUtil.formTime(timeList);
                        }
                        if (kqb.getaOnTime() == null) {
                            //aOnStr = "7,";
                            aOnStr = "77,";
                            if (times != null) {
                                for (Time time : times) {
                                    if (time.after(ws.getMorningOnFrom()) && !time.after(ws.getMorningOnEnd()) || (time.equals(ws.getMorningOn()))) {
                                        // aOnStr = "17,";
                                        aOnStr = "67,";
                                    }
                                }
                            } else {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getMorningOn());
                                if (od != null) {
                                    aOnStr = "61";
                                }
                            }
                        } else {
                            //aOnStr = "1,";
                            aOnStr = "61,";
                        }
                        aOffStr = aOnStr;
                        if (kqb.getaOffTime() == null) {
                            aOffStr = "77,";
                            if (times != null) {
                                for (Time time : times) {
                                    if (time.after(ws.getMorningOffFrom()) && !time.after(ws.getMorningOffEnd()) || (time.equals(ws.getMorningOff()))) {
                                        if (aOnStr.equals("77,")) {
                                            aOffStr = "77,";
                                        } else {
                                            aOffStr = "67,";
                                        }

                                    }
                                }
                            } else {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getMorningOff());
                                if (od != null) {
                                    if (aOnStr.equals("77,")) {
                                        aOffStr = "77,";
                                    } else {
                                        aOffStr = "61,";
                                    }
                                }
                            }
                        }


                        pOnStr = "77,";
                        pOffStr = "77,";
                        if (kqb.getpOnTime() == null) {
                            if (times != null) {
                                for (Time time : times) {
                                    if (time.after(ws.getNoonOnFrom()) && !time.after(ws.getNoonOnEnd()) || (time.equals(ws.getNoonOn()))) {
                                        pOnStr = "67,";
                                    }
                                }
                            } else {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getNoonOn());
                                if (od != null) {
                                    pOnStr = "61";
                                }
                            }
                        } else {
                            pOnStr = "61,";
                        }

                        pOffStr = pOnStr;
                        if (kqb.getpOffTime() == null) {
                            if (times != null) {
                                for (Time time : times) {
                                    if (time.after(ws.getNoonOffFrom()) && !time.after(ws.getNoonOffEnd()) || (time.equals(ws.getNoonOff()))) {
                                        if (pOnStr == "77,") {
                                            pOffStr = "77,";
                                        } else {
                                            pOffStr = "67,";
                                        }
                                    }
                                }
                            } else {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getNoonOff());
                                if (od != null) {
                                    if (pOnStr == "77,") {
                                        pOffStr = "77,";
                                    } else {
                                        pOffStr = "61,";
                                    }
                                }
                            }

                        }
                        if (qk != null) {
                            kqb.setTimeStr(StringUtil.sortTimes2(kqb.getTimeStr(), qk.getTimeStr()));
                        }
                        if (nowDate == null) {
                            nowDate = new DateSplit();
                            nowDate.setDate(kqb.getDateStr());
                        }
                        nowDate.setDateFrom(ws.getMorningOn());
                        nowDate.setDateEnd(ws.getNoonOff());
                        jiabanHours = DateUtil.getJiaBanHoursByDateSplitAndTimeStr(nowDate, kqb.getTimeStr(), ws, kqb.getWorkType());
                        jiaHours = Double.valueOf(jiabanHours.split(",")[2]);

                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat(aOffStr).concat(beFStr.concat(pOffStr));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat((lianBanTotalH + (jiaHours + totalTiaoXiuByDay) + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                            } else {
                                dayNum = dayNum.concat((jiaHours + totalTiaoXiuByDay) + "");
                            }
                        } else {
                            dayNum = aOffStr + pOffStr;
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat((lianBanTotalH + jiaHours + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                            } else {
                                dayNum = dayNum.concat(jiaHours.toString());
                            }
                        }
                    } else if (kqb.getClockResult() == 8) {
                        ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                        Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo(), kqb.getDateStr());
                        OutClockIn oci = null;
                        ClockInSetUp csu = null;
                        Integer cishu = null;
                        if (out != null) {
                            csu = personMapper.getClockSetUpByDays(out.getInterDays());
                            oci = personMapper.getOutClockInByEmpNoAndDateA(kqb.getEmpNo(), kqb.getDateStr());
                            cishu = StringUtil.calTimesByOutClockIn(oci);
                            if (cishu >= csu.getDayClockInTimes()) {
                                dayNum = "1080,1080,";
                                if (!kqb.getHavePinShi().equals("1")) {
                                    dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                } else {
                                    dayNum = dayNum.concat("8.0");
                                }
                            } else {
                                dayNum = "1070,1070,";
                                if (!kqb.getHavePinShi().equals("1")) {
                                    dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                } else {
                                    dayNum = dayNum.concat("8.0");
                                }
                            }
                            if (oci != null) {
                                cishu = StringUtil.calTimesByOutClockIn(oci);
                                if (cishu >= csu.getDayClockInTimes()) {
                                    dayNum = "1080,1080,";
                                    if (!kqb.getHavePinShi().equals("1")) {
                                        dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                    } else {
                                        dayNum = dayNum.concat("8.0");
                                    }
                                } else {
                                    dayNum = "1070,1070,";
                                    if (!kqb.getHavePinShi().equals("1")) {
                                        dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                    } else {
                                        dayNum = dayNum.concat("8.0");
                                    }
                                }
                            } else {
                                dayNum = "1060,1060,";
                                if (!kqb.getHavePinShi().equals("1")) {
                                    dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                } else {
                                    dayNum = dayNum.concat("8.0");
                                }
                            }
                        } else {
                            Double aaa = 0.0;
                            if (kqb.getaOnTime() != null && kqb.getaOffTime() != null) {
                                aaa = 4.0;
                            }
                            if (kqb.getpOnTime() != null && kqb.getpOffTime() != null) {
                                aaa = aaa + 4.0;
                            }
                            dayNum = "18,18,";

                            if (totalTiaoXiuByDay >= 8) {
                                dayNum = "601,601,";
                            } else if (totalTiaoXiuByDay >= 4 && totalTiaoXiuByDay < 8) {
                                dayNum = "601,".concat("619,");
                                kqb.setRemark("0.0,".concat((totalTiaoXiuByDay - 4) + ""));
                            } else if (totalTiaoXiuByDay < 4 && totalTiaoXiuByDay > 0) {
                                dayNum = "619,8,";
                                kqb.setRemark("0.0," + totalTiaoXiuByDay);
                            }

                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat((lianBanTotalH + aaa + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                            } else {
                                dayNum = dayNum.concat(aaa.toString());
                            }
                        }
                    } else if (kqb.getClockResult() == 21) {
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("18,").concat(beFStr.concat("18,"));
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat((lianBanTotalH + 0.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                            } else {
                                dayNum = dayNum.concat((0.0 + totalTiaoXiuByDay) + "");
                            }
                        } else {
                            dayNum = "18,18,";
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat((lianBanTotalH + 0.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                            } else {
                                dayNum = dayNum.concat("0.0");
                            }
                        }
                    } else if (kqb.getClockResult() == 5) {
                        ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                        Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo(), kqb.getDateStr());
                        OutClockIn oci = null;
                        ClockInSetUp csu = null;
                        Integer cishu = null;
                        if (out != null) {
                            csu = personMapper.getClockSetUpByDays(out.getInterDays());
                            oci = personMapper.getOutClockInByEmpNoAndDateA(kqb.getEmpNo(), kqb.getDateStr());
                            if (oci != null) {
                                cishu = StringUtil.calTimesByOutClockIn(oci);
                                if (csu!=null && cishu >= csu.getDayClockInTimes()) {
                                    if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                        dayNum = beFStr.concat("1080,").concat(beFStr.concat("1080,"));
                                        if (!kqb.getHavePinShi().equals("1")) {
                                            dayNum = dayNum.concat((lianBanTotalH + 8.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                        } else {
                                            dayNum = dayNum.concat((8.0 + totalTiaoXiuByDay) + "");
                                        }
                                    } else {
                                        dayNum = "1080,1080,";
                                        if (!kqb.getHavePinShi().equals("1")) {
                                            dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                        } else {
                                            dayNum = dayNum.concat("8.0");
                                        }
                                    }
                                } else {
                                    if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                        dayNum = beFStr.concat("1070,").concat(beFStr.concat("1070,"));
                                        if (!kqb.getHavePinShi().equals("1")) {
                                            dayNum = dayNum.concat((lianBanTotalH + 8.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                        } else {
                                            dayNum = dayNum.concat((8.0 + totalTiaoXiuByDay) + "");
                                        }
                                    } else {
                                        dayNum = "1070,1070,";
                                        if (!kqb.getHavePinShi().equals("1")) {
                                            dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                        } else {
                                            dayNum = dayNum.concat("8.0");
                                        }
                                    }

                                }
                            } else {
                                if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                    dayNum = beFStr.concat("1060,").concat(beFStr.concat("1060,"));
                                    if (!kqb.getHavePinShi().equals("1")) {
                                        dayNum = dayNum.concat((lianBanTotalH + 8.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                    } else {
                                        dayNum = dayNum.concat((8.0 + totalTiaoXiuByDay) + "");
                                    }
                                } else {
                                    dayNum = "1060,1060,";
                                    if (!kqb.getHavePinShi().equals("1")) {
                                        dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                    } else {
                                        dayNum = dayNum.concat("8.0");
                                    }
                                }
                            }
                        } else {
                            if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                dayNum = beFStr.concat("18,").concat(beFStr.concat("18,"));
                                if (!kqb.getHavePinShi().equals("1")) {
                                    dayNum = dayNum.concat((lianBanTotalH + 0.0 + totalTiaoXiuByDay + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                } else {
                                    dayNum = dayNum.concat((0.0 + totalTiaoXiuByDay) + "");
                                }
                            } else {
                                dayNum = "18,18,";
                                if (!kqb.getHavePinShi().equals("1")) {
                                    dayNum = dayNum.concat((lianBanTotalH + 0.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                } else {
                                    dayNum = dayNum.concat("0.0");
                                }
                            }
                        }

                    } else if (kqb.getClockResult() == 17) {
                        String[] plusValue = kqb.getRemark().split(",");
                        Double ona = 0.0;
                        Double onb = 0.0;
                        if (Double.valueOf(plusValue[0]) == 0.0) {
                            ona = 4.0;
                        } else {
                            ona = Double.valueOf(plusValue[0]);
                        }

                        if (Double.valueOf(plusValue[1]) == 0.0) {
                            onb = 4.0;
                        } else {
                            onb = Double.valueOf(plusValue[1]);
                        }
                        if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                            dayNum = beFStr.concat("18,").concat(beFStr.concat("18,"));
                            dayNum = dayNum.concat((lianBanTotalH + (ona + onb + totalTiaoXiuByDay) + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                        } else {
                            dayNum = "18,18,";
                            dayNum = dayNum.concat((lianBanTotalH + ona + onb + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                        }
                    }
                    if (kqb.getClockResult() == 13) {
                        dayNum = dayNum.concat(kqb.getExtWorkHours().toString());
                    }

                    mkf.setDayNum(dayNum);
                    mkf.setDaytitleSql(daytitleSql);
                    mkf.setDaytitleSqlRemark(daytitleSqlRe);
                    mkf.setDayNumRemark(kqb.getRemark());
                } else {
                    Double subTotal = 0.0;
                    if (!youGuess.contains(kqb.getDateStr())) {
                        if (kqb.getClockResult() == 1) {
                            dayNum = "1,1,";
                        } else if (kqb.getClockResult() == 4) {
                            dayNum = "4,4,";
                        } else if (kqb.getClockResult() == 6) {
                            dayNum = "6,6,";
                        } else if (kqb.getClockResult() == 11) {
                            dayNum = "11,11,";
                        } else if (kqb.getClockResult() == 2) {
                            dayNum = "2,2,";
                        } else if (kqb.getClockResult() == 13) {
                            dayNum = "13,13,";
                        } else if (kqb.getClockResult() == 12) {
                            dayNum = "12,12,";
                        } else if (kqb.getClockResult() == 3) {
                            dayNum = "15,15,";
                        } else if (kqb.getClockResult() == 16) {
                            Double ho = 0.0;
                            String a;
                            String b;
                            String aaOnStr = "";
                            String ppOnStr = "";
                            String[] ab = kqb.getRemark().split(",");
                            if (Double.valueOf(ab[0]) == 0.0) {
                                if (kqb.getaOnTime() != null && kqb.getaOffTime() != null) {
                                    a = "1,";
                                } else if (kqb.getaOnTime() != null || kqb.getaOffTime() != null) {
                                    a = "7,";
                                    qk = personMapper.getQianKaByDateAndEmpnoA(kqb.getEmpNo(), kqb.getDateStr());
                                    ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                    if (qk != null && qk.getTimeStr() != null) {
                                        timeStr = qk.getTimeStr().split(" ");
                                        for (String str : timeStr) {
                                            timeList.add(str.trim());
                                        }
                                        times = StringUtil.formTime(timeList);
                                    }

                                    if (kqb.getaOnTime() == null) {
                                        aOnStr = "7,";
                                        if (times != null)
                                            for (Time time : times) {
                                                if (time.after(ws.getMorningOnFrom()) && !time.after(ws.getMorningOnEnd()) || (time.equals(ws.getMorningOn()))) {
                                                    aOnStr = "17,";
                                                }
                                            }
                                    } else {
                                        aOnStr = "1,";
                                    }

                                    if (aOnStr.equals("7,")) {
                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrABC(kqb.getDateStr() + " " + ws.getMorningOn(), kqb.getEmpNo());
                                        if (le != null) {
                                            aaOnStr = "6,";
                                        }
                                    }

                                    aOffStr = aOnStr;
                                    if (kqb.getaOffTime() == null) {
                                        aOffStr = "7,";
                                        if (times != null)
                                            for (Time time : times) {
                                                if (time.after(ws.getMorningOffFrom()) && !time.after(ws.getMorningOffEnd()) || (time.equals(ws.getMorningOff()))) {
                                                    if (aOnStr.equals("7,")) {
                                                        aOffStr = "7,";
                                                    } else {
                                                        aOffStr = "17,";
                                                    }
                                                }
                                            }
                                    }

                                    if (aOffStr.equals("7,")) {
                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrABC(kqb.getDateStr() + " " + ws.getMorningOff(), kqb.getEmpNo());
                                        if (le != null) {
                                            ppOnStr = "6,";
                                        }
                                    }


                                    if (aaOnStr.equals("6,") && ppOnStr.equals("6,")) {
                                        a = "6,";
                                    } else {
                                        a = pOffStr;
                                    }
//                                ho += 4.0;
                                } else {
                                    a = "8,";
                                    qk = personMapper.getQianKaByDateAndEmpnoA(kqb.getEmpNo(), kqb.getDateStr());
                                    ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                    if (qk != null && qk.getTimeStr() != null) {
                                        timeStr = qk.getTimeStr().split(" ");
                                        for (String str : timeStr) {
                                            timeList.add(str.trim());
                                        }
                                        times = StringUtil.formTime(timeList);
                                    }

                                    if (kqb.getaOnTime() == null) {
                                        aOnStr = "8,";
                                        if (times != null)
                                            for (Time time : times) {
                                                if (time.after(ws.getMorningOnFrom()) && !time.after(ws.getMorningOnEnd()) || (time.equals(ws.getMorningOn()))) {
                                                    aOnStr = "17,";
                                                }
                                            }
                                    } else {
                                        aOnStr = "1,";
                                    }

                                    if (aOnStr.equals("8,")) {
                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrABC(kqb.getDateStr() + " " + ws.getMorningOn(), kqb.getEmpNo());
                                        if (le != null) {
                                            aaOnStr = "6,";
                                        }
                                    }

                                    aOffStr = aOnStr;
                                    if (kqb.getaOffTime() == null) {
                                        aOffStr = "8,";
                                        if (times != null)
                                            for (Time time : times) {
                                                if (time.after(ws.getMorningOffFrom()) && !time.after(ws.getMorningOffEnd()) || (time.equals(ws.getMorningOff()))) {
                                                    if (aOnStr.equals("8,")) {
                                                        aOffStr = "8,";
                                                    } else {
                                                        aOffStr = "17,";
                                                    }
                                                }
                                            }
                                    }

                                    if (aOffStr.equals("8,")) {
                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrABC(kqb.getDateStr() + " " + ws.getMorningOff(), kqb.getEmpNo());
                                        if (le != null) {
                                            ppOnStr = "6,";
                                        }
                                    }


                                    if (aaOnStr.equals("6,") && ppOnStr.equals("6,")) {
                                        a = "6,";
                                    } else {
                                        a = pOffStr;
                                    }
//                                ho += 4.0;
                                }
                            } else {
                                a = "16,";
//                            ho += Double.valueOf(ab[0]);
                            }

                            if (Double.valueOf(ab[1]) == 0.0) {
                                if (kqb.getpOnTime() != null && kqb.getpOffTime() != null) {
                                    b = "1,";
                                } else if (kqb.getpOnTime() != null || kqb.getpOffTime() != null) {
                                    b = "7,";
                                    qk = personMapper.getQianKaByDateAndEmpnoA(kqb.getEmpNo(), kqb.getDateStr());
                                    ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                    if (qk != null && qk.getTimeStr() != null) {
                                        timeStr = qk.getTimeStr().split(" ");
                                        for (String str : timeStr) {
                                            timeList.add(str.trim());
                                        }
                                        times = StringUtil.formTime(timeList);
                                    }

                                    if (kqb.getaOnTime() == null) {
                                        pOnStr = "7,";
                                        if (times != null)
                                            for (Time time : times) {
                                                if (time.after(ws.getNoonOnFrom()) && !time.after(ws.getNoonOnEnd()) || (time.equals(ws.getNoonOn()))) {
                                                    pOnStr = "17,";
                                                }
                                            }
                                    } else {
                                        pOnStr = "1,";
                                    }

                                    if (pOnStr.equals("7,")) {
                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrABC(kqb.getDateStr() + " " + ws.getNoonOn(), kqb.getEmpNo());
                                        if (le != null) {
                                            aaOnStr = "6,";
                                        }
                                    }

                                    pOffStr = pOnStr;
                                    if (kqb.getpOffTime() == null) {
                                        pOffStr = "7,";
                                        if (times != null)
                                            for (Time time : times) {
                                                if (time.after(ws.getNoonOffFrom()) && !time.after(ws.getNoonOffEnd()) || (time.equals(ws.getNoonOff()))) {
                                                    if (pOnStr.equals("7,")) {
                                                        pOffStr = "7,";
                                                    } else {
                                                        pOffStr = "17,";
                                                    }
                                                }
                                            }
                                    }

                                    if (pOffStr.equals("7,")) {
                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrABC(kqb.getDateStr() + " " + ws.getNoonOff(), kqb.getEmpNo());
                                        if (le != null) {
                                            ppOnStr = "6,";
                                        }
                                    }


                                    if (ppOnStr.equals("6,") && aaOnStr.equals("6,")) {
                                        b = "6,";
                                    } else {
                                        b = pOffStr;
                                    }

                                } else {
                                    b = "8,";
                                    qk = personMapper.getQianKaByDateAndEmpnoA(kqb.getEmpNo(), kqb.getDateStr());
                                    ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                    if (qk != null && qk.getTimeStr() != null) {
                                        timeStr = qk.getTimeStr().split(" ");
                                        for (String str : timeStr) {
                                            timeList.add(str.trim());
                                        }
                                        times = StringUtil.formTime(timeList);
                                    }

                                    if (kqb.getpOnTime() == null) {
                                        pOnStr = "8,";
                                        if (times != null)
                                            for (Time time : times) {
                                                if (time.after(ws.getNoonOnFrom()) && !time.after(ws.getNoonOnEnd()) || (time.equals(ws.getNoonOn()))) {
                                                    pOnStr = "17,";
                                                }
                                            }
                                    } else {
                                        pOnStr = "1,";
                                    }

                                    if (pOnStr.equals("8,")) {
                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrABC(kqb.getDateStr() + " " + ws.getNoonOn(), kqb.getEmpNo());
                                        if (le != null) {
                                            aaOnStr = "6,";
                                        }
                                    }

                                    pOffStr = pOnStr;
                                    if (kqb.getpOffTime() == null) {
                                        pOffStr = "8,";
                                        if (times != null)
                                            for (Time time : times) {
                                                if (time.after(ws.getNoonOffFrom()) && !time.after(ws.getNoonOffEnd()) || (time.equals(ws.getNoonOff()))) {
                                                    if (pOnStr.equals("8,")) {
                                                        pOffStr = "8,";
                                                    } else {
                                                        pOffStr = "17,";
                                                    }
                                                }
                                            }
                                    }

                                    if (pOffStr.equals("8,")) {
                                        Leave le = personMapper.getLeaveByEmpIdAndDateStrABC(kqb.getDateStr() + " " + ws.getNoonOff(), kqb.getEmpNo());
                                        if (le != null) {
                                            ppOnStr = "6,";
                                        }
                                    }


                                    if (aaOnStr.equals("6,") && ppOnStr.equals("6,")) {
                                        b = "6,";
                                    } else {
                                        b = pOffStr;
                                    }
//                                ho += 4.0;
                                }
//                            ho += 4.0;
                            } else {
                                b = "16,";
//                            ho += Double.valueOf(ab[1]);
                            }

                            mkf.setDayNumRemark(kqb.getRemark());
                            if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                if (a.equals("16,") || a.equals("19,") || a.equals("8,")) {
                                    if (a.equals("16,") || a.equals("19,")) {
                                        splitDouble = kqb.getRemark().split(",");
                                        if (totalTiaoXiuByDay >= (4 - Double.valueOf(splitDouble[0]))) {
                                            a = "601,";
                                            extHours = totalTiaoXiuByDay - (4 - Double.valueOf(splitDouble[0]));
                                        } else {
                                            a = "619,";
                                            splitDouble[0] = (Double.valueOf(splitDouble[0]) + totalTiaoXiuByDay) + "";
                                        }
                                    } else if (a.equals("8,")) {
                                        splitDouble = kqb.getRemark().split(",");
                                        if (totalTiaoXiuByDay >= 4) {
                                            a = "601,";
                                            extHours = totalTiaoXiuByDay - 4;
                                        } else {
                                            a = "619,";
                                            splitDouble[0] = totalTiaoXiuByDay + "";
                                        }
                                    }
                                }

                                if (b.equals("16,") || b.equals("19,") || b.equals("8,")) {
                                    if (b.equals("16,") || b.equals("19,")) {
                                        splitDouble = kqb.getRemark().split(",");
                                        if (totalTiaoXiuByDay >= (4 - Double.valueOf(splitDouble[1]))) {
                                            b = "601,";
                                            extHours = totalTiaoXiuByDay - (4 - Double.valueOf(splitDouble[1]));
                                        } else {
                                            b = "619,";
                                            splitDouble[1] = (Double.valueOf(splitDouble[1]) + totalTiaoXiuByDay) + "";
                                        }
                                    } else if (b.equals("8,")) {
                                        splitDouble = kqb.getRemark().split(",");
                                        if (totalTiaoXiuByDay >= 4) {
                                            b = "601,";
                                            extHours = totalTiaoXiuByDay - 4;
                                        } else {
                                            b = "619,";
                                            splitDouble[1] = totalTiaoXiuByDay + "";
                                        }
                                    }
                                }
                                mkf.setDayNumRemark(splitDouble[0] + "," + splitDouble[1]);
                            }
                            dayNum = a + b;
                        } else if (kqb.getClockResult() == 18) {
                            if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                dayNum = beFStr.concat("20").concat(beFStr.concat("20"));
                            } else {
                                dayNum = "20,20,";
                            }
                        } else if (kqb.getClockResult() == 19) {
                            if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                dayNum = beFStr.concat("21").concat(beFStr.concat("21"));
                            } else {
                                dayNum = "21,21,";
                            }
                        } else if (kqb.getClockResult() == 20) {
                            if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                dayNum = beFStr.concat("22").concat(beFStr.concat("22"));
                            } else {
                                dayNum = "22,22,";
                            }
                        } else if (kqb.getClockResult() == 21) {
                            if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                dayNum = beFStr.concat("23").concat(beFStr.concat("23"));
                            } else {
                                dayNum = "23,23,";
                            }
                        } else if (kqb.getClockResult() == 17) {
                            qk = personMapper.getQianKaByDateAndEmpnoA(kqb.getEmpNo(), kqb.getDateStr());
                            ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                            if (qk != null) {
                                String[] ab = kqb.getRemark().split(",");
                                Double hoa = Double.valueOf(ab[0]);
                                aOnStr = "19,";
                                aOffStr = "19,";
                                boolean isComin = false;
                                if (qk != null && qk.getTimeStr() != null) {
                                    timeStr = qk.getTimeStr().split(" ");
                                    for (String str : timeStr) {
                                        timeList.add(str.trim());
                                    }
                                    times = StringUtil.formTime(timeList);
                                }
                                if (kqb.getaOnTime() == null) {
                                    aOnStr = "19,";
                                    hoa = Double.valueOf(ab[0]);
                                    if (times != null)
                                        for (Time time : times) {
                                            if (time.after(ws.getMorningOnFrom()) && !time.after(ws.getMorningOnEnd()) || (time.equals(ws.getMorningOn()))) {
                                                aOnStr = "17,";
                                                hoa = 4.0;
                                            }
                                        }
                                } else {
                                    aOnStr = "1,";
                                    hoa = 4.0;
                                }
                                aOffStr = aOnStr;
                                if (kqb.getaOffTime() == null) {
                                    aOffStr = "19,";
                                    hoa = Double.valueOf(ab[0]);
                                    if (times != null)
                                        for (Time time : times) {
                                            if (time.after(ws.getMorningOffFrom()) && !time.after(ws.getMorningOffEnd()) || (time.equals(ws.getMorningOff()))) {
                                                if (aOnStr.equals("19,")) {
                                                    aOffStr = "19,";
                                                    hoa = Double.valueOf(ab[0]);
                                                } else {
                                                    aOffStr = "17,";
                                                    hoa = 4.0;
                                                }

                                            }
                                        }

                                }


                                pOnStr = "19,";
                                pOffStr = "19,";
                                Double hob = Double.valueOf(ab[0]);
                                if (kqb.getpOnTime() == null) {
                                    if (times != null)
                                        for (Time time : times) {
                                            if (time.after(ws.getNoonOnFrom()) && !time.after(ws.getNoonOnEnd()) || (time.equals(ws.getNoonOn()))) {
                                                pOnStr = "17,";
                                                hob = 4.0;
                                            }
                                        }
                                } else {
                                    pOnStr = "1,";
                                    hob = 4.0;
                                }

                                pOffStr = pOnStr;
                                if (kqb.getpOffTime() == null) {
                                    if (times != null)
                                        for (Time time : times) {
                                            if (time.after(ws.getNoonOffFrom()) && !time.after(ws.getNoonOffEnd()) || (time.equals(ws.getNoonOff()))) {
                                                if (pOnStr == "19,") {
                                                    pOffStr = "19,";
                                                    hob = Double.valueOf(ab[0]);
                                                } else {
                                                    pOffStr = "17,";
                                                    hob = 4.0;
                                                }
                                            }
                                        }

                                }
                                dayNum = aOffStr + pOffStr;
                                mkf.setDayNumRemark(kqb.getRemark());
                            } else {
                                Double ho = 0.0;
                                String a;
                                String b;
                                String[] ab = kqb.getRemark().split(",");
                                if (ab[0] != null && ab[0].trim().length() != 0 && Double.valueOf(ab[0]) == 0.0) {
                                    a = "1,";
                                    ho += 4.0;
                                } else if (ab[0] == null || ab[0].trim().length() == 0) {
                                    a = "8,";
                                    ho += 0.0;
                                } else {
                                    a = "19,";
                                    ho += Double.valueOf(ab[0]);
                                }

                                if (Double.valueOf(ab[1]) == 0.0) {
                                    b = "1,";
                                    ho += 4.0;
                                } else {
                                    b = "19,";
                                    ho += Double.valueOf(ab[1]);
                                }

                                mkf.setDayNumRemark(kqb.getRemark());

                                if (tiaoXiuList != null && tiaoXiuList.size() > 0) {
                                    if (a.equals("16,") || a.equals("19,") || a.equals("8,")) {
                                        if (a.equals("16,") || a.equals("19,")) {
                                            splitDouble = kqb.getRemark().split(",");
                                            if (totalTiaoXiuByDay >= (4 - Double.valueOf(splitDouble[0]))) {
                                                a = "601,";
                                                extHours = totalTiaoXiuByDay - (4 - Double.valueOf(splitDouble[0]));
                                            } else {
                                                a = "619,";
                                                splitDouble[0] = (Double.valueOf(splitDouble[0]) + totalTiaoXiuByDay) + "";
                                            }
                                        } else if (a.equals("8,")) {
                                            splitDouble = kqb.getRemark().split(",");
                                            if (totalTiaoXiuByDay >= 4) {
                                                a = "601,";
                                                extHours = totalTiaoXiuByDay - 4;
                                            } else {
                                                a = "619,";
                                                splitDouble[0] = totalTiaoXiuByDay + "";
                                            }
                                        }
                                    }

                                    if (b.equals("16,") || b.equals("19,") || b.equals("8,")) {
                                        if (b.equals("16,") || b.equals("19,")) {
                                            splitDouble = kqb.getRemark().split(",");
                                            if (totalTiaoXiuByDay >= (4 - Double.valueOf(splitDouble[1]))) {
                                                b = "601,";
                                                extHours = totalTiaoXiuByDay - (4 - Double.valueOf(splitDouble[1]));
                                            } else {
                                                b = "619,";
                                                splitDouble[1] = (Double.valueOf(splitDouble[1]) + totalTiaoXiuByDay) + "";
                                            }
                                        } else if (b.equals("8,")) {
                                            splitDouble = kqb.getRemark().split(",");
                                            if (totalTiaoXiuByDay >= 4) {
                                                b = "601,";
                                                extHours = totalTiaoXiuByDay - 4;
                                            } else {
                                                b = "619,";
                                                splitDouble[1] = totalTiaoXiuByDay + "";
                                            }
                                        }
                                    }
                                    mkf.setDayNumRemark(splitDouble[0] + "," + splitDouble[1]);
                                }
                                dayNum = a + b;
                            }
                        } else if (kqb.getClockResult() == 7) {
                            aOnStr = "7,";
                            aOffStr = "7,";
                            boolean isComin = false;
                            qk = personMapper.getQianKaByDateAndEmpnoA(kqb.getEmpNo(), kqb.getDateStr());
                            ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                            if (qk != null && qk.getTimeStr() != null) {
                                timeStr = qk.getTimeStr().split(" ");
                                for (String str : timeStr) {
                                    timeList.add(str.trim());
                                }
                                times = StringUtil.formTime(timeList);
                            }
                            if (kqb.getaOnTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getMorningOn());
                                aOnStr = "7,";
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getMorningOnFrom()) && !time.after(ws.getMorningOnEnd()) || (time.equals(ws.getMorningOn()))) {
                                            aOnStr = "17,";
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        aOnStr = "2,";
                                    }
                                }
                            } else {
                                aOnStr = "1,";
                            }
                            aOffStr = aOnStr;
                            if (kqb.getaOffTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getMorningOff());
                                aOffStr = "7,";
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getMorningOffFrom()) && !time.after(ws.getMorningOffEnd()) || (time.equals(ws.getMorningOff()))) {
                                            if (aOnStr.equals("7,")) {
                                                aOffStr = "7,";
                                            } else {
                                                aOffStr = "17,";
                                            }

                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        if (aOnStr.equals("7,")) {
                                            aOffStr = "7,";
                                        } else {
                                            aOffStr = "2,";
                                        }
                                    }
                                }

                            }


                            pOnStr = "7,";
                            pOffStr = "7,";
                            if (kqb.getpOnTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getNoonOn());
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getNoonOnFrom()) && !time.after(ws.getNoonOnEnd()) || (time.equals(ws.getNoonOn()))) {
                                            pOnStr = "17,";
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        pOnStr = "2,";
                                    }
                                }
                            } else {
                                pOnStr = "1,";
                            }

                            pOffStr = pOnStr;
                            if (kqb.getpOffTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getNoonOff());
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getNoonOffFrom()) && !time.after(ws.getNoonOffEnd()) || (time.equals(ws.getNoonOff()))) {
                                            if (pOnStr == "7,") {
                                                pOffStr = "7,";
                                            } else {
                                                pOffStr = "17,";
                                            }
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        if (pOnStr == "7,") {
                                            pOffStr = "7,";
                                        } else {
                                            pOffStr = "2,";
                                        }
                                    } else {
                                        pOffStr = "7,";
                                    }
                                }

                            }

                            if (qk == null) {
                                if (kqb.getaOnTime() == null && kqb.getaOffTime() == null) {
                                    Leave le = personMapper.getLeaveByEmpIdAndDateStrAAAB(kqb.getDateStr() + " " + ws.getMorningOn(), kqb.getDateStr() + " " + ws.getMorningOff(), kqb.getEmpNo());
                                    if (le != null) {
                                        if (le.getType() == 0) {
                                            aOffStr = "6,";
                                        } else if (le.getType() == 1) {
                                            aOffStr = "2,";
                                        } else if (le.getType() == 3) {
                                            aOffStr = "21,";
                                        } else if (le.getType() == 4) {
                                            aOffStr = "20,";
                                        } else if (le.getType() == 5) {
                                            aOffStr = "22,";
                                        } else if (le.getType() == 6) {
                                            aOffStr = "23,";
                                        }
                                    }
                                } else if (kqb.getpOnTime() == null && kqb.getpOffTime() == null) {
                                    Leave le = personMapper.getLeaveByEmpIdAndDateStrAAAB(kqb.getDateStr() + " " + ws.getNoonOn(), kqb.getDateStr() + " " + ws.getNoonOff(), kqb.getEmpNo());
                                    if (le != null) {
                                        if (le.getType() == 0) {
                                            pOffStr = "6,";
                                        } else if (le.getType() == 1) {
                                            pOffStr = "2,";
                                        } else if (le.getType() == 3) {
                                            pOffStr = "21,";
                                        } else if (le.getType() == 4) {
                                            pOffStr = "20,";
                                        } else if (le.getType() == 5) {
                                            pOffStr = "22,";
                                        } else if (le.getType() == 6) {
                                            pOffStr = "23,";
                                        }
                                    }
                                }
                            }

                            if (aOnStr.equals("7,")) {
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getMorningOn());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDateAM(kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        aOnStr = "108,";
                                    } else {
                                        aOnStr = "106,";
                                    }
                                } else {
                                    aOnStr = "7,";
                                }
                            }

                            if (aOffStr.equals("7,")) {
                                aOffStr = aOnStr;
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getMorningOff());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDateAM(kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        aOffStr = "108,";
                                    } else {
                                        aOffStr = "106,";
                                    }
                                } else {
                                    aOffStr = "7,";
                                }
                            }

                            if (pOnStr.equals("7,")) {
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo() == null ? "" : kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getNoonOn());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDatePM(kqb.getEmpNo() == null ? "" : kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        pOnStr = "108,";
                                    } else {
                                        pOnStr = "106,";
                                    }
                                } else {
                                    pOnStr = "7,";
                                }
                            }

                            if (pOffStr.equals("7,")) {
                                pOffStr = pOnStr;
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo() == null ? "" : kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getNoonOff());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDatePM(kqb.getEmpNo() == null ? "" : kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        pOffStr = "108,";
                                    } else {
                                        pOffStr = "106,";
                                    }
                                } else {
                                    pOffStr = "7,";
                                }
                            }

                            dayNum = aOffStr + pOffStr;
                        } else if (kqb.getClockResult() == 8) {
                            aOnStr = "8,";
                            aOffStr = "8,";
                            boolean isComin = false;
                            qk = personMapper.getQianKaByDateAndEmpnoA(kqb.getEmpNo() == null ? "" : kqb.getEmpNo(), kqb.getDateStr());
                            ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                            if (qk != null && qk.getTimeStr() != null) {
                                timeStr = qk.getTimeStr().split(" ");
                                for (String str : timeStr) {
                                    timeList.add(str.trim());
                                }
                                times = StringUtil.formTime(timeList);
                            }
                            if (kqb.getaOnTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getMorningOn());
                                aOnStr = "8,";
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getMorningOnFrom()) && !time.after(ws.getMorningOnEnd()) || (time.equals(ws.getMorningOn()))) {
                                            aOnStr = "17,";
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        aOnStr = "2,";
                                    }
                                }
                            } else {
                                aOnStr = "1,";
                            }
                            aOffStr = aOnStr;
                            if (kqb.getaOffTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getMorningOff());
                                aOffStr = "8,";
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getMorningOffFrom()) && !time.after(ws.getMorningOffEnd()) || (time.equals(ws.getMorningOff()))) {
                                            if (aOnStr.equals("8,")) {
                                                aOffStr = "8,";
                                            } else {
                                                aOffStr = "17,";
                                            }

                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        if (aOnStr.equals("8,")) {
                                            aOffStr = "8,";
                                        } else {
                                            aOffStr = "2,";
                                        }
                                    }
                                }
                                if (aOnStr.equals("1,") && aOffStr.equals("8,") || aOnStr.equals("8,") && aOffStr.equals("1,")) {
                                    aOffStr = "7,";
                                }

                            }

                            pOnStr = "8,";
                            pOffStr = "8,";
                            if (kqb.getpOnTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getNoonOn());
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getNoonOnFrom()) && !time.after(ws.getNoonOnEnd()) || (time.equals(ws.getNoonOn()))) {
                                            pOnStr = "17,";
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        pOnStr = "2,";
                                    }
                                }
                            } else {
                                pOnStr = "1,";
                            }

                            pOffStr = pOnStr;
                            if (kqb.getpOffTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getNoonOff());
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getNoonOffFrom()) && !time.after(ws.getNoonOffEnd()) || (time.equals(ws.getNoonOff()))) {
                                            if (pOnStr == "8,") {
                                                pOffStr = "7,";
                                            } else {
                                                pOffStr = "17,";
                                            }
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        if (pOnStr == "8,") {
                                            pOffStr = "8,";
                                        } else {
                                            pOffStr = "2,";
                                        }
                                    } else {
                                        pOffStr = "8,";
                                    }
                                }

                                if ((pOnStr.equals("1,") && pOffStr.equals("8,")) || pOnStr.equals("8,") && pOffStr.equals("1,")) {
                                    pOffStr = "7,";
                                }


                            }


                            if (aOffStr.equals("8,")) {
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDate(kqb.getEmpNo() == null ? "" : kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getMorningOn(), kqb.getDateStr() + " " + ws.getMorningOff());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDateA(kqb.getEmpNo() == null ? "" : kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        cishu = StringUtil.calTimesByOutClockIn(oci);
                                        if (cishu >= csu.getDayClockInTimes()) {
                                            aOffStr = "108,";
                                        } else {
                                            aOffStr = "107,";
                                        }
                                    } else {
                                        aOffStr = "106,";
                                    }
                                } else {
                                    aOffStr = "8,";
                                }
                            }


                            if (pOffStr.equals("8,")) {
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDate(kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getNoonOn(), kqb.getDateStr() + " " + ws.getNoonOff());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDateA(kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        cishu = StringUtil.calTimesByOutClockIn(oci);
                                        if (cishu >= csu.getDayClockInTimes()) {
                                            pOffStr = "108,";
                                        } else {
                                            pOffStr = "107,";
                                        }
                                    } else {
                                        pOffStr = "106,";
                                    }
                                } else {
                                    pOffStr = "8,";
                                }
                            }
                            aOffStrRe = "0.0";
                            pOffStrRe = "0.0";

                            if (aOffStr.equals("8,") || pOffStr.equals("8,")) {
                                if (aOffStr.equals("8,")) {
                                    if (totalTiaoXiuByDay >= 4) {
                                        aOffStr = "601,";
                                        aOffStrRe = "0.0,";
                                        totalTiaoXiuByDay -= 4;
                                    } else if (totalTiaoXiuByDay < 4 && totalTiaoXiuByDay > 0) {
                                        aOffStr = "619,";
                                        aOffStr = totalTiaoXiuByDay + "";
                                        totalTiaoXiuByDay = 0.0;
                                    }

                                }

                                if (pOffStr.equals("8,")) {
                                    if (totalTiaoXiuByDay >= 4) {
                                        pOffStr = "601,";
                                        pOffStrRe = "0.0";
                                        totalTiaoXiuByDay -= 4;
                                    } else if (totalTiaoXiuByDay < 4 && totalTiaoXiuByDay > 0) {
                                        pOffStr = "619,";
                                        pOffStr = totalTiaoXiuByDay + "";
                                        totalTiaoXiuByDay = 0.0;
                                    }
                                }
                            }

                            dayNum = aOffStr + pOffStr;
                            kqb.setRemark(aOffStrRe.concat(pOffStrRe));
                        }
                        if (kqb.getClockResult() == 13) {
                            dayNum = dayNum.concat((kqb.getExtWorkHours() - 8.0) + "");
                        } else {
                            if (!kqb.getHavePinShi().equals("1")) {
                                dayNum = dayNum.concat(lianBanTotalH + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + totalTiaoXiuByDay + "");
                            } else {
                                dayNum = dayNum.concat("0.0");
                            }
                        }
                        mkf.setDayNum(dayNum);
                        mkf.setDaytitleSql(daytitleSql);
                        mkf.setDayNumRemark(kqb.getRemark());
                        mkf.setDaytitleSqlRemark(daytitleSqlRe);
                    } else {
                        if (kqb.getClockResult() == 1) {
                            dayNum = "18,18,";
                        } else if (kqb.getClockResult() == 4) {
                            dayNum = "4,4,";
                        } else if (kqb.getClockResult() == 6) {
                            dayNum = "6,6,";
                        } else if (kqb.getClockResult() == 11) {
                            dayNum = "11,11,";
                        } else if (kqb.getClockResult() == 2) {
                            dayNum = "2,2,";
                        } else if (kqb.getClockResult() == 12) {
                            dayNum = "12,12,";
                        } else if (kqb.getClockResult() == 3) {
                            dayNum = "3,3,";
                        } else if (kqb.getClockResult() == 7) {
                            aOnStr = "77,";
                            aOffStr = "77,";
                            boolean isComin = false;
                            qk = personMapper.getQianKaByDateAndEmpnoA(kqb.getEmpNo(), kqb.getDateStr());
                            ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                            if (qk != null && qk.getTimeStr() != null) {
                                timeStr = qk.getTimeStr().split(" ");
                                for (String str : timeStr) {
                                    timeList.add(str.trim());
                                }
                                times = StringUtil.formTime(timeList);
                            }
                            if (kqb.getaOnTime() == null) {
                                //aOnStr = "7,";
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getMorningOn());
                                aOnStr = "77,";
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getMorningOnFrom()) && !time.after(ws.getMorningOnEnd()) || (time.equals(ws.getMorningOn()))) {
                                            // aOnStr = "17,";
                                            aOnStr = "67,";
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        aOnStr = "2,";
                                    }
                                }
                            } else {
                                //aOnStr = "1,";
                                aOnStr = "61,";
                            }
                            aOffStr = aOnStr;
                            if (kqb.getaOffTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getMorningOff());
                                aOffStr = "77,";
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getMorningOffFrom()) && !time.after(ws.getMorningOffEnd()) || (time.equals(ws.getMorningOff()))) {
                                            if (aOnStr.equals("77,")) {
                                                aOffStr = "77,";
                                            } else {
                                                aOffStr = "67,";
                                            }
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        if (aOnStr.equals("77,")) {
                                            aOffStr = "77,";
                                        } else {
                                            aOffStr = "2,";
                                        }
                                    }
                                }
                            }


                            pOnStr = "77,";
                            pOffStr = "77,";
                            if (kqb.getpOnTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getNoonOn());
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getNoonOnFrom()) && !time.after(ws.getNoonOnEnd()) || (time.equals(ws.getNoonOn()))) {
                                            pOnStr = "67,";
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        pOnStr = "2,";
                                    }
                                }
                            } else {
                                pOnStr = "61,";
                            }

                            pOffStr = pOnStr;
                            if (kqb.getpOffTime() == null) {
                                od = personMapper.getOutDanByEmpNoAndDateStr(kqb.getEmpNo(), kqb.getDateStr(), kqb.getDateStr() + " " + ws.getNoonOff());
                                if (times != null) {
                                    for (Time time : times) {
                                        if (time.after(ws.getNoonOffFrom()) && !time.after(ws.getNoonOffEnd()) || (time.equals(ws.getNoonOff()))) {
                                            if (pOnStr == "77,") {
                                                pOffStr = "77,";
                                            } else {
                                                pOffStr = "67,";
                                            }
                                        }
                                    }
                                } else {
                                    if (od != null) {
                                        if (pOnStr == "77,") {
                                            pOffStr = "77,";
                                        } else {
                                            pOffStr = "2,";
                                        }
                                    }
                                }
                            }

                            if (aOnStr.equals("77,")) {
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getMorningOn());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDateAM(kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        aOnStr = "1080,";
                                    } else {
                                        aOnStr = "1060,";
                                    }
                                } else {
                                    aOnStr = "77,";
                                }
                            }

                            if (aOffStr.equals("77,")) {
                                aOffStr = aOnStr;
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getMorningOff());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDateAM(kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        aOffStr = "1080,";
                                    } else {
                                        aOffStr = "1060,";
                                    }
                                } else {
                                    aOffStr = "77,";
                                }
                            }


                            if (pOnStr.equals("77,")) {
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getNoonOn());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDatePM(kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        pOnStr = "1080,";
                                    } else {
                                        pOnStr = "1060,";
                                    }
                                } else {
                                    pOnStr = "77,";
                                }
                            }

                            if (pOffStr.equals("77,")) {
                                pOffStr = pOnStr;
                                ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                                Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo(), kqb.getDateStr() + " " + ws.getNoonOff());
                                OutClockIn oci = null;
                                ClockInSetUp csu = null;
                                Integer cishu = null;
                                if (out != null) {
                                    csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                    oci = personMapper.getOutClockInByEmpNoAndDatePM(kqb.getEmpNo(), kqb.getDateStr());
                                    if (oci != null) {
                                        pOffStr = "1080,";
                                    } else {
                                        pOffStr = "1060,";
                                    }
                                } else {
                                    pOffStr = "77,";
                                }
                            }

                            dayNum = aOffStr + pOffStr;
                        } else if (kqb.getClockResult() == 8) {
                            ws = personMapper.getWorkSetByMonthAndPositionLevelA(kqb.getYearMonth(), kqb.getPositionLevel());
                            Out out = personMapper.getOutDanByEmpNoandDateOnly(kqb.getEmpNo(), kqb.getDateStr());
                            OutClockIn oci = null;
                            ClockInSetUp csu = null;
                            Integer cishu = null;
                            if (out != null) {

                                csu = personMapper.getClockSetUpByDays(out.getInterDays());
                                oci = personMapper.getOutClockInByEmpNoAndDateA(kqb.getEmpNo(), kqb.getDateStr());
                                if (oci != null) {
                                    dayNum = "1080,1080,";
                                    if (!kqb.getHavePinShi().equals("1")) {
                                        dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                    } else {
                                        dayNum = dayNum.concat("8.0");
                                    }
                                    cishu = StringUtil.calTimesByOutClockIn(oci);
//                                    if (cishu >= csu.getDayClockInTimes()) {
//                                        dayNum = "1080,1080,";
//                                        if (!kqb.getHavePinShi().equals("1")) {
//                                            dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
//                                        } else {
//                                            dayNum = dayNum.concat("8.0");
//                                        }
//                                    } else {
//                                        dayNum = "1070,1070,";
//                                        if (!kqb.getHavePinShi().equals("1")) {
//                                            dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
//                                        } else {
//                                            dayNum = dayNum.concat("8.0");
//                                        }
//                                    }
                                } else {
                                    dayNum = "1060,1060,";
                                    if (!kqb.getHavePinShi().equals("1")) {
                                        dayNum = dayNum.concat((lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                    } else {
                                        dayNum = dayNum.concat("8.0");
                                    }
                                }
                            } else {
                                dayNum = "8,8,";

                                if (totalTiaoXiuByDay >= 8) {
                                    dayNum = "601,601,";
                                } else if (totalTiaoXiuByDay >= 4 && totalTiaoXiuByDay < 8) {
                                    dayNum = "601,".concat("619,");
                                    kqb.setRemark("0.0,".concat((totalTiaoXiuByDay - 4) + ""));
                                } else if (totalTiaoXiuByDay < 4 && totalTiaoXiuByDay > 0) {
                                    dayNum = "619,8,";
                                    kqb.setRemark("0.0," + totalTiaoXiuByDay);
                                }


                                if (!kqb.getHavePinShi().equals("1")) {
                                    dayNum = dayNum.concat((lianBanTotalH + 0.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours())) + "");
                                } else {
                                    dayNum = dayNum.concat("0.0");
                                }
                            }

                        }


                        if (!kqb.getHavePinShi().equals("1")) {
                            dayNum = dayNum.concat(lianBanTotalH + 8.0 + (kqb.getExtWorkHours() == null ? 0.0 : kqb.getExtWorkHours()) + "");
                        } else {
                            dayNum = dayNum.concat("8.0");
                        }
                        mkf.setDayNum(dayNum);
                        mkf.setDaytitleSql(daytitleSql);
                        mkf.setDaytitleSqlRemark(daytitleSqlRe);
                        mkf.setDayNumRemark(kqb.getRemark());
                    }
                }
            }

            monthKQInfoList.add(mkf);

            if (mkf.getId() != null) {
                mkf.setNameReal(kqb.getNameReal());
                personMapper.updateMonthKQInfoByCheckKQBean(mkf);
            } else {
                mkf.setNameReal(kqb.getNameReal());
                personMapper.saveMonthKQInfoByCheckKQBean(mkf);
            }
        }

        for (OutClockIn oci : outClockInList) {
            tiaoXiuList = personMapper.getTiaoXiuDanByDateStrAndNoUse(oci.getClockInDateStr());
            for (TiaoXiu tx : tiaoXiuList) {
                day = "";
                yearMonth = "";
                fromDay = "";
                fromYM = "";
                dayNumRemark = "";
                rb = null;
                daytitleSqlTo = "";
                daytitleSqlReTo = "";
                if (tx.getFromDateStr().equals(oci.getClockInDateStr()) && tx.getAusaged() == 0) {
                    day = tx.getFromDateStr().split("-")[2];
                    beFStr = "6";
                    yearMonth = tx.getFromDateStr().split("-")[0] + "-" + tx.getFromDateStr().split("-")[1];

                    daytitleSql = "day".concat(day);
                    daytitleSqlRe = daytitleSql.concat("Remark");

                    fromDay = tx.getToDateStr().split("-")[2];
                    fromYM = tx.getToDateStr().split("-")[0] + "-" + tx.getToDateStr().split("-")[1];

                    daytitleSqlTo = "day".concat(fromDay);
                    daytitleSqlReTo = daytitleSqlTo.concat("Remark");

                    rb = personMapper.getMKbyYearMonthAndDayNumAndEmpNo(tx.getEmpNo(), yearMonth, daytitleSql, daytitleSqlRe);
                    personMapper.deleteMKBeanByYMAndDNAEN(tx.getEmpNo(), yearMonth, daytitleSql, daytitleSqlRe);
                    if (rb == null || rb.getDayNum() == null) {
                        rb = new ReturnBean();
                        rb.setDayNum("618,618,0");
                        rb.setDayNumRemark("对调给" + tx.getToDateStr());
                    } else {
                        asplit = rb.getDayNum().split(",");
                        if (asplit[0].length() <= 1) {
                            asplit[0] = beFStr.concat("0").concat(asplit[0]);
                        } else {
                            asplit[0] = beFStr.concat(asplit[0]);
                        }
                        if (asplit[1].length() <= 1) {
                            asplit[1] = beFStr.concat("0").concat(asplit[1]);
                        } else {
                            asplit[1] = beFStr.concat(asplit[1]);
                        }

                        rb.setDayNum(asplit[0] + "," + asplit[1] + "," + asplit[2]);
                    }
                    personMapper.updateTiaoXiuById(tx.getId(), ((rb == null || rb.getDayNum() == null) ? "" : rb.getDayNum()),
                            ((rb == null || rb.getDayNumRemark() == null) ? "" : rb.getDayNumRemark()));

                }
                if (tx.getToDateStr().equals(oci.getClockInDateStr()) && tx.getUsaged() == 0) {
                    beFStr = "6";
                    day = tx.getToDateStr().split("-")[2];
                    yearMonth = tx.getToDateStr().split("-")[0] + "-" + tx.getToDateStr().split("-")[1];

                    daytitleSqlTo = "day".concat(day);
                    daytitleSqlReTo = daytitleSqlTo.concat("Remark");

                    fromDay = tx.getFromDateStr().split("-")[2];
                    fromYM = tx.getFromDateStr().split("-")[0] + "-" + tx.getFromDateStr().split("-")[1];

                    daytitleSql = "day".concat(fromDay);
                    daytitleSqlRe = daytitleSql.concat("Remark");

                    if (tx.getAusaged() == 0) {
                        rb = personMapper.getMKbyYearMonthAndDayNumAndEmpNo(tx.getEmpNo(), fromYM, daytitleSql, daytitleSqlRe);
                    } else {
                        rb = new ReturnBean();
                        rb.setDayNum(tx.getSaveStr() == null ? "" : tx.getSaveStr());
                        rb.setDayNumRemark(tx.getSaveStrRemark() == null ? "" : tx.getSaveStrRemark());
                    }

                    personMapper.deleteMKBeanByYMAndDNAEN(tx.getEmpNo(), fromYM, daytitleSql, daytitleSqlRe);

                    if (rb == null || rb.getDayNum() == null) {
                        rb = new ReturnBean();
                        rb.setDayNum("318,318,0");
                        rb.setDayNumRemark("被对调" + tx.getFromDateStr());
                    }
                    personMapper.updateToDateMKByParam(tx.getEmpNo(), yearMonth, rb.getDayNum(), rb.getDayNumRemark(), daytitleSqlTo, daytitleSqlReTo);
                    personMapper.updateTiaoXiuStatusById(tx.getId());
                }
            }
        }
    }

    public void deleteQianKaDateToMysql(Integer id) throws Exception {
        personMapper.deleteQianKaDateToMysql(id);
    }

    public Integer getDeptIdByDeptName(String deptName) throws Exception {
        return personMapper.getDeptIdByDeptName(deptName);
    }


    public void updateEmployeeDeptIdById(Integer empId, Integer deptId) throws Exception {
        personMapper.updateEmployeeDeptIdById(empId, deptId);
    }


    public void saveQKData(List<QYWXSPFROM> fromList) throws Exception {
        //empNo,date,timeStr,type,remark
        QianKa qk = null;
        String empNo = null;
        Leave leave = null;
        Employee employee = null;
        String empId = null;
        Out out = null;
        for (QYWXSPFROM qyxf : fromList) {
            if (qyxf.getSpname().equals("补卡申请")) {
                empNo = personMapper.getEmpNoByUserId(qyxf.getApply_user_id());
                if (empNo != null && empNo.contains("CS")) {
                    employee = personMapper.getPersonByEmpNo(empNo);
                } else {
                    employee = personMapper.getPersonByName(empNo);
                }
                if (empNo == null)
                    continue;
                qk = new QianKa();
                qk.setEmpNo(empNo);
                qk.setDateStr(qyxf.getBukaYearM());
                qk.setTimeStr(qyxf.getBukaTime());
                qk.setType(2);
                qk.setRemark(qyxf.getBukaReason());
                if (qk.getDateStr() == null)
                    continue;
                String[] strDay = qk.getDateStr().split("-");
                KQBean kqb = null;
                if (empNo.contains("CS")) {
                    kqb = personMapper.getKQBeanByDateStrAndEmpNo(qk.getDateStr(), qk.getEmpNo());
                } else {
                    kqb = personMapper.getKQBeanByDateStrAndName(qk.getDateStr(), qk.getEmpNo());
                }
                if (employee != null) {
                    WorkSet ws = personMapper.getWorkSetByYearMonthAndPositionLevel(strDay[0] + "-" + strDay[1], employee.getPositionLevel());
                    int isNeed = 0;
                    if (kqb != null) {
                        isNeed = StringUtil.checkIsNeed(qk.getTimeStr(), ws, kqb);
                    }

                    QianKa qianKaOld = personMapper.getQianKaByDateAndEmpno(qk);
                    int isIn = StringUtil.checkIsIn(qk.getTimeStr(), ws);
                    int len = 0;
                    if (qianKaOld != null) {
                        // len = StringUtil.checkIsRepeatQianKa(qianKaOld, qk, ws);
                    }
                    if (len == 0) {
                        QianKa qkk = personMapper.getQianKaByDateAndEmpno(qk);
                        if (qkk == null) {
                            personMapper.saveQianKa(qk);
                        } else {
                            String timeStr = StringUtil.sortTimes(qkk.getTimeStr(), qk.getTimeStr());
                            qk.setTimeStr(timeStr);
                            qk.setId(qkk.getId());
                            personMapper.updateQianKa(qk);
                        }
                    }
                }
            } else if (qyxf.getSpname().equals("请假")) {
                leave = new Leave();
                empId = personMapper.getEmpIdByUserId2(qyxf.getApply_user_id());
                if (empId != null && empId.trim().length() > 0) {
                    leave.setEmpNo(empId);
                    leave.setBeginLeaveStr(qyxf.getStart_timeStr());
                    leave.setEndLeaveStr(qyxf.getEnd_timeStr());
                    leave.setLeaveLong(Double.valueOf(qyxf.getDuration()));
                    leave.setLeaveDescrip(qyxf.getBukaReason());
                    leave.setType(qyxf.getLeave_type());
                    int isRight = personMapper.checkBeginLeaveRight(leave);
                    if (isRight == 0)
                        personMapper.addLeaveData(leave);
                }
            } else if (qyxf.getSpname().equals("外出申请")) {
                out = new Out();
                empNo = personMapper.getEmpNoByUserId(qyxf.getApply_user_id());
                if (empNo != null) {
                    out.setEmpNo(empNo);
                    out.setDateStr(qyxf.getApply_timeStr());
                    out.setInterDays(((Integer) qyxf.getDuration()) / 60 / 60 / 24.0);
                    out.setOutaddr(qyxf.getOutAddr());
                    out.setOutfor(qyxf.getBukaReason());
                    out.setOuttimeStr(qyxf.getStart_timeStr());
                    out.setRealcomtimeStr(qyxf.getEnd_timeStr());
                    personMapper.saveOutBeanToSql(out);
                }
            } else if (qyxf.getSpname().equals("出差")) {
                out = new Out();
                empNo = personMapper.getEmpNoByUserId(qyxf.getApply_user_id());
                if (empNo != null) {
                    out.setEmpNo(empNo);
                    out.setDateStr(qyxf.getApply_timeStr());
                    out.setInterDays((((Integer) qyxf.getDuration()) / 60 / 60 / 24.0) - 1);
                    out.setOutaddr(qyxf.getOutAddr());
                    out.setOutfor(qyxf.getBukaReason());
                    out.setOuttimeStr(qyxf.getStart_timeStr());
                    out.setRealcomtimeStr(qyxf.getEnd_timeStr());
                    out.setRemark("出差");
                    personMapper.saveOutBeanToSql(out);
                }
            }
        }
    }

    public String getTimeStrByDateStrAndEmpNo(String empNo, String ymdStr) throws Exception {
        return personMapper.getTimeStrByDateStrAndEmpNo(empNo, ymdStr);
    }

    public String getTimeStrByDateStrAndNameLinShiGong(String name, String ymdStr) throws Exception {
        return personMapper.getTimeStrByDateStrAndNameLinShiGong(name, ymdStr);
    }

    public List<Employee> findEmpAndLinS() throws Exception {
        return personMapper.findEmpAndLinS();
    }


    public List<ReturnString> getAllDataByName(String name, String dateStr) throws Exception {
        return personMapper.getAllDataByName(name, dateStr);
    }

    public int saveQianKaDateToMysql(QianKa qianKa) throws Exception {
        Employee employee = personMapper.getEmployeeOneById(qianKa.getEmpId());
        qianKa.setEmpNo(employee.getEmpNo());
        String[] strDay = qianKa.getDateStr().split("-");
        KQBean kqb = personMapper.getKQBeanByDateStrAndEmpNo(qianKa.getDateStr(), qianKa.getEmpNo());
        WorkSet ws = personMapper.getWorkSetByYearMonthAndPositionLevel(strDay[0] + "-" + strDay[1], employee.getPositionLevel());
        int isNeed = 0;
        if (kqb != null) {
            isNeed = StringUtil.checkIsNeed(qianKa.getTimeStr(), ws, kqb);
        }
        if (isNeed == 4) {
            return isNeed;
        }
        QianKa qianKaOld = personMapper.getQianKaByDateAndEmpno(qianKa);
        int isIn = StringUtil.checkIsIn(qianKa.getTimeStr(), ws);
        if (isIn == 3) {
            // return isIn;
        }

        int len = 0;
        if (qianKaOld != null) {
            len = StringUtil.checkIsRepeatQianKa(qianKaOld, qianKa, ws);
        }
        if (len == 0) {
            QianKa qk = personMapper.getQianKaByDateAndEmpno(qianKa);
            if (qk == null) {
                personMapper.saveQianKa(qianKa);
            } else {
                String timeStr = StringUtil.sortTimes(qianKa.getTimeStr(), qk.getTimeStr());
                qianKa.setTimeStr(timeStr);
                qianKa.setId(qk.getId());
                personMapper.updateQianKa(qianKa);
            }
            return 1;
        } else {
            return 2;
        }
    }

    public List<QianKa> findAllQianKa(QianKa qianKa) throws Exception {
        return personMapper.findAllQianKa(qianKa);
    }

    public String getDeptNameByEmployId(Integer id) throws Exception {
        return personMapper.getDeptNameByEmployId(id);
    }

    public int findAllQianKaCount() throws Exception {
        return personMapper.findAllQianKaCount();
    }

    public List<QianKa> queryQKByCondition(QianKa qianKa) throws Exception {
        return personMapper.queryQKByCondition(qianKa);
    }

    public int queryQKByConditionCount(QianKa qianKa) throws Exception {
        return personMapper.queryQKByConditionCount(qianKa);
    }

    public List<YeBan> queryYBByCondition(YeBan yeBan) throws Exception {
        return personMapper.queryYBByCondition(yeBan);
    }

    public void saveDAPCSetUp(DaKaPianCha daKaPianCha) throws Exception {
        DaKaPianCha old = personMapper.getDAPC();
        if (old == null)
            personMapper.saveDAPCSetUp(daKaPianCha);
        personMapper.updateDAPCSetUp(daKaPianCha);
    }

    public int queryYBByConditionCount(YeBan yeBan) throws Exception {
        return personMapper.queryYBByConditionCount(yeBan);
    }

    public DaKaPianCha getDaKaPianCha() throws Exception {
        return personMapper.getDaKaPianCha();
    }

    public WeiXinUsrId getUserIdByUSerId(String userId) throws Exception {
        return personMapper.getUserIdByUSerId(userId);
    }

    public void saveWeiXinUserIdByBean(WeiXinUsrId wx) throws Exception {
        personMapper.saveWeiXinUserIdByBean(wx);
    }

    public boolean computeSignKQByMonth(String yearMonth) throws Exception {
        String dateStrs = personMapper.getWorkDateByMonthE(yearMonth);
        Double fullWorkHours = StringUtil.computeFullWorkHours(dateStrs);
        Double zhengbanHours;
        Double usualExtHours;
        Double workendHours;
        Double chinaPaidLeave;
        Double otherPaidLeave;
        Double leaveOfAbsense;
        Double sickLeave;
        boolean isWeekEnd;
        List<Integer> zhengChangDaKa = new ArrayList<Integer>();
        zhengChangDaKa.add(1);
        zhengChangDaKa.add(17);
        zhengChangDaKa.add(7);
        zhengChangDaKa.add(13);
        zhengChangDaKa.add(301);
        zhengChangDaKa.add(323);
        zhengChangDaKa.add(601);
        zhengChangDaKa.add(623);

        List<Integer> otherPaidDaKa = new ArrayList<Integer>();
        otherPaidDaKa.add(20);
        otherPaidDaKa.add(21);
        otherPaidDaKa.add(22);
        otherPaidDaKa.add(23);

        List<Integer> weekEndDaka = new ArrayList<Integer>();
        weekEndDaka.add(67);
        weekEndDaka.add(61);
        weekEndDaka.add(367);
        weekEndDaka.add(361);
        weekEndDaka.add(661);
        weekEndDaka.add(667);

        List<Integer> outDaka = new ArrayList<Integer>();
        weekEndDaka.add(2);
        weekEndDaka.add(12);
        weekEndDaka.add(106);
        weekEndDaka.add(107);
        weekEndDaka.add(108);
        weekEndDaka.add(302);
        weekEndDaka.add(312);
        weekEndDaka.add(3106);
        weekEndDaka.add(3107);
        weekEndDaka.add(3108);
        weekEndDaka.add(602);
        weekEndDaka.add(612);
        weekEndDaka.add(6106);
        weekEndDaka.add(6107);
        weekEndDaka.add(6108);

        String wd = personMapper.getWorkDateByMonthC(yearMonth);
        DayJI dj;
        List<DayJI> dayJIList = null;
        List<MonthKQInfo> monthKQInfoList = personMapper.getMonthKqInfoListByEmpNoandYearMonth(yearMonth);
        for (MonthKQInfo mk : monthKQInfoList) {
            zhengbanHours = 0.0;
            usualExtHours = 0.0;
            workendHours = 0.0;
            chinaPaidLeave = 0.0;
            otherPaidLeave = 0.0;
            leaveOfAbsense = 0.0;
            sickLeave = 0.0;
            dayJIList = MKExcelUtil.getDayJIListByMKList(mk);
            for (int i = 0; i < dayJIList.size(); i++) {
                isWeekEnd = DateUtil.checkIsWeekEnd(wd, (i + 1) + "");
                dj = dayJIList.get(i);
                if (zhengChangDaKa.contains(dj.getDayJiAM()) || outDaka.contains(dj.getDayJiAM())) {
                    if (isWeekEnd) {
                        workendHours += 4;
                    } else {
                        zhengbanHours += 4;
                    }
                } else if (dj.getDayJiAM() == 4) {
                    chinaPaidLeave += 4;
                } else if (dj.getDayJiAM() == 6) {
                    leaveOfAbsense += 4;
                } else if (dj.getDayJiAM() == 11) {
                    sickLeave += 4;
                } else if (otherPaidDaKa.contains(dj.getDayJiAM())) {
                    otherPaidLeave += 4;
                } else if (dj.getDayJiAM() == 16 || dj.getDayJiAM() == 19) {
                    leaveOfAbsense += 4 - (dj.getDayJiAMRemark() == null ? 0.0 : dj.getDayJiAMRemark());
                    if (isWeekEnd) {
                        workendHours += (dj.getDayJiAMRemark() == null ? 0.0 : dj.getDayJiAMRemark());
                    } else {
                        zhengbanHours += (dj.getDayJiAMRemark() == null ? 0.0 : dj.getDayJiAMRemark());
                    }
                }


                if (zhengChangDaKa.contains(dj.getDayJiPM()) || outDaka.contains(dj.getDayJiPM())) {
                    if (isWeekEnd) {
                        workendHours += 4;
                    } else {
                        zhengbanHours += 4;
                    }
                } else if (dj.getDayJiPM() == 4) {
                    chinaPaidLeave += 4;
                } else if (dj.getDayJiPM() == 6) {
                    leaveOfAbsense += 4;
                } else if (dj.getDayJiPM() == 11) {
                    sickLeave += 4;
                } else if (otherPaidDaKa.contains(dj.getDayJiPM())) {
                    otherPaidLeave += 4;
                } else if (dj.getDayJiPM() == 16 || dj.getDayJiPM() == 19) {
                    leaveOfAbsense += 4 - (dj.getDayJiPMRemark() == null ? 0.0 : dj.getDayJiPMRemark());
                    if (isWeekEnd) {
                        workendHours += (dj.getDayJiPMRemark() == null ? 0.0 : dj.getDayJiPMRemark());
                    } else {
                        zhengbanHours += (dj.getDayJiPMRemark() == null ? 0.0 : dj.getDayJiPMRemark());
                    }
                }

                if (isWeekEnd) {
                    workendHours += (dj.getDayJiExHours() == null ? 0.0 : dj.getDayJiExHours());
                } else {
                    usualExtHours += (dj.getDayJiExHours() == null ? 0.0 : dj.getDayJiExHours());
                }
            }

            mk.setZhengbanHours(zhengbanHours == null ? 0.0 : zhengbanHours);
            mk.setUsualExtHours(usualExtHours == null ? 0.0 : usualExtHours);
            mk.setWorkendHours(workendHours == null ? 0.0 : workendHours);
            mk.setChinaPaidLeave(chinaPaidLeave == null ? 0.0 : chinaPaidLeave);
            mk.setOtherPaidLeave(otherPaidLeave == null ? 0.0 : otherPaidLeave);
            mk.setLeaveOfAbsense(leaveOfAbsense == null ? 0.0 : leaveOfAbsense);
            mk.setSickLeave(sickLeave == null ? 0.0 : sickLeave);
            if (((otherPaidLeave + leaveOfAbsense + sickLeave) <= 0) && (fullWorkHours <= zhengbanHours)) {
                mk.setFullWorkReword(100D);
            } else {
                mk.setFullWorkReword(0D);
            }
            personMapper.updateMKById(mk);
        }
        return true;
    }

    public List<Insurance> translateTabletoInsuranceBean(MultipartFile file) throws Exception {
        DateFormat dftdatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dftdate = new SimpleDateFormat("yyyy-MM-dd");
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
        if (Workbook != null) {
            jxl.Sheet[] sheets = Workbook.getSheets();
            xlsfSheet = sheets[0];
        }
        List<Insurance> outList = new ArrayList<Insurance>();
        Insurance cio = null;
        int rowNums = xlsfSheet.getRows();
        jxl.Cell[] cell = null;
        String timestr;
        Date transferDate = null;
        DateCell dc = null;
        Date date = null;
        Calendar c = null;
        for (int i = 5; i < rowNums; i++) {
            cell = xlsfSheet.getRow(i);
            if (cell != null && cell.length > 0) {
                cio = new Insurance();
                try {
                    if (cell[2].getType() != CellType.EMPTY) {
                        cio.setName(cell[2].getContents().trim());
                        cio.setID_NO(cell[3].getContents().trim());
                        if (cell[8].getType() != CellType.EMPTY) {
                            cio.setEndowInsur(cell[8].getContents() == null ? 0.0 : Double.valueOf(cell[8].getContents().trim()));
                        } else {
                            cio.setEndowInsur(0.0);
                        }
                        if (cell[12].getType() != CellType.EMPTY) {
                            cio.setMedicalInsur(cell[12].getContents() == null ? 0.0 : Double.valueOf(cell[12].getContents().trim()));
                        } else {
                            cio.setMedicalInsur(0.0);
                        }
                        if (cell[18].getType() != CellType.EMPTY) {
                            cio.setUnWorkInsur(cell[18].getContents() == null ? 0.0 : Double.valueOf(cell[18].getContents().trim()));
                        } else {
                            cio.setUnWorkInsur(0.0);
                        }

                        outList.add(cio);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("表格第" + (i + 1) + "行日期有错误!");
                }
            }
        }
        return outList;
    }

    public void saveInsuranceList(List<Insurance> insuranceList) throws Exception {
        personMapper.deleteAllInsurance();
        for (Insurance is : insuranceList) {
            personMapper.saveInsuranceByBean(is);
        }
    }


    public List<AccuFund> translateTabletoAccuFundBean(MultipartFile file) throws Exception {
        DateFormat dftdatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dftdate = new SimpleDateFormat("yyyy-MM-dd");
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
        if (Workbook != null) {
            jxl.Sheet[] sheets = Workbook.getSheets();
            xlsfSheet = sheets[0];
        }
        List<AccuFund> outList = new ArrayList<AccuFund>();
        AccuFund cio = null;
        int rowNums = xlsfSheet.getRows();
        jxl.Cell[] cell = null;
        String timestr;
        Date transferDate = null;
        DateCell dc = null;
        Date date = null;
        Calendar c = null;
        for (int i = 5; i < rowNums; i++) {
            cell = xlsfSheet.getRow(i);
            if (cell != null && cell.length > 0) {
                cio = new AccuFund();
                try {
                    if (cell[2].getType() != CellType.EMPTY) {
                        cio.setName(cell[2].getContents().trim());
                        cio.setID_NO(cell[3].getContents().trim());
                        if (cell[11].getType() != CellType.EMPTY) {
                            cio.setAccuFundFee(cell[11].getContents() == null ? 0.0 : Double.valueOf(cell[11].getContents().trim()));
                        } else {
                            cio.setAccuFundFee(0.0);
                        }

                        outList.add(cio);
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("表格第" + (i + 1) + "行日期有错误!");
                }
            }
        }
        return outList;
    }

    public void saveAccuFundList(List<AccuFund> accuFundList) throws Exception {
        personMapper.deleteAllAccuFund();
        for (AccuFund is : accuFundList) {
            personMapper.saveAccuFundByBean(is);
        }
    }

    public AccuFund getAFByEmpNo(String empNo) throws Exception {
        return personMapper.getAFByEmpNo(empNo);
    }

    public Insurance getISByEmpNo(String empNo) throws Exception {
        return personMapper.getISByEmpNo(empNo);
    }

    public List<Employee> findAllLinShiEmpAll() throws Exception {
        return personMapper.findAllLinShiEmpAll();
    }

    public List<Employee> findAllLinShiEmp(Employee ee) throws Exception {
        return personMapper.findAllLinShiEmp(ee);
    }

    public int findAllLinShiEmpCount() throws Exception {
        return personMapper.findAllLinShiEmpCount();
    }

    public List<Employee> queryLinShiByCondition(Employee ee) throws Exception {
        return personMapper.queryLinShiByCondition(ee);
    }

    public int queryLinShiByConditionConditionCount(Employee ee) throws Exception {
        return personMapper.queryLinShiByConditionCount(ee);
    }

    public List<Employee> findAllEmployeeLinShiAll() throws Exception {
        return personMapper.findAllEmployeeLinShiAll();
    }

    public List<Employee> findAllLSZKAndOutData(Employee employee) throws Exception {
        return personMapper.findAllLSZKAndOutData(employee);
    }

    public int findAllLSZKAndOutDataCount() throws Exception {
        return personMapper.findAllLSZKAndOutDataCount();
    }

    public List<Employee> findAllLSZKAndOutDataCondition(Employee employee) throws Exception {
        return personMapper.findAllLSZKAndOutDataCondition(employee);
    }

    public int findAllLSZKAndOutDataConditionCount(Employee employee) throws Exception {
        return personMapper.findAllLSZKAndOutDataConditionCount(employee);
    }

    public List<ZhongKongBean> getAllKQDataByYearMonthDayA(String date) throws Exception {
        return personMapper.getAllKQDataByYearMonthDayA(date);
    }


    public List<LinShiHours> findAllLinShiDan(LinShiHours linShiHours) throws Exception {
        return personMapper.findAllLinShiDan(linShiHours);
    }

    public int findAllLinShiDanCount() throws Exception {
        return personMapper.findAllLinShiDanCount();
    }

}


