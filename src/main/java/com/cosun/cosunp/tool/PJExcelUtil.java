package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.*;
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
public class PJExcelUtil {

    XSSFWorkbook workbook = new XSSFWorkbook();
    HSSFWorkbook wb = new HSSFWorkbook();
    List<String> returnArray = new ArrayList<String>();
    FileOutputStream fos = null;
    FileInputStream fis = null;
    CellRangeAddress region = null;

    int beginRow = 2;

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
    ProjectHeadOrderItem oh = null;


    public List<String> writePJdataTOExcel(List<ProjectHeadOrderItem> mkList, String finalDirPath, String fileName) throws Exception {
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
        file.createNewFile();
        fis = new FileInputStream(finalDirPath + fileName);
        HSSFSheet hssfSheet = wb.createSheet("项目订单明细");
        hssfSheet.createFreezePane(7, 2, 7, 2);
        this.createOneSheet(mkList, hssfSheet);

        fos = new FileOutputStream(finalDirPath + fileName);
        wb.write(fos);

        returnArray.add(pathname);
        return returnArray;

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

    public void createOneSheet(List<ProjectHeadOrderItem> mkList, HSSFSheet hssfSheet) {
        beginRow = 2;
        try {
            row = hssfSheet.createRow(0);
            row2 = null;
            row3 = null;
            cell = row.createCell(0);
            cell.setCellValue("海 底 捞 项  目 进 度 表");
            cell.setCellStyle(cellStyle2);

            row = hssfSheet.createRow(1);
            row.setHeight((short) 400);
            cell = row.createCell(0);
            cell.setCellValue("序号");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellValue("省份");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue("客户名称");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue("订单编号");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue("产品名称");
            cell.setCellStyle(cellStyle);


            cell = row.createCell(5);
            cell.setCellValue("交店时间");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellValue("名称");
            cell.setCellStyle(cellStyle);

            hssfSheet.setColumnWidth(0, 6 * 256);
            hssfSheet.setColumnWidth(1, 10 * 256);
            hssfSheet.setColumnWidth(2, 10 * 256);
            hssfSheet.setColumnWidth(3, 12 * 256);
            hssfSheet.setColumnWidth(4, 12 * 256);
            hssfSheet.setColumnWidth(5, 12 * 256);
            hssfSheet.setColumnWidth(6, 10 * 256);


            cell = row.createCell(7);
            cell.setCellValue("接单时间");
            hssfSheet.setColumnWidth(7, 12 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(8);
            hssfSheet.setColumnWidth(8, 12 * 256);
            cell.setCellValue(" 勘察日期");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(9);
            hssfSheet.setColumnWidth(9, 12 * 256);
            cell.setCellValue("出图日期");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(10);
            cell.setCellValue("方案确认");
            hssfSheet.setColumnWidth(10, 12 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(11);
            cell.setCellValue("下单日期");
            hssfSheet.setColumnWidth(11, 12 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(12);
            cell.setCellValue("交货日期");
            hssfSheet.setColumnWidth(12, 12 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(13);
            cell.setCellValue("安装日期");
            hssfSheet.setColumnWidth(13, 12 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(14);
            cell.setCellValue(" 验收日期");
            hssfSheet.setColumnWidth(14, 12 * 256);
            cell.setCellStyle(cellStyle);


            cell = row.createCell(15);
            cell.setCellValue("进度说明");
            hssfSheet.setColumnWidth(15, 12 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(16);
            cell.setCellValue("总包 ");
            hssfSheet.setColumnWidth(16, 10 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(17);
            cell.setCellValue("合同金额");
            hssfSheet.setColumnWidth(17, 10 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(18);
            cell.setCellValue("到款金额");
            hssfSheet.setColumnWidth(18, 10 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(19);
            cell.setCellValue("未回款金额");
            hssfSheet.setColumnWidth(19, 10 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(20);
            cell.setCellValue(" 订单状态");
            hssfSheet.setColumnWidth(20, 10 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(21);
            cell.setCellValue("审核状态");
            hssfSheet.setColumnWidth(21, 10 * 256);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(22);
            cell.setCellValue("备注");
            hssfSheet.setColumnWidth(22, 10 * 256);
            cell.setCellStyle(cellStyle);



            region = new CellRangeAddress(0, 0, 0, 22);
            hssfSheet.addMergedRegion(region);

            RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
            RegionUtil.setBorderTop(1, region, hssfSheet, wb);
            RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
            RegionUtil.setBorderRight(1, region, hssfSheet, wb);

            for (int a = 0; a < mkList.size(); a++) {
                oh = mkList.get(a);

                row = hssfSheet.createRow(beginRow++);
                row.setHeight((short) 400);
                cell = row.createCell(0);
                cell.setCellValue(a + 1);
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(1);
                cell.setCellValue(oh.getProvinceStr());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(oh.getCustomer_Name());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(3);
                cell.setCellValue(oh.getOrderNo());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(4);
                cell.setCellValue(oh.getProduct_Name());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(5);
                cell.setCellValue(oh.getDelivery_DateStr());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(6);
                cell.setCellValue("计划交期");
                cell.setCellStyle(cellStyleA);

                row2 = hssfSheet.createRow(beginRow++);
                row2.setHeight((short) 400);
                cell = row2.createCell(6);
                cell.setCellValue("实际交期");
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(7);
                cell.setCellValue(oh.getGetOrder_Date_PlanStr());
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(7);
                cell.setCellValue(oh.getGetOrder_Date_AccuStr());
                cell.setCellStyle(cellStyleA);


                cell = row.createCell(8);
                cell.setCellValue(oh.getZhanCha_Date_PlanStr());
                cell.setCellStyle(cellStyle);


                cell = row2.createCell(8);
                cell.setCellValue(oh.getZhanCha_Date_AccuStr());
                cell.setCellStyle(cellStyleA);


                cell = row.createCell(9);
                cell.setCellValue(oh.getOutDraw_Date_PlanStr());
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(9);
                cell.setCellValue(oh.getOutDraw_Date_AccuStr());
                cell.setCellStyle(cellStyleA);


                cell = row.createCell(10);
                cell.setCellValue(oh.getProgram_confir_Date_PlanStr());
                cell.setCellStyle(cellStyle);


                cell = row2.createCell(10);
                cell.setCellValue(oh.getProgram_confir_Date_AccuStr());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(11);
                cell.setCellValue(oh.getGiveOrder_Date_PlanStr());
                cell.setCellStyle(cellStyle);


                cell = row2.createCell(11);
                cell.setCellValue(oh.getGiveOrder_Date_AccuStr());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(12);
                cell.setCellValue(oh.getDelivery_Goods_Date_PlanStr());
                cell.setCellStyle(cellStyle);


                cell = row2.createCell(12);
                cell.setCellValue(oh.getDelivery_Goods_Date_AccuStr());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(13);
                cell.setCellValue(oh.getInstall_Date_PlanStr());
                cell.setCellStyle(cellStyle);

                cell = row2.createCell(13);
                cell.setCellValue(oh.getInstall_Date_AccuStr());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(14);
                cell.setCellValue(oh.getYanShou_Date_PlanStr());
                cell.setCellStyle(cellStyle);


                cell = row2.createCell(14);
                cell.setCellValue(oh.getYanShou_Date_AccuStr());
                cell.setCellStyle(cellStyleA);

                cell = row.createCell(15);
                cell.setCellValue(oh.getJindu_remark());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(16);
                cell.setCellValue(oh.getTotalBao());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(17);
                cell.setCellValue(oh.getHetongMoney() == null ? 0.0 : oh.getHetongMoney());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(18);
                cell.setCellValue(oh.getHereMoney() == null ? 0.0 : oh.getHereMoney());
                cell.setCellStyle(cellStyle);


                cell = row.createCell(19);
                cell.setCellValue(oh.getWeiHuiMoney() == null ? 0.0 : oh.getWeiHuiMoney());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(20);
                cell.setCellValue(oh.getStatusStr());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(21);
                cell.setCellValue(oh.getCheckedStr());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(22);
                cell.setCellValue(oh.getRemark());
                cell.setCellStyle(cellStyle);

                region = new CellRangeAddress(beginRow - 2, beginRow-1, 0, 0);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);


                region = new CellRangeAddress(beginRow - 2, beginRow -1, 1, 1);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 2, 2);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);


                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 3, 3);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);


                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 4, 4);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 5, 5);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);




                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 7, 7);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 15, 15);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);


                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 16, 16);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 17, 17);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 18, 18);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 19, 19);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 20, 20);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 21, 21);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);

                region = new CellRangeAddress(beginRow - 2, beginRow - 1, 22, 22);
                hssfSheet.addMergedRegion(region);

                RegionUtil.setBorderBottom(1, region, hssfSheet, wb);
                RegionUtil.setBorderTop(1, region, hssfSheet, wb);
                RegionUtil.setBorderLeft(1, region, hssfSheet, wb);
                RegionUtil.setBorderRight(1, region, hssfSheet, wb);




            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
