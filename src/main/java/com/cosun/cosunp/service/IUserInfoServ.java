package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.UserInfo;

import java.util.List;

public interface IUserInfoServ {

    UserInfo findUserByUserNameandPassword(String userName, String userPwd) throws Exception;

    void setNewPasswordByuId(Integer uId,String newPassword) throws Exception;

    String getMobileNumByUserName(String userName) throws Exception;

    int getUserInfoCountByEmpNo(String empNo) throws Exception;

    Employee getUserInfoByEmpNo(String empNo) throws Exception;

    void updateUserInfoLoginTime(Integer uId,String datetime) throws Exception;

    int getUserInfoCountByUserName(String userName) throws Exception;

    void saveUserInfoByBean(UserInfo userInfo) throws Exception;

    List<UserInfo> findAllUser(UserInfo userInfo) throws Exception;

    UserInfo getUserInfoByUId(Integer uId) throws Exception;

    int findAllUserCount() throws Exception;

    void deleteUserInfotByUId(Integer uId) throws Exception;

    void deleteUserByBatch(List<Integer> ids) throws Exception;

    List<UserInfo> queryUserByCondition(UserInfo userInfo) throws Exception;

    int queryUserByConditionCount(UserInfo userInfo) throws Exception;

    void updateUserInfo(UserInfo userInfo) throws Exception;


}


