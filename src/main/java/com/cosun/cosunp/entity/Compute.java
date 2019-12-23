package com.cosun.cosunp.entity;

import java.io.File;
import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/5/16 0016 上午 9:55
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Compute implements Serializable {

    private static final long serialVersionUID = -7917611767290395985L;

    private String fileLocation;
    private File originFile;

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public File getOriginFile() {
        return originFile;
    }

    public void setOriginFile(File originFile) {
        this.originFile = originFile;
    }
}
