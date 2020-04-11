package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.AccuFund;
import com.cosun.cosunp.entity.DayJI;
import com.cosun.cosunp.entity.Insurance;
import com.cosun.cosunp.entity.MonthKQInfo;
import com.cosun.cosunp.service.IPersonServ;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/12/5  上午 9:25
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class MKExcelUtil {

    XSSFWorkbook workbook = new XSSFWorkbook();
    HSSFWorkbook wb = new HSSFWorkbook();
    List<String> returnArray = new ArrayList<String>();
    MonthKQInfo opw;
    FileOutputStream fos = null;
    FileInputStream fis = null;
    CellRangeAddress region = null;
    IPersonServ personServ = null;

    int beginRow = 3;
    String inComStr;
    String ymdStr;
    AccuFund af = null;
    Insurance is = null;
    List<DayJI> dayJIList = new ArrayList<DayJI>();
    DayJI dayJI = null;


    MonthKQInfo oh = null;

    Font font = null;
    Font fontABC = null;
    Font fontR = null;
    Font fontRT = null;
    Font fontP = null;
    Font fontB = null;
    Font fontBT = null;
    Font font2 = null;
    Font font3 = null;
    Font font4 = null;
    Font font5 = null;
    Font font6 = null;
    Font font7 = null;
    Font fontGT = null;

    CellStyle cellStyle = null;
    CellStyle cellStyleABC = null;
    CellStyle cellStyleRR = null;
    CellStyle cellStyleBOPY = null;
    CellStyle cellStyleBOY = null;
    CellStyle cellStyleBOYG = null;
    CellStyle cellStyleBOPYG = null;
    CellStyle cellStyleRRThin = null;
    CellStyle cellStylePP = null;
    CellStyle cellStyleBB = null;
    CellStyle cellStyleBBThin = null;
    CellStyle cellStyle4BOY = null;
    CellStyle cellStyle4BOYG = null;
    CellStyle cellStyleA = null;
    CellStyle cellStyleAY = null;
    CellStyle cellStyleAYG = null;
    CellStyle cellStyle2 = null;
    CellStyle cellStyle3 = null;
    CellStyle cellStyleGT = null;
    CellStyle cellStyle3P = null;
    CellStyle cellStyle4P = null;
    CellStyle cellStyle4PY = null;
    CellStyle cellStyle4PYG = null;
    CellStyle cellStyle3BOP = null;
    CellStyle cellStyle3BO = null;
    CellStyle cellStyle6BOP = null;
    CellStyle cellStyle6BOPY = null;
    CellStyle cellStyle6BOPYG = null;
    CellStyle cellStyle6BO = null;
    CellStyle cellStyle6BOY = null;
    CellStyle cellStyle6BOYG = null;
    CellStyle cellStyle4BOP = null;
    CellStyle cellStyle4BOPY = null;
    CellStyle cellStyle4BOPYG = null;
    CellStyle cellStyle4BO = null;
    CellStyle cellStyleBG = null;
    CellStyle cellStyleBR = null;
    CellStyle cellStyleBOP = null;
    CellStyle cellStyleAP = null;
    CellStyle cellStyleAPY = null;
    CellStyle cellStyleAPYG = null;
    CellStyle cellStyle4 = null;
    CellStyle cellStyle4Y = null;
    CellStyle cellStyle4YG = null;
    CellStyle cellStyle5 = null;
    CellStyle cellStyle6 = null;
    CellStyle cellStyle6Y = null;
    CellStyle cellStyle6YG = null;
    CellStyle cellStyle6P = null;
    CellStyle cellStyle6PY = null;
    CellStyle cellStyle6PYG = null;
    CellStyle cellStyleBO = null;
    HSSFRow row = null;
    HSSFRow row2 = null;
    HSSFRow row3 = null;
    Cell cell = null;
    int days = 0;
    String day = null;
    boolean isWeekEnd = false;
    boolean isFaDing = false;


    public List<String> writeMKdataTOExcel(List<MonthKQInfo> mkList, List<MonthKQInfo> mkList1, String finalDirPath, String fileName, String yearMonth, String wd, String fd) throws Exception {
        personServ = SpringUtil.getBean(IPersonServ.class);
        this.initExcel();
        String pathname = finalDirPath + fileName;
        returnArray.add(fileName);
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
        }
        File targetFile = new File(finalDirPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //www.homey.nat100.top
        //keytool -import -v -file D:/keys/client.cer -keystore D:/keys/tomcat.keystor
        // keytool -export -alias client -keystore D:/keys/client.p12 -storetype PKCS12 -keypass 123456 -file D:/keys/client.cer
        file.createNewFile();
        fis = new FileInputStream(finalDirPath + fileName);
        HSSFSheet hssfSheet = wb.createSheet(yearMonth + "考勤明细");
        hssfSheet.createFreezePane(4, 3, 4, 3);
        this.createOneSheet(mkList, hssfSheet, yearMonth, wd, fd);


        HSSFSheet hssfSheet2 = wb.createSheet(yearMonth + "考勤汇总");
        hssfSheet2.createFreezePane(3, 2, 3, 2);
        this.createTwoSheet(mkList, hssfSheet2, yearMonth, wd, fd);


        HSSFSheet hssfSheet3 = wb.createSheet(yearMonth + "临时工");
        hssfSheet3.createFreezePane(4, 3, 4, 3);
        this.createThreeSheet(mkList1, hssfSheet3, yearMonth, wd, fd);

        HSSFSheet hssfSheet4 = wb.createSheet(yearMonth + "临时工汇总");
        hssfSheet4.createFreezePane(3, 2, 3, 2);
        this.createFourSheet(mkList1, hssfSheet4, yearMonth, wd, fd);

        fos = new FileOutputStream(finalDirPath + fileName);
        wb.write(fos);

        returnArray.add(pathname);
        return returnArray;

    }


    public static List<DayJI> getDayJIListByMKList28(MonthKQInfo oh) throws Exception {
        List<DayJI> dayJIList = new ArrayList<DayJI>();
        if (oh != null) {
            DayJI dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay01AM() == null ? 0 : oh.getDay01AM());
            dayJI.setDayJiAMRemark(oh.getDay01AMRemark() == null ? 0.0 : oh.getDay01AMRemark());
            dayJI.setDayJiPM(oh.getDay01PM() == null ? 0 : oh.getDay01PM());
            dayJI.setDayJiPMRemark(oh.getDay01PMRemark() == null ? 0.0 : oh.getDay01PMRemark());
            dayJI.setDayJiExHours(oh.getDay01ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay02AM() == null ? 0 : oh.getDay02AM());
            dayJI.setDayJiAMRemark(oh.getDay02AMRemark() == null ? 0.0 : oh.getDay02AMRemark());
            dayJI.setDayJiPM(oh.getDay02PM() == null ? 0 : oh.getDay02PM());
            dayJI.setDayJiPMRemark(oh.getDay02PMRemark() == null ? 0.0 : oh.getDay02PMRemark());
            dayJI.setDayJiExHours(oh.getDay02ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay03AM() == null ? 0 : oh.getDay03AM());
            dayJI.setDayJiAMRemark(oh.getDay03AMRemark() == null ? 0.0 : oh.getDay03AMRemark());
            dayJI.setDayJiPM(oh.getDay03PM() == null ? 0 : oh.getDay03PM());
            dayJI.setDayJiPMRemark(oh.getDay03PMRemark() == null ? 0.0 : oh.getDay03PMRemark());
            dayJI.setDayJiExHours(oh.getDay03ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay04AM() == null ? 0 : oh.getDay04AM());
            dayJI.setDayJiAMRemark(oh.getDay04AMRemark() == null ? 0.0 : oh.getDay04AMRemark());
            dayJI.setDayJiPM(oh.getDay04PM() == null ? 0 : oh.getDay04PM());
            dayJI.setDayJiPMRemark(oh.getDay04PMRemark() == null ? 0.0 : oh.getDay04PMRemark());
            dayJI.setDayJiExHours(oh.getDay04ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay05AM() == null ? 0 : oh.getDay05AM());
            dayJI.setDayJiAMRemark(oh.getDay05AMRemark() == null ? 0.0 : oh.getDay05AMRemark());
            dayJI.setDayJiPM(oh.getDay05PM() == null ? 0 : oh.getDay05PM());
            dayJI.setDayJiPMRemark(oh.getDay05PMRemark() == null ? 0.0 : oh.getDay05PMRemark());
            dayJI.setDayJiExHours(oh.getDay05ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay06AM() == null ? 0 : oh.getDay06AM());
            dayJI.setDayJiAMRemark(oh.getDay06AMRemark() == null ? 0.0 : oh.getDay06AMRemark());
            dayJI.setDayJiPM(oh.getDay06PM() == null ? 0 : oh.getDay06PM());
            dayJI.setDayJiPMRemark(oh.getDay06PMRemark() == null ? 0.0 : oh.getDay06PMRemark());
            try {
                dayJI.setDayJiExHours(oh.getDay06ExHours());
            }catch (Exception e) {
                System.out.println(oh.getName()+"====="+oh.getEmpNo());
            }
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay07AM() == null ? 0 : oh.getDay07AM());
            dayJI.setDayJiAMRemark(oh.getDay07AMRemark() == null ? 0.0 : oh.getDay07AMRemark());
            dayJI.setDayJiPM(oh.getDay07PM() == null ? 0 : oh.getDay07PM());
            dayJI.setDayJiPMRemark(oh.getDay07PMRemark() == null ? 0.0 : oh.getDay07PMRemark());
            dayJI.setDayJiExHours(oh.getDay07ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay08AM() == null ? 0 : oh.getDay08AM());
            dayJI.setDayJiAMRemark(oh.getDay08AMRemark() == null ? 0.0 : oh.getDay08AMRemark());
            dayJI.setDayJiPM(oh.getDay08PM() == null ? 0 : oh.getDay08PM());
            dayJI.setDayJiPMRemark(oh.getDay08PMRemark() == null ? 0.0 : oh.getDay08PMRemark());
            dayJI.setDayJiExHours(oh.getDay08ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay09AM() == null ? 0 : oh.getDay09AM());
            dayJI.setDayJiAMRemark(oh.getDay09AMRemark() == null ? 0.0 : oh.getDay09AMRemark());
            dayJI.setDayJiPM(oh.getDay09PM() == null ? 0 : oh.getDay09PM());
            dayJI.setDayJiPMRemark(oh.getDay09PMRemark() == null ? 0.0 : oh.getDay09PMRemark());
            dayJI.setDayJiExHours(oh.getDay09ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay10AM() == null ? 0 : oh.getDay10AM());
            dayJI.setDayJiAMRemark(oh.getDay10AMRemark() == null ? 0.0 : oh.getDay10AMRemark());
            dayJI.setDayJiPM(oh.getDay10PM() == null ? 0 : oh.getDay10PM());
            dayJI.setDayJiPMRemark(oh.getDay10PMRemark() == null ? 0.0 : oh.getDay10PMRemark());
            dayJI.setDayJiExHours(oh.getDay10ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay11AM() == null ? 0 : oh.getDay11AM());
            dayJI.setDayJiAMRemark(oh.getDay11AMRemark() == null ? 0.0 : oh.getDay11AMRemark());
            dayJI.setDayJiPM(oh.getDay11PM() == null ? 0 : oh.getDay11PM());
            dayJI.setDayJiPMRemark(oh.getDay11PMRemark() == null ? 0.0 : oh.getDay11PMRemark());
            dayJI.setDayJiExHours(oh.getDay11ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay12AM() == null ? 0 : oh.getDay12AM());
            dayJI.setDayJiAMRemark(oh.getDay12AMRemark() == null ? 0.0 : oh.getDay12AMRemark());
            dayJI.setDayJiPM(oh.getDay12PM() == null ? 0 : oh.getDay12PM());
            dayJI.setDayJiPMRemark(oh.getDay12PMRemark() == null ? 0.0 : oh.getDay12PMRemark());
            dayJI.setDayJiExHours(oh.getDay12ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay13AM() == null ? 0 : oh.getDay13AM());
            dayJI.setDayJiAMRemark(oh.getDay13AMRemark() == null ? 0.0 : oh.getDay13AMRemark());
            dayJI.setDayJiPM(oh.getDay13PM() == null ? 0 : oh.getDay13PM());
            dayJI.setDayJiPMRemark(oh.getDay13PMRemark() == null ? 0.0 : oh.getDay13PMRemark());
            dayJI.setDayJiExHours(oh.getDay13ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay14AM() == null ? 0 : oh.getDay14AM());
            dayJI.setDayJiAMRemark(oh.getDay14AMRemark() == null ? 0.0 : oh.getDay14AMRemark());
            dayJI.setDayJiPM(oh.getDay14PM() == null ? 0 : oh.getDay14PM());
            dayJI.setDayJiPMRemark(oh.getDay14PMRemark() == null ? 0.0 : oh.getDay14PMRemark());
            dayJI.setDayJiExHours(oh.getDay14ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay15AM() == null ? 0 : oh.getDay15AM());
            dayJI.setDayJiAMRemark(oh.getDay15AMRemark() == null ? 0.0 : oh.getDay15AMRemark());
            dayJI.setDayJiPM(oh.getDay15PM() == null ? 0 : oh.getDay15PM());
            dayJI.setDayJiPMRemark(oh.getDay15PMRemark() == null ? 0.0 : oh.getDay15PMRemark());
            dayJI.setDayJiExHours(oh.getDay15ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay16AM() == null ? 0 : oh.getDay16AM());
            dayJI.setDayJiAMRemark(oh.getDay16AMRemark() == null ? 0.0 : oh.getDay16AMRemark());
            dayJI.setDayJiPM(oh.getDay16PM() == null ? 0 : oh.getDay16PM());
            dayJI.setDayJiPMRemark(oh.getDay16PMRemark() == null ? 0.0 : oh.getDay16PMRemark());
            dayJI.setDayJiExHours(oh.getDay16ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay17AM() == null ? 0 : oh.getDay17AM());
            dayJI.setDayJiAMRemark(oh.getDay17AMRemark() == null ? 0.0 : oh.getDay17AMRemark());
            dayJI.setDayJiPM(oh.getDay17PM() == null ? 0 : oh.getDay17PM());
            dayJI.setDayJiPMRemark(oh.getDay17PMRemark() == null ? 0.0 : oh.getDay17PMRemark());
            dayJI.setDayJiExHours(oh.getDay17ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay18AM() == null ? 0 : oh.getDay18AM());
            dayJI.setDayJiAMRemark(oh.getDay18AMRemark() == null ? 0.0 : oh.getDay18AMRemark());
            dayJI.setDayJiPM(oh.getDay18PM() == null ? 0 : oh.getDay18PM());
            dayJI.setDayJiPMRemark(oh.getDay18PMRemark() == null ? 0.0 : oh.getDay18PMRemark());
            dayJI.setDayJiExHours(oh.getDay18ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay19AM() == null ? 0 : oh.getDay19AM());
            dayJI.setDayJiAMRemark(oh.getDay19AMRemark() == null ? 0.0 : oh.getDay19AMRemark());
            dayJI.setDayJiPM(oh.getDay19PM() == null ? 0 : oh.getDay19PM());
            dayJI.setDayJiPMRemark(oh.getDay19PMRemark() == null ? 0.0 : oh.getDay19PMRemark());
            dayJI.setDayJiExHours(oh.getDay19ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay20AM() == null ? 0 : oh.getDay20AM());
            dayJI.setDayJiAMRemark(oh.getDay20AMRemark() == null ? 0.0 : oh.getDay20AMRemark());
            dayJI.setDayJiPM(oh.getDay20PM() == null ? 0 : oh.getDay20PM());
            dayJI.setDayJiPMRemark(oh.getDay20PMRemark() == null ? 0.0 : oh.getDay20PMRemark());
            dayJI.setDayJiExHours(oh.getDay20ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay21AM() == null ? 0 : oh.getDay21AM());
            dayJI.setDayJiAMRemark(oh.getDay21AMRemark() == null ? 0.0 : oh.getDay21AMRemark());
            dayJI.setDayJiPM(oh.getDay21PM() == null ? 0 : oh.getDay21PM());
            dayJI.setDayJiPMRemark(oh.getDay21PMRemark() == null ? 0.0 : oh.getDay21PMRemark());
            dayJI.setDayJiExHours(oh.getDay21ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay22AM() == null ? 0 : oh.getDay22AM());
            dayJI.setDayJiAMRemark(oh.getDay22AMRemark() == null ? 0.0 : oh.getDay22AMRemark());
            dayJI.setDayJiPM(oh.getDay22PM() == null ? 0 : oh.getDay22PM());
            dayJI.setDayJiPMRemark(oh.getDay22PMRemark() == null ? 0.0 : oh.getDay22PMRemark());
            dayJI.setDayJiExHours(oh.getDay22ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay23AM() == null ? 0 : oh.getDay23AM());
            dayJI.setDayJiAMRemark(oh.getDay23AMRemark() == null ? 0.0 : oh.getDay23AMRemark());
            dayJI.setDayJiPM(oh.getDay23PM() == null ? 0 : oh.getDay23PM());
            dayJI.setDayJiPMRemark(oh.getDay23PMRemark() == null ? 0.0 : oh.getDay23PMRemark());
            dayJI.setDayJiExHours(oh.getDay23ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay24AM() == null ? 0 : oh.getDay24AM());
            dayJI.setDayJiAMRemark(oh.getDay24AMRemark() == null ? 0.0 : oh.getDay24AMRemark());
            dayJI.setDayJiPM(oh.getDay24PM() == null ? 0 : oh.getDay24PM());
            dayJI.setDayJiPMRemark(oh.getDay24PMRemark() == null ? 0.0 : oh.getDay24PMRemark());
            dayJI.setDayJiExHours(oh.getDay24ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay25AM() == null ? 0 : oh.getDay25AM());
            dayJI.setDayJiAMRemark(oh.getDay25AMRemark() == null ? 0.0 : oh.getDay25AMRemark());
            dayJI.setDayJiPM(oh.getDay25PM() == null ? 0 : oh.getDay25PM());
            dayJI.setDayJiPMRemark(oh.getDay25PMRemark() == null ? 0.0 : oh.getDay25PMRemark());
            dayJI.setDayJiExHours(oh.getDay25ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay26AM() == null ? 0 : oh.getDay26AM());
            dayJI.setDayJiAMRemark(oh.getDay26AMRemark() == null ? 0.0 : oh.getDay26AMRemark());
            dayJI.setDayJiPM(oh.getDay26PM() == null ? 0 : oh.getDay26PM());
            dayJI.setDayJiPMRemark(oh.getDay26PMRemark() == null ? 0.0 : oh.getDay26PMRemark());
            dayJI.setDayJiExHours(oh.getDay26ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay27AM() == null ? 0 : oh.getDay27AM());
            dayJI.setDayJiAMRemark(oh.getDay27AMRemark() == null ? 0.0 : oh.getDay27AMRemark());
            dayJI.setDayJiPM(oh.getDay27PM() == null ? 0 : oh.getDay27PM());
            dayJI.setDayJiPMRemark(oh.getDay27PMRemark() == null ? 0.0 : oh.getDay27PMRemark());
            dayJI.setDayJiExHours(oh.getDay27ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay28AM() == null ? 0 : oh.getDay28AM());
            dayJI.setDayJiAMRemark(oh.getDay28AMRemark() == null ? 0.0 : oh.getDay28AMRemark());
            dayJI.setDayJiPM(oh.getDay28PM() == null ? 0 : oh.getDay28PM());
            dayJI.setDayJiPMRemark(oh.getDay28PMRemark() == null ? 0.0 : oh.getDay28PMRemark());
            dayJI.setDayJiExHours(oh.getDay28ExHours());
            dayJIList.add(dayJI);

            return dayJIList;
        }
        return null;
    }

    public static List<DayJI> getDayJIListByMKList(MonthKQInfo oh) throws Exception {
        List<DayJI> dayJIList = new ArrayList<DayJI>();
        if (oh != null) {
            DayJI dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay01AM() == null ? 0 : oh.getDay01AM());
            dayJI.setDayJiAMRemark(oh.getDay01AMRemark() == null ? 0.0 : oh.getDay01AMRemark());
            dayJI.setDayJiPM(oh.getDay01PM() == null ? 0 : oh.getDay01PM());
            dayJI.setDayJiPMRemark(oh.getDay01PMRemark() == null ? 0.0 : oh.getDay01PMRemark());
            dayJI.setDayJiExHours(oh.getDay01ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay02AM() == null ? 0 : oh.getDay02AM());
            dayJI.setDayJiAMRemark(oh.getDay02AMRemark() == null ? 0.0 : oh.getDay02AMRemark());
            dayJI.setDayJiPM(oh.getDay02PM() == null ? 0 : oh.getDay02PM());
            dayJI.setDayJiPMRemark(oh.getDay02PMRemark() == null ? 0.0 : oh.getDay02PMRemark());
            dayJI.setDayJiExHours(oh.getDay02ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay03AM() == null ? 0 : oh.getDay03AM());
            dayJI.setDayJiAMRemark(oh.getDay03AMRemark() == null ? 0.0 : oh.getDay03AMRemark());
            dayJI.setDayJiPM(oh.getDay03PM() == null ? 0 : oh.getDay03PM());
            dayJI.setDayJiPMRemark(oh.getDay03PMRemark() == null ? 0.0 : oh.getDay03PMRemark());
            dayJI.setDayJiExHours(oh.getDay03ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay04AM() == null ? 0 : oh.getDay04AM());
            dayJI.setDayJiAMRemark(oh.getDay04AMRemark() == null ? 0.0 : oh.getDay04AMRemark());
            dayJI.setDayJiPM(oh.getDay04PM() == null ? 0 : oh.getDay04PM());
            dayJI.setDayJiPMRemark(oh.getDay04PMRemark() == null ? 0.0 : oh.getDay04PMRemark());
            dayJI.setDayJiExHours(oh.getDay04ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay05AM() == null ? 0 : oh.getDay05AM());
            dayJI.setDayJiAMRemark(oh.getDay05AMRemark() == null ? 0.0 : oh.getDay05AMRemark());
            dayJI.setDayJiPM(oh.getDay05PM() == null ? 0 : oh.getDay05PM());
            dayJI.setDayJiPMRemark(oh.getDay05PMRemark() == null ? 0.0 : oh.getDay05PMRemark());
            dayJI.setDayJiExHours(oh.getDay05ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay06AM() == null ? 0 : oh.getDay06AM());
            dayJI.setDayJiAMRemark(oh.getDay06AMRemark() == null ? 0.0 : oh.getDay06AMRemark());
            dayJI.setDayJiPM(oh.getDay06PM() == null ? 0 : oh.getDay06PM());
            dayJI.setDayJiPMRemark(oh.getDay06PMRemark() == null ? 0.0 : oh.getDay06PMRemark());
            try {
                dayJI.setDayJiExHours(oh.getDay06ExHours());
            }catch (Exception e) {
                System.out.println(oh.getName()+"================"+oh.getEmpNo());
            }
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay07AM() == null ? 0 : oh.getDay07AM());
            dayJI.setDayJiAMRemark(oh.getDay07AMRemark() == null ? 0.0 : oh.getDay07AMRemark());
            dayJI.setDayJiPM(oh.getDay07PM() == null ? 0 : oh.getDay07PM());
            dayJI.setDayJiPMRemark(oh.getDay07PMRemark() == null ? 0.0 : oh.getDay07PMRemark());
            dayJI.setDayJiExHours(oh.getDay07ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay08AM() == null ? 0 : oh.getDay08AM());
            dayJI.setDayJiAMRemark(oh.getDay08AMRemark() == null ? 0.0 : oh.getDay08AMRemark());
            dayJI.setDayJiPM(oh.getDay08PM() == null ? 0 : oh.getDay08PM());
            dayJI.setDayJiPMRemark(oh.getDay08PMRemark() == null ? 0.0 : oh.getDay08PMRemark());
            dayJI.setDayJiExHours(oh.getDay08ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay09AM() == null ? 0 : oh.getDay09AM());
            dayJI.setDayJiAMRemark(oh.getDay09AMRemark() == null ? 0.0 : oh.getDay09AMRemark());
            dayJI.setDayJiPM(oh.getDay09PM() == null ? 0 : oh.getDay09PM());
            dayJI.setDayJiPMRemark(oh.getDay09PMRemark() == null ? 0.0 : oh.getDay09PMRemark());
            dayJI.setDayJiExHours(oh.getDay09ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay10AM() == null ? 0 : oh.getDay10AM());
            dayJI.setDayJiAMRemark(oh.getDay10AMRemark() == null ? 0.0 : oh.getDay10AMRemark());
            dayJI.setDayJiPM(oh.getDay10PM() == null ? 0 : oh.getDay10PM());
            dayJI.setDayJiPMRemark(oh.getDay10PMRemark() == null ? 0.0 : oh.getDay10PMRemark());
            dayJI.setDayJiExHours(oh.getDay10ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay11AM() == null ? 0 : oh.getDay11AM());
            dayJI.setDayJiAMRemark(oh.getDay11AMRemark() == null ? 0.0 : oh.getDay11AMRemark());
            dayJI.setDayJiPM(oh.getDay11PM() == null ? 0 : oh.getDay11PM());
            dayJI.setDayJiPMRemark(oh.getDay11PMRemark() == null ? 0.0 : oh.getDay11PMRemark());
            dayJI.setDayJiExHours(oh.getDay11ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay12AM() == null ? 0 : oh.getDay12AM());
            dayJI.setDayJiAMRemark(oh.getDay12AMRemark() == null ? 0.0 : oh.getDay12AMRemark());
            dayJI.setDayJiPM(oh.getDay12PM() == null ? 0 : oh.getDay12PM());
            dayJI.setDayJiPMRemark(oh.getDay12PMRemark() == null ? 0.0 : oh.getDay12PMRemark());
            dayJI.setDayJiExHours(oh.getDay12ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay13AM() == null ? 0 : oh.getDay13AM());
            dayJI.setDayJiAMRemark(oh.getDay13AMRemark() == null ? 0.0 : oh.getDay13AMRemark());
            dayJI.setDayJiPM(oh.getDay13PM() == null ? 0 : oh.getDay13PM());
            dayJI.setDayJiPMRemark(oh.getDay13PMRemark() == null ? 0.0 : oh.getDay13PMRemark());
            dayJI.setDayJiExHours(oh.getDay13ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay14AM() == null ? 0 : oh.getDay14AM());
            dayJI.setDayJiAMRemark(oh.getDay14AMRemark() == null ? 0.0 : oh.getDay14AMRemark());
            dayJI.setDayJiPM(oh.getDay14PM() == null ? 0 : oh.getDay14PM());
            dayJI.setDayJiPMRemark(oh.getDay14PMRemark() == null ? 0.0 : oh.getDay14PMRemark());
            dayJI.setDayJiExHours(oh.getDay14ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay15AM() == null ? 0 : oh.getDay15AM());
            dayJI.setDayJiAMRemark(oh.getDay15AMRemark() == null ? 0.0 : oh.getDay15AMRemark());
            dayJI.setDayJiPM(oh.getDay15PM() == null ? 0 : oh.getDay15PM());
            dayJI.setDayJiPMRemark(oh.getDay15PMRemark() == null ? 0.0 : oh.getDay15PMRemark());
            dayJI.setDayJiExHours(oh.getDay15ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay16AM() == null ? 0 : oh.getDay16AM());
            dayJI.setDayJiAMRemark(oh.getDay16AMRemark() == null ? 0.0 : oh.getDay16AMRemark());
            dayJI.setDayJiPM(oh.getDay16PM() == null ? 0 : oh.getDay16PM());
            dayJI.setDayJiPMRemark(oh.getDay16PMRemark() == null ? 0.0 : oh.getDay16PMRemark());
            dayJI.setDayJiExHours(oh.getDay16ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay17AM() == null ? 0 : oh.getDay17AM());
            dayJI.setDayJiAMRemark(oh.getDay17AMRemark() == null ? 0.0 : oh.getDay17AMRemark());
            dayJI.setDayJiPM(oh.getDay17PM() == null ? 0 : oh.getDay17PM());
            dayJI.setDayJiPMRemark(oh.getDay17PMRemark() == null ? 0.0 : oh.getDay17PMRemark());
            dayJI.setDayJiExHours(oh.getDay17ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay18AM() == null ? 0 : oh.getDay18AM());
            dayJI.setDayJiAMRemark(oh.getDay18AMRemark() == null ? 0.0 : oh.getDay18AMRemark());
            dayJI.setDayJiPM(oh.getDay18PM() == null ? 0 : oh.getDay18PM());
            dayJI.setDayJiPMRemark(oh.getDay18PMRemark() == null ? 0.0 : oh.getDay18PMRemark());
            dayJI.setDayJiExHours(oh.getDay18ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay19AM() == null ? 0 : oh.getDay19AM());
            dayJI.setDayJiAMRemark(oh.getDay19AMRemark() == null ? 0.0 : oh.getDay19AMRemark());
            dayJI.setDayJiPM(oh.getDay19PM() == null ? 0 : oh.getDay19PM());
            dayJI.setDayJiPMRemark(oh.getDay19PMRemark() == null ? 0.0 : oh.getDay19PMRemark());
            dayJI.setDayJiExHours(oh.getDay19ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay20AM() == null ? 0 : oh.getDay20AM());
            dayJI.setDayJiAMRemark(oh.getDay20AMRemark() == null ? 0.0 : oh.getDay20AMRemark());
            dayJI.setDayJiPM(oh.getDay20PM() == null ? 0 : oh.getDay20PM());
            dayJI.setDayJiPMRemark(oh.getDay20PMRemark() == null ? 0.0 : oh.getDay20PMRemark());
            dayJI.setDayJiExHours(oh.getDay20ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay21AM() == null ? 0 : oh.getDay21AM());
            dayJI.setDayJiAMRemark(oh.getDay21AMRemark() == null ? 0.0 : oh.getDay21AMRemark());
            dayJI.setDayJiPM(oh.getDay21PM() == null ? 0 : oh.getDay21PM());
            dayJI.setDayJiPMRemark(oh.getDay21PMRemark() == null ? 0.0 : oh.getDay21PMRemark());
            dayJI.setDayJiExHours(oh.getDay21ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay22AM() == null ? 0 : oh.getDay22AM());
            dayJI.setDayJiAMRemark(oh.getDay22AMRemark() == null ? 0.0 : oh.getDay22AMRemark());
            dayJI.setDayJiPM(oh.getDay22PM() == null ? 0 : oh.getDay22PM());
            dayJI.setDayJiPMRemark(oh.getDay22PMRemark() == null ? 0.0 : oh.getDay22PMRemark());
            dayJI.setDayJiExHours(oh.getDay22ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay23AM() == null ? 0 : oh.getDay23AM());
            dayJI.setDayJiAMRemark(oh.getDay23AMRemark() == null ? 0.0 : oh.getDay23AMRemark());
            dayJI.setDayJiPM(oh.getDay23PM() == null ? 0 : oh.getDay23PM());
            dayJI.setDayJiPMRemark(oh.getDay23PMRemark() == null ? 0.0 : oh.getDay23PMRemark());
            dayJI.setDayJiExHours(oh.getDay23ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay24AM() == null ? 0 : oh.getDay24AM());
            dayJI.setDayJiAMRemark(oh.getDay24AMRemark() == null ? 0.0 : oh.getDay24AMRemark());
            dayJI.setDayJiPM(oh.getDay24PM() == null ? 0 : oh.getDay24PM());
            dayJI.setDayJiPMRemark(oh.getDay24PMRemark() == null ? 0.0 : oh.getDay24PMRemark());
            dayJI.setDayJiExHours(oh.getDay24ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay25AM() == null ? 0 : oh.getDay25AM());
            dayJI.setDayJiAMRemark(oh.getDay25AMRemark() == null ? 0.0 : oh.getDay25AMRemark());
            dayJI.setDayJiPM(oh.getDay25PM() == null ? 0 : oh.getDay25PM());
            dayJI.setDayJiPMRemark(oh.getDay25PMRemark() == null ? 0.0 : oh.getDay25PMRemark());
            dayJI.setDayJiExHours(oh.getDay25ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay26AM() == null ? 0 : oh.getDay26AM());
            dayJI.setDayJiAMRemark(oh.getDay26AMRemark() == null ? 0.0 : oh.getDay26AMRemark());
            dayJI.setDayJiPM(oh.getDay26PM() == null ? 0 : oh.getDay26PM());
            dayJI.setDayJiPMRemark(oh.getDay26PMRemark() == null ? 0.0 : oh.getDay26PMRemark());
            dayJI.setDayJiExHours(oh.getDay26ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay27AM() == null ? 0 : oh.getDay27AM());
            dayJI.setDayJiAMRemark(oh.getDay27AMRemark() == null ? 0.0 : oh.getDay27AMRemark());
            dayJI.setDayJiPM(oh.getDay27PM() == null ? 0 : oh.getDay27PM());
            dayJI.setDayJiPMRemark(oh.getDay27PMRemark() == null ? 0.0 : oh.getDay27PMRemark());
            dayJI.setDayJiExHours(oh.getDay27ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay28AM() == null ? 0 : oh.getDay28AM());
            dayJI.setDayJiAMRemark(oh.getDay28AMRemark() == null ? 0.0 : oh.getDay28AMRemark());
            dayJI.setDayJiPM(oh.getDay28PM() == null ? 0 : oh.getDay28PM());
            dayJI.setDayJiPMRemark(oh.getDay28PMRemark() == null ? 0.0 : oh.getDay28PMRemark());
            dayJI.setDayJiExHours(oh.getDay28ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay29AM() == null ? 0 : oh.getDay29AM());
            dayJI.setDayJiAMRemark(oh.getDay29AMRemark() == null ? 0.0 : oh.getDay29AMRemark());
            dayJI.setDayJiPM(oh.getDay29PM() == null ? 0 : oh.getDay29PM());
            dayJI.setDayJiPMRemark(oh.getDay29PMRemark() == null ? 0.0 : oh.getDay29PMRemark());
            dayJI.setDayJiExHours(oh.getDay29ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay30AM() == null ? 0 : oh.getDay30AM());
            dayJI.setDayJiAMRemark(oh.getDay30AMRemark() == null ? 0.0 : oh.getDay30AMRemark());
            dayJI.setDayJiPM(oh.getDay30PM() == null ? 0 : oh.getDay30PM());
            dayJI.setDayJiPMRemark(oh.getDay30PMRemark() == null ? 0.0 : oh.getDay30PMRemark());
            dayJI.setDayJiExHours(oh.getDay30ExHours());
            dayJIList.add(dayJI);

            dayJI = new DayJI();
            dayJI.setDayJiAM(oh.getDay31AM() == null ? 0 : oh.getDay31AM());
            dayJI.setDayJiAMRemark(oh.getDay31AMRemark() == null ? 0.0 : oh.getDay31AMRemark());
            dayJI.setDayJiPM(oh.getDay31PM() == null ? 0 : oh.getDay31PM());
            dayJI.setDayJiPMRemark(oh.getDay31PMRemark() == null ? 0.0 : oh.getDay31PMRemark());
            dayJI.setDayJiExHours(oh.getDay31ExHours());
            dayJIList.add(dayJI);

            return dayJIList;
        }
        return null;
    }

    public void initExcel() {
        font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);

        fontABC = wb.createFont();
        fontABC.setFontHeightInPoints((short) 9);
        fontABC.setColor(HSSFColor.RED.index);


        fontR = wb.createFont();
        fontR.setBold(true);
        fontR.setFontHeightInPoints((short) 10);
        fontR.setColor(HSSFColor.RED.index);

        fontRT = wb.createFont();
        fontRT.setBold(false);
        fontRT.setFontHeightInPoints((short) 10);
        fontRT.setColor(HSSFColor.RED.index);

        fontP = wb.createFont();
        fontP.setBold(true);
        fontP.setFontHeightInPoints((short) 10);
        fontP.setColor(HSSFColor.PINK.index);


        fontB = wb.createFont();
        fontB.setBold(true);
        fontB.setFontHeightInPoints((short) 10);
        fontB.setColor(HSSFColor.DARK_BLUE.index);


        fontBT = wb.createFont();
        fontBT.setBold(false);
        fontBT.setFontHeightInPoints((short) 10);
        fontBT.setColor(HSSFColor.DARK_BLUE.index);


        font2 = wb.createFont();
        font2.setBold(true);
        font2.setFontHeightInPoints((short) 20);

        font3 = wb.createFont();
        font3.setBold(false);
        font3.setFontHeightInPoints((short) 10);


        fontGT = wb.createFont();
        fontGT.setBold(false);
        fontGT.setFontHeightInPoints((short) 10);

        font4 = wb.createFont();
        font4.setBold(false);
        font4.setFontHeightInPoints((short) 10);
        font4.setColor(HSSFColor.GREEN.index);

        font5 = wb.createFont();
        font5.setBold(false);
        font5.setFontHeightInPoints((short) 10);
        font5.setColor(HSSFColor.RED.index);

        font6 = wb.createFont();
        font6.setBold(false);
        font6.setFontHeightInPoints((short) 10);
        font6.setColor(HSSFColor.BLUE.index);

        font7 = wb.createFont();
        font7.setBold(false);
        font7.setFontHeightInPoints((short) 10);
        font7.setColor(HSSFColor.VIOLET.index);

        cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);


        cellStyleABC = wb.createCellStyle();
        cellStyleABC.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleABC.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleABC.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleABC.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleABC.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleABC.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleABC.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleABC.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleABC.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleABC.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleABC.setFont(fontABC);
        cellStyleABC.setWrapText(true);

        cellStyleRR = wb.createCellStyle();
        cellStyleRR.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleRR.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleRR.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleRR.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleRR.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleRR.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleRR.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleRR.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleRR.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleRR.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleRR.setFont(fontR);
        cellStyleRR.setWrapText(true);

        cellStyleRRThin = wb.createCellStyle();
        cellStyleRRThin.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleRRThin.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleRRThin.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleRRThin.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleRRThin.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleRRThin.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleRRThin.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleRRThin.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleRRThin.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleRRThin.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleRRThin.setFont(fontRT);
        cellStyleRRThin.setWrapText(true);

        cellStylePP = wb.createCellStyle();
        cellStylePP.setAlignment(CellStyle.ALIGN_CENTER);
        cellStylePP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStylePP.setBorderBottom(CellStyle.BORDER_THIN);
        cellStylePP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStylePP.setBorderLeft(CellStyle.BORDER_THIN);
        cellStylePP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStylePP.setBorderRight(CellStyle.BORDER_THIN);
        cellStylePP.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStylePP.setBorderTop(CellStyle.BORDER_THIN);
        cellStylePP.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStylePP.setFont(fontP);
        cellStylePP.setWrapText(true);

        cellStyleBB = wb.createCellStyle();
        cellStyleBB.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBB.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBB.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBB.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBB.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBB.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBB.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBB.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBB.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBB.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBB.setFont(fontB);
        cellStyleBB.setWrapText(true);

        cellStyleBBThin = wb.createCellStyle();
        cellStyleBBThin.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBBThin.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBBThin.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBBThin.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBBThin.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBBThin.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBBThin.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBBThin.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBBThin.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBBThin.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBBThin.setFont(fontBT);
        cellStyleBBThin.setWrapText(true);


        cellStyleA = wb.createCellStyle();
        cellStyleA.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleA.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleA.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleA.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleA.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleA.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleA.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleA.setFont(font3);
        cellStyleA.setWrapText(true);


        cellStyleAY = wb.createCellStyle();
        cellStyleAY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleAY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleAY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleAY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleAY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleAY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleAY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleAY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyleAY.setFont(font3);
        cellStyleAY.setWrapText(true);


        cellStyleAYG = wb.createCellStyle();
        cellStyleAYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleAYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleAYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleAYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleAYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleAYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleAYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleAYG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyleAYG.setFont(font3);
        cellStyleAYG.setWrapText(true);

        cellStyle2 = wb.createCellStyle();
        cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle2.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle2.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle2.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle2.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle2.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle2.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle2.setFont(font2);
        cellStyle2.setWrapText(true);


        cellStyle3 = wb.createCellStyle();
        cellStyle3.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle3.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle3.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle3.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle3.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle3.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3.setFont(font4);
        cellStyle3.setWrapText(true);


        cellStyleGT = wb.createCellStyle();
        cellStyleGT.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleGT.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleGT.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleGT.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleGT.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleGT.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleGT.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleGT.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleGT.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleGT.setTopBorderColor(IndexedColors.GREEN.getIndex());
        cellStyleGT.setFont(fontGT);
        cellStyleGT.setWrapText(true);

        cellStyleAP = wb.createCellStyle();
        cellStyleAP.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleAP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleAP.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleAP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAP.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleAP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAP.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleAP.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAP.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleAP.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleAP.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyleAP.setFont(font3);
        cellStyleAP.setWrapText(true);


        cellStyleAPY = wb.createCellStyle();
        cellStyleAPY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleAPY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleAPY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleAPY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAPY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleAPY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAPY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleAPY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAPY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleAPY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAPY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleAPY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyleAPY.setFont(font3);
        cellStyleAPY.setWrapText(true);


        cellStyleAPYG = wb.createCellStyle();
        cellStyleAPYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleAPYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleAPYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleAPYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAPYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleAPYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAPYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleAPYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAPYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleAPYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleAPYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleAPYG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyleAPYG.setFont(font3);
        cellStyleAPYG.setWrapText(true);

        cellStyle4 = wb.createCellStyle();
        cellStyle4.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4.setFont(font5);
        cellStyle4.setWrapText(true);


        cellStyle4Y = wb.createCellStyle();
        cellStyle4Y.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4Y.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4Y.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4Y.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4Y.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4Y.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4Y.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4Y.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4Y.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4Y.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4Y.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4Y.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle4Y.setFont(font5);
        cellStyle4Y.setWrapText(true);


        cellStyle4YG = wb.createCellStyle();
        cellStyle4YG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4YG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4YG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4YG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4YG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4YG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4YG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4YG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4YG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4YG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4YG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4YG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyle4YG.setFont(font5);
        cellStyle4YG.setWrapText(true);


        cellStyle5 = wb.createCellStyle();
        cellStyle5.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle5.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle5.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle5.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle5.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle5.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle5.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle5.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle5.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle5.setFont(font6);
        cellStyle5.setWrapText(true);

        cellStyle6 = wb.createCellStyle();
        cellStyle6.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6.setFont(font7);
        cellStyle6.setWrapText(true);

        cellStyle6Y = wb.createCellStyle();
        cellStyle6Y.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6Y.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6Y.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6Y.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6Y.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6Y.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6Y.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6Y.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6Y.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6Y.setTopBorderColor(IndexedColors.YELLOW.getIndex());
        cellStyle6Y.setFont(font7);
        cellStyle6Y.setWrapText(true);


        cellStyle6YG = wb.createCellStyle();
        cellStyle6YG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6YG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6YG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6YG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6YG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6YG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6YG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6YG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6YG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6YG.setTopBorderColor(IndexedColors.DARK_YELLOW.getIndex());
        cellStyle6YG.setFont(font7);
        cellStyle6YG.setWrapText(true);


        cellStyle6P = wb.createCellStyle();
        cellStyle6P.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6P.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6P.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6P.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6P.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6P.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6P.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6P.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6P.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6P.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6P.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle6P.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyle6P.setFont(font7);
        cellStyle6P.setWrapText(true);

        cellStyle6PY = wb.createCellStyle();
        cellStyle6PY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6PY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6PY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6PY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6PY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6PY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6PY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6PY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6PY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6PY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6PY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle6PY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle6PY.setFont(font7);
        cellStyle6PY.setWrapText(true);

        cellStyle6PYG = wb.createCellStyle();
        cellStyle6PYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6PYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6PYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6PYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6PYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6PYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6PYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6PYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6PYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6PYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6PYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle6PYG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyle6PYG.setFont(font7);
        cellStyle6PYG.setWrapText(true);

        cellStyleBO = wb.createCellStyle();
        cellStyleBO.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBO.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBO.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBO.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBO.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBO.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBO.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBO.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBO.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBO.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBO.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleBO.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        cellStyleBO.setFont(font3);
        cellStyleBO.setWrapText(true);

        cellStyleBOY = wb.createCellStyle();
        cellStyleBOY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBOY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBOY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBOY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBOY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBOY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBOY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleBOY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyleBOY.setFont(font3);
        cellStyleBOY.setWrapText(true);

        cellStyleBOYG = wb.createCellStyle();
        cellStyleBOYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBOYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBOYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBOYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBOYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBOYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBOYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleBOYG.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
        cellStyleBOYG.setFont(font3);
        cellStyleBOYG.setWrapText(true);

        cellStyleBOP = wb.createCellStyle();
        cellStyleBOP.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBOP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBOP.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBOP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOP.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBOP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOP.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBOP.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOP.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBOP.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleBOP.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyleBOP.setFont(font3);
        cellStyleBOP.setWrapText(true);


        cellStyleBOPY = wb.createCellStyle();
        cellStyleBOPY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBOPY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBOPY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBOPY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOPY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBOPY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOPY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBOPY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOPY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBOPY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOPY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleBOPY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyleBOPY.setFont(font3);
        cellStyleBOPY.setWrapText(true);

        cellStyleBOPYG = wb.createCellStyle();
        cellStyleBOPYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBOPYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBOPYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBOPYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOPYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBOPYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOPYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBOPYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOPYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBOPYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBOPYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleBOPYG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyleBOPYG.setFont(font3);
        cellStyleBOPYG.setWrapText(true);


        cellStyleBR = wb.createCellStyle();
        cellStyleBR.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBR.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBR.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBR.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBR.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBR.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBR.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBR.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBR.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBR.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBR.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleBR.setFillForegroundColor(HSSFColor.RED.index);
        cellStyleBR.setFont(font3);
        cellStyleBR.setWrapText(true);


        cellStyleBG = wb.createCellStyle();
        cellStyleBG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleBG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleBG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyleBG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyleBG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyleBG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyleBG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyleBG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyleBG.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        cellStyleBG.setFont(font3);
        cellStyleBG.setWrapText(true);

        cellStyle4BO = wb.createCellStyle();
        cellStyle4BO.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4BO.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4BO.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4BO.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BO.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4BO.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BO.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4BO.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BO.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4BO.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BO.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4BO.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        cellStyle4BO.setFont(font5);
        cellStyle4BO.setWrapText(true);


        cellStyle4BOY = wb.createCellStyle();
        cellStyle4BOY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4BOY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4BOY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4BOY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4BOY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4BOY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4BOY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4BOY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle4BOY.setFont(font5);
        cellStyle4BOY.setWrapText(true);

        cellStyle4BOYG = wb.createCellStyle();
        cellStyle4BOYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4BOYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4BOYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4BOYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4BOYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4BOYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4BOYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4BOYG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyle4BOYG.setFont(font5);
        cellStyle4BOYG.setWrapText(true);

        cellStyle4BOP = wb.createCellStyle();
        cellStyle4BOP.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4BOP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4BOP.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4BOP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOP.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4BOP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOP.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4BOP.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOP.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4BOP.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4BOP.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyle4BOP.setFont(font5);
        cellStyle4BOP.setWrapText(true);

        cellStyle4BOPY = wb.createCellStyle();
        cellStyle4BOPY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4BOPY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4BOPY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4BOPY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOPY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4BOPY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOPY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4BOPY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOPY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4BOPY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOPY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4BOPY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle4BOPY.setFont(font5);
        cellStyle4BOPY.setWrapText(true);

        cellStyle4BOPYG = wb.createCellStyle();
        cellStyle4BOPYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4BOPYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4BOPYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4BOPYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOPYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4BOPYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOPYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4BOPYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOPYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4BOPYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4BOPYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4BOPYG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyle4BOPYG.setFont(font5);
        cellStyle4BOPYG.setWrapText(true);

        cellStyle6BO = wb.createCellStyle();
        cellStyle6BO.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6BO.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6BO.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6BO.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BO.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6BO.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BO.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6BO.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BO.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6BO.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BO.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle6BO.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        cellStyle6BO.setFont(font7);
        cellStyle6BO.setWrapText(true);

        cellStyle6BOY = wb.createCellStyle();
        cellStyle6BOY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6BOY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6BOY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6BOY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6BOY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6BOY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6BOY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle6BOY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle6BOY.setFont(font7);
        cellStyle6BOY.setWrapText(true);

        cellStyle6BOYG = wb.createCellStyle();
        cellStyle6BOYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6BOYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6BOYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6BOYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6BOYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6BOYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6BOYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle6BOYG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyle6BOYG.setFont(font7);
        cellStyle6BOYG.setWrapText(true);

        cellStyle6BOP = wb.createCellStyle();
        cellStyle6BOP.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6BOP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6BOP.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6BOP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOP.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6BOP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOP.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6BOP.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOP.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6BOP.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle6BOP.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyle6BOP.setFont(font7);
        cellStyle6BOP.setWrapText(true);


        cellStyle6BOPY = wb.createCellStyle();
        cellStyle6BOPY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6BOPY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6BOPY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6BOPY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOPY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6BOPY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOPY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6BOPY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOPY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6BOPY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOPY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle6BOPY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle6BOPY.setFont(font7);
        cellStyle6BOPY.setWrapText(true);


        cellStyle6BOPYG = wb.createCellStyle();
        cellStyle6BOPYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle6BOPYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle6BOPYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle6BOPYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOPYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle6BOPYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOPYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle6BOPYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOPYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle6BOPYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle6BOPYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle6BOPYG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyle6BOPYG.setFont(font7);
        cellStyle6BOPYG.setWrapText(true);


        cellStyle3BO = wb.createCellStyle();
        cellStyle3BO.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle3BO.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle3BO.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle3BO.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3BO.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle3BO.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3BO.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle3BO.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3BO.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle3BO.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3BO.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle3BO.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        cellStyle3BO.setFont(font4);
        cellStyle3BO.setWrapText(true);

        cellStyle3BOP = wb.createCellStyle();
        cellStyle3BOP.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle3BOP.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle3BOP.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle3BOP.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3BOP.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle3BOP.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3BOP.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle3BOP.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3BOP.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle3BOP.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3BOP.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle3BOP.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyle3BOP.setFont(font4);
        cellStyle3BOP.setWrapText(true);

        cellStyle4P = wb.createCellStyle();
        cellStyle4P.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4P.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4P.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4P.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4P.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4P.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4P.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4P.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4P.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4P.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4P.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4P.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyle4P.setFont(font5);
        cellStyle4P.setWrapText(true);


        cellStyle4PY = wb.createCellStyle();
        cellStyle4PY.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4PY.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4PY.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4PY.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4PY.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4PY.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4PY.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4PY.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4PY.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4PY.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4PY.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4PY.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle4PY.setFont(font5);
        cellStyle4PY.setWrapText(true);

        cellStyle4PYG = wb.createCellStyle();
        cellStyle4PYG.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle4PYG.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle4PYG.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle4PYG.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4PYG.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle4PYG.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4PYG.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle4PYG.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4PYG.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle4PYG.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle4PYG.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle4PYG.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
        cellStyle4PYG.setFont(font5);
        cellStyle4PYG.setWrapText(true);

        cellStyle3P = wb.createCellStyle();
        cellStyle3P.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle3P.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle3P.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle3P.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3P.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle3P.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3P.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle3P.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3P.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle3P.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle3P.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle3P.setFillForegroundColor(HSSFColor.GOLD.index);
        cellStyle3P.setFont(font4);
        cellStyle3P.setWrapText(true);
    }

    int week;

    public void init29(List<MonthKQInfo> mkList, HSSFSheet hssfSheet, String yearMonth, String wd, String fd) {
        try {
            if (29 <= days) {
                if (oh.getEmpNo().contains("CS")) {
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                } else {
                    inComStr = personServ.getLinShiInComDateByName(oh.getName());
                }
                isWeekEnd = DateUtil.checkIsWeekEnd(wd, 29 + "");
                ymdStr = yearMonth + "-29";
                week = DateUtil.getWeek(ymdStr);
                String commentstr = null;
                if (oh.getEmpNo().contains("CS")) {
                    commentstr = personServ.getTimeStrByDateStrAndEmpNo(oh.getEmpNo(), ymdStr);
                } else {
                    commentstr = personServ.getTimeStrByDateStrAndNameLinShiGong(oh.getEmpNo(), ymdStr);
                }


                if (oh != null && oh.getDay29AM() != null) {
                    if (oh.getDay29AM() == 1) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 2) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 4) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay29AM() == 6) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay29AM() == 11) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay29AM() == 12) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 13) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 15) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay29AM() == 18) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 7) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29AM() == 8) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29AM() == 17) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (oh.getDay29AM() == 67) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 77) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 61) {
                        cell = row.createCell(5 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 16) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue(oh.getDay29AMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 19) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue(oh.getDay29AMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29AM() == 20) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("婚");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 21) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("丧");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 22) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 23) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("陪");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 108) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 107) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (oh.getDay29AM() == 106) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 301) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 302) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 312) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 313) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 318) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 367) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 377) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 361) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 316) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 319) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPY);
                            } else {
                                cell.setCellStyle(cellStyle4BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PY);
                            } else {
                                cell.setCellStyle(cellStyle4Y);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 3108) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 3107) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOPY);
                            } else {
                                cell.setCellStyle(cellStyle6BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6PY);
                            } else {
                                cell.setCellStyle(cellStyle6Y);
                            }
                        }

                    } else if (dayJI.getDayJiAM() == 3106) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPY);
                            } else {
                                cell.setCellStyle(cellStyle4BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PY);
                            } else {
                                cell.setCellStyle(cellStyle4Y);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 325) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        cell.setCellStyle(cellStyle4BOPY);
                    } else if (dayJI.getDayJiAM() == 601) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 602) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 612) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 613) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 618) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 667) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 677) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 661) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 616) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 619) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle4BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PYG);
                            } else {
                                cell.setCellStyle(cellStyle4YG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 6108) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 6107) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle6BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6PYG);
                            } else {
                                cell.setCellStyle(cellStyle6YG);
                            }
                        }

                    } else if (dayJI.getDayJiAM() == 6106) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle4BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PYG);
                            } else {
                                cell.setCellStyle(cellStyle4YG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 625) {
                        cell = row.createCell(5 + 28);
                        cell.setCellValue("");
                        cell.setCellStyle(cellStyle4BOPYG);
                    }
                    if (commentstr != null && commentstr.trim().length() > 0) {
                        HSSFPatriarch p = hssfSheet.createDrawingPatriarch();
                        comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                        comment.setString(new HSSFRichTextString(commentstr));
                        comment.setAuthor("程序员");
                        cell.setCellComment(comment);
                    }
                } else {
                    cell = row.createCell(5 + 28);
                    cell.setCellValue("");
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4BOP);
                        } else {
                            cell.setCellStyle(cellStyle4BO);
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4P);
                        } else {
                            cell.setCellStyle(cellStyle4);
                        }
                    }
                }

                if (oh != null && oh.getDay29PM() != null) {
                    if (oh.getDay29PM() == 1) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 2) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 4) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("假");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay29PM() == 6) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay29PM() == 11) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay29PM() == 12) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 13) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 15) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay29PM() == 18) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 7) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29PM() == 8) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29PM() == 17) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (oh.getDay29PM() == 67) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 77) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 61) {
                        cell = row2.createCell(5 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 16) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue(oh.getDay29PMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 19) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue(oh.getDay29PMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29PM() == 20) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("婚");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 21) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("丧");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 22) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 23) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 108) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 107) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (oh.getDay29PM() == 106) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 601) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 602) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 612) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 613) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 618) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 667) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 677) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 661) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 616) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 619) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle4BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PYG);
                            } else {
                                cell.setCellStyle(cellStyle4YG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 6108) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 6107) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle6BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6PYG);
                            } else {
                                cell.setCellStyle(cellStyle6YG);
                            }
                        }

                    } else if (dayJI.getDayJiPM() == 6106) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle4BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PYG);
                            } else {
                                cell.setCellStyle(cellStyle4YG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 625) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        cell.setCellStyle(cellStyle4BOPYG);
                    } else if (dayJI.getDayJiPM() == 301) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 302) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 312) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 313) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 318) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 367) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 377) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 361) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 316) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 319) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPY);
                            } else {
                                cell.setCellStyle(cellStyle4BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PY);
                            } else {
                                cell.setCellStyle(cellStyle4Y);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 3108) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 3107) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOPY);
                            } else {
                                cell.setCellStyle(cellStyle6BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6PY);
                            } else {
                                cell.setCellStyle(cellStyle6Y);
                            }
                        }

                    } else if (dayJI.getDayJiPM() == 3106) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPY);
                            } else {
                                cell.setCellStyle(cellStyle4BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PY);
                            } else {
                                cell.setCellStyle(cellStyle4Y);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 325) {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        cell.setCellStyle(cellStyle4BOPY);
                    } else {
                        cell = row2.createCell(5 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                } else {
                    cell = row2.createCell(5 + 28);
                    cell.setCellValue("");
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4BOP);
                        } else {
                            cell.setCellStyle(cellStyle4BO);
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4P);
                        } else {
                            cell.setCellStyle(cellStyle4);
                        }
                    }
                }

                if (oh != null && oh.getDay29ExHours() != null) {
                    cell = row3.createCell(5 + 28);
                    cell.setCellValue(oh.getDay29ExHours() == 0.0 ? "" : oh.getDay29ExHours().toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }

                } else {
                    cell = row3.createCell(5  + 28);
                    cell.setCellValue(oh.getDay29ExHours() == null ? "" : oh.getDay29ExHours().toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init28(List<MonthKQInfo> mkList, HSSFSheet hssfSheet, String yearMonth, String wd, String fd) {

        try {
            for (int n = 0; n < dayJIList.size(); n++) {
                dayJI = dayJIList.get(n);
                if (oh.getEmpNo().contains("CS")) {
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                } else {
                    inComStr = personServ.getLinShiInComDateByName(oh.getName());
                }
                isWeekEnd = DateUtil.checkIsWeekEnd(wd, (n + 1) + "");
                isFaDing = DateUtil.checkIsFaDing(fd, (n + 1) + "");
                if (n < 9) {
                    ymdStr = yearMonth + "-0" + (n + 1);
                } else {
                    ymdStr = yearMonth + "-" + (n + 1);
                }
                week = DateUtil.getWeek(ymdStr);
                String commentstr = null;
                if (oh != null && dayJI.getDayJiAM() != null) {
                    if (oh.getEmpNo().contains("CS")) {
                        commentstr = personServ.getTimeStrByDateStrAndEmpNo(oh.getEmpNo(), ymdStr);
                    } else {
                        commentstr = personServ.getTimeStrByDateStrAndNameLinShiGong(oh.getEmpNo(), ymdStr);
                    }

                    if (dayJI.getDayJiAM() == 1) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 2) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 4) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (dayJI.getDayJiAM() == 6) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (dayJI.getDayJiAM() == 11) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (dayJI.getDayJiAM() == 12) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 13) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 15) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (dayJI.getDayJiAM() == 18) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 7) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 8) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 17) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 67) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 77) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 61) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 16) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 19) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 20) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("婚");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 21) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("丧");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 22) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 23) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("陪");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 108) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 107) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (dayJI.getDayJiAM() == 106) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 301) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 302) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 312) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 313) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 318) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 367) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 377) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 361) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 316) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 319) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPY);
                            } else {
                                cell.setCellStyle(cellStyle4BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PY);
                            } else {
                                cell.setCellStyle(cellStyle4Y);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 3108) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 3107) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOPY);
                            } else {
                                cell.setCellStyle(cellStyle6BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6PY);
                            } else {
                                cell.setCellStyle(cellStyle6Y);
                            }
                        }

                    } else if (dayJI.getDayJiAM() == 3106) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPY);
                            } else {
                                cell.setCellStyle(cellStyle4BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PY);
                            } else {
                                cell.setCellStyle(cellStyle4Y);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 325) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        cell.setCellStyle(cellStyle4BOPY);
                    } else if (dayJI.getDayJiAM() == 601) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 602) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 612) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 613) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 618) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 667) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 677) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 661) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 616) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 619) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        cell.setCellComment(comment);
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle4BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PYG);
                            } else {
                                cell.setCellStyle(cellStyle4YG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 6108) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        cell.setCellComment(comment);
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 6107) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        cell.setCellComment(comment);
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle6BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6PYG);
                            } else {
                                cell.setCellStyle(cellStyle6YG);
                            }
                        }

                    } else if (dayJI.getDayJiAM() == 6106) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle4BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PYG);
                            } else {
                                cell.setCellStyle(cellStyle4YG);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 625) {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        cell.setCellStyle(cellStyle4BOPYG);
                    } else {
                        cell = row.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                    if (commentstr != null && commentstr.trim().length() > 0) {
                        HSSFPatriarch p = hssfSheet.createDrawingPatriarch();
                        comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                        comment.setString(new HSSFRichTextString(commentstr));
                        comment.setAuthor("程序员");
                        cell.setCellComment(comment);
                    }
                }
                if (oh != null && dayJI.getDayJiPM() != null) {

                    if (dayJI.getDayJiPM() == 1) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 2) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 4) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("假");
                        cell.setCellStyle(cellStyleBR);
                    } else if (dayJI.getDayJiPM() == 6) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (dayJI.getDayJiPM() == 11) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (dayJI.getDayJiPM() == 12) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 13) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 15) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("假");
                        cell.setCellStyle(cellStyleBR);
                    } else if (dayJI.getDayJiPM() == 18) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 7) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 8) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 17) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 67) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 77) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 61) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 16) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 19) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 20) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 21) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 22) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 23) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 108) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 107) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (dayJI.getDayJiPM() == 106) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 601) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 602) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 612) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 613) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 618) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 667) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 677) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 661) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 616) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 619) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle4BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PYG);
                            } else {
                                cell.setCellStyle(cellStyle4YG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 6108) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPYG);
                            } else {
                                cell.setCellStyle(cellStyleAYG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 6107) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle6BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6PYG);
                            } else {
                                cell.setCellStyle(cellStyle6YG);
                            }
                        }

                    } else if (dayJI.getDayJiPM() == 6106) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPYG);
                            } else {
                                cell.setCellStyle(cellStyle4BOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PYG);
                            } else {
                                cell.setCellStyle(cellStyle4YG);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 625) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        cell.setCellStyle(cellStyle4BOPYG);
                    } else if (dayJI.getDayJiPM() == 301) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 302) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 312) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 313) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPYG);
                            } else {
                                cell.setCellStyle(cellStyleBOYG);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 318) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 367) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 377) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 361) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 316) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 319) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPY);
                            } else {
                                cell.setCellStyle(cellStyle4BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PY);
                            } else {
                                cell.setCellStyle(cellStyle4Y);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 3108) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOPY);
                            } else {
                                cell.setCellStyle(cellStyleBOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAPY);
                            } else {
                                cell.setCellStyle(cellStyleAY);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 3107) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOPY);
                            } else {
                                cell.setCellStyle(cellStyle6BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6PY);
                            } else {
                                cell.setCellStyle(cellStyle6Y);
                            }
                        }

                    } else if (dayJI.getDayJiPM() == 3106) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOPY);
                            } else {
                                cell.setCellStyle(cellStyle4BOY);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4PY);
                            } else {
                                cell.setCellStyle(cellStyle4Y);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 325) {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        cell.setCellStyle(cellStyle4BOPY);
                    } else {
                        cell = row2.createCell(5 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                }
                if (oh != null && dayJI.getDayJiExHours() != null) {
                    cell = row3.createCell(5 + n);
                    cell.setCellValue((dayJI.getDayJiExHours() == 0.0 ? "" : dayJI.getDayJiExHours()).toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }

                } else {
                    cell = row3.createCell(5 + n);
                    cell.setCellValue((dayJI.getDayJiExHours() == null ? "" : dayJI.getDayJiExHours()).toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createThreeSheet(List<MonthKQInfo> mkList1, HSSFSheet hssfSheet3, String yearMonth, String wd, String fd) throws Exception{
        beginRow = 3;
        row = hssfSheet3.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue(yearMonth + "月份考勤表");
        cell.setCellStyle(cellStyle2);

        row = hssfSheet3.createRow(1);
        row.setHeight((short) 400);
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("部门");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("星期");
        cell.setCellStyle(cellStyle);

        row2 = hssfSheet3.createRow(2);
        row2.setHeight((short) 400);
        cell = row2.createCell(3);
        cell.setCellValue("日期");
        cell.setCellStyle(cellStyle);

        hssfSheet3.setColumnWidth(0, 6 * 256);
        hssfSheet3.setColumnWidth(1, 8 * 256);
        hssfSheet3.setColumnWidth(2, 6 * 256);
        hssfSheet3.setColumnWidth(3, 6 * 256);

        for (int i = 1; i <= days; i++) {
            cell = row.createCell(3 + i);
            if (i < 10) {
                day = "0" + i;
            } else {
                day = i + "";
            }

            isWeekEnd = DateUtil.checkIsWeekEnd(wd, i + "");
            isFaDing = DateUtil.checkIsFaDing(fd, i + "");

            hssfSheet3.setColumnWidth(3 + i, 4 * 270);
            cell.setCellValue(DateUtil.getWeekStr(yearMonth + "-" + day));
            if (isWeekEnd) {
                if (isFaDing) {
                    cell.setCellStyle(cellStyleBR);
                } else {
                    cell.setCellStyle(cellStyleBR);
                }
            } else {
                if (isFaDing) {
                    cell.setCellStyle(cellStyleBR);
                } else {
                    cell.setCellStyle(cellStyle);
                }
            }
            cell = row2.createCell(3 + i);
            cell.setCellValue(i);
            if (isWeekEnd) {
                if (isFaDing) {
                    cell.setCellStyle(cellStyleBR);
                } else {
                    cell.setCellStyle(cellStyleBR);
                }
            } else {
                if (isFaDing) {
                    cell.setCellStyle(cellStyleBR);
                } else {
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        cell = row.createCell(3 + days + 1);
        cell.setCellValue("正班出勤工时");
        hssfSheet3.setColumnWidth(3 + days + 1, 5 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 2);
        hssfSheet3.setColumnWidth(3 + days + 2, 5 * 256);
        cell.setCellValue("平时加班(H)");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 3);
        hssfSheet3.setColumnWidth(3 + days + 3, 5 * 256);
        cell.setCellValue("周末加班(H)");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 4);
        cell.setCellValue("合计上班工时");
        hssfSheet3.setColumnWidth(3 + days + 4, 4 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 5);
        cell.setCellValue("伙食费");
        hssfSheet3.setColumnWidth(3 + days + 5, 5 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 6);
        cell.setCellValue("房租/水电费");
        hssfSheet3.setColumnWidth(3 + days + 6, 5 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 7);
        cell.setCellValue("扣代付养老险");
        hssfSheet3.setColumnWidth(3 + days + 7, 6 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 8);
        cell.setCellValue("扣代付医疗险");
        hssfSheet3.setColumnWidth(3 + days + 8, 6 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 9);
        cell.setCellValue("扣代付失业险");
        hssfSheet3.setColumnWidth(3 + days + 9, 6 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 10);
        cell.setCellValue("扣代付公积金");
        hssfSheet3.setColumnWidth(3 + days + 10, 6 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 11);
        cell.setCellValue("代扣家属旅游费");
        hssfSheet3.setColumnWidth(3 + days + 11, 5 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 12);
        cell.setCellValue("工作失误");
        hssfSheet3.setColumnWidth(3 + days + 12, 5 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 13);
        cell.setCellValue("绩效分");
        hssfSheet3.setColumnWidth(3 + days + 13, 5 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 14);
        cell.setCellValue("备注");
        hssfSheet3.setColumnWidth(3 + days + 14, 5 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 15);
        cell.setCellValue("请确定无误后签字");
        hssfSheet3.setColumnWidth(3 + days + 15, 5 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 16);
        cell.setCellValue("迟到分钟");
        hssfSheet3.setColumnWidth(3 + days + 16, 5 * 256);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3 + days + 17);
        cell.setCellValue("迟到次数");
        hssfSheet3.setColumnWidth(3 + days + 17, 5 * 256);
        cell.setCellStyle(cellStyle);

        region = new CellRangeAddress(1, 2, 0, 0);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 1, 1);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 2, 2);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 1, 3 + days + 1);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 2, 3 + days + 2);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 3, 3 + days + 3);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 4, 3 + days + 4);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 5, 3 + days + 5);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);


        region = new CellRangeAddress(1, 2, 3 + days + 6, 3 + days + 6);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 7, 3 + days + 7);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 8, 3 + days + 8);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 9, 3 + days + 9);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 10, 3 + days + 10);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 11, 3 + days + 11);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 12, 3 + days + 12);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 13, 3 + days + 13);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 14, 3 + days + 14);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 15, 3 + days + 15);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 16, 3 + days + 16);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(1, 2, 3 + days + 17, 3 + days + 17);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

        region = new CellRangeAddress(0, 0, 0, 3 + days);
        hssfSheet3.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet3, wb);
        for (int a = 0; a < mkList1.size(); a++) {
            oh = mkList1.get(a);
            try {
                af = personServ.getAFByName(oh.getName());
                is = personServ.getISByName(oh.getName());
                dayJIList = getDayJIListByMKList28(oh);
            } catch (Exception e) {
                e.printStackTrace();
            }
            row = hssfSheet3.createRow(beginRow++);
            row.setHeight((short) 400);
            cell = row.createCell(0);
            cell.setCellValue(a + 1);
            cell.setCellStyle(cellStyleA);

            cell = row.createCell(1);
            cell.setCellValue(oh.getName());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(oh.getDeptName());
            cell.setCellStyle(cellStyleA);

            cell = row.createCell(3);
            cell.setCellValue("上午");
            cell.setCellStyle(cellStyleA);

            row2 = hssfSheet3.createRow(beginRow++);
            row2.setHeight((short) 400);
            cell = row2.createCell(3);
            cell.setCellValue("下午");
            cell.setCellStyle(cellStyleA);

            row3 = hssfSheet3.createRow(beginRow++);
            row3.setHeight((short) 400);
            cell = row3.createCell(3);
            cell.setCellValue("加班");
            cell.setCellStyle(cellStyleA);

            int week = 0;
            for (int n = 0; n < dayJIList.size(); n++) {
                dayJI = dayJIList.get(n);
                if (oh.getEmpNo().contains("CS")) {
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                } else {
                    try {
                        inComStr = personServ.getLinShiInComDateByName(oh.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isWeekEnd = DateUtil.checkIsWeekEnd(wd, (n + 1) + "");
                isFaDing = DateUtil.checkIsFaDing(fd, (n + 1) + "");
                if (n < 9) {
                    ymdStr = yearMonth + "-0" + (n + 1);
                } else {
                    ymdStr = yearMonth + "-" + (n + 1);
                }
                try {
                    week = DateUtil.getWeek(ymdStr);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                String commentstr = null;
                if (oh.getEmpNo().contains("CS")) {
                    commentstr = personServ.getTimeStrByDateStrAndEmpNo(oh.getEmpNo(), ymdStr);
                } else {
                    commentstr = personServ.getTimeStrByDateStrAndNameLinShiGong(oh.getEmpNo(), ymdStr);
                }
                if (oh != null && dayJI.getDayJiAM() != null) {
                    if (dayJI.getDayJiAM() == 1) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 2) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 4) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (dayJI.getDayJiAM() == 6) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (dayJI.getDayJiAM() == 11) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (dayJI.getDayJiAM() == 12) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 13) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 15) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (dayJI.getDayJiAM() == 18) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 7) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 8) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 17) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 67) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 77) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 61) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 16) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 19) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue(dayJI.getDayJiAMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 20) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("婚");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 21) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("丧");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 22) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 23) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("陪");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 108) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiAM() == 107) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (dayJI.getDayJiAM() == 106) {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else {
                        cell = row.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }

                    if (commentstr != null && commentstr.trim().length() > 0) {
                        HSSFPatriarch p = hssfSheet3.createDrawingPatriarch();
                        comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                        comment.setString(new HSSFRichTextString(commentstr));
                        comment.setAuthor("程序员");
                        cell.setCellComment(comment);
                    }
                }
                if (oh != null && dayJI.getDayJiPM() != null) {

                    if (dayJI.getDayJiPM() == 1) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 2) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 4) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("假");
                        cell.setCellStyle(cellStyleBR);
                    } else if (dayJI.getDayJiPM() == 6) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (dayJI.getDayJiPM() == 11) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (dayJI.getDayJiPM() == 12) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 13) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 15) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("假");
                        cell.setCellStyle(cellStyleBR);
                    } else if (dayJI.getDayJiPM() == 18) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 7) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 8) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 17) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 67) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 77) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 61) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 16) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 19) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue(dayJI.getDayJiPMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 20) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 21) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 22) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 23) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 108) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (dayJI.getDayJiPM() == 107) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (dayJI.getDayJiPM() == 106) {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else {
                        cell = row2.createCell(4 + n);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                }
                if (oh != null && dayJI.getDayJiExHours() != null) {
                    cell = row3.createCell(4 + n);
                    cell.setCellValue((dayJI.getDayJiExHours() == 0.0 ? "" : dayJI.getDayJiExHours()).toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }

                } else {
                    cell = row3.createCell(4 + n);
                    cell.setCellValue((dayJI.getDayJiExHours() == null ? "" : dayJI.getDayJiExHours()).toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }
                }
            }


            if (29 <= days) {
                if (oh.getEmpNo().contains("CS")) {
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                } else {
                    try {
                        inComStr = personServ.getLinShiInComDateByName(oh.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isWeekEnd = DateUtil.checkIsWeekEnd(wd, 29 + "");
                ymdStr = yearMonth + "-29";
                try {
                    week = DateUtil.getWeek(ymdStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String commentstr = null;
                if (oh.getEmpNo().contains("CS")) {
                    commentstr = personServ.getTimeStrByDateStrAndEmpNo(oh.getEmpNo(), ymdStr);
                } else {
                    commentstr = personServ.getTimeStrByDateStrAndNameLinShiGong(oh.getEmpNo(), ymdStr);
                }
                if (oh != null && oh.getDay29AM() != null) {
                    if (oh.getDay29AM() == 1) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 2) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 4) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay29AM() == 6) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay29AM() == 11) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay29AM() == 12) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 13) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 15) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay29AM() == 18) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 7) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29AM() == 8) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29AM() == 17) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (oh.getDay29AM() == 67) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 77) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 61) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 16) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue(oh.getDay29AMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 19) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue(oh.getDay29AMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29AM() == 20) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("婚");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 21) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("丧");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 22) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 23) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("陪");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 108) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29AM() == 107) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (oh.getDay29AM() == 106) {
                        cell = row.createCell(4 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                    if (commentstr != null && commentstr.trim().length() > 0) {
                        HSSFPatriarch p = hssfSheet3.createDrawingPatriarch();
                        comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                        comment.setString(new HSSFRichTextString(commentstr));
                        comment.setAuthor("程序员");
                        cell.setCellComment(comment);
                    }
                } else {
                    cell = row.createCell(4 + 28);
                    cell.setCellValue("");
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4BOP);
                        } else {
                            cell.setCellStyle(cellStyle4BO);
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4P);
                        } else {
                            cell.setCellStyle(cellStyle4);
                        }
                    }
                }

                if (oh != null && oh.getDay29PM() != null) {
                    if (oh.getDay29PM() == 1) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 2) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 4) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("假");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay29PM() == 6) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay29PM() == 11) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay29PM() == 12) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 13) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 15) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay29PM() == 18) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 7) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29PM() == 8) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29PM() == 17) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (oh.getDay29PM() == 67) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 77) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 61) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 16) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue(oh.getDay29PMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 19) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue(oh.getDay29PMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay29PM() == 20) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("婚");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 21) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("丧");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 22) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 23) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 108) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay29PM() == 107) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (oh.getDay29PM() == 106) {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else {
                        cell = row2.createCell(4 + 28);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                } else {
                    cell = row2.createCell(4 + 28);
                    cell.setCellValue("");
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4BOP);
                        } else {
                            cell.setCellStyle(cellStyle4BO);
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4P);
                        } else {
                            cell.setCellStyle(cellStyle4);
                        }
                    }
                }

                if (oh != null && oh.getDay29ExHours() != null) {
                    cell = row3.createCell(4 + 28);
                    cell.setCellValue(oh.getDay29ExHours() == 0.0 ? "" : oh.getDay29ExHours().toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }

                } else {
                    cell = row3.createCell(4 + 28);
                    cell.setCellValue(oh.getDay29ExHours() == null ? "" : oh.getDay29ExHours().toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }
                }
            }

            if (30 <= days) {
                if (oh.getEmpNo().contains("CS")) {
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                } else {
                    try {
                        inComStr = personServ.getLinShiInComDateByName(oh.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isWeekEnd = DateUtil.checkIsWeekEnd(wd, 30 + "");
                ymdStr = yearMonth + "-30";
                try {
                    week = DateUtil.getWeek(ymdStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String commentstr = null;
                if (oh.getEmpNo().contains("CS")) {
                    commentstr = personServ.getTimeStrByDateStrAndEmpNo(oh.getEmpNo(), ymdStr);
                } else {
                    commentstr = personServ.getTimeStrByDateStrAndNameLinShiGong(oh.getEmpNo(), ymdStr);
                }
                if (oh != null && oh.getDay30AM() != null) {
                    if (oh.getDay30AM() == 1) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 2) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 4) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay30AM() == 6) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay30AM() == 11) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay30AM() == 12) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 13) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 15) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay30AM() == 18) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 7) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay30AM() == 8) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay30AM() == 17) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (oh.getDay30AM() == 67) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 77) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 61) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 16) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue(oh.getDay30AMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 19) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue(oh.getDay30AMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay30AM() == 20) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("婚");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 21) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("丧");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 22) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 23) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("陪");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 108) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30AM() == 107) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (oh.getDay30AM() == 106) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                    if (commentstr != null && commentstr.trim().length() > 0) {
                        HSSFPatriarch p = hssfSheet3.createDrawingPatriarch();
                        comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                        comment.setString(new HSSFRichTextString(commentstr));
                        comment.setAuthor("程序员");
                        cell.setCellComment(comment);
                    }
                } else {
                    cell = row.createCell(4 + 29);
                    cell.setCellValue("");
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4BOP);
                        } else {
                            cell.setCellStyle(cellStyle4BO);
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4P);
                        } else {
                            cell.setCellStyle(cellStyle4);
                        }
                    }
                }
                if (oh != null && oh.getDay30PM() != null) {
                    if (oh.getDay30PM() == 1) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 2) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 4) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay30AM() == 6) {
                        cell = row.createCell(4 + 29);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay30PM() == 11) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay30PM() == 12) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 13) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 15) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("假");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay30PM() == 18) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 7) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay30PM() == 8) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay30PM() == 17) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (oh.getDay30PM() == 67) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 77) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 61) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 16) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue(oh.getDay30PMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 19) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue(oh.getDay30PMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay30PM() == 20) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 21) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("丧");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 22) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 23) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 108) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay30PM() == 107) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (oh.getDay30PM() == 106) {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else {
                        cell = row2.createCell(4 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                } else {
                    cell = row2.createCell(4 + 29);
                    cell.setCellValue("");
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4BOP);
                        } else {
                            cell.setCellStyle(cellStyle4BO);
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4P);
                        } else {
                            cell.setCellStyle(cellStyle4);
                        }
                    }
                }

                if (oh != null && oh.getDay30ExHours() != null) {
                    cell = row3.createCell(4 + 29);
                    cell.setCellValue(oh.getDay30ExHours() == 0.0 ? "" : oh.getDay30ExHours().toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }

                } else {
                    cell = row3.createCell(4 + 29);
                    cell.setCellValue(oh.getDay30ExHours() == null ? "" : oh.getDay30ExHours().toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }
                }
            }

            if (31 <= days) {
                if (oh.getEmpNo().contains("CS")) {
                    inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                } else {
                    try {
                        inComStr = personServ.getLinShiInComDateByName(oh.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isWeekEnd = DateUtil.checkIsWeekEnd(wd, 31 + "");
                ymdStr = yearMonth + "-31";
                try {
                    week = DateUtil.getWeek(ymdStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String commentstr = null;
                if (oh.getEmpNo().contains("CS")) {
                    commentstr = personServ.getTimeStrByDateStrAndEmpNo(oh.getEmpNo(), ymdStr);
                } else {
                    commentstr = personServ.getTimeStrByDateStrAndNameLinShiGong(oh.getEmpNo(), ymdStr);
                }
                if (oh != null && oh.getDay31AM() != null) {
                    if (oh.getDay31AM() == 1) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 2) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 4) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay31AM() == 6) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay31AM() == 11) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay31AM() == 12) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 13) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("夜");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 15) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("休");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay31AM() == 18) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 7) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay31AM() == 8) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay31AM() == 17) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (oh.getDay31AM() == 67) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 77) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 61) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 16) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue(oh.getDay31AMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 19) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue(oh.getDay31AMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay31AM() == 20) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("婚");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 21) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("丧");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 22) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 23) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("陪");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 108) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31AM() == 107) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (oh.getDay31AM() == 106) {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else {
                        cell = row.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                    if (commentstr != null && commentstr.trim().length() > 0) {
                        HSSFPatriarch p = hssfSheet3.createDrawingPatriarch();
                        comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                        comment.setString(new HSSFRichTextString(commentstr));
                        comment.setAuthor("程序员");
                        cell.setCellComment(comment);
                    }
                } else {
                    cell = row.createCell(4 + 30);
                    cell.setCellValue("");
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4BOP);
                        } else {
                            cell.setCellStyle(cellStyle4BO);
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4P);
                        } else {
                            cell.setCellStyle(cellStyle4);
                        }
                    }
                }

                if (oh != null && oh.getDay31PM() != null) {
                    if (oh.getDay31PM() == 1) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 2) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 4) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("假");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay31PM() == 6) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("△");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay31PM() == 11) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("▲");
                        cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                    } else if (oh.getDay31PM() == 12) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("●");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 13) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("班");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 15) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("假");
                        cell.setCellStyle(cellStyleBR);
                    } else if (oh.getDay31PM() == 18) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 7) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }

                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay31PM() == 8) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("✖");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay31PM() == 17) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("√");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3BOP);
                            } else {
                                cell.setCellStyle(cellStyle3BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle3P);
                            } else {
                                cell.setCellStyle(cellStyle3);
                            }
                        }
                    } else if (oh.getDay31PM() == 67) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 77) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 61) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 16) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue(oh.getDay31PMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 19) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue(oh.getDay31PMRemark());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else if (oh.getDay31PM() == 20) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 21) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 22) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("假");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 23) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("产");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 108) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleBOP);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyleAP);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    } else if (oh.getDay31PM() == 107) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6BOP);
                            } else {
                                cell.setCellStyle(cellStyle6BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle6P);
                            } else {
                                cell.setCellStyle(cellStyle6);
                            }
                        }

                    } else if (oh.getDay31PM() == 106) {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("О");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    } else {
                        cell = row2.createCell(4 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                } else {
                    cell = row2.createCell(4 + 30);
                    cell.setCellValue("");
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4BOP);
                        } else {
                            cell.setCellStyle(cellStyle4BO);
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            cell.setCellStyle(cellStyle4P);
                        } else {
                            cell.setCellStyle(cellStyle4);
                        }
                    }
                }
                if (oh != null && oh.getDay31ExHours() != null) {
                    cell = row3.createCell(4 + 30);
                    cell.setCellValue(oh.getDay31ExHours() == 0.0 ? "" : oh.getDay31ExHours().toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }

                } else {
                    cell = row3.createCell(4 + 30);
                    cell.setCellValue(oh.getDay31ExHours() == null ? "" : oh.getDay31ExHours().toString());
                    if (week == 6 || week == 7 || isWeekEnd) {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBOP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleBO);
                            }
                        }
                    } else {
                        if (inComStr.equals(ymdStr)) {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleAP);
                            }
                        } else {
                            if (isFaDing) {
                                cell.setCellStyle(cellStyleBR);
                            } else {
                                cell.setCellStyle(cellStyleA);
                            }
                        }
                    }
                }


            }


            cell = row.createCell(3 + days + 1);
            cell.setCellValue(oh.getZhengbanHours());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 2);
            cell.setCellValue(oh.getUsualExtHours());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 3);
            cell.setCellValue(oh.getWorkendHours());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 4);
            cell.setCellValue(oh.getWorkendHours() + oh.getUsualExtHours() + oh.getZhengbanHours());
            cell.setCellStyle(cellStyleRR);

            cell = row.createCell(3 + days + 5);
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 6);
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 7);
            cell.setCellValue(((is == null || is.getEndowInsur() == null || is.getEndowInsur() == 0) ? "" : is.getEndowInsur()) + "");
            cell.setCellStyle(cellStyleABC);

            cell = row.createCell(3 + days + 8);
            cell.setCellValue(((is == null || is.getMedicalInsur() == null || is.getMedicalInsur() == 0) ? "" : is.getMedicalInsur()).toString());
            cell.setCellStyle(cellStyleABC);

            cell = row.createCell(3 + days + 9);
            cell.setCellValue(((is == null || is.getUnWorkInsur() == null || is.getUnWorkInsur() == 0) ? "" : is.getUnWorkInsur()).toString());
            cell.setCellStyle(cellStyleABC);

            cell = row.createCell(3 + days + 10);
            cell.setCellValue(((af == null || af.getAccuFundFee() == null || af.getAccuFundFee() == 0) ? "" : af.getAccuFundFee()).toString());
            cell.setCellStyle(cellStyleABC);


            cell = row.createCell(3 + days + 11);
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);


            cell = row.createCell(3 + days + 12);
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);


            cell = row.createCell(3 + days + 13);
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 14);
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 15);
            cell.setCellValue("");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 16);
            cell.setCellValue(oh.getLateminites());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3 + days + 17);
            cell.setCellValue(oh.getLatetimes());
            cell.setCellStyle(cellStyle);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 0, 0);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);


            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 1, 1);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 2, 2);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 1, 3 + days + 1);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 2, 3 + days + 2);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 3, 3 + days + 3);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 4, 3 + days + 4);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 5, 3 + days + 5);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 6, 3 + days + 6);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 7, 3 + days + 7);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 8, 3 + days + 8);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 9, 3 + days + 9);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 10, 3 + days + 10);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 11, 3 + days + 11);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 12, 3 + days + 12);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 13, 3 + days + 13);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 14, 3 + days + 14);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 15, 3 + days + 15);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 16, 3 + days + 16);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3 + days + 17, 3 + days + 17);
            hssfSheet3.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet3, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet3, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet3, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet3, wb);

            dayJIList.clear();


        }
    }

    public void createTwoSheet(List<MonthKQInfo> mkList, HSSFSheet hssfSheet2, String yearMonth, String wd, String fd) {
        row = hssfSheet2.createRow(0);
        row.setHeight((short) 600);
        cell = row.createCell(0);
        cell.setCellValue(yearMonth + "月考勤");
        cell.setCellStyle(cellStyle2);

        row = hssfSheet2.createRow(1);
        row.setHeight((short) 800);
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("部门");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellValue("正班出勤工时");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue("平时加班(H)");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(5);
        cell.setCellValue("周末加班(H)");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(6);
        cell.setCellValue("国家有薪假(H)");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(7);
        cell.setCellValue("其它有薪假(H)");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(8);
        cell.setCellValue("事假(H)");
        cell.setCellStyle(cellStylePP);

        cell = row.createCell(9);
        cell.setCellValue("病假(H)");
        cell.setCellStyle(cellStyleBB);

        cell = row.createCell(10);
        cell.setCellValue("其它补贴(出差/夜)");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(11);
        cell.setCellValue("全勤奖");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(12);
        cell.setCellValue("伙食费");
        cell.setCellStyle(cellStyleBB);

        cell = row.createCell(13);
        cell.setCellValue("房租/水电费");
        cell.setCellStyle(cellStyleBB);

        cell = row.createCell(14);
        cell.setCellValue("扣代付养老险");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(15);
        cell.setCellValue("扣代付医疗险");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(16);
        cell.setCellValue("扣代付失业险");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(17);
        cell.setCellValue("扣代付公积金");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(18);
        cell.setCellValue("工作失误");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(19);
        cell.setCellValue("绩效分");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(20);
        cell.setCellValue("备注");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(21);
        cell.setCellValue("请确定无误后签字");
        cell.setCellStyle(cellStyle);

        region = new CellRangeAddress(0, 0, 0, 21);
        hssfSheet2.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet2, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet2, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet2, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet2, wb);


        hssfSheet2.setColumnWidth(0, 6 * 256);
        hssfSheet2.setColumnWidth(1, 8 * 256);
        hssfSheet2.setColumnWidth(2, 10 * 256);
        hssfSheet2.setColumnWidth(3, 7 * 256);
        hssfSheet2.setColumnWidth(4, 7 * 256);
        hssfSheet2.setColumnWidth(5, 7 * 256);
        hssfSheet2.setColumnWidth(6, 7 * 256);
        hssfSheet2.setColumnWidth(7, 7 * 256);
        hssfSheet2.setColumnWidth(8, 7 * 256);
        hssfSheet2.setColumnWidth(9, 7 * 256);
        hssfSheet2.setColumnWidth(10, 7 * 256);
        hssfSheet2.setColumnWidth(11, 7 * 256);
        hssfSheet2.setColumnWidth(12, 7 * 256);
        hssfSheet2.setColumnWidth(13, 7 * 256);
        hssfSheet2.setColumnWidth(14, 7 * 256);
        hssfSheet2.setColumnWidth(15, 7 * 256);
        hssfSheet2.setColumnWidth(16, 7 * 256);
        hssfSheet2.setColumnWidth(17, 7 * 256);
        hssfSheet2.setColumnWidth(18, 7 * 256);
        hssfSheet2.setColumnWidth(19, 7 * 256);
        hssfSheet2.setColumnWidth(20, 10 * 256);
        hssfSheet2.setColumnWidth(21, 11 * 256);
        try {
            for (int i = 0; i < mkList.size(); i++) {
                oh = mkList.get(i);
                af = personServ.getAFByEmpNo(oh.getEmpNo());
                is = personServ.getISByEmpNo(oh.getEmpNo());
                row = hssfSheet2.createRow(i + 2);
                row.setHeight((short) 400);
                cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell.setCellStyle(cellStyle3);

                cell = row.createCell(1);
                cell.setCellValue(oh.getName() == null ? "" : oh.getName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(oh.getDeptName() == null ? "" : oh.getDeptName());
                cell.setCellStyle(cellStyle3);

                cell = row.createCell(3);
                cell.setCellValue(oh.getZhengbanHours() == null ? 0 : oh.getZhengbanHours());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue(oh.getUsualExtHours() == null ? 0 : oh.getUsualExtHours());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellValue(oh.getWorkendHours() == null ? 0 : oh.getWorkendHours());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(6);
                cell.setCellValue(((oh.getChinaPaidLeave() == null || oh.getChinaPaidLeave() == 0) ? "" : oh.getChinaPaidLeave()) + "");
                cell.setCellStyle(cellStyleRR);

                cell = row.createCell(7);
                cell.setCellValue(((oh.getOtherPaidLeave() == null || oh.getOtherPaidLeave() == 0) ? "" : oh.getOtherPaidLeave()) + "");
                cell.setCellStyle(cellStyle);


                cell = row.createCell(8);
                cell.setCellValue(((oh.getLeaveOfAbsense() == null || oh.getLeaveOfAbsense() == 0) ? "" : oh.getLeaveOfAbsense()) + "");
                cell.setCellStyle(cellStylePP);

                cell = row.createCell(9);
                cell.setCellValue(((oh.getSickLeave() == null || oh.getSickLeave() == 0) ? "" : oh.getSickLeave()) + "");
                cell.setCellStyle(cellStyleBB);

                cell = row.createCell(10);
                cell.setCellValue(((oh.getOtherAllo() == null || oh.getOtherAllo() == 0) ? "" : oh.getOtherAllo()) + "");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(11);
                cell.setCellValue(oh.getFullWorkReword() == null ? 0 : oh.getFullWorkReword());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(12);
                cell.setCellValue("");
                cell.setCellStyle(cellStyleBBThin);

                cell = row.createCell(13);
                cell.setCellValue("");
                cell.setCellStyle(cellStyleBBThin);

                cell = row.createCell(14);
                cell.setCellValue((is == null || is.getEndowInsur() == null) ? 0 : is.getEndowInsur());
                cell.setCellStyle(cellStyleRRThin);

                cell = row.createCell(15);
                cell.setCellValue((is == null || is.getMedicalInsur() == null) ? 0 : is.getMedicalInsur());
                cell.setCellStyle(cellStyleRRThin);

                cell = row.createCell(16);
                cell.setCellValue((is == null || is.getUnWorkInsur() == null) ? 0.0 : is.getUnWorkInsur());
                cell.setCellStyle(cellStyleRRThin);

                cell = row.createCell(17);
                cell.setCellValue((af == null || af.getAccuFundFee() == null) ? 0 : af.getAccuFundFee());
                cell.setCellStyle(cellStyleRRThin);

                cell = row.createCell(18);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(19);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle3);

                cell = row.createCell(20);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle3);

                cell = row.createCell(21);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    HSSFComment comment;

    public void createOneSheet(List<MonthKQInfo> mkList, HSSFSheet hssfSheet, String yearMonth, String wd, String fd) {
        beginRow = 3;
        try {
            row = hssfSheet.createRow(0);
            row2 = null;
            row3 = null;
            cell = row.createCell(0);
            cell.setCellValue(yearMonth + "月份考勤表");
            cell.setCellStyle(cellStyle2);

            row = hssfSheet.createRow(1);
            row.setHeight((short) 400);
            cell = row.createCell(0);
            cell.setCellValue("序号");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue("姓名");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue("部门");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue("工号");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue("星期");
            cell.setCellStyle(cellStyle);

            row2 = hssfSheet.createRow(2);
            row2.setHeight((short) 400);
            cell = row2.createCell(4);
            cell.setCellValue("日期");
            cell.setCellStyle(cellStyle);

            hssfSheet.setColumnWidth(0, 6 * 256);
            hssfSheet.setColumnWidth(1, 8 * 256);
            hssfSheet.setColumnWidth(2, 6 * 256);
            hssfSheet.setColumnWidth(3, 6 * 256);
            hssfSheet.setColumnWidth(4, 8 * 256);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            days = DateUtil.getDaysByYearMonth(sdf.parse(yearMonth + "-01"));

            for (int i = 1; i <= days; i++) {
                cell = row.createCell(4 + i);
                if (i < 10) {
                    day = "0" + i;
                } else {
                    day = i + "";
                }

                isWeekEnd = DateUtil.checkIsWeekEnd(wd, i + "");
                isFaDing = DateUtil.checkIsFaDing(fd, i + "");

                hssfSheet.setColumnWidth(4 + i, 4 * 270);
                cell.setCellValue(DateUtil.getWeekStr(yearMonth + "-" + day));
                if (isWeekEnd) {
                    if (isFaDing) {
                        cell.setCellStyle(cellStyleBR);
                    } else {
                        cell.setCellStyle(cellStyleBR);
                    }
                } else {
                    if (isFaDing) {
                        cell.setCellStyle(cellStyleBR);
                    } else {
                        cell.setCellStyle(cellStyle);
                    }
                }
                cell = row2.createCell(4 + i);
                cell.setCellValue(i);
                if (isWeekEnd) {
                    if (isFaDing) {
                        cell.setCellStyle(cellStyleBR);
                    } else {
                        cell.setCellStyle(cellStyleBR);
                    }
                } else {
                    if (isFaDing) {
                        cell.setCellStyle(cellStyleBR);
                    } else {
                        cell.setCellStyle(cellStyle);
                    }
                }
            }

            cell = row.createCell(4 + days + 1);
            cell.setCellValue("正班出勤工时");
            hssfSheet.setColumnWidth(4 + days + 1, 5 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 2);
            hssfSheet.setColumnWidth(4 + days + 2, 5 * 256);
            cell.setCellValue("平时加班(H)");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 3);
            hssfSheet.setColumnWidth(4 + days + 3, 5 * 256);
            cell.setCellValue("周末加班(H)");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 4);
            cell.setCellValue("国家有薪假(H)");
            hssfSheet.setColumnWidth(4 + days + 4, 4 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 5);
            cell.setCellValue("其它有薪假(H)");
            hssfSheet.setColumnWidth(4 + days + 5, 4 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 6);
            cell.setCellValue("事假(H)");
            hssfSheet.setColumnWidth(4 + days + 6, 4 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 7);
            cell.setCellValue("病假(H)");
            hssfSheet.setColumnWidth(4 + days + 7, 4 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 8);
            cell.setCellValue("其它补贴(出差/夜班)");
            hssfSheet.setColumnWidth(4 + days + 8, 4 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 9);
            cell.setCellValue("全勤奖");
            hssfSheet.setColumnWidth(4 + days + 9, 5 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 10);
            cell.setCellValue("伙食费");
            hssfSheet.setColumnWidth(4 + days + 10, 5 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 11);
            cell.setCellValue("房租/水电费");
            hssfSheet.setColumnWidth(4 + days + 11, 5 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 12);
            cell.setCellValue("扣代付养老险");
            hssfSheet.setColumnWidth(4 + days + 12, 6 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 13);
            cell.setCellValue("扣代付医疗险");
            hssfSheet.setColumnWidth(4 + days + 13, 6 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 14);
            cell.setCellValue("扣代付失业险");
            hssfSheet.setColumnWidth(4 + days + 14, 6 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 15);
            cell.setCellValue("扣代付公积金");
            hssfSheet.setColumnWidth(4 + days + 15, 6 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 16);
            cell.setCellValue("代扣家属旅游费");
            hssfSheet.setColumnWidth(4 + days + 16, 5 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 17);
            cell.setCellValue("工作失误");
            hssfSheet.setColumnWidth(4 + days + 17, 5 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 18);
            cell.setCellValue("绩效分");
            hssfSheet.setColumnWidth(4 + days + 18, 5 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 19);
            cell.setCellValue("备注");
            hssfSheet.setColumnWidth(4 + days + 19, 5 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 20);
            cell.setCellValue("请确定无误后签字");
            hssfSheet.setColumnWidth(4 + days + 20, 5 * 256);
            cell.setCellStyle(cellStyle);


            cell = row.createCell(4 + days + 21);
            cell.setCellValue("总正班工时");
            hssfSheet.setColumnWidth(4 + days + 21, 5 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4 + days + 22);
            cell.setCellValue("正班工时核对");
            hssfSheet.setColumnWidth(4 + days + 22, 5 * 256);
            cell.setCellStyle(cellStyle);


            cell = row.createCell(4 + days + 23);
            cell.setCellValue("迟到分钟");
            hssfSheet.setColumnWidth(4 + days + 23, 5 * 256);
            cell.setCellStyle(cellStyle);


            cell = row.createCell(4 + days + 24);
            cell.setCellValue("迟到次数");
            hssfSheet.setColumnWidth(4 + days + 24, 5 * 256);
            cell.setCellStyle(cellStyle);


            region = new CellRangeAddress(1, 2, 0, 0);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 1, 1);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 2, 2);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 3, 3);
            hssfSheet.addMergedRegion(region);


            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 1, 4 + days + 1);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 2, 4 + days + 2);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 3, 4 + days + 3);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 4, 4 + days + 4);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 5, 4 + days + 5);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 6, 4 + days + 6);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 7, 4 + days + 7);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 8, 4 + days + 8);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 9, 4 + days + 9);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 10, 4 + days + 10);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 11, 4 + days + 11);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 12, 4 + days + 12);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 13, 4 + days + 13);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 14, 4 + days + 14);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 15, 4 + days + 15);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 16, 4 + days + 16);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 17, 4 + days + 17);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 18, 4 + days + 18);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 19, 4 + days + 19);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 20, 4 + days + 20);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);


            region = new CellRangeAddress(1, 2, 4 + days + 21, 4 + days + 21);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 22, 4 + days + 22);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);


            region = new CellRangeAddress(1, 2, 4 + days + 23, 4 + days + 23);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            region = new CellRangeAddress(1, 2, 4 + days + 24, 4 + days + 24);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);


            region = new CellRangeAddress(0, 0, 0, 4 + days);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);


            for (int a = 0; a < mkList.size(); a++) {
                oh = mkList.get(a);
                af = personServ.getAFByEmpNo(oh.getEmpNo());
                is = personServ.getISByEmpNo(oh.getEmpNo());
                dayJIList = getDayJIListByMKList28(oh);

                row = hssfSheet.createRow(beginRow++);
                row.setHeight((short) 400);
                cell = row.createCell(0);
                cell.setCellValue(a + 1);
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(1);
                cell.setCellValue(oh.getName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(oh.getDeptName());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(3);
                cell.setCellValue(oh.getEmpNo());
                cell.setCellStyle(cellStyleA);


                cell = row.createCell(4);
                cell.setCellValue("上午");
                cell.setCellStyle(cellStyleA);

                row2 = hssfSheet.createRow(beginRow++);
                row2.setHeight((short) 400);
                cell = row2.createCell(4);
                cell.setCellValue("下午");
                cell.setCellStyle(cellStyleA);

                row3 = hssfSheet.createRow(beginRow++);
                row3.setHeight((short) 400);
                cell = row3.createCell(4);
                cell.setCellValue("加班");
                cell.setCellStyle(cellStyleA);

                init28(mkList, hssfSheet, yearMonth, wd, fd);

                init29(mkList, hssfSheet, yearMonth, wd, fd);


                if (30 <= days) {
                    if (oh.getEmpNo().contains("CS")) {
                        inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                    } else {
                        inComStr = personServ.getLinShiInComDateByName(oh.getName());
                    }
                    isWeekEnd = DateUtil.checkIsWeekEnd(wd, 30 + "");
                    ymdStr = yearMonth + "-30";
                    week = DateUtil.getWeek(ymdStr);
                    String commentstr = null;
                    if (oh.getEmpNo().contains("CS")) {
                        commentstr = personServ.getTimeStrByDateStrAndEmpNo(oh.getEmpNo(), ymdStr);
                    } else {
                        commentstr = personServ.getTimeStrByDateStrAndNameLinShiGong(oh.getEmpNo(), ymdStr);
                    }

                    if (oh != null && oh.getDay30AM() != null) {
                        if (oh.getDay30AM() == 1) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 2) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 4) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay30AM() == 6) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay30AM() == 11) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay30AM() == 12) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 13) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 15) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay30AM() == 18) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 7) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30AM() == 8) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30AM() == 17) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay30AM() == 67) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 77) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 61) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 16) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue(oh.getDay30AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 19) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue(oh.getDay30AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30AM() == 20) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("婚");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 21) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("丧");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 22) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 23) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("陪");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 108) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30AM() == 107) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay30AM() == 106) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 301) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 302) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 312) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 313) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 318) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 367) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 377) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 361) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 316) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 319) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle4BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PY);
                                } else {
                                    cell.setCellStyle(cellStyle4Y);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 3108) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 3107) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle6BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6PY);
                                } else {
                                    cell.setCellStyle(cellStyle6Y);
                                }
                            }

                        } else if (dayJI.getDayJiAM() == 3106) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle4BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PY);
                                } else {
                                    cell.setCellStyle(cellStyle4Y);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 325) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            cell.setCellStyle(cellStyle4BOPY);
                        } else if (dayJI.getDayJiAM() == 601) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 602) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 612) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 613) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 618) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 667) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 677) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 661) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 616) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 619) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle4BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PYG);
                                } else {
                                    cell.setCellStyle(cellStyle4YG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 6108) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 6107) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle6BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6PYG);
                                } else {
                                    cell.setCellStyle(cellStyle6YG);
                                }
                            }

                        } else if (dayJI.getDayJiAM() == 6106) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle4BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PYG);
                                } else {
                                    cell.setCellStyle(cellStyle4YG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 625) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            cell.setCellStyle(cellStyle4BOPYG);
                        } else {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }

                        if (commentstr != null && commentstr.trim().length() > 0) {
                            HSSFPatriarch p = hssfSheet.createDrawingPatriarch();
                            comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                            comment.setString(new HSSFRichTextString(commentstr));
                            comment.setAuthor("程序员");
                            cell.setCellComment(comment);
                        }

                    } else {
                        cell = row.createCell(5 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                    if (oh != null && oh.getDay30PM() != null) {
                        if (oh.getDay30PM() == 1) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 2) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 4) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay30AM() == 6) {
                            cell = row.createCell(5 + 29);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay30PM() == 11) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay30PM() == 12) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 13) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 15) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("假");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay30PM() == 18) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 7) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30PM() == 8) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30PM() == 17) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay30PM() == 67) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 77) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 61) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 16) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue(oh.getDay30PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 19) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue(oh.getDay30PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay30PM() == 20) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 21) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("丧");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 22) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 23) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 108) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay30PM() == 107) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay30PM() == 106) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 601) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 602) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 612) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 613) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 618) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 667) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 677) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 661) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 616) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 619) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle4BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PYG);
                                } else {
                                    cell.setCellStyle(cellStyle4YG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 6108) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 6107) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle6BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6PYG);
                                } else {
                                    cell.setCellStyle(cellStyle6YG);
                                }
                            }

                        } else if (dayJI.getDayJiPM() == 6106) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle4BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PYG);
                                } else {
                                    cell.setCellStyle(cellStyle4YG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 625) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            cell.setCellStyle(cellStyle4BOPYG);
                        } else if (dayJI.getDayJiPM() == 301) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 302) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 312) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 313) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 318) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 367) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 377) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 361) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 316) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 319) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle4BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PY);
                                } else {
                                    cell.setCellStyle(cellStyle4Y);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 3108) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 3107) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle6BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6PY);
                                } else {
                                    cell.setCellStyle(cellStyle6Y);
                                }
                            }

                        } else if (dayJI.getDayJiPM() == 3106) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle4BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PY);
                                } else {
                                    cell.setCellStyle(cellStyle4Y);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 325) {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            cell.setCellStyle(cellStyle4BOPY);
                        } else {
                            cell = row2.createCell(5 + 29);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    } else {
                        cell = row2.createCell(5 + 29);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }

                    if (oh != null && oh.getDay30ExHours() != null) {
                        cell = row3.createCell(5 + 29);
                        cell.setCellValue(oh.getDay30ExHours() == 0.0 ? "" : oh.getDay30ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }

                    } else {
                        cell = row3.createCell(5 + 29);
                        cell.setCellValue(oh.getDay30ExHours() == null ? "" : oh.getDay30ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }
                    }
                }

                if (31 <= days) {
                    if (oh.getEmpNo().contains("CS")) {
                        inComStr = StringUtil.StringToDateStr(oh.getEmpNo());
                    } else {
                        inComStr = personServ.getLinShiInComDateByName(oh.getName());
                    }
                    isWeekEnd = DateUtil.checkIsWeekEnd(wd, 31 + "");
                    ymdStr = yearMonth + "-31";
                    week = DateUtil.getWeek(ymdStr);
                    String commentstr = null;
                    if (oh.getEmpNo().contains("CS")) {
                        commentstr = personServ.getTimeStrByDateStrAndEmpNo(oh.getEmpNo(), ymdStr);
                    } else {
                        commentstr = personServ.getTimeStrByDateStrAndNameLinShiGong(oh.getEmpNo(), ymdStr);
                    }
                    if (oh != null && oh.getDay31AM() != null) {
                        if (oh.getDay31AM() == 1) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 2) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 4) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay31AM() == 6) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay31AM() == 11) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay31AM() == 12) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 13) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 15) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("休");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay31AM() == 18) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 7) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31AM() == 8) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31AM() == 17) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay31AM() == 67) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 77) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 61) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 16) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue(oh.getDay31AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 19) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue(oh.getDay31AMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31AM() == 20) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("婚");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 21) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("丧");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 22) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 23) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("陪");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 108) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31AM() == 107) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay31AM() == 106) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 301) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 302) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 312) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 313) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 318) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 367) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 377) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 361) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 316) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 319) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle4BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PY);
                                } else {
                                    cell.setCellStyle(cellStyle4Y);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 3108) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 3107) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle6BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6PY);
                                } else {
                                    cell.setCellStyle(cellStyle6Y);
                                }
                            }

                        } else if (dayJI.getDayJiAM() == 3106) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle4BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PY);
                                } else {
                                    cell.setCellStyle(cellStyle4Y);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 325) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            cell.setCellStyle(cellStyle4BOPY);
                        } else if (dayJI.getDayJiAM() == 601) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 602) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 612) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 613) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("夜");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 618) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 667) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 677) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 661) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 616) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 619) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue(dayJI.getDayJiAMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle4BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PYG);
                                } else {
                                    cell.setCellStyle(cellStyle4YG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 6108) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 6107) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle6BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6PYG);
                                } else {
                                    cell.setCellStyle(cellStyle6YG);
                                }
                            }

                        } else if (dayJI.getDayJiAM() == 6106) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle4BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PYG);
                                } else {
                                    cell.setCellStyle(cellStyle4YG);
                                }
                            }
                        } else if (dayJI.getDayJiAM() == 625) {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            cell.setCellStyle(cellStyle4BOPYG);
                        } else {
                            cell = row.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                        if (commentstr != null && commentstr.trim().length() > 0) {
                            HSSFPatriarch p = hssfSheet.createDrawingPatriarch();
                            comment = p.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                            comment.setString(new HSSFRichTextString(commentstr));
                            comment.setAuthor("程序员");
                            cell.setCellComment(comment);
                        }
                    } else {
                        cell = row.createCell(5 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }

                    if (oh != null && oh.getDay31PM() != null) {
                        if (oh.getDay31PM() == 1) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 2) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 4) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("假");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay31PM() == 6) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("△");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay31PM() == 11) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("▲");
                            cell.setCellStyle(cellStyleBG);
