package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.*;
import jxl.CellType;
import jxl.DateCell;
import jxl.WorkbookSettings;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:homey Wong
 * @date:2019/5/17 0017 上午 11:15
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ExcelUtil {


    public static Map<String, List<?>> translateAllTable(MultipartFile file) throws Exception {
        Map<String, List<?>> listMap = new HashMap<String, List<?>>();
        DateFormat dftdatetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dftdate = new SimpleDateFormat("yyyy-MM-dd");
        WorkbookSettings ws = new WorkbookSettings();
        String fileName = file.getOriginalFilename();
        ws.setCellValidationDisabled(true);
        String titleName = null;
        jxl.Workbook Workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (fileType.equals("xls")) {
            Workbook = jxl.Workbook.getWorkbook(file.getInputStream(), ws);
        } else {
            throw new Exception("文档格式后缀不正确!!！只接受xls格式.");
        }
        jxl.Sheet xlsfSheet = null;
        jxl.Sheet xlsfSheetqk = null;
        jxl.Sheet xlsfSheetlb = null;
        jxl.Sheet xlsfSheetjb = null;
        jxl.Sheet xlsfSheetyb = null;
        jxl.Sheet xlsfSheettx = null;
        if (Workbook != null) {
            jxl.Sheet[] sheets = Workbook.getSheets();
            xlsfSheet = sheets[1];
            xlsfSheetqk = sheets[2];
            xlsfSheetlb = sheets[3];
            xlsfSheetjb = sheets[4];
            xlsfSheetyb = sheets[5];
            xlsfSheettx = sheets[6];
        }
        List<Leave> leaveList = new ArrayList<Leave>();
        List<QianKa> qianKaList = new ArrayList<QianKa>();
        List<LianBan> lianBanList = new ArrayList<LianBan>();
        List<JiaBan> jiaBanList = new ArrayList<JiaBan>();
        List<YeBan> yeBanList = new ArrayList<YeBan>();
        List<TiaoXiu> tiaoXiuList = new ArrayList<TiaoXiu>();
        Leave leave = null;
        QianKa qianKa = null;
        LianBan lianBan = null;
        JiaBan jiaBan = null;
        YeBan yeBan = null;
        TiaoXiu tiaoXiu = null;
        int rowNums = xlsfSheet.getRows();
        int rowNumsQK = xlsfSheetqk.getRows();
        int rowNumsLB = xlsfSheetlb.getRows();
        int rowNumsJB = xlsfSheetjb.getRows();
        int rowNumsYB = xlsfSheetyb.getRows();
        int rowNumsTX = xlsfSheettx.getRows();
        jxl.Cell[] cell = null;
        String timestr;
        Date transferDate = null;
        DateCell dc = null;
        Date date = null;
        Calendar c = null;
        for (int i = 2; i < rowNums; i++) {
            cell = xlsfSheet.getRow(i);
            if (cell != null && cell.length > 0 && cell[0].getType() != CellType.EMPTY) {
                leave = new Leave();
                try {
                    if (cell[0].getType() == CellType.DATE) {
                        titleName = "输单日期";
                        dc = (DateCell) cell[0];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        leave.setDate(transferDate);
                        leave.setDateStr(dftdate.format(transferDate));
                    }
                    titleName = "姓名";
                    leave.setName(cell[1].getContents().trim());
                    titleName = "工号";
                    if (cell[2].getType() != CellType.EMPTY) {
                        leave.setEmpNo(cell[2].getContents().trim());
                    } else {
                        leave.setEmpNo(leave.getName());
                    }
                    if (cell[3].getType() == CellType.DATE) {
                        titleName = "请假时间始";
                        dc = (DateCell) cell[3];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        leave.setBeginLeave(transferDate);
                        leave.setBeginLeaveStr(dftdatetime.format(transferDate));
                    }

                    if (cell[4].getType() == CellType.DATE) {
                        titleName = "请假时间止";
                        dc = (DateCell) cell[4];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        leave.setEndLeave(transferDate);
                        leave.setEndLeaveStr(dftdatetime.format(transferDate));
                    }

                    titleName = "请假时长";
                    leave.setLeaveLong(Double.valueOf(cell[5].getContents().trim()));
                    titleName = "请假类型";
                    leave.setType(StringUtil.getLeaveTypByStr(i, cell[6].getContents().trim()));
                    titleName = "请假说明";
                    leave.setLeaveDescrip(cell[7].getContents().trim());
                    leaveList.add(leave);
                } catch (Exception e) {
                    throw new Exception("请假单表格第" + (i + 1) + "行" + titleName + "有错误!");
                }
            }
        }


        for (int i = 2; i < rowNumsQK; i++) {
            cell = xlsfSheetqk.getRow(i);
            if (cell != null && cell.length > 0 && cell[0].getType() != CellType.EMPTY) {
                qianKa = new QianKa();
                try {
                    if (cell[0].getType() == CellType.DATE) {
                        titleName = "输单日期";
                        dc = (DateCell) cell[0];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        qianKa.setDanDate(transferDate);
                        qianKa.setDanDateStr(dftdate.format(transferDate));
                    }
                    titleName = "姓名";
                    qianKa.setName(cell[1].getContents().trim());
                    titleName = "工号";
                    if (cell[2].getType() != CellType.EMPTY) {
                        qianKa.setEmpNo(cell[2].getContents().trim());
                    } else {
                        qianKa.setEmpNo(qianKa.getName());
                    }
                    if (cell[3].getType() == CellType.DATE) {
                        titleName = "签卡日期";
                        dc = (DateCell) cell[3];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        qianKa.setDate(transferDate);
                        qianKa.setDateStr(dftdate.format(transferDate));
                    }


                    titleName = "签卡时间";
                    qianKa.setTimeStr(cell[4].getContents().trim());
                    titleName = "签卡原因";
                    qianKa.setType(2);
                    qianKaList.add(qianKa);
                } catch (Exception e) {
                    throw new Exception("签卡单表格第" + (i + 1) + "行" + titleName + "有错误!");
                }
            }
        }

        for (int i = 2; i < rowNumsLB; i++) {
            cell = xlsfSheetlb.getRow(i);
            if (cell != null && cell.length > 0 && cell[0].getType() != CellType.EMPTY) {
                lianBan = new LianBan();
                try {
                    if (cell[0].getType() == CellType.DATE) {
                        titleName = "输单日期";
                        dc = (DateCell) cell[0];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        lianBan.setDanDate(transferDate);
                        lianBan.setDanDateStr(dftdate.format(transferDate));
                    }
                    titleName = "姓名";
                    lianBan.setName(cell[1].getContents().trim());
                    titleName = "工号";
                    if (cell[2].getType() != CellType.EMPTY) {
                        lianBan.setEmpNo(cell[2].getContents().trim());
                    } else {
                        lianBan.setEmpNo(lianBan.getName());
                    }
                    if (cell[3].getType() == CellType.DATE) {
                        titleName = "连班日期";
                        dc = (DateCell) cell[3];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        lianBan.setDate(transferDate);
                        lianBan.setDateStr(dftdate.format(transferDate));
                    }


                    titleName = "中午连班小时";
                    lianBan.setNoonHours(Double.valueOf(cell[4].getContents().trim()));
                    titleName = "晚上连班小时";
                    lianBan.setNightHours(Double.valueOf(cell[5].getContents().trim()));
                    titleName = "连班原因";
                    lianBan.setRemark(cell[6].getContents().trim());
                    lianBanList.add(lianBan);
                } catch (Exception e) {
                    throw new Exception("连班单表格第" + (i + 1) + "行" + titleName + "有错误!");
                }
            }
        }

        for (int i = 2; i < rowNumsJB; i++) {
            cell = xlsfSheetjb.getRow(i);
            if (cell != null && cell.length > 0 && cell[0].getType() != CellType.EMPTY) {
                jiaBan = new JiaBan();
                try {
                    if (cell[0].getType() == CellType.DATE) {
                        titleName = "输单日期";
                        dc = (DateCell) cell[0];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        jiaBan.setDanDate(transferDate);
                        jiaBan.setDanDateStr(dftdate.format(transferDate));
                    }
                    titleName = "姓名";
                    jiaBan.setName(cell[1].getContents().trim());
                    titleName = "工号";
                    if (cell[2].getType() != CellType.EMPTY) {
                        jiaBan.setEmpNo(cell[2].getContents().trim());
                    } else {
                        jiaBan.setEmpNo(jiaBan.getName());
                    }
                    if (cell[3].getType() == CellType.DATE) {
                        titleName = "加班时间起";
                        dc = (DateCell) cell[3];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        jiaBan.setExtDateFrom(transferDate);
                        jiaBan.setExtDateFromStr(dftdatetime.format(transferDate));
                    }

                    if (cell[4].getType() == CellType.DATE) {
                        titleName = "加班时间止";
                        dc = (DateCell) cell[4];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        jiaBan.setExtDateEnd(transferDate);
                        jiaBan.setExtDateEndStr(dftdatetime.format(transferDate));
                    }


                    titleName = "加班工时";
                    jiaBan.setExtWorkHours(Double.valueOf(cell[5].getContents().trim()));
                    titleName = "加班原因";
                    jiaBan.setRemark(cell[6].getContents().trim());
                    jiaBanList.add(jiaBan);
                } catch (Exception e) {
                    throw new Exception("加班单表格第" + (i + 1) + "行" + titleName + "有错误!");
                }
            }
        }

        for (int i = 2; i < rowNumsYB; i++) {
            cell = xlsfSheetyb.getRow(i);
            if (cell != null && cell.length > 0 && cell[0].getType() != CellType.EMPTY) {
                yeBan = new YeBan();
                try {
                    if (cell[0].getType() == CellType.DATE) {
                        titleName = "输单日期";
                        dc = (DateCell) cell[0];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        yeBan.setDanDate(transferDate);
                        yeBan.setDanDateStr(dftdate.format(transferDate));
                    }
                    titleName = "姓名";
                    yeBan.setName(cell[1].getContents().trim());
                    titleName = "工号";
                    if (cell[2].getType() != CellType.EMPTY) {
                        yeBan.setEmpNo(cell[2].getContents().trim());
                    } else {
                        yeBan.setEmpNo(yeBan.getName());
                    }

                    if (cell[3].getType() == CellType.DATE) {
                        titleName = "夜班时间";
                        dc = (DateCell) cell[3];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        yeBan.setDate(transferDate);
                        yeBan.setDateStr(dftdate.format(transferDate));
                    }

                    titleName = "备注";
                    yeBan.setRemark(cell[4].getContents().trim());
                    yeBanList.add(yeBan);
                } catch (Exception e) {
                    throw new Exception("夜班单表格第" + (i + 1) + "行" + titleName + "有错误!");
                }
            }
        }


        for (int i = 2; i < rowNumsTX; i++) {
            cell = xlsfSheettx.getRow(i);
            if (cell != null && cell.length > 0 && cell[0].getType() != CellType.EMPTY) {
                tiaoXiu = new TiaoXiu();
                try {
                    if (cell[0].getType() == CellType.DATE) {
                        titleName = "输单日期";
                        dc = (DateCell) cell[0];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        tiaoXiu.setDanDate(transferDate);
                        tiaoXiu.setDanDateStr(dftdate.format(transferDate));
                    }
                    titleName = "姓名";
                    tiaoXiu.setName(cell[1].getContents().trim());
                    titleName = "工号";
                    if (cell[2].getType() != CellType.EMPTY) {
                        tiaoXiu.setEmpNo(cell[2].getContents().trim());
                    } else {
                        tiaoXiu.setEmpNo(tiaoXiu.getName());
                    }

                    if (cell[3].getType() == CellType.DATE) {
                        titleName = "调休从";
                        dc = (DateCell) cell[3];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        tiaoXiu.setFromDate(transferDate);
                        tiaoXiu.setFromDateStr(dftdate.format(transferDate));
                    }

                    titleName = "调休几个小时";
                    tiaoXiu.setHours(Double.valueOf(cell[4].getContents().trim()));
                    if (cell[5].getType() == CellType.DATE) {
                        titleName = "调休至";
                        dc = (DateCell) cell[5];
                        date = dc.getDate();
                        c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.HOUR, -8);
                        transferDate = c.getTime();
                        tiaoXiu.setToDate(transferDate);
                        tiaoXiu.setToDateStr(dftdate.format(transferDate));
                    }
                    titleName = "调休类型";
                    tiaoXiu.setType("公调".equals(cell[5].getContents().trim()) ? 0 : 1);
                    titleName = "备注";
                    tiaoXiu.setRemark(cell[6].getContents().trim());
                    tiaoXiuList.add(tiaoXiu);
                } catch (Exception e) {
                    throw new Exception("调休单表格第" + (i + 1) + "行" + titleName + "有错误!");
                }
            }
        }

        listMap.put("leave", leaveList);
        listMap.put("qianka", qianKaList);
        listMap.put("lianban", lianBanList);
        listMap.put("jiaban", jiaBanList);
        listMap.put("yeban", yeBanList);
        listMap.put("tiaoxiu", tiaoXiuList);

        return listMap;
    }


    public static List<String> writeExcelSubWorkHours(List<SubEmphours> outDatas, String yearMonth, String finalDirPath) {
        List<String> returnArray = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SubEmphours opw;
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("sheet1");
        Date d = new Date();
        String str = sdf.format(d);

        String mkdir = finalDirPath + "/linshi/";
        String pathname = finalDirPath + "/linshi/" + yearMonth + str + "工资计算表" + ".xlsx";
        returnArray.add(yearMonth + str + "工时统计表" + ".xlsx");
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
        }
        File targetFile = new File(mkdir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.createNewFile();
            Row row = null;
            Cell cell = null;
            row = sheet1.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("序号");
            cell = row.createCell(1);
            cell.setCellValue("年月");
            cell = row.createCell(2);
            cell.setCellValue("姓名");
            cell = row.createCell(3);
            cell.setCellValue("工号");
            cell = row.createCell(4);
            cell.setCellValue("部门");
            cell = row.createCell(5);
            cell.setCellValue("职位");
            cell = row.createCell(6);
            cell.setCellValue("正常出勤工时");
            cell = row.createCell(7);
            cell.setCellValue("平常加班工时");
            cell = row.createCell(8);
            cell.setCellValue("周末加班工时");
            cell = row.createCell(9);
            cell.setCellValue("法定有薪假");
            cell = row.createCell(10);
            cell.setCellValue("法定节假日加班工时");
            cell = row.createCell(11);
            cell.setCellValue("其它有薪假工时");
            for (int i = 0; i < outDatas.size(); i++) {
                opw = outDatas.get(i);
                row = sheet1.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell = row.createCell(1);
                cell.setCellValue(opw.getMonth());
                cell = row.createCell(2);
                cell.setCellValue(opw.getName());
                cell = row.createCell(3);
                cell.setCellValue(opw.getEmpNo());
                cell = row.createCell(4);
                cell.setCellValue(opw.getDeptName());
                cell = row.createCell(5);
                cell.setCellValue(opw.getPositionName());
                cell = row.createCell(6);
                cell.setCellValue(opw.getZhengbanWorkHours());
                cell = row.createCell(7);
                cell.setCellValue(opw.getUsualExtWorkHoursl());
                cell = row.createCell(8);
                cell.setCellValue(opw.getWeekendWorkHours());
                cell = row.createCell(9);
                cell.setCellValue(opw.getLegalPaidLeaveHours());
                cell = row.createCell(10);
                cell.setCellValue(opw.getLegalDayWorkHours());
                cell = row.createCell(11);
                cell.setCellValue(opw.getOtherpaidLeaveHours());
            }
            OutputStream stream = new FileOutputStream(file);
            workbook.write(stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnArray.add(pathname);
        return returnArray;

    }

    public static List<String> writeExcelSalary(List<SalaryDataOutPut> outDatas, String yearMonth, String finalDirPath) {
        List<String> returnArray = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SalaryDataOutPut opw;
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("sheet1");
        Date d = new Date();
        String str = sdf.format(d);

        String mkdir = finalDirPath + "/linshi/";
        String pathname = finalDirPath + "/linshi/" + yearMonth + str + "工资计算表" + ".xlsx";
        returnArray.add(yearMonth + str + "工资计算表" + ".xlsx");
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
        }
        File targetFile = new File(mkdir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.createNewFile();
            SalaryDataOutPut outData = null;
            Row row = null;
            Cell cell = null;
            row = sheet1.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("序号");
            cell = row.createCell(1);
            cell.setCellValue("大部门");
            cell = row.createCell(2);
            cell.setCellValue("部门");
            cell = row.createCell(3);
            cell.setCellValue("职务");
            cell = row.createCell(4);
            cell.setCellValue("职位");
            cell = row.createCell(5);
            cell.setCellValue("姓名");
            cell = row.createCell(6);
            cell.setCellValue("工号");
            cell = row.createCell(7);
            cell.setCellValue("入职时间");
            cell = row.createCell(8);
            cell.setCellValue("基本工时");
            cell = row.createCell(9);
            cell.setCellValue("正常出勤工时");
            cell = row.createCell(10);
            cell.setCellValue("正常出勤工资");
            cell = row.createCell(11);
            cell.setCellValue("法定有薪假工时");
            cell = row.createCell(12);
            cell.setCellValue("法定有薪假工资");
            cell = row.createCell(13);
            cell.setCellValue("其它有薪假工时");
            cell = row.createCell(14);
            cell.setCellValue("其它有薪假工资");
            cell = row.createCell(15);
            cell.setCellValue("基本工资小计");
            cell = row.createCell(16);
            cell.setCellValue("平时加班工时");
            cell = row.createCell(17);
            cell.setCellValue("平时加班费");
            cell = row.createCell(18);
            cell.setCellValue("周末加班工时");
            cell = row.createCell(19);
            cell.setCellValue("周末加班费");
            cell = row.createCell(20);
            cell.setCellValue("法定假日加班工时");
            cell = row.createCell(21);
            cell.setCellValue("法定假日加班费");
            cell = row.createCell(22);
            cell.setCellValue("综合技能");
            cell = row.createCell(23);
            cell.setCellValue("岗位工资");
            cell = row.createCell(24);
            cell.setCellValue("职务工资");
            cell = row.createCell(25);
            cell.setCellValue("绩效奖金");
            cell = row.createCell(26);
            cell.setCellValue("绩效分数");
            cell = row.createCell(27);
            cell.setCellValue("奖金/技能小计");
            cell = row.createCell(28);
            cell.setCellValue("业务等级工资");
            cell = row.createCell(29);
            cell.setCellValue("业务等级实得");
            cell = row.createCell(30);
            cell.setCellValue("房补/话补");
            cell = row.createCell(31);
            cell.setCellValue("高温补贴及其它");
            cell = row.createCell(32);
            cell.setCellValue("全勤奖");
            cell = row.createCell(33);
            cell.setCellValue("工龄工资");
            cell = row.createCell(34);
            cell.setCellValue("业务提成");
            cell = row.createCell(35);
            cell.setCellValue("综合工资");
            cell = row.createCell(36);
            cell.setCellValue("扣代付餐费");
            cell = row.createCell(37);
            cell.setCellValue("扣代付水电");
            cell = row.createCell(38);
            cell.setCellValue("扣代付养老险");
            cell = row.createCell(39);
            cell.setCellValue("扣代付医疗险");
            cell = row.createCell(40);
            cell.setCellValue("扣代付失业险");
            cell = row.createCell(41);
            cell.setCellValue("扣代付公积金");
            cell = row.createCell(42);
            cell.setCellValue("其他扣款");
            cell = row.createCell(43);
            cell.setCellValue("专项附加扣除");
            cell = row.createCell(44);
            cell.setCellValue("个税");
            cell = row.createCell(45);
            cell.setCellValue("实发");
            for (int i = 0; i < outDatas.size(); i++) {
                opw = outDatas.get(i);
                row = sheet1.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell = row.createCell(1);
                cell.setCellValue(opw.getBigDeptName());
                cell = row.createCell(2);
                cell.setCellValue(opw.getDeptName());
                cell = row.createCell(3);
                cell.setCellValue(opw.getPositionName());
                cell = row.createCell(4);
                cell.setCellValue(opw.getPositionAttrName());
                cell = row.createCell(5);
                cell.setCellValue(opw.getName());
                cell = row.createCell(6);
                cell.setCellValue(opw.getEmpNo());
                cell = row.createCell(7);
                cell.setCellValue(opw.getIncomDate());
                cell = row.createCell(8);
                cell.setCellValue(opw.getBasickWorkHours());
                cell = row.createCell(9);
                cell.setCellValue(opw.getNorAttenHours());
                cell = row.createCell(10);
                cell.setCellValue(opw.getNorAttendSalary());
                cell = row.createCell(11);
                cell.setCellValue(opw.getChinaPailLeavHours());
                cell = row.createCell(12);
                cell.setCellValue(opw.getChinaPaidLeavSalary());
                cell = row.createCell(13);
                cell.setCellValue(opw.getOtherPaidLeavHours());
                cell = row.createCell(14);
                cell.setCellValue(opw.getOtherPaidLeavSalary());
                cell = row.createCell(15);
                cell.setCellValue(opw.getBasicSalarySubTotal());
                cell = row.createCell(16);
                cell.setCellValue(opw.getUsualExtraHours());
                cell = row.createCell(17);
                cell.setCellValue(opw.getUsralExtraSalary());
                cell = row.createCell(18);
                cell.setCellValue(opw.getWeekendWorkHours());
                cell = row.createCell(19);
                cell.setCellValue(opw.getWeekendWorkSalary());
                cell = row.createCell(20);
                cell.setCellValue(opw.getChinaHoliWorkHours());
                cell = row.createCell(21);
                cell.setCellValue(opw.getChinaHoliWorkSalary());
                cell = row.createCell(22);
                cell.setCellValue(opw.getCompressSalary());
                cell = row.createCell(23);
                cell.setCellValue(opw.getJobSalary());
                cell = row.createCell(24);
                cell.setCellValue(opw.getPositionSalary());
                cell = row.createCell(25);
                cell.setCellValue(opw.getMeritSalary());
                cell = row.createCell(26);
                cell.setCellValue(opw.getMeritScore());
                cell = row.createCell(27);
                cell.setCellValue(opw.getSubbonusTotal());
                cell = row.createCell(28);
                cell.setCellValue(opw.getSalorLevelSalary());
                cell = row.createCell(29);
                cell.setCellValue(opw.getSalrActuGetSalary());
                cell = row.createCell(30);
                cell.setCellValue(opw.getHouseOrTELSubsidy());
                cell = row.createCell(31);
                cell.setCellValue(opw.getHotTempOrOtherAllow());
                cell = row.createCell(32);
                cell.setCellValue(opw.getFullWorkReword());
                cell = row.createCell(33);
                cell.setCellValue(opw.getWorkYearsSalary());
                cell = row.createCell(34);
                cell.setCellValue(opw.getSellCommi());
                cell = row.createCell(35);
                cell.setCellValue(opw.getCompreSalary());
                cell = row.createCell(36);
                cell.setCellValue(opw.getBuckFoodCost());
                cell = row.createCell(37);
                cell.setCellValue(opw.getBuckWaterEleCost());
                cell = row.createCell(38);
                cell.setCellValue(opw.getBuckOldAgeInsurCost());
                cell = row.createCell(39);
                cell.setCellValue(opw.getBuckMedicInsurCost());
                cell = row.createCell(40);
                cell.setCellValue(opw.getBuckUnEmployCost());
                cell = row.createCell(41);
                cell.setCellValue(opw.getBuckAccumCost());
                cell = row.createCell(42);
                cell.setCellValue(opw.getOtherBuckCost());
                cell = row.createCell(43);
                cell.setCellValue(opw.getSixDeducCost());
                cell = row.createCell(44);
                cell.setCellValue(opw.getPersonIncomTaxCost());
                cell = row.createCell(45);
                cell.setCellValue(opw.getNetPaySalary());
            }
            OutputStream stream = new FileOutputStream(file);
            workbook.write(stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnArray.add(pathname);
        return returnArray;

    }


    public static List<String> writeExcel(List<OutPutWorkData> outDatas, String finalDirPath) {
        List<String> returnArray = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        OutPutWorkData opw;
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("sheet1");
        Date d = new Date();
        String str = sdf.format(d);

        String mkdir = finalDirPath + "/linshi/";
        String pathname = finalDirPath + "/linshi/" + str + "考勤结果表" + ".xlsx";
        returnArray.add(str + "考勤结果表" + ".xlsx");
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
        }
        File targetFile = new File(mkdir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.createNewFile();
            OutPutWorkData outData = null;
            Row row = null;
            Cell cell = null;
            row = sheet1.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("几日(hao)");
            cell = row.createCell(1);
            cell.setCellValue("姓名");
            cell = row.createCell(2);
            cell.setCellValue("部门");
            cell = row.createCell(3);
            cell.setCellValue("原考勤整个时间");
            cell = row.createCell(4);
            cell.setCellValue("加班时间始");
            cell = row.createCell(5);
            cell.setCellValue("加班时间止");
            cell = row.createCell(6);
            cell.setCellValue("加班时长");
            cell = row.createCell(7);
            cell.setCellValue("备注");
            cell = row.createCell(8);
            cell.setCellValue("提示");
            for (int i = 0; i < outDatas.size(); i++) {
                opw = outDatas.get(i);
                row = sheet1.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(opw.getWorkInDate());
                cell = row.createCell(1);
                cell.setCellValue(opw.getEmployeeName());
                cell = row.createCell(2);
                cell.setCellValue(opw.getDeptName());
                cell = row.createCell(3);
                cell.setCellValue(opw.getOrginClockInStr());
                cell = row.createCell(4);
                cell.setCellValue(opw.getWorkSetExtOn());
                cell = row.createCell(5);
                cell.setCellValue(opw.getWorkSetExtOff());
                cell = row.createCell(6);
                cell.setCellValue(opw.getExtHours() == null ? 0.0 : opw.getExtHours());
                cell = row.createCell(7);
                cell.setCellValue(opw.getRemark());
                cell = row.createCell(8);
                cell.setCellValue((opw.getIsAonOk().equals("正常") ? "" : opw.getIsAonOk() + "==") +
                        (opw.getIsAoffOk().equals("正常") ? "" : opw.getIsAoffOk() + "==")
                        + (opw.getIsPOnOk().equals("正常") ? "" : opw.getIsPOnOk() + "==") +
                        (opw.getIsPOffOk().equals("正常") ? "" : opw.getIsPOffOk()));
            }
            OutputStream stream = new FileOutputStream(file);
            workbook.write(stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnArray.add(pathname);
        return returnArray;
    }


    public static List<String> writeMKdataTOExcel(List<MonthKQInfo> outDatas, String finalDirPath, String fileName, String yearMonth) {
        List<String> returnArray = new ArrayList<String>();
        MonthKQInfo opw;
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("sheet1");
        String pathname = finalDirPath + "/linshi/" + fileName;
        returnArray.add(fileName);
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
        }
        File targetFile = new File(finalDirPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.createNewFile();
            MonthKQInfo outData = null;
            Row row = null;
            Cell cell = null;
            row = sheet1.createRow(0);
            row.createCell(0);
            cell.setCellValue(yearMonth + "月份考勤表");

            row = sheet1.createRow(1);
            cell = row.createCell(0);
            cell.setCellValue("序号");
            cell = row.createCell(1);
            cell.setCellValue("姓名");
            cell = row.createCell(2);
            cell.setCellValue("部门");
            cell = row.createCell(3);
            cell.setCellValue("原考勤整个时间");
            cell = row.createCell(4);
            cell.setCellValue("加班时间始");
            cell = row.createCell(5);
            cell.setCellValue("加班时间止");
            cell = row.createCell(6);
            cell.setCellValue("加班时长");
            cell = row.createCell(7);
            cell.setCellValue("备注");
            cell = row.createCell(8);
            cell.setCellValue("提示");
            for (int i = 0; i < outDatas.size(); i++) {
                opw = outDatas.get(i);
                row = sheet1.createRow(i + 1);
                cell = row.createCell(0);
                cell = row.createCell(1);
                cell = row.createCell(2);
                cell.setCellValue(opw.getDeptName());
                cell = row.createCell(3);
                cell = row.createCell(4);
                cell = row.createCell(5);
                cell = row.createCell(6);
                cell = row.createCell(7);
                cell.setCellValue(opw.getRemark());
                cell = row.createCell(8);
            }
            OutputStream stream = new FileOutputStream(file);
            workbook.write(stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnArray.add(pathname);
        return returnArray;
    }
}
