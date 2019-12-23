package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.OrderMapper;
import com.cosun.cosunp.service.IOrderServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.StringUtil;
import com.cosun.cosunp.tool.WordToPDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/8/13 0013 上午 9:31
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements IOrderServ {

    private static Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @Autowired
    private OrderMapper orderMapper;

    public List<Employee> findAllSalor() throws Exception {
        return orderMapper.findAllSalor();
    }

    public List<OrderHead> getOrderItemByHeadId(Integer id) throws Exception {
        return orderMapper.getOrderItemByHeadId(id);
    }

    public OrderHead getOrderHeadByHeadId(Integer id) throws Exception {
        return orderMapper.getOrderHeadByHeadId(id);
    }

    public OrderHead getOrderHeadByOrderNo2(String orderNo) throws Exception {
        return orderMapper.getOrderHeadByOrderNo2(orderNo);
    }


    public void deleteAllOrderByHeadId(Integer headId) throws Exception {
        OrderHead orderHead = orderMapper.getOrderHeadByHeadId2(headId);
        FileUtil.delFolder(finalDirPath+"order/"+orderHead.getEngName()+"/"+orderHead.getOrderNo()+"/");
        orderMapper.deleAllOrderItemByHeadId(headId);
        orderMapper.deleteAllOrderHeadById(headId);

    }

    public List<OrderHead> findAllOrderHeadForPMC(OrderHead orderHead) throws Exception {
        return orderMapper.findAllOrderHeadForPMC(orderHead);
    }

    public OrderItem getOrderItemById(Integer itemId) throws Exception {
        return orderMapper.getOrderItemById(itemId);
    }

    public int deleteOrderItemByItemId(Integer id) throws Exception {
        List<OrderItem> ois = orderMapper.getAllOrderItemBy(id);
        if (ois.size() > 1) {
            orderMapper.updateOrderHeadTotalNum(ois.get(0).getOrderHeadId(), ois.size()-1);
            orderMapper.deleteOrderItemByItemId(id);
            return 0;
        } else {
            OrderHead orderHead = orderMapper.getOrderHeadByHeadId2(ois.get(0).getOrderHeadId());
            FileUtil.delFolder(finalDirPath+"order/"+orderHead.getEngName()+"/"+orderHead.getOrderNo()+"/");
            orderMapper.deleteAllOrderHeadAndItemByItemId(id);
            orderMapper.deleteOrderItemByItemId(id);
            return 1;
        }
    }

    @Transactional
    public void addOrderHeadAndItemByBean(OrderHead orderHead, MultipartFile[] files) throws Exception {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(new Date());
        orderHead.setState(0);
        Integer num = 1;
        orderHead.setOrderSetNum(num);
        orderMapper.addOrderHeadByBean(orderHead);
        orderHead.setOrderHeadId(orderHead.getId());
        orderHead.setProductName(orderHead.getProductTotalName());
        orderHead.setItemCreateTimeStr(dateStr);
        orderMapper.addOrderItemByBean(orderHead);

        String urlName;
        String fileName;
        if (files.length > 0) {
            OrderItemAppend oia;
            for (MultipartFile file : files) {
                if (file.getOriginalFilename().trim().length() > 0) {
                    FileUtil.uploadOrderAppend(finalDirPath, orderHead.getEngName(), orderHead.getOrderNo(), file);
                    fileName = file.getOriginalFilename();
                    urlName = "order" + "\\" + orderHead.getEngName() + "\\" + orderHead.getOrderNo() + "\\" + fileName;
                    if (urlName != null) {
                        oia = new OrderItemAppend();
                        oia.setFileName(fileName);
                        oia.setOrderNo(orderHead.getOrderNo());
                        oia.setUrlName(urlName);
                        oia.setHeadId(orderHead.getId());
                        orderMapper.saveOrderItemAppend(oia);
                    }
                }
            }
        }
    }

    public void updateProjectData(OrderHead orderHead, List<OrderItem> ois, UserInfo userInfo, MultipartFile[] files) throws Exception {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(new Date());
        orderHead.setOrderSetNum(ois.size());
        OrderHead oldHead = orderMapper.getOldHeadByOrderNo(orderHead.getOrderNo());
        if (!oldHead.getProductTotalName().equals(orderHead.getProductTotalName()) || oldHead.getOrderSetNum() != orderHead.getOrderSetNum()) {
            orderHead.setHeadUpdateTimeStr(dateStr);
            orderHead.setUpdateHeadTimes((oldHead.getUpdateHeadTimes() == null ? 0 : oldHead.getUpdateHeadTimes()) + 1);
            orderMapper.updateOrderHeadByOrderNo(orderHead);
        }

        OrderItem oldItem;
        OrderItem newItem;
        for (int a = 0; a < ois.size(); a++) {
            newItem = ois.get(a);
            ois.get(a).setOrderHeadId(oldHead.getId());
            oldItem = orderMapper.getOldItemByOrderIdandNewestFinishNo(ois.get(a));
            if (!oldItem.getProductName().equals(newItem.getProductName()) || !oldItem.getNewFinishProudNo().equals(newItem.getNewFinishProudNo()) ||
                    !oldItem.getProductBigType().equals(newItem.getProductBigType()) || !oldItem.getProductMainShape().equals(newItem.getProductMainShape()) ||
                    oldItem.getNeedNum() != newItem.getNeedNum() || !oldItem.getProductSize().equals(newItem.getProductSize()) ||
                    !oldItem.getEdgeHightSize().equals(newItem.getEdgeHightSize()) || !oldItem.getItemDeliverTimeStr().equals(newItem.getItemDeliverTimeStr()) ||
                    !oldItem.getBackInstallSelect().equals(newItem.getBackInstallSelect()) || !oldItem.getMainMateriAndArt().equals(newItem.getMainMateriAndArt()) ||
                    !oldItem.getElectMateriNeeds().equals(newItem.getElectMateriNeeds()) || !oldItem.getInstallTransfBacking().equals(newItem.getInstallTransfBacking()) ||
                    !oldItem.getOtherRemark().equals(newItem.getOtherRemark())) {
                ois.get(a).setItemUpdateTimeStr(dateStr);
                ois.get(a).setUpdateItemTimes((oldItem.getUpdateItemTimes() == null ? 0 : oldItem.getUpdateItemTimes()) + 1);
                ois.get(a).setId(oldItem.getId());
                orderMapper.updateOrderItemById(ois.get(a));
            }
        }


        String urlName;
        String fileName;
        if (files.length > 0) {
            OrderItemAppend oia;
            for (MultipartFile file : files) {
                if (file.getOriginalFilename().trim().length() > 0) {
                    FileUtil.uploadOrderAppend(finalDirPath, orderHead.getEngName(), orderHead.getOrderNo(), file);
                    fileName = file.getOriginalFilename();
                    urlName = "order" + "\\" + orderHead.getEngName() + "\\" + orderHead.getOrderNo() + "\\" + fileName;
                    if (urlName != null) {
                        oia = new OrderItemAppend();
                        oia.setFileName(fileName);
                        oia.setOrderNo(orderHead.getOrderNo());
                        oia.setUrlName(urlName);
                        oia.setHeadId(orderHead.getId());
                        orderMapper.saveOrderItemAppend(oia);
                    }
                }
            }
        }
    }

    public List<String> findAllFileNameByOrderNo(String orderNo) throws Exception {
        return orderMapper.findAllFileNameByOrderNo(orderNo);
    }

    public List<OrderItemAppend> findAllItemAppendByOrderNoReal(String orderNo) throws Exception {
        return orderMapper.findAllItemAppendByOrderNoReal(orderNo);
    }

    public String fillDataToModuleExcelByOrderId(Integer id) throws Exception {
        List<OrderHead> orderHeadList = orderMapper.getOrderItemByHeadId(id);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sDateFormat.format(new Date());
        String excelName = orderHeadList.get(0).getOrderNo() + dateStr + ".xls";
        HSSFWorkbook wb = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        if (orderHeadList.size() == 1) {
            try {
                File newFile = new File(finalDirPath + "linshi/");
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                fis = new FileInputStream(finalDirPath + "module/单品订单打印模板.xls");
                POIFSFileSystem ps = new POIFSFileSystem(fis);
                wb = new HSSFWorkbook(ps);
                HSSFSheet hssfSheet = wb.getSheetAt(0);

                HSSFRow row = hssfSheet.getRow(0);
                Cell cell = row.getCell(0);
                cell.setCellValue(orderHeadList.get(0).getProductTotalName()+"制作需求表");

                row = hssfSheet.getRow(1);
                cell = row.getCell(0);
                cell.setCellValue(orderHeadList.get(0).getOrderTimeStr());

                row = hssfSheet.getRow(2);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getOrderNo());
                cell = row.getCell(3);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getSalor());

                row = hssfSheet.getRow(3);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getNewFinishProudNo());

                row = hssfSheet.getRow(4);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getProductName());
                cell = row.getCell(3);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getNeedNum());

                row = hssfSheet.getRow(5);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getProductSize());
                cell = row.getCell(3);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getEdgeHightSize());
                cell = row.getCell(6);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getItemDeliverTimeStr());


                row = hssfSheet.getRow(7);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getBackInstallSelect());

                row = hssfSheet.getRow(8);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getMainMateriAndArt());

                row = hssfSheet.getRow(9);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getElectMateriNeeds());

                row = hssfSheet.getRow(10);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getInstallTransfBacking());

                row = hssfSheet.getRow(11);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getInstallTransfBacking());

                row = hssfSheet.getRow(12);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getInstallTransfBacking());

                row = hssfSheet.getRow(13);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getOtherRemark());

                fos = new FileOutputStream(finalDirPath + "linshi/" + excelName);
                wb.write(fos);
            } catch (Exception e) {
                logger.info("复制excel表格异常1：" + e.getMessage());
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
                    logger.info("复制excel表格异常2：" + e.getMessage());
                }
            }
            return excelName;
        } else {
            try {
                File newFile = new File(finalDirPath + "linshi/");
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                fis = new FileInputStream(finalDirPath + "module/多项订单打印摸板.xls");
                POIFSFileSystem ps = new POIFSFileSystem(fis);
                wb = new HSSFWorkbook(ps);

                CellStyle style = wb.createCellStyle();
                style.setBorderBottom(CellStyle.BORDER_THIN);
                style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                style.setBorderLeft(CellStyle.BORDER_THIN);
                style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                style.setBorderRight(CellStyle.BORDER_THIN);
                style.setRightBorderColor(IndexedColors.BLACK.getIndex());
                style.setBorderTop(CellStyle.BORDER_THIN);
                style.setTopBorderColor(IndexedColors.BLACK.getIndex());
                style.setWrapText(true);

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
                cellStyle.setWrapText(true);

                HSSFSheet hssfSheet = wb.getSheetAt(0);

                HSSFRow row = hssfSheet.getRow(0);
                Cell cell = row.getCell(0);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getProductTotalName()+"制作需求表");

                row = hssfSheet.getRow(1);
                cell = row.getCell(0);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getOrderTimeStr());

                row = hssfSheet.getRow(2);
                cell = row.getCell(1);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getOrderNo());
                cell = row.getCell(3);//合并的单元格取第一个cell的位置对象
                cell.setCellValue(orderHeadList.get(0).getSalor());
                OrderHead oh;
                int beginRow = 3;
                for (int a = 0; a < orderHeadList.size(); a++) {
                    oh = orderHeadList.get(a);
                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(0);
                    cell.setCellValue("成品编号");
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getNewFinishProudNo());

                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(0);
                    cell.setCellValue("品名");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getProductName());
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(2);
                    cell.setCellValue("需求数量");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(3);
                    cell.setCellValue(oh.getNeedNum());
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(4);
                    cell.setCellValue("设计师签名");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(5);
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyle);

                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(0);
                    cell.setCellValue("产品尺寸");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getProductSize());
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(2);
                    cell.setCellValue("边高尺寸");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(3);
                    cell.setCellValue(oh.getEdgeHightSize());
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(4);
                    cell.setCellValue("交货时间");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(5);
                    cell.setCellValue(oh.getItemDeliverTimeStr());
                    cell.setCellStyle(cellStyle);


                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 2500);
                    cell = row.createCell(0);
                    cell.setCellValue("产品简易效果图");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(1);
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyle);


                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(0);
                    cell.setCellValue("主体材质及工艺");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getMainMateriAndArt());
                    cell.setCellStyle(style);

                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getMainMateriAndArt());
                    cell.setCellStyle(style);

                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(0);
                    cell.setCellValue("电子类辅料需求");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getMainMateriAndArt());
                    cell.setCellStyle(style);

                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(0);
                    cell.setCellValue("安装/运输/包装方式");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getInstallTransfBacking());
                    cell.setCellStyle(style);

                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getInstallTransfBacking());
                    cell.setCellStyle(style);

                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getInstallTransfBacking());
                    cell.setCellStyle(style);

                    row = hssfSheet.createRow(beginRow++);
                    row.setHeight((short) 400);
                    cell = row.createCell(0);
                    cell.setCellValue("其它备注");
                    cell.setCellStyle(cellStyle);
                    cell = row.createCell(1);
                    cell.setCellValue(oh.getOtherRemark());
                    cell.setCellStyle(style);

                    CellRangeAddress region = new CellRangeAddress(beginRow - 7, beginRow - 6, 0, 0);
                    hssfSheet.addMergedRegion(region);

                    region = new CellRangeAddress(beginRow - 4, beginRow - 2, 0, 0);
                    hssfSheet.addMergedRegion(region);

                    region = new CellRangeAddress(beginRow - 8, beginRow - 8, 1, 5);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);

                    region = new CellRangeAddress(beginRow - 7, beginRow - 7, 1, 5);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);

                    region = new CellRangeAddress(beginRow - 6, beginRow - 6, 1, 5);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);


                    region = new CellRangeAddress(beginRow - 5, beginRow - 5, 1, 5);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);


                    region = new CellRangeAddress(beginRow - 4, beginRow - 4, 1, 5);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);

                    region = new CellRangeAddress(beginRow - 3, beginRow - 3, 1, 5);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);

                    region = new CellRangeAddress(beginRow - 2, beginRow - 2, 1, 5);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);

                    region = new CellRangeAddress(beginRow - 1, beginRow - 1, 1, 5);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);

                    region = new CellRangeAddress(beginRow - 0, beginRow - 0, 1, 5);
                    RegionUtil.setBorderRight(HSSFBorderFormatting.BORDER_THIN, region, hssfSheet, wb);
                    hssfSheet.addMergedRegion(region);

                    beginRow++;

                }

                fos = new FileOutputStream(finalDirPath + "linshi/" + excelName);
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
    }

    public String transferExcelToPDF(String excelUrlName) throws Exception {
        int index = excelUrlName.lastIndexOf(".");
        String centerName = excelUrlName.substring(0, index);
        WordToPDF.WordToPDFOrder(excelUrlName, centerName.concat(".pdf"), finalDirPath);
        return finalDirPath + "linshi/" + centerName.concat(".pdf");
    }

    public void addOrderAppendByOrderNo(MultipartFile[] files, String orderNo) throws Exception {
        OrderHead orderHead = orderMapper.getOldHeadByOrderNo2(orderNo);
        String urlName;
        String fileName;
        if (files.length > 0) {
            OrderItemAppend oia;
            for (MultipartFile file : files) {
                if (file.getOriginalFilename().trim().length() > 0) {
                    FileUtil.uploadOrderAppend(finalDirPath, orderHead.getEngName(), orderHead.getOrderNo(), file);
                    fileName = file.getOriginalFilename();
                    urlName = "order" + "\\" + orderHead.getEngName() + "\\" + orderHead.getOrderNo() + "\\" + fileName;
                    if (urlName != null) {
                        oia = new OrderItemAppend();
                        oia.setFileName(fileName);
                        oia.setOrderNo(orderHead.getOrderNo());
                        oia.setUrlName(urlName);
                        oia.setHeadId(orderHead.getId());
                        orderMapper.saveOrderItemAppend(oia);
                    }
                }
            }
        }
    }


    public void deleteOrderItemAppendByItemAppendIds(Integer[] ids, String orderNo) throws Exception {
        OrderItemAppend oia;
        for (Integer id : ids) {
            oia = orderMapper.getOrderItemAppendById(id);
            FileUtil.delFile(finalDirPath + oia.getUrlName());
            orderMapper.deleteOrderItemAppendByAppendId(id);
        }
    }

    public List<OrderItemAppend> findAllItemAppendByOrderNo(Integer headId) throws Exception {
        return orderMapper.findAllItemAppendByOrderNo(headId);
    }

    public OrderHead getOrderHeadByOrderNo(String orderNo) throws Exception {
        return orderMapper.getOrderHeadByOrderNo(orderNo);
    }

    public List<String> findAllUrlByOrderNo(String orderNo) throws Exception {
        return orderMapper.findAllUrlByOrderNo(orderNo);
    }

    public void updateStateByOrderNo(OrderHead orderHead) throws Exception {
        orderMapper.updateStateByOrderNo(orderHead);
    }

    public String findNewestOrderNoBySalor(String empNo, String startTime, String endTime) throws Exception {
        return orderMapper.findNewestOrderNoBySalor(empNo, startTime, endTime);
    }

    public String findNewestFinishProdNoByOldFinishProdNo(String empNo, String startTime, String endTime) throws Exception {
        return orderMapper.findNewestFinishProdNoByOldFinishProdNo(empNo, startTime, endTime);
    }

    public List<OrderHead> getOrderItemByOrderHeadId(Integer id) throws Exception {
        return orderMapper.getOrderItemByOrderHeadId(id);
    }

    public String saveProjectData(OrderHead orderHead, List<OrderItem> ois, UserInfo userInfo, MultipartFile[] files) throws Exception {
        orderHead.setSingleOrProject(1);
        orderHead.setSalorNo(userInfo.getEmpNo());
        orderHead.setEngName(userInfo.getEngName());
        String orderNo;
        String newestOrderNo;
        SimpleDateFormat sDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr1 = sDateFormat1.format(new Date());
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sDateFormat.format(new Date());
        OrderHead isExsitOrderNo = orderMapper.getOrderNoBYOrderNo(orderHead.getOrderNo());
        String newestProductNo;
        OrderItemAppend oia;
        OrderItem isExsitProNo;
        String newestFinishProdNo;
        String returnmessage = "";
        String returnmessage2 = "";
        if (isExsitOrderNo == null) {
            orderHead.setOrderSetNum(ois.size());
            orderMapper.addOrderHeadByBean(orderHead);
        } else {
            returnmessage += "订单单号已存在，新订单单号为:" + orderHead.getOrderNo() + ".";
            orderNo = orderMapper.findNewestOrderNoBySalor(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
            newestOrderNo = StringUtil.increateOrderByOlderOrderNo(orderNo, userInfo.getShortOrderName());
        }
        for (int a = 0; a < ois.size(); a++) {
            ois.get(a).setOrderHeadId(orderHead.getId());
            ois.get(a).setItemCreateTimeStr(dateStr1);
            isExsitProNo = orderMapper.getProductNoByProductNoBeforSave(ois.get(a).getNewFinishProudNo());
            if (isExsitProNo == null) {
                orderMapper.addOrderItemByItemBean(ois.get(a));
            } else {
                returnmessage2 += ois.get(a).getNewFinishProudNo() + ",";
                newestProductNo = orderMapper.findNewestFinishProdNoByOldFinishProdNo(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
                newestFinishProdNo = StringUtil.increateFinishiNoByOrldFinishiNo(newestProductNo, userInfo.getShortOrderName());
                ois.get(a).setNewFinishProudNo(newestFinishProdNo);
                orderMapper.addOrderItemByItemBean(ois.get(a));
            }
        }

        String fileName;
        String urlName;
        if (files.length > 0) {
            for (MultipartFile file : files) {
                if (file.getOriginalFilename().trim().length() > 0) {
                    FileUtil.uploadOrderAppend(finalDirPath, orderHead.getEngName(), orderHead.getOrderNo(), file);
                    fileName = file.getOriginalFilename();
                    urlName = "order" + "\\" + orderHead.getEngName() + "\\" + orderHead.getOrderNo() + "\\" + fileName;
                    if (urlName != null) {
                        oia = new OrderItemAppend();
                        oia.setFileName(fileName);
                        oia.setOrderNo(orderHead.getOrderNo());
                        oia.setUrlName(urlName);
                        oia.setHeadId(orderHead.getId());
                        orderMapper.saveOrderItemAppend(oia);
                    }
                }
            }
        }
        if (returnmessage2.trim().length() > 0) {
            returnmessage2 = "新成品编号已存在，新的为:" + returnmessage2;
        }
        return returnmessage + returnmessage2;
    }

    public List<OrderHead> findAllOrderNo() throws Exception {
        return orderMapper.findAllOrderNo();
    }

    public List<String> findAllProdName() throws Exception {
        return orderMapper.findAllProdName();
    }


    public List<OrderHead> findAllOrderHead(OrderHead orderHead) throws Exception {
        return orderMapper.findAllOrderHead(orderHead);
    }

    public int findAllOrderHeadCount() throws Exception {
        return orderMapper.findAllOrderHeadCount();
    }

    public List<OrderHead> queryOrderHeadByCondition(OrderHead orderHead) throws Exception {
        return orderMapper.queryOrderHeadByCondition(orderHead);
    }

    public int queryOrderHeadByConditionCount(OrderHead orderHead) throws Exception {
        return orderMapper.queryOrderHeadByConditionCount(orderHead);
    }

}