//                            if (week == 6 || week == 7 || isWeekEnd) {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleBOP);
//                                } else {
//                                    cell.setCellStyle(cellStyleBO);
//                                }
//                            } else {
//                                if (inComStr.equals(ymdStr)) {
//                                    cell.setCellStyle(cellStyleAP);
//                                } else {
//                                    cell.setCellStyle(cellStyleA);
//                                }
//                            }
                        } else if (oh.getDay31PM() == 12) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 13) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 15) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("假");
                            cell.setCellStyle(cellStyleBR);
                        } else if (oh.getDay31PM() == 18) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 7) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }

                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31PM() == 8) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("✖");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31PM() == 17) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3BOP);
                                } else {
                                    cell.setCellStyle(cellStyle3BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle3P);
                                } else {
                                    cell.setCellStyle(cellStyle3);
                                }
                            }
                        } else if (oh.getDay31PM() == 67) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 77) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 61) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 16) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue(oh.getDay31PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 19) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue(oh.getDay31PMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (oh.getDay31PM() == 20) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 21) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 22) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("假");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 23) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("产");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 108) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOP);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAP);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        } else if (oh.getDay31PM() == 107) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOP);
                                } else {
                                    cell.setCellStyle(cellStyle6BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6P);
                                } else {
                                    cell.setCellStyle(cellStyle6);
                                }
                            }

                        } else if (oh.getDay31PM() == 106) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 601) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 602) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 612) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 613) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 618) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 667) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 677) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 661) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 616) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 619) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle4BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PYG);
                                } else {
                                    cell.setCellStyle(cellStyle4YG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 6108) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPYG);
                                } else {
                                    cell.setCellStyle(cellStyleAYG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 6107) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle6BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6PYG);
                                } else {
                                    cell.setCellStyle(cellStyle6YG);
                                }
                            }

                        } else if (dayJI.getDayJiPM() == 6106) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPYG);
                                } else {
                                    cell.setCellStyle(cellStyle4BOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PYG);
                                } else {
                                    cell.setCellStyle(cellStyle4YG);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 625) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            cell.setCellStyle(cellStyle4BOPYG);
                        } else if (dayJI.getDayJiPM() == 301) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("√");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 302) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 312) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("●");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 313) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("班");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPYG);
                                } else {
                                    cell.setCellStyle(cellStyleBOYG);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 318) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 367) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 377) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 361) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 316) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 319) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue(dayJI.getDayJiPMRemark());
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle4BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PY);
                                } else {
                                    cell.setCellStyle(cellStyle4Y);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 3108) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleBOPY);
                                } else {
                                    cell.setCellStyle(cellStyleBOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyleAPY);
                                } else {
                                    cell.setCellStyle(cellStyleAY);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 3107) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle6BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle6PY);
                                } else {
                                    cell.setCellStyle(cellStyle6Y);
                                }
                            }

                        } else if (dayJI.getDayJiPM() == 3106) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("О");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOPY);
                                } else {
                                    cell.setCellStyle(cellStyle4BOY);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4PY);
                                } else {
                                    cell.setCellStyle(cellStyle4Y);
                                }
                            }
                        } else if (dayJI.getDayJiPM() == 325) {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            cell.setCellStyle(cellStyle4BOPY);
                        } else {
                            cell = row2.createCell(5 + 30);
                            cell.setCellValue("");
                            if (week == 6 || week == 7 || isWeekEnd) {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4BOP);
                                } else {
                                    cell.setCellStyle(cellStyle4BO);
                                }
                            } else {
                                if (inComStr.equals(ymdStr)) {
                                    cell.setCellStyle(cellStyle4P);
                                } else {
                                    cell.setCellStyle(cellStyle4);
                                }
                            }
                        }
                    } else {
                        cell = row2.createCell(5 + 30);
                        cell.setCellValue("");
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4BOP);
                            } else {
                                cell.setCellStyle(cellStyle4BO);
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                cell.setCellStyle(cellStyle4P);
                            } else {
                                cell.setCellStyle(cellStyle4);
                            }
                        }
                    }
                    if (oh != null && oh.getDay31ExHours() != null) {
                        cell = row3.createCell(5 + 30);
                        cell.setCellValue(oh.getDay31ExHours() == 0.0 ? "" : oh.getDay31ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }

                    } else {
                        cell = row3.createCell(5 + 30);
                        cell.setCellValue(oh.getDay31ExHours() == null ? "" : oh.getDay31ExHours().toString());
                        if (week == 6 || week == 7 || isWeekEnd) {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBOP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleBO);
                                }
                            }
                        } else {
                            if (inComStr.equals(ymdStr)) {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleAP);
                                }
                            } else {
                                if (isFaDing) {
                                    cell.setCellStyle(cellStyleBR);
                                } else {
                                    cell.setCellStyle(cellStyleA);
                                }
                            }
                        }
                    }


                }


                cell = row.createCell(4 + days + 1);
                cell.setCellValue(oh.getZhengbanHours());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 2);
                cell.setCellValue(oh.getUsualExtHours());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 3);
                cell.setCellValue(oh.getWorkendHours());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 4);
                cell.setCellValue((oh.getChinaPaidLeave() == 0.0 ? "" : oh.getChinaPaidLeave()) + "");
                cell.setCellStyle(cellStyleRR);

                cell = row.createCell(4 + days + 5);
                cell.setCellValue((oh.getOtherPaidLeave() == 0.0 ? "" : oh.getOtherPaidLeave()) + "");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 6);
                cell.setCellValue((oh.getLeaveOfAbsense() == 0.0 ? "" : oh.getLeaveOfAbsense()) + "");
                cell.setCellStyle(cellStylePP);

                cell = row.createCell(4 + days + 7);
                cell.setCellValue((oh.getSickLeave() == 0.0 ? "" : oh.getSickLeave()) + "");
                cell.setCellStyle(cellStyleBB);


                cell = row.createCell(4 + days + 8);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 9);
                cell.setCellValue(((oh.getFullWorkReword() == null || oh.getFullWorkReword() == 0.0) ? "0" : oh.getFullWorkReword()) + "");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 10);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 11);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);


                cell = row.createCell(4 + days + 12);
                cell.setCellValue(((is == null || is.getEndowInsur() == null || is.getEndowInsur() == 0) ? "" : is.getEndowInsur()) + "");
                cell.setCellStyle(cellStyleABC);

                cell = row.createCell(4 + days + 13);
                cell.setCellValue(((is == null || is.getMedicalInsur() == null || is.getMedicalInsur() == 0) ? "" : is.getMedicalInsur()).toString());
                cell.setCellStyle(cellStyleABC);

                cell = row.createCell(4 + days + 14);
                cell.setCellValue(((is == null || is.getUnWorkInsur() == null || is.getUnWorkInsur() == 0) ? "" : is.getUnWorkInsur()).toString());
                cell.setCellStyle(cellStyleABC);

                cell = row.createCell(4 + days + 15);
                cell.setCellValue(((af == null || af.getAccuFundFee() == null || af.getAccuFundFee() == 0) ? "" : af.getAccuFundFee()).toString());
                cell.setCellStyle(cellStyleABC);


                cell = row2.createCell(4 + days + 8);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(4 + days + 9);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(4 + days + 10);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(4 + days + 11);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(4 + days + 12);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(4 + days + 13);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(4 + days + 14);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(4 + days + 15);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);


                cell = row3.createCell(4 + days + 8);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row3.createCell(4 + days + 9);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row3.createCell(4 + days + 10);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row3.createCell(4 + days + 11);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row3.createCell(4 + days + 12);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row3.createCell(4 + days + 13);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row3.createCell(4 + days + 14);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row3.createCell(4 + days + 15);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);


                cell = row.createCell(4 + days + 16);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);


                cell = row.createCell(4 + days + 17);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(4 + days + 17);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row3.createCell(4 + days + 17);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 18);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 19);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 20);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 21);
                cell.setCellValue(oh.getCheckHours());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 22);
                cell.setCellValue(oh.getCheckHours() - oh.getZhengbanHours() - oh.getLeaveOfAbsense() - oh.getSickLeave());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 23);
                cell.setCellValue(oh.getLateminites());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4 + days + 24);
                cell.setCellValue(oh.getLatetimes());
                cell.setCellStyle(cellStyle);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 0, 0);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);


                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 1, 1);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 2, 2);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);


                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 3, 3);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 1, 4 + days + 1);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 2, 4 + days + 2);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 3, 4 + days + 3);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 4, 4 + days + 4);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 5, 4 + days + 5);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 6, 4 + days + 6);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 7, 4 + days + 7);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 8, 4 + days + 8);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 9, 4 + days + 9);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 10, 4 + days + 10);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 11, 4 + days + 11);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 16, 4 + days + 16);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 18, 4 + days + 18);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 19, 4 + days + 19);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 20, 4 + days + 20);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 21, 4 + days + 21);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 22, 4 + days + 22);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 23, 4 + days + 23);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);


                region = new CellRangeAddress(beginRow - 3, beginRow - 1, 4 + days + 24, 4 + days + 24);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                dayJIList.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createFourSheet(List<MonthKQInfo> mkList, HSSFSheet hssfSheet2, String yearMonth, String wd, String fd) {

        row = hssfSheet2.createRow(0);
        row.setHeight((short) 600);
        cell = row.createCell(0);
        cell.setCellValue("临时工" + yearMonth + "月考勤");
        cell.setCellStyle(cellStyle2);

        row = hssfSheet2.createRow(1);
        row.setHeight((short) 800);
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(cellStyleGT);

        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("部门");
        cell.setCellStyle(cellStyleGT);

        cell = row.createCell(3);
        cell.setCellValue("正班出勤工时");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(4);
        cell.setCellValue("平时加班(H)");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(5);
        cell.setCellValue("周末加班(H)");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(6);
        cell.setCellValue("合计上班工时(H)");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(7);
        cell.setCellValue("伙食费");
        cell.setCellStyle(cellStyleBB);

        cell = row.createCell(8);
        cell.setCellValue("房租/水电费");
        cell.setCellStyle(cellStyleBB);

        cell = row.createCell(9);
        cell.setCellValue("扣代付养老险");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(10);
        cell.setCellValue("扣代付医疗险");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(11);
        cell.setCellValue("扣代付失业险");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(12);
        cell.setCellValue("扣代付公积金");
        cell.setCellStyle(cellStyleRR);

        cell = row.createCell(13);
        cell.setCellValue("工作失误");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(14);
        cell.setCellValue("绩效分");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(15);
        cell.setCellValue("备注");
        cell.setCellStyle(cellStyle);

        cell = row.createCell(16);
        cell.setCellValue("请确定无误后签字");
        cell.setCellStyle(cellStyle);

        region = new CellRangeAddress(0, 0, 0, 16);
        hssfSheet2.addMergedRegion(region);

        RegionUtil.setBorderBottom(1, region, hssfSheet2, wb);
        RegionUtil.setBorderTop(1, region, hssfSheet2, wb);
        RegionUtil.setBorderLeft(1, region, hssfSheet2, wb);
        RegionUtil.setBorderRight(1, region, hssfSheet2, wb);


        hssfSheet2.setColumnWidth(0, 6 * 256);
        hssfSheet2.setColumnWidth(1, 8 * 256);
        hssfSheet2.setColumnWidth(2, 10 * 256);
        hssfSheet2.setColumnWidth(3, 7 * 256);
        hssfSheet2.setColumnWidth(4, 7 * 256);
        hssfSheet2.setColumnWidth(5, 7 * 256);
        hssfSheet2.setColumnWidth(6, 7 * 256);
        hssfSheet2.setColumnWidth(7, 7 * 256);
        hssfSheet2.setColumnWidth(8, 7 * 256);
        hssfSheet2.setColumnWidth(9, 7 * 256);
        hssfSheet2.setColumnWidth(10, 7 * 256);
        hssfSheet2.setColumnWidth(11, 7 * 256);
        hssfSheet2.setColumnWidth(12, 7 * 256);
        hssfSheet2.setColumnWidth(13, 7 * 256);
        hssfSheet2.setColumnWidth(14, 7 * 256);
        hssfSheet2.setColumnWidth(15, 10 * 256);
        hssfSheet2.setColumnWidth(16, 11 * 256);

        try {
            for (int i = 0; i < mkList.size(); i++) {
                oh = mkList.get(i);
                af = personServ.getAFByName(oh.getName());
                is = personServ.getISByName(oh.getName());
                row = hssfSheet2.createRow(i + 2);
                row.setHeight((short) 400);
                cell = row.createCell(0);
                cell.setCellValue(i + 1);
                cell.setCellStyle(cellStyleGT);

                cell = row.createCell(1);
                cell.setCellValue(oh.getName() == null ? "" : oh.getName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(oh.getDeptName() == null ? "" : oh.getDeptName());
                cell.setCellStyle(cellStyleGT);

                cell = row.createCell(3);
                cell.setCellValue(oh.getZhengbanHours() == null ? 0 : oh.getZhengbanHours());
                cell.setCellStyle(cellStyleRR);

                cell = row.createCell(4);
                cell.setCellValue(oh.getUsualExtHours() == null ? 0 : oh.getUsualExtHours());
                cell.setCellStyle(cellStyleRR);

                cell = row.createCell(5);
                cell.setCellValue(oh.getWorkendHours() == null ? 0 : oh.getWorkendHours());
                cell.setCellStyle(cellStyleRR);

                cell = row.createCell(6);
                cell.setCellValue((oh.getWorkendHours() == null ? 0 : oh.getWorkendHours()) + (oh.getUsualExtHours() == null ? 0 : oh.getUsualExtHours()) + (oh.getZhengbanHours() == null ? 0 : oh.getZhengbanHours()));
                cell.setCellStyle(cellStyleRR);

                cell = row.createCell(7);
                cell.setCellValue("");
                cell.setCellStyle(cellStyleBBThin);

                cell = row.createCell(8);
                cell.setCellValue("");
                cell.setCellStyle(cellStyleBBThin);

                cell = row.createCell(9);
                cell.setCellValue(((is == null || is.getEndowInsur() == null || is.getEndowInsur() == 0) ? "" : is.getEndowInsur()) + "");
                cell.setCellStyle(cellStyleRRThin);

                cell = row.createCell(10);
                cell.setCellValue(((is == null || is.getMedicalInsur() == null || is.getMedicalInsur() == 0) ? "" : is.getMedicalInsur()) + "");
                cell.setCellStyle(cellStyleRRThin);

                cell = row.createCell(11);
                cell.setCellValue(((is == null || is.getUnWorkInsur() == null || is.getUnWorkInsur() == 0) ? "" : is.getUnWorkInsur()) + "");
                cell.setCellStyle(cellStyleRRThin);

                cell = row.createCell(12);
                cell.setCellValue(((af == null || af.getAccuFundFee() == null || af.getAccuFundFee() == 0) ? "" : af.getAccuFundFee()) + "");
                cell.setCellStyle(cellStyleRRThin);

                cell = row.createCell(13);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(14);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle3);

                cell = row.createCell(15);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle3);

                cell = row.createCell(16);
                cell.setCellValue("");
                cell.setCellStyle(cellStyle3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
