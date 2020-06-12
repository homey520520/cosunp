package com.cosun.cosunp.entity;

import com.cosun.cosunp.tool.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
import org.apache.poi.ss.util.RegionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:homey Wong
 * @Date: 2020/5/18 0018 上午 9:22
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DesignExcel {

    public String writeDesigndataTOExcel(DesignMaterialHead head, String finalDirPath, String excelName) throws Exception {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
        HSSFWorkbook wb = null;
        CellRangeAddress region = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        DesignMaterialHeadProduct product = null;
        DesignMaterialHeadProductItem item = null;
        List<DesignMaterialHeadProductItem> itemList = new ArrayList<DesignMaterialHeadProductItem>();


        try {
            File file = new File(finalDirPath + excelName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            File targetFile = new File(finalDirPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            fis = new FileInputStream(finalDirPath + excelName);
            wb = new HSSFWorkbook();


            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 10.5);
            font.setColor(HSSFColor.BLACK.index);
            font.setBold(true);

            Font fontb = wb.createFont();
            fontb.setFontHeightInPoints((short) 10.5);
            fontb.setColor(HSSFColor.BLACK.index);

            Font fonta = wb.createFont();
            fonta.setFontHeightInPoints((short) 16);
            fonta.setColor(HSSFColor.BLACK.index);
            fonta.setBold(true);

            CellStyle cellStyle = wb.createCellStyle();
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


            CellStyle cellStyleb = wb.createCellStyle();
            cellStyleb.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyleb.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyleb.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyleb.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleb.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyleb.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleb.setBorderRight(CellStyle.BORDER_THIN);
            cellStyleb.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleb.setBorderTop(CellStyle.BORDER_THIN);
            cellStyleb.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleb.setFont(fontb);
            cellStyleb.setWrapText(true);


            CellStyle cellStylea = wb.createCellStyle();
            cellStylea.setAlignment(CellStyle.ALIGN_CENTER);
            cellStylea.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStylea.setBorderBottom(CellStyle.BORDER_THIN);
            cellStylea.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStylea.setBorderLeft(CellStyle.BORDER_THIN);
            cellStylea.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStylea.setBorderRight(CellStyle.BORDER_THIN);
            cellStylea.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStylea.setBorderTop(CellStyle.BORDER_THIN);
            cellStylea.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStylea.setFont(fonta);
            cellStylea.setWrapText(true);


            CellStyle cellStylec = wb.createCellStyle();
            cellStylec.setAlignment(CellStyle.ALIGN_LEFT);
            cellStylec.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStylec.setBorderBottom(CellStyle.BORDER_THIN);
            cellStylec.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStylec.setBorderLeft(CellStyle.BORDER_THIN);
            cellStylec.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStylec.setBorderRight(CellStyle.BORDER_THIN);
            cellStylec.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStylec.setBorderTop(CellStyle.BORDER_THIN);
            cellStylec.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStylec.setFont(font);
            cellStylec.setWrapText(true);

            HSSFSheet hssfSheet = wb.createSheet("明细");

            hssfSheet.setColumnWidth(0, 5 * 256);
            hssfSheet.setColumnWidth(1, 14 * 256);
            hssfSheet.setColumnWidth(2, 8 * 256);
            hssfSheet.setColumnWidth(3, 8 * 256);
            hssfSheet.setColumnWidth(4, 4 * 256);
            hssfSheet.setColumnWidth(5, 12 * 256);
            hssfSheet.setColumnWidth(6, 12 * 256);
            hssfSheet.setColumnWidth(7, 6 * 256);
            hssfSheet.setColumnWidth(8, 8 * 256);
            hssfSheet.setColumnWidth(9, 9 * 256);
            hssfSheet.setColumnWidth(10, 6 * 256);
            hssfSheet.setColumnWidth(11, 10 * 256);

            HSSFRow row = hssfSheet.createRow(0);
            row.setHeight((short) 600);

            Cell cell = row.createCell(0);
            cell.setCellValue("客户单号");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(head.getCustomerNo());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellValue("接单时间");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(7);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(8);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(9);
            cell.setCellValue(head.getGetOrderDateStr());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(10);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(11);
            cell.setCellStyle(cellStyle);

            region = new CellRangeAddress(0, 0, 0, 1);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(0, 0, 2, 5);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(0, 0, 6, 8);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(0, 0, 9, 11);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            row = hssfSheet.createRow(1);
            row.setHeight((short) 600);
            cell = row.createCell(0);
            cell.setCellValue("客户国家/地区");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(1);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellValue(head.getOrderArea());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue("业务员");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue(head.getSalorEmpStr());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellValue("交货时间");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(7);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(8);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(9);
            cell.setCellValue(head.getDeliveryOrderDateStr() == null ? "" : head.getDeliveryOrderDateStr());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(10);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(11);
            cell.setCellStyle(cellStyle);


            region = new CellRangeAddress(1, 1, 0, 1);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 1, 4, 5);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 1, 6, 8);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(1, 1, 9, 11);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            int rowLen = 2;
            for (int i = 0; i < head.getProductList().size(); i++) {
                itemList.clear();
                product = head.getProductList().get(i);
                for (int j = 0; j < head.getProductItemList().size(); j++) {
                    if (head.getProductItemList().get(j).getHead_product_id().equals(Integer.valueOf(product.getId())) ) {
                        itemList.add(head.getProductItemList().get(j));
                    }
                }

                itemList.add(new DesignMaterialHeadProductItem());
                itemList.add(new DesignMaterialHeadProductItem());

                row = hssfSheet.createRow(i + rowLen);
                row.setHeight((short) 600);
                cell = row.createCell(0);
                cell.setCellValue("(" + product.getProductName() + ")材料明细表");
                cell.setCellStyle(cellStylea);


                region = new CellRangeAddress(i + rowLen, i + rowLen, 0, 11);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                row = hssfSheet.createRow(1 + i + rowLen);
                row.setHeight((short) 600);

                cell = row.createCell(0);
                cell.setCellValue("品号");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellValue(product.getProductNo());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue("需求数量");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellValue(product.getNeedNum());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(6);
                cell.setCellValue("图纸编号");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(8);
                cell.setCellValue(product.getDrawingNo());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(9);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(10);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(11);
                cell.setCellStyle(cellStyle);


                region = new CellRangeAddress(i + 1 + rowLen, i + 1 + rowLen, 1, 2);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 1 + rowLen, i + 1 + rowLen, 3, 4);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 1 + rowLen, i + 1 + rowLen, 6, 7);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 1 + rowLen, i + 1 + rowLen, 8, 11);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                row = hssfSheet.createRow(2 + i + rowLen);
                row.setHeight((short) 600);

                cell = row.createCell(0);
                cell.setCellValue("序号");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellValue("材料品号");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue("材料品名");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellValue("规格");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(6);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(7);
                cell.setCellValue("单位");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(8);
                cell.setCellValue("数量");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(9);
                cell.setCellValue("使用车间");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(10);
                cell.setCellValue("库存");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(11);
                cell.setCellValue("其它备注");
                cell.setCellStyle(cellStyle);


                row = hssfSheet.createRow(3 + i + rowLen);
                row.setHeight((short) 600);

                cell = row.createCell(0);
                cell.setCellValue("工艺路线");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(product.getProductRoute());
                cell.setCellStyle(cellStyle);

                row = hssfSheet.createRow(4 + i + rowLen);
                row.setHeight((short) 600);
                cell = row.createCell(0);
                cell.setCellValue("产品分类编码");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(product.getProductTypeNo());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue("产品分类名称");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(6);
                cell.setCellValue(product.getProductTypeName());
                cell.setCellStyle(cellStyle);

                cell = row.createCell(7);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(8);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(9);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(10);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(11);
                cell.setCellStyle(cellStyle);

                row = hssfSheet.createRow(5 + i + rowLen);
                row.setHeight((short) 600);

                cell = row.createCell(0);
                cell.setCellValue("产品工艺路线");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(1);
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue(product.getProductTypeRoute());
                cell.setCellStyle(cellStyle);


                region = new CellRangeAddress(i + 4 + rowLen, i + 4 + rowLen, 0, 1);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 4 + rowLen, i + 4 + rowLen, 2, 3);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);
                region = new CellRangeAddress(i + 4 + rowLen, i + 4 + rowLen, 4, 5);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 4 + rowLen, i + 4 + rowLen, 6, 11);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 3 + rowLen, i + 3 + rowLen, 0, 1);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 3 + rowLen, i + 3 + rowLen, 2, 11);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 5 + rowLen, i + 5 + rowLen, 0, 1);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 5 + rowLen, i + 5 + rowLen, 2, 11);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 2 + rowLen, i + 2 + rowLen, 2, 4);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);

                region = new CellRangeAddress(i + 2 + rowLen, i + 2 + rowLen, 5, 6);
                RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                hssfSheet.addMergedRegion(region);


                for (int a = 0; a < itemList.size(); a++) {
                    item = itemList.get(a);
                    row = hssfSheet.createRow(6 + i + a + rowLen);
                    cell = row.createCell(0);
                    cell.setCellValue(a + 1);
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(1);
                    cell.setCellValue(item.getMateiralNo() == null ? "" : item.getMateiralNo());
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(2);
                    cell.setCellValue(item.getMaterialName() == null ? "" : item.getMaterialName());
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(3);
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(4);
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(5);
                    cell.setCellValue(item.getMaterialSpeci() == null ? "" : item.getMaterialSpeci());
                    cell.setCellStyle(cellStyleb);


//                    int rwsTemp = (item.getMaterialSpeci() == null ? "" : item.getMaterialSpeci()).toString().split("\n").length;
//                    row.setHeight((short)(rwsTemp * 600));

                    cell = row.createCell(6);
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(7);
                    cell.setCellValue(item.getUnit() == null ? "" : item.getUnit());
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(8);
                    cell.setCellValue(item.getNum() == null ? "" : item.getNum());
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(9);
                    cell.setCellValue(item.getUseDeptName() == null ? "" : item.getUseDeptName());
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(10);
                    cell.setCellValue(item.getMateiralStock() == null ? 0.0 : item.getMateiralStock());
                    cell.setCellStyle(cellStyleb);

                    cell = row.createCell(11);
                    cell.setCellValue(item.getRemark() == null ? "" : item.getRemark());
                    cell.setCellStyle(cellStyleb);

                    region = new CellRangeAddress(6 + i + a + rowLen, 6 + i + a + rowLen, 2, 4);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);

                    region = new CellRangeAddress(6 + i + a + rowLen, 6 + i + a + rowLen, 5, 6);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);
                    calcAndSetRowHeigt(row);
                    if (item.getMateiralNo() == null) {
                        row.setHeight((short) 600);
                    }
                }

                rowLen = rowLen + (itemList.size() + 5);
            }

            rowLen = 2 + head.getProductList().size() * 6 + head.getProductItemList().size() + 2 * head.getProductList().size();
            row = hssfSheet.createRow(rowLen);
            row.setHeight((short) 600);
            cell = row.createCell(0);
            cell.setCellValue("制表:" + (head.getOrderMakerStr() == null ? "" : head.getOrderMakerStr()));
            cell.setCellStyle(cellStylec);

            cell = row.createCell(1);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue("设计经理审核:");
            cell.setCellStyle(cellStylec);

            cell = row.createCell(5);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(7);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(8);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(9);
            cell.setCellValue("采购:");
            cell.setCellStyle(cellStyle);

            cell = row.createCell(10);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(11);
            cell.setCellStyle(cellStyle);


            region = new CellRangeAddress(rowLen, rowLen, 0, 3);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(rowLen, rowLen, 4, 8);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(rowLen, rowLen, 10, 11);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);


            row = hssfSheet.createRow(rowLen + 1);
            row.setHeight((short) 600);

            cell = row.createCell(0);
            cell.setCellValue("PMC:                            实际交期：                     业务确认:                                  ");
            cell.setCellStyle(cellStylec);

            cell = row.createCell(1);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(7);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(8);
            cell.setCellValue("批准:");
            cell.setCellStyle(cellStylec);

            cell = row.createCell(9);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(10);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(11);
            cell.setCellStyle(cellStyle);


            region = new CellRangeAddress(rowLen + 1, rowLen + 1, 0, 7);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);

            region = new CellRangeAddress(rowLen + 1, rowLen + 1, 8, 11);
            RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
            hssfSheet.addMergedRegion(region);


            HSSFPrintSetup ps = hssfSheet.getPrintSetup();

            ps.setLandscape(false);
            ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
            hssfSheet.setMargin(HSSFSheet.BottomMargin, (double) 0.3);
            hssfSheet.setMargin(HSSFSheet.LeftMargin, (double) 0.1);
            hssfSheet.setMargin(HSSFSheet.RightMargin, (double) 0.1);
            hssfSheet.setMargin(HSSFSheet.TopMargin, (double) 0.3);
            hssfSheet.setHorizontallyCenter(true);
            hssfSheet.setVerticallyCenter(false);

            fos = new FileOutputStream(finalDirPath + excelName);
            wb.write(fos);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
        }

        return excelName;

    }


    public static void calcAndSetRowHeigt(HSSFRow sourceRow) throws Exception {
        for (int cellIndex = sourceRow.getFirstCellNum(); cellIndex <= sourceRow.getPhysicalNumberOfCells(); cellIndex++) {
            //行高
            double maxHeight = sourceRow.getHeight();
            HSSFCell sourceCell = sourceRow.getCell(cellIndex);
            //单元格的内容
            String cellContent = getCellContentAsString(sourceCell);
            if (null == cellContent || "".equals(cellContent)) {
                continue;
            }
            //单元格的宽高及单元格信息
            Map<String, Object> cellInfoMap = getCellInfo(sourceCell);
            Integer cellWidth = (Integer) cellInfoMap.get("width");
            Integer cellHeight = (Integer) cellInfoMap.get("height");
            if (cellHeight > maxHeight) {
                maxHeight = cellHeight;
            }
            System.out.println("单元格的宽度 : " + cellWidth + "    单元格的高度 : " + maxHeight + ",    单元格的内容 : " + cellContent);
            HSSFCellStyle cellStyle = sourceCell.getCellStyle();
            HSSFFont font = cellStyle.getFont(sourceRow.getSheet().getWorkbook());
            //字体的高度
            short fontHeight = font.getFontHeight();

            //cell内容字符串总宽度
            double cellContentWidth = cellContent.getBytes().length * 2 * 256;

            //字符串需要的行数 不做四舍五入之类的操作
            double stringNeedsRows = (double) cellContentWidth / cellWidth;
            //小于一行补足一行
            if (stringNeedsRows < 1.0) {
                stringNeedsRows = 1.0;
            }

            //需要的高度 			(Math.floor(stringNeedsRows) - 1) * 40 为两行之间空白高度
            double stringNeedsHeight = (double) fontHeight * stringNeedsRows;
            //需要重设行高
            if (stringNeedsHeight > maxHeight) {
                maxHeight = stringNeedsHeight;
                //超过原行高三倍 则为5倍 实际应用中可做参数配置
                if (maxHeight / cellHeight > 5) {
                    maxHeight = 5 * cellHeight;
                }
                //最后取天花板防止高度不够
                maxHeight = Math.ceil(maxHeight);
                //重新设置行高 同时处理多行合并单元格的情况
                Boolean isPartOfRowsRegion = (Boolean) cellInfoMap.get("isPartOfRowsRegion");
                if (isPartOfRowsRegion) {
                    Integer firstRow = (Integer) cellInfoMap.get("firstRow");
                    Integer lastRow = (Integer) cellInfoMap.get("lastRow");
                    //平均每行需要增加的行高
                    double addHeight = (maxHeight - cellHeight) / (lastRow - firstRow + 1);
                    for (int i = firstRow; i <= lastRow; i++) {
                        double rowsRegionHeight = sourceRow.getSheet().getRow(i).getHeight() + addHeight;
                        if (rowsRegionHeight < 600) {
                            sourceRow.setHeight((short) 600);
                        } else {
                            sourceRow.getSheet().getRow(i).setHeight((short) 600);
                        }
                    }
                } else {
                    if (maxHeight < 600) {
                        sourceRow.setHeight((short) 600);
                    } else {
                        sourceRow.setHeight((short) maxHeight);
                    }
                }
            }
            System.out.println("字体高度 : " + fontHeight + ",    字符串宽度 : " + cellContentWidth + ",    字符串需要的行数 : " + stringNeedsRows + ",   需要的高度 : " + stringNeedsHeight + ",   现在的行高 : " + maxHeight);
            System.out.println();
        }
    }

    /**
     * 解析一个单元格得到数据
     *
     * @param cell
     * @return
     */
    private static String getCellContentAsString(HSSFCell cell) throws Exception {
        if (null == cell) {
            return "";
        }
        String result = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                String s = String.valueOf(cell.getNumericCellValue());
                if (s != null) {
                    if (s.endsWith(".0")) {
                        s = s.substring(0, s.length() - 2);
                    }
                }
                result = s;
                break;
            case Cell.CELL_TYPE_STRING:
                result = StringUtil.nulltoempty(String.valueOf(cell.getStringCellValue())).trim();
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 获取单元格及合并单元格的宽度
     *
     * @param cell
     * @return
     */
    private static Map<String, Object> getCellInfo(HSSFCell cell) {
        HSSFSheet sheet = cell.getSheet();
        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getColumnIndex();

        boolean isPartOfRegion = false;
        int firstColumn = 0;
        int lastColumn = 0;
        int firstRow = 0;
        int lastRow = 0;
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            firstColumn = ca.getFirstColumn();
            lastColumn = ca.getLastColumn();
            firstRow = ca.getFirstRow();
            lastRow = ca.getLastRow();
            if (rowIndex >= firstRow && rowIndex <= lastRow) {
                if (columnIndex >= firstColumn && columnIndex <= lastColumn) {
                    isPartOfRegion = true;
                    break;
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Integer width = 0;
        Integer height = 0;
        boolean isPartOfRowsRegion = false;
        if (isPartOfRegion) {
            for (int i = firstColumn; i <= lastColumn; i++) {
                width += sheet.getColumnWidth(i);
            }
            for (int i = firstRow; i <= lastRow; i++) {
                height += sheet.getRow(i).getHeight();
            }
            if (lastRow > firstRow) {
                isPartOfRowsRegion = true;
            }
        } else {
            width = sheet.getColumnWidth(columnIndex);
            height += cell.getRow().getHeight();
        }
        map.put("isPartOfRowsRegion", isPartOfRowsRegion);
        map.put("firstRow", firstRow);
        map.put("lastRow", lastRow);
        map.put("width", width);
        map.put("height", height);
        return map;
    }

}

