package com.cosun.cosunp.entity;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/1/16 0016 上午 8:36
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class AjaxBean {

    private DownloadView view;
    private List<String> privilegestrs;
    private List<String> privilegeusers;
    private List<String> folderOrFilesName;

    public List<String> getFolderOrFilesName() {
        return folderOrFilesName;
    }

    public void setFolderOrFilesName(List<String> folderOrFilesName) {
        this.folderOrFilesName = folderOrFilesName;
    }

    public List<String> getPrivilegeusers() {
        return privilegeusers;
    }

    public void setPrivilegeusers(List<String> privilegeusers) {
        this.privilegeusers = privilegeusers;
    }

    public List<String> getPrivilegestrs() {
        return privilegestrs;
    }

    public void setPrivilegestrs(List<String> privilegestrs) {
        this.privilegestrs = privilegestrs;
    }

    public DownloadView getView() {
        return view;
    }

    public void setView(DownloadView view) {
        this.view = view;
    }
}
