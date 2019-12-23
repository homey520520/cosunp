package com.cosun.cosunp.entity;

import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/7/10 0010 上午 11:22
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FolderUpdateList {

    private String isSameFileUploadFolderName;
    List<String[]> urlAfterUpdateForNoRepeat;
    List<String> allPath;

    public List<String> getAllPath() {
        return allPath;
    }

    public void setAllPath(List<String> allPath) {
        this.allPath = allPath;
    }

    public String getIsSameFileUploadFolderName() {
        return isSameFileUploadFolderName;
    }

    public void setIsSameFileUploadFolderName(String isSameFileUploadFolderName) {
        this.isSameFileUploadFolderName = isSameFileUploadFolderName;
    }

    public List<String[]> getUrlAfterUpdateForNoRepeat() {
        return urlAfterUpdateForNoRepeat;
    }

    public void setUrlAfterUpdateForNoRepeat(List<String[]> urlAfterUpdateForNoRepeat) {
        this.urlAfterUpdateForNoRepeat = urlAfterUpdateForNoRepeat;
    }
}
