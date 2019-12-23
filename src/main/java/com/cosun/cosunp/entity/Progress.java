package com.cosun.cosunp.entity;

/**
 * @author:homey Wong
 * @date:2019/2/28 0028 下午 8:24
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Progress {
    private long bytesRead;
    private long contentLength;
    private long items;

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getItems() {
        return items;
    }

    public void setItems(long items) {
        this.items = items;
    }
}
