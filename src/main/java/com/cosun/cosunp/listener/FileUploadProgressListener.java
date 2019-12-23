package com.cosun.cosunp.listener;

import com.cosun.cosunp.entity.Progress;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author:homey Wong
 * @date:2019/2/28 0028 下午 8:25
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Component
public class FileUploadProgressListener implements ProgressListener {
    private HttpSession session;
    public void setSession(HttpSession session){
        this.session=session;
        Progress status = new Progress();
        session.setAttribute("status", status);
    }
    @Override
    public void update(long bytesRead, long contentLength, int items) {
        Progress status = (Progress) session.getAttribute("status");
        status.setBytesRead(bytesRead);
        status.setContentLength(contentLength);
        status.setItems(items);

    }
}
