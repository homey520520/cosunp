package com.cosun.cosunp.tool;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;
import java.io.*;


/**
 * @author:homey Wong
 * @Date: 2020/6/12 0012 下午 1:41
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class BarCodeUtil {

//    public static void getBarCode(String msg,String path){
//        try {
//            File file=new File(path);
//            OutputStream ous=new FileOutputStream(file);
//            if(StringUtils.isEmpty(msg) || ous==null)
//                return;
//            //选择条形码类型(好多类型可供选择)
//            Code128Bean bean=new Code128Bean();
//            //设置长宽
//            final double moduleWidth=0.30;
//            final int resolution=300;
//            bean.setModuleWidth(moduleWidth);
//            bean.doQuietZone(false);
//            String format = "image/png";
//            // 输出流
//            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format,
//                    resolution, BufferedImage.TYPE_BYTE_BINARY, false, 0);
//            //生成条码
//            bean.generateBarcode(canvas,msg);
//            canvas.finish();
//        }catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    /**
     * 生成code128条形码
     *
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param message       要生成的文本
     * @param withQuietZone 是否两边留白
     * @param hideText      隐藏可读文本
     * @return 图片对应的字节码
     */
    public static void generateBarCode128(String message, Double height, Double width, boolean withQuietZone, boolean hideText, String path) {
        try {
            File file = new File(path);
            OutputStream ous = new FileOutputStream(file);
            if (StringUtils.isEmpty(message) || ous == null)
                return;

            Code128Bean bean = new Code128Bean();
            // 分辨率
            int dpi = 512;
            // 设置两侧是否留白
            bean.doQuietZone(withQuietZone);

            // 设置条形码高度和宽度
            bean.setBarHeight((double) ObjectUtils.defaultIfNull(height, 9.0D));
            if (width != null) {
                bean.setModuleWidth(width);
            }
            // 设置文本位置（包括是否显示）
            if (hideText) {
                bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
            }
            // 设置图片类型
            String format = "image/png";

            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // 生产条形码
            bean.generateBarcode(canvas, message);
            canvas.finish();
        } catch (Exception e) {
            //ByteArrayOutputStream won't happen
        }
    }


    public static void main(String[] args) {
        // String msg = "C200812YZ01BJ001";
        String msg = "C200526QL03TW001";
        String path = "D:\\pilar6669.jpg";
        BarCodeUtil.generateBarCode128(msg, 10.00, 0.3, true, false,path);

        //BarCodeUtil.generateBarCode128(msg,3.5d,0.09,false,false,path);
    }

}
