package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserInfo implements Serializable {


    private static final long serialVersionUID = -2934846573147845117L;
    private Integer uId;
    private String userName;
    private String userPwd;
    private Integer userActor;
    private Integer type;
    private String fullName;
    private Integer useruploadright;
    private String empNo;
    private Integer state;
    private String deptNo;
    private Integer flag;
    private String positionName;
    private String deptName;
    private String newestlogintime;
    private String webPrivi;
    private String sortMethod;
    private String sortByName;
    private String engName;
    private String shortOrderName;

    private String stateStr;
    private String useruploadrightStr;
    private String userActorStr;


    private List<Integer> useruploadrights;
    private List<Integer> userActors;
    private List<Integer> states;

    private List<Integer> nameIds;
    private List<Integer> ids;
    private List<Integer> deptIds;
    private List<Integer> sexIds;
    private List<Integer> positionIds;

    private String startIncomDateStr;
    private String endIncomDateStr;


    private int currentPage = 1;
    private int maxPage;
    private int recordCount;
    private int pageSize = 10;
    private int currentPageTotalNum;

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getShortOrderName() {
        return shortOrderName;
    }

    public void setShortOrderName(String shortOrderName) {
        this.shortOrderName = shortOrderName;
    }

    public String getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }

    public String getWebPrivi() {
        return webPrivi;
    }

    public void setWebPrivi(String webPrivi) {
        this.webPrivi = webPrivi;
    }

    public String getNewestlogintime() {
        return newestlogintime;
    }

    public void setNewestlogintime(String newestlogintime) {
        this.newestlogintime = newestlogintime;
    }

    public List<Integer> getNameIds() {
        return nameIds;
    }

    public void setNameIds(List<Integer> nameIds) {
        this.nameIds = nameIds;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public List<Integer> getUseruploadrights() {
        return useruploadrights;
    }

    public void setUseruploadrights(List<Integer> useruploadrights) {
        this.useruploadrights = useruploadrights;
    }

    public List<Integer> getUserActors() {
        return userActors;
    }

    public void setUserActors(List<Integer> userActors) {
        this.userActors = userActors;
    }

    public List<Integer> getStates() {
        return states;
    }

    public void setStates(List<Integer> states) {
        this.states = states;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<Integer> getSexIds() {
        return sexIds;
    }

    public void setSexIds(List<Integer> sexIds) {
        this.sexIds = sexIds;
    }

    public List<Integer> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<Integer> positionIds) {
        this.positionIds = positionIds;
    }

    public String getStartIncomDateStr() {
        return startIncomDateStr;
    }

    public void setStartIncomDateStr(String startIncomDateStr) {
        this.startIncomDateStr = startIncomDateStr;
    }

    public String getEndIncomDateStr() {
        return endIncomDateStr;
    }

    public void setEndIncomDateStr(String endIncomDateStr) {
        this.endIncomDateStr = endIncomDateStr;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getStateStr() {
        if(state!=null) {
            if(state==0) {
                return "未审核";
            }else if(state ==1) {
                return "审核通过";
            }else if(state==2) {
                return "审核未通过";
            }
        }
        return "";
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getUseruploadrightStr() {
        if(useruploadright!=null) {
            if(useruploadright==0) {
                return "无";
            }else if(useruploadright==1) {
                return "有";
            }
        }
        return "";
    }

    public void setUseruploadrightStr(String useruploadrightStr) {
        this.useruploadrightStr = useruploadrightStr;
    }

    public String getUserActorStr() {

        if(this.userActor!=null) {
            if(userActor==1) {
                return "总监级别";
            }else if(userActor==2) {
                return "总经理级别";
            }else if(userActor==3) {
                return "副总经理级别";
            }else if(userActor==4) {
                return "经理级别";
            }else if(userActor==5) {
                return "主管级别";
            }else if(userActor==6) {
                return "组长级别";
            }else{
                return "普通员工级别";
            }
        }
        return userActorStr;
    }

    public void setUserActorStr(String userActorStr) {
        this.userActorStr = userActorStr;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageTotalNum() {
        if (this.currentPage != 0)
            return (currentPage - 1) * pageSize;
        return 0;
    }

    public void setCurrentPageTotalNum(int currentPageTotalNum) {
        this.currentPageTotalNum = currentPageTotalNum;
    }


    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUseruploadright() {
        return useruploadright;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUseruploadright(Integer useruploadright) {
        this.useruploadright = useruploadright;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Integer getUserActor() {
        return userActor;
    }

    public void setUserActor(Integer userActor) {
        this.userActor = userActor;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
