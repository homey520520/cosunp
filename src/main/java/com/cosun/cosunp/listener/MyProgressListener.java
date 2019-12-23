package com.cosun.cosunp.listener;

import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;

/**
 * @author:homey Wong
 * @date:2019/3/2 0002 上午 9:05
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class MyProgressListener implements ProgressListener {
    private HttpSession session;

    public MyProgressListener(HttpServletRequest request) {
        session = request.getSession();
    }

    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        double readM = pBytesRead / 1024.0 / 1024.0;
        double totalM = pContentLength / 1024.0 / 1024.0;
        double percent = readM / totalM;

        String readf = dataFormat(pBytesRead);
        String totalf = dataFormat(pContentLength);
        NumberFormat format = NumberFormat.getPercentInstance();
        String progress = format.format(percent);

        session.setAttribute("progress", progress);

    }


    public String dataFormat(double data) {
        String formdata = "";
        if (data >= 1024 * 1024) {
            formdata = Double.toString(data / 1024 / 1024) + "M";
        } else if (data >= 1024) {
            formdata = Double.toString(data / 1024) + "KB";
        } else {
            formdata = Double.toString(data) + "byte";
        }
        return formdata.substring(0, formdata.indexOf(".") + 2);
    }
}
