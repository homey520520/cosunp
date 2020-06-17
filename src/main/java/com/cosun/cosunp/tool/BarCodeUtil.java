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


    public static void generateBarCode128(String message, Double height, Double width, boolean withQuietZone, boolean hideText, String path) {
        try {
            File file = new File(path);
            OutputStream ous = new FileOutputStream(file);
            if (StringUtils.isEmpty(message) || ous == null)
                return;

            Code128Bean bean = new Code128Bean();
            int dpi = 512;
            bean.doQuietZone(withQuietZone);

            bean.setBarHeight((double) ObjectUtils.defaultIfNull(height, 9.0D));
            if (width != null) {
                bean.setModuleWidth(width);
            }
            if (hideText) {
                bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
            }
            String format = "image/png";

            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            bean.generateBarcode(canvas, message);
            canvas.finish();
        } catch (Exception e) {
        }
    }


    public static void main(String[] args) {
        String msg = "C200526QL03TW001";
        String path = "D:\\pilar6669.jpg";
        BarCodeUtil.generateBarCode128(msg, 10.00, 0.3, true, false,path);
    }

}
