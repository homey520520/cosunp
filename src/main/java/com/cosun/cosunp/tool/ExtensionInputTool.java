package com.cosun.cosunp.tool;

/**
 * @author:homey Wong
 * @date:2019/7/22 0022 下午 5:05
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ExtensionInputTool {


    public static void main(String[] args) {
        try {
            new ReadTextUtil().readTxtUtil("C:\\Users\\Administrator\\Desktop\\扩展名.txt");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
